package cn.itcast.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.FixedAreaRepository;
import cn.itcast.bos.domain.base.FixedArea;
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

	@Override
	public void save(FixedArea model) {
		fixedAreaRepository.save(model);
	}

	@Override
	public Page<FixedArea> findPageData(Specification<FixedArea> sp, PageRequest pageable) {
		
		return fixedAreaRepository.findAll(sp, pageable);
	}
	
	
	

}
