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
 * ���������ȡJSON����
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
     * ��ȡ���з�������
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
            //  log.info("���ص�json���ݣ�" + json);
            //�жϷ���ֵ�Ƿ�Ϊ200
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
	 * ������Ȩ
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
     * �鿴ָ���������Ƶķ���״̬
     * eg:INIT��INSTALLING��INSTALL_FAILED��INSTALLED,STARTING��STARTED��
     * STOPPING��UNINSTALLING��UNINSTALLED��WIPING_OUT��UPGRADING��MAINTENANCE��UNKNOWN
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
     * ��ȡ��Ⱥ���з�����������Ϣ
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
//            String json = method.getResponseBodyAsString();//�о��棬��������ʱ�򱨴�
            
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
     * �ж��Ƿ���Ҫ���ʹ��̾�����Ϣ��������Ҫ�������Ϣ��ӵ��ʼ�����
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
					contents = clusterName+"��Ⱥ"+hostName+"������"+diskInfoList.get(i).toString();
					//������һ�����޵Ĵ���ʱ�򼴱������洢��contentsList�У���Ϊ�ʼ�����
					contentsList.add(contents);//�洢
					flag = true;
				}
    		} catch (ParseException e) {
				e.printStackTrace();
			}
    	}
    	return flag;
    }
    /**
     * ���ĳ��Ⱥ����״̬
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
				contents = clusterName+"��Ⱥ��"+serviceName+"����״̬�쳣��"+status+"<br><br>";
				contentsList.add(contents);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return flag;
    }
    
    
    /**
     * �������д������ʼ�����Ϣ
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
		for(int clusterNum = 0 ; clusterNum < clusterName.size() ; clusterNum++){//��Ⱥ
			for(int serviceNum = 0 ; serviceNum < serviceName.size() ; serviceNum ++){//����
				flag = flag || this.isServiceNeedAlert(clusterName.get(clusterNum), serviceName.get(serviceNum));
			}
			for(int hostNum = 0 ; hostNum < hostName.size() ; hostNum++){//����
				flag = flag|| this.isDiskNeedAlert(clusterName.get(clusterNum), hostName.get(hostNum));
			}
		}
		return flag;
    }



	public List<String> getContentsList() {
		return contentsList;
	}

}
