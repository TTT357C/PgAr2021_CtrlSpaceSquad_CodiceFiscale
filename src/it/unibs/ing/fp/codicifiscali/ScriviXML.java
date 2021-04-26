package it.unibs.ing.fp.codicifiscali;

import java.util.ArrayList;

public class ScriviXML {

    /**
     * metodo per creazione codice fiscale
     * @return boolean per riuscita di creazione codice fiscale
     * @author Rossi Mirko
     */

    public boolean scriviCodiciPersone(Persona persona, ArrayList<Comune> comune) {

        boolean riuscita = true;

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

        for(int i=0; i<persona.getNome().length(); i++){

            char temp1 = persona.getNome().charAt(i);

            for(int j=0; j<ARR_CONSONANTI.length; j++) {

                char temp2 = ARR_CONSONANTI[j];

                if (temp1 == temp2) {

                    nome_estratto += temp1;
                }
            }
        }

        for(int i=0; i<persona.getNome().length(); i++){

            char temp1 = persona.getNome().charAt(i);

            for(int j=0; j<ARR_VOCALI.length; j++){

                char temp2 = ARR_VOCALI[j];

                if(temp1==temp2){

                    nome_estratto += temp1;

                }
            }
        }

        cod_fiscale += nome_estratto.substring(0, 4);

        //cognome


        for(int i=0; i<persona.getCognome().length(); i++){

            char temp1 = persona.getCognome().charAt(i);

            for(int j=0; j<ARR_CONSONANTI.length; j++) {

                char temp2 = ARR_CONSONANTI[j];

                if (temp1 == temp2) {

                    cognome_estratto += temp1;

                }
            }
        }

        for(int i=0; i<persona.getCognome().length(); i++){

            char temp1 = persona.getCognome().charAt(i);

            for(int j=0; j<ARR_VOCALI.length; j++){

                char temp2 = ARR_VOCALI[j];

                if(temp1==temp2){

                    cognome_estratto += temp1;

                }
            }
        }

        cod_fiscale += cognome_estratto.substring(0, 4);

        //giorno

        int giorno_int = persona.getData_nascita().getDay();

        if(persona.getSesso() == 'F'){
            giorno_int = giorno_int + 30;
        }

        giorno_nascita = giorno_nascita+giorno_int;

        cod_fiscale += giorno_nascita;

        //mese (i mesi con la classe date partono da 0)



        //anno

        int anno_int = persona.getData_nascita().getYear();

        String anno_string="";
        anno_string = anno_string+anno_int;

        anno_nascita = anno_string.substring(3, 5);

        cod_fiscale += anno_nascita;

        //comune

        for (int i=0; i<comune.size(); i++){

            if (persona.getLuogo_nascita().getCodice() == comune.get(i).getCodice()){

                comune_nascita = comune.get(i).getCodice();

            }
        }

        cod_fiscale += comune_nascita;

        //calcolo carattere controllo


        return riuscita;

    }
}
