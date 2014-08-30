/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.ui.login.action;




import com.fks.promo.master.service.Resp;
import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.ServiceMaster;
import java.io.PrintWriter;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author krutij
 */
public class ForgotPassword extends ActionBase implements ServletResponseAware {

    private static Logger logger = Logger.getLogger(ForgotPassword.class);

    private HttpServletResponse response;

    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    
    public void ajaxresetPassword() throws Exception{
        logger.info("Geeting Request to reset Password for User ID : "+ userId +" === Service : getUserMasterService().forgotPassword");
        Resp resp = ServiceMaster.getUserMasterService().forgotPassword(Long.valueOf(userId));        
        String message = resp.getMsg();
        JSONObject responsedata = new JSONObject();
         responsedata.put("success", message);
        PrintWriter out = response.getWriter();
        out.print(responsedata);
    }


    public void setServletResponse(HttpServletResponse response) {
	this.response = response;
    }

    public HttpServletResponse getServletResponse() {
	return response;
    }

}
