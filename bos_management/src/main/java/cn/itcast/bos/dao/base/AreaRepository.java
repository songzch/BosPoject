package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.itcast.bos.domain.base.Area;

/** 
* @author  songzch 
* @date 创建时间：2018年9月6日 下午4:08:10  
* @parameter  
* @return  
*/
public interface AreaRepository extends JpaRepository<Area, String>,JpaSpecificationExecutor<Area> {

}
