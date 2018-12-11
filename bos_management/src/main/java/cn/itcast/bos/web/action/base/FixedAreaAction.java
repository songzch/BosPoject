package cn.itcast.bos.web.action.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.CourierService;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.bos.service.base.TakeTimeService;
import cn.itcast.bos.web.action.common.BaseAction;
import cn.itcast.crm.domain.Customer;

/** 
* @author  songzch 
* @date 创建时间：2018年9月7日 下午3:24:47  
* @parameter  
* @return  
*/
@Controller
@Scope("prototype")
@ParentPackage("json-default")
@Namespace("/")
public class FixedAreaAction extends BaseAction<FixedArea> {
	
	private final Logger log= Logger.getLogger(FixedAreaAction.class);

	@Autowired
	private FixedAreaService fixedAreaService;
	
	@Autowired
	private CourierService courierService;
	

	/*
	 * 实现定区的保存功能
	 */
	@Action(value="fixedArea_save",results={@Result(name="success",location="./pages/base/fixed_area.html",type="redirect")})
	public String save(){
		fixedAreaService.save(model);
		return SUCCESS;
	}
	
	/*
	 * 实现定区的查询功能	
	 */
	@Action(value="fixedArea_pageQuery",results={@Result(name="success",type="json")})
	public String findPageData(){
		PageRequest pageRequest = new PageRequest(page-1, rows);
		ArrayList<Predicate> arrayList = new ArrayList<Predicate>(); 
		Specification<FixedArea> specification = new Specification<FixedArea>() {
    
			@Override
			public Predicate toPredicate(Root<FixedArea> root, CriteriaQuery<?> query,CriteriaBuilder cb) {
				if(StringUtils.isNotBlank(model.getId())){
					Predicate p1 = cb.equal(root.get("id").as(String.class), model.getId());
					arrayList.add(p1);
				}
				if(StringUtils.isNotBlank(model.getCompany())){
					Predicate p2 = cb.like(root.get("company").as(String.class), "%"+model.getCompany()+"%");
					arrayList.add(p2);
				}
				return cb.and(arrayList.toArray(new Predicate[0]));
			}
		};
		Page<FixedArea> findPageData = fixedAreaService.findPageData(specification, pageRequest);
		pushPageDataToValueStack(findPageData);
		return SUCCESS;
	}
	
	
	@Action(value = "fixedArea_findNoAssociationCustomers", results = { @Result(name = "success", type = "json") })
	public String findNoAssociationCustomers() {
		Collection<? extends Customer> collection = WebClient.create("http://localhost:8082/services/customerService/noAssociationCustomers")
				.accept(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		
		ActionContext.getContext().getValueStack().push(collection);

		return SUCCESS;
	}
	              
	@Action(value="fixedArea_findHasAssociationFixedAreaCustomers",results={@Result(name="success",type="json")})
	public String  findHasAssociationFixedAreaCustomers(){
		Collection<? extends Customer> collection = WebClient.create("http://localhost:8082/services/customerService/associationfixedareaCustomers/"+model.getId())
				.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).getCollection(Customer.class);
		ActionContext.getContext().getValueStack().push(collection);
		return SUCCESS;
	}
	
	
	private String[] customerIds;

	public void setCustomerIds(String[] customerIds) {
		this.customerIds = customerIds;
	}
	
	/*
	 * 实现客户的与定区的关联
	 */
	@Action(value="fixedArea_associationCustomersToFixedArea",results={@Result(name="success",type="redirect",location="./pages/base/fixed_area.html")})
	public String associationCustomersToFixedArea() {
		//将客户的id值拼接,生成字符串，进行传递。
		String customerStrId = StringUtils.join(customerIds, ",");
		
		WebClient
				.create("http://localhost:8082/services/customerService/associationcustomerstofixedarea?customerIdStr="
						+ customerStrId + "&fixedAreaId=" + model.getId())
				.put(null);

		return SUCCESS;
	}
	
	@Action(value="courier_findnoassociation",results={@Result(name="success",type="json")})
	public String findNoAssociationCouriers(){
		List<Courier> list = courierService.NoAssociationCouriers();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;	
	}
	
	@Autowired
	private TakeTimeService takeTimeService;
	
	@Action(value="taketime_findAll",results={@Result(name="success",type="json")})
	public String findTakeTime(){
		List<TakeTime> list = takeTimeService.findAll();
		ActionContext.getContext().getValueStack().push(list);
		return SUCCESS;	
	}
	
	private Integer courierId;
	
	private Integer takeTimeId;
	
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}

	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}
    
	
	@Action(value="fixedArea_associationCourierToFixedArea",results={@Result(name="success",type="redirect",location="./pages/base/fixed_area.html")})
	public String associationCourierToFixedArea(){
		fixedAreaService.associationCourierToFixedArea(model.getId(),courierId,takeTimeId);
		
		return SUCCESS;	
	}
	
	
	
	
	
	
	
	
	
	

}
