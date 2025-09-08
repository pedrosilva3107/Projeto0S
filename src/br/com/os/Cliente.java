package br.com.os;

public class Cliente extends Pessoa {
    private String cpf;

    public Cliente(String nome, String email, String cpf) {
        super(nome, email);
        this.cpf = cpf;
    }

    @Override
    public void exibirInfo() {
        System.out.println("Cliente: " + nome + " | CPF: " + cpf);
    }
    public String getNome() {
        return nome;
    }

}
