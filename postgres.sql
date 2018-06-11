CREATE TABLE usuario
(
  id bigserial NOT NULL,
  ativo boolean NOT NULL,
  data_registro timestamp without time zone NOT NULL,
  login character varying(64) NOT NULL,
  nome character varying(64) NOT NULL,
  senha character varying(128) NOT NULL,
  tipo character varying(16) NOT NULL,
  CONSTRAINT usuario_pkey PRIMARY KEY (id),
  CONSTRAINT uk_g1orfqvgih1w8s3vyg15fq2b8 UNIQUE (login)
)
WITH (
  OIDS=FALSE
);
ALTER TABLE usuario
  OWNER TO postgres;


CREATE TABLE chamado
(
  id bigserial NOT NULL,
  assunto character varying(64) NOT NULL,
  data_registro timestamp without time zone NOT NULL,
  mensagem character varying(2048) NOT NULL,
  status character varying(8) NOT NULL,
  tipo character varying(16) NOT NULL,
  usuario_id bigint NOT NULL,
  usuario_status bigint NOT NULL,
  CONSTRAINT chamado_pkey PRIMARY KEY (id),
  CONSTRAINT fk8ky228shgjl73pg1j5m2m3u3x FOREIGN KEY (usuario_id)
      REFERENCES usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk9gx3ey8aj49ms75knhof5kb79 FOREIGN KEY (usuario_status)
      REFERENCES usuario (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE chamado
  OWNER TO postgres;