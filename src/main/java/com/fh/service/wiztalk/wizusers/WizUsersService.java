package com.fh.service.wiztalk.wizusers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("wizusersService")
public class WizUsersService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("WizUsersMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("WizUsersMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("WizUsersMapper.edit", pd);
	}
	
	/*
	* 删除
	*/
	public void deleteByUserId(PageData pd)throws Exception{
		dao.delete("AppAlowMapper.deleteByUserId", pd);
	}
	
	/*
	* 删除deleteByUserIdUU
	*/
	public void deleteByUserIdUU(PageData pd)throws Exception{
		dao.delete("AppAlowMapper.deleteByUserIdUU", pd);
	}
	
	/*
	* 删除deleteByUserIdAPPU
	*/
	public void deleteByUserIdAPPU(PageData pd)throws Exception{
		dao.delete("AppAlowMapper.deleteByUserIdAPPU", pd);
	}
	
	/*
	* 修改 权限
	*/
	public void editAppAlow(PageData pd)throws Exception{
		dao.update("AppAlowMapper.editAppAlow", pd);
	}
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WizUsersMapper.datalistPage", page);
	}
	
	
	/*
	*列表带org user
	*/
	public List<PageData> datalistPageByOrgUser(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WizUsersMapper.datalistPageByOrgUser", page);
	}
	
	/*
	*列表带org user app alow
	*/
	public List<PageData> datalistPageByAppAlow(Page page)throws Exception{
		return (List<PageData>)dao.findForList("AppAlowMapper.datalistPageByAppAlow", page);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findByAppAlowId(PageData pd)throws Exception{
		return (PageData)dao.findForObject("AppAlowMapper.findByAppAlowId", pd);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WizUsersMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WizUsersMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("WizUsersMapper.deleteAll", ArrayDATA_IDS);
	}

	public void batchUpdate(List objs) throws Exception {
		dao.batchUpdate("AppAlowMapper.batchUpdate", objs);
		
	}
	
	/*
	* 通过username获取数据
	*/
	public List<PageData> findByUName(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WizUsersMapper.findByUName", pd);
	}
	public List<PageData> findByUNameID(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WizUsersMapper.findByUNameID", pd);
	}
	
	/*
	* 通过email获取数据
	*/
	public List<PageData> findByUE(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WizUsersMapper.findByUE", pd);
	}
	
	/*
	* 通过查询是否存在
	*/
	public PageData findByUserApp(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WizUsersMapper.findByUserApp", pd);
	}
	
	/*
	* 新增用户应用
	*/
	public void saveUserApp(PageData pd)throws Exception{
		dao.save("WizUsersMapper.saveUserApp", pd);
	}
	
}

