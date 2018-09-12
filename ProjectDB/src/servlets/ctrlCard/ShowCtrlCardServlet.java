package servlets.ctrlCard;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
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

@WebServlet("/showCtrlCard")
public class ShowCtrlCardServlet extends HttpServlet {
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
		
		int techCtrlId = 0;
		String stechCtrlId = request.getParameter("techCtrlId");
		if (stechCtrlId != null && !stechCtrlId.equals("")) {
			techCtrlId = Integer.parseInt(stechCtrlId.trim());
		}
		
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			TechControl techCtrl = em.find(TechControl.class, techCtrlId);
			request.setAttribute("techCtrl", techCtrl);
			em.getTransaction().commit();
			TechOperation techOper = (TechOperation) techCtrl.getAssocObj();
			request.setAttribute("techOper", techOper);
		} catch (Exception e) {e.printStackTrace();}
		finally{em.close();}
		request.getServletContext().getRequestDispatcher("/WEB-INF/views/ctrlCard/showCtrlCardView.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}
}
