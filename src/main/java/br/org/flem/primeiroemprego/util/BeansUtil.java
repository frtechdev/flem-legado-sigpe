package br.org.flem.primeiroemprego.util;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.mgbean.BeanManager;
import javax.faces.context.FacesContext;

/**
 *
 * @author emsilva
 */
public class BeansUtil {

    private static BeanManager getBeanManager() {
        try {
            return ApplicationAssociate.getCurrentInstance().getBeanManager();
        } catch (Exception e) {
            return null;
        }
    }

    public static Object getBean(String name) {
        BeanManager bm = getBeanManager();
        if (bm != null) {
            return bm.getBeanFromScope(name, FacesContext.getCurrentInstance());
        }
        return null;
    }
}