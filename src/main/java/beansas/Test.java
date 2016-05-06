package beansas;

import java.util.ArrayList;
import java.util.List;

import beans.Task;
import beans.Users;
import ochesnokov.general.ListBeans;
import ochesnokov.general.WorkDataBase;

public class Test {
	public static void main(String[] args) {
		ListBeans lb = new ListBeans();
		List<Task> allTask = lb.getAllTask();
		WorkDataBase wdb = WorkDataBase.getInstance();
		Users user = new Users();
		user.getTaskFromUser(17725);
		user.getAllTaskFromUser(allTask,user.getTaskForUser());
		
		for (Task us : user.getTaskForUser()) {
			System.out.println(us.getTaskName());
		}
	}
}
