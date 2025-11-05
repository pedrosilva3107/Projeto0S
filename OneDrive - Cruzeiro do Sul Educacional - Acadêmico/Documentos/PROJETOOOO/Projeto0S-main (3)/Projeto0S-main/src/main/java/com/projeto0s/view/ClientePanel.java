package com.projeto0s.view;

import com.projeto0s.dao.ClienteDAO;
import com.projeto0s.model.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ClientePanel extends JPanel {

    private ClienteDAO dao;
    private DefaultTableModel model;
    private JTable table;

    public ClientePanel(ClienteDAO dao) {
        this.dao = dao;
        setLayout(new BorderLayout(10,10));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnAdd = new JButton("Cadastrar Cliente");
        top.add(btnAdd);
        add(top, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[]{"ID","Nome","CPF/CNPJ","Rua","Número","Bairro"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(e -> abrirCadastro());

        atualizar();
    }

    private void abrirCadastro() {
        JTextField nome = new JTextField();
        JTextField cpf = new JTextField();
        JTextField rua = new JTextField();
        JTextField numero = new JTextField();
        JTextField bairro = new JTextField();

        Object[] campos = {
                "Nome:", nome,
                "CPF/CNPJ:", cpf,
                "Rua:", rua,
                "Número:", numero,
                "Bairro:", bairro
        };

        int res = JOptionPane.showConfirmDialog(this, campos, "Novo Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            if (nome.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome obrigatório");
                return;
            }
            Cliente c = new Cliente(nome.getText().trim(), cpf.getText().trim(), rua.getText().trim(),
                    numero.getText().trim(), bairro.getText().trim());
            dao.adicionar(c);
            atualizar();
            JOptionPane.showMessageDialog(this, "Cliente cadastrado!");
        }
    }

    public void atualizar() {
        model.setRowCount(0);
        for (Cliente c : dao.listar()) {
            model.addRow(new Object[]{c.getId(), c.getNome(), c.getCpfCnpj(), c.getRua(), c.getNumero(), c.getBairro()});
        }
    }
}
