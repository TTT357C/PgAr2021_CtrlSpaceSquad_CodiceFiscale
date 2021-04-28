package it.unibs.ing.fp.codicifiscali;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class LeggiXML {

    //Costanti dei Tag
    public static final String TAG_PERSONA = "persona";
    public static final String TAG_NOME = "nome";
    public static final String TAG_CODICE = "codice";
    public static final String TAG_COGNOME = "cognome";
    public static final String TAG_SESSO = "sesso";
    public static final String TAG_COMUNE_NASCITA = "comune_nascita";
    public static final String TAG_DATA_NASCITA = "data_nascita";

    public static void leggiCitta(ArrayList<Comune> citta, XMLStreamReader xmlr, String filename) {
        try {
            while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione

                // switch sul tipo di evento
                switch (xmlr.getEventType()) {

                    // inizio del documento: stampa che inizia il documento
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println(" Start Read Doc " + filename);
                        break;

                    // inizio di un elemento: stampa il nome del tag e i suoi attributi
                    case XMLStreamConstants.START_ELEMENT:
                        String nome="";
                        String codice="";

                        switch (xmlr.getLocalName()){
                            //solo se comune
                            case "comune":
                                //va avanti finché non trova un area di testo
                                do {
                                    continuaFinoCaratteri(xmlr);
                                }while(xmlr.getText().trim().length() == 0);
                                nome=xmlr.getText();

                                //va avanti finché non trova un area di testo
                                do {
                                    continuaFinoCaratteri(xmlr);
                                }while(xmlr.getText().trim().length() == 0);
                                codice=xmlr.getText();

                            break;
                            default:
                            break;
                        }

                        citta.add(new Comune(nome,codice));
                    break;

                    default:
                    break;
                }
                xmlr.next();
            }
        }catch (Exception e){
            System.err.println(e);
        }
    }

    public static void extractedPersone(ArrayList<Persona> arr, DocumentBuilderFactory dbf,String FILENAME,ArrayList<Comune> comuni) {
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName(TAG_PERSONA);
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String firstname = element.getElementsByTagName(TAG_NOME).item(0).getTextContent();
                    String lastname = element.getElementsByTagName(TAG_COGNOME).item(0).getTextContent();
                    String sesso = element.getElementsByTagName(TAG_SESSO).item(0).getTextContent();
                    String comune = element.getElementsByTagName(TAG_COMUNE_NASCITA).item(0).getTextContent();
                    String bday = element.getElementsByTagName(TAG_DATA_NASCITA).item(0).getTextContent();
                    char sex = sesso.charAt(0);
                    LocalDate date1 = LocalDate.parse(bday);
                    Comune comune1 = null;

                    for (int j = 0; j < comuni.size() ; j++) {
                        if (comuni.get(j).getNome().equals(comune)) {
                            comune1 = new Comune(comune, comuni.get(j).getCodice());
                        }
                    }
                    arr.add(new Persona(firstname,lastname,sex,date1,comune1));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Thomas Causetti
     */
    public static void leggiCodici(ArrayList<CodiceFiscale> arr, XMLStreamReader xmlr, String filename) {
        try {
            while (xmlr.hasNext()) { // continua a leggere finché ha eventi a disposizione

                // switch sul tipo di evento
                switch (xmlr.getEventType()) {

                    // inizio del documento: stampa che inizia il documento
                    case XMLStreamConstants.START_DOCUMENT:
                        System.out.println(" Start Read Doc " + filename);
                        break;

                    // inizio di un elemento: stampa il nome del tag e i suoi attributi
                    case XMLStreamConstants.START_ELEMENT:
                        System.out.println(" Tag " + xmlr.getLocalName());
                        //entra per leggere caratteri
                        xmlr.next();
                        // content all’interno di un elemento: stampa il testo
                        // controlla se il testo non contiene solo spazi
                        if (xmlr.getText().trim().length() > 0)
                            System.out.println(" -> " + xmlr.getText());
                        arr.add(new CodiceFiscale(xmlr.getText()));
                        break;

                    // fine di un elemento: stampa il nome del tag chiuso
                    case XMLStreamConstants.END_ELEMENT:
                        System.out.println(" END-Tag " + xmlr.getLocalName());
                        break;

                    default:
                        break;
                }
                xmlr.next();
            }
        }catch (Exception e){
            System.err.println(e);
        }
    }

    private static void continuaFinoCaratteri(XMLStreamReader xmlr) throws XMLStreamException {
        do{
            xmlr.next();
        }while(xmlr.getEventType()!= XMLStreamConstants.CHARACTERS);
    }
}
