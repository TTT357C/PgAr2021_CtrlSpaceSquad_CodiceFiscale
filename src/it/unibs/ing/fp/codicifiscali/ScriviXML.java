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

        /*
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

            for(int j=0; j<Persona.ARR_CONSONANTI.length; j++) {

                char temp2 = Persona.ARR_CONSONANTI[j];

                if (temp1 == temp2) {

                    nome_estratto += temp1;
                }
            }
        }

        for(int i=0; i<persona.getNome().length(); i++){

            char temp1 = persona.getNome().charAt(i);

            for(int j=0; j<Persona.ARR_VOCALI.length; j++){

                char temp2 = Persona.ARR_VOCALI[j];

                if(temp1==temp2){

                    nome_estratto += temp1;

                }
            }
        }

        cod_fiscale += nome_estratto.substring(0, 4);

        //cognome


        for(int i=0; i<persona.getCognome().length(); i++){

            char temp1 = persona.getCognome().charAt(i);

            for(int j=0; j<Persona.ARR_CONSONANTI.length; j++) {

                char temp2 = Persona.ARR_CONSONANTI[j];

                if (temp1 == temp2) {

                    cognome_estratto += temp1;

                }
            }
        }

        for(int i=0; i<persona.getCognome().length(); i++){

            char temp1 = persona.getCognome().charAt(i);

            for(int j=0; j< Persona.ARR_VOCALI.length; j++){

                char temp2 = Persona.ARR_VOCALI[j];

                if(temp1==temp2){

                    cognome_estratto += temp1;

                }
            }
        }

        cod_fiscale += cognome_estratto.substring(0, 4);

        //giorno

        int giorno_int = persona.getData_nascita().getDayOfMonth();

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
        */

        CodiceFiscale.calcoloCodiceFiscale(persona);

        //calcolo carattere controllo


        return riuscita;

    }
}
