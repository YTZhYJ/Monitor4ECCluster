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
 * �����ʼ����߰�
 * 
 * @author ZhYJ
 *
 */
public class EmailUtil {

	Properties props ;
	Session session;
	String name;//�û���
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
	 * ��ʼ��Email������Ϣ
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
			from.setPersonal(MimeUtility.encodeText(name)); //��������
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to.toString()));
			message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(copy.toString()));
			message.setSubject(MimeUtility.encodeText("Cluster Disk and Service Warning��")); //�ʼ�����  
	        message.setSentDate(new Date());   
	        MimeMultipart msgMultipart = new MimeMultipart("mixed");// ָ��Ϊ��Ϲ�ϵ   
	        message.setContent(msgMultipart);
	        MimeBodyPart htmlPart = new MimeBodyPart();   
	        htmlPart.setContent(   
                      "<body>"  
                              + "<div style='position: absolute; left: 30px; top: 30px;height: "  
                              + "100px;width: 1000px;' align='left'>"  
                              + "<font color='red'>"+content+"</font>" + "</div></body>",   
                      "text/html;charset=UTF-8");   
	        msgMultipart.addBodyPart(htmlPart); 
	        // ��װ����   
//	        MimeBodyPart file = new MimeBodyPart();   
//	        FileDataSource file_datasource = new FileDataSource("D:\\Log4JDemo06.rar");   
//	        DataHandler dh = new DataHandler(file_datasource);   
//	        file.setDataHandler(dh);   
	        // ����������Ƕ���ݵ�һ���ص������ļ�����Ϊ��ֹ��������Ҫ����   
//	        file.setFileName(MimeUtility.encodeText(dh.getName()));   
//	        msgMultipart.addBodyPart(file);        
//	        message.saveChanges();
	        //�����ʼ��Ĺ���:
	        Transport transport = session.getTransport( PropertiesUtil.getValue(MailObject.MAIL_PROTOCOL));   //��������
	        transport.connect( PropertiesUtil.getValue(MailObject.MAIL_HOST), name, password); //���ӷ�����   ���������˿ڣ������ʼ��û��� ����Ҫ@���棩������
	        transport.sendMessage(message, message.getAllRecipients());  //������Ϣ 

	        transport.close();   //�ر�
	        System.out.println("�������");
	        
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}


}
