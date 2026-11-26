from __future__ import annotations

import random
from dataclasses import dataclass

OPS = {
    "+": "+",
    "-": "-",
    "*": "×",
    "/": "÷",
}

PRECEDENCE = {"+": 1, "-": 1, "*": 2, "/": 2}

ANSWER_MIN = 0
ANSWER_MAX = 100

# 题库参数：数字上限按运算种类分档，两数题与三数题混合出现
CAPS_WITH_MULDIV = (9, 12, 20)
CAPS_ADD_SUB_ONLY = (9, 12, 20, 50, 80)
CAPS_DIV_ONLY = (12, 20, 50, 100)
TWO_TERM_RATE = 0.25


@dataclass(frozen=True)
class Problem:
    nums: tuple[int, ...]
    ops: tuple[str, ...]
    bracket: str  # "none" | "left" | "right"
    answer: int

    @property
    def text(self) -> str:
        if self.bracket == "left":
            inner = f"{self.nums[0]} {OPS[self.ops[0]]} {self.nums[1]}"
            rest = "".join(
                f" {OPS[op]} {num}" for op, num in zip(self.ops[1:], self.nums[2:])
            )
            return f"({inner}){rest}"
        if self.bracket == "right":
            open_at = len(self.nums) - 2
            head = _format(self.nums[:open_at], self.ops[: open_at - 1])
            inner = f"{self.nums[open_at]} {OPS[self.ops[open_at]]} {self.nums[open_at + 1]}"
            return f"{head} {OPS[self.ops[open_at - 1]]} ({inner})"
        return _format(self.nums, self.ops)


def _format(nums: tuple[int, ...], ops: tuple[str, ...]) -> str:
    text = str(nums[0])
    for op, num in zip(ops, nums[1:]):
        text += f" {OPS[op]} {num}"
    return text


def _in_range(value: int) -> bool:
    return ANSWER_MIN <= value <= ANSWER_MAX


def _apply(left: int, op: str, right: int) -> int | None:
    if op == "+":
        result = left + right
    elif op == "-":
        result = left - right
    elif op == "*":
        result = left * right
    elif op == "/":
        # 不出现除以 1，也不出现自己除以自己
        if right < 2 or left == right or left % right != 0:
            return None
        result = left // right
    else:
        return None

    if not _in_range(result):
        return None
    return result


def _apply_inner(left: int, op: str, right: int) -> int | None:
    """括号内必须是正整数。"""
    result = _apply(left, op, right)
    if result is None or result < 1:
        return None
    return result


def _evaluate_plain(nums: list[int], ops: list[str]) -> int | None:
    """先乘除后加减，每一步都必须是 0~100 的整数。"""
    values = [nums[0]]
    add_ops: list[str] = []
    for index, op in enumerate(ops):
        if op in ("*", "/"):
            merged = _apply(values.pop(), op, nums[index + 1])
            if merged is None:
                return None
            values.append(merged)
        else:
            values.append(nums[index + 1])
            add_ops.append(op)
    total = values[0]
    for index, op in enumerate(add_ops):
        merged = _apply(total, op, values[index + 1])
        if merged is None:
            return None
        total = merged
    return total


def evaluate(nums: list[int], ops: list[str], bracket: str = "none") -> int | None:
    """bracket="left" 时前两个数先算，"right" 时最后两个数先算。"""
    work_nums = list(nums)
    work_ops = list(ops)

    if bracket == "left":
        inner = _apply_inner(work_nums[0], work_ops[0], work_nums[1])
        if inner is None:
            return None
        work_nums = [inner, *work_nums[2:]]
        work_ops = work_ops[1:]
    elif bracket == "right":
        last = len(work_nums) - 2
        inner = _apply_inner(work_nums[last], work_ops[last], work_nums[last + 1])
        if inner is None:
            return None
        work_nums = [*work_nums[:last], inner]
        work_ops = work_ops[:last]

    return _evaluate_plain(work_nums, work_ops)


def _pick_max_number(ops: list[str], rng: random.Random) -> int:
    if all(op == "/" for op in ops):
        return rng.choice(CAPS_DIV_ONLY)
    if any(op in ("*", "/") for op in ops):
        return rng.choice(CAPS_WITH_MULDIV)
    return rng.choice(CAPS_ADD_SUB_ONLY)


def generate_problem(
    operators: list[str],
    *,
    with_bracket: bool = False,
    rng: random.Random | None = None,
) -> Problem:
    if not operators:
        raise ValueError("至少选择一种运算")

    rng = rng or random.Random()
    for _ in range(5000):
        term_count = 2 if rng.random() < TWO_TERM_RATE else 3
        ops = [rng.choice(operators) for _ in range(term_count - 1)]
        max_num = _pick_max_number(ops, rng)
        nums = [rng.randint(1, max_num) for _ in range(term_count)]
        bracket = "none"
        if with_bracket and term_count > 2:
            bracket = "left" if rng.random() < 0.5 else "right"
        answer = evaluate(nums, ops, bracket)
        if answer is None:
            continue
        return Problem(tuple(nums), tuple(ops), bracket, answer)

    raise RuntimeError("无法生成符合规则的题目，请换一组运算再试")


def generate_quiz(
    operators: list[str],
    count: int,
    *,
    with_bracket: bool = False,
    rng: random.Random | None = None,
) -> list[Problem]:
    """同一份练习里尽量不出现重复题目。"""
    rng = rng or random.Random()
    problems: list[Problem] = []
    seen: set[str] = set()
    tries = 0
    while len(problems) < count and tries < count * 20:
        tries += 1
        problem = generate_problem(operators, with_bracket=with_bracket, rng=rng)
        if problem.text in seen:
            continue
        seen.add(problem.text)
        problems.append(problem)
    while len(problems) < count:
        problems.append(generate_problem(operators, with_bracket=with_bracket, rng=rng))
    return problems
