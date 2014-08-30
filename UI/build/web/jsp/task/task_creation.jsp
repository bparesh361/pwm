<%-- 
    Document   : task_creation
    Created on : Jan 9, 2013, 4:09:04 PM
    Author     : ajitn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Task Creation</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <script src="js/jquery-1.6.4.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
        <link href="css/tabs.css" rel="stylesheet" type="text/css" />
        <script src="js/jquery.ui.position.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/Tabfunction.js"></script>
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>

        <script type="text/javascript">
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });

                var teamMemberId;
                $("#txtuserempName").autocomplete({
                    minLength : 1,
                    delay:10,
                    scrollHeight:100,
                    source : function(request, response) {
                        var temp=    $.ajax({
                            url : "getTeamMemberFlexBox?txtuserempName="+$("#txtuserempName").val() ,
                            dataType : "json",
                            type:"POST",
                            data : {name_startsWith : request.term},
                            success : function(data) {                                   // alert(data);
                                response($.map(data.results,function(item) {
                                    return {id : item.id,label : item.name};

                                }));
                            }
                        });
                    }, select: function( event, ui ) {
                        // teamMemberId = ui.item.id;
                    }
                });

                //Submit button
                $("#submitBtn").click(function(){
                    var validateFields=validateTaskCreationFields();
                    if(validateFields[0]==false){
                        alert(validateFields[1]);
                        return false;
                    }
                  
                });
                

                function validateTaskCreationFields(){
                    var remarksLength=1000;
                    var  teamMemberId =$("#taskAssignToSel").val();
                   
                    //                    if(teamMemberId==undefined || teamMemberId==null || teamMemberId.length==0){
                    //                        return [false,"Please select Task Assign To."];
                    //                    }
                    if(teamMemberId==-1){
                        return [false,"Please select Task Assign To."];
                    }
                    var taskTypeCheck = checkComboSelection("taskTypeSel", "Task Type");
                    if(taskTypeCheck[0]==false){
                        return [false,taskTypeCheck[1]];
                    }
                    var remark=$("#remarks").val();
                    remark=  remark.replace(/(\r\n|\n|\r)/gm," ");
                    $("#remarks").val(remark);
                    var validateRemark=isBlank(remark,"Remarks");
                    if(validateRemark[0]==false){
                        $("#remarks").focus();
                        return[false,validateRemark[1]];
                    }

                    validateRemark=checkLength(remark, "Remarks", remarksLength);
                    if(validateRemark[0]==false){
                        $("#remarks").focus();
                        return[false,validateRemark[1]];
                    }

              
                    var promoCount = $("#promoCount").val();
                    if(promoCount.length>0){
                        //                        if(!isNumeric(promoCount)){
                        //                            $("#promoCount").focus();
                        //                            return [false,"Promo count Value should be numeric."];
                        //                        }
                        promoCount=checkLength(promoCount, "Promo Count", 9);
                        if(promoCount[0]==false){
                            $("#promoCount").focus();
                            return[false,promoCount[1]];
                        }
                        promoCount = $("#promoCount").val();
                        if (!promoCount.match(/^\d+$/)){
                            $("#promoCount").focus();
                            return [false,"Promo Count should be numeric."];
                        }
                    }

                    //                    var fileid=  document.getElementById("taskFile").value;
                    //                    if(fileid==null ||fileid==""){
                    //                        return [false,"Please select file to upload."];
                    //
                    //                    }else{
                    //                        var valid_extensions = /(.csv)$/i;
                    //                        if(valid_extensions.test(fileid)){
                    //                        } else{
                    //                            return [false,'Invalid File. Please Upload CSV File Only.'];
                    //                        }
                    //                    }
                    //$("#hdnTeamMemberId").val(teamMemberId);
                    return true;
                }
                
            });
        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <div id="middle_cont">

            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" >
                <s:form id="createTask" action="createTaskCreation_action" method="POST" enctype="multipart/form-data">
                    <tr>
                        <td><h1>Task Creation</h1></td>
                    </tr>
                    <tr><td class="errorText" align="right">(Fields marked with a * are mandatory.)</td></tr>
                    <tr>
                        <td width="100%" align="center" style="color: #0D6C0D;font-weight: bold">
                            <div id="message">
                                <s:actionmessage cssClass="successText"/>
                                <s:actionerror cssClass="errorText"/>
                            </div>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <img src="images/spacer.gif" width="10" height="10"/>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">
                            <table>
                                <tr>
                                    <td align="right">
                                        Task Assign To:<span class="errorText">&nbsp;*</span>
                                    </td>
                                    <td align="left">
                                        <s:select headerKey="-1" headerValue="---Select---" list="taskAssignToMap" id="taskAssignToSel" name="taskVO.taskAssignTo"  cssClass="dropdown"/>
                                        <!--                                        <input type="text" size="42px" id="txtuserempName" />-->
                                        <%--  <s:hidden id="hdnTeamMemberId" name="taskVO.taskAssignTo"/> --%>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <img src="images/spacer.gif" width="10" height="10"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        Task Type:<span class="errorText">&nbsp;*</span>
                                    </td>
                                    <td align="left">
                                        <s:select headerKey="-1" headerValue="---Select---" list="taskTypeMap" id="taskTypeSel" name="taskVO.taskType"  cssClass="dropdown"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <img src="images/spacer.gif" width="10" height="10"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        Promo count:
                                    </td>
                                    <td align="left">
                                        <input type="text" id="promoCount" name="taskVO.promoCount" maxlength="9"></td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <img src="images/spacer.gif" width="10" height="10"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        Remarks:<span class="errorText">&nbsp;*</span>
                                    </td>
                                    <td align="left">
                                        <textarea cols="40" rows="4" name="taskVO.remarks" id="remarks" title="Max 1000 Characters."></textarea>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <img src="images/spacer.gif" width="10" height="10"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">
                                        File Upload:
                                    </td>
                                    <td align="left">
                                        <s:file id ="taskFile" name="taskFileUpload" ></s:file>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <img src="images/spacer.gif" width="10" height="10"/>
                        </td>
                    </tr>
                    <tr align="center">
                        <td>
                            <input type="submit" name="submitBtn" id="submitBtn" value="Submit"  class="btn"/>&nbsp;&nbsp;
                        </td>
                    </tr>
                </s:form>
            </table>
        </div>
    </body>
</html>
