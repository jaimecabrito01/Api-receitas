<template>
  <div class="d-flex justify-content-center align-items-center min-vh-100 bg-light">
    <div class="card shadow-lg p-4" style="width: 100%; max-width: 420px;">
      <div class="card-body">
        <h2 class="text-center mb-4 fw-bold text-primary"> 
          <img src="../assets/account-circle.png" alt="">
          Cadastrar Usuário</h2>

        <form @submit.prevent="registerUser" class="d-flex flex-column gap-3">
          <div>
            <label class="form-label">Nome:</label>
            <input
              v-model="name"
              type="text"
              class="form-control"
              placeholder="Digite seu nome"
              required
            />
          </div>

          <div>
            <label class="form-label">Email:</label>
            <input
              v-model="email"
              type="email"
              class="form-control"
              placeholder="exemplo@email.com"
              required
            />
          </div>

          <div>
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

        <transition name="fade">
          <div
            v-if="successMessage"
            class="alert alert-success alert-dismissible fade show mt-4 text-center"
            role="alert"
          >
            <i class="bi bi-check-circle-fill me-2"></i> {{ successMessage }}
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="alert"
              aria-label="Fechar"
              @click="successMessage = ''"
            ></button>
          </div>
        </transition>

        <transition name="fade">
          <div
            v-if="errorMessage"
            class="alert alert-danger alert-dismissible fade show mt-4 text-center"
            role="alert"
          >
            <i class="bi bi-exclamation-triangle-fill me-2"></i> {{ errorMessage }}
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="alert"
              aria-label="Fechar"
              @click="errorMessage = ''"
            ></button>
          </div>
        </transition>
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

    setTimeout(() => router.push("/login"), 1500); 
  } catch (error) {
    console.error(error);
    errorMessage.value = "Erro ao cadastrar usuário.";
  }
}
</script>

<style>
.fade-enter-active, .fade-leave-active {
  transition: opacity 0.4s ease;
}
.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>
