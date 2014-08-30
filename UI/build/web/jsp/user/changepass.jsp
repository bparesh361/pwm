<%-- 
    Document   : changepass
    Created on : Dec 11, 2012, 11:23:44 AM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <link href="css/style.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript">

            jQuery(document).ready(function(){

                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });

                $("#btnsubmit").click(function(){

                    if($("#oldPass").val().length ==0){
                        alert ("Please enter Current Password.") ;
                        $("#oldPass").focus();
                        return false ;
                    }
                    if($("#newPass").val()==""){
                        alert ("Please enter New Password.") ;
                        $("#newPass").focus();
                        return false ;
                    }
                    if($("#newPass").val()=="india123"){
                        alert ("New Password should not be system generated password.") ;
                        $("#newPass").val('');
                        $("#rePass").val('');
                        $("#newPass").focus();
                        return false ;
                    }
                    if($("#rePass").val() == "")
                    {
                        alert ("Confirm password should not be blank.") ;
                        $("#rePass").focus();
                        return false ;
                    }


                    if($("#rePass").val() != $("#newPass").val()){
                        alert ("Confirm Password must same as New Password.") ;
                        $("#rePass").val('');
                        $("#rePass").focus();
                        return false ;
                    }
                    if($("#newPass").val() == $("#oldPass").val()){
                        alert ("New Password should not same as Current Password.") ;
                        $("#newPass").val('');
                        $("#newPass").focus();
                        return false ;
                    }
                    $("#changePassformId").submit();
                });
            });

        </script>
    </head>
    <body>
        <s:form id="changePassformId" action="changePasswordSubmit">
            <table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                    <td>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0">

                            <tr>
                                <td>
                                    <%
                                                Boolean getIsNewUSer = (Boolean) session.getAttribute("isNewUser");
                                                if (getIsNewUSer == false) {
                                    %>
                                    <jsp:include page="/jsp/menu/menuPage.jsp"/>
                                    <% } else {%> <jsp:include page="/jsp/menu/blankmenu.jsp"/>
                                    <%}%>

                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr><td style="height:10px"></td></tr>
                <tr>
                    <td>
                        <table width="70%" border="0" align="center" cellpadding="0" cellspacing="0">
                            <tr>
                                <td>
                                    <div id="headertxt" align="center"><h1>Change Password</h1></div>
                                </td>
                            </tr>
                            <tr><td style="height:30px"></td></tr>

                            <tr>
                                <td width="100%" align="center" >
                                    <div id="message">
                                        <s:actionmessage cssClass="successText"/>
                                        <s:actionerror cssClass="errorText"/>
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td  width="70%">
                                    <table width="80%" >
                                        <tr >
                                            <td class="formtext" align="right">Current Password : &nbsp;<span class="errorText">&nbsp;*</span></td>
                                            <td class="formtext" style="height:25px" align="left">  <!--<input type="text" id="oldPass" name="oldPass" size="11" align="middle"/>-->
                                                <s:password name="oldPass"  value="" id="oldPass" size="12"></s:password>
                                            </td>
                                            <td style="height:10px;"></td>
                                        </tr>
                                        <tr>
                                            <td class="formtext" align="right">New Password : &nbsp;<span class="errorText">&nbsp;*</span></td>
                                            <td class="formtext" style="height:25px" align="left">  <!--            <input type="text" id="newPass" name="newPass" size="11" align="middle"/>-->
                                                <s:password name="newPass"  value="" id="newPass" size="12"></s:password>
                                            </td>
                                            <td style="height:10px;"></td>
                                        </tr>
                                        <tr>
                                            <td class="formtext" align="right">Confirm New Password : &nbsp;<span class="errorText">&nbsp;*</span></td>
                                            <td class="formtext" style="height:25px" align="left">  <!--            <input type="text" id="rePass" name="rePass" size="11" align="middle"/>-->
                                                <s:password name="rePass"  value="" id="rePass" size="12"></s:password>
                                            </td>
                                            <td style="height:10px;"></td>
                                        </tr>
                                        <tr><td style="height:10px"></td></tr>
                                        <tr>
                                            <td colspan="2" align="center" >
                                                            <%--  <s:submit name="btnsubmit" id="btnsubmit" value="Change Password"  action="changePasswordSubmit" cssClass="button"/> --%>

                                                            <input align="left" type="button" id="btnsubmit" name="btnsubmit" value="Change Password" class="largebtn"  />
                                                        </td>
                                        </tr>
                                        <tr><td style="height:10px"></td></tr>
                                    </table>
                                </td>
                            </tr>                          
                        </table>
                    </td>
                </tr>
            </table>
        </s:form>
    </body>
</html>
