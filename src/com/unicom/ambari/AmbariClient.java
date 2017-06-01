package com.unicom.ambari;

import java.util.ArrayList;
import java.util.List;

import com.unicom.ambari.info.DiskInfo;
import com.unicom.basic.utils.EmailUtil;
import com.unicom.basic.utils.PropertiesUtil;

public class AmbariClient {
	
	public static void main(String[] args) {
//		
		EmailUtil email = new EmailUtil();
		AmbariCmd cmd = new AmbariCmd();
		cmd.getAuth();
		try {
			if(cmd.getAllContents4Mail()){
				List<String> contentsList = cmd.getContentsList(); 
				String contents = "";
				for(int index = 0 ; index < contentsList.size() ; index++){
					contents += contentsList.get(index);
				}
				email.sendEmail(contents);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("End");
//		System.out.println(PropertiesUtil.getValue("cluster.name"));
	}
	
}
