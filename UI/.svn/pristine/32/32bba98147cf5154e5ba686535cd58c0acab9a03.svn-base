<%-- 
    Document   : categorymaster
    Created on : Dec 6, 2012, 12:10:19 PM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>

        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Category</title>
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
          
                jQuery("#list4").jqGrid({
                    url:"getAllMCHdtl",
                    datatype: 'json',
                    width: 530,
                    height:280,
                    colNames:['Category','Sub Category','MC Code', 'MC Name','Status'],
                    colModel:[
                        {name:'cname',index:'cname', width:150, align:"center"},
                        {name:'sname',index:'sname', width:150, align:"center"},
                        {name:'mchid',index:'mchid', width:150, align:"center"},
                        {name:'mcname',index:'mcname', width:150, align:"center"},
                        {name:'status',index:'status', width:150, align:"center"}
                    ],
                    rowNum:15,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#pglist4',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images"
                    // caption:"Category Master"
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
                                url:'blockMCDetail?selIdToDelete='+storeID+"&status="+storestatus,
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
                                    jQuery("#list4").jqGrid('setGridParam',{url:'getAllMCHdtl',datatype:'json',page:1}).trigger("reloadGrid");
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
                    var fileid=  document.getElementById("categoryFile").value;
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
        <div id="middle_cont">
            <s:form  id="uploadFile"  action="donothing" method="post" enctype="multipart/form-data">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" >
                    <tr>
                        <td>
                            <table  width="100%">
                                <tr><td height="5px"></td></tr>
                                <tr>
                                    <td align="center"><h1>Category</h1></td>
                                </tr>
                                <tr align="center">
                                    <td align="center" >
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
                                <tr><td style="height:5px"></td></tr>
                                <tr>
                                    <td  align="center"  >
                                        <table id="list4"></table>
                                        <div id="pglist4"></div>
                                    </td>
                                </tr>

                                <tr>
                                    <td align="center">
                                        <table width="85%">
                                            <tr>
                                                <td>
                                                    Upload File :<span class="errorText">&nbsp;*</span>
                                                </td>
                                                <td align="left">
                                                    <s:file id ="categoryFile" name="categoryMCHFile" ></s:file>
                                                </td>
                                                <td align="left" >
                                                    <s:submit  id="btnSubmit" name="btnSubmit" value="Upload" cssClass="btn" action="submitCategoryMaster" ></s:submit>
                                                </td>
                                                <td  align="right">
                                                    <s:submit id="btnDownlaod" name="btnDownlaod" value=" Download Template" cssClass="largebtn" action="downloadSampleCategory" ></s:submit>
                                                </td>
                                                <td  align="right">
                                                    <s:submit id="btnMaster" name="btnMaster" value=" Download Master" cssClass="largebtn" action="downloadMasterCategory" ></s:submit>
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
