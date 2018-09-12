package servlets.techProcess;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

@WebServlet("/addTechOper")
public class AddRemoveTechOperationServlet extends HttpServlet {
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
		int inputId = 0;
		if(request.getParameter("inputId") != null && !request.getParameter("inputId").equals("")) {
			String sinputId = request.getParameter("inputId");
			if (sinputId!=null) {
				inputId = Integer.parseInt(sinputId.trim());
			}
			TechOperation to = new TechOperation(null, null, null, new Date(), new Date(), 0, 0, 0, 0, 0, 0, null, null, 0, 0, 0, 0, 0, 0, 0, 0,null);
			TechProcess techProc = (TechProcess) request.getServletContext().getAttribute("techProc");
			EntityManager emr = emf.createEntityManager();
			emr.getTransaction().begin();
			techProc.getLinkedObjs().add(inputId, to);
			to.setAssocObj(techProc);
			emr.persist(to);
			emr.merge(techProc);
			emr.getTransaction().commit();
			emr.close();
		}
		
		int removeId = 0;
		if(request.getParameter("removeId") != null) {
			String sremoveId = request.getParameter("removeId");
			if (sremoveId!=null) {
				removeId = Integer.parseInt(sremoveId.trim());
			}
			TechProcess techProc = (TechProcess) request.getServletContext().getAttribute("techProc");
			EntityManager emr = emf.createEntityManager();
			try {
				emr.getTransaction().begin();
				new TechProcessService(emr).deleteOperation(techProc,removeId);
				emr.getTransaction().commit();
			} catch (SQLException e) {e.printStackTrace();}
			finally {emr.close();}
		}
	}

}
