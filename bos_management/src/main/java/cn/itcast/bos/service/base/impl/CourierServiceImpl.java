package cn.itcast.bos.service.base.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.struts2.components.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierRepository;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;

/** 
* @author  songzch 
* @date 创建时间：2018年8月29日 下午9:31:55  
* @parameter  
* @return  
*/
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
	
	@Autowired
	private CourierRepository courierRepository;

	@Override
	public void save(Courier courier) {
		courierRepository.save(courier);

	}

	@Override
	public Page<Courier> findAll(PageRequest pageable) {
		Page<Courier> findAll = courierRepository.findAll(pageable);
		return findAll;
	}
    
	//进行快递员的按条件进行全部查出
	@Override
	public Page<Courier> findPageData(PageRequest pageable, Specification specification) {
		Page<Courier> page = courierRepository.findAll(specification, pageable);
		return page;
	}

	// 将快递员进行逻辑删除
	@Override
	public void delCourier(String[] array) {
		for (String strid : array) {
			Integer id = Integer.valueOf(strid);
			courierRepository.delCourier(id);
		}

	}

	@Override
	public List<Courier> NoAssociationCouriers() {
		Specification<Courier> specification = new Specification<Courier>() {
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				Predicate p = cb.isEmpty(root.get("fixedAreas"));
				return p;
			}
		};
		return courierRepository.findAll(specification);
	}

	@Override
	public Courier findOne(Integer id) {
		return courierRepository.findOne(id);
	}
	

}
