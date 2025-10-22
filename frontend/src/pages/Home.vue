<template>
  <div class="container mt-4">
    <h1 class="mb-4">🍽️ Receitas</h1>

    <div v-if="loading" class="text-center">Carregando receitas...</div>

    <div v-else class="row">
      <div v-for="receita in receitas" :key="receita.id" class="col-md-4 mb-3">
        <div class="card h-100" @click="goToRecipe(receita.id)" style="cursor: pointer;">
          <div class="card-body">
            <h5 class="card-title">{{ receita.titulo }}</h5>
            <p class="card-text">{{ receita.descricao }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import api from "../services/api";

const receitas = ref([]);
const loading = ref(true);
const router = useRouter();

onMounted(async () => {
  try {
    const response = await api.get("/receitas");
    receitas.value = response.data;
  } catch (error) {
    console.error(error);
  } finally {
    loading.value = false;
  }
});

function goToRecipe(id) {
  router.push(`/recipe/${id}`);
}
</script>
