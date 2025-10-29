package com.projeto0s.model;

public class OS {
    private static int contador = 1;
    private int numero;
    private Cliente cliente;
    private String descricao;
    private double preco;
    private boolean ativa;

    public OS(Cliente cliente, String descricao, double preco, boolean ativa) {
        this.cliente = cliente;
        this.descricao = descricao;
        this.preco = preco;
        this.ativa = ativa;
        this.numero = contador++;
    }

    public int getNumero() { return numero; }
    public Cliente getCliente() { return cliente; }
    public String getDescricao() { return descricao; }
    public double getPreco() { return preco; }
    public boolean isAtiva() { return ativa; }
    public void setAtiva(boolean ativa) { this.ativa = ativa; }
}
