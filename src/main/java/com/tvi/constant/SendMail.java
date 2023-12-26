package com.tvi.constant;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SendMail {
	public static String sendMail(String mailFrom, String to,String cc, String bcc, String subject, String msg,String[] attachFiles) throws UnsupportedEncodingException{
	    String host = "smtp.gmail.com";//or IP address  
	    String returnString = null;
	    //Get the session object  
	    Properties properties = System.getProperties();  
	    properties.setProperty("mail.smtp.host", host);  
	    properties.setProperty("mail.smtp.socketFactory.port", "465");
	    properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    properties.setProperty("mail.smtp.auth", "true");
	    properties.setProperty("mail.smtp.port", "465");
	    Session session = Session.getDefaultInstance(properties,    new javax.mail.Authenticator() {    
            protected PasswordAuthentication getPasswordAuthentication() {    
            	return new PasswordAuthentication(Constant.MAIL_SEND_FROM,Constant.MAIL_SEND_PWD);  
            }    
	     });    
	  
	     //compose the message  
	     try{  
	         MimeMessage message = new MimeMessage(session);  
	         Address address = new InternetAddress(Constant.MAIL_SEND_FROM, mailFrom);
	         message.setFrom(address);
	         InternetAddress[] toAddressArray = InternetAddress.parse(to);
	         message.addRecipients(Message.RecipientType.TO,toAddressArray);
	         InternetAddress[] ccAddressArray = InternetAddress.parse(cc);
	         message.setRecipients(Message.RecipientType.CC, ccAddressArray);
	         InternetAddress[] bccAddressArray = InternetAddress.parse(bcc);
	         message.setRecipients(Message.RecipientType.BCC, bccAddressArray);
	         message.setSubject(subject);  
	         
	         // creates message part
	         MimeBodyPart messageBodyPart = new MimeBodyPart();
	         messageBodyPart.setContent(msg, "text/html");
	  
	         // creates multi-part
	         Multipart multipart = new MimeMultipart();
	         multipart.addBodyPart(messageBodyPart);
	         
	         if (attachFiles != null && attachFiles.length > 0) {
	             for (String filePath : attachFiles) {
	                 MimeBodyPart attachPart = new MimeBodyPart();
	  
	                 try {
	                     attachPart.attachFile(filePath);
	                 } catch (IOException ex) {
	                     ex.printStackTrace();
	                 }
	  
	                 multipart.addBodyPart(attachPart);
	             }
	         }
	  
	         // sets the multi-part as e-mail's content
	         message.setContent(multipart);
	         
	         // Send message  
	         Transport.send(message);  
	         returnString = "message sent successfully....";
	         //System.out.println(returnString);
	      }catch (MessagingException mex) {
	    	  mex.printStackTrace();
	    }
	     
	     return returnString;
	}
}
