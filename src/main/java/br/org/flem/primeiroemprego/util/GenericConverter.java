package br.org.flem.primeiroemprego.util;
import br.org.flem.primeiroemprego.entity.UID;
import java.io.Serializable;
import java.util.HashMap;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author emsilva
 */
@FacesConverter(value = "genericConverter")
@ManagedBean
@ViewScoped
public class GenericConverter implements Converter,Serializable{

    private HashMap<String, UID> hash;//Class:id

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (hash == null || value == null || value.isEmpty()) {
            return null;
        }
        return hash.get(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof String) {
            return value.toString();
        }
        if(((UID) value).getId() == null){
            return null;
        }
        if (hash == null) {
            hash = new HashMap<String, UID>();
        }
        String key = value.getClass().getSimpleName()+":"+((UID) value).getId();
        hash.put(key, (UID) value);
        return key;
    }

}