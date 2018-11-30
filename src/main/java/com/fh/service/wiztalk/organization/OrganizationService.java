package com.fh.service.wiztalk.organization;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("organizationService")
public class OrganizationService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("OrganizationMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OrganizationMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("OrganizationMapper.edit", pd);
	}
	
	
	public void bathUpate(List<PageData> list)throws Exception{
		dao.batchUpdate("OrganizationMapper.bathUpate", list);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrganizationMapper.datalistPage", page);
	}
	
	/*
	*包含租户父类子类LIST
	*/
	public List<PageData> orgRentlistPage(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrganizationMapper.orgRentlistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OrganizationMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OrganizationMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OrganizationMapper.deleteAll", ArrayDATA_IDS);
	}
	

	public List checkParent(String orgID)throws Exception{
		
		return (List)dao.findForList("OrganizationMapper.checkParent", orgID);
	}

	public Object findByUName(PageData pd) throws Exception{
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("OrganizationMapper.findByUName", pd);
	}

	
	
	public PageData findTenantByPId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OrganizationMapper.findTenantByPId", pd);
	}
	
	public List listAllUsersByPId(PageData pd) throws Exception{
        return (List<PageData>) dao.findForList("OrganizationMapper.listAllUsersByPId", pd);
    }
	
}

