<template>
  <nav class="navbar">
    <router-link to="/" class="navbar-logo">
      <img src="../assets/logo.png" alt="">
    </router-link>

    <div class="navbar-center">
      <router-link to="/receitas" class="nav-item">
        <img src="../assets/receita.png" alt="Receitas" class="icon" />
        Receitas
      </router-link>
      <router-link v-if="isLoggedIn" to="/criar-receita" class="nav-item nav-create">
        <img src="../assets/add2.png" alt="Criar" class="icon" />
        Criar Receita
      </router-link>
    </div>

    <div v-if="!isLoggedIn" class="navbar-links">
      <router-link to="/login" class="nav-item">
        <img src="../assets/login.png" alt="Login" class="icon" />
        Entrar
      </router-link>
      <router-link to="/register" class="nav-item">
        <img src="../assets/account.png" alt="Cadastrar" class="icon" />
        Cadastrar
      </router-link>
    </div>

    <div v-else class="navbar-user" @click="toggleSidebar">
      <button class="user-btn">
        <img src="../assets/account.png" alt="Usuário" class="icon-lg" />
        <span>{{ nome }}</span>
        <svg xmlns="http://www.w3.org/2000/svg" class="arrow" fill="none"
             viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                d="M4 6h16M4 12h16M4 18h16" />
        </svg>
      </button>
    </div>
  </nav>

  <Transition name="sidebar">
    <div v-if="sidebarOpen" class="sidebar-overlay" @click="sidebarOpen = false"></div>
  </Transition>

  <Transition name="sidebar-panel">
    <aside v-if="sidebarOpen" class="sidebar">
      <div class="sidebar-header">
        <img src="../assets/account-circle.png" alt="" class="sidebar-avatar" />
        <span class="sidebar-nome">{{ nome }}</span>
      </div>

      <div class="sidebar-links">
        <router-link to="/minhas-receitas" class="sidebar-item" @click="sidebarOpen = false">
          <img src="../assets/menu.png" alt="" class="icon-sm" />
          Minhas Receitas
        </router-link>
        <router-link to="/editar-perfil" class="sidebar-item" @click="sidebarOpen = false">
          <img src="../assets/edit.png" alt="" class="icon-sm" />
          Editar Perfil
        </router-link>
      </div>

      <div class="sidebar-footer">
        <button @click="logout" class="sidebar-item sidebar-logout">
          <img src="../assets/logout.png" alt="" class="icon-sm" />
          Logout
        </button>
      </div>
    </aside>
  </Transition>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import api from "../services/api"

const router = useRouter();
const sidebarOpen = ref(false);
const isLoggedIn = ref(false);
const nome = ref("");

function toggleSidebar() {
  sidebarOpen.value = !sidebarOpen.value;
}

function logout() {
  localStorage.removeItem("token");
  sidebarOpen.value = false;
  router.push("/login");
  window.location.reload();
}

onMounted(async () => {
  const token = localStorage.getItem("token");

  if (token) {
    isLoggedIn.value = true;
    try {
      const response = await api.get("/user/me", {
        headers: { Authorization: `Bearer ${token}` },
      });
      nome.value = response.data.nome;
    } catch (error) {
      console.error("Erro ao buscar dados do usuário:", error);
      nome.value = "Usuário";
    }
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
  background-color: var(--bg-warm, #FFF3E0);
  color: var(--text-dark, #1a1a1a);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0.6rem 1.5rem;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.1);
}

.navbar-logo {
  font-size: 1.5rem;
  font-weight: bold;
  color: var(--text-dark);
  text-decoration: none;
}

.navbar-center {
  display: flex;
  align-items: center;
  gap: 1.5rem;
}

.navbar-links {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.nav-item {
  display: flex;
  align-items: center;
  color: var(--text-dark);
  text-decoration: none;
  font-weight: 500;
  transition: color 0.2s;
}

.nav-item:hover { color: var(--primary); }

.nav-create {
  background-color: var(--primary);
  color: #fff !important;
  padding: 0.4rem 0.9rem;
  border-radius: 8px;
  transition: background-color 0.2s;
}

.nav-create:hover {
  background-color: var(--primary-dark);
  color: #fff !important;
}

.icon { width: 18px; height: 18px; margin-right: 6px; }
.icon-lg { width: 24px; height: 24px; }
.icon-sm { width: 18px; height: 18px; margin-right: 8px; }

.navbar-user { position: relative; }

.user-btn {
  display: flex;
  align-items: center;
  gap: 6px;
  background: none;
  border: none;
  color: var(--text-dark);
  cursor: pointer;
  font-weight: 500;
  padding: 6px 10px;
  border-radius: 8px;
  transition: background 0.2s;
}

.user-btn:hover { background-color: rgba(101, 31, 31, 0.08); }

.arrow { width: 18px; height: 18px; }

.navbar-logo img {
  height: 52px;
  width: auto;
  object-fit: contain;
  display: block;
}

/* ── Overlay ── */
.sidebar-overlay {
  position: fixed;
  inset: 0;
  z-index: 1001;
  background: rgba(0, 0, 0, 0.4);
}

/* ── Sidebar ── */
.sidebar {
  position: fixed;
  top: 0;
  right: 0;
  z-index: 1002;
  width: 270px;
  height: 100vh;
  background: #fff;
  box-shadow: -4px 0 16px rgba(0, 0, 0, 0.12);
  display: flex;
  flex-direction: column;
  padding-top: 1.5rem;
}

.sidebar-header {
  display: flex;
  align-items: center;
  gap: 0.7rem;
  padding: 0 1.5rem 1.2rem;
  border-bottom: 1px solid #eee;
}

.sidebar-avatar {
  width: 36px;
  height: 36px;
}

.sidebar-nome {
  font-weight: 600;
  font-size: 1rem;
  color: var(--text-dark);
}

.sidebar-links {
  flex: 1;
  display: flex;
  flex-direction: column;
  padding: 0.8rem 0;
}

.sidebar-item {
  display: flex;
  align-items: center;
  padding: 0.75rem 1.5rem;
  color: var(--text-dark);
  text-decoration: none;
  font-size: 0.95rem;
  font-weight: 500;
  transition: background 0.15s;
  border: none;
  background: none;
  cursor: pointer;
  text-align: left;
  width: 100%;
}

.sidebar-item:hover { background-color: #f5f0eb; }

.sidebar-footer {
  border-top: 1px solid #eee;
  padding: 0.5rem 0;
}

.sidebar-logout { color: var(--accent); }
.sidebar-logout:hover { background-color: #fde8e8; }

/* ── Transições ── */
.sidebar-enter-active, .sidebar-leave-active { transition: opacity 0.25s; }
.sidebar-enter-from, .sidebar-leave-to { opacity: 0; }

.sidebar-panel-enter-active { transition: transform 0.3s ease; }
.sidebar-panel-leave-active { transition: transform 0.2s ease; }
.sidebar-panel-enter-from, .sidebar-panel-leave-to { transform: translateX(100%); }
</style>
