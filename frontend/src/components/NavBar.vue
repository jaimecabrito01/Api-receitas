<template>
  <nav class="navbar">
    <h2>🍽️ Receitas</h2>
    <div class="links">
      <button @click="goToCreate">Criar Receita</button>
      <button @click="goToMyRecipes">Minhas Receitas</button>
    </div>
  </nav>
</template>

<script setup>
import { useRouter } from "vue-router";

const router = useRouter();

function goToCreate() {
  router.push("/create"); 
}


</script>

<style scoped>
.navbar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  background-color: #ff7043;
  padding: 1rem;
  color: white;
  border-radius: 0 0 10px 10px;
}

.links button {
  margin-left: 0.5rem;
  background-color: white;
  color: #ff7043;
  border: none;
  padding: 0.5rem 1rem;
  border-radius: 5px;
  cursor: pointer;
}

.links button:hover {
  opacity: 0.8;
}
</style>
