"use client";

import Link from "next/link";
import { useRouter } from "next/navigation";
import { useEffect, useState } from "react";
import { ApiError, api, type Session } from "@/lib/api";
import { useSessao } from "@/lib/sessao";

export function Acesso({ modo }: { modo: "entrar" | "cadastro" }) {
  const cadastro = modo === "cadastro";
  const router = useRouter();
  const { sessao, pronto, entrar } = useSessao();
  const [nome, setNome] = useState("");
  const [usuario, setUsuario] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState<string | null>(null);
  const [enviando, setEnviando] = useState(false);

  useEffect(() => {
    if (pronto && sessao) {
      router.replace("/");
    }
  }, [pronto, sessao, router]);

  async function enviar(evento: React.FormEvent) {
    evento.preventDefault();
    setErro(null);
    setEnviando(true);
    try {
      const caminho = cadastro ? "/api/auth/register" : "/api/auth/login";
      const corpo = cadastro ? { name: nome, username: usuario, password: senha } : { username: usuario, password: senha };
      const resposta = await api<Session>(caminho, { method: "POST", body: JSON.stringify(corpo) });
      entrar(resposta);
      router.push("/");
    } catch (falha) {
      setErro(falha instanceof ApiError ? falha.message : "Não foi possível entrar");
    } finally {
      setEnviando(false);
    }
  }

  return (
    <main className="painel-auth">
      <aside>
        <img src="/marca.svg" alt="" width={42} height={42} />
        <div>
          <h1>Java em módulos, no seu ritmo.</h1>
          <p>Nove módulos. Cada tema tem o seu vídeo. Entre para assistir livremente ou seguir a trilha.</p>
        </div>
      </aside>
      <form onSubmit={enviar}>
        <p className="etapa">{cadastro ? "Criar acesso" : "Entrar"}</p>
        <h2 className="titulo-form">
          {cadastro ? "Cadastre seu usuário" : "Bem-vindo de volta"}
        </h2>
        {cadastro && (
          <label>
            Nome
            <input value={nome} onChange={(evento) => setNome(evento.target.value)} autoComplete="name" required />
          </label>
        )}
        <label>
          Usuário
          <input value={usuario} onChange={(evento) => setUsuario(evento.target.value)} autoComplete="username" required />
        </label>
        <label>
          Senha
          <input
            type="password"
            value={senha}
            onChange={(evento) => setSenha(evento.target.value)}
            autoComplete={cadastro ? "new-password" : "current-password"}
            minLength={8}
            required
          />
        </label>
        {erro && <p className="erro">{erro}</p>}
        <button className="botao" type="submit" disabled={enviando}>
          {enviando ? "Enviando…" : cadastro ? "Criar conta" : "Entrar"}
        </button>
        <p className="mudo">
          {cadastro ? (
            <Link href="/entrar">Já tenho usuário</Link>
          ) : (
            <Link href="/cadastro">Criar um usuário</Link>
          )}
        </p>
      </form>
    </main>
  );
}
