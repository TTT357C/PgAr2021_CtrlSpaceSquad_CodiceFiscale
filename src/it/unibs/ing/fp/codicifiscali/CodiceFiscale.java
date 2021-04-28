package it.unibs.ing.fp.codicifiscali;

import java.util.Objects;

/**
 * @author Thomas Causetti
 */
public class CodiceFiscale {
    private String codice_fiscale;

    public static final char[] ARR_VOCALI = {'A', 'E', 'I', 'O', 'U'};
    public static final char[] ARR_CONSONANTI = {'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'};
    static String [] ARR_MESI_IN_LETTERE = {"A","B","C","D","E","H","L","M","P","R","S","T"};
    static int [] ARR_VALORI_CHAR_PARI = {0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
    static int [] ARR_VALORI_CHAR_DISPARI = {1,0,5,7,9,13,15,17,19,21,1,0,5,7,9,13,15,17,19,21,2,4,18,20,11,3,6,8,12,14,16,10,22,25,24,23};
    static int [] ARR_GIORNI_IN_UN__MESE = {31,28,31,30,31,30,31,31,30,31,30,31};

    public CodiceFiscale(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    /**
     * Metodo che ritorna codice fiscale
     * @return Ritorna codice fiscale
     */

    public String getCodice_fiscale() {
        return codice_fiscale;
    }

    /**
     * Metodo che permette di calcolare il codice fiscale di una persona
     * @return Ritorna il codice fiscale di una persona
     * @author Mirko Rossi
     */
    public static CodiceFiscale calcoloCodiceFiscale(Persona persona){


        String cod_fiscale_str = "";
        String nome_estratto = "";
        String cognome_estratto = "";
        String giorno_nascita = "";
        String mese_nascita = "";
        String anno_nascita = "";
        String comune_nascita = "";

        //nome

        //TODO Mirko controlla

        nome_estratto+=trovaVocaliOConsonanti(ARR_CONSONANTI,persona.getNome());

        /*
        for(int i=0; i<this.getNome().length(); i++){
            char temp1 = this.getNome().charAt(i);
            for(int j=0; j<ARR_CONSONANTI.length; j++) {
                char temp2 = ARR_CONSONANTI[j];
                if (temp1 == temp2) {
                    nome_estratto += temp1;
                }
            }
        }*/

        nome_estratto+=trovaVocaliOConsonanti(ARR_VOCALI, persona.getNome());

        /*
        for(int i=0; i<this.getNome().length(); i++){
            char temp1 = this.getNome().charAt(i);
            for(int j=0; j<ARR_VOCALI.length; j++){
                char temp2 = ARR_VOCALI[j];
                if(temp1==temp2){
                    nome_estratto += temp1;
                }
            }
        }
        */
        cod_fiscale_str += nome_estratto.substring(0, 4);



        //cognome

        cognome_estratto+=trovaVocaliOConsonanti(ARR_CONSONANTI, persona.getCognome());

        /*
        for(int i=0; i<this.getCognome().length(); i++){
            char temp1 = this.getCognome().charAt(i);
            for(int j=0; j<ARR_CONSONANTI.length; j++) {
                char temp2 = ARR_CONSONANTI[j];
                if (temp1 == temp2) {
                    cognome_estratto += temp1;
                }
            }
        }*/

        cognome_estratto+=trovaVocaliOConsonanti(ARR_VOCALI, persona.getCognome());
        /*
        for(int i=0; i<this.getCognome().length(); i++){
            char temp1 = this.getCognome().charAt(i);
            for(int j=0; j<ARR_VOCALI.length; j++){
                char temp2 = ARR_VOCALI[j];
                if(temp1==temp2){
                    cognome_estratto += temp1;
                }
            }
        }*/
        cod_fiscale_str += cognome_estratto.substring(0, 4);

        //giorno
        int giorno_int = persona.getData_nascita().getDayOfMonth();
        if(persona.getSesso() == 'F'){
            giorno_int = giorno_int + 40;
        }
        giorno_nascita = giorno_nascita+giorno_int;
        cod_fiscale_str += giorno_nascita;

        //mese

        int mese_int = persona.getData_nascita().getMonthValue();
        /*for(int i=0; ){

        }*/


        //anno
        int anno_int = persona.getData_nascita().getYear();
        String anno_string="";
        anno_string = anno_string+anno_int;
        anno_nascita = anno_string.substring(3, 5);
        cod_fiscale_str += anno_nascita;

        //comune
        comune_nascita = persona.getLuogo_nascita().getCodice();


        cod_fiscale_str += comune_nascita;

        //calcolo carattere controllo

        CodiceFiscale codiceFiscale = new CodiceFiscale(cod_fiscale_str);

        return codiceFiscale;
    }

    /**
     * Metodo che evita la ripetizione di questo doppio ciclo in calcoloCodiceFiscale
     * @author Thomas Causetti
     * @return stringa
     */
    private static String trovaVocaliOConsonanti(char[] array, String stringa){
        String estratto = "";
        for(int i=0; i < stringa.length(); i++){
            char temp1 = stringa.charAt(i);
            for(int j=0; j<array.length; j++){
                char temp2 = array[j];
                if(temp1==temp2){
                    estratto += temp1;
                }
            }
        }
        return estratto;
    }

    /**
     * Metodo che permette di controllare se il codice fiscale è giusto
     * @author Thomas Causetti
     * @return Ritorna true se il codice fiscale è valido altrimenti falso
     */
    public static boolean controlloCodFiscale(Persona persona){
        //TODO sistema
        if (persona.getCod_fiscale().equals(calcoloCodiceFiscale(persona))){
            return true;
        }
        else{
            return false;
        }
    }
    //TODO ESTRARRE MAGIC NUMBER
    /**
     * Metodo che controlla la validità di un codice fiscale
     * @return Ritorna True se il codice fiscale è valido, false altrimenti
     * @author Mattia Visini
     */
    public boolean validitaCodFiscale(){

        String cf = (this.getCodice_fiscale()).toUpperCase();
        //controllo lunghezza uguale a 16 caratteri altrimenti ritorna subito false
        if(cf.length()==16) {
            String ctrl_surname = cf.substring(0,3);
            //CONTROLLLO 3 cognome
            boolean seq_sur [] = {false,false,false};
            boolean checksur=false;
            for(int i=0; i < ARR_CONSONANTI.length ;i++ ) {
                for (int j = 0; j < ctrl_surname.length(); j++) {
                    if((ctrl_surname.substring(j,j+1)).equals(ARR_CONSONANTI[i])) {
                        seq_sur[j] = true;
                    }
                }
            }
            if(areAllTrue(seq_sur)) {
                //Tutte consonanti
                checksur=true;
            }
            else if(seq_sur[1]==false && seq_sur[2]==true){
                if(isVocale(ctrl_surname.subSequence(1,2)) && ctrl_surname.substring(2,3).equalsIgnoreCase("X")) {
                    checksur=true;
                }
            }
            else if(seq_sur[2]==false && seq_sur[1]==true && seq_sur[0]==true){
                checksur=true;
            }
            else if(seq_sur[2]==false && seq_sur[1]==false && seq_sur[0]==true) {
                checksur=true;
            }//FINE CONTROLLO LETTERE COGNOME
            if(checksur==false){
                return false;
            }

            //INIZIO CONTROLLO NOME
            String ctrl_name = cf.substring(3,6);
            boolean seq_name [] = {false,false,false};
            boolean check_name=false;
            for(int i=0; i < ARR_CONSONANTI.length ;i++ ) {
                for (int j = 0; j < ctrl_name.length(); j++) {
                    if((ctrl_name.substring(j,j+1)).equals(ARR_CONSONANTI[i])) {
                        seq_name[j] = true;
                    }
                }
            }
            if(areAllTrue(seq_name)) {//Tutte consonanti
                check_name=true;
            }
            else if(seq_name[1]==false && seq_name[2]==true){ //CASO FOX NOME DI 2 LETTERE
                if(isVocale(ctrl_name.subSequence(1,2)) && ctrl_name.substring(2,3).equalsIgnoreCase("X")) {
                    check_name=true;
                }
            }
            else if(seq_name[2]==false && seq_name[1]==true && seq_name[0]==true){ //EX NOME TRE
                check_name=true;
            }
            else if(seq_name[2]==false && seq_name[1]==false && seq_name[0]==true) { //NOME £ LETTERE CON 2 VOCALI EX. ADA
                check_name=true;
            }//FINE CONTROOLO NOME
            if(check_name==false) {
                return false;
            }
            
            //CONTROLLO 2 cifre anno
            String ctrlyear = cf.substring(6,8);
            boolean check_years=false;
            if(ctrlyear.charAt(0)>=48 && ctrlyear.charAt(0)<=57 && ctrlyear.charAt(1)>=48 && ctrlyear.charAt(1)<=57) {
                check_years=true;
            } //FINE CHECK YEAR
           if(check_years==false){
               return false;
           }

            //CHECK MESE
            String ctrlmonth = cf.substring(8,9);
            boolean check_month =false;
            int pos_mese=0;
            for (int i = 0; i < ARR_MESI_IN_LETTERE.length; i++) {
                if(ctrlmonth.equalsIgnoreCase(ARR_MESI_IN_LETTERE[i])) {
                    check_month =true;
                    pos_mese=i;
                }
            }//FINE CHECK MESE
            if(check_month==false){
                return false;
            }

            //CHECK GIORNO
            String ctrl_day = cf.substring(9,11);
            boolean check_day=false;
            if(ctrl_day.charAt(0)>= 48 && ctrl_day.charAt(0)<=57 && ctrl_day.charAt(1)>=48 && ctrl_day.charAt(1)<=57) {
                int ctrldayInt = Integer.parseInt(ctrl_day);
                if(ctrldayInt > 0 && ctrldayInt <= ARR_GIORNI_IN_UN__MESE[pos_mese]) {
                    check_day = true;
                }
            }//FINE CHECK GIORNO
            if(check_day==false){
                return false;
            }

            //check codice paese controllo che sia presente in comuni
            String ctrl_code = cf.substring(11,14);
            boolean checkcode=false;
            //Controllo in arraylist Comuni Italiani
            for (int i = 0; i < Main.getComuni().size() ; i++) {
                if (ctrl_code.equalsIgnoreCase(Main.getComuni().get(i).getCodice())){
                    checkcode=true;
                }
            }
            //per straniero
            if(ctrl_code.substring(0,1).equals("Z")) {
                if(ctrl_code.charAt(1)>=48 && ctrl_code.charAt(1)<=57
                        && ctrl_code.charAt(2)>=48 && ctrl_code.charAt(2)<=57
                        && ctrl_code.charAt(3)>=48 && ctrl_code.charAt(3)<=57) {
                    checkcode=true;
                }
            } //fine check codice nata all'estero
            if(checkcode==false){
                return false;
            }

            //CONTROLLO carattere di controllo
            int sum=0;
            for (int i = 0; i < (cf.length()-1); i++) {
                if(i%2==0) {
                    //caratteri in posizione pari
                    char char_temp = cf.charAt(i);
                    if(char_temp>=48 && char_temp<=57 ) {
                        //se è un numero in posizione pari
                        sum=sum+ ARR_VALORI_CHAR_PARI[char_temp-48];
                    }else {
                        //se è lettera in posizione pari
                        if(char_temp>=65 && char_temp<=90) {
                            sum=sum+ ARR_VALORI_CHAR_PARI[char_temp-55];
                        }
                    }
                }else {
                    //caratteri in posizione dispari
                    char t = cf.charAt(i);
                    if(t>=48 && t<=57 ) {
                        //se è un numero in posizione dispari
                        sum=sum+ARR_VALORI_CHAR_DISPARI[t-48];
                    }else {
                        //se è una lettera in posizione dispari
                        if(t>=65 && t<=90) {
                            sum=sum+ARR_VALORI_CHAR_DISPARI[t-55];
                        }
                    }
                }
            }
            char codice_teorico=(char)((sum%26)+65);
            char codice_reale=cf.charAt(15);
            if(codice_teorico == codice_reale) {
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }

    /**
     *
     * @param charSequence Carattere tipo char
     * @return Ritorna true se è una vocale false altrimenti
     */
    private static boolean isVocale(CharSequence charSequence) {
        // TODO Auto-generated method stub
        for (int i = 0; i < ARR_VOCALI.length; i++) {
            if(((String) charSequence).equals(ARR_VOCALI[i])) {
                return true;
            }
        }
        return false;
    }

    /*
     * @param array di tipo boolean
     * @return True gli elementi sono true
     */
    public static boolean areAllTrue(boolean[] array) {
        for(boolean b : array) if(!b) return false;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodiceFiscale that = (CodiceFiscale) o;
        return Objects.equals(codice_fiscale, that.codice_fiscale);
    }

    @Override
    public String toString() {
        return "CodiceFiscale: " + codice_fiscale + "\n";
    }
}
