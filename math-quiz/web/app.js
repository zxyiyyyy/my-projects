const OPS_DISPLAY = { "+": "+", "-": "−", "*": "×", "/": "÷" };
const PRECEDENCE = { "+": 1, "-": 1, "*": 2, "/": 2 };

function apply(left, op, right) {
  let result;
  if (op === "+") result = left + right;
  else if (op === "-") result = left - right;
  else if (op === "*") result = left * right;
  else if (op === "/") {
    if (right === 0 || left % right !== 0) return null;
    result = Math.floor(left / right);
  } else return null;
  if (result <= 0) return null;
  return result;
}

function evaluate(a, op1, b, op2, c) {
  if (PRECEDENCE[op1] >= PRECEDENCE[op2]) {
    const mid = apply(a, op1, b);
    if (mid === null) return null;
    return apply(mid, op2, c);
  }
  const mid = apply(b, op2, c);
  if (mid === null) return null;
  return apply(a, op1, mid);
}

function randomInt(min, max) {
  return Math.floor(Math.random() * (max - min + 1)) + min;
}

function generateProblem(operators, minN = 1, maxN = 12) {
  if (!operators.length) throw new Error("至少选择一种运算");
  for (let attempt = 0; attempt < 5000; attempt++) {
    const a = randomInt(minN, maxN);
    const b = randomInt(minN, maxN);
    const c = randomInt(minN, maxN);
    const op1 = operators[Math.floor(Math.random() * operators.length)];
    const op2 = operators[Math.floor(Math.random() * operators.length)];
    const answer = evaluate(a, op1, b, op2, c);
    if (answer !== null) return { a, op1, b, op2, c, answer };
  }
  throw new Error("无法生成符合规则的题目，请换一组运算再试");
}

function generateQuiz(operators, count) {
  return Array.from({ length: count }, () => generateProblem(operators));
}

function formatProblem(problem) {
  const { a, op1, b, op2, c } = problem;
  return `${a} ${OPS_DISPLAY[op1]} ${b} ${OPS_DISPLAY[op2]} ${c}`;
}

const setupView = document.getElementById("setup-view");
const quizView = document.getElementById("quiz-view");
const resultView = document.getElementById("result-view");
const setupError = document.getElementById("setup-error");
const questionCountInput = document.getElementById("question-count");
const startBtn = document.getElementById("start-btn");
const progressText = document.getElementById("progress-text");
const progressFill = document.getElementById("progress-fill");
const problemText = document.getElementById("problem-text");
const answerInput = document.getElementById("answer-input");
const feedbackEl = document.getElementById("feedback");
const submitBtn = document.getElementById("submit-btn");
const statCorrect = document.getElementById("stat-correct");
const statTotal = document.getElementById("stat-total");
const statRate = document.getElementById("stat-rate");
const reviewList = document.getElementById("review-list");
const retryBtn = document.getElementById("retry-btn");

let session = { problems: [], index: 0, results: [] };

function show(view) {
  setupView.hidden = view !== "setup";
  quizView.hidden = view !== "quiz";
  resultView.hidden = view !== "result";
}

function getSelectedOperators() {
  return [...document.querySelectorAll('input[name="op"]:checked')].map((el) => el.value);
}

function clampCount(raw) {
  const n = Number.parseInt(String(raw), 10);
  if (!Number.isFinite(n) || n < 1) return 10;
  return Math.min(n, 100);
}

function startQuiz() {
  setupError.hidden = true;
  const operators = getSelectedOperators();
  if (!operators.length) {
    setupError.textContent = "请至少选择一种运算。";
    setupError.hidden = false;
    return;
  }

  const count = clampCount(questionCountInput.value);
  questionCountInput.value = String(count);

  try {
    session = { problems: generateQuiz(operators, count), index: 0, results: [] };
  } catch (err) {
    setupError.textContent = err instanceof Error ? err.message : "出题失败";
    setupError.hidden = false;
    return;
  }

  show("quiz");
  renderQuestion();
}

function renderQuestion() {
  const { problems, index } = session;
  const total = problems.length;
  const problem = problems[index];

  progressText.textContent = `第 ${index + 1} / ${total} 题`;
  progressFill.style.width = `${((index + 1) / total) * 100}%`;
  problemText.textContent = `${formatProblem(problem)} = ?`;

  answerInput.value = "";
  answerInput.focus();
  feedbackEl.hidden = true;
  submitBtn.textContent = index === total - 1 ? "提交并查看结果" : "提交";
}

function submitAnswer() {
  const raw = answerInput.value.trim();
  if (raw === "") {
    feedbackEl.textContent = "请输入整数答案。";
    feedbackEl.className = "feedback error";
    feedbackEl.hidden = false;
    return;
  }

  const guess = Number.parseInt(raw, 10);
  if (!Number.isFinite(guess)) {
    feedbackEl.textContent = "请输入整数答案。";
    feedbackEl.className = "feedback error";
    feedbackEl.hidden = false;
    return;
  }

  const problem = session.problems[session.index];
  const correct = guess === problem.answer;
  session.results.push({ correct, userAnswer: guess });

  if (session.index >= session.problems.length - 1) {
    showResults();
    return;
  }

  session.index += 1;
  renderQuestion();
}

function showResults() {
  const total = session.problems.length;
  const correctCount = session.results.filter((r) => r.correct).length;
  const rate = total === 0 ? 0 : Math.round((correctCount / total) * 1000) / 10;

  statCorrect.textContent = String(correctCount);
  statTotal.textContent = String(total);
  statRate.textContent = `${rate}%`;

  reviewList.replaceChildren();
  session.problems.forEach((problem, i) => {
    const result = session.results[i];
    const li = document.createElement("li");
    li.className = result.correct ? "ok" : "bad";
    const mark = result.correct ? "✓" : "✗";
    li.textContent = `${mark} ${formatProblem(problem)} = ${problem.answer}（你的答案：${result.userAnswer}）`;
    reviewList.appendChild(li);
  });

  show("result");
}

startBtn.addEventListener("click", startQuiz);
document.querySelectorAll(".count-presets .chip").forEach((chip) => {
  chip.addEventListener("click", () => {
    questionCountInput.value = chip.dataset.count;
    document.querySelectorAll(".count-presets .chip").forEach((c) => c.classList.remove("active"));
    chip.classList.add("active");
  });
});
questionCountInput.addEventListener("input", () => {
  document.querySelectorAll(".count-presets .chip").forEach((c) =>
    c.classList.toggle("active", c.dataset.count === questionCountInput.value.trim())
  );
});
submitBtn.addEventListener("click", submitAnswer);
answerInput.addEventListener("keydown", (e) => {
  if (e.key === "Enter") submitAnswer();
});
retryBtn.addEventListener("click", () => show("setup"));

show("setup");
