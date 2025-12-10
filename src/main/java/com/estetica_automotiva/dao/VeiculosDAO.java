package com.estetica_automotiva.dao;

import com.estetica_automotiva.model.Veiculos;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VeiculosDAO {

    private Connection connection;

    public VeiculosDAO() {
        this.connection = Database.getConnection();
    }

    public void salvar(Veiculos v) {
        String sql = "INSERT INTO veiculo (modelo, marca, ano, placa, cliente_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, v.getModelo());
            stmt.setDouble(3, v.getAno());
            stmt.setString(4, v.getPlaca());
            stmt.setInt(5, v.getIdCliente());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Veiculos> listarTodos() {
    List<Veiculos> lista = new ArrayList<>();
    String sql = "SELECT v.id, v.placa, v.modelo, v.ano, v.categoria, c.nome AS nome_cliente " +
                 "FROM veiculo v " +
                 "JOIN cliente c ON v.cliente_id = c.id";

    try (PreparedStatement stmt = connection.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {
        while (rs.next()) {
            Veiculos v = new Veiculos();
            v.setId(rs.getInt("id"));
            v.setPlaca(rs.getString("placa"));
            v.setModelo(rs.getString("modelo"));
            v.setAno(rs.getInt("ano"));
            v.setCategoria(rs.getString("categoria"));
            v.setNomeCliente(rs.getString("nome_cliente"));
            lista.add(v);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
}

    public void atualizar(Veiculos v) {
    String sql = "UPDATE veiculo SET modelo = ?, ano = ?, placa = ?, categoria = ? WHERE id = ?";
    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, v.getModelo());
        stmt.setInt(2, v.getAno());
        stmt.setString(3, v.getPlaca());
        stmt.setString(4, v.getCategoria());
        stmt.setInt(5, v.getId());

        stmt.executeUpdate();

    } catch (SQLException e) {
        e.printStackTrace();
    }
}

   public void excluir(int id) {
        String sql = "DELETE FROM veiculo WHERE id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
