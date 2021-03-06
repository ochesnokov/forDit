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

import beans.AllPeriods;
import beans.ClientOrderBean;
import beans.Periods;
import beans.TimeSheet;
import beans.Users;
import ochesnokov.general.ListBeans;
import ochesnokov.general.WorkDataBase;

/**
 * Servlet implementation class PlotnostUser
 */
@WebServlet("/PlotnostUser")
public class PlotnostUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	WorkDataBase wdb = WorkDataBase.getInstance();
	ListBeans lb = new ListBeans();
	String startDate;
	String finishDate;

	AllPeriods allPeriods = new AllPeriods();

	public PlotnostUser() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");

		String myUserParam = (String) request.getParameter("thisUser");
		String periodId = (String) request.getParameter("thisPeriod");
		String inPlotnost = (String) request.getParameter("inPlotnost");
		inPlotnost = inPlotnost.replace(',', '.');

		double lastPlotnost = 0;

		if (!inPlotnost.isEmpty()) {
			lastPlotnost = Double.parseDouble(inPlotnost);
		}
		int myUser = Integer.parseInt(myUserParam);
		int myPeriod = Integer.parseInt(periodId);
		String periodName = "";

		List<Periods> periods = allPeriods.getAllPeriods();
		for (Periods p : periods) {
			if (p.getId() == myPeriod) {
				startDate = p.getStartPeriod();
				finishDate = p.getFinishPeriod();
				periodName = p.getName();
			}
		}

		@SuppressWarnings("unchecked")
		List<Users> first = wdb.em.createNativeQuery(
				"select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID] WHERE [ccr].[CltCltRelationID] ="
						+ myUser + " ",
				Users.class).getResultList();
		String nameUser = first.get(0).getName();

		// Все списания на бизнесс задачи в которых ответственный myUser
		List<TimeSheet> allTimeSheet = lb.getAllTimeSheet(startDate, finishDate, myUser);

		// Списания тестировщика с типами рабты "тестирование", "Верификация",
		// "Воспроизведение ошибки"
		List<TimeSheet> testerTimeSheet = new ArrayList<TimeSheet>();
		for (TimeSheet ts : allTimeSheet) {
			if ((int) ts.getUserId() == myUser) {
				if (ts.getTaskType() == 11 || ts.getTaskType() == 17 || ts.getTaskType() == 93) {
					testerTimeSheet.add(ts);
				}
			}
		}
		// сумма списаний тестировщика

		double sumTaskSheetTester = 0;

		for (TimeSheet ts : testerTimeSheet) {
			sumTaskSheetTester += ts.getWorkTime();
		}

		// список департаментов разработчиков
		int[] departmentsDevelop = { 940, 1961, 2119 };

		// отбираем всех разработчиков ДИТ
		List<Users> developersDit = new ArrayList<Users>();

		for (int i = 0; i < departmentsDevelop.length; i++) {
			developersDit.addAll((lb.getUsersDit(departmentsDevelop[i])));
		}

		// отбираем списания разработчиков с типом разработка
		List<TimeSheet> developersTimeSheet = new ArrayList<TimeSheet>();

		for (Users dd : developersDit) {
			for (TimeSheet ts : allTimeSheet) {
				if ((int) ts.getUserId() == (int) dd.getId()) {

					developersTimeSheet.add(ts);

				}
			}
		}

		for (TimeSheet ts : developersTimeSheet) {
			for (Users us : developersDit) {
				if (ts.getUserId() == us.getId()) {
					ts.setName(us.getName());
				}
			}

		}
		// сумма списаний разработчиков

		double sumTaskSheetDevelopers = 0;
		for (TimeSheet ts : developersTimeSheet) {
			sumTaskSheetDevelopers += ts.getWorkTime();
		}

		// список ошибок по бизнес задачам сотрудника
		List<ClientOrderBean> allNse = new ArrayList<ClientOrderBean>();

		// Получаем список всех нсе за указаный период включая копии
		List<ClientOrderBean> allNseKop = lb.getAllNseKopFromUser(startDate, finishDate, myUser);

		// убираем копии и состояния "Доступно" и "Есть решение"
		for (ClientOrderBean cob : allNseKop) {
			if (cob.getId() == cob.getParentCo() && cob.getStateId() != 359 && cob.getStateId() != 360) {
				allNse.add(cob);
			}
		}

		// Массив id всех отделов ДИТ
		int[] depDit = { 938, 1141, 939, 940, 1961, 2119, 944, 945, 1722, 1723, 1724, 1725, 1727, 1121, 2116, 2118 };

		@SuppressWarnings("unchecked")
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

		// Отбираем все клиентские ошибки
		List<ClientOrderBean> clientNse = new ArrayList<ClientOrderBean>();
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

		// Колличество клиентских ошибок
		int clientNseSize = clientNse.size();

		double plotnost = ((double) clientNseSize) / (sumTaskSheetDevelopers - sumTaskSheetTester);

		double result = 0;
		if (lastPlotnost != 0) {
			result = 100 * (lastPlotnost - plotnost) / lastPlotnost;
		}

		String resultString = new DecimalFormat("#0.00").format(result) + "%";
		String plotnostString = String.format("%.4f", plotnost);
		String sumTaskSheetDevelopersString = new DecimalFormat("#0.00").format(sumTaskSheetDevelopers);
		String sumTaskSheetTesterString = new DecimalFormat("#0.00").format(sumTaskSheetTester);

		List<Users> users = wdb.em.createNativeQuery(
				"select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID] WHERE [ccr].[f_DepartmentID] = 944 AND [u].[Flag] = 0 AND [u].[ProfileID] = 1",
				Users.class).getResultList();
		int userId = (int) first.get(0).getId();

		request.setAttribute("users", users);
		request.setAttribute("userId", userId);
		request.setAttribute("clientOrder", allNse);
		request.setAttribute("periodId", periodId);
		request.setAttribute("periodName", periodName);
		request.setAttribute("clientNseSize", clientNseSize);
		request.setAttribute("plotnost", plotnostString);
		request.setAttribute("sumTaskSheetTester", sumTaskSheetTesterString);
		request.setAttribute("sumTaskSheetDevelopers", sumTaskSheetDevelopersString);
		request.setAttribute("nameUser", nameUser);
		request.setAttribute("periods", periods);
		request.setAttribute("result", resultString);
		request.setAttribute("lastPlotnost", lastPlotnost);

		request.setAttribute("testerTimeSheet", testerTimeSheet);
		request.setAttribute("developersTimeSheet", developersTimeSheet);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/plotnostUser.jsp");
		if (dispatcher != null) {

			dispatcher.forward(request, response);

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
