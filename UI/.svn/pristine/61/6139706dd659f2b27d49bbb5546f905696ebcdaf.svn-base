/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.login.action;

import com.fks.ui.common.action.ActionBase;
import org.apache.log4j.Logger;

/**
 *
 * @author ajitn
 */
public class PreLoginAction extends ActionBase{

    private static final Logger LOGGER = Logger.getLogger(PreLoginAction.class);

    @Override
    public String execute() throws Exception {
        LOGGER.info("==== Pre Login Request Called ====");
//        addActionMessage("Your Session has Expire or You have not LoggedIn.");
        addActionMessage("Your Login Session has expired, Please Re-Login.");
        return SUCCESS;
    }

    public String showLoginPage() throws Exception {
        LOGGER.info("Pre Login Request Called!");
        return SUCCESS;
    }
}
