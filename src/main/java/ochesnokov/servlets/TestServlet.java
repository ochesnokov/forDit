package ochesnokov.servlets;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

@WebServlet(name = "TestServlet1", urlPatterns = { "/TestServlet1" })
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	WorkDataBase wdb = WorkDataBase.getInstance();

	public TestServlet() {
		super();
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		String myUserParam = (String) request.getParameter("thisUser");
		String startDate = request.getParameter("dateStart");
		String finishDate = request.getParameter("dateFinish");

		int myUser = Integer.parseInt(myUserParam);

		// Получаем список сотрудников из jsp
		
		List<Users> first = wdb.em.createNativeQuery(
				"select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID] WHERE [ccr].[CltCltRelationID] ="
						+ myUser + " ",
				Users.class).getResultList();
		List<ClientOrderBean> allNse = new ArrayList<ClientOrderBean>();
		
		// Получаем список ошибок Дит c копиями
		List<ClientOrderBean> allNseKop = wdb.em.createNativeQuery(
				"SELECT * FROM [dbo].[tClientOrder] WHERE  [InDateTime] > '" + startDate + "' and  [InDateTime] < '"
						+ finishDate
						+ "' and [ClientInstrumentID] = 28 and [Status] !=8 and [TaskID]  IN (SELECT [AppModuleTaskID] from [dbo].[tResponsiblePerson] rp inner JOIN [dbo].[tAppModuleTask] amt ON amt.[AppModuleTaskID] = [rp].[ObjectID] where [rp].[EmployeeID] = "
						+ myUser + " and [rp].[RoleID] = 3 and [amt].[Status] !=0  and [amt].[Status] != 2)",
				ClientOrderBean.class).getResultList();

		for(ClientOrderBean cob : allNseKop){
			if(cob.getId() == cob.getParentCo() && cob.getStateId() != 359 && cob.getStateId() != 360 ){
				allNse.add(cob);
			}
		}
		
		
		String nameUser = first.get(0).getName();
		int userId = (int) first.get(0).getId();
		
		// Получаем список тестировщиков Дит
		List<Users> users = wdb.em.createNativeQuery(
				"select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID] WHERE [ccr].[f_DepartmentID] = 944 AND [u].[Flag] = 0 AND [u].[ProfileID] = 1",
				Users.class).getResultList();

		Collections.sort(allNse, new Comparator<ClientOrderBean>() {
			public int compare(ClientOrderBean o1, ClientOrderBean o2) {

				return o1.getInDateTime().compareTo(o2.getInDateTime());
			}
		});

		// колличество Нсе
		int alNse = allNse.size();
		
		// находим сотрудников департаментов
		int[] depDit = { 938, 1141, 939, 940, 1961, 2119, 944, 945, 1722, 1723, 1724, 1725, 1727, 1121, 2116, 2118 };
		List<Users> allUser = wdb.em.createNativeQuery(
				"select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID] WHERE  [f_IsActive] = 2",
				Users.class).getResultList();
		List<Users> userDit = new ArrayList<Users>();

		for (Users us : allUser) {
			for (int i = 0; i < depDit.length; i++) {
				if (us.getDepartament() == depDit[i]) {
					userDit.add(us);
				}
			}
		}

		Collections.sort(userDit, new Comparator<Users>() {
			public int compare(Users o1, Users o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		//Отбираем клиентские нсе
		List<ClientOrderBean> clientNse = new ArrayList<ClientOrderBean>() {
		};
		boolean a = false;
		List<ClientOrderBean> ditNse = new ArrayList<ClientOrderBean>();
		for (ClientOrderBean cob : allNse) {
			for (Users us : userDit) {
				a = false;
				if (us.getOnName() == cob.getOnName()) {
					ditNse.add(cob);
					a = true;
					break;
				}
			}
			if (!a) {
				clientNse.add(cob);
			}
		}

		// Колличество внутренних ошибок
		int ditNseSize = ditNse.size();

		// Колличество клиентских ошибок
		int clientNseSize = clientNse.size();

		// Колличество внутренних нсе 1 приоритета
		int kolDitNseFirst = 0;
		// колличество в очках
		int costDitNse = ditNseSize;
		for (ClientOrderBean cod : ditNse) {
			if (cod.getPriority() == 7) {
				costDitNse = costDitNse + 3;
				kolDitNseFirst++;
			}
		}

		// Колличество внешних нсе 1 приоритета

		int kolClientNseFirst = 0;
		// колличество в очках
		int costClientNse = clientNseSize;
		for (ClientOrderBean cod : clientNse) {
			if (cod.getPriority() == 7) {
				costClientNse = costClientNse + 3;
				kolClientNseFirst++;
			}
		}
		// Считаем kpi
		double costclientNseDouble = (double) costClientNse;
		double costDitNseDouble = (double) costDitNse;

		double summCostNse = costclientNseDouble + costDitNseDouble;
		double kpi;
		if (summCostNse != 0) {
			kpi = (costDitNseDouble / summCostNse) * 100;
		} else {
			kpi = 0;
		}
		String formattedDouble = new DecimalFormat("#0.00").format(kpi);

		request.setAttribute("userId", userId);
		request.setAttribute("nameUser", nameUser);
		request.setAttribute("clientOrder", allNse);
		request.setAttribute("sizeCo", alNse);
		request.setAttribute("users", users);
		request.setAttribute("dateStart", startDate);
		request.setAttribute("dateFinish", finishDate);
		request.setAttribute("clientFirst", kolClientNseFirst);
		request.setAttribute("clientNseSize", clientNseSize);
		request.setAttribute("kolDitNseFirst", kolDitNseFirst);
		request.setAttribute("kpi", formattedDouble);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/forma19User.jsp");
		if (dispatcher != null) {

			dispatcher.forward(request, response);

		}

		
	}

}
