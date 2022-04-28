package br.org.flem.primeiroemprego.mb;

import br.org.flem.primeiroemprego.dao.ParametroDAO;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class ParametroMB implements Serializable{
    
    @ManagedProperty(value = "#{parametroDAO}")
    private ParametroDAO parametroDAO;
    
    public String valor(String chave){
        return parametroDAO.obterValor(chave);
    }
    
    public Long valorEmLong(String chave){
        return Long.parseLong(parametroDAO.obterValor(chave));
    }

    public ParametroDAO getParametroDAO() {
        return parametroDAO;
    }

    public void setParametroDAO(ParametroDAO parametroDAO) {
        this.parametroDAO = parametroDAO;
    }

}
