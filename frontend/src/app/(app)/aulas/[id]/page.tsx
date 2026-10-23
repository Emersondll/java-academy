"use client";

import Link from "next/link";
import { useParams } from "next/navigation";
import { useEffect, useState } from "react";
import { Player } from "@/components/player";
import { ApiError, api } from "@/lib/api";

type Lesson = {
  id: number;
  title: string;
  summary: string;
  videoUrl: string | null;
  moduleId: number;
  moduleTitle: string;
  position: number;
  globalOrder: number;
  lessonCount: number;
  completed: boolean;
  previousLessonId: number | null;
  nextLessonId: number | null;
};

export default function AulaPage() {
  const params = useParams<{ id: string }>();
  const [aula, setAula] = useState<Lesson | null>(null);
  const [bloqueio, setBloqueio] = useState<ApiError | null>(null);
  const [erro, setErro] = useState<string | null>(null);
  const [enviando, setEnviando] = useState(false);

  useEffect(() => {
    let ativo = true;
    setAula(null);
    setBloqueio(null);
    setErro(null);
    api<Lesson>(`/api/lessons/${params.id}`)
      .then((dados) => {
        if (ativo) {
          setAula(dados);
        }
      })
      .catch((falha: unknown) => {
        if (!ativo) {
          return;
        }
        if (falha instanceof ApiError && falha.status === 403) {
          setBloqueio(falha);
          return;
        }
        setErro(falha instanceof Error ? falha.message : "Não foi possível abrir a aula");
      });
    return () => {
      ativo = false;
    };
  }, [params.id]);

  async function concluir() {
    setEnviando(true);
    try {
      const atualizada = await api<Lesson>(`/api/lessons/${params.id}/completion`, { method: "POST" });
      setAula(atualizada);
    } catch (falha) {
      setErro(falha instanceof Error ? falha.message : "Não foi possível concluir");
    } finally {
      setEnviando(false);
    }
  }

  if (bloqueio) {
    return (
      <main className="leitura">
        <p className="etapa">Trilha</p>
        <h1>Esta aula ainda está fechada.</h1>
        <p>{bloqueio.message}</p>
        <div className="acoes">
          {bloqueio.openLessonId && (
            <Link className="botao" href={`/aulas/${bloqueio.openLessonId}`}>
              Ir para a aula liberada
            </Link>
          )}
          <Link className="botao-linha" href="/">
            Voltar ao início
          </Link>
        </div>
      </main>
    );
  }

  if (erro && !aula) {
    return <p className="erro">{erro}</p>;
  }
  if (!aula) {
    return <p style={{ padding: "2rem 0" }}>Carregando a aula…</p>;
  }

  return (
    <main>
      <p className="voltar">
        <Link href={`/modulos/${aula.moduleId}`}>{aula.moduleTitle}</Link>
        <span className="mudo">
          {" "}
          · aula {aula.globalOrder} de {aula.lessonCount}
        </span>
      </p>
      <div style={{ marginTop: "1rem" }}>
        <Player url={aula.videoUrl} titulo={aula.title} />
      </div>
      <article className="leitura">
        <p className="etapa">Tema {String(aula.position).padStart(2, "0")}</p>
        <h1>{aula.title}</h1>
        <p className="resumo">{aula.summary}</p>
        {erro && <p className="erro">{erro}</p>}
        <div className="acoes">
          {aula.previousLessonId && (
            <Link className="botao-linha" href={`/aulas/${aula.previousLessonId}`}>
              Anterior
            </Link>
          )}
          <button className="botao" type="button" onClick={concluir} disabled={enviando || aula.completed}>
            {aula.completed ? "Concluída" : enviando ? "Salvando…" : "Marcar como concluída"}
          </button>
          {aula.nextLessonId && (
            <Link className="botao-linha" href={`/aulas/${aula.nextLessonId}`}>
              Próxima
            </Link>
          )}
        </div>
      </article>
    </main>
  );
}
