# course-api

API do Roteiro Java. Spring Boot 3.4 e Java 21. Cadastro, login e leitura do catálogo. Todas as contas têm o mesmo acesso; o que muda é o modo de navegação, `FREE` ou `TRAIL`.

O esquema e o conteúdo sobem pelo Flyway, em `src/main/resources/db/migration`. O Hibernate só valida o que já está no banco (`ddl-auto: validate`).

## Endpoints

Públicos:

| Método | Caminho | Resposta |
| --- | --- | --- |
| `POST` | `/api/auth/register` | `201` e o token |
| `POST` | `/api/auth/login` | token |
| `GET` | `/api/health` | `{"status":"ok"}` |

O restante exige `Authorization: Bearer <token>`.

| Método | Caminho | O que devolve |
| --- | --- | --- |
| `GET` | `/api/me` | nome, usuário e modo |
| `PUT` | `/api/me/mode` | o mesmo, depois de gravar `{"mode":"FREE"}` ou `{"mode":"TRAIL"}` |
| `GET` | `/api/dashboard` | progresso, módulos e `nextLessonId` |
| `GET` | `/api/modules/{id}` | módulo e aulas, cada uma com `completed` e `open` |
| `GET` | `/api/lessons/{id}` | aula, vídeo, anterior e próxima |
| `POST` | `/api/lessons/{id}/completion` | marca a aula como concluída e devolve o detalhe |

Cadastro: `name` (2 a 80), `username` (3 a 40: letras, dígitos, `.`, `_` ou `-`) e `password` (8 a 80). O usuário é gravado em minúsculas. A senha fica em BCrypt. Conta nova começa em `FREE`.

Login e cadastro aceitam no máximo 8 tentativas por cliente e 32 por endereço, numa janela de 1 minuto. O cliente é o cabeçalho `X-Course-Client` (`16` a `64` caracteres: letras, dígitos, `_` ou `-`). Acima do limite a resposta é `429`.

Erros voltam `{"message":"..."}`. Aula fechada na trilha responde `403` e inclui `openLessonId`, o id da aula que ainda pode ser aberta.

## Modos

`AccessRule` decide se a aula abre.

- **FREE:** qualquer aula abre.
- **TRAIL:** a aula de ordem global N só abre quando todas as ordens de 1 até N−1 estão concluídas. A ordem 1 abre sempre.

Concluir uma aula fechada também falha. Concluir de novo uma aula já concluída não cria outra linha em `lesson_progress`.

## Configuração

| Variável | Padrão | Uso |
| --- | --- | --- |
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/cursos` | JDBC |
| `SPRING_DATASOURCE_USERNAME` | `cursos` | usuário do banco |
| `SPRING_DATASOURCE_PASSWORD` | `cursos` | senha do banco |
| `JWT_SECRET` | segredo local de desenvolvimento | HMAC do JWT, no mínimo 32 bytes |
| `JWT_EXPIRATION_MINUTES` | `720` | validade do token |
| `APP_CORS_ORIGINS` | `http://localhost:3000,http://127.0.0.1:3000` | origens separadas por vírgula |

A sessão é stateless. CORS libera `GET`, `POST`, `PUT` e `OPTIONS`, com os cabeçalhos `Authorization`, `Content-Type` e `X-Course-Client`.

## Subir sozinho

Java 21, Maven e um PostgreSQL com o banco `cursos`. Pela raiz, o Compose publica esse banco em `localhost:5433`:

```bash
docker compose up postgres -d
```

Na pasta `course-api`, aponte a URL para essa porta:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--spring.datasource.url=jdbc:postgresql://localhost:5433/cursos
```

A API escuta em http://localhost:8080.

Os testes não usam banco. `mvn verify` roda a suíte e o JaCoCo exige cobertura de 100% em linha, ramo e instrução.

Pela raiz, `docker compose up --build` compila o jar (`mvn verify` na imagem) e sobe a API na porta 8080, já ligada ao Postgres do Compose.
