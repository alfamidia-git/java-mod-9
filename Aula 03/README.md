# Inserindo dados numa página dinâmica
Nesta aula veremos como inserir dados com uma página dinâmica utilizando Servlet e JSP

## Criando a página de inserção
Crie um arquivo jsp, chamado **formAluno**.
```html
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Cadastro de Aluno</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <div class="container">
        <h1>Cadastro de Aluno</h1>
        <form action="AlunoController" method="post">
            <div class="mb-3">
                <label for="nome" class="form-label">Nome:</label>
                <input type="text" class="form-control" id="nome" name="nome" required>
            </div>
            <div class="mb-3">
                <label for="idade" class="form-label">Idade:</label>
                <input type="number" class="form-control" id="idade" name="idade" required>
            </div>
            <button type="submit" class="btn btn-primary">Enviar</button>
        </form>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
```

## Tratando o Formulário no Servlet
No seu AlunoController, você vai tratar os dados enviados pelo formulário no método doPost.

```java
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        int idade = Integer.parseInt(request.getParameter("idade"));

        Aluno aluno = new Aluno();
        aluno.setNome(nome);
        aluno.setIdade(idade);

        AlunoRepository repository = new AlunoRepository();
        repository.create(aluno);

        response.sendRedirect("alunos");
    }
```

# Exercicio
1. Reproduza tudo que foi nesta aula, utilizando a estrutura dos exercicios do módulo anterior (tabela curso)