# Introdução ao Java Servlet
Java Servlet é uma tecnologia fundamental no desenvolvimento de aplicações web em Java. Servlets são utilizados para estender as capacidades de servidores que hospedam aplicações acessadas por meio de um modelo de requisição-resposta. Nesta aula, exploraremos os conceitos básicos do Java Servlet, incluindo comunicação HTTP, Tomcat como servidor de aplicação, a estrutura de um projeto Servlet, e a implementação dos métodos doGet e doPost.

## O que é Java Servlet?
Java Servlet é uma API que fornece uma maneira de criar aplicações web dinâmicas em Java. Servlets são capazes de receber e responder a requisições de clientes, normalmente navegadores web, através do protocolo HTTP.

## Comunicação HTTP
Servlets operam na camada de aplicação do protocolo HTTP. Eles podem manipular diversos tipos de requisições HTTP, como GET, POST, PUT, DELETE, entre outros. Cada tipo de requisição tem um propósito específico na comunicação entre o cliente e o servidor.

## Apache Tomcat
Apache Tomcat é um servidor de aplicação Java amplamente utilizado para hospedar aplicações Java, incluindo servlets. Ele age como um container Servlet, gerenciando o ciclo de vida dos servlets e encaminhando as requisições HTTP para eles.

## Estrutura de um Projeto Servlet
* Diretórios src para código-fonte Java e WebContent para arquivos web (HTML, CSS, JavaScript).
* Arquivo web.xml para configuração de servlets, mapeamento de URL e outras configurações web.


### Criando um Servlet
1. Crie uma classe Java estendendo HttpServlet.
2. Sobrescreva os métodos doGet ou doPost, dependendo do tipo de requisição que deseja manipular.

```java
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MeuServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        // Código para manipular requisição GET
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        // Código para manipular requisição POST
    }
}
```

## Métodos doGet e doPost
1. **doGet:** Utilizado para requisições GET, tipicamente usadas para solicitar dados do servidor.
2. **doPost:** Utilizado para requisições POST, frequentemente usadas para enviar dados para serem processados pelo servidor.

## Benefícios de Utilizar Java Servlets
1. **Flexibilidade:** Permite manipular diferentes tipos de requisições HTTP.
2. **Integração:** Facilmente integrável com outras tecnologias Java, como JSP e frameworks como Spring.
3. **Desempenho:** Eficiente no gerenciamento de recursos e processamento de requisições.

# Exercicio
1. Crie um banco de dados chamada: **escola**
2. Crie uma classe no Java chamada Conexão e crie um método estático para obter conexão com essa base de dados criada.
3. Na sua classe principal, teste a conexão