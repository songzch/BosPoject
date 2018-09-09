package cn.itcast.bos.web.action.base;

import java.util.ArrayList;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
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

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.bos.web.action.common.BaseAction;

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
	
	@Autowired
	private FixedAreaService fixedAreaService;
	
	/*
	 * 实现定区的保存功能
	 */
	@Action(value="fixedArea_save",results={@Result(name="success",location="./pages/base/fixed_area.html")})
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
	
	
	
	

}
