package br.org.flem.primeiroemprego.entity;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author AJLima
 */
public class Mes implements Serializable{
    
    private final static String[] meses;
     
    static {
        meses = new String[12];
        meses[0] = "JANEIRO";
        meses[1] = "FEVEREIRO";
        meses[2] = "MARÇO";
        meses[3] = "ABRIL";
        meses[4] = "MAIO";
        meses[5] = "JUNHO";
        meses[6] = "JULHO";
        meses[7] = "AGOSTO";
        meses[8] = "SETEMBRO";
        meses[9] = "OUTUBRO";
        meses[10] = "NOVEMBRO";
        meses[11] = "DEZEMBRO";
        
    }

    public List<String> getColors() {
        return Arrays.asList(meses);
    }
     
    public static String[] getMeses() {
        return meses;
    }

}
