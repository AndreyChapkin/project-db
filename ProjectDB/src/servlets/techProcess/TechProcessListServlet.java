package servlets.techProcess;

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

@WebServlet("/techProcList")
public class TechProcessListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EntityManagerFactory emf;
	
	private int productId;
	
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
		
		productId = 0;
		String sproductId = request.getParameter("productId");
		if (sproductId!=null && !sproductId.equals("")) {
			productId = Integer.parseInt(sproductId.trim());
		}
		
		if(productId == 0) {
		EntityManager em = emf.createEntityManager();
			try{
				em.getTransaction().begin();
				List<TechProcess> techProcList = new TechProcessService(em).getAll();
				em.getTransaction().commit();
				request.setAttribute("techProcList", techProcList);
			}
			
			catch(Exception e) {response.getWriter().println(e);}
			finally {em.close();}
		}
		else {
			EntityManager em = emf.createEntityManager();
			response.setCharacterEncoding("UTF-8");
			try{
				em.getTransaction().begin();
				Product tpProd = new ProductService(em).find(productId);
				em.getTransaction().commit();
				List<TechProcess> techProcList = new ArrayList<TechProcess>();
				for(TechObject lo:tpProd.getLinkedObjs()) {
					if(lo instanceof TechProcess)
						techProcList.add((TechProcess) lo);
				}
				request.setAttribute("productId", productId);
				request.setAttribute("prod", tpProd);
				request.setAttribute("techProcList", techProcList);
			}
			catch(Exception e) {response.getWriter().println(e);}
			finally {em.close();}
		}		
			request.getServletContext().getRequestDispatcher("/WEB-INF/views/techProcess/techProcessListView.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
