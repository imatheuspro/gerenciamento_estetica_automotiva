package com.estetica_automotiva.model;

public class Clientes {
    private int id;

   
    private String nome;
    private String telefone;
    private String email;
    private String cpf;

    public Clientes(String nome, String telefone, String email, String cpf) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
    }
    public Clientes() {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
    }
    public String getNome() { return nome; }
    public String getTelefone() { return telefone; }
    public String getEmail() { return email; }
    public String getCpf() { return cpf; }

    public void setNome(String nome) { this.nome = nome; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEmail(String email) { this.email = email; }
    public void setCpf(String cpf) { this.cpf = cpf; } 
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
