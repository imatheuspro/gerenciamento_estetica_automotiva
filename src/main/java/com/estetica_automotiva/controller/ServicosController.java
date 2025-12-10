package com.estetica_automotiva.controller;

import com.estetica_automotiva.dao.ServicoDAO;
import com.estetica_automotiva.model.Servicos;
import com.estetica_automotiva.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ServicosController {

    @FXML private TextField nomeField;
    @FXML private TextField veiculoField;
    @FXML private TextField servicoField;
    @FXML private TextField precoField;
    @FXML private TextField campoBusca;

    @FXML private TableView<Servicos> tabelaServicos;
    @FXML private TableColumn<Servicos, String> colIdCliente;
    @FXML private TableColumn<Servicos, Integer> colVeiculo_id;
    @FXML private TableColumn<Servicos, String> colServico;
    @FXML private TableColumn<Servicos, Double> colPreco;
    @FXML private TableColumn<Servicos, Double> colPlaca;
    @FXML private Button voltar;

    private ServicoDAO servicoDAO = new ServicoDAO();
    private ObservableList<Servicos> listaCompleta = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colIdCliente.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
        colVeiculo_id.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colServico.setCellValueFactory(new PropertyValueFactory<>("servico"));
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colPlaca.setCellValueFactory(new PropertyValueFactory<>("placa"));

        carregarServicos();
        campoBusca.textProperty().addListener((observable, oldValue, newValue) -> filtrarServicos(newValue));

    }
    private void filtrarServicos(String filtro) {
    if (filtro == null || filtro.isEmpty()) {
        tabelaServicos.setItems(listaCompleta);
        return;
    }

    String lower = filtro.toLowerCase();
    ObservableList<Servicos> filtrado = FXCollections.observableArrayList();

    for (Servicos s : listaCompleta) {
        boolean combina =
                s.getNomeCliente().toLowerCase().contains(lower) ||   
                s.getModelo().toLowerCase().contains(lower) ||     
                s.getServico().toLowerCase().contains(lower) ||       
                String.valueOf(s.getPreco()).contains(lower);         

        if (combina) {
            filtrado.add(s);
        }
    }

    tabelaServicos.setItems(filtrado);
}


    private void carregarServicos() {
        listaCompleta.setAll(servicoDAO.listarTodos());
        tabelaServicos.setItems(listaCompleta);
    }


    @FXML
    public void salvarServico() {
        Servicos s = new Servicos();

        s.setServico(servicoField.getText());
        s.setPreco(Double.parseDouble(precoField.getText()));

        servicoDAO.salvar(s);  
        
        carregarServicos();
        limparCampos();
    }

    @FXML
    public void editarServico() {
        Servicos selecionado = tabelaServicos.getSelectionModel().getSelectedItem();

        if (selecionado != null) {
            abrirFormulario(selecionado);
        } else {
            System.out.println("Nenhum serviço selecionado.");
        }
    }
    

    @FXML
    public void atualizarServico() {
        Servicos selecionado = tabelaServicos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {

            selecionado.setServico(servicoField.getText());
            selecionado.setPreco(Double.parseDouble(precoField.getText()));

            servicoDAO.atualizar(selecionado);
            carregarServicos();
            limparCampos();
        }
    }

    @FXML
    public void excluirServico() {
        Servicos selecionado = tabelaServicos.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            servicoDAO.excluir(selecionado);
            carregarServicos();
        }
    }
    private void abrirFormulario(Servicos servico) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/estetica_automotiva/view/editarServico.fxml"));
        Parent root = loader.load();

        editarServicoController controller = loader.getController();
        controller.setServico(servico);      
        controller.setOnSaveCallback(this::carregarServicos); 

        Stage stage = new Stage();
        stage.setTitle("Editar Serviço");
        stage.setScene(new Scene(root));
        stage.show();

    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @FXML
    public void voltarInicio() {
        ScreenManager.changeScene("menu.fxml");
    }

    private void limparCampos() {
        nomeField.clear();
        veiculoField.clear();
        servicoField.clear();
        precoField.clear();
    }
}
