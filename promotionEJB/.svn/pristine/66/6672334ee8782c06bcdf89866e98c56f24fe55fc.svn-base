/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.service;

import com.fks.promotion.vo.SubmitPromoOrgdtlReq;
import java.util.logging.Level;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import org.apache.log4j.Logger;

/**
 *
 * @author krutij
 */
@Stateless
@LocalBean
public class CommonPromoMailService {

    private static final Logger logger = Logger.getLogger(CommonPromoMailService.class.getName());
    private NotificationMessage notificationMessage;
    @Resource(name = "", mappedName = "jms/promofactory")
    private QueueConnectionFactory factory;
    @Resource(name = "promoqueue", mappedName = "jms/promoqueue")
    private Queue articlevalidationQueue;
    @Resource(name = "subpromoqueue", mappedName = "jms/subpromoqueue")
    private Queue subPromoQueue;
     @Resource(name = "orgSubmitQue", mappedName = "jms/orgSubmitQueue")
    private Queue orgSubmitQue;

    public void sendNotificationMessage(NotificationMessage notificationMessage) {
        logger.info(" ===== Sending Notification Message ===== ");
        this.notificationMessage = notificationMessage;
        try {
            sendMessage();
            logger.info("message sent successfully for Notification Type : " + notificationMessage.getNotificationType());
        } catch (Exception e) {
            logger.error("Error while sending message to JMS " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void sendCreateUpdateSubPromoMessage(NotificationMessage notificationMessage) {
        logger.info("------- Sending Trans Promo Update Message ---------" );
        QueueConnection connection = null;
        QueueSession session = null;
        try {
            connection = (QueueConnection) factory.createConnection();
            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            ObjectMessage message = session.createObjectMessage();
            message.setObject(notificationMessage);
            session.createProducer(subPromoQueue).send(message);
        } catch (Exception ex) {
            logger.error("----- Error In sendUpdateSubPromoMessage : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException ex) {
                    java.util.logging.Logger.getLogger(CommonPromoMailService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ex) {
                    java.util.logging.Logger.getLogger(CommonPromoMailService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

     public void sendStatusUpdateOfOrganizationMessage(NotificationMessage notificationMessage) {
        logger.info("------- Sending sendStatusUpdateOfOrganizationMessage ---------" );
        QueueConnection connection = null;
        QueueSession session = null;
        try {
            connection = (QueueConnection) factory.createConnection();
            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            ObjectMessage message = session.createObjectMessage();
            message.setObject(notificationMessage);
            session.createProducer(orgSubmitQue).send(message);
        } catch (Exception ex) {
            logger.error("----- Error In sendStatusUpdateOfOrganizationMessage : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException ex) {
                    java.util.logging.Logger.getLogger(CommonPromoMailService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException ex) {
                    java.util.logging.Logger.getLogger(CommonPromoMailService.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void sendMessage() throws Exception {
        QueueConnection connection = null;
        QueueSession session = null;
        try {
            connection = (QueueConnection) factory.createConnection();

            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            ObjectMessage message = session.createObjectMessage();
            // message.setJMSExpiration(3600000L);
            message.setObject(notificationMessage);
            session.createProducer(articlevalidationQueue).send(message);
        } catch (Exception e) {
            logger.error("Error while sending message to JMS " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }



//    public void sendCreateSubPromoFileMessage(NotificationMessage notificationMessage) {
//        logger.info(" ===== Sending create subpromotion Message ===== ");
//        try {
//            this.notificationMessage = notificationMessage;
//            sendCreatePromoQueue();
//            logger.info("Promo Id  : " + notificationMessage.getTransPromoId());
//        } catch (Exception e) {
//            logger.error("Error while sending message to JMS " + e.getMessage());
//            e.printStackTrace();
//        }
//    }

//    private void sendCreatePromoQueue() throws Exception {
//        QueueConnection connection = null;
//        QueueSession session = null;
//        try {
//            connection = (QueueConnection) factory.createConnection();
//            session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
//            ObjectMessage message = session.createObjectMessage();
//            message.setObject(notificationMessage);
//            session.createProducer(createPromoQueue).send(message);
//        } catch (Exception e) {
//            logger.error("Error while sending message to JMS " + e.getMessage());
//            e.printStackTrace();
//        } finally {
//            if (session != null) {
//                session.close();
//            }
//            if (connection != null) {
//                connection.close();
//            }
//        }
//    }
}
