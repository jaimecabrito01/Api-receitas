<template>
  <div class="container mt-4">
    <h1 class="mb-4">Criar Receita</h1>
    <form @submit.prevent="submitForm">
      <div class="mb-3">
        <label class="form-label">Título:</label>
        <input v-model="titulo" class="form-control" required />
      </div>
      <div class="mb-3">
        <label class="form-label">Descrição:</label>
        <textarea v-model="descricao" class="form-control" rows="4" required></textarea>
      </div>
      <button type="submit" class="btn btn-primary">Criar</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import api from "../services/api";

const titulo = ref("");
const descricao = ref("");

async function submitForm() {
  try {
    const payload = { titulo, descricao, userId: 1 };
    await api.post("/receitas/create", payload);
    alert("Receita criada com sucesso!");
    titulo.value = "";
    descricao.value = "";
  } catch (error) {
    console.error("Erro ao criar receita:", error);
  }
}
</script>
