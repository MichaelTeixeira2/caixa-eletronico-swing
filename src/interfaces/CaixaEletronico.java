package interfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CaixaEletronico extends JFrame {

    private JTextField txtNome;
    private JTextField txtSaldo;
    private JComboBox<String> cbTipoConta;
    private JTextField txtValor;
    private JButton butConcluir;

    private DefaultListModel<String> modeloExtrato;
    private JList<String> extrato;


    public CaixaEletronico(){

        setTitle("Caixa eletrônico");
        setSize(700,550);
        setLayout(new BorderLayout(10,10));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painelPrincipal = new JPanel(new FlowLayout(FlowLayout.LEFT));

        painelPrincipal.add(new JLabel("Titular:"));
        txtNome = new JTextField(10);
        painelPrincipal.add(txtNome);

        painelPrincipal.add(new JLabel("Saldo"));
        txtSaldo = new JTextField(10);
        painelPrincipal.add(txtSaldo);

        add(painelPrincipal, BorderLayout.NORTH);


        modeloExtrato = new DefaultListModel<>();
        extrato = new JList<>(modeloExtrato);

        JScrollPane scrollExtrato = new JScrollPane(extrato);

        add(scrollExtrato,BorderLayout.CENTER);

        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.LEFT));

        painelInferior.add(new JLabel("Valor:"));
        txtValor = new JTextField(10);
        painelInferior.add(txtValor);

        String[] texto = {"Depositar", "Sacar"};
        cbTipoConta = new JComboBox<>(texto);
        painelInferior.add(cbTipoConta);

        butConcluir = new JButton("Concluir");
        painelInferior.add(butConcluir);

        add(painelInferior, BorderLayout.SOUTH);

        JPanel painelTeclado  = new JPanel(new GridLayout(4,3,3,3));

        painelTeclado.setBorder(BorderFactory.createTitledBorder("Teclado"));

        add(painelTeclado, BorderLayout.EAST);

        String[] teclas = {"7", "8", "9",
                "4", "5", "6",
                "1", "2", "3",
                ",", "0", "C"};

        for(String t : teclas){
            JButton botao = new JButton(t);

            botao.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(t.equals("C")){
                        txtValor.setText("");
                    }
                    else{
                        txtValor.setText(txtValor.getText() + t);
                    }
                }
            });
            painelTeclado.add(botao);
        }

        butConcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = txtNome.getText().trim();
                String saldo = txtSaldo.getText().replace(",", ".").trim();
                String valor = txtValor.getText().replace(",",".").trim();

                try {
                    if (nome.isEmpty() || saldo.isEmpty() || valor.isEmpty()) {
                        JOptionPane.showMessageDialog(CaixaEletronico.this, "NENHUM CAMPO PODE ESTAR VAZIO",
                                "AVISO", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    double valorSaldo = Double.parseDouble(saldo);

                    if (valorSaldo < 0) {
                        JOptionPane.showMessageDialog(CaixaEletronico.this, "O SALDO NÃO PODE SER NEGATIVO",
                                "AVISO", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    double valorFinal = Double.parseDouble(valor);

                    if(valorFinal < 0){
                        JOptionPane.showMessageDialog(CaixaEletronico.this, "O VALOR NÃO PODE SER NEGATIVO",
                                "AVISO", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    if(cbTipoConta.getSelectedItem().equals("Depositar")){
                        valorSaldo += valorFinal;
                        String extrato = String.format("Deposito R$ %.2f | Saldo: R$ %.2f", valorFinal, valorSaldo);
                        modeloExtrato.addElement(extrato);
                    }else{
                        if(valorFinal > valorSaldo){
                            JOptionPane.showMessageDialog(CaixaEletronico.this, "SALDO INDISPONIVEL!!",
                                    "AVISO", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        valorSaldo -= valorFinal;
                        String extrato = String.format("Saque R$ %.2f | Saldo: R$ %.2f", valorFinal, valorSaldo);
                        modeloExtrato.addElement(extrato);
                    }

                    txtSaldo.setText(String.format("%.2f", valorSaldo));
                    txtValor.setText("");
                    txtValor.requestFocus();


                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(CaixaEletronico.this, "CAMPO INVALIDO",
                            "AVISO", JOptionPane.WARNING_MESSAGE);
                }

            }
        });

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new CaixaEletronico().setVisible(true);
            }
        });
    }

}
