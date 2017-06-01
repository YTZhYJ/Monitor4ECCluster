package com.unicom.basic.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.unicom.basic.objecs.MailObject;


/**
 * 发送邮件工具包
 * 
 * @author ZhYJ
 *
 */
public class EmailUtil {

	Properties props ;
	Session session;
	String name;//用户名
	String password;//
	InternetAddress from ;
	InternetAddress to ;
	InternetAddress copy;
	Message message ;
	
	public EmailUtil () {
		super();
		this.initConfig();
		this.initValue();
		
	}


	/**
	 * 初始化Email配置信息
	 */
	public void initConfig(){
		props = new Properties();
		props.put(MailObject.MAIL_HOST, PropertiesUtil.getValue(MailObject.MAIL_HOST));
		props.put(MailObject.MAIL_PROTOCOL, PropertiesUtil.getValue(MailObject.MAIL_PROTOCOL));
		props.put(MailObject.MAIL_AUTH, PropertiesUtil.getValue(MailObject.MAIL_AUTH));
		
	}
	
	public void initValue(){
		try {
			this.from = new InternetAddress(PropertiesUtil.getValue(MailObject.MAIL_FROM));
			this.to = new InternetAddress (PropertiesUtil.getValue(MailObject.MAIL_TO));
			this.copy = new InternetAddress (PropertiesUtil.getValue(MailObject.MAIL_COPY));
			this.name = PropertiesUtil.getValue(MailObject.MAIL_USERNAME);
			this.password = PropertiesUtil.getValue(MailObject.MAIL_PASSWORD);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void sendEmail(String content){
		//126
		session = Session.getInstance(props,new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication(){
				return new PasswordAuthentication(name,password);
			}
		});
		message = new MimeMessage(session);
		
		try {
			message.setFrom(from);
			from.setPersonal(MimeUtility.encodeText(name)); //发件人名
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to.toString()));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(copy.toString()));
			message.setSubject(MimeUtility.encodeText("Cluster Disk and Service Warning！")); //邮件标题  
	        message.setSentDate(new Date());   
	        MimeMultipart msgMultipart = new MimeMultipart("mixed");// 指定为混合关系   
	        message.setContent(msgMultipart);
	        MimeBodyPart htmlPart = new MimeBodyPart();   
	        htmlPart.setContent(   
                      "<body>"  
                              + "<div style='position: absolute; left: 30px; top: 30px;height: "  
                              + "100px;width: 1000px;' align='left'>"  
                              + "<font color='red'>"+content+"</font>" + "</div></body>",   
                      "text/html;charset=UTF-8");   
	        msgMultipart.addBodyPart(htmlPart); 
	        // 组装附件   
//	        MimeBodyPart file = new MimeBodyPart();   
//	        FileDataSource file_datasource = new FileDataSource("D:\\Log4JDemo06.rar");   
//	        DataHandler dh = new DataHandler(file_datasource);   
//	        file.setDataHandler(dh);   
	        // 附件区别内嵌内容的一个特点是有文件名，为防止中文乱码要编码   
//	        file.setFileName(MimeUtility.encodeText(dh.getName()));   
//	        msgMultipart.addBodyPart(file);        
//	        message.saveChanges();
	        //发送邮件的过程:
	        Transport transport = session.getTransport( PropertiesUtil.getValue(MailObject.MAIL_PROTOCOL));   //创建连接
	        transport.connect( PropertiesUtil.getValue(MailObject.MAIL_HOST), name, password); //连接服务器   服务名，端口，发送邮件用户名 （不要@后面），密码
	        transport.sendMessage(message, message.getAllRecipients());  //发送信息 

	        transport.close();   //关闭
	        System.out.println("发送完毕");
	        
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}


}
