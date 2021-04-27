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
    private char char_controllo;
    private String cod_fiscale;

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
    public void setCodiceFiscale(ArrayList<Comune> comune){
        this.cod_fiscale = this.calcoloCodiceFiscale(comune);
    }

    /**
     * Metodo che permette di calcolare il codice fiscale di una persona
     * @return Ritorna il codice fiscale di una persona
     * @author Mirko Rossi
     */
    public String calcoloCodiceFiscale(ArrayList<Comune> comune){

        final char[] ARR_VOCALI = {'A', 'E', 'I', 'O', 'U'};
        final char[] ARR_CONSONANTI = {'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'};
        final char[] ARR_MESI = {};

        String cod_fiscale = "";
        String nome_estratto = "";
        String cognome_estratto = "";
        String giorno_nascita = "";
        String mese_nascita = "";
        String anno_nascita = "";
        String comune_nascita = "";

        //nome

        //TODO Mirko controlla

        nome_estratto+=trovaVocaliOConsonanti(ARR_CONSONANTI,nome);

        /*
        for(int i=0; i<this.getNome().length(); i++){
            char temp1 = this.getNome().charAt(i);
            for(int j=0; j<ARR_CONSONANTI.length; j++) {
                char temp2 = ARR_CONSONANTI[j];
                if (temp1 == temp2) {
                    nome_estratto += temp1;
                }
            }
        }*/

        nome_estratto+=trovaVocaliOConsonanti(ARR_VOCALI,nome);

        /*
        for(int i=0; i<this.getNome().length(); i++){
            char temp1 = this.getNome().charAt(i);
            for(int j=0; j<ARR_VOCALI.length; j++){
                char temp2 = ARR_VOCALI[j];
                if(temp1==temp2){
                    nome_estratto += temp1;
                }
            }
        }
        */
        cod_fiscale += nome_estratto.substring(0, 4);



        //cognome

        cognome_estratto+=trovaVocaliOConsonanti(ARR_CONSONANTI,cognome);

        /*
        for(int i=0; i<this.getCognome().length(); i++){
            char temp1 = this.getCognome().charAt(i);
            for(int j=0; j<ARR_CONSONANTI.length; j++) {
                char temp2 = ARR_CONSONANTI[j];
                if (temp1 == temp2) {
                    cognome_estratto += temp1;
                }
            }
        }*/

        cognome_estratto+=trovaVocaliOConsonanti(ARR_VOCALI,cognome);
        /*
        for(int i=0; i<this.getCognome().length(); i++){
            char temp1 = this.getCognome().charAt(i);
            for(int j=0; j<ARR_VOCALI.length; j++){
                char temp2 = ARR_VOCALI[j];
                if(temp1==temp2){
                    cognome_estratto += temp1;
                }
            }
        }*/
        cod_fiscale += cognome_estratto.substring(0, 4);

        //giorno
        int giorno_int = this.getData_nascita().getDayOfMonth();
        if(this.getSesso() == 'F'){
            giorno_int = giorno_int + 30;
        }
        giorno_nascita = giorno_nascita+giorno_int;
        cod_fiscale += giorno_nascita;

        //mese (i mesi con la classe date partono da 0)



        //anno
        int anno_int = this.getData_nascita().getYear();
        String anno_string="";
        anno_string = anno_string+anno_int;
        anno_nascita = anno_string.substring(3, 5);
        cod_fiscale += anno_nascita;

        //comune
        comune_nascita = this.luogo_nascita.getCodice();


        cod_fiscale += comune_nascita;

        //calcolo carattere controllo



        return cod_fiscale;
    }

    /**
     * Metodo che evita la ripetizione di questo doppio ciclo in calcoloCodiceFiscale
     * @author Thomas Causetti
     * @return stringa
     */
    private String trovaVocaliOConsonanti(char[] array, String stringa){
        String estratto = "";
        for(int i=0; i < stringa.length(); i++){
            char temp1 = stringa.charAt(i);
            for(int j=0; j<array.length; j++){
                char temp2 = array[j];
                if(temp1==temp2){
                    estratto += temp1;
                }
            }
        }
        return estratto;
    }

    /**
     * Metodo che permette di controllare se il codice fiscale è giusto
     * @author Thomas Causetti
     * @return Ritorna true se il codice fiscale è valido altrimenti falso
     */
    /*public boolean controlloCodFiscale(){
        //TODO sistema
        if (cod_fiscale.equals(calcoloCodiceFiscale())){
            return true;
        }
        else{
            return false;
        }
    }*/
}
