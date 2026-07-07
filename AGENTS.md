# AGENTS.md — Api-receitas

## Stack

- **Backend** (`api/`): Java 21, Spring Boot 3.5.6, Maven, PostgreSQL 16, JWT (RSA)
- **Frontend** (`frontend/`): Vue 3 (Composition API), Vite 7, Bun, Bootstrap 5, Axios, Vue Router
- **Infra**: Docker Compose at root, CI builds/pushes Docker images to `ghcr.io` (`.github/workflows/ci.yml`), Kubernetes at `~/Projetos/devops/receitas-app-infra/k8s/`, ArgoCD

## Run locally

```bash
docker compose up --build
```

- Frontend: `http://localhost:5173`
- Backend: `http://localhost:8080`
- DB: `postgresql://admin:admin123@localhost:5432/receitas`

Dev without Docker:
- Backend: `cd api && ./mvnw spring-boot:run` (requires PostgreSQL on localhost:5432)
- Frontend: `cd frontend && bun install && bun run dev`

## Build

- Backend JAR: `cd api && ./mvnw package -DskipTests` → `api/target/*.jar`
- Frontend: `cd frontend && bun run build`
- Docker (backend usa `Dockerfile.local` — copia JAR pré-buildado, sem baixar deps):

```bash
cd api && ./mvnw package -DskipTests && cd ..
docker compose up --build -d
```

## Backend endpoints

| Method | Path | Auth | Notes |
|--------|------|------|-------|
| POST | `/authenticate` | No | Login via email/password, returns JWT + expiresIn |
| POST | `/user/create` | No | Register (`name`, `email`, `password`) |
| GET | `/user/me` | JWT | Current user info |
| GET | `/user/all` | No | List all users |
| PUT | `/user/update` | JWT | Updates authenticated user (ignores any `id` in body) |
| DELETE | `/user/delete/{id}` | JWT | Path param `{id}` is **ignored** — deletes the authenticated user |
| GET | `/receitas/all` | No | All recipes (returns `List<ReceitaResponseDTO>` with `usuarioNome`) |
| GET | `/receitas/{id}` | No | Single recipe |
| GET | `/receitas/me` | JWT | Current user's recipes |
| POST | `/receitas/add` | JWT | Create recipe |
| PUT | `/receitas/update/{id}` | JWT | Update recipe |
| DELETE | `/receitas/delete/{id}` | JWT | Delete recipe |

`ingredientes` and `passos` are `List<String>` (stored as JSON in PostgreSQL).

## Auth

- JWT via RSA key pair: `api/src/main/resources/app.key` (private, gitignored), `app.pub` (public)
- Token expires in 86400s (24h), JWT subject = user UUID
- Send as `Authorization: Bearer <token>`
- CORS allows only `http://localhost:5173`
- In Kubernetes, JWT keys mounted via SealedSecret `jwt-keys` at `/etc/jwt/`

## DB

- `ddl-auto=update` — JPA creates/alters tables on startup
- Roles `admin` and `basic` seeded by `DataInitializer` (`entity/DataInitializer.java`)
- Tables: `users`, `receitas`, `roles`, `tb_users_roles`

## Conventions

- Backend: Portuguese naming (`Receita`, `titulo`, `descricao`, `ingredientes`, `passos`)
- DTOs: getters/setters classes except records (`LoginRequest`, `LoginResponse`, `UserResponseDTO`, `UserDTO`)
- Lombok on entities only (`@Getter`, `@Setter`, `@NoArgsConstructor`, `@AllArgsConstructor`)
- Security config at `config/SecurityConfig.java`, CORS at `cors/CorsConfig.java`
- Token stored in `localStorage.getItem("token")`
- Vue Router guards: redirect to `/login` if no token and route requires auth; redirect away from `/login`/`/register` if token exists

## Notable quirks

- **Frontend Axios baseURL is `/api`** (not `http://localhost:8080` as the README states) — see `frontend/src/services/api.js:4`. This requires a reverse proxy (present in k8s ingress) for production. For local dev without Docker, you'll need to either add Vite proxy config or change to the full backend URL.
- **`ReceitaRepository.findAllReceitas()`** returns `List<ReceitaResponseDTO>` via JPQL (`SELECT new com.example.api.dto.ReceitaResponseDTO(...)`).
- **`DELETE /user/delete/{id}`** ignores the `{id}` path variable — it deletes the authenticated user via JWT.
- **`PUT /user/update`** also identifies the user from JWT, not from request body.
- No lint/format/typecheck config committed.
- Only one stub test (`ApiApplicationTests.java`).

## Kubernetes secrets

- `postgres-credentials` and `jwt-keys`: managed via SealedSecret (`k8s/security/`)
- To regenerate after rotating keys: `bash ~/Projetos/devops/receitas-app-infra/k8s/app/security/seal-secrets.sh`
- Commit updated `*-sealedsecret.yaml` — ArgoCD syncs automatically.
