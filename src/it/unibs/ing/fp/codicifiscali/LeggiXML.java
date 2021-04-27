package it.unibs.ing.fp.codicifiscali;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
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

    public static void extractedCity(ArrayList<Comune> city, DocumentBuilderFactory dbf, String FILENAME) {
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("comune");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getElementsByTagName(TAG_NOME).item(0).getTextContent();
                    String code = element.getElementsByTagName(TAG_CODICE).item(0).getTextContent();
                    city.add(new Comune(name,code));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
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
    public static void extractedCodici(ArrayList<CodiceFiscale> arr, DocumentBuilderFactory dbf,String filename) {
        try {
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(filename));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("codici");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String codice_fis_str = element.getElementsByTagName("codice").item(0).getTextContent();
                    arr.add(new CodiceFiscale(codice_fis_str));
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
