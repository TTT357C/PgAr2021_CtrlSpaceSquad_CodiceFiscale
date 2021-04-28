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

                        XMLInputFactory xmlif = null;
                        XMLStreamReader xmlr = null;
                        try {
                            xmlif = XMLInputFactory.newInstance();
                            xmlr = xmlif.createXMLStreamReader(FILENAME_COMUNI, new FileInputStream(FILENAME_COMUNI));
                        } catch (Exception e) {
                            System.out.println("Errore nell'inizializzazione del reader:");
                            System.out.println(e.getMessage());
                        }
                        LeggiXML.leggiCitta(comuni, xmlr, FILENAME_COMUNI);

                        //lettura da file delle persone
                        ArrayList<Persona> persone = new ArrayList<>();
                        DocumentBuilderFactory people_builder = DocumentBuilderFactory.newInstance();
                        LeggiXML.extractedPersone(persone, people_builder,FILENAME_PERSONE,comuni);

                        //lettura da file dei codici
                        ArrayList <CodiceFiscale> codice_fis = new ArrayList<CodiceFiscale>();
                        XMLInputFactory xmlif2 = null;
                        XMLStreamReader xmlr2 = null;
                        try {
                            xmlif2 = XMLInputFactory.newInstance();
                            xmlr2 = xmlif2.createXMLStreamReader(FILENAME_CODICI, new FileInputStream(FILENAME_CODICI));
                        } catch (Exception e) {
                            System.out.println("Errore nell'inizializzazione del reader:");
                            System.out.println(e.getMessage());
                        }
                        LeggiXML.leggiCodici(codice_fis, xmlr2, FILENAME_CODICI);

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
