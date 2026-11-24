# EcoRota API

![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen?logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql)
![Flyway](https://img.shields.io/badge/Flyway-Migrations-red)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED?logo=docker)

API RESTful desenvolvida em **Java 21** e **Spring Boot** para a plataforma **EcoRota**, um sistema voltado à gestão e promoção do turismo sustentável, controle de capacidade de carga ambiental, rastreamento de pegada de carbono e auditoria de certificações socioambientais.

---

## Sobre o Projeto

O turismo em áreas naturais enfrenta desafios críticos como a superlotação de ecossistemas frágeis, a falta de transparência sobre práticas sustentáveis de prestadores de serviço e o impacto das emissões de gases de efeito estufa nos deslocamentos.

A **EcoRota API** resolve esses problemas estruturando a operação em **5 módulos integrados**:

1. **Território e Atrativos:** Cadastro hierárquico de municípios (com código IBGE), destinos turísticos e atrativos naturais/culturais (coordenadas geográficas, exigência de guia e valor de ingresso).
2. **Capacidade de Carga e Reservas:** Definição de cotas diárias e por horário com base em estudos de capacidade de carga, além de agendamento de janelas de visitação com controle de concorrência (*Pessimistic Write Lock*) para impedir *overbooking*.
3. **Fornecedores e Selo EcoRota:** Registro de prestadores de serviço (via Cadastur, indicação de viajantes ou cadastro próprio), fluxo de reivindicação de perfil empresarial via CNPJ e auditoria de critérios de sustentabilidade (eixos ambiental, social, econômico e gestão).
4. **Viagens e Pegada de Carbono:** Planejamento de roteiros, contratação de serviços locais e cálculo automático de emissões ($\text{kgCO}_2\text{e}$) por trecho de deslocamento utilizando fatores de emissão por modal (metodologia `GHG-Protocol-v1.0`).
5. **Avaliações e Reputação:** Sistema de avaliação de fornecedores por viajantes, cálculo de média de notas e direito de resposta oficial exclusivo para perfis reivindicados.

---

## Tecnologias Utilizadas

* **Linguagem:** Java 21 (LTS)
* **Framework:** Spring Boot 3 (Web, Data JPA, Validation)
* **Banco de Dados:** MySQL 8.0
* **Versionamento de Banco:** Flyway Migration (`V1__criar_tabelas.sql` com 19 tabelas e restrições de integridade)
* **Produtividade:** Lombok & Java Records (DTOs imutáveis)
* **Build e Containerização:** Maven Wrapper (`mvnw`) & Docker (Multi-stage build)

---

## Endpoints Principais

| Módulo | Recurso | Métodos | Descrição |
| :--- | :--- | :--- | :--- |
| **1. Território** | `/api/municipios` | `GET`, `POST` | Listagem, busca por ID e cadastro de municípios |
| **1. Território** | `/api/destinos` | `GET`, `POST` | Gestão de destinos vinculados a um município (`?idMunicipio=`) |
| **1. Território** | `/api/atrativos` | `GET`, `POST` | Gestão de atrativos turísticos (`?idDestino=`) |
| **2. Reservas** | `/api/capacidades-carga` | `GET`, `POST` | Regras de vigência e cotas de visitação por atrativo |
| **2. Reservas** | `/api/janelas-visitacao` | `GET`, `POST` | Horários disponíveis validados contra a capacidade de carga |
| **2. Reservas** | `/api/viajantes` | `GET`, `POST` | Cadastro e consulta de viajantes |
| **2. Reservas** | `/api/reservas` | `GET`, `POST`, `PATCH` | Criação e cancelamento (`/cancelar`) com atualização automática de vagas |
| **3. Certificação** | `/api/fornecedores` | `GET`, `POST`, `PATCH` | Cadastro e reivindicação de perfil (`/reivindicar`) |
| **3. Certificação** | `/api/criterios-sustentabilidade` | `GET`, `POST` | Critérios ESG por eixo (`ambiental`, `social`, `economico`, `gestao`) |
| **3. Certificação** | `/api/certificacoes` | `GET`, `POST`, `PATCH` | Emissão de selo, registro de itens de auditoria e alteração de status |
| **4. Carbono** | `/api/modais-transporte` | `GET`, `POST` | Meios de transporte disponíveis |
| **4. Carbono** | `/api/fatores-emissao` | `GET`, `POST` | Fatores de emissão ($\text{kgCO}_2\text{e/km}$) por modal e ano |
| **4. Carbono** | `/api/viagens` | `GET`, `POST`, `PATCH` | Roteiros, adição de trechos (`/trechos`), serviços (`/servicos`) e recálculo de $\text{CO}_2$ |
| **5. Avaliações** | `/api/avaliacoes` | `GET`, `POST`, `PATCH` | Avaliações de fornecedores, resumo de médias (`/resumo`) e resposta (`/responder`) |

---

## Como Executar Localmente

### Pré-requisitos

* **JDK 21** instalado e configurado no `JAVA_HOME`
* **MySQL 8.0** rodando localmente na porta `3306`
* **Git** instalado

### Passo a passo

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/tthiago-oliveira/ecorota-api.git
   cd ecorota-api
   ```

2. **Verifique o banco de dados MySQL:**
   * Certifique-se de que o serviço do MySQL 8 está ativo.
   * Por padrão, a aplicação conecta usando usuário `root` e senha `root` e cria automaticamente o banco `ecorota` (`createDatabaseIfNotExist=true`), executando todas as migrations do Flyway na primeira inicialização.
   * Caso a senha do seu MySQL local seja diferente, ajuste o valor padrão em `src/main/resources/application.properties`:
     ```properties
     spring.datasource.password=${DB_PASSWORD:sua_senha_local}
     ```

3. **Execute a aplicação pelo terminal:**
   * **Windows (PowerShell):**
     ```powershell
     .\mvnw.cmd spring-boot:run
     ```
   * **Windows (CMD):**
     ```cmd
     mvnw.cmd spring-boot:run
     ```
   * **Linux / macOS:**
     ```bash
     ./mvnw spring-boot:run
     ```

4. **Acesse a API:**
   * Quando aparecer `Started EcorotaApiApplication` no console, a API estará disponível em:
     `http://localhost:8080/api/municipios`

---

## Execução com Docker e Deploy em Nuvem

O projeto possui um `Dockerfile` multi-stage na raiz e está preparado para ambientes em nuvem (como Render, Railway ou Fly.io) utilizando variáveis de ambiente com *fallback* automático para execução local:

| Variável | Descrição | Valor Padrão (Local) |
| :--- | :--- | :--- |
| `PORT` | Porta HTTP do servidor embutido | `8080` |
| `DB_URL` | String JDBC de conexão com o MySQL 8 | `jdbc:mysql://localhost:3306/ecorota?...` |
| `DB_USER` | Usuário do banco de dados | `root` |
| `DB_PASSWORD` | Senha do banco de dados | `root` |

Para gerar e rodar a imagem Docker localmente:

```bash
docker build -t ecorota-api .
docker run -p 8080:8080 -e DB_URL="jdbc:mysql://host.docker.internal:3306/ecorota?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC" ecorota-api
```

---

## Estrutura de Pastas

```text
ecorota-api/
├── src/
│   ├── main/
│   │   ├── java/br/com/ecorota/ecorota_api/
│   │   │   ├── config/          # Configuração de CORS
│   │   │   ├── controller/      # Controladores REST (14 endpoints base)
│   │   │   ├── dto/             # Records de entrada (Request) e saída (Response)
│   │   │   ├── exception/       # Tratamento global de erros e validações
│   │   │   ├── model/           # Entidades JPA e Enums de domínio
│   │   │   ├── repository/      # Interfaces Spring Data JPA
│   │   │   └── service/         # Regras de negócio e transações
│   │   └── resources/
│   │       ├── db/migration/    # Scripts SQL versionados pelo Flyway
│   │       └── application.properties
├── Dockerfile                   # Build em 2 etapas (JDK 21 -> JRE 21)
├── pom.xml                      # Dependências Maven
└── README.md
```