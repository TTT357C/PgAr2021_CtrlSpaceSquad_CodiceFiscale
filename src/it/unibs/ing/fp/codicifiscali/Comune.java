package it.unibs.ing.fp.codicifiscali;

public class Comune {

    private String nome;
    private String codice;

    /**
     * Costruttore per inizializzare un comune dato il nome e il codice
     * @param nome
     * @param codice
     */
    public Comune(String nome, String codice) {
        this.nome = nome;
        this.codice = codice;
    }

    /**
     * Metodo che ritorna il nome del comune
     * @return nome del comune
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo che ritorna il codice del comune
     * @return codice del comune
     */
    public String getCodice() {
        return codice;
    }
}
