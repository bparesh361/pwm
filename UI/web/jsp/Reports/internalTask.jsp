<%--
    Document   : createSubPromoFile
    Created on : Feb 15, 2013, 1:45:38 PM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reports</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
        <link href="css/thickbox.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>
        <script type="text/javascript" src="js/thickbox.js"></script>        
        <script type="text/javascript">
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });
                
                
                jQuery("#reqGrid").jqGrid({
                    url:"getAllReportForInternalTask",
                    datatype: 'json',
                    width: 875,
                    height:150,
                    colNames:['Report Request ID','Request Date ','From Date','To Date','Task Type','Status','Remarks','File'],
                    colModel:[
                        {name:'no',index:'no', width:210, align:"center",sortable: false },
                        {name:'rdate',index:'rdate', width:200, align:"center",sortable: false },
                        {name:'rinitiationDateFrom',index:'rinitiationDateFrom', width:200, align:"center",sortable: false },
                        {name:'rinitiationDateTo',index:'rinitiationDateTo', width:200, align:"center",sortable: false },
                        {name:'tasktype',index:'tasktype', width:200, align:"center",sortable: false },
                        {name:'status',index:'status', width:200, align:"center",sortable: false },
                        {name:'remarks',index:'remarks', width:200, align:"center",sortable: false },
                        {name:'file',index:'file', width:200, align:"center",sortable: false },
                    ],
                    rowNum:30,
                    rowList:[30],
                    viewrecords: true,
                    headertitles: true,
                    pager: '#reqPager',
                    multiselect: false,
                   // loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:""                    
                }).navGrid('#reqPager',
                {add:false, edit:false, del:false, search:false, refresh: false},
                {width:"auto"},// Default for Add
                {width:"auto"},// Default for edit
                {width:"auto"}// Default for Delete
            ).navButtonAdd(
                '#reqPager',
                {
                    caption:"Refresh",
                    buttonicon:"ui-icon-gear",
                    onClickButton: function(){
                        var urlStr='getAllReportForInternalTask';
                        jQuery("#reqGrid").jqGrid('setGridParam',{url:urlStr,page:1}).trigger("reloadGrid");

                    }
                }
            );
                $(function() {
                    var dates = $( "#popup_container1, #popup_container2" ).datepicker({
                        //defaultDate: "+1w",
                        //numberOfMonths: 1,
                        changeMonth: true,
                        changeYear: true,
                        dateFormat: 'yy-mm-dd',
                        // maxDate : '+0d',
                        //minDate:'+0d',

                        onSelect: function( selectedDate ) {
                            var option = this.id == "popup_container1" ? "minDate" : "maxDate",
                            instance = $( this ).data( "datepicker" ),
                            date = $.datepicker.parseDate(
                            instance.settings.dateFormat ||
                                $.datepicker._defaults.dateFormat,
                            selectedDate, instance.settings );
                            dates.not( this ).datepicker( "option", option, date );
                        }

                    });
                });
                $("#btnClear").click(function (){
                 jqueryclearDate("popup_container1","popup_container2");
                    $('#msg').html('');
                    $('#uploadFile')[0].reset();
                    jQuery("#reqGrid").jqGrid('setGridParam',{url:"getAllReportForInternalTask",datatype:'json',page:1}).trigger("reloadGrid");
                });
                var startDate,endDate,taskType;
                $("#btnSearch").click(function (){
                    startDate=$("#popup_container1").val();
                    endDate=$("#popup_container2").val();

                    taskType=$("#taskTypeSel option:selected").val();                   
                      if((startDate==undefined || startDate==null || startDate.length==0) && (endDate==undefined || endDate==null || endDate.length==0)){
                        alert("Please select Task Assigned Date.");
                        return false;
                    }else if(startDate==undefined || startDate==null || startDate.length==0){
                        alert("Please select Task Assigned Date From.");
                        return false;
                    }else if(endDate==undefined || endDate==null || endDate.length==0){
                        alert("Please select Task Assigned Date To.");
                        return false;
                    }else if((startDate!=null || startDate.length>0) && (endDate!=null || endDate.length>0) && (taskType==-1 ||taskType==null)  ){
                        $("#reporttype").val("TASK_ASSIGNED_DATE");
                    }else if((startDate!=null || startDate.length>0) && (endDate!=null || endDate.length>0) && (taskType!=-1 ||taskType!=null)){
                        $("#reporttype").val("TASK_ASSIGNED_DATE_TASK_TYPE");
                    }
                });

            });
        </script>
    </head>
    <body >
        <jsp:include page="/jsp/menu/menuPage.jsp" />        
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">
            <tr>
                <td>
                    <table  width="30%">
                        <tr>
                            <td align="center">
                                <h1>Internal Task</h1>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td width="100%" align="center" >
                    <div id="msg">
                        <s:actionmessage cssClass="successText"/>
                        <s:actionerror cssClass="errorText"/>
                    </div>
                </td>
            </tr>
               <tr><td height="5px" ></td></tr>
            <tr>
                <td align="center">
                    <table cellspacing="4" cellpadding="4" width="60%">
                        <s:form  id="uploadFile"  action="submittaskReportRequest" method="POST">
                            <s:token name="token"></s:token>
                            <s:hidden name="formVo.reporttype" id="reporttype" />
                            <tr>
                                <td>
                                    <table>
                                        <tr>
                                            <td align="right">Task Assigned Date </td>
                                            <td class="errorText">*&nbsp;&nbsp;</td>
                                            <td align="left"> From &nbsp;  <input name="formVo.startDate" type="text" class="datef" id="popup_container1" readonly="readonly"/>
                                                &nbsp;&nbsp;&nbsp;To &nbsp;
                                                <input name="formVo.endDate" type="text" class="datef" id="popup_container2" readonly="readonly"/>
                                                &nbsp;&nbsp;&nbsp;</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table>

                                        <tr><td height="5px" ></td></tr>
                                        <tr>
                                            <td  align="right">Task Type</td>
                                            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                            <td align="left" ><s:select name="formVo.taskTypeSel" id="taskTypeSel"  list="taskTypeMap" headerKey="-1" headerValue="---Select Task type---" value="---Select Task Type---" cssClass="dropdown"/></td>
                                        </tr>
                                        
                                    </table>
                                </td>

                            </tr>
                            <tr><td></td></tr>
                            <tr id="searchTR" >
                                <td colspan="8" align="center">
                                    <table align="center" >
                                        <tr>
                                            <td align="center" >
                                                <s:submit  type="button" name="btnSearch" id="btnSearch" value="Submit" cssClass="btn"/>                                           
                                            </td>
                                            <td>
                                                <input type="button" name="btnClear" id="btnClear" value="Clear" class="btn"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </s:form>
                    </table>
                </td>
            </tr>
            <tr>
                <td  align="center">
                    <table id="reqGrid"></table>
                    <div id="reqPager"></div>
                </td>
            </tr>
        </table>
        <!--</div>-->

    </body>
</body>
</html>