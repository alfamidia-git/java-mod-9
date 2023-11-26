package repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/escola"; //URL de conexão
    private static final String USUARIO = "root"; //Usuario do banco de dados
    private static final String SENHA = ""; // Senha do banco de dados


    public static Connection getConexao() {
        Connection connection = null;

        try {
        	Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USUARIO, SENHA);
        }catch(ClassNotFoundException e) {
        	System.out.println(e.getMessage());
        }
        catch(SQLException e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    public static void fecharConexao(Connection conexao){
        try {
            conexao.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
