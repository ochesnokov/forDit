package beansas;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import beans.ClientOrderBean;
import beans.Users;
import ochesnokov.general.WorkDataBase;

public class Test {
public static void main(String[] args){
	WorkDataBase wdb = WorkDataBase.getInstance();
	List<Users> users = wdb.em.createNativeQuery("select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID] WHERE [ccr].[f_DepartmentID] = 944 AND [u].[Flag] = 0 AND [u].[ProfileID] = 1", Users.class).getResultList();

	for(Users us : users){
		System.out.println(us.getName());
		
	}

}
}
