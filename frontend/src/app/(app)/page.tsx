"use client";

import Link from "next/link";
import { useEffect, useState } from "react";
import { api, type Mode } from "@/lib/api";

type ModuleCard = {
  id: number;
  position: number;
  stage: string;
  title: string;
  summary: string;
  lessonCount: number;
  completedLessonCount: number;
};

type Dashboard = {
  name: string;
  mode: Mode;
  lessonCount: number;
  completedLessonCount: number;
  nextLessonId: number | null;
  modules: ModuleCard[];
};

export default function HomePage() {
  const [painel, setPainel] = useState<Dashboard | null>(null);
  const [erro, setErro] = useState<string | null>(null);

  useEffect(() => {
    let ativo = true;
    api<Dashboard>("/api/dashboard")
      .then((dados) => {
        if (ativo) {
          setPainel(dados);
        }
      })
      .catch((falha: unknown) => {
        if (ativo) {
          setErro(falha instanceof Error ? falha.message : "Não foi possível abrir o painel");
        }
      });
    return () => {
      ativo = false;
    };
  }, []);

  if (erro) {
    return <p className="erro">{erro}</p>;
  }
  if (!painel) {
    return <p style={{ padding: "2rem 0" }}>Carregando os módulos…</p>;
  }

  const progresso = painel.lessonCount === 0 ? 0 : Math.round((painel.completedLessonCount / painel.lessonCount) * 100);

  const primeiroNome = painel.name.split(" ")[0];

  return (
    <main>
      <section className="hero">
        <div>
          <p className="etapa">{painel.mode === "TRAIL" ? "Trilha sequencial" : "Navegação livre"}</p>
          <h1>Olá, {primeiroNome}.</h1>
          <p>
            Nove módulos, do primeiro tipo Java até Spring Boot. Abra qualquer aula ou siga a trilha, uma de cada vez.
          </p>
          {painel.nextLessonId && (
            <div className="acoes">
              <Link className="botao" href={`/aulas/${painel.nextLessonId}`}>
                Continuar aula
              </Link>
            </div>
          )}
        </div>
        <div className="hero-painel">
          <div className="metrica">
            <strong>{painel.completedLessonCount}</strong>
            <span>aulas concluídas</span>
          </div>
          <div className="metrica">
            <strong>{painel.lessonCount}</strong>
            <span>aulas no curso</span>
          </div>
          <div className="metrica">
            <strong>{progresso}%</strong>
            <span>da trilha percorrida</span>
          </div>
        </div>
      </section>
      <h2 className="secao">Módulos</h2>
      <section className="grade">
        {painel.modules.map((modulo) => {
          const percentual = modulo.lessonCount === 0 ? 0 : (modulo.completedLessonCount / modulo.lessonCount) * 100;
          return (
            <Link key={modulo.id} href={`/modulos/${modulo.id}`} className="cartao">
              <p className="indice">{String(modulo.position).padStart(2, "0")}</p>
              <p className="etapa">{modulo.stage}</p>
              <h2>{modulo.title}</h2>
              <p>{modulo.summary}</p>
              <div className="barra">
                <span style={{ width: `${percentual}%` }} />
              </div>
              <p>
                {modulo.completedLessonCount} de {modulo.lessonCount} aulas
              </p>
            </Link>
          );
        })}
      </section>
    </main>
  );
}
