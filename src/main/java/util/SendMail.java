package util;

import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.internet.MimeMultipart;
import web.mobile.xlsread.Xls_Reader;
import web.mobile.test.Constants;

public class SendMail {

	 public static void sendNotification() throws IOException {
			Xls_Reader suiteXLS = new Xls_Reader(Constants.projectBaseDir+"/src/main/java/web/mobile/xls/MasterSuite.xlsx");
			
			String EmailNotificationFlag = suiteXLS.getCellData("EnvDetails", "Email_Notification", 2);
			
			if (EmailNotificationFlag.trim().equalsIgnoreCase("Y"))
			{
			
	        final String username = suiteXLS.getCellData("EnvDetails", "Username", 2);
	        //final String password = suiteXLS.getCellData("EnvDetails", "Password", 2);
	        final String password = "Cardinals#2015";

	        Properties props = new Properties();
	        props.put("mail.smtp.auth", "true");
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", "outlook.office365.com");
	        props.put("mail.smtp.port", "587");

	        Session session = Session.getInstance(props,
	          new javax.mail.Authenticator() {
	            protected PasswordAuthentication getPasswordAuthentication() {
	                return new PasswordAuthentication(username, password);
	            }
	          });

	        try {

	        	// Create a default MimeMessage object.
	            Message message = new MimeMessage(session);
	            String Sender = suiteXLS.getCellData("EnvDetails", "Sender", 2);
	            String Recepient = suiteXLS.getCellData("EnvDetails", "Recepient", 2);
	            message.setFrom(new InternetAddress(Sender));
	            message.setRecipients(Message.RecipientType.TO,
	                InternetAddress.parse(Recepient));
	            
	            // Create the message part
	            MimeBodyPart messageBodyPart = new MimeBodyPart();
	            
	            // Create a multipar message
	            Multipart multipart = new MimeMultipart();
	            
	            
	         // Set text message part
	            multipart.addBodyPart(messageBodyPart);
	            
	            message.setSubject("Verizon Cloud Automation Test Status");
	            
	            
	            messageBodyPart.setContent("<h1>This is an automated email to notify all test cases have been executed and the test report and logs are ready to review</h1>","text/html");

	         // Set text message part
	            multipart.addBodyPart(messageBodyPart);
	            
	         // Attach execution report
	            messageBodyPart = new MimeBodyPart();
	            String filename = Constants.projectBaseDir+"/Report/VZCloudAndroidTestReport.zip";
	            DataSource source = new FileDataSource(filename);
	            messageBodyPart.setDataHandler(new DataHandler(source));
	            messageBodyPart.setFileName(filename);
	            multipart.addBodyPart(messageBodyPart);
	            
	         // Send the complete message parts
	            message.setContent(multipart);
	            
	            Transport.send(message);

	            System.out.println("Message sent successfully....");

	        } catch (MessagingException e) {
	            throw new RuntimeException(e);
	        }
	       
			}
			else   
				System.out.println("Email Notification is currently turned off");
	    } 
	 
	}