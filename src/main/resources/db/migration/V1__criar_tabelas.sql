CREATE TABLE municipio (
                           id_municipio INT AUTO_INCREMENT PRIMARY KEY,
                           nome VARCHAR(120) NOT NULL,
                           uf CHAR(2) NOT NULL,
                           codigo_ibge CHAR(7) UNIQUE
);

CREATE TABLE destino (
                         id_destino INT AUTO_INCREMENT PRIMARY KEY,
                         id_municipio INT NOT NULL,
                         nome VARCHAR(120) NOT NULL,
                         tipo VARCHAR(40),
                         CONSTRAINT fk_destino_municipio
                             FOREIGN KEY (id_municipio) REFERENCES municipio (id_municipio)
);

CREATE TABLE atrativo (
                          id_atrativo INT AUTO_INCREMENT PRIMARY KEY,
                          id_destino INT NOT NULL,
                          nome VARCHAR(150)  NOT NULL,
                          tipo VARCHAR(40),
                          latitude DECIMAL(9,6),
                          longitude DECIMAL(9,6),
                          exige_guia BOOLEAN NOT NULL DEFAULT FALSE,
                          valor_ingresso DECIMAL(10,2) NOT NULL DEFAULT 0,
                          CONSTRAINT fk_atrativo_destino
                              FOREIGN KEY (id_destino) REFERENCES destino (id_destino),
                          CONSTRAINT ck_atrativo_valor CHECK (valor_ingresso >= 0)
);

CREATE TABLE capacidade_carga (
                                  id_capacidade INT AUTO_INCREMENT PRIMARY KEY,
                                  id_atrativo INT  NOT NULL,
                                  vigencia_inicio DATE NOT NULL,
                                  vigencia_fim DATE NOT NULL,
                                  cota_diaria INT NOT NULL,
                                  cota_por_janela INT NOT NULL,
                                  CONSTRAINT fk_capacidade_atrativo
                                      FOREIGN KEY (id_atrativo) REFERENCES atrativo (id_atrativo),
                                  CONSTRAINT ck_capacidade_datas CHECK (vigencia_fim >= vigencia_inicio),

                                  CONSTRAINT ck_capacidade_cotas CHECK (cota_por_janela > 0 AND cota_por_janela <= cota_diaria)
);

CREATE TABLE janela_visitacao (
                                  id_janela INT AUTO_INCREMENT PRIMARY KEY,
                                  id_atrativo INT NOT NULL,
                                  data_visita DATE NOT NULL,
                                  hora_inicio TIME NOT NULL,
                                  vagas_total INT NOT NULL,
                                  vagas_ocupadas INT NOT NULL DEFAULT 0,
                                  CONSTRAINT fk_janela_atrativo
                                      FOREIGN KEY (id_atrativo) REFERENCES atrativo (id_atrativo),

                                  CONSTRAINT uk_janela_horario UNIQUE (id_atrativo, data_visita, hora_inicio),

                                  CONSTRAINT ck_janela_vagas CHECK (vagas_ocupadas BETWEEN 0 AND vagas_total)
);


CREATE TABLE viajante (
                          id_viajante INT AUTO_INCREMENT PRIMARY KEY,
                          nome VARCHAR(120) NOT NULL,
                          email VARCHAR(150) NOT NULL UNIQUE,
                          cpf CHAR(11)
);

CREATE TABLE reserva (
                         id_reserva INT AUTO_INCREMENT PRIMARY KEY,
                         id_viajante INT NOT NULL,
                         datahora DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                         status VARCHAR(20) NOT NULL DEFAULT 'confirmada',
                         CONSTRAINT fk_reserva_viajante
                             FOREIGN KEY (id_viajante) REFERENCES viajante (id_viajante),
                         CONSTRAINT ck_reserva_status CHECK (status IN ('confirmada', 'cancelada'))
);

CREATE TABLE item_reserva (
                              id_item INT AUTO_INCREMENT PRIMARY KEY,
                              id_reserva INT NOT NULL,
                              id_janela INT NOT NULL,
                              qtd_pessoas INT NOT NULL,
                              valor DECIMAL(10,2) NOT NULL,
                              CONSTRAINT fk_item_reserva
                                  FOREIGN KEY (id_reserva) REFERENCES reserva (id_reserva),
                              CONSTRAINT fk_item_janela
                                  FOREIGN KEY (id_janela) REFERENCES janela_visitacao (id_janela),
                              CONSTRAINT uk_item_janela UNIQUE (id_reserva, id_janela),
                              CONSTRAINT ck_item_pessoas CHECK (qtd_pessoas > 0),
                              CONSTRAINT ck_item_valor CHECK (valor >= 0)
);


CREATE TABLE fornecedor (
                            id_fornecedor INT AUTO_INCREMENT PRIMARY KEY,
                            id_municipio INT NOT NULL,
                            nome_fantasia VARCHAR(150) NOT NULL,
                            tipo VARCHAR(40),
                            origem_cadastro VARCHAR(20) NOT NULL,
                            perfil_reivindicado BOOLEAN NOT NULL DEFAULT FALSE,
                            data_reivindicacao DATETIME,
                            cnpj CHAR(14) UNIQUE,
                            razao_social VARCHAR(150),
                            CONSTRAINT fk_fornecedor_municipio
                                FOREIGN KEY (id_municipio) REFERENCES municipio (id_municipio),
                            CONSTRAINT ck_fornecedor_origem CHECK (origem_cadastro IN ('cadastur', 'viajante', 'proprio')),
                            CONSTRAINT ck_fornecedor_reivindicado CHECK (
                                perfil_reivindicado = FALSE OR (cnpj IS NOT NULL AND data_reivindicacao IS NOT NULL))
);

CREATE TABLE fornecedor_certificacao (
                                         id_forn_cert INT AUTO_INCREMENT PRIMARY KEY,
                                         id_fornecedor INT NOT NULL,
                                         numero VARCHAR(40) NOT NULL,
                                         data_emissao DATE NOT NULL,
                                         data_validade DATE NOT NULL,
                                         url_comprovante VARCHAR(255),
                                         status VARCHAR(20)  NOT NULL DEFAULT 'em_analise',
                                         CONSTRAINT fk_fc_fornecedor
                                             FOREIGN KEY (id_fornecedor) REFERENCES fornecedor (id_fornecedor),
                                         CONSTRAINT uk_fc_numero UNIQUE (numero),
                                         CONSTRAINT ck_fc_status CHECK (status IN ('em_analise', 'verificada', 'recusada')),
                                         CONSTRAINT ck_fc_datas CHECK (data_validade > data_emissao)
);

CREATE TABLE criterio_sustentabilidade (
                                           id_criterio INT AUTO_INCREMENT PRIMARY KEY,
                                           eixo VARCHAR(20) NOT NULL,
                                           descricao VARCHAR(255) NOT NULL,
                                           peso INT NOT NULL,
                                           obrigatorio BOOLEAN NOT NULL DEFAULT FALSE,
                                           CONSTRAINT ck_criterio_eixo CHECK (eixo IN ('ambiental', 'social', 'economico', 'gestao')),
                                           CONSTRAINT ck_criterio_peso CHECK (peso BETWEEN 1 AND 3)
);

CREATE TABLE item_auditoria (
                                id_item_auditoria INT AUTO_INCREMENT PRIMARY KEY,
                                id_forn_cert INT NOT NULL,
                                id_criterio INT NOT NULL,
                                atendido BOOLEAN NOT NULL,
                                evidencia_url VARCHAR(255),
                                observacao VARCHAR(500),
                                CONSTRAINT fk_ia_certificacao
                                    FOREIGN KEY (id_forn_cert) REFERENCES fornecedor_certificacao (id_forn_cert),
                                CONSTRAINT fk_ia_criterio
                                    FOREIGN KEY (id_criterio) REFERENCES criterio_sustentabilidade (id_criterio),
                                CONSTRAINT uk_ia_criterio UNIQUE (id_forn_cert, id_criterio),
                                CONSTRAINT ck_ia_evidencia CHECK (atendido = FALSE OR evidencia_url IS NOT NULL)
);

CREATE TABLE viagem (
                        id_viagem INT AUTO_INCREMENT PRIMARY KEY,
                        id_viajante INT NOT NULL,
                        data_inicio DATE NOT NULL,
                        data_fim DATE,
                        status VARCHAR(20) NOT NULL DEFAULT 'planejada',
                        CONSTRAINT fk_viagem_viajante
                            FOREIGN KEY (id_viajante) REFERENCES viajante (id_viajante),
                        CONSTRAINT ck_viagem_status CHECK (status IN ('planejada', 'em_andamento', 'concluida', 'cancelada')),
                        CONSTRAINT ck_viagem_datas CHECK (data_fim IS NULL OR data_fim >= data_inicio)
);

CREATE TABLE modal_transporte (
                                  id_modal INT AUTO_INCREMENT PRIMARY KEY,
                                  nome VARCHAR(60) NOT NULL,
                                  categoria VARCHAR(40)
);

CREATE TABLE fator_emissao (
                               id_fator INT AUTO_INCREMENT PRIMARY KEY,
                               id_modal INT NOT NULL,
                               ano_vigencia SMALLINT NOT NULL,
                               kgco2e_por_km DECIMAL(8,4) NOT NULL,
                               fonte VARCHAR(150) NOT NULL,
                               CONSTRAINT fk_fator_modal
                                   FOREIGN KEY (id_modal) REFERENCES modal_transporte (id_modal),
                               CONSTRAINT uk_fator_modal_ano UNIQUE (id_modal, ano_vigencia),
                               CONSTRAINT ck_fator_valor CHECK (kgco2e_por_km >= 0)
);

CREATE TABLE trecho_deslocamento (
                                     id_trecho INT AUTO_INCREMENT PRIMARY KEY,
                                     id_viagem INT NOT NULL,
                                     id_fator INT NOT NULL,
                                     origem VARCHAR(120) NOT NULL,
                                     destino VARCHAR(120) NOT NULL,
                                     distancia_km DECIMAL(8,2)  NOT NULL,
                                     CONSTRAINT fk_trecho_viagem
                                         FOREIGN KEY (id_viagem) REFERENCES viagem (id_viagem),
                                     CONSTRAINT fk_trecho_fator
                                         FOREIGN KEY (id_fator) REFERENCES fator_emissao (id_fator),
                                     CONSTRAINT ck_trecho_distancia CHECK (distancia_km > 0)
);

CREATE TABLE pegada_viagem (
                               id_pegada INT AUTO_INCREMENT PRIMARY KEY,
                               id_viagem INT NOT NULL UNIQUE,
                               total_kgco2e DECIMAL(10,3) NOT NULL,
                               calculado_em DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                               versao_metodologia VARCHAR(20) NOT NULL,
                               CONSTRAINT fk_pegada_viagem
                                   FOREIGN KEY (id_viagem) REFERENCES viagem (id_viagem)
);

CREATE TABLE servico_contratado (
                                    id_servico INT AUTO_INCREMENT PRIMARY KEY,
                                    id_viagem INT NOT NULL,
                                    id_fornecedor INT NOT NULL,
                                    tipo_servico VARCHAR(40)   NOT NULL,
                                    valor DECIMAL(10,2) NOT NULL,
                                    CONSTRAINT fk_servico_viagem
                                        FOREIGN KEY (id_viagem) REFERENCES viagem (id_viagem),
                                    CONSTRAINT fk_servico_fornecedor
                                        FOREIGN KEY (id_fornecedor) REFERENCES fornecedor (id_fornecedor),
                                    CONSTRAINT ck_servico_valor CHECK (valor >= 0)
);


CREATE TABLE avaliacao (
                           id_avaliacao INT AUTO_INCREMENT PRIMARY KEY,
                           id_fornecedor INT NOT NULL,
                           id_viajante INT NOT NULL,
                           nota INT NOT NULL,
                           comentario VARCHAR(1000) NOT NULL,
                           data_avaliacao DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
                           resposta VARCHAR(1000),
                           data_resposta DATETIME,
                           CONSTRAINT fk_avaliacao_fornecedor
                               FOREIGN KEY (id_fornecedor) REFERENCES fornecedor (id_fornecedor),
                           CONSTRAINT fk_avaliacao_viajante
                               FOREIGN KEY (id_viajante) REFERENCES viajante (id_viajante),
                           CONSTRAINT uk_avaliacao UNIQUE (id_fornecedor, id_viajante),
                           CONSTRAINT ck_avaliacao_nota CHECK (nota BETWEEN 1 AND 5),
                           CONSTRAINT ck_avaliacao_resposta CHECK (resposta IS NULL OR data_resposta IS NOT NULL)
);