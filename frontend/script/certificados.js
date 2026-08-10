(() => {
    const API_URL = "http://localhost:8080";

    const alunoCertificados = JSON.parse(localStorage.getItem("usuarioLogado"));

    const listaCertificados = document.getElementById("lista-certificados");
    const estadoVazio = document.getElementById("estado-vazio-certificados");
    const totalCertificados = document.getElementById("total-certificados");
    const certificadoAluno = document.getElementById("certificado-aluno");
    const certificadoCurso = document.getElementById("certificado-curso");
    const certificadoData = document.getElementById("certificado-data");
    const certificadoCodigo = document.getElementById("certificado-codigo");

    const btnImprimir = document.getElementById("btn-imprimir-certificado");

    async function buscarCertificados() {
        const resposta = await fetch(`${API_URL}/certificados/alunos/${alunoCertificados.id}`);

        if (!resposta.ok) {
            const erro = await resposta.text();
            throw new Error(erro || "Erro ao carregar certificados.");
        }

        return resposta.json();
    }

    function formatarData(dataEmissao) {
        return new Date(dataEmissao).toLocaleDateString("pt-BR");
    }

    function mostrarCertificado(certificado) {
        certificadoAluno.textContent = certificado.alunoNome;
        certificadoCurso.textContent = certificado.cursoTitulo;
        certificadoData.textContent = formatarData(certificado.dataEmissao);
        certificadoCodigo.textContent = certificado.codigoValidacao;

        btnImprimir.disabled = false;
    }

    function criarItemCertificado(certificado) {
        const botao = document.createElement("button");

        botao.type = "button";
        botao.classList.add("certificate-list-item");

        const titulo = document.createElement("strong");
        titulo.textContent = certificado.cursoTitulo;

        const data = document.createElement("span");
        data.textContent = `Emitido em ${formatarData(certificado.dataEmissao)}`;

        const codigo = document.createElement("small");
        codigo.textContent = certificado.codigoValidacao;

        botao.appendChild(titulo);
        botao.appendChild(data);
        botao.appendChild(codigo);

        botao.addEventListener("click", () => {
            document.querySelectorAll(".certificate-list-item.active")
                .forEach((item) => {
                    item.classList.remove("active");
                });

            botao.classList.add("active");
            mostrarCertificado(certificado);
        });

        return botao;
    }

    function renderizarCertificados(certificados) {
        listaCertificados.innerHTML = "";
        totalCertificados.textContent = certificados.length;

        if (certificados.length === 0) {
            listaCertificados.appendChild(estadoVazio);
            estadoVazio.style.display = "grid";
            return;
        }

        estadoVazio.style.display = "none";

        certificados.forEach((certificado) => {
            listaCertificados.appendChild(criarItemCertificado(certificado));
        });

        mostrarCertificado(certificados[0]);

        listaCertificados.querySelector(".certificate-list-item")
            ?.classList.add("active");
    }

    async function iniciarPagina() {
        if (!alunoCertificados) {
            window.location.href = "login.html";
            return;
        }

        try {
            const certificados = await buscarCertificados();
            renderizarCertificados(certificados);
        } catch (erro) {
            console.error(erro);
            estadoVazio.querySelector("p").textContent = "Não foi possivel carregar os certificados.";
        }
    }

    btnImprimir.addEventListener("click", () => {
        window.print();
    });

    iniciarPagina();
})();