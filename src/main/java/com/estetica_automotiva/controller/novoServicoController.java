package com.estetica_automotiva.controller;

import com.estetica_automotiva.dao.ServicoDAO;
import com.estetica_automotiva.model.Servicos;
import com.estetica_automotiva.util.ScreenManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class novoServicoController {

    @FXML private ComboBox<String> comboCategoria;
    @FXML private ComboBox<String> comboServico;

    @FXML private TextField nomeCliente;
    @FXML private TextField cpfCliente;
    @FXML private TextField emailCliente;
    @FXML private TextField telefoneCliente;
    @FXML private TextField modeloVeiculo;
    @FXML private TextField anoVeiculo;
    @FXML private TextField placaVeiculo;
    @FXML private TextField precoServico;

    @FXML
    public void initialize() {
        comboCategoria.getItems().addAll("Hatch", "Sedan", "SUV", "Caminhonete", "Picape", "Minivan", "Esportivo", "Moto", "Outro");
        comboServico.getItems().addAll("Lavagem detalhada", "Higienização interna", "Polimento técnico", "Vitrificação de pintura", "Revitalização de faróis", "Tratamento de couro", "Aplicação de película protetora", "Outro");
    }

   @FXML
    void salvarDados(ActionEvent event) {

        if (nomeCliente.getText().isEmpty() ||
            cpfCliente.getText().isEmpty() ||
            emailCliente.getText().isEmpty() ||
            telefoneCliente.getText().isEmpty() ||
            modeloVeiculo.getText().isEmpty() ||
            anoVeiculo.getText().isEmpty() ||
            placaVeiculo.getText().isEmpty() ||
            precoServico.getText().isEmpty() ||
            comboCategoria.getValue() == null ||
            comboServico.getValue() == null) {

            mostrarAlerta("Preencha todos os campos antes de salvar!");
            return; 
        }

        try {
            Integer.parseInt(anoVeiculo.getText());
            Double.parseDouble(precoServico.getText());
        } catch (NumberFormatException e) {
            mostrarAlerta("Ano e Preço devem ser números válidos!");
            return;
        }

        Servicos s = new Servicos();

        s.setNomeCliente(nomeCliente.getText());
        s.setCpfCliente(cpfCliente.getText());
        s.setEmailCliente(emailCliente.getText());
        s.setTelefoneCliente(telefoneCliente.getText());
        s.setModelo(modeloVeiculo.getText());
        s.setAno(Integer.parseInt(anoVeiculo.getText()));
        s.setPlaca(placaVeiculo.getText());
        s.setCategoria(comboCategoria.getValue());
        s.setServico(comboServico.getValue());
        s.setPreco(Double.parseDouble(precoServico.getText()));

        ServicoDAO.salvar(s);

        ScreenManager.changeScene("servicos.fxml");
    }

    @FXML
    void voltarInicio(ActionEvent event) {
        ScreenManager.changeScene("menu.fxml");
    }
    private void mostrarAlerta(String mensagem) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

}