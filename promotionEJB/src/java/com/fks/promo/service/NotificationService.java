/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.service;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import org.apache.log4j.Logger;

/**
 *
 * @author krutij
 */
@MessageDriven(mappedName = "jms/promoqueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class NotificationService implements MessageListener {

    private static final Logger logger = Logger.getLogger(NotificationService.class.getName());

    public NotificationService() {
    }
    @EJB
    private NotificationUtil notificationUtil;

    // @Override
    public void onMessage(Message message) {
        logger.info("Inside Processing the Message.");
        try {

            ObjectMessage objectMessage = (ObjectMessage) message;
            NotificationMessage notificationMessage = (NotificationMessage) objectMessage.getObject();
            switch (notificationMessage.getNotificationType()) {
                case ARTICLE_FILE_VALIDATION:
                    notificationUtil.genrateFileValidationMail(notificationMessage.getTransPromoId());
                    logger.info(" ==== Article File Validation - Notification Sent Successfully.");
                    break;
                case REQUEST_SUBMIT:
                    notificationUtil.getRequestSubmitListForL1(notificationMessage.getId());
                    logger.info(" ==== Request Submit - Notification Sent Successfully. " + notificationMessage.getId());
                    break;
                case L1_ESCALATED:
                    notificationUtil.getL1EscalatedL2EmployeeDetailBasedonMC(notificationMessage.getId());
                    logger.info(" ==== L1 ESCALATAED - Notification Sent Successfully. " + notificationMessage.getId());
                    break;
                case TEAM_MEMBER_ASSIGNED:
                    notificationUtil.getTeamMemberAssignedNotification(notificationMessage.getId(), notificationMessage.getEmpId());
                    logger.info(" ==== Team Member Assigned - Notification Sent Successfully. ");
                    break;
                case INITIATOR_MAIL:
                    notificationUtil.sendMailtoInitiatorOnStatusUpdate(notificationMessage.getId(), notificationMessage.getEmpId(), notificationMessage.getMsg());
                    logger.info(" ==== Initiator - Notification Sent Successfully. ");
                    break;
                case BUSINESS_EXIGENCY_ACTION:
                    notificationUtil.sendMailtoInitiatorOnStatusUpdate(notificationMessage.getId(), notificationMessage.getEmpId(), notificationMessage.getMsg());
                    notificationUtil.sendMailtoApproverOnStatusUpdateByExigency(notificationMessage.getId(), notificationMessage.getEmpId(),  notificationMessage.getMsg());
                    logger.info(" ==== Initiator & Approver - Notification Sent Successfully For Business Exigency action. ");
                    break;
                case TASK_ASSIGN:
                    notificationUtil.sendNotificationOnTaskAssignment(notificationMessage.getId());
                    logger.info(" === Task Assigne Notification sent Successfully For Task Assignment ===");
                    break;
                case PROMO_CLOSURE_MAIL:
                    System.out.println(" === PromoCloser Mail ===");
                    notificationUtil.sendPromoClosureMailNotificationToInitiatorAndApprover(notificationMessage.getId());
                    System.out.println(" === Prmo Closure Notification sent Successfully to Initator and Approver(L1,L2,BE) ===");
                    break;
                case BUSINESS_EXIGENCY_MAIL:
                    System.out.println(" === Business Exegency Mail ===");
                    notificationUtil.sendMailNotificationToBusinessExigency(notificationMessage.getId(),notificationMessage.isIsEscalated());
                    System.out.println(" ===  Notification sent Successfully to Business Exigency) ===");
                    break;

            }

        } catch (Exception e) {
            logger.error(" ======== ERROR ======   " + e.getMessage());
            e.printStackTrace();
        }
    }
}
