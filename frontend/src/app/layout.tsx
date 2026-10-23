import type { Metadata } from "next";
import { Plus_Jakarta_Sans } from "next/font/google";
import { ProvedorSessao } from "@/lib/sessao";
import "./globals.css";

const sans = Plus_Jakarta_Sans({ subsets: ["latin"], variable: "--fonte-sans" });

export const metadata: Metadata = {
  title: "Roteiro Java",
  description: "Cursos de Java, do básico ao Spring Boot.",
  icons: { icon: "/marca.svg" },
};

export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="pt-BR" className={sans.variable}>
      <body>
        <ProvedorSessao>{children}</ProvedorSessao>
      </body>
    </html>
  );
}
