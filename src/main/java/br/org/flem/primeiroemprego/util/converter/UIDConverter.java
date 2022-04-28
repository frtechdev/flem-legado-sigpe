package br.org.flem.primeiroemprego.util.converter;

import br.org.flem.primeiroemprego.entity.UID;
import java.io.Serializable;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author tscortes
 */
@FacesConverter(value = "uidConverter")
public class UIDConverter implements Converter{
    
    @Override
    public Object getAsObject(FacesContext ctx, UIComponent component, String value) {  
        if (value != null) {  
            return this.getAttributesFrom(component).get(value);  
        }  
        return null;  
    }  
    @Override
    public String getAsString(FacesContext ctx, UIComponent component, Object value) {  
  
        if (value != null && !"".equals(value)) {  
  
            UID entity = (UID) value;  

            this.addAttribute(component, entity);  
  
            Serializable codigo =  entity.getId();  
            if (codigo != null) {  
                return String.valueOf(codigo);  
            }  
        }  
  
        return (String) value;  
    }  
  
    private void addAttribute(UIComponent component, UID o) {  
        String key = o.getId().toString();
        this.getAttributesFrom(component).put(key, o);  
    }  
  
    private Map<String, Object> getAttributesFrom(UIComponent component) {  
        return component.getAttributes();  
    } 
    
}
