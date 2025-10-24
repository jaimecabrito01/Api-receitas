<template>
  <div class="container mt-4">
    <h1 class="mb-4 text-center">🍽️ Receitas</h1>

    <div v-if="loading" class="text-center text-muted">Carregando receitas...</div>

    <div v-else class="row">
      <div
        v-for="receita in receitas"
        :key="receita.id"
        class="col-md-6 col-lg-4 mb-4"
      >
        <div
          class="card h-100 shadow-sm border-0"
          @click="goToRecipe(receita.id)"
          style="cursor: pointer;"
        >
          <div class="card-body">
            <h5 class="card-title fw-bold mb-2">{{ receita.titulo }}</h5>
            <p class="card-text text-muted">{{ receita.descricao }}</p>

            <hr />

            <h6 class="fw-semibold">🧂 Ingredientes:</h6>
            <ul class="list-unstyled ms-2">
              <li v-for="(ingrediente, i) in receita.ingredientes" :key="i">
                - {{ ingrediente }}
              </li>
            </ul>

            <h6 class="fw-semibold mt-3">👨‍🍳 Modo de Preparo:</h6>
            <ol class="ms-3">
              <li v-for="(passo, i) in receita.passos" :key="i">
                {{ passo }}
              </li>
            </ol>
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
    const response = await api.get("/receitas/all");
    receitas.value = response.data;
  } catch (error) {
    console.error(error);
  } finally {
    loading.value = false;
  }
});

function goToRecipe(id) {
  router.push(`/receita/${id}`);
}
</script>
