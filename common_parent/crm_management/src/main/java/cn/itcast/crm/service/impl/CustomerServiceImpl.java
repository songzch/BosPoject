package cn.itcast.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.dao.CustomerRepository;
import cn.itcast.crm.domain.Customer;
import cn.itcast.crm.service.CustomerService;

/** 
* @author  songzch 
* @date 创建时间：2018年9月10日 下午1:36:24  
* @parameter  
* @return  
*/
@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	private CustomerRepository customerRepository;
       
	@Override
	public List<Customer> findNoAssociationCustomers() {

		return customerRepository.findByFixedAreaIdIsNull();
	}

	@Override
	public List<Customer> findHasAssociationFixedAreaCustomers(String fixedAreaId) {
		return customerRepository.findByFixedAreaId(fixedAreaId);
	}
    //修改用户的定区，将用户绑定到定区中
	@Override
	public void associationCustomersToFixedArea(String customerIdStr, String FixedAreaId) {
		System.out.println("定区id值为:"+FixedAreaId);
		customerRepository.clearFixedArea(FixedAreaId);
		String[] array = customerIdStr.split(",");
		for (String customerId : array) {
			System.out.println("用户id值为:"+customerId);
			Integer id = Integer.parseInt(customerId);
			customerRepository.updateFixedArea(id, FixedAreaId);
		}

	}

}
