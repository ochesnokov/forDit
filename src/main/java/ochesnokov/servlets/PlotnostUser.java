package ochesnokov.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.ClientOrderBean;
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

		// Все списания на бизнесс задачи в которых ответственный myUser
		List<TimeSheet> allTimeSheet = lb.getAllTimeSheet(startDate, finishDate, myUser);
		
		// Списания тестировщика с типами рабты "тестирование", "Верификация", "Воспроизведение ошибки"
		List<TimeSheet> testerTimeSheet = new ArrayList<TimeSheet>();
		for(TimeSheet ts : allTimeSheet){
			if((int)ts.getUserId() == myUser ){
				if(ts.getTaskType() == 11 || ts.getTaskType() == 17 || ts.getTaskType() == 93){
					testerTimeSheet.add(ts);
				}
			}
		}
		//сумма списаний тестировщика
		double sumTaskSheetTester = 0;
		for(TimeSheet ts : testerTimeSheet){
			sumTaskSheetTester += ts.getWorkTime();
		}
		
		// список департаментов разработчиков
		int[] departmentsDevelop = {940, 1961, 2119};
		
		// отбираем всех разработчиков ДИТ
		List<Users> developersDit = new ArrayList<Users>();
		for(int i = 0; i < departmentsDevelop.length; i++){
			developersDit.addAll((lb.getUsersDit(departmentsDevelop[i])));
		}
		//отбираем списания разработчиков с типом разработка
		List<TimeSheet> developersTimeSheet = new ArrayList<TimeSheet>();
		
		for(Users dd : developersDit){
			for(TimeSheet ts : allTimeSheet){
				if((int)ts.getUserId() == dd.getUser() ){
					if(ts.getTaskType() == 1){
						developersTimeSheet.add(ts);
					}
				}
			}
		}
		//сумма списаний разработчиков
		double sumTaskSheetDevelopers = 0;
		for(TimeSheet ts : developersTimeSheet){
			sumTaskSheetDevelopers += ts.getWorkTime();
		}
		
		
		
		
		

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
