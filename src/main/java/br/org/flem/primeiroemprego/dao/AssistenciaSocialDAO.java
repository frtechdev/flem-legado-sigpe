package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.AssistenciaSocial;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author emsilva
 */
@ManagedBean
@ViewScoped
public class AssistenciaSocialDAO extends GenericDAO<AssistenciaSocial, Long> {
    
    public AssistenciaSocialDAO() throws Exception{
        super(AssistenciaSocial.class);
        this.nomeColunaParaOdemSimples = "dataCriacao";
    }

}
