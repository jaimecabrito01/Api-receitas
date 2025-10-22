<template>
  <Navbar />
  <router-view />
</template>

<script setup>
import Navbar from "./components/Navbar.vue";
</script>

<style>
body {
  margin: 0;
  font-family: system-ui, sans-serif;
  background: #f6f6f6;
}
</style>
