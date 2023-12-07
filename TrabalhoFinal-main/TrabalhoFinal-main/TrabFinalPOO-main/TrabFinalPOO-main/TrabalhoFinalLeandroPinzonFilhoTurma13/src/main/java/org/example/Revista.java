package org.example;

/**
 * Representa uma revista, que é uma subclasse de Publicacao.
 *
 * @param <T> Tipo genérico para o ano de publicação.
 */
public class Revista<T> extends Publicacao {

    /**
     * Construtor para a classe Revista.
     *
     * @param isbn   ISBN da revista.
     * @param nm     Nome da revista.
     * @param aut    Autor da revista.
     * @param edit   Editora da revista.
     * @param anoP   Ano de publicação da revista.
     */
    public Revista(String isbn, String nm, String aut, String edit, T anoP) {
        super(isbn, nm, aut, edit, anoP);
    }
}
