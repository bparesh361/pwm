<%-- 
    Document   : flatdiscount
    Created on : Dec 26, 2012, 12:32:45 PM
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
        <title>Promotion Initiation Dashboard</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
        <link href="css/tabs.css" rel="stylesheet" type="text/css" />
        <link href="css/thickbox.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/Tabfunction.js"></script>
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>
        <script type="text/javascript" src="js/PromotionCommonUtil.js"></script>
        <script type="text/javascript" src="js/thickbox.js"></script>
        <script type="text/javascript">
            var glbReqId, idsOfSelectedRows = [];
            var  selectedRowsIdAndStatus = [];
            $(function () {
                jQuery("#reqGrid").jqGrid({
                    url:"getallPromoreq_dashboard?searchType=ALL",
                    datatype: 'json',
                    width: 1200,
                    height:250,
                    colNames:[' mstreqid','Promotion Req Number','Transpromo','Promotion Req Number','Last Updated Date','Request Date','Request Name','Promo type', 'Campaign','Marketing Type','Objective','Category','Sub Category','Status','Reason Rejection','Download','statusid','Update','Error File'],
                    colModel:[
                        {name:'mstreqid',index:'mstreqid', width:170, align:"center" ,hidden:true},
                        {name:'reqno',index:'reqno', width:170, align:"center" ,hidden:true},
                        {name:'transid',index:'transid', width:170, align:"center",hidden:true},
                        {name:'preqno',index:'preqno', width:170, align:"center"},
                        {name:'ldate',index:'ldate', width:200, align:"center"},
                        {name:'rdate',index:'rdate', width:200, align:"center"},
                        {name:'reqName',index:'reqName', width:170, align:"center"},
                        {name:'promotype',index:'promotype', width:170, align:"center"},
                        {name:'event',index:'event', width:200, align:"center"},
                        {name:'mktgtype',index:'mktgtype', width:200, align:"center"},
                        {name:'campaign',index:'campaign', width:200, align:"center"},
                        {name:'category',index:'category', width:200, align:"center"},
                        {name:'subcategory',index:'subcategory', width:200, align:"center"},
                        {name:'status',index:'status', width:200, align:"center"},
                        {name:'reasonRejection',index:'reasonRejection', width:200, align:"center"},
                        {name:'downlodarti',index:'downlodarti', width:200, align:"center",hidden:true},
                        {name:'statusID',index:'statusID', width:200, align:"center",hidden:true},
                        {name:'downlodRequest',index:'downlodRequest', width:200, align:"center",hidden:true},
                        {name:'errorReq',index:'errorReq', width:200, align:"center"},

                    ],
                    rowNum:30,
                    rowList:[30],
                    viewrecords: true,
                    headertitles: true,
                    pager: '#reqPager',
                    multiselect: true,
                    //emptyrecords:'',
                    //recordtext:'',
                    //loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:"",
                    onSelectRow: function(id,isSelected) {
                        gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);
                        $("#message").val("");
                        var mstreqno =$('#reqGrid').getCell(id,'mstreqid');
                        $("#mstPromoId").val(mstreqno);
                        var transid =$('#reqGrid').getCell(id,'transid');
                        $("#mstSubPromoId").val(transid);
                        var statusid =$('#reqGrid').getCell(id,'statusID');
                        $("#statusID").val(statusid);

                        var ticket_Number=$('#reqGrid').getCell(id,'preqno');
                        // alert(ticket_Number);
                        var page_no=$('#reqGrid').getGridParam('page');
                        //alert(page_no);
                        var id_status_id=ticket_Number+"-"+statusid+"-"+page_no;
                        gridPageNavigationPersistRowIDAndStatus(id_status_id,isSelected,selectedRowsIdAndStatus);

                        //                        var selid = $('#reqGrid').jqGrid('getGridParam','selarrrow');
                        //                        for(var i =0;i<selid.length;i++){
                        //                            var statusid =$('#reqGrid').getCell(selid[i],'statusID');
                        //                            if(statusid=="11" || statusid=="6" || statusid=="5"){
                        //                                $("#btnDelete").attr("disabled",false);
                        //                                $("#btnHeaderRedirect").attr("disabled",false);
                        //                                $("#btnUpdateRequest").attr("disabled",false);
                        //                            }else{
                        //                                $("#btnDelete").attr("disabled",true);
                        //                                $("#btnHeaderRedirect").attr("disabled",true);
                        //                                $("#btnUpdateRequest").attr("disabled",true);
                        //                            }
                        //                            if(statusid=="6" || statusid=="5"){
                        //                                $("#btnCreateCopy").attr("disabled",true);
                        //                                if(statusid=="6"){
                        //                                    $("#btnUpdateRequest").attr("disabled",false);
                        //                                }else{
                        //                                    $("#btnUpdateRequest").attr("disabled",true);
                        //                                }
                        //                            }else{
                        //                                $("#btnCreateCopy").attr("disabled",false);
                        //                            }
                        //                        }
                    },
                    onSelectAll: function (aRowids, isSelected) {
                        var i, id;
                        for (i = 0; i < aRowids.length; i++) {
                            id = aRowids[i];
                            gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);
                        }
                        //                        $("#btnDelete").attr("disabled",true);
                        //                        $("#btnHeaderRedirect").attr("disabled",true);
                        //                        $("#btnUpdateRequest").attr("disabled",true);
                        //                        $("#btnUpdateRequest").attr("disabled",true);

                        var ticket_Number=$('#reqGrid').getCell(id,'preqno');
                        var statusid =$('#reqGrid').getCell(id,'statusID');
                        // alert(ticket_Number);
                        var page_no=$('#reqGrid').getGridParam('page');
                        //alert(page_no);
                        var id_status_id=ticket_Number+"-"+statusid+"-"+page_no;
                        gridPageNavigationPersistRowIDAndStatus(id_status_id,isSelected,selectedRowsIdAndStatus);
                    },
                    loadComplete: function () {
                        var $this = $(this), i, count;
                        for (i = 0, count = idsOfSelectedRows.length; i < count; i++) {
                            $this.jqGrid('setSelection', idsOfSelectedRows[i], false);
                        }
                    }
                });
            });

            function checkStatusIdInSelectedRows(){
                if(selectedRowsIdAndStatus.length>0){
                    for (i = 0; i < selectedRowsIdAndStatus.length; i++) {

                        var row_id_status_id=(selectedRowsIdAndStatus[i]).split("-");
                        var ticket_number=row_id_status_id[0]+"-"+row_id_status_id[1];
                        var status_id=row_id_status_id[2];
                        var page_no=row_id_status_id[3];
                        //                        alert("-- row Id : "+ticket_number);
                        //                        alert("-- status Id : "+status_id);
                        if(status_id!="11" && status_id!="6" && status_id!="5"){
                            return[false,"You can not Delete or Update Header for Promotion Request Number : "+ticket_number+" On Page No : "+page_no+" ."];
                        }
                        //                        if(status_id=="6" || status_id=="5"){
                        //                            return[false,"You can not create copy of Promotion Request Number : "+ticket_number+" On Page No : "+page_no+" ."];
                        //                        }
                    }
                }
                return [true,''];
            }
            function checkStatusIdInSelectedRowsForCreateCopy(){
                if(selectedRowsIdAndStatus.length>0){
                    for (i = 0; i < selectedRowsIdAndStatus.length; i++) {

                        var row_id_status_id=(selectedRowsIdAndStatus[i]).split("-");
                        var ticket_number=row_id_status_id[0]+"-"+row_id_status_id[1];
                        var status_id=row_id_status_id[2];
                        var page_no=row_id_status_id[3];
                        if(status_id=="6" || status_id=="5"){
                            return[false,"You can not create copy of Promotion Request Number : "+ticket_number+" On Page No : "+page_no+" ."];
                        }
                    }
                }
                return [true,''];
            }
            function resetAllFields(){
                loadDataDropdowns();
                // $("#searchTypeSel").val("-1");
                $("#message").html("");
                $("#eventSel").val("-1");
                $("#txtBXGYFrom").val("");
                $("#txtBXGYTo").val("");
                $("#marketingsel").val("-1");
                $("#promotionSel").val("-1");
                $("#categorySel").val("-1");
                $("#statusSel").val("-1");

            }

            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });
                
                //$("#btnDelete").attr("disabled",true);
                //$("#btnHeaderRedirect").attr("disabled",true);
                $("#updateFileUploadTR").hide();

                function loadDataDropdowns(){
                    var dates = $( "#txtBXGYFrom, #txtBXGYTo" ).datepicker({
                        //                        defaultDate: "+1w",
                        //                        numberOfMonths: 1,
                        changeMonth: true,
                        changeYear: true,
                        dateFormat: 'yy-mm-dd',
                        // maxDate : '+0d',
                        //minDate:'+0d',
                      
                        onSelect: function( selectedDate ) {
                            var option = this.id == "txtBXGYFrom" ? "minDate" : "maxDate",
                            instance = $( this ).data( "datepicker" ),
                            date = $.datepicker.parseDate(
                            instance.settings.dateFormat ||
                                $.datepicker._defaults.dateFormat,
                            selectedDate, instance.settings );
                            dates.not( this ).datepicker( "option", option, date );
                        }
                    });
                }
                loadDataDropdowns();
                function clearDate(){
                    var dates = $("input[id$='txtBXGYFrom'], input[id$='txtBXGYTo']");
                    dates.attr('value', '');
                    dates.each(function(){
                        $.datepicker._clearDate(this);
                    });
                }

                $("#btnReset").click(function (data){
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    clearDate();
                    $("#message").html('');
                    $("#mstPromoId").val('');
                    $("#mstSubPromoId").val('');                    
                    $("#btnCreateCopy").attr("disabled",false);
                    $("#btnHeaderRedirect").attr("disabled",false);
                    $("#updateFileUploadTR").hide();
                    $('#inititationdashbaord')[0].reset();
                    jQuery("#reqGrid").jqGrid('setGridParam',{url:"getallPromoreq_dashboard?searchType=ALL",datatype:'json',page:1}).trigger("reloadGrid");

                });

                $("#btnDelete").click(function (data){

                    var checkSelectedRowsStatus=checkStatusIdInSelectedRows();
                    if(checkSelectedRowsStatus[0]==false){
                        alert(checkSelectedRowsStatus[1]);
                        return false;
                    }
                    $("#message").val("");
                    
                    //alert(id);
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request.");
                        return false;
                    }
                    //var selid = $('#reqGrid').jqGrid('getGridParam','selarrrow');

                    $("#mstSubPromoId").val(idsOfSelectedRows);
                    var selid=idsOfSelectedRows;
                    if(selid.length>1){
                        alert("Please Select Only One Request For Delete!");
                        return false;
                    }
                    if (confirm('Are you sure you want to delete Request ?')) {
                        return true;
                    }
                    else {
                        return false;
                    }

                });
                $("#btnCreateCopy").click(function (data){
                    var checkSelectedRowsStatus=checkStatusIdInSelectedRowsForCreateCopy();
                    if(checkSelectedRowsStatus[0]==false){
                        alert(checkSelectedRowsStatus[1]);
                        return false;
                    }
                    $("#message").val("");
                    //var id = $("#mstSubPromoId").val();
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request.");                       
                        return false;
                    }
                    //var selid = $('#reqGrid').jqGrid('getGridParam','selarrrow');
                    var selid=idsOfSelectedRows;

                    $("#mstSubPromoId").val(idsOfSelectedRows);
                    if(selid.length>1){
                        alert("Please Select Only One Request For Create Copy!");
                        return false;
                    }
                    if (confirm('Are you sure you want to Create Copy of Request ?')) {
                        return true;
                    }
                    else {
                        return false;
                    }

                });
                $("#btnHeaderRedirect").click(function (data){
                    var checkSelectedRowsStatus=checkStatusIdInSelectedRows();
                    if(checkSelectedRowsStatus[0]==false){
                        alert(checkSelectedRowsStatus[1]);
                        return false;
                    }
                    var id = $("#mstPromoId").val();                  
                    if(id.length==0){
                        alert("Please Select Request.");
                        return false;
                    }
                    //var selid = $('#reqGrid').jqGrid('getGridParam','selarrrow');
                    var selid=idsOfSelectedRows;
                    if(idsOfSelectedRows.length==0 || selid.length>1){
                        alert("Please Select Only One Request For Header Update!");
                        return false;
                    }

                });

                $("#btnUpdateRequest").click(function (){
                    var hdnSubPromoId=$("#mstSubPromoId").val();
                    if(hdnSubPromoId==undefined || hdnSubPromoId==null || hdnSubPromoId.length==0){
                        alert("Please Request.");
                        return false;
                    }
                    var selid = $('#reqGrid').jqGrid('getGridParam','selarrrow');
                    if(selid.length>1){
                        alert("Please Select Only One Request For Header Update!");
                        return false;
                    }
                    $("#updateFileUploadTR").show();
                    $("#btnDelete").attr("disabled",true);
                    $("#btnCreateCopy").attr("disabled",true);
                    $("#btnHeaderRedirect").attr("disabled",true);
                });
                // DisabledAllFields();
                function DisabledAllFields(){
                    // $("#searchTypeSel").val("-1");
                    $("#eventSel").attr("disabled",true);
                    $("#txtBXGYFrom").attr("disabled",true);
                    $("#txtBXGYTo").attr("disabled",true);
                    $("#marketingsel").attr("disabled",true);
                    $("#promotionSel").attr("disabled",true);
                }
            
                $("#btnSearch").click(function (){
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    $("#message").html('');
                    var url;
                    var validFrom=$("#txtBXGYFrom").val();
                    var validTo=$("#txtBXGYTo").val();
                    var event=$("#eventSel").val();
                    var mktg=$("#marketingsel").val();
                    var promotype=$("#promotionSel").val();
                    var category =$("#categorySel").val();
                    var status =$("#statusSel").val();

                    if(validFrom==undefined || validFrom==null || validFrom.length==0){
                        alert("Please select valid from date.");
                        return false;
                    }else if(validTo==undefined || validTo==null || validTo.length==0){
                        alert("Please select valid to date.");
                        return false;
                    }else{
                        //                        if(event==-1 && mktg==-1 && promotype==-1){
                        //                            alert("Please Select Event or Marketing Type or Promotion Type!");
                        //                            $("#eventSel").val("-1");
                        //                            $("#marketingsel").val("-1");
                        //                            $("#promotionSel").val("-1");
                        //                            return false;
                        //                        }
                        if((validFrom!=null || validFrom.length>0) && (validTo!=null || validTo.length>0) && event==-1 && mktg==-1 && promotype==-1 && category==-1 && status==-1){
                            url='getallPromoreq_dashboard?searchType=DATE&startDate='+validFrom+'&endDate='+validTo;
                        }else if((event!=-1 || event!=null)  && (mktg==-1 || mktg==null)  && (promotype==-1 || promotype==null) && (category==-1 || category==null) && (status==-1 || status==null)){
                            url='getallPromoreq_dashboard?searchType=DATE_EVENT&eventSel='+event+"&startDate="+validFrom+'&endDate='+validTo;
                        }else  if((event==-1 || event==null)  && (mktg!=-1 || mktg!=null)  && (promotype==-1 || promotype==null) && (category==-1 || category==null) && (status==-1 || status==null) ){
                            url='getallPromoreq_dashboard?searchType=DATE_MARKETING_TYPE&marketingsel='+mktg+"&startDate="+validFrom+'&endDate='+validTo;
                        }else  if((event==-1 || event==null)  && (mktg==-1 || mktg==null)  && (promotype!=-1 || promotype!=null) && (category==-1 || category==null) && (status==-1 || status==null) ){
                            url='getallPromoreq_dashboard?searchType=DATE_PROMOTION_TPYE&promotionSel='+promotype+"&startDate="+validFrom+'&endDate='+validTo;
                        }else  if((event==-1 || event==null)  && (mktg==-1 || mktg==null)  && (promotype==-1 || promotype==null) && (category!=-1 || category!=null) && (status==-1 || status==null) ){
                            url='getallPromoreq_dashboard?searchType=SUB_CATEGORY_DATE&categoryName='+category+"&startDate="+validFrom+'&endDate='+validTo;
                        }else  if((event==-1 || event==null)  && (mktg==-1 || mktg==null)  && (promotype==-1 || promotype==null) && (category==-1 || category==null) && (status!=-1 || status!=null) ){
                            url='getallPromoreq_dashboard?searchType=STATUS_DATE&statusSel='+status+"&startDate="+validFrom+'&endDate='+validTo;
                        }else  if((event!=-1 || event!=null)  && (mktg==-1 || mktg==null)  && (promotype==-1 || promotype==null) && (category==-1 || category==null) && (status!=-1 || status!=null) ){
                            url='getallPromoreq_dashboard?searchType=STATUS_DATE_EVENT_TYPE&statusSel='+status+"&eventSel="+event+"&startDate="+validFrom+'&endDate='+validTo;
                        }else  if((event==-1 || event==null)  && (mktg!=-1 || mktg!=null)  && (promotype==-1 || promotype==null) && (category==-1 || category==null) && (status!=-1 || status!=null) ){
                            url='getallPromoreq_dashboard?searchType=STATUS_DATE_MARKETTING_TYPE&statusSel='+status+"&marketingsel="+mktg+"&eventSel="+event+"&startDate="+validFrom+'&endDate='+validTo;
                        }else  if((event==-1 || event==null)  && (mktg==-1 || mktg==null)  && (promotype!=-1 || promotype!=null) && (category==-1 || category==null) && (status!=-1 || status!=null) ){
                            url='getallPromoreq_dashboard?searchType=STATUS_DATE_PROMO_TYPE&statusSel='+status+"&promotionSel="+promotype+"&eventSel="+event+"&startDate="+validFrom+'&endDate='+validTo;
                        }else  if((event==-1 || event==null)  && (mktg==-1 || mktg==null)  && (promotype==-1 || promotype==null) && (category!=-1 || category!=null) && (status!=-1 || status!=null) ){
                            url='getallPromoreq_dashboard?searchType=STATUS_DATE_SUB_CATEGORY&statusSel='+status+"&categoryName="+category+"&eventSel="+event+"&startDate="+validFrom+'&endDate='+validTo;
                        }
                        //                        else if((event!=-1 && mktg!=-1) || (event!=-1 && promotype!=-1) || (mktg!=-1 && promotype!=-1)|| (category!=-1 && category!=-1)|| (status!=-1 && status!=-1)){
                        //                            alert("Please Select Category or Status or Event or Marketing Type or Promotion Type.");
                        //                            $("#eventSel").val("-1");
                        //                            $("#marketingsel").val("-1");
                        //                            $("#promotionSel").val("-1");
                        //                            $("#categorySel").val("-1");
                        //                            $("#statusSel").val("-1");
                        //                            return false;
                        //                        }
                        else if(status !=null && status!=-1){
                            if((event!=-1 && mktg!=-1) || (event!=-1 && promotype!=-1) || (mktg!=-1 && promotype!=-1)|| (category!=-1 && category!=-1)){
                                alert("Please Select Sub Category or Campaign or Marketing Type or Promotion Type.");
                                $("#eventSel").val("-1");
                                $("#marketingsel").val("-1");
                                $("#promotionSel").val("-1");
                                $("#categorySel").val("-1");
                                return false;
                            }
                        }else{
                            if((event!=-1 && mktg!=-1) || (event!=-1 && promotype!=-1) || (mktg!=-1 && promotype!=-1)|| (category!=-1 && category!=-1)){
                                alert("Please Select Sub Category or Campaign or Marketing Type or Promotion Type along with Status.");
                                $("#eventSel").val("-1");
                                $("#marketingsel").val("-1");
                                $("#promotionSel").val("-1");
                                $("#categorySel").val("-1");
                                return false;
                            }
                            // url='getallPromoreq_dashboard?searchType=ALL';
                        }
                    }
                    $("#reqGrid").jqGrid("clearGridData", true);
                    jQuery("#reqGrid").jqGrid('setGridParam',{url:url,datatype:'json',page:1}).trigger("reloadGrid");
                  
                });

                $("#btnUploadFile").click(function (){
                    //File Valdations
                    var fileid=  document.getElementById("subPromoFile").value;
                    if(fileid==null ||fileid==""){
                        alert("Please select file to upload.");
                        return false;
                    }
                    var valid_extensions = /(.csv)$/i;
                    if(valid_extensions.test(fileid)){
                    } else{
                        alert("Invalid File. Please Upload CSV File Only.");
                        return false;
                    }
                });
                //Download button
                $("#downloadBtn").click(function(){
                    $("#message").html('');                   
                    var  empId=   $("#hdempId").val();
                    // var id = $('#reqGrid').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request !");
                        return false;
                    }else{
                        var iframe = document.createElement("iframe");
                        iframe.src = "downloadMultipleExcel?transId="+idsOfSelectedRows+"&empID="+empId+"&downloadType=INITIATOR_TO_EXIGENCY_DOWNLOAD";
                        iframe.style.display = "none";
                        document.body.appendChild(iframe);
                    }

                });

                

            });
        </script>      
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />

        <div class="main_nav" >
            <s:form id="inititationdashbaord" action="donothing" method="POST" enctype="multipart/form-data">
                <s:hidden name="mstPromoId" id="mstPromoId" />
                <s:hidden name="mstSubPromoId" id="mstSubPromoId" />

                <s:hidden name="statusID" id="statusID" />

                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">
                    <tr>
                        <td><h1>View Requests :</h1></td>
                        <td align="center" >
                            <a href ="#" class="download-sample " onclick="tb_show( '', 'viewModifyPromoHelp?height=150&width=650');">
                                Help
                            </a>
                        </td>
                    </tr>
                    <tr>
                        <td height="5px"></td>
                    </tr>
                    <tr>
                        <td>
                            <table align="center" width="50%">
                                <tr>
                                    <td width="100%" align="center" >
                                        <div id="message">
                                            <s:actionmessage cssClass="successText"/>
                                            <s:actionerror cssClass="errorText"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="left"><input type="hidden" name="hdempId" id="hdempId"  value="<%= session.getAttribute(WebConstants.EMP_ID)%>" disabled/></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td height="10px"></td>
                    </tr>
                    <tr>
                        <td colspan="2" align="left">
                            <table align="center" >
                                <tr>
                                    <%-- <td>Search Type :</td>
                                    <td><s:select   list="#{'-1':'---Select---','1':'Date','2':'Event Type','3':'Marketing Type','4':'Promotion Type'}" id="searchTypeSel"  cssClass="dropdown"/>
</td>--%>
                                    <td>Start Date :<span class="errorText">*&nbsp;</span></td>
                                    <td><s:textfield id="txtBXGYFrom"  readonly="true"/></td>
                                    <td>End Date :<span class="errorText">*&nbsp;</span></td>
                                    <td><s:textfield id="txtBXGYTo" readonly="true"/></td>
                                    <td align="right">
                                        Status:
                                    </td>
                                    <td align="left">
                                        <s:select name="statusSel" id="statusSel" list="statutsMap"  headerKey="-1" headerValue="---Select----"/>
                                    </td>
                                    <td align="right">
                                        Sub Category :
                                    </td>
                                    <td align="left">
                                        <s:select name="categoryName" id="categorySel" list="categoryMap"  headerKey="-1" headerValue="---Select----"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td height="3px"></td>
                                </tr>
                                <tr>
                                    <td align="right">Campaign</td>
                                    <td align="left"><s:select name="eventSel" id="eventSel"  list="eventMap" headerKey="-1" headerValue="---Select Campaign---" value="---Select Campaign---" cssClass="dropdown"/></td>
                                    <td align="right">Marketing Type</td>
                                    <td align="left"><s:select name="marketingsel" id="marketingsel"  list="mktgMap" headerKey="-1" headerValue="---Select Marketing ---" value="---Select Marketing ---" cssClass="dropdown"/></td>
                                    <td align="right">Promotion Type</td>
                                    <td align="left"><s:select name="promotionSel" id="promotionSel"  list="promotionMap" headerKey="-1" headerValue="---Select Promotion Type---" value="---Select Promotion Type---" cssClass="dropdown" /></td>
                                    <td align="center">
                                        <input type="button" value="Search" name="Search" id="btnSearch" class="btn"/>
                                    </td>
                                    <td align="center">
                                        <input align="left" type="button" id="btnReset" name="btnReset"  Value="Clear" Class="btn" />
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td style="height: 10px;"></td>
                    </tr>

                    <tr>
                        <td  align="center">
                            <table id="reqGrid"></table>
                            <div id="reqPager"></div>
                        </td>
                    </tr>
                    <tr>
                        <td height="10px"></td>
                    </tr>

                    <tr>
                        <td align="center">
                            <table width="35%">
                                <tr>
                                    <td align="right">
                                        <s:submit action="deletePromoRequest" id="btnDelete" name="btnDelete" cssClass="btn" value="Delete"/>
                                        <!--                                        <input align="left" type="submit" id="btnDelete" name="btnDelete"  Value="Delete" Class="btn" />-->
                                    </td>
                                    <td align="right">
                                        <s:submit action="createCopyRequest" id="btnCreateCopy" name="btnCreateCopy" cssClass="btn" value="Create Copy"/>
                                        <!--                                        <input align="left" type="submit" id="btnDelete" name="btnDelete"  Value="Delete" Class="btn" />-->
                                    </td>
                                    <td style="height: 200%">
                                        <input type="button" name="downloadBtn" id="downloadBtn" value="Download"  class="btn"/>
                                    </td>
                                    <td align="center">
                                        <s:submit id="btnHeaderRedirect" name="btnHeaderRedirect"  value="Update Header" cssClass="largebtn" action="updatePromoHeaderRedirect"   />
                                    </td>
                                    <!--                                    <td align="center">
                                                                            <input type="button" id="btnUpdateRequest" name="btnUpdateRequest" value="Update Request" Class="largebtn" disabled/>
                                                                        </td>-->
                                </tr>
                                <tr>
                                    <%--    <tr id="updateFileUploadTR">
                                    <td colspan="5" align="center">
                                        <table>
                                            <tr>
                                                <td colspan="3" style="height: 10px;"></td>
                                            </tr>
                                            <tr>
                                                <td align="right">Upload File</td>
                                                <td align="left">
                                                    <s:file id ="subPromoFile" name="subPromoFileUpload" ></s:file>
                                                </td>
                                                <td align="center">
                                                    <s:submit id="btnUploadFile" name="btnUploadFile"  value="Upload" cssClass="btn" action="updateSubPromoFileUpload"   />
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                    --%>
                            </table>
                        </td>
                    </tr>
                </s:form>
            </table>
        </div>
    </body>
</html>

