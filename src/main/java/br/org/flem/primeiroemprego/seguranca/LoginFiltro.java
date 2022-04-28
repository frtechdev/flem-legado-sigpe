package br.org.flem.primeiroemprego.seguranca;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 *
 * @author emsilva
 */
public class LoginFiltro extends br.org.flem.fw.web.filtro.LoginFiltro{
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        super.doFilter(request, response, chain);
    }

}
