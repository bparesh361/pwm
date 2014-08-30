<%-- 
    Document   : createproposal
    Created on : Dec 21, 2012, 10:54:17 AM
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
        <title>Create Proposal</title>

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
                setUploadFormForLocalFileValidationFailure();
                setUploadFormForODSFileValidationFailure();



                //This function set the proposal ID in hidden field ad disable other controlls when file is having any error
                //Only File UPload and Save And Submit buttons are enabled. Rest Of the things are disabled.
                function setUploadFormForODSFileValidationFailure(){
                    var hdnproposalID=$("#hdnProposalId").val();
                    //                    alert("Proposal Id : "+hdnproposalID);
                    if(hdnproposalID!=undefined && hdnproposalID.length>0){
                        tabSwitch_2(2,2, 'tab_', 'content_');
                        
                        
                        $("#rbtnManual").attr("disabled",true);
                        $("#rbtnManual").attr("checked",false);
                        $("#rbtnUpload").attr("checked",true);

                        //                        $("#deptUploadSel").attr("disabled",true);
                        //                        $("#problemUploadSel").attr("disabled",true);
                        //                        $("#txtUploadRemarks").attr("disabled",true);
                        //                        $("#txtUploadSolDesc").attr("disabled",true);
                        //                        $("#promoUploadSel").attr("disabled",true);
                        //                        $("#txtUploadPromoDesc").attr("disabled",true);


                        //                        $("#btnReset").attr("disabled",true);

                        //Check Update Flag If it is 1 then we set upload file update view.
                        var hdnIsUpdateFlag=$("#hdnIsUpdateFlagForFileUpload").val();
                        //                        alert("hdnisUpdate Flag : "+hdnIsUpdateFlag);
                        if(hdnIsUpdateFlag=="1"){
                            $("#tdErrorFile").hide();
                            $("#tdDownloadArticleFileForUpdate").show();
                            $("#downloadOtherFile").show();
                            $("#btnReset").attr("disabled",true);

                        }else{
                            $("#tdErrorFile").show();
                            $("#tdDownloadArticleFileForUpdate").hide();
                            $("#downloadOtherFile").hide();
                            $("#btnReset").attr("disabled",false);
                        }

                        var hdnFileError=$("#hdnisUploadFileError").val();
                        //                        alert("hdnisUploadFileError : "+hdnFileError);
                        if(hdnFileError!=undefined && hdnFileError.length>0 && hdnFileError=="1"){
                            $("#tdErrorFile").show();
                            $("#tdDownloadArticleFileForUpdate").show();
                            $("#downloadOtherFile").show();
                            $("#btnReset").attr("disabled",false);
                        }

                        isManualEntryFormSubmit=0;
                    }
                }

                //This function set the Upload File View In Case Any Of the Local File Validation Gets Failed
                function setUploadFormForLocalFileValidationFailure(){
                    var hdnFileError=$("#hdnisUploadFileError").val();
                    //                    alert("hdnisUploadFileError : "+hdnFileError);
                    if(hdnFileError!=undefined && hdnFileError.length>0 && hdnFileError=="1"){
                        tabSwitch_2(2,2, 'tab_', 'content_');


                        $("#rbtnManual").attr("disabled",true);
                        $("#rbtnManual").attr("checked",false);
                        $("#rbtnUpload").attr("checked",true);
                        
                        isManualEntryFormSubmit=0;
                    }
                }


                function disabledControllOnPageLoad(){
                    $("#tdErrorFile").hide();
                    $("#tdDownloadArticleFileForUpdate").hide();
                    $("#downloadOtherFile").hide();
                }

                $("#rbtnManual").click(function(){
                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(1,2, 'tab_', 'content_');
                    }
                    isManualEntryFormSubmit=1;
                });
                $("#rbtnUpload").click(function(){
                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(2,2, 'tab_', 'content_');
                    }
                    isManualEntryFormSubmit=0;
                });
                               
                jQuery("#articleGrid").jqGrid({
                    datatype: 'local',
                    width: 850,
                    height:210,
                    colNames:['Article Number','Article Description','MC Code', 'MC Description'],
                    colModel:[
                        {name:'artNo',index:'artNo', width:100, align:"center"},
                        {name:'artDesc',index:'artDesc', width:200, align:"center"},
                        {name:'mcCode',index:'mcCode', width:100, align:"center"},
                        {name:'mcDesc',index:'mcDesc', width:200, align:"center"},
                    ],                    
                    viewrecords: true,
                    pager: '#articlePager',
                    multiselect: false,                    
                    editurl:'donothing',
                    hidegrid : false,
                    imgpath: "themes/basic/images",
                    caption:"Article Detail"
                }).navGrid('#articlePager',
                {add:false, edit:false, del:false, search:false, refresh: false},
                {width:"auto"},// Default for Add
                {width:"auto"},// Default for edit
                {width:"auto"}// Default for Delete
            ).navButtonAdd(
                '#articlePager',
                {
                    caption:"DELETE",
                    buttonicon:"ui-icon-add",
                    onClickButton: function(){
                        var toDelete = $("#articleGrid").jqGrid('getGridParam','selrow');
                        //alert("Selected Row :"+toDelete);
                        var maxRows=jQuery("#articleGrid tr").length;
                        if(toDelete!=null){
                            if(maxRows>1){
                                $("#articleGrid").jqGrid(
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

                var isArticleEntered=1;
                var  articleCode,articleDesc,mcCode,mcDesc;
                /*
                $("#btnValidateArticle").click(function (){
                    articleCode= $("#txtArticleNo").val();
                    mcCode=$("#txtMCCode").val();

                    if( articleCode !=undefined && articleCode.length>0 ){
                        isArticleEntered=1;
                    }else{
                        isArticleEntered=0;
                    }
                    
                    //Validate The Article OR MC From js/PromotionCommonUtil.js And Set The Response In textbox
                    validateArticleORMCCode(isArticleEntered, mcCode, articleCode,"txtArticleNo","txtArticleDesc","txtMCCode","txtMCDesc",undefined,"0");
                    
                });
                 */
                var tempIndex=0;
                $("#btnAddArticle").click(function (){

                    // CR2 Change Start
                    
                    articleCode= $("#txtArticleNo").val();
                    mcCode=$("#txtMCCode").val();

                    if( articleCode !=undefined && articleCode.length>0 ){
                        isArticleEntered=1;
                    }else{
                        isArticleEntered=0;
                    }

                    //Validate The Article OR MC From js/PromotionCommonUtil.js And Set The Response In textbox
                    var validatePromoArticle=validatePromotionArticleORMCCode(isArticleEntered, mcCode, articleCode,"txtArticleNo","txtArticleDesc","txtMCCode","txtMCDesc",undefined,undefined,undefined,"0");

                    //                    alert("validate : "+validatePromoArticle);
                    if(validatePromoArticle[0]==false){
                        alert(validatePromoArticle[1]);
                        return false;
                    }

                    //Cr2 Change Finished

                    //check condition if articledescription and mc details are not null
                    //if it is null then article is not validated                    
                    articleDesc=$("#txtArticleDesc").val();
                    mcCode=$("#txtMCCode").val();
                    mcDesc=$("#txtMCDesc").val();
                    

                    if( (articleDesc==null || articleDesc.length==0) || (mcCode==null || mcCode.length==0) || (mcDesc==null || mcDesc.length==0) ){
                        alert("Article Is Not Validated.");
                        return false;
                    }

                    var validateArticle=addArticleInGrid();
                    if(validateArticle[0]==false){
                        alert(validateArticle[1]);
                        //                        $("#btnAddArticle").attr("disabled", true);
                        $("#btnValidateArticle").attr("disabled", false);
                        $("#txtArticleNo").attr("disabled", false);
                        $("#txtMCCode").attr("disabled", false);
                        return false;
                    }
                    tempIndex++;

                    $("#txtArticleNo").attr("disabled", false);
                    $("#txtMCCode").attr("disabled", false);
                    
                    //Cr2 Change
                    //                    $("#btnAddArticle").attr("disabled", true);
                    $("#btnValidateArticle").attr("disabled", false);
                    
                });

                function addArticleInGrid(){
                    articleCode= $("#txtArticleNo").val();
                    articleDesc=$("#txtArticleDesc").val();
                    mcCode=$("#txtMCCode").val();
                    mcDesc=$("#txtMCDesc").val();

                    if(jQuery("#articleGrid tr").length>1){
                        var tableDataIds= jQuery('#articleGrid').getDataIDs();
                        //alert("tab : "+tableDataIds);
                        var insertData;
                        if(isArticleEntered==1){
                            insertData=$("#txtArticleNo").val();
                        }else{
                            insertData=$("#txtMCCode").val();
                        }
                        for(var index=0;index<=tableDataIds.length;index++)
                        {
                            var rowData = jQuery('#articleGrid').getRowData(tableDataIds[index]);

                            // alert("Grid Data : "+rowData.bn);
                            if(isArticleEntered==1){
                                if(insertData==rowData.artNo){
                                    resetArticleDtl();
                                    $("#txtArticleNo").focus();
                                    return [false,'Selected aricle code already exist.'];
                                }
                            }else{
                                if(insertData==rowData.mcCode){                                    
                                    resetArticleDtl();
                                    $("#txtMCCode").focus();
                                    return [false,'Selected MC code already exist.'];
                                }
                            }

                        }
                    }


                    $("#articleGrid").jqGrid('addRowData',tempIndex,{artNo:articleCode,artDesc:articleDesc,mcCode:mcCode,mcDesc:mcDesc});
                    resetArticleDtl();

                    return [true,''];
                }
                
                $("#btnSaveDraft").click(function (){
                    var validateCall=validateCreateProposalDtl();
                    if(validateCall[0]==false){
                        alert(validateCall[1]);
                        return false;
                    }

                    if(isManualEntryFormSubmit==1){
                        $("#isSaveDraft").val("1");
                        $("#createManualProposal").submit();
                    }else{
                        $("#isUploadSaveDraft").val("1");
                        $("#uploadFile").submit();
                    }
                    
                });

                $("#btnSubmit").click(function (){
                    //                    alert("Manual Entry :"+isManualEntryFormSubmit);
                    var validateCall=validateCreateProposalDtl();
                    if(validateCall[0]==false){
                        alert(validateCall[1]);
                        return false;
                    }
                    if(isManualEntryFormSubmit==1){
                        $("#isSaveDraft").val("0");
                        //                        return false;
                        $("#createManualProposal").submit();
                    }else{
                        $("#isUploadSaveDraft").val("0");
                        //                        return false;
                        $("#uploadFile").submit();
                    }
                    
                });

                $("#btnReset").click(function (){
                    //                    if(isManualEntryFormSubmit==1){
                    //                        resetManualDtl();
                    //                    }else{
                    //                        resetUploadFileDtl();
                    //                    }
                    
                    isManualEntryFormSubmit=1;

                    $("#message").html('');

                    tabSwitch_2(1,2, 'tab_', 'content_');
                    $("#rbtnManual").attr("disabled",false);
                    $("#rbtnManual").attr("checked",true);
                    $("#rbtnUpload").attr("checked",false);

                    resetArticleDtl();

                    $("#isSaveDraft").val('');
                    $("#articleGridData").val('');

                    $("#articleGrid").jqGrid("clearGridData", true);


                    $("#deptMenualSel").val('-1');
                    $("#problemMenualSel").val('-1');
                    $("#txtMenualRemarks").val('');
                    $("#txtMenualSolDesc").val('');
                    $("#promoMenualSel").val('-1');
                    $("#txtMenualPromoDesc").val('');

                    //                    $("#createManualProposal")[0].reset();

                    $("#hdnProposalId").val('');
                    $("#isUploadSaveDraft").val('');

                    $("#deptUploadSel").val('-1');
                    $("#problemUploadSel").val('-1');
                    $("#txtUploadRemarks").val('');
                    $("#txtUploadSolDesc").val('');
                    $("#promoUploadSel").val('-1');
                    $("#txtUploadPromoDesc").val('');

                    $("#tdErrorFile").hide();
                    $("#tdDownloadArticleFileForUpdate").hide();
                    $("#downloadOtherFile").hide();

                    //                    $("#uploadFile")[0].reset();

                });

                function resetArticleDtl(){
                    $("#txtArticleNo").val("");
                    $("#txtArticleDesc").val("");
                    $("#txtMCCode").val("");
                    $("#txtMCDesc").val("");
                    //                    $("#btnAddArticle").attr("disabled", true);
                    $("#txtArticleNo").focus();
                }

                function resetManualDtl(){
                    isManualEntryFormSubmit=1;

                    $("#message").html('');

                    tabSwitch_2(1,2, 'tab_', 'content_');

                    $("#rbtnManual").attr("disabled",false);
                    $("#rbtnManual").attr("checked",true);
                    $("#rbtnUpload").attr("checked",false);

                    resetArticleDtl();

                    $("#isSaveDraft").val('');
                    $("#articleGridData").val('');

                    $("#articleGrid").jqGrid("clearGridData", true);


                    $("#deptMenualSel").val('-1');
                    $("#problemMenualSel").val('-1');
                    $("#txtMenualRemarks").val('');
                    $("#txtMenualSolDesc").val('');
                    $("#promoMenualSel").val('-1');
                    $("#txtMenualPromoDesc").val('');

                    $("#createManualProposal")[0].reset();
                }
                function resetUploadFileDtl(){
                    isManualEntryFormSubmit=1;

                    $("#message").html('');

                    tabSwitch_2(1,2, 'tab_', 'content_');

                    $("#rbtnManual").attr("disabled",false);
                    $("#rbtnManual").attr("checked",true);
                    $("#rbtnUpload").attr("checked",false);

                    $("#hdnProposalId").val('');
                    $("#isUploadSaveDraft").val('');

                    $("#deptUploadSel").val('-1');
                    $("#problemUploadSel").val('-1');
                    $("#txtUploadRemarks").val('');
                    $("#txtUploadSolDesc").val('');
                    $("#promoUploadSel").val('-1');
                    $("#txtUploadPromoDesc").val('');

                    $("#tdErrorFile").hide();
                    $("#tdDownloadArticleFileForUpdate").hide();
                    $("#downloadOtherFile").hide();


                    $("#uploadFile")[0].reset();
                }

                function validateCreateProposalDtl(){
                    var fieldValidation;
                    if(isManualEntryFormSubmit==1){
                        // Manual Entry Form Validations & Submit
                        if(jQuery("#articleGrid tr").length<=1){
                            $("#txtArticleNo").focus();
                            return [false,"Please enter article code or MC code"];
                        }
                        getArticleData();
                        fieldValidation=validateFields("deptMenualSel","problemMenualSel","txtMenualRemarks","txtMenualSolDesc","promoMenualSel","txtMenualPromoDesc");

                        if(fieldValidation[0]==false){
                            return[false,fieldValidation[1]];
                        }
                        
                        //                        validateFileSize("otherFileUpload");
                        
                    }else{
                        // Upload File Form Validations & Submit
                        fieldValidation=validateFields("deptUploadSel","problemUploadSel","txtUploadRemarks","txtUploadSolDesc","promoUploadSel","txtUploadPromoDesc");

                        if(fieldValidation[0]==false){
                            return[false,fieldValidation[1]];
                        }
                    }
                    
                    
                    if(isManualEntryFormSubmit==0){


                        var isupdateFlag=$("#hdnIsUpdateFlagForFileUpload").val();
                        if(isupdateFlag=="0"){
                            //File Valdations
                            var fileid=  document.getElementById("articleFile").value;
                            if(fileid==null ||fileid==""){
                                return [false,"Please select file to upload."];
                            }
                        }
                        
                        var fileid=  document.getElementById("articleFile").value;
                        if(fileid==null ||fileid==""){
                            
                        }else{
                            var valid_extensions = /(.csv)$/i;
                            if(valid_extensions.test(fileid)){
                            } else{
                                return [false,'Invalid File. Please Upload CSV File Only.'];
                            }
                        }
                        
                    }
                    return [true,''];
                }

                function validateFileSize(component){
                    alert("inside validate file size");
                    var size = 0;
                    if(navigator.appName=="Microsoft Internet Explorer"){
                        //IE/Tools/Internet Options/Security/custom Level/Enabled Initialize And Script ActiveX Controlls not marked as safe for scripting
                        var oas=new ActiveXObject("Scripting.FileSystemObject");
                        var filePath = $("#" + component)[0].value;
                        var e=oas.getFile(filePath);
                        size=e.size;
                        alert("size : "+size);

                        //                        getSize();
                        

                    }else{
                        alert("Inside Other Browser");
                        //                        size = component.files[0].size;
                        size = $("#" + component)[0].files[0].size
                        alert("size : "+size);
                    }                     
                    
                }
                function getSize()
                {
                    var myFSO = new ActiveXObject("Scripting.FileSystemObject");
                    var filepath = document.upload.file.value;
                    var thefile = myFSO.getFile(filepath);
                    var size = thefile.size;
                    alert(size + " bytes");
                }

                function getArticleData(){
                    var articleGridIds = jQuery("#articleGrid").getDataIDs();
                    if(articleGridIds.length > 0){
                        //alert("Total Qual Grid Data = "+qualGridIds);
                        var tempIndex=0;
                        var arr=new Array();

                        for(var index=0;index<articleGridIds.length;index++){
                            //Columns Values!!
                            var rowData=jQuery('#articleGrid').getRowData(articleGridIds[index]);
                            arr[tempIndex]=rowData.artNo;
                            arr[++tempIndex]=rowData.artDesc;
                            arr[++tempIndex]=rowData.mcCode;
                            arr[++tempIndex]=rowData.mcDesc;
                            tempIndex++;
                        }
                        // alert("Qual arr : "+arr);
                        $("#articleGridData").val(arr);
                        //                        alert("Article Data : "+$("#articleGridData").val());
                    }

                }
                
                function validateFields(deptId,problemTypeId,remarksId,SoutionDescId,promotionId,PromoDescId){

                    var remarksLength=1000,solutionDesc=1000,promoDesc=1000;

                    var deptIdCheck =  checkComboSelection(deptId, "department");
                    if(deptIdCheck[0]==false){
                        return [false,deptIdCheck[1]];
                    }

                    var problemIdCheck = checkComboSelection(problemTypeId, "problem type");
                    if(problemIdCheck[0]==false){
                        return [false,problemIdCheck[1]];
                    }

                    var remarksVal=$("#"+remarksId).val();                    
                     remarksVal=  remarksVal.replace(/(\r\n|\n|\r)/gm," ");                    
                    $("#txtMenualRemarks").val(remarksVal);
                    $("#txtUploadRemarks").val(remarksVal);
                    
                    var remarksCheck=isBlank(remarksVal,"Remarks");
                    if(remarksCheck[0]==false){
                        $("#"+remarksId).focus();
                        return[false,remarksCheck[1]];
                    }

                    remarksCheck=checkLength(remarksVal, "Remarks", remarksLength);
                    if(remarksCheck[0]==false){
                        $("#"+remarksId).focus();
                        return[false,remarksCheck[1]];
                    }

                    var solutionDescVal=$("#"+SoutionDescId).val();
                    solutionDescVal=  solutionDescVal.replace(/(\r\n|\n|\r)/gm," ");
                    $("#txtMenualSolDesc").val(solutionDescVal);
                       $("#txtUploadSolDesc").val(solutionDescVal);
                    var solutionCheck=isBlank(solutionDescVal,"Solution description");
                    if(solutionCheck[0]==false){
                        $("#"+SoutionDescId).focus();
                        return[false,solutionCheck[1]];
                    }

                    solutionCheck=checkLength(solutionDescVal, "Solution description", solutionDesc);
                    if(solutionCheck[0]==false){
                        $("#"+SoutionDescId).focus();
                        return[false,solutionCheck[1]];
                    }


                    var promoDescVal=$("#"+PromoDescId).val();
                          promoDescVal=  promoDescVal.replace(/(\r\n|\n|\r)/gm," ");
                    $("#txtMenualPromoDesc").val(promoDescVal);
                     $("#txtUploadPromoDesc").val(promoDescVal);
                    if(promoDescVal!=undefined && promoDescVal!=null && promoDescVal.length>0){
                        var promoCheck=checkLength(promoDescVal, "Promotion description", promoDesc);
                        if(promoCheck[0]==false){
                            $("#"+PromoDescId).focus();
                            return[false,promoCheck[1]];
                        }
                    }
                    return [true,''];
                }



            });
        </script>

    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />

        <div id="middle_cont">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14">
                <tr><td class="errorText" align="right">(Fields marked with a * are mandatory.)</td></tr>
                <tr>
                    <td width="100%" align="center" >
                        <div id="message">
                            <s:actionmessage cssClass="successText"/>
                            <s:actionerror cssClass="errorText"/>
                        </div>
                    </td>
                </tr>                
                <tr>
                    <td><h1>Requestor Details</h1></td>
                    <td align="center" >
                        <a href ="#" class="download-sample " onclick="tb_show( '', 'proposalHelp?height=400&width=650');">
                            Help
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                            <tr>
                                <td  align="right">Name</td>
                                <td align="left"><input type="text" name="txtempName" id="txtempName"  value="<%= session.getAttribute(WebConstants.EMP_NAME)%>" disabled/></td>
                                <td align="right">Employee Code</td>
                                <td align="left"><input type="text" name="txtempCode" id="txtempCode" value="<%= session.getAttribute(WebConstants.USER_ID)%>" disabled/></td>
                            </tr>
                            <tr>
                                <td  align="right">Zone</td>
                                <td align="left"><input type="text" name="txtZone" id="txtZone" value="<%= session.getAttribute(WebConstants.ZONE_NAME)%>" disabled/></td>
                                <td  align="right">Contact Number</td>
                                <td align="left"><input type="text" name="txtcontactNo" id="txtcontactNo" value="<%= session.getAttribute(WebConstants.CONTACT_NO)%>" disabled/></td>
                            </tr>
                            <tr>
                                <td align="right">Site Code</td>
                                <td align="left"><input type="text" name="txtsiteCode" id="txtsiteCode" value="<%= session.getAttribute(WebConstants.STORE_CODE)%>" disabled/></td>
                                <td  align="right">Email ID</td>
                                <td align="left"><input type="text" name="txtemailId" id="txtemailId" value="<%= session.getAttribute(WebConstants.EMAIL_ID)%>" disabled/></td>
                            </tr>
                            <tr>
                                <td  align="right">Site Description</td>
                                <td align="left"><input type="text" name="txtsiteDesc" id="txtsiteDesc" value="<%= session.getAttribute(WebConstants.STORE_DESC)%>" disabled/></td>
                            </tr>
                        </table>
                    </td>
                </tr>                
                <tr>
                    <td ><h1>Request Details</h1></td>
                </tr>                
                <tr>
                    <td align="left">
                        <table width="60%" border="0" align="left" cellpadding="2" cellspacing="4">
                            <tr>
                                <td align="left">Capture Article</td>
                                <td align="right">Manual Entry</td>
                                <td align="left">
                                    <input type="radio" id="rbtnManual" name="captureArticle" checked="true" value="1"/>
                                </td>
                                <td align="right">Upload Article</td>
                                <td align="left">
                                    <input type="radio" id="rbtnUpload" name="captureArticle"  value="0"/>
                                </td>
                                <%--  <td align="right">
                                    <a href ="#" class="download-sample " onclick="tb_show( '', 'viewArticleMCSearch_action?height=375&width=1000');">
                                        Article Search
                                    </a>
                                </td>
                                --%>
                                <!--                                <td align="right">
                                                                    <a href ="viewMChUserDownloadhtm" class="download-sample">
                                                                        MC Download
                                                                    </a>
                                                                </td>-->
                            </tr>
                        </table>
                    </td>
                </tr>                
                <tr>
                    <td align="left">
                        <div id="tabMaster" class="tabbed_box">
                            <ul class="tabs">
                                <li><a href="#" id="tab_1" class="active" onclick="return false;">Manual Article Entry</a></li>
                                <li><a href="#" id="tab_2" onclick="return false;">Upload Article File</a></li>
                            </ul>
                            <div id="content_1" class="content" >
                                <table id="manualTable" width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                    <s:form id="createManualProposal" action="createManualProposal_action" method="POST" enctype="multipart/form-data">
                                        <input type="hidden" name="createProposalForm.isSaveDraft" id="isSaveDraft" />
                                        <input type="hidden" name="createProposalForm.manualArticleData" id="articleGridData" />
                                        <tr>
                                            <td align="right">Article Number</td>
                                            <td align="left"><input type="text" name="txtArticleNo" id="txtArticleNo" /></td>
                                            <td align="right">MC Code</td>
                                            <td align="left"><input type="text" name="txtMCCode" id="txtMCCode" /></td>

                                            <td align="left">
                                                <input type="button" name="btnAddArticle" id="btnAddArticle" value="Add" class="btn" />
                                            </td>
                                        </tr>
                                        <tr style="display: none;">
                                            <td align="right">Article Description</td>
                                            <td align="left"><input type="text" name="txtArticleDesc" id="txtArticleDesc" readonly="true" /></td>
                                            <td align="right">MC Description</td>
                                            <td align="left"><input type="text" name="txtMCDesc" id="txtMCDesc" readonly="true" /></td>
                                            <td align="left">
                                                <input type="button" name="btnValidateArticle" id="btnValidateArticle" class="btn" value="Validate" style="display: none;"/>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="5" align="center">
                                                <table id="articleGrid"></table>
                                                <div id="articlePager"></div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td colspan="5" align="center">
                                                <table width="75%" cellpadding="2" cellspacing="4" border="0">
                                                    <tr>
                                                        <td  align="right">Department:
                                                            <span class="errorText">&nbsp;*</span>
                                                        </td>
                                                        <td align="left" colspan="2">  <s:select name="createProposalForm.manualDeptId" id="deptMenualSel"  list="deptMap" headerKey="-1" headerValue="---Select---" value="---Select---" cssClass="dropdown"/>  </td>
                                                    </tr>
                                                    <tr>
                                                        <td  align="right">Problem Type:<span class="errorText">&nbsp;*</span></td>
                                                        <td align="left" colspan="2">  <s:select name="createProposalForm.manualProblemTypeId" id="problemMenualSel"  list="problemMap" headerKey="-1" headerValue="---Select---" value="---Select---" cssClass="dropdown" /> </td>
                                                    </tr>
                                                    <tr>
                                                        <td  align="right">Remarks:<span class="errorText">&nbsp;*</span></td>
                                                        <td align="left" colspan="2"><textarea cols="40" rows="4" name="createProposalForm.manualRemarks" id="txtMenualRemarks" title="Max 1000 Characters."></textarea>
                                                        </td>                                                        
                                                    </tr>
                                                    <tr>
                                                        <td align="right">Solution Description:<span class="errorText">&nbsp;*</span></td>
                                                        <td align="left" colspan="2"><textarea cols="40" rows="4" name="createProposalForm.manualSolutionDesc" id="txtMenualSolDesc" title="Max 1000 Characters."></textarea>
                                                        </td>                                                        
                                                    </tr>
                                                    <tr>
                                                        <td  align="right">Promo Type:
                                                            <!-- <span class="errorText">&nbsp;*</span> -->
                                                        </td>
                                                        <td align="left" colspan="2">  <s:select name="createProposalForm.manualPromoType" id="promoMenualSel"  list="promoMap" headerKey="-1" headerValue="---Select---" value="---Select---" cssClass="dropdown"  />  </td>
                                                    </tr>
                                                    <tr>
                                                        <td  align="right">Promo Description:
                                                            <!-- <span class="errorText">&nbsp;*</span> -->
                                                        </td>
                                                        <td align="left" colspan="2"><textarea cols="40" rows="4" name="createProposalForm.manualPromoDesc" id="txtMenualPromoDesc" title="Max 1000 Characters."></textarea></td>
                                                    </tr>
                                                    <tr>
                                                        <td align="right">Additional File upload :</td>
                                                        <td align="left">
                                                            <s:file id ="otherFileUpload" name="otherFileUpload" ></s:file>
                                                        </td>
                                                        <td align="left"> <span class="errorText">&nbsp;MAX 5 MB Only</span></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>

                                    </s:form>
                                </table>
                            </div>
                            <div id="content_2" class="content">
                                <table id="uploadTable" width="100%" border="0" align="center">
                                    <tr>
                                        <td align="right">
                                            <table   align="center" cellpadding="2" cellspacing="4">
                                                <s:form id="uploadFile"  action="uploadProposalArticle_action" method="post" enctype="multipart/form-data">
                                                    <s:hidden id="hdnProposalId" name="createProposalForm.proposalID" value="%{createProposalForm.proposalID}"/>
                                                    <s:hidden id="hdnisUploadFileError" name="createProposalForm.isUploadFileError" value="%{createProposalForm.isUploadFileError}"/>
                                                    <s:hidden id="hdnIsUpdateFlagForFileUpload" name="createProposalForm.isUpdateFlagForFileUpload" value="%{createProposalForm.isUpdateFlagForFileUpload}"/>
                                                    <input type="hidden" name="createProposalForm.isUploadSaveDraft" id="isUploadSaveDraft" />
                                                    <tr>
                                                        <td  align="right">Department :
                                                            <span class="errorText">&nbsp;*</span>
                                                        </td>
                                                        <td align="left">  <s:select name="createProposalForm.uploadDeptId" id="deptUploadSel"  list="deptMap" headerKey="-1" headerValue="---Select---" value="%{createProposalForm.uploadDeptId}" cssClass="dropdown" />  </td>

                                                    </tr>
                                                    <tr>
                                                        <td  align="right">Problem Type :<span class="errorText">&nbsp;*</span></td>
                                                        <td align="left">  <s:select name="createProposalForm.uploadProblemTypeId" id="problemUploadSel"  list="problemMap" headerKey="-1" headerValue="---Select---" value="%{createProposalForm.uploadProblemTypeId}" cssClass="dropdown" />  </td>

                                                    </tr>
                                                    <tr>
                                                        <td  align="right">Remarks :<span class="errorText">&nbsp;*</span></td>
                                                        <td align="left"><s:textarea cols="40" rows="4" name="createProposalForm.uploadRemarks" id="txtUploadRemarks" value="%{createProposalForm.uploadRemarks}"></s:textarea>
                                                        </td>

                                                    </tr>
                                                    <tr>
                                                        <td align="right">Solution Description :<span class="errorText">&nbsp;*</span></td>
                                                        <td align="left">
                                                            <s:textarea cols="40" rows="4" name="createProposalForm.uploadSolutionDesc" id="txtUploadSolDesc" value="%{createProposalForm.uploadSolutionDesc}"></s:textarea>
                                                        </td>

                                                    </tr>
                                                    <tr>
                                                        <td  align="right">Promo Type :
                                                            <!-- <span class="errorText">&nbsp;*</span> -->
                                                        </td>
                                                        <td align="left">  <s:select name="createProposalForm.uploadPromoType" id="promoUploadSel"  list="promoMap" headerKey="-1" headerValue="---Select---" value="%{createProposalForm.uploadPromoType}" cssClass="dropdown"/>  </td>

                                                    </tr>
                                                    <tr>
                                                        <td  align="right">Promo Description :
                                                            <!--<span class="errorText">&nbsp;*</span>-->
                                                        </td>
                                                        <td align="left">
                                                            <s:textarea cols="40" rows="4" name="createProposalForm.uploadPromoDesc" id="txtUploadPromoDesc" value="%{createProposalForm.uploadPromoDesc}"></s:textarea>
                                                        </td>

                                                    </tr>
                                                    <tr>                                                        
                                                        <td align="right">Upload Article File :<span class="errorText">&nbsp;*</span></td>
                                                        <td align="left">
                                                            <s:file id ="articleFile" name="articleFileUpload" ></s:file>
                                                        </td>                                                                                                               
                                                    </tr>
                                                    <tr>
                                                        <td colspan="2">
                                                            <table width="100%">
                                                                <tr>
                                                                    <td align="right" width="25%">Additional File upload :</td>
                                                                    <td align="center" width="35%">
                                                                        <s:file id ="otherFile" name="otherFileUpload" ></s:file>
                                                                    </td>
                                                                    <td align="left"> <span class="errorText">&nbsp;MAX 5 MB Only</span></td>
                                                                </tr>
                                                            </table>
                                                        </td>


                                                    </tr>
                                                    <tr>
                                                        <td align="right">Download :</td>
                                                        <td align="left">
                                                            <table width="100%">
                                                                <tr>
                                                                    <td align="center">
                                                                        <a href ="downloadSampleArticleMCFile" class="download-sample " id="downloadSample">
                                                                            Sample File
                                                                        </a>
                                                                    </td>
                                                                    <td style="width: 1%"></td>
                                                                    <td align="center" id="tdErrorFile">
                                                                        <s:a  href="%{createProposalForm.errorfilePath}" class="downloadError" id="downloadErrorFile" > Status File </s:a>
                                                                    </td>
                                                                    <td style="width: 1%"></td>
                                                                    <td align="center" id="tdDownloadArticleFileForUpdate">
                                                                        <s:a  href="%{createProposalForm.downloadArticleFileForUpdate}"  id="downloadArticleFileForUpdate" > Article File </s:a>
                                                                    </td>
                                                                    <td style="width: 1%"></td>
                                                                    <td align="center" id="tdDownloadOtherFile">
                                                                        <s:a  href="%{createProposalForm.downloadOtherFile}"  id="downloadOtherFile" > Additional File </s:a>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </s:form>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td align="center">
                        <table width="40%">
                            <tr>
                                <td>
                                    <input align="left" type="submit" id="btnSaveDraft" name="btnSaveDraft"  Value="Save" Class="btn" />
                                </td>
                                <td>
                                    <input align="left" type="submit" id="btnSubmit" name="btnSubmit"  Value="Submit" Class="btn" />
                                </td>
                                <td>
                                    <input align="left" type="button" id="btnReset" name="btnReset"  Value="RESET" Class="btn" />
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>        
    </body>
</html>
