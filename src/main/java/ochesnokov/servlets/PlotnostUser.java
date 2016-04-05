package ochesnokov.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ClientOrderBean;
import ochesnokov.general.WorkDataBase;

/**
 * Servlet implementation class PlotnostUser
 */
@WebServlet("/PlotnostUser")
public class PlotnostUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	WorkDataBase wdb = WorkDataBase.getInstance();  
    
    public PlotnostUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html;charset=UTF-8");

		String myUserParam = (String) request.getParameter("thisUser");
		String startDate = request.getParameter("dateStart");
		String finishDate = request.getParameter("dateFinish");

		int myUser = Integer.parseInt(myUserParam);
		
		List<ClientOrderBean> allClientOrder = wdb.em.createNativeQuery(
				"SELECT * FROM [dbo].[tClientOrder] WHERE  [InDateTime] > '" + startDate + "' and  [InDateTime] < '"
						+ finishDate
						+ "' and [ClientInstrumentID] != 19  and [TaskID]  IN (SELECT [AppModuleTaskID] from [dbo].[tResponsiblePerson] rp inner JOIN [dbo].[tAppModuleTask] amt ON amt.[AppModuleTaskID] = [rp].[ObjectID] where [rp].[EmployeeID] = "
						+ myUser + " and [rp].[RoleID] = 3 and [amt].[Status] !=0  and [amt].[Status] != 2)",
				ClientOrderBean.class).getResultList();
		
		
		
		
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
