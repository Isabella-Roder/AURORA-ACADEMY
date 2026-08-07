const API_URL = "http://localhost:8080";

const formLogin = document.getElementById("form-login");
const inputEmail = document.getElementById("email-login");
const inputSenha = document.getElementById("senha-login");
const mensagemLogin = document.getElementById("mensagem-login");
const btnLogin = document.getElementById("btn-login");

formLogin.addEventListener("submit", async (event) => {
    event.preventDefault();

    mensagemLogin.textContent = "";

    const email = inputEmail.value.trim();
    const senha = inputSenha.value.trim();

    if (!email || !senha) {
        mensagemLogin.textContent = "Preencha email e senha.";
        return;
    }

    try {
        btnLogin.disabled = true;
        btnLogin.textContent = "Entrando...";

        const resposta = await fetch(`${API_URL}/usuarios/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email,
                senha
            })
        });

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Login invalido.");
        }

        const usuario = await resposta.json();

        localStorage.setItem("usuarioLogado", JSON.stringify(usuario));

        setTimeout(() => {
            if (usuario.perfil === "ADMIN") {
                window.location.href = "admin-dashboard.html";
            } else if (usuario.perfil === "PROFESSOR") {
                window.location.href = "professor-dashboard.html";
            } else {
                window.location.href = "aluno-dashboard.html";
            }
        }, 500);

    } catch (erro) {
        console.error(erro);
        mensagemLogin.textContent = "Erro: " + erro.message;
    } finally {
        btnLogin.disabled = false;
        btnLogin.textContent = "Entrar";
    }
});