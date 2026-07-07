<template>
  <div class="home-page">
    <section class="hero">
      <div class="hero-overlay"></div>
      <div class="hero-content">
        <h1>Compartilhe suas Receitas</h1>
        <p class="hero-subtitle">
          Descubra, crie e compartilhe receitas incríveis com a comunidade.
          Seu caderno de receitas digital, sempre acessível.
        </p>
        <router-link to="/receitas" class="btn btn-primary btn-lg hero-btn">
          Ver Receitas
        </router-link>
      </div>
    </section>

    <section class="destaque" v-if="receitas.length > 0">
      <h2 class="section-title">Destaque do Dia</h2>

      <div class="carrossel-wrapper">
        <div
          class="carrossel-track"
          :style="{ transform: `translateX(-${currentIndex * 100}%)` }"
        >
          <div
            v-for="receita in receitas"
            :key="receita.id"
            class="carrossel-slide"
          >
            <div
              class="slide-img"
              :style="{ backgroundImage: `url(${receita.imagem || ''})` }"
            ></div>
            <div class="slide-body">
              <span class="slide-categoria">{{ receita.categoria || "Geral" }}</span>
              <h3 class="slide-titulo">{{ receita.titulo }}</h3>
              <p class="slide-desc">{{ receita.descricao }}</p>
              <router-link
                :to="`/receitas/${receita.id}`"
                class="slide-btn"
              >
                Ver receita &rarr;
              </router-link>
            </div>
          </div>
        </div>

        <button class="carrossel-btn carrossel-prev" @click="prevSlide" aria-label="Anterior">&#10094;</button>
        <button class="carrossel-btn carrossel-next" @click="nextSlide" aria-label="Próximo">&#10095;</button>

        <div class="carrossel-dots">
          <span
            v-for="(_, i) in receitas"
            :key="i"
            class="dot"
            :class="{ active: i === currentIndex }"
            @click="currentIndex = i"
          ></span>
        </div>
      </div>
    </section>

    <section class="features container">
      <h2 class="text-center mb-5">Como funciona</h2>
      <div class="row g-4">
        <div class="col-md-4">
          <div class="feature-card">
            <img src="../assets/receitas.png" alt="Explorar" class="feature-icon" />
            <h5>Explore Receitas</h5>
            <p>Navegue por diversas receitas criadas pela comunidade. Encontre inspiração para o seu dia a dia.</p>
          </div>
        </div>
        <div class="col-md-4">
          <div class="feature-card">
            <img src="../assets/add2.png" alt="Compartilhar" class="feature-icon" />
            <h5>Compartilhe suas Criações</h5>
            <p>Crie sua conta e publique suas próprias receitas com ingredientes e modo de preparo detalhados.</p>
          </div>
        </div>
        <div class="col-md-4">
          <div class="feature-card">
            <img src="../assets/account.png" alt="Organizar" class="feature-icon" />
            <h5>Organize suas Receitas</h5>
            <p>Acesse suas receitas a qualquer momento, edite ou remova conforme necessário.</p>
          </div>
        </div>
      </div>
    </section>

    <section class="inspirar" v-if="inspirar.length > 0">
      <h2 class="inspirar-title">Receitinhas para se inspirar</h2>
      <div class="inspirar-grid">
        <div
          v-for="(r, i) in inspirar"
          :key="r.id"
          class="inspirar-card"
          :class="i % 2 === 0 ? 'card-right' : 'card-left'"
        >
          <div
            class="inspirar-img"
            :style="{ backgroundImage: `url(${r.imagem || ''})` }"
          ></div>
          <div class="inspirar-body">
            <h4 class="inspirar-nome">{{ r.titulo }}</h4>
            <span class="inspirar-autor">por {{ r.usuarioNome }}</span>
          </div>
        </div>
      </div>
    </section>
  </div>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from "vue";
import api from "../services/api";

const receitas = ref([]);
const inspirar = ref([]);
const currentIndex = ref(0);
let intervalId = null;

onMounted(async () => {
  try {
    const response = await api.get("/receitas/all");
    const todas = Array.isArray(response.data) ? response.data : [];
    todas.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
    receitas.value = todas.slice(0, 6);
    inspirar.value = todas.slice(0, 3);
  } catch (error) {
    console.error(error);
  }

  intervalId = setInterval(nextSlide, 5000);
});

onUnmounted(() => {
  if (intervalId) clearInterval(intervalId);
});

function nextSlide() {
  if (receitas.value.length === 0) return;
  currentIndex.value = (currentIndex.value + 1) % receitas.value.length;
}

function prevSlide() {
  if (receitas.value.length === 0) return;
  currentIndex.value = (currentIndex.value - 1 + receitas.value.length) % receitas.value.length;
}
</script>

<style scoped>
.home-page { padding-top: 0; }

/* ── Hero ── */
.hero {
  position: relative;
  height: 75vh;
  min-height: 420px;
  display: flex;
  align-items: center;
  justify-content: center;
  background: url("https://images.unsplash.com/photo-1504674900247-0877df9cc836?w=1600&q=80") center / cover no-repeat;
  color: white;
  text-align: center;
  margin-top: -115px;
}
.hero-overlay {
  position: absolute;
  inset: 0;
  background: linear-gradient(135deg, rgba(101, 31, 31, 0.85) 0%, rgba(0, 0, 0, 0.6) 100%);
}
.hero-content {
  position: relative;
  z-index: 1;
  max-width: 700px;
  padding: 0 1rem;
}
.hero h1 { font-size: 2.8rem; font-weight: 800; margin-bottom: 1rem; }
.hero-subtitle {
  font-size: 1.2rem;
  color: #f0e6dc;
  margin-bottom: 2rem;
  line-height: 1.6;
}
.hero-btn {
  padding: 0.75rem 2rem;
  font-size: 1.1rem;
  border-radius: 8px;
}

/* ── Destaque / Carrossel ── */
.section-title {
  text-align: center;
  font-size: 1.8rem;
  font-weight: 700;
  margin-bottom: 2rem;
  color: var(--text-dark);
}
.destaque { padding: 3rem 1rem 2rem; max-width: 900px; margin: 0 auto; }
.carrossel-wrapper {
  position: relative;
  overflow: hidden;
  border-radius: 16px;
  box-shadow: 0 4px 20px rgba(0,0,0,.12);
  background: #fff;
}
.carrossel-track { display: flex; transition: transform .5s ease; }
.carrossel-slide { min-width: 100%; display: flex; flex-direction: column; }
.slide-img { width: 100%; height: 260px; background-size: cover; background-position: center; background-color: #e0d6cc; }
.slide-body { padding: 1.5rem; text-align: center; }
.slide-categoria {
  display: inline-block;
  background-color: var(--bg-warm);
  color: var(--primary);
  font-size: .78rem;
  font-weight: 600;
  padding: .2rem .8rem;
  border-radius: 12px;
  margin-bottom: .5rem;
}
.slide-titulo { font-size: 1.3rem; font-weight: 700; margin-bottom: .4rem; color: var(--text-dark); }
.slide-desc { color: var(--text-muted); font-size: .95rem; margin-bottom: 1rem; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; }
.slide-btn { display: inline-block; background: none; border: none; color: var(--primary); font-weight: 600; font-size: .95rem; cursor: pointer; text-decoration: none; }
.slide-btn:hover { color: var(--primary-dark); }

.carrossel-btn {
  position: absolute; top: 50%; transform: translateY(-50%);
  background: rgba(255,255,255,.85); border: none; font-size: 1.4rem;
  width: 38px; height: 38px; border-radius: 50%; cursor: pointer; z-index: 2;
  box-shadow: 0 2px 6px rgba(0,0,0,.15); color: var(--text-dark);
  display: flex; align-items: center; justify-content: center;
}
.carrossel-prev { left: 12px; }
.carrossel-next { right: 12px; }
.carrossel-dots { display: flex; justify-content: center; gap: .5rem; padding: 1rem 0 1.2rem; }
.dot { width: 10px; height: 10px; border-radius: 50%; background-color: #d0d0d0; cursor: pointer; }
.dot.active { background-color: var(--primary); }

/* ── Como funciona ── */
.features { padding: 3rem 1rem 4rem; }
.features h2 { font-weight: 700; color: var(--text-dark); }
.feature-card {
  background: #fff; border-radius: 12px; padding: 2rem; text-align: center;
  box-shadow: 0 2px 8px rgba(0,0,0,.08); transition: transform .2s ease; height: 100%;
}
.feature-card:hover { transform: translateY(-4px); }
.feature-icon { width: 48px; height: 48px; margin-bottom: 1rem; }
.feature-card h5 { font-weight: 600; margin-bottom: .75rem; color: var(--text-dark); }
.feature-card p { color: var(--text-muted); font-size: .95rem; }

/* ── Receitinhas para se inspirar ── */
.inspirar {
  background: #E63946;
  padding: 3rem 1rem;
}
.inspirar-title {
  text-align: center;
  font-size: 1.8rem;
  font-weight: 700;
  color: #fff;
  margin-bottom: 2.5rem;
}
.inspirar-grid {
  max-width: 800px;
  margin: 0 auto;
  display: flex;
  flex-direction: column;
  gap: 1.5rem;
}
.inspirar-card {
  display: flex;
  align-items: center;
  gap: 1rem;
  background: #fff;
  border-radius: 14px;
  overflow: hidden;
  box-shadow: 0 3px 12px rgba(0,0,0,.15);
  max-width: 480px;
  transition: transform .2s;
}
.inspirar-card:hover { transform: translateY(-3px); }

.card-right { align-self: flex-end; }
.card-left { align-self: flex-start; }

.inspirar-img {
  width: 130px;
  min-height: 100px;
  background-size: cover;
  background-position: center;
  background-color: #e0d6cc;
  flex-shrink: 0;
}
.inspirar-body { padding: .8rem .8rem .8rem 0; }
.inspirar-nome { font-size: 1.05rem; font-weight: 700; color: var(--text-dark); margin: 0 0 .2rem; }
.inspirar-autor { font-size: .82rem; color: var(--text-muted); }

@media (max-width: 600px) {
  .inspirar-card { max-width: 100%; align-self: center !important; width: 100%; }
  .inspirar-img { width: 100px; min-height: 80px; }
}
</style>
