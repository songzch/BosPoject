package cn.itcast.bos.web.action.common;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;

import org.springframework.data.domain.Page;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.ActionContext;

/** 
* @author  songzch 
 * @param <T>
 * @param <T>
* @date 创建时间：2018年9月7日 上午10:35:34  
* @parameter  
* @return  
*/
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	protected T model;

	public T getModel() {
		return model;
	}
	
	//构造方法对属性进行赋值，初始化属性值
	public BaseAction(){
		
		//获取父类的泛型参数类型
		Type genericSuperclass = this.getClass().getGenericSuperclass();
		
		ParameterizedType type = (ParameterizedType) genericSuperclass;
		
		Type[] actualTypeArguments = type.getActualTypeArguments();
		 Class<T>  clazz1=  (Class<T>) actualTypeArguments[0];
		 try {
			 model = clazz1.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	protected int page;
	
	protected int rows;

	public void setPage(int page) {
		this.page = page;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	public void pushPageDataToValueStack(Page<T> page){
		
		HashMap<String, Object> hashMap = new HashMap<String,Object>();
		hashMap.put("total", page.getTotalElements());
		hashMap.put("rows", page.getContent());
		ActionContext.getContext().getValueStack().push(hashMap);
	}
	
	
	
	
	

}
