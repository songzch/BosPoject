package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.base.TakeTime;

/** 
* @author  songzch 
* @date 创建时间：2018年9月25日 下午4:34:30  
* @parameter  
* @return  
*/
public interface TakeTimeRepository extends JpaRepository<TakeTime, Integer> {

	
}
