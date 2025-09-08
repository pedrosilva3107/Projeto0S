package br.com.os;

public class Tecnico extends Pessoa {
    private String especialidade;

    public Tecnico(String nome, String email, String especialidade) {
        super(nome, email);
        this.especialidade = especialidade;
    }

    @Override
    public void exibirInfo() {
        System.out.println("Técnico: " + nome + " | Especialidade: " + especialidade);
    }

    // O getter deve ficar dentro da classe
    public String getNome() {
        return nome;
    }
}
