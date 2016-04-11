package beansas;

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
	List<TimeSheet> ts = lb.getAllTimeSheet("20160401", "20160410", 22804);
	for(TimeSheet tsi : ts){
	System.out.println(tsi.getClientOrderId());
	
	}
	WorkDataBase wdb = WorkDataBase.getInstance();
	
	System.out.println(ts.size());
	
	double sumUserTimeSheet = 0;
	for(TimeSheet tsi : ts){
		sumUserTimeSheet += tsi.getWorkTime();
	}
	System.out.println(sumUserTimeSheet);
}
}
