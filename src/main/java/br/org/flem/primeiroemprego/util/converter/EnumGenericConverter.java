package br.org.flem.primeiroemprego.util.converter;

import br.org.flem.primeiroemprego.entity.EnumSexo;
import javax.faces.convert.EnumConverter;
import javax.faces.convert.FacesConverter;

/**
 * @author tscortes
 */
@FacesConverter(value = "enumConverter")
public class EnumGenericConverter extends EnumConverter {
    
    public EnumGenericConverter(){
        super(EnumSexo.class);
    }
    
}
