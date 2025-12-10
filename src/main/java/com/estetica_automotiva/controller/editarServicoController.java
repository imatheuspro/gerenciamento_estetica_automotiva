package com.estetica_automotiva.controller;

import com.estetica_automotiva.dao.ServicoDAO;
import com.estetica_automotiva.model.Servicos;
import com.estetica_automotiva.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class editarServicoController {

    @FXML private TextField txtCliente;
    @FXML private TextField txtModelo;
    @FXML private TextField txtPlaca;
    

    @FXML private ComboBox<String> comboServico;
    @FXML private TextField txtPreco;

    private Servicos servico;
    private ServicoDAO servicoDAO = new ServicoDAO();

    private Runnable onSaveCallback;

    public void setOnSaveCallback(Runnable callback) {
        this.onSaveCallback = callback;
    }

    public void setServico(Servicos servico) {
        this.servico = servico;
               comboServico.getItems().addAll("Lavagem detalhada", "Higienização interna", "Polimento técnico", "Vitrificação de pintura", "Revitalização de faróis", "Tratamento de couro", "Aplicação de película protetora", "Outro");

        txtCliente.setText(servico.getNomeCliente());
        txtModelo.setText(servico.getModelo());
        txtPlaca.setText(servico.getPlaca());
        
        
        txtCliente.setDisable(true);
        txtModelo.setDisable(true);
        txtPlaca.setDisable(true);
        
        
        comboServico.setValue(servico.getServico());
        txtPreco.setText(String.valueOf(servico.getPreco()));
    }

    @FXML
    private void salvar() {
        servico.setServico(comboServico.getValue());
        servico.setPreco(Double.parseDouble(txtPreco.getText()));

        servicoDAO.atualizar(servico);

        if (onSaveCallback != null) {
            onSaveCallback.run();
        }

        fecharJanela();
    }

    @FXML
    private void voltar() {
        fecharJanela();
    }

    private void fecharJanela() {
        Stage stage = (Stage) comboServico.getScene().getWindow();
        stage.close();
    }
}
