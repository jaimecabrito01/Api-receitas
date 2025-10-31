<template>
  <nav class="navbar">
    <!-- Logo -->
    <router-link to="/" class="navbar-logo">
      🍳 ReceitasApp
    </router-link>

    
    <div v-if="!isLoggedIn" class="navbar-links">
      <router-link to="/login" class="nav-item">
        <img src="../assets/login.png" alt="Login" class="icon" />
        Login
      </router-link>

      <router-link to="/register" class="nav-item">
        <img src="../assets/account.png" alt="Cadastrar" class="icon" />
        Cadastrar
      </router-link>
    </div>

    <!-- Menu quando LOGADO -->
    <div v-else class="navbar-user" @click="toggleMenu">
      <button class="user-btn">
        <img src="../assets/account.png" alt="Usuário" class="icon-lg" />
        <span>{{ nome }}</span>
        <svg xmlns="http://www.w3.org/2000/svg" class="arrow" fill="none"
             viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M19 9l-7 7-7-7" />
        </svg>
      </button>

      <div v-if="menuOpen" class="dropdown">
        <router-link to="/minhas-receitas" class="dropdown-item">
          <img src="../assets/account.png" alt="Minhas Receitas" class="icon-sm" />
          Minhas Receitas
        </router-link>

        <router-link to="/editar-perfil" class="dropdown-item">
          <img src="../assets/account.png" alt="Editar Perfil" class="icon-sm" />
          Editar Perfil
        </router-link>
        <router-link to="/criar-receita" class="dropdown-item">
          <img src="../assets/account.png" alt="Criar receita" class="icon-sm" />
          Postar receita
        </router-link>

        <button @click.stop="logout" class="dropdown-item logout">
          <img src="../assets/logout.png" alt="Logout" class="icon-sm" />
          Logout
        </button>
      </div>
    </div>
  </nav>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import api from "../services/api"

const router = useRouter();
const menuOpen = ref(false);
const isLoggedIn = ref(false);
const nome = ref("");

function toggleMenu() {
  menuOpen.value = !menuOpen.value;
}

function logout() {
  localStorage.removeItem("token");
  router.push("/login");
  window.location.reload();
}

onMounted(async () => {
  const token = localStorage.getItem("token");

  if (token) {
    isLoggedIn.value = true;
    try {
      const response = await api.get("/user/me", {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      nome.value = response.data.nome;
    } catch (error) {
      console.error("Erro ao buscar dados do usuário:", error);
      nome.value = "Usuário";
    }
  }
});

window.addEventListener("click", (e) => {
  const dropdown = document.querySelector(".navbar-user");
  if (dropdown && !dropdown.contains(e.target)) {
    menuOpen.value = false;
  }
});
</script>

<style scoped>
.navbar {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1000;
  background-color: #111827;
  color: white;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.6rem 1.5rem;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.3);
}

.navbar-logo {
  font-size: 1.5rem;
  font-weight: bold;
  color: white;
  text-decoration: none;
}

.navbar-links {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.nav-item {
  display: flex;
  align-items: center;
  color: #e5e7eb;
  text-decoration: none;
  font-weight: 500;
  transition: color 0.2s;
}

.nav-item:hover {
  color: #a5b4fc;
}

.icon {
  width: 18px;
  height: 18px;
  margin-right: 6px;
}

.icon-lg {
  width: 24px;
  height: 24px;
}

.icon-sm {
  width: 16px;
  height: 16px;
  margin-right: 6px;
}

.navbar-user {
  position: relative;
}

.user-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  background: none;
  border: none;
  color: white;
  cursor: pointer;
  font-weight: 500;
  padding: 6px 10px;
  border-radius: 8px;
  transition: background 0.2s;
}

.user-btn:hover {
  background-color: #1f2937;
}

.arrow {
  width: 14px;
  height: 14px;
}

.dropdown {
  position: absolute;
  right: 0;
  top: 110%;
  background-color: #1f2937;
  border-radius: 8px;
  min-width: 180px;
  overflow: hidden;
  border: 1px solid #374151;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
}

.dropdown-item {
  display: flex;
  align-items: center;
  padding: 0.6rem 1rem;
  color: #e5e7eb;
  text-decoration: none;
  font-size: 0.95rem;
  transition: background 0.2s;
  width: 100%;
}

.dropdown-item:hover {
  background-color: #374151;
}

.logout {
  color: #f87171;
}

.logout:hover {
  background-color: #7f1d1d;
  color: white;
}
</style>
