# 🍽️ Aplicação de Receitas

Uma aplicação completa para criação, edição, visualização e exclusão de receitas culinárias.  
O projeto é dividido em **backend (Spring Boot + PostgreSQL)** e **frontend (Vue 3 + Vite + Bun)**.

---

## 🚀 Tecnologias Utilizadas

### 🧠 Backend (API)
- **Java 21**
- **Spring Boot 3**
  - Spring Web
  - Spring Security (JWT)
  - Spring Data JPA
- **PostgreSQL** como banco de dados
- **Maven** para build e dependências
- **Docker** para conteinerização
- **OpenJDK 21** como base da imagem Docker

### 💻 Frontend
- **Vue.js 3** com **Composition API**
- **Vite** como bundler e servidor de desenvolvimento
- **Bun** como gerenciador de pacotes e executor
- **Bootstrap 5** para estilização
- Comunicação com a API via **Axios**
- **Vue Router** para navegação entre telas

---

## 🧩 Estrutura do Projeto
├── backend/
│ ├── src/
│ ├── pom.xml
│ └── Dockerfile
│
├── frontend/
│ ├── src/
│ ├── package.json
│ └── Dockerfile
│
└── docker-compose.yml

## ⚙️ Pré-requisitos

Antes de começar, você precisa ter instalado:

- [Docker](https://www.docker.com/get-started)
- [Docker Compose](https://docs.docker.com/compose/install/)

## 🧱 Como Rodar Localmente com Docker

### 1️⃣ Clone o repositório
```bash
git clone https://github.com/jaimecabrito01/Api-receitas.git
cd Api-receitas

docker compose up --build

```

### Pronto !!
 A aplicação ja estara rodando em localhost:5173 !!


