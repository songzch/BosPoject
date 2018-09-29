package cn.itcast.bos.service.base;

import java.util.List;

import cn.itcast.bos.domain.base.TakeTime;

/** 
* @author  songzch 
* @date 创建时间：2018年9月25日 下午4:29:33  
* @parameter  
* @return  
*/
public interface TakeTimeService {
	
  public List<TakeTime> findAll();
  
  public TakeTime findOne(Integer id);

}
