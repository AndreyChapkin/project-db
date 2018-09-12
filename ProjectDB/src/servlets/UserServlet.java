package servlets;

import java.io.IOException;
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
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import entities.*;
import services.*;

@WebServlet("/userList")
public class UserServlet extends HttpServlet {
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
		EntityManager em = emf.createEntityManager();
		
			try{
				em.getTransaction().begin();
				List<User> userList = new UserService(em).getAll();
				for(User user:userList) {
					em.refresh(user);
				}
				em.getTransaction().commit();
				request.setAttribute("userList", userList);
			}
			catch(Exception e) {response.getWriter().println(e);}
			finally {em.close();}
			request.getServletContext().getRequestDispatcher("/WEB-INF/views//userListView.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManager em = emf.createEntityManager();
		request.setCharacterEncoding("utf-8");
		String name = request.getParameter("name").trim();
		String surname = request.getParameter("surname").trim();
		String post = request.getParameter("post").trim();
		String password = request.getParameter("password").trim();
		try{
			em.getTransaction().begin();
			User user = new User(name, surname, post, password);
			new UserService(em).add(user);
			em.getTransaction().commit();
		}
		catch(Exception e) {response.getWriter().println(e);}
		finally {em.close();}
		response.sendRedirect(request.getContextPath()+"/userList");
	}

}

