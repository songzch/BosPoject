package cn.itcast.bos.service.base;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Area;

/** 
* @author  songzch 
* @date 创建时间：2018年9月6日 下午4:03:59  
* @parameter  
* @return  
*/
public interface AreaService {
	public void save(ArrayList<Area> list);

	public Page<Area> findAll(PageRequest pageable, Specification<Area> specification);
	
	public Page<Area> findPageData(PageRequest pageable);
	
	
	


}
