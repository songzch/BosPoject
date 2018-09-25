package cn.itcast.crm.service;

import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import cn.itcast.crm.domain.Customer;

/** 
* @author  songzch 
* @date 创建时间：2018年9月10日 上午10:40:21  
* @parameter  
* @return  
*/
@Path("/")
public interface CustomerService {
	
	/**
	 * 查询所有未关联客户列表
	 * @return
	 */
	@Path("/noAssociationCustomers")
	@Produces({"application/xml","application/json"})
	@GET
	public List<Customer> findNoAssociationCustomers();
	
	
	/**
	 * 查询所有已经关联定区的的客户列表
	 * @param fixedAreaId
	 * @return
	 */
	@Path("/associationfixedareaCustomers/{fixedAreaId}")
	@Produces({"application/xml","application/json"})
	@GET
	public List<Customer> findHasAssociationFixedAreaCustomers(@PathParam("fixedAreaId") String fixedAreaId);
	
	
	@Path("/associationcustomerstofixedarea")
	@PUT
	public void associationCustomersToFixedArea(@QueryParam("customerIdStr") String customerIdStr,
			                                   @QueryParam("fixedAreaId") String FixedAreaId);
	
	
	
	
	

}
