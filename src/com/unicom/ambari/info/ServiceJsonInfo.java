package com.unicom.ambari.info;

import java.util.List;

/**
 * 处理集群上的所有服务
 * 
 * @author ZhYJ
 *
 */
public class ServiceJsonInfo {
	/**
	 * 返回JSON数据格式
	 * {
    	"href" : "http://your.ambari.server/api/v1/clusters/c1/services",
    	"items" : [
    		{
        		"href" : "http://your.ambari.server/api/v1/clusters/c1/services/NAGIOS",
        		"ServiceInfo" : {
          			"cluster_name" : "c1",
          			"service_name" : "NAGIOS"
          		}
        	},
        	{
        		"href" : "http://your.ambari.server/api/v1/clusters/c1/services/PIG",
        		"ServiceInfo" : {
        	  		"cluster_name" : "c1",
        	  		"service_name" : "PIG"
        	  	}	
        	}
        ]
    }    
	 */
	//根据JSON数据流设置变量
	private List<ClustJsonInfo> items;

	public List<ClustJsonInfo> getItems() {
		return items;
	}

	public void setItems(List<ClustJsonInfo> items) {
		this.items = items;
	}
	
	
}
