package org.pahappa.systems.core.services.impl;

import org.pahappa.systems.core.services.EmailService;
import org.pahappa.systems.models.Requisition;
import org.sers.webutils.model.security.User;
import org.sers.webutils.server.core.utils.ApplicationContextProvider;
import org.sers.webutils.server.shared.SharedAppData;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailService extends Thread{
    String message, heading;
    User user;
    public MailService(User user, String message, String heading) {
        this.message = message;
        this.user = user;
        this.heading = heading;
    }


    @Override
    public void run() {
        try {
            EmailService emailService = ApplicationContextProvider.getBean(EmailService.class);
            emailService.save(this.user, this.heading, this.message);

            sendMail(this.heading, this.message, new String[] {
                            user.getEmailAddress() }, "pahappatest@gmail.com",
                    "pahappa2020", "smtp.gmail.com", "587");


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
    public static void sendRequisitionEmail(User loggedInUser, String message) {
        try {
            StringBuilder body = new StringBuilder();
            body.append("<html><body>");
            body.append("<p>Hello<b>").append(loggedInUser.getFullName()).append("</b>");

            sendMail("Pahappa FMS",
                    String.format("Hello %s, %s", loggedInUser.getFullName(), message),
                    new String[] { loggedInUser.getEmailAddress() },
                    "pahappatest@gmail.com", "pahappa2020", "smtp.gmail.com", "587");

        } catch (AddressException e) {
            System.out.println("Address not found...");
            e.printStackTrace();
        } catch (MessagingException e) {
            System.out.println("Messaging issue...");
            e.printStackTrace();
        }
    }

    public static void sendWelcomeEmail() {
        User loggedInUser = SharedAppData.getLoggedInUser();
        try {


            sendMail("Pahappa FMS",
                    String.format("Hello %s, you have successfully logged in FMS:BACKGROUND JOB", loggedInUser.getFullName()),
                    new String[] { loggedInUser.getEmailAddress() },
                    "pahappatest@gmail.com", "pahappa2020", "smtp.gmail.com", "587");

        } catch (AddressException e) {
            System.out.println("Address not found...");
            e.printStackTrace();
        } catch (MessagingException e) {
            System.out.println("Messaging issue...");
            e.printStackTrace();
        }
    }
}
