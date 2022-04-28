package br.org.flem.primeiroemprego.util.converter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;

/**
 * Conversor de Telefone.
 *
 * @author tscortes
 */
@FacesConverter(value = "telefoneConverter")
public class TelefoneConverter implements Converter {

    private static final Pattern FIXO_PATTERN = Pattern.compile("(\\d{2})-(\\d{4})(\\d{4})");
    private static final Pattern CELULAR_PATTERN = Pattern.compile("(\\d{3})-(\\d{5})(\\d{4})");

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
        Matcher matcher = FIXO_PATTERN.matcher(value);
        if( !matcher.matches()){
            matcher = CELULAR_PATTERN.matcher(value);
        }
        if (matcher.matches()) {
            String areaCode = matcher.group(1);
            String exchange = matcher.group(2);
            String line = matcher.group(3);
            return areaCode + exchange + line;
        } 
        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
        String telefone = value.toString();
        if (telefone != null && !StringUtils.isEmpty(telefone) && telefone.length() >= 10) {
            String prefixo = telefone.substring(0, 2);
            String parteUm, parteDois;
            if (telefone.length() == 11) {
                parteUm = telefone.substring(2, 7);
                parteDois = telefone.substring(7, 11);
                telefone = "(" + prefixo + ")" + parteUm + "-" + parteDois;
            } else if (telefone.length() == 10) {
                parteUm = telefone.substring(2, 6);
                parteDois = telefone.substring(6, 10);
                telefone = "(" + prefixo + ")" + parteUm + "-" + parteDois;
            }
            return telefone;
        }

        return null;
    }
}
