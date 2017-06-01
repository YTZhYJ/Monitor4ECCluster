package com.unicom.basic.objecs;

public class AmbariObject {

	//权限设置
	public static String HOSTIP="ambari.host";
	public static String PORT="ambari.host.port";
	public static String URL="service.url";
	
	
	public static String USERNAME="ambari.username";
	public static String PASSWORD="ambari.password";
	
	//集群名称 GET /clusters/:name
	public static String KAFKA_CLUSTER_NAME = "kafka_hosts";
	public static String STORM_CLUSTER_NAME = "storm_hosts";
	
	//设置
	public static String CLUSTER_NAME="cluster.name";
	public static String CLUSTER_HOST_NAME = "cluster.host.name";
	public static String SERVICE_NAME="service.name";
	
	//资源请求返回值判断
	public static int OK =200;
	public static int ERROR = 400;
	public static int UNAUTHORIZED = 401;
	public static int FORBIDDEN = 403;
	public static int NOTFOUNT = 404;
	public static int SERVERERROR = 500;
	
	
}
