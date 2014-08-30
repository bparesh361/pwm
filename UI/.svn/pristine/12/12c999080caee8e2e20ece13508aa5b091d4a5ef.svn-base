/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.login.action;

import com.fks.promo.master.service.Resp;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.ServiceMaster;
import com.fks.ui.constants.WebConstants;
import org.apache.log4j.Logger;

/**
 *
 * @author krutij
 */
public class ChangePasswordAction extends ActionBase {

    private static Logger logger = Logger.getLogger(ChangePasswordAction.class);

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }
    private String newPass;
    private String oldPass;

    public String changePassword() throws Exception {
        try {


            logger.info("Geeting Request to Change Password!! . Service : getUserMasterService().changePassword");

            String userID = (String) getSessionMap().get(WebConstants.EMP_ID);

            Resp resp = ServiceMaster.getUserMasterService().changePassword(Long.valueOf(userID), oldPass.trim(), newPass.trim());


            if (resp.getRespCode().value().equalsIgnoreCase(WebConstants.success)) {
                getSessionMap().remove(WebConstants.ACCESS_MENU);

                addActionMessage(resp.getMsg());
                return SUCCESS;
            } else {

                addActionError(resp.getMsg());
                return INPUT;
            }

        } catch (Exception e) {
            logger.info(e.getLocalizedMessage());

            logger.info("Exception in excute() of changePassword Page ----- ");
            e.printStackTrace();
            return ERROR;
        }
    }

    public String getNewPass() {
        return newPass;
    }

    public void setNewPass(String newPass) {
        this.newPass = newPass;
    }

    public String getOldPass() {
        return oldPass;
    }

    public void setOldPass(String oldPass) {
        this.oldPass = oldPass;
    }
}
