# Frontend

Site do Roteiro Java. Next.js 15, React 19 e TypeScript, com Tailwind CSS 4. Mostra a home, os módulos e as aulas, e fala com a API em `course-api`.

A sessão fica no `localStorage`, na chave `roteiro.sessao`. Cada chamada autenticada envia `Authorization: Bearer …`. Um identificador estável do navegador vai no cabeçalho `X-Course-Client`, usado pela API no limite de tentativas de login e cadastro.

## Páginas

| Endereço | O que faz |
| --- | --- |
| `/entrar` | Login. Quem já tem sessão volta para a home. |
| `/cadastro` | Cria a conta e entra. |
| `/` | Painel: progresso, módulos e o link da próxima aula. |
| `/modulos/[id]` | Aulas do módulo, com as que estão abertas e as concluídas. |
| `/aulas/[id]` | Player, resumo e o botão de concluir. |

`/`, `/modulos/[id]` e `/aulas/[id]` exigem sessão. Sem ela, o layout manda para `/entrar`.

No topo, **Livre** e **Trilha** chamam `PUT /api/me/mode` e recarregam a página. **Sair** apaga a sessão local.

## Player

O endereço vem de `videoUrl`:

- link do YouTube vira um iframe em `youtube-nocookie.com`;
- qualquer outro `https` vira a tag `<video>`;
- endereço vazio, inválido ou que não seja HTTPS mostra o tema reservado, sem player.

## Subir sozinho

A API precisa estar no ar. O padrão é `http://localhost:8080`.

```bash
npm install
npm run dev
```

O site abre em http://localhost:3000.

Para apontar para outra API, defina `NEXT_PUBLIC_API_URL` antes do `dev` ou do `build`. O valor entra no bundle na hora da compilação.

```bash
npm test
npm run build
npm start
```

`npm test` cobre `src/lib/video.ts` e `src/lib/api.ts`.

Pela raiz do repositório, `docker compose up --build` sobe este serviço na porta 3000. A imagem de produção usa `output: "standalone"` e grava `NEXT_PUBLIC_API_URL=http://localhost:8080`, porque o navegador chama a API na máquina de quem acessa o site.
