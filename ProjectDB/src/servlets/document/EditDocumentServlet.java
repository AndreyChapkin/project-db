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

@WebServlet("/editDoc")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 20, // 20MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class EditDocumentServlet extends HttpServlet {
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
		
		int docId = 0;
		String sdocId = request.getParameter("docId");
		if (sdocId != null && !sdocId.equals("")) {
			docId = Integer.parseInt(sdocId.trim());
		}
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction();
		try {
			Document doc = new DocumentService(em).find(docId);
			request.getServletContext().setAttribute("doc", doc);
		} catch (Exception e) {e.printStackTrace();}
		finally{em.close();}
		request.getServletContext().getRequestDispatcher("/WEB-INF/views/document/editDocumentView.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Document oldDoc = (Document) request.getServletContext().getAttribute("doc");
		request.getServletContext().removeAttribute("doc");
		
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
			new DocumentService(em).edit(oldDoc, doc, techFiles);
			em.getTransaction().commit();
		}
		catch(SQLException e) {response.getWriter().println(e);}
		finally {em.close();}
		
		response.sendRedirect(request.getContextPath()+"/showDoc?docId="+oldDoc.getObjId());
	}
}
