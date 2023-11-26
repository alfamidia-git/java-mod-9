package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Aluno;
import repository.AlunoRepository;

@WebServlet(urlPatterns ={"/aluno", "/alunos", "/cadastrarAluno"})
public class AlunoController extends HttpServlet {
	
	AlunoRepository repository = new AlunoRepository();
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlunoController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String uri = request.getRequestURI();
	    
	    if(uri.contains("cadastrar")) {
	    	this.cadastrarAluno(request, response);
	    }else {
	    	this.listarAlunos(request, response);
	    }
	}
	
	private void cadastrarAluno(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/formAluno.jsp");
	    dispatcher.forward(request, response);
	}
	
	private void listarAlunos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Aluno> alunos = repository.readAll();

	    request.setAttribute("alunos", alunos);

	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/views/alunos.jsp");
	    dispatcher.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 	String nome = request.getParameter("nome");
	        int idade = Integer.parseInt(request.getParameter("idade"));

	        Aluno aluno = new Aluno();
	        aluno.setNome(nome);
	        aluno.setIdade(idade);

	        repository.create(aluno);

	        response.sendRedirect("aluno");
	}

}
