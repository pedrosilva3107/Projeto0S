package com.projeto0s.model;

public class Cliente {
    private int id;
    private String nome;
    private String cpfCnpj;
    private String rua;
    private String numero;
    private String bairro;

    public Cliente(String nome, String cpfCnpj, String rua, String numero, String bairro) {
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public String getCpfCnpj() { return cpfCnpj; }
    public String getRua() { return rua; }
    public String getNumero() { return numero; }
    public String getBairro() { return bairro; }

    public void setId(int id) { this.id = id; }

    @Override
    public String toString() {
        return nome;
    }
}
