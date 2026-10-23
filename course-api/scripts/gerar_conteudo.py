# -*- coding: utf-8 -*-
"""Build the historical V2 seed from the study guide.

V2 inserts into the original table names. V4 renames those tables to English.
Do not change the INSERT targets: Flyway already applied V2.
"""

from pathlib import Path

ROOT = Path(__file__).resolve().parents[2]
GUIDE = ROOT / "roteiro-estudos-java.md"
DESTINATION = ROOT / "course-api" / "src" / "main" / "resources" / "db" / "migration" / "V2__conteudo.sql"

MODULES = [
    (1, "Iniciante", "A linguagem", "Tipos, decisões, laços, métodos, arrays e texto. A base de todo o curso."),
    (2, "Iniciante", "Orientação a objetos", "Classes, encapsulamento, herança, interfaces, enum, record e o modelo do pedido."),
    (3, "Intermediário", "Coleções", "List, Set, Queue, Deque e Map, e o critério para escolher cada uma."),
    (4, "Intermediário", "Streams", "Pipelines e a diferença entre stream, for, for-each e Iterator."),
    (5, "Intermediário", "Plataforma Java", "Exceções, Optional, datas, arquivos, JSON e o que o Spring lê em execução."),
    (6, "Intermediário", "Concorrência", "Threads, locks, pools, futures e virtual threads antes da API."),
    (7, "Spring Boot", "Spring Boot iniciante", "Container, injeção, configuração e a primeira API."),
    (8, "Spring Boot", "Spring Boot intermediário", "Validação, JPA, transações, testes e chamadas a outras APIs."),
    (9, "Spring Boot", "Spring Boot avançado", "Segurança, filas, cache, Docker e mais de uma instância."),
]


def quote(text: str) -> str:
    cleaned = text.replace("$$", "$")
    return f"$${cleaned}$$"


def extract_lessons():
    lines = GUIDE.read_text(encoding="utf-8").splitlines()
    lessons = []
    level = 0
    index = 0
    while index < len(lines):
        line = lines[index]
        if line.startswith("NÍVEL"):
            level += 1
            index += 1
            continue
        if level == 0 or line.strip() != "Tema":
            index += 1
            continue
        index += 1
        while index < len(lines) and not lines[index].strip():
            index += 1
        title = lines[index].strip()
        index += 1
        while index < len(lines) and not lines[index].strip():
            index += 1
        if index < len(lines) and lines[index].strip() == "Descrição do conteúdo":
            index += 1
        parts = []
        while index < len(lines):
            current = lines[index]
            if current.strip() == "Tema" or current.startswith("====") or current.startswith("NÍVEL"):
                break
            parts.append(current)
            index += 1
        text = "\n".join(parts).strip()
        while "\n\n\n" in text:
            text = text.replace("\n\n\n", "\n\n")
        if len(text) > 900:
            cut = text.rfind(" ", 0, 900)
            text = text[: cut if cut > 500 else 900].rstrip() + "…"
        lessons.append((level, title, text))
    return lessons


def main():
    lessons = extract_lessons()
    if len(lessons) != 132:
        raise SystemExit(f"expected 132 lessons, found {len(lessons)}")
    blocks = [
        "-- Generated from roteiro-estudos-java.md",
        "INSERT INTO modulo (id, ordem, etapa, titulo, resumo) VALUES",
    ]
    module_rows = []
    for position, stage, title, summary in MODULES:
        module_rows.append(f"({position}, {position}, {quote(stage)}, {quote(title)}, {quote(summary)})")
    blocks.append(",\n".join(module_rows) + ";")
    blocks.append("SELECT setval(pg_get_serial_sequence('modulo', 'id'), (SELECT MAX(id) FROM modulo));")
    blocks.append("INSERT INTO aula (modulo_id, ordem, ordem_global, titulo, resumo, video_url) VALUES")
    counts = {position: 0 for position, *_ in MODULES}
    lesson_rows = []
    for global_order, (level, title, summary) in enumerate(lessons, start=1):
        counts[level] += 1
        lesson_rows.append(
            f"({level}, {counts[level]}, {global_order}, {quote(title)}, {quote(summary)}, NULL)"
        )
    blocks.append(",\n".join(lesson_rows) + ";")
    DESTINATION.write_text("\n".join(blocks) + "\n", encoding="utf-8")
    print(f"lessons={len(lessons)} file={DESTINATION}")


if __name__ == "__main__":
    main()
