/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import io.github.joildo.java.backend.data.Ficha;
import io.github.joildo.java.backend.infra.ConexaoJDBC;
import io.github.joildo.java.backend.infra.ConexaoPostgresJDBC;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aluno
 */
public class FichaDAO {

    private final ConexaoJDBC conexao;

    public FichaDAO() throws SQLException, ClassNotFoundException {
        this.conexao = new ConexaoPostgresJDBC();
    }

    public Long inserir(Ficha chamado) throws SQLException, ClassNotFoundException {
        Long id = null;
        String sqlQuery = "INSERT INTO chamado (assunto, mensagem) VALUES (?, ?) RETURNING id";

        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, chamado.getAssunto());
            //stmt.setString(2, chamado.getStatus().toString());
            stmt.setString(2, chamado.getMensagem());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getLong("id");
            }

            this.conexao.commit();
        } catch (SQLException e) {
            this.conexao.rollback();
            throw e;
        }

        return id;
    }

    public int alterar(Ficha chamado) throws SQLException, ClassNotFoundException {
        String sqlQuery = "UPDATE chamado SET assunto = ?,  mensagem = ? WHERE id = ?";
        int linhasAfetadas = 0;

        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
            stmt.setString(1, chamado.getAssunto());
           // stmt.setString(2, chamado.getStatus().toString());
            stmt.setString(2, chamado.getMensagem());
            stmt.setLong(3, chamado.getId());

            linhasAfetadas = stmt.executeUpdate();
            this.conexao.commit();
        } catch (SQLException e) {
            this.conexao.rollback();
            throw e;
        }

        return linhasAfetadas;
    }

    public int excluir(long id) throws SQLException, ClassNotFoundException {
        int linhasAlfetadas = 0;
        String sqlQuery = "DELETE FROM chamado WHERE id = ?";

        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
            stmt.setLong(1, id);
            linhasAlfetadas = stmt.executeUpdate();
            this.conexao.commit();
        } catch (SQLException e) {
            this.conexao.rollback();
            throw e;
        }

        return linhasAlfetadas;
    }

    public Ficha selecionar(long id) throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM chamado WHERE id = ?";

        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return parser(rs);
            }
        } catch (SQLException e) {
            throw e;
        }

        return null;
    }

    public List<Ficha> listar() throws SQLException, ClassNotFoundException {
        String sqlQuery = "SELECT * FROM chamado ORDER BY id";

        try {
            PreparedStatement stmt = this.conexao.getConnection().prepareStatement(sqlQuery);
            ResultSet rs = stmt.executeQuery();

            List<Ficha> chamados = new ArrayList<>();

            while (rs.next()) {
                chamados.add(parser(rs));
            }

            return chamados;
        } catch (SQLException e) {
            throw e;
        }
    }

    private Ficha parser(ResultSet resultSet) throws SQLException {
        Ficha c = new Ficha();

        c.setId(resultSet.getLong("id"));
        c.setAssunto(resultSet.getString("assunto"));
        c.setMensagem(resultSet.getString("mensagem"));
       // c.setStatus(Status.valueOf(resultSet.getString("status")));

        return c;
    }
}
