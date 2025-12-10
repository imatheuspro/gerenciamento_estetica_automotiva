package com.estetica_automotiva.controller;

import com.estetica_automotiva.util.ScreenManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MenuController {

    @FXML
    public void criarServico(ActionEvent event) {
        ScreenManager.changeScene("novo-servico.fxml");
    }

    @FXML
    public void abrirClientes(ActionEvent event) {
        ScreenManager.changeScene("clientes.fxml");
    }

    @FXML
    public void abrirVeiculos(ActionEvent event) {
        ScreenManager.changeScene("veiculos.fxml");
    }

    @FXML
    public void abrirServicos(ActionEvent event) {
        ScreenManager.changeScene("servicos.fxml");
    }
    @FXML
    public void Sair(ActionEvent event) {
        Platform.exit();
    }
}
