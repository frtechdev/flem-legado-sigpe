package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.EscritorioRegional;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class EscritorioRegionalDAO extends GenericDAO<EscritorioRegional, Long>  {
    
    public EscritorioRegionalDAO(){
        super(EscritorioRegional.class);
    }
    
}
