<template>
  <div class="container mt-5" style="max-width: 400px;">
    <h2 class="text-center mb-4">🔐 Login</h2>

    <div class="card shadow-sm p-4">
      <form @submit.prevent="login">
        <div class="mb-3">
          <label for="email" class="form-label">Email</label>
          <input
            type="email"
            id="email"
            v-model="email"
            class="form-control"
            placeholder="Digite seu email"
            required
          />
        </div>

        <div class="mb-3">
          <label for="password" class="form-label">Senha</label>
          <input
            type="password"
            id="password"
            v-model="password"
            class="form-control"
            placeholder="Digite sua senha"
            required
          />
        </div>

        <button type="submit" class="btn btn-primary w-100" :disabled="loading">
          {{ loading ? 'Entrando...' : 'Entrar' }}
        </button>

        <div v-if="error" class="alert alert-danger mt-3">
          {{ error }}
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import axios from "axios";
import { useRouter } from "vue-router";

const email = ref("");
const password = ref("");
const loading = ref(false);
const error = ref(null);
const router = useRouter();

const login = async () => {
  loading.value = true;
  error.value = null;

  try {
    const response = await axios.post("http://localhost:8080/authenticate", {
      email: email.value,
      senha: password.value,
    });

    // O backend deve retornar o token JWT
    const token = response.data;

    // Salva no localStorage
    localStorage.setItem("token", token);

    // Redireciona para a página principal
    router.push("/");
  } catch (err) {
    error.value = "Credenciais inválidas. Tente novamente.";
  } finally {
    loading.value = false;
  }
};
</script>
