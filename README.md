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

A estrutura segue o padrão do Spring Boot, organizada por responsabilidades:
