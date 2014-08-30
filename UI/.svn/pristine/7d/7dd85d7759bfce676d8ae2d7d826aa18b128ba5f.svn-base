<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lead Time Configuration</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script src="js/jquery_ui_validations.js" type="text/javascript"></script>
        <script type="text/javascript">
            jQuery(document).ready(function(){
                var lastsel2;
                jQuery("#list4").jqGrid({
                    url:"getStatusForLeadTime",
                    datatype: 'json',
                    width: 530,
                    height:210,
                    colNames:['Status Id','Status','L1','L2','L5'],
                    colModel:[
                        {name:'id',index:'id', width:60, align:"center", hidden:true, editable:true},
                        {name:'status',index:'status', width:250, align:"center"},   
                        {name:'L1',index:'L1', width:250, align:"center", editable:true,edittype:'text',editoptions: { size:15 , maxlength: 12} ,editrules:{custom:true, custom_func:validateTAT }},   
                        {name:'L2',index:'L2', width:250, align:"center", editable:true,edittype:'text',editoptions: { size:15 , maxlength: 12},editrules:{custom:true, custom_func:validateTAT }},   
                        {name:'L5',index:'L5', width:250, align:"center", editable:true,edittype:'text',editoptions: { size:15 , maxlength: 12} ,editrules:{custom:true, custom_func:validateTAT }},   
                    ],
                    rowNum:10,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#pglist4',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    //caption:'Escalation Lead Time Configuration',
                    editurl: "updateLeadTime",  
                    onSelectRow: function(id){                                     
                        if(id && id!==lastsel2){
                            jQuery('#list4').restoreRow(lastsel2);                            
                            lastsel2=id;
                        }
                        jQuery('#list4').editRow(id,true);
                    }                       
                });                               
                var regValidateDigit = /^(([1-8][0-9]?|9[0-8]?)\.\d+|[1-9][0-9]?)$/;
                function validateTAT(duration){                                           
                    if(duration.length==0){
                        return [false,'Value Can Not Be Blank.'];
                    }
                    if(duration.length>0){                        
                        if(!isNumeric(duration)){
                            return [false,'Value should be numeric !'];
                        }
                        if(!regValidateDigit.test(duration)){
                            return [false,'Value should not be negative and should not be greater than 99'];
                        }
                    }
                    if(duration.length>3){
                        return [false,'Max Value should be 999!'];
                    }
                    
                    return [true,''];
                }
                
                $("#btnSubmit").click(function (){                                       
                    var duration = $("#leadTimeValue").val();
                    duration=$.trim(duration);
                    var checkblank = isBlank(duration,"Duration");
                    if(checkblank[0]==false){
                        alert(checkblank[1]); 
                        return false;
                    }
                    if(duration.length>0){                        
                        if(!isNumeric(duration)){
                            alert('Value should be numeric!');
                            $("#msg").html('');
                            return false;
                        }                        
                    }
                    if(duration.length>3){
                        alert('Max Value should be 999!');
                        $("#msg").html('');
                        return false;
                    }
                                   
                
                });
                
                $.ajax({
                    url: "getLeadTimeForPromoSetUp",
                    cache:false,
                    global: false,
                    type: "POST",
                    dataType:"json",
                    contanttype: 'text/json',
                    async:false,
                    error:function(){
                        alert("Can not connect to server");
                    },
                    success: function(data){                                                
                        $("#leadTimeValue").val(data.leadTimeValue);
                    }                
                });
              
                $("#btnReset").click(function (data){
                    // $("#leadTimeValue").val("");
                    $("#msg").html('');
                    jQuery("#list4").jqGrid('setGridParam',{url:'getStatusForLeadTime',datatype:'json',page:1}).trigger("reloadGrid");


                });
                
            });
        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <s:form action="donothing" method="post">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" >
                <tr>
                    <td>
                        <table  width="100%">
                            <tr><td height="10px"></td></tr>
                            <tr><td align="center"><h1>Escalation Lead Time Configuration</h1></td></tr>
                            <tr align="center">
                                <td colspan="2">
                                    <div id="msg" >
                                        <s:actionmessage id="actionmessage" name="actionmessage" cssClass="successText"/>
                                        <s:actionerror cssClass="errorText" />
                                    </div>
                                </td>
                            </tr>
                            <tr><td style="height:10px"></td></tr>
                            <tr>
                                <td  align="center" colspan="2" >
                                    <table id="list4"></table>
                                    <div id="pglist4"></div>
                                </td>
                            </tr>
                            <tr><td style="height:10px" ></td></tr>                           
                            <tr>
                                <td align="center">
                                    <table align="center">
                                        <tr>
                                            <td>
                                                Lead time for Promo Setup :<span class="errorText">&nbsp;*</span>
                                            </td>
                                            <td>
                                                <input type="text" id="leadTimeValue" name="leadTimeValue"  />
                                            </td>
                                        </tr>                                    
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <table width="40%">
                                        <tr>
                                            <td align="center" >
                                                <s:submit  id="btnSubmit" name="btnSubmit" value="Submit" cssClass="btn" action="updateLeadTimeForPromoSetUp"></s:submit>
                                            </td>
                                            <td align="left">
                                                <input id="btnReset" name="btnReset" type="button" value="Clear"  class="btn"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
            </table>
        </s:form>
        <%--<s:form action="submitCalendarFile" method="post" enctype="multipart/form-data">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" >
                <tr>
                    <td>
                        <table  width="100%">                                                
                            <tr><td style="height:10px" ></td></tr>
                            <tr>
                                <td>
                                    <table width="40%">
                                        <tr>
                                            <td align="center">
                                                <s:file id ="calendarFile" name="calendarFile" ></s:file>
                                            </td>
                                            <td align="right" >
                                                <s:submit  id="btnUpload" name="btnUpload" value="Upload" cssClass="button" ></s:submit>
                                            </td>                                        
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <s:submit id="downloadSampleCalendarFile" name="downloadSampleCalendarFile" value=" Download Master" cssClass="button" action="downloadSampleCalendarFile" ></s:submit>
                    </td>
                </tr>
            </table>
</s:form>--%>
    </body>
</html>

