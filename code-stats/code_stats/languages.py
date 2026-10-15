from __future__ import annotations

from dataclasses import dataclass
from typing import Iterable


@dataclass(frozen=True)
class CommentRules:
    """Comment syntax for a language group."""

    line_comments: tuple[str, ...] = ()
    block_start: str | None = None
    block_end: str | None = None


@dataclass(frozen=True)
class Language:
    name: str
    extensions: frozenset[str]
    rules: CommentRules | None = None


# Shared comment rule presets
C_STYLE = CommentRules(line_comments=("//",), block_start="/*", block_end="*/")
HASH_STYLE = CommentRules(line_comments=("#",))
SEMICOLON_STYLE = CommentRules(line_comments=(";",))
HTML_STYLE = CommentRules(block_start="<!--", block_end="-->")
SQL_STYLE = CommentRules(line_comments=("--",), block_start="/*", block_end="*/")
LUA_STYLE = CommentRules(line_comments=("--",), block_start="--[[", block_end="]]")
HASKELL_STYLE = CommentRules(line_comments=("--",), block_start="{-", block_end="-}")
MATLAB_STYLE = CommentRules(line_comments=("%",), block_start="%{", block_end="%}")
FORTRAN_STYLE = CommentRules(line_comments=("!", "c", "C", "*"))
VHDL_STYLE = CommentRules(line_comments=("--",))
TCL_STYLE = CommentRules(line_comments=("#",))
REM_STYLE = CommentRules(line_comments=("REM ", "rem ", "::"))
APACHE_STYLE = CommentRules(line_comments=("#",))
PROLOG_STYLE = CommentRules(line_comments=("%",), block_start="/*", block_end="*/")
ERLANG_STYLE = CommentRules(line_comments=("%",))
COBOL_STYLE = CommentRules(line_comments=("*>", "*> ", "*>"))
ADA_STYLE = CommentRules(line_comments=("--",))
RUBY_STYLE = CommentRules(line_comments=("#",), block_start="=begin", block_end="=end")


LANGUAGES: tuple[Language, ...] = (
    Language("Python", frozenset({".py", ".pyw", ".pyi", ".pyx", ".pxd", ".ipynb"}), HASH_STYLE),
    Language("JavaScript", frozenset({".js", ".mjs", ".cjs", ".jsx"}), C_STYLE),
    Language("TypeScript", frozenset({".ts", ".tsx", ".mts", ".cts"}), C_STYLE),
    Language("Java", frozenset({".java"}), C_STYLE),
    Language("Kotlin", frozenset({".kt", ".kts"}), C_STYLE),
    Language("Scala", frozenset({".scala", ".sc"}), C_STYLE),
    Language("C", frozenset({".c", ".h"}), C_STYLE),
    Language("C++", frozenset({".cpp", ".cc", ".cxx", ".c++", ".hpp", ".hh", ".hxx", ".h++", ".inl", ".ipp"}), C_STYLE),
    Language("C#", frozenset({".cs", ".csx"}), C_STYLE),
    Language("Go", frozenset({".go"}), C_STYLE),
    Language("Rust", frozenset({".rs"}), C_STYLE),
    Language("Swift", frozenset({".swift"}), C_STYLE),
    Language("Objective-C", frozenset({".m", ".mm"}), C_STYLE),
    Language("Ruby", frozenset({".rb", ".rake", ".gemspec", ".ru"}), RUBY_STYLE),
    Language("PHP", frozenset({".php", ".php3", ".php4", ".php5", ".phtml"}), C_STYLE),
    Language("Perl", frozenset({".pl", ".pm", ".t", ".pod"}), HASH_STYLE),
    Language("Shell", frozenset({".sh", ".bash", ".zsh", ".ksh", ".fish"}), HASH_STYLE),
    Language("PowerShell", frozenset({".ps1", ".psm1", ".psd1"}), HASH_STYLE),
    Language("Batch", frozenset({".bat", ".cmd"}), REM_STYLE),
    Language("R", frozenset({".r", ".R"}), HASH_STYLE),
    Language("Lua", frozenset({".lua"}), LUA_STYLE),
    Language("Haskell", frozenset({".hs", ".lhs"}), HASKELL_STYLE),
    Language("Elixir", frozenset({".ex", ".exs"}), HASH_STYLE),
    Language("Erlang", frozenset({".erl", ".hrl"}), ERLANG_STYLE),
    Language("Clojure", frozenset({".clj", ".cljs", ".cljc", ".edn"}), SEMICOLON_STYLE),
    Language("Lisp", frozenset({".lisp", ".lsp", ".cl", ".el"}), SEMICOLON_STYLE),
    Language("Scheme", frozenset({".scm", ".ss", ".rkt"}), SEMICOLON_STYLE),
    Language("F#", frozenset({".fs", ".fsi", ".fsx"}), C_STYLE),
    Language("OCaml", frozenset({".ml", ".mli"}), C_STYLE),
    Language("Dart", frozenset({".dart"}), C_STYLE),
    Language("Groovy", frozenset({".groovy", ".gradle", ".gvy"}), C_STYLE),
    Language("Visual Basic", frozenset({".vb", ".vbs"}), C_STYLE),
    Language("Delphi/Pascal", frozenset({".pas", ".dpr", ".dfm", ".inc"}), C_STYLE),
    Language("Fortran", frozenset({".f", ".f90", ".f95", ".f03", ".for"}), FORTRAN_STYLE),
    Language("COBOL", frozenset({".cob", ".cbl", ".cpy"}), COBOL_STYLE),
    Language("Ada", frozenset({".adb", ".ads"}), ADA_STYLE),
    Language("VHDL", frozenset({".vhd", ".vhdl"}), VHDL_STYLE),
    Language("Verilog", frozenset({".v", ".sv", ".svh"}), C_STYLE),
    Language("Assembly", frozenset({".asm", ".s", ".S", ".nasm"}), SEMICOLON_STYLE),
    Language("SQL", frozenset({".sql", ".psql", ".mysql"}), SQL_STYLE),
    Language("PL/SQL", frozenset({".pls", ".pks", ".pkb", ".pck"}), SQL_STYLE),
    Language("HTML", frozenset({".html", ".htm", ".xhtml"}), HTML_STYLE),
    Language("CSS", frozenset({".css"}), C_STYLE),
    Language("SCSS", frozenset({".scss", ".sass"}), C_STYLE),
    Language("Less", frozenset({".less"}), C_STYLE),
    Language("Vue", frozenset({".vue"}), HTML_STYLE),
    Language("Svelte", frozenset({".svelte"}), HTML_STYLE),
    Language("XML", frozenset({".xml", ".xsl", ".xslt", ".svg", ".plist", ".xaml"}), HTML_STYLE),
    Language("JSON", frozenset({".json", ".jsonc", ".json5"}), None),
    Language("YAML", frozenset({".yaml", ".yml"}), HASH_STYLE),
    Language("TOML", frozenset({".toml"}), HASH_STYLE),
    Language("INI", frozenset({".ini", ".cfg", ".conf", ".properties"}), SEMICOLON_STYLE),
    Language("Markdown", frozenset({".md", ".markdown", ".mdown", ".mkd"}), None),
    Language("LaTeX", frozenset({".tex", ".sty", ".cls", ".bib"}), CommentRules(line_comments=("%",))),
    Language("Makefile", frozenset({"Makefile", "makefile", "GNUmakefile", ".mk"}), HASH_STYLE),
    Language("CMake", frozenset({".cmake", "CMakeLists.txt"}), HASH_STYLE),
    Language("Dockerfile", frozenset({"Dockerfile", ".dockerfile"}), HASH_STYLE),
    Language("Terraform", frozenset({".tf", ".tfvars", ".hcl"}), HASH_STYLE),
    Language("Nix", frozenset({".nix"}), HASH_STYLE),
    Language("Zig", frozenset({".zig"}), C_STYLE),
    Language("Nim", frozenset({".nim", ".nims"}), HASH_STYLE),
    Language("Crystal", frozenset({".cr"}), HASH_STYLE),
    Language("Julia", frozenset({".jl"}), HASH_STYLE),
    Language("MATLAB", frozenset({".m", ".matlab"}), MATLAB_STYLE),
    Language("Prolog", frozenset({".pro", ".P"}), PROLOG_STYLE),
    Language("Solidity", frozenset({".sol"}), C_STYLE),
    Language("GraphQL", frozenset({".graphql", ".gql"}), HASH_STYLE),
    Language("Protocol Buffers", frozenset({".proto"}), C_STYLE),
    Language("Thrift", frozenset({".thrift"}), HASH_STYLE),
    Language("OpenAPI", frozenset({".openapi", ".swagger"}), None),
    Language("Racket", frozenset({".rkt"}), SEMICOLON_STYLE),
    Language("D", frozenset({".d", ".di"}), C_STYLE),
    Language("V", frozenset({".v"}), C_STYLE),
    Language("Gleam", frozenset({".gleam"}), C_STYLE),
    Language("Reason", frozenset({".re", ".rei"}), C_STYLE),
    Language("PureScript", frozenset({".purs"}), C_STYLE),
    Language("Elm", frozenset({".elm"}), C_STYLE),
    Language("Tcl", frozenset({".tcl", ".tk"}), TCL_STYLE),
    Language("Awk", frozenset({".awk"}), HASH_STYLE),
    Language("Sed", frozenset({".sed"}), HASH_STYLE),
    Language("QML", frozenset({".qml"}), C_STYLE),
    Language("Arduino", frozenset({".ino"}), C_STYLE),
    Language("WebAssembly", frozenset({".wat", ".wast"}), SEMICOLON_STYLE),
    Language("CUDA", frozenset({".cu", ".cuh"}), C_STYLE),
    Language("OpenCL", frozenset({".cl", ".opencl"}), C_STYLE),
    Language("GLSL", frozenset({".glsl", ".vert", ".frag", ".geom", ".comp"}), C_STYLE),
    Language("HLSL", frozenset({".hlsl", ".fx", ".fxh"}), C_STYLE),
    Language("ABAP", frozenset({".abap"}), C_STYLE),
    Language("ActionScript", frozenset({".as", ".mxml"}), C_STYLE),
    Language("ColdFusion", frozenset({".cfm", ".cfc"}), HTML_STYLE),
    Language("JSP", frozenset({".jsp", ".jspf"}), HTML_STYLE),
    Language("ASP", frozenset({".asp", ".aspx", ".ascx"}), HTML_STYLE),
    Language("Mustache", frozenset({".mustache", ".hbs", ".handlebars"}), HTML_STYLE),
    Language("EJS", frozenset({".ejs"}), HTML_STYLE),
    Language("Jinja", frozenset({".jinja", ".jinja2", ".j2"}), HTML_STYLE),
    Language("Smarty", frozenset({".tpl"}), HTML_STYLE),
    Language("Razor", frozenset({".cshtml", ".razor"}), HTML_STYLE),
    Language("XQuery", frozenset({".xq", ".xquery"}), C_STYLE),
    Language("XSLT", frozenset({".xsl", ".xslt"}), HTML_STYLE),
    Language("Bicep", frozenset({".bicep"}), C_STYLE),
    Language("Puppet", frozenset({".pp"}), HASH_STYLE),
    Language("Chef", frozenset({".rb"}), RUBY_STYLE),
    Language("Salt", frozenset({".sls"}), HASH_STYLE),
    Language("Ansible", frozenset({".yml", ".yaml"}), HASH_STYLE),
    Language("Vim Script", frozenset({".vim", ".vimrc"}), HASH_STYLE),
    Language("Emacs Lisp", frozenset({".el", ".elc"}), SEMICOLON_STYLE),
    Language("AutoHotkey", frozenset({".ahk"}), SEMICOLON_STYLE),
    Language("AutoIt", frozenset({".au3"}), SEMICOLON_STYLE),
    Language("Smalltalk", frozenset({".st"}), C_STYLE),
    Language("Io", frozenset({".io"}), HASH_STYLE),
    Language("Factor", frozenset({".factor"}), C_STYLE),
    Language("Forth", frozenset({".fth", ".4th", ".forth"}), C_STYLE),
    Language("APL", frozenset({".apl", ".dyalog"}), C_STYLE),
    Language("Raku", frozenset({".raku", ".rakumod", ".rakudoc", ".rakutest"}), HASH_STYLE),
    Language("Hack", frozenset({".hh", ".hack"}), C_STYLE),
    Language("Vala", frozenset({".vala", ".vapi"}), C_STYLE),
    Language("Genie", frozenset({".gs"}), C_STYLE),
    Language("Meson", frozenset({".meson"}), HASH_STYLE),
    Language("Bazel", frozenset({".bzl", "BUILD", "WORKSPACE"}), HASH_STYLE),
    Language("Starlark", frozenset({".star"}), HASH_STYLE),
    Language("Sass", frozenset({".sass"}), C_STYLE),
    Language("Stylus", frozenset({".styl"}), C_STYLE),
    Language("PostCSS", frozenset({".pcss"}), C_STYLE),
    Language("Coffeescript", frozenset({".coffee", ".litcoffee"}), HASH_STYLE),
    Language("LiveScript", frozenset({".ls"}), HASH_STYLE),
    Language("Idris", frozenset({".idr", ".lidr"}), C_STYLE),
    Language("Agda", frozenset({".agda"}), C_STYLE),
    Language("Coq", frozenset({".v", ".coq"}), C_STYLE),
    Language("Lean", frozenset({".lean"}), C_STYLE),
    Language("Isabelle", frozenset({".thy"}), C_STYLE),
    Language("SAS", frozenset({".sas"}), C_STYLE),
    Language("Stata", frozenset({".do", ".ado", ".dta"}), C_STYLE),
    Language("SPSS", frozenset({".sps"}), C_STYLE),
    Language("R Markdown", frozenset({".rmd", ".Rmd"}), HASH_STYLE),
    Language("Jupyter Notebook", frozenset({".ipynb"}), None),
    Language("Apache Config", frozenset({".htaccess", ".conf"}), APACHE_STYLE),
    Language("Nginx Config", frozenset({".nginx"}), HASH_STYLE),
    Language("SystemVerilog", frozenset({".sv", ".svh"}), C_STYLE),
    Language("Templ", frozenset({".templ"}), C_STYLE),
    Language("Odin", frozenset({".odin"}), C_STYLE),
    Language("Carbon", frozenset({".carbon"}), C_STYLE),
    Language("Move", frozenset({".move"}), C_STYLE),
    Language("Cairo", frozenset({".cairo"}), C_STYLE),
    Language("Noir", frozenset({".nr"}), C_STYLE),
    Language("Clarity", frozenset({".clar"}), SEMICOLON_STYLE),
    Language("Cadence", frozenset({".cdc"}), C_STYLE),
    Language("Motoko", frozenset({".mo"}), C_STYLE),
    Language("WASM Text", frozenset({".wat"}), SEMICOLON_STYLE),
    Language("RegEx", frozenset({".regex", ".re"}), None),
    Language("Diff", frozenset({".diff", ".patch"}), None),
    Language("Log", frozenset({".log"}), None),
)


def build_extension_map(languages: Iterable[Language] = LANGUAGES) -> dict[str, Language]:
    mapping: dict[str, Language] = {}
    for language in languages:
        for ext in language.extensions:
            key = ext.lower() if ext.startswith(".") else ext
            mapping[key] = language
    return mapping


EXTENSION_MAP = build_extension_map()


def detect_language(path: str) -> Language | None:
    import os

    basename = os.path.basename(path)
    if basename in EXTENSION_MAP:
        return EXTENSION_MAP[basename]

    _, ext = os.path.splitext(path)
    if not ext:
        return None
    return EXTENSION_MAP.get(ext.lower())


def register_extension(ext: str, language_name: str, rules: CommentRules | None = None) -> None:
    """Register a custom extension at runtime."""
    ext_key = ext.lower() if ext.startswith(".") else ext
    EXTENSION_MAP[ext_key] = Language(language_name, frozenset({ext_key}), rules)
