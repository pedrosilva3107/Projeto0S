package com.projeto0s.view;

import com.projeto0s.dao.OSDAO;
import com.projeto0s.model.OS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OSFinalizadasPanel extends JPanel {

    private OSDAO osDAO;
    private DefaultTableModel model;

    public OSFinalizadasPanel(OSDAO dao) {
        this.osDAO = dao;
        setLayout(new BorderLayout(8,8));
        model = new DefaultTableModel(new String[]{"ID","Cliente","Descrição","Prioridade","Valor"},0);
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);
        atualizar();
    }

    public void atualizar() {
        model.setRowCount(0);
        for (OS os : osDAO.listarPorStatus("FINALIZADA")) {
            model.addRow(new Object[]{os.getId(), os.getCliente().getNome(), os.getDescricao(), os.getPrioridade(), os.getValor()});
        }
    }
}
