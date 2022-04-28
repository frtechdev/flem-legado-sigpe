package br.org.flem.primeiroemprego.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author emsilva
 */
public class RedirectUtil {
    public static void redirect(String url){
        try{
            ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
            context.redirect(context.getRequestContextPath()+url);
        }catch(Exception e){
            Logger.getLogger(RedirectUtil.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public static void navigate(String action) {
        try{
            FacesContext context = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse)context.getExternalContext().getResponse();
            response.sendRedirect( context.getExternalContext().getRequestContextPath()+"/"+action);
        }catch(Exception e){
            Logger.getLogger(RedirectUtil.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
