package com.fh.entity.manager;

import com.fh.entity.Page;

public class ORG {
	private String ORG_ID;
	private String ORG_NAME;
	private String SHORT_NAME;
	private String PARENT_ID;
	private String LOCATION;
	private String SORT;
	private String CTIME;
	private String MTIME;
	private Rent rent;
	private Page page;
	private String RENT_ID;
	private Boolean ISPARENT;
	private String PARENT_NAME;
	
	
	
	
	
	
	public String getPARENT_NAME() {
		return PARENT_NAME;
	}


	public void setPARENT_NAME(String pARENT_NAME) {
		PARENT_NAME = pARENT_NAME;
	}


	public Boolean getISPARENT() {
		return ISPARENT;
	}


	public void setISPARENT(Boolean iSPARENT) {
		ISPARENT = iSPARENT;
	}


	public Rent getRent() {
		return rent;
	}
	
	
	public void setRent(Rent rent) {
		this.rent = rent;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	
	
	public String getORG_ID() {
		return ORG_ID;
	}
	public void setORG_ID(String oRG_ID) {
		ORG_ID = oRG_ID;
	}
	
	
	public String getORG_NAME() {
		return ORG_NAME;
	}


	public void setORG_NAME(String oRG_NAME) {
		ORG_NAME = oRG_NAME;
	}


	public String getSHORT_NAME() {
		return SHORT_NAME;
	}
	public void setSHORT_NAME(String sHORT_NAME) {
		SHORT_NAME = sHORT_NAME;
	}
	public String getPARENT_ID() {
		return PARENT_ID;
	}
	public void setPARENT_ID(String pARENT_ID) {
		PARENT_ID = pARENT_ID;
	}

	public String getRENT_ID() {
		return RENT_ID;
	}


	public void setRENT_ID(String rENT_ID) {
		RENT_ID = rENT_ID;
	}


	public String getLOCATION() {
		return LOCATION;
	}
	public void setLOCATION(String lOCATION) {
		LOCATION = lOCATION;
	}
	public String getSORT() {
		return SORT;
	}
	public void setSORT(String sORT) {
		SORT = sORT;
	}
	public String getCTIME() {
		return CTIME;
	}
	public void setCTIME(String cTIME) {
		CTIME = cTIME;
	}
	public String getMTIME() {
		return MTIME;
	}
	public void setMTIME(String mTIME) {
		MTIME = mTIME;
	}


	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("{id:\"");
		sb.append(ORG_ID);
		sb.append("\",name:\"");
		sb.append(ORG_NAME);
		sb.append("\",SHORT_NAME:\"");
		sb.append(SHORT_NAME);
		sb.append("\",pId:\"");
		sb.append(PARENT_ID);
		sb.append("\",isParent:\"");
		sb.append(ISPARENT);
		sb.append("\",LOCATION:\"");
		sb.append(LOCATION);
		sb.append("\",RENT_ID:\"");
		sb.append(RENT_ID);
		sb.append("\"}");
		
		return sb.toString();
	}
	
	
	
	
	
	
	

}
