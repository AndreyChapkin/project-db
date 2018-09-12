package filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {
	
	String excludeURI = "/login";
	
    public AuthorizationFilter() {
    	
    }

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false);
		String servletPath = req.getServletPath();
		
		boolean loggedIn = (session != null && session.getAttribute("login") != null);
		boolean allowedPath = servletPath.equals(excludeURI);
		
		if(loggedIn || allowedPath) chain.doFilter(request, response);
		else res.sendRedirect(req.getServletContext().getContextPath()+"/login");
		
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
