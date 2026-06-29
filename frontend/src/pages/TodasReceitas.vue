<template>
  <div class="container mt-4">
    <h2 class="mb-4 text-center">Todas as Receitas</h2>

    <div v-if="loading" class="text-center text-muted">Carregando receitas...</div>

    <div v-else class="d-flex justify-content-center flex-wrap gap-4">
      <div
        v-for="receita in receitas"
        :key="receita.id"
        class="card receita-card"
      >
        <div class="card-body">
          <h5 class="card-title fw-bold">{{ receita.titulo }}</h5>
          <p class="card-text text-muted">{{ receita.descricao }}</p>
          <p class="card-text text-secondary small">Criado por: {{ receita.usuarioNome }}</p>
          <hr />

          <h6 class="fw-semibold"> <img src="../assets/receita.png" alt="">Ingredientes:</h6>
          <ul class="list-unstyled ms-2">
            <li v-for="(ingrediente, i) in receita.ingredientes" :key="i">
              - {{ ingrediente }}
            </li>
          </ul>

          <h6 class="fw-semibold mt-3"><img src="../assets/chef.png" alt=""> Modo de Preparo:</h6>
          <ol class="ms-3">
            <li v-for="(passo, i) in receita.passos" :key="i">
              {{ passo }}
            </li>
          </ol>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import api from "../services/api";

const receitas = ref([]);
const loading = ref(true);

onMounted(async () => {
  try {
    const response = await api.get("/receitas/all");
    receitas.value = response.data.map(r => ({
      ...r,
      ingredientes: Array.isArray(r.ingredientes) ? r.ingredientes : [],
      passos: Array.isArray(r.passos) ? r.passos : []
    }));
  } catch (error) {
    console.error(error);
  } finally {
    loading.value = false;
  }
});
</script>

<style scoped>
.receita-card {
  width: 300px;
  min-width: 280px;
  cursor: pointer;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
}

.receita-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
}

ul, ol {
  padding-left: 1rem;
  margin: 0.5rem 0;
  list-style-position: inside;
  white-space: normal;
}

li {
  white-space: normal;
}
</style>
