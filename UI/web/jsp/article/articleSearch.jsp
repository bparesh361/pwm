<%--
    Document   : createSubPromoFile
    Created on : Feb 15, 2013, 1:45:38 PM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Article Download</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
        <link href="css/thickbox.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>
        <script type="text/javascript" src="js/thickbox.js"></script>        
        <script type="text/javascript">
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });
                
                
                jQuery("#reqGrid").jqGrid({
                    url:"viewArticleDownloadData",
                    datatype: 'json',
                    width: 875,
                    height:150,
                    colNames:['Search Request NO','MC','Brand Name', 'Season code','Request - Date ', 'Request - Time ','Status','Download'],
                    colModel:[
                        {name:'no',index:'no', width:210, align:"center"},
                        {name:'mc',index:'mc', width:200, align:"center"},
                        {name:'bname',index:'bname', width:200, align:"center"},
                        {name:'scode',index:'scode', width:200, align:"center"},
                        {name:'rdate',index:'rdate', width:200, align:"center"},
                        {name:'rtime',index:'rtime', width:200, align:"center"},
                        {name:'status',index:'status', width:200, align:"center",hidden:true},
                        {name:'download',index:'download', width:200, align:"center"}
                    ],
                     rowNum:30,
                    rowList:[30],
                    viewrecords: true,
                    headertitles: true,
                    pager: '#reqPager',
                    multiselect: false,
                    //loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:""                    
                }).navGrid('#reqPager',
                {add:false, edit:false, del:false, search:false, refresh: false},
                {width:"auto"},// Default for Add
                {width:"auto"},// Default for edit
                {width:"auto"}// Default for Delete
            ).navButtonAdd(
                '#reqPager',
                {
                    caption:"Refresh",
                    buttonicon:"ui-icon-gear",
                    onClickButton: function(){
                        var urlStr='viewArticleDownloadData';
                        jQuery("#reqGrid").jqGrid('setGridParam',{url:urlStr,page:1}).trigger("reloadGrid");

                    }
                }
            );

                $("#btnClear").click(function (){
                    $('#msg').html('');
                    $('#uploadFile')[0].reset();
                    jQuery("#reqGrid").jqGrid('setGridParam',{url:"viewArticleDownloadData",datatype:'json',page:1}).trigger("reloadGrid");
                });
                var  articleCode,mcCode,brand,seasoncode;
                var searchType;
                $("#btnSearch").click(function (){

                    // $("#txtPageGrid").val("1");
                    mcCode=$("#txtMCCodeSearch").val();
                    seasoncode=$("#txtSeasonCode").val();
                    brand=$("#txtBrand").val();

                    if(mcCode.length==0 && brand.length==0 && seasoncode.length==0){
                        alert("Please Enter Search Values.");
                        return false;
                    }else if(mcCode.length>0 && brand.length==0 && seasoncode.length==0){
                        
                        var checklengthMc = checkLength(mcCode,"MC Code ",3000);
                        if(checklengthMc[0]==false){
                            $("#txtMCCodeSearch").focus();
                            alert(checklengthMc[1]);
                            return false;
                        }

                        searchType="MC";
                        $("#searchType").val(searchType);
                        //jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?mccode="+mcCode+"&searchType="+searchType+"&hdnPageGrid="+1,datatype:'json',page:1}).trigger("reloadGrid");
                    } else if(mcCode.length==0 && brand.length>0 && seasoncode.length==0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",3000);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                  
                        searchType="BRAND";
                        $("#searchType").val(searchType);
                        //jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?brand="+brand+"&searchType="+searchType+"&hdnPageGrid="+1,datatype:'json',page:1}).trigger("reloadGrid");
                    }else if(mcCode.length==0 && brand.length==0 && seasoncode.length>0 ){
                        searchType="SEASON_CODE";
                        var checklengthseason = checkLength(seasoncode,"Season Code ",3000);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                       
                        $("#searchType").val(searchType);
                        //jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?seasoncode="+seasoncode+"&searchType="+searchType+"&hdnPageGrid="+1,datatype:'json',page:1}).trigger("reloadGrid");
                    }else if(mcCode.length>0 && brand.length>0 && seasoncode.length==0 ){
                        var checklengthMc = checkLength(mcCode,"MC Code ",3000);
                        if(checklengthMc[0]==false){
                            $("#txtMCCodeSearch").focus();
                            alert(checklengthMc[1]);
                            return false;
                        }
                   
                        var checklengthbrand = checkLength(brand,"Brand ",3000);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                    
                        searchType="MC_BRAND";
                        $("#searchType").val(searchType);
                        //jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?mccode="+mcCode+"&searchType="+searchType+"&brand="+brand+"&hdnPageGrid="+1,datatype:'json',page:1}).trigger("reloadGrid");
                    }else if(mcCode.length>0 && brand.length==0 && seasoncode.length>0 ){
                        var checklengthMc = checkLength(mcCode,"MC Code ",3000);
                        if(checklengthMc[0]==false){
                            $("#txtMCCodeSearch").focus();
                            alert(checklengthMc[1]);
                            return false;
                        }
                  
                        searchType="MC_SEASON_CODE";
                        var checklengthseason = checkLength(seasoncode,"Season Code ",3000);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
               
                        $("#searchType").val(searchType);
                        // jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?mccode="+mcCode+"&searchType="+searchType+"&seasoncode="+seasoncode+"&hdnPageGrid="+1,datatype:'json',page:1}).trigger("reloadGrid");
                    }else if(mcCode.length==0 && brand.length>0 && seasoncode.length>0 ){
                        var checklengthbrand = checkLength(brand,"Brand ",3000);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                  
                        var checklengthseason = checkLength(seasoncode,"Season Code ",3000);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
               
                        searchType="BRAND_SEASON_CODE";
                        $("#searchType").val(searchType);
                        //jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?brand="+brand+"&searchType="+searchType+"&seasoncode="+seasoncode+"&hdnPageGrid="+1,datatype:'json',page:1}).trigger("reloadGrid");
                    }else if(mcCode.length>0 && brand.length>0 && seasoncode.length>0 ){
                        var checklengthMc = checkLength(mcCode,"MC Code ",3000);
                        if(checklengthMc[0]==false){
                            $("#txtMCCodeSearch").focus();
                            alert(checklengthMc[1]);
                            return false;
                        }
              
                        var checklengthbrand = checkLength(brand,"Brand ",300);
                        if(checklengthbrand[0]==false){
                            $("#txtBrand").focus();
                            alert(checklengthbrand[1]);
                            return false;
                        }
                 
                        var checklengthseason = checkLength(seasoncode,"Season Code ",3000);
                        if(checklengthseason[0]==false){
                            $("#txtSeasonCode").focus();
                            alert(checklengthseason[1]);
                            return false;
                        }
                     
                        searchType="MC_BRAND_SEASON_CODE";
                        //jQuery("#list4").jqGrid('setGridParam',{url:"getArticleSearchDetail?brand="+brand+"&searchType="+searchType+"&seasoncode="+seasoncode+"&mccode="+mcCode+"&hdnPageGrid="+1,datatype:'json',page:1}).trigger("reloadGrid");
                        $("#searchType").val(searchType);
                    }


                });

            

            });
        </script>
    </head>
    <body >
        <jsp:include page="/jsp/menu/menuPage.jsp" />

        <!--            <div id="middle_cont">-->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">
            <tr>
                <td>
                    <table  width="30%">
                        <tr>
                            <td align="center">
                                <h1>Article Download</h1>
                            </td>

                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td width="100%" align="center" >
                    <div id="msg">
                        <s:actionmessage cssClass="successText"/>
                        <s:actionerror cssClass="errorText"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <table cellspacing="4" cellpadding="4" width="60%">
                        <s:form  id="uploadFile"  action="submitArticleDownloadreq" method="POST">
                            <tr id="mcTr">
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
                            <tr><td></td></tr>
                            <tr id="searchTR" >
                                <td colspan="8" align="center">
                                    <table align="center" >
                                        <tr>
                                            <td align="center" >
                                                <s:submit  type="button" name="btnSearch" id="btnSearch" value="Submit" cssClass="btn"/>
                                                <!--                                                <input type="button" name="btnSearch" id="btnSearch" value="Submit" class="btn"/>-->
                                            </td>
                                            <td>

                                                <input type="button" name="btnClear" id="btnClear" value="Clear" class="btn"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </s:form>
                    </table>
                </td>
            </tr>
            <tr>
                <td  align="center">
                    <table id="reqGrid"></table>
                    <div id="reqPager"></div>
                </td>
            </tr>


        </table>
        <!--</div>-->

    </body>
</body>
</html>
