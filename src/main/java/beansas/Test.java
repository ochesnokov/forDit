package beansas;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import beans.ClientOrderBean;
import beans.Period;
import beans.Products;
import beans.TimeSheet;
import beans.Users;
import ochesnokov.general.ListBeans;
import ochesnokov.general.WorkDataBase;

public class Test {
public static void main(String[] args){
	ListBeans lb = new ListBeans();
	int[] departmentsDevelop = {940, 1961, 2119};
	
	List<Users> developersDit = new ArrayList<Users>();
	for(int i = 0; i < departmentsDevelop.length; i++){
		developersDit.addAll((lb.getUsersDit(departmentsDevelop[i])));
	}
	for(Users us : developersDit){
	System.out.println(us.getName());
	}
}
}
