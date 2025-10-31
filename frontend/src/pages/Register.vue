<template>
  <div class="container mt-5">
    <div class="row justify-content-center">
      <div class="col-md-6">
        <div class="card shadow-sm">
          <div class="card-body">
            <h2 class="text-center mb-4">Cadastrar Usuário</h2>

            <form @submit.prevent="registerUser">
              <div class="mb-3">
                <label class="form-label">Nome:</label>
                <input
                  v-model="name"
                  type="text"
                  class="form-control"
                  placeholder="Digite seu nome"
                  required
                />
              </div>

              <div class="mb-3">
                <label class="form-label">Email:</label>
                <input
                  v-model="email"
                  type="email"
                  class="form-control"
                  placeholder="exemplo@email.com"
                  required
                />
              </div>

              <div class="mb-3">
                <label class="form-label">Senha:</label>
                <input
                  v-model="password"
                  type="password"
                  class="form-control"
                  placeholder="Digite sua senha"
                  required
                />
              </div>

              <button type="submit" class="btn btn-primary w-100">
                Cadastrar
              </button>
            </form>

            <div v-if="successMessage" class="alert alert-success mt-3 text-center">
              {{ successMessage }}
            </div>

            <div v-if="errorMessage" class="alert alert-danger mt-3 text-center">
              {{ errorMessage }}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import api from "../services/api"; 
import { useRouter } from "vue-router";


const name = ref("");
const email = ref("");
const password = ref("");
const successMessage = ref("");
const errorMessage = ref("");
const router = useRouter();

async function registerUser() {
  successMessage.value = "";
  errorMessage.value = "";

  try {
    const payload = {
      name: name.value,
      email: email.value,
      password: password.value,
    };

    await api.post("/user/create", payload); 
    successMessage.value = "Usuário cadastrado com sucesso!";
    name.value = "";
    email.value = "";
    password.value = "";
    router.push("/")
  } catch (error) {
    console.error(error);
    errorMessage.value = "Erro ao cadastrar usuário.";
  }
}
</script>
