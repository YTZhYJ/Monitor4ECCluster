package com.unicom.ambari;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.methods.GetMethod;

import com.google.gson.Gson;
import com.unicom.ambari.info.ClustJsonInfo;
import com.unicom.ambari.info.DiskInfo;
import com.unicom.ambari.info.DiskJsonInfo;
import com.unicom.ambari.info.HostJsonInfo;
import com.unicom.ambari.info.ServiceInfo;
import com.unicom.ambari.info.ServiceJsonInfo;
import com.unicom.basic.objecs.AmbariObject;
import com.unicom.basic.objecs.ClusterAlertObject;
import com.unicom.basic.objecs.ServiceStatusObject;
import com.unicom.basic.utils.PropertiesUtil;

/**
 * 发送命令获取JSON数据
 * 
 * @author ZhYJ
 *
 */
public class AmbariCmd {
	HttpClient client = null;
	Gson gson = null;
	public List<String> contentsList;
	
    public AmbariCmd() {
		super();
		client = new HttpClient();
		gson = new Gson();
		contentsList = new ArrayList<String>();
	}



	/**
     * 获取所有服务名称
     *
     * @param clusterName
     */
    public List<ClustJsonInfo> getAllClusterService(String clusterName) throws Exception {
        GetMethod method = new GetMethod(
        		PropertiesUtil.getValue(AmbariObject.URL) + "api/v1/clusters/" + clusterName + "/services");
        method.setDoAuthentication(true);
        try {
            int status = client.executeMethod(method);
            String json = method.getResponseBodyAsString();//Get the info
            System.out.println(json);
            //  log.info("返回的json数据：" + json);
            //判断返回值是否为200
            if (status == AmbariObject.OK) {
            	ServiceJsonInfo service = gson.fromJson(json, ServiceJsonInfo.class);
                return service.getItems();
            } else {
                throw new RuntimeException("Failed : HTTP error code : " + status);
            }
        } finally {
            method.releaseConnection();
        }
    }
    
	/**
	 * 进行授权
	 */
	public void getAuth(){
		String hostIP = PropertiesUtil.getValue(AmbariObject.HOSTIP);
        int hostPort = Integer.parseInt(PropertiesUtil.getValue(AmbariObject.PORT));
        String authName = PropertiesUtil.getValue(AmbariObject.USERNAME);
        String authPwd = PropertiesUtil.getValue(AmbariObject.PASSWORD);
        
        client.getState().setCredentials(
                new AuthScope(hostIP, hostPort, AuthScope.ANY_REALM),
                new UsernamePasswordCredentials(authName, authPwd));
        client.getParams().setAuthenticationPreemptive(true);
	}

	/**
     * 查看指定服务名称的服务状态
     * eg:INIT、INSTALLING、INSTALL_FAILED、INSTALLED,STARTING、STARTED、
     * STOPPING、UNINSTALLING、UNINSTALLED、WIPING_OUT、UPGRADING、MAINTENANCE、UNKNOWN
     *
     * @param clusterName
     * @param serviceName
     * @return
     */
    public String getServiceInfoByName(String clusterName, String serviceName) throws Exception {
    	GetMethod method = new GetMethod(
        		PropertiesUtil.getValue(AmbariObject.URL) + "api/v1/clusters/" + clusterName + "/services/" + serviceName);
//    			PropertiesUtil.getValue(AmbariObject.URL) + "api/v1/clusters");
//    			PropertiesUtil.getValue(AmbariObject.URL) + "api/v1/clusters/" + clusterName + "/hosts/zk2.hcbss");
        method.setDoAuthentication(true);
        try {
            int status = client.executeMethod(method);
            String json = method.getResponseBodyAsString();
            System.out.println(json);
            if (status == AmbariObject.OK) {
            	ClustJsonInfo service = gson.fromJson(json, ClustJsonInfo.class);
            	//
                return service.getServiceInfo().getState();
            } else {
                throw new RuntimeException("Failed : HTTP error code : " + status);
            }
        } finally {
            method.releaseConnection();
        }
    }

    /**
     * 获取集群所有服务器磁盘信息
     * GET api/v1/clusters/c1/hosts/host_name
     * 
     * @param clusterName
     * @param hostName
     * @return List<>
     */
    public List<DiskInfo> getAllClusterDiskAlert(String clusterName,String hostName){
    	GetMethod method = new GetMethod(
    			PropertiesUtil.getValue(AmbariObject.URL) + "api/v1/clusters/" + clusterName + "/hosts/"+hostName);
        method.setDoAuthentication(true);
        try {
            int status = client.executeMethod(method);
//            String json = method.getResponseBodyAsString();//有警告，大流量的时候报错
            
            InputStream resStream = method.getResponseBodyAsStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(resStream));
            StringBuffer resBuffer = new StringBuffer(); 
            String resTemp = "";
            while((resTemp = br.readLine()) != null){
            	resBuffer.append(resTemp); 
            }
            String json = resBuffer.toString();
            
            if (status == AmbariObject.OK) {
            	HostJsonInfo diskInfoList = gson.fromJson(json, HostJsonInfo.class);
            	return diskInfoList.getHosts().getDisk_info();
            } else {
                throw new RuntimeException("Failed : HTTP error code : " + status);
            }
        } catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            method.releaseConnection();
        }
    	return null;
    }
    
    /**
     * 判断是否需要发送磁盘警告信息，并将需要警告的信息添加到邮件内容
     * @param clusterName
     * @param hostName
     * @return
     */
    public boolean isDiskNeedAlert(String clusterName , String hostName){
    	boolean flag = false;
    	String contents = "";
    	List<DiskInfo> diskInfoList = this.getAllClusterDiskAlert(clusterName, hostName);
    	int len = diskInfoList.size();
    	NumberFormat nf = NumberFormat.getPercentInstance();
    	for(int i = 0 ; i < len ; i++){
    		try {
				double num = nf.parse(diskInfoList.get(i).getPercent()).doubleValue();//0.86
				if (num <= ClusterAlertObject.DISK_LIMIT){
					contents = clusterName+"集群"+hostName+"主机的"+diskInfoList.get(i).toString();
					//当发现一个超限的磁盘时候即报警，存储到contentsList中，作为邮件内容
					contentsList.add(contents);//存储
					flag = true;
				}
    		} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    	return flag;
    }
    /**
     * 检查某集群服务状态
     * @param clusterName
     * @param serviceName
     * @return
     */
    public boolean isServiceNeedAlert(String clusterName , String serviceName){
    	String contents = "";
    	boolean flag = false;
    	try {
			String status = this.getServiceInfoByName(clusterName, serviceName);
			if(!ServiceStatusObject.STARTED.equals(status)){
				flag = true;
				contents = clusterName+"集群的"+serviceName+"服务状态异常："+status+"<br><br>";
				contentsList.add(contents);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return flag;
    }
    
    
    /**
     * 处理所有待发送邮件的消息
     * @return
     */
    public boolean getAllContents4Mail(){
		AmbariConfig config = new AmbariConfig();
		List<String> clusterName = 
				config.getAllMonitorClusters(PropertiesUtil.getValue(AmbariObject.CLUSTER_NAME));
		List<String> hostName = 
				config.getAllMonitorHosts(PropertiesUtil.getValue(AmbariObject.CLUSTER_HOST_NAME));
		List<String> serviceName = 
				config.getAllMonitorService(PropertiesUtil.getValue(AmbariObject.SERVICE_NAME));
		boolean flag = false;
		for(int clusterNum = 0 ; clusterNum < clusterName.size() ; clusterNum++){//集群
			for(int serviceNum = 0 ; serviceNum < serviceName.size() ; serviceNum ++){//服务
				flag = flag || this.isServiceNeedAlert(clusterName.get(clusterNum), serviceName.get(serviceNum));
			}
			for(int hostNum = 0 ; hostNum < hostName.size() ; hostNum++){//主机
				flag = flag|| this.isDiskNeedAlert(clusterName.get(clusterNum), hostName.get(hostNum));
			}
		}
		return flag;
    }



	public List<String> getContentsList() {
		return contentsList;
	}

}
