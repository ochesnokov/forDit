package ochesnokov.servlets;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import beans.ClientOrderBean;
import beans.Products;
import beans.Task;
import beans.Users;
import ochesnokov.general.ListBeans;
import ochesnokov.general.WorkDataBase;

@WebServlet(name = "Forma19User", urlPatterns = { "/Forma19User" })
public class Forma19User extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ListBeans lb = new ListBeans();
	WorkDataBase wdb = WorkDataBase.getInstance();

	public Forma19User() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
		// Получаем параметры с jsp страницы
		String myUserParam = (String) request.getParameter("thisUser");
		String startDate = request.getParameter("dateStart");
		String finishDate = request.getParameter("dateFinish");

		System.out.println(startDate);
		System.out.println(finishDate);

		int myUser = Integer.parseInt(myUserParam);
		List<Products> allProducts = lb.getAllProduct();

		@SuppressWarnings("unchecked")
		List<Users> first = wdb.em.createNativeQuery(
				"select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID] WHERE [ccr].[CltCltRelationID] ="
						+ myUser + " ",
				Users.class).getResultList();
		List<ClientOrderBean> allNse = new ArrayList<ClientOrderBean>();

		// Получаем список всех нсе за указаный период включая копии

		List<ClientOrderBean> allNseKop = lb.getAllNseKopFromUser(startDate, finishDate, myUser);

		// отбираем все нсе в состояниях отличных от "отказано" и "есть решение"
		for (ClientOrderBean cob : allNseKop) {
			if (cob.getId() == cob.getParentCo() && cob.getStateId() != 359 && cob.getStateId() != 360) {
				allNse.add(cob);
			}
		}

		String nameUser = first.get(0).getName();
		int userId = (int) first.get(0).getId();

		// Получаем список тестировщиков ДИТ

		List<Users> users = lb.getUsersDit(944);

		// Проставляем текстовые поля "продукт"," модуль ","бизнес задача " и
		// поле "от имени"
		for (ClientOrderBean cob : allNse) {

			Products a = wdb.em.find(Products.class, new Long(cob.getProductId()));
			cob.setProductName(a.getSysName());

			Products b = wdb.em.find(Products.class, new Long(cob.getModuleId()));
			cob.setModuleName(b.getSysName());

			Task d = wdb.em.find(Task.class, new Long(cob.getTaskId()));
			cob.setTask(d.getTaskName());

			@SuppressWarnings("unchecked")
			List<Users> c = wdb.em.createNativeQuery(
					"select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID] WHERE [ccr].[f_ChildClientID] ="
							+ cob.getOnName() + " ",
					Users.class).getResultList();
			cob.setOnNameString(c.get(0).getName());

		}

		Collections.sort(allNse, new Comparator<ClientOrderBean>() {
			public int compare(ClientOrderBean o1, ClientOrderBean o2) {

				return o1.getProductName().compareTo(o2.getProductName());
			}
		});
		// Убираем повторяющиеся продукты в списке
		Set<Integer> manyProducts = new HashSet<Integer>();
		for (ClientOrderBean an : allNse) {
			manyProducts.add(an.getProductId());
		}

		List<Products> productsForDetail = new ArrayList<Products>();
		for (Products pr : allProducts) {

			for (Integer mp : manyProducts) {

				if (mp == pr.getId()) {
					productsForDetail.add(pr);
				}
			}
		}

		// Колличество отобранных задач
		int alNse = allNse.size();

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

		int kolDitNseFirst = 0;
		// Расчитаем сумму ошибок ДИТ
		// ошибки первого приоритета идут за 4 балла
		int costDitNse = ditNseSize;
		for (ClientOrderBean cod : ditNse) {
			if (cod.getPriority() == 7) {
				costDitNse = costDitNse + 3;
				kolDitNseFirst++;
			}
		}

		int kolClientNseFirst = 0;
		// // Расчитаем сумму клиентских ошибок
		// ошибки первого приоритета идут за 4 балла
		int costClientNse = clientNseSize;
		for (ClientOrderBean cod : clientNse) {
			if (cod.getPriority() == 7) {
				costClientNse = costClientNse + 3;
				kolClientNseFirst++;
			}
		}

		double costclientNseDouble = (double) costClientNse;
		double costDitNseDouble = (double) costDitNse;
		// Считаем KPI
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
		request.setAttribute("manyProducts", productsForDetail);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/forma19User.jsp");
		if (dispatcher != null) {

			dispatcher.forward(request, response);

		}

	}

}
