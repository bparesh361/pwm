<%-- 
    Document   : article_mc_search
    Created on : Jan 11, 2013, 2:04:34 PM
    Author     : ajitn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Article MC Search</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <script src="js/jquery-1.6.4.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>        
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>
        <script type="text/javascript" src="js/PromotionCommonUtil.js"></script>

        <script type="text/javascript">
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });
                //getArticleSearchDetail
                jQuery("#list4").jqGrid({
                    url:"",
                    datatype: 'json',
                    width: 730,
                    height:210,
                    colNames:['Article No','Article Desc','MC Code', 'MC Name','Brand', 'Price','Season Code'],
                    colModel:[
                        {name:'ano',index:'ano', width:150, align:"center"},
                        {name:'adesc',index:'adesc', width:150, align:"center"},
                        {name:'mchid',index:'mchid', width:150, align:"center"},
                        {name:'mcname',index:'mcname', width:150, align:"center"},
                        {name:'brand',index:'brand', width:150, align:"center"},
                        {name:'price',index:'price', width:150, align:"center",hidden:true},
                        {name:'scode',index:'scode', width:150, align:"center"}
                    ],
                    rowNum:30,
                   rowList:[30],
                    viewrecords: true,
                    pager: '#pglist4',
                    multiselect: false,
                    //loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images"
                    // caption:"Category Master"
                });
               
                //                $("#selSearch").change(function(){
                //                    var seltype=$("#selSearch option:selected").val();
                //                    if(seltype=="1"){
                //                        $("#mcTr").show();
                //                        $("#brandTR").hide();
                //                        $("#SCodeTr").hide();
                //                    }else if(seltype=="2"){
                //                        $("#brandTR").show();
                //                        $("#mcTr").hide();
                //                        $("#SCodeTr").hide();
                //                    }else if(seltype=="3"){
                //                        $("#SCodeTr").show();
                //                        $("#mcTr").hide();
                //                        $("#brandTR").hide();
                //                    }else{
                //                        $("#mcTr").hide();
                //                        $("#brandTR").hide();
                //                        $("#SCodeTr").hide();
                //                    }
                //
                //                });
                var  articleCode,mcCode,brand,seasoncode;
                var searchType;
                $("#btnSearch").click(function (){

                    $("#txtPageGrid").val("1");
                    mcCode=$("#txtMCCodeSearch").val();
                    seasoncode=$("#txtSeasonCode").val();
                    brand=$("#txtBrand").val();
                    // var searchType='';
                    //                    var checkmcblank = isBlank(mcCode,"MC Code ");
                    //
                    //                    if(checkmcblank[0]==false){
                    //                        $("#txtMCCodeSearch").focus();
                    //                        alert(checkmcblank[1]);
                    //                        return false;
                    //                    }
                    if(mcCode.length==0 && brand.length==0 && seasoncode.length==0){
                        alert("Please Enter Search Values.");
                        return false;
                    }else if(mcCode.length>0 && brand.length==0 && seasoncode.length==0){
                        var checklengthMc = checkLength(mcCode,"MC Code ",300);
                        searchType="MC";
                        if(checklengthMc[0]==false){
                            $("#txtMCCodeSearch").focus();
                            alert(checklengthMc[1]);
                            return false;
                        }
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?mccode="+mcCode+"&searchType="+searchType+"&hdnPageGrid="+1,datatype:'json',page:1}).trigger("reloadGrid");
                    } else if(mcCode.length==0 && brand.length>0 && seasoncode.length==0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        searchType="BRAND";
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?brand="+brand+"&searchType="+searchType+"&hdnPageGrid="+1,datatype:'json',page:1}).trigger("reloadGrid");
                    }else if(mcCode.length==0 && brand.length==0 && seasoncode.length>0 ){
                        searchType="SEASON_CODE";
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?seasoncode="+seasoncode+"&searchType="+searchType+"&hdnPageGrid="+1,datatype:'json',page:1}).trigger("reloadGrid");
                    }else if(mcCode.length>0 && brand.length>0 && seasoncode.length==0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        searchType="MC_BRAND";
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?mccode="+mcCode+"&searchType="+searchType+"&brand="+brand+"&hdnPageGrid="+1,datatype:'json',page:1}).trigger("reloadGrid");
                    }else if(mcCode.length>0 && brand.length==0 && seasoncode.length>0 ){
                        searchType="MC_SEASON_CODE";
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?mccode="+mcCode+"&searchType="+searchType+"&seasoncode="+seasoncode+"&hdnPageGrid="+1,datatype:'json',page:1}).trigger("reloadGrid");
                    }else if(mcCode.length==0 && brand.length>0 && seasoncode.length>0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                        searchType="BRAND_SEASON_CODE";
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?brand="+brand+"&searchType="+searchType+"&seasoncode="+seasoncode+"&hdnPageGrid="+1,datatype:'json',page:1}).trigger("reloadGrid");
                    }else if(mcCode.length>0 && brand.length>0 && seasoncode.length>0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                        searchType="MC_BRAND_SEASON_CODE";
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?brand="+brand+"&searchType="+searchType+"&seasoncode="+seasoncode+"&mccode="+mcCode+"&hdnPageGrid="+1,datatype:'json',page:1}).trigger("reloadGrid");

                    }
           
                });
                var pageGrid=0;
                $("#txtPageGrid").val("1");
                $("#btnright").click(function (){
                    // pageGrid=1;
                    mcCode=$("#txtMCCodeSearch").val();
                    seasoncode=$("#txtSeasonCode").val();
                    brand=$("#txtBrand").val();
                    // var searchType='';
                    //                    var checkmcblank = isBlank(mcCode,"MC Code ");
                    //
                    //                    if(checkmcblank[0]==false){
                    //                        $("#txtMCCodeSearch").focus();
                    //                        alert(checkmcblank[1]);
                    //                        return false;
                    //                    }
                    pageGrid=$("#txtPageGrid").val();
                    // alert("---- page : "+pageGrid);
                    pageGrid++;
                    //alert("---- after page : "+pageGrid);
                    //return false;
                   

                    if(mcCode.length==0 && brand.length==0 && seasoncode.length==0){
                        alert("Please Enter Search Values.");
                        return false;
                    }else if(mcCode.length>0 && brand.length==0 && seasoncode.length==0){
                        var checklengthMc = checkLength(mcCode,"MC Code ",300);
                        searchType="MC";
                        if(checklengthMc[0]==false){
                            $("#txtMCCodeSearch").focus();
                            alert(checklengthMc[1]);
                            return false;
                        }
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?mccode="+mcCode+"&searchType="+searchType+"&hdnPageGrid="+pageGrid,datatype:'json',page:pageGrid}).trigger("reloadGrid");
                    } else if(mcCode.length==0 && brand.length>0 && seasoncode.length==0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        searchType="BRAND";
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?brand="+brand+"&searchType="+searchType+"&hdnPageGrid="+pageGrid,datatype:'json',page:pageGrid}).trigger("reloadGrid");
                    }else if(mcCode.length==0 && brand.length==0 && seasoncode.length>0 ){
                        searchType="SEASON_CODE";
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?seasoncode="+seasoncode+"&searchType="+searchType+"&hdnPageGrid="+pageGrid,datatype:'json',page:pageGrid}).trigger("reloadGrid");
                    }else if(mcCode.length>0 && brand.length>0 && seasoncode.length==0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        searchType="MC_BRAND";
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?mccode="+mcCode+"&searchType="+searchType+"&brand="+brand+"&hdnPageGrid="+pageGrid,datatype:'json',page:pageGrid}).trigger("reloadGrid");
                    }else if(mcCode.length>0 && brand.length==0 && seasoncode.length>0 ){
                        searchType="MC_SEASON_CODE";
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?mccode="+mcCode+"&searchType="+searchType+"&seasoncode="+seasoncode+"&hdnPageGrid="+pageGrid,datatype:'json',page:pageGrid}).trigger("reloadGrid");
                    }else if(mcCode.length==0 && brand.length>0 && seasoncode.length>0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                        searchType="BRAND_SEASON_CODE";
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?brand="+brand+"&searchType="+searchType+"&seasoncode="+seasoncode+"&hdnPageGrid="+pageGrid,datatype:'json',page:pageGrid}).trigger("reloadGrid");
                    }else if(mcCode.length>0 && brand.length>0 && seasoncode.length>0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                        searchType="MC_BRAND_SEASON_CODE";
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?brand="+brand+"&searchType="+searchType+"&seasoncode="+seasoncode+"&mccode="+mcCode+"&hdnPageGrid="+pageGrid,datatype:'json',page:pageGrid}).trigger("reloadGrid");

                    }
                    $("#txtPageGrid").val(pageGrid);
                });
                $("#btnleft").click(function (){
                    // pageGrid=1;
                    mcCode=$("#txtMCCodeSearch").val();
                    seasoncode=$("#txtSeasonCode").val();
                    brand=$("#txtBrand").val();
                    // var searchType='';
                    //                    var checkmcblank = isBlank(mcCode,"MC Code ");
                    //
                    //                    if(checkmcblank[0]==false){
                    //                        $("#txtMCCodeSearch").focus();
                    //                        alert(checkmcblank[1]);
                    //                        return false;
                    //                    }
                    pageGrid=$("#txtPageGrid").val();
                    //alert("---- page : "+pageLeftGrid);
                    if(pageGrid!="1"){
                        pageGrid=pageGrid-1;
                    }
                    //                    if(pageLeftGrid==0){
                    //                        pageLeftGrid==1;
                    //                    }
                    
                    //return false;
                    //alert("---- after page : "+pageLeftGrid);
                    $("#txtPageGrid").val(pageGrid);

                    if(mcCode.length==0 && brand.length==0 && seasoncode.length==0){
                        alert("Please Enter Search Values.");
                        return false;
                    }else if(mcCode.length>0 && brand.length==0 && seasoncode.length==0){
                        var checklengthMc = checkLength(mcCode,"MC Code ",300);
                        searchType="MC";
                        if(checklengthMc[0]==false){
                            $("#txtMCCodeSearch").focus();
                            alert(checklengthMc[1]);
                            return false;
                        }
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?mccode="+mcCode+"&searchType="+searchType+"&hdnPageGrid="+pageGrid,datatype:'json',page:pageGrid}).trigger("reloadGrid");
                    } else if(mcCode.length==0 && brand.length>0 && seasoncode.length==0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        searchType="BRAND";
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?brand="+brand+"&searchType="+searchType+"&hdnPageGrid="+pageGrid,datatype:'json',page:pageGrid}).trigger("reloadGrid");
                    }else if(mcCode.length==0 && brand.length==0 && seasoncode.length>0 ){
                        searchType="SEASON_CODE";
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?seasoncode="+seasoncode+"&searchType="+searchType+"&hdnPageGrid="+pageGrid,datatype:'json',page:pageGrid}).trigger("reloadGrid");
                    }else if(mcCode.length>0 && brand.length>0 && seasoncode.length==0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        searchType="MC_BRAND";
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?mccode="+mcCode+"&searchType="+searchType+"&brand="+brand+"&hdnPageGrid="+pageGrid,datatype:'json',page:pageGrid}).trigger("reloadGrid");
                    }else if(mcCode.length>0 && brand.length==0 && seasoncode.length>0 ){
                        searchType="MC_SEASON_CODE";
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?mccode="+mcCode+"&searchType="+searchType+"&seasoncode="+seasoncode+"&hdnPageGrid="+pageGrid,datatype:'json',page:pageGrid}).trigger("reloadGrid");
                    }else if(mcCode.length==0 && brand.length>0 && seasoncode.length>0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                        searchType="BRAND_SEASON_CODE";
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?brand="+brand+"&searchType="+searchType+"&seasoncode="+seasoncode+"&hdnPageGrid="+pageGrid,datatype:'json',page:pageGrid}).trigger("reloadGrid");
                    }else if(mcCode.length>0 && brand.length>0 && seasoncode.length>0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                        searchType="MC_BRAND_SEASON_CODE";
                        jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?brand="+brand+"&searchType="+searchType+"&seasoncode="+seasoncode+"&mccode="+mcCode+"&hdnPageGrid="+pageGrid,datatype:'json',page:pageGrid}).trigger("reloadGrid");

                    }
                 
                });
               
                var pageno =0;
                $("#btnDownlod").click(function (){
                    pageno++;
                    
                    document.getElementById('pageNum').innerHTML ='Page ' +pageno;
                    mcCode=$("#txtMCCodeSearch").val();
                    seasoncode=$("#txtSeasonCode").val();
                    brand=$("#txtBrand").val();
                  
                    if(mcCode.length==0 && brand.length==0 && seasoncode.length==0){
                        alert("Please Enter Search Values.");
                        return false;
                    }else if(mcCode.length>0 && brand.length==0 && seasoncode.length==0){
                        var checklengthMc = checkLength(mcCode,"MC Code ",300);
                        if(checklengthMc[0]==false){
                            $("#txtMCCodeSearch").focus();
                            alert(checklengthMc[1]);
                            return false;
                        }
                        searchType="MC";
                    }else if(mcCode.length==0 && brand.length>0 && seasoncode.length==0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        searchType="BRAND";

                    }else if(mcCode.length==0 && brand.length==0 && seasoncode.length>0 ){
                        searchType="SEASON_CODE";
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }

                    }else if(mcCode.length>0 && brand.length>0 && seasoncode.length==0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        searchType="MC_BRAND";

                    }else if(mcCode.length>0 && brand.length==0 && seasoncode.length>0 ){
                        searchType="MC_SEASON_CODE";
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }

                    }else if(mcCode.length==0 && brand.length>0 && seasoncode.length>0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                        searchType="BRAND_SEASON_CODE";

                    }else if(mcCode.length>0 && brand.length>0 && seasoncode.length>0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                        var checklengthseason = checkLength(seasoncode,"Season Code ",300);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                        searchType="MC_BRAND_SEASON_CODE";
                    }
                    $("#searchType").val(searchType);
                    //                    var iframe = document.createElement("iframe");
                    //                    iframe.src = "downloadArticleSearchdtl?articleCode="+articleCode+"&mccode="+mcCode+"&brand="+brand+"&seasoncode="+seasoncode;
                    //                    iframe.style.display = "none";
                    //                    document.body.appendChild(iframe);
                    //                    alert(pageno);


                    $("#loading")
                    .ajaxStart(function(){
                        $(this).show();
                    })
                    .ajaxComplete(function(){
                        $(this).hide();
                    });
                    $.ajax({
                        url:"downloadArticleSearchdtl?brand="+brand+"&searchType="+searchType+"&seasoncode="+seasoncode+"&mccode="+mcCode+"&pageno="+pageno,
                        global: false,
                        type: "POST",
                        dataType: "json",
                        contanttype: 'text/json',
                        async:false,
                        cache:false,
                        success:
                            function(data){                            
                            if(data.rows.code=="SUCCESS"){
                                $("#tdDownlaodFile").show();                               
                                document.getElementById('downlodnum').innerHTML = data.rows.msg;                                
                                $("#downloadArticleFile").attr("href", "downloadErrorArticleMCFile?errorFilePath=" + data.rows.filePath);

                            }else{
                                //$("#downlodnum").val(data.rows.code.msg);
                                document.getElementById('downlodnum').innerHTML = data.rows.msg;
                            }
                        }
                    });
                
                });
                  
            });
        </script>
    </head>
    <body>        
        <div >
            <s:form  id="uploadFile"  action="donothing">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="left">

                    <tr>
                        <td><h1>Article MC Search</h1></td>
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
                            <table cellspacing="4" cellpadding="4" width="70%">
                                <!--                                <tr>

                                                                    <td align="right">
                                                                        Search Type
                                                                    </td>
                                                                    <td align="left">
                                                                        <select name="selSearch" id="selSearch" class="dropdown">
                                                                            <OPTION value="-1">----- Select Search By -----</OPTION>
                                                                                                                    <OPTION value="0">Article</OPTION>
                                                                            <OPTION value="1">MC</OPTION>
                                                                            <OPTION value="2">Brand</OPTION>
                                                                            <OPTION value="3">Season Code</OPTION>
                                                                        </select>
                                                                    </td>

                                                                </tr>-->
                                <tr id="mcTr">
                                    <!--                                <td align="right">Article Number</td>
                                                                    <td align="left"><input type="text" name="txtArticleNo" id="txtArticleNoSearch" /></td>-->
                                    <td align="right">MC Code</td>
                                    <td align="left"><input type="text" name="mccode" id="txtMCCodeSearch" /></td>
                                    <td></td>
                                    <td align="right">Brand</td>
                                    <td align="left"><input type="text" name="brand" id="txtBrand"  /></td>
                                    <td></td>
                                    <td align="right">Season Code</td>
                                    <td align="left"><input type="text" name="seasoncode" id="txtSeasonCode"  /></td>
                                    <td align="left"><input type="hidden" name="searchType" id="searchType"  /></td>
                                </tr>
                                <!--                                <tr id="brandTR">
                                                                    <td align="right">Brand</td>
                                                                    <td align="left"><input type="text" name="brand" id="txtBrand"  /></td>
                                                                </tr>-->
                                <!--                            <tr id=brandwiseTR"
                                                                <td align="right">Brand</td>
                                                                <td align="left"><input type="text" name="txtBrand" id="txtBrand"  /></td>
                                                            </tr>-->
                                <!--                                <tr id="SCodeTr" >
                                                                    <td align="right">Season Code</td>
                                                                    <td align="left"><input type="text" name="seasoncode" id="txtSeasonCode"  /></td>
                                                                </tr>-->
                                <tr><td></td></tr>
                                <tr id="searchTR" > 
                                    <td colspan="5" align="center">
                                        <table align="center" >
                                            <tr>
                                                <td align="center" >
                                                    <input type="button" name="btnSearch" id="btnSearch" value="Search" class="btn"/>
                                                </td>
                                                <td style="display: none">
                                                    <input type="button" name="btnDownlod" id="btnDownlod" value="Download" class="btn"/>
                                                </td>
                                                <td><img id='loading' src='images/loading_image_1.gif' style='display:none;'></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr style="display: none">
                                    <td align="center" colspan="5" >
                                        <table align="center">
                                            <tr>
                                                <td   align="left"><label  name="pageNum" id="pageNum" /></td>
                                                <td></td>
                                                <td align="center" id="tdDownlaodFile" style="display: none">
                                                    <a  href=""  id="downloadArticleFile" ><u> Download</u>   </a>
                                                </td>
                                                <td></td>
                                                <td  align="right"><label  name="downlodnum" id="downlodnum"  class="successText" /></td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td  align="center" width="70%" >
                            <table id="list4"></table>
                            <!--                            <div id="pglist4"></div>-->
                        </td>
                    </tr>
                    <tr>
                        <td align="center">
                            <table align="center" width="20%">
                                <tr>
                                    <td>
                                        <input type="button" name="btnleft" id="btnleft" value="<" class="btn"/>
                                    </td>
                                    <td>Page</td>
                                    <td> <input type="text" id="txtPageGrid" name="hdnPageGrid" size="3" readonly/></td>
                                    <td>
                                        <input type="button" name="btnright" id="btnright" value=">" class="btn"/>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </s:form>
        </div>
    </body>
</html>
