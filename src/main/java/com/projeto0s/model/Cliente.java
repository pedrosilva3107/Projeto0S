package com.projeto0s.model;

public class Cliente {
    private String nome, rua, numero, bairro, cpfCnpj, fotoPath;

    public Cliente(String nome, String rua, String numero, String bairro, String cpfCnpj, String fotoPath) {
        this.nome = nome;
        this.rua = rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cpfCnpj = cpfCnpj;
        this.fotoPath = fotoPath;
    }

    public String getNome() { return nome; }
    public String getRua() { return rua; }
    public String getNumero() { return numero; }
    public String getBairro() { return bairro; }
    public String getCpfCnpj() { return cpfCnpj; }
    public String getFotoPath() { return fotoPath; }

    public void setNome(String nome) { this.nome = nome; }
    public void setRua(String rua) { this.rua = rua; }
    public void setNumero(String numero) { this.numero = numero; }
    public void setBairro(String bairro) { this.bairro = bairro; }
    public void setCpfCnpj(String cpfCnpj) { this.cpfCnpj = cpfCnpj; }
    public void setFotoPath(String fotoPath) { this.fotoPath = fotoPath; }
}
