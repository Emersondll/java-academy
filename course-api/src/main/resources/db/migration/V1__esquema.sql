CREATE TABLE usuario (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(80) NOT NULL,
    usuario VARCHAR(40) NOT NULL UNIQUE,
    senha VARCHAR(100) NOT NULL,
    modo VARCHAR(10) NOT NULL,
    criado_em TIMESTAMPTZ NOT NULL
);

CREATE TABLE modulo (
    id BIGSERIAL PRIMARY KEY,
    ordem INT NOT NULL UNIQUE,
    etapa VARCHAR(40) NOT NULL,
    titulo VARCHAR(120) NOT NULL,
    resumo VARCHAR(400) NOT NULL
);

CREATE TABLE aula (
    id BIGSERIAL PRIMARY KEY,
    modulo_id BIGINT NOT NULL REFERENCES modulo (id),
    ordem INT NOT NULL,
    ordem_global INT NOT NULL UNIQUE,
    titulo VARCHAR(240) NOT NULL,
    resumo TEXT NOT NULL,
    video_url VARCHAR(500),
    UNIQUE (modulo_id, ordem)
);

CREATE TABLE progresso (
    usuario_id BIGINT NOT NULL REFERENCES usuario (id),
    aula_id BIGINT NOT NULL REFERENCES aula (id),
    concluida_em TIMESTAMPTZ NOT NULL,
    PRIMARY KEY (usuario_id, aula_id)
);
