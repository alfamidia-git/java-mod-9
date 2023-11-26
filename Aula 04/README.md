# Atualizando dados e excluindo
Nesta aula veremos como atualizar e excluir dados com uma página dinâmica utilizando Servlet e JSP

## Modificando formAluno.jsp para Atualização
Primeiro, ajuste formAluno.jsp para que ele possa ser usado tanto para inserir quanto para atualizar alunos:

```html
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Aluno" %>
<!DOCTYPE html>
<html>
<head>
    <!-- Cabeçalho igual ao anterior -->
</head>
<body>
    <%
        Aluno aluno = (Aluno) request.getAttribute("alunoParaEditar");
        String nome = aluno != null ? aluno.getNome() : "";
        int idade = aluno != null ? aluno.getIdade() : "";
        String id = aluno != null ? aluno.getId() : "";
    %>
    <div class="container">
        <h1><%= id != -1 ? "Editar Aluno" : "Cadastrar Aluno" %></h1>
        <form action="AlunoController" method="post">
            <input type="hidden" name="id" value="<%= id %>">
            <div class="mb-3">
                <label for="nome" class="form-label">Nome:</label>
                <input type="text" class="form-control" id="nome" name="nome" value="<%= nome %>" required>
            </div>
            <div class="mb-3">
                <label for="idade" class="form-label">Idade:</label>
                <input type="number" class="form-control" id="idade" name="idade" value="<%= idade %>" required>
            </div>
            <button type="submit" class="btn btn-primary">Enviar</button>
        </form>
    </div>
    <!-- Scripts Bootstrap -->
</body>
</html>

```

## Tratando o Formulário no Servlet
No seu AlunoController, você vai tratar os dados enviados pelo formulário no método doPost.

```java
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idString = request.getParameter("id");

        Integer id = Objects.nonNull(idString) ? Integer.parseInt(idString) : null;
        String nome = request.getParameter("nome");
        int idade = Integer.parseInt(request.getParameter("idade"));

        Aluno aluno = new Aluno();
        aluno.setId(id);
        aluno.setNome(nome);
        aluno.setIdade(idade);

        if(Objects.nonNull(aluno.getId())){
            repository.update(aluno);
        }else{
            repository.create(aluno);
        }

        response.sendRedirect("alunos");
    }
```

## Modificando nossa lista e adicionando botões:
Adicione a biblioteca do fontawesome para usar ícones no seu **alunos.jsp**.

```html
<head>
    <!-- Cabeçalho igual ao anterior -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.2/css/all.min.css" integrity="sha512-z3gLpd7yknf1YoNbCzqRKc4qyor8gaKU1qmn+CShxbuBusANI9QpRohGBreCFkKxLhei6S9CQXFEbbKuqLg0DA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
</head>
<body>
<h1 style="text-align: center">Lista de Alunos</h1>
    <div class="container">
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">ID</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Idade</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Aluno> alunos = (List<Aluno>) request.getAttribute("alunos");
                    for (Aluno aluno : alunos) {
                        out.println("<tr>");
                        out.println("<th scope='row'>" + aluno.getId() + "</th>");
                        out.println("<td>" + aluno.getNome() + "</td>");
                        out.println("<td>" + aluno.getIdade() + "</td>");
                        out.println("<td> <a href='/ProjetoEscola/editarAluno?id=" + aluno.getId() + "'><i class='fa-solid fa-pen'></i></a> </td>");
                        out.println("</tr>");
                    }
                %>
            </tbody>
        </table>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
```

## Modifique seu AlunoController
Modifique a anotação @WebServlet e também o método doGet para tratar a requisição /editarAluno.
```java
@WebServlet(urlPatterns ={"/aluno", "/alunos", "/cadastrarAluno", "/editarAluno"})
```
```java
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String uri = request.getRequestURI();
	    
	    if(uri.contains("cadastrar")) {
	    	this.cadastrarAluno(request, response);
	    }else if(uri.contains("editar")){
            this.setarAlunoParaEditar(request, response);
        }else {
	    	this.listarAlunos(request, response);
	    }
	}
```
E crie o método **setarAlunoParaEditar**

```java
private void setarAlunoParaEditar(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		String idString = request.getParameter("id");
	    if(Objects.nonNull(idString) && !idString.isEmpty()){
	        int id = Integer.parseInt(idString);

	        Aluno aluno = repository.readById(id);

	        request.setAttribute("aluno", aluno);
	        RequestDispatcher dispatcher = request.getRequestDispatcher("formAluno.jsp");
	        dispatcher.forward(request, response);
	    }
	}
```

## Excluindo um usuário
Na sua lista de alunos na página **alunos.jsp**, adicione a coluna para exclusão
```html
out.println("<td> <i class='fa-solid fa-trash' href='/ProjetoEscola/excluirAluno?id=" + aluno.getId() + "'></i> </td>");
```

Depois trate isso no seu AlunoController
```java
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String uri = request.getRequestURI();
	    
	    if(uri.contains("cadastrar")) {
	    	this.cadastrarAluno(request, response);
	    }else if(uri.contains("editar")){
            this.setarAlunoParaEditar(request, response);
        }else if(uri.contains("excluir")){
            this.excluirAluno(request, response);
        }else {
	    	this.listarAlunos(request, response);
	    }
	}
```

E crie o método para excluir o aluno
```java
private void excluirAluno(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

    String idString = request.getParameter("id");
	    if(Objects.nonNull(idString) && !idString.isEmpty()){
	        int id = Integer.parseInt(idString);
            repository.delete(id);
        }
    
        response.sendRedirect("aluno");
}
```
# Exercicio
1. Reproduza tudo que foi nesta aula, utilizando a estrutura dos exercicios do módulo anterior (tabela curso)