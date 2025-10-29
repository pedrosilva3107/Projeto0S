package com.projeto0s.view;

import com.projeto0s.model.Cliente;
import com.projeto0s.model.OS;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;

/**
 * Classe principal da interface gráfica do Sistema de Ordem de Serviço (OS).
 * Gerencia abas para OS ativas e chamadas inativas, com funcionalidades de criação,
 * edição, busca e fechamento de OS.
 */
public class MainFrame extends JFrame {
    private JTabbedPane tabbedPane;
    private JPanel osAtivasPanel, chamadosInativosPanel;
    private JTable osAtivasTable, chamadosInativosTable;
    private DefaultTableModel osAtivasModel, chamadosInativosModel;
    private ArrayList<OS> osList;
    private ArrayList<OS> osInativosList;
    private JTextField searchField;

    // Constantes para colunas das tabelas
    private static final String[] TABLE_COLUMNS = {"Número", "Cliente", "Serviço", "Preço", "Status"};

    public MainFrame() {
        setTitle("Sistema de OS - Empresa");
        setSize(1200, 700); // Aumentado para melhor visualização
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Inicialização das listas
        osList = new ArrayList<>();
        osInativosList = new ArrayList<>();

        // Configuração do painel com abas
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

    /**
     * Configura a aba de OS Ativas com tabela, busca e botão de criação.
     */
    private void setupOSAtivasTab() {
        // Painel superior com botão e campo de busca
        JPanel topPanel = new JPanel(new BorderLayout());
        JButton createOSButton = new JButton("Criar O.S.");
        searchField = new JTextField();
        searchField.setToolTipText("Digite para buscar por qualquer campo");
        topPanel.add(createOSButton, BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        osAtivasPanel.add(topPanel, BorderLayout.NORTH);

        // Configuração da tabela
        osAtivasModel = new DefaultTableModel(TABLE_COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabela não editável diretamente
            }
        };
        osAtivasTable = new JTable(osAtivasModel);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(osAtivasModel);
        osAtivasTable.setRowSorter(sorter);
        osAtivasTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        osAtivasPanel.add(new JScrollPane(osAtivasTable), BorderLayout.CENTER);

        // Listener para busca em tempo real
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String text = searchField.getText().trim();
                sorter.setRowFilter(text.isEmpty() ? null : RowFilter.regexFilter("(?i)" + text));
            }
        });

        // Listener para criação de OS
        createOSButton.addActionListener(e -> {
            OS novaOS = criarOSDialog();
            if (novaOS != null) {
                adicionarOS(novaOS);
            }
        });

        // Listener para interações na tabela
        osAtivasTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = osAtivasTable.getSelectedRow();
                if (row < 0) return;
                int modelRow = osAtivasTable.convertRowIndexToModel(row);
                OS os = osList.get(modelRow);

                if (e.getClickCount() == 2) {
                    // Clique duplo: fechar OS
                    fecharOS(os, modelRow);
                } else if (e.getClickCount() == 1 && osAtivasTable.columnAtPoint(e.getPoint()) == 1) {
                    // Clique simples na coluna Cliente: editar cliente
                    editarClienteDialog(os.getCliente());
                    osAtivasModel.setValueAt(os.getCliente().getNome(), modelRow, 1);
                }
            }
        });
    }

    /**
     * Configura a aba de Chamados Inativos com tabela.
     */
    private void setupChamadosInativosTab() {
        chamadosInativosModel = new DefaultTableModel(TABLE_COLUMNS, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        chamadosInativosTable = new JTable(chamadosInativosModel);
        chamadosInativosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        chamadosInativosPanel.add(new JScrollPane(chamadosInativosTable), BorderLayout.CENTER);

        // Listener para reabrir OS (duplo clique)
        chamadosInativosTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = chamadosInativosTable.getSelectedRow();
                    if (row >= 0) {
                        int modelRow = chamadosInativosTable.convertRowIndexToModel(row);
                        OS os = osInativosList.get(modelRow);
                        reabrirOS(os, modelRow);
                    }
                }
            }
        });
    }

    /**
     * Adiciona uma OS à lista apropriada e atualiza a tabela.
     */
    private void adicionarOS(OS os) {
        if (os.isAtiva()) {
            osList.add(os);
            osAtivasModel.addRow(new Object[]{os.getNumero(), os.getCliente().getNome(),
                    os.getDescricao(), os.getPreco(), "Ativa"});
        } else {
            osInativosList.add(os);
            chamadosInativosModel.addRow(new Object[]{os.getNumero(), os.getCliente().getNome(),
                    os.getDescricao(), os.getPreco(), "Fechada"});
        }
    }

    /**
     * Fecha uma OS ativa e move para inativos.
     */
    private void fecharOS(OS os, int modelRow) {
        int option = JOptionPane.showConfirmDialog(this,
                "Deseja fechar esta O.S.?", "Fechar OS",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            os.setAtiva(false);
            osList.remove(os);
            osAtivasModel.removeRow(modelRow);
            adicionarOS(os);
        }
    }

    /**
     * Reabre uma OS inativa e move para ativas.
     */
    private void reabrirOS(OS os, int modelRow) {
        int option = JOptionPane.showConfirmDialog(this,
                "Deseja reabrir esta O.S.?", "Reabrir OS",
                JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            os.setAtiva(true);
            osInativosList.remove(os);
            chamadosInativosModel.removeRow(modelRow);
            adicionarOS(os);
        }
    }

    /**
     * Diálogo para criar uma nova OS e cliente.
     */
    private OS criarOSDialog() {
        JTextField nomeField = new JTextField();
        JTextField ruaField = new JTextField();
        JTextField numeroField = new JTextField();
        JTextField bairroField = new JTextField();
        JTextField cpfCnpjField = new JTextField();
        JTextField servicoField = new JTextField();
        JTextField precoField = new JTextField();

        JLabel fotoLabel = new JLabel("Nenhuma foto selecionada");
        JButton escolherFotoButton = new JButton("Escolher Foto");
        final String[] fotoPath = {""};

        escolherFotoButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int res = chooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                fotoPath[0] = file.getAbsolutePath();
                fotoLabel.setIcon(new ImageIcon(new ImageIcon(fotoPath[0]).getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
                fotoLabel.setText("");
            }
        });

        JCheckBox fechadaCheck = new JCheckBox("Marcar como fechada");

        // Melhor layout usando BoxLayout para alinhamento vertical
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(createLabeledField("Nome:", nomeField));
        panel.add(createLabeledField("Rua:", ruaField));
        panel.add(createLabeledField("Número:", numeroField));
        panel.add(createLabeledField("Bairro:", bairroField));
        panel.add(createLabeledField("CPF/CNPJ:", cpfCnpjField));
        panel.add(createLabeledField("Serviço:", servicoField));
        panel.add(createLabeledField("Preço:", precoField));
        panel.add(createLabeledField("", fotoLabel));
        panel.add(escolherFotoButton);
        panel.add(fechadaCheck);

        int result = JOptionPane.showConfirmDialog(this, panel, "Criar O.S. / Cliente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                // Validações básicas
                if (nomeField.getText().trim().isEmpty() || servicoField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nome e Serviço são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return null;
                }
                double preco = Double.parseDouble(precoField.getText().trim());
                if (preco < 0) {
                    JOptionPane.showMessageDialog(this, "Preço deve ser positivo.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return null;
                }

                Cliente cliente = new Cliente(nomeField.getText().trim(), ruaField.getText().trim(),
                        numeroField.getText().trim(), bairroField.getText().trim(),
                        cpfCnpjField.getText().trim(), fotoPath[0]);
                OS os = new OS(cliente, servicoField.getText().trim(), preco, !fechadaCheck.isSelected());
                return os;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Preço deve ser um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }

    /**
     * Diálogo para editar um cliente.
     */
    private void editarClienteDialog(Cliente cliente) {
        JTextField nomeField = new JTextField(cliente.getNome());
        JTextField ruaField = new JTextField(cliente.getRua());
        JTextField numeroField = new JTextField(cliente.getNumero());
        JTextField bairroField = new JTextField(cliente.getBairro());
        JTextField cpfCnpjField = new JTextField(cliente.getCpfCnpj());
        JLabel fotoLabel = new JLabel();
        if (!cliente.getFotoPath().isEmpty()) {
            fotoLabel.setIcon(new ImageIcon(new ImageIcon(cliente.getFotoPath()).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
        } else {
            fotoLabel.setText("Nenhuma foto");
        }
        JButton escolherFotoButton = new JButton("Escolher Foto");
        final String[] fotoPath = {cliente.getFotoPath()};

        escolherFotoButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int res = chooser.showOpenDialog(this);
            if (res == JFileChooser.APPROVE_OPTION) {
                fotoPath[0] = chooser.getSelectedFile().getAbsolutePath();
                fotoLabel.setIcon(new ImageIcon(new ImageIcon(fotoPath[0]).getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH)));
                fotoLabel.setText("");
            }
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(createLabeledField("Nome:", nomeField));
        panel.add(createLabeledField("Rua:", ruaField));
        panel.add(createLabeledField("Número:", numeroField));
        panel.add(createLabeledField("Bairro:", bairroField));
        panel.add(createLabeledField("CPF/CNPJ:", cpfCnpjField));
        panel.add(createLabeledField("", fotoLabel));
        panel.add(escolherFotoButton);

        int result = JOptionPane.showConfirmDialog(this, panel, "Editar Cliente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (nomeField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nome é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            cliente.setNome(nomeField.getText().trim());
            cliente.setRua(ruaField.getText().trim());
            cliente.setNumero(numeroField.getText().trim());
            cliente.setBairro(bairroField.getText().trim());
            cliente.setCpfCnpj(cpfCnpjField.getText().trim());
            cliente.setFotoPath(fotoPath[0]);
        }
    }

    /**
     * Cria um painel com label e campo para melhor organização.
     */
    private JPanel createLabeledField(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(labelText), BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}