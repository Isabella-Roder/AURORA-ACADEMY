# Aurora Academy - Requisitos do Projeto

## 1. Visao Geral

A Aurora Academy sera uma plataforma de cursos online, com foco em cadastro de cursos, aulas, matriculas, progresso do aluno e certificados.

O projeto sera construido como uma aplicacao de portfolio, usando Java Spring Boot no backend e uma interface web separada no frontend.

## 2. Objetivos

- [ ] Criar uma plataforma onde alunos possam se cadastrar e acessar cursos.
- [ ] Permitir que professores cadastrem cursos, modulos e aulas.
- [ ] Permitir que administradores gerenciem usuarios e cursos.
- [ ] Registrar matriculas dos alunos.
- [ ] Controlar progresso das aulas.
- [ ] Emitir certificado ao concluir um curso.
- [ ] Criar dashboards para aluno, professor e administrador.

## 3. Perfis do Sistema

### 3.1 Aluno

- [ ] Criar conta.
- [ ] Fazer login.
- [ ] Ver catalogo de cursos.
- [ ] Ver detalhes de um curso.
- [ ] Matricular-se em um curso.
- [ ] Assistir/acessar aulas.
- [ ] Marcar aula como concluida.
- [ ] Acompanhar progresso.
- [ ] Baixar certificado ao concluir o curso.

### 3.2 Professor

- [ ] Fazer login.
- [ ] Criar cursos.
- [ ] Editar cursos.
- [ ] Criar modulos.
- [ ] Criar aulas.
- [ ] Publicar ou arquivar cursos.
- [ ] Ver alunos matriculados.

### 3.3 Administrador

- [ ] Fazer login.
- [ ] Listar usuarios.
- [ ] Ativar/desativar usuarios.
- [ ] Listar cursos.
- [ ] Aprovar ou remover cursos.
- [ ] Visualizar metricas da plataforma.

## 4. Modulos Principais

### 4.1 Usuarios

- [ ] Entidade `Usuario`.
- [ ] Enum `PerfilUsuario`.
- [ ] Cadastro de usuario.
- [ ] Login simples por email e senha.
- [ ] Validacao de email unico.
- [ ] Validacao de campos obrigatorios.
- [ ] Listagem de usuarios.
- [ ] Edicao de usuario.
- [ ] Ativacao/desativacao de usuario.

### 4.2 Cursos

- [ ] Entidade `Curso`.
- [ ] Enum `StatusCurso`.
- [ ] Criar curso.
- [ ] Listar cursos.
- [ ] Buscar curso por ID.
- [ ] Editar curso.
- [ ] Publicar curso.
- [ ] Arquivar curso.
- [ ] Filtrar cursos publicados.
- [ ] Filtrar cursos por professor.

### 4.3 Modulos do Curso

- [ ] Entidade `ModuloCurso`.
- [ ] Criar modulo dentro de um curso.
- [ ] Listar modulos de um curso.
- [ ] Editar modulo.
- [ ] Remover modulo.
- [ ] Ordenar modulos.

### 4.4 Aulas

- [ ] Entidade `Aula`.
- [ ] Enum `TipoAula`.
- [ ] Criar aula dentro de um modulo.
- [ ] Listar aulas de um modulo.
- [ ] Editar aula.
- [ ] Remover aula.
- [ ] Ordenar aulas.
- [ ] Permitir aula do tipo video.
- [ ] Permitir aula do tipo texto.
- [ ] Permitir aula do tipo PDF.
- [ ] Permitir aula do tipo quiz futuramente.

### 4.5 Matriculas

- [ ] Entidade `Matricula`.
- [ ] Aluno pode se matricular em curso publicado.
- [ ] Evitar matricula duplicada no mesmo curso.
- [ ] Listar cursos matriculados do aluno.
- [ ] Cancelar matricula.
- [ ] Registrar data da matricula.

### 4.6 Progresso

- [ ] Entidade `ProgressoAula`.
- [ ] Marcar aula como concluida.
- [ ] Remover conclusao de uma aula.
- [ ] Calcular percentual de progresso do curso.
- [ ] Mostrar ultima aula acessada.
- [ ] Marcar curso como concluido ao atingir 100%.

### 4.7 Certificados

- [ ] Entidade `Certificado`.
- [ ] Gerar certificado ao concluir curso.
- [ ] Evitar certificado duplicado para o mesmo curso.
- [ ] Listar certificados do aluno.
- [ ] Exibir certificado em tela.
- [ ] Exportar certificado em PDF futuramente.

### 4.8 Avaliacoes

- [ ] Entidade `AvaliacaoCurso`.
- [ ] Aluno pode avaliar curso concluido.
- [ ] Nota de 1 a 5.
- [ ] Comentario opcional.
- [ ] Calcular media de avaliacao do curso.

## 5. Telas do Frontend

### 5.1 Area Publica

- [ ] Landing/catalogo inicial.
- [ ] Tela de login.
- [ ] Tela de cadastro.
- [ ] Catalogo de cursos publicados.
- [ ] Detalhe publico do curso.

### 5.2 Area do Aluno

- [ ] Dashboard do aluno.
- [ ] Meus cursos.
- [ ] Tela de estudo do curso.
- [ ] Tela de aula.
- [ ] Tela de progresso.
- [ ] Tela de certificados.

### 5.3 Area do Professor

- [ ] Dashboard do professor.
- [ ] Meus cursos criados.
- [ ] Formulario de curso.
- [ ] Gerenciamento de modulos.
- [ ] Gerenciamento de aulas.
- [ ] Lista de alunos matriculados.

### 5.4 Area Administrativa

- [ ] Dashboard admin.
- [ ] CRUD de usuarios.
- [ ] CRUD de cursos.
- [ ] Moderacao de cursos.
- [ ] Relatorios basicos.

## 6. Regras de Negocio

- [ ] Email de usuario deve ser unico.
- [ ] Usuario inativo nao pode fazer login.
- [ ] Apenas professor ou admin pode criar curso.
- [ ] Apenas o professor dono ou admin pode editar curso.
- [ ] Apenas curso publicado aparece no catalogo do aluno.
- [ ] Aluno nao pode se matricular duas vezes no mesmo curso.
- [ ] Certificado so pode ser gerado com 100% de progresso.
- [ ] Aula concluida deve contar apenas uma vez no progresso.
- [ ] Curso arquivado nao deve aceitar novas matriculas.

## 7. Backend Spring Boot

- [ ] Criar pacotes `model`, `repository`, `service`, `controller`, `dto` e `enums`.
- [ ] Configurar banco H2.
- [ ] Configurar CORS para o frontend.
- [ ] Criar tratamento basico de erros.
- [ ] Criar endpoints REST para usuarios.
- [ ] Criar endpoints REST para cursos.
- [ ] Criar endpoints REST para modulos.
- [ ] Criar endpoints REST para aulas.
- [ ] Criar endpoints REST para matriculas.
- [ ] Criar endpoints REST para progresso.
- [ ] Criar endpoints REST para certificados.

## 8. Frontend

- [ ] Criar estrutura `frontend/html`, `frontend/css` e `frontend/script`.
- [ ] Criar CSS base.
- [ ] Criar layout reutilizavel.
- [ ] Criar chamadas `fetch` para o backend.
- [ ] Salvar usuario logado no `localStorage`.
- [ ] Separar menus por perfil.
- [ ] Mostrar mensagens de erro e sucesso.
- [ ] Criar responsividade basica.

## 9. MVP - Primeira Versao

Para considerar a primeira versao pronta:

- [ ] Cadastro e login de usuario.
- [ ] Perfil de aluno, professor e admin.
- [ ] CRUD de cursos.
- [ ] CRUD de modulos.
- [ ] CRUD de aulas.
- [ ] Catalogo de cursos.
- [ ] Matricula do aluno.
- [ ] Progresso de aulas.
- [ ] Certificado visual simples.
- [ ] Dashboard basico do aluno.
- [ ] Dashboard basico do professor.
- [ ] Dashboard basico do admin.

## 10. Ideias Futuras

- [ ] Login com Google.
- [ ] Autenticacao com JWT.
- [ ] Upload real de videos.
- [ ] Upload de PDFs.
- [ ] Player de video melhorado.
- [ ] Quiz com pontuacao.
- [ ] Forum/comentarios por aula.
- [ ] Chat entre aluno e professor.
- [ ] Pagamento de cursos.
- [ ] Cupons de desconto.
- [ ] Assinatura mensal.
- [ ] Certificado em PDF.
- [ ] Relatorios com Python.
- [ ] Recomendacao de cursos.

## 11. Status Atual

- [x] Projeto Spring Boot gerado.
- [x] Git configurado.
- [~] Estrutura inicial criada.
- [ ] Banco H2 ainda precisa ser configurado.
- [ ] Backend ainda precisa subir sem erro.
- [ ] Primeiras entidades ainda nao foram criadas.
