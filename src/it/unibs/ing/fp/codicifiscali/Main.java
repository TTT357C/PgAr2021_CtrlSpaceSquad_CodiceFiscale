package it.unibs.ing.fp.codicifiscali;

import it.unibs.ing.fp.mylib.InputDati;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

public class Main {
    public static final String FILENAME_COMUNI = "comuni.xml";
    public static final String FILENAME_PERSONE = "inputPersone.xml";
    public static final String FILENAME_CODICI = "inputPersone.xml";
    public static final String MENU = "  ___________________________________\n"
                                    + " |                                   |\n"
                                    + " |   - Codici Fiscali -              |\n"
                                    + " |                                   |\n"
                                    + " |   1 - Inizia Programma            |\n"
                                    + " |   0 - Exit                        |\n"
                                    + " |                                   |\n"
                                    + " |___________________________________|\n";

    public static void main(String[] args) {

        int scelta;
        do{
            System.out.println(MENU);
            scelta = InputDati.leggiIntero(" -->", 0, 1);

            //Nel caso avvenga un eccezione il programma visualizza un messaggio di errore ma non termina l'esecuzione
            try {
                switch (scelta) {
                    case 0:
                        System.out.println("\n _ Arrivederci _____________ \n");
                        break;
                    case 1:
                        //letture da file dei comuni
                        ArrayList<Comune>  comuni = new ArrayList<>();
                        DocumentBuilderFactory comune_builder = DocumentBuilderFactory.newInstance();
                        LeggiXML.extractedCity(comuni, comune_builder,FILENAME_COMUNI);

                        //lettura da file delle persone
                        ArrayList<Persona> persone = new ArrayList<>();
                        DocumentBuilderFactory people_builder = DocumentBuilderFactory.newInstance();
                        LeggiXML.extractedPersone(persone, people_builder,FILENAME_PERSONE,comuni);

                        //lettura da file delle persone
                        ArrayList<CodiceFiscale> codice_fis = new ArrayList<>();
                        DocumentBuilderFactory codici_builder = DocumentBuilderFactory.newInstance();
                        LeggiXML.extractedCodici(codice_fis, codici_builder,FILENAME_CODICI);

                        System.out.println(comuni);
                        System.out.println(persone);
                        break;
                    default:
                        break;
                }
            }catch (Exception e){
                System.err.println(" Errore Ritorno a Menu ");
                System.err.println(" ("+e+")");
                System.out.println("\n");
            }

        }while(scelta!=0);



    }


}
