const API_URL = "http://localhost:8080";

const formCurso = document.getElementById("form-curso");
const mensagemCurso = document.getElementById("mensagem-curso");
const btnSalvarCurso = document.getElementById("btn-salvar-curso");

const usuarioProfessor = JSON.parse(localStorage.getItem("usuarioLogado"));
let professorId = null;

if (!usuarioProfessor) {
    window.location.href = "login.html";
} else if (usuarioProfessor.perfil !== "PROFESSOR") {
    if (usuarioProfessor.perfil === "ALUNO") {
        window.location.href = "aluno-dashboard.html";
    } else if (usuarioProfessor.perfil === "ADMIN") {
        window.location.href = "admin-dashboard.html";
    } else {
        window.location.href = "login.html";
    }
} else {
    professorId = usuarioProfessor.id;
}

formCurso.addEventListener("submit", async (event) => {
    event.preventDefault();

    mensagemCurso.textContent = "";

    const titulo = document.getElementById("curso-titulo").value.trim();
    const descricao = document.getElementById("curso-descricao").value.trim();
    const categoria = document.getElementById("curso-categoria").value.trim();
    const nivelCurso = document.getElementById("curso-nivel").value;
    const cargaHoraria = document.getElementById("curso-carga-horaria").value;
    const preco = Number(document.getElementById("curso-preco").value);
    const imgUrl = document.getElementById("curso-img-url").value.trim();
    const statusCurso = document.getElementById("curso-status").value;

    if (!professorId) {
        mensagemCurso.textContent = "Faca login como professor para criar curso.";
        return;
    }

    if (!titulo || !descricao || !categoria || !nivelCurso || !preco || !imgUrl || !statusCurso) {
        mensagemCurso.textContent = "Preencha os campos obrigatorios.";
        return;
    }

    const curso = {
        titulo,
        descricao,
        categoria,
        nivelCurso,
        cargaHoraria: cargaHoraria || null,
        preco,
        imgUrl,
        statusCurso,
        professor: {
            id: professorId
        }
    };

    try {
        btnSalvarCurso.disabled = true;
        btnSalvarCurso.textContent = "Criando curso...";

        const resposta = await fetch(`${API_URL}/cursos`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(curso)
        });

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Erro ao salvar curso.");
        }

        await resposta.json();

        mensagemCurso.textContent = "Curso criado com sucesso.";
        formCurso.reset();
    } catch (erro) {
        console.error(erro);
        mensagemCurso.textContent = "Erro: " + erro.message;
    } finally {
        btnSalvarCurso.disabled = false;
        btnSalvarCurso.textContent = "Salvar curso";
    }
});
