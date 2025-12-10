package com.estetica_automotiva.dao;

import com.estetica_automotiva.dao.Database;
import com.estetica_automotiva.model.Servicos;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicoDAO {

    public static void salvar(Servicos s) {
        String sql = "INSERT INTO servicos (nome_cliente, telefone_cliente, email_cliente, cpf_cliente, placa, modelo, ano, categoria, tipo_servico, preco) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, s.getNomeCliente());
            stmt.setString(2, s.getTelefoneCliente());
            stmt.setString(3, s.getEmailCliente());
            stmt.setString(4, s.getCpfCliente());
            stmt.setString(5, s.getPlaca());
            stmt.setString(6, s.getModelo());
            stmt.setInt(7, s.getAno());
            stmt.setString(8, s.getCategoria());
            stmt.setString(9, s.getServico());
            stmt.setDouble(10, s.getPreco());

            stmt.executeUpdate();
            System.out.println("Serviço salvo na tabela servicos (trigger será executada).");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Servicos> listarTodos() {
        List<Servicos> lista = new ArrayList<>();

        String sql = """
            SELECT s.id,
                   c.nome AS nome_cliente,
                   v.modelo AS modelo_veiculo,
                   v.placa AS placa_veiculo,
                   s.tipo_servico,
                   s.preco
            FROM servico s
            JOIN cliente c ON s.cliente_id = c.id
            JOIN veiculo v ON s.veiculo_id = v.id
            ORDER BY s.id DESC
        """;

        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Servicos s = new Servicos();
                s.setId(rs.getInt("id"));
                s.setNomeCliente(rs.getString("nome_cliente"));
                s.setModelo(rs.getString("modelo_veiculo"));
                s.setServico(rs.getString("tipo_servico"));
                s.setPreco(rs.getDouble("preco"));
                s.setPlaca(rs.getString("placa_veiculo"));


                lista.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    

    public void atualizar(Servicos s) {
    String sql = "UPDATE servico SET tipo_servico = ?, preco = ? WHERE id = ?";

    try (Connection conn = Database.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, s.getServico());
        stmt.setDouble(2, s.getPreco());
        stmt.setInt(3, s.getId());

        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}




    public void excluir(Servicos s) {
        String sql = "DELETE FROM servico WHERE id = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, s.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
