package com.arabsoft.mailing.service;

import org.springframework.stereotype.Service;

import com.arabsoft.mailing.dao.EmailSenderDao;
import com.arabsoft.mailing.entities.EmailRequest;
import com.arabsoft.mailing.entities.EmailResponse;
import com.arabsoft.mailing.entities.EmailSender;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletContext;

import java.io.File;
import java.io.StringWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Service
public class EmailRequestService {
	@Autowired
	private EmailSenderDao emailSenderDao;
	
	@Autowired
	private VelocityEngine velocityEngine;
	
	
	
	   public EmailResponse sendEmail(EmailRequest emailRequest)
	    {
		   EmailResponse emailResponse = new EmailResponse();
	        //boolean foo = false;
	        
	        try {
	        	Session session = getSession(emailRequest);
	            MimeMessage msg = new MimeMessage(session); 
	            //msg.setFrom(new InternetAddress(emailRequest.getFrom()));
	            //msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRequest.getTo()));
	            msg.addRecipients(Message.RecipientType.TO, emailRequest.getTo());
	            msg.addRecipients(Message.RecipientType.CC, emailRequest.getTo_cc());
	            msg.addRecipients(Message.RecipientType.BCC, emailRequest.getTo_bcc());
	            msg.setSubject(emailRequest.getSubject());
	            msg.setText(emailRequest.getMessage());
	            Transport.send(msg);
	            System.out.println("Email Sent Wtih Attachment Successfully...");
	            //emailResponse.setCode("MAIL-000");
	            emailResponse.setCode("0");
	            emailResponse.setMessage("Message envoyé avec succés.");
	            System.out.println("Email Sent Wtih Attachment Successfully...");
	        }
	        catch(AddressException e)
	        {
	        	//emailResponse.setCode("MAIL-001");
	        	emailResponse.setCode("1");
	            emailResponse.setMessage("Erreur d'envoi. Problème de formatage d'une des adresses emails");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(AuthenticationFailedException e)
	        {
	        	//emailResponse.setCode("MAIL-002");
	        	emailResponse.setCode("2");
	            emailResponse.setMessage("Erreur d'envoi. Problème d'authentification");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(SendFailedException e)
	        {
	        	//emailResponse.setCode("MAIL-003");
	        	emailResponse.setCode("3");
	            emailResponse.setMessage("Erreur d'envoi à une ou plusieurs adresses emails");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(NullPointerException e){
	        	//emailResponse.setCode("MAIL-004");
	        	emailResponse.setCode("4");
	            emailResponse.setMessage("Erreur d'envoi. Impossible de récupérer les informations depuis l'adresse émettrice ");
	            System.out.println("EmailService File Error"+ e.getLocalizedMessage() );
	            e.printStackTrace();
	        }
	        catch(Exception e){
	        	//emailResponse.setCode("MAIL-100");
	        	emailResponse.setCode("6");
	            emailResponse.setMessage("Erreur technique lors de l'envoi");
	            System.out.println("EmailService File Error"+ e);
	         
	        }
	        System.out.println(emailResponse.toString());
	        return emailResponse;
	    }

	private Session getSession(EmailRequest emailRequest)  throws Exception{
		EmailSender emailSender  = emailSenderDao.findByAdress();
		System.out.println(emailSender.getEmailAdress());
		Properties properties = new Properties();
		properties.put("mail.username", emailSender.getEmailAdress());

		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", emailSender.getStarttls());
		properties.put("mail.smtp.host", emailSender.getHostName());
		properties.put("mail.smtp.port", emailSender.getPort().toString());
		
		Session session = Session.getInstance(properties, new Authenticator()
		{
		    protected PasswordAuthentication getPasswordAuthentication(){
		        return new PasswordAuthentication(emailSender.getEmailAdress(), emailSender.getPwd());
		    }
		});
		return session;
	}

	    public EmailResponse sendWithAttachment(EmailRequest emailRequest)
	    {
	    	EmailResponse emailResponse = new EmailResponse();
	        
	        try {
	        	Session session = getSession(emailRequest);
	        	
	            MimeMessage msg = new MimeMessage(session); 
	            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(msg,true);
	            mimeMessageHelper.setFrom(new InternetAddress(emailRequest.getFrom()));	            
	            //msg.setFrom(new InternetAddress(emailRequest.getFrom()));
	            
	            
	            
	            

	            
	            
	            Map model = new HashMap<>();
	            model.put("subject", emailRequest.getSubject());
	            model.put("message", emailRequest.getMessage());
	            
	            VelocityContext velocityContext = new VelocityContext(model);
				//velocityContext.put("user", emailRequest.getFrom());
				StringWriter stringWriter = new StringWriter();
				velocityEngine.mergeTemplate("Template/email.vm", "UTF-8", velocityContext, stringWriter);
	          
				//  msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRequest.getTo()));
	            msg.addRecipients(Message.RecipientType.TO, emailRequest.getTo());
	            //mimeMessageHelper.setTo("mouhaned.djo@gmail.com");
	            msg.addRecipients(Message.RecipientType.CC, emailRequest.getTo_cc());
	            //mimeMessageHelper.setCc(emailRequest.getTo_cc());
	            msg.addRecipients(Message.RecipientType.BCC, emailRequest.getTo_bcc());
	            //mimeMessageHelper.setBcc(emailRequest.getTo_bcc());
	            //msg.setSubject(emailRequest.getSubject());	            
	            String path = emailRequest.getPath_report();//"C:\\Users\\Public\\Downloads\\css_tutorial.pdf";
	            System.out.println("Path report : "+emailRequest.getPath_report());
	            System.out.println("Path report : "+emailRequest.getPath_report());
	            System.out.println("Path report : "+emailRequest.getPath_report());
	            System.out.println("Path report : "+emailRequest.getPath_report());
	            System.out.println("Path report : "+emailRequest.getPath_report());

	            if (path != "") {
	            	MimeMultipart mimeMultipart = new MimeMultipart(); 
		            MimeBodyPart textMime = new MimeBodyPart();
	            	MimeBodyPart fileMime = new MimeBodyPart();
	            	File file = new File(path);
		            fileMime.attachFile(file);
		            mimeMultipart.addBodyPart(fileMime);
		            textMime.setText(emailRequest.getMessage());
		            mimeMultipart.addBodyPart(textMime);
		            msg.setContent(mimeMultipart);		            
	            } else {
	            	//msg.setText(stringWriter.toString());	
	            	mimeMessageHelper.addInline("asii", new ClassPathResource(
							"Template/asi.svg"));
	            	mimeMessageHelper.setText(stringWriter.toString(), true);
	            	
	            }
	            Transport.send(msg);
	            //mailSender.send(msg);
	            System.out.println("Email Sent With Attachment Successfully...");
	            //emailResponse.setCode("MAIL-000");
	            emailResponse.setCode("0");
	            emailResponse.setMessage("Message envoyé avec succés.");
	            System.out.println("Email Sent Wtih Attachment Successfully...");
	        }
	        catch(AddressException e)
	        {
	        	//emailResponse.setCode("MAIL-001");
	        	emailResponse.setCode("1");
	            emailResponse.setMessage("Erreur d'envoi. Problème de formatage d'une des adresses emails");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(AuthenticationFailedException e)
	        {
	        	//emailResponse.setCode("MAIL-002");
	        	emailResponse.setCode("2");
	            emailResponse.setMessage("Erreur d'envoi. Problème d'authentification");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(SendFailedException e)
	        {
	        	//emailResponse.setCode("MAIL-003");
	        	e.printStackTrace();
	        	emailResponse.setCode("3");
	            emailResponse.setMessage("Erreur d'envoi à une ou plusieurs adresses emails");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(NullPointerException e){
	        	//emailResponse.setCode("MAIL-004");
	        	emailResponse.setCode("4");
	            emailResponse.setMessage("Erreur d'envoi. Impossible de récupérer les informations depuis l'adresse émettrice ");
	            System.out.println("EmailService File Error"+ e);
	         
	        }
	        catch(Exception e){
	        	//emailResponse.setCode("MAIL-100");
	        	emailResponse.setCode("6");
	            emailResponse.setMessage("Erreur technique lors de l'envoi");
	            System.out.println("EmailService File Error"+ e);
	         
	        }
	        System.out.println(emailResponse.toString());
	        return emailResponse;
	    }

	    public EmailResponse sendHtmlTemplate(EmailRequest emailRequest)
	    {
	    	EmailResponse emailResponse = new EmailResponse();
	         
	        try {
	        	Session session = getSession(emailRequest); 
	            MimeMessage msg = new MimeMessage(session);
	            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
	            helper.setFrom(new InternetAddress(emailRequest.getFrom()));
	            //msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRequest.getTo()));
	            msg.addRecipients(Message.RecipientType.TO, emailRequest.getTo());
	            msg.addRecipients(Message.RecipientType.CC, emailRequest.getTo_cc());
	            msg.addRecipients(Message.RecipientType.BCC, emailRequest.getTo_bcc());
	            helper.setSubject(emailRequest.getSubject());
	            MimeMultipart mimeMultipart = new MimeMultipart();
	            MimeBodyPart messageBodyPart = new MimeBodyPart();
	            messageBodyPart.setContent(emailRequest.getMessage(),"text/html; charset=utf-8");
	            mimeMultipart.addBodyPart(messageBodyPart);
	            msg.setContent(mimeMultipart);
	            Transport.send(msg);
	            System.out.println("Email Sent With HTML Template Style Successfully...");
	            //emailResponse.setCode("MAIL-000");
	            emailResponse.setCode("0");
	            emailResponse.setMessage("Message envoyé avec succès.");
	            System.out.println("Email Sent Wtih Attachment Successfully...");
	        }
	        catch(AddressException e)
	        {
	        	//emailResponse.setCode("MAIL-001");
	        	emailResponse.setCode("1");
	            emailResponse.setMessage("Erreur d'envoi. Problème de formatage d'une des adresses emails");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(AuthenticationFailedException e)
	        {
	        	//emailResponse.setCode("MAIL-002");
	        	emailResponse.setCode("2");
	            emailResponse.setMessage("Erreur d'envoi. Problème d'authentification");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(SendFailedException e)
	        {
	        	//emailResponse.setCode("MAIL-003");
	        	emailResponse.setCode("3");
	            emailResponse.setMessage("Erreur d'envoi à une ou plusieurs adresses emails");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(NullPointerException e){
	        	//emailResponse.setCode("MAIL-004");
	        	emailResponse.setCode("4");
	            emailResponse.setMessage("Erreur d'envoi. Impossible de récupérer les informations depuis l'adresse émettrice ");
	            System.out.println("EmailService File Error"+ e.getMessage());
	         
	        }
	        catch(Exception e){
	        	//emailResponse.setCode("MAIL-100");
	        	emailResponse.setCode("6");
	            emailResponse.setMessage("Erreur technique lors de l'envoi");
	            System.out.println("EmailService File Error"+ e);
	        }
	        System.out.println(emailResponse.toString());
	        return emailResponse;
	    }

	    public EmailResponse sendEmailInlineImage(EmailRequest emailRequest)
	    {
	    	EmailResponse emailResponse = new EmailResponse();
	        try {
	        	Session session = getSession(emailRequest); 
	            MimeMessage msg = new MimeMessage(session); 
	            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
	            helper.setFrom(new InternetAddress(emailRequest.getFrom()));
	            //msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRequest.getTo()));
	            msg.addRecipients(Message.RecipientType.TO, emailRequest.getTo());
	            msg.addRecipients(Message.RecipientType.CC, emailRequest.getTo_cc());
	            msg.addRecipients(Message.RecipientType.BCC, emailRequest.getTo_bcc());
	            /*helper.setSubject(emailRequest.getSubject());
	            String path = "E:\\toystoys88.jpg";
	            MimeMultipart mimeMultipart = new MimeMultipart("related");
	            MimeBodyPart textMime = new MimeBodyPart();
	            MimeBodyPart messageBodyPart = new MimeBodyPart();
	            MimeBodyPart fileMime = new MimeBodyPart();
	            textMime.setText(emailRequest.getMessage());
	            String content =  "<br><b>Hi friends</b>,<br><i>look at this nice logo :)</i>"	            
	            + "<br><img src='cid:image52'/><br><b>Your Regards Onlyxcodes</b>";
	            messageBodyPart.setContent(content,"text/html; charset=utf-8");
	            File file = new File(path);
	            fileMime.attachFile(file);
	            fileMime.setDisposition(MimeBodyPart.INLINE);
	            helper.addInline("image52", file);
	            mimeMultipart.addBodyPart(textMime);
	            mimeMultipart.addBodyPart(messageBodyPart);
	            mimeMultipart.addBodyPart(fileMime);
	            msg.setContent(mimeMultipart);*/
	            MimeMultipart mimeMultipart = new MimeMultipart("related");	
	            String cid = "image52";
	            String message = emailRequest.getMessage().toString();
	            String path = emailRequest.getPath_report();
	            System.out.println(message);
	            MimeBodyPart textPart = new MimeBodyPart();
	            textPart.setText("<html><head>"
	            	      + "<title>This is not usually displayed</title>"
	            	      + "</head>\n"
	            	      + "<body style=\"margin: 0; padding: 0; background-color: white;\" marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" leftmargin=\"0\">"
	            	      + "<table width=\"600px\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"1\"><tr>\n"
	            	      + "<td align=\"center\" bgcolor=\"#FDFCFC\"><img src=\"cid:"
	            	      + cid
	            	      + "\" /></td></tr>" 
	            	      + "<tr>"
	            	      +"<td style=\"padding-top:10px;\" bgcolor=\"#FDFCFC\">"
	            	      +"<div align=\"left\" style=\"padding-left:10px;display:inline-block;\">"
	            	      +"<div style=\"display:inline-block;\"><h4>Subject : &nbsp;</h4></div>"
	            	      +"<div style=\"display:inline-block;color:#1E56A3;\"><h4>"
	            	      + emailRequest.getSubject().toString()
	            	      +".</h4></div>"
	            	      +"</div>"
	            	      +"<div align=\"left\" style=\"padding-left:10px;\"><h4>Bonjour,</h4></div>"
	            	      +"<div align=\"left\" style=\"padding-left:20px;padding-right:20px;\">"
	            	      +"<p>"
	            	      + emailRequest.getMessage().toString()
	            	      + "<p>"
	            	      +"</div>"
	            	      +"<div align=\"left\" style=\"padding-left:10px;\"><h4>Cordialement.</h4></div>"
	            	      +"</td>"
	            	      +"</tr>"	
	            	      +"<tr>"
	            	      +"<td bgcolor=\"#FDFCFC\">"
	            	      +"<div align=\"center\" style=\"padding-top:1px;padding-bottom:1px;color:#1E56A3;\">"
	            	      +"<p><b><h5>Arab Soft : Rue 8368 - Bloc A - Espace El Aziz Lot Ennassim-Montplaisir 1073 Tunis-Tunisie</h5></b></p>"
	            	      +"</div>"
	            	      +"</td>"
	            	      +"</tr>"
	            	      + "</table>\n" + "</body></html>", 
	            	      "US-ASCII", "html");
	            mimeMultipart.addBodyPart(textPart);

	            // Image part
	            MimeBodyPart imagePart = new MimeBodyPart();
	            imagePart.attachFile("/AS_MAIL_API/src/main/resources/Template/asiii.png");
	            imagePart.setContentID("<" + cid + ">");
	            imagePart.setDisposition(MimeBodyPart.INLINE);
	            mimeMultipart.addBodyPart(imagePart);
	            
	            if (path != "") {	            	
	            	MimeBodyPart fileMime = new MimeBodyPart();
	            	File file = new File(path);
		            fileMime.attachFile(file);
		            mimeMultipart.addBodyPart(fileMime);		            
	            }
	            
	            msg.setContent(mimeMultipart);
	            Transport.send(msg);
	            System.out.println("Email Sent With Inline Image Successfully...");
	            //emailResponse.setCode("MAIL-000");
	            emailResponse.setCode("0");
	            emailResponse.setMessage("Message envoyé avec succés.");
	            System.out.println("Email Sent Wtih Attachment Successfully...");
	        }
	        catch(AddressException e)
	        {
	        	//emailResponse.setCode("MAIL-001");
	        	emailResponse.setCode("1");
	            emailResponse.setMessage("Erreur d'envoi. Problème de formatage d'une des adresses emails");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(AuthenticationFailedException e)
	        {
	        	//emailResponse.setCode("MAIL-002");
	        	emailResponse.setCode("2");
	            emailResponse.setMessage("Erreur d'envoi. Problème d'authentification");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(SendFailedException e)
	        {
	        	//emailResponse.setCode("MAIL-003");
	        	emailResponse.setCode("3");
	            emailResponse.setMessage("Erreur d'envoi à une ou plusieurs adresses emails");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(NullPointerException e){
	        	//emailResponse.setCode("MAIL-004");
	        	emailResponse.setCode("4");
	            emailResponse.setMessage("Erreur d'envoi. Impossible de récupérer les informations depuis l'adresse émettrice ");
	            System.out.println("EmailService File Error"+ e);
	         
	        }
	        catch(Exception e){
	        	//emailResponse.setCode("MAIL-100");
	        	emailResponse.setCode("6");
	            emailResponse.setMessage("Erreur technique lors de l'envoi");
	            System.out.println("EmailService File Error"+ e);
	         
	        }
	        System.out.println(emailResponse.toString());
	        return emailResponse;
	    }
	    
	    public EmailResponse sendEmailCombined(EmailRequest emailRequest)
	    {
	    	EmailResponse emailResponse = new EmailResponse();
	        try {
	        	Session session = getSession(emailRequest); 
	            MimeMessage msg = new MimeMessage(session); 
	            MimeMessageHelper helper = new MimeMessageHelper(msg, true);
	        
	            //msg.addRecipient(Message.RecipientType.TO, new InternetAddress(emailRequest.getTo()));
	            msg.addRecipients(Message.RecipientType.TO, emailRequest.getTo());
	            msg.addRecipients(Message.RecipientType.CC, emailRequest.getTo_cc());
	            msg.addRecipients(Message.RecipientType.BCC, emailRequest.getTo_bcc());
	            msg.setSubject(emailRequest.getSubject(),"UTF-8");
	            /*helper.setSubject(emailRequest.getSubject());
	            String path = "E:\\toystoys88.jpg";
	            MimeMultipart mimeMultipart = new MimeMultipart("related");
	            MimeBodyPart textMime = new MimeBodyPart();
	            MimeBodyPart messageBodyPart = new MimeBodyPart();
	            MimeBodyPart fileMime = new MimeBodyPart();
	            textMime.setText(emailRequest.getMessage());
	            String content =  "<br><b>Hi friends</b>,<br><i>look at this nice logo :)</i>"	            
	            + "<br><img src='cid:image52'/><br><b>Your Regards Onlyxcodes</b>";
	            messageBodyPart.setContent(content,"text/html; charset=utf-8");
	            File file = new File(path);
	            fileMime.attachFile(file);
	            fileMime.setDisposition(MimeBodyPart.INLINE);
	            helper.addInline("image52", file);
	            mimeMultipart.addBodyPart(textMime);
	            mimeMultipart.addBodyPart(messageBodyPart);
	            mimeMultipart.addBodyPart(fileMime);
	            msg.setContent(mimeMultipart);*/
	            MimeMultipart mimeMultipart = new MimeMultipart("related");	
	            String cid = "image52";
	            String message = emailRequest.getMessage().toString();
	            String path = emailRequest.getPath_report();
	            System.out.println(message);
	            MimeBodyPart textPart = new MimeBodyPart();
	            textPart.setText("<html><head>"
	            	      + "<title>This is not usually displayed</title>"
	            	      + "</head>\n"
	            	      + "<body style=\"margin: 0; padding: 0; background-color: white;\" marginheight=\"0\" topmargin=\"0\" marginwidth=\"0\" leftmargin=\"0\">"
	            	      + "<table width=\"600px\" align=\"center\" cellspacing=\"0\" cellpadding=\"0\" border=\"1\"><tr>\n"
	            	      + "<td align=\"center\" bgcolor=\"#FDFCFC\"><img src=\"cid:"
	            	      + cid
	            	      + "\" /></td></tr>" 
	            	      + "<tr>"
	            	      +"<td style=\"padding-top:10px;\" bgcolor=\"#FDFCFC\">"
	            	      +"<div align=\"left\" style=\"padding-left:10px;display:inline-block;\">"
	            	      +"<div style=\"display:inline-block;\"><h4>Subject : &nbsp;</h4></div>"
	            	      +"<div style=\"display:inline-block;color:#1E56A3;\"><h4>"
	            	      + emailRequest.getSubject().toString()
	            	      +".</h4></div>"
	            	      +"</div>"
	            	      +"<div align=\"left\" style=\"padding-left:10px;\"><h4>Bonjour,</h4></div>"
	            	      +"<div align=\"left\" style=\"padding-left:20px;padding-right:20px;\">"
	            	      +"<p>"
	            	      + emailRequest.getMessage().toString()
	            	      + "<p>"
	            	      +"</div>"
	            	      +"<div align=\"left\" style=\"padding-left:10px;\"><h4>Cordialement.</h4></div>"
	            	      +"</td>"
	            	      +"</tr>"	
	            	      +"<tr>"
	            	      +"<td bgcolor=\"#FDFCFC\">"
	            	      +"<div align=\"center\" style=\"padding-top:1px;padding-bottom:1px;color:#1E56A3;\">"
	            	      +"<p><b><h5>Arab Soft : Rue 8368 - Bloc A - Espace El Aziz Lot Ennassim-Montplaisir 1073 Tunis-Tunisie</h5></b></p>"
	            	      +"</div>"
	            	      +"</td>"
	            	      +"</tr>"
	            	      + "</table>\n" + "</body></html>", 
	            	      "US-ASCII", "html");
	            mimeMultipart.addBodyPart(textPart);
	            EmailSender emailSender  = emailSenderDao.findByAdress();
	            // Image part
	            MimeBodyPart imagePart = new MimeBodyPart();
	            imagePart.attachFile(emailSender.getPathLogo().toString()); ///AS_MAIL_API/src/main/resources/Template/asiii.png + E:\\PARTAGE\\asiii.png
	            imagePart.setContentID("<" + cid + ">");
	            imagePart.setDisposition(MimeBodyPart.INLINE);
	            mimeMultipart.addBodyPart(imagePart);
	            
	            if (path != "") {	            	
	            	MimeBodyPart fileMime = new MimeBodyPart();
	            	File file = new File(path);
		            fileMime.attachFile(file);
		            mimeMultipart.addBodyPart(fileMime);		            
	            }
	            msg.setFrom(emailSender.getEmailAdress().toString());
	            msg.setContent(mimeMultipart);
	            Transport.send(msg);
	            System.out.println("Email Sent With Inline Image Successfully...");
	            //emailResponse.setCode("MAIL-000");
	            emailResponse.setCode("0");
	            emailResponse.setMessage("Message envoyé avec succés.");
	            System.out.println("Email Sent Wtih Attachment Successfully...");
	        }
	        catch(AddressException e)
	        {
	        	//emailResponse.setCode("MAIL-001");
	        	emailResponse.setCode("1");
	            emailResponse.setMessage("Erreur d'envoi. Problème de formatage d'une des adresses emails");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(AuthenticationFailedException e)
	        {
	        	//emailResponse.setCode("MAIL-002");
	        	emailResponse.setCode("2");
	            emailResponse.setMessage("Erreur d'envoi. Problème d'authentification");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(SendFailedException e)
	        {
	        	//emailResponse.setCode("MAIL-003");
	        	emailResponse.setCode("3");
	            emailResponse.setMessage("Erreur d'envoi à une ou plusieurs adresses emails");
	            System.out.println("EmailService File Error"+ e);
	        }
	        catch(NullPointerException e){
	        	//emailResponse.setCode("MAIL-004");
	        	emailResponse.setCode("4");
	            emailResponse.setMessage("Erreur d'envoi. Impossible de récupérer les informations depuis l'adresse émettrice ");
	            System.out.println("EmailService File Error"+ e);
	         
	        }
	        catch(Exception e){
	        	//emailResponse.setCode("MAIL-100");
	        	emailResponse.setCode("6");
	            emailResponse.setMessage("Erreur technique lors de l'envoi");
	            System.out.println("EmailService File Error"+ e);
	         
	        }
	        System.out.println(emailResponse.toString());
	        return emailResponse;
	    }
}
