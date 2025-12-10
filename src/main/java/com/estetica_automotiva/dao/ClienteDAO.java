package com.estetica_automotiva.dao;
import com.estetica_automotiva.model.Clientes;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

   public List<Clientes> listarTodos() {
    List<Clientes> lista = new ArrayList<>();
    String sql = "SELECT id, nome, telefone, email, cpf FROM cliente";

    try (Connection con = Database.getConnection();
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            Clientes c = new Clientes();
            c.setId(rs.getInt("id"));
            c.setNome(rs.getString("nome"));
            c.setTelefone(rs.getString("telefone"));
            c.setEmail(rs.getString("email"));
            c.setCpf(rs.getString("cpf"));
            lista.add(c);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}


    public void salvar(Clientes c) {
        String sql = "INSERT INTO cliente(nome, telefone, email, cpf) VALUES(?, ?, ?, ?)";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getNome());
            ps.setString(2, c.getTelefone());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getCpf());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void atualizar(Clientes c) {
        String sql = "UPDATE cliente SET nome = ?, telefone = ?, email = ?, cpf = ? WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getNome());
            ps.setString(2, c.getTelefone());
            ps.setString(3, c.getEmail());
            ps.setString(4, c.getCpf());
            ps.setInt(5, c.getId()); 

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(Clientes c) {
        String sql = "DELETE FROM cliente WHERE cpf = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCpf());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
