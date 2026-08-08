const API_URL = "http://localhost:8080";

const form = document.getElementById("form-cadastro");
const btnCadastrar = document.getElementById("btn-cadastrar");
const mensagemCadastro = document.getElementById("mensagem-cadastro");

const inputNome = document.getElementById("nome");
const inputEmail = document.getElementById("email");
const inputSenha = document.getElementById("senha");
const inputConfirmaSenha = document.getElementById("confirmar-senha");
const selectPerfil = document.getElementById("perfil");

form.addEventListener("submit", async (event) => {
    event.preventDefault();

    mensagemCadastro.textContent = "";

    const nome = inputNome.value.trim();
    const email = inputEmail.value.trim();
    const senha = inputSenha.value.trim();
    const confirmarSenha = inputConfirmaSenha.value.trim();
    const perfil = selectPerfil.value;

    if (!nome || !email || !senha || !confirmarSenha || !perfil) {
        mensagemCadastro.textContent = "Preencha todos os campos.";
        return;
    }

    if (senha !== confirmarSenha) {
        mensagemCadastro.textContent = "As senhas não conferem.";
        return;
    }

    const usuario = {
        nome,
        email,
        senha,
        perfil
    };

    try {
        btnCadastrar.disabled = true;
        btnCadastrar.textContent = "Criando conta...";

        const resposta = await fetch(`${API_URL}/usuarios`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(usuario)
        });

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Erro ao criar conta.");
        }

        await resposta.json();

        mensagemCadastro.textContent = "Conta criada com sucesso.";

        setTimeout(() => {
            window.location.href = "index.html";
        }, 900);

    } catch (erro) {
        console.error(erro)
        mensagemCadastro.textContent = "Erro: " + erro.message;
    } finally {
        btnCadastrar.disabled = false;
        btnCadastrar.textContent = "Criar conta";
    }
});