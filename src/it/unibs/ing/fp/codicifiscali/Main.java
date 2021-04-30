package it.unibs.ing.fp.codicifiscali;

import it.unibs.ing.fp.mylib.InputDati;

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
    public static final String ARRIVEDERCI = "\n _ Arrivederci _____________ \n";
    public static final String ERRORE = " Errore Ritorno a Menu ";
    public static final String FILE_SCRITTO_CORRETTAMENTE = "\n File Scritto Correttamente";


    private static ArrayList<Comune> comuni = new ArrayList<>();

    public static void main(String[] args) {

        int scelta;
        do{
            System.out.println(MENU);
            scelta = InputDati.leggiIntero(" -->", 0, 1);

            //Nel caso avvenga un eccezione il programma visualizza un messaggio di errore ma non termina l' esecuzione
            try {
                switch (scelta) {
                    case 0:
                        System.out.println(ARRIVEDERCI);
                        break;
                    case 1:
                        //letture da file dei comuni

                        //Creo oggetto LeggiXML
                        LeggiXML leggixml=new LeggiXML();
                        leggixml.leggiCitta(comuni, FILENAME_COMUNI);

                        //lettura da file delle persone
                        ArrayList<Persona> persone = new ArrayList<>();

                        leggixml.leggiPersone(persone, FILENAME_PERSONE, comuni);

                        for (int i=0;i<persone.size();i++) {
                            persone.get(i).setCodiceFiscale();
                        }


                        //lettura da file dei codici
                        ArrayList <CodiceFiscale> codice_fis = new ArrayList<>();

                        leggixml.leggiCodici(codice_fis, FILENAME_CODICI);

                        ArrayList <CodiceFiscale> codice_fis_invalidi = new ArrayList<>();
                        ArrayList <CodiceFiscale> codice_fis_validi = new ArrayList<>();

                        for (int i=0;i<codice_fis.size();i++){
                            if (codice_fis.get(i).validitaCodFiscale()){
                                codice_fis_validi.add(codice_fis.get(i));
                            }
                            else{
                                codice_fis_invalidi.add(codice_fis.get(i));
                            }
                        }

                        ArrayList <CodiceFiscale> codice_fis_val_spa = new ArrayList<>();
                        ArrayList <CodiceFiscale> codice_fis_val_acc = new ArrayList<>();
                        codice_fis_val_spa = codice_fis_validi;


                        //For per la suddivisione del array codice_fis_validi in codici accoppiati (acc) e codici spaiati (spa)
                        for (int i=0;i<persone.size();i++) {
                            for (int j=0;j<codice_fis_validi.size();j++){
                                if(codice_fis_validi.get(j).equals(persone.get(i).getCod_fiscale())){
                                    codice_fis_val_acc.add(codice_fis_validi.get(j));
                                    codice_fis_val_spa.remove(j);
                                }
                            }
                        }


                        //Array per Scrivi XML --> codice_fis_invalidi e codice_fis_val_spa

                        //ScriviXML
                        ScriviXML scriviXml = new ScriviXML();
                        scriviXml.scriviXML(persone,codice_fis_invalidi,codice_fis_val_spa);
                        System.out.println(FILE_SCRITTO_CORRETTAMENTE);

                        break;
                    default:
                        break;
                }
            }catch (Exception e){
                System.err.println(ERRORE);
                System.err.println(" ("+e+")");
                // printStackTrace method
                e.printStackTrace();
                System.out.println("\n");
            }

        }while(scelta!=0);

    }

    public static ArrayList<Comune> getComuni() {
        return comuni;
    }
}
