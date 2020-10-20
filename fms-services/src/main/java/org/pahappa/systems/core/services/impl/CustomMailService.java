package org.pahappa.systems.core.services.impl;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.pahappa.systems.core.services.EmailTemplateService;
import org.pahappa.systems.models.Requisition;
import org.pahappa.systems.models.RequisitionStatus;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;

public class CustomMailService extends Thread {
	private User user, changedBy;
	private String heading;
	private String comment;
	private RequisitionStatus requisitionStatus;
	private Requisition requisition;

	public CustomMailService(User user, String heading, String comment, RequisitionStatus requisitionStatus,
			Requisition requisition, User changedBy) {
		this.user = user;
		this.heading = heading;
		this.comment = comment;
		this.requisitionStatus = requisitionStatus;
		this.requisition = requisition;
		this.changedBy = changedBy;
	}

	@Override
	public void run() {
		try {
			String name = this.user.getFullName();
			String email = this.user.getEmailAddress();
			EmailTemplateService emailTemplateService = ApplicationContextProvider.getBean(EmailTemplateService.class);
			if (this.comment == null) {
				this.comment = "";
			}
			if(requisitionStatus == RequisitionStatus.ACKNOWLEDGED) {
				name = this.changedBy.getFullName();
				email = this.changedBy.getEmailAddress();
			}
			sendMail(this.heading,
					String.format(emailTemplateService.getTemplateByStatus(this.requisitionStatus).getTemplate(),
							name, this.requisition.getRequisitionNumber()) + this.comment,
					new String[] { this.user.getEmailAddress() }, "pahappatest@gmail.com", "pahappa2020", "smtp.gmail.com",
					"587");
		} catch (AddressException e) {
			System.out.println("Address not found...");
			e.printStackTrace();
		} catch (MessagingException e) {
			System.out.println("Messaging issue...");
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