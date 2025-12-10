package com.estetica_automotiva.controller;

import com.estetica_automotiva.dao.ClienteDAO;
import com.estetica_automotiva.model.Clientes;
import com.estetica_automotiva.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class editarClienteController {

    @FXML private TextField nomeField;
    @FXML private TextField telefoneField;
    @FXML private TextField emailField;
    @FXML private TextField cpfField;

    private Clientes cliente;
    private ClienteDAO clienteDAO = new ClienteDAO();

    public void setCliente(Clientes cliente) {
        this.cliente = cliente;
        nomeField.setText(cliente.getNome());
        telefoneField.setText(cliente.getTelefone());
        emailField.setText(cliente.getEmail());
        cpfField.setText(cliente.getCpf());
    }

    @FXML
    public void salvarEdicao() {
        cliente.setNome(nomeField.getText());
        cliente.setTelefone(telefoneField.getText());
        cliente.setEmail(emailField.getText());
        cliente.setCpf(cpfField.getText());

        clienteDAO.atualizar(cliente);

        Stage stage = (Stage) nomeField.getScene().getWindow();
        stage.close();
        
    }
    
    @FXML
    private void voltar() {
        Stage stage = (Stage) nomeField.getScene().getWindow();
        stage.close(); 
        ScreenManager.changeScene("clientes.fxml");
    }
    
}
