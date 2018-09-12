package servlets.ctrlCard;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
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

@WebServlet("/editCtrlCard")
public class EditCtrlCardServlet extends HttpServlet {
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
		if (stechOperId != null && !stechOperId.equals("")) {
			techOperId = Integer.parseInt(stechOperId.trim());
		}
		int techCtrlId = 0;
		String stechCtrlId = request.getParameter("techCtrlId");
		if (stechCtrlId != null && !stechCtrlId.equals("")) {
			techCtrlId = Integer.parseInt(stechCtrlId.trim());
		}
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			TechOperation techOper = new TechOperationService(em).find(techOperId);
			if(techCtrlId != 0) {
				TechControl techCtrl = em.find(TechControl.class, techCtrlId);;
				request.getServletContext().setAttribute("techCtrl", techCtrl);
			}
			
			@SuppressWarnings("unchecked")
			List<Tool> toolList = em.createNamedQuery("AllTools").getResultList();
			request.setAttribute("toolList", toolList);
			for(Tool t:toolList) {
				em.refresh(t);
			}
			em.getTransaction().commit();
			request.getServletContext().setAttribute("techOper", techOper);
		} catch (SQLException e) {e.printStackTrace();}
		finally{em.close();}
		
		request.getServletContext().getRequestDispatcher("/WEB-INF/views/ctrlCard/editCtrlCardView.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		TechOperation techOper = (TechOperation) request.getServletContext().getAttribute("techOper");
		request.getServletContext().removeAttribute("techOper");
		
		TechControl oldTechCtrl = (TechControl) request.getServletContext().getAttribute("techCtrl");
		request.getServletContext().removeAttribute("techOper");
		
		request.setCharacterEncoding("utf-8");
		
		//Для документа -------------------------------
		String docName = request.getParameter("docName");
		String docSign = request.getParameter("docSign");
		String docLetter = request.getParameter("docLetter");
		
		//Для самого тех. контроля -------------------------------
		String instructSign = request.getParameter("instructSign");

		//Для операции
		float mainTime = 0;
		String smainTime = request.getParameter("mainTime");
		if (smainTime!=null && !smainTime.equals("")) {
			mainTime = Float.parseFloat(smainTime.trim());
		}
		float assistTime = 0;
		String sassistTime = request.getParameter("assistTime");
		if (sassistTime!=null && !sassistTime.equals("")) {
			assistTime = Float.parseFloat(sassistTime.trim());
		}
		techOper.setMainTime(mainTime);
		techOper.setAssistTime(assistTime);
		
		//Для параметров------------------------------
		String[] sparamNumbers = request.getParameterValues("paramNumber");
		int[] paramNumbers = new int[sparamNumbers.length];
		for(int i=0; i<sparamNumbers.length; i++) {
				if (sparamNumbers[i]!=null && !sparamNumbers[i].equals("")) {
					paramNumbers[i] = Integer.parseInt(sparamNumbers[i].trim());
				}
		}
		
		String[] parameters = request.getParameterValues("parameter");
		
		// Средства ТО ----------------------------------
		String[] devicesPiles = request.getParameterValues("devicesPile");
		//--------------------------------------------
		
		String[] valuePCs = request.getParameterValues("valuePC");
		
		String[] stimesRatios = request.getParameterValues("timesRatio");
		float[] timesRatios = new float[stimesRatios.length];
		for(int i=0; i<stimesRatios.length; i++) {
				if (stimesRatios[i]!=null && !stimesRatios[i].equals("")) {
					timesRatios[i] = Float.parseFloat(stimesRatios[i].trim());
				}
		}
		
		//Формируем сущности
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			Document document = new Document(null, null, null,new Date(), new Date(), docName, "Карта контроля", docLetter, docSign);
			TechControl techCtrl = new TechControl(null, null, null, new Date(), new Date(), instructSign);
			List<ParamControl> paramControls = new ArrayList<ParamControl>();
			for(int i=0;i<paramNumbers.length;i++) {
				ParamControl paramCtrl = new ParamControl(techCtrl, parameters[i], valuePCs[i], timesRatios[i], paramNumbers[i]);
				paramControls.add(paramCtrl);
			}
			new TechOperationService(em).editCtrlCard(techOper, document, techCtrl, oldTechCtrl, paramControls, devicesPiles);
			em.getTransaction().commit();
		} 
		catch (SQLException e) {e.printStackTrace();}
		finally {em.close();}
		response.sendRedirect(request.getContextPath()+"/ctrlCardList?techOperId="+techOper.getObjId());
		}
}
