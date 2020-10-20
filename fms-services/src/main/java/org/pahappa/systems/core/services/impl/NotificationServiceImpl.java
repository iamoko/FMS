package org.pahappa.systems.core.services.impl;
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.pahappa.systems.services.impl;
//
//import java.util.ArrayList;
//import java.util.List;
//import org.pahappa.systems.models.Requisition;
//import org.pahappa.systems.services.NotificationService;
//import org.sers.webutils.model.security.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.mail.MailException;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.SimpleMailMessage;
//
///**
// *
// * @author noah
// */
//public class NotificationServiceImpl implements NotificationService{
//
//    @Autowired
//    SimpleMailMessage message;
//
//    @Autowired
//    JavaMailSender mailSender;
//
//
//    private String[] getEmailAddresses(List<User> users){
//
//        ArrayList<String> emails = new ArrayList<String>();
//        for (User user : users) {
//            emails.add(user.getEmailAddress());
//        }
//
//        return emails.toArray(new String[emails.size()]);
//    }
//
//    private boolean sendMail(SimpleMailMessage messge){
//
//        try {
//            mailSender.send(messge);
//        }
//        catch (MailException e) {
//            System.err.println(e.getMessage());
//            return false;
//        }
//
//        return true;
//    }
//
//    @Override
//    public boolean accountCreated(User user) {
//
//        message.setFrom("");
//        message.setTo(user.getEmailAddress());
//        message.setSubject("Notification of accunt creation");
//        message.setText(
//                String.format(
//                        "Hello {0} \n "
//                      + "An account on FMS Pahappa has been created for you\n"
//                      + "",user.getFirstName()
//                ));
//
//        return this.sendMail(message);
//    }
//
//    @Override
//    public boolean requisitionCreated(List<User> users, Requisition requisition) {
//
//        for(User user: users){
//            message.setFrom("");
//            message.setTo(user.getEmailAddress());
//            message.setSubject("Notification of requisition creation");
//            message.setText(
//                    String.format(
//                            "Hello {0}\n "
//                          + "A requisition has been created\n"
//                          + "Requisistion id : {1}",user.getFirstName(),
//                          requisition.getId())
//                    );
//
//            this.sendMail(message);
//        }
//
//        return true;
//
//    }
//
//    @Override
//    public boolean requisitionApproved(List<User> users, Requisition requisition) {
//
//        for(User user: users){
//            message.setFrom("");
//            message.setTo(user.getEmailAddress());
//            message.setSubject("Notification of requisition approval");
//            message.setText(
//                    String.format(
//                            "Hello {0}\n "
//                          + "A requisition has been approved\n"
//                          + "Requisistion id : {1}",user.getFirstName(),
//                          requisition.getId())
//                    );
//
//            this.sendMail(message);
//        }
//
//        return true;
//
//    }
//
//    @Override
//    public boolean requisitionRejected(List<User> users, Requisition requisition) {
//
//        for(User user: users){
//            message.setFrom("");
//            message.setTo(user.getEmailAddress());
//            message.setSubject("Notification of requisition rejcection");
//            message.setText(
//                    String.format(
//                            "Hello {0}\n "
//                          + "A requisition has been rejcected\n"
//                          + "Requisistion id : {1}",user.getFirstName(),
//                          requisition.getId())
//                    );
//
//            this.sendMail(message);
//        }
//
//        return true;
//    }
//
//    @Override
//    public boolean requisitionAck(List<User> users, Requisition requisition) {
//
//        for(User user: users){
//            message.setFrom("");
//            message.setTo(user.getEmailAddress());
//            message.setSubject("Notification of requisition acknowledgement");
//            message.setText(
//                    String.format(
//                            "Hello {0}\n "
//                          + "A requisition has been acknowledged\n"
//                          + "Requisistion id : {1}",user.getFirstName(), requisition.getId())
//                    );
//
//            this.sendMail(message);
//        }
//
//        return true;
//    }
//
//}
