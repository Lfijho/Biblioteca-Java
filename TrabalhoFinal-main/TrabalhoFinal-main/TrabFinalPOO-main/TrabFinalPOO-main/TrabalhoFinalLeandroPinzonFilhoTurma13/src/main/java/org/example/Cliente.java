package org.example;

/**
 * Classe que representa um cliente na biblioteca.
 *
 * @param <S> Tipo genérico para flexibilidade.
 */
public class Cliente<S> {
    private String nome;
    private String matricula;
    private String telefone;


    /**
     * Construtor para a classe Cliente.
     *
     * @param nome      Nome do cliente.
     * @param matricula Matrícula do cliente.
     * @param telefone  Número de telefone do cliente.
     */
    public Cliente(String nome, String matricula, String telefone) {
        this.nome = nome;
        this.matricula = matricula;
        this.telefone = telefone;
    }

    /**
     * Obtém o nome do cliente.
     *
     * @return Nome do cliente.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Obtém a matrícula do cliente.
     *
     * @return Matrícula do cliente.
     */
    public String getMatricula() {
        return matricula;
    }

    /**
     * Obtém o número de telefone do cliente.
     *
     * @return Número de telefone do cliente.
     */
    public String getTelefone() {
        return telefone;
    }
}
