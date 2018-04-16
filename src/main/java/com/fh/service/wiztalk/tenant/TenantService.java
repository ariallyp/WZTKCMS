package com.fh.service.wiztalk.tenant;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("tenantService")
public class TenantService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("TenantMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("TenantMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("TenantMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("TenantMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("TenantMapper.listAll", pd);
	}
	
	   /*
	    *列表(全部)
	    */
	public List<PageData> listAllByTid(PageData pd)throws Exception{
	        return (List<PageData>)dao.findForList("TenantMapper.listAllByTid", pd);
	 }
	
	
	/*
	*列表租户名称，用于下拉框列表选择。
	*/
	public List listAllappERRents() throws Exception {
		return (List) dao.findForList("TenantMapper.listAllappERRents", null);
		
	}
	
	
	public List listRentByPID(PageData pd) throws Exception {
		return (List) dao.findForList("TenantMapper.listRentByPID", pd);
		
	}
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("TenantMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("TenantMapper.deleteAll", ArrayDATA_IDS);
	}

	public Object findByUName(PageData pd) throws Exception{
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("TenantMapper.findByUName", pd);
	}

	

	public List listAllOrgsByPId(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("TenantMapper.listAllOrgsByPId", pd);
	}

	public List listAllUsersByPId(PageData pd) throws Exception{
		return (List<PageData>) dao.findForList("TenantMapper.listAllUsersByPId", pd);
	}
	
	
}

