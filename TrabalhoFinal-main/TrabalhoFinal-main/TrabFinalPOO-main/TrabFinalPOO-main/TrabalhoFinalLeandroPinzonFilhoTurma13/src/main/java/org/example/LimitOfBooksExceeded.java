package org.example;

/**
 * Exceção lançada quando o limite de empréstimos por cliente é excedido.
 */
public class LimitOfBooksExceeded extends Throwable {

    /**
     * Construtor para a exceção LimitOfBooksExceeded.
     *
     * @param a Mensagem descritiva da exceção.
     */
    public LimitOfBooksExceeded(String a) {
        super(a);
    }
}
