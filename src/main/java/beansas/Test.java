package beansas;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import beans.ClientOrderBean;
import beans.Period;
import beans.Products;
import beans.Users;
import ochesnokov.general.WorkDataBase;

public class Test {
public static void main(String[] args){
	WorkDataBase wdb = WorkDataBase.getInstance();
	long a = new Long(2000000);
	ClientOrderBean cob = wdb.em.find(ClientOrderBean.class, a);
	 long b = new Long(555);
	Products c = wdb.em.find(Products.class, b);
	cob.setProductName(c.getSysName());
	
	System.out.println(cob.getProductName());
	Integer aa = 1;
	int bb = aa;
	long cc = 1;
	if(cc == bb){
		System.out.println("да");
	} else{
		System.out.println("нет");
	}

}
}
