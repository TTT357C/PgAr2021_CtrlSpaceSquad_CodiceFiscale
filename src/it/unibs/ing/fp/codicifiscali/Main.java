package it.unibs.ing.fp.codicifiscali;

import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;

public class Main {
    public static final String FILENAME_COMUNI = "comuni.xml";
    public static final String FILENAME_PERSONE = "inputPersone.xml";

    public static void main(String[] args) {
        //letture da file dei comuni
        ArrayList<Comune>  comuni = new ArrayList<>();
        DocumentBuilderFactory comune_builder = DocumentBuilderFactory.newInstance();
        LeggiXML.extractedCity(comuni, comune_builder,FILENAME_COMUNI);
        
        //lettura da file delle persone
        ArrayList<Persona> persone = new ArrayList<>();
        DocumentBuilderFactory people_builder = DocumentBuilderFactory.newInstance();
        LeggiXML.extractedPersone(persone, people_builder,FILENAME_PERSONE,comuni);



        System.out.println(comuni);
        System.out.println(persone);
    }


}
