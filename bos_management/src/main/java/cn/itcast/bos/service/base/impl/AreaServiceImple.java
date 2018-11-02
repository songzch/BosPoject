package cn.itcast.bos.service.base.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaRepository;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;

/** 
* @author  songzch 
* @date 创建时间：2018年9月6日 下午4:04:42  
* @parameter  
* @return  
*/
@Service
@Transactional
public class AreaServiceImple implements AreaService {
	
	@Autowired
	private AreaRepository areaRepository;

	@Override
	public void save(ArrayList<Area> list) {
		areaRepository.save(list);
		
	}
	@Override
	public Page<Area> findAll(PageRequest pageable, Specification<Area> specification) {
		Page<Area> findAll = areaRepository.findAll(specification, pageable);
		return findAll;
	}

	@Override
	public Page<Area> findPageData(PageRequest pageable) {
		return areaRepository.findAll(pageable);
	}
	
    
	
	
	

}
