"use client";

import { useRouter } from "next/navigation";
import { useEffect } from "react";
import { Casca } from "@/components/casca";
import { useSessao } from "@/lib/sessao";

export default function LayoutAutenticado({ children }: { children: React.ReactNode }) {
  const { sessao, pronto } = useSessao();
  const router = useRouter();

  useEffect(() => {
    if (pronto && !sessao) {
      router.replace("/entrar");
    }
  }, [pronto, sessao, router]);

  if (!pronto || !sessao) {
    return <p className="casca" style={{ paddingTop: "3rem" }}>Abrindo a sua sessão…</p>;
  }

  return <Casca>{children}</Casca>;
}
