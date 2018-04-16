package com.fh.test;

public class Node {
	public String id;
	public String text;
	public String parentId;
	public Children children = new Children();
	
	 public String toString() { 
		 String result = "{"
		  + "id : '" + id + "'"
		  + ", text : '" + text + "'";
		  
		 if (children != null && children.getSize() != 0) {
		  result += ", children : " + children.toString();
		 } else {
		  result += ", leaf : true";
		 }
		   
		 return result + "}";
		 }
		  
		 // 兄弟节点横向排序
		 public void sortChildren() {
		 if (children != null && children.getSize() != 0) {
		  children.sortChildren();
		 }
		 }
		  
		 // 添加孩子节点
		 public void addChild(Node node) {
		 this.children.addChild(node);
		 }
		
	
	

}
