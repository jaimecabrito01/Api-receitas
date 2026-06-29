# AGENTS.md — Api-receitas

## Stack

- **Backend** (`api/`): Java 21, Spring Boot 3.5.6, Maven, PostgreSQL 16, JWT (RSA)
- **Frontend** (`frontend/`): Vue 3 (Composition API), Vite 7, Bun, Bootstrap 5, Axios, Vue Router
- **Infra**: Docker Compose at root, Kubernetes at `~/Projetos/devops/receitas-app-infra/k8s/`, ArgoCD

## Run locally

```bash
docker compose up --build
```

- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`
- DB: `postgresql://admin:admin123@localhost:5432/receitas`

## Backend endpoints

| Method | Path | Auth | Description |
|--------|------|------|-------------|
| POST | `/authenticate` | No | Login, returns JWT |
| POST | `/user/create` | No | Register |
| GET | `/user/me` | JWT | Current user |
| GET | `/user/all` | No | List users |
| PUT | `/user/update` | JWT | Update current user |
| DELETE | `/user/delete/{id}` | JWT | Delete user |
| GET | `/receitas/all` | No | List recipes |
| GET | `/receitas/{id}` | No | Get recipe |
| GET | `/receitas/me` | JWT | My recipes |
| POST | `/receitas/add` | JWT | Create recipe |
| PUT | `/receitas/update/{id}` | JWT | Update recipe |
| DELETE | `/receitas/delete/{id}` | JWT | Delete recipe |

`ingredientes` and `passos` are `List<String>` (stored as JSON in PostgreSQL).

## Auth

- JWT via RSA key pair: `api/src/main/resources/app.key` (private, gitignored), `app.pub` (public)
- Token expires in 86400s (24h), JWT subject = user UUID
- Send as `Authorization: Bearer <token>` header
- CORS allows only `http://localhost:5173`
- In Kubernetes, JWT keys are mounted via SealedSecret `jwt-keys` at `/etc/jwt/`

## DB

- `ddl-auto=update` — JPA creates/alters tables on startup
- Roles `admin` and `basic` seeded automatically via `DataInitializer`
- Tables: `users`, `receitas`, `roles`, `tb_users_roles`

## Dev without Docker

Backend:
```bash
cd api && ./mvnw spring-boot:run
```
Requires PostgreSQL on localhost:5432 with the credentials above.

Frontend:
```bash
cd frontend && bun install && bun run dev
```

## Build

Backend (produces `api/target/*.jar`):
```bash
cd api && mvn clean package -DskipTests
```

Frontend:
```bash
cd frontend && bun run build
```

## Kubernetes secrets

- `postgres-credentials`: created manually or via SealedSecret
- `jwt-keys`: managed via SealedSecret (`k8s/security/jwt-sealedsecret.yaml`)

To regenerate the sealed JWT keys after rotating them:
```bash
bash ~/Projetos/devops/receitas-app-infra/k8s/security/seal-jwt-keys.sh
```
Then commit the updated `jwt-sealedsecret.yaml` — ArgoCD syncs automatically.

## Conventions

- Backend: Portuguese naming (`Receita`, `titulo`, `descricao`, `ingredientes`, `passos`)
- DTOs with getters/setters (except records `LoginRequest`, `UserResponseDTO`, `LoginResponse`)
- Lombok on entities only (`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`)
- Security config at `config/SecurityConfig.java`, CORS at `cors/CorsConfig.java`
- Frontend: Axios instance at `src/services/api.js` (baseURL hardcoded to `http://localhost:8080`)
- Frontend: Vue Router guards redirect to `/login` if no token; redirect away from `/login`/`/register` if token exists
- Token stored in `localStorage.getItem("token")`

## Notable quirks

- `ReceitaRepository.findAllReceitas()` returns `List<ReceitaResponseDTO>` — check for JPQL custom query
- No lint/format/typecheck config committed
- Only one stub test (`ApiApplicationTests.java`)
