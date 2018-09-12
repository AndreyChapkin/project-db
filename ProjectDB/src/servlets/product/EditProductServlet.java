package servlets.product;

import java.io.IOException;
import java.sql.SQLException;
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

@WebServlet("/editProd")
public class EditProductServlet extends HttpServlet {
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
		ProductService ps = new ProductService(em);
		int prodId = 0;
		String sprodId = request.getParameter("prodId");
		//В случае удаления
		String action = request.getParameter("action");
		
		if(sprodId!=null && !sprodId.equals("")) {
			prodId = Integer.parseInt(sprodId);
		}
		if(action!="delete") {
			try {
				em.getTransaction().begin();
				List<ProductGroup> prodGroupList = ps.getAllGroups();
				for(ProductGroup pg:prodGroupList) {
					em.refresh(pg);
				}
				List<MeasureUnit> measUnitList = ps.getAllMeasUnits();
				for(MeasureUnit mu:measUnitList) {
					em.refresh(mu);
				}
				if(prodId!=0) {
						Product prod = ps.find(prodId);
						request.getSession(true).setAttribute("prod", prod);
				}
				em.getTransaction().commit();
				request.setAttribute("prodGroupList", prodGroupList);
				request.setAttribute("measUnitList", measUnitList);
			}
			catch(Exception e) {response.getWriter().println(e);}
			finally {em.close();}
			
			request.getServletContext().getRequestDispatcher("/WEB-INF/views/product/editProductView.jsp").forward(request, response);
		}
		else {
			try {
				em.getTransaction().begin();
				if(prodId!=0) {
					ps.delete(prodId);
				}
				em.getTransaction().commit();
			}
			catch(Exception e) {response.getWriter().println(e);}
			finally {em.close();}
			
			response.sendRedirect(request.getContextPath()+"/prodList");	
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// Получение параметров
		Map<String,Object> prop = new HashMap<String,Object>();

		prop.put("prodName",request.getParameter("prodName").trim());
		
		float prodWeight = 0;
		if (request.getParameter("prodWeight")!=null && !request.getParameter("prodWeight").equals("")) {
			prodWeight = Float.parseFloat(request.getParameter("prodWeight").trim());
		}
		prop.put("prodWeight",prodWeight);
		
		int batchValue = 0;
		if (request.getParameter("batchValue")!=null && !request.getParameter("batchValue").equals("")) {
			batchValue = Integer.parseInt(request.getParameter("batchValue").trim());
		}
		prop.put("batchValue",batchValue);
		
		prop.put("prodSign",request.getParameter("prodSign").trim());
		prop.put("prodGroupName",request.getParameter("prodGroupName").trim());
		prop.put("measUnitName",request.getParameter("measUnitName").trim());
		
		String prodGroupName = request.getParameter("prodGroupName").trim();
		String measUnitName = request.getParameter("measUnitName").trim();
		
		Product prod = (Product) request.getSession(true).getAttribute("prod");
		request.getSession(true).removeAttribute("prod");
		
		// Сохранение в БД
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			User editor = (User) request.getSession(false).getAttribute("user");
			if(editor != null) prod.setEditor(editor);
			new ProductService(em).edit(prod,prop, prodGroupName, measUnitName);
			em.getTransaction().commit();
		}
		catch(SQLException e) {response.getWriter().println(e);}
		finally {em.close();}
		
		response.sendRedirect(request.getContextPath()+"/prodList");
	}

}
