package br.org.flem.primeiroemprego.util;

import br.org.flem.fwe.web.filtro.base.BaseServletFilterAb;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author emsilva
 */
public class CloseEntityManagerFilter extends BaseServletFilterAb {
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(request, response);
        JPAUtil.closeEntityManager();
    }

}
