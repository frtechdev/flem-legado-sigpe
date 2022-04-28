package br.org.flem.primeiroemprego.util.converter;

import br.org.flem.primeiroemprego.dao.ColaboradorDAO;
import br.org.flem.primeiroemprego.entity.Colaborador;
import javax.faces.bean.ManagedProperty;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
* @author tscortes
*/
@FacesConverter(value = "colaboradorConverter")
public class ColaboradorConverter implements Converter {
    
    @ManagedProperty(value = "#{colaboradorDAO}")
    private ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
    
    @Override
    public Object getAsObject(FacesContext fc, UIComponent comp, String value) {
       if(value == null || value.isEmpty()){
           return null;
       }
        return colaboradorDAO.obterPorPK(Long.valueOf(value));
    }

    @Override
    public String getAsString(FacesContext fc, UIComponent comp, Object value) {
       return ((Colaborador) value).getId().toString();
    }

    public ColaboradorDAO getColaboradorDAO() {
        return colaboradorDAO;
    }

    public void setColaboradorDAO(ColaboradorDAO colaboradorDAO) {
        this.colaboradorDAO = colaboradorDAO;
    }
    
    
}