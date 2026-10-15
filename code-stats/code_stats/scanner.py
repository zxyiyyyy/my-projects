from __future__ import annotations

import fnmatch
import os
from pathlib import Path

from .counter import FileStats, LanguageStats, ProjectStats, count_file
from .languages import detect_language, register_extension
from .languages import CommentRules as Rules


DEFAULT_EXCLUDES = {
    ".git",
    ".hg",
    ".svn",
    ".bzr",
    "__pycache__",
    ".pytest_cache",
    ".mypy_cache",
    ".ruff_cache",
    ".tox",
    ".venv",
    "venv",
    "env",
    ".env",
    "node_modules",
    "bower_components",
    "vendor",
    "dist",
    "build",
    "out",
    "target",
    "bin",
    "obj",
    ".idea",
    ".vscode",
    ".cursor",
    "coverage",
    ".coverage",
    "htmlcov",
    ".next",
    ".nuxt",
    ".svelte-kit",
    ".angular",
    ".gradle",
    ".terraform",
    "Pods",
    "DerivedData",
    ".DS_Store",
    "Thumbs.db",
}


def _should_exclude(name: str, exclude_names: set[str], exclude_patterns: list[str]) -> bool:
    if name in exclude_names:
        return True
    return any(fnmatch.fnmatch(name, pattern) for pattern in exclude_patterns)


def _load_ignore_file(root: Path) -> tuple[set[str], list[str]]:
    ignore_path = root / ".codestatsignore"
    if not ignore_path.is_file():
        return set(), []

    names: set[str] = set()
    patterns: list[str] = []
    for raw_line in ignore_path.read_text(encoding="utf-8", errors="replace").splitlines():
        line = raw_line.strip()
        if not line or line.startswith("#"):
            continue
        if any(ch in line for ch in "*?[]"):
            patterns.append(line)
        else:
            names.add(line.rstrip("/\\"))
    return names, patterns


def scan_paths(
    paths: list[str],
    *,
    include_unknown: bool = False,
    allowed_languages: set[str] | None = None,
    exclude_names: set[str] | None = None,
    exclude_patterns: list[str] | None = None,
    custom_extensions: dict[str, str] | None = None,
) -> ProjectStats:
    if custom_extensions:
        for ext, lang_name in custom_extensions.items():
            register_extension(ext, lang_name, Rules())

    exclude_names = DEFAULT_EXCLUDES | (exclude_names or set())
    exclude_patterns = list(exclude_patterns or [])

    file_stats: list[FileStats] = []
    skipped = 0
    unknown = 0

    for base in paths:
        base_path = Path(base).resolve()
        if not base_path.exists():
            continue

        local_excludes, local_patterns = _load_ignore_file(base_path)
        active_names = exclude_names | local_excludes
        active_patterns = exclude_patterns + local_patterns

        if base_path.is_file():
            candidates = [base_path]
        else:
            candidates = _walk_files(base_path, active_names, active_patterns)

        for file_path in candidates:
            language = detect_language(str(file_path))
            if language is None:
                if include_unknown and file_path.is_file():
                    unknown += 1
                    stats = count_file(str(file_path), _unknown_language(file_path))
                    if stats:
                        file_stats.append(stats)
                continue

            if allowed_languages is not None and language.name not in allowed_languages:
                continue

            stats = count_file(str(file_path), language)
            if stats is None:
                skipped += 1
                continue
            file_stats.append(stats)

    project = ProjectStats(by_file=file_stats, skipped_files=skipped, unknown_files=unknown)
    for stats in file_stats:
        bucket = project.by_language.setdefault(stats.language, LanguageStats(stats.language))
        bucket.add(stats)
    return project


def _walk_files(root: Path, exclude_names: set[str], exclude_patterns: list[str]) -> list[Path]:
    results: list[Path] = []
    for dirpath, dirnames, filenames in os.walk(root):
        dirnames[:] = [
            name
            for name in dirnames
            if not _should_exclude(name, exclude_names, exclude_patterns)
        ]

        current = Path(dirpath)
        if _should_exclude(current.name, exclude_names, exclude_patterns):
            continue

        for filename in filenames:
            if _should_exclude(filename, exclude_names, exclude_patterns):
                continue
            results.append(current / filename)
    return results


def _unknown_language(path: Path) -> "Language":
    from .languages import Language

    return Language("Unknown", frozenset({path.suffix.lower()}), None)
