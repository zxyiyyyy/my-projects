from __future__ import annotations

import argparse
import sys

from .generator import generate_quiz

MENU = {
    "1": ("+", "加法 +"),
    "2": ("-", "减法 -"),
    "3": ("*", "乘法 ×"),
    "4": ("/", "除法 ÷"),
}


def _ask_operators() -> list[str]:
    print("请选择要用的运算（可多选，输入数字，用空格分开，例如：1 3）")
    for key, (_, label) in MENU.items():
        print(f"  {key}. {label}")

    while True:
        raw = input("你的选择：").strip()
        tokens = raw.replace(",", " ").split()
        ops: list[str] = []
        for token in tokens:
            if token not in MENU:
                print("只能输入 1、2、3、4。")
                ops = []
                break
            ops.append(MENU[token][0])
        if ops:
            return ops


def _ask_count(default: int = 10) -> int:
    raw = input(f"要出几道题？（直接回车表示 {default} 题）：").strip()
    if not raw:
        return default
    try:
        count = int(raw)
    except ValueError:
        print("输入不是数字，改用默认题数。")
        return default
    if count < 1:
        print("题数太少，改用默认题数。")
        return default
    return min(count, 50)


def _read_answer() -> int | None:
    raw = input("你的答案：").strip()
    try:
        return int(raw)
    except ValueError:
        print("请输入整数。")
        return None


def run_quiz(operators: list[str], count: int) -> int:
    problems = generate_quiz(operators, count)
    correct = 0

    print()
    print("规则：三个数字、两个符号；×÷ 先算；每一步和最终结果都是正整数。")
    print()

    for index, problem in enumerate(problems, start=1):
        print(f"第 {index} 题：{problem.text} = ?")
        guess = _read_answer()
        if guess == problem.answer:
            print("正确！")
            correct += 1
        else:
            print(f"不对。正确答案是 {problem.answer}。")
        print()

    print(f"完成！{correct}/{count} 题正确。")
    return 0 if correct == count else 1


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(description="小学生四则运算练习：三个数字、两个符号，结果为正整数。")
    parser.add_argument("--ops", default="", help="直接指定运算，例如 +-* 或 +-*/")
    parser.add_argument("--count", type=int, default=0, help="题目数量")
    return parser


def _ops_from_flag(raw: str) -> list[str]:
    mapping = {"+": "+", "-": "-", "*": "*", "x": "*", "×": "*", "/": "/", "÷": "/"}
    ops: list[str] = []
    for ch in raw:
        if ch.isspace():
            continue
        if ch not in mapping:
            raise ValueError(f"不支持的符号：{ch}")
        ops.append(mapping[ch])
    if not ops:
        raise ValueError("请至少选择一种运算")
    return ops


def main(argv: list[str] | None = None) -> int:
    parser = build_parser()
    args = parser.parse_args(argv)

    print("小学生四则运算练习")
    print("每题都是：数字 符号 数字 符号 数字")
    print()

    if args.ops:
        try:
            operators = _ops_from_flag(args.ops)
        except ValueError as exc:
            print(exc)
            return 2
    else:
        operators = _ask_operators()

    count = args.count if args.count > 0 else _ask_count()
    return run_quiz(operators, count)


if __name__ == "__main__":
    sys.exit(main())
