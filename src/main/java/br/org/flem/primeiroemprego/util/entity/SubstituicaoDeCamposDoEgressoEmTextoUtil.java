package br.org.flem.primeiroemprego.util.entity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author emsilva
 */

public class SubstituicaoDeCamposDoEgressoEmTextoUtil {
    private final Pattern tokensPattern = Pattern.compile("\\{([\\p{L} \\d]*?)\\}");
    private final Matcher matcher;
    
    public SubstituicaoDeCamposDoEgressoEmTextoUtil(String texto){
        matcher = tokensPattern.matcher(texto);
    }
    
    

    public boolean procurarSubstituicao(){
        return matcher.find();
    }

    public String textoParaSubstituir() {
        return matcher.group(1);
    }

}
