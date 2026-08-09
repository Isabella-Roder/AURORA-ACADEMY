const API_URL = "http://localhost:8080";

const alunoLogado = JSON.parse(localStorage.getItem("usuarioLogado"));

const alunoNome = document.getElementById("aluno-nome");

const totalMatriculas = document.getElementById("total-matriculas");
const totalEmAndamento = document.getElementById("total-em-andamento");
const totalConcluidos = document.getElementById("total-concluidos");

const listaMeusCursos = document.getElementById("lista-meus-cursos");
const estadoVazioMeusCursos = document.getElementById("estado-vazio-meus-cursos");

const proximaAulaCard = document.getElementById("proxima-aula-card");
const proximaAulaCurso = document.getElementById("proxima-aula-curso");
const proximaAulaTitulo = document.getElementById("proxima-aula-titulo");
const btnContinuarAula = document.getElementById("btn-continuar-aula");

async function carregarMeusCursos() {
    if (!alunoLogado) {
        return;
    }

    alunoNome.textContent = alunoLogado.nome;

    try {
        const resposta = await fetch(`${API_URL}/matriculas/aluno/${alunoLogado.id}`);

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Erro ao carregar seus cursos.");
        }

        const matriculas = await resposta.json();

        console.log(matriculas);

        renderizarResumo(matriculas);
        renderizarCursos(matriculas);
        renderizarProximaAula(matriculas);

    } catch (erro) {
        console.error(erro);
        listaMeusCursos.innerHTML = `
            <p class="form-message">Erro ao carregar seus cursos.</p>
        `;
    }
}

function renderizarResumo(matriculas) {
    const emAndamento = matriculas.filter((matricula) => {
        return matricula.statusMatricula === "ATIVA";
    });

    const concluidos = matriculas.filter((matricula) => {
        return matricula.statusMatricula === "CONCLUIDA";
    });

    totalMatriculas.textContent = matriculas.length;
    totalEmAndamento.textContent = emAndamento.length;
    totalConcluidos.textContent = concluidos.length;
}

function renderizarCursos(matriculas) {
    listaMeusCursos.innerHTML = "";

    if (matriculas.length === 0) {
        estadoVazioMeusCursos.style.display = "grid";
        return;
    }

    estadoVazioMeusCursos.style.display = "none";

    matriculas.forEach((matricula) => {
        const curso = matricula.curso;

        const card = document.createElement("article");
        card.classList.add("course-card");

        card.innerHTML = `
            <div class="course-info">
                <span class="course-tag">${curso.categoria}</span>
                <h3>${curso.titulo}</h3>
                <p>${curso.descricao}</p>

                <div class="course-meta">
                    <span>${curso.nivelCurso}</span>
                    <span>${curso.cargaHoraria}</span>
                </div>

                <div class="course-meta">
                    <span>${matricula.statusMatricula}</span>
                    <span>${matricula.dataMatricula}</span>
                </div>

                <a class="btn btn-secondary" href="curso-estudo.html?id=${curso.id}">
                    Continuar estudando
                </a>
            </div>
        `;

        listaMeusCursos.appendChild(card);
    });
}

function renderizarProximaAula(matriculas) {
    if (matriculas.length === 0) {
        btnContinuarAula.style.display = "none";
        return;
    }

    const primeiraMatricula = matriculas[0];
    const curso = primeiraMatricula.curso;

    proximaAulaCurso.textContent = curso.titulo;
    proximaAulaTitulo.textContent = "Continue estudando este curso.";
    btnContinuarAula.href = `curos-estudo.html?id=${curso.id}`;
    btnContinuarAula.style.display = "inline-flex";
}

carregarMeusCursos();