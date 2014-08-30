/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.mail;

import com.fks.reports.common.ReportCommonConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author krutij
 */
public class MailUtil {

    private static final Logger logger = Logger.getLogger(MailUtil.class.getName());
    static Properties props = null;

    static {
        try {
            if (props == null) {
                props = new Properties();
                props.put("mail.smtp.host", "10.0.28.31");
                props.put("mail.smtp.port", "587");
                props.put("mail.smtp.user", "AdmiP");
                props.put("mail.smtp.pwd", "future1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean sendMailNotificationWithAttachment(String emailAddr, String mes, String fileName, String currDate) throws Exception {

        Session session = Session.getInstance(props, null);
        session.setDebug(true);

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("admin.promotionworkflow@futuregroup.in"));
        message.setSubject("Store proposal pending report as on " + currDate);

        InputStream is = (InputStream) MailUtil.class.getResourceAsStream(ReportCommonConstants.PROPERTY);
        Properties pro = new Properties();
        pro.load(is);

        Multipart mp = new MimeMultipart("mixed");
        BodyPart textPart = new MimeBodyPart();
        textPart.setHeader("Content-Type", "text/html");
        textPart.setContent(mes, "text/html");
        mp.addBodyPart(textPart);
        if (fileName != null) {
            textPart = new MimeBodyPart();
            textPart = addAttachment(fileName, (MimeBodyPart) textPart);
            textPart.setDisposition(MimeBodyPart.ATTACHMENT);
            mp.addBodyPart(textPart);
        }
        message.setContent(mp);

        message.setRecipients(Message.RecipientType.TO, emailAddr);
        message.setSentDate(new Date());

        Transport transport = session.getTransport("smtp");
        transport.connect("10.0.28.31", 587, "AdmiP", "future1");
        logger.info("Connection successful");
        transport.sendMessage(message, message.getAllRecipients());

        transport.close();
        is.close();

        return true;
    }

    private static MimeBodyPart addAttachment(String filename, MimeBodyPart messageBodyPart) throws MessagingException {
        DataSource source = new FileDataSource(filename);
        messageBodyPart.setDataHandler(new DataHandler(source));
        messageBodyPart.setFileName(source.getName());
        return messageBodyPart;
    }

    public static String genrateMessageBodyForAutomatedReportSendingForStoreProposal(String currDate, String fromDate, String toDate, String zoneName) {
        StringBuilder buffer = new StringBuilder();
        buffer.append("<html><head></head><body>");
        buffer.append("\n<table width=100%>");
        buffer.append("\n<tr><td align=left>Dear ").append("User").append(" ,</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>Store proposal pending report has been generated on ").append(currDate).append("</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left><b>Report Duration : </b>").append(fromDate).append(" to ").append(toDate).append("</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left><b>Zone  :</b>").append(zoneName).append("</td></tr>");
        buffer.append("\n<br />");
        buffer.append("\n<tr><td align=left>Regards,</td></tr>");
        buffer.append("\n<tr><td align=left>Admin Promotion-Workflow.</td></tr>");
        return buffer.toString();
    }

    public static void main(String[] args) throws Exception {
        String str = genrateMessageBodyForAutomatedReportSendingForStoreProposal("05/09/2013", "01/09/2013", "01/08/2013", "West");
        final StringBuilder sb = new StringBuilder();
        sb.append("Test String");

//      final File f = new File("c:\\test.zip");
//      final ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
//      ZipEntry e = new ZipEntry("C:\\folderB\\promotion\\report\\internal_task\\status_T-214_1374668212354.xls");
//      out.putNextEntry(e);
//
//      byte[] data = sb.toString().getBytes();
//      out.write(data, 0, data.length);
//      out.closeEntry();
//
//      out.close();
        // sendMailNotificationWithAttachment("kruti.jani@futuregroup.in", str, "C:\\folderB\\promotion\\report\\internal_task\\status_T-214_1374668212354.zip", "Subject line");
        logger.info("Mail send....");
    }
}
