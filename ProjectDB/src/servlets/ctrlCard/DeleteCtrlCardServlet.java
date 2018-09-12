package servlets.ctrlCard;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
import services.*;

@WebServlet("/delCtrlCard")
public class DeleteCtrlCardServlet extends HttpServlet {
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
		
		int techOperId = 0;
		String stechOperId = request.getParameter("techOperId");		
		if(stechOperId!=null && !stechOperId.equals("")) {
			techOperId = Integer.parseInt(stechOperId);
		}
		
		int techCtrlId = 0;
		String stechCtrlId = request.getParameter("techCtrlId");		
		if(stechCtrlId!=null && !stechCtrlId.equals("")) {
			techCtrlId = Integer.parseInt(stechCtrlId);
		}
		
		try {
			em.getTransaction().begin();
			if(techCtrlId!=0) {
				new TechOperationService(em).deleteCtrlCard(techCtrlId);
			}
			em.getTransaction().commit();
		}
		catch(Exception e) {response.getWriter().println(e);}
		finally {em.close();}
		response.sendRedirect(request.getContextPath()+"/ctrlCardList?techOperId="+techOperId);	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
