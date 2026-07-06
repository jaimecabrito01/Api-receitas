<template>
  <div class="d-flex justify-content-center align-items-center min-vh-100 bg-light">
    <div class="card shadow-lg p-4" style="width: 100%; max-width: 400px;">
      <div class="card-body">
        <h2 class="text-center mb-4"><img src="../assets/account-circle.png" alt=""> Login</h2>

        <form @submit.prevent="login" class="d-flex flex-column gap-3">
          <div>
            <label for="email" class="form-label">E-mail</label>
            <input
              v-model="email"
              type="email"
              id="email"
              class="form-control"
              placeholder="Digite seu e-mail"
              required
            />
          </div>

          <div>
            <label for="password" class="form-label">Senha</label>
            <input
              v-model="password"
              type="password"
              id="password"
              class="form-control"
              placeholder="Digite sua senha"
              required
            />
          </div>

          <button type="submit" class="btn btn-primary w-100" :disabled="loading">
            {{ loading ? 'Entrando...' : 'Entrar' }}
          </button>
        </form>

        <!-- Mensagens de sucesso ou erro -->
        <div v-if="successMessage" class="alert alert-success mt-4 text-center" role="alert">
          {{ successMessage }}
        </div>

        <div v-if="errorMessage" class="alert alert-danger mt-4 text-center" role="alert">
          {{ errorMessage }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'
import { useRouter } from "vue-router";

const email = ref('')
const password = ref('')
const successMessage = ref('')
const errorMessage = ref('')
const loading = ref(false)
const router = useRouter();

async function login() {
  successMessage.value = ''
  errorMessage.value = ''
  loading.value = true

  try {
    const response = await axios.post('/authenticate', {
      email: email.value,
      password: password.value
    })

    localStorage.setItem('token', response.data.token)

    successMessage.value = 'Login realizado com sucesso!'

    setTimeout(() => {
      router.push('/')
    }, 1500)
  } catch (err) {
    errorMessage.value = 'E-mail ou senha incorretos.'
  } finally {
    loading.value = false
  }
}
</script>
