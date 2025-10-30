<template>
  <div class="container mt-5" style="max-width: 400px;">
    <h2 class="mb-4 text-center">🔐 Login</h2>

    <form @submit.prevent="login">
      <div class="mb-3">
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

      <div class="mb-3">
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

      <p v-if="error" class="text-danger mt-3 text-center">{{ error }}</p>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import axios from 'axios'

const email = ref('')
const password = ref('')
const error = ref('')
const loading = ref(false)

async function login() {
  error.value = ''
  loading.value = true

  try {
    const response = await axios.post('http://localhost:8080/authenticate', {
      email: email.value,
      password: password.value
    })

    
    localStorage.setItem('token', response.data.token)
    alert('Login realizado com sucesso!')

   
    window.location.href = '/'
  } catch (err) {
    error.value = 'E-mail ou senha incorretos.'
  } finally {
    loading.value = false
  }
}
</script>
