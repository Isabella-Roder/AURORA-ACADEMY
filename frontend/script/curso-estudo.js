const API_URL = "http://localhost:8080";

const params = new URLSearchParams(window.location.search);
const cursoId = params.get("id");

const alunoLogado = JSON.parse(localStorage.getItem("usuarioLogado"));

const cursoCategoria = document.getElementById("curso-categoria");
const cursoTitulo = document.getElementById("curso-titulo");
const cursoDescricao = document.getElementById("curso-descricao");

const cursoProgresso = document.getElementById("curso-progresso");
const totalModulos = document.getElementById("total-modulos");
const totalAulas = document.getElementById("total-aulas");
const totalAulasConcluidas = document.getElementById("total-aulas-concluidas");
const listaModulos = document.getElementById("lista-modulos-estudo");
const estadoVazio = document.getElementById("estado-vazio-modulos-estudo");

const aulaTitulo = document.getElementById("aula-titulo");
const aulaModulo = document.getElementById("aula-modulo");
const aulaDescricao = document.getElementById("aula-descricao");
const btnAbrirAula = document.getElementById("btn-abrir-aula");
const btnMarcarComoConcluida = document.getElementById("btn-marcar-concluida");

const mensagemAula = document.getElementById("mensagem-aula");

let aulaSelecionada = null;
let quantidadeTotalAulas = 0;

function validarAcesso() {
    if (!alunoLogado) {
        window.location.href = "login.html";
        return false;
    }

    if (alunoLogado.perfil !== "ALUNO") {
        window.location.href = "cursos.html";
        return false;
    }

    if (!cursoId) {
        cursoTitulo.textContent = "Curso não informado";
        cursoDescricao.textContent = "Volte para meus cursos e selecione um curso para estudar.";
        return false;
    }

    return true;
}

async function buscarCurso() {
    const resposta = await fetch(`${API_URL}/cursos/${cursoId}`);

    if (!resposta.ok) {
        const erro = await resposta.text();
        throw new Error(erro || "Erro ao carregar curso.");
    }

    return resposta.json();
}

async function buscarModulos() {
    const resposta = await fetch(`${API_URL}/modulos/curso/${cursoId}`);

    if (!resposta.ok) {
        const erro = await resposta.text();
        throw new Error(erro || "Erro ao carregar modulos.");
    }

    return resposta.json();
}

async function buscarAulas(moduloId) {
    const resposta = await fetch(`${API_URL}/aulas/modulos/${moduloId}`);

    if (!resposta.ok) {
        const erro = await resposta.text();
        throw new Error(erro || "Erro ao carregar aulas.");
    }

    return resposta.json();
}

async function renderizarModulos(modulos) {
    listaModulos.innerHTML = "";

    if (modulos.length === 0) {
        estadoVazio.style.display = "block";
        totalAulas.textContent = 0;
        return;
    }

    estadoVazio.style.display = "none";

    let quantidadeAulas = 0;

    for (const modulo of modulos) {
        const aulas = await buscarAulas(modulo.id);
        quantidadeAulas += aulas.length;

        const blocoModulo = document.createElement("article");
        blocoModulo.classList.add("module-card");

        const cabecalho = document.createElement("div");
        cabecalho.classList.add("module-header");

        cabecalho.innerHTML = `
            <span>Módulo ${modulo.ordem}</span>
            <h3>${modulo.titulo}</h3>
        `;

        const listaAulas = document.createElement("div");
        listaAulas.classList.add("lesson-table");

        if (aulas.length === 0) {
            listaAulas.innerHTML = "<p>Sem aulas cadastradas.</p>";
        }

        aulas.forEach((aula) => {
            const botaoAula = document.createElement("button");

            botaoAula.type = "button";
            botaoAula.classList.add("lesson-row");

            botaoAula.innerHTML = `
                <span>${aula.ordem}</span>
                <strong>${aula.titulo}</strong>
                <small>${aula.duracao || ""}</small>
            `;

            botaoAula.addEventListener("click", () => {
                selecionarAula(aula, modulo);
            });

            listaAulas.appendChild(botaoAula);
        });

        blocoModulo.appendChild(cabecalho);
        blocoModulo.appendChild(listaAulas);
        listaModulos.appendChild(blocoModulo);

    }

    totalAulas.textContent = quantidadeAulas;
    quantidadeTotalAulas = quantidadeAulas;
}

function selecionarAula(aula, modulo) {
    aulaSelecionada = aula;

    aulaTitulo.textContent = aula.titulo;

    aulaTitulo.textContent = aula.titulo;
    aulaModulo.textContent = `Módulo ${modulo.ordem}: ${modulo.titulo}`;
    aulaDescricao.textContent = aula.descricao || "Sem descrição.";

    if (aula.urlVideo) {
        btnAbrirAula.href = aula.urlVideo;
        btnAbrirAula.style.display = "inline-flex";
    } else {
        btnAbrirAula.style.display = "none";
    }

    btnMarcarComoConcluida.style.display = "inline-flex";
    mensagemAula.textContent = "";
}

async function buscarProgresso() {
    const resposta = await fetch(`${API_URL}/progressos/alunos/${alunoLogado.id}/cursos/${cursoId}`);

    if (!resposta.ok) {
        const erro = await resposta.text();
        throw new Error(erro || "Erro ao carregar progresso.");
    }

    return resposta.json();
}

function atualizarResumoProgresso(progressos) {
    const concluidas = progressos.filter((progresso) => progresso.concluida);

    totalAulasConcluidas.textContent = concluidas.length;

    const percentual = quantidadeTotalAulas === 0 ? 0 : Math.round((concluidas.length / quantidadeTotalAulas) * 100);

    cursoProgresso.textContent = `${percentual}%`;
}

async function marcarAulaComoConcluida() {
    if (!aulaSelecionada) {
        mensagemAula.textContent = "Selecione uma aula primeiro.";
        return;
    }

    try {
        btnMarcarComoConcluida.disabled = true;
        btnMarcarComoConcluida.textContent = "Salvando...";
        mensagemAula.textContent = "";

        const resposta = await fetch(`${API_URL}/progressos/alunos/${alunoLogado.id}/aulas/${aulaSelecionada.id}/concluir`, {
            method: "POST"
        });

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Erro ao concluir aula.");
        }

        await resposta.json();

        mensagemAula.textContent = "Aula marcada como concluida.";

        const progressos = await buscarProgresso();
        atualizarResumoProgresso(progressos);
    } catch (erro) {
        console.error(erro);
        mensagemAula.textContent = "Erro: " + erro.message;
    } finally {
        btnMarcarComoConcluida.disabled = false;
        btnMarcarComoConcluida.textContent = "Marcar como concluida";
    }
}

btnMarcarComoConcluida.addEventListener("click", marcarAulaComoConcluida);

async function iniciarPagina() {
    if(!validarAcesso()) {
        return;
    }

    try {
        const curso = await buscarCurso();
        const modulos = await buscarModulos();

        console.log("Curso:", curso);
        console.log("Modulos:", modulos);

        cursoCategoria.textContent = curso.categoria || "Curso";
        cursoTitulo.textContent = curso.titulo || "Curso sem titulo";
        cursoDescricao.textContent = curso.descricao || "Curso sem descrição";

        totalModulos.textContent = modulos.length;

        await renderizarModulos(modulos);

        const progressos = await buscarProgresso();
        atualizarResumoProgresso(progressos);

    } catch (erro) {
        console.error(erro);
        mensagemAula.textContent = erro.message;
    }
}

iniciarPagina();
