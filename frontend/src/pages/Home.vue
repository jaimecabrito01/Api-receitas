<template>
  <div class="container">
    <h1>🍽️ Receitas</h1>

    <div v-if="loading" class="loading">Carregando receitas...</div>

    <div v-else class="recipes">
      <div
        v-for="receita in receitas"
        :key="receita.id"
        class="recipe-card"
      >
        <h3>{{ receita.titulo }}</h3>
        <p>{{ receita.descricao }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import api from '../services/api'

const receitas = ref([]);
const loading = ref(true);

onMounted(async () => {
  try {
    const response = await api.get("/receitas");
    receitas.value = response.data;
  } catch (error) {
    console.error("Erro ao buscar receitas:", error);
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.container {
  max-width: 800px;
  margin: 0 auto;
  padding: 1rem;
}

.recipe-card {
  background: #fff;
  border-radius: 10px;
  padding: 1rem;
  margin-bottom: 1rem;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.loading {
  text-align: center;
  font-style: italic;
}
</style>
