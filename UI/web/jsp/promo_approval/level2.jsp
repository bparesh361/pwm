<%--
    Document   : level1
    Created on : Dec 29, 2012, 5:25:08 PM
    Author     : nehabha
--%>

<%@page import="com.fks.ui.constants.WebConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Level 2 Approval</title>
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
        <script type="text/javascript" src="js/popup.js"></script>
        <script type="text/javascript" src="js/thickbox.js"></script>
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>
        <script type="text/javascript">
            var glbReqId,idsOfSelectedRows = [];
            var suggestedDate;
            resetAllButton();
            $(function () {

                jQuery("#level1Grid").jqGrid({
                    url:"getAllPromotiondtl2?searchType=ALL",
                    datatype: 'json',
                    width: 1300,
                    height:230,
                    colNames:['PromotionReq Number','Last Updated Date','Request Date','Initiator Name', 'Contact Number','Employee Code','Initiator Location',
                        'Request Name','Campaign','Marketing Type','Objective','Category','Sub Category','Promotion Type','Valid from','Valid To','Status','Remark'],
                    colModel:[
                        {name:'reqno',index:'reqno', width:170, align:"center"},
                        {name:'ldate',index:'ldate', width:200, align:"center"},
                        {name:'date',index:'date', width:200, align:"center"},
                        {name:'initiatorName',index:'initiatorName', width:170, align:"center"},
                        {name:'contNo',index:'contNo', width:200, align:"center"},
                        {name:'empCode',index:'empCode', width:200, align:"center"},
                        {name:'initiatorlocation',index:'initiatorlocation', width:200, align:"center"},
                        {name:'reqName',index:'reqName', width:200, align:"center"},
                        {name:'event',index:'event', width:200, align:"center"},
                        {name:'marketingtype',index:'marketingtype', width:200, align:"center"},
                        {name:'campaign',index:'campaign', width:200, align:"center"},
                        {name:'category',index:'category', width:200, align:"center"},
                        {name:'subcategory',index:'subcategory', width:200, align:"center"},
                        {name:'promotype',index:'promotype', width:200, align:"center"},
                        {name:'validfrom',index:'validfrom', width:200, align:"center"},
                        {name:'validto',index:'validto', width:200, align:"center"},
                        {name:'status',index:'status', width:200, align:"center"},
                        {name:'remark',index:'remark', width:200, align:"center"},
                    ],
                    rowNum:15,
                    rowList:[15],
                    headertitles: true,
                    viewrecords: true,
                    pager: '#level1Pager',
                    multiselect: true,
                    //emptyrecords:'',
                    //recordtext:'',
                    //loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:"",
                    onSelectRow:function (id, isSelected) {
                        gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);
                        glbReqId=id;
                        $("#level1reqId").val(idsOfSelectedRows);
                        $("#buttonshow").hide();
                        $("#dateshow").hide();
                        $("#savebuttonshow").hide();
                        resetAllButton();
                    },onSelectAll: function (aRowids, isSelected) {
                        var i, id;
                        for (i = 0; i < aRowids.length; i++) {
                            id = aRowids[i];
                            gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);
                        }
                        $("#level1reqId").val(idsOfSelectedRows);
                        $("#buttonshow").hide();
                        $("#dateshow").hide();
                        $("#savebuttonshow").hide();
                        resetAllButton();
                    },
                    loadComplete: function () {
                        var $this = $(this), i, count;
                        for (i = 0, count = idsOfSelectedRows.length; i < count; i++) {
                            $this.jqGrid('setSelection', idsOfSelectedRows[i], false);
                        }
                    }
                    //                    ,gridComplete:function(){
                    //                        $("#cb_"+$("#level1Grid")[0].id).hide();
                    //                    }
                });
                

            });
            function resetAllButton(){
                $("#rejectBtn").attr("disabled",true);
                $("#approveBtn").attr("disabled",false);
                $("#holdBtn").attr("disabled",false);
                $("#mstReasonRejectionVOs").val("-1");
                $("#trRemarks").hide();
            }
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });

                fillL1TeamMembersCombo("");
                enableDisableZoneAndL1DropDown("0");

                //                $("#buttonshow").hide();
                //                $("#dateshow").hide();
                //                $("#savebuttonshow").hide();
                $(function() {
                    var dates = $( "#popup_container1, #popup_container2" ).datepicker({
                        //defaultDate: "+1w",
                        //numberOfMonths: 1,
                        changeMonth: true,
                        changeYear: true,
                        dateFormat: 'dd-mm-yy',
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

                $(function() {
                    var dates = $( "#txtBXGYFrom, #txtBXGYTo" ).datepicker({
                        //                        defaultDate: "+1w",
                        //                        numberOfMonths: 1,
                        changeMonth: true,
                        changeYear: true,
                        dateFormat: 'yy-mm-dd',
                        // maxDate : '+0d',
                        // minDate:'+0d',


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
               
                $("#btnReset").click(function (data){
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    jqueryclearDate("popup_container1","popup_container2");
                    jqueryclearDate("txtBXGYFrom","txtBXGYTo");
                    $("#popup_container2").val("");
                    $("#popup_container1").val("");
                    resetAllButton();
                    $("#message").html('');
                    $("#trRemarks").hide();
                    $('#l2')[0].reset();
                    jQuery("#level1Grid").jqGrid('setGridParam',{url:"getAllPromotiondtl2?searchType=ALL",datatype:'json',page:1}).trigger("reloadGrid");

                });
           
           

                //Hold button
                $("#holdBtn").click(function(){

                    //var id = $('#level1Grid').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request !");
                        return false;
                    }
                    $("#transPromoID").val(idsOfSelectedRows);
                    $("#actionName").val("HOLD");
                  
                });

                //Approve button
                $("#approveBtn").click(function(){
                    $("#buttonshow").hide();
                    $("#dateshow").hide();
                    $("#savebuttonshow").hide();  
                    $("#message").html('');
                    //var id = $('#level1Grid').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request !");
                        return false;
                    }
                    $("#transPromoID").val(idsOfSelectedRows);
                    $("#actionName").val("APPROVE");

                    if(idsOfSelectedRows.length==1){
                        $("#businessExigencyBtn").attr("disabled",false);
                        var level1reqId= $("#transPromoID").val();
                        $.ajax({
                            url: "approveSingleL2Request?id="+level1reqId+"&status=APPROVE",
                            global: false,
                            type: "POST",
                            dataType:"json",
                            contanttype: 'text/html',
                            async:false,
                            error:function(){
                                alert("Can not connect to server");
                            },
                            success: function(data){
                                if(data!=null){
                                    if(data.status=="SUCCESS"){
                                        $("#buttonshow").hide();
                                        $("#message").html(data.message);                                        
                                        //                                        $("#level1Grid").jqGrid("clearGridData", true);
                                        //                                        jQuery("#level1Grid").jqGrid('setGridParam',{url:'getAllPromotiondtl?searchType=ALL',datatype:'json',page:1}).trigger("reloadGrid");
                                    }else{
                                        if(data.leadTimeFailed=="true"){
                                            $("#buttonshow").show();
                                            //                                            Popup.showModal('popUpDiv');
                                            Popup.showModal('popUpDiv',null,null,{'screenColor':'#F1EEEE','screenOpacity':.6});

                                            $("#popUpMessage").html(data.message);
                                            suggestedDate=data.suggesteDate;
                                            jqueryclearDate("popup_container1","popup_container2");
                                            jqueryclearDate("txtBXGYFrom","txtBXGYTo");
                                            $("#popup_container2").val("");
                                            $("#popup_container1").val("");
                                            return false;
                                        }
                                        $("#message").html(data.message);
                                    }
                                    idsOfSelectedRows = [];
                                    $("#transPromoID").val(idsOfSelectedRows);
                                    $("#level1Grid").jqGrid("clearGridData", true);
                                    jQuery("#level1Grid").jqGrid('setGridParam',{url:'getAllPromotiondtl2?searchType=ALL',datatype:'json',page:1}).trigger("reloadGrid");
                                }

                            }
                        });

                        // Prevent Page from Post Call
                        return false;
                    }

                });
                $("#trRemarks").hide();
                $("#mstReasonRejectionVOs").change(function(){
                    var reason=$("#mstReasonRejectionVOs").val();
                    // alert(reason);
                    if(reason=="-1"){
                        $("#rejectBtn").attr("disabled",true);
                        $("#approveBtn").attr("disabled",false);
                        $("#holdBtn").attr("disabled",false);
                        $("#trRemarks").hide();
                    }else if(reason=="10002"){
                        $("#rejectBtn").attr("disabled",false);
                        $("#approveBtn").attr("disabled",true);
                        $("#holdBtn").attr("disabled",true);
                        $("#trRemarks").show();
                    }else{
                        $("#rejectBtn").attr("disabled",false);
                        $("#approveBtn").attr("disabled",true);
                        $("#holdBtn").attr("disabled",true);
                        $("#trRemarks").hide();
                    }
                });
              
                
                //Reject button
                $("#rejectBtn").click(function(){
                    // var id = $('#level1Grid').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request !");
                        return false;
                    }

                    var rejReason =$("#mstReasonRejectionVOs option:selected").text();
                    var reason=$("#mstReasonRejectionVOs").val();
                    if(rejReason == null || rejReason.length == 0){
                        alert("Please, select the Reason for rejection .")
                        return false;
                    }else if(reason== "10002"){
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
                    $("#transPromoID").val(idsOfSelectedRows);
                    $("#actionName").val("REJECT");
                    $("#rejReason").val(rejReason);
                   
                });

                //Bussiness exigency button
                $("#businessExigencyBtn").click(function(){
                    $("#message").html('');
                    var level1reqId=$("#level1reqId").val();
                    if(level1reqId == null || level1reqId == 0){
                        alert("Please, select the row .")
                        return false;
                    }else{
                        $.ajax({
                            url: "approveSingleL2Request?id="+level1reqId+"&status=BUSSUNESSEXIGENCY",
                            global: false,
                            type: "POST",
                            dataType:"json",
                            contanttype: 'text/html',
                            async:false,
                            error:function(){
                                alert("Can not connect to server");
                            },
                            success: function(data){
                                $("#buttonshow").hide();
                                if(data!=null){
                                    $("#message").html(data.message);
                                }
                                $("#level1Grid").jqGrid("clearGridData", true);
                                jQuery("#level1Grid").jqGrid('setGridParam',{url:'getAllPromotiondtl2?searchType=ALL',datatype:'json',page:1}).trigger("reloadGrid");
                                fillL1TeamMembersCombo("");
                                enableDisableZoneAndL1DropDown("0");
                                Popup.hide('popUpDiv');
                                idsOfSelectedRows = [];
                            }
                        });
                    }
                });

                //Download button
                $("#downloadBtn").click(function(){
                    $("#message").html('');
                    $("#message").html('');
                    var  empId=   $("#hdempId").val();
                    var id = $('#level1Grid').jqGrid('getGridParam','selarrrow');
                    if(id.length==0){
                        alert("Please Select Request !");
                        return false;
                    }else{
                        var iframe = document.createElement("iframe");
                        iframe.src = "downloadMultipleExcel?transId="+idsOfSelectedRows+"&empID="+empId+"&downloadType=INITIATOR_TO_EXIGENCY_DOWNLOAD";
                        iframe.style.display = "none";
                        document.body.appendChild(iframe);
                    }
                 
                });

                //Change promotion on date button
                $("#businessExigencyBtn").attr("disabled",false);
                $("#changePromotiondateBtn").click(function(){
                    $("#businessExigencyBtn").attr("disabled",true);
                    $("#message").html('');
                    $("#dateshow").show();
                    $("#savebuttonshow").hide();

                });

                //save & apprive button
                $("#saveApproveBtn").click(function(){
                    $("#message").html('');
                    var level1reqId=$("#level1reqId").val();
                    var startdate = $("#popup_container1").val();
                    var enddate = $("#popup_container2").val();
                    //
                    if(level1reqId == null || level1reqId == 0){
                        alert("Please, select the row .")
                        return false;
                    }else{

                        $.ajax({
                            url: "approveSingleL2Request?id="+level1reqId+"&startDate="
                                +startdate+"&endDate="+enddate +"&status=CHANGEDATE",
                            global: false,
                            type: "POST",
                            dataType:"json",
                            contanttype: 'text/html',
                            async:false,
                            error:function(data){
                                $("#message").html(data.message);
                                alert("Can not connect to server");
                            },
                            success: function(data){
                                if(data!=null){
                                    $("#message").html(data.message);
                                }
                                $("#level1Grid").jqGrid("clearGridData", true);
                                jQuery("#level1Grid").jqGrid('setGridParam',{url:'getAllPromotiondtl2?searchType=ALL',datatype:'json',page:1}).trigger("reloadGrid");
                                $("#buttonshow").hide();
                                $("#dateshow").hide();
                                $("#savebuttonshow").hide();
                                fillL1TeamMembersCombo("");
                                enableDisableZoneAndL1DropDown("0");
                                Popup.hide('popUpDiv');
                                idsOfSelectedRows = [];
                                jqueryclearDate("popup_container1","popup_container2");
                                jqueryclearDate("txtBXGYFrom","txtBXGYTo");
                                $("#popup_container2").val("");
                                $("#popup_container1").val("");
                            }
                        });
                        return false;
                    }
                });
                //save & apprive button
                $("#validatedateBtn").click(function(){
                    $("#message").html('');
                    var startdate = $("#popup_container1").val();
                    var enddate = $("#popup_container2").val();
                    if(startdate == null || startdate == 0 ||enddate == null || enddate == 0){
                        alert("Please, select the Start date and end date .")
                        return false;
                    }
                    //                    else if(startdate!=suggestedDate){
                    //                        alert("Selected Start date should be same or greater than suggested date : "+suggestedDate)
                    //                        return false;
                    //                    }
                    else{

                        $.ajax({
                            url: "validatedate?startDate="+startdate+"&endDate="+enddate,
                            global: false,
                            type: "POST",
                            dataType:"json",
                            contanttype: 'text/html',
                            async:false,
                            error:function(data){
                                $("#message").html(data.message);
                                alert("Can not connect to server");
                            },
                            success: function(data){
                                if(data!=null){

                                    if(data.status=="SUCCESS"){
                                        $("#savebuttonshow").show();
                                        $("#popUpMessage").html(data.message);
                                        //$("#level1Grid").jqGrid("clearGridData", true);
                                        //jQuery("#level1Grid").jqGrid('setGridParam',{url:'getAllPromotiondtl2',datatype:'json',page:1}).trigger("reloadGrid");
                                    }else{
                                        $("#savebuttonshow").hide();
                                        $("#popUpMessage").html(data.message);
                                        //                                        $("#message").html(data.message);
                                    }
                                }
                            }
                        });
                    }
                });
                function resetAllFields(){
                    // $("#searchTypeSel").val("-1");
                    // jQuery("#level1Grid").jqGrid('setGridParam',{url:'getAllPromotiondtl?searchType=ALL',datatype:'json',page:1}).trigger("reloadGrid");
                    $("#eventSel").val("-1");
                    $("#categorySel").val("-1");
                    $("#txtBXGYFrom").val("");
                    $("#txtBXGYTo").val("");
                    $("#marketingsel").val("-1");
                    $("#promotionSel").val("-1");                    
                    //$("#subcategorySel").val("-1");
                }
                
                $("#btnSearch").click(function(){
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    $("#message").html('');
                    var url;
                    var category=$("#categorySel").val();
                    var validFrom=$("#txtBXGYFrom").val();
                    var validTo=$("#txtBXGYTo").val();
                    var event=$("#eventSel").val();
                    var mktg=$("#marketingsel").val();
                    var promotype=$("#promotionSel").val();
                    //                    if(category==-1){
                    //                        alert("Please select sub category.");
                    //                        return false;
                    //                    }else{

                    if(validFrom==undefined || validFrom==null || validFrom.length==0){
                        alert("Please select valid from date.");
                        return false;
                    }else if(validTo==undefined || validTo==null || validTo.length==0){
                        alert("Please select valid to date.");
                        return false;
                    }else{

                        if((validFrom!=null || validFrom.length>0) && (validTo!=null || validTo.length>0) && event==-1 && mktg==-1 && promotype==-1 && category==-1){
                            url='getAllPromotiondtl2?searchType=DATE&startDate='+validFrom+'&endDate='+validTo;
                        }else if((event==-1 || event==null)  && (mktg==-1 || mktg==null)  && (promotype==-1 || promotype==null) && (category!=-1 || category!=null)){
                            url='getAllPromotiondtl2?searchType=SUB_CATEGORY_DATE&categoryName='+category+"&startDate="+validFrom+'&endDate='+validTo;
                        }else if((event!=-1 || event !=null)  && (mktg==-1 || mktg==null)  && (promotype==-1 || promotype==null) && (category==-1 || category==null)){
                            url='getAllPromotiondtl2?searchType=DATE_EVENT_TYPE&startDate='+validFrom+'&endDate='+validTo+"&eventSel="+event;
                        }else if((event==-1 || event ==null)  && (mktg!=-1 || mktg!=null)  && (promotype==-1 || promotype==null) && (category==-1 || category==null)){
                            url='getAllPromotiondtl2?searchType=DATE_MARKETTING_TYPE&marketingsel='+mktg+"&startDate="+validFrom+'&endDate='+validTo;
                        }else if((event==-1 || event ==null)  && (mktg==-1 || mktg==null)  && (promotype!=-1 || promotype!=null) && (category==-1 || category==null)){
                            url='getAllPromotiondtl2?searchType=DATE_PROMO_TYPE&promotionSel='+promotype+"&startDate="+validFrom+'&endDate='+validTo;
                        }
                        //                            if((event==-1 || event==null)  && (mktg==-1 || mktg==null)  && (promotype==-1 || promotype==null) ){
                        //                                url='getAllPromotiondtl2?searchType=SUB_CATEGORY_DATE&categoryName='+category+"&startDate="+validFrom+'&endDate='+validTo;
                        //                            }else if((event!=-1 || event !=null) &&(mktg==-1 || mktg==null) && (promotype==-1|| promotype==null) ){
                        //                                url='getAllPromotiondtl2?searchType=SUB_CATEGORY_DATE_EVENT_TYPE&categoryName='+category+"&startDate="+validFrom+'&endDate='+validTo+"&eventSel="+event;
                        //                            }else if((event==-1 || event==null) &&(mktg!=-1 || mktg!=null) && (promotype==-1|| promotype==null) ){
                        //                                url='getAllPromotiondtl2?searchType=SUB_CATEGORY_DATE_MARKETTING_TYPE&categoryName='+category+"&marketingsel="+mktg+"&startDate="+validFrom+'&endDate='+validTo;
                        //                            }else if((event==-1 || event==null) &&(mktg==-1 || mktg==null) && (promotype!=-1 || promotype!=null) ){
                        //                                url='getAllPromotiondtl2?searchType=SUB_CATEGORY_DATE_PROMO_TYPE&categoryName='+category+"&promotionSel="+promotype+"&startDate="+validFrom+'&endDate='+validTo;
                        //                            }
                        else if((event!=-1 && mktg!=-1) || (event!=-1 && promotype!=-1) || (event!=-1 && category!=-1)|| (mktg!=-1 && promotype!=-1)|| (mktg!=-1 && category!=-1)|| (promotype!=-1 && category!=-1)){
                            alert("Please Select Sub Category or Campaign or Marketing Type or Promotion Type.");
                            $("#eventSel").val("-1");
                            $("#marketingsel").val("-1");
                            $("#promotionSel").val("-1")
                            $("#categorySel").val("-1")
                            return false;
                        }
                        else{
                            url='getAllPromotiondtl2?searchType=DATE&startDate='+validFrom+'&endDate='+validTo;
                            //url='getAllPromotiondtl2?searchType=SUB_CATEGORY_DATE&categoryName='+category+"&startDate="+validFrom+'&endDate='+validTo;
                        }

                    }
                    
                    $("#level1Grid").jqGrid("clearGridData", true);
                    jQuery("#level1Grid").jqGrid('setGridParam',{url:url,datatype:'json',page:1}).trigger("reloadGrid");
                    fillL1TeamMembersCombo("");
                    enableDisableZoneAndL1DropDown("0");
                });

                $("#chkIsL1ByPass").click(function(){
                    if($("#chkIsL1ByPass").attr("checked")==1){
                        enableDisableZoneAndL1DropDown("1");
                    }else{
                        fillL1TeamMembersCombo("");
                        enableDisableZoneAndL1DropDown("0");
                        var url="getAllPromotiondtl2?searchType=ALL";
                        $("#level1Grid").jqGrid("clearGridData", true);
                        jQuery("#level1Grid").jqGrid('setGridParam',{url:url,datatype:'json',page:1}).trigger("reloadGrid");
                    }                    
                });
                $("#zoneSel").change(function(){
                    var zoneId=$("#zoneSel").val();
                    if(zoneId!=-1){
                        fillL1TeamMembersCombo(zoneId);
                    }
                });
                $("#btnSearchByPass").click(function(){
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    var l1Id=$("#assignEmpSel").val();
                    if(l1Id==-1){
                        alert("Please select L1 Team Member.");
                        return false;
                    }
                    var url="getAllL1Promotiondtl2?searchType=ALL&l1EmpId="+l1Id;
                    $("#level1Grid").jqGrid("clearGridData", true);
                    jQuery("#level1Grid").jqGrid('setGridParam',{url:url,datatype:'json',page:1}).trigger("reloadGrid");
                });
                function fillL1TeamMembersCombo(zoneId){
                    $.ajax({
                        url: "fillL1TeamMember?zoneId="+zoneId,
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
                            //                            alert("size : "+data.ListSize);
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

                function enableDisableZoneAndL1DropDown(isEnabled){
                    if(isEnabled=="1"){
                        $("#holdBtn").attr("disabled",true);
                        $("#zoneLabelTD").show();
                        $("#zoneDropDownTD").show();
                        $("#zoneSpaceTD").show();
                        $("#l1TeamLabelTD").show();
                        $("#l1TeamLabelDropDownTD").show();
                        $("#btnSearchByPassTD").show();
                        $("#zoneSel").val(-1);
                    }else{
                        $("#holdBtn").attr("disabled",false);
                        $("#zoneLabelTD").hide();
                        $("#zoneDropDownTD").hide();
                        $("#zoneSpaceTD").hide();
                        $("#l1TeamLabelTD").hide();
                        $("#l1TeamLabelDropDownTD").hide();
                        $("#btnSearchByPassTD").hide();
                        $("#chkIsL1ByPass").attr("checked",false);
                    }
                }

            });
        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <s:form action="donothing" id="l2" name="l2">
            <input type="hidden" name="level1reqId" id="level1reqId" />
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">
                <tr>
                    <td colspan="1"><h1>Level 2 Approval : </h1></td>
                    <td align="right" >
                        <a href ="#" class="download-sample " onclick="tb_show( '', 'viewHelpl2?height=300&width=550');">
                            Help
                        </a>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td width="100%" align="center" style="color: #0D6C0D;font-weight: bold" colspan="2">
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
                    <td colspan="2" style="height: 10px;">
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <table align="center">
                            <tr>
                                <%--                              <td>
                                                                    Search Type :
                                                                </td>
                                                                <td>
                                                                    <s:select   list="#{'-1':'---Select---','1':'Category','2':'Date','3':'Event Type','4':'Marketing Type','5':'Promotion Type'}" id="searchTypeSel"  cssClass="dropdown"/>
                                                                </td>
                                --%>

                                <td align="right">Start Date :<span class="errorText">*&nbsp;</span></td>
                                <td align="left"><s:textfield id="txtBXGYFrom" readonly="true" /></td>
                                <td align="right">End Date :<span class="errorText">*&nbsp;</span></td>
                                <td align="left"><s:textfield id="txtBXGYTo" readonly="true"/></td>
                                <td align="right">
                                    Sub Category :
                                </td>
                                <td align="left">
                                    <s:select name="categoryName" id="categorySel" list="categoryMap"  headerKey="-1" headerValue="---Select----"/>
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
                    <td colspan="2" style="height: 10px;">
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="left">
                        <table  align="left">
                            <tr>
                                <td align="right">
                                    <input type="checkbox" id="chkIsL1ByPass"/>
                                </td>
                                <td align="left">
                                    L1 By Pass
                                </td>
                                <td style="width: 5%">
                                </td>
                                <% String storeCodeLocation = (String) session.getAttribute(WebConstants.LOCATION);
                                            if (storeCodeLocation.equalsIgnoreCase("HO")) {
                                %>
                                <td id="zoneLabelTD" align="right">
                                    Zone :
                                </td>
                                <td id="zoneDropDownTD" align="left">
                                    <s:select headerKey="-1" headerValue="---Select---" list="zoneMap" id="zoneSel" name="zoneSel"  cssClass="dropdown"/>
                                </td>
                                <td style="width: 5%" id="zoneSpaceTD">
                                </td>
                                <%}%>
                                <td id="l1TeamLabelTD" align="right">
                                    L1 Team Member :
                                </td>
                                <td id="l1TeamLabelDropDownTD" align="left">
                                    <s:select headerKey="-1" headerValue="---Select---" list="#{}" id="assignEmpSel" name="assignEmpSel"  cssClass="dropdown"/>
                                </td>
                                <td align="center" id="btnSearchByPassTD">
                                    <input type="button" value="Search" name="Search" id="btnSearchByPass" class="btn"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td  align="center" colspan="2">
                        <table id="level1Grid"></table>
                        <div id="level1Pager"></div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <img src="images/spacer.gif" width="10" height="10"/>
                    </td>
                </tr>
                <tr>
                    <td align="right">
                        Reason for rejection : </td>
                    <td align="left" >
                        <s:select name="reasonName" id="mstReasonRejectionVOs" list="mstReasonRejectionVOs" headerKey="-1" headerValue="---Select----"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <img src="images/spacer.gif" width="10" height="10"/>
                    </td>
                </tr>
                <tr id="trRemarks" >
                    <td align="center" colspan="2">
                        <table width="40%">
                            <td align="right">Remarks : </td>
                            <td align="left"><textarea cols="40" rows="4" name="manualRemarks" id="txtMenualRemarks" title="Max 1000 Characters."></textarea>
                        </table>
                    </td>
                </tr>
                <tr align="center">
                    <!--                <td colspan="2">
                                        <input type="button" name="holdBtn" id="holdBtn" value="Hold"  class="btn"/>&nbsp;&nbsp;
                                        <input type="button" name="approveBtn" id="approveBtn" value="Approve"  class="btn"/>&nbsp;&nbsp;
                                        <input type="button" name="rejectBtn" id="rejectBtn" value="Reject"  class="btn"/>&nbsp;&nbsp;
                                        <input type="button" name="downloadBtn" id="downloadBtn" value="Download"  class="btn"/>
                                    </td>-->
                    <td align="center" colspan="2">
                        <table width="30%" align="center">
                            <tr align="center">
                                <s:hidden id="transPromoID" name="transPromoID" />
                                <s:hidden id="actionName" name="actionName" />
                                <s:hidden id="rejReason" name="rejReason" />
                                <td>
                                    <s:submit id="holdBtn" name="holdBtn"  value="Hold" cssClass="btn" action="HoldAppRejTransPromoreq2"   />
                                </td>
                                <td>
                                    <s:submit id="approveBtn" name="approveBtn"  value="Approve" cssClass="btn" action="HoldAppRejTransPromoreq2"   />
                                </td>
                                <td>
                                    <s:submit id="rejectBtn" name="rejectBtn"  value="Reject" cssClass="btn" action="HoldAppRejTransPromoreq2"   />
                                </td>
                                <td style="height: 200%">
                                    <input type="button" name="downloadBtn" id="downloadBtn" value="Download"  class="btn"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <img src="images/spacer.gif" width="10" height="10"/>
                    </td>
                </tr>
                <tr align="center" id="buttonshow" style="display: none">
                    <td colspan="2" align="center">
                        <div id="popUpDiv" style="width: 500px;height: 175px;border:2px solid black; background-color:#F1EEEE; text-align:center; display:none;">
                            <table align="center">
                                <tr>
                                    <td colspan="5" align="right">
                                        <a href="#" onclick="Popup.hide('popUpDiv')" style="font-size: 15px;">Close</a>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="5">
                                        <img src="images/spacer.gif" width="10" height="10"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="5" align="center">
                                        <div id="popUpMessage">
                                            <s:actionmessage cssClass="successText"/>
                                            <s:actionerror cssClass="errorText"/>
                                        </div>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="5">
                                        <img src="images/spacer.gif" width="10" height="10"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <input type="button" name="businessExigencyBtn" id="businessExigencyBtn" value="Business Exigency"  class="largebtn"/>&nbsp;&nbsp;
                                    </td>
                                    <td colspan="2">
                                        <input type="button" name="changePromotiondateBtn" id="changePromotiondateBtn" value="Change the Promotion date"  class="largebtn"/>
                                    </td>
                                    <td></td>
                                </tr>
                                <tr>
                                    <td colspan="5">
                                        <img src="images/spacer.gif" width="10" height="10"/>
                                    </td>
                                </tr>
                                <tr align="center" id="dateshow" style="display: none">
                                    <td>
                                        FROM DATE :
                                    </td>
                                    <td>
                                        <input name="startDate" type="text" class="datef" id="popup_container1" readonly="readonly"/>
                                    </td>
                                    <td>
                                        TO DATE :
                                    </td>
                                    <td>
                                        <input name="endDate" type="text" class="datef" id="popup_container2" readonly="readonly"/>
                                    </td>
                                    <td>
                                        <input type="button" name="validatedateBtn" id="validatedateBtn" value="Validate"  class="btn"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="5">
                                        <img src="images/spacer.gif" width="10" height="10"/>
                                    </td>
                                </tr>
                                <tr align="center" id="savebuttonshow" style="display: none">
                                    <td colspan="5">
                                        <input type="button" name="saveApproveBtn" id="saveApproveBtn" value="Save & Approve"  class="largebtn"/>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>
        </s:form>
    </body>
</html>
