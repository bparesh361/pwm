/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.jms;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jms.JMSException;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import org.apache.log4j.Logger;

/**
 *
 * @author krutij
 */
@Stateless
@LocalBean
public class ReportsJMSService {

    private static final Logger logger = Logger.getLogger(ReportsJMSService.class.getName());
    @Resource(name = "", mappedName = "jms/promoReportfactory")
    private QueueConnectionFactory connectionFactory;
    @Resource(name = "reportqueue", mappedName = "jms/reportqueue")
    private Queue reportQueue;

    public void sendReportRequestIntoQueue(JMSMessage jMSMessage) {
        System.out.println("=================== Report Request Sent into Report Queue. ====================");
        QueueConnection connection = null;
        QueueSession session = null;
        try {
            connection = connectionFactory.createQueueConnection();
            session = connection.createQueueSession(false, session.AUTO_ACKNOWLEDGE);
            ObjectMessage message = session.createObjectMessage();
            message.setObject(jMSMessage);
            session.createProducer(reportQueue).send(message);
        } catch (Exception ex) {
            System.out.println("----- Error In sendReportRequestIntoQueue : " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (JMSException e) {
                   System.out.println("----- Error in sendReportRequestIntoQueue while clossing session : " + e.getMessage());
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                   System.out.println("----- Error in sendReportRequestIntoQueue while clossing connection : " + e.getMessage());
                }
            }
        }
    }
}
