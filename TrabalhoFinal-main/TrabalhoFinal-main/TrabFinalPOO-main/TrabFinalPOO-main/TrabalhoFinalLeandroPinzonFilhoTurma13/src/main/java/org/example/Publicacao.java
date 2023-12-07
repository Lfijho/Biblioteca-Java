package org.example;

/**
 * Representa uma publicação genérica.
 *
 * @param <T> Tipo genérico para o ano de publicação.
 */
public class Publicacao<T> {
    protected String ISBN;
    protected String nomeObra;
    protected String autor;
    protected String editora;
    protected T anoPublicacao;
    protected int numExemplares;

    /**
     * Construtor para a classe Publicacao.
     *
     * @param isbn     ISBN da publicação.
     * @param nm       Nome da obra.
     * @param aut      Autor da obra.
     * @param edit     Editora da obra.
     * @param anoP     Ano de publicação da obra.
     */
    public Publicacao(String isbn, String nm, String aut, String edit, T anoP) {
        this.ISBN = isbn;
        this.nomeObra = nm;
        this.autor = aut;
        this.editora = edit;
        this.anoPublicacao = anoP;
    }

    /**
     * Obtém o ISBN da publicação.
     *
     * @return ISBN da publicação.
     */
    protected String getISBN() {
        return this.ISBN;
    }

    /**
     * Obtém o nome da obra.
     *
     * @return Nome da obra.
     */
    protected String getNomeObra() {
        return this.nomeObra;
    }

    /**
     * Obtém o autor da obra.
     *
     * @return Autor da obra.
     */
    protected String getAutor() {
        return this.autor;
    }

    /**
     * Obtém a editora da obra.
     *
     * @return Editora da obra.
     */
    protected String getEditora() {
        return this.editora;
    }

    /**
     * Obtém o ano de publicação da obra.
     *
     * @return Ano de publicação da obra.
     */
    protected T getAno() {
        return this.anoPublicacao;
    }
}
