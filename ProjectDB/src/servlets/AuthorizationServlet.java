package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.ServletException;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import entities.User;
import services.UserService;

@WebServlet("/login")
public class AuthorizationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EntityManagerFactory emf;
	
	@SuppressWarnings({"rawtypes","unchecked"})
    public void init() throws ServletException{	    	
		try {
	    	InitialContext ctx = new InitialContext();
	    	DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
	    	Map properties = new HashMap();
	    	properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
	    	emf = Persistence.createEntityManagerFactory("ProjectDB",properties);
	    }
	    catch(NamingException e) {throw new ServletException(e);}
	}
	    
	    public void destroy() {
	    	emf.close();
	    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			HttpSession session = request.getSession(false);
			if(session != null) {
				session.removeAttribute("login");
				session.invalidate();
			}
			request.getServletContext().getRequestDispatcher("/WEB-INF/views/authorizationView.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		String login = request.getParameter("login").trim();
		String password = request.getParameter("password").trim();
		User user = null;
		
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			user = new UserService(em).findWithLogin(login);
			em.getTransaction().commit();
		} catch (SQLException e) {e.printStackTrace();}
		
		if( (login.equals("admin") && password.equals("admin")) || ((user != null) && password.equals(user.getPassword())) ) {
			HttpSession session = request.getSession(true);
			session.setMaxInactiveInterval(60*60);
			session.setAttribute("login", login);
			session.setAttribute("user", user);
			response.sendRedirect(request.getContextPath()+"/home");
		}
		else response.sendRedirect(request.getServletContext().getContextPath()+"/login");
	}

}
