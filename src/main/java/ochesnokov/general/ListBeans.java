package ochesnokov.general;

import java.util.List;

import beans.Products;
import beans.Users;

public class ListBeans {

	WorkDataBase wdb = WorkDataBase.getInstance();
	
	
	public List<Users> getAllUer(){
		List<Users> AllUer = wdb.em.createNativeQuery("select * FROM [dbo].[tCltCltRelation] ccr inner JOIN [dbo].[tUser] u ON [u].[UserID] = [ccr].[f_UserID]", Users.class).getResultList();
		return AllUer;
	} 
	
	public List<Products> getAllProduct(){
	 List<Products> AllProduct = wdb.em.createNativeQuery("SELECT * FROM [dbo].[tAppModule]", Products.class).getResultList();
	 return AllProduct;
	
	}
}
