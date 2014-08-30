/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.service;

import com.fks.promo.common.Resp;
import com.fks.promo.entity.MstPromo;
import com.fks.promo.facade.MstPromoFacade;
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
@MessageDriven(mappedName = "jms/orgSubmitQueue", activationConfig = {
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue")
})
public class OrganizationMDB implements MessageListener {

    private static final Logger logger = Logger.getLogger(OrganizationMDB.class.getName());
  
    @EJB
    OrganizationUtil organizationUtil;

    public OrganizationMDB() {
    }

    public void onMessage(Message message) {
        logger.info("------- Inside Processing ORGANIZATION Update Message------");

        try {
            ObjectMessage objectMessage = (ObjectMessage) message;
            NotificationMessage notificationMessage = (NotificationMessage) objectMessage.getObject();
            switch (notificationMessage.getNotificationType()) {
                case ORGANIZATION_SUBMIT:
                    Resp resp = organizationUtil.SaveSubmitPromoOrganizationDtl(notificationMessage.getOrgdtlReq());
                    if (resp.getRespCode().values().toString().equalsIgnoreCase("SUCCESS")) {
                        logger.info("------- Organization Detail inserted Successfully------");
                        logger.info(resp.getMsg());
                    } else {
                        logger.info(resp.getMsg());
                        //logger.info("------- Error while inserting Organization Detail ------");
                    }
                    break;
            }
        } catch (Exception ex) {
            logger.error("------- Error While Processing Sub Promo Update Message : " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
