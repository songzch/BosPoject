package cn.itcast.bos.service.base.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.TakeTimeRepository;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.TakeTimeService;

/** 
* @author  songzch 
* @date 创建时间：2018年9月25日 下午4:31:21  
* @parameter  
* @return  
*/
@Service
@Transactional
public class TakeTimeServiceImpl implements TakeTimeService {
	
	
	@Autowired
	private TakeTimeRepository takeTimeRepository;
	

	@Override
	public List<TakeTime> findAll() {
		
		List<TakeTime> list = takeTimeRepository.findAll();
		
		return list;
	}


	@Override
	public TakeTime findOne(Integer id) {
		return takeTimeRepository.findOne(id);
	}

}
