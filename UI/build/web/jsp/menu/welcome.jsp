<%-- 
    Document   : welcome
    Created on : Dec 11, 2012, 11:23:19 AM
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
        <title>Promotion Management Workflow</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/ddsmoothmenu.js"></script>

    </head>
    <body>

        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <!-- Middle content start here -->
        <div id="middle_cont">
            <s:form action="donothing" method="POST">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14">
                    <div>
                        <table width="50%" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                                <td height="80px" align="center">
                                    <h1>Welcome</h1>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
                                        <tr>
                                            <td align="left">User Code </td><td> :&nbsp;&nbsp;</td>
                                            <td align="left">
                                                <%=session.getAttribute(WebConstants.USER_ID)%>
                                            </td>
                                        </tr>
                                        <tr><td height="10px"></td></tr>
                                        <tr>
                                            <td align="left">User Name </td><td> :&nbsp;&nbsp;</td>
                                            <td align="left">
                                                <%=session.getAttribute(WebConstants.EMP_NAME)%>
                                            </td>
                                        </tr>
                                        <tr><td height="10px"></td></tr>
                                        <tr>
                                            <td align="left">Contact No </td><td> :&nbsp;&nbsp;</td>
                                            <td align="left" >
                                                <%=session.getAttribute(WebConstants.CONTACT_NO)%>
                                            </td>
                                        </tr>
                                        <tr><td height="10px"></td></tr>
                                        <tr>
                                            <td align="left">Email Id</td><td> :&nbsp;&nbsp;</td>
                                            <td align="left">
                                                <%=session.getAttribute(WebConstants.EMAIL_ID)%>
                                            </td>
                                        </tr>
                                        <tr><td height="10px"></td></tr>
                                        <tr>
                                            <td align="left">Role </td><td> :&nbsp;&nbsp;</td>
                                            <td align="left">
                                                <%=session.getAttribute(WebConstants.USER_ROLE)%>
                                            </td>
                                        </tr>
                                        <tr><td height="10px"></td></tr>
                                        <tr>
                                            <td align="left">Store Code</td><td> :&nbsp;&nbsp;</td>
                                            <td align="left">
                                                <%=session.getAttribute(WebConstants.STORE_CODE)%>
                                            </td>
                                        </tr>
                                        <tr><td height="10px"></td></tr>
                                        <tr>
                                            <td align="left">Store Description</td><td> :&nbsp;&nbsp;</td>
                                            <td align="left">
                                                <%=session.getAttribute(WebConstants.STORE_DESC)%>
                                            </td>
                                        </tr>
                                        <tr><td height="10px"></td></tr>
                                        <tr>
                                            <td align="left">Zone</td><td> :&nbsp;&nbsp;</td>
                                            <td align="left">
                                                <%=session.getAttribute(WebConstants.ZONE_NAME)%>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </div>
                </table>
            </s:form>
        </div>
        <!-- Middle content start here -->

    </body>
</html>
