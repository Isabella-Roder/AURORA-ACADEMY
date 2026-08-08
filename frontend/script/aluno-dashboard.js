const API_URL = "http://localhost:8080";

const alunoLogado = JSON.parse(localStorage.getItem("usuarioLogado"));

const totalCursos = document.getElementById("total-cursos");
const totalAulas = document.getElementById("total-aulas");
const totalCertificados = document.getElementById("total-certificados");
const listaCursosAluno = document.getElementById("lista-cursos-aluno");
const estadoVazioAluno = document.getElementById("estado-vazio-aluno");

async function carregarCursosAluno(params) {
    if (!alunoLogado || alunoLogado.perfil !== "ALUNO") {
        return;
    }

    try {
        const resposta = await fetch(`${API_URL}/matriculas/aluno/${alunoLogado.id}`);

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Erro ao carregar cursos.");
        }

        const matriculas = await resposta.json();

        renderizarResumo(matriculas);
        renderizarCursos(matriculas);

    } catch (erro) {
        console.error(erro);
        listaCursosAluno.innerHTML = `
            <p class="form-message">Erro ao carregar seu curso.</p>
        `;
    }
}

function renderizarResumo(matriculas) {
    totalCursos.textContent = matriculas.length;
    totalAulas.textContent = "0";
    totalCertificados.textContent = "0";
}

function renderizarCursos(matriculas) {
    listaCursosAluno.innerHTML = "";

    if (matriculas.length === 0) {
        estadoVazioAluno.style.display = "block";
        return;
    }

    estadoVazioAluno.style.display = "none";

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

                <a class="btn btn-secondary" href="curso-detalhe.html?id=${curso.id}">
                    Ver curso
                </a>
            </div>
        `;

        listaCursosAluno.appendChild(card);
    });
}

carregarCursosAluno();