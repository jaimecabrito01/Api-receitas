<template>
  <nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top">
    <div class="container">
      <a class="navbar-brand" href="#">🍽️ Receitas</a>
      <div class="collapse navbar-collapse">
        <ul class="navbar-nav ms-auto">
          <li class="nav-item">
            <button class="btn btn-light me-2" @click="goToCreate">Criar Receita</button>
          </li>
          <li class="nav-item">
            <button class="btn btn-light" @click="goToMyRecipes">Minhas Receitas</button>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { useRouter } from "vue-router";
const router = useRouter();

function goToCreate() {
  router.push("/create");
}

function goToMyRecipes() {
  router.push("/my-recipes");
}
</script>

<style scoped>

body, #app {
  padding-top: 70px; 
}
</style>
