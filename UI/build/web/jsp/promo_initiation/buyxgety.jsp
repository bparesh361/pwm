<%-- 
    Document   : flatdiscount
    Created on : Dec 26, 2012, 12:32:45 PM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buy x Get y</title>
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
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });
                var isManualEntryFormSubmit=1;
                disabledControllOnPageLoad();
                setUploadFormForFileValidationFailure();
                
                 var reqUrl="";
                var redirected=$("#isInitiatorRedirect").val();
                var sessionPromoId=$("#SessionmstPromoId").val();
                if(redirected!=undefined && redirected.length>0 && redirected=="1"){
                    reqUrl="getPromotiondtl_promoIdwise?mstPromoId="+sessionPromoId;
                }else{
                    reqUrl="getAllPromotiondtl_initiator";
                }
                //This function set the Upload File View In Case Any Of the  File Validation Gets Failed
                function setUploadFormForFileValidationFailure(){
                    var hdnFileError=$("#hdnisUploadFileError").val();
                    //                    alert("hdnisUploadFileError : "+hdnFileError);
                    if(hdnFileError!=undefined && hdnFileError.length>0 && hdnFileError=="1"){
                        tabSwitch_2(2,2, 'tab_', 'content_');


                        $("#rbtnManualBX").attr("disabled",true);
                        $("#rbtnManualBX").attr("checked",false);
                        $("#rbtnUploadBX").attr("checked",true);

                        isManualEntryFormSubmit=0;

                        $("#tdErrorFile").show();
                    }
                    
                }

                function disabledControllOnPageLoad(){
                    $("#tdErrorFile").hide();
                }

                // This function is used to set and select the selected request id on grid complete on page load.
                // This scenario happens when file is uploaded with errors.
                function setGridRowSelectionOnFileFailureInGridCompelete(){
                    var hdnPromoId=$("#mstPromoId").val();
                    if(hdnPromoId!=undefined && hdnPromoId.length>0 && hdnPromoId!=null){
                        //                        alert("promo Id : "+hdnPromoId);
                        $("#reqGrid").resetSelection();
                        $("#reqGrid").jqGrid('setSelection', hdnPromoId);
                    }

                }
               
                jQuery("#reqGrid").jqGrid({
                    url:reqUrl,
                    datatype: 'json',
                    width: 875,
                    height:110,
                    colNames:['Request Number','Request Date','Request Name', 'Event','Marketing Type','Category','Sub Category'],
                    colModel:[
                        {name:'reqno',index:'reqno', width:170, align:"center"},
                        {name:'date',index:'date', width:200, align:"center"},
                        {name:'reqName',index:'reqName', width:170, align:"center"},
                        {name:'event',index:'event', width:200, align:"center"},
                        {name:'mktgtype',index:'mktgtype', width:200, align:"center"},
                        {name:'category',index:'category', width:200, align:"center"},
                        {name:'subcategory',index:'subcategory', width:200, align:"center"},

                    ],
                    rowNum:5,
                    rowList:[5,7,10],
                    viewrecords: true,
                    pager: '#reqPager',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:"",
                    onSelectRow: function(id) {
                        $("#mstPromoId").val(id);
                    },gridComplete:function(){
                        setGridRowSelectionOnFileFailureInGridCompelete();
                    }
                });

                jQuery("#XarticleGrid").jqGrid({
                    url:"",
                    datatype: 'local',
                    width: 850,
                    height:110,
                    colNames:['Article Number','Article Description','MC Code', 'MC Description','QTY'],
                    colModel:[
                        {name:'xartNo',index:'xartNo', width:100, align:"center"},
                        {name:'xartDesc',index:'xartDesc', width:200, align:"center"},
                        {name:'xmcCode',index:'xmcCode', width:100, align:"center"},
                        {name:'xmcDesc',index:'xmcDesc', width:200, align:"center"},
                        {name:'xqty',index:'xqty', width:150, align:"center",hidden:true},
                    ],
                    rowNum:10,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#XarticlePager',
                    editurl:'donothing',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:""
                }).navGrid('#XarticlePager',
                {add:false, edit:false, del:false, search:false, refresh: false},
                {width:"auto"},// Default for Add
                {width:"auto"},// Default for edit
                {width:"auto"}// Default for Delete
            ).navButtonAdd(
                '#XarticlePager',
                {
                    caption:"Del",
                    buttonicon:"ui-icon-add",
                    onClickButton: function(){
                        var toDelete = $("#XarticleGrid").jqGrid('getGridParam','selrow');
                        //alert("Selected Row :"+toDelete);
                        var maxRows=jQuery("#XarticleGrid tr").length;
                        if(toDelete!=null){
                            if(maxRows>1){
                                $("#XarticleGrid").jqGrid(
                                'delGridRow',
                                toDelete,
                                {url:'donothing',reloadAfterSubmit:true}
                            );
                                return true;
                            }
                        }else{
                            alert("Please, Select a Row");
                            return false;
                        }
                    }
                }
            );

                
                
                
                $("#rbtnManualBX").click(function(){
                    //BX_uploadTable   BX_manualTable

                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(1,2, 'tab_', 'content_');
                    }
                    isManualEntryFormSubmit=1;
                });
                $("#rbtnUploadBX").click(function(){                    
                    var checked = $(this).attr('checked', true);

                    if(checked){
                        tabSwitch_2(2,2, 'tab_', 'content_');
                    }
                    isManualEntryFormSubmit=0;
                });
            
                var isArticleEntered=1;
                var  articleCode,articleDesc,mcCode,mcDesc,qty;
                $("#btnValidateArticle").click(function (){
                    articleCode= $("#txtArticleNo").val();
                    mcCode=$("#txtMCCode").val();

                    if( articleCode !=undefined && articleCode.length>0 ){
                        isArticleEntered=1;
                    }else{
                        isArticleEntered=0;
                    }
                    var mstPromoId=$("#mstPromoId").val();
                    //Validate The Article OR MC From js/PromotionCommonUtil.js And Set The Response In textbox
                    validateArticleORMCCode(isArticleEntered, mcCode, articleCode,"txtArticleNo","txtArticleDesc","txtMCCode","txtMCDesc",mstPromoId,"1");
                    $("#btnAddArticle").attr("disabled", false);
                    //                    alert("is article ent  flag :"+isArticleEntered);
                    
                });

                var tempIndex=0;
                $("#btnAddArticle").click(function (){
                    articleCode= $("#txtArticleNo").val();
                    articleDesc=$("#txtArticleDesc").val();
                    mcCode=$("#txtMCCode").val();
                    mcDesc=$("#txtMCDesc").val();
                    //                    qty=$("#txtXQty").val();
                    qty="0";
                                        
                    if(jQuery("#XarticleGrid tr").length>1){
                        var tableDataIds= jQuery('#XarticleGrid').getDataIDs();
                        //alert("tab : "+tableDataIds);
                        var insertData;
                        if(isArticleEntered==1){
                            insertData=$("#txtArticleNo").val();
                        }else{
                            insertData=$("#txtMCCode").val();
                        }
                        for(var index=0;index<=tableDataIds.length;index++)
                        {
                            var rowData = jQuery('#XarticleGrid').getRowData(tableDataIds[index]);

                            // alert("Grid Data : "+rowData.bn);
                            if(isArticleEntered==1){
                                if(insertData==rowData.xartNo){
                                    alert('Selected aricle code already exist.');
                                    $("#txtArticleNo").focus();
                                    resetArticleDtl();
                                    return false;
                                }
                            }else{
                                if(insertData==rowData.xmcCode){
                                    alert('Selected MC code already exist.');
                                    $("#txtMCCode").focus();
                                    resetArticleDtl();
                                    return false;
                                }
                            }

                        }
                    }

                    //                    if(qty==undefined || qty==null || qty.length==0){
                    //                        alert("Please enter qunatity");
                    //                        $("#txtXQty").focus();
                    //                        return false;
                    //                    }else if(!isNumeric(qty)){
                    //                        alert("Qunatity should be numeric.");
                    //                        $("#txtXQty").focus();
                    //                        return false;
                    //                    }

                    $("#XarticleGrid").jqGrid('addRowData',tempIndex,{xartNo:articleCode,xartDesc:articleDesc,xmcCode:mcCode,xmcDesc:mcDesc,xqty:qty});
                    resetArticleDtl();

                    tempIndex++;
                });

                $("#btnSave").click(function(){                    
                    //alert(isManualEntryFormSubmit);
                    // return false;

                                        var validateFields=validateFlatDiscountFields();
                                        if(validateFields[0]==false){
                                            alert(validateFields[1]);
                                            return false;
                                        }
                   

//                    if(isManualEntryFormSubmit==1){
//                        //alert("inside");
//                        $("#isManualEntry").val("1");
//                    }else{
//                        $("#isManualEntry").val("0");
//                    }
                    // alert( $("#isManualEntry").val());
                    //return false;
                     $("#createFlatDiscount").submit();
                    
                });

                function getArticleData(){
                    var articleGridIds = jQuery("#XarticleGrid").getDataIDs();
                    if(articleGridIds.length > 0){
                        //alert("Total Qual Grid Data = "+qualGridIds);
                        var tempIndex=0;
                        var arr=new Array();

                        for(var index=0;index<=articleGridIds.length;index++){
                            //Columns Values!!
                            var rowData=jQuery('#XarticleGrid').getRowData(articleGridIds[index]);
                            arr[tempIndex]=rowData.xartNo;
                            arr[++tempIndex]=rowData.xartDesc;
                            arr[++tempIndex]=rowData.xmcCode;
                            arr[++tempIndex]=rowData.xmcDesc;
                            arr[++tempIndex]=rowData.xqty;
                            tempIndex++;
                        }
                        // alert("Qual arr : "+arr);
                        $("#XarticleGridData").val(arr);
                    }

                }

                function resetArticleDtl(){
                    $("#txtArticleNo").val("");
                    $("#txtArticleDesc").val("");
                    $("#txtMCCode").val("");
                    $("#txtMCDesc").val("");
                    $("#txtXQty").val("");
                    $("#btnAddArticle").attr("disabled", true);
                    $("#txtArticleNo").focus();
                }
                
                function validateFlatDiscountFields(){
                    
                    var mstPromoId=$("#mstPromoId").val();
                    if(mstPromoId==undefined || mstPromoId==null || mstPromoId.length==0){
                        return[false,"Please select request"];
                    }
                    if(isManualEntryFormSubmit==0){
                        var fileid=  document.getElementById("articleFile").value;
                        if(fileid==null ||fileid==""){
                            return [false,"Please select file to upload."];
                        }
                        var valid_extensions = /(.csv)$/i;
                        if(valid_extensions.test(fileid)){
                        } else{
                            return [false,'Invalid File. Please Upload CSV File Only.'];
                        }
                         $("#isManualEntry").val("0");
                    }else{                        

                         getArticleData();
                         var xarticle= $("#XarticleGridData").val();
                        if(xarticle.length==0){                            
                           return[false,"Please enter Article detail or Mc detail for X."];
                        }
                       $("#isManualEntry").val("1");

                    }
                  

                    var discountConfig=$("#txtDisConfig").val();
                    //                    var validateDiscountConfig=isBlank(discountConfig,"Discount Config");
                    //                    if(validateDiscountConfig[0]==false){
                    //                        $("#txtDisConfig").focus();
                    //                        return[false,validateDiscountConfig[1]];
                    //                    }else if(!isNumeric(discountConfig)){
                    //                        $("#txtDisConfig").focus();
                    //                        return [false,"Discount Config should be numeric."];
                    //                    }

                    var problemIdCheck = checkComboSelection("txtDisConfig", "Discount Config");
                    if(problemIdCheck[0]==false){
                        return [false,problemIdCheck[1]];
                    }

                    var txtbuy=$("#txtbuy").val();
                    var txtget=$("#txtget").val();
                    

                    var validatebuy=isBlank(txtbuy,"Buy Value");
                    if(validatebuy[0]==false){
                        $("#txtbuy").focus();
                        return[false,validatebuy[1]];
                    }else if(!isNumeric(txtbuy)){
                        $("#txtbuy").focus();
                        return [false,"Buy Value should be numeric."];
                    }

                     var blankmget = isBlank(txtget,"Get ");
                    if(blankmget[0]==false){
                        $("#txtget").focus();
                        return[false,blankmget[1]];
                    }else if(!isNumeric(txtget)){
                        $("#txtget").focus();
                        return [false,"get Value should be numeric."];
                    }
                   

                    var discountConfigValue=$("#txtdisValue").val();
                    var validateDiscountConfigValue=isBlank(discountConfigValue,"Discount Config Value");
                    if(validateDiscountConfigValue[0]==false){
                        $("#txtdisValue").focus();
                        return[false,validateDiscountConfigValue[1]];
                    }else if(!isNumeric(discountConfigValue)){
                        $("#txtdisValue").focus();
                        return [false,"Discount Config Value should be numeric."];
                    }

                    var marginAchivement=$("#txtBXGYmargin").val();
                    var validateMarginAchivement=isBlank(marginAchivement,"Margin Achievement");
                    if(validateMarginAchivement[0]==false){
                        $("#txtBXGYmargin").focus();
                        return[false,validateMarginAchivement[1]];
                    }else if(!isNumeric(marginAchivement)){                        
                        $("#txtBXGYmargin").focus();
                        return [false,"Margin Achievement Value should be numeric."];
                    }

                    var ticketSizeGrowth=$("#txtBXGYgrowth").val();
                    var validateTicketSize=isBlank(ticketSizeGrowth,"Growth In Ticket Size");
                    if(validateTicketSize[0]==false){
                        $("#txtBXGYgrowth").focus();
                        return[false,validateTicketSize[1]];
                    }else if(!isNumeric(ticketSizeGrowth)){                        
                        $("#txtBXGYgrowth").focus();
                        return [false,"Growth In Ticket Size should be numeric."];
                    }

                    var sellQty=$("#txtBXGYsellQty").val();
                    var validateSellQty=isBlank(sellQty,"Sell Thru-V/S Quantity");
                    if(validateSellQty[0]==false){
                        $("#txtBXGYsellQty").focus();
                        return[false,validateSellQty[1]];
                    }else if(!isNumeric(sellQty)){                        
                        $("#txtBXGYsellQty").focus();
                        return [false,"Sell Thru-V/S Quantity should be numeric."];
                    }

                    var conversionGrowth=$("#txtBXGYgrowthConver").val();
                    var validateConversionGrowth=isBlank(conversionGrowth,"Growth In Conversion");
                    if(validateConversionGrowth[0]==false){
                        $("#txtBXGYgrowthConver").focus();
                        return[false,validateConversionGrowth[1]];
                    }else if(!isNumeric(conversionGrowth)){                        
                        $("#txtBXGYgrowthConver").focus();
                        return [false,"Growth In Conversion should be numeric."];
                    }

                    var qtyValueSellGrowth=$("#txtBXGYsalegrowth").val();
                    var validateSellQtyValue=isBlank(qtyValueSellGrowth,"Sale Growth Both In Qty And Value");
                    if(validateSellQtyValue[0]==false){
                        $("#txtBXGYsalegrowth").focus();
                        return[false,validateSellQtyValue[1]];
                    }else if(!isNumeric(qtyValueSellGrowth)){                        
                        $("#txtBXGYsalegrowth").focus();
                        return [false,"Sale Growth Both In Qty And Value should be numeric."];
                    }

                    var validFrom=$("#txtBXGYFrom").val();
                    var validTo=$("#txtBXGYTo").val();

                    if(validFrom==undefined || validFrom==null || validFrom.length==0){
                        return[false,"Please select valid from date."];
                    }else if(validTo==undefined || validTo==null || validTo.length==0){
                        return[false,"Please select valid to date."];
                    }
                    var remarksLength=1000;
                    var rmarksVal=$("#txtremarks").val();
                    var remarksCheck=isBlank(rmarksVal,"Remarks");
                    if(remarksCheck[0]==false){
                        $("#txtremarks").focus();
                        return[false,remarksCheck[1]];
                    }

                    remarksCheck=checkLength(rmarksVal, "Remarks", remarksLength);
                    if(remarksCheck[0]==false){
                        $("#txtremarks").focus();
                        return[false,remarksCheck[1]];
                    }
                    
                
                    return[true,''];
                    
                }

                $(function() {
                    var dates = $( "#txtBXGYFrom, #txtBXGYTo" ).datepicker({
                        defaultDate: "+1w",
                        numberOfMonths: 1,
                        //changeMonth: true,
                        //changeYear: true,
                        dateFormat: 'dd-mm-yy',
                        // maxDate : '+0d',
                        minDate:'+0d',


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

            });
        </script>      
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />

        <div id="middle_cont">
            <s:form id="createFlatDiscount" action="createbxgysingle_action" method="POST" enctype="multipart/form-data">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">
                    <tr>
                        <td width="100%" align="center" >
                            <div id="message">
                                <s:actionmessage cssClass="successText"/>
                                <s:actionerror cssClass="errorText"/>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><h1>Buy X Get Y</h1></td>
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
                        <td><h1>Article Detail</h1></td>
                    </tr>
                    <tr>
                        <td>
                            <table id="BuyXGetYTable" width="90%" border="0" align="center" cellpadding="2" cellspacing="4">

                                <input type="hidden" name="formvo.isManualEntry" id="isManualEntry" />
                                <s:hidden name="formvo.mstPromoId" id="mstPromoId" value="%{formvo.mstPromoId}"/>
                                <input type="hidden" name="formvo.manualArticleData" id="XarticleGridData" />
                                <s:hidden id="hdnisUploadFileError" name="formvo.isUploadFileError" value="%{formvo.isUploadFileError}"/>
                                 <s:hidden  name="formvo.isInitiatorRedirect" id="isInitiatorRedirect" value="%{formvo.isInitiatorRedirect}" />
                            <s:hidden  name="formvo.SessionmstPromoId" id="SessionmstPromoId" value="%{formvo.SessionmstPromoId}" />
                                <tr>
                                    <td align="left">
                                        <table width="60%" >
                                            <tr>
                                                <td align="left"><b>Capture Article</b></td>
                                                <td align="right">Manual Entry</td>
                                                <td align="left">
                                                    <input type="radio" id="rbtnManualBX" name="captureArticle" checked="true" value="1"/>
                                                </td>
                                                <td align="right">Upload Article</td>
                                                <td align="left">
                                                    <input type="radio" id="rbtnUploadBX" name="captureArticle"  value="0"/>
                                                </td>
                                                <td align="center" >
                                                    <a href ="#" class="download-sample " onclick="tb_show( '', 'viewArticleMCSearch_action?height=150&width=520');">
                                                        Article Search
                                                    </a>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td align="left">
                                        <table width="95%" border="0" align="center" cellpadding="2" cellspacing="4">
                                            <tr>
                                                <td >
                                                    <div id="BXtabMaster" class="tabbed_box">
                                                        <ul class="tabs">
                                                            <li><a href="#" id="tab_1" class="active" onclick="return false;">Manual Article Entry</a></li>
                                                            <li><a href="#" id="tab_2" onclick="return false;">Upload Article File</a></li>
                                                        </ul>
                                                        <div id="content_1" class="content" >
                                                            <table id="BX_manualTable" width="100%" border="0" align="center" cellpadding="2" cellspacing="4">                                                                
                                                                <tr>
                                                                    <td align="right">Article Number</td>
                                                                    <td align="left"><input type="text"  id="txtArticleNo" /></td>
                                                                    <td align="right">MC Code</td>
                                                                    <td align="left"><input type="text"  id="txtMCCode" /></td>
                                                                    <td align="left">
                                                                        <input type="button" name="btnValidateArticle" id="btnValidateArticle" value="Validate" title="Please Select Request Before Validate"/>
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td align="right">Article Description</td>
                                                                    <td align="left"><input type="text" id="txtArticleDesc" readonly="true" /></td>
                                                                    <td align="right">MC Description</td>
                                                                    <td align="left"><input type="text"  id="txtMCDesc" readonly="true" /></td>
                                                                    <td align="left">
                                                                        <input type="button" name="btnAddArticle" id="btnAddArticle" value="Add" disabled/>
                                                                    </td>
                                                                </tr>
                                                                <tr style="display: none">
                                                                    <td align="right">Qty</td>
                                                                    <td align="left"><input type="text"  id="txtXQty"  /></td>
                                                                </tr>
                                                                <tr>
                                                                    <td colspan="5" align="center">
                                                                        <table id="XarticleGrid"></table>
                                                                        <div id="XarticlePager"></div>
                                                                    </td>
                                                                </tr>

                                                            </table>
                                                        </div>
                                                        <div id="content_2" class="content">
                                                            <table id="BX_uploadTable"  align="center" cellpadding="2" cellspacing="4">
                                                                <tr>
                                                                    <td width="2%"></td>
                                                                    <td align="right">Upload File</td>
                                                                    <td align="left">
                                                                        <s:file id ="articleFile" name="articleFileUpload" ></s:file>
                                                                    </td>
                                                                    <td align="center">
                                                                        <!--                                                                        <input  type="submit" id="btnBXUpload" name="btnBXUpload"  Value="Upload" />-->
                                                                    </td>
                                                                </tr>
                                                                <tr>
                                                                    <td width="2%"></td>
                                                                    <td align="right">Download</td>
                                                                    <td align="center">
                                                                        <a href ="downloadSampleArticleMCFile" class="download-sample ">
                                                                            Sample File
                                                                        </a>
                                                                    </td>
                                                                    <td align="center" id="tdErrorFile">                                                                        
                                                                        <s:a  href="%{formvo.errorfilePath}" class="downloadError" id="downloadErrorFile" > Status File </s:a>
                                                                    </td>
                                                                </tr>

                                                            </table>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>

                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td align="center">
                            <table width="40%" border="0" align="center" cellpadding="2" cellspacing="4">
                                <tr>
                                    <td>Buy</td>
                                    <td>
                                        <s:textfield id="txtbuy" name="formvo.txtbuy" />
                                    </td>
                                    <td>Get</td>
                                    <td><s:textfield id="txtget" name="formvo.txtget" /></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr><td height="15px" ></td></tr>
                    <tr>
                        <td align="center">
                            <table width="70%" border="0" align="center" cellpadding="2" cellspacing="4">
                                <tr>
                                    <td>Discount Config   </td>
                                    <td>
                                        <s:select headerKey="-1" headerValue="---Select Discount---" list="#{'0':'Value Off','1':'Percentage Off','2':'Flat Discount'}" id="txtDisConfig" name="formvo.discountConfig" value="%{formvo.discountConfig}" cssClass="dropdown"/>
                                    </td>
                                    <td>Value</td>
                                    <td><s:textfield id="txtdisValue" name="formvo.discountConfigValue" value="%{formvo.discountConfigValue}" /> </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr><td height="5px" ></td></tr>
                    <tr>
                        <td align="center">
                            <table width="70%" border="0" align="center" cellpadding="2" cellspacing="4">
                                <tr>
                                    <td >Margin Achievement </td>
                                    <td align="left"><s:textfield id="txtBXGYmargin" name="formvo.marginAchivement" value="%{formvo.marginAchivement}" /></td>
                                </tr>
                                <tr>
                                    <td>Growth in Ticket size</td>
                                    <td><s:textfield id="txtBXGYgrowth" name="formvo.ticketSizeGrowth" value="%{formvo.ticketSizeGrowth}" /></td>
                                </tr>
                                <tr>
                                    <td>Sell thru – v/s quantity</td>
                                    <td><s:textfield id="txtBXGYsellQty" name="formvo.sellQty" value="%{formvo.sellQty}" /></td>
                                </tr>
                                <tr>
                                    <td>Growth in conversions</td>
                                    <td><s:textfield id="txtBXGYgrowthConver" name="formvo.conversionGrowth" value="%{formvo.conversionGrowth}" /></td>
                                </tr>
                                <tr>
                                    <td>Sales Growth – both in quantity and value</td>
                                    <td><s:textfield id="txtBXGYsalegrowth" name="formvo.qtyValueSellGrowth" value="%{formvo.qtyValueSellGrowth}" /></td>
                                </tr>
                                <tr>
                                    <td colspan="2">
                                        <table width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                            <tr>
                                                <td>Valid From</td>
                                                <td><s:textfield id="txtBXGYFrom" name="formvo.validFrom" value="%{formvo.validFrom}" /></td>
                                                <td>Valid To</td>
                                                <td><s:textfield id="txtBXGYTo" name="formvo.validTo" value="%{formvo.validTo}" /></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td>Remarks</td>
                                    <td ><textarea id="txtremarks" name="formvo.txtRemarks"  rows="3" cols="25" style="width: 70%;height: 20%"></textarea></td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                    <tr>
                        <td align="center">
                            <table width="35%">
                                <tr>
                                    <td align="center">
                                        <input align="center" type="submit" id="btnSave" name="btnSave"  Value="Save" Class="btn" />
                                    </td>
<!--                                    <td align="center">
                                        <input align="left" type="button" id="btnReset" name="btnReset"  Value="Reset" Class="btn" />
                                    </td>-->

                                </tr>
                            </table>
                        </td>
                    </tr>
                </s:form>
            </table>
        </div>
    </body>
</html>

