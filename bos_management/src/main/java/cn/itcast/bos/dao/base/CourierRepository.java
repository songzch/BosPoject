package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Courier;

/** 
* @author  songzch 
* @date 创建时间：2018年8月29日 下午10:04:03  
* @parameter  
* @return  
*/
public interface CourierRepository extends JpaRepository<Courier, Integer>,JpaSpecificationExecutor<Courier> {
	
	
	@Query(value="update Courier set deltag = '1' where id = ?")
	@Modifying
	public void delCourier(Integer id);
	

}
