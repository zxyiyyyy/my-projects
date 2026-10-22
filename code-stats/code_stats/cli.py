from __future__ import annotations

import argparse
import csv
import json
import sys
from pathlib import Path
from typing import Sequence

from . import __version__
from .counter import LanguageStats
from .scanner import scan_paths

COURSE_LANGUAGES = ("C", "C++", "C#", "Java", "Python")

LANGUAGE_ALIASES = {
    "c": "C",
    "c++": "C++",
    "cpp": "C++",
    "c plus plus": "C++",
    "c#": "C#",
    "csharp": "C#",
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
        help="Output results as JSON to stdout",
    )
    parser.add_argument(
        "--csv",
        nargs="?",
        const="code_stats.csv",
        metavar="FILE",
        help="Write results to a CSV file (default: code_stats.csv)",
    )
    parser.add_argument(
        "--excel",
        nargs="?",
        const="code_stats.xlsx",
        metavar="FILE",
        help="Write results to an Excel .xlsx file (default: code_stats.xlsx)",
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


def _file_code_stats(project, language_name: str) -> list[int]:
    """Return list of code line counts per file for a given language."""
    return [f.code for f in project.by_file if f.language == language_name]


def _stats_summary(values: list[int]) -> tuple[int, int, float, float]:
    """Return (max, min, mean, median) for a list of integers."""
    if not values:
        return 0, 0, 0.0, 0.0
    sorted_v = sorted(values)
    n = len(sorted_v)
    vmax = sorted_v[-1]
    vmin = sorted_v[0]
    vmean = sum(sorted_v) / n
    if n % 2 == 1:
        vmedian = float(sorted_v[n // 2])
    else:
        vmedian = (sorted_v[n // 2 - 1] + sorted_v[n // 2]) / 2.0
    return vmax, vmin, round(vmean, 1), round(vmedian, 1)


_CSV_HEADERS = ["语言", "源文件数", "代码行数", "空行数", "注释行数", "函数个数", "最大值", "最小值", "均值", "中位数"]


def _build_rows(project) -> list[list]:
    """Build output rows for the five required languages, zero-filled if missing."""
    rows = []
    for name in COURSE_LANGUAGES:
        stats = project.by_language.get(name)
        file_codes = _file_code_stats(project, name)
        vmax, vmin, vmean, vmedian = _stats_summary(file_codes)
        if stats is None:
            rows.append([name, 0, 0, 0, 0, 0, 0, 0, 0.0, 0.0])
        else:
            rows.append([
                name,
                stats.files,
                stats.code,
                stats.blank,
                stats.comment,
                stats.functions,
                vmax,
                vmin,
                vmean,
                vmedian,
            ])
    return rows


def write_csv(project, filepath: str, *, by_file: bool = False) -> None:
    """Write language statistics to a CSV file."""
    path = Path(filepath)
    rows = _build_rows(project)
    with open(path, "w", newline="", encoding="utf-8-sig") as f:
        writer = csv.writer(f)
        writer.writerow(_CSV_HEADERS)
        for row in rows:
            writer.writerow(row)
    print(f"CSV written to {path.resolve()}")


def write_excel(project, filepath: str, *, by_file: bool = False) -> None:
    """Write language statistics to an Excel .xlsx file (requires openpyxl)."""
    try:
        from openpyxl import Workbook
        from openpyxl.styles import Font, Alignment
    except ImportError:
        print("Error: openpyxl is required for Excel output. Install it with: pip install openpyxl", file=sys.stderr)
        sys.exit(1)

    path = Path(filepath)
    wb = Workbook()
    ws = wb.active
    ws.title = "Code Stats"

    bold = Font(bold=True)
    ws.append(_CSV_HEADERS)
    for cell in ws[1]:
        cell.font = bold
        cell.alignment = Alignment(horizontal="center")

    rows = _build_rows(project)
    for row in rows:
        ws.append(row)

    for col in ws.columns:
        max_len = max(len(str(cell.value or "")) for cell in col)
        ws.column_dimensions[col[0].column_letter].width = min(max_len + 2, 20)

    wb.save(path)
    print(f"Excel written to {path.resolve()}")


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

    if args.csv:
        write_csv(project, args.csv, by_file=args.by_file)
    if args.excel:
        write_excel(project, args.excel, by_file=args.by_file)
    if args.json:
        print(render_json(project, by_file=args.by_file))
    elif args.course or args.languages:
        if allowed_languages is None:
            allowed_languages = set(project.by_language)
        print(render_course_text(project, allowed_languages))
    elif not args.csv and not args.excel:
        print(render_text(project, by_file=args.by_file))

    return 0


if __name__ == "__main__":
    sys.exit(main())
