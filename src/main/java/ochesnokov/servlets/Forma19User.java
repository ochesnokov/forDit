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
		// РџРѕР»СѓС‡Р°РµРј РґР°РЅРЅС‹Рµ СЃ JSP
		String myUserParam = (String) request.getParameter("thisUser");
		String startDate = request.getParameter("dateStart");
		String finishDate = request.getParameter("dateFinish");

		int myUser = Integer.parseInt(myUserParam);
		List<Products> allProducts = lb.getAllProduct();
		
		// РџРѕР»СѓС‡Р°РµРј СЃРїРёСЃРѕРє СЃРѕС‚СЂСѓРґРЅРёРєРѕРІ РёР· jsp

		@SuppressWarnings("unchecked")
		List<Users> first = wdb.em.createNativeQuery(
				"select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID] WHERE [ccr].[CltCltRelationID] ="
						+ myUser + " ",
				Users.class).getResultList();
		List<ClientOrderBean> allNse = new ArrayList<ClientOrderBean>();

		// РџРѕР»СѓС‡Р°РµРј СЃРїРёСЃРѕРє РѕС€РёР±РѕРє Р”РёС‚ c РєРѕРїРёСЏРјРё
		@SuppressWarnings("unchecked")
		List<ClientOrderBean> allNseKop = wdb.em.createNativeQuery(
				"SELECT * FROM [dbo].[tClientOrder] WHERE  [InDateTime] > '" + startDate + "' and  [InDateTime] < '"
						+ finishDate
						+ "' and [ClientInstrumentID] = 28 and [Status] !=8 and [TaskID]  IN (SELECT [AppModuleTaskID] from [dbo].[tResponsiblePerson] rp inner JOIN [dbo].[tAppModuleTask] amt ON amt.[AppModuleTaskID] = [rp].[ObjectID] where [rp].[EmployeeID] = "
						+ myUser + " and [rp].[RoleID] = 3 and [amt].[Status] !=0  and [amt].[Status] != 2)",
				ClientOrderBean.class).getResultList();

		// РћС‚Р±РёСЂР°РµРј РќРµРѕР±С…РѕРґРёРјС‹Рµ РќРЎР•
		for (ClientOrderBean cob : allNseKop) {
			if (cob.getId() == cob.getParentCo() && cob.getStateId() != 359 && cob.getStateId() != 360) {
				allNse.add(cob);
			}
		}

		String nameUser = first.get(0).getName();
		int userId = (int) first.get(0).getId();

		// РџРѕР»СѓС‡Р°РµРј СЃРїРёСЃРѕРє С‚РµСЃС‚РёСЂРѕРІС‰РёРєРѕРІ Р”РёС‚
		@SuppressWarnings("unchecked")
		List<Users> users = wdb.em.createNativeQuery(
				"select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID] WHERE [ccr].[f_DepartmentID] = 944 AND [u].[Flag] = 0 AND [u].[ProfileID] = 1",
				Users.class).getResultList();

		// Р”РѕР±Р°РІР»СЏРµРј РЅР°РёРјРµРЅРѕРІР°РЅРёРµ РїСЂРѕРґСѓРєС‚Р°, РјРѕРґСѓР»СЏ, Рё РїРѕР»СЏ "РѕС‚ РёРјРµРЅРё"
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
		//РџРѕР»СѓС‡РёРј РјРЅРѕР¶РµСЃС‚РІРѕ РїСЂРѕРґСѓРєС‚РѕРІ
		Set<Integer> manyProducts = new HashSet<Integer>();
		for(ClientOrderBean an : allNse){
			manyProducts.add(an.getProductId());
		}
		
		
		
		List<Products> productsForDetail = new ArrayList<Products>();
		for(Products pr : allProducts){
			
			for(Integer mp : manyProducts){
				int a = mp;
				if(mp == pr.getId()){
					productsForDetail.add(pr);
				}
			}
		}
		
		
		// РєРѕР»Р»РёС‡РµСЃС‚РІРѕ РќСЃРµ
		int alNse = allNse.size();

		// РЅР°С…РѕРґРёРј СЃРѕС‚СЂСѓРґРЅРёРєРѕРІ РґРµРїР°СЂС‚Р°РјРµРЅС‚РѕРІ
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

		// РћС‚Р±РёСЂР°РµРј РєР»РёРµРЅС‚СЃРєРёРµ РЅСЃРµ
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

		// РљРѕР»Р»РёС‡РµСЃС‚РІРѕ РІРЅСѓС‚СЂРµРЅРЅРёС… РѕС€РёР±РѕРє
		int ditNseSize = ditNse.size();

		// РљРѕР»Р»РёС‡РµСЃС‚РІРѕ РєР»РёРµРЅС‚СЃРєРёС… РѕС€РёР±РѕРє
		int clientNseSize = clientNse.size();

		// РљРѕР»Р»РёС‡РµСЃС‚РІРѕ РІРЅСѓС‚СЂРµРЅРЅРёС… РЅСЃРµ 1 РїСЂРёРѕСЂРёС‚РµС‚Р°
		int kolDitNseFirst = 0;
		// РєРѕР»Р»РёС‡РµСЃС‚РІРѕ РІ РѕС‡РєР°С…
		int costDitNse = ditNseSize;
		for (ClientOrderBean cod : ditNse) {
			if (cod.getPriority() == 7) {
				costDitNse = costDitNse + 3;
				kolDitNseFirst++;
			}
		}

		// РљРѕР»Р»РёС‡РµСЃС‚РІРѕ РІРЅРµС€РЅРёС… РЅСЃРµ 1 РїСЂРёРѕСЂРёС‚РµС‚Р°

		int kolClientNseFirst = 0;
		// РєРѕР»Р»РёС‡РµСЃС‚РІРѕ РІ РѕС‡РєР°С…
		int costClientNse = clientNseSize;
		for (ClientOrderBean cod : clientNse) {
			if (cod.getPriority() == 7) {
				costClientNse = costClientNse + 3;
				kolClientNseFirst++;
			}
		}
		// РЎС‡РёС‚Р°РµРј kpi
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

		// РѕС‚РїСЂР°РІР»СЏРµРј РґР°РЅРЅС‹Рµ РІ jsp
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
