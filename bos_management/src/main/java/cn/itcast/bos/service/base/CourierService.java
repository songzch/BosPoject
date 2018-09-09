package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Courier;

/** 
* @author  songzch 
* @date 创建时间：2018年8月29日 下午9:17:38  
* @parameter  
* @return  
*/
public interface CourierService {

	public void save(Courier courier);

	public Page<Courier> findAll(PageRequest pageable);
	
	public Page<Courier> findPageData(PageRequest pageable,Specification specification);
	
	public void delCourier(String[] array);
		

}
