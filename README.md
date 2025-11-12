# 🍽️ Aplicação de Receitas

Uma aplicação completa para criação, edição, visualização e exclusão de receitas culinárias.

O projeto é um *monorepo* dividido em **backend (Spring Boot + PostgreSQL)** e **frontend (Vue 3 + Vite + Bun)**, ambos configurados para rodar facilmente com Docker.

---

## 🚀 Tecnologias Utilizadas

### 🧠 Backend (API)

* **Java 21**
* **Spring Boot 3**
    * Spring Web
    * Spring Security (com JWT para autenticação)
    * Spring Data JPA
* **PostgreSQL** como banco de dados
* **Maven** para build e gerenciamento de dependências
* **Docker** para conteinerização (baseado em `openjdk:21-slim`)

### 💻 Frontend

* **Vue.js 3** (utilizando Composition API)
* **Vite** como bundler e servidor de desenvolvimento
* **Bun** como gerenciador de pacotes e executor
* **Bootstrap 5** para estilização e responsividade
* **Vue Router** para gerenciamento de rotas
* **Axios** para comunicação com a API REST

---

## 📂 Estrutura do Projeto

O repositório está organizado em duas pastas principais: `api` e `frontend`.

### `api/` (Backend Spring Boot)

A estrutura segue o padrão do Spring Boot, organizada por responsabilidades:´
```
api
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── example
    │   │           └── api
    │   │               ├── config/       # Configs de Spring Security, JWT
    │   │               ├── controllers/  # Endpoints REST (rotas da API)
    │   │               ├── cors/         # Configuração de Cross-Origin
    │   │               ├── dto/          # Data Transfer Objects (para JSON)
    │   │               ├── entity/       # Entidades JPA (modelos do banco)
    │   │               ├── repository/   # Repositórios Spring Data JPA
    │   │               ├── services/     # Lógica de negócio
    │   │               └── ApiApplication.java # Ponto de entrada
    │   └── resources
    │       ├── application.properties  # Configs da aplicação (banco, etc.)
    │       ├── app.key                 # Chave privada (para JWT)
    │       └── app.pub                 # Chave pública (para JWT)
    ...`
   ```

frontend/ (Frontend Vue.js)
A estrutura utiliza o padrão do Vite + Vue 3:
```
frontend
└── src
    ├── assets/       # Imagens, fontes e CSS/SCSS globais
    ├── components/   # Componentes Vue reutilizáveis (ex: Navbar, RecipeCard)
    ├── pages/        # Telas/Views da aplicação (ex: Home, Login, CreateRecipe)
    ├── router/       # Configuração do Vue Router (index.js)
    ├── services/     # Módulos para chamadas de API (Axios)
    ├── App.vue       # Componente raiz da aplicação
    └── main.js       # Ponto de entrada do Vue.js

```
---

## ⚙️ Pré-requisitos

Para rodar este projeto, você precisa ter instalado em sua máquina:

* [Docker](https://www.docker.com/get-started)
* [Docker Compose](https://docs.docker.com/compose/install/) (geralmente já vem com o Docker Desktop)

---

## 🧱 Como Rodar Localmente com Docker

O `docker-compose.yaml` na raiz do projeto foi configurado para construir e orquestrar os dois serviços (api e frontend) e o banco de dados.

### 1️⃣ Clone o repositório

```bash
git clone https://github.com/jaimecabrito01/Api-receitas.git
cd Api-receitas
```
### Suba os containers
- Use o Docker Compose para construir as imagens e iniciar todos os serviços:
```
docker compose up --build
```
### Pronto !!
Após os containers subirem, a aplicação estará disponível:

Frontend (Vue): ``` http://localhost:5173 ```

Backend (Spring): ``` http://localhost:8080 ``` (A porta da API, caso precise acessá-la diretamente)

    
