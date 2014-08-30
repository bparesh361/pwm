<%-- 
    Document   : promo_Execute_Dashboard
    Created on : Jan 7, 2013, 5:26:41 PM
    Author     : ajitn
--%>

<%@page import="com.fks.ui.constants.WebConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Promo Execute Approval</title>
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
            var transPromoId;
            var  idsOfSelectedRows = [];
            var  selectedRowsIdAndStatus = [];
            $(function () {
                jQuery("#dashboardGrid").jqGrid({
                    url:"promoExecuteApprovalDtl_action?SearchType=ALL",
                    //url:"promoExecuteApprovalDtl_action",
                    datatype: 'json',
                    width: 1300,
                    height:230,
                    colNames:['PromotionReq Number','Last Updated Date','Request Date','Request Time','Initiator Name', 'Contact Number','Employee Code','Initiator Location',
                        'Request Name','Campaign','Marketing Type','Category','Sub Category','Promotion Type','Valid from','Valid To','Status','Remark','Approver Name',
                        'Approval From','Team Assignment From','Team Assigned To','statusId'],
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
                        {name:'assignto',index:'assignto', width:200, align:"center"},
                        {name:'statusId',index:'statusId', width:200, align:"center",hidden:true}
                        //                        {name:'reject',index:'reject', width:200, align:"center"}
                    ],
                    rowNum:30,
                    rowList:[30],
                    viewrecords: true,
                    headertitles: true,
                    pager: '#dashboardPager',
                    multiselect: true,
                    // sortorder: 'desc',
                    //                    emptyrecords:'',
                    //                    recordtext:'',
                    //loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:"",
                    onSelectRow:function(id,isSelected){

                        //                        if(transPromoId!=undefined && transPromoId!=null && transPromoId.length>0){
                        //                            var statusid = jQuery("#dashboardGrid").jqGrid('getCell',transPromoId,'statusId');
                        //                            changeRowColor(transPromoId,statusid);
                        //                        }
                        //                        alert("---- is selected : "+isSelected);
                        if(isSelected){
                            var statusid = jQuery("#dashboardGrid").jqGrid('getCell',id,'statusId');
                            changeRowColor(id,statusid);
                            rowSelectionColor(id);
                        }else{
                            var statusid = jQuery("#dashboardGrid").jqGrid('getCell',id,'statusId');
                            changeRowColor(id,statusid);
                        }
                        
                        transPromoId=id;
                        resetAllButton();

                        var statusid =$('#dashboardGrid').getCell(id,'statusId');
                        //                        if(statusid=="27" && isSelected){
                        //                            $("#mstReasonRejectionVOs").attr("disabled",true);
                        //                        }else{
                        //                            $("#mstReasonRejectionVOs").attr("disabled",false);
                        //                        }
                        gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);

                        var ticket_Number=$('#dashboardGrid').getCell(id,'reqno');
                        var page_no=$('#dashboardGrid').getGridParam('page');
                        var id_status_id=ticket_Number+"-"+statusid+"-"+page_no;
                        gridPageNavigationPersistRowIDAndStatus(id_status_id,isSelected,selectedRowsIdAndStatus);
                    }
                    ,onSelectAll: function (aRowids, isSelected) {
                        var i, id;
                        for (i = 0; i < aRowids.length; i++) {
                            id = aRowids[i];
                            gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);

                            if(isSelected){
                                var statusid = jQuery("#dashboardGrid").jqGrid('getCell',id,'statusId');
                                changeRowColor(id,statusid);
                                rowSelectionColor(id);
                            }else{
                                var statusid = jQuery("#dashboardGrid").jqGrid('getCell',id,'statusId');
                                changeRowColor(id,statusid);
                            }

                            var ticket_Number=$('#dashboardGrid').getCell(id,'reqno');
                            var statusid =$('#dashboardGrid').getCell(id,'statusId');
                            var page_no=$('#dashboardGrid').getGridParam('page');
                            var id_status_id=ticket_Number+"-"+statusid+"-"+page_no;
                            gridPageNavigationPersistRowIDAndStatus(id_status_id,isSelected,selectedRowsIdAndStatus);
                        }
                        resetAllButton();
                    }
                    ,gridComplete:function(){
                        //                        $("#cb_"+$("#dashboardGrid")[0].id).hide();
                        var ids =jQuery("#dashboardGrid").jqGrid('getDataIDs');
                        for(var i=0;i < ids.length;i++)
                        {     var rowId = ids[i];
                            var statusid = jQuery("#dashboardGrid").jqGrid('getCell',ids[i],'statusId');
                            changeRowColor(rowId,statusid);
                        }
                    }
                    ,loadComplete: function () {
                        var $this = $(this), i, count;
                        for (i = 0, count = idsOfSelectedRows.length; i < count; i++) {
                            $this.jqGrid('setSelection', idsOfSelectedRows[i], false);
                            rowSelectionColor(idsOfSelectedRows[i]);
                        }
                    }
                });
            });
            
            function resetAllButton(){
                $("#rejectBtn").attr("disabled",true);
                $("#approveBtn").attr("disabled",false);
                $("#mstReasonRejectionVOs").val("-1");
                $("#zoneSel").attr("disabled",false);
                $("#trRemarks").hide();
                $("#assignEmpSel").attr("disabled",false);

            }

            function changeRowColor(rowId,statusId){
                var color='white';
                if(statusId=="25"){
                    // light Blue
                    color='#A5EC9B';
                    // color='#C6DBF5';
                }else if(statusId=="32"){
                    //escalated
                    // light Orange
                    color='#FBD48F';
                }
                $("#dashboardGrid").jqGrid('setRowData', rowId, false, {'background-color':color,color:'black'});
                var trElement = jQuery("#"+ rowId,$('#dashboardGrid'));
                trElement.removeClass("ui-state-hover");
                trElement.addClass('ui-state-disabled');
            }

            function rowSelectionColor(rowId){
                // light yellow
                var color='#F4F6B1';
                $("#dashboardGrid").jqGrid('setRowData', rowId, false, {'background-color':color,color:'black'});
                var trElement = jQuery("#"+ rowId,$('#dashboardGrid'));
                trElement.removeClass("ui-state-hover");
                trElement.addClass('ui-state-disabled');
            }

            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });

                resetAllButton();
                
                fillTeamMembersCombo("");
                $("#buttonshow").hide();
                $("#dateshow").hide();
                $("#savebuttonshow").hide();

                $(function() {
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
                });

                $(function() {
                    var dates = $( "#popup_container1, #popup_container2" ).datepicker({
                        defaultDate: "+1w",
                        numberOfMonths: 1,
                        changeMonth: true,
                        changeYear: true,
                        dateFormat: 'dd-mm-yy',
                        // maxDate : '+0d',
                        minDate:'+0d',

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

                $("#categorySel").change(function(){
                    var txtcategoryname = $("#categorySel option:selected").val();
                    $("#message").html('');
                    $.ajax({
                        url: "fillsubcategroyExigency?txtcategoryname="+txtcategoryname,
                        global: false,
                        type: "POST",
                        dataType:"json",
                        contanttype: 'text/json',
                        async:false,
                        error:function(){
                            alert("Can not connect to server");
                        },
                        success: function(data){
                            //  alert(data);
                            $('#subcategorySel option').each(function(ik, option){
                                $(option).remove();
                            });

                            var select = document.getElementById("subcategorySel");
                            var newoption = document.createElement('option');
                            newoption.text = "---Select Sub Category---";
                            newoption.value = -1;
                            select.add(newoption);

                            if(data!=null){
                                for(var i=0; i<data.rows.subcategroylist.length ; i++){
                                    var optn = document.createElement("OPTION")
                                    optn.text = data.rows.subcategroylist[i];
                                    optn.value = data.rows.subcategroylist[i];
                                    select.add(optn);
                                }
                            }
                        }
                    });
                });
                                                               
                $("#zoneSel").change(function(){
                    var zoneId=$("#zoneSel").val();
                    if(zoneId!=-1){
                        fillTeamMembersCombo(zoneId);
                    }
                });
                
                //Approve button
                $("#approveBtn").click(function(){                    
                    
                    
                    //                    var id = $('#dashboardGrid').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request !");
                        return false;
                    }
                    var empId=$('#assignEmpSel').val();
                   
                    if(empId=="-1"){
                        $('#assignEmpSel').focus();
                        alert("Please select assign team member.");
                        return false;
                    }
                    $("#hdnteamMemberId").val(empId);
                    $("#hdnStatus").val("0");
                    $("#hdntransPromoId").val(idsOfSelectedRows);
                    $("#actionName").val("APPROVE");
                });
                
                //Reject button
                $("#rejectBtn").click(function(){
                    var checkSelectedRowsStatus=checkStatusIdInSelectedRows();
                    if(checkSelectedRowsStatus[0]==false){
                        alert(checkSelectedRowsStatus[1]);
                        return false;
                    }
                    var reason=$("#mstReasonRejectionVOs").val();
                    if(reason=="-1"){
                        alert("Please Select Reason for rejection.");
                        return false;
                    }
                    //                    var id = $('#dashboardGrid').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request !");
                        return false;
                    }else if(reason=="10001"){                     

                        var txtaddRemarks =$("#txtMenualRemarks").val();
                        txtaddRemarks=  txtaddRemarks.replace(/(\r\n|\n|\r)/gm," ");
                        $("#txtMenualRemarks").val(txtaddRemarks);
                        var checkblank = isBlank(txtaddRemarks,"Remarks ");                        
                        if(checkblank[0]==false){
                            $("#txtMenualRemarks").focus()
                            alert(checkblank[1]);
                            return false;
                        }
                        var checklengthresp = checkLength(txtaddRemarks,"Remarks  ",150);
                        if(checklengthresp[0]==false){
                            $("#txtMenualRemarks").focus()
                            alert(checklengthresp[1]);
                            return false;
                        }                      
                    }

                    var reasonText =$("#mstReasonRejectionVOs option:selected").text();
                    var reason=$("#mstReasonRejectionVOs option:selected").val();

                    $("#actionName").val("REJECT");                    
                    $("#hdnreasonRejection").val(reasonText);
                    $("#hdnStatus").val("1");
                    $("#hdntransPromoId").val(idsOfSelectedRows);
                });

                //Download button
                $("#downloadBtn").click(function(){
                   
                    var  empId=   $("#hdempId").val();
                    //                    var id = $('#dashboardGrid').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request !");
                        return false;
                    }else{
                        var iframe = document.createElement("iframe");
                        iframe.src = "downloadMultipleExcel?transId="+idsOfSelectedRows+"&empID="+empId+"&downloadType=MANAGER_EXECUTOR_DOWNLOAD";
                        iframe.style.display = "none";
                        document.body.appendChild(iframe);
                    }

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
                            if(status_id==27){
                                return[false,"Promotion Request Number : "+ticket_number+" On Page No : "+page_no+" Is Already Assigned."];
                            }
                        }
                    }
                    return [true,''];
                }
                function validateExecutionFields(isApprove){                    
                    if(isApprove=="1"){
                        var empId=$('#assignEmpSel').val();
                        if(empId==-1){
                            $('#assignEmpSel').focus();
                            return[false,"Please select assign team member."];
                        }
                        $("#hdnteamMemberId").val(empId);
                        $("#hdnStatus").val("0");
                    }else{
                        $("#hdnStatus").val("1");
                    }
                    //$("#hdntransPromoId").val(transPromoId);
                }
                                                
                $("#trRemarks").hide();
                $("#mstReasonRejectionVOs").change(function(){
                    var reason=$("#mstReasonRejectionVOs").val();
                    // alert(reason);
                    if(reason=="-1"){
                        $("#rejectBtn").attr("disabled",true);
                        $("#approveBtn").attr("disabled",false);
                        $("#zoneSel").attr("disabled",false);
                        $("#assignEmpSel").attr("disabled",false);
                        $("#trRemarks").hide();
                    }else if(reason=="10001"){
                        $("#rejectBtn").attr("disabled",false);
                        $("#approveBtn").attr("disabled",true);
                        $("#zoneSel").attr("disabled",true);
                        $("#assignEmpSel").attr("disabled",true);
                        $("#trRemarks").show();
                    }else{
                        $("#rejectBtn").attr("disabled",false);
                        $("#approveBtn").attr("disabled",true);
                        $("#zoneSel").attr("disabled",true);
                        $("#assignEmpSel").attr("disabled",true);
                        $("#trRemarks").hide();
                        
                    }
                });
                function fillTeamMembersCombo(zoneId){
                    $.ajax({
                        url: "fillTeamMembers_action?zoneId="+zoneId,
                        global: false,
                        type: "POST",
                        dataType:"json",
                        contanttype: 'text/json',
                        async:false,
                        error:function(){
                            alert("Can not connect to server");
                        },
                        success: function(data){
                            //  alert(data);
                            $('#assignEmpSel option').each(function(ik, option){
                                $(option).remove();
                            });

                            var select = document.getElementById("assignEmpSel");
                            var newoption = document.createElement('option');

                            newoption.text = "---Select---";
                            newoption.value = -1;
                            select.add(newoption);
                            //alert(" Doc Dec length : "+data.rows.formatdecList.length);

                            if(data.ListSize!="0"){
                                for(var i=0; i<data.rows.empNameList.length ; i++){
                                    var optn = document.createElement("OPTION")
                                    optn.text = data.rows.empNameList[i];
                                    optn.value = data.rows.empIDList[i];
                                    // alert(optn.text);
                                    //document.reqForm.phaseSel.options.add(optn);
                                    select.add(optn);
                                }
                            }else{
                                alert(data.msg);
                                return false;
                            }

                        }
                    });
                }
                                
                function DisabledAllFields(){
                    // $("#searchTypeSel").val("-1");
                    $("#eventSel").attr("disabled",true);
                    $("#txtBXGYFrom").attr("disabled",true);
                    $("#txtBXGYTo").attr("disabled",true);
                    $("#marketingsel").attr("disabled",true);
                    $("#promotionSel").attr("disabled",true);
                }

                function addoptionToSubCategory(){
                    $("#subcategorySel").empty();
                    var select = document.getElementById("subcategorySel");
                    var newoption = document.createElement('option');
                    newoption.text = "---Select Sub Category---";
                    newoption.value = -1;
                    select.add(newoption);
                }
                function resetAllFields(){
                    // $("#searchTypeSel").val("-1");
                    $("#eventSel").val("-1");
                    $("#txtBXGYFrom").val("");
                    $("#txtBXGYTo").val("");
                    $("#marketingsel").val("-1");
                    $("#promotionSel").val("-1");
                    $("#categorySel").val("-1");
                    $("#subcategorySel").val("-1");
                    addoptionToSubCategory();
                }
                $("#btnSearch").click(function(){
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    var url;
                    
                    $("#msg").html('');
                    var validFrom=$("#txtBXGYFrom").val();
                    var validTo=$("#txtBXGYTo").val();
                    var event=$("#eventSel").val();
                    var mktg=$("#marketingsel").val();
                    var promotype=$("#promotionSel").val();
                    var subcategory=$("#subcategorySel").val();
                    var category=$("#categorySel").val();
                    if(validFrom==undefined || validFrom==null || validFrom.length==0){
                        alert("Please select valid from date.");
                        //                        alert("Please select start date.");
                        return false;
                    }else if(validTo==undefined || validTo==null || validTo.length==0){
                        alert("Please select valid to date.");
                        //                        alert("Please select end date.");
                        return false;
                    }else{                        
                        if((validFrom!=null || validFrom.length>0) && (validTo!=null || validTo.length>0) && event==-1 && mktg==-1 && promotype==-1 && category==-1 && subcategory==-1){
                            url='promoExecuteApprovalDtl_action?searchType=DATE&startDate='+validFrom+'&endDate='+validTo;
                        } else if((event!=-1 || event !=null) &&(mktg==-1 || mktg==null) && (promotype==-1|| promotype==null) && (category==-1|| category==null) && (subcategory==-1|| subcategory==null) ){
                            url='promoExecuteApprovalDtl_action?searchType=DATE_EVENT&startDate='+validFrom+'&endDate='+validTo+"&eventSel="+event;
                        }else if((event==-1 || event==null) &&(mktg!=-1 || mktg!=null) && (promotype==-1|| promotype==null)&& (category==-1|| category==null) && (subcategory==-1|| subcategory==null) ){
                            url='promoExecuteApprovalDtl_action?searchType=DATE_MARKETING_TYPE&marketingsel='+mktg+'&startDate='+validFrom+'&endDate='+validTo;
                        }else if((event==-1 || event==null) &&(mktg==-1 || mktg==null) && (promotype!=-1 || promotype!=null) && (category==-1|| category==null) && (subcategory==-1|| subcategory==null)){
                            url='promoExecuteApprovalDtl_action?searchType=DATE_PROMOTION_TPYE&promotionSel='+promotype+"&startDate="+validFrom+'&endDate='+validTo;
                        }else if((event==-1 || event==null) &&(mktg==-1 || mktg==null) && (promotype==-1 || promotype==null) && (category!=-1|| category!=null) && (subcategory==-1|| subcategory==null)){
                            url='promoExecuteApprovalDtl_action?searchType=CATEGORY_DATE&categoryName='+category+"&startDate="+validFrom+'&endDate='+validTo;
                        }else if((event==-1 || event==null) &&(mktg==-1 || mktg==null) && (promotype==-1 || promotype==null) && (category!=-1|| category!=null) && (subcategory!=-1|| subcategory!=null)){
                            url='promoExecuteApprovalDtl_action?searchType=SUB_CATEGORY_DATE&subcategoryName='+subcategory+"&startDate="+validFrom+'&endDate='+validTo;
                        }  else if((event!=-1 && mktg!=-1) || (event!=-1 && promotype!=-1) || (mktg!=-1 && promotype!=-1) ||(subcategory!=-1 && subcategory!=-1)){
                            alert("Please Select Sub Category or Campaign or Marketing Type or Promotion Type.");
                            $("#eventSel").val("-1");
                            $("#marketingsel").val("-1");
                            $("#promotionSel").val("-1");
                            $("#subcategorySel").val("-1");
                            $("#categorySel").val("-1");
                            addoptionToSubCategory();
                            return false;
                        }
                        else{
                            url='promoExecuteApprovalDtl_action?searchType=DATE&startDate='+validFrom+'&endDate='+validTo;
                        }
                    }
                    $("#dashboardGrid").jqGrid("clearGridData", true);
                    jQuery("#dashboardGrid").jqGrid('setGridParam',{url:url,datatype:'json',page:1}).trigger("reloadGrid");
                });

                function clearDate(){
                    var dates = $("input[id$='txtBXGYFrom'], input[id$='txtBXGYTo']");
                    dates.attr('value', '');
                    dates.each(function(){
                        $.datepicker._clearDate(this);
                    });
                }
                $("#btnReset").click(function (data){
                    $("#msg").html('');
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    clearDate();
                    resetAllButton();
                    resetAllFields();
                    $("#msg").val("");
                    $('#ApproveRejectPromoManager')[0].reset();
                    $("#trRemarks").hide();
                    jQuery("#dashboardGrid").jqGrid('setGridParam',{url:"promoExecuteApprovalDtl_action?SearchType=ALL",datatype:'json',page:1}).trigger("reloadGrid");

                });
            });
        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />        
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">
            <tr><td class="errorText" align="right">(Fields marked with a * are mandatory.)</td></tr>
            <s:form id="ApproveRejectPromoManager" action="promoExecuteApprReject_action" method="POST" >                
                <s:hidden id="hdntransPromoId" name="pmFormVO.transPromoId"/>
                <s:hidden id="hdnteamMemberId" name="pmFormVO.teamMemberId"/>
                <s:hidden id="hdnStatus" name="pmFormVO.status"/>
                <s:hidden id="hdnreasonRejection" name="pmFormVO.reasonRejection"/>

                <s:hidden id="actionName" name="actionName" />
                <tr>
                    <td><h1>Promo Execute Approval :</h1></td>
                </tr>
                <tr>
                    <td width="100%" align="center" style="color: #0D6C0D;font-weight: bold">
                        <div id="msg">
                            <s:actionmessage cssClass="successText"/>
                            <s:actionerror cssClass="errorText"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td align="left"><input type="hidden" name="hdempId" id="hdempId"  value="<%= session.getAttribute(WebConstants.EMP_ID)%>" disabled/></td>
                </tr>

                <tr>
                    <td align="center" colspan="2">
                        <table align="center">
                            <tr>
                                <%-- <td> Search Type :</td>
                                 <td><s:select   list="#{'-1':'---Select---','1':'Date','2':'Event Type','3':'Marketing Type','4':'Promotion Type'}" id="searchTypeSel"  cssClass="dropdown"/>
 </td>--%>
                                <td>Start Date :<span class="errorText">*&nbsp;</span></td>
                                <td><s:textfield id="txtBXGYFrom"  readonly="true"/></td>
                                <td>End Date :<span class="errorText">*&nbsp;</span></td>
                                <td><s:textfield id="txtBXGYTo" readonly="true"/></td>
                                <td align="right">Category :</td>
                                <td align="left">
                                    <s:select name="categoryName" id="categorySel" list="categoryMap"  headerKey="-1" headerValue="---Select----" cssClass="dropdown"/>
                                </td>
                                <td align="right">Sub Category :</td>
                                <td align="left">
                                    <s:select name="subcategoryName" id="subcategorySel" list="subcategoryMap"  headerKey="-1" headerValue="---Select----"/>
                                </td>
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
                    <td  align="center">
                        <table id="dashboardGrid"></table>
                        <div id="dashboardPager"></div>
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
                                    Reason for rejection : </td>
                                <td align="left" >
                                    <s:select name="reasonName" id="mstReasonRejectionVOs" list="mstReasonRejectionVOs"  headerKey="-1" headerValue="---Select---" cssClass="dropdown"/>
                                </td>
                            </tr>

                            <tr id="trRemarks">
                                <td align="center" colspan="2">
                                    <table width="40%">
                                        <td align="right">Remarks : </td>
                                        <td align="left"><textarea cols="40" rows="4" name="pmFormVO.manualRemarks" id="txtMenualRemarks" title="Max 1000 Characters."></textarea>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td  style="height: 10px;"></td>
                </tr>

                <tr>
                    <td align="center">
                        <table>
                            <% String storeCodeLocation = (String) session.getAttribute(WebConstants.LOCATION);
                                        if (storeCodeLocation.equalsIgnoreCase("HO")) {
                            %>
                            <tr>
                                <td align="right">
                                    Zone :<span class="errorText">&nbsp;*</span>
                                </td>
                                <td align="left">
                                    <s:select headerKey="-1" headerValue="---Select---" list="zoneMap" id="zoneSel" name="zoneSel"  cssClass="dropdown"/>
                                </td>
                            </tr>
                            <tr>
                                <td colspan="2" style="height: 10px;"></td>
                            </tr>
                            <%}%>
                            <tr>
                                <td align="right">
                                    Team Member Assign :<span class="errorText">&nbsp;*</span>
                                </td>
                                <td align="left">
                                    <s:select headerKey="-1" headerValue="---Select---" list="#{}" id="assignEmpSel" name="assignEmpSel"  cssClass="dropdown"/>
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
                        <input type="submit" name="approveBtn" id="approveBtn" value="Submit"  class="btn"/>&nbsp;&nbsp;
                        <input type="submit" name="rejectBtn" id="rejectBtn" value="Reject"  class="btn"/>&nbsp;&nbsp;
                        <input type="button" name="downloadBtn" id="downloadBtn" value="Download"  class="btn"/>
                    </td>
                </tr>
            </s:form>
        </table>
    </body>
</html>
