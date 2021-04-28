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


        CodiceFiscale.calcoloCodiceFiscale(persona);

        //calcolo carattere controllo


        return riuscita;

    }
}
