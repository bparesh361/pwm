/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.interceptors;

import com.fks.ui.login.action.PreLoginAction;
import com.fks.ui.constants.WebConstants;
import com.fks.ui.login.action.ChangePasswordAction;
import com.fks.ui.login.action.UserCreateAction;
import com.fks.ui.login.action.ForgotPassword;
import com.fks.ui.login.action.UserLoginAction;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 *
 * @author ajitn
 */
public class SecurityManager implements Interceptor {

    @Override
    public void destroy() {
        System.out.println("#########----- Interceptor Destroy Called -----#########");
    }

    @Override
    public void init() {
        System.out.println("#########----- Interceptor Init Called -----#########");
    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        System.out.println("#########----- Security Manager Interceptor Called -----#########");
        String userName = (String) actionInvocation.getInvocationContext().getSession().get(WebConstants.USER_NAME);
        System.out.println("User Name In Interceptor : "+userName);
        if (UserLoginAction.class.getSimpleName().equals(actionInvocation.getAction().getClass().getSimpleName())
                || ChangePasswordAction.class.getSimpleName().equals(actionInvocation.getAction().getClass().getSimpleName())
//                || CreateUserAction.class.getSimpleName().equals(actionInvocation.getAction().getClass().getSimpleName())
                || PreLoginAction.class.getSimpleName().equals(actionInvocation.getAction().getClass().getSimpleName())
                || ForgotPassword.class.getSimpleName().equals(actionInvocation.getAction().getClass().getSimpleName())) {

            return actionInvocation.invoke();

        } else if (userName != null) {            
            return actionInvocation.invoke();
        } else {
            System.out.println("======inside interceptor. Your Session has Expired or You have Not Logged In.");
            return "login";
        }
    }
}
