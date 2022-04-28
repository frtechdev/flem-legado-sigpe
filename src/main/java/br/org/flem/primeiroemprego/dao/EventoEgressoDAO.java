package br.org.flem.primeiroemprego.dao;

import br.org.flem.primeiroemprego.entity.EventoEgresso;
import br.org.flem.primeiroemprego.entity.EventoEgressoID;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author tscortes
 */
@ManagedBean
@ViewScoped
public class EventoEgressoDAO extends GenericDAO<EventoEgresso, EventoEgressoID> {
    
    public EventoEgressoDAO() throws Exception{
        super(EventoEgresso.class);
    }
    
}
