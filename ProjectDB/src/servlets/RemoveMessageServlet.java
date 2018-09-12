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

@WebServlet("/removeMes")
public class RemoveMessageServlet extends HttpServlet {
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
		int removeId = 0;
		if(request.getParameter("removeId") != null) {
			String sremoveId = request.getParameter("removeId");
			if (sremoveId!=null) {
				removeId = Integer.parseInt(sremoveId.trim());
			}
			EntityManager emr = emf.createEntityManager();
			try {
				emr.getTransaction().begin();
				new MessageService(emr).delete(removeId);
				emr.getTransaction().commit();
			} catch (Exception e) {e.printStackTrace();}
			finally {emr.close();}
		}
		
		int removeOutId = 0;
		if(request.getParameter("removeOutId") != null) {
			String sremoveOutId = request.getParameter("removeOutId");
			if (sremoveOutId!=null) {
				removeOutId = Integer.parseInt(sremoveOutId.trim());
			}
			EntityManager emr = emf.createEntityManager();
			try {
				emr.getTransaction().begin();
				new MessageService(emr).deleteOut(removeOutId);
				emr.getTransaction().commit();
			} catch (Exception e) {e.printStackTrace();}
			finally {emr.close();}
		}
	}
}
