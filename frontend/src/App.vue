<template>
  <Navbar />
  <CategoryBar />
  <div class="page-content">
    <router-view />
  </div>
</template>

<script setup>
import Navbar from "./components/NavBar.vue";
import CategoryBar from "./components/CategoryBar.vue";
</script>

<style>
body {
  margin: 0;
  font-family: system-ui, sans-serif;
  background: #fafafa;
}

#app {
  max-width: 100%;
  margin: 0;
  padding: 0;
}

.page-content {
  padding-top: 115px;
}
</style>
