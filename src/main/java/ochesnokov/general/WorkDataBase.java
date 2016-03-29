package ochesnokov.general;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public class WorkDataBase {

	private static final WorkDataBase instance = new WorkDataBase();
	private  WorkDataBase() {}
	
	public static WorkDataBase getInstance(){
	 return instance;
	}
	
	public EntityManager em = Persistence.createEntityManagerFactory("COLIBRI").createEntityManager();
	
	
}
	

