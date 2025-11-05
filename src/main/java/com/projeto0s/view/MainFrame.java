package com.projeto0s.view;

import com.projeto0s.dao.ClienteDAO;
import com.projeto0s.dao.OSDAO;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {

    private ClienteDAO clienteDAO;
    private OSDAO osDAO;

    private ClientePanel clientePanel;
    private OSPanel osPanel;
    private OSAbertasPanel abertasPanel;
    private OSAndamentoPanel andamentoPanel;
    private OSFinalizadasPanel finalizadasPanel;

    public MainFrame() {
        setTitle("Sistema de Ordem de Serviço");
        setSize(1000,700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        clienteDAO = new ClienteDAO();
        osDAO = new OSDAO();

        clientePanel = new ClientePanel(clienteDAO);
        osPanel = new OSPanel(clienteDAO, osDAO);
        abertasPanel = new OSAbertasPanel(osDAO);
        andamentoPanel = new OSAndamentoPanel(osDAO);
        finalizadasPanel = new OSFinalizadasPanel(osDAO);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Clientes", clientePanel);
        tabs.addTab("Criar / Listar OS", osPanel);
        tabs.addTab("OS Abertas", abertasPanel);
        tabs.addTab("OS em Andamento", andamentoPanel);
        tabs.addTab("OS Finalizadas", finalizadasPanel);

        // Atualiza painéis ao trocar aba
        tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int i = tabs.getSelectedIndex();
                if (i == 0) clientePanel.atualizar();
                if (i == 1) osPanel.atualizar();
                if (i == 2) abertasPanel.atualizar();
                if (i == 3) andamentoPanel.atualizar();
                if (i == 4) finalizadasPanel.atualizar();
            }
        });

        add(tabs);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
