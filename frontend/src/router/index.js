import { createRouter, createWebHistory } from "vue-router";
import Home from "../pages/Home.vue";
import CriarReceita from "../pages/CriarReceita.vue";
import Register from "../pages/Register.vue";
import Login from "../pages/Login.vue";

const routes = [
  { path: "/", name: "Home", component: Home },
  { path: "/create", name: "CriarReceita", component: CriarReceita, meta: {requiresAuth:true} },
  { path: "/register", name:"Registrar", component:Register},
  { path: "/login", name: "login", component:Login },
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
