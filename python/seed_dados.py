import json
from urllib import request, error

API_URL = "http://localhost:8080"


def get(endpoint):
    url = f"{API_URL}{endpoint}"

    try:
        with request.urlopen(url) as resposta:
            conteudo = resposta.read().decode("utf-8")
            return json.loads(conteudo)
    except error.HTTPError as erro:
        mensagem = erro.read().decode("utf-8")
        print(f"Erro em {endpoint}: {erro.code} - {mensagem}")
        return None


def post(endpoint, dados):
    url = f"{API_URL}{endpoint}"
    corpo = json.dumps(dados).encode("utf-8")

    requisicao = request.Request(
        url,
        data=corpo,
        headers={"Content-Type": "application/json"},
        method="POST"
    )

    try:
        with request.urlopen(requisicao) as resposta:
            conteudo = resposta.read().decode("utf-8")
            return json.loads(conteudo)
    except error.HTTPError as erro:
        mensagem = erro.read().decode("utf-8")
        print(f"Erro em {endpoint}: {erro.code} - {mensagem}")
        return None


def buscar_usuario_por_email(email):
    usuarios = get("/usuarios") or []

    for usuario in usuarios:
        if usuario.get("email") == email:
            return usuario

    return None


def criar_usuario(nome, email, senha, perfil):
    usuario_existente = buscar_usuario_por_email(email)

    if usuario_existente:
        print(f"Usuario ja existe: {email}")
        return usuario_existente

    usuario = {
        "nome": nome,
        "email": email,
        "senha": senha,
        "perfil": perfil
    }

    return post("/usuarios", usuario)


def criar_curso(titulo, descricao, categoria, nivel, preco, professor_id):
    curso = {
        "titulo": titulo,
        "descricao": descricao,
        "categoria": categoria,
        "nivelCurso": nivel,
        "cargaHoraria": "10:00",
        "preco": preco,
        "imgUrl": "https://images.unsplash.com/photo-1516321318423-f06f85e504b3",
        "statusCurso": "PUBLICADO",
        "professor": {
            "id": professor_id
        }
    }

    return post("/cursos", curso)


def main():
    print("Criando usuarios...")

    criar_usuario(
        "Aluno Teste",
        "aluno@aurora.com",
        "1234",
        "ALUNO"
    )

    professor = criar_usuario(
        "Professor Teste",
        "professor@aurora.com",
        "1234",
        "PROFESSOR"
    )

    criar_usuario(
        "Admin Teste",
        "admin@aurora.com",
        "1234",
        "ADMIN"
    )

    if not professor:
        print("Professor nao foi criado. Nao da para criar cursos.")
        return

    professor_id = professor["id"]

    print("Criando cursos...")

    criar_curso(
        "Java com Spring Boot",
        "Aprenda a criar APIs REST com Java, Spring Boot, JPA e H2.",
        "Programacao",
        "INICIANTE",
        750.90,
        professor_id
    )

    criar_curso(
        "Frontend com HTML, CSS e JavaScript",
        "Construa interfaces bonitas e conectadas com APIs.",
        "Frontend",
        "INTERMEDIARIO",
        100.90,
        professor_id
    )

    criar_curso(
        "Python para Relatorios",
        "Automatize relatorios e leia dados com Python.",
        "Dados",
        "INICIANTE",
        79.90,
        professor_id
    )

    print("Dados criados com sucesso.")


if __name__ == "__main__":
    main()
