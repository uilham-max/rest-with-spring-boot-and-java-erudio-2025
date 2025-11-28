package br.com.erudio.model;


/*
    record é um tipo especial de classe introduzido no Java 14 (prévia)
    e Java 16 (estável) para representar estruturas imutáveis de dados, como DTOs.

    O Java gera automaticamente:
        - Atributos id e content como final
        - Construtor
        - Getters (mas chamados pelo nome do campo → id() e content())
        - equals()
        - hashCode()
        - toString()

        por exemplo:

        public record Greeting(long id, String content) {}


 */

public class Greeting {
    private final long id;
    private final String message;

    public Greeting(long id, String message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
}