package com.projeto0s.model;

public class OS {
    private int id;
    private Cliente cliente;
    private String descricao;
    private String prioridade;
    private String status;
    private double valor;

    public OS() {}

    public OS(Cliente cliente, String descricao, String prioridade, double valor) {
        this.cliente = cliente;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.valor = valor;
        this.status = "ABERTA";
    }

    public int getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public String getDescricao() { return descricao; }
    public String getPrioridade() { return prioridade; }
    public String getStatus() { return status; }
    public double getValor() { return valor; }

    public void setId(int id) { this.id = id; }
    public void setStatus(String status) { this.status = status; }
}
