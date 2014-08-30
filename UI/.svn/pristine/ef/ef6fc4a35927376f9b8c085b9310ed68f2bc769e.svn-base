<%-- 
    Document   : forgotpassword
    Created on : Nov 28, 2011, 12:42:46 PM
    Author     : javals
--%>

<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Forgot Password</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <style type="text/css">
            body,html {background:url(images/home_bg.png) no-repeat #a51f26; background-position: center; width: 100%;}
        </style>
        <script type="text/javascript">
            jQuery(document).ready(function()
            {
             
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });
                $("#btnSendPassword").click(function(){
                    $("#error").html('');
                    $("#success").html('');
                    var userID=$("#userId").val();
                    //This regex is from the jquery.numeric plugin itself
                    if(!isNumeric(userID)){
                        alert("User name should be numeric.");
                        $("#userName").val("");
                        $("#userName").focus();
                        return false;
                    }

                    if(userID==''){
                        $("#error").html('Please Enter UserID');
                    }else{
                        $.ajax({
                            url:"forgotpasswordreq?userId="+$("#userId").val(),
                            global: false,
                            type: "POST",
                            dataType: "json",
                            async:false,
                            error:function(){
                                alert("Can not Connect to Server");
                            },
                            success: function(data){
                                for(var key in data){
                                    if(key=="success"){
                                        $("#success").html(data[key]);
                                    }else {
                                        $("#error").html(data[key]);
                                    }
                                }
                            }
                        });
                    }
                });
                

              
            });
        </script>

    </head>
    <body>
        <table width="50%"  align="center">
            <tr>
                <td>
                    <div id="login_page">
                        <!--                        <div class="home_logo"><img src="images/promotion_workflow_logo.png"/></div>-->
                        
                        <div class="login_box">
                          
                            <div id="headertxt" align="center"><b>Reset Password</b></div>
                            <div class="login_error" id="error"><s:actionerror /></div>
                            <div class="login_successmsg" id="success"><s:actionmessage /></div>

                            <div class="full_width mt20">
                                <div class="login_txt left">USER ID<span class="errorText">&nbsp;*</span></div>
                                <input type="text" name="userId" id="userId" />

                            </div>
                            <div id="login_btn" class="mt20 left" align="center">                                
                                <input type="button" name="btnSendPassword" id="btnSendPassword" value="Submit" class="but_login"/>
                                <!--                        <input type="submit" name="button" id="button" value="Clear" class="but_login" />-->
                            </div>

                        </div>
  
                    </div>
                </td>
            </tr>
        </table>


        <!--        <table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <td>
                            <table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">

                                <tr>
                                    <td colspan="2" align="center">
                                         User ID :
                                         <input type="text" name="userId" id="userId" />

                                    </td>
                                </tr>
                               <tr>
                                    <td colspan="2">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td  colspan="2" align="center">
                                        <input type="button" name="btnsubmit" id="btnsubmit" value="Get Password" class="but_login" />
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="center">

                                        <div class="" id="errorMessage1Lable">
                                            <div class="" id="success"></div>
                                            <div class="" id="error"></div>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>&nbsp;</td>
                    </tr>

                </table>-->
    </body>
</html>

