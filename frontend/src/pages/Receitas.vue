<template>
  <div class="container mt-4">
    <h1 class="mb-4 text-center">🍽️ Minhas Receitas</h1>

    <div v-if="loading" class="text-center text-muted">Carregando receitas...</div>

    <div v-else-if="receitas.length === 0" class="text-center text-muted">
      <p>Você ainda não criou nenhuma receita.</p>
    </div>

    <div v-else class="d-flex justify-content-center flex-wrap gap-4">
      <div
        v-for="receita in receitas"
        :key="receita.id"
        class="card receita-card"
      >
        <div class="card-body">
          <h5 class="card-title fw-bold">{{ receita.titulo }}</h5>
          <p class="card-text text-muted">{{ receita.descricao }}</p>
          <p class="card-text text-secondary small">
            Criado em: {{ formatarData(receita.createdAt) }}
          </p>
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

          <div class="mt-3 d-flex justify-content-between">
            <router-link
              :to="`/editar-receita/${receita.id}`"
              class="btn btn-primary btn-sm"
            >
              ✏️ Editar
            </router-link>
            <button
              @click="deletarReceita(receita.id)"
              class="btn btn-danger btn-sm"
            >
              🗑️ Excluir
            </button>
          </div>
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

function formatarData(data) {
  return new Date(data).toLocaleDateString("pt-BR");
}

async function carregarReceitas() {
  try {
    const token = localStorage.getItem("token");
    const res = await api.get("/receitas/me", {
      headers: { Authorization: `Bearer ${token}` },
    });
    receitas.value = res.data;
  } catch (err) {
    console.error("Erro ao carregar receitas:", err);
  } finally {
    loading.value = false;
  }
}

async function deletarReceita(id) {
  if (!confirm("Tem certeza que deseja excluir esta receita?")) return;
  try {
    const token = localStorage.getItem("token");
    await api.delete(`/receitas/${id}`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    receitas.value = receitas.value.filter((r) => r.id !== id);
  } catch (err) {
    console.error("Erro ao deletar receita:", err);
  }
}

onMounted(carregarReceitas);
</script>

<style scoped>
.receita-card {
  width: 380px;
  background: #ffffff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  transition: transform 0.2s;
}
.receita-card:hover {
  transform: translateY(-4px);
}
.btn {
  border-radius: 8px;
}
</style>
