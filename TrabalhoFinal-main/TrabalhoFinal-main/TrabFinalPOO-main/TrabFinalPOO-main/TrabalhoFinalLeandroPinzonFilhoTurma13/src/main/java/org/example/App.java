package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A classe App representa a JAVA GUI para interação do usuário com o sistema de biblioteca.
 */
public class App {
    private JFrame frame;
    private Biblioteca biblioteca;

    /**
     * Constrói a JAVA GUI da aplicação e inicializa a Biblioteca.
     */

    public App() {
        frame = new JFrame("Biblioteca");
        biblioteca = new Biblioteca();
        String explicacaoString = "Esta biblioteca foi criada para realizar o trabalho final da disciplina de POO da PUCRS.\nEscolha abaixo a opção que desejar.";
        JLabel explicacao = new JLabel(explicacaoString);
        JButton cliente = new JButton("Cadastrar Cliente");
        JButton publicacao = new JButton("Cadastrar Publicação");
        JButton retirarLivro = new JButton("Retirar publicação");
        JButton devolverLivro = new JButton("Devolver Livro");
        JButton relatorio = new JButton("Gerar Relatório");

        /**
         * Listener para o botão de cadastrar cliente.
         */

        cliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarCliente();
            }
        });

        /**
         * Listener para o botão de cadastrar publicação.
         */

        publicacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarPublicacao();
            }
        });

        /**
         * Listener para o botão de retirar publicação.
         */

        retirarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    retirarLivro();
                } catch (LimitOfBooksExceeded ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        /**
         * Listener para o botão de devolver publicação.
         */

        devolverLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                devolverLivro();
            }
        });

        /**
         * Listener para o botão de relatórios.
         */

        relatorio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gerarRelatorio();
            }
        });

        /**
         * Configuração do layout e adição de componentes ao painel.
         */

        JPanel panel = new JPanel();
        panel.add(explicacao);
        panel.add(cliente);
        panel.add(publicacao);
        panel.add(retirarLivro);
        panel.add(devolverLivro);
        panel.add(relatorio);

        /**
         * Configuração da janela de execução principal do programa.
         */

        frame.add(panel);
        frame.setSize(750, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    /**
     * Método para cadastrar um novo cliente no sistema.
     */
    private void cadastrarCliente() {
        String nome = JOptionPane.showInputDialog("Digite o nome do cliente:");
        String matricula = JOptionPane.showInputDialog("Digite a matrícula do cliente (8 dígitos sem o verificador):").trim();
        String telefone = JOptionPane.showInputDialog("Digite o telefone do cliente:");
        biblioteca.cadastrarCliente(nome, matricula, telefone);
    }

    /**
     * Método para cadastrar uma nova publicação (livro ou revista) no sistema.
     */
    private void cadastrarPublicacao() {
        String isbn = JOptionPane.showInputDialog("Digite o ISBN da publicação (13 dígitos, não utilize - ou /):").trim();
        String nome = JOptionPane.showInputDialog("Digite o nome da publicação:");
        String autor = JOptionPane.showInputDialog("Digite o nome do autor:");
        String editora = JOptionPane.showInputDialog("Digite o nome da editora:");
        int anoPublicacao = Integer.parseInt(JOptionPane.showInputDialog("Digite o ano de publicação:"));
        biblioteca.cadastrarPublicacao(isbn, nome, autor, editora, anoPublicacao);
    }

    /**
     * Método para realizar a retirada de um livro por um cliente.
     *
     * @throws LimitOfBooksExceeded Se o cliente atingiu o limite de livros emprestados.
     */
    private void retirarLivro() throws LimitOfBooksExceeded {
        String matriculaCliente = JOptionPane.showInputDialog("Digite a matrícula do cliente:").trim();
        String isbnLivro = JOptionPane.showInputDialog("Digite o ISBN da publicação (13 dígitos, não utilize - ou /):").trim();
        biblioteca.retirarLivro(matriculaCliente, isbnLivro);
    }

    /**
     * Método para devolver um livro ao sistema.
     */
    private void devolverLivro() {
        String matriculaCliente = JOptionPane.showInputDialog("Digite a matrícula do cliente:").trim();
        String isbnLivro = JOptionPane.showInputDialog("Digite o ISBN da publicação (13 dígitos, não utilize - ou /):").trim();
        biblioteca.devolverLivro(matriculaCliente, isbnLivro);
    }

    /**
     * Método para gerar e exibir um relatório na interface gráfica.
     */
    private void gerarRelatorio() {
        String relatorio = biblioteca.relatorio();
        JOptionPane.showMessageDialog(frame, relatorio, "Relatório", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Método principal que inicia a aplicação Swing.
     *
     * @param args Argumentos da linha de comando (não utilizados).
     * @throws LimitOfBooksExceeded Se ocorrer um erro relacionado ao limite de livros emprestados.
     */

    public static void main(String[] args) throws LimitOfBooksExceeded {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });
    }
}
