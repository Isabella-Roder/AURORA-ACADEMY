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

- [x] Entidade `Usuario`.
- [x] Enum `PerfilUsuario`.
- [x] Cadastro de usuario.
- [x] Login simples por email e senha.
- [x] Validacao de email unico.
- [x] Validacao de campos obrigatorios.
- [x] Listagem de usuarios.
- [x] Edicao de usuario.
- [ ] Ativacao/desativacao de usuario.

### 4.2 Cursos

- [x] Entidade `Curso`.
- [x] Enum `StatusCurso`.
- [x] Criar curso.
- [x] Listar cursos.
- [x] Buscar curso por ID.
- [x] Editar curso.
- [ ] Publicar curso.
- [ ] Arquivar curso.
- [x] Filtrar cursos publicados.
- [x] Filtrar cursos por professor.

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

- [x] Landing/catalogo inicial.
- [x] Tela de login.
- [x] Tela de cadastro.
- [ ] Catalogo de cursos publicados.
- [ ] Detalhe publico do curso.

### 5.2 Area do Aluno

- [x] Dashboard do aluno.
- [ ] Meus cursos.
- [ ] Tela de estudo do curso.
- [ ] Tela de aula.
- [ ] Tela de progresso.
- [ ] Tela de certificados.

### 5.3 Area do Professor

- [x] Dashboard do professor.
- [ ] Meus cursos criados.
- [ ] Formulario de curso.
- [ ] Gerenciamento de modulos.
- [ ] Gerenciamento de aulas.
- [ ] Lista de alunos matriculados.

### 5.4 Area Administrativa

- [x] Dashboard admin.
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

- [x] Criar pacotes `model`, `repository`, `service`, `controller`, `dto` e `enums`.
- [x] Configurar banco H2.
- [x] Configurar CORS para o frontend.
- [ ] Criar tratamento basico de erros.
- [x] Criar endpoints REST para usuarios.
- [x] Criar endpoints REST para cursos.
- [ ] Criar endpoints REST para modulos.
- [ ] Criar endpoints REST para aulas.
- [ ] Criar endpoints REST para matriculas.
- [ ] Criar endpoints REST para progresso.
- [ ] Criar endpoints REST para certificados.

## 8. Frontend

- [x] Criar estrutura `frontend/html`, `frontend/css` e `frontend/script`.
- [x] Criar CSS base.
- [ ] Criar layout reutilizavel.
- [x] Criar chamadas `fetch` para o backend.
- [x] Salvar usuario logado no `localStorage`.
- [~] Separar menus por perfil.
- [x] Mostrar mensagens de erro e sucesso.
- [x] Criar responsividade basica.

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
- [x] Estrutura inicial criada.
- [x] Banco H2 configurado.
- [x] Backend subindo sem erro.
- [x] Entidade `Usuario` criada.
- [x] Login e cadastro funcionando no frontend.
- [x] Dashboards iniciais criados.
- [~] Modulo de cursos iniciado.
