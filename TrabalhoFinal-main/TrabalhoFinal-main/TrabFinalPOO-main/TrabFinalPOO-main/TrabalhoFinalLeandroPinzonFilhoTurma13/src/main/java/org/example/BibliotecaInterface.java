package org.example;

import java.util.List;

/**
 * Interface que define as operações básicas de uma biblioteca.
 *
 * @param <T> Tipo genérico para flexibilidade.
 */
public interface BibliotecaInterface<T> {

    /**
     * Cadastra uma nova publicação na biblioteca.
     *
     * @param isbn           ISBN da publicação.
     * @param nome           Nome da publicação.
     * @param autor          Autor da publicação.
     * @param editora        Editora da publicação.
     * @param anoPublicacao  Ano de publicação da publicação.
     */
    void cadastrarPublicacao(String isbn, String nome, String autor, String editora, T anoPublicacao);

    /**
     * Cadastra um novo cliente na biblioteca.
     *
     * @param nome     Nome do cliente.
     * @param matricula Matrícula do cliente.
     * @param telefone Número de telefone do cliente.
     */
    void cadastrarCliente(String nome, String matricula, String telefone);

    /**
     * Realiza o processo de retirada de um livro por um cliente.
     *
     * @param matricula Matrícula do cliente.
     * @param isbn      ISBN do livro a ser retirado.
     * @throws LimitOfBooksExceeded Exceção lançada quando o limite de livros é excedido.
     */
    void retirarLivro(String matricula, String isbn) throws LimitOfBooksExceeded;

    /**
     * Realiza o processo de devolução de um livro por um cliente.
     *
     * @param matricula Matrícula do cliente.
     * @param isbn      ISBN do livro a ser devolvido.
     */
    void devolverLivro(String matricula, String isbn);

    /**
     * Gera um relatório com informações específicas escolhidas pelo usuário.
     *
     * @return String contendo o relatório gerado.
     */
    String relatorio();
}
