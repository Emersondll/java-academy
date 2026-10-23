"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { api, type Mode } from "@/lib/api";
import { useSessao } from "@/lib/sessao";

export function Casca({ children }: { children: React.ReactNode }) {
  const { sessao, sair, atualizarModo } = useSessao();
  const router = useRouter();

  async function escolher(modo: Mode) {
    if (!sessao || sessao.mode === modo) {
      return;
    }
    try {
      await api("/api/me/mode", { method: "PUT", body: JSON.stringify({ mode: modo }) });
      atualizarModo(modo);
      window.location.reload();
    } catch {
      return;
    }
  }

  function encerrar() {
    sair();
    router.push("/entrar");
  }

  return (
    <div className="casca">
      <header className="topo">
        <Link href="/" className="marca">
          <img src="/marca.svg" alt="" width={32} height={32} />
          <div>
            <strong>Roteiro Java</strong>
            <span>cursos</span>
          </div>
        </Link>
        <div className="topo-acoes">
          <div className="modos" role="group" aria-label="Modo de navegação">
            <button className={sessao?.mode === "FREE" ? "ativo" : ""} onClick={() => escolher("FREE")} type="button">
              Livre
            </button>
            <button className={sessao?.mode === "TRAIL" ? "ativo" : ""} onClick={() => escolher("TRAIL")} type="button">
              Trilha
            </button>
          </div>
          <div className="conta">
            <span className="quem">{sessao?.name}</span>
            <button className="sair" type="button" onClick={encerrar}>
              Sair
            </button>
          </div>
        </div>
      </header>
      {children}
    </div>
  );
}
