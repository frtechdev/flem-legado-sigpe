
package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.TipoAssistenciaSocial;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author MPPassos
 */
@ManagedBean
@ViewScoped
public class TipoAssistenciaSocialDAO extends GenericDAO<TipoAssistenciaSocial, Long> {
    
    public TipoAssistenciaSocialDAO() throws Exception{
        super(TipoAssistenciaSocial.class);
        this.nomeColunaParaOdemSimples = "nome";
    }

}
