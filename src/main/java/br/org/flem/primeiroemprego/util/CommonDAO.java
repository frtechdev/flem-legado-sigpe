package br.org.flem.primeiroemprego.util;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author emsilva
 */
public abstract class CommonDAO implements Serializable {

    
    protected EntityTransaction transaction;

    protected CommonDAO() {
    }

    public void begin(){
        transaction  = getEntityManager().getTransaction();
        transaction.begin();
    }

    public void commit(){
        transaction.commit();
    }
    
    public void rollback(){
        transaction.rollback();
    }

    protected EntityManager getEntityManager(){
        return JPAUtil.getEntityManager();
    }
    

}
