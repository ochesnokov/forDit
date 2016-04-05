package beansas;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import beans.ClientOrderBean;
import beans.Period;
import beans.Users;
import ochesnokov.general.WorkDataBase;

public class Test {
public static void main(String[] args){
	WorkDataBase wdb = WorkDataBase.getInstance();

	int period = 115;
	
	Period thisPeriod = wdb.em.find(Period.class, period);
	System.out.println(thisPeriod.getPeriodStartDate());
	System.out.println(thisPeriod.getPeriodEndDate());
	

}
}
