<template>
  <div class="editar-perfil container">
    <h2 class="text-center mb-4">👤 Editar Perfil</h2>

    <div v-if="loading" class="text-center text-muted">Carregando informações...</div>

    <form v-else @submit.prevent="atualizarPerfil" class="form-editar">
      <div class="form-group mb-3">
        <label for="nome">Nome</label>
        <input
          id="nome"
          v-model="user.nome"
          type="text"
          class="form-control"
          placeholder="Seu nome completo"
          required
        />
      </div>

      <div class="form-group mb-3">
        <label for="email">E-mail</label>
        <input
          id="email"
          v-model="user.email"
          type="email"
          class="form-control"
          placeholder="seuemail@email.com"
          required
        />
      </div>

      <div class="form-group mb-4">
        <label for="senha">Nova Senha</label>
        <input
          id="senha"
          v-model="user.senha"
          type="password"
          class="form-control"
          placeholder="Digite uma nova senha"
        />
      </div>

      <div class="d-flex justify-content-between">
        <button type="submit" class="btn btn-primary">Salvar Alterações</button>
        <router-link to="/" class="btn btn-secondary">Cancelar</router-link>
      </div>
    </form>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import api from "../services/api";
import { useRouter } from "vue-router";

const router = useRouter();
const user = ref({ nome: "", email: "", senha: "" });
const loading = ref(true);

onMounted(async () => {
  const token = localStorage.getItem("token");
  if (!token) {
    router.push("/login");
    return;
  }

  try {
    const response = await api.get("/user/me", {
      headers: { Authorization: `Bearer ${token}` },
    });
    user.value.nome = response.data.nome;
    user.value.email = response.data.email;
  } catch (error) {
    console.error("Erro ao buscar dados do usuário:", error);
    alert("Erro ao carregar seus dados. Faça login novamente.");
    localStorage.removeItem("token");
    router.push("/login");
  } finally {
    loading.value = false;
  }
});

async function atualizarPerfil() {
  const token = localStorage.getItem("token");
  if (!token) return alert("Sessão expirada. Faça login novamente.");

  try {
    await api.put(
      "/user/update",
      {
        nome: user.value.nome,
        email: user.value.email,
        senha: user.value.senha || undefined,
      },
      {
        headers: { Authorization: `Bearer ${token}` },
      }
    );

    alert("Perfil atualizado com sucesso!");
    router.push("/");
  } catch (error) {
    console.error(error);
    alert("Erro ao atualizar o perfil.");
  }
}
</script>

<style scoped>
.container {
  max-width: 500px;
  margin-top: 100px;
  background: #ffffff;
  border-radius: 12px;
  padding: 2rem;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.text-center {
  text-align: center;
}

label {
  font-weight: 600;
  color: #374151;
}

.form-control {
  border-radius: 8px;
  border: 1px solid #d1d5db;
  padding: 0.6rem;
  transition: border-color 0.2s;
}

.form-control:focus {
  border-color: #2563eb;
  box-shadow: 0 0 0 3px rgba(37, 99, 235, 0.2);
}

.btn {
  border-radius: 8px;
  padding: 0.6rem 1.2rem;
  font-weight: 500;
}

.btn-primary {
  background-color: #2563eb;
  border: none;
}

.btn-primary:hover {
  background-color: #1e40af;
}

.btn-secondary {
  background-color: #9ca3af;
  color: white;
  text-decoration: none;
}

.btn-secondary:hover {
  background-color: #6b7280;
}
</style>
