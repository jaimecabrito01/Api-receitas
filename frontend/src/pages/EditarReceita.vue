<template>
    <div class="container py-5">
        <h1 class="mb-5 text-center text-dark"> <img src="../assets/edit.png" alt="">Editar Receita</h1>

        
        <div v-if="statusMessage"
             :class="{'alert-success': !isError, 'alert-danger': isError}"
             class="alert alert-dismissible fade show mx-auto" style="max-width: 700px;" role="alert">
            <strong>{{ statusMessage }}</strong>
            <button type="button" class="btn-close" @click="statusMessage = null" aria-label="Close"></button>
        </div>

        
        <div v-if="loading" class="text-center text-muted py-5 mx-auto" style="max-width: 700px;">
            <div class="spinner-border text-primary" role="status">
                <span class="visually-hidden">Carregando...</span>
            </div>
            <p class="mt-2">Carregando dados da receita...</p>
        </div>
        
    
        <form v-else @submit.prevent="atualizarReceita" class="card shadow-lg p-4 mx-auto" style="max-width: 700px;">
            
            
            <div class="mb-3">
                <label class="form-label fw-bold">Título:</label>
                <input
                    v-model="receita.titulo"
                    type="text"
                    class="form-control"
                    placeholder="Digite o nome da receita"
                    required
                />
            </div>

            
            <div class="mb-3">
                <label class="form-label fw-bold">Descrição:</label>
                <textarea
                    v-model="receita.descricao"
                    class="form-control"
                    rows="3"
                    placeholder="Fale brevemente sobre a receita"
                    required
                ></textarea>
            </div>

            
            <div class="mb-4">
                <label class="form-label fw-bold">Ingredientes:</label>
                <div v-for="(ingrediente, index) in receita.ingredientes" :key="index" class="input-group mb-2">
                    <input
                        v-model="receita.ingredientes[index]"
                        type="text"
                        class="form-control"
                        placeholder="Ex: 2 ovos, 1 xícara de farinha..."
                        required
                    />
                    <button
                        type="button"
                        class="btn btn-outline-danger"
                        @click="removerIngrediente(index)"
                    >
                        Remover
                    </button>
                </div>
                <button 
                    type="button" 
                    class="btn btn-outline-primary btn-sm mt-2" 
                    @click="adicionarIngrediente"
                >
                    + Adicionar ingrediente
                </button>
            </div>

            
            <div class="mb-4">
                <label class="form-label fw-bold">Passos:</label>
                <div v-for="(passo, index) in receita.passos" :key="index" class="input-group mb-2">
                    <textarea
                        v-model="receita.passos[index]"
                        class="form-control"
                        rows="2"
                        placeholder="Descreva o passo (Ex: Misture os ingredientes secos...)"
                        required
                    ></textarea>
                    <button
                        type="button"
                        class="btn btn-outline-danger"
                        @click="removerPasso(index)"
                    >
                        Remover
                    </button>
                </div>
                <button 
                    type="button" 
                    class="btn btn-outline-primary btn-sm mt-2" 
                    @click="adicionarPasso"
                >
                    + Adicionar passo
                </button>
            </div>

            
            <div class="d-flex justify-content-between mt-4">
                <button type="submit" class="btn btn-success" :disabled="isSaving">
                    <span v-if="!isSaving"> <img src="../assets/save.png" alt="">Salvar Alterações</span>
                    <span v-else>Salvando...</span>
                </button>
                <button type="button" class="btn btn-secondary" @click="voltar">
                    <img src="../assets/cancel.png" alt="">Cancelar
                </button>
            </div>
            
        </form>
    </div>
</template>

<script setup>
    import { ref, onMounted } from 'vue';
    import { useRoute, useRouter } from 'vue-router';
    import api from '../services/api'; 
    
    const route = useRoute();
    const router = useRouter();
    
    const loading = ref(true);
    const isSaving = ref(false);
    const statusMessage = ref(null);
    const isError = ref(false);
    
    const receita = ref({
        titulo: "",
        descricao: "",
        ingredientes: [], 
        passos: []       
    });


    onMounted(async () => {
        const id = route.params.id; 
        const token = localStorage.getItem("token");
        
        try {
            statusMessage.value = null;
            
            const response = await api.get(`/receitas/${id}`, {
                 headers: { Authorization: `Bearer ${token}` }
            });
            
            let fetchedData = response.data;
            
            if (fetchedData && typeof fetchedData.present === 'boolean' && fetchedData.value) {
                fetchedData = fetchedData.value;
            }
            
            if (fetchedData) {
                receita.value = fetchedData; 
                
                if (!receita.value.ingredientes) {
                    receita.value.ingredientes = [];
                }
                if (!receita.value.passos) {
                    receita.value.passos = [];
                }
            } else {
                 statusMessage.value = "❌ Resposta da API vazia ou inválida.";
                 isError.value = true;
            }
            
        } catch (error) {
            console.error("Erro ao buscar receita:", error);
            statusMessage.value = "❌ Erro ao carregar a receita. Verifique a conexão com a API.";
            isError.value = true;
        } finally {
            loading.value = false;
        }
    });

    const atualizarReceita = async () => {
        isSaving.value = true;
        statusMessage.value = null; 
        isError.value = false;
        
        const id = route.params.id;
        const token = localStorage.getItem("token");
        
        const receitaParaSalvar = {
            ...receita.value,
            id: id, 
            ingredientes: receita.value.ingredientes.map(i => i.trim()).filter(i => i.length > 0),
            passos: receita.value.passos.map(p => p.trim()).filter(p => p.length > 0),
        };

        if (!receitaParaSalvar.titulo || receitaParaSalvar.ingredientes.length === 0 || receitaParaSalvar.passos.length === 0) {
             statusMessage.value = "⚠️ Preencha o título e adicione pelo menos um ingrediente e um passo.";
             isError.value = true;
             isSaving.value = false;
             return;
        }
        
        try {
           await api.put(`/receitas/update/${id}`, receitaParaSalvar, {
    headers: { Authorization: `Bearer ${token}` }
});
            
            statusMessage.value = "✅ Receita atualizada com sucesso! Redirecionando...";
            
            setTimeout(() => {
                 router.push("/minhas-receitas");
            }, 2000);
            
        } catch (error) {
            console.error("Erro ao atualizar receita:", error);
            statusMessage.value = `❌ Erro ao salvar as alterações: ${error.response?.data?.message || 'Falha na conexão com o servidor.'}`;
            isError.value = true;
        } finally {
            isSaving.value = false;
        }
    };

    const adicionarIngrediente = () => receita.value.ingredientes.push("");
    const removerIngrediente = (index) => receita.value.ingredientes.splice(index, 1);
    
    const adicionarPasso = () => receita.value.passos.push("");
    const removerPasso = (index) => receita.value.passos.splice(index, 1);
    
    const voltar = () => router.push("/minhas-receitas"); 

</script>

<style scoped>
    .form-control:focus {
        border-color: #0d6efd;
        box-shadow: 0 0 0 0.25rem rgba(13, 110, 253, 0.25);
    }
    .card {
        border-radius: 12px;
    }
</style>
