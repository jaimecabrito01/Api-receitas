import { createRouter, createWebHistory } from "vue-router";
import Home from "../pages/Home.vue";
import CriarReceita from "../pages/CriarReceita.vue";

const routes = [
  { path: "/", name: "Home", component: Home },
  { path: "/create", name: "CriarReceita", component: CriarReceita },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;
