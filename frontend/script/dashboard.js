const usuarioLogado = JSON.parse(localStorage.getItem("usuarioLogado"));

const usuarioNome = document.getElementById("usuario-nome");
const usuarioPerfil = document.getElementById("usuario-perfil");
const btnLogout = document.getElementById("btn-logout");

if (!usuarioLogado) {
    window.location.href = "login.html";
} else {
    if (usuarioNome) {
        usuarioNome.textContent = usuarioLogado.nome || "usuario";
    }

    if (usuarioPerfil) {
        usuarioPerfil.textContent = usuarioLogado.perfil || "PERFIL";
    }
}

if (btnLogout) {
    btnLogout.addEventListener("click", () => {
        localStorage.removeItem("usuarioLogado");
        window.location.href = "login.html";
    });
}
