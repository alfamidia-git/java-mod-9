package repository;

import model.Aluno;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AlunoRepository implements Crud<Aluno>{
    private Connection conn;
    @Override
    public void create(Aluno aluno) {
        String sql = "INSERT INTO aluno (nome, idade) VALUES (?, ?)";
        this.conn = Conexao.getConexao();

        try (
            PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, aluno.getNome());
            pstmt.setInt(2, aluno.getIdade());

            int resultado = pstmt.executeUpdate();
            if (resultado > 0) {
                System.out.println("Inserção bem-sucedida!");
            } else {
                System.out.println("Erro ao inserir.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Conexao.fecharConexao(this.conn);
    }

    @Override
    public void update(Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ?, idade = ? WHERE id = ?";
        this.conn = Conexao.getConexao();

        try ( PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            pstmt.setString(1, aluno.getNome());
            pstmt.setInt(2, aluno.getIdade());
            pstmt.setInt(3, aluno.getId());

            int resultado = pstmt.executeUpdate();
            if (resultado > 0) {
                System.out.println("Atualização bem-sucedida!");
            } else {
                System.out.println("Erro ao atualizar.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Conexao.fecharConexao(this.conn);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM aluno WHERE id = " + id;
        this.conn = Conexao.getConexao();

        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {

            int resultado = pstmt.executeUpdate();
            if (resultado > 0) {
                System.out.println("Remoção bem-sucedida!");
            } else {
                System.out.println("Erro ao remover.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Conexao.fecharConexao(this.conn);
    }

    @Override
    public List<Aluno> readAll() {
        List<Aluno> alunos = new ArrayList<>();

        String sql = "SELECT * FROM aluno";
        this.conn = Conexao.getConexao();

        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setIdade(rs.getInt("idade"));


                alunos.add(aluno);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Conexao.fecharConexao(this.conn);

        return alunos;
    }

    @Override
    public Aluno readById(int id) {
        String sql = "SELECT * FROM aluno WHERE id = " + id;
        this.conn = Conexao.getConexao();

        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Aluno aluno = new Aluno();
                aluno.setId(rs.getInt("id"));
                aluno.setNome(rs.getString("nome"));
                aluno.setIdade(rs.getInt("idade"));
                return aluno;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            Conexao.fecharConexao(this.conn);
        }

        return null;
    }
}
