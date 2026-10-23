"use client";

import { createContext, useContext, useEffect, useState } from "react";
import { writeSession, readSession, clearSession, type Mode, type Session } from "@/lib/api";

type Contexto = {
  sessao: Session | null;
  pronto: boolean;
  entrar: (sessao: Session) => void;
  sair: () => void;
  atualizarModo: (modo: Mode) => void;
};

const Ctx = createContext<Contexto | null>(null);

export function ProvedorSessao({ children }: { children: React.ReactNode }) {
  const [sessao, setSessao] = useState<Session | null>(null);
  const [pronto, setPronto] = useState(false);

  useEffect(() => {
    setSessao(readSession());
    setPronto(true);
  }, []);

  function entrar(nova: Session) {
    writeSession(nova);
    setSessao(nova);
  }

  function sair() {
    clearSession();
    setSessao(null);
  }

  function atualizarModo(modo: Mode) {
    setSessao((atual) => {
      if (!atual) {
        return atual;
      }
      const nova = { ...atual, mode: modo };
      writeSession(nova);
      return nova;
    });
  }

  return <Ctx.Provider value={{ sessao, pronto, entrar, sair, atualizarModo }}>{children}</Ctx.Provider>;
}

export function useSessao() {
  const contexto = useContext(Ctx);
  if (!contexto) {
    throw new Error("Sessão indisponível");
  }
  return contexto;
}
