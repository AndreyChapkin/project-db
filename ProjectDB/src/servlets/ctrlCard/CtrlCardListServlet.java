package servlets.ctrlCard;

import java.io.IOException;
import java.util.ArrayList;
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

@WebServlet("/ctrlCardList")
public class CtrlCardListServlet extends HttpServlet {
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
		
		int techOperId = 0;
		String stechOperId = request.getParameter("techOperId");
		if (stechOperId!=null && !stechOperId.equals("")) {
			techOperId = Integer.parseInt(stechOperId.trim());
		}
		
		if(techOperId == 0) {
		EntityManager em = emf.createEntityManager();
			try{
				em.getTransaction().begin();
				@SuppressWarnings("unchecked")
				List<TechControl> techCtrlList = em.createNamedQuery("AllTechControls").getResultList();
				em.getTransaction().commit();
				request.setAttribute("techCtrlList", techCtrlList);
			}
			
			catch(Exception e) {response.getWriter().println(e);}
			finally {em.close();}
		}
		else {
			EntityManager em = emf.createEntityManager();
			response.setCharacterEncoding("UTF-8");
			try{
				em.getTransaction().begin();
				TechOperation techOper = new TechOperationService(em).find(techOperId);
				em.getTransaction().commit();
				List<TechControl> techCtrlList = new ArrayList<TechControl>();
				for(TechObject lo:techOper.getLinkedObjs()) {
					if(lo instanceof TechControl)
						techCtrlList.add((TechControl) lo);
				}
				request.setAttribute("techOperId", techOperId);
				request.setAttribute("techCtrlList", techCtrlList);
			}
			catch(Exception e) {response.getWriter().println(e);}
			finally {em.close();}
		}		
			request.getServletContext().getRequestDispatcher("/WEB-INF/views/ctrlCard/ctrlCardListView.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
