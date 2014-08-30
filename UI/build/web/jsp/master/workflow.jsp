<%-- 
    Document   : workflow
    Created on : Dec 16, 2012, 3:03:21 PM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Work Flow</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            jQuery(document).ready(function(){
                $("#tdErrorFile").hide();
                setUploadFormForFileValidationFailure();
                function setUploadFormForFileValidationFailure(){
                    var hdnFileError=$("#isuploaderror").val();

                    if(hdnFileError!=undefined && hdnFileError.length>0 && hdnFileError=="1"){

                        $("#tdErrorFile").show();
                    }

                }

                jQuery("#workflow").jqGrid({
                    url:"getAllWorkflowDtl",
                    datatype: 'json',
                    width: 710,
                    height:260,
                    colNames:['Category','Sub Category', 'MC Code','MC Description', 'Location', 'locationid'],
                    colModel:[
                        {name:'category',index:'category', width:200, align:"center"},
                        {name:'subcat',index:'subcat', width:200, align:"center"},
                        {name:'mccode',index:'mccode', width:150, align:"center"},
                        {name:'mcdesc',index:'mcdesc', width:200, align:"center"},
                        {name:'location',index:'location', width:100, align:"center"},
                        {name:'lid',index:'lid', width:15, align:"center",hidden:true},
                       
                    ],
                    rowNum:10,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#pgworkflow',
                    //multiselect: true,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images"
                    //caption:"Workflow Master"
                });
                
                $("#btnSubmit").click(function (){
                    var fileid=  document.getElementById("workflowFile").value;
                    // alert(fileid);
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


            });
        </script>

    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <!-- Middle content start here -->
        <!--        <div id="middle_cont" >-->
        <div id="middle_cont">

            <s:form id="searchemp" action="donothing" method="POST" enctype="multipart/form-data">
                <s:hidden id="isuploaderror" name="formVo.isuploaderror" value="%{formVo.isuploaderror}"/>
                <table width="100%" border="0" cellspacing="0" cellpadding="0"  align="center">
                    <tr><td height="15px"></td>
                    </tr>
                    <tr>
                        <td align="center"><h1>Work Flow</h1></td>
                    </tr>
                    <tr align="center">
                        <td align="center">
                            <div id="msg" >
                                <s:actionmessage cssClass="successText"/>
                                <s:actionerror cssClass="errorText" />
                            </div>
                        </td>
                    </tr>

                    <tr><td height="15px"></td></tr>
                    <tr>
                        <td>
                            <table width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                <tr>
                                    <td  align="center"  >
                                        <table id="workflow"></table>
                                        <div id="pgworkflow"></div>
                                    </td>

                                </tr>
                                <tr><td height="15px"></td>
                                </tr>
                                <tr>
                                    <td align="center">
                                        <table width="75%">
                                            <tr>
                                                <td>
                                                    Upload File :<span class="errorText">&nbsp;*</span>
                                                </td>
                                                <td align="center">
                                                    <s:file id ="workflowFile" name="workflowMCHFile" ></s:file>
                                                </td>
                                                <td align="right" >
                                                    <s:submit  id="btnSubmit" name="btnSubmit" value="Upload" cssClass="btn" action="submitworkflow" ></s:submit>
                                                </td>
                                                <td align="right">
                                                    <s:submit id="btnDownlaod" name="btnDownlaod" value=" Download Template" cssClass="largebtn" action="downloadWorkflowMaster" ></s:submit>
                                                </td>
                                                <td align="center" id="tdErrorFile">
                                                    <s:a  href="%{formVo.errorfilePath}" class="downloadError" id="downloadErrorFile" > Error File </s:a>
                                                </td>
                                            </tr>
                                        </table>
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
