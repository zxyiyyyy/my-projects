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


@dataclass(frozen=True)
class Problem:
    a: int
    op1: str
    b: int
    op2: str
    c: int
    answer: int

    @property
    def text(self) -> str:
        return f"{self.a} {OPS[self.op1]} {self.b} {OPS[self.op2]} {self.c}"


def _apply(left: int, op: str, right: int) -> int | None:
    if op == "+":
        result = left + right
    elif op == "-":
        result = left - right
    elif op == "*":
        result = left * right
    elif op == "/":
        if right == 0 or left % right != 0:
            return None
        result = left // right
    else:
        return None

    if result <= 0:
        return None
    return result


def evaluate(a: int, op1: str, b: int, op2: str, c: int) -> int | None:
    """Evaluate a op1 b op2 c with ×÷ first, then +-. Every step must stay a positive integer."""
    if PRECEDENCE[op1] >= PRECEDENCE[op2]:
        mid = _apply(a, op1, b)
        if mid is None:
            return None
        return _apply(mid, op2, c)

    mid = _apply(b, op2, c)
    if mid is None:
        return None
    return _apply(a, op1, mid)


def generate_problem(
    operators: list[str],
    *,
    min_n: int = 1,
    max_n: int = 12,
    rng: random.Random | None = None,
) -> Problem:
    if not operators:
        raise ValueError("至少选择一种运算")

    rng = rng or random.Random()
    for _ in range(5000):
        a = rng.randint(min_n, max_n)
        b = rng.randint(min_n, max_n)
        c = rng.randint(min_n, max_n)
        op1 = rng.choice(operators)
        op2 = rng.choice(operators)
        answer = evaluate(a, op1, b, op2, c)
        if answer is None:
            continue
        return Problem(a, op1, b, op2, c, answer)

    raise RuntimeError("无法生成符合规则的题目，请换一组运算再试")


def generate_quiz(operators: list[str], count: int, **kwargs) -> list[Problem]:
    return [generate_problem(operators, **kwargs) for _ in range(count)]
