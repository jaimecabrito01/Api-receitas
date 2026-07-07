<template>
  <div class="container py-5 text-center">
    <div v-if="loading" class="text-muted">Carregando...</div>
    <div v-else>
      <h2 class="fw-bold">{{ receita.titulo }}</h2>
      <p class="text-muted">{{ receita.descricao }}</p>
      <div v-if="receita.imagem" class="my-4">
        <img :src="receita.imagem" alt="Imagem da receita" class="img-fluid rounded shadow" style="max-height: 400px; object-fit: cover;" />
      </div>
      <h5 class="fw-semibold mt-4">Ingredientes:</h5>
      <ul class="list-unstyled">
        <li v-for="(ing, i) in receita.ingredientes" :key="i">{{ ing }}</li>
      </ul>
      <h5 class="fw-semibold mt-4">Modo de Preparo:</h5>
      <ol class="text-start mx-auto" style="max-width: 600px;">
        <li v-for="(passo, i) in receita.passos" :key="i">{{ passo }}</li>
      </ol>
      <router-link to="/receitas" class="btn btn-primary mt-4">Voltar</router-link>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import api from "../services/api";

const route = useRoute();
const router = useRouter();
const receita = ref({});
const loading = ref(true);

onMounted(async () => {
  try {
    const res = await api.get(`/receitas/${route.params.id}`);
    receita.value = res.data;
  } catch {
    router.push("/receitas");
  } finally {
    loading.value = false;
  }
});
</script>
