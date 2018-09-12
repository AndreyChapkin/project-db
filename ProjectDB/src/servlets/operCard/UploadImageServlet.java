package servlets.operCard;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.InitialContext;
import javax.persistence.Persistence;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.eclipse.persistence.config.PersistenceUnitProperties;
import entities.*;
import services.*;
import tools.FileWorker;

@WebServlet("/uploadImage")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 20, // 20MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadImageServlet extends HttpServlet {
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
		
		int techOperId = 0;
		String stechOperId = request.getParameter("techOperId");
		if (stechOperId != null && !stechOperId.equals("")) {
			techOperId = Integer.parseInt(stechOperId.trim());
		}
		
		request.setCharacterEncoding("UTF-8");
    	Part dataPart = request.getPart("dataFile");
    	String fileName = FileWorker.extractFileName(dataPart);
    	String fileType = FileWorker.extractFileType(fileName);
    	byte[] fileData = FileWorker.extractFileBytes(dataPart);
    	
    	EntityManager em = emf.createEntityManager();
    	try {
    		em.getTransaction().begin();
    		if (fileName!=""){
				TechFile techFile = new TechFile(fileData,fileName,fileType);
				int operCardId = (Integer) request.getServletContext().getAttribute("operCardId");
				new DocumentService(em).addDocumentFile(techFile,operCardId);
    		em.getTransaction().commit();
        	}
    	}
        	catch (Exception e) {response.getWriter().println(e);}
        	finally {em.close();}
    		response.sendRedirect(request.getContextPath()+"/showOperCard?techOperId="+techOperId);
	}

}
