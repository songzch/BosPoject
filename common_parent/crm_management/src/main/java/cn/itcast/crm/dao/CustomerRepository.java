package cn.itcast.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.crm.domain.Customer;

/** 
* @author  songzch 
* @date 创建时间：2018年9月10日 下午1:37:36  
* @parameter  
* @return  
*/
public interface CustomerRepository extends JpaRepository<Customer, Integer>{
	
	public List<Customer> findByFixedAreaIdIsNull();
	
	                            
	public List<Customer> findByFixedAreaId(String fixedAreaId);
	
	@Query("update Customer set fixedAreaId =?2 where id = ?1")
	@Modifying
	public void updateFixedArea(Integer id,String FixedAreaId);
	
	@Query("update Customer set fixedAreaId = null where fixedAreaId= ?")
	@Modifying
	public void clearFixedArea(String FixedAreaId);

}
