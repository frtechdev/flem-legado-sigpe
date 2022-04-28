package br.org.flem.primeiroemprego.util.converter;

import br.org.flem.primeiroemprego.dto.DemandanteMunicipioDTO;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * @author tscortes
 */
@FacesConverter(value = "dtoConverter")
public class DtoConverter implements Converter {

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

            DemandanteMunicipioDTO entity = (DemandanteMunicipioDTO) value;

            this.addAttribute(component, entity);

            Long codigo = entity.getIdDemandante();
            if (codigo != null) {
                return String.valueOf(codigo);
            }
        }

        return (String) value;
    }

    private void addAttribute(UIComponent component, DemandanteMunicipioDTO o) {
        if (o.getIdDemandante() != null) {
            String key = o.getIdDemandante().toString();
            this.getAttributesFrom(component).put(key, o);
        }
    }

    private Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }

}
