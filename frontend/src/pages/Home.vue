<template>
  <div class="home-page">
    <section class="hero">
      <div class="hero-content">
        <h1>Compartilhe suas Receitas</h1>
        <p class="hero-subtitle">
          Descubra, crie e compartilhe receitas incríveis com a comunidade.
          Seu caderno de receitas digital, sempre acessível.
        </p>
        <router-link to="/receitas" class="btn btn-primary btn-lg">
          Ver Receitas
        </router-link>
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
  </div>
</template>

<script setup>
</script>

<style scoped>
.home-page {
  padding-top: 70px;
}

.hero {
  background: linear-gradient(135deg, #111827 0%, #1e3a5f 100%);
  color: white;
  text-align: center;
  padding: 5rem 1rem;
}

.hero-content {
  max-width: 700px;
  margin: 0 auto;
}

.hero h1 {
  font-size: 2.8rem;
  font-weight: 800;
  margin-bottom: 1rem;
}

.hero-subtitle {
  font-size: 1.2rem;
  color: #cbd5e1;
  margin-bottom: 2rem;
  line-height: 1.6;
}

.hero .btn {
  padding: 0.75rem 2rem;
  font-size: 1.1rem;
  border-radius: 8px;
}

.features {
  padding: 4rem 1rem;
}

.features h2 {
  font-weight: 700;
  color: #111827;
}

.feature-card {
  background: #ffffff;
  border-radius: 12px;
  padding: 2rem;
  text-align: center;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
  transition: transform 0.2s ease;
  height: 100%;
}

.feature-card:hover {
  transform: translateY(-4px);
}

.feature-icon {
  width: 48px;
  height: 48px;
  margin-bottom: 1rem;
}

.feature-card h5 {
  font-weight: 600;
  margin-bottom: 0.75rem;
  color: #111827;
}

.feature-card p {
  color: #6b7280;
  font-size: 0.95rem;
}
</style>
