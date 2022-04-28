package br.org.flem.primeiroemprego.util.validator;

import java.util.Map;
import java.util.regex.Pattern;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import org.apache.poi.xwpf.converter.core.utils.StringUtils;
import org.primefaces.validate.ClientValidator;

/**
 * @author tscortes
 */
@FacesValidator("emailValidator")
public class EmailValidator implements Validator, ClientValidator {
    
    private Pattern pattern;
  
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
  
    public EmailValidator() {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }
    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null){
            return;
        }else if(value instanceof String){
            String str = (String) value;
            if(StringUtils.isEmpty(str)){
                return;
            }
        }
        
        if(!pattern.matcher(value.toString()).matches()) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro de validação de Email: ", 
                        value + " não é um e-mail válido"));
        }
    }
    @Override
    public Map<String, Object> getMetadata() {
        return null;
    }
    @Override
    public String getValidatorId() {
        return "emailValidator";
    }   
}