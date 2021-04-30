package it.unibs.ing.fp.codicifiscali;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.time.LocalDate;
import java.util.*;

/**
 * @author Thomas Causetti
 */
public class LeggiXML {

    //========================================================================================================
    //Costanti
    private static final String READ_DOC = " Start Read Doc ";
    private static final String CODICE = "codice";
    private static final String PERSONA = "persona";
    private static final String COMUNE = "comune";
    public static final String ESTERO = "Estero";
    public static final int MAX = 999;
    public static final int MIN = 100;
    public static final String ERRORE_NELL_INIZ = "Errore nell' inizializzazione del reader:";
    public static final int INDEX_NOME_C = 0;
    public static final int INDEX_CODICE_C = 1;
    public static final int INDEX_COMUNE_P = 3;
    public static final int INDEX_NOME_P = 0;
    public static final int INDEX_COGNOME_P = 1;
    public static final int INDEX_SESSO_P = 2;
    public static final int INDEX_DATA_P = 4;
    public static final int INDEX_CODICEF_C = 0;

    //========================================================================================================
    /**
     * Metodo che genera un XMLStreamReader
     * @param nome_file Stringa contenente il nome del file
     * @return xmlr XMLStreamReader
     */
    public XMLStreamReader xmlStreamReaderGenerator(String nome_file){
        XMLInputFactory xmlif;
        XMLStreamReader xmlr = null;
        try {
            xmlif = XMLInputFactory.newInstance();
            xmlr = xmlif.createXMLStreamReader(nome_file, new FileInputStream(nome_file));
        } catch (Exception e) {
            System.out.println(ERRORE_NELL_INIZ);
            System.out.println(e.getMessage());
        }
        return xmlr;
    }

    //========================================================================================================
    /**
     * Metodo che legge il file xml contenente oggetti di tipo Comune
     * @param citta Arraylist comuni
     * @param filename Nome file
     */
    public void leggiCitta(ArrayList<Comune> citta, String filename) {

        XMLStreamReader xmlr=xmlStreamReaderGenerator(filename);
        try {
            while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione

                //--- Inizio Switch --------------------------------------
                // switch sul tipo di evento
                switch (xmlr.getEventType()) {

                    // inizio del documento: stampa che inizia il documento
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println(READ_DOC + filename);
                    break;

                    // inizio di un elemento: stampa il nome del tag e i suoi attributi
                    case XMLStreamConstants.START_ELEMENT:
                        ArrayList<String> letto=new ArrayList<>();
                        leggiOggettiXml(letto,xmlr, COMUNE);
                        if(letto.size()!=0){
                            citta.add(new Comune(letto.get(INDEX_NOME_C),letto.get(INDEX_CODICE_C)));
                        }
                    break;

                    default:
                    break;
                }
                //--- Fine Switch --------------------------------------

                xmlr.next();
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }


    //========================================================================================================
    /**
     * @author Thomas Causetti
     */
    public void leggiPersone(ArrayList<Persona> persone, String filename,ArrayList<Comune> comuni) {

        XMLStreamReader xmlr=xmlStreamReaderGenerator(filename);
        try {
            while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione

                //--- Inizio Switch --------------------------------------
                // switch sul tipo di evento
                switch (xmlr.getEventType()) {

                    // inizio del documento: stampa che inizia il documento
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println(READ_DOC + filename);
                    break;

                    // inizio di un elemento: stampa il nome del tag e i suoi attributi
                    case XMLStreamConstants.START_ELEMENT:
                        ArrayList<String> letto=new ArrayList<>();
                        leggiOggettiXml(letto,xmlr, PERSONA);
                        if(letto.size()!=0){
                            String comune=letto.get(INDEX_COMUNE_P);
                            Comune comune1 = null;
                            for (int j = 0; j < comuni.size() ; j++) {
                                if (comuni.get(j).getNome().equals(comune)) {
                                    comune1 = new Comune(comune, comuni.get(j).getCodice());
                                }
                            }

                            //Se non presente in lista comuni italiani --> comune = Estero, codice = Z e tre num random
                            if(comune1 == null){

                                Random rand = new Random();
                                int temp = ((MAX-MIN) + 1);
                                comune1 = new Comune(ESTERO, "Z"+((rand.nextInt(temp))+MIN));
                            }

                            persone.add(new Persona(letto.get(INDEX_NOME_P),letto.get(INDEX_COGNOME_P),(letto.get(INDEX_SESSO_P).charAt(0)),LocalDate.parse(letto.get(INDEX_DATA_P)),comune1));
                        }
                    break;

                    default:
                    break;
                }
                //--- Fine Switch --------------------------------------

                xmlr.next();
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }

    }


    //========================================================================================================
    /**
     * @author Thomas Causetti
     */
    public void leggiCodici(ArrayList<CodiceFiscale> arr, String filename) {

        XMLStreamReader xmlr=xmlStreamReaderGenerator(filename);
        try {
            while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione

                //--- Inizio Switch --------------------------------------
                // switch sul tipo di evento
                switch (xmlr.getEventType()) {

                    // inizio del documento: stampa che inizia il documento
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println(READ_DOC + filename);
                    break;

                    // inizio di un elemento: stampa il nome del tag e i suoi attributi
                    case XMLStreamConstants.START_ELEMENT:
                        ArrayList<String> letto=new ArrayList<>();
                        leggiOggettiXml(letto,xmlr,CODICE);

                        if(letto.size()!=0){
                            arr.add(new CodiceFiscale(letto.get(INDEX_CODICEF_C)));
                        }
                    break;

                    default:
                    break;
                }
                //--- Fine Switch --------------------------------------

                xmlr.next();
            }
        }catch (Exception e){
            System.err.println(e.getMessage());
        }
    }


    //========================================================================================================
    /**
     * Metodo che continua a ciclare fino al prossimo getEventType() --> XMLStreamConstants.CHARACTERS
     * @param xmlr XMLStreamReader
     * @throws XMLStreamException throws exception
     */
    private void continuaFinoCaratteri(XMLStreamReader xmlr) throws XMLStreamException {
        do{
            xmlr.next();
        }while(xmlr.getEventType()!= XMLStreamConstants.CHARACTERS);
    }


    //========================================================================================================
    /**
     * Metodo che ritorna un ArrayList di stringhe contenente i diversi attributi di un oggetto.
     * @param letto Arraylist dove vengono salvate le strighe (Passaggio per riferimento)
     * @param xmlr XMLStreamReader attuale
     * @param tag_input e' la stringa del nome del tag xml che contiene tutti gli attributi dell' oggetto (esempio: comune)
     */
    public void leggiOggettiXml(ArrayList<String> letto, XMLStreamReader xmlr, String tag_input){
        boolean fine;
        //solo se stringa
        if(xmlr.getLocalName().equals(tag_input)){
            fine=false;
            //va avanti finché non trova un area di testo
            do {
                //Continua a leggere finche' non trova un getEventType() == XMLStreamConstants.CHARACTERS
                try {
                    continuaFinoCaratteri(xmlr);
                } catch (XMLStreamException e) {
                    System.err.println(e.getMessage());
                }

                //Se ci sono caratteri li aggiunge ad array
                if(xmlr.getText().trim().length() > 0) {
                    letto.add(xmlr.getText());
                }

                //Avanza di uno (Passa a quello dopo CHARACTERS)
                try {
                    xmlr.next();
                } catch (XMLStreamException e) {
                    System.out.println(e.getMessage());
                }

                //Controlla se e' un END_ELEMENT
                if(xmlr.getEventType()==XMLStreamConstants.END_ELEMENT){

                    //Controlla se e' l'END_ELEMENT di tag_input
                    if(xmlr.getLocalName().equalsIgnoreCase(tag_input)) {
                        fine = true;
                    }
                }

            }while(!fine);

        }
    }
    //========================================================================================================
}
