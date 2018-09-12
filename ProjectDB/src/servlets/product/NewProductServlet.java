package servlets.product;

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

@WebServlet("/newProd")
public class NewProductServlet extends HttpServlet {
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
		//Для новой версии изделия
		int parentId=0;
		if (request.getParameter("parent")!=null && !request.getParameter("parent").equals("")) {
			parentId = Integer.parseInt(request.getParameter("parent").trim());
		}
		
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
			//Для новой версии изделия
			if(parentId!=0) {
				Product parentProd = ps.find(parentId);
				request.getSession(true).setAttribute("parentProd", parentProd);	
			}
			//---
			em.getTransaction().commit();
			request.setAttribute("prodGroupList", prodGroupList);
			request.setAttribute("measUnitList", measUnitList);
			//Для версии изделия
			request.setAttribute("parentId", parentId);
		}
		catch(Exception e) {response.getWriter().println(e);}
		finally {em.close();}
		request.getServletContext().getRequestDispatcher("/WEB-INF/views/product/newProductView.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// Получение параметров
		String prodName = request.getParameter("prodName").trim();
		
		float prodWeight = 0;
		if (request.getParameter("prodWeight")!=null && !request.getParameter("prodWeight").equals("")) {
			prodWeight = Float.parseFloat(request.getParameter("prodWeight").trim());
		}
		
		int batchValue = 0;
		if (request.getParameter("batchValue")!=null && !request.getParameter("batchValue").equals("")) {
			batchValue = Integer.parseInt(request.getParameter("batchValue").trim());
		}
		
		String prodSign = request.getParameter("prodSign").trim();
		String prodGroupName = request.getParameter("prodGroupName").trim();
		String measUnitName = request.getParameter("measUnitName").trim();
		
		Product parentProd = (Product) request.getSession(true).getAttribute("parentProd");
		request.getSession(true).removeAttribute("parentProd");
		
		// Сохранение в БД
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Product prod = new Product(null,null,null,new Date(), new Date(), prodName,
					prodWeight, batchValue, prodSign);
			User creator = (User) request.getSession(false).getAttribute("user");
			if(creator != null) prod.setCreator(creator);
			new ProductService(em).add(prod,prodGroupName,measUnitName, parentProd);
			em.getTransaction().commit();
		}
		catch(SQLException e) {response.getWriter().println(e);}
		finally {em.close();}
		
		response.sendRedirect(request.getContextPath()+"/prodList");
	}
}