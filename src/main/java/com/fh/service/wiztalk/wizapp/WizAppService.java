package com.fh.service.wiztalk.wizapp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.util.PageData;


@Service("wizappService")
public class WizAppService {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/*
	* 新增
	*/
	public void save(PageData pd)throws Exception{
		dao.save("WizAppMapper.save", pd);
	}
	
	/*
	* 删除
	*/
	public void delete(PageData pd)throws Exception{
		dao.delete("WizAppMapper.delete", pd);
	}
	
	/*
	* 修改
	*/
	public void edit(PageData pd)throws Exception{
		dao.update("WizAppMapper.edit", pd);
	}
	
	/*
	*列表
	*/
	public List<PageData> list(Page page)throws Exception{
		return (List<PageData>)dao.findForList("WizAppMapper.datalistPage", page);
	}
	
	/*
	*列表(全部)
	*/
	public List<PageData> listAll(PageData pd)throws Exception{
		return (List<PageData>)dao.findForList("WizAppMapper.listAll", pd);
	}
	
	/*
	*列表租户名称，用于下拉框列表选择。
	*/
	public List listAllAPPS() throws Exception {
		return (List) dao.findForList("WizAppMapper.listAllAPPS", null);
		
	}
	
	/*
	* 通过id获取数据
	*/
	public PageData findById(PageData pd)throws Exception{
		return (PageData)dao.findForObject("WizAppMapper.findById", pd);
	}
	
	/*
	* 批量删除
	*/
	public void deleteAll(String[] ArrayDATA_IDS)throws Exception{
		dao.delete("WizAppMapper.deleteAll", ArrayDATA_IDS);
	}

	public Object findByUName(PageData pd) throws Exception{
		// TODO Auto-generated method stub
		return (PageData)dao.findForObject("WizAppMapper.findByUName", pd);
	}
	
}

