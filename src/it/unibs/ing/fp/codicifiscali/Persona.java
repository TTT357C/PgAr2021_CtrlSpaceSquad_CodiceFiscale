package it.unibs.ing.fp.codicifiscali;

import java.util.Date;

public class Persona {

    private String nome;
    private String cognome;
    private char sesso;
    private Date data_nascita;
    private Comune luogo_nascita;
    private char char_controllo;
    private String cod_fiscale;

    /**
     * Costruttore completo mancante di codice fiscale
     * @param nome
     * @param cognome
     * @param sesso
     * @param data_nascita
     * @param luogo_nascita
     * @param char_controllo
     */
    public Persona(String nome, String cognome, char sesso, Date data_nascita, Comune luogo_nascita, char char_controllo) {
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.data_nascita = data_nascita;
        this.luogo_nascita = luogo_nascita;
        this.char_controllo = char_controllo;
    }

    /**
     * Costruttore dato solo il codice fiscale
     * @param cod_fiscale della persona
     */
    public Persona(String cod_fiscale) {
        this.cod_fiscale = cod_fiscale;
    }

    /**
     * Metodo che ritorna il nome della persona
     * @return Ritorna nome della persona
     */
    public String getNome() {
        return nome;
    }

    /**
     * Metodo che ritorna il cognome della persona
     * @return Ritorna cognome della persona
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Metodo che ritorna il sesso della persona
     * @return Ritorna il sesso della persona (M maschio oppure F femmina)
     */
    public char getSesso() {
        return sesso;
    }

    /**
     * Metodo che ritorna la data di nascita
     * @return Ritorna data di nascita della persona
     */
    public Date getData_nascita() {
        return data_nascita;
    }

    /**
     * Metodo che ritorna un oggetto di tipo comune
     * @return Ritorna comune di nascita
     */
    public Comune getLuogo_nascita() {
        return luogo_nascita;
    }

    /**
     * Metodo che ritorna il carattere di controllo del C.F.
     * @return Ritorna carattere di controllo
     */
    public char getChar_controllo() {
        return char_controllo;
    }

    /**
     * Metodo che permette di calcolare il codice fiscale di una persona
     * @return Ritorna il codice fiscale di una persona
     */
    public String calcoloCodiceFiscale(){
        return "";
    }

    /**
     * Metodo che permette di controllare se il codice fiscale è giusto
     * @return Ritorna true se il codice fiscale è falido altrimenti falso
     */
    public boolean controlloCodFiscale(){
        return false;
    }
}
