package com.unicom.ambari;

import java.util.ArrayList;
import java.util.List;

import com.unicom.basic.objecs.AmbariObject;
import com.unicom.basic.utils.PropertiesUtil;

/**
 * ��ȡ�����ļ���Ambari��Ⱥ��������Ϣ
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
	 * ������Ⱥ���ƣ���","�ָ����list��ʽ����
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
	 * ���������ļ��е�������Ҫ��ص�ƽ̨��Ⱥ�����������ã�����list
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
	 * ���������ļ��е�������Ҫ��ص�ƽ̨���з������ã�����list
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
