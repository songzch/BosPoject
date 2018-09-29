package cn.itcast.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.dao.base.TakeTimeRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.FixedAreaService;

/** 
* @author  songzch 
* @date 创建时间：2018年9月7日 下午3:27:28  
* @parameter  
* @return  
*/
@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService{
	
	@Autowired
	private FixedAreaRepository fixedAreaRepository;
	
	@Autowired
	private CourierRepository courierRepository;
	
	@Autowired
	private TakeTimeRepository takeTimeRepository;

	@Override
	public void save(FixedArea model) {
		fixedAreaRepository.save(model);
	}

	@Override
	public Page<FixedArea> findPageData(Specification<FixedArea> sp, PageRequest pageable) {
		
		return fixedAreaRepository.findAll(sp, pageable);
	}

	@Override
	public FixedArea findOne(String id) {
		return fixedAreaRepository.findOne(id);
	}

	@Override
	public void associationCourierToFixedArea(String id, Integer courierId, Integer takeTimeId) {
		FixedArea fixedArea = fixedAreaRepository.findOne(id);
		
		Courier courier = courierRepository.findOne(courierId);
		
		TakeTime takeTime = takeTimeRepository.findOne(takeTimeId);
		
		System.out.println("收派时间为："+takeTime);
		System.out.println("收派时间id为："+takeTimeId);
		fixedArea.getCouriers().add(courier);
		
		courier.setTakeTime(takeTime);
		
		
		
	}
	
	
	

}
