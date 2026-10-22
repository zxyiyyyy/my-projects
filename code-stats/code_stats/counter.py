from __future__ import annotations

import re
from dataclasses import dataclass, field

from .languages import CommentRules, Language, detect_language


@dataclass
class FileStats:
    path: str
    language: str
    files: int = 1
    blank: int = 0
    comment: int = 0
    code: int = 0
    functions: int = 0

    @property
    def total(self) -> int:
        return self.blank + self.comment + self.code


@dataclass
class LanguageStats:
    language: str
    files: int = 0
    blank: int = 0
    comment: int = 0
    code: int = 0
    functions: int = 0

    @property
    def total(self) -> int:
        return self.blank + self.comment + self.code

    def add(self, stats: FileStats) -> None:
        self.files += stats.files
        self.blank += stats.blank
        self.comment += stats.comment
        self.code += stats.code
        self.functions += stats.functions


@dataclass
class ProjectStats:
    by_language: dict[str, LanguageStats] = field(default_factory=dict)
    by_file: list[FileStats] = field(default_factory=list)
    skipped_files: int = 0
    unknown_files: int = 0

    @property
    def total(self) -> LanguageStats:
        total = LanguageStats("TOTAL")
        for stats in self.by_language.values():
            total.files += stats.files
            total.blank += stats.blank
            total.comment += stats.comment
            total.code += stats.code
            total.functions += stats.functions
        return total


# Function definition patterns per language group
_FUNC_PATTERNS = {
    "c_cpp": re.compile(
        r"^\s*(?:static\s+|extern\s+|inline\s+|virtual\s+|override\s+)*"
        r"(?:void|int|char|float|double|long|short|bool|unsigned\s+\w+|size_t|auto|\w+::\w+|\w+)\s+"
        r"\w+\s*\([^;]*$"
    ),
    "java": re.compile(
        r"^\s*(?:public|private|protected|static|final|abstract|synchronized|native)*\s*"
        r"(?:void|int|char|float|double|long|short|boolean|String|var|\w+)\s+"
        r"\w+\s*\([^;]*$"
    ),
    "python": re.compile(r"^\s*def\s+\w+\s*\("),
    "csharp": re.compile(
        r"^\s*(?:public|private|protected|internal|static|readonly|virtual|override|async)*\s*"
        r"(?:void|int|char|float|double|long|short|bool|string|var|\w+)\s+"
        r"\w+\s*\([^;]*$"
    ),
}


def _count_functions(content: str, language_name: str) -> int:
    """Roughly count function definitions in a file."""
    name = language_name.lower()
    if "python" in name:
        pattern = _FUNC_PATTERNS["python"]
    elif "c++" in name or "c" == name:
        pattern = _FUNC_PATTERNS["c_cpp"]
    elif "java" in name:
        pattern = _FUNC_PATTERNS["java"]
    elif "c#" in name or "csharp" in name:
        pattern = _FUNC_PATTERNS["csharp"]
    else:
        return 0

    count = 0
    for line in content.splitlines():
        stripped = line.strip()
        if not stripped or stripped.startswith(("//", "#", "*", "/*")):
            continue
        if pattern.match(line):
            count += 1
    return count


def _strip_bom(text: str) -> str:
    return text.lstrip("﻿")


def _is_blank(line: str) -> bool:
    return not line.strip()


def _starts_with_any(line: str, prefixes: tuple[str, ...]) -> bool:
    stripped = line.lstrip()
    return any(stripped.startswith(prefix) for prefix in prefixes)


def count_lines(content: str, rules: CommentRules | None) -> tuple[int, int, int]:
    """Return blank, comment, and code line counts."""
    if rules is None:
        lines = content.splitlines()
        blank = sum(1 for line in lines if _is_blank(line))
        total = len(lines)
        return blank, 0, total - blank

    blank = 0
    comment = 0
    code = 0
    in_block = False
    block_end = rules.block_end or ""

    for raw_line in content.splitlines():
        line = _strip_bom(raw_line)
        if _is_blank(line):
            blank += 1
            continue
        if in_block:
            comment += 1
            if block_end and block_end in line:
                in_block = False
            continue
        stripped = line.lstrip()
        if rules.line_comments and _starts_with_any(stripped, rules.line_comments):
            comment += 1
            continue
        if rules.block_start and rules.block_start in stripped:
            comment += 1
            start_idx = stripped.find(rules.block_start)
            after_start = stripped[start_idx + len(rules.block_start):]
            if rules.block_end and rules.block_end in after_start:
                end_idx = after_start.find(rules.block_end)
                remainder = after_start[end_idx + len(rules.block_end):].strip()
                if remainder:
                    code += 1
            else:
                in_block = True
            continue
        code += 1
    return blank, comment, code


def count_file(path: str, language: Language | None = None) -> FileStats | None:
    import os

    if language is None:
        language = detect_language(path)
    if language is None:
        return None

    try:
        with open(path, encoding="utf-8", errors="replace") as handle:
            content = handle.read()
    except OSError:
        return None

    if language.name == "Jupyter Notebook" and path.endswith(".ipynb"):
        content = _extract_notebook_source(content)

    blank, comment, code = count_lines(content, language.rules)
    funcs = _count_functions(content, language.name)

    return FileStats(
        path=path,
        language=language.name,
        blank=blank,
        comment=comment,
        code=code,
        functions=funcs,
    )


def _extract_notebook_source(content: str) -> str:
    import json

    try:
        notebook = json.loads(content)
    except json.JSONDecodeError:
        return content

    cells = notebook.get("cells", [])
    parts: list[str] = []
    for cell in cells:
        if cell.get("cell_type") != "code":
            continue
        source = cell.get("source", [])
        if isinstance(source, list):
            parts.append("".join(source))
        elif isinstance(source, str):
            parts.append(source)
    return "\n".join(parts)


def aggregate_file_stats(file_stats: list[FileStats]) -> ProjectStats:
    project = ProjectStats(by_file=file_stats)
    for stats in file_stats:
        bucket = project.by_language.setdefault(stats.language, LanguageStats(stats.language))
        bucket.add(stats)
    return project
