package servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletOutputStream;
import javax.sql.DataSource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;

import org.eclipse.persistence.config.PersistenceUnitProperties;

import services.*;
import entities.*;

@WebServlet("/imageFromFile")
public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private EntityManagerFactory emf;
	private DataSource ds;
	
    public ImageServlet() {
        super();
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void init() throws ServletException {
        try {
            InitialContext ctx = new InitialContext();
            ds = (DataSource) ctx.lookup("java:comp/env/jdbc/DefaultDB");
            Map properties = new HashMap();
            properties.put(PersistenceUnitProperties.NON_JTA_DATASOURCE, ds);
            emf = Persistence.createEntityManagerFactory("ProjectDB", properties);
        } catch (NamingException e) {
            throw new ServletException(e);
        }
    }

    public void destroy() {
        emf.close();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileType = request.getParameter("type");
		
		EntityManager em = emf.createEntityManager();
		int fileId = Integer.parseInt(request.getParameter("fileId"));
		TechFile tf = new TechFileService(em).findTechFile(fileId);
		
		if(fileType !=null && fileType.trim().equals("image")) {
					response.setContentType("image/*");
					response.setHeader("Content-Length", String.valueOf(tf.getFileData().length));
			        response.setHeader("Content-Disposition", "inline; filename=\"" + tf.getFileName() + "\"");
					ServletOutputStream os = response.getOutputStream();
					os.write(tf.getFileData());
					os.close();
		}
		else 
			if(fileType !=null && fileType.trim().equals("pdf")) {
				String contentType = this.getServletContext().getMimeType(tf.getFileName());
		        response.setHeader("Content-Type", contentType);
		        response.setHeader("Content-Length", String.valueOf(tf.getFileData().length));
		        response.setHeader("Content-Disposition", "inline; filename=\"" + tf.getFileName() + "\"");
				
		        response.getOutputStream().write(tf.getFileData());
			}
			else {
				String contentType = this.getServletContext().getMimeType(tf.getFileName());
		        response.setHeader("Content-Type", contentType);
		        response.setHeader("Content-Length", String.valueOf(tf.getFileData().length));
		        response.setHeader("Content-Disposition", "attachment; filename=\"" + tf.getFileName() + "\"");
				
		        response.getOutputStream().write(tf.getFileData());
			}
			
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
