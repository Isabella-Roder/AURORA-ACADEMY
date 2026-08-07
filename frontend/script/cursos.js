const API_URL = "http://localhost:8080";

const buscaCurso = document.getElementById("busca-curso");
const filtroNivel = document.getElementById("filtro-nivel");
const filtroCategoria = document.getElementById("filtro-categoria");
const totalCusoCatalogo = document.getElementById("total-cursos-catalogo");
const listaCursos = document.getElementById("lista-cursos");

let cursos = [];

function formatarMoeda(valor) {
    return Number(valor).toLocaleString("pt-BR", {
        style: "currency",
        currency: "BRL"
    });
}

function formatarTexto(texto) {
    return String(texto)
        .toLowerCase()
        .replaceAll("_", " ")
        .replace(/^\w/, (letra) => letra.toUpperCase());
}

async function carregarCurso() {
    try {
        listaCursos.innerHTML = "<p>Carregando cursos...</p>";

        const resposta = await fetch(`${API_URL}/cursos`);

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Erro ao buscar cursos.");
        }

        cursos = await resposta.json();

    } catch (erro) {
        console.error(erro);
        listaCursos.innerHTML = "<p>Nao foi possivel carregar.</p>";
        totalCusoCatalogo.textContent = "0 cursos";
    }
}

function renderizarCursos(lista) {
    if (!lista || lista.length === 0) {
        listaCursos.innerHTML = `
            <div class="empty-state">
                <p>Nenhum curso encontrado.</p>
            </div>
        `;
        totalCusoCatalogo.textContent = "0 cursos";
        return;
    }

    totalCusoCatalogo.textContent = `${lista.length} curso${lista.length > 1 ? "s" : ""}`;

    listaCursos.innerHTML = lista.map((curso) => {
        const titulo = curso.titulo || "Curso sem titulo";
        const descricao = curso.descricao || "Sem descrição";
        const categoria = curso.categoria || "Geral";
        const nivel = curso.nivelCurso || "Nivel nao informado";
        const cargaHoraria = curso.cargaHoraria || "Carga horaria nao informada";
        const preco = curso.preco != null ? formatarMoeda(curso.preco) : "Gratuito";

        return `
            <article class="course-card">
                <div class="course-cover"></div>

                <div class="course-info">
                    <span class="course-tag">${categoria}</span>
                    <h3>${titulo}</h3>
                    <p>${descricao}</p>

                    <div class="course-meta">
                        <span>${formatarTexto(nivel)}</span>
                        <span>${cargaHoraria}</span>
                    </div>

                    <div class="course-meta">
                        <span>${preco}</span>
                        <span>${formatarTexto(curso.statusCurso || "RASCUNHO")}</span>
                    </div>

                    <a class="btn btn-secondary" href="#">Ver detalhes</a>
                </div>
            </article>
        `;
    }).join("");
}

function filtrarCursos() {
    const busca = buscaCurso.value.trim().toLowerCase();
    const nivel = filtroNivel.value;
    const categoria = filtroCategoria.value;

    const cursosFiltrados = cursos.filter((curso) => {
        const titulo = (curso.titulo || "").toLowerCase();
        const descricao = (curso.descricao || "").toLowerCase();
        const categoriaCurso = curso.categoria || "";
        const nivelCurso = curso.nivelCurso || "";

        const combinaBusca = titulo.includes(busca) || descricao.includes(busca);

        const combinaNivel = !nivel || nivelCurso === nivel;

        const combinaCategoria = !categoria || categoriaCurso === categoria;

        return combinaBusca && combinaNivel && combinaCategoria;
    });

    renderizarCursos(cursosFiltrados);
}

buscaCurso.addEventListener("input", filtrarCursos);

filtroNivel.addEventListener("change", filtrarCursos);

filtroCategoria.addEventListener("change", filtrarCursos);

carregarCurso();