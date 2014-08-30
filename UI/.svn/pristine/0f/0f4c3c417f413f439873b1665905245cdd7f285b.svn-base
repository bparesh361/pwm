<%-- 
    Document   : login
    Created on : Dec 11, 2012, 11:11:16 AM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <style type="text/css">
            body,html {background:url(images/home_bg.png) no-repeat #a51f26; background-position: center; width: 100%;}
        </style>
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link href="css/thickbox.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="js/thickbox.js"></script>
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>
        <script language="javascript" type="text/javascript">


            jQuery(document).ready(function()
            {
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });
                $('#userName').focus();

                $("#btnsubmit").click(function(){
                    var userName = $("#userName").val();
                    var pwd = $("#pwd").val();
                    $("#message").html('');

                    if(userName.length==0){
                        alert("Please enter User Id.");
                        return false;
                    }
                    if(pwd.length==0){
                        alert("Please enter Password.");
                        return false;
                    }
                    if (userName == null || !userName.toString().match(/^[-]?\d*\$/)){
                        alert("User name should be numeric.");
                        $("#userName").val("");
                        $("#userName").focus();
                        return false;
                    }

        //                    if(!isNumeric(userName)){
        //                        alert("User name should be numeric.");
        //                        $("#userName").val("");
        //                        $("#userName").focus();
        //                        return false;
        //                    }
                });

                //                jQuery("#showWindow").click(function(){
                //                    window.open("forgotpassword","width=450, height=200,top=100,left=200");
                //                    //tb_show( "", "forgotpassword?height=200&width=450");
                //                });
              
              
            });
        </script>
    </head>
    <body>
        <s:form action="doLogin" method="POST">
            <div id="login_page">
                <div class="home_logo"><img src="images/promotion_workflow_logo.png"></div>
                <div class="login_box">
                    <div class="login_error" id="message"><s:actionerror /></div>
                    <div class="login_successmsg" id="message1"><s:actionmessage /></div>
                    <div class="full_width mt20">
                        <div class="login_txt left">USER ID<span class="errorText">&nbsp;*</span></div>
                        <input type="text" name="userName" id="userName" />
                    </div>
                    <div class="full_width mt20">
                        <div class="login_txt left">Password<span class="errorText">&nbsp;*</span></div>
                        <input type="password" name="pwd" id="pwd" />
                    </div>
                    <div class="full_width forgot_link">
                        <a id="showWindow" onclick="tb_show( '', 'forgotpassword?height=340&width=530');" >Forgot Password?</a>
                    </div>
                    <div id="login_btn" class="mt20 left" align="center">
                        <input type="submit" name="btnsubmit" id="btnsubmit" value="Submit" class="but_login" />
                        <!--                        <input type="submit" name="button" id="button" value="Clear" class="but_login" />-->
                    </div>

                </div>

            </div>
        </s:form>
    </body>
</html>
