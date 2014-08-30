<%-- 
    Document   : userMCHdownlod
    Created on : March 04, 2013, 9:24:06 AM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User MCH Download</title>

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
                    url:"",
                    datatype: 'json',
                    width: 530,
                    height:210,
                    colNames:['MC Code','Emp Code',' Name','Contact No','Email Id','F1 Approver', 'F2 Approver','Zone','MC Status'],
                    colModel:[
                        {name:'mccode',index:'mccode', width:150, align:"center"},
                        {name:'ecode',index:'ecode', width:150, align:"center"},
                        {name:'ename',index:'ename', width:150, align:"center"},
                        {name:'enumber',index:'enumber', width:150, align:"center",hidden:true},
                        {name:'emailid',index:'emailid', width:150, align:"center"},
                        {name:'f1',index:'f1', width:150, align:"center",formatter: 'checkbox'},
                        {name:'f2',index:'f2', width:150, align:"center",formatter: 'checkbox'},
                          {name:'zone',index:'zone', width:150, align:"center"},
                           {name:'Mcstatus',index:'Mcstatus', width:150, align:"center"},
                       
                    ],
                    rowNum:30,
                    rowList:[15,30,45],
                    viewrecords: true,
                    pager: '#pglist4',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images"
                    // caption:"Category Master"
                });
                $("#btnSubmit").click(function (){
                    var mcCode= $("#txtmchCode").val();
                    if(mcCode.length==0){
                        alert("Please enter Mc Code.");
                        return false;
                    }
                    jQuery("#list4").jqGrid('setGridParam',{url:'getmchUserdata?txtmchCode='+mcCode,datatype:'json',page:1}).trigger("reloadGrid");
                    
                });
                $("#btnDownload").click(function (){
                    var mcCode= $("#txtmchCode").val();
                    if(mcCode.length==0){
                        alert("Please enter Mc Code.");
                        return false;
                    }
                    var iframe = document.createElement("iframe");
                    iframe.src = "downloaduserMchdetail?txtmchCode="+mcCode;
                    iframe.style.display = "none";
                    document.body.appendChild(iframe);

                });

                $("#btnReset").click(function (data){
                    $("#msg").html("");
                    $("#txtmchCode").val("");
                     jQuery("#list4").jqGrid("clearGridData", true);
                });
            });
        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <s:form action="donothing">
            <table width="100%" align="center" >

                <tr>
                    <td>
                        <table  width="100%">
                            <tr><td align="center"><h1>User MCH Download</h1></td></tr>
                            <tr><td style="height:5px"></td></tr>
                            <tr align="center">
                                <td >
                                    <div id="msg" >
                                        <s:actionmessage cssClass="successText"/>
                                        <s:actionerror cssClass="errorText" />
                                    </div>
                                </td>
                            </tr>
                            <tr><td style="height:5px"></td></tr>
                            <tr>
                                <td align="center">
                                    <table align="center" >
                                        <tr>
                                            <td align="right">
                                                MC Code:<span class="errorText">&nbsp;*</span>
                                            </td>
                                            <td align="left">
                                                <input type="text" id="txtmchCode" name="txtmchCode" />
                                            </td>
                                        </tr>
                                        <tr><td style="height:5px"></td></tr>
                                        <tr>
                                            <td  align="center" colspan="2"  >
                                                <table id="list4"></table>
                                                <div id="pglist4"></div>
                                            </td>
                                        </tr>
                                        <tr><td style="height:5px"></td></tr>
                                        <tr align="center">
                                            <td align="center" colspan="2">
                                                <table align="center" width="50%">
                                                    <tr>
                                                        <td align="right">
                                                            <input id="btnSubmit" name="btnSubmit" type="button" value="Search"  class="btn"/>
                                                        </td>
                                                        <td align="right">
                                                            <input id="btnDownload" name="btnDownload" type="button" value="Download"  class="btn"/>
                                                        </td>
                                                        <td align="left">
                                                            <input id="btnReset" name="btnReset" type="button" value="Clear"  class="btn"/>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr><td style="height:5px"></td></tr>
                        </table>
                    </td>
                </tr>
            </table>
        </s:form>
    </body>
</html>
