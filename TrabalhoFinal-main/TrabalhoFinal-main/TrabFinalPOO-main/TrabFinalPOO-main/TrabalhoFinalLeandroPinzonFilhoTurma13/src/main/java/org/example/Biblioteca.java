package org.example;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * A classe `Biblioteca` representa um sistema de gerenciamento de biblioteca.
 * Permite o gerenciamento de clientes, publicações e empréstimos de livros.
 *
 * @param <T> O parâmetro de tipo para o ano de publicação.
 */

public class Biblioteca<T> implements BibliotecaInterface<T> {
    private Map<String, String> biblioteca;
    private ArrayList<Cliente> clientes;

    /**
     * Constrói um novo objeto `Biblioteca` com um mapa vazio para publicações
     * e uma lista vazia para clientes.
     */
    public Biblioteca() {
        biblioteca = new HashMap<>();
        clientes = new ArrayList<>();
    }
    /**
     * Registra um novo cliente no sistema da biblioteca e verifica se esse cliente já não está cadastrado.
     *
     * @param nome     O nome do cliente.
     * @param matricula A matricula do cliente
     * @param telefone O número de telefone do cliente.
     */
    @Override
    public void cadastrarCliente(String nome, String matricula, String telefone) {
        Path clientesPath = Paths.get("C:\\Users\\Leandro Pinzon\\Downloads\\TrabalhoFinal-main\\TrabalhoFinal-main\\TrabFinalPOO-main\\TrabFinalPOO-main\\TrabalhoFinalLeandroPinzonFilhoTurma13\\src\\main\\resources\\CLIENTES.TXT");


        boolean clienteExistente = false;

        try {
            List<String> linhasClientes = Files.readAllLines(clientesPath, Charset.defaultCharset());
            for (int i = 0; i < linhasClientes.size(); i++) {
                String linha = linhasClientes.get(i);
                String matriculaExistente = (linha.length() >= 8) ? linha.substring(0, 8).trim() : "";
                if (matriculaExistente.equals(matricula)) {
                    clienteExistente = true;
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Erro ao ler o arquivo CLIENTES.TXT: " + ex.getMessage());
        }


        if (clienteExistente) {
            JOptionPane.showMessageDialog(null, "Já existe um cliente com a matrícula informada. Use outra matrícula.");
            return;
        }

        Cliente cliente = new Cliente(nome, matricula, telefone);
        clientes.add(cliente);
        int a = 0;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(clientesPath.toFile(), true))) {
            writer.write(matricula + " " + a + " " + nome + "\n");
            JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
        } catch (IOException e) {
            System.err.format("Erro ao gravar no arquivo 'CLIENTES.TXT': %s%n", e);
        }
    }
    /**
     * Registra uma nova publicação (livro ou revista) no sistema da biblioteca. Adiciona a publicação aos arquivos PUBLICACOES.TXT E PUBLICACOESREGISTRADAS.TXT
     *
     * @param isbn O ISBN da publicação.
     * @param nm   O nome da publicação.
     * @param aut  O autor da publicação.
     * @param edit A editora ou editor da publicação.
     * @param anoP O ano de publicação.
     * @throws IllegalArgumentException Se o tipo de publicação for inválido.
     */
    @Override
    public void cadastrarPublicacao(String isbn, String nm, String aut, String edit, T anoP) throws IllegalArgumentException {
        String[] opcoes = {"Livro", "Revista"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                "Selecione o tipo de publicação:",
                "Cadastrar Publicação",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );

        if (escolha == 0) {
            Livro livro = new Livro(isbn, nm, aut, edit, anoP);
            biblioteca.put(isbn, nm);
            JOptionPane.showMessageDialog(null, "Livro cadastrado com sucesso!");
        } else if (escolha == 1) {
            Revista revista = new Revista(isbn, nm, aut, edit, anoP);
            biblioteca.put(isbn, nm);
            JOptionPane.showMessageDialog(null, "Revista cadastrada com sucesso!");
        } else {
            throw new IllegalArgumentException("Escolha inválida. Por favor, selecione o tipo de publicação.");
        }


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Leandro Pinzon\\Downloads\\TrabalhoFinal-main\\TrabalhoFinal-main\\TrabFinalPOO-main\\TrabFinalPOO-main\\TrabalhoFinalLeandroPinzonFilhoTurma13\\src\\main\\resources\\PUBLICACOES.TXT", true))) {
            String a = (escolha == 0) ? "Livro" : "Revista";
            writer.write(isbn + " " + nm + "\r\n");

        } catch (IOException e) {
            System.err.format("Erro ao gravar no arquivo 'PUBLICACOES.TXT': %s%n", e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:\\Users\\Leandro Pinzon\\Downloads\\TrabalhoFinal-main\\TrabalhoFinal-main\\TrabFinalPOO-main\\TrabFinalPOO-main\\TrabalhoFinalLeandroPinzonFilhoTurma13\\src\\main\\resources\\PUBLICACOESREGISTRADAS.TXT", true))) {
            String a = (escolha == 0) ? "Livro" : "Revista";
            writer.write(isbn + " " + nm + "\r\n");

        } catch (IOException e) {
            System.err.format("Erro ao gravar no arquivo 'PUBLICACOESREGISTRADAS.TXT': %s%n", e);
        }
    }

    /**
     * Método para o cliente retirar o livro da biblioteca. Verifica se o numero de empréstimos registrados no nome do cliente não esta maior que o permitido pela biblioteca.
     * Caso não, incrementa o número de empréstimos e remove o livro de PUBLICACOES.TXT, calcula 7 dias após o dia da retirada para ser o dia da enrtrega do livro e adiciona em EMPRESTIMOS.TXT.
     * @param matricula A matrícula do cliente que esta retirando o livro
     * @param isbn O ISBN da publicação a ser emprestada.
     * @throws LimitOfBooksExceeded Se o cliente atingiu o limite de livros emprestados.
     */
    @Override
    public void retirarLivro(String matricula, String isbn) throws LimitOfBooksExceeded {
        Path path1 = Paths.get("C:\\Users\\Leandro Pinzon\\Downloads\\TrabalhoFinal-main\\TrabalhoFinal-main\\TrabFinalPOO-main\\TrabFinalPOO-main\\TrabalhoFinalLeandroPinzonFilhoTurma13\\src\\main\\resources\\CLIENTES.TXT");
        try (BufferedReader reader = Files.newBufferedReader(path1, Charset.defaultCharset())) {
            String line = null;
            String matricula1 = "";
            String isbn1 = "";

            while ((line = reader.readLine()) != null) {
                matricula1 = (line.length() >= 8) ? line.substring(0, 8) : "";
                if (matricula1.equals(matricula)) {
                    String nomeCliente = line.substring(10);

                    Path path2 = Paths.get("C:\\Users\\Leandro Pinzon\\Downloads\\TrabalhoFinal-main\\TrabalhoFinal-main\\TrabFinalPOO-main\\TrabFinalPOO-main\\TrabalhoFinalLeandroPinzonFilhoTurma13\\src\\main\\resources\\PUBLICACOES.TXT");
                    try (BufferedReader reader1 = Files.newBufferedReader(path2, Charset.defaultCharset())) {
                        String line1 = null;
                        while ((line1 = reader1.readLine()) != null) {
                            isbn1 = line1.length() >= 13 ? line1.substring(0, 13) : "";
                            String nome = line1.substring(14);
                            if (isbn1.equals(isbn)) {
                                int numEmprestimos = Integer.parseInt(line.substring(9, 10));
                                if (numEmprestimos >= 3) {
                                    JOptionPane.showMessageDialog(null, "O cliente não pode mais retirar livros. Devolva algum livro antes de fazer um novo empréstimo.");
                                    throw new LimitOfBooksExceeded("O cliente não pode mais retirar livros. Devolva algum livro antes de fazer um novo empréstimo.");
                                } else {
                                    Path emprestimosPath = Paths.get("C:\\Users\\Leandro Pinzon\\Downloads\\TrabalhoFinal-main\\TrabalhoFinal-main\\TrabFinalPOO-main\\TrabFinalPOO-main\\TrabalhoFinalLeandroPinzonFilhoTurma13\\src\\main\\resources/EMPRESTIMOS.TXT");
                                    try (BufferedWriter writer = Files.newBufferedWriter(emprestimosPath, Charset.defaultCharset(), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
                                        DateTimeFormatter brasil = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                        String dataBrasil1 = LocalDate.now().plusDays(7).format(brasil);
                                        writer.write(isbn1 + " " + dataBrasil1 + " " + nome);
                                        writer.newLine();
                                    } catch (IOException ex) {
                                        System.out.println("Erro ao escrever no arquivo EMPRESTIMOS.TXT: " + ex.getMessage());
                                    }
                                    DateTimeFormatter brasil = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                    String dataBrasil = LocalDate.now().plusDays(7).format(brasil);
                                    JOptionPane.showMessageDialog(null, "Livro/revista emprestado para " + nomeCliente + ". Matricula: " + matricula + ". Data de devolução: " + dataBrasil);
                                    List<String> linhasPub = Files.readAllLines(path2, Charset.defaultCharset());
                                    int indiceLAnt = -1;
                                    for (int i = 0; i < linhasPub.size(); i++) {
                                        if (linhasPub.get(i).startsWith(isbn)) {
                                            indiceLAnt = i;
                                            break;
                                        }
                                    }
                                    if (indiceLAnt != -1) {
                                        linhasPub.remove(indiceLAnt);
                                    }
                                    try (BufferedWriter pubWriter = Files.newBufferedWriter(path2, Charset.defaultCharset())) {
                                        for (int i = 0; i < linhasPub.size(); i++) {
                                            pubWriter.write(linhasPub.get(i));
                                            pubWriter.newLine();
                                        }
                                    } catch (IOException ex) {
                                        System.out.println("Erro ao sobrescrever o arquivo CLIENTES.TXT: " + ex.getMessage());
                                    }
                                    numEmprestimos++;
                                    String novaLinhaCliente = String.format("%s%d %s", line.substring(0, 9), numEmprestimos, line.substring(11));
                                    List<String> linhasClientes = Files.readAllLines(path1, Charset.defaultCharset());
                                    int indiceLinhaAntiga = -1;
                                    for (int i = 0; i < linhasClientes.size(); i++) {
                                        if (linhasClientes.get(i).startsWith(matricula)) {
                                            indiceLinhaAntiga = i;
                                            break;
                                        }
                                    }
                                    if (indiceLinhaAntiga != -1) {
                                        linhasClientes.remove(indiceLinhaAntiga);
                                    }
                                    linhasClientes.add(novaLinhaCliente);
                                    try (BufferedWriter clientesWriter = Files.newBufferedWriter(path1, Charset.defaultCharset())) {
                                        for (int i = 0; i < linhasClientes.size(); i++) {
                                            clientesWriter.write(linhasClientes.get(i));
                                            clientesWriter.newLine();
                                        }
                                    } catch (IOException ex) {
                                        System.out.println("Erro ao sobrescrever o arquivo CLIENTES.TXT: " + ex.getMessage());
                                    }
                                }
                                break;
                            }
                        }
                    } catch (IOException ex) {
                        System.out.println("Erro ao ler o arquivo PUBLICAÇÕES.TXT: " + ex.getMessage());
                    }
                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Erro ao ler o arquivo CLIENTES.TXT: " + ex.getMessage());
        }
    }

    /**
     * Permite que um cliente devolva um livro ou revista emprestado à biblioteca. Decrementa o número de empréstimos registrados ao cliente e verifica se não está com atraso para entrega.
     * Remove de EMPRESTIMOS.TXT e faz com que o livro fique disponível novamente para retirada em PUBLICACOES.TXT;
     * @param matricula A matrícula do cliente que está devolvendo o livro.
     * @param isbn O ISBN da publicação devolvida.
     */
    @Override
    public void devolverLivro(String matricula, String isbn) {
        Path clientesPath = Paths.get("C:\\Users\\Leandro Pinzon\\Downloads\\TrabalhoFinal-main\\TrabalhoFinal-main\\TrabFinalPOO-main\\TrabFinalPOO-main\\TrabalhoFinalLeandroPinzonFilhoTurma13\\src\\main\\resources\\CLIENTES.TXT");
        Path emprestimosPath = Paths.get("C:\\Users\\Leandro Pinzon\\Downloads\\TrabalhoFinal-main\\TrabalhoFinal-main\\TrabFinalPOO-main\\TrabFinalPOO-main\\TrabalhoFinalLeandroPinzonFilhoTurma13\\src\\main\\resources\\EMPRESTIMOS.TXT");
        Path publicacoesPath = Paths.get("C:\\Users\\Leandro Pinzon\\Downloads\\TrabalhoFinal-main\\TrabalhoFinal-main\\TrabFinalPOO-main\\TrabFinalPOO-main\\TrabalhoFinalLeandroPinzonFilhoTurma13\\src\\main\\resources\\PUBLICACOES.TXT");

        try {
            List<String> clientesLines = Files.readAllLines(clientesPath, Charset.defaultCharset());
            List<String> emprestimosLines = Files.readAllLines(emprestimosPath, Charset.defaultCharset());

            for (int i = 0; i < clientesLines.size(); i++) {
                String line = clientesLines.get(i);
                String matricula1 = (line.length() >= 8) ? line.substring(0, 8) : "";
                String nomeCliente = line.substring(11);
                if (matricula1.equals(matricula)) {
                    int numEmprestimos = Integer.parseInt(line.substring(9, 10));

                    if (numEmprestimos > 0) {
                        String isbnWithSpace = isbn + " ";
                        String nomeLivro = "";
                        for (int j = 0; j < emprestimosLines.size(); j++) {
                            String emprestimoLine = emprestimosLines.get(j);
                            if (emprestimoLine.startsWith(isbnWithSpace)) {
                                nomeLivro = emprestimoLine.substring(25).trim();
                                Files.write(publicacoesPath, Collections.singletonList(String.format("%s %s", isbn, nomeLivro)), StandardOpenOption.APPEND);

                                emprestimosLines.remove(j);

                                numEmprestimos--;
                                String novaLinhaCliente = String.format("%s%d %s", line.substring(0, 9), numEmprestimos, nomeCliente);
                                clientesLines.set(i, novaLinhaCliente);

                                String dataDevolucao = emprestimoLine.substring(14, 24).trim();
                                LocalDate diaDevolucao = LocalDate.parse(dataDevolucao, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                                if (LocalDate.now().isAfter(diaDevolucao)) {
                                    int diasAtraso = (int) LocalDate.now().until(diaDevolucao).getDays();
                                    Files.write(emprestimosPath, emprestimosLines, Charset.defaultCharset());
                                    Files.write(clientesPath, clientesLines, Charset.defaultCharset());
                                    JOptionPane.showMessageDialog(null, "Livro atrasado em " + diasAtraso + " dias. Pague a multa para não ficar com dívidas no sistema. Livro devolvido as prateleiras.");
                                } else {
                                    Files.write(emprestimosPath, emprestimosLines, Charset.defaultCharset());
                                    Files.write(clientesPath, clientesLines, Charset.defaultCharset());
                                    JOptionPane.showMessageDialog(null, "Livro devolvido para as prateleiras. Obrigado por utilizar a biblioteca de POO.");

                                }
                                break;
                            }
                        }
                    }

                    break;
                }
            }
        } catch (IOException ex) {
            System.out.println("Erro ao processar arquivos: " + ex.getMessage());
        }
    }
    /**
     * Gera um relatório com base na seleção do usuário.
     * Seleção 1: Relatório de clientes registrados no sistema da biblioteca;
     * Seleção 2: Relatório de publicações disponíveis n abiblioteca;
     * Seleção 3: Relatório de publicações registradas no sistema da biblioteca;
     * Seleção 4: Relatório de livros emprestados para os clientes da biblioteca, contendo a data que o cliente que está com o livro deve entregar, a fim de controle do intressado sobre o livro.
     *
     * @return Uma string contendo o relatório selecionado.
     */
    @Override
    public String relatorio() {
        String[] opcoes = {"Relatório de clientes", "Relatório de publicações disponíveis", "Relatório de publicações registradas", "Relatório de livros emprestados"};
        int escolha = JOptionPane.showOptionDialog(
                null,
                "Selecione o tipo de relatório:",
                "Relatório",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                opcoes,
                opcoes[0]
        );
        String relatorio = "";
        if (escolha == 0) {
            Path clientesPath = Paths.get("C:\\Users\\Leandro Pinzon\\Downloads\\TrabalhoFinal-main\\TrabalhoFinal-main\\TrabFinalPOO-main\\TrabFinalPOO-main\\TrabalhoFinalLeandroPinzonFilhoTurma13\\src\\main\\resources\\CLIENTES.TXT");
            try (BufferedReader br = new BufferedReader(new FileReader(clientesPath.toFile()))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    relatorio += linha + "\n";
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo CLIENTES.TXT: " + e.getMessage());
            }
        } else if (escolha == 1) {
            Path publicacoesPath = Paths.get("C:\\Users\\Leandro Pinzon\\Downloads\\TrabalhoFinal-main\\TrabalhoFinal-main\\TrabFinalPOO-main\\TrabFinalPOO-main\\TrabalhoFinalLeandroPinzonFilhoTurma13\\src\\main\\resources\\PUBLICACOES.TXT");
            try (BufferedReader br = new BufferedReader(new FileReader(publicacoesPath.toFile()))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    relatorio += linha + "\n";
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo PUBLICACOES.TXT: " + e.getMessage());
            }
        } else if (escolha == 2) {
            Path pubRegistPath = Paths.get("C:\\Users\\Leandro Pinzon\\Downloads\\TrabalhoFinal-main\\TrabalhoFinal-main\\TrabFinalPOO-main\\TrabFinalPOO-main\\TrabalhoFinalLeandroPinzonFilhoTurma13\\src\\main\\resources\\PUBLICACOESREGISTRADAS.TXT");
            try (BufferedReader br = new BufferedReader(new FileReader(pubRegistPath.toFile()))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    relatorio += linha + "\n";
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo PUBLICACOESREGISTRADAS.TXT: " + e.getMessage());
            }
        }
        else if ( escolha == 3) {
            Path emprestimosPath = Paths.get("C:\\Users\\Leandro Pinzon\\Downloads\\TrabalhoFinal-main\\TrabalhoFinal-main\\TrabFinalPOO-main\\TrabFinalPOO-main\\TrabalhoFinalLeandroPinzonFilhoTurma13\\src\\main\\resources\\EMPRESTIMOS.TXT");
            try (BufferedReader br = new BufferedReader(new FileReader(emprestimosPath.toFile()))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    relatorio += linha + "\n";
                }
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo EMPRESTIMOS.TXT: " + e.getMessage());
            }
        }
        else {
            System.out.println("Opção inválida!");
        }
        return relatorio;
    }

}


