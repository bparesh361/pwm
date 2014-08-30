<%-- 
    Document   : outward_task
    Created on : Jan 10, 2013, 12:18:56 PM
    Author     : ajitn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Outward Task</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <script src="js/jquery-1.6.4.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
        <link href="css/tabs.css" rel="stylesheet" type="text/css" />
        <script src="js/jquery.ui.position.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/Tabfunction.js"></script>

        <script type="text/javascript">
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });
               
                jQuery("#taskGrid").jqGrid({
                    url:"outwardTaskDasboardDtl_action",
                    datatype: 'json',
                    width: 1300,
                    height:230,
                    colNames:['TaskReq Number','Task Assigner', 'Zone','Task Assigned To','Task Type','Promo count',
                        'Remarks','Status','Assigned Date','Task Updated Date','Task Creation File','Inward Attachment'],
                    colModel:[
                        {name:'reqno',index:'reqno', width:170, align:"center"},
                        {name:'assigner',index:'assigner', width:200, align:"center"},
                        {name:'zone',index:'zone', width:200, align:"center"},
                        {name:'assignto',index:'assignto', width:170, align:"center"},
                        {name:'tasktype',index:'tasktype', width:200, align:"center"},
                        {name:'promocount',index:'promocount', width:200, align:"center"},
                        {name:'remarks',index:'remarks', width:200, align:"center"},
                        {name:'status',index:'status', width:200, align:"center"},
                        {name:'AssignedDate',index:'AssignedDate', width:200, align:"center"},
                        {name:'updateDate',index:'updateDate', width:200, align:"center"},
                        {name:'headerFile',index:'headerFile', width:200, align:"center"},
                        {name:'attachment',index:'attachment', width:200, align:"center"}
                        
                    ],
                    rowNum:30,
                    rowList:[30],
                    headertitles: true,
                    viewrecords: true,
                    pager: '#taskPager',
                    emptyrecords:'',
                    recordtext:'',
                    //multiselect: false,
                    //                    sortorder: 'desc',
                    //loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:""                    
                });               

            });
        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">


            <tr>
                <td><h1>Outward Task</h1></td>
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
                <td>
                    <img src="images/spacer.gif" width="10" height="10"/>
                </td>
            </tr>
            <tr>
                <td  align="center">
                    <table id="taskGrid"></table>
                    <div id="taskPager"></div>
                </td>
            </tr>            
        </table>
    </body>
</html>
