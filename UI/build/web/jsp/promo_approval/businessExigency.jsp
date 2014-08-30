<%-- 
    Document   : businessExigency
    Created on : Jan 8, 2013, 1:16:16 PM
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
        <title>Business Exigency Approval</title>
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
            var glbReqId, idsOfSelectedRows = [];
            $(function () {
                jQuery("#levelGrid").jqGrid({
                    url:"getAllPromotiondtlBusinessExigency?searchType=ALL",
                    datatype: 'json',
                    width: 1300,
                    height:290,
                    colNames:['PromotionReq Number','Last Updated Date','Request Date','Initiator Name', 'Contact Number','Employee Code','Initiator Location',
                        'Request Name','Campaign','Marketing Type','Category','Sub Category','Promotion Type','Valid from','Valid To','Status','Remark'],
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
                        {name:'category',index:'category', width:200, align:"center"},
                        {name:'subcategory',index:'subcategory', width:200, align:"center"},
                        {name:'promotype',index:'promotype', width:200, align:"center"},
                        {name:'validfrom',index:'validfrom', width:200, align:"center"},
                        {name:'validto',index:'validto', width:200, align:"center"},
                        {name:'status',index:'status', width:200, align:"center"},
                        {name:'remark',index:'remark', width:200, align:"center"},
                    ],
                    rowNum:30,
                    rowList:[30],
                    viewrecords: true,
                    headertitles: true,
                    pager: '#level1Pager',
                    multiselect: true,
                    //                    sortorder: 'desc',
                    //                    loadonce:true,
                    //                    emptyrecords:'',
                    //                    recordtext:'',
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:"",
                    onSelectRow:function(id,isSelected){
                        gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);
                        glbReqId=id;
                        $("#level1reqId").val(idsOfSelectedRows);
                        resetAllButton();
                    }
                    ,onSelectAll: function (aRowids, isSelected) {
                        var i, id;
                        for (i = 0; i < aRowids.length; i++) {
                            id = aRowids[i];
                            gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);
                        }
                        $("#level1reqId").val(idsOfSelectedRows);
                        resetAllButton();
                    }
                    ,loadComplete: function () {
                        var $this = $(this), i, count;
                        for (i = 0, count = idsOfSelectedRows.length; i < count; i++) {
                            $this.jqGrid('setSelection', idsOfSelectedRows[i], false);
                        }
                    }
                    //                    ,gridComplete:function(){
                    //                        $("#cb_"+$("#levelGrid")[0].id).hide();
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

                resetAllButton();

                $("#trRemarks").hide();
                
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
                                                                                
                
                $("#mstReasonRejectionVOs").change(function(){
                    var reason=$("#mstReasonRejectionVOs").val();
                    // alert(reason);
                    if(reason=="-1"){
                        $("#rejectBtn").attr("disabled",true);
                        $("#approveBtn").attr("disabled",false);
                        $("#holdBtn").attr("disabled",false);
                        $("#trRemarks").hide();
                    }else if(reason=="10002"){
                        $("#trRemarks").show();
                        $("#rejectBtn").attr("disabled",false);
                        $("#approveBtn").attr("disabled",true);
                        $("#holdBtn").attr("disabled",true);
                    }
                    else{
                        $("#rejectBtn").attr("disabled",false);
                        $("#approveBtn").attr("disabled",true);
                        $("#holdBtn").attr("disabled",true);
                        $("#trRemarks").hide();
                    }
                });

                //Approve button
                $("#approveBtn").click(function(){
                    $("#message").html('');
                    //                    var id = $('#levelGrid').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request !");
                        return false;
                    }
                    $("#transPromoID").val(idsOfSelectedRows);
                    $("#actionName").val("APPROVE");

                    //                    alert("-- length : "+idsOfSelectedRows.length);
                    //                    alert("-- length : "+idsOfSelectedRows);
                });

                //Reject button
                $("#rejectBtn").click(function(){
                    //                    var id = $('#levelGrid').jqGrid('getGridParam','selarrrow');
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

                    //                    alert("-- length : "+idsOfSelectedRows.length);
                    //                    alert("-- length : "+idsOfSelectedRows);
                });

                //Download button
                $("#downloadBtn").click(function(){
                   
                    var  empId=   $("#hdempId").val();
                    //                    var id = $('#levelGrid').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request !");
                        return false;
                    }else{
                        //                        alert("-- length : "+idsOfSelectedRows.length);
                        //                        alert("-- length : "+idsOfSelectedRows);
                        var iframe = document.createElement("iframe");
                        iframe.src = "downloadMultipleExcel?transId="+idsOfSelectedRows+"&empID="+empId+"&downloadType=INITIATOR_TO_EXIGENCY_DOWNLOAD";
                        iframe.style.display = "none";
                        document.body.appendChild(iframe);
                    }

                });
                function resetAllFields(){
                    // $("#searchTypeSel").val("-1");
                    jQuery("#levelGrid").jqGrid('setGridParam',{url:'getAllPromotiondtlBusinessExigency?searchType=ALL',datatype:'json',page:1}).trigger("reloadGrid");
                    $("#eventSel").val("-1");
                    $("#txtBXGYFrom").val("");
                    $("#txtBXGYTo").val("");
                    $("#marketingsel").val("-1");
                    $("#promotionSel").val("-1");
                }
                
                function DisabledAllFields(){
                    // $("#searchTypeSel").val("-1");
                    $("#eventSel").attr("disabled",true);
                    $("#txtBXGYFrom").attr("disabled",true);
                    $("#txtBXGYTo").attr("disabled",true);
                    $("#marketingsel").attr("disabled",true);
                    $("#promotionSel").attr("disabled",true);
                }
                $("#btnSearch").click(function(){
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    $("#message").html('');
                    var url;
                    var category=$("#categorySel").val();
                    var subcategory=$("#subcategorySel").val();
                    var validFrom=$("#txtBXGYFrom").val();
                    var validTo=$("#txtBXGYTo").val();
                    var event=$("#eventSel").val();
                    var mktg=$("#marketingsel").val();
                    var promotype=$("#promotionSel").val();
                                        
                    if(validFrom==undefined || validFrom==null || validFrom.length==0){
                        alert("Please select valid from date.");
                        return false;
                    }else if(validTo==undefined || validTo==null || validTo.length==0){
                        alert("Please select valid to date.");
                        return false;
                    }else{
                        
                        if((validFrom!=null || validFrom.length>0) && (validTo!=null || validTo.length>0) && event==-1 && mktg==-1 && promotype==-1 && category==-1 && subcategory==-1){
                            url='getAllPromotiondtlBusinessExigency?searchType=DATE&startDate='+validFrom+'&endDate='+validTo;
                        } else if((event!=-1 || event !=null) &&(mktg==-1 || mktg==null) && (promotype==-1|| promotype==null) && (category==-1|| category==null) && (subcategory==-1|| subcategory==null) ){
                            url='getAllPromotiondtlBusinessExigency?searchType=DATE_EVENT_TYPE&startDate='+validFrom+'&endDate='+validTo+"&eventSel="+event;
                        }else if((event==-1 || event==null) &&(mktg!=-1 || mktg!=null) && (promotype==-1|| promotype==null)&& (category==-1|| category==null) && (subcategory==-1|| subcategory==null) ){
                            url='getAllPromotiondtlBusinessExigency?searchType=DATE_MARKETTING_TYPE&marketingsel='+mktg+'&startDate='+validFrom+'&endDate='+validTo;
                        }else if((event==-1 || event==null) &&(mktg==-1 || mktg==null) && (promotype!=-1 || promotype!=null) && (category==-1|| category==null) && (subcategory==-1|| subcategory==null)){
                            url='getAllPromotiondtlBusinessExigency?searchType=DATE_PROMO_TYPE&promotionSel='+promotype+"&startDate="+validFrom+'&endDate='+validTo;
                        }else if((event==-1 || event==null) &&(mktg==-1 || mktg==null) && (promotype==-1 || promotype==null) && (category!=-1|| category!=null) && (subcategory==-1|| subcategory==null)){
                            url='getAllPromotiondtlBusinessExigency?searchType=CATEGORY_DATE&categoryName='+category+"&startDate="+validFrom+'&endDate='+validTo;
                        }else if((event==-1 || event==null) &&(mktg==-1 || mktg==null) && (promotype==-1 || promotype==null) && (category!=-1|| category!=null) && (subcategory!=-1|| subcategory!=null)){
                            url='getAllPromotiondtlBusinessExigency?searchType=SUB_CATEGORY_DATE&subcategoryName='+subcategory+"&startDate="+validFrom+'&endDate='+validTo;
                        }                        else if((event!=-1 && mktg!=-1) || (event!=-1 && promotype!=-1) || (mktg!=-1 && promotype!=-1) ||(subcategory!=-1 && subcategory!=-1)){
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
                            url='getAllPromotiondtlBusinessExigency?searchType=DATE&startDate='+validFrom+'&endDate='+validTo;
                        }

                    }                    
                    $("#levelGrid").jqGrid("clearGridData", true);
                    jQuery("#levelGrid").jqGrid('setGridParam',{url:url,datatype:'json',page:1}).trigger("reloadGrid");
                });
                function addoptionToSubCategory(){
                    $("#subcategorySel").empty();
                    var select = document.getElementById("subcategorySel");
                    var newoption = document.createElement('option');
                    newoption.text = "---Select Sub Category---";
                    newoption.value = -1;
                    select.add(newoption);
                }
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
                    $("#message").html("");
                    $('#exigency')[0].reset();
                    $("#trRemarks").hide();
                    $("#categorySel").val("-1");
                    $("#subcategorySel").val("-1");
                    $("#approveBtn").attr("disabled",false);
                    $("#rejectBtn").attr("disabled",true);
                    addoptionToSubCategory();
                    jQuery("#levelGrid").jqGrid('setGridParam',{url:"getAllPromotiondtlBusinessExigency?searchType=ALL",datatype:'json',page:1}).trigger("reloadGrid");

                });
            });

        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <s:form action="donothing" id="exigency" name="exigency">

            <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="f14"align="center">

                <tr>
                    <td colspan="2"><h1>Business Exigency Approval : </h1></td>
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
                    <td align="center" colspan="2">
                        <table align="center">
                            <tr>
                                <%--  <td> Search Type :</td>
                                <td><s:select   list="#{'-1':'---Select---','1':'Date','2':'Event Type','3':'Marketing Type','4':'Promotion Type'}" id="searchTypeSel"  cssClass="dropdown"/>
                                </td>
                                --%>

                                <td align="right">Start Date :<span class="errorText">*&nbsp;</span></td>
                                <td align="left"><s:textfield id="txtBXGYFrom"  readonly="true"/></td>
                                <td align="right">End Date :<span class="errorText">*&nbsp;</span></td>
                                <td align="left"><s:textfield id="txtBXGYTo" readonly="true"/></td>
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
                    <td colspan="2" style="height: 10px;">
                    </td>
                </tr>
                <tr>
                    <td  align="center" colspan="2">
                        <table id="levelGrid"></table>
                        <div id="level1Pager"></div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <img src="images/spacer.gif" width="10" height="10"/>
                    </td>
                </tr>
                <tr>
                    <td colspan="2" align="center">
                        <table width="50%">
                            <tr>
                                <td align="right">
                                    Reason for rejection :
                                </td>
                                <td align="left" >
                                    <s:select name="reasonName" id="mstReasonRejectionVOs" list="mstReasonRejectionVOs"  headerKey="-1" headerValue="---Select----"/>
                                </td>
                            </tr>
                            <tr id="trRemarks">
                                <td align="right">Remarks : </td>
                                <td align="left"><textarea cols="40" rows="4" name="manualRemarks" id="txtMenualRemarks" title="Max 1000 Characters."></textarea>
                            </tr>
                        </table>
                    </td>

                </tr>

                <tr>
                    <td colspan="2">
                        <img src="images/spacer.gif" width="10" height="10"/>
                    </td>
                </tr>
                <tr align="center">
                    <td align="center" colspan="2">
                        <table width="30%" align="center">
                            <tr align="center">
                                <s:hidden id="transPromoID" name="transPromoID" />
                                <s:hidden id="actionName" name="actionName" />
                                <s:hidden id="rejReason" name="rejReason" />

                                <td>
                                    <s:submit id="approveBtn" name="approveBtn"  value="Approve" cssClass="btn" action="HoldAppRejTransPromoreqBusinessExigency"   />
                                </td>
                                <td>
                                    <s:submit id="rejectBtn" name="rejectBtn"  value="Reject" cssClass="btn" action="HoldAppRejTransPromoreqBusinessExigency"   />
                                </td>
                                <td style="height: 200%">
                                    <input type="button" name="downloadBtn" id="downloadBtn" value="Download"  class="btn"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <!--                <td colspan="2">
                                        <input type="button" name="approveBtn" id="approveBtn" value="Approve"  class="btn"/>&nbsp;&nbsp;
                                        <input type="button" name="rejectBtn" id="rejectBtn" value="Reject"  class="btn"/>&nbsp;&nbsp;
                                        <input type="button" name="downloadBtn" id="downloadBtn" value="Download"  class="btn"/>
                                    </td>-->
                </tr>

                <tr>
                    <td colspan="2">
                        <img src="images/spacer.gif" width="10" height="10"/>
                    </td>
                </tr>

            </table>
        </s:form>
    </body>
</html>
