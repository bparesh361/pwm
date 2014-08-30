<%-- 
    Document   : poolrewerdticket
    Created on : Dec 26, 2012, 9:29:15 AM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Ticket Size with discount on specific product (POOL REWARD)</title>
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

                var isManualXArticleEntry=1;
                var isManualYArticleEntry=1;
                disabledControllOnPageLoad();
                setUploadFormForXFileValidationFailureSuccess();
                setUploadFormForYFileValidationFailureSuccess();
 var reqUrl="";
                var redirected=$("#isInitiatorRedirect").val();
                var sessionPromoId=$("#SessionmstPromoId").val();
                if(redirected!=undefined && redirected.length>0 && redirected=="1"){
                    reqUrl="getPromotiondtl_promoIdwise?mstPromoId="+sessionPromoId;
                }else{
                    reqUrl="getAllPromotiondtl_initiator";
                }
                //This function set the X Article Upload File View In Case Any Of the  File Validation Gets Failed
                //Also Set Upload File Disabled For X Article If File is successfully uploaded
                function setUploadFormForXFileValidationFailureSuccess(){
                    var hdnFileError=$("#hdnisUploadFileError").val();
                    //                    alert("hdnisUploadFileError : "+hdnFileError);
                    if(hdnFileError!=undefined && hdnFileError.length>0){
                        tabSwitch_2(2,2, 'tab_', 'content_');


                        $("#rbtnManualBX").attr("disabled",true);
                        $("#rbtnManualBX").attr("checked",false);
                        $("#rbtnUploadBX").attr("checked",true);

                        if(hdnFileError=="1"){
                            $("#tdErrorFile").show();
                        }else{
                            $("#tdErrorFile").hide();
                            $("#btnBXUpload").attr("disabled",true);
                        }

                        isManualXArticleEntry=0;
                    }

                }

                //This function set the Y Article Upload File View In Case Any Of the  File Validation Gets Failed
                //Also Set Upload File Disabled For Y Article If File is successfully uploaded
                function setUploadFormForYFileValidationFailureSuccess(){
                    var hdnFileError=$("#hdnisUploadYFileError").val();
                    //                    alert("hdnisUploadFileError : "+hdnFileError);
                    if(hdnFileError!=undefined && hdnFileError.length>0){
                        tabSwitch_2(2,2, 'taby_', 'contenty_');


                        $("#rbtnManualBY").attr("disabled",true);
                        $("#rbtnManualBY").attr("checked",false);
                        $("#rbtnUploadBY").attr("checked",true);

                        if(hdnFileError=="1"){
                            $("#tdYErrorFile").show();
                        }else{
                            $("#tdYErrorFile").hide();
                            $("#btnBYUpload").attr("disabled",true);
                        }

                        isManualYArticleEntry=0;

                    }

                    // Set X Article File Tab According to hdnXFileHistoryFlag
                    var hdnXFileFlag=$("#hdnXFileHistoryFlag").val();
                    //                    alert("X History : "+hdnXFileFlag);
                    if(hdnXFileFlag!=undefined && hdnFileError.length>0){
                        if(hdnXFileFlag=="1"){
                            tabSwitch_2(2,2, 'tab_', 'content_');


                            $("#rbtnManualBX").attr("disabled",true);
                            $("#rbtnManualBX").attr("checked",false);
                            $("#rbtnUploadBX").attr("checked",true);

                            $("#tdErrorFile").hide();
                            $("#btnBXUpload").attr("disabled",true);

                            isManualXArticleEntry=0;
                        }else{
                            tabSwitch_2(1,2, 'tab_', 'content_');


                            $("#rbtnUploadBX").attr("disabled",true);
                            $("#rbtnUploadBX").attr("checked",false);
                            $("#rbtnManualBX").attr("checked",true);

                            $("#btnValidateArticle").attr("disabled",true);
                            $("#btnAddArticle").attr("disabled",true);

                            isManualXArticleEntry=1;
                        }
                    }

                }

                function disabledControllOnPageLoad(){
                    $("#tdErrorFile").hide();
                    $("#tdYErrorFile").hide();
                    $("#contenty_2").hide();
                }

                // This function is used to set and select the selected request id on grid complete on page load.
                // This scenario happens when X Article file is uploaded with POST Call.
                function setMasterRequestGridRowSelectionOnGridCompeleteForXFileUpload(){
                    var hdnPromoId=$("#hdnXPromoId").val();
                    //                    var hdnPromoId=$("#mstPromoId").val();
                    if(hdnPromoId!=undefined && hdnPromoId.length>0 && hdnPromoId!=null){
                        //                        alert("promo Id : "+hdnPromoId);
                        $("#reqGrid").resetSelection();
                        $("#reqGrid").jqGrid('setSelection', hdnPromoId);
                    }

                }

                // This function is used to set and select the selected request id on grid complete on page load.
                // This scenario happens when Y Article file is uploaded with POST Call.
                function setMasterRequestGridRowSelectionOnGridCompeleteForYFileUpload(){
                    var hdnPromoId=$("#hdnYPromoId").val();
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
                        $("#hdnXPromoId").val(id);
                        $("#hdnYPromoId").val(id);
                    }
                    ,gridComplete:function(){
                        setMasterRequestGridRowSelectionOnGridCompeleteForXFileUpload();
                        setMasterRequestGridRowSelectionOnGridCompeleteForYFileUpload();
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
                    //                    ,gridComplete:function(){
                    //                        SetXArticleGridDataOnYFilePostFileInGridComplete();
                    //                    }
                }).navGrid('#XarticlePager',
                {add:false, edit:false, del:false, search:false, refresh: false},
                {width:"auto"},// Default for Add
                {width:"auto"},// Default for edit
                {width:"auto"}// Default for Delete
            ).navButtonAdd(
                '#XarticlePager',
                {
                    caption:"DELETE",
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

                SetXArticleGridDataOnYFilePostFileOnPageLoad();

                //This function is used to set the entered data in X Article grid Manually.
                // This scenario happens when Y Article file is uploaded with Post Call.
                //Call The function just below the X Article Grid Defined in Script Sothat Data Can be filled after grid compeletion.
                function SetXArticleGridDataOnYFilePostFileOnPageLoad(){
                    var hdnXArticleGridData=$("#hdnXManualGridData").val();
                    if(hdnXArticleGridData!=undefined && hdnXArticleGridData.length>0 && hdnXArticleGridData!=null){
                        var index=0;

                        var articledata=hdnXArticleGridData.split(",");
                        //                        alert(discdata);
                        for(var i=0;i<articledata.length/5;i++){
                            var xarticleCode=articledata[i * 5];
                            var xarticleDesc=articledata[(i * 5) + 1];
                            var xmcCode=articledata[(i * 5) + 2];
                            var xmcDesc=articledata[(i * 5) + 3];
                            var xqty=articledata[(i * 5) + 4];
                            if(xarticleCode!=undefined && xarticleCode.length>0){
                                $("#XarticleGrid").jqGrid('addRowData',ytempIndex,{xartNo:xarticleCode,xartDesc:xarticleDesc,xmcCode:xmcCode,xmcDesc:xmcDesc,xqty:xqty});
                                index++;
                            }

                        }
                    }
                }

                $("#rbtnManualBX").click(function(){
                    //BX_uploadTable   BX_manualTable

                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(1,2, 'tab_', 'content_');
                    }
                    isManualXArticleEntry=1;
                });
                $("#rbtnUploadBX").click(function(){
                    var checked = $(this).attr('checked', true);

                    if(checked){
                        tabSwitch_2(2,2, 'tab_', 'content_');
                    }
                    isManualXArticleEntry=0;
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

                    //Validate The Article OR MC From js/PromotionCommonUtil.js And Set The Response In textbox
                    //validateArticleORMCCode(isArticleEntered, mcCode, articleCode,"txtArticleNo","txtArticleDesc","txtMCCode","txtMCDesc");
                    //$("#btnAddArticle").attr("disabled", false);
                    //alert("is article ent  flag :"+isArticleEntered);

                    var mstPromoId=$("#mstPromoId").val();
                    //Validate The Article OR MC From js/PromotionCommonUtil.js And Set The Response In textbox
                    validateArticleORMCCode(isArticleEntered, mcCode, articleCode,"txtArticleNo","txtArticleDesc","txtMCCode","txtMCDesc",mstPromoId,"1");
                    $("#btnAddArticle").attr("disabled", false);

                });

                var tempIndex=0;
                $("#btnAddArticle").click(function (){
                    articleCode= $("#txtArticleNo").val();
                    articleDesc=$("#txtArticleDesc").val();
                    mcCode=$("#txtMCCode").val();
                    mcDesc=$("#txtMCDesc").val();
                    //                qty=$("#txtXQty").val();
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

                    //                if(qty==undefined || qty==null || qty.length==0){
                    //                    alert("Please enter qunatity");
                    //                    $("#txtXQty").focus();
                    //                    return false;
                    //                }else if(!isNumeric(qty)){
                    //                    alert("Qunatity should be numeric.");
                    //                    $("#txtXQty").focus();
                    //                    return false;
                    //                }

                    $("#XarticleGrid").jqGrid('addRowData',tempIndex,{xartNo:articleCode,xartDesc:articleDesc,xmcCode:mcCode,xmcDesc:mcDesc,xqty:qty});
                    resetArticleDtl();

                    tempIndex++;
                });


                $("#btnBXUpload").click(function (){
                    var validateFileUpload=validatePoolTicketFile("1");
                    if(validateFileUpload[0]==false){
                        alert(validateFileUpload[0]);
                        return false;
                    }
                    $("#hdnIsXFileUploaded").val("1");
                });


                jQuery("#YarticleGrid").jqGrid({
                    url:"",
                    datatype: 'local',
                    width: 850,
                    height:110,
                    colNames:['Article Number','Article Description','MC Code', 'MC Description','QTY'],
                    colModel:[
                        {name:'yartNo',index:'yartNo', width:100, align:"center"},
                        {name:'yartDesc',index:'yartDesc', width:200, align:"center"},
                        {name:'ymcCode',index:'ymcCode', width:100, align:"center"},
                        {name:'ymcDesc',index:'ymcDesc', width:200, align:"center"},
                        {name:'yqty',index:'yqty', width:150, align:"center",hidden:true},
                    ],
                    rowNum:10,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#YarticlePager',
                    editurl:'donothing',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:""
                }).navGrid('#YarticlePager',
                {add:false, edit:false, del:false, search:false, refresh: false},
                {width:"auto"},// Default for Add
                {width:"auto"},// Default for edit
                {width:"auto"}// Default for Delete
            ).navButtonAdd(
                '#YarticlePager',
                {
                    caption:"Del",
                    buttonicon:"ui-icon-add",
                    onClickButton: function(){
                        var toDelete = $("#YarticleGrid").jqGrid('getGridParam','selrow');
                        //alert("Selected Row :"+toDelete);
                        var maxRows=jQuery("#YarticleGrid tr").length;
                        if(toDelete!=null){
                            if(maxRows>1){
                                $("#YarticleGrid").jqGrid(
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

                $("#rbtnManualBY").click(function(){
                    //BX_uploadTable   BX_manualTable

                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(1,2, 'taby_', 'contenty_');
                    }
                    isManualYArticleEntry=1;
                });
                $("#rbtnUploadBY").click(function(){
                    var checked = $(this).attr('checked', true);

                    if(checked){
                        tabSwitch_2(2,2, 'taby_', 'contenty_');
                        $("#contenty_2").show();
                    }
                    isManualYArticleEntry=0;
                });

                var isYArticleEntered=1;
                var  yarticleCode,yarticleDesc,ymcCode,ymcDesc,yqty;
                $("#btnValidateYArticle").click(function (){
                    yarticleCode= $("#txtYArticleNo").val();
                    ymcCode=$("#txtYMCCode").val();

                    if( yarticleCode !=undefined && yarticleCode.length>0 ){
                        isYArticleEntered=1;
                    }else{
                        isYArticleEntered=0;
                    }

                    //Validate The Article OR MC From js/PromotionCommonUtil.js And Set The Response In textbox
                    //validateArticleORMCCode(isYArticleEntered, ymcCode, yarticleCode,"txtYArticleNo","txtYArticleDesc","txtYMCCode","txtYMCDesc");
                    //$("#btnAddYArticle").attr("disabled", false);
                    //alert("is article ent  flag :"+isArticleEntered);

                    var mstPromoId=$("#mstPromoId").val();
                    //Validate The Article OR MC From js/PromotionCommonUtil.js And Set The Response In textbox
                    validateArticleORMCCode(isYArticleEntered, ymcCode, yarticleCode,"txtYArticleNo","txtYArticleDesc","txtYMCCode","txtYMCDesc",mstPromoId,"1");
                    $("#btnAddYArticle").attr("disabled", false);

                });


                var ytempIndex=0;
                $("#btnAddYArticle").click(function (){
                    yarticleCode= $("#txtYArticleNo").val();
                    yarticleDesc=$("#txtYArticleDesc").val();
                    ymcCode=$("#txtYMCCode").val();
                    ymcDesc=$("#txtYMCDesc").val();
                    //                yqty=$("#txtYQty").val();
                    yqty="0";

                    if(jQuery("#YarticleGrid tr").length>1){
                        var tableDataIds= jQuery('#YarticleGrid').getDataIDs();
                        //alert("tab : "+tableDataIds);
                        var insertData;
                        if(isYArticleEntered==1){
                            insertData=$("#txtYArticleNo").val();
                        }else{
                            insertData=$("#txtYMCCode").val();
                        }
                        for(var index=0;index<=tableDataIds.length;index++)
                        {
                            var rowData = jQuery('#YarticleGrid').getRowData(tableDataIds[index]);

                            // alert("Grid Data : "+rowData.bn);
                            if(isYArticleEntered==1){
                                if(insertData==rowData.yartNo){
                                    alert('Selected aricle code already exist.');
                                    $("#txtYArticleNo").focus();
                                    resetYArticleDtl();
                                    return false;
                                }
                            }else{
                                if(insertData==rowData.ymcCode){
                                    alert('Selected MC code already exist.');
                                    $("#txtYMCCode").focus();
                                    resetYArticleDtl();
                                    return false;
                                }
                            }

                        }
                    }

                    //                if(yqty==undefined || yqty==null || yqty.length==0){
                    //                    alert("Please enter qunatity");
                    //                    $("#txtYQty").focus();
                    //                    return false;
                    //                }else if(!isNumeric(yqty)){
                    //                    alert("Qunatity should be numeric.");
                    //                    $("#txtYQty").focus();
                    //                    return false;
                    //                }

                    $("#YarticleGrid").jqGrid('addRowData',ytempIndex,{yartNo:yarticleCode,yartDesc:yarticleDesc,ymcCode:ymcCode,ymcDesc:ymcDesc,yqty:yqty});
                    resetYArticleDtl();

                    ytempIndex++;
                });

                $("#btnBYUpload").click(function (){
                    var validateFileUpload=validatePoolTicketFile("0");
                    if(validateFileUpload[0]==false){
                        alert(validateFileUpload[0]);
                        return false;
                    }
                    $("#hdnIsYFileUploaded").val("1");

                    //get The X Article Grid Data And Set Back On Page Load After Y Article File Validation COmpleted
                    getXArticleGridDataOnYArticleFileUpload();
                });


                $("#btnSave").click(function(){
                    var validateFields=validatePoolTicketSizeFields();
                    if(validateFields[0]==false){
                        alert(validateFields[1]);
                        return false;
                    }
                });

                function getXArticleGridDataOnYArticleFileUpload(){
                    if(isManualXArticleEntry==1){
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
                            $("#hdnXManualGridData").val(arr);
                        }
                    }
                }

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

                function getYArticleData(){
                    var articleGridIds = jQuery("#YarticleGrid").getDataIDs();
                    if(articleGridIds.length > 0){
                        //alert("Total Qual Grid Data = "+qualGridIds);
                        var tempIndex=0;
                        var arr=new Array();

                        for(var index=0;index<=articleGridIds.length;index++){
                            //Columns Values!!
                            var rowData=jQuery('#YarticleGrid').getRowData(articleGridIds[index]);
                            arr[tempIndex]=rowData.yartNo;
                            arr[++tempIndex]=rowData.yartDesc;
                            arr[++tempIndex]=rowData.ymcCode;
                            arr[++tempIndex]=rowData.ymcDesc;
                            arr[++tempIndex]=rowData.yqty;
                            tempIndex++;
                        }
                        // alert("Qual arr : "+arr);
                        $("#YarticleGridData").val(arr);
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

                function resetYArticleDtl(){
                    $("#txtYArticleNo").val("");
                    $("#txtYArticleDesc").val("");
                    $("#txtYMCCode").val("");
                    $("#txtYMCDesc").val("");
                    $("#txtYQty").val("");
                    $("#btnAddYArticle").attr("disabled", true);
                    $("#txtYArticleNo").focus();
                }

                function validatePoolTicketFile(isXFile){
                    var fileid;
                    if(isXFile=="1"){
                        fileid=  document.getElementById("articleFile").value;

                    }else{
                        fileid=  document.getElementById("YarticleFile").value;
                    }
                    if(fileid==null ||fileid==""){
                        return [false,"Please select file to upload."];
                    }
                    var valid_extensions = /(.csv)$/i;
                    if(valid_extensions.test(fileid)){
                    } else{
                        return [false,'Invalid File. Please Upload CSV File Only.'];
                    }
                    return [true,''];
                }

                function validatePoolTicketSizeFields(){

                    var mstPromoId=$("#mstPromoId").val();
                    if(mstPromoId==undefined || mstPromoId==null || mstPromoId.length==0){
                        return[false,"Please select request"];
                    }

                    var buyWorth=$("#txtbuyWortAmt").val();
                    var validateBuyWorth=isBlank(buyWorth,"Buy Worth Amt");
                    if(validateBuyWorth[0]==false){
                        $("#txtbuyWortAmt").focus();
                        return[false,validateBuyWorth[1]];
                    }else if(!isNumeric(buyWorth)){
                        $("#txtbuyWortAmt").focus();
                        return [false,"Buy Worth Amt should be numeric."];
                    }

                    $("#hdnBuyWorthAmt").val(buyWorth);

                    if(isManualXArticleEntry==1){
                        if(jQuery("#XarticleGrid tr").length<=1){
                            $("#txtArticleNo").focus();
                            return [false,"Please enter article code or MC code"];
                        }
                        getArticleData();

                        $("#isManualEntry").val("1");
                    }else{
                        var fileUpload=$("#hdnIsXFileUploaded").val();
                        if(fileUpload!=undefined && fileUpload!=null && fileUpload!="1"){
                            return[false,"Please upload x article file."];
                        }

                        $("#isManualEntry").val("0");
                    }

                    if(isManualYArticleEntry==1){
                        if(jQuery("#YarticleGrid tr").length<=1){
                            $("#txtYArticleNo").focus();
                            return [false,"Please enter article code or MC code"];
                        }
                        getYArticleData();

                        $("#isYManualEntry").val("1");
                    }else{
                        var fileUpload=$("#hdnIsYFileUploaded").val();
                        if(fileUpload!=undefined && fileUpload!=null && fileUpload!="1"){
                            return[false,"Please upload y article file."];
                        }

                        $("#isYManualEntry").val("0");
                    }

                    var discountConfigCheck = checkComboSelection("txtDisConfig", "Discount Config");
                    if(discountConfigCheck[0]==false){
                        return [false,discountConfigCheck[1]];
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

                    var discountConfigQty=$("#txtdisQty").val();
                    var validateDiscountConfigQty=isBlank(discountConfigQty,"Discount Config Qty");
                    if(validateDiscountConfigQty[0]==false){
                        $("#txtdisQty").focus();
                        return[false,validateDiscountConfigQty[1]];
                    }else if(!isNumeric(discountConfigQty)){
                        $("#txtdisQty").focus();
                        return [false,"Discount Config Qty should be numeric."];
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
                    <td height="10px"></td>
                </tr>
                <tr>
                    <td><h1>Ticket Size with discount on specific product (POOL REWARD)</h1></td>
                </tr>
                <tr>
                    <td  align="center">
                        <table id="reqGrid"></table>
                        <div id="reqPager"></div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table id="BuyXGetYTable" width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                            <tr><td height="15px"></td></tr>
                            <tr>
                                <td>
                                    <table width="70%" border="0" align="center" cellpadding="2" cellspacing="4" >
                                        <td align="right">Buy Worth Amount</td>
                                        <td align="left"><input type ="text" id="txtbuyWortAmt"/></td>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table id="buyXTable" width="100%">
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
                                                                                <input type="button" name="btnValidateArticle" id="btnValidateArticle" value="Validate"/>
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
                                                                            <td align="left"><input type="text"  id="txtXQty" /></td>
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
                                                                    <table id="BX_uploadTable"  align="center" cellpadding="2" cellspacing="4" width="50%">
                                                                        <s:form id="uploadTicketPoolXArticleFile" action="uploadTicketPoolXArticleFile_action" method="POST" enctype="multipart/form-data">
                                                                            <s:hidden id="hdnisUploadFileError" name="poolticketSizeVO.isUploadFileError" value="%{poolticketSizeVO.isUploadFileError}"/>

                                                                            <s:hidden id="hdnXPromoId" name="poolticketSizeVO.xMstPromoId" value="%{poolticketSizeVO.xMstPromoId}"/>
                                                                            <tr>
                                                                                <td width="2%"></td>
                                                                                <td align="right">Upload File</td>
                                                                                <td align="left">
                                                                                    <s:file id ="articleFile" name="articleFileUpload" ></s:file>
                                                                                </td>
                                                                                <td align="center">
                                                                                    <input  type="submit" id="btnBXUpload" name="btnBXUpload"  Value="Upload" />
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
                                                                                    <s:a  href="%{poolticketSizeVO.errorfilePath}" class="downloadError" id="downloadErrorFile" > Status File </s:a>
                                                                                </td>
                                                                            </tr>
                                                                        </s:form>
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
                                <td><b>GET</b></td>
                            </tr>

                            <tr>
                                <td>
                                    <table id="buyYTable" width="100%">
                                        <tr>
                                            <td align="left">
                                                <table width="70%" >
                                                    <tr>
                                                        <td align="left"><b>Capture Article</b></td>
                                                        <td align="right">Manual Entry</td>
                                                        <td align="left">
                                                            <input type="radio" id="rbtnManualBY" name="captureYArticle" checked="true" value="1"/>
                                                        </td>
                                                        <td align="right">Upload Article</td>
                                                        <td align="left">
                                                            <input type="radio" id="rbtnUploadBY" name="captureYArticle"  value="0"/>
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
                                                <table width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                    <tr>
                                                        <td >
                                                            <div id="BYtabMaster" class="tabbed_box">
                                                                <ul class="tabs">
                                                                    <li><a href="#" id="taby_1" class="active" onclick="return false;">Manual Article Entry</a></li>
                                                                    <li><a href="#" id="taby_2" onclick="return false;">Upload Article File</a></li>
                                                                </ul>
                                                                <div id="contenty_1" class="content" >
                                                                    <table id="BY_manualTable" width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                                        <tr>
                                                                            <td align="right">Article Number</td>
                                                                            <td align="left"><input type="text"  id="txtYArticleNo" /></td>
                                                                            <td align="right">MC Code</td>
                                                                            <td align="left"><input type="text"  id="txtYMCCode" /></td>
                                                                            <td align="left">
                                                                                <input type="button" name="btnValidateYArticle" id="btnValidateYArticle" value="Validate"/>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td align="right">Article Description</td>
                                                                            <td align="left"><input type="text" id="txtYArticleDesc" readonly="true" /></td>
                                                                            <td align="right">MC Description</td>
                                                                            <td align="left"><input type="text"  id="txtYMCDesc" readonly="true" /></td>
                                                                            <td align="left">
                                                                                <input type="button" name="btnAddYArticle" id="btnAddYArticle" value="Add" disabled/>
                                                                            </td>
                                                                        </tr>
                                                                        <tr style="display: none">
                                                                            <td align="right">Qty</td>
                                                                            <td align="left"><input type="text"  id="txtYQty" /></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td colspan="5" align="center">
                                                                                <table id="YarticleGrid"></table>
                                                                                <div id="YarticlePager"></div>
                                                                            </td>
                                                                        </tr>

                                                                    </table>
                                                                </div>
                                                                <div id="contenty_2" class="content">
                                                                    <table id="BY_uploadTable"  align="center" cellpadding="2" cellspacing="4" width="50%">
                                                                        <s:form id="uploadTicketPoolYArticleFile" action="uploadTicketPoolYArticleFile_action" method="POST" enctype="multipart/form-data">
                                                                            <s:hidden id="hdnXFileHistoryFlag" name="poolticketSizeVO.xFileHistoryFlag" value="%{poolticketSizeVO.xFileHistoryFlag}"/>
                                                                            <s:hidden id="hdnXManualGridData" name="poolticketSizeVO.xManualGridData" value="%{poolticketSizeVO.xManualGridData}"/>
                                                                            <s:hidden id="hdnisUploadYFileError" name="poolticketSizeVO.isUploadYFileError" value="%{poolticketSizeVO.isUploadYFileError}"/>
                                                                            <s:hidden id="hdnYPromoId" name="poolticketSizeVO.yMstPromoId" value="%{poolticketSizeVO.yMstPromoId}"/>
                                                                            <tr>
                                                                                <td align="right">Upload File</td>
                                                                                <td align="left">
                                                                                    <s:file id ="YarticleFile" name="YarticleFileUpload" ></s:file>
                                                                                </td>
                                                                                <td align="left">
                                                                                    <input  type="submit" id="btnBYUpload" name="btnBYUpload"  Value="Upload" />
                                                                                </td>
                                                                            </tr>
                                                                            <tr>

                                                                                <td align="right">Download</td>
                                                                                <td align="center">
                                                                                    <a href ="downloadSampleArticleMCFile" class="download-sample ">
                                                                                        Sample File
                                                                                    </a>
                                                                                </td>
                                                                                <td align="center" id="tdYErrorFile">
                                                                                    <s:a  href="%{poolticketSizeVO.errorYfilePath}" class="downloadError" id="downloadYErrorFile" > Status File </s:a>
                                                                                </td>
                                                                            </tr>
                                                                        </s:form>
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
                            <tr><td height="15px" ></td></tr>
                            <tr>
                                <td>
                                    <table width="90%">
                                        <s:form id="createPoolTicketSize" action="createPoolTicket_action" method="POST">
                                            <s:hidden name="poolticketSizeVO.isManualEntry" id="isManualEntry" />
                                            <s:hidden name="poolticketSizeVO.isYManualEntry" id="isYManualEntry" />
                                            <s:hidden name="poolticketSizeVO.mstPromoId" id="mstPromoId" value="%{poolticketSizeVO.mstPromoId}"/>
                                            <s:hidden name="poolticketSizeVO.manualArticleData" id="XarticleGridData" />
                                            <s:hidden name="poolticketSizeVO.manualYArticleData" id="YarticleGridData" />
                                            <s:hidden name="poolticketSizeVO.buyWorthAmt" id="hdnBuyWorthAmt" />
                                            <s:hidden id="hdnIsXFileUploaded" name="poolticketSizeVO.isXFileUploaded" value="%{poolticketSizeVO.isXFileUploaded}"/>
                                            <s:hidden id="hdnIsYFileUploaded" name="poolticketSizeVO.isYFileUploaded" value="%{poolticketSizeVO.isYFileUploaded}"/>
                                            <s:hidden  name="poolticketSizeVO.isInitiatorRedirect" id="isInitiatorRedirect" value="%{poolticketSizeVO.isInitiatorRedirect}" />
                                <s:hidden  name="poolticketSizeVO.SessionmstPromoId" id="SessionmstPromoId" value="%{poolticketSizeVO.SessionmstPromoId}" />
                                            <tr>
                                                <td align="center">
                                                    <table width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                        <tr>
                                                            <td>Discount Config   </td>
                                                            <td>
                                                                <s:select headerKey="-1" headerValue="---Select Discount---" list="#{'0':'Value Off','1':'Percentage Off','2':'Flat Discount'}" id="txtDisConfig" name="poolticketSizeVO.discConfig" value="%{poolticketSizeVO.discConfig}" />
                                                            </td>
                                                            <td>Value</td>
                                                            <td><s:textfield id="txtdisValue" name="poolticketSizeVO.discountConfigValue" value="%{poolticketSizeVO.discountConfigValue}" /> </td>
                                                            <td>Qty</td>
                                                            <td><s:textfield id="txtdisQty" name="poolticketSizeVO.discountConfigQty" value="%{poolticketSizeVO.discountConfigQty}" /> </td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr><td height="5px" ></td></tr>
                                            <tr>
                                                <td>
                                                    <table width="60%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                        <tr>
                                                            <td>Margin Achievement </td>
                                                            <td><s:textfield id="txtBXGYmargin" name="poolticketSizeVO.marginAchivement" value="%{poolticketSizeVO.marginAchivement}" /></td>
                                                        </tr>
                                                        <tr>
                                                            <td>Growth in Ticket size</td>
                                                            <td><s:textfield id="txtBXGYgrowth" name="poolticketSizeVO.ticketSizeGrowth" value="%{poolticketSizeVO.ticketSizeGrowth}" /></td>
                                                        </tr>
                                                        <tr>
                                                            <td>Sell thru – v/s quantity</td>
                                                            <td><s:textfield id="txtBXGYsellQty" name="poolticketSizeVO.sellQty" value="%{poolticketSizeVO.sellQty}" /></td>
                                                        </tr>
                                                        <tr>
                                                            <td>Growth in conversions</td>
                                                            <td><s:textfield id="txtBXGYgrowthConver" name="poolticketSizeVO.growthConversion" value="%{poolticketSizeVO.growthConversion}" /></td>
                                                        </tr>
                                                        <tr>
                                                            <td>Sales Growth – both in quantity and value</td>
                                                            <td><s:textfield id="txtBXGYsalegrowth" name="poolticketSizeVO.qtyValueSellGrowth" value="%{poolticketSizeVO.qtyValueSellGrowth}" /></td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="2">
                                                                <table width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                                    <tr>
                                                                        <td>Valid From</td>
                                                                        <td><s:textfield id="txtBXGYFrom" name="poolticketSizeVO.validFrom" value="%{poolticketSizeVO.validFrom}" /></td>
                                                                        <td>Valid To</td>
                                                                        <td><s:textfield id="txtBXGYTo" name="poolticketSizeVO.validTo" value="%{poolticketSizeVO.validTo}" /></td>
                                                                    </tr>
                                                                </table>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td>Remarks</td>
                                                            <td ><textarea id="txtremarks" name="poolticketSizeVO.txtRemarks"  rows="3" cols="25" style="width: 70%;height: 20%"></textarea></td>
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                            <tr>
                                                <td align="center">
                                                    <table width="35%">
                                                        <tr>
                                                            <td align="center">
                                                                <input align="left" type="submit" id="btnSave" name="btnSave"  Value="Save" Class="btn" />
                                                            </td>
                                                            <!--                                                            <td align="center">
                                                                                                                            <input align="left" type="button" id="btnReset" name="btnReset"  Value="RESET" Class="button" />
                                                                                                                        </td>-->
                                                        </tr>
                                                    </table>
                                                </td>
                                            </tr>
                                        </s:form>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
