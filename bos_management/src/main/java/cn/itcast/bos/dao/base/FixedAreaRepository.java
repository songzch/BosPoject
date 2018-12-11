package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.bos.domain.base.FixedArea;

/** 
* @author  songzch 
* @date 创建时间：2018年9月7日 下午3:27:52  
* @parameter  
* @return  
*/
public interface FixedAreaRepository extends JpaRepository<FixedArea, String>,JpaSpecificationExecutor<FixedArea> {
    
	
	
}
