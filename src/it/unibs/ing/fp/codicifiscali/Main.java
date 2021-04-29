package it.unibs.ing.fp.codicifiscali;

import it.unibs.ing.fp.mylib.InputDati;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.util.ArrayList;

/**
 * @author Thomas Causetti
 */

public class Main {

    //Costanti
    public static final String FILENAME_COMUNI = "comuni.xml";
    public static final String FILENAME_PERSONE = "inputPersone.xml";
    public static final String FILENAME_CODICI = "codiciFiscali.xml";
    public static final String MENU = "  ___________________________________\n"
                                    + " |                                   |\n"
                                    + " |   - Codici Fiscali -              |\n"
                                    + " |                                   |\n"
                                    + " |   1 - Inizia Programma            |\n"
                                    + " |   0 - Exit                        |\n"
                                    + " |                                   |\n"
                                    + " |___________________________________|\n";


    private static ArrayList<Comune> comuni = new ArrayList<>();

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


                        LeggiXML.leggiCitta(comuni, FILENAME_COMUNI);

                        //lettura da file delle persone
                        ArrayList<Persona> persone = new ArrayList<>();

                        LeggiXML.leggiPersone(persone, FILENAME_PERSONE,comuni);

                        //lettura da file dei codici
                        ArrayList <CodiceFiscale> codice_fis = new ArrayList<>();

                        LeggiXML.leggiCodici(codice_fis, FILENAME_CODICI);

                        System.out.println(comuni);
                        System.out.println(persone);
                        System.out.println(codice_fis);
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

    public static ArrayList<Comune> getComuni() {
        return comuni;
    }
}
