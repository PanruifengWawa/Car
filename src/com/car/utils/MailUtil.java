package com.car.utils;
import java.security.Security;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.car.enums.Parameters;

public class MailUtil {


    @SuppressWarnings("restriction")
	public static boolean send(String[] emailList,String subject,String content) {
    	try {  
//            Properties props = new Properties();  
//            props.put("username", Parameters.emailUserName);   
//            props.put("password", Parameters.emailCode);   
//            props.put("mail.transport.protocol", Parameters.emailProtocol );  
//            props.put("mail.smtp.host",Parameters.emailHost);  
//            props.put("mail.smtp.port", Parameters.emailPort);  
//
//            Session mailSession = Session.getDefaultInstance(props);  
//
//            Message msg = new MimeMessage(mailSession);     
//            msg.setFrom(new InternetAddress(Parameters.emailUserName));
//            for(String address : emailList) {
//            	msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(address)); 
//            }
//    
//            msg.setSubject(subject);   
//            msg.setContent(content,"text/html;charset=UTF-8");
//         
//            msg.saveChanges();  
//
//            Transport transport = mailSession.getTransport("smtp");  
//            transport.connect(props.getProperty("mail.smtp.host"), props  
//                    .getProperty("username"), props.getProperty("password"));   
//            transport.sendMessage(msg, msg.getAllRecipients());  
//            transport.close();  
    		
    		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            final String SSL_FACTORY = Parameters.SSL_FACTORY;
            // Get a Properties object
            Properties props = new Properties();
            props.setProperty("mail.smtp.host", Parameters.emailHost);
            props.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
            props.setProperty("mail.smtp.socketFactory.fallback", "false");
            props.setProperty("mail.smtp.port", Parameters.emailPort);
            props.setProperty("mail.smtp.socketFactory.port", Parameters.emailPort);
            props.put("mail.smtp.auth", "true");
            
            Session session = Session.getDefaultInstance(props, new Authenticator(){
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Parameters.emailUserName, Parameters.emailCode);
                }});
            Message msg = new MimeMessage(session);
           
            // 设置发件人和收件人
            msg.setFrom(new InternetAddress(Parameters.emailUserName));
            Address to[] = new InternetAddress[emailList.length];
            for(int i=0;i<emailList.length;i++){
                to[i] = new InternetAddress(emailList[i]);
            }
            // 多个收件人地址
            msg.setRecipients(Message.RecipientType.TO, to);
            msg.setSubject(subject); // 标题
            msg.setContent(content,"text/html;charset=UTF-8");
            Transport.send(msg);
            
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;
        }  
    	return true;
    }
}
