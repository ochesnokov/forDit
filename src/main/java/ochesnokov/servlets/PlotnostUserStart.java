package ochesnokov.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.AllPeriods;

import beans.Periods;
import beans.Users;
import ochesnokov.general.WorkDataBase;

/**
 * Servlet implementation class PlotnostUser
 */
@WebServlet("/PlotnostUserStart")
public class PlotnostUserStart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	WorkDataBase wdb = WorkDataBase.getInstance();
	AllPeriods allPeriods = new AllPeriods();
	
    public PlotnostUserStart() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		List<Users> users = wdb.em.createNativeQuery(
				"select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID] WHERE [ccr].[f_DepartmentID] = 944 AND [u].[Flag] = 0 AND [u].[ProfileID] = 1",
				Users.class).getResultList();

		request.setAttribute("users", users);
		
		
		
		List<Periods> periods =  allPeriods.getAllPeriods();

		
		
		request.setAttribute("periods", periods);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/plotnostUser.jsp");
		if (dispatcher != null) {

			dispatcher.forward(request, response);
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
