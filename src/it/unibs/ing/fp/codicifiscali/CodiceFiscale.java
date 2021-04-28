package it.unibs.ing.fp.codicifiscali;

import java.util.Objects;

/**
 * @author Thomas Causetti
 */
public class CodiceFiscale {
    private String codice_fiscale;

    public static final char[] ARR_VOCALI = {'A', 'E', 'I', 'O', 'U'};
    public static final char[] ARR_CONSONANTI = {'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'};
    public static final char[] ARR_MESI = {};

    public CodiceFiscale(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    /**
     * Metodo che permette di calcolare il codice fiscale di una persona
     * @return Ritorna il codice fiscale di una persona
     * @author Mirko Rossi
     */
    public static CodiceFiscale calcoloCodiceFiscale(Persona persona){


        String cod_fiscale_str = "";
        String nome_estratto = "";
        String cognome_estratto = "";
        String giorno_nascita = "";
        String mese_nascita = "";
        String anno_nascita = "";
        String comune_nascita = "";

        //nome

        //TODO Mirko controlla

        nome_estratto+=trovaVocaliOConsonanti(ARR_CONSONANTI,persona.getNome());

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

        nome_estratto+=trovaVocaliOConsonanti(ARR_VOCALI, persona.getNome());

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
        cod_fiscale_str += nome_estratto.substring(0, 4);



        //cognome

        cognome_estratto+=trovaVocaliOConsonanti(ARR_CONSONANTI, persona.getCognome());

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

        cognome_estratto+=trovaVocaliOConsonanti(ARR_VOCALI, persona.getCognome());
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
        cod_fiscale_str += cognome_estratto.substring(0, 4);

        //giorno
        int giorno_int = persona.getData_nascita().getDayOfMonth();
        if(persona.getSesso() == 'F'){
            giorno_int = giorno_int + 40;
        }
        giorno_nascita = giorno_nascita+giorno_int;
        cod_fiscale_str += giorno_nascita;

        //mese (i mesi con la classe date partono da 0)



        //anno
        int anno_int = persona.getData_nascita().getYear();
        String anno_string="";
        anno_string = anno_string+anno_int;
        anno_nascita = anno_string.substring(3, 5);
        cod_fiscale_str += anno_nascita;

        //comune
        comune_nascita = persona.getLuogo_nascita().getCodice();


        cod_fiscale_str += comune_nascita;

        //calcolo carattere controllo

        CodiceFiscale codiceFiscale = new CodiceFiscale(cod_fiscale_str);

        return codiceFiscale;
    }

    /**
     * Metodo che evita la ripetizione di questo doppio ciclo in calcoloCodiceFiscale
     * @author Thomas Causetti
     * @return stringa
     */
    private static String trovaVocaliOConsonanti(char[] array, String stringa){
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
    public static boolean controlloCodFiscale(Persona persona){
        //TODO sistema
        if (persona.getCod_fiscale().equals(calcoloCodiceFiscale(persona))){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodiceFiscale that = (CodiceFiscale) o;
        return Objects.equals(codice_fiscale, that.codice_fiscale);
    }

    @Override
    public String toString() {
        return "CodiceFiscale: " + codice_fiscale + "\n";
    }
}
