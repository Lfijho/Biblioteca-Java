package org.example;

/**
 * Representa um livro, que é uma subclasse de Publicacao.
 *
 * @param <T> Tipo genérico para o ano de publicação.
 */
public class Livro<T> extends Publicacao {

    /**
     * Construtor para a classe Livro.
     *
     * @param isbn ISBN do livro.
     * @param nm   Nome do livro.
     * @param aut  Autor do livro.
     * @param edit Editora do livro.
     * @param anoP Ano de publicação do livro.
     */
    public Livro(String isbn, String nm, String aut, String edit, T anoP) {
        super(isbn, nm, aut, edit, anoP);
    }
}
