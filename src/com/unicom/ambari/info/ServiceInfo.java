package com.unicom.ambari.info;

import com.google.gson.Gson;

/**
 * 
 * @author ZhYJ
 *
 */
public class ServiceInfo {

	/**
	 * ClustJsonInfo的一部分
	 * {
      		"cluster_name" : "c1",
      		"service_name" : "HDFS",
      		"state" : "STARTED"      		
      	}
	 */
	
	private String cluster_name;
	private String service_name;
	private String state;
	public String getCluster_name() {
		return cluster_name;
	}
	public void setCluster_name(String cluster_name) {
		this.cluster_name = cluster_name;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
