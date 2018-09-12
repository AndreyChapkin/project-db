package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Date;

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

@WebServlet("/newMes")
public class NewMessageServlet extends HttpServlet {
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
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		String recipientLogin = request.getParameter("recipientLogin");
		String title = request.getParameter("title");
		String text = request.getParameter("text");
		// Сохранение в БД
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			User recipient = new UserService(em).findWithLogin(recipientLogin);
			User sender = (User) request.getSession(false).getAttribute("user");
			sender = new UserService(em).find(sender.getUserId());
			Message mes = new Message(title, text, new Date(), sender, recipient);
			new MessageService(em).add(mes, recipient, sender);
			em.getTransaction().commit();
		}
		catch(SQLException e) {response.getWriter().println(e);}
		finally {em.close();}
		
		response.sendRedirect(request.getContextPath()+"/home");
	}
}
