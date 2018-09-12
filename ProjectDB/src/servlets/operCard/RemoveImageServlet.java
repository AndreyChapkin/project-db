package servlets.operCard;

import java.io.IOException;
import java.sql.SQLException;
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
import entities.*;
import services.*;

@WebServlet("/removeImage")
public class RemoveImageServlet extends HttpServlet {
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
		//Ответ ajax-у
		int removeId = 0;
		if(request.getParameter("removeId") != null) {
			String sremoveId = request.getParameter("removeId");
			if (sremoveId!=null) {
				removeId = Integer.parseInt(sremoveId.trim());
			}
			
		EntityManager emr = emf.createEntityManager();
			try {
				emr.getTransaction().begin();
				TechFile imageFile = (TechFile) emr.find(TechFile.class, removeId);
				imageFile.getDocument().getTechFiles().remove(imageFile);
				emr.remove(imageFile);
				emr.getTransaction().commit();
			} catch (Exception e) {e.printStackTrace();}
			finally {emr.close();}
		}
	}

}

