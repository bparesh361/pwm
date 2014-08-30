/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.common;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author krutij
 */
public class MailUtil {

    public static boolean sendforgetPasswordMail(String emailAddr, String newPwd,String userName) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host", "10.0.28.31");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.user", "AdmiP");
        props.put("mail.smtp.pwd", "future1");
        Session session = Session.getInstance(props, null);
        session.setDebug(true);


        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("admin.promotionworkflow@futuregroup.in"));
        msg.setSubject("New Password");
        msg.setText("Dear "+userName+", \n\nYour Password has been reset succesfully. \n\nNew Password is : " + newPwd + "\n\n\nThanks. \nAdmin Promotion-Workflow  ");
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAddr));

        Transport transport = session.getTransport("smtp");
        transport.connect("10.0.28.31", 587, "AdmiP", "future1");
        System.out.println("Connection successful");
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
        return true;
    }



    public static boolean sendUserCreationMail(String emailAddr, String Usercode, String userPass, boolean iscreate) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host", "10.0.28.31");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.user", "AdmiP");
        props.put("mail.smtp.pwd", "future1");
        String portalurl = PromotionPropertyUtil.getPropertyString(PropertyEnum.PORTAL_URL);
        Session session = Session.getInstance(props, null);
        session.setDebug(true);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("admin.promotionworkflow@futuregroup.in"));
        msg.setSubject("Promotion Workflow Management");
        if (iscreate) {
            String messagebody = getUserCreationMessageBody(Usercode, userPass, portalurl);
            msg.setText(messagebody);
        }else{
             String messagebody = getUserUpdateMessageBody(Usercode, userPass, portalurl);
            msg.setText(messagebody);
        }
        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(emailAddr));

        Transport transport = session.getTransport("smtp");
        transport.connect("10.0.28.31", 587, "AdmiP", "future1");
        System.out.println("Connection successful");
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
        return true;
    }

    public static String getUserCreationMessageBody(String UserCode, String pass, String url) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\nDear User ,");
        buffer.append("\n\n");
        buffer.append("You are registerd with Promotion Workflow Management. Please refer following details.");
        buffer.append("\n\n");
        buffer.append("User Code : ").append(UserCode);
        buffer.append("\n");
        buffer.append("Password : ").append(pass);
        buffer.append("\n");
        buffer.append("Portal URL : ").append(url);
        buffer.append("\n");
        buffer.append("\nRegards,");
        buffer.append("\nAdmin Promotion-Workflow.");
        return buffer.toString();
    }
     public static String getUserUpdateMessageBody(String UserCode, String pass, String url) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("\nDear User ,");
        buffer.append("\n\n");
        buffer.append("Your detail updated successfully on Promotion Workflow Management. Please refer following details.");
        buffer.append("\n\n");
        buffer.append("User Code : ").append(UserCode);
        buffer.append("\n");
        buffer.append("Password : ").append(pass);
        buffer.append("\n");
        buffer.append("Portal URL : ").append(url);
        buffer.append("\n");
        buffer.append("\nRegards,");
        buffer.append("\nAdmin Promotion-Workflow.");
        return buffer.toString();
    }
}
