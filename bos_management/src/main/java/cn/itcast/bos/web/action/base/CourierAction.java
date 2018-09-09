package cn.itcast.bos.web.action.base;

import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;

/** 
* @author  songzch 
* @date 创建时间：2018年8月29日 下午9:04:40  
* @parameter  
* @return  
*/
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("json-default")
public class CourierAction extends ActionSupport implements ModelDriven<Courier> {
	
	
	private Courier courier = new Courier();

	@Override
	public Courier getModel() {
		return courier;
	}
	
	@Autowired
	private CourierService courierService;
	
	
	
	private int page;
	
	private int rows;
	
	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
   
	private String ids;
	

	public void setIds(String ids) {
		this.ids = ids;
	}

	@Action(value="courier_save",results ={@Result(name="success",location="./pages/base/courier.html",type="redirect")}) 
	public String save(){
		
		courierService.save(courier);
		
		return SUCCESS;
	}
	
	
	@Action(value = "courier_pageQuery",results ={@Result(name="success",type="json")})
	public String pageQuery(){
		
		PageRequest pageable = new PageRequest(page-1,rows);
		
		Specification<Courier> specification = new Specification<Courier>() {

			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				
				ArrayList<Predicate> arrayList = new ArrayList<Predicate>();
				//快递员的编号
				if(StringUtils.isNotBlank(courier.getCourierNum())){
					Predicate p1 = cb.equal(root.get("courierNum").as(String.class), courier.getCourierNum());
					arrayList.add(p1);
				}
				if(StringUtils.isNotBlank(courier.getCompany())){
					Predicate p2 = cb.like(root.get("company").as(String.class), "%"+courier.getCompany()+"%");
					arrayList.add(p2);
				}
				if(StringUtils.isNotBlank(courier.getType())){
					Predicate p3 = cb.equal(root.get("type").as(String.class), courier.getType());
					arrayList.add(p3);
				}
				
				Join<Object, Object> standardJoin = root.join("standard",JoinType.INNER);
				
				if(courier.getStandard() != null  && StringUtils.isNotBlank(courier.getStandard().getName())){
					Predicate p4 = cb.like(standardJoin.get("name").as(String.class), courier.getStandard().getName());
					arrayList.add(p4);
				}
				
				
				return cb.and(arrayList.toArray(new Predicate[0]));
			}
			
		};
		Page<Courier> pageData = courierService.findPageData(pageable, specification);
		//Page<Courier> pageData= courierService.findAll(pageable);
		
		HashMap<String, Object> map = new HashMap<String,Object>();
		
		map.put("total", pageData.getTotalElements());
		map.put("rows", pageData.getContent());
		
		ActionContext.getContext().getValueStack().push(map);
		
		
		return SUCCESS;
		
	}
	
	
	@Action(value="courier_delBatch",results={@Result(location="./pages/base/courier.html",type="redirect",name="success")})
	public String delCourier(){
		
		String[] split = ids.split(",");
		courierService.delCourier(split);
		
		
		return SUCCESS;
	}
	
	




}
