package com.estetica_automotiva.model;


public class Servicos {

    private int id;
    private String nomeCliente;
    private String telefoneCliente;
    private String emailCliente;
    private String cpfCliente;

    private int veiculoId;
    private int clienteId;

   
    private String placa;
    private String modelo;
    private int ano;
    private String categoria;

    private String servico;
    private double preco;

    public int getVeiculoId() {
    return veiculoId;
}

public void setVeiculoId(int veiculoId) {
    this.veiculoId = veiculoId;
}
 public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNomeCliente() { return nomeCliente; }
    public void setNomeCliente(String nomeCliente) { this.nomeCliente = nomeCliente; }

    public String getTelefoneCliente() { return telefoneCliente; }
    public void setTelefoneCliente(String telefoneCliente) { this.telefoneCliente = telefoneCliente; }

    public String getEmailCliente() { return emailCliente; }
    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }

    public String getCpfCliente() { return cpfCliente; }
    public void setCpfCliente(String cpfCliente) { this.cpfCliente = cpfCliente; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAno() { return ano; }
    public void setAno(int anoVeiculo) { this.ano = anoVeiculo; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getServico() { return servico; }
    public void setServico(String servico) { this.servico = servico; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }
}