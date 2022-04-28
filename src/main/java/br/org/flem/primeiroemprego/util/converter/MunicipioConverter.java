package br.org.flem.primeiroemprego.util.converter;

import br.org.flem.primeiroemprego.dao.MunicipioDAO;
import br.org.flem.primeiroemprego.entity.Municipio;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
* @author tscortes
*/
@FacesConverter(value = "municipioConverter")
public class MunicipioConverter implements Converter {
    
    @ManagedProperty(value = "#{municipioDAO}")
    private MunicipioDAO municipioDAO = new MunicipioDAO();
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent comp, String value) {
       if(value == null || value.isEmpty()){
           return null;
       }
        return municipioDAO.obterPorPK(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent comp, Object value) {
       return ((Municipio) value).getId().toString();
    }

    public MunicipioDAO getMunicipioDAO() {
        return municipioDAO;
    }

    public void setMunicipioDAO(MunicipioDAO municipioDAO) {
        this.municipioDAO = municipioDAO;
    }
    
    
}