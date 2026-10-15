from __future__ import annotations

import argparse
import sys

from .generator import generate_quiz


def _ask_operators() -> list[str]:
    mapping = {"+": "+", "-": "-", "*": "*", "x": "*", "×": "*",
               "/": "/", "÷": "/"}
    raw = input("选择运算(可多选, 如 +-*/ 直接连写, 回车默认 +-*): ").strip()
    ops: list[str] = []
    for ch in raw:
        if ch.isspace():
            continue
        op = mapping.get(ch)
        if op and op not in ops:
            ops.append(op)
    if not ops:
        ops = ["+", "-", "*"]
    return ops


def _ops_from_flag(raw: str) -> list[str]:
    mapping = {"+": "+", "-": "-", "*": "*", "x": "*", "×": "*",
               "/": "/", "÷": "/"}
    ops: list[str] = []
    for ch in raw:
        if ch.isspace():
            continue
        if ch not in mapping:
            raise ValueError(f"不支持的符号: {ch}")
        if mapping[ch] not in ops:
            ops.append(mapping[ch])
    if not ops:
        raise ValueError("请至少选择一种运算")
    return ops


def _ask_count() -> int:
    while True:
        raw = input("请选择题目数量 (1-100, 快捷: 5/10/20/50/100): ").strip()
        try:
            count = int(raw)
        except ValueError:
            count = 0
        if 1 <= count <= 100:
            return count
        print("输入无效, 请输入 1~100 的整数。")


def _read_answer() -> int:
    while True:
        raw = input().strip()
        try:
            return int(raw)
        except ValueError:
            print("请输入整数: ", end="")


def run_quiz(operators: list[str], count: int) -> int:
    problems = generate_quiz(operators, count)
    user_ans: list[int] = []
    correct = 0

    print()
    print("规则: 三个数、两个符号; 先乘除后加减; 每一步和最终结果都是正整数。")
    print()

    for index, problem in enumerate(problems, start=1):
        print(f"第 {index} / {count} 题: {problem.text} = ? ", end="")
        guess = _read_answer()
        user_ans.append(guess)
        if guess == problem.answer:
            print("正确! ✓")
            correct += 1
        else:
            print(f"错误, 正确答案是 {problem.answer}")
        print()

    rate = correct * 100.0 / count if count else 0
    print("=== 练习结果 ===")
    print(f"答对: {correct} / {count}  正确率: {rate:.1f}%")
    print()
    print("错题回顾:")
    for problem, guess in zip(problems, user_ans):
        mark = "✓" if guess == problem.answer else "x"
        print(f"[{mark}] {problem.text} = {problem.answer} (你的答案: {guess})")
    return 0 if correct == count else 1


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(
        description="小学生四则运算练习: 三个数字、两个符号, 结果为正整数。")
    parser.add_argument("--ops", default="", help="直接指定运算, 例如 +-* 或 +-*/")
    parser.add_argument("--count", type=int, default=0, help="题目数量 (1-100)")
    return parser


def main(argv: list[str] | None = None) -> int:
    parser = build_parser()
    args = parser.parse_args(argv)

    print("=== 小学生四则运算练习 (Python版) ===")
    print("三数两符号, 先乘除后加减, 每一步都是正整数")
    print()

    if args.ops:
        try:
            operators = _ops_from_flag(args.ops)
        except ValueError as exc:
            print(exc)
            return 2
    else:
        operators = _ask_operators()

    if args.count and 1 <= args.count <= 100:
        count = args.count
    else:
        if args.count:
            print("count 非法, 改为交互输入。")
        count = _ask_count()
    return run_quiz(operators, count)


if __name__ == "__main__":
    sys.exit(main())
