package com.estetica_automotiva.controller;

import com.estetica_automotiva.dao.ClienteDAO;
import com.estetica_automotiva.model.Clientes;
import com.estetica_automotiva.util.ScreenManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class clientesController {

    @FXML private TextField nomeField;
    @FXML private TextField telefoneField;
    @FXML private TextField emailField;
    @FXML private TextField cpfField;
    @FXML private TextField campoBusca;

    @FXML private TableView<Clientes> tabelaClientes;
    @FXML private TableColumn<Clientes, String> colNome;
    @FXML private TableColumn<Clientes, String> colTelefone;
    @FXML private TableColumn<Clientes, String> colEmail;

    private ClienteDAO clienteDAO = new ClienteDAO();
    private Clientes clienteSelecionado = null;

    private ObservableList<Clientes> listaClientes; 

    @FXML
    public void initialize() {

        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        carregarClientes();
        configurarFiltro();
    }

    private void carregarClientes() {
        listaClientes = FXCollections.observableArrayList(clienteDAO.listarTodos());
        tabelaClientes.setItems(listaClientes); 
    }

    private void configurarFiltro() {

        FilteredList<Clientes> filtro = new FilteredList<>(listaClientes, p -> true);

        campoBusca.textProperty().addListener((obs, oldValue, newValue) -> {
            filtro.setPredicate(cliente -> {

                if (newValue == null || newValue.isEmpty()) return true;

                String busca = newValue.toLowerCase();

                return cliente.getNome().toLowerCase().contains(busca)
                    || cliente.getTelefone().toLowerCase().contains(busca)
                    || cliente.getEmail().toLowerCase().contains(busca)
                    || cliente.getCpf().toLowerCase().contains(busca);
            });
        });

        SortedList<Clientes> ordenada = new SortedList<>(filtro);
        ordenada.comparatorProperty().bind(tabelaClientes.comparatorProperty());

        tabelaClientes.setItems(ordenada);
    }

    
    @FXML
void editarCliente() {
    Clientes selecionado = tabelaClientes.getSelectionModel().getSelectedItem();

    if (selecionado != null) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/estetica_automotiva/view/editarClientes.fxml"));
            Parent root = loader.load();

            editarClienteController controller = loader.getController();
            controller.setCliente(selecionado);

            Stage stage = new Stage();
            stage.setTitle("Editar Cliente");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            carregarClientes(); 
            configurarFiltro();  

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


    @FXML
    public void salvarCliente() {
        if (clienteSelecionado != null) {
            clienteSelecionado.setNome(nomeField.getText());
            clienteSelecionado.setTelefone(telefoneField.getText());
            clienteSelecionado.setEmail(emailField.getText());
            clienteSelecionado.setCpf(cpfField.getText());

            clienteDAO.atualizar(clienteSelecionado);
            clienteSelecionado = null;
        } else {
            Clientes c = new Clientes();
            c.setNome(nomeField.getText());
            c.setTelefone(telefoneField.getText());
            c.setEmail(emailField.getText());
            c.setCpf(cpfField.getText());

            clienteDAO.salvar(c);
        }

        carregarClientes();
        configurarFiltro(); 
        limparCampos();
    }

    private void limparCampos() {
        nomeField.clear();
        telefoneField.clear();
        emailField.clear();
        cpfField.clear();
    }

    @FXML
    void excluirCliente() {
        Clientes selecionado = tabelaClientes.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            clienteDAO.excluir(selecionado);
            carregarClientes();
            configurarFiltro();
        }
    }

    @FXML
    void voltarInicio() {
        ScreenManager.changeScene("menu.fxml");
    }
}
