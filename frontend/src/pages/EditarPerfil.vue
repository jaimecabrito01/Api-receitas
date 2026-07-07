<template>
  <div class="editar-perfil container text-center">
   

    <h2 class="mb-4"> <img src="../assets/account-circle.png" alt="">Editar Perfil</h2>

    <div v-if="alert.message" class="mb-4">
      <div
        :class="[
          'alert',
          alert.type === 'success' ? 'alert-success' : 'alert-danger',
          'text-center'
        ]"
        role="alert"
      >
        {{ alert.message }}
      </div>
    </div>

    <div v-if="loading" class="text-muted">Carregando informações...</div>

    <form v-else @submit.prevent="atualizarPerfil" class="form-editar text-start">
      <div class="form-group mb-3">
        <label for="nome">Nome</label>
        <input
          id="nome"
          v-model="user.name"
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
          v-model="user.password"
          type="password"
          class="form-control"
          placeholder="Digite uma nova senha"
        />
      </div>

      <div class="d-flex justify-content-between">
        <button type="submit" class="btn btn-primary" :disabled="loading">
          {{ loading ? 'Salvando...' : 'Salvar Alterações' }}
        </button>
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
const user = ref({ name: "", email: "", password: "" });
const loading = ref(true);
const alert = ref({ message: "", type: "" }); 

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
    user.value.name = response.data.nome;
    user.value.email = response.data.email;
  } catch (error) {
    console.error("Erro ao buscar dados do usuário:", error);
    alert.value = { message: "Erro ao carregar seus dados. Faça login novamente.", type: "danger" };
    localStorage.removeItem("token");
    router.push("/login");
  } finally {
    loading.value = false;
  }
});

async function atualizarPerfil() {
  loading.value = true;
  const token = localStorage.getItem("token");

  const payload = {};
  if (user.value.name.trim() !== "") payload.name = user.value.name.trim();
  if (user.value.email.trim() !== "") payload.email = user.value.email.trim();
  if (user.value.password.trim() !== "") payload.password = user.value.password.trim();

  try {
    await api.put("/user/update", payload, {
      headers: { Authorization: `Bearer ${token}` },
    });

    alert.value = { message: "✅ Perfil atualizado com sucesso!", type: "success" };
    user.value.password = ""; 
  } catch (error) {
    console.error("Erro ao atualizar perfil:", error);
    alert.value = { message: "❌ Erro ao atualizar perfil. Tente novamente.", type: "danger" };
  } finally {
    loading.value = false;

    setTimeout(() => (alert.value.message = ""), 4000);
  }
}
</script>

<style scoped>
.container {
  max-width: 500px;
  background: #ffffff;
  border-radius: 12px;
  padding: 2rem;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

.logo {
  width: 120px;
  height: auto;
  display: block;
  margin: 0 auto 1.5rem auto;
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
