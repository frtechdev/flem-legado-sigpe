package br.org.flem.primeiroemprego.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author emsilva
 */
public class JPAUtil {
    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("aplicativoPRIMEIROEMPREGO");;
    private static final ThreadLocal<EntityManager> entityManager;

    static {
        entityManager =  new ThreadLocal<EntityManager>();
    }
    
    public static EntityManager getEntityManager(){
        if(entityManager.get() == null){
            entityManager.set(entityManagerFactory.createEntityManager());
        }
        return entityManager.get();
    }
    
    public static void closeEntityManager(){
        if(entityManager.get() != null){
            entityManager.get().close();
            entityManager.set(null);
        }
    }
    
    public static void closeEntityFactory(){
        if(entityManagerFactory != null){
            entityManagerFactory.close();
        }
    }
}
