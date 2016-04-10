package ochesnokov.general;

import java.util.List;

import beans.ClientOrderBean;
import beans.Products;
import beans.Task;
import beans.TimeSheet;
import beans.Users;

public class ListBeans {

	WorkDataBase wdb = WorkDataBase.getInstance();

	// метод позволяет получить список всех сотрудников
	public List<Users> getAllUser() {
		@SuppressWarnings("unchecked")
		List<Users> AllUser = wdb.em.createNativeQuery(
				"select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID]",
				Users.class).getResultList();
		return AllUser;
	}

	// метод позволяет получить список всех продуктов
	public List<Products> getAllProduct() {
		List<Products> AllProduct = wdb.em.createNativeQuery("SELECT * FROM [dbo].[tAppModule]", Products.class)
				.getResultList();
		return AllProduct;
	}

	// метод позволяет получить список всех бизнес задач
	public List<Task> getAllTask() {
		List<Task> AllTask = wdb.em.createNativeQuery("select * from [dbo].[tAppModuleTask]", Task.class)
				.getResultList();
		return AllTask;
	}

	// метод позволяет получить список тестировщиков ДИТ

	public List<Users> getUsersDit() {
		@SuppressWarnings("unchecked")
		List<Users> users = wdb.em.createNativeQuery(
				"select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID] WHERE [ccr].[f_DepartmentID] = 944 AND [u].[Flag] = 0 AND [u].[ProfileID] = 1",
				Users.class).getResultList();
		return users;
	}

	// метод позволяет получить список всех нсе за указаный период включая копии
	// по id сотрудника
	@SuppressWarnings("unchecked")
	public List<ClientOrderBean> getAllNseKopFromUser(String startDate, String finishDate, int myUser) {
		List<ClientOrderBean> allNseKop = wdb.em.createNativeQuery(
				"SELECT * FROM [dbo].[tClientOrder] WHERE  [InDateTime] > '" + startDate + "' and  [InDateTime] < '"
						+ finishDate
						+ "' and [ClientInstrumentID] = 28 and [Status] !=8 and [TaskID]  IN (SELECT [AppModuleTaskID] from [dbo].[tResponsiblePerson] rp inner JOIN [dbo].[tAppModuleTask] amt ON amt.[AppModuleTaskID] = [rp].[ObjectID] where [rp].[EmployeeID] = "
						+ myUser + " and [rp].[RoleID] = 3 and [amt].[Status] !=0  and [amt].[Status] != 2)",
				ClientOrderBean.class).getResultList();
		return allNseKop;
	}
	
	//метод позволяет получить список всех факт списаний по датам и по задачам в которых в бизнес задаче ответственный тестировщик myUser 
	public List<TimeSheet> getAllTimeSheet(String startDate, String finishDate, int myUser) {
		@SuppressWarnings("unchecked")
		List<TimeSheet> allTimeSheet = wdb.em.createNativeQuery(
				"select * from [dbo].[tTimeSheet] where [DateIn] >= '" + startDate + "' and  [DateIn] <= '" + finishDate
						+ " and [ClientOrderID] IN (SELECT [ClientOrderID] FROM [dbo].[tClientOrder] WHERE"
						+ " [ClientInstrumentID] != 19  and [TaskID]  IN (SELECT [AppModuleTaskID] from "
						+ "[dbo].[tResponsiblePerson] rp inner JOIN [dbo].[tAppModuleTask] amt ON amt.[AppModuleTaskID] = [rp].[ObjectID] where [rp].[EmployeeID] = "
						+ myUser + " and [rp].[RoleID] = 3 ))",
				TimeSheet.class).getResultList();
		return allTimeSheet;
	}
}
