package servlets.ctrlCard;

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

@WebServlet("/addParamCtrl")
public class AddRemoveParamControlServlet extends HttpServlet {
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
			TechOperation techOper = (TechOperation) request.getServletContext().getAttribute("techOper");
			int techCtrlId = techOper.getLinkedObjs().get(0).getObjId();
			ParamControl newParamCtrl = new ParamControl( null, null, null, 0, 0);
			
			EntityManager emr = emf.createEntityManager();
			emr.getTransaction().begin();
			TechControl techCtrl = emr.find(TechControl.class, techCtrlId);
			techCtrl.getParamControls().add(inputId,newParamCtrl);
			newParamCtrl.setTechControl(techCtrl);
			emr.persist(newParamCtrl);
			emr.getTransaction().commit();
			emr.close();
		}
		
		int removeId = 0;
		if(request.getParameter("removeId") != null) {
			String sremoveId = request.getParameter("removeId");
			if (sremoveId!=null) {
				removeId = Integer.parseInt(sremoveId.trim());
			}
			TechOperation techOper = (TechOperation) request.getServletContext().getAttribute("techOper");
			EntityManager emr = emf.createEntityManager();
			try {
				emr.getTransaction().begin();
				TechControl techCtrl = emr.find(TechControl.class, techOper.getLinkedObjs().get(0).getObjId());
				new TechOperationService(emr).deleteParamControl(techCtrl,removeId);
				emr.getTransaction().commit();
			} catch (SQLException e) {e.printStackTrace();}
			finally {emr.close();}
		}
	}

}
