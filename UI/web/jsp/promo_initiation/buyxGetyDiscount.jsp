<%-- 
    Document   : buyxGetyDiscount
    Created on : Dec 26, 2012, 12:17:09 PM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buy X and Y @ Discounted price</title>
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
        <script type="text/javascript" src="js/thickbox.js"></script>

        <script type="text/javascript">
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });

                jQuery("#reqGrid").jqGrid({
                    url:"",
                    datatype: 'local',
                    width: 850,
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
                    caption:""
                });

                var isManualEntryFormSubmit=1;
                $("#rbtnManualBX").click(function(){
                    //BX_uploadTable   BX_manualTable

                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(1,2, 'BX_tab_', 'BX_sub_content_');
                    }
                    isManualEntryFormSubmit=1;
                });
                $("#rbtnUploadBX").click(function(){
                    document.getElementById('BX_sub_content_2').style.display='';
                    //                    document.getElementById('BX_manualTable').style.display='';
                    var checked = $(this).attr('checked', true);

                    if(checked){
                        tabSwitch_2(2,2, 'BX_tab_', 'BX_sub_content_');
                    }
                    isManualEntryFormSubmit=0;
                });

                $("#rbtnManualGY").click(function(){
                    //BX_uploadTable   BX_manualTable

                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(1,2, 'GY_tab_', 'GY_sub_content_');
                    }
                    isManualEntryFormSubmit=1;
                });
                $("#rbtnUploadGY").click(function(){
                    document.getElementById('GY_sub_content_2').style.display='';

                    var checked = $(this).attr('checked', true);

                    if(checked){
                        tabSwitch_2(2,2, 'GY_tab_', 'GY_sub_content_');
                    }
                    isManualEntryFormSubmit=0;
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
                        {name:'xqty',index:'xqty', width:150, align:"center"},
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
                }).navGrid('#XarticlePager',
                {add:false, edit:false, del:false, search:false, refresh: false},
                {width:"auto"},// Default for Add
                {width:"auto"},// Default for edit
                {width:"auto"}// Default for Delete
            ).navButtonAdd(
                '#XarticlePager',
                {
                    caption:"Del",
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
                        {name:'yqty',index:'yqty', width:150, align:"center"},
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
            });
        </script>
        <script type="text/javascript">


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

            if(document.all && !document.getElementById) { //IE4 support
                document.getElementById = function(id) { return document.all[id]; }
            }
            function dss_addLoadEvent(fn) {
                if(typeof(fn)!="function")return;
                var tempFunc=window.onload;
                window.onload=function() {
                    if(typeof(tempFunc)=="function")tempFunc();
                    fn();
                }
            }
            dss_addLoadEvent(function() {

                if(!document.getElementById) return;

                document.getElementById('BX_sub_content_2').style.display = 'none';
                document.getElementById('GY_sub_content_2').style.display = 'none';


            });

        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />

        <!--            <div id="middle_cont">-->
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
                <td><h1>Buy X and Y @ Discounted price</h1></td>
            </tr>
            <tr>
                <td colspan="5" align="center">
                    <table id="reqGrid"></table>
                    <div id="reqPager"></div>
                </td>
            </tr>
            <tr>
                <td>
                    <table id="BuyXGetYTable" width="90%" border="0" align="center" cellpadding="2" cellspacing="4">
                        <tr>
                            <td align="left">
                                <table width="60%" border="0" align="left" cellpadding="2" cellspacing="4">
                                    <tr>
                                        <td align="left"><b>X Article</b></td>
                                        <td align="left">Capture Article</td>
                                        <td align="right">Manual Entry</td>
                                        <td align="left">
                                            <input type="radio" id="rbtnManualBX" name="captureArticle" checked="true" value="1"/>
                                        </td>
                                        <td align="right">Upload Article</td>
                                        <td align="left">
                                            <input type="radio" id="rbtnUploadBX" name="captureArticle"  value="0"/>
                                        </td>

                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr><td height="5px"></td></tr>
                        <tr>
                            <td align="left">
                                <table width="95%" border="0" align="center" cellpadding="2" cellspacing="4">
                                    <tr>
                                        <td >
                                            <div id="BXtabMaster" class="tabbed_box">
                                                <ul class="tabs">
                                                    <li><a href="#" id="BX_tab_1" class="active" onclick="return false;">Manual Article Entry</a></li>
                                                    <li><a href="#" id="BX_tab_2" onclick="return false;">Upload Article File</a></li>
                                                </ul>
                                                <div id="BX_sub_content_1" class="content" >
                                                    <table id="BX_manualTable" width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                        <s:form id="createManualProposal" action="" method="POST">
                                                            <input type="hidden" name="createBXGYFormVO.isXManualEntry" id="isXManualEntry" />
                                                            <input type="hidden" name="createBXGYFormVO.isXSaveDraft" id="isXSaveDraft" />
                                                            <input type="hidden" name="createBXGYFormVO.manualXArticleData" id="XarticleGridData" />
                                                            <tr>
                                                                <td align="right">Article Number</td>
                                                                <td align="left"><input type="text" name="createBXGYFormVO.txtBXArticleNo" id="txtBXArticleNo" /></td>
                                                                <td align="right">Article Description</td>
                                                                <td align="left"><input type="text" name="createBXGYFormVO.txtBXArticleDesc" id="txtBXArticleDesc" /></td>
                                                                <td align="left">
                                                                    <input type="button" name="btnValidateXArticle" id="btnValidateXArticle" value="Validate" class="btn"/>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td align="right">MC Code</td>
                                                                <td align="left"><input type="text" name="createBXGYFormVO.txtBXMCCode" id="txtXMCCode" /></td>
                                                                <td align="right">MC Description</td>
                                                                <td align="left"><input type="text" name="createBXGYFormVO.txtBXMCDesc" id="txtXMCDesc" /></td>
                                                                <td align="left">
                                                                    <input type="button" name="btnAddXArticle" id="btnAddXArticle" value="Add" class="btn"/>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td align="right">Qty</td>
                                                                <td align="left"><input type="text" name="createBXGYFormVO.txtXQty" id="txtXQty" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="5" align="center">
                                                                    <table id="XarticleGrid"></table>
                                                                    <div id="XarticlePager"></div>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td align="center" colspan="5">
                                                                    <a href ="#" class="download-sample " onclick="tb_show( '', 'viewArticleMCSearch_action?height=150&width=520');">
                                                                        Article Search
                                                                    </a>
                                                                </td>
                                                            </tr>
                                                        </s:form>
                                                    </table>
                                                </div>
                                                <div id="BX_sub_content_2" class="content">
                                                    <table id="BX_uploadTable"  align="center" cellpadding="2" cellspacing="4">
                                                        <s:form id="uploadFile"  action="" method="post" enctype="multipart/form-data">
                                                            <tr>
                                                                <td width="2%"></td>
                                                                <td align="right">Upload File</td>
                                                                <td align="left">
                                                                    <s:file id ="BXarticleFile" name="createBXGYFormVO.BXarticleFile" ></s:file>
                                                                </td>
                                                                <td align="center">
                                                                    <input  type="submit" id="btnBXUpload" name="btnBXUpload"  Value="Upload" />
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td width="2%"></td>
                                                                <td align="right">Download</td>
                                                                <td align="center">
                                                                    <a href ="#" class="download-sample ">
                                                                        Sample File
                                                                    </a>
                                                                </td>
                                                                <td align="center">
                                                                    <a href ="#" class="download-sample ">
                                                                        Status File
                                                                    </a>
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
                        <tr>
                            <td align="left">
                                <table width="60%" border="0" align="left" cellpadding="2" cellspacing="4">
                                    <tr>
                                        <td align="left"><b>Y Article</b></td>
                                        <td align="left">Capture Article</td>
                                        <td align="right">Manual Entry</td>
                                        <td align="left">
                                            <input type="radio" id="rbtnManualGY" name="captureArticle" checked="true" value="1"/>
                                        </td>
                                        <td align="right">Upload Article</td>
                                        <td align="left">
                                            <input type="radio" id="rbtnUploadGY" name="captureArticle"  value="0"/>
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
                                            <div id="GYtabMaster" class="tabbed_box">
                                                <ul class="tabs">
                                                    <li><a href="#" id="GY_tab_1" class="active" onclick="return false;">Manual Article Entry</a></li>
                                                    <li><a href="#" id="GY_tab_2" onclick="return false;">Upload Article File</a></li>
                                                </ul>
                                                <div id="GY_sub_content_1" class="content" >
                                                    <table id="GY_manualTable" width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                        <s:form id="createManualProposal" action="" method="POST">
                                                            <input type="hidden" name="createBXGYFormVO.isYManualEntry" id="isYManualEntry" />
                                                            <input type="hidden" name="createBXGYFormVO.isYSaveDraft" id="isYSaveDraft" />
                                                            <input type="hidden" name="createBXGYFormVO.manualYArticleData" id="YarticleGridData" />

                                                            <tr>
                                                                <td align="right">Article Number</td>
                                                                <td align="left"><input type="text" name="createBXGYFormVO.txtGYArticleNo" id="txtGYArticleNo" /></td>
                                                                <td align="right">Article Description</td>
                                                                <td align="left"><input type="text" name="createBXGYFormVO.txtGYArticleDesc" id="txtGYArticleDesc" /></td>
                                                                <td align="left">
                                                                    <input type="button" name="btnValidateYArticle" id="btnValidateYArticle" value="Validate" class="btn"/>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td align="right">MC Code</td>
                                                                <td align="left"><input type="text" name="createBXGYFormVO.txtGYMCCode" id="txtGYMCCode" /></td>
                                                                <td align="right">MC Description</td>
                                                                <td align="left"><input type="text" name="createBXGYFormVO.txtGYMCDesc" id="txtGYMCDesc" /></td>
                                                                <td align="left">
                                                                    <input type="button" name="btnAddGYArticle" id="btnAddGYArticle" value="Add" class="btn"/>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td align="right">Qty</td>
                                                                <td align="left"><input type="text" name="createBXGYFormVO.txtYQty" id="txtYQty" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="5" align="center">
                                                                    <table id="YarticleGrid"></table>
                                                                    <div id="YarticlePager"></div>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td align="center" colspan="5">
                                                                    <a href ="#" class="download-sample " onclick="tb_show( '', 'viewArticleMCSearch_action?height=150&width=520');">
                                                                        Article Search
                                                                    </a>
                                                                </td>
                                                            </tr>
                                                        </s:form>
                                                    </table>
                                                </div>
                                                <div id="GY_sub_content_2" class="content">
                                                    <table id="GY_uploadTable"  align="center" cellpadding="2" cellspacing="4">
                                                        <s:form id="uploadFile"  action="" method="post" enctype="multipart/form-data">
                                                            <tr>
                                                                <td width="2%"></td>
                                                                <td align="right">Upload File</td>
                                                                <td align="left">
                                                                    <s:file id ="GYarticleFile" name="createBXGYFormVO.GYarticleFile" ></s:file>
                                                                </td>
                                                                <td align="center">
                                                                    <input  type="submit" id="btnGYUpload" name="btnGYUpload"  Value="Upload" />
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td width="2%"></td>
                                                                <td align="right">Download</td>
                                                                <td align="center">
                                                                    <a href ="#" class="download-sample ">
                                                                        Sample File
                                                                    </a>
                                                                </td>
                                                                <td align="center">
                                                                    <a href ="#" class="download-sample ">
                                                                        Status File
                                                                    </a>
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
                        <tr><td height="15px" ></td></tr>
                        <tr>
                            <td align="center">
                                <table width="70%" border="0" align="center" cellpadding="2" cellspacing="4">
                                    <tr>
                                        <td>Discount Config   </td>
                                        <td><input type="text" id="txtDisConfig" name="createBXGYFormVO.DisConfig"/> </td>
                                        <td>Value</td>
                                        <td><input type="text" id="txtdisValue" name="createBXGYFormVO.txtdisValue"/> </td>
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
                                        <td><input type="text" id="txtBXGYmargin" name="createBXGYFormVO.txtBXGYmargin"></td>
                                    </tr>
                                    <tr>
                                        <td>Growth in Ticket size</td>
                                        <td><input type="text" id="txtBXGYgrowth" name="createBXGYFormVO.txtBXGYgrowth"></td>
                                    </tr>
                                    <tr>
                                        <td>Sell thru – v/s quantity</td>
                                        <td><input type="text" id="txtBXGYsellQty" name="createBXGYFormVO.txtBXGYsellQty"></td>
                                    </tr>
                                    <tr>
                                        <td>Growth in conversions</td>
                                        <td><input type="text" id="txtBXGYgrowthConver" name="createBXGYFormVO.txtBXGYgrowthConver"></td>
                                    </tr>
                                    <tr>
                                        <td>Sales Growth – both in quantity and value</td>
                                        <td><input type="text" id="txtBXGYsalegrowth" name="createBXGYFormVO.txtBXGYsalegrowth"></td>
                                    </tr>
                                    <tr>
                                        <td colspan="2">
                                            <table width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                <tr>
                                                    <td>Valid From</td>
                                                    <td><input type="text" id="txtBXGYFrom" name="createBXGYFormVO.txtBXGYFrom"></td>
                                                    <td>Valid To</td>
                                                    <td><input type="text" id="txtBXGYTo" name="createBXGYFormVO.txtBXGYTo"></td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td align="center">
                                <table width="35%">
                                    <tr>
                                        <td align="right">
                                            <input align="left" type="submit" id="btnBXGYSaveProceed" name="btnBXGYSaveProceed"  Value="Save & Proceed" Class="btn" />
                                        </td>
                                        <td align="center">
                                            <input align="left" type="submit" id="btnBXGYSaveNext" name="btnBXGYSaveNext"  Value="Save & Next Promo" Class="btn" />
                                        </td>

                                    </tr>
                                </table>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
