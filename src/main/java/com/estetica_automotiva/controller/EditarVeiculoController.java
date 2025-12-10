package com.estetica_automotiva.controller;

import com.estetica_automotiva.dao.VeiculosDAO;
import com.estetica_automotiva.model.Veiculos;
import com.estetica_automotiva.util.ScreenManager;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class EditarVeiculoController {

    // Objeto enviado pela outra tela
    public static Veiculos veiculoEmEdicao;

    @FXML private TextField campoModelo;
    @FXML private TextField campoAno;
    @FXML private TextField campoPlaca;
    @FXML private ComboBox<String> comboCategoria;
    @FXML private TextField campoCliente;

    private VeiculosDAO veiculoDAO = new VeiculosDAO();

    @FXML
    public void initialize() {
        comboCategoria.getItems().addAll(
                "Hatch", "Sedan", "SUV", "Picape", "Moto", "Utilitário"
        );

        if (veiculoEmEdicao != null) {
            campoModelo.setText(veiculoEmEdicao.getModelo());
            campoAno.setText(String.valueOf(veiculoEmEdicao.getAno()));
            campoPlaca.setText(veiculoEmEdicao.getPlaca());
            comboCategoria.setValue(veiculoEmEdicao.getCategoria());
            campoCliente.setText(veiculoEmEdicao.getNomeCliente());

            campoCliente.setDisable(true);
        }
    }

    @FXML
    private void salvarAlteracoes() {
        try {
            veiculoEmEdicao.setModelo(campoModelo.getText());
            veiculoEmEdicao.setAno(Integer.parseInt(campoAno.getText()));
            veiculoEmEdicao.setPlaca(campoPlaca.getText());
            veiculoEmEdicao.setCategoria(comboCategoria.getValue());

            veiculoDAO.atualizar(veiculoEmEdicao);

            ScreenManager.changeScene("veiculos.fxml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void voltar() {
        ScreenManager.changeScene("veiculos.fxml");
    }
}
