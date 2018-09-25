package cn.itcast.bos.web.action.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.service.base.AreaService;
import cn.itcast.bos.utils.PinYin4jUtils;
import cn.itcast.bos.web.action.common.BaseAction;

/** 
* @author  songzch 
* @date 创建时间：2018年9月6日 下午3:39:35  
* @parameter  
* @return  
*/

@ParentPackage("json-default")
@Namespace("/")
@Scope("prototype")
@Controller
public class AreaAction extends BaseAction<Area>{
	
	
	private File file2;
	
	public void setFile2(File file2) {
		this.file2 = file2;
	}
	@Autowired
	private AreaService areaService;
	
	@Action(value="area_batchImport",results={@Result(type="redirect",name="success",location="./pages/base/area.html")})
	public String batchImport() throws FileNotFoundException, IOException{
		ArrayList<Area> list = new ArrayList<Area>();
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(file2));
		XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
		for (Row row : sheet) {
			if(row.getRowNum()==0){
				continue;
			}
			if(row.getCell(0)==null || StringUtils.isBlank(row.getCell(0).getStringCellValue())){
				//如果第一列出现空列时候，既不在向数据库填写数据
				break;
			}
            Area area = new Area();
            if (row.getCell(0)!= null){
            	row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                area.setId(row.getCell(0).getStringCellValue());
            }
            area.setProvince(row.getCell(1).getStringCellValue());
            area.setCity(row.getCell(2).getStringCellValue());
            area.setDistrict(row.getCell(3).getStringCellValue());
            if(row.getCell(4)!= null){
            	row.getCell(4).setCellType(Cell.CELL_TYPE_STRING);
                area.setPostcode(row.getCell(4).getStringCellValue());
            }
            String province = area.getProvince();
            String city = area.getCity();
            String district = area.getDistrict();
            province = province.substring(0, province.length()-1);
            city = city.substring(0, city.length()-1);
            district = district.substring(0, district.length()-1);
            String[] array = PinYin4jUtils.getHeadByString(province+city+district);
            StringBuffer sb = new StringBuffer();
            for (String str : array) {
            	sb.append(str);
			}
            String shortCode = sb.toString();
            area.setShortcode(shortCode);
            String cityCode = PinYin4jUtils.hanziToPinyin(city, "");
            area.setCitycode(cityCode);
            list.add(area);
		}
		areaService.save(list);
		return SUCCESS;
	}
	
	@Action(value="area_pageQuery",results={@Result(name="success",type="json")})
	public String findAll(){
		PageRequest pageable = new PageRequest(page-1, rows);
		Specification<Area> specification = new Specification<Area>() {
			ArrayList<Predicate> arrayList = new ArrayList<Predicate>();
			@Override
			public Predicate toPredicate(Root<Area> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				if(StringUtils.isNotBlank(model.getProvince())){
					Predicate p1 = cb.like(root.get("province").as(String.class), "%"+model.getProvince()+"%");
					arrayList.add(p1);
				}
				if(StringUtils.isNotBlank(model.getCity())){
					Predicate p2 = cb.like(root.get("city").as(String.class), "%"+model.getCity()+"%");
					arrayList.add(p2);
				}
				if(StringUtils.isNotBlank(model.getDistrict())){
					Predicate p3 = cb.like(root.get("district").as(String.class), "%"+model.getDistrict()+"%");
					arrayList.add(p3);
				}
				return cb.and(arrayList.toArray(new Predicate[0]));
			}
		};
		Page<Area> page = areaService.findAll(pageable,specification);
		//将查询出来的page压栈
        pushPageDataToValueStack(page);
		return SUCCESS;
	}
}
