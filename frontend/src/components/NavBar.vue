<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top">
    <div class="container">
      <a class="navbar-brand" href="#" @click.prevent="goToHome">🍽️ Receitas</a>

      <div class="collapse navbar-collapse">
        <ul class="navbar-nav ms-auto align-items-center">
          <li class="nav-item">
            <button class="btn btn-light me-2" @click="goToCreate">Criar Receita</button>
          </li>

          <li class="nav-item">
            <button class="btn btn-light me-2" @click="goToMyRecipes">Minhas Receitas</button>
          </li>

          <li class="nav-item" v-if="!isLoggedIn">
            <button class="btn btn-light ms-2" @click="register">Cadastrar</button>
          </li>

          <li class="nav-item" v-if="!isLoggedIn">
            <button class="btn btn-light ms-2" @click="goToLogin">Login</button>
          </li>

          <li class="nav-item" v-if="isLoggedIn">
            <button @click="logout" class="btn btn-outline-light ms-2">Logout</button>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, onMounted, watchEffect } from "vue";
import { useRouter } from "vue-router";

const router = useRouter();
const isLoggedIn = ref(false);

// Funções de navegação
function goToCreate() {
  router.push("/create");
}

function goToMyRecipes() {
  router.push("/my-recipes");
}

function goToHome() {
  router.push("/");
}

function register() {
  router.push("/register");
}

function goToLogin() {
  router.push("/login");
}

onMounted(() => {
  checkLoginStatus();
});

watchEffect(() => {
  checkLoginStatus();
});

function checkLoginStatus() {
  const token = localStorage.getItem("token");
  isLoggedIn.value = !!token;
}

// Função de logout
function logout() {
  localStorage.removeItem("token");
  isLoggedIn.value = false;
  router.push("/login");
}
</script>

<style scoped>
body,
#app {
  padding-top: 70px;
}

.logout-btn,
.btn-outline-light {
  border: 1px solid white;
  padding: 0.3rem 0.7rem;
  cursor: pointer;
}

.logout-btn:hover,
.btn-outline-light:hover {
  background-color: white;
  color: black;
}
</style>
