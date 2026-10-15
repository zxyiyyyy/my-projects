from __future__ import annotations

import argparse
import json
import sys
from typing import Sequence

from . import __version__
from .counter import LanguageStats
from .scanner import scan_paths

COURSE_LANGUAGES = ("C", "C++", "Java", "Python")

LANGUAGE_ALIASES = {
    "c": "C",
    "c++": "C++",
    "cpp": "C++",
    "c plus plus": "C++",
    "java": "Java",
    "python": "Python",
    "py": "Python",
}


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(
        prog="codestats",
        description="Count lines of code across programming languages.",
    )
    parser.add_argument(
        "paths",
        nargs="*",
        default=["."],
        help="Files or directories to scan (default: current directory)",
    )
    parser.add_argument(
        "-V",
        "--version",
        action="version",
        version=f"codestats {__version__}",
    )
    parser.add_argument(
        "--json",
        action="store_true",
        help="Output results as JSON",
    )
    parser.add_argument(
        "--by-file",
        action="store_true",
        help="Show per-file breakdown",
    )
    parser.add_argument(
        "--include-unknown",
        action="store_true",
        help="Include files with unrecognized extensions",
    )
    parser.add_argument(
        "--exclude",
        action="append",
        default=[],
        metavar="NAME",
        help="Directory or file name to exclude (repeatable)",
    )
    parser.add_argument(
        "--exclude-pattern",
        action="append",
        default=[],
        metavar="GLOB",
        help="Glob pattern to exclude (repeatable)",
    )
    parser.add_argument(
        "--ext",
        action="append",
        default=[],
        metavar="EXT:LANG",
        help="Register custom extension mapping, e.g. .mylang:MyLang",
    )
    parser.add_argument(
        "--course",
        action="store_true",
        help="Only count C, C++, Java, and Python with assignment-style output",
    )
    parser.add_argument(
        "--languages",
        metavar="LANGS",
        help="Comma-separated language list, e.g. C,C++,Java,Python",
    )
    return parser


def _parse_custom_extensions(values: Sequence[str]) -> dict[str, str]:
    mapping: dict[str, str] = {}
    for item in values:
        if ":" not in item:
            raise ValueError(f"Invalid --ext value '{item}', expected format .ext:Language")
        ext, lang = item.split(":", 1)
        ext = ext.strip()
        lang = lang.strip()
        if not ext or not lang:
            raise ValueError(f"Invalid --ext value '{item}'")
        mapping[ext] = lang
    return mapping


def _parse_languages(raw: str | None, *, course: bool) -> set[str] | None:
    if course:
        return set(COURSE_LANGUAGES)
    if not raw:
        return None

    languages: set[str] = set()
    for item in raw.split(","):
        name = item.strip()
        if not name:
            continue
        canonical = LANGUAGE_ALIASES.get(name.lower(), name)
        languages.add(canonical)
    return languages or None


def _format_table(
    rows: list[tuple[str, int, int, int, int]],
    *,
    title: str | None = None,
    include_total: bool = False,
) -> str:
    if include_total:
        headers = ("Language", "Files", "Blank", "Comment", "Code", "Total")
        widths = [len(h) for h in headers]

        def project_row(row: tuple[str, int, int, int, int]) -> tuple[str, int, int, int, int, int]:
            return row[0], row[1], row[2], row[3], row[4], row[2] + row[3] + row[4]
    else:
        headers = ("Language", "Files", "Blank", "Comment", "Code")
        widths = [len(h) for h in headers]

        def project_row(row: tuple[str, int, int, int, int]) -> tuple[str, int, int, int, int]:
            return row

    for row in rows:
        projected = project_row(row)
        for idx, cell in enumerate(projected):
            widths[idx] = max(widths[idx], len(str(cell)))

    def fmt_row(cells: Sequence[str | int]) -> str:
        return "  ".join(str(cells[i]).rjust(widths[i]) for i in range(len(cells)))

    lines: list[str] = []
    if title:
        lines.append(title)
    lines.append(fmt_row(headers))
    lines.append(fmt_row(tuple("-" * w for w in widths)))
    lines.extend(fmt_row(project_row(row)) for row in rows)
    return "\n".join(lines)


def render_course_text(project, allowed_languages: set[str]) -> str:
    rows: list[tuple[str, int, int, int, int]] = []
    total = LanguageStats("SUM")

    for language in COURSE_LANGUAGES:
        if language not in allowed_languages:
            continue
        stats = project.by_language.get(language, LanguageStats(language))
        rows.append((language, stats.files, stats.blank, stats.comment, stats.code))
        total.files += stats.files
        total.blank += stats.blank
        total.comment += stats.comment
        total.code += stats.code

    rows.append(("SUM", total.files, total.blank, total.comment, total.code))
    return _format_table(rows)


def render_text(project, *, by_file: bool = False) -> str:
    lines: list[str] = []

    rows = sorted(
        (
            (
                stats.language,
                stats.files,
                stats.blank,
                stats.comment,
                stats.code,
            )
            for stats in project.by_language.values()
        ),
        key=lambda item: (-(item[2] + item[3] + item[4]), item[0].lower()),
    )

    if rows:
        lines.append(_format_table(rows, title="Language statistics", include_total=True))
    else:
        lines.append("No source files found.")

    total = project.total
    lines.append("")
    lines.append(
        f"SUM: {total.files} files | blank {total.blank} | comment {total.comment} | code {total.code} | total {total.total}"
    )

    if project.skipped_files:
        lines.append(f"Skipped unreadable files: {project.skipped_files}")
    if project.unknown_files:
        lines.append(f"Unknown extension files included: {project.unknown_files}")

    if by_file and project.by_file:
        lines.append("")
        file_rows = sorted(
            (
                (
                    stats.language,
                    1,
                    stats.blank,
                    stats.comment,
                    stats.code,
                )
                for stats in project.by_file
            ),
            key=lambda item: item[0].lower(),
        )
        lines.append(_format_table(file_rows, title="Per-file statistics", include_total=True))
        lines.append("")
        for stats in sorted(project.by_file, key=lambda s: s.path.lower()):
            lines.append(f"{stats.path}: {stats.code} code / {stats.total} total")

    return "\n".join(lines)


def render_json(project, *, by_file: bool = False) -> str:
    payload = {
        "summary": {
            "files": project.total.files,
            "blank": project.total.blank,
            "comment": project.total.comment,
            "code": project.total.code,
            "total": project.total.total,
            "skipped_files": project.skipped_files,
            "unknown_files": project.unknown_files,
        },
        "languages": {
            name: {
                "files": stats.files,
                "blank": stats.blank,
                "comment": stats.comment,
                "code": stats.code,
                "total": stats.total,
            }
            for name, stats in sorted(project.by_language.items(), key=lambda item: item[0].lower())
        },
    }
    if by_file:
        payload["files"] = [
            {
                "path": stats.path,
                "language": stats.language,
                "blank": stats.blank,
                "comment": stats.comment,
                "code": stats.code,
                "total": stats.total,
            }
            for stats in sorted(project.by_file, key=lambda s: s.path.lower())
        ]
    return json.dumps(payload, indent=2, ensure_ascii=False)


def main(argv: Sequence[str] | None = None) -> int:
    parser = build_parser()
    args = parser.parse_args(list(argv) if argv is not None else None)

    try:
        custom_extensions = _parse_custom_extensions(args.ext)
    except ValueError as exc:
        parser.error(str(exc))
        return 2

    allowed_languages = _parse_languages(args.languages, course=args.course)

    project = scan_paths(
        args.paths,
        include_unknown=args.include_unknown,
        allowed_languages=allowed_languages,
        exclude_names=set(args.exclude),
        exclude_patterns=args.exclude_pattern,
        custom_extensions=custom_extensions,
    )

    if args.json:
        print(render_json(project, by_file=args.by_file))
    elif args.course or args.languages:
        if allowed_languages is None:
            allowed_languages = set(project.by_language)
        print(render_course_text(project, allowed_languages))
    else:
        print(render_text(project, by_file=args.by_file))

    return 0


if __name__ == "__main__":
    sys.exit(main())
