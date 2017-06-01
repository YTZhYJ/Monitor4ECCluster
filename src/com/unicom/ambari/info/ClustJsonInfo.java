package com.unicom.ambari.info;

import java.util.List;

import com.unicom.basic.objecs.ServiceStatusObject;

/**
 * 用于获取某个服务的状态信息
 * item -> ServiceInfo -> state
 * calss: ServiceJsonInfo -> ClustJsonInfo -> ServiceInfo
 * @author ZhYJ
 *
 */
public class ClustJsonInfo {
	/**
	 * href和ServiceInfo为ServiceJSONInfo的一部分
	 * JSON数据格式为：
	 * {
    	"href" : "http://your.ambari.server/api/v1/clusters/c1/services/HDFS",
    	"ServiceInfo" : {
      		"cluster_name" : "c1",
      		"service_name" : "HDFS",
      		"state" : "STARTED"      		
      	},
    	"components" : [
      		{
      			"href" : "http://your.ambari.server/api/v1/clusters/c1/services/HDFS/components/NAMENODE",
      			"ServiceComponentInfo" : {
        			"cluster_name" : "c1",
        			"component_name" : "NAMENODE",
        			"service_name" : "HDFS"
       			}
      		},
      		{
      			"href" : "http://your.ambari.server/api/v1/clusters/c1/services/HDFS/components/DATANODE",
      			"ServiceComponentInfo" : {
        			"cluster_name" : "c1",
        			"component_name" : "DATANODE",
        			"service_name" : "HDFS"
        		}
      		},
      		{
      			"href" : "http://your.ambari.server/api/v1/clusters/c1/services/HDFS/components/HDFS_CLIENT",
      			"ServiceComponentInfo" : {
        			"cluster_name" : "c1",
        			"component_name" : "HDFS_CLIENT",
        			"service_name" : "HDFS"
        		}
      		},
      		{
      			"href" : "http://your.ambari.server/api/v1/clusters/c1/services/HDFS/components/SECONDARY_NAMENODE",
     			"ServiceComponentInfo" : {
        			"cluster_name" : "c1",
        			"component_name" : "SECONDARY_NAMENODE",
        			"service_name" : "HDFS"
        		}
      		}
      	]
	 */
	private ServiceInfo ServiceInfo;

	public ServiceInfo getServiceInfo() {
		return ServiceInfo;
	}

	public void setServiceInfo(ServiceInfo serviceInfo) {
		ServiceInfo = serviceInfo;
	}
	

}
