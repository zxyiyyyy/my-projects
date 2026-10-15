#include <algorithm>
#include <ctime>
#include <iostream>
#include <vector>
#include <string>
#include <random>
#include <optional>
#include <limits>

struct Problem {
    int a, b, c;
    char op1, op2;
    int answer;
};

int precedence(char op) { return (op == '*' || op == '/') ? 2 : 1; }

std::optional<int> applyOp(int left, char op, int right) {
    long result = 0;
    if (op == '+') result = (long)left + right;
    else if (op == '-') result = (long)left - right;
    else if (op == '*') result = (long)left * right;
    else if (op == '/') {
        if (right == 0 || left % right != 0) return std::nullopt;
        result = left / right;
    } else return std::nullopt;
    if (result <= 0) return std::nullopt;
    return (int)result;
}

std::optional<int> evaluate(int a, char op1, int b, char op2, int c) {
    if (precedence(op1) >= precedence(op2)) {
        auto mid = applyOp(a, op1, b);
        if (!mid) return std::nullopt;
        return applyOp(*mid, op2, c);
    }
    auto mid = applyOp(b, op2, c);
    if (!mid) return std::nullopt;
    return applyOp(a, op1, *mid);
}

std::string opDisplay(char op) {
    if (op == '*') return "x";
    if (op == '/') return "/";
    return std::string(1, op);
}

int main() {
    std::mt19937 rng((unsigned)time(nullptr));
    std::cout << "=== 小学生四则运算练习 (C++版) ===\n";
    std::cout << "三数两符号, 先乘除后加减, 每一步都是正整数\n\n";

    // 选择运算
    std::string opsInput;
    std::cout << "选择运算(可多选, 如 +-*/ 直接连写, 回车默认 +-*): ";
    std::getline(std::cin, opsInput);
    std::vector<char> ops;
    for (char ch : opsInput) {
        if ((ch == '+' || ch == '-' || ch == '*' || ch == '/') &&
            std::find(ops.begin(), ops.end(), ch) == ops.end())
            ops.push_back(ch);
    }
    if (ops.empty()) ops = {'+', '-', '*'};

    // 选择数量
    int count = 0;
    while (true) {
        std::cout << "请选择题目数量 (1-100, 快捷: 5/10/20/50/100): ";
        std::string line;
        std::getline(std::cin, line);
        try {
            count = std::stoi(line);
        } catch (...) { count = 0; }
        if (count >= 1 && count <= 100) break;
        std::cout << "输入无效, 请输入 1~100 的整数。\n";
    }

    // 出题
    std::uniform_int_distribution<int> numDist(1, 12);
    std::vector<Problem> problems;
    int guard = 0;
    while ((int)problems.size() < count && guard++ < count * 5000) {
        int a = numDist(rng), b = numDist(rng), c = numDist(rng);
        char op1 = ops[rng() % ops.size()];
        char op2 = ops[rng() % ops.size()];
        auto ans = evaluate(a, op1, b, op2, c);
        if (ans) problems.push_back({a, b, c, op1, op2, *ans});
    }
    if ((int)problems.size() < count) {
        std::cerr << "出题失败, 请换一组运算再试。\n";
        return 1;
    }

    // 答题
    int correct = 0;
    std::vector<int> userAns(count);
    for (int i = 0; i < count; ++i) {
        auto &p = problems[i];
        std::cout << "\n第 " << (i + 1) << " / " << count << " 题: "
                  << p.a << " " << opDisplay(p.op1) << " "
                  << p.b << " " << opDisplay(p.op2) << " "
                  << p.c << " = ? ";
        int v;
        while (!(std::cin >> v)) {
            std::cout << "请输入整数: ";
            std::cin.clear();
            std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        }
        std::cin.ignore(std::numeric_limits<std::streamsize>::max(), '\n');
        userAns[i] = v;
        if (v == p.answer) {
            std::cout << "正确! ✓\n";
            ++correct;
        } else {
            std::cout << "错误, 正确答案是 " << p.answer << "\n";
        }
    }

    // 结算
    double rate = count ? correct * 100.0 / count : 0;
    std::cout << "\n=== 练习结果 ===\n";
    std::cout << "答对: " << correct << " / " << count
              << "  正确率: " << rate << "%\n\n错题回顾:\n";
    for (int i = 0; i < count; ++i) {
        auto &p = problems[i];
        bool ok = userAns[i] == p.answer;
        std::cout << (ok ? "[✓] " : "[x] ")
                  << p.a << " " << opDisplay(p.op1) << " "
                  << p.b << " " << opDisplay(p.op2) << " "
                  << p.c << " = " << p.answer
                  << " (你的答案: " << userAns[i] << ")\n";
    }
    return 0;
}
