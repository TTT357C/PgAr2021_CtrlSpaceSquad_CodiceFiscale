package it.unibs.ing.fp.codicifiscali;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ScriviXML {

    public static final String CODICI_PERSONE = "codiciPersone.xml";

    /**
     * metodo per creazione del file XML
     * @author Rossi Mirko
     */


    public void scriviXML(ArrayList<Persona> persona, ArrayList<CodiceFiscale> codici_invalidi, ArrayList<CodiceFiscale> codici_spaiati) {



        //scrittura encoding e versione del file XML

        XMLOutputFactory xmlof = null;
        XMLStreamWriter xmlw = null;

        try{

            xmlof = XMLOutputFactory.newInstance();
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream(CODICI_PERSONE), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");

        } catch (Exception e){

            System.out.println("Errore nell'inizializzazione del writer: ");
            System.out.println(e.getMessage());

        }

        //scrittura struttura xml

        try{

            xmlw.writeStartElement("output");
            xmlw.writeComment("INIZIO LISTA");

            xmlw.writeStartElement("persone");
            xmlw.writeAttribute("numero", persona.size()+"");

            for(int i=0; i<persona.size(); i++){

                //tag persona

                xmlw.writeStartElement("persona");
                xmlw.writeAttribute("id", i+"");

                //tag nome

                xmlw.writeStartElement("nome");
                xmlw.writeCharacters(persona.get(i).getNome());
                xmlw.writeEndElement();

                //tag cognome

                xmlw.writeStartElement("cognome");
                xmlw.writeCharacters(persona.get(i).getCognome());
                xmlw.writeEndElement();

                //tag sesso

                xmlw.writeStartElement("sesso");
                xmlw.writeCharacters(persona.get(i).getSesso()+"");
                xmlw.writeEndElement();

                //tag comune di nascita

                xmlw.writeStartElement("comune_nascita");
                xmlw.writeCharacters(persona.get(i).getLuogo_nascita().getNome());
                xmlw.writeEndElement();

                //tag data nascita

                xmlw.writeStartElement("data_nascita");
                xmlw.writeCharacters(persona.get(i).getData_nascita().format(DateTimeFormatter.ofPattern("YYYY-MM-dd")));
                xmlw.writeEndElement();

                //tag codice fiscale

                xmlw.writeStartElement("cod_fiscale");

                if(persona.get(i).getCod_fiscale()!= null){
                    xmlw.writeCharacters(persona.get(i).getCod_fiscale().getCodice_fiscale());
                }
                else{
                    xmlw.writeCharacters("ASSENTE");
                }

                xmlw.writeEndElement();

                //chiusura tag persona

                xmlw.writeEndElement();

            }

            //chiusura tag persone

            xmlw.writeEndElement();

            //apertura tag codici

            xmlw.writeStartElement("codici");

            //apertura tag codici invalidi

            scriviCodici(codici_invalidi,xmlw,"invalidi");

            //apertura tag codici spaiati

            scriviCodici(codici_spaiati,xmlw,"spaiati");

            //chiusura tag codici

            xmlw.writeEndElement();

            //chiusura tag programmaArnaldo

            xmlw.writeEndElement();

            //chiusura documento

            xmlw.writeEndDocument();

            //svuotamento buffer e scrittura

            xmlw.flush();

            //chiusura documento e risorse impiegate

            xmlw.close();

            //Crea lo stesso file ma indentato.
            try {
                indentaFile();
            }catch (Exception e){
                System.err.println(e);
            }


        } catch(Exception e){

            System.out.println("Errore nella scrittura: ");
            System.out.println(e.getMessage());

        }

    }

    public void indentaFile(){
        try {

            DocumentBuilderFactory dbFactory;
            DocumentBuilder dBuilder;
            Document original = null;

            try {
                dbFactory = DocumentBuilderFactory.newInstance();
                dBuilder = dbFactory.newDocumentBuilder();

                //leggo il file originale
                original = dBuilder.parse(new InputSource(new InputStreamReader(new FileInputStream(CODICI_PERSONE))));
            } catch (SAXException | IOException | ParserConfigurationException e) {
                e.printStackTrace();
            }

            StringWriter stringWriter = new StringWriter();
            StreamResult xmlOutput = new StreamResult(stringWriter);
            TransformerFactory tf = TransformerFactory.newInstance();

            //Transformer per indentazione
            Transformer transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.METHOD, "xml");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

            //Gli passo cio' che ho letto dal file originale
            transformer.transform(new DOMSource(original), xmlOutput);

            //Dopo formattazione scrivo su file
            FileWriter writer;
            writer=new FileWriter("codiciPersoneFormattato.xml");

            BufferedWriter bufferedWriter;
            bufferedWriter=new BufferedWriter (writer);

            bufferedWriter.write(xmlOutput.getWriter().toString());

            bufferedWriter.flush();
            bufferedWriter.close();

        } catch (Exception ex) {
            System.err.println(ex);
        }
    }

    private void scriviCodici(ArrayList<CodiceFiscale> codici,XMLStreamWriter xmlw,String tag) throws XMLStreamException {
        xmlw.writeStartElement(tag);
        xmlw.writeAttribute("numero", Integer.toString((codici.size()-1)));

        for(int i=0; i<codici.size(); i++){

            //tag codice invalidato

            xmlw.writeStartElement("codice");
            xmlw.writeCharacters(codici.get(i).getCodice_fiscale());
            xmlw.writeEndElement();

        }

        //chiusura tag codici invalidi

        xmlw.writeEndElement();
    }
}
