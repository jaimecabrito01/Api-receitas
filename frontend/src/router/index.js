import { createRouter, createWebHistory } from "vue-router";
import Home from "../pages/Home.vue";
import TodasReceitas from "../pages/TodasReceitas.vue";
import CriarReceita from "../pages/CriarReceita.vue";
import Register from "../pages/Register.vue";
import Login from "../pages/Login.vue";
import EditarPerfil from "../pages/EditarPerfil.vue";
import Receitas from "../pages/Receitas.vue";
import EditarReceita from "../pages/EditarReceita.vue";
import ReceitaDetalhe from "../pages/ReceitaDetalhe.vue";

const routes = [
  { path: "/", name: "Home", component: Home },
  { path: "/receitas", name: "TodasReceitas", component: TodasReceitas },
  { path: "/receitas/:id", name: "ReceitaDetalhe", component: ReceitaDetalhe },
  { path: "/criar-receita", name: "CriarReceita", component: CriarReceita, meta: {requiresAuth:true} },
  { path: "/register", name:"Registrar", component:Register},
  { path: "/login", name: "login", component:Login },
  {path: "/editar-perfil",name:"editar-perfil",component: EditarPerfil,meta: {requiresAuth:true}},
  {path:"/editar-receita/:id",name:"editar-receita",component:EditarReceita,meta:{requiresAuth:true}},
  {path: "/minhas-receitas",name:"minhas-receitas",component: Receitas, meta:{requiresAuth:true}}
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const token = localStorage.getItem("token");
 if (token && (to.path === '/login' || to.path === '/register')) {
    next('/')
    return
  }
  if (to.meta.requiresAuth && !token) {

    next("/login");
  } else {
    next();
  }
});

export default router;
