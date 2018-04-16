package com.fh.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VirtualDataGenerator {
	public static List getVirtualResult() {      
		  List dataList = new ArrayList();  
		    
		  HashMap dataRecord1 = new HashMap();  
		  dataRecord1.put("id", "1");  
		  dataRecord1.put("text", "安全与科技环保部");  
		  dataRecord1.put("parentId", "");  
		    
		  HashMap dataRecord2 = new HashMap();  
		  dataRecord2.put("id", "2");  
		  dataRecord2.put("text", "电器一部");  
		  dataRecord2.put("parentId", "1");  
		    
		  HashMap dataRecord3 = new HashMap();  
		  dataRecord3.put("id", "3");  
		  dataRecord3.put("text", "电器二部");  
		  dataRecord3.put("parentId", "1");  
		  /*      
		  HashMap dataRecord4 = new HashMap();  
		  dataRecord4.put("id", "113000");  
		  dataRecord4.put("text", "廊坊银行开发区支行");  
		  dataRecord4.put("parentId", "110000");  
		        
		  HashMap dataRecord5 = new HashMap();  
		  dataRecord5.put("id", "100000");  
		  dataRecord5.put("text", "廊坊银行总行");  
		  dataRecord5.put("parentId", "");  
		    
		  HashMap dataRecord6 = new HashMap();  
		  dataRecord6.put("id", "110000");  
		  dataRecord6.put("text", "廊坊分行");  
		  dataRecord6.put("parentId", "100000");  
		    
		  HashMap dataRecord7 = new HashMap();  
		  dataRecord7.put("id", "111000");  
		  dataRecord7.put("text", "廊坊银行金光道支行");  
		  dataRecord7.put("parentId", "110000");    */
		      
		  dataList.add(dataRecord1);  
		  dataList.add(dataRecord2);  
		  dataList.add(dataRecord3);  
		  /*dataList.add(dataRecord4);  
		  dataList.add(dataRecord5);  
		  dataList.add(dataRecord6);  
		  dataList.add(dataRecord7); */ 
		    
		  return dataList;  
		 }   
}
