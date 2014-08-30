<%-- 
    Document   : executePromoDashboard
    Created on : Jan 8, 2013, 4:45:51 PM
    Author     : ajitn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Promo Execute Dashboard</title>
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
                
                $(function() {
                    var dates = $( "#txtStartDate, #txtEndDate" ).datepicker({
                        //                        defaultDate: "+1w",
                        //                        numberOfMonths: 1,
                        changeMonth: true,
                        changeYear: true,
                        dateFormat: 'yy-mm-dd',
                        //maxDate : '+0d',
                        //                        minDate:'+0d',

                        onSelect: function( selectedDate ) {
                            var option = this.id == "txtStartDate" ? "minDate" : "maxDate",
                            instance = $( this ).data( "datepicker" ),
                            date = $.datepicker.parseDate(
                            instance.settings.dateFormat ||
                                $.datepicker._defaults.dateFormat,
                            selectedDate, instance.settings );
                            dates.not( this ).datepicker( "option", option, date );
                        }
                    });
                });

                jQuery("#dashboardGrid").jqGrid({
                    url:"promoExecuteDasboardDtl_action?SearchType=ALL",
                    datatype: 'json',
                    width: 1300,
                    height:230,
                    colNames:['PromotionReq Number','Last Updated Date','Request Date','Request Time','Initiator Name', 'Contact Number','Employee Code','Initiator Location',
                        'Request Name','Campaign','Marketing Type','Category','Sub Category','Promotion Type','Valid from','Valid To','Status','Remark','Approver Name','Approval From','Team Assignment From','Assigned To','Team Member Assigned Date'],
                    colModel:[
                        {name:'reqno',index:'reqno', width:170, align:"center"},
                        {name:'ldate',index:'ldate', width:200, align:"center"},
                        {name:'date',index:'date', width:200, align:"center"},
                        {name:'time',index:'time', width:200, align:"center"},
                        {name:'initiatorName',index:'initiatorName', width:170, align:"center"},
                        {name:'contNo',index:'contNo', width:200, align:"center"},
                        {name:'empCode',index:'empCode', width:200, align:"center"},
                        {name:'initiatorlocation',index:'initiatorlocation', width:200, align:"center"},
                        {name:'reqName',index:'reqName', width:200, align:"center"},
                        {name:'event',index:'event', width:200, align:"center"},
                        {name:'marketingtype',index:'marketingtype', width:200, align:"center"},
                        {name:'category',index:'category', width:200, align:"center"},
                        {name:'subcategory',index:'subcategory', width:200, align:"center"},
                        {name:'promotype',index:'promotype', width:200, align:"center"},
                        {name:'validfrom',index:'validfrom', width:200, align:"center"},
                        {name:'validto',index:'validto', width:200, align:"center"},
                        {name:'status',index:'status', width:200, align:"center"},
                        {name:'remark',index:'remark', width:200, align:"center"},
                        {name:'approvername',index:'approvername', width:200, align:"center"},
                        {name:'approvalfrom',index:'approvalfrom', width:200, align:"center"},
                        {name:'teamassignfrom',index:'teamassignfrom', width:200, align:"center"},
                        {name:'assignby',index:'assignby', width:200, align:"center"},
                        {name:'teamDate',index:'teamDate', width:200, align:"center"}
                        //                        {name:'reject',index:'reject', width:200, align:"center"}
                    ],
                     rowNum:30,
                   rowList:[30],
                    viewrecords: true,
                    pager: '#dashboardPager',
                    multiselect: false,
//                    sortorder: 'desc',
                    emptyrecords:'',
                    recordtext:'',
                    //loadonce:true,
                    // toolbar: [true, "top"], //to print ny message
                    headertitles: true,  // header tooltip
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:"",
                    onSelectRow:function(id){
                        transPromoId=id;
                        fillTeamMembersCombo();
                    }
                });

                //Search button
                $("#searchBtn").click(function(){
                    var validateFields=validateAndFillDashbaordFields();
                    if(validateFields[0]==false){
                        alert(validateFields[1]);
                        $('#assignEmpSel').val("-1");
                        $("#txtStartDate").val('');
                        $("#txtEndDate").val('');
                        $('#assignEmpSel').focus();
                        return false;
                    }                    
                });
                //RESET button
                $("#resetBtn").click(function(){
                    resetDtl();
                });

                function validateAndFillDashbaordFields(){
                    var empId=$('#assignEmpSel').val();
                    var startDate=$("#txtStartDate").val();
                    var endDate=$("#txtEndDate").val();

                    if(empId==-1 && (startDate==undefined || startDate==null || startDate.length==0)){                        
                        return[false,"Please select assign team member OR date."];
                    }else if(empId!=-1 &&  startDate.length>0){                        
                        return[false,"Please select assign team member OR date."];                        
                    }else if( startDate.length>0 &&  endDate.length==0 ){
                        return[false,"Please select end date."];
                    }

                    //Validation compeleted
                    var searchType="ALL";
                    if(empId!=-1){
                        searchType="TEAM_MEMBER";
                    }else{
                        searchType="DATE";
                    }
                    $("#dashboardGrid").jqGrid("clearGridData", true);
                    jQuery("#dashboardGrid").jqGrid('setGridParam',{url:'promoExecuteDasboardDtl_action?SearchType='+searchType+'&teamMember='+empId+'&startDate='+startDate+'&endDate='+endDate,datatype:'json',page:1}).trigger("reloadGrid");

                    return[true,''];
                }
                function clearDate(){
                    var dates = $("input[id$='txtStartDate'], input[id$='txtEndDate']");
                    dates.attr('value', '');
                    dates.each(function(){
                        $.datepicker._clearDate(this);
                    });
                }
                function resetDtl(){
                    clearDate();
                    $('#assignEmpSel').val("-1");
                    $("#txtStartDate").val('');
                    $("#txtEndDate").val('');
                    $('#promomanagerDash')[0].reset();
                    $("#dashboardGrid").jqGrid("clearGridData", true);
                    jQuery("#dashboardGrid").jqGrid('setGridParam',{url:'promoExecuteDasboardDtl_action?SearchType=ALL',datatype:'json',page:1}).trigger("reloadGrid");
                }
                
            });
        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">
            <s:form action="donothing" id="promomanagerDash" name="promomanagerDash">
                <tr>
                    <td><h1>Promo Execute Dashboard :</h1></td>
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
                    <td align="center">
                        <table>
                            <tr>
                                <td>
                                    Team Member Assign :
                                </td>
                                <td>
                                    <s:select headerKey="-1" headerValue="---Select---" list="teamMemberList" id="assignEmpSel" name="assignEmpSel"  cssClass="dropdown"/>
                                </td>
                                <td>Assigned Start Date :</td>
                                <td><s:textfield id="txtStartDate" name="startDate"  readonly="true" /></td>
                                <td>Assigned End Date :</td>
                                <td><s:textfield id="txtEndDate" name="endDate" readonly="true" /></td>
                                <td>
                                    <input type="button" name="searchBtn" id="searchBtn" value="Search"  class="btn"/>
                                </td>
                                <td>
                                    <input type="button" name="resetBtn" id="resetBtn" value="Clear"  class="btn"/>
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
                <tr>
                    <td  align="center">
                        <table id="dashboardGrid"></table>
                        <div id="dashboardPager"></div>
                    </td>
                </tr>
            </s:form>
        </table>
    </body>
</html>
