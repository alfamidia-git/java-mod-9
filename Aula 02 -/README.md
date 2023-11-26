# Criando uma página dinâmica
Nesta aula veremos como criar uma página dinâmica utilizando Servlet e JSP. Também aprenderemos sobre JSP.
Para criar a página dinâmica, reaproveitaremos o que foi criado no módulo anterior de acesso ao Banco de Dados.

## O que é JSP?
Java Server Pages (JSP) é uma tecnologia para desenvolvimento web que permite a criação de páginas web dinâmicas, interativas e ricas em conteúdo. Ela é parte de plataforma Java Enterprise Edition (Java EE) e utiliza a linguagem de programação Java.

## Funcionalidades do JSP
1. **Páginas Web Dinâmicas:** JSP permite a criação de páginas web que podem alterar seu conteúdo de acordo com as ações do usuário, dados do servidor ou outras fontes.
2.**Integração com Java:** Como parte da plataforma Java, o JSP pode interagir facilmente com código Java, aproveitando a robustez, portabilidade e segurança da linguagem.
3. **Tags Personalizadas:** JSP suporta tags personalizadas, que permitem aos desenvolvedores encapsular funcionalidades complexas em tags simples de usar no HTML.
4. **Scriptlets:** Em JSP, é possível embutir código Java diretamente nas páginas, conhecido como scriptlets, para realizar operações do lado do servidor.

## Criando nosso projeto
Crie um dynamic web project e marque opção para gerar o web.xml

#### Organize seu projeto
Dentro do package **src.main.java** crie três packages: controller, model e repository.

### Reaproveitando nosso módulo anterior
* Dentro do package **model**, crie uma classe igual a criada no módulo anterior (utilizaremos a arquitetura de acesso ao banco de dados)
* Dentro do package **repository**, crie clases iguais as criadas no módulo anterior: **Conexao, Crud e AlunoRepository**

## Criando nosso arquivo .jsp
Dentro do package **src.main.webapp** crie um arquivo alunos.jsp.
Por enquanto, trataremos ele somente com html.

Copie e cole esse código no seu arquivo

```html
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Listagem de alunos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
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
                <tr>
                    <th scope="row">1</th>
                    <td>Mark</td>
                    <td>Otto</td>
                </tr>
                <tr>
                    <th scope="row">2</th>
                    <td>Jacob</td>
                    <td>Thornton</td>
                    <td>@fat</td>
                </tr>
                <tr>
                    <th scope="row">3</th>
                    <td colspan="2">Larry the Bird</td>
                    <td>@twitter</td>
                </tr>
            </tbody>
        </table>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>
```

### Copiando o JAR do mysql-connector para nossa pasta lib
Copie o jar que você baixou no módulo anterior e adicione na pasta **WEB-INF/lib**.


### Criando nosso Servlet
Dentro do package controller, crie nosso servlet. Nomeie como AlunoController e sobrescreva o método doGet.

```java
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    response.sendRedirect("alunos.jsp");
}
```
Após isso, coloque seu servidor para rodar e teste se está tudo funcionando. Caso tenha algum problema, reveja seu projeto.

## Enviando dados para o arquivo JSP
Com tudo funcionando, agora vamos editar nosso método doGet para enviar dados para nosso JSP.
```java
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    AlunoRepository repository = new AlunoRepository();
    List<Aluno> alunos = repository.readAll();

    request.setAttribute("alunos", alunos);

    RequestDispatcher dispatcher = request.getRequestDispatcher("alunos.jsp");
    dispatcher.forward(request, response);
}
```
**Entendendo o que foi feito**
1. o método **setAttribute** defini um atributo que poderá ser usado no JSP. Neste caso setamos um atributo chamado "alunos" que conterá a lista com todos os alunos.
2. **request.getRequestDispatcher("alunos.jsp")** obtém um RequestDispatcher para o arquivo alunos.jsp. O **RequestDispatcher** é um objeto que permite encaminhar requisições para recursos diferentes dentro da aplicação.
3. **dispatcher.forward(request, response)** encaminha a requisição atual e a resposta para o recurso especificado, neste caso, o arquivo JSP alunos.jsp.

## Recebendo os dados no JSP

```HTML
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="model.Aluno" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Lista de alunos</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
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
                        out.println("</tr>");
                    }
                %>
            </tbody>
        </table>
    </div>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>
```
**Entendendo o que foi feito**
1. Diretivas de páginas (**<%@ page ... %>**): As diretivas de página em JSP são instruções que fornecem informações globais sobre a página inteira, como a linguagem de programação usada, o tipo de conteúdo, importações de pacotes, entre outros.

2. Scriptlet: Os scriptlets em JSP são blocos de código Java inseridos dentro da página JSP. Eles são delimitados por <% ... %>. 

# Exercicio
1. Reproduza tudo que foi nesta aula, utilizando a estrutura dos exercicios do módulo anterior (tabela curso)