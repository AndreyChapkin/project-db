package servlets.document;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import entities.*;
import services.*;
import tools.FileWorker;

@WebServlet("/newDoc")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 20, // 20MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class NewDocumentServlet extends HttpServlet {
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
		
		int parentId=0;
		if (request.getParameter("parentId")!=null && !request.getParameter("parentId").equals("")) {
			parentId = Integer.parseInt(request.getParameter("parentId").trim());
		}
		
		int productId = 0;
		String sproductId = request.getParameter("productId");
		if (sproductId!=null && !sproductId.equals("")) {
			productId = Integer.parseInt(sproductId.trim());
		}
		
		try {
			em.getTransaction().begin();
			//Для новой версии документа
			if(parentId!=0) {
				Document parentDoc = em.find(Document.class, parentId);
				request.getServletContext().setAttribute("parentDoc", parentDoc);	
			}
			//---
			Product prod = em.find(Product.class, productId);
			request.getServletContext().setAttribute("prod", prod);	
			em.getTransaction().commit();
			//Для версии изделия
			request.setAttribute("parentId", parentId);
		}
		catch(Exception e) {response.getWriter().println(e);}
		finally {em.close();}
		
		request.getServletContext().getRequestDispatcher("/WEB-INF/views/document/newDocumentView.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Product prod = (Product) request.getServletContext().getAttribute("prod");
		request.getServletContext().removeAttribute("prod");

		Document parentDoc = (Document) request.getServletContext().getAttribute("parentDoc");
		request.getServletContext().removeAttribute("parentDoc");

		
		request.setCharacterEncoding("utf-8");
		// Получение параметров
		String docName = request.getParameter("docName").trim();
		String docSign = request.getParameter("docSign").trim();
		String docType = request.getParameter("docType").trim();
		String docLetter = request.getParameter("docLetter").trim();
		
		List<TechFile> techFiles = new ArrayList<TechFile>();
		for(Part filePart:request.getParts()) {
			String fileName = FileWorker.extractFileName(filePart);
			if(fileName != null && !fileName.equals("")) {	
				String fileType = FileWorker.extractFileType(fileName);
				byte[] fileData = FileWorker.extractFileBytes(filePart);
				TechFile techFile = new TechFile(fileData, fileName, fileType);
				techFiles.add(techFile);
			}
		}
		// Сохранение в БД
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Document doc = new Document(null, null, null, new Date(), new Date(), docName, docType, docLetter, docSign);
			new DocumentService(em).add(prod, parentDoc, doc, techFiles);
			em.getTransaction().commit();
		}
		catch(SQLException e) {response.getWriter().println(e);}
		finally {em.close();}
		
		response.sendRedirect(request.getContextPath()+"/docList?productId="+prod.getObjId());
	}
}