const API_URL = "http://localhost:8080";

const usuarioProfessor = JSON.parse(localStorage.getItem("usuarioLogado"));

const totalCursos = document.getElementById("total-cursos");
const totalAlunos = document.getElementById("total-alunos");
const totalPublicados = document.getElementById("total-publicados");
const listaCursosProfessor = document.getElementById("lista-cursos-professor");
const estadoVazioCursos = document.getElementById("estado-vazio-cursos");

async function carregarCursosProfessor() {
    if (!usuarioProfessor || usuarioProfessor.perfil !== "PROFESSOR") {
        return;
    }

    try {
        const resposta = await fetch(`${API_URL}/cursos/professor/${usuarioProfessor.id}`);

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Erro ao buscar cursos.");
        }

        const cursosDoProfessor = await resposta.json();

        renderizarResumo(cursosDoProfessor);
        renderizarCursos(cursosDoProfessor);
        
    } catch (erro) {
        console.error(erro);
        listaCursosProfessor.innerHTML = `
            <p class="form-message">Erro ao carregar cursos.</p>
        `;
    }
}

function renderizarResumo(cursos) {
    const publicados = cursos.filter((curso) => curso.statusCurso === "PUBLICADO");

    totalCursos.textContent = cursos.length;
    totalAlunos.textContent = "0";
    totalPublicados.textContent = publicados.length;
}

function renderizarCursos(cursos) {
    listaCursosProfessor.innerHTML = "";

    if (cursos.length === 0) {
        estadoVazioCursos.style.display = "block";
        return;
    }

    estadoVazioCursos.style.display = "none";

    cursos.forEach((curso) => {
        const card = document.createElement("article");
        card.classList.add("course-card");

        card.innerHTML = `
            <img src="${curso.imgUrl}" alt="${curso.titulo}">
            <div class="course-card-body">
                <span class="course-status">${curso.statusCurso}</span>
                <h3>${curso.titulo}</h3>
                <p>${curso.descricao}</p>

                <div class="course-meta">
                    <span>${curso.categoria}</span>
                    <span>${curso.nivelCurso}</span>
                    <span>R$ ${Number(curso.preco).toFixed(2)}</span>
                </div>
            </div>
        `;

        listaCursosProfessor.appendChild(card);
    });
}

carregarCursosProfessor();
