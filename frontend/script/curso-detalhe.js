const API_URL = "http://localhost:8080";

const params = new URLSearchParams(window.location.search);
const cursoId = params.get("id");

const cursoCategoria = document.getElementById("curso-categoria");
const cursoTitulo = document.getElementById("curso-titulo");
const cursoDescricao = document.getElementById("curso-descricao");
const cursoNivel = document.getElementById("curso-nivel");
const cursoCargaHoraria = document.getElementById("curso-carga-horaria");
const cursoStatus = document.getElementById("curso-status");
const cursoPreco = document.getElementById("curso-preco");
const cursoProfessor = document.getElementById("curso-professor");
const cursoDescricaoCompleta = document.getElementById("curso-descricao-completa");
const mensagemMatricula = document.getElementById("mensagem-matricula");
const btnMatricular = document.getElementById("btn-matricular");

function formatarMoeda(valor) {
    return Number(valor).toLocaleString("pt-BR", {
        style: "currency",
        currency: "BRL"
    });
}

function formatarTexto(texto) {
    return String(texto || "")
        .toLowerCase()
        .replaceAll("_", " ")
        .replace(/^\w/, (letra) => letra.toUpperCase());
}

function mostrarErro(mensagem) {
    cursoTitulo.textContent = "Nao foi possivel carregar o curso.";
    cursoDescricao.textContent = mensagem;
    cursoDescricaoCompleta.textContent = mensagem;
    btnMatricular.disabled = true;
}

function renderizarCurso(curso) {
    cursoCategoria.textContent = curso.categoria || "Categoria";
    cursoTitulo.textContent = curso.titulo || "Curso sem titulo";
    cursoDescricao.textContent = curso.descricao || "Sem descricao.";
    cursoDescricaoCompleta.textContent = curso.descricao || "Sem descricao completa.";
    cursoNivel.textContent = formatarTexto(curso.nivelCurso || "Nivel nao informado");
    cursoCargaHoraria.textContent = curso.cargaHoraria || "Carga horaria nao informada";
    cursoStatus.textContent = formatarTexto(curso.statusCurso || "RASCUNHO");
    cursoPreco.textContent = curso.preco != null ? formatarMoeda(curso.preco) : "Gratuito";

    if (curso.professor && curso.professor.nome) {
        cursoProfessor.textContent = curso.professor.nome;
    } else {
        cursoProfessor.textContent = "Professor nao informado";
    }
}

async function carregarDetalheCurso() {
    if (!cursoId) {
        mostrarErro("Volte ao catalogo e clique em Ver detalhes de um curso.");
        return;
    }

    try {
        const resposta = await fetch(`${API_URL}/cursos/${cursoId}`);

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Erro ao buscar curso.");
        }

        const curso = await resposta.json();
        renderizarCurso(curso);

    } catch (erro) {
        console.error(erro);
        mostrarErro("Erro: " + erro.message);
    }
}

async function matricularAluno() {
    mensagemMatricula.textContent = "";

    const usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));

    if (!usuarioLogado) {
        mensagemMatricula.textContent = "Faca login para se matricular.";

        setTimeout(() => {
            window.location.href = "login.html";
        }, 900);

        return;
    }

    if (usuarioLogado.perfil !== "ALUNO") {
        mensagemMatricula.textContent = "Apenas alunos podem se matricular em cursos.";
        return;
    }

    try {
        btnMatricular.disabled = true;
        btnMatricular.textContent = "Matriculando...";

        const resposta = await fetch(`${API_URL}/matriculas`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                aluno: {
                    id: usuarioLogado.id
                },
                curso: {
                    id: Number(cursoId)
                }
            })
        });

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Erro ao realizar matricula.");
        }

        await resposta.json();
        mensagemMatricula.textContent = "Matricula realizada com sucesso.";

    } catch (erro) {
        console.error(erro);
        mensagemMatricula.textContent = "Erro: " + erro.message;
    } finally {
        btnMatricular.disabled = false;
        btnMatricular.textContent = "Matricular-se";
    }
}

btnMatricular.addEventListener("click", matricularAluno);

carregarDetalheCurso();
