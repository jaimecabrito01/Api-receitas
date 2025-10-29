<template>
  <div class="container mt-4">
    <h1 class="mb-4 text-center">Criar Receita</h1>

    <form @submit.prevent="criarReceita" class="card shadow-sm p-4">
     
      <div class="mb-3">
        <label class="form-label fw-bold">Título:</label>
        <input
          v-model="titulo"
          class="form-control"
          placeholder="Digite o nome da receita"
          required
        />
      </div>

    
      <div class="mb-3">
        <label class="form-label fw-bold">Descrição:</label>
        <textarea
          v-model="descricao"
          class="form-control"
          rows="3"
          placeholder="Fale brevemente sobre a receita"
          required
        ></textarea>
      </div>

      
      <div class="mb-3">
        <label class="form-label fw-bold">Ingredientes:</label>

        <div v-for="(ingrediente, index) in ingredientes" :key="index" class="input-group mb-2">
          <input
            v-model="ingredientes[index]"
            type="text"
            class="form-control"
            placeholder="Ex: 2 ovos, 1 xícara de farinha..."
            required
          />
          <button
            type="button"
            class="btn btn-outline-danger"
            @click="removerIngrediente(index)"
          >
            Remover
          </button>
        </div>

        <button type="button" class="btn btn-outline-primary" @click="adicionarIngrediente">
          + Adicionar ingrediente
        </button>
      </div>

    
      <div class="mb-3">
        <label class="form-label fw-bold">Passos:</label>

        <div v-for="(passo, index) in passos" :key="index" class="input-group mb-2">
          <textarea
            v-model="passos[index]"
            class="form-control"
            rows="2"
            placeholder="Descreva o passo"
            required
          ></textarea>
          <button
            type="button"
            class="btn btn-outline-danger"
            @click="removerPasso(index)"
          >
            Remover
          </button>
        </div>

        <button type="button" class="btn btn-outline-primary" @click="adicionarPasso">
          + Adicionar passo
        </button>
      </div>

     
      <button type="submit" class="btn btn-success w-100 mt-3">
        Criar Receita
      </button>
    </form>
  </div>
</template>

<script setup>
import { ref } from "vue";
import api from "../services/api";
const titulo = ref("");
const descricao = ref("");
const ingredientes = ref([""]);
const passos = ref([""]);
const userId = ref([""])

function adicionarIngrediente() {
  ingredientes.value.push("");
}

function removerIngrediente(index) {
  ingredientes.value.splice(index, 1);
}

function adicionarPasso() {
  passos.value.push("");
}

function removerPasso(index) {
  passos.value.splice(index, 1);
}



async function criarReceita() {
  const payload = {
    titulo: titulo.value,
    descricao: descricao.value,
    ingredientes: ingredientes.value,
    passos: passos.value,
    
  };

  
  try {
    await api.post("/receitas/add", payload, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    });

    alert("Receita criada com sucesso!");
    titulo.value = "";
    descricao.value = "";
    ingredientes.value = [""];
    passos.value = [""];
  } catch (error) {
    console.error("Erro ao criar receita:", error);
    alert("Erro ao criar receita!");
  }
}




</script>
