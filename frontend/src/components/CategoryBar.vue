<template>
  <div class="category-bar">
    <div class="category-inner">
      <router-link
        v-for="cat in categorias"
        :key="cat"
        :to="`/receitas?categoria=${encodeURIComponent(cat)}`"
        class="category-link"
        :class="{ active: activeCategoria === cat }"
      >
        {{ cat }}
      </router-link>
    </div>
  </div>
</template>

<script setup>
import { computed } from "vue";
import { useRoute } from "vue-router";

const route = useRoute();

const categorias = [
  "Todas",
  "Pratos Principais",
  "Sobremesas",
  "Massas",
  "Lanches",
  "Cafés",
];

const activeCategoria = computed(() => {
  const cat = route.query.categoria;
  return cat || "Todas";
});
</script>

<style scoped>
.category-bar {
  position: fixed;
  top: 64px;
  left: 0;
  right: 0;
  z-index: 999;
  background-color: var(--bg-warm, #FFF3E0);
  border-bottom: 1px solid rgba(0, 0, 0, 0.06);
  padding: 0;
}

.category-inner {
  max-width: 1100px;
  margin: 0 auto;
  display: flex;
  justify-content: center;
  gap: 0.25rem;
  padding: 0.5rem 1rem;
  overflow-x: auto;
  scrollbar-width: none;
}

.category-inner::-webkit-scrollbar {
  display: none;
}

.category-link {
  padding: 0.35rem 1rem;
  border-radius: 20px;
  font-size: 0.88rem;
  font-weight: 500;
  color: var(--text-dark);
  text-decoration: none;
  white-space: nowrap;
  transition: all 0.2s;
}

.category-link:hover {
  background-color: rgba(101, 31, 31, 0.08);
  color: var(--primary);
}

.category-link.active {
  background-color: var(--primary);
  color: #fff;
}
</style>
