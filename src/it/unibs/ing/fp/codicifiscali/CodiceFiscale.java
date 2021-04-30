package it.unibs.ing.fp.codicifiscali;

/**
 * @author Thomas Causetti
 */
public class CodiceFiscale {

    public static final int ASCII_ZERO = 48;
    public static final int ASCII_NOVE = 57;
    public static final int ASCII_A = 65;
    public static final int ASCII_Z = 90;
    public static final char[] ARR_VOCALI = {'A', 'E', 'I', 'O', 'U'};
    public static final char[] ARR_CONSONANTI = {'B', 'C', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'V', 'W', 'X', 'Y', 'Z'};
    public static final int INCREMENTO_MESE_DONNE = 40;
    public static final int DELTA_ARRAY_SOMMA_CHAR = 55;
    public static final int ALFABETO_LENGTH = 26;
    static final char [] ARR_MESI_IN_LETTERE = {'A','B','C','D','E','H','L','M','P','R','S','T'};
    public static final int CF_LENGTH = 16;
    public static final char[] ARR_ALFABETO_NUM = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static final char[] ARR_ALFABETO = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    static final int [] ARR_VALORI_CHAR_PARI = {0,1,2,3,4,5,6,7,8,9,0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25};
    static final int [] ARR_VALORI_CHAR_DISPARI = {1,0,5,7,9,13,15,17,19,21,1,0,5,7,9,13,15,17,19,21,2,4,18,20,11,3,6,8,12,14,16,10,22,25,24,23};
    static final int [] ARR_GIORNI_IN_UN__MESE = {31,28,31,30,31,30,31,31,30,31,30,31};

    private String codice_fiscale;

    /**
     * Costruttore di codice fiscale
     * @param codice_fiscale codice fiscale
     */
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
    public static CodiceFiscale calcoloCodiceFiscale(Persona persona) {


        String cod_fiscale_str = "";
        String nome_estratto = "";
        String cognome_estratto = "";
        String giorno_nascita = "";
        String mese_nascita = "";
        String anno_nascita = "";
        String comune_nascita = "";

        int sommatoria_valori = 0;

        //cognome

        cognome_estratto += trovaVocaliOConsonanti(ARR_CONSONANTI, persona.getCognome());
        cognome_estratto += trovaVocaliOConsonanti(ARR_VOCALI, persona.getCognome());
        cognome_estratto += "XXX";
        cod_fiscale_str += cognome_estratto.substring(0, 3);

        //nome

        nome_estratto += trovaVocaliOConsonanti(ARR_CONSONANTI, persona.getNome());
        if(nome_estratto.length()>=4){
            nome_estratto=nome_estratto.charAt(0)+nome_estratto.substring(2,4);
        }

        nome_estratto += trovaVocaliOConsonanti(ARR_VOCALI, persona.getNome());
        nome_estratto += "XXX";
        cod_fiscale_str += nome_estratto.substring(0, 3);


        //anno

        int anno_int = persona.getData_nascita().getYear();
        String anno_string = "";
        anno_string = anno_string + anno_int;
        anno_nascita = anno_string.substring(2, 4);
        cod_fiscale_str += anno_nascita;

        //mese

        int mese_int = persona.getData_nascita().getMonthValue();
        char mese_nascita_char = ARR_MESI_IN_LETTERE[mese_int - 1];
        mese_nascita += mese_nascita_char;
        cod_fiscale_str += mese_nascita;


        //giorno
        int giorno_int = persona.getData_nascita().getDayOfMonth();
        if (persona.getSesso() == 'F') {
            giorno_int = giorno_int + 40;
        }
        giorno_nascita = giorno_nascita + giorno_int;
        if (Integer.parseInt(giorno_nascita) < 10) {
            giorno_nascita = "0" + giorno_nascita;
        }
        cod_fiscale_str += giorno_nascita;


        //comune
        comune_nascita = persona.getLuogo_nascita().getCodice();

        cod_fiscale_str += comune_nascita;

        //calcolo carattere controllo

        //Per il codice fiscale si parte a contare da 1, nel charAt abbiamo messo i-1 in questo modo non va in overflow
        for(int i=1;i<=cod_fiscale_str.length();i++){

            for(int j=0;j<ARR_ALFABETO_NUM.length;j++) {

                //Calcola sommatoria_valori cercando il valore del carattere in posizione [i]
                if(ARR_ALFABETO_NUM[j]==cod_fiscale_str.charAt(i-1)) {
                    //se pari
                    if (i % 2 == 0) {
                        sommatoria_valori+=ARR_VALORI_CHAR_PARI[j];
                    } else {
                        sommatoria_valori+=ARR_VALORI_CHAR_DISPARI[j];
                    }

                }
            }
        }


        int resto_calcolo_carattere = sommatoria_valori%ALFABETO_LENGTH;

        char char_controllo = ARR_ALFABETO[resto_calcolo_carattere];

        cod_fiscale_str += char_controllo;

        return new CodiceFiscale(cod_fiscale_str);
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
     * Metodo che controlla la validità di un codice fiscale
     * @return Ritorna True se il codice fiscale è valido, false altrimenti
     * @author Mattia Visini
     */
    public boolean validitaCodFiscale(){

        String cf = (this.getCodice_fiscale()).toUpperCase();
        //controllo lunghezza uguale a 16 caratteri altrimenti ritorna subito false
        if(cf.length() == CF_LENGTH) {
            //CONTROLLO COGNOME
            boolean checksur = checkSurname(cf);
            if(!checksur){
                return false;
            }
            //INIZIO CONTROLLO NOME
            boolean check_name = checkName(cf);
            if(!check_name) {
                return false;
            }
            //CONTROLLO 2 cifre anno
            boolean check_years = checkYear(cf);
            if(!check_years){
               return false;
           }
            //CHECK MESE
            char ctrlmonth = cf.charAt(8);
            boolean check_month =false;
            int pos_mese=0;
            for (int i = 0; i < ARR_MESI_IN_LETTERE.length; i++) {
                if(ctrlmonth == (ARR_MESI_IN_LETTERE[i])) {
                    check_month = true;
                    pos_mese = i;
                }
            }//FINE CHECK MESE
            if(!check_month){
                return false;
            }
            //CHECK GIORNO
            boolean check_day = checkDay(cf, pos_mese);
            if(!check_day){
                return false;
            }
            //check codice paese controllo che sia presente in comuni
            boolean checkcode = checkCode(cf);
            if(!checkcode){
                return false;
            }
            //CONTROLLO carattere di controllo
            if(calcoloCharControllo(cf) == ((char) cf.charAt(CF_LENGTH-1))) {
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
     *Metodo che controlla i primi 3 caratteri
     * @param cf Codice Fiscale
     * @return True se i primi 3 caratteri del codice fiscale sono in un foramto corretto
     */
    private boolean checkSurname(String cf) {
        String ctrl_surname = cf.substring(0,3);
        boolean [] seq_sur =sonoConsonanti(ctrl_surname);

        return isChecksur(ctrl_surname, seq_sur);
    }

    /**
     * Metodo controlla se il formato dei secondi 3 caratteri del c.f. sono validi
     * @param cf Codice fiscale
     * @return True se il formato del nome è corretto, false altrimenti
     */
    private boolean checkName(String cf) {
        String ctrl_name = cf.substring(3,6);
        boolean [] seq_name =sonoConsonanti(ctrl_name);

        return isChecksur(ctrl_name, seq_name);
    }

    /**
     *  Metodo che controlla se il formato dell'anno è corretto
     * @param cf codice fiscale
     * @return true se l'anno di nascita è valido, false altrimenti
     */
    private boolean checkYear(String cf) {
        String ctrl_year = cf.substring(6,8);

        return isCheckYears(ctrl_year);
    }

    /**
     *Metodo che controlla se i 2 caratteri dell'anno di nascita sono in un formato valido
     * @param cf Codice fiscale
     * @param pos_mese Posizione del mese calcolata in precedenza
     * @return True se il giorno è valido false altrimenti
     */
    private boolean checkDay(String cf, int pos_mese) {
        String ctrl_day = cf.substring(9,11);

        return isCheckDay(pos_mese, ctrl_day);
    }

    /**
     *Metodo che calcola se il codice del comune di nascita è valido
     * @param cf Codice fiscale
     * @return True se il codice del comune è valido, false altrimenti
     */
    private boolean checkCode(String cf) {
        String ctrl_code = cf.substring(11,15);
        boolean check_code=false;
        //Controllo in arraylist Comuni Italiani
        check_code = isCheckItalyCode(ctrl_code);
        //Per straniero
        check_code = isCheckForeignCode(ctrl_code, check_code);
        return check_code;
    }

    /**
     * Metodo che calcola il codice di controllo del codice fiscale passato
     * @param cf Codice Fiscale come stringa
     * @return Char carattere di controllo del c.f.
     */
    private char calcoloCharControllo(String cf) {
        int sum=0;
        for (int i = 0; i < (cf.length()-1); i++) {
            if((i+1)%2 == 0) {
                //caratteri in posizione pari
                char char_temp = cf.charAt(i);
                if(char_temp >= ASCII_ZERO && char_temp <= ASCII_NOVE ) {
                    //se è un numero in posizione pari
                    sum = sum + ARR_VALORI_CHAR_PARI[char_temp-ASCII_ZERO];
                }else {
                    //se è lettera in posizione pari
                    if(char_temp>=ASCII_A && char_temp<=ASCII_Z) {
                        sum = sum + ARR_VALORI_CHAR_PARI[char_temp- DELTA_ARRAY_SOMMA_CHAR];
                    }
                }
            }else {
                //caratteri in posizione dispari
                char t = cf.charAt(i);
                if(t >= ASCII_ZERO && t <= ASCII_NOVE ) {
                    //se è un numero in posizione dispari
                    sum = sum + ARR_VALORI_CHAR_DISPARI[t-ASCII_ZERO];
                }else {
                    //se è una lettera in posizione dispari
                    if(t >= ASCII_A && t <= ASCII_Z) {
                        sum = sum + ARR_VALORI_CHAR_DISPARI[t-DELTA_ARRAY_SOMMA_CHAR];
                    }
                }
            }
        }

        return (char)((sum % ALFABETO_LENGTH) + ASCII_A);
    }

    //---INIZIO METODI UTILIZZATI NEI PRECEDENTI---
    /**
     *
     * @param ctrl_code Codice comune di nascita
     * @param checkcode Boolean è true se è gia stato trovato nei paesi italiani , false altrimenti
     * @return True se il codice è di un paese estero oppure checkcode è true dunque è gia stato trovato nei paesi italiani, false altrimenti
     */
    private boolean isCheckForeignCode(String ctrl_code, boolean checkcode) {
        if(checkcode){
            return true;
        }
        if(ctrl_code.charAt(0) == 'Z') {
            if(ctrl_code.charAt(1)>=ASCII_ZERO && ctrl_code.charAt(1)<=ASCII_NOVE
                    && ctrl_code.charAt(2)>=ASCII_ZERO && ctrl_code.charAt(2)<=ASCII_NOVE
                    && ctrl_code.charAt(3)>=ASCII_ZERO && ctrl_code.charAt(3)<=ASCII_NOVE) {
                return true;
            }
        }
        return false;
    }

    /**
     * Metodo che cerca se il codice del comune di nascita è valido ed è presente nell'array list comuni
     * @param ctrl_code Codice comune di nascita
     * @return True se è valido il codice, false altrimenti;
     */
    private boolean isCheckItalyCode(String ctrl_code) {
        boolean checkcode = false;
        for (int i = 0; i < Main.getComuni().size() ; i++) {
            if (ctrl_code.equals(Main.getComuni().get(i).getCodice())){
                checkcode =true;
            }
        }
        return checkcode;
    }

    /**
     * Calcola se il giorno rappresentato sul c.f. è valido
     * @param pos_mese Posizione del mese nell' array calcolato precedentemente
     * @param ctrl_day stringa rappresentante un numero composto da 2 cifre
     * @return Ritorna true se il giorno di nascita è valido false altrimenti
     */
    private boolean isCheckDay(int pos_mese, String ctrl_day) {
        boolean check_day =false;
        if(ctrl_day.charAt(0) >= ASCII_ZERO && ctrl_day.charAt(0) <= ASCII_NOVE && ctrl_day.charAt(1) >= ASCII_ZERO && ctrl_day.charAt(1) <= ASCII_NOVE) {
            int ctrldayInt = Integer.parseInt(ctrl_day);
            if(ctrldayInt > 0 && (ctrldayInt <= ARR_GIORNI_IN_UN__MESE[pos_mese] ||  (ctrldayInt <= (ARR_GIORNI_IN_UN__MESE[pos_mese]+ INCREMENTO_MESE_DONNE)))) {
                check_day = true;
            }
        }//FINE CHECK GIORNO
        return check_day;
    }

    /**
     * Metodo chhe controlla che sia inserito un numero rappresentato da 2 cifre
     * @param ctrlyear Stringa di 2 caratteri rappresentanti l' anno di nascita
     * @return True se è un anno valido false altrimenti
     */
    private boolean isCheckYears(String ctrlyear) {
        boolean check_years = false;
        if(ctrlyear.charAt(0) >= ASCII_ZERO && ctrlyear.charAt(0) <= ASCII_NOVE && ctrlyear.charAt(1) >= ASCII_ZERO && ctrlyear.charAt(1) <= ASCII_NOVE) {
            check_years =true;
        } //FINE CHECK YEAR
        return check_years;
    }

    /**
     * Calcola se la disposizione delle prime tre lettere del c.f. sono in un formato valido
     * @param ctrl_surname Prime tre lettere del codice fiscale
     * @param seq_sur Calcolato dal metodo sonoConsonanti
     * @return Boolean true se il formato è corretto con controllo anche sul nome e cognome troppo corti, false altrimenti
     */
    private boolean isChecksur(String ctrl_surname, boolean[] seq_sur) {
        boolean checksur = false;
        if (areAllTrue(seq_sur)) {
            //Tutte consonanti
            checksur = true;
        } else if (seq_sur[1] == false && seq_sur[2] == true) {
            if (isVocale(ctrl_surname.charAt(1)) && ctrl_surname.charAt(2) == 'X') {
                checksur = true;
            }
        } else if (seq_sur[2] == false && seq_sur[1] == true && seq_sur[0] == true) {
            checksur = true;
        } else if (seq_sur[2] == false && seq_sur[1] == false && seq_sur[0] == true) {
            checksur = true;
        }//FINE CONTROLLO LETTERE COGNOME
        return checksur;
    }

    /**
     * Data tre lettere del codice fiscale salva in un array boolean true se il carattere è una consonante false altrimenti
     * Vengono controllati anche se sono stati immessi caratteri non validi
     * @param ctrl_surname
     * @return Array di boolean true se in posizione una consonante
     */
    private boolean[] sonoConsonanti(String ctrl_surname) {
        boolean [] seq_nick={false,false,false};
        boolean carattere_non_valido = false;
        for (int i = 0; i < ARR_CONSONANTI.length; i++) {
            for (int j = 0; j < ctrl_surname.length(); j++) {
                if ((ctrl_surname.charAt(j)) == (ARR_CONSONANTI[i])) {
                    seq_nick[j] = true;
                }
                 if(!isVocale(ctrl_surname.charAt(j)) && !(ctrl_surname.charAt(j) >= ASCII_A && ctrl_surname.charAt(j) <= ASCII_Z)){
                    carattere_non_valido = true;
                }
            }
        }
        if(carattere_non_valido){
            for (int i = 0; i < seq_nick.length; i++)
                seq_nick[i] = false;
        }
        return seq_nick;
    }

    /**
     *Metodo che trova se il carattere pasato è una vocale (true)
     * @param charSequence Carattere tipo char
     * @return Ritorna true se è una vocale false altrimenti
     */
    private static boolean isVocale(char charSequence) {
        for (int i = 0; i < ARR_VOCALI.length; i++) {
            if( charSequence == ARR_VOCALI[i]) {
                return true;
            }
        }
        return false;
    }

    /*
     * @param array di tipo boolean
     * @return True gli elementi dell'array sono true
     */
    public static boolean areAllTrue(boolean[] array) {
        for(boolean b : array) if(!b) return false;
        return true;
    }
    //---FINE METODI UTILIZZATI NEI PRECEDENTI---


    public boolean equals(CodiceFiscale in) {
        return codice_fiscale.equals(in.getCodice_fiscale());
    }

    @Override
    public String toString() {
        return "CodiceFiscale: " + codice_fiscale + "\n";
    }
}
