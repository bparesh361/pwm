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
        <title>Promotion Initiation</title>
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
        <script language="JavaScript" type="text/javascript">

            var version = navigator.appVersion;

            function showKeyCode(e) {
                var keycode = (window.event) ? event.keyCode : e.keyCode;

                if ((version.indexOf('MSIE') != -1)) {
                    if (keycode == 116) {
                        event.keyCode = 0;
                        event.returnValue = false;
                        return false;
                    }
                }
                else {
                    if (keycode == 116) {
                        return false;
                    }
                }
            }

        </script>
        <script type="text/javascript">
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });
                $("#btnUpload").attr("disabled",true);
                var promoId="";
                jQuery("#reqGrid").jqGrid({
                    url:"getAllPromotiondtl_initiator",
                    datatype: 'json',
                    width: 875,
                    height:150,
                    colNames:['Ticket Number','Request Date','Request Name', 'Campaign','Marketing Type','Category','Sub Category','Uploaded File','Status File'],
                    colModel:[
                        {name:'reqno',index:'reqno', width:170, align:"center"},
                        {name:'date',index:'date', width:200, align:"center"},
                        {name:'reqName',index:'reqName', width:170, align:"center"},
                        {name:'event',index:'event', width:200, align:"center"},
                        {name:'mktgtype',index:'mktgtype', width:200, align:"center"},
                        {name:'category',index:'category', width:200, align:"center"},
                        {name:'subcategory',index:'subcategory', width:200, align:"center"},
                        {name:'uploadFile',index:'uploadFile', width:200, align:"center"},
                        {name:'statusFile',index:'statusFile', width:200, align:"center"},
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
                    caption:"",
                    onSelectRow: function(id) {
                        promoId=id;
                        $("#masterReqId").val(id);
                        $("#btnUpload").attr("disabled",false);
                    },gridComplete:function(){
                      
                    }
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
                        
                        var urlStr='getAllPromotiondtl_initiator';
                        jQuery("#reqGrid").jqGrid('setGridParam',{url:urlStr,page:1}).trigger("reloadGrid");
                        $("#btnUpload").attr("disabled","disabled");

                    }
                }
            );

                var submitFlag=true;
                $("#btnUpload").click(function (){
                    if(submitFlag){
                        $("#btnUpload").attr("disabled",true);
                        submitFlag=false;
                        if(promoId.length==0){
                            submitFlag=true;
                            $("#btnUpload").attr("disabled",false);
                            alert("Please Select Employee!");
                            return false;
                        }
                        $('#txtReqId').val(promoId);
                        var fileid=  document.getElementById("promotionFile").value;
                        if(fileid==null ||fileid==""){
                            submitFlag=true;
                            $("#btnUpload").attr("disabled",false);
                            alert("Please select file to upload.");
                            return false;
                        }
                        var valid_extensions = /(.xls)$/i;
                        var valid_extensions_xlsx = /(.xlsx)$/i;
                        if(valid_extensions.test(fileid) || valid_extensions_xlsx.test(fileid) ){
                        } else{
                            submitFlag=true;
                            $("#btnUpload").attr("disabled",false);
                            alert('Invalid File! Please Upload Excel File Only');                            
                            return false;
                        }
                    }
                    $("#initiate").submit();
                    
                });
            });
        </script>
    </head>
    <body onload="JavaScript:document.body.focus();" onkeydown="return showKeyCode(event)">
        <jsp:include page="/jsp/menu/menuPage.jsp" />

        <!--            <div id="middle_cont">-->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">
            <tr>
                <td>
                    <table  width="30%">
                        <tr>
                            <td align="center">
                                <h1>Create Promotion Using File :</h1>
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
            <tr><td height="20px" ></td></tr>

            <tr>
                <td>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">
                        <tr><td align="center" >
                                <table width="50%" align="right">
                                    <tr>
                                        <td align="center" >
                                            <a href ="#" class="download-sample " onclick="tb_show( '', 'viewSubPromoUploadHelp?height=500&width=650');">
                                                Help
                                            </a>
                                        </td>
                                    </tr>
                                </table>

                            </td></tr>
                        <tr><td height="15px" ></td></tr>
                        <tr>
                            <td  align="center">
                                <table id="reqGrid"></table>
                                <div id="reqPager"></div>
                            </td>
                        </tr>
                        <tr><td height="15px" ></td></tr>
                        <s:form id="initiate" name="initiate" action="createSubPromoFile" method="POST" enctype="multipart/form-data">
                            <s:token name="token"></s:token>
                            <s:hidden id="txtReqId" name="formVo.txtReqId" value="%{formVo.txtReqId}" />
                            <s:hidden id="masterReqId" name="masterReqId"  />
                            <tr>
                                <td align="center">
                                    <table>
                                        <tr>
                                            <td>
                                                Upload File : <span class="errorText">&nbsp;*</span>
                                            </td>
                                            <td>
                                                <s:file id ="promotionFile" name="promotionFileUpload" ></s:file>
                                            </td>
                                            <td></td>
                                            <td align="center">
                                                <a href ="downloadMultiplePromoTemplateFile" class="download-sample ">
                                                    Sample File
                                                </a>
                                            </td>
                                            <td></td>
                                            <%--   <td align="center" id="tdErrorFile" style="display: none">
                                                   <s:a  href="%{formVo.errorfilePath}" class="downloadError" id="downloadErrorFile" > Status File </s:a>
                                               </td>
                                            --%>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr><td height="5px" ></td></tr>
                            <tr>
                                <td align="center">
                                    <s:submit align="center" action="createSubPromoFile" type="submit" id="btnUpload" name="btnUpload" value="Upload" cssClass="btn" />
                                </td>
                            </tr>
                        </s:form>
                    </table>
                </td>
            </tr>
            <tr><td height="15px" ></td></tr>
        </table>
        <!--</div>-->

    </body>
</body>
</html>
