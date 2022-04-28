package br.org.flem.primeiroemprego.util.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;

/**
* Conversor de Cep.
* @author tscortes
*/
@FacesConverter(value = "cepConverter")
public class CepConverter implements Converter {
     @Override
     public Object getAsObject(FacesContext context, UIComponent component, String value) throws ConverterException {
          String cep = null;
          if (value != null && !value.isEmpty()){
              cep = value.replaceAll("\\-", "");
          }
          return cep;
     }
 
     @Override
     public String getAsString(FacesContext context, UIComponent component, Object value) throws ConverterException {
          String cep = value.toString();   
          if (cep != null && !StringUtils.isEmpty(cep) && cep.length() == 8){
              String prefixo = cep.substring(0, 5);
              String sufixo = cep.substring(5, 8);
              cep = prefixo +"-"+ sufixo;
              return cep;
          }
          return null;
     }
}