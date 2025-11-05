package com.projeto0s.view;

import com.projeto0s.dao.OSDAO;
import com.projeto0s.model.OS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OSAbertasPanel extends JPanel {

    private OSDAO osDAO;
    private DefaultTableModel model;
    private JTable table;

    public OSAbertasPanel(OSDAO osDAO) {
        this.osDAO = osDAO;
        setLayout(new BorderLayout(8,8));

        model = new DefaultTableModel(new String[]{"ID","Cliente","Descrição","Prioridade","Valor"},0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton btnAndamento = new JButton("Colocar em ANDAMENTO");
        btnAndamento.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row == -1) { JOptionPane.showMessageDialog(this,"Selecione uma OS"); return; }
            int id = (int) model.getValueAt(row,0);
            osDAO.atualizarStatus(id, "ANDAMENTO");
            atualizar();
        });
        add(btnAndamento, BorderLayout.SOUTH);

        atualizar();
    }

    public void atualizar() {
        model.setRowCount(0);
        for (OS os : osDAO.listarPorStatus("ABERTA")) {
            model.addRow(new Object[]{os.getId(), os.getCliente().getNome(), os.getDescricao(), os.getPrioridade(), os.getValor()});
        }
    }
}
