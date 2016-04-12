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

/**
 * Servlet implementation class Forma19Dit
 */
@WebServlet("/Forma19Dit")
public class Forma19Dit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	WorkDataBase wdb = WorkDataBase.getInstance();
	ListBeans lb = new ListBeans();

	public Forma19Dit() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");

		String startDate = request.getParameter("dateStart");
		String finishDate = request.getParameter("dateFinish");

		// Получаем список тестировщиков дит
		List<Users> users = lb.getUsersDit(944);
		List<Products> allProducts = lb.getAllProduct();
		List<ClientOrderBean> allNseKop = new ArrayList<ClientOrderBean>();
		List<ClientOrderBean> allNseKopTime = new ArrayList<ClientOrderBean>();

		// Получаем список ошибок Дит c копиями
		for (Users user : users) {
			allNseKopTime.clear();
			allNseKopTime = lb.getAllNseKopFromUser(startDate, finishDate, (int) user.getId());
			allNseKop.addAll(allNseKopTime);
		}

		List<ClientOrderBean> allNse = new ArrayList<ClientOrderBean>();
		for (ClientOrderBean cob : allNseKop) {
			if (cob.getId() == cob.getParentCo() && cob.getStateId() != 359 && cob.getStateId() != 360) {
				allNse.add(cob);
			}
		}

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

				return o1.getInDateTime().compareTo(o2.getInDateTime());
			}
		});

		// колличество Нсе
		int alNse = allNse.size();

		// находим сотрудников департаментов
		int[] depDit = { 938, 1141, 939, 940, 1961, 2119, 944, 945, 1722, 1723, 1724, 1725, 1727, 1121, 2116, 2118 };

		List<Users> allUser = lb.getAllUser();
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

		Set<Integer> manyProducts = new HashSet<Integer>();
		for (ClientOrderBean an : allNse) {
			manyProducts.add(an.getProductId());
		}

		List<Products> productsForDetail = new ArrayList<Products>();
		for (Products pr : allProducts) {

			for (Integer mp : manyProducts) {
				int a = mp;
				if (mp == pr.getId()) {
					productsForDetail.add(pr);
				}
			}
		}
		// Отбираем клиентские нсе
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
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/forma19Dit.jsp");
		if (dispatcher != null) {

			dispatcher.forward(request, response);

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
