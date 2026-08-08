const API_URL = "http://localhost:8080";

const params = new URLSearchParams(window.location.search);
const cursoId = params.get("id");

const cursoTitulo = document.getElementById("curso-titulo");
const cursoDescricao = document.getElementById("curso-descricao");
const cursoStatus = document.getElementById("curso-status");

const formModulo = document.getElementById("form-modulo");
const moduloTitulo = document.getElementById("modulo-titulo");
const moduloOrdem = document.getElementById("modulo-ordem");
const mensagemModulo = document.getElementById("mensagem-modulo");
const btnCriarModulo = document.getElementById("btn-criar-modulo");

const listaModulosProfessor = document.getElementById("lista-modulos-professor");
const estadoVazioModulosProfessor = document.getElementById("estado-vazio-modulos-professor");

function formatarTexto(texto) {
    return String(texto || "")
        .toLowerCase()
        .replaceAll("_", " ")
        .replace(/^\w/, (letra) => letra.toUpperCase());
}

function mostrarErroCurso(mensagem) {
    cursoTitulo.textContent = "Curso nao encontrado.";
    cursoDescricao.textContent = mensagem;
    cursoStatus.textContent = "ERRO";
}

async function carregarCurso() {
    if (!cursoId) {
        mostrarErroCurso("Volte ao dashboard e selecione um curso.");
        return;
    }

    try {
        const resposta = await fetch(`${API_URL}/cursos/${cursoId}`);

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Erro ao carregar curso.");
        }

        const curso = await resposta.json();

        cursoTitulo.textContent = curso.titulo || "Curso sem titulo";
        cursoDescricao.textContent = curso.descricao || "Sem descricao.";
        cursoStatus.textContent = formatarTexto(curso.statusCurso || "RASCUNHO");

    } catch (erro) {
        console.error(erro);
        mostrarErroCurso("Erro: " + erro.message);
    }
}

async function carregarModulo() {
    if (!cursoId) {
        return;
    }

    try {
        const resposta = await fetch(`${API_URL}/modulos/curso/${cursoId}`);

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Erro ao carregar modulos.");
        }

        const modulos = await resposta.json();

        await renderizarModulos(modulos);

    } catch (erro) {
        console.error(erro);
        listaModulosProfessor.innerHTML = `
            <p class="form-message">Erro ao carregar modulos.</p>
        `;
    }
}

async function buscarAulasDoModulo(moduloId) {
    const resposta = await fetch(`${API_URL}/aulas/modulos/${moduloId}`);

    if (!resposta.ok) {
        const erro = await resposta.text();
        throw new Error(erro || "Erro ao carregar aulas.");
    }

    return await resposta.json();
}

async function renderizarModulos(modulos) {
    listaModulosProfessor.innerHTML = "";

    if (modulos.length === 0) {
        estadoVazioModulosProfessor.style.display = "block";
        return;
    }

    estadoVazioModulosProfessor.style.display = "none";

    for (const modulo of modulos) {
        const aulas = await buscarAulasDoModulo(modulo.id);

        const blocoModulo = document.createElement("article");
        blocoModulo.classList.add("module-card");

        blocoModulo.innerHTML = `
            <div class="module-header">
                <span>Modulo ${modulo.ordem}</span>
                <h3>${modulo.titulo}</h3>
            </div>

            <form class="resource-form aula-form" data-modulo-id="${modulo.id}">
                <div class="form-grid">
                    <div class="form-group">
                        <label>Titulo da aula</label>
                        <input type="text" class="aula-titulo" placeholder="Ex: Primeira aula">
                    </div>

                    <div class="form-group">
                        <label>Descricao</label>
                        <input type="text" class="aula-descricao" placeholder="Resumo da aula">
                    </div>

                    <div class="form-group">
                        <label>URL do video</label>
                        <input type="url" class="aula-url" placeholder="https://...">
                    </div>

                    <div class="form-group">
                        <label>Duracao</label>
                        <input type="time" class="aula-duracao">
                    </div>

                    <div class="form-group">
                        <label>Ordem</label>
                        <input type="number" class="aula-ordem" min="1" placeholder="1">
                    </div>
                </div>

                <p class="form-message aula-mensagem"></p>

                <div class="form-actions">
                    <button class="btn btn-secondary" type="submit">Criar aula</button>
                </div>
            </form>

            <div class="lesson-table">
                ${aulas.length === 0 ? `
                    <p>Nenhuma aula cadastrada neste modulo.</p>
                ` : aulas.map((aula) => `
                    <div class="lesson-row">
                        <span>${aula.ordem}</span>
                        <strong>${aula.titulo}</strong>
                        <small>${aula.duracao}</small>
                        <a class="btn btn-secondary btn-small" href="${aula.urlVideo}" target="_blank">
                            Assistir
                        </a>
                    </div>
                `).join("")}
            </div>
        `;

        listaModulosProfessor.appendChild(blocoModulo);
    }

    configurarFormsAula();
}

async function criarModulo(event) {
    event.preventDefault();

    mensagemModulo.textContent = "";

    const titulo = moduloTitulo.value.trim();
    const ordem = Number(moduloOrdem.value);

    if (!titulo || !ordem) {
        mensagemModulo.textContent = "Preencha titulo e ordem.";
        return;
    }

    try {
        btnCriarModulo.disabled = true;
        btnCriarModulo.textContent = "Criando...";

        const resposta = await fetch(`${API_URL}/modulos`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                titulo,
                ordem,
                curso: {
                    id: Number(cursoId)
                }
            })
        });

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Erro ao criar modulo.");
        }

        await resposta.json();

        mensagemModulo.textContent = "Modulo criado com sucesso.";
        formModulo.reset();

        await carregarModulo();

    } catch (erro) {
        console.error(erro);
        mensagemModulo.textContent = "Erro: " + erro.message;
    } finally {
        btnCriarModulo.disabled = false;
        btnCriarModulo.textContent = "Criar modulo";
    }
}

function configurarFormsAula() {
    const formsAula = document.querySelectorAll(".aula-form");

    formsAula.forEach((formAula) => {
        formAula.addEventListener("submit", criarAula);
    });
}

async function criarAula(event) {
    event.preventDefault();

    const formAula = event.currentTarget;
    const moduloId = formAula.dataset.moduloId;

    const titulo = formAula.querySelector(".aula-titulo").value.trim();
    const descricao = formAula.querySelector(".aula-descricao").value.trim();
    const urlVideo = formAula.querySelector(".aula-url").value.trim();
    const duracao = formAula.querySelector(".aula-duracao").value;
    const ordem = Number(formAula.querySelector(".aula-ordem").value);
    const mensagem = formAula.querySelector(".aula-mensagem");

    mensagem.textContent = "";

    if (!titulo || !descricao || !urlVideo || !duracao || !ordem) {
        mensagem.textContent = "Preencha todos os campos.";
        return;
    }

    try {
        const resposta = await fetch(`${API_URL}/aulas`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                titulo,
                descricao,
                urlVideo,
                duracao,
                ordem,
                modulo: {
                    id: Number(moduloId)
                }
            })
        });

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Erro ao criar aula.");
        }

        await resposta.json();

        mensagem.textContent = "Aula criada com sucesso.";
        formAula.reset();

        await carregarModulo();
    } catch (erro) {
        console.error(erro);
        mensagem.textContent = "Erro: " + erro.message;
    }
}

async function iniciarPagina() {
    await carregarCurso();
    await carregarModulo();
}

formModulo.addEventListener("submit", criarModulo);

iniciarPagina();
