package br.com.caelum;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/*
 * Pattern/Padrão conhecido como OpenEntityManagerInView
 * A desvantagem que abriremos sempre uma trasão com banco 
 * para toda requisição mesmo que não precise utilizar conexão com o banco.
 */
public class EntityManagerFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
//		EntityManager em = null;
//		request.setAttribute("em", em);
//		chain.doFilter(request, response);
//		em.close();

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

}
