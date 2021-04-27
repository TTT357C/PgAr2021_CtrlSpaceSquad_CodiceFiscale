package it.unibs.ing.fp.codicifiscali;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.parsers.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class LeggiXML {

    public static void extractedCity(ArrayList<Comune> city, DocumentBuilderFactory dbf,String FILENAME) {
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
                    String name = element.getElementsByTagName("nome").item(0).getTextContent();
                    String code = element.getElementsByTagName("codice").item(0).getTextContent();
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
            NodeList list = doc.getElementsByTagName("persona");
            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String firstname = element.getElementsByTagName("nome").item(0).getTextContent();
                    String lastname = element.getElementsByTagName("cognome").item(0).getTextContent();
                    String sesso = element.getElementsByTagName("sesso").item(0).getTextContent();
                    String comune = element.getElementsByTagName("comune_nascita").item(0).getTextContent();
                    String bday = element.getElementsByTagName("data_nascita").item(0).getTextContent();
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

}
