package br.com.os;

public class OrdemDeServico implements Fechavel {
    private int id;
    private String assunto;
    private double valor;
    private String status; // ABERTA ou FECHADA
    private Cliente cliente;
    private Tecnico tecnico;

    public OrdemDeServico(int id, String assunto, double valor, Cliente cliente, Tecnico tecnico) {
        this.id = id;
        this.assunto = assunto;
        this.valor = valor;
        this.cliente = cliente;
        this.tecnico = tecnico;
        this.status = "ABERTA";
    }

    public int getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public void exibirDetalhes() {
        System.out.println("OS #" + id + " | Assunto: " + assunto + " | Valor: R$" + valor);
        System.out.println("Cliente: " + cliente.getNome() + " | Técnico: " + tecnico.getNome() + " | Status: " + status);
        System.out.println("--------------------------------------------------");
    }

    @Override
    public void fecharOS() throws OSJaFechadaException {
        if (status.equals("FECHADA")) {
            throw new OSJaFechadaException("A OS #" + id + " já está fechada!");
        }
        status = "FECHADA";
        System.out.println("OS #" + id + " foi fechada com sucesso.");
    }
}
