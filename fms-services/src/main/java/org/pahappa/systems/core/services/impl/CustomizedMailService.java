package org.pahappa.systems.core.services.impl;

import org.pahappa.systems.core.services.EmailTemplateService;
import org.pahappa.systems.models.Requisition;
import org.pahappa.systems.models.RequisitionStatus;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class CustomizedMailService extends Thread{
	User user;
	String heading,  message;
	public CustomizedMailService(User user, String heading, String message){
		this.user = user;
		this.heading = heading;
		this.message = message;
	}

	@Override
	public void run() {
		try {
			sendMail(this.heading,"<p>Dear "+this.user.getFullName()+"</p><p>"+this.message+"</p>",
					new String[] { user.getEmailAddress() }, "pahappatest@gmail.com", "pahappa2020", "smtp.gmail.com",
					"587");
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	public static boolean sendMail(String subject, String emailContent, String[] emailsOfReceipients,
			final String addressFrom, final String fromPassword, String smtpHost, String smtpPort)
			throws AddressException, MessagingException {
		if (emailsOfReceipients != null && emailsOfReceipients.length > 0) {
			Properties props = new Properties();
			props.put("mail.smtp.host", smtpHost);
			props.put("mail.smtp.port", smtpPort);
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.ssl.trust", "*");
			Authenticator auth = new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(addressFrom, fromPassword);
				}
			};
			Session session = Session.getInstance(props, auth);
			MimeMessage message = new MimeMessage(session);
			message.setSubject(subject);
			message.setFrom(new InternetAddress(addressFrom));
			message.setContent(emailContent, "text/html");
			for (String recepient : emailsOfReceipients)
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			Transport.send(message);
			return true;
		}
		return false;
	}
}