<%-- 
    Document   : Test
    Created on : Dec 27, 2012, 3:26:03 PM
    Author     : ajitn
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
        <link href="css/tabs.css" rel="stylesheet" type="text/css" />
        <link href="css/thickbox.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
<!--        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>-->
        <script type="text/javascript" src="js/Tabfunction.js"></script>
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>
        <script type="text/javascript" src="js/PromotionCommonUtil.js"></script>

        <script type="text/javascript" src="js/thickbox.js"></script>


        <script type="text/javascript">
            jQuery(document).ready(function(){
                jQuery("#articleGrid").jqGrid({
                    url:"",
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
                    rowNum:10,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#articlePager',
                    multiselect: false,
                    editurl:'clientArray',
                    loadonce:true,
                    ignoreCase:true,
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
                    caption:"Del",
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
                                {url:'clientArray',reloadAfterSubmit:true}
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
            
                var tempIndex=0;
                var articleCode,articleDesc,mcCode,mcDesc;
                $("#btnAddArticle").click(function (){
                    articleCode= $("#txtArticleNo").val();
                    articleDesc=$("#txtArticleDesc").val();
                    mcCode=$("#txtMCCode").val();
                    mcDesc=$("#txtMCDesc").val();
                                 
                    
                    $("#articleGrid").jqGrid('addRowData',tempIndex,{artNo:articleCode,artDesc:articleDesc,mcCode:mcCode,mcDesc:mcDesc});
                   
                    
                    tempIndex++;
                });

            });
        </script>

    </head>
    <body>
        <h1>Hello World!</h1>
        <s:form id="testSubmit_action" action="testSubmit_action" method="POST">
            <!--            <input type="text" name="vo.txtMCDesc" id="txtMCDesc"   />-->
            <s:textfield type="text" name="vo.txtMCDesc" id="txtMCDesc" value="%{vo.txtMCDesc}"/>
            <table id="manualTable" width="100%" border="0" align="center" cellpadding="2" cellspacing="4">


                <tr>
                    <td align="right">Article Number</td>
                    <td align="left"><input type="text" name="txtArticleNo" id="txtArticleNo" /></td>
                    <td align="right">MC Code</td>
                    <td align="left"><input type="text" name="txtMCCode" id="txtMCCode" /></td>

                </tr>
                <tr>

                    <td align="right">Article Description</td>
                    <td align="left"><input type="text" name="txtArticleDesc" id="txtArticleDesc"  /></td>
                    <td align="right">MC Description</td>
                    <td align="left"><input type="text" name="txtMCDesc" id="txtMCDesc"  /></td>
                    <td align="left">
                        <input type="button" name="btnAddArticle" id="btnAddArticle" value="Add" class="btn" />
                    </td>
                </tr>
                <tr>
                    <td colspan="5" align="center">
                        <table id="articleGrid"></table>
                        <div id="articlePager"></div>
                    </td>
                </tr>


            </table>
            <input align="left" type="submit" id="btnSubmit" name="btnSubmit"  Value="Submit" Class="button" />
        </s:form>
    </body>
</html>
