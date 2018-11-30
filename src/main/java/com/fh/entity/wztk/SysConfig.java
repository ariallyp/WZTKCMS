package com.fh.entity.wztk;

/**
 * @author Arial
 *
 */
public class SysConfig{

	//private String id;
	private String field;
	private String value;
	private String remark;
	
	
	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
	
	public SysConfig(String field, String value) {
		super();
		this.field = field;
		this.value = value;
	}

	
	public SysConfig(String field, String value, String remark) {
		super();
		this.field = field;
		this.value = value;
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "SysConfig [field=" + field + ", value=" + value + "]";
	}





}
