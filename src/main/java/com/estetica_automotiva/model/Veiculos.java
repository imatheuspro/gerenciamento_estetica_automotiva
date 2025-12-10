package com.estetica_automotiva.model;

public class Veiculos {
    private int id;
    private String modelo;
    private String marca;
    private int ano;
    private String placa;
    private String categoria;
    private int idCliente;

    
    private String nomeCliente;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public int getAno() { return ano; }
    public void setAno(int ano) { this.ano = ano; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    
    public void setCategoria(String categoria) {this.categoria = categoria;}
    public String getCategoria() {return categoria;}
    
    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }
}
