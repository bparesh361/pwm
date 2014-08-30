<%-- 
    Document   : promoSetupApproval
    Created on : Jan 10, 2013, 5:14:35 PM
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
        <title>Promotion Execute Approval</title>
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
            var isCashierTrigger=0;
            var  idsOfSelectedRows = [];            
            $(function () {
                jQuery("#dashboardGrid").jqGrid({
                    url:"viewPromoSetupDashboard_action?searchType=ALL",
                    datatype: 'json',
                    width: 1300,
                    height:230,
                    colNames:['PromotionReq Number','Last Updated Date','Request Date','Request Time','Initiator Name', 'Contact Number','Employee Code','Initiator Location',
                        'Request Name','Campaign','Marketing Type','Category','Sub Category','Promotion Type','Valid from','Valid To','Status','Approver Name','Approval From','Team Assignment From','Assigned By'],
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
                        {name:'approvername',index:'approvername', width:200, align:"center"},
                        {name:'approvalfrom',index:'approvalfrom', width:200, align:"center"},
                        {name:'teamassignfrom',index:'teamassignfrom', width:200, align:"center"},
                        {name:'assignby',index:'assignby', width:200, align:"center"}
                    ],
                    rowNum:30,
                    rowList:[30],
                    headertitles: true,
                    viewrecords: true,
                    pager: '#dashboardPager',
                    multiselect: true,
                    //                    emptyrecords:'',
                    //                    recordtext:'',
                    //sortorder: 'desc',
                    //loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:"",
                    onSelectRow:function(id,isSelected){
                        transPromoId=id;
                        
                        var apprverfrom = jQuery("#dashboardGrid").jqGrid('getCell',id,'approvalfrom');
                        changeRowColor(id,apprverfrom);
                        rowSelectionColor(id);
                       
                        gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);
                        resetAllButton();
                    }
                    ,onSelectAll: function (aRowids, isSelected) {
                        var i, id;
                        for (i = 0; i < aRowids.length; i++) {
                            id = aRowids[i];
                            gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);
                        }
                        var apprverfrom = jQuery("#dashboardGrid").jqGrid('getCell',id,'approvalfrom');
                        changeRowColor(id,apprverfrom);
                        rowSelectionColor(id);
                        resetAllButton();
                    },gridComplete:function(){
                        var ids =jQuery("#dashboardGrid").jqGrid('getDataIDs');
                        for(var i=0;i < ids.length;i++)
                        {     var rowId = ids[i];
                            var approvalfrom = jQuery("#dashboardGrid").jqGrid('getCell',ids[i],'approvalfrom');
                            changeRowColor(rowId,approvalfrom);
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

            function rowSelectionColor(rowId){
                // light yellow
                var color='#F4F6B1';
                $("#dashboardGrid").jqGrid('setRowData', rowId, false, {
                    'background-color':color,
                    color:'black'
                });
                var trElement = jQuery("#"+ rowId,$('#dashboardGrid'));
                trElement.removeClass("ui-state-hover");
                trElement.addClass('ui-state-disabled');
            }


            function changeRowColor(rowId,approverform){
                var color='white';
                if("Business Exigency"==approverform){
                    color='#FBD48F';
                }
                $("#dashboardGrid").jqGrid('setRowData', rowId, false, {
                    'background-color':color,
                    color:'black'
                });
                var trElement = jQuery("#"+ rowId,$('#dashboardGrid'));
                trElement.removeClass("ui-state-hover");
                trElement.addClass('ui-state-disabled');
            }
            function resetAllButton(){
                $("#rejectBtn").attr("disabled",true);
                $("#holdBtn").attr("disabled",false);
                $("#approveBtn").attr("disabled",false);
                $("#mstReasonRejectionVOs").val("-1");
                $("#zoneSel").attr("disabled",false);
                $("#assignEmpSel").attr("disabled",false);
            }
             
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });

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

                resetAllButton();
                //isManual
                var isManualPromoCloser=0;
                var isFileUploadPromoCloser=0;
                $("#tdErrorFile").hide();
                setUploadFormForFileValidationFailure();
                function setUploadFormForFileValidationFailure(){
                    var hdnFileError=$("#isuploaderror").val();
                    //                    alert("is error : "+hdnFileError);
                    var hdnFile=$("#hdnisFile").val();
                    //                    alert("is File : "+hdnFile);
                    if(hdnFile!=undefined && hdnFile.length>0 && hdnFile=="1"){
                        $("#trFileUpload").show();
                        $("#trManual").hide();
                        $("#isFile").attr('checked', true);
                        $("#isManual").attr('checked', false);
                        isManualPromoCloser=0;
                        isFileUploadPromoCloser=1;
                    }else{
                        $("#trManual").hide();
                        $("#trFileUpload").hide();
                        var checked = $("#isManual").attr('checked', true);
                        if(checked){
                            $("#trManual").show();
                            $("#trFileUpload").hide();
                            isManualPromoCloser=1;
                        }
                    }
                    if(hdnFileError!=undefined && hdnFileError.length>0 && hdnFileError=="1"){
                        $("#tdErrorFile").show();
                    }
                }
                                             
                $("#isManual").click(function(){
                    var checked = $(this).attr('checked', true);
                    if(checked){
                        $("#trManual").show();
                        $("#trFileUpload").hide();
                        isManualPromoCloser=1;
                    }
                });
                $("#isFile").click(function(){
                    var checked = $(this).attr('checked', true);
                    if(checked){
                        $("#trManual").hide();
                        $("#trFileUpload").show();
                        isFileUploadPromoCloser=1;
                    }
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
                
                               
                //Hold button
                $("#holdBtn").click(function(){
                    var validateFields=validateSetupFields("HOLD");
                    if(validateFields[0]==false){
                        alert(validateFields[1]);
                        return false;
                    }                    
                });
                //Approve button
                $("#approveBtn").click(function(){                    
                    //                    var id = $('#dashboardGrid').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length>1){
                        alert("Please select single request.");
                        return false;
                    }

                    var validateFields=validateSetupFields("APPROVE");
                    if(validateFields[0]==false){
                        alert(validateFields[1]);
                        return false;
                    }
                    var remarksVal=$("#txtRemarks").val();
                    remarksVal=  remarksVal.replace(/(\r\n|\n|\r)/gm," ");
                    $("#txtRemarks").val(remarksVal);
                });
                
                
                //Reject button
                $("#rejectBtn").click(function(){
                    var reason=$("#mstReasonRejectionVOs").val();
                    if(reason=="-1"){
                        alert("Please Select Reason for rejection.");
                        return false;
                    }
                    var reasonText =$("#mstReasonRejectionVOs option:selected").text();
                    $("#hdnreasonRejection").val(reasonText);
                    var validateFields=validateSetupFields("REJECT");
                    if(validateFields[0]==false){
                        alert(validateFields[1]);
                        return false;
                    }
                });
                //Download button
                $("#downloadBtn").click(function(){
                    $("#message").html('');
                    
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


                $("#mstReasonRejectionVOs").change(function(){
                    var reason=$("#mstReasonRejectionVOs").val();
                    // alert(reason);
                    if(reason=="-1"){
                        $("#rejectBtn").attr("disabled",true);
                        $("#approveBtn").attr("disabled",false);
                        $("#holdBtn").attr("disabled",false);
                        $("#zoneSel").attr("disabled",false);
                        $("#assignEmpSel").attr("disabled",false);
                    }else{
                        $("#rejectBtn").attr("disabled",false);
                        $("#approveBtn").attr("disabled",true);
                        $("#holdBtn").attr("disabled",true);
                        $("#zoneSel").attr("disabled",true);
                        $("#assignEmpSel").attr("disabled",true);

                    }
                });
                
                function validateSetupFields(status){
                    //                    var id = $('#dashboardGrid').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        return [false,"Please, select Request ."];
                    }
                    //                    if(transPromoId == null || transPromoId == 0){
                    //                        return [false,"Please, select the row ."];
                    //                    }
                    var remarksLength=200;

                    var remarksVal=$("#txtRemarks").val();
                    var remarksCheck;
                    if(status=="REJECT"){
                        remarksCheck=isBlank(remarksVal,"Remarks");
                        remarksVal=  remarksVal.replace(/(\r\n|\n|\r)/gm," ");
                        $("#txtRemarks").val(remarksVal);
                        if(remarksCheck[0]==false){
                            $("#txtRemarks").focus();
                            return[false,remarksCheck[1]];
                        }
                    }
                    

                    remarksCheck=checkLength(remarksVal, "Remarks", remarksLength);
                    if(remarksCheck[0]==false){
                        $("#txtRemarks").focus();
                        return[false,remarksCheck[1]];
                    }

                    //                    remarksCheck=isAlphaNumeric(remarksVal,"Remarks");
                    //                    if(remarksCheck[0]==false){
                    //                        $("#txtRemarks").focus();
                    //                        return[false,remarksCheck[1]];
                    //                    }

                    if(status=="APPROVE"){
                       
                        var bonusVal=$("#txtBonusBuy").val();
                        var bonusCheck=isBlank(bonusVal,"Bonus Buy");
                        if(bonusCheck[0]==false){
                            $("#txtBonusBuy").focus();
                            return[false,bonusCheck[1]];
                        }

                        //                        bonusCheck=isAlphaNumeric(bonusVal,"Bonus Buy");
                        //                        if(bonusCheck[0]==false){
                        //                            $("#txtBonusBuy").focus();
                        //                            return[false,bonusCheck[1]];
                        //                        }

                        var promoDtlVal=$("#txtPromoDtl").val();
                        promoDtlVal=  promoDtlVal.replace(/(\r\n|\n|\r)/gm," ");
                        $("#txtPromoDtl").val(promoDtlVal);
                        var promoCheck=isBlank(promoDtlVal,"Promo Detail");
                        if(promoCheck[0]==false){
                            $("#txtPromoDtl").focus();
                            return[false,promoCheck[1]];
                        }

                        promoCheck=checkLength(promoDtlVal, "Promo Detail", remarksLength);
                        if(promoCheck[0]==false){
                            $("#txtPromoDtl").focus();
                            return[false,promoCheck[1]];
                        }

                        //                        promoCheck=isAlphaNumeric(promoDtlVal,"Promo Detail");
                        //                        if(promoCheck[0]==false){
                        //                            $("#txtPromoDtl").focus();
                        //                            return[false,promoCheck[1]];
                        //                        }

                        var checkedCashier = $("#chkCashier").val();
                        if(!isNumeric(checkedCashier)){
                            $("#chkCashier").focus();
                            return [false,"Cachier Trigger Value should be numeric."];
                        }
                        promoCheck=checkLength(checkedCashier, "Cachier Trigger", 20);
                        if(promoCheck[0]==false){
                            $("#chkCashier").focus();
                            return[false,promoCheck[1]];
                        }

                        //                        var fileid=  document.getElementById("lsmwFile").value;
                        //                        if(fileid==null ||fileid==""){
                        //                            return [false,"Please select file to upload."];
                        //                        }

                    }
                                        
                    var checkedCashier = $("#chkCashier").val();
                    $("#hdncashierTrigger").val(checkedCashier);
                    $("#hdntransPromoId").val(idsOfSelectedRows);
                    $("#hdnStatus").val(status);
                    return[true,''];
                }

                $("#btnUploadFile").click(function (){
                    var fileid=  document.getElementById("promoCloserFile").value;
                    if(fileid==null ||fileid==""){
                        alert("Please select file to upload.");
                        return false;
                    }
                    var valid_extensions = /(.csv)$/i;
                    if(valid_extensions.test(fileid)){
                    } else{
                        alert('Invalid File! Please Upload CSV File Only');
                        return false;
                    }
                });

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
                    $("#subcategorySel").empty();
                    addoptionToSubCategory();

                }
         
              
                function DisabledAllFields(){
                    // $("#searchTypeSel").val("-1");
                    $("#eventSel").attr("disabled",true);
                    $("#txtBXGYFrom").attr("disabled",true);
                    $("#txtBXGYTo").attr("disabled",true);
                    $("#marketingsel").attr("disabled",true);
                    $("#promotionSel").attr("disabled",true);
                }
                //DisabledAllFields();
                $("#btnSearch").click(function(){
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    $("#message").html('');
                    var url;
                    $("#message").html('');
                    var validFrom=$("#txtBXGYFrom").val();
                    var validTo=$("#txtBXGYTo").val();
                    var event=$("#eventSel").val();
                    var mktg=$("#marketingsel").val();
                    var promotype=$("#promotionSel").val();
                    var subcategory=$("#subcategorySel").val();
                    var category=$("#categorySel").val();
                    if(validFrom==undefined || validFrom==null || validFrom.length==0){
                        alert("Please select valid from date.");
                        return false;
                    }else if(validTo==undefined || validTo==null || validTo.length==0){
                        alert("Please select valid to date.");
                        return false;
                    }else{                        
                        if((validFrom!=null || validFrom.length>0) && (validTo!=null || validTo.length>0) && event==-1 && mktg==-1 && promotype==-1 && category==-1 && subcategory==-1){
                            url='viewPromoSetupDashboard_action?searchType=DATE&startDate='+validFrom+'&endDate='+validTo;
                        } else if((event!=-1 || event !=null) &&(mktg==-1 || mktg==null) && (promotype==-1|| promotype==null) && (category==-1|| category==null) && (subcategory==-1|| subcategory==null) ){
                            url='viewPromoSetupDashboard_action?searchType=DATE_EVENT&startDate='+validFrom+'&endDate='+validTo+"&eventSel="+event;
                        }else if((event==-1 || event==null) &&(mktg!=-1 || mktg!=null) && (promotype==-1|| promotype==null)&& (category==-1|| category==null) && (subcategory==-1|| subcategory==null) ){
                            url='viewPromoSetupDashboard_action?searchType=DATE_MARKETING_TYPE&marketingsel='+mktg+'&startDate='+validFrom+'&endDate='+validTo;
                        }else if((event==-1 || event==null) &&(mktg==-1 || mktg==null) && (promotype!=-1 || promotype!=null) && (category==-1|| category==null) && (subcategory==-1|| subcategory==null)){
                            url='viewPromoSetupDashboard_action?searchType=DATE_PROMOTION_TPYE&promotionSel='+promotype+"&startDate="+validFrom+'&endDate='+validTo;
                        }else if((event==-1 || event==null) &&(mktg==-1 || mktg==null) && (promotype==-1 || promotype==null) && (category!=-1|| category!=null) && (subcategory==-1|| subcategory==null)){
                            url='viewPromoSetupDashboard_action?searchType=CATEGORY_DATE&categoryName='+category+"&startDate="+validFrom+'&endDate='+validTo;
                        }else if((event==-1 || event==null) &&(mktg==-1 || mktg==null) && (promotype==-1 || promotype==null) && (category!=-1|| category!=null) && (subcategory!=-1|| subcategory!=null)){
                            url='viewPromoSetupDashboard_action?searchType=SUB_CATEGORY_DATE&subcategoryName='+subcategory+"&startDate="+validFrom+'&endDate='+validTo;
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
                            url='viewPromoSetupDashboard_action?searchType=DATE&startDate='+validFrom+'&endDate='+validTo;
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
                    $("#message").html('');
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    clearDate();
                    resetAllFields();
                    resetAllButton();
                    $("#message").val("");
                    $('#submitPromoSetup')[0].reset();
                    $("#trRemarks").hide();
                    jQuery("#dashboardGrid").jqGrid('setGridParam',{url:"viewPromoSetupDashboard_action?searchType=ALL",datatype:'json',page:1}).trigger("reloadGrid");

                });               

            });
        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">
            <tr><td class="errorText" align="right">(Fields marked with a * are mandatory.)</td></tr>
            <s:form id="submitPromoSetup" action="submitPromoSetup_action" method="POST" enctype="multipart/form-data" >
                <s:hidden id="hdntransPromoId" name="setupFormVo.trnsPromoId"/>
                <s:hidden id="hdncashierTrigger" name="setupFormVo.cashierTrigger"/>
                <s:hidden id="hdnStatus" name="setupFormVo.status"/>
                <s:hidden id="hdnreasonRejection" name="setupFormVo.reasonRejection"/>
                <s:hidden id="hdnisFile" name="setupFormVo.isFile" value="%{setupFormVo.isFile}"/>
                <s:hidden id="isuploaderror" name="setupFormVo.isuploaderror" value="%{setupFormVo.isuploaderror}"/>
                <tr>
                    <td><h1><s:property value="headerName"/> </h1></td>
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
                    <td align="left"><input type="hidden" name="hdempId" id="hdempId"  value="<%= session.getAttribute(WebConstants.EMP_ID)%>" disabled/></td>
                </tr>
                <tr>
                    <td align="center" colspan="2">
                        <table align="center">
                            <tr>
                                <%--    <td> Search Type :</td>
                                <td><s:select   list="#{'-1':'---Select---','1':'Date','2':'Event Type','3':'Marketing Type','4':'Promotion Type'}" id="searchTypeSel"  cssClass="dropdown"/>
                                </td>
                                --%>
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
                <tr><td><img src="images/spacer.gif" width="10" height="10"/></td></tr>
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
                        </table>
                    </td>
                </tr>
                <tr><td  style="height: 10px;"></td></tr>
                <tr>
                    <td align="center">
                        <table align="center" width="50%">
                            <tr>
                                <td align="right">
                                    <input type="radio" id="isManual" name="captureDetail" checked="true" value="1"/> Manual
                                </td>
                                <td>&nbsp;</td>
                                <td align="left">
                                    <input type="radio" id="isFile" name="captureDetail"  value="0"/> Upload File
                                </td>
                            </tr>
                        </table>
                    </td>

                </tr>
                <tr id="trManual">
                    <td>
                        <table align="center" >
                            <tr >
                                <td align="center">
                                    <table cellspacing="4" cellpadding="4">
                                        <tr>
                                            <td align="right">
                                                Remark:<span class="errorText">&nbsp;*</span>
                                            </td>
                                            <td align="left">
                                                <s:textarea cols="40" rows="3" name="setupFormVo.remarks" id="txtRemarks" title="Max 200 Characters."/>
                                            </td>
                                            <td></td>
                                            <td align="right">
                                                Promotion Detail:<span class="errorText">&nbsp;*</span>
                                            </td>
                                            <td align="left">
                                                <s:textarea cols="40" rows="3" name="setupFormVo.promoDtl" id="txtPromoDtl" title="Max 200 Characters."/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td align="right">
                                                Bonus Buy:<span class="errorText">&nbsp;*</span>
                                            </td>
                                            <td align="left">
                                                <s:textfield name="setupFormVo.bonusBuy" id="txtBonusBuy"/>
                                            </td>
                                            <td></td>
                                            <td align="right">
                                                Cashier Trigger:
                                            </td>
                                            <td align="left">
                                                <s:textfield name="chkCashier" id="chkCashier" maxLength="10"/>
                                            </td>
                                        </tr>
                                        <%--   <tr>
                                            <td align="right">LSMW File :</td>
                                            <td align="left">
                                                <s:file id ="lsmwFile" name="lsmwFileUpload" ></s:file>
                                            </td>
                                        </tr>
                                        --%>
                                    </table>
                                </td>
                            </tr>
                            <tr align="center">
                                <td align="center">
                                    <table>
                                        <tr>
                                            <td>
                                                <input type="submit" name="holdBtn" id="holdBtn" value="Hold"  class="btn"/>
                                            </td>
                                            <td>
                                                <s:submit id="approveBtn" name="approveBtn"  value="Submit" cssClass="btn" action="submitPromoSetup_action"   />
                                            </td>
                                            <td>
                                                <input type="submit" name="rejectBtn" id="rejectBtn" value="Reject"  class="btn"/>
                                            </td>
                                            <td>
                                                <input type="button" name="downloadBtn" id="downloadBtn" value="Download"  class="btn"/>
                                            </td>
                                        </tr>
                                    </table>
                                    <!--                                    <input type="submit" name="approveBtn" id="approveBtn" value="Submit"  class="btn"/>-->
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr id="trFileUpload">
                    <td>
                        <table align="center">
                            <tr>
                                <td colspan="3" style="height: 10px;"></td>
                            </tr>
                            <tr>
                                <td align="right">Upload File</td>
                                <td align="left">
                                    <s:file id ="promoCloserFile" name="promoCloserFileUpload" ></s:file>
                                </td>
                                <td align="center">
                                    <s:submit id="btnUploadFile" name="btnUploadFile"  value="Upload" cssClass="btn" action="submitFilePromoSetup_action"  />
                                </td>
                                <td align="center" id="tdErrorFile">
                                    <s:a  href="getErrorFileOfPromoClose" class="downloadError" id="downloadErrorFile" > Error File </s:a>
                                </td>
                                <td align="center" >
                                    <s:a  href="PromoCloseSample" class="downloadError" id="downloadErrorFile" > Sample File </s:a>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr><td height="10px"></td></tr>
            </s:form>
        </table>
    </body>
</html>
