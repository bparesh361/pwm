<%-- 
    Document   : topHeader
    Created on : Dec 11, 2012, 3:25:08 PM
    Author     : krutij
--%>

<%@page import="com.fks.ui.constants.WebConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <title>JSP Page</title>
    </head>
    <body>
        <div id="header">
            <!--  <div class="left"><a href="#" class="logo"><img src="images/promotion_workflow_logo.png"></a></div> -->
            <div class="left"><a href="#" ><img src="images/PWM_inner.png"></a></div>
            <div style="position: absolute; top: 30px; left: 35%; font-family: 'Myriad Pro', arial; font-size: 25px; color: #FFFFFF;">Promotion Workflow Management</div>

            <div class="right">
                <div class="login_bg">
			Logged in as <%= session.getAttribute(WebConstants.USER_NAME)%><span class="pipespace">|</span>
                    Role : <%= session.getAttribute(WebConstants.USER_ROLE)%><span class="pipespace">|</span>
                    <a href="welcome">Home</a><span class="pipespace">|</span>
<!--                        Store Code : <%= session.getAttribute(WebConstants.STORE_CODE)%><span class="pipespace">|</span>-->
                    <a href="logout">Logout</a><span class="pipespace">|</span>
                    <a href="changePassword">Change Password</a>
                </div>
                <div class="clear"></div>
                <div class="right" style="margin-top: 12px; margin-right: 10px;"><img src="images/futuregroup_logo.png"></div>
            </div>
        </div>

    </body>
</html>
