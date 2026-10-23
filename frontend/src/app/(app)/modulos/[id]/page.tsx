"use client";

import Link from "next/link";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { api } from "@/lib/api";

type LessonItem = {
  id: number;
  position: number;
  title: string;
  completed: boolean;
  open: boolean;
};

type ModuleDetail = {
  id: number;
  position: number;
  stage: string;
  title: string;
  summary: string;
  lessons: LessonItem[];
};

export default function ModuloPage() {
  const params = useParams<{ id: string }>();
  const [modulo, setModulo] = useState<ModuleDetail | null>(null);
  const [erro, setErro] = useState<string | null>(null);

  useEffect(() => {
    let ativo = true;
    setModulo(null);
    setErro(null);
    api<ModuleDetail>(`/api/modules/${params.id}`)
      .then((dados) => {
        if (ativo) {
          setModulo(dados);
        }
      })
      .catch((falha: unknown) => {
        if (ativo) {
          setErro(falha instanceof Error ? falha.message : "Não foi possível abrir o módulo");
        }
      });
    return () => {
      ativo = false;
    };
  }, [params.id]);

  if (erro) {
    return <p className="erro">{erro}</p>;
  }
  if (!modulo) {
    return <p style={{ padding: "2rem 0" }}>Carregando o módulo…</p>;
  }

  return (
    <main>
      <p className="voltar">
        <Link href="/">Início</Link>
      </p>
      <section className="hero">
        <div>
          <p className="etapa">
            Módulo {String(modulo.position).padStart(2, "0")} · {modulo.stage}
          </p>
          <h1>{modulo.title}</h1>
          <p>{modulo.summary}</p>
        </div>
      </section>
      <ol className="lista">
        {modulo.lessons.map((aula) =>
          aula.open ? (
            <li key={aula.id}>
              <Link className="aula-item" href={`/aulas/${aula.id}`}>
                <span className="indice">{String(aula.position).padStart(2, "0")}</span>
                <span className="titulo-aula">{aula.title}</span>
                <span className="mudo">{aula.completed ? "Concluída" : "Assistir"}</span>
              </Link>
            </li>
          ) : (
            <li key={aula.id} className="aula-item-travada">
              <span className="indice indice-mudo">{String(aula.position).padStart(2, "0")}</span>
              <span>{aula.title}</span>
              <span>Na trilha, depois da anterior</span>
            </li>
          ),
        )}
      </ol>
    </main>
  );
}
