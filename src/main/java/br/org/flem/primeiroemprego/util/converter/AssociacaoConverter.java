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
@FacesConverter(value = "associacaoConverter")
public class AssociacaoConverter implements Converter {

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

            return getKey(entity);

        }

        return (String) value;
    }

    private void addAttribute(UIComponent component, DemandanteMunicipioDTO o) {
        String key = getKey(o);
        this.getAttributesFrom(component).put(key, o);
    }

    private Map<String, Object> getAttributesFrom(UIComponent component) {
        return component.getAttributes();
    }

    private String getKey(DemandanteMunicipioDTO entity) {

        String idDemandante = "" + entity.getIdDemandante();
        String idMunicipio = "" + entity.getIdMunicipio();
        return idDemandante + ":" + idMunicipio;

    }

}
