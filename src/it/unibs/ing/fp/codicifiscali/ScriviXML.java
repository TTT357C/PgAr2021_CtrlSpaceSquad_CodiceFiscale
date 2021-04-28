package it.unibs.ing.fp.codicifiscali;

import javax.swing.text.Document;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ScriviXML {

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
            xmlw = xmlof.createXMLStreamWriter(new FileOutputStream("codiciPersone.xml"), "utf-8");
            xmlw.writeStartDocument("utf-8", "1.0");

        } catch (Exception e){

            System.out.println("Errore nell'inizializzazione del writer: ");
            System.out.println(e.getMessage());

        }

        //scrittura struttura xml

        try{

            xmlw.writeStartElement("programmaArnaldo");
            xmlw.writeComment("INIZIO LISTA");

            for(int i=0; i<persona.size(); i++){

                //tag persona

                xmlw.writeStartElement("persona: ");
                xmlw.writeAttribute("id: ", Integer.toString(i));

                //tag nome

                xmlw.writeStartElement("nome: ");
                xmlw.writeCharacters(persona.get(i).getNome());
                xmlw.writeEndElement();

                //tag cognome

                xmlw.writeStartElement("cognome: ");
                xmlw.writeCharacters(persona.get(i).getCognome());
                xmlw.writeEndElement();

                //tag comune di nascita

                xmlw.writeStartElement("comune_nascita: ");
                xmlw.writeCharacters(persona.get(i).getLuogo_nascita().getNome());
                xmlw.writeEndElement();

                //tag data nascita

                xmlw.writeStartElement("data_nascita: ");
                xmlw.writeCharacters(persona.get(i).getData_nascita().format(DateTimeFormatter.ofPattern("YYYY-MM-DD")));
                xmlw.writeEndElement();

                //tag codice fiscale

                xmlw.writeStartElement("cod_fiscale: ");

                if(persona.get(i).getCod_fiscale()!= null){
                    xmlw.writeCharacters(String.valueOf(persona.get(i).getCod_fiscale()));
                }
                else{
                    xmlw.writeCharacters("CODICE ASSENTE");
                }

                xmlw.writeEndElement();

                //chiusura tag persona

                xmlw.writeEndElement();

            }

            //apertura tag codici

            xmlw.writeStartElement("codici");

            //apertura tag codici invalidi

            xmlw.writeStartElement("invalidi");
            xmlw.writeAttribute("numero = ", Integer.toString((codici_invalidi.size()-1)));

            for(int i=0; i<codici_invalidi.size(); i++){

                //tag codice invalidato

                xmlw.writeStartElement("codice");
                xmlw.writeCharacters(codici_invalidi.get(i).toString());
                xmlw.writeEndElement();

            }

            //chiusura tag codici invalidi

            xmlw.writeEndElement();

            //apertura tag codici spaiati

            xmlw.writeStartElement("spaiati");
            xmlw.writeAttribute("numero = ", Integer.toString((codici_spaiati.size()-1)));

            for(int i=0; i<codici_spaiati.size(); i++){

                //tag codice spaiato

                xmlw.writeStartElement("codice");
                xmlw.writeCharacters(codici_spaiati.get(i).toString());
                xmlw.writeEndElement();

            }

            //chiusura tag codici spaiati

            xmlw.writeEndElement();

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

        } catch(Exception e){

            System.out.println("Errore nella scrittura: ");
            System.out.println(e.getMessage());

        }

    }
}
