<%-- 
    Document   : inward_task
    Created on : Jan 10, 2013, 11:38:34 AM
    Author     : ajitn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Inward Task</title>
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

        <script type="text/javascript">
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });

                // status are hardcoded from DB
                var wipStatusId=52;
                var closedStatusId=53;

                var isClosed=1;
                var trnsTaskId;
                jQuery("#taskGrid").jqGrid({
                    url:"inwardTaskDasboardDtl_action",
                    datatype: 'json',
                    width: 1300,
                    height:210,
                    colNames:['TaskReq Number','Task Assigner', 'Zone','Task Assigned To','Task Type','Promo count',
                        'Remarks','Status','Assigned Date','Task Updated Date','Task Creation File'],
                    colModel:[
                        {name:'reqno',index:'reqno', width:170, align:"center"},
                        {name:'assigner',index:'assigner', width:200, align:"center"},
                        {name:'zone',index:'zone', width:200, align:"center"},
                        {name:'assignto',index:'assignto', width:170, align:"center"},
                        {name:'tasktype',index:'tasktype', width:200, align:"center"},
                        {name:'promocount',index:'promocount', width:200, align:"center"},
                        {name:'remarks',index:'remarks', width:200, align:"center"},
                        {name:'status',index:'status', width:200, align:"center"},
                        {name:'AssignedDate',index:'AssignedDate', width:200, align:"center"},
                        {name:'updateDate',index:'updateDate', width:200, align:"center"},
                        {name:'attachement',index:'attachment', width:200, align:"center"}
                    ],
                    rowNum:30,
                  rowList:[30],
                    headertitles: true,
                    viewrecords: true,
                    pager: '#taskPager',
                    emptyrecords:'',
                    recordtext:'',
                    //multiselect: false,
//                    sortorder: 'desc',
                    //loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:"",
                    onSelectRow:function(id){
                        trnsTaskId=id;
                        submitButtonEnableDisableOnReqSel(id);
                    }
                });

                $("#rbtnWIP").click(function(){                    
                    var checked = $(this).attr('checked', true);
                    if(checked){
                        isClosed=0;
                    }                    
                });

                $("#rbtnClosed").click(function(){
                    //BX_uploadTable   BX_manualTable

                    var checked = $(this).attr('checked', true);
                    if(checked){
                        isClosed=1;
                    }
                });

                //Search button
                $("#submitBtn").click(function(){
                    var validateFields=validateInwardTaskFields();
                    if(validateFields[0]==false){
                        alert(validateFields[1]);
                        return false;
                    }
                });
                
                function submitButtonEnableDisableOnReqSel(id){
                    var selectedCellstatus=$('#taskGrid').getCell(id, 'status');
                    if(selectedCellstatus=="Closed"){
                        $("#submitBtn").attr("disabled", true);
                        $("#rbtnClosed").attr('checked', true);
                        isClosed=1;
                    }else{
                        $("#submitBtn").attr("disabled", false);
                        $("#rbtnWIP").attr('checked', true);
                        isClosed=0;
                    }
                }

                $(function(){
                    $('input#submit').click(function(){
                        var file = $('input[type="file"]').val();
                        var exts = ['doc','docx','rtf','odt'];
                        // first check if file field has any value
                        if ( file ) {
                            // split file name at dot
                            var get_ext = file.split('.');
                            // reverse name to check extension
                            get_ext = get_ext.reverse();
                            // check file type is valid as given in 'exts' array
                            if ( $.inArray ( get_ext[0].toLowerCase(), exts ) > -1 ){
                                alert( 'Allowed extension!' );
                            } else {
                                alert( 'Invalid file!' );
                            }
                        }
                    });
                });
                      
                function validateInwardTaskFields(){
                    if(trnsTaskId==undefined || trnsTaskId==null || trnsTaskId.length==0){
                        return [false,"Please select request"];
                    }
                    
                    var fileid= document.getElementById("taskFile").value;                                       
                    if(isClosed==1){
                        //                        if(fileid==null ||fileid==""){
                        //                            return [false,"Please select file to upload."];
                        //                        }else{
                        //                            var valid_extensions = /(.csv)$/i;
                        //                            if(valid_extensions.test(fileid)){
                        //                            } else{
                        //                                return [false,'Invalid File. Please Upload CSV File Only.'];
                        //                            }
                        //                        }
                    }else if(isClosed==0){
                        if(fileid==null ||fileid==""){
                            //                            return [false,"Please select file to upload."];
                        }else{
                            //var valid_extensions = /(.csv/.xls)$/i;
                            var exts = ['csv','xls','xlsx'];
                            var get_ext = fileid.split('.');
                            // reverse name to check extension
                            get_ext = get_ext.reverse();
                            // check file type is valid as given in 'exts' array
                            if ( $.inArray ( get_ext[0].toLowerCase(), exts ) > -1 ){
                                //return[true,''];
                            } else {
                                return [false,'Invalid File. Please Upload valid File .'];
                            }
                            
                        }
                        
                    }

                    $("#hdnTrnsTaskId").val(trnsTaskId);
                    if(isClosed==1){
                        $("#hdnstatusId").val(closedStatusId);
                    }else{
                        $("#hdnstatusId").val(wipStatusId);
                    }
                    return[true,''];
                }
                
                $("#btnClear").click(function (){
                     $("#message").html('');
                    $("#updateTask").get(0).reset();
                });
            });
        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">

            <s:form id="updateTask" action="updateTask_action" method="POST" enctype="multipart/form-data">
                <s:hidden name="trnsTaskId" id="hdnTrnsTaskId" />
                <s:hidden name="statusId" id="hdnstatusId" />

                <tr>
                    <td><h1>Inward Task</h1></td>
                </tr>
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
                    <td  align="center">
                        <table id="taskGrid"></table>
                        <div id="taskPager"></div>
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
                                <td>
                                    WIP
                                </td>
                                <td>
                                    <input type="radio" id="rbtnWIP" name="taskStatus"  value="52"/>
                                </td>
                                <td>
                                    Closed
                                </td>
                                <td>
                                    <input type="radio" id="rbtnClosed" name="taskStatus" checked="true" value="53"/>
                                </td>
                            </tr>

                            <tr>
                                <td align="right" colspan="2">
                                    File Upload:
                                </td>
                                <td align="left" colspan="2">
                                    <s:file id ="taskFile" name="taskFileUpload"></s:file>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2">
                                    <input type="submit" name="submitBtn" id="submitBtn" value="Submit"  class="btn"/>
                                </td>
                                <td colspan="2">
                                    <input type="button" name="btnClear" id="btnClear" value="Clear"  class="btn"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </s:form>
        </table>
    </body>
</html>

