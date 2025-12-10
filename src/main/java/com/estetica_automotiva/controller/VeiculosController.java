package com.estetica_automotiva.controller;

import com.estetica_automotiva.dao.VeiculosDAO;
import com.estetica_automotiva.model.Veiculos;
import com.estetica_automotiva.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class VeiculosController {

    @FXML private TextField campoBuscaPlaca;

    @FXML private TableView<Veiculos> tabelaVeiculos;
    @FXML private TableColumn<Veiculos, String> colModelo;
    @FXML private TableColumn<Veiculos, String> colCategoria;
    @FXML private TableColumn<Veiculos, Integer> colAno;
    @FXML private TableColumn<Veiculos, String> colPlaca;
    @FXML private TableColumn<Veiculos, String> colIdCliente;

    private VeiculosDAO veiculoDAO = new VeiculosDAO();
    private ObservableList<Veiculos> listaVeiculos;

    @FXML
    public void initialize() {

        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("ano"));
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        carregarVeiculos();


        campoBuscaPlaca.textProperty().addListener((obs, oldValue, newValue) -> {
            filtrarVeiculos(newValue);
        });
    }


    private void filtrarVeiculos(String filtro) {
    if (filtro == null || filtro.isEmpty()) {
        tabelaVeiculos.setItems(listaVeiculos);
        return;
    }

    String lower = filtro.toLowerCase();
    ObservableList<Veiculos> filtrado = FXCollections.observableArrayList();

    for (Veiculos v : listaVeiculos) {

        boolean combina =
                (v.getPlaca() != null && v.getPlaca().toLowerCase().contains(lower)) ||
                (v.getModelo() != null && v.getModelo().toLowerCase().contains(lower)) ||
                (v.getCategoria() != null && v.getCategoria().toLowerCase().contains(lower)) ||
                String.valueOf(v.getAno()).contains(lower) ||
                (v.getNomeCliente() != null && v.getNomeCliente().toLowerCase().contains(lower));

        if (combina) {
            filtrado.add(v);
        }
    }

    tabelaVeiculos.setItems(filtrado);
}



    private void carregarVeiculos() {
        listaVeiculos = FXCollections.observableArrayList(veiculoDAO.listarTodos());
        tabelaVeiculos.setItems(listaVeiculos);
    }


    @FXML
    private void excluirVeiculo() {
        Veiculos selecionado = tabelaVeiculos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            veiculoDAO.excluir(selecionado.getId());
            listaVeiculos.remove(selecionado);
        }
    }


    @FXML
    private void editarVeiculo() {
        Veiculos selecionado = tabelaVeiculos.getSelectionModel().getSelectedItem();

        if (selecionado != null) {

            EditarVeiculoController.veiculoEmEdicao = selecionado;

            ScreenManager.changeScene("editarVeiculo.fxml");
        }
    }

    @FXML
    private void voltarInicio() {
        ScreenManager.changeScene("menu.fxml");
    }
}
