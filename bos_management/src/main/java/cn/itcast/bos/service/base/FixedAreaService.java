package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.FixedArea;

/** 
* @author  songzch 
* @date 创建时间：2018年9月7日 下午3:27:04  
* @parameter  
* @return  
*/
public interface FixedAreaService {
	
	
	public void save(FixedArea model);
	
	
	public Page<FixedArea> findPageData(Specification<FixedArea> sp,PageRequest pageable);
	

}
