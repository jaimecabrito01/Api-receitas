<template>
  <div class="container mt-4">
    <h2 class="mb-4 text-center">{{ paginaTitulo }}</h2>

    <div v-if="loading" class="text-center text-muted">Carregando receitas...</div>

    <div v-else-if="receitas.length === 0" class="text-center text-muted">
      Nenhuma receita encontrada.
    </div>

    <div v-else class="d-flex justify-content-center flex-wrap gap-4">
      <div
        v-for="receita in receitas"
        :key="receita.id"
        class="card receita-card"
      >
        <div
          v-if="receita.imagem"
          class="card-img-top receita-img"
          :style="{ backgroundImage: `url(${receita.imagem})` }"
        ></div>
        <div class="card-body">
          <span v-if="receita.categoria" class="categoria-badge">{{ receita.categoria }}</span>
          <h5 class="card-title fw-bold mt-1">{{ receita.titulo }}</h5>
          <p class="card-text text-muted">{{ receita.descricao }}</p>
          <p class="card-text text-secondary small">Criado por: {{ receita.usuarioNome }}</p>
          <hr />

          <h6 class="fw-semibold">Ingredientes:</h6>
          <ul class="list-unstyled ms-2">
            <li v-for="(ingrediente, i) in receita.ingredientes" :key="i">
              - {{ ingrediente }}
            </li>
          </ul>

          <h6 class="fw-semibold mt-3">Modo de Preparo:</h6>
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
import { ref, onMounted, computed } from "vue";
import { useRoute } from "vue-router";
import api from "../services/api";

const route = useRoute();
const receitas = ref([]);
const loading = ref(true);

const paginaTitulo = computed(() => {
  const cat = route.query.categoria;
  return cat ? `Receitas - ${cat}` : "Todas as Receitas";
});

onMounted(async () => {
  try {
    const params = {};
    const cat = route.query.categoria;
    if (cat && cat !== "Todas") {
      params.categoria = cat;
    }
    const response = await api.get("/receitas/all", { params });
    receitas.value = (response.data || []).map(r => ({
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
  width: 320px;
  min-width: 280px;
  cursor: pointer;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s ease, box-shadow 0.2s ease;
  overflow: hidden;
}

.receita-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
}

.receita-img {
  height: 180px;
  background-size: cover;
  background-position: center;
  background-color: #e0d6cc;
}

.categoria-badge {
  display: inline-block;
  background-color: var(--bg-warm, #FFF3E0);
  color: var(--primary, #651F1F);
  font-size: 0.75rem;
  font-weight: 600;
  padding: 0.15rem 0.7rem;
  border-radius: 10px;
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
