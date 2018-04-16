package com.fh.service.wiztalk.orguser;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("orguserService")
public class OrgUserService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("OrgUserMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("OrgUserMapper.delete", pd);
	}
	
	/*
	* 删除
	*/
	public void deleteByOrgId(PageData pd)throws Exception{
		dao.delete("OrgUserMapper.deleteByOrgId", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("OrgUserMapper.edit", pd);
	}
	/*
	* 修改
	*/
	public void editByUserID(PageData pd)throws Exception{
		dao.update("OrgUserMapper.editByUserID", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("OrgUserMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("OrgUserMapper.listAll", pd);
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("OrgUserMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("OrgUserMapper.deleteAll", ArrayDATA_IDS);
	}
	
}

