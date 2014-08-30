<%-- 
    Document   : org_master
    Created on : Dec 6, 2012, 4:36:16 PM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Organization</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            jQuery(document).ready(function(){

                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });
                $("#tdErrorFile").hide();
                setUploadFormForFileValidationFailure();
                function setUploadFormForFileValidationFailure(){
                    var hdnFileError=$("#isuploaderror").val();
                                
                    if(hdnFileError!=undefined && hdnFileError.length>0 && hdnFileError=="1"){
                        $("#tdErrorFile").show();
                    }
                }

                jQuery("#list4").jqGrid({
                    url:"getOrganizationDtl",
                    datatype: 'json',
                    width: 730,
                    height:260,
                    colNames:['Zone','State','Region', 'City', 'Site Code', 'Site Description','Location', 'Store Class','Format','Status'],
                    colModel:[
                        {name:'zonename',index:'zonename', width:150, align:"center"},
                        {name:'state',index:'state', width:150, align:"center"},
                        {name:'region',index:'region', width:150, align:"center"},                       
                        {name:'city',index:'city', width:150, align:"center"},
                        {name:'scode',index:'scode', width:150, align:"center"},
                        {name:'sdesc',index:'sdesc', width:150, align:"center"},
                        {name:'location',index:'location', width:150, align:"center"},
                        {name:'sclass',index:'sclass', width:150, align:"center"},
                        {name:'format',index:'format', width:150, align:"center"},
                        {name:'status',index:'status', width:150, align:"center"}
                    ],
                    rowNum:10,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#pglist4',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images"
                    
                }) .navGrid('#pglist4',
                {add:false, edit:false, del:false, search:false, refresh: false },
                {width:"auto"},// Default for Add
                {width:"auto"},// Default for edit
                {width:"auto"}// Default for Delete
            ).navButtonAdd(
                '#pglist4',
                {
                    caption:"Block / Unblock",
                    buttonicon:"ui-icon-add",
                    onClickButton: function(){
                        var storeID = $("#list4").jqGrid('getGridParam','selrow');
                        //  alert("Selected Row :"+storeID);
                        var storestatus = $("#list4").getCell(storeID, 'status');
                        //alert("Selected status :"+storestatus);                   
                        if(storeID!=null){
                            // alert("hello");
                            $("#msg").html("");
                              $("#tdErrorFile").hide();
                            $.ajax({
                                url:'blockUnblcokOrg?selIdToDelete='+storeID+"&status="+storestatus,
                                type: "POST",
                                dataType:"json",
                                error :function(){
                                    alert("Can not connect to server");
                                },success:function(data){
                                    if(data!=null){
                                        if(data.flag=="SUCCESS"){
                                            $("#successmsg").html(data.msg);
                                        }else{
                                            $("#errormsg").html(data.msg);
                                        }
                                    }
                                    jQuery("#list4").jqGrid('setGridParam',{url:'getOrganizationDtl',datatype:'json',page:1}).trigger("reloadGrid");
                                }
                            });
                          
                        }else{
                            alert("Please, Select a Row.");
                            return false;
                        }
                    }
                }
            );
                $("#btnSubmit").click(function (){
                    var fileid=  document.getElementById("orgFile").value;
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
        <s:form  id="uploadFile"  action="donothing" method="post" enctype="multipart/form-data">
            <s:hidden id="isuploaderror" name="formVo.isuploaderror" value="%{formVo.isuploaderror}"/>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" >
                <tr>
                    <td>
                        <table  width="100%">
                            <tr><td height="10px"></td></tr>
                            <tr>
                                <td align="center"><h1>Organization </h1></td>
                            </tr>
                            <!--                               <tr><td height="10px"></td></tr>-->
                            <tr align="center">
                                <td >
                                    <div id="successmsg" class="successText" >
                                    </div>
                                    <div id="errormsg" class="errorText" >
                                    </div>
                                    <div id="msg" >
                                        <s:actionmessage cssClass="successText"/>
                                        <s:actionerror cssClass="errorText" />
                                    </div>
                                </td>
                            </tr>

                            <tr><td style="height:10px"></td></tr>
                            <tr>
                                <td  align="center"  >
                                    <table id="list4"></table>
                                    <div id="pglist4"></div>
                                </td>

                            </tr>
                            <tr><td style="height:10px" ></td></tr>
                            <tr>
                                <td>
                                    <table width="65%" align="center">
                                        <tr>
                                            <td align="right">Upload File:<span class="errorText">&nbsp;*</span></td>
                                            <td align="left">
                                                <s:file id ="orgFile" name="orgFileName" ></s:file>
                                            </td>
                                            <td align="left" >
                                                <s:submit  id="btnSubmit" name="btnSubmit" value="Upload" cssClass="btn" action="submitOrgMaster" ></s:submit>
                                            </td>
                                            <td align="center" >
                                                <s:submit id="btnDownlaod" name="btnDownlaod" value=" Download Template" cssClass="largebtn" action="downloadSampleOrgFile" ></s:submit>
                                            </td>
                                            <td align="center" id="tdErrorFile">
                                                <s:a  href="%{formVo.errorfilePath}" class="downloadError" id="downloadErrorFile" > Error File </s:a>
                                            </td>
                                            <td align="center" >
                                                <s:submit id="btnMaster" name="btnMaster" value=" Download Master" cssClass="largebtn" action="downloadMasterOrgFile" ></s:submit>
                                            </td>

                                        </tr>
                                        <tr><td style="height:10px" ></td></tr>                                        
                                    </table>
                                </td>

                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
        </s:form>
    </body>
</html>
