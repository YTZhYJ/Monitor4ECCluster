package com.unicom.ambari.info;

import java.util.List;

/**
 * ����Ⱥ�ϵ����з���
 * 
 * @author ZhYJ
 *
 */
public class ServiceJsonInfo {
	/**
	 * ����JSON���ݸ�ʽ
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
	//����JSON���������ñ���
	private List<ClustJsonInfo> items;

	public List<ClustJsonInfo> getItems() {
		return items;
	}

	public void setItems(List<ClustJsonInfo> items) {
		this.items = items;
	}
	
	
}
