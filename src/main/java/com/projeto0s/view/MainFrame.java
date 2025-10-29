package com.projeto0s.view;

import com.projeto0s.model.Cliente;
import com.projeto0s.model.OS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel osAtivasPanel, chamadosInativosPanel;
    private JTable osAtivasTable, chamadosInativosTable;
    private DefaultTableModel osAtivasModel, chamadosInativosModel;
    private ArrayList<OS> osList;
    private ArrayList<OS> osInativosList;
    private JTextField searchField;

    public MainFrame() {
        setTitle("Sistema de OS - Empresa");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        osList = new ArrayList<>();
        osInativosList = new ArrayList<>();

        tabbedPane = new JTabbedPane();
        osAtivasPanel = new JPanel(new BorderLayout());
        chamadosInativosPanel = new JPanel(new BorderLayout());

        setupOSAtivasTab();
        setupChamadosInativosTab();

        tabbedPane.addTab("OS Ativas", osAtivasPanel);
        tabbedPane.addTab("Chamados Inativos", chamadosInativosPanel);

        add(tabbedPane);
        setVisible(true);
    }

    private void setupOSAtivasTab() {
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton createOSButton = new JButton("Criar O.S.");
        searchField = new JTextField();
        topPanel.add(createOSButton, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        osAtivasPanel.add(topPanel, BorderLayout.NORTH);

        String[] columns = {"Número", "Cliente", "Serviço", "Preço", "Status"};
        osAtivasModel = new DefaultTableModel(columns, 0);
        osAtivasTable = new JTable(osAtivasModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(osAtivasModel);
        osAtivasTable.setRowSorter(sorter);
        osAtivasPanel.add(new JScrollPane(osAtivasTable), BorderLayout.CENTER);

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        createOSButton.addActionListener(e -> {
            OS novaOS = criarOSDialog();
            if (novaOS != null) {
                if (!novaOS.isAtiva()) {
                    osInativosList.add(novaOS);
                    chamadosInativosModel.addRow(new Object[]{novaOS.getNumero(), novaOS.getCliente().getNome(),
                            novaOS.getDescricao(), novaOS.getPreco(), "Fechada"});
                } else {
                    osList.add(novaOS);
                    osAtivasModel.addRow(new Object[]{novaOS.getNumero(), novaOS.getCliente().getNome(),
                            novaOS.getDescricao(), novaOS.getPreco(), "Ativa"});
                }
            }
        });

        osAtivasTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = osAtivasTable.getSelectedRow();
                if (row >= 0) {
                    int modelRow = osAtivasTable.convertRowIndexToModel(row);
                    OS os = osList.get(modelRow);

                    // Clique duplo para fechar OS
                    if (e.getClickCount() == 2) {
                        int option = JOptionPane.showConfirmDialog(null,
                                "Deseja fechar esta O.S.?", "Fechar OS",
                                JOptionPane.YES_NO_OPTION);
                        if (option == JOptionPane.YES_OPTION) {
                            os.setAtiva(false);
                            osList.remove(os);
                            osAtivasModel.removeRow(modelRow);
                            osInativosList.add(os);
                            chamadosInativosModel.addRow(new Object[]{os.getNumero(), os.getCliente().getNome(),
                                    os.getDescricao(), os.getPreco(), "Fechada"});
                        }
                    }

                    // Clique simples para editar cliente
                    if (e.getClickCount() == 1 && osAtivasTable.columnAtPoint(e.getPoint()) == 1) {
                        editarClienteDialog(os.getCliente());
                        // Atualizar nome na tabela
                        osAtivasModel.setValueAt(os.getCliente().getNome(), modelRow, 1);
                    }
                }
            }
        });
    }

    private void setupChamadosInativosTab() {
        String[] columns = {"Número", "Cliente", "Serviço", "Preço", "Status"};
        chamadosInativosModel = new DefaultTableModel(columns, 0);
        chamadosInativosTable = new JTable(chamadosInativosModel);
        chamadosInativosPanel.add(new JScrollPane(chamadosInativosTable), BorderLayout.CENTER);
    }

    private OS criarOSDialog() {
        JTextField nomeField = new JTextField();
        JTextField ruaField = new JTextField();
        JTextField numeroField = new JTextField();
        JTextField bairroField = new JTextField();
        JTextField cpfCnpjField = new JTextField();
        JTextField servicoField = new JTextField();
        JTextField precoField = new JTextField();

        JLabel fotoLabel = new JLabel();
        JButton escolherFotoButton = new JButton("Escolher Foto");
        final String[] fotoPath = {""};

        escolherFotoButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showOpenDialog(null);
            if (res == JFileChooser.APPROVE_OPTION) {
                fotoPath[0] = chooser.getSelectedFile().getAbsolutePath();
                fotoLabel.setIcon(new ImageIcon(new ImageIcon(fotoPath[0]).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
            }
        });

        JCheckBox fechadaCheck = new JCheckBox("Marcar como fechada");

        JPanel panel = new JPanel(new GridLayout(10, 2));
        panel.add(new JLabel("Nome:")); panel.add(nomeField);
        panel.add(new JLabel("Rua:")); panel.add(ruaField);
        panel.add(new JLabel("Número:")); panel.add(numeroField);
        panel.add(new JLabel("Bairro:")); panel.add(bairroField);
        panel.add(new JLabel("CPF/CNPJ:")); panel.add(cpfCnpjField);
        panel.add(new JLabel("Serviço:")); panel.add(servicoField);
        panel.add(new JLabel("Preço:")); panel.add(precoField);
        panel.add(fotoLabel); panel.add(escolherFotoButton);
        panel.add(new JLabel("Status:")); panel.add(fechadaCheck);

        int result = JOptionPane.showConfirmDialog(null, panel, "Criar O.S. / Cliente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            Cliente cliente = new Cliente(nomeField.getText(), ruaField.getText(), numeroField.getText(),
                    bairroField.getText(), cpfCnpjField.getText(), fotoPath[0]);
            OS os = new OS(cliente, servicoField.getText(), Double.parseDouble(precoField.getText()),
                    !fechadaCheck.isSelected());
            return os;
        }
        return null;
    }

    private void editarClienteDialog(Cliente cliente) {
        JTextField nomeField = new JTextField(cliente.getNome());
        JTextField ruaField = new JTextField(cliente.getRua());
        JTextField numeroField = new JTextField(cliente.getNumero());
        JTextField bairroField = new JTextField(cliente.getBairro());
        JTextField cpfCnpjField = new JTextField(cliente.getCpfCnpj());
        JLabel fotoLabel = new JLabel();
        fotoLabel.setIcon(new ImageIcon(new ImageIcon(cliente.getFotoPath()).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        JButton escolherFotoButton = new JButton("Escolher Foto");
        final String[] fotoPath = {cliente.getFotoPath()};

        escolherFotoButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showOpenDialog(null);
            if (res == JFileChooser.APPROVE_OPTION) {
                fotoPath[0] = chooser.getSelectedFile().getAbsolutePath();
                fotoLabel.setIcon(new ImageIcon(new ImageIcon(fotoPath[0]).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
            }
        });

        JPanel panel = new JPanel(new GridLayout(7, 2));
        panel.add(new JLabel("Nome:")); panel.add(nomeField);
        panel.add(new JLabel("Rua:")); panel.add(ruaField);
        panel.add(new JLabel("Número:")); panel.add(numeroField);
        panel.add(new JLabel("Bairro:")); panel.add(bairroField);
        panel.add(new JLabel("CPF/CNPJ:")); panel.add(cpfCnpjField);
        panel.add(fotoLabel); panel.add(escolherFotoButton);

        int result = JOptionPane.showConfirmDialog(null, panel, "Editar Cliente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            cliente.setNome(nomeField.getText());
            cliente.setRua(ruaField.getText());
            cliente.setNumero(numeroField.getText());
            cliente.setBairro(bairroField.getText());
            cliente.setCpfCnpj(cpfCnpjField.getText());
            cliente.setFotoPath(fotoPath[0]);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
