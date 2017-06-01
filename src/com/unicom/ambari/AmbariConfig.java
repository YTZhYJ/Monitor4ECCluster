package com.unicom.ambari;

import java.util.ArrayList;
import java.util.List;

import com.unicom.basic.objecs.AmbariObject;
import com.unicom.basic.utils.PropertiesUtil;

/**
 * 获取配置文件中Ambari集群的数据信息
 * 
 * @author ZhYJ
 *
 */
public class AmbariConfig {
	
	List<String> clusterName ;
	List<String> hostsName;
	List<String> serviceName;
		
	public AmbariConfig() {
		super();
		clusterName = new ArrayList<String>();
		hostsName = new ArrayList<String>();
		serviceName = new ArrayList<String>();
	}

	

	/**
	 * 解析集群名称，以","分割，并以list形式返回
	 * @param clustersName
	 * @return
	 */
	public List<String> getAllMonitorClusters(String clustersName){
		String[] clustersNameStr = clustersName.split(",");
		System.out.println(clustersNameStr.length);
		for(int i = 0 ; i < clustersNameStr.length ; i ++){
			clusterName.add(clustersNameStr[i]);
		}
		return clusterName;
	}
	
	
	/**
	 * 解析配置文件中的所有需要监控的平台集群主机名称配置，返回list
	 * @return
	 */
	public List<String> getAllMonitorHosts(String clusterHostsName){
		String[] hostsNameStr = clusterHostsName.split(",");
		for(int i = 0; i < hostsNameStr.length ; i++){
			hostsName.add(hostsNameStr[i]);
		}
		return hostsName;
	}
	/**
	 * 解析配置文件中的所有需要监控的平台运行服务配置，返回list
	 * @return
	 */
	public List<String> getAllMonitorService(String clusterServiceName){
		String[] serviceNameStr = clusterServiceName.split(",");
		for(int i = 0 ; i < serviceNameStr.length ; i++){
			serviceName.add(serviceNameStr[i]);
		}
		return serviceName;
	}

	public List<String> getClusterName() {
		return clusterName;
	}

	public List<String> getHostsName() {
		return hostsName;
	}

	public List<String> getServiceName() {
		return serviceName;
	}
	
}
