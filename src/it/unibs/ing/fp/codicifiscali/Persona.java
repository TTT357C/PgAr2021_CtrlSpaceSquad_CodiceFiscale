package it.unibs.ing.fp.codicifiscali;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;

public class Persona {

    private String nome;
    private String cognome;
    private char sesso;
    private LocalDate data_nascita;
    private Comune luogo_nascita;
    private Boolean cod_fisc_pres;
    private CodiceFiscale cod_fiscale;

    /**
     * Costruttore completo mancante di codice fiscale
     * @param nome
     * @param cognome
     * @param sesso
     * @param data_nascita
     * @param luogo_nascita
     */
    public Persona(String nome, String cognome, char sesso, LocalDate data_nascita, Comune luogo_nascita) {
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.data_nascita = data_nascita;
        this.luogo_nascita = luogo_nascita;
    }

    /**
     * Costruttore dato solo il codice fiscale
     * @param cod_fiscale della persona
     */
    public Persona(CodiceFiscale cod_fiscale) {
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
     * Metodo che ritorna il codice fiscale della persona
     * @return Ritorna il codice fiscale
     */
    public CodiceFiscale getCod_fiscale() {
        return cod_fiscale;
    }

    /**
     * Metodo che ritorna la data di nascita
     * @return Ritorna data di nascita della persona
     */
    public LocalDate getData_nascita() {
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
     * Metodo permette di settare il C.F. della persona
     * utilizzando il valore restituito dalla funzione calcoloCodiceFiscale
     */
    public void setCodiceFiscale(){
        this.cod_fiscale = CodiceFiscale.calcoloCodiceFiscale(this);
    }





    @Override
    public String toString() {
        return "Persona --> nome: " + nome + ", cognome: " + cognome + ", sesso: " + sesso
                + ", data_nascita: " + data_nascita + ", luogo_nascita: " + luogo_nascita
                + ", cod_fisc_pres: " + cod_fisc_pres + ", cod_fiscale: " + cod_fiscale + "\n";
    }
}
