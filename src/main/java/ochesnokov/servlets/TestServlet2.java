package ochesnokov.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ClientOrderBean;
import beans.Users;
import ochesnokov.general.WorkDataBase;

/**
 * Servlet implementation class TestServlet2
 */
@WebServlet(name = "TestServlet2", urlPatterns = { "/TestServlet2" })
public class TestServlet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	WorkDataBase wdb = WorkDataBase.getInstance();

	public TestServlet2() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		List<Users> users = wdb.em.createNativeQuery(
				"select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID] WHERE [ccr].[f_DepartmentID] = 944 AND [u].[Flag] = 0 AND [u].[ProfileID] = 1",
				Users.class).getResultList();

		request.setAttribute("users", users);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/forma19User.jsp");
		if (dispatcher != null) {

			dispatcher.forward(request, response);

		}

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
