<%--
    Document   : bxGypromotion
    Created on : Dec 26, 2012, 9:29:15 AM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Buy x Get Y Set Level Discount</title>
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

        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>
        <script type="text/javascript" src="js/PromotionCommonUtil.js"></script>
        <script type="text/javascript" src="js/ui_gridcolmodels.js"></script>
        <script type="text/javascript" src="js/thickbox.js"></script>

        <script type="text/javascript">
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });

                var isxManualEntryFormSubmit="";
                var isYManualEntryFormSubmit="";
                var isZManualEntryFormSubmit="";
                var isSet4ManualEntryFormSubmit="";
                var isSet5ManualEntryFormSubmit="";


                disabledOrDisplayControllOnPageLoad();
                setUploadFormForLocalFileValidationFailure();
                setUploadFormForODSFileValidationFailure();
                setUploadFormAfterSuccessfullUpload();

//                SetXZSet4Set5ArticleGridDataOnYFilePostFileOnPageLoad();
//                SetYZSet4Set5ArticleGridDataOnYFilePostFileOnPageLoad();
//                SetXYSet4Set5ArticleGridDataOnYFilePostFileOnPageLoad();
//                SetXYZSet5ArticleGridDataOnYFilePostFileOnPageLoad();
//                SetXYZSet4ArticleGridDataOnYFilePostFileOnPageLoad();
                var promoReqId;
                var reqUrl="";
                var redirected=$("#isInitiatorRedirect").val();
                var sessionPromoId=$("#SessionmstPromoId").val();
                if(redirected!=undefined && redirected.length>0 && redirected=="1"){
                    reqUrl="getPromotiondtl_promoIdwise?mstPromoId="+sessionPromoId;
                }else{
                    reqUrl="getAllPromotiondtl_initiator";
                }
//            /    //  callLoadReqAndSubReqGrid();
//                //                set4Grid();
//                //                set5Grid();
//                //                callXGrid();
//                //                callYGrid();
//                //                callZGrid();
                //         function set4Grid(){
                jQuery("#Set4articleGrid").jqGrid({
                    url:"",
                    datatype: 'local',
                    width: 850,
                    height:110,
                    colNames:['Article Number','Article Description','MC Code', 'MC Description','QTY'],
                    colModel:[
                        {name:'set4artNo',index:'set4artNo', width:100, align:"center"},
                        {name:'set4artDesc',index:'set4artDesc', width:200, align:"center"},
                        {name:'set4mcCode',index:'set4mcCode', width:100, align:"center"},
                        {name:'set4mcDesc',index:'set4mcDesc', width:200, align:"center"},
                        {name:'set4qty',index:'set4qty', width:150, align:"center"},
                    ],
                    rowNum:10,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#Set4articlePager',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:""
                }).navGrid('#Set4articlePager',
                {add:false, edit:false, del:false, search:false, refresh: false},
                {width:"auto"},// Default for Add
                {width:"auto"},// Default for edit
                {width:"auto"}// Default for Delete
            ).navButtonAdd(
                '#Set4articlePager',
                {
                    caption:"Del",
                    buttonicon:"ui-icon-add",
                    onClickButton: function(){
                        var toDelete = $("#Set4articleGrid").jqGrid('getGridParam','selrow');
                        //alert("Selected Row :"+toDelete);
                        var maxRows=jQuery("#Set4articleGrid tr").length;
                        if(toDelete!=null){
                            if(maxRows>1){
                                $("#Set4articleGrid").jqGrid(
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
                //  }
                //    function set5Grid(){
                jQuery("#Set5articleGrid").jqGrid({
                    url:"",
                    datatype: 'local',
                    width: 850,
                    height:110,
                    colNames:['Article Number','Article Description','MC Code', 'MC Description','QTY'],
                    colModel:[
                        {name:'set5artNo',index:'set5artNo', width:100, align:"center"},
                        {name:'set5artDesc',index:'set5artDesc', width:200, align:"center"},
                        {name:'set5mcCode',index:'set5mcCode', width:100, align:"center"},
                        {name:'set5mcDesc',index:'set5mcDesc', width:200, align:"center"},
                        {name:'set5qty',index:'set5qty', width:150, align:"center"},
                    ],
                    rowNum:10,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#Set5articlePager',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:""
                }).navGrid('#Set5articlePager',
                {add:false, edit:false, del:false, search:false, refresh: false},
                {width:"auto"},// Default for Add
                {width:"auto"},// Default for edit
                {width:"auto"}// Default for Delete
            ).navButtonAdd(
                '#Set5articlePager',
                {
                    caption:"Del",
                    buttonicon:"ui-icon-add",
                    onClickButton: function(){
                        var toDelete = $("#Set5articleGrid").jqGrid('getGridParam','selrow');
                        //alert("Selected Row :"+toDelete);
                        var maxRows=jQuery("#Set5articleGrid tr").length;
                        if(toDelete!=null){
                            if(maxRows>1){
                                $("#Set5articleGrid").jqGrid(
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
                //}
                //function callXGrid(){
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
                    multiselect: false,
                    //loadonce:true,
                  

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
                // }
                //  function callYGrid(){
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
                // }
                // function callZGrid(){

                jQuery("#ZarticleGrid").jqGrid({
                    url:"",
                    datatype: 'local',
                    width: 850,
                    height:110,
                    colNames:['Article Number','Article Description','MC Code', 'MC Description','QTY'],
                    colModel:[
                        {name:'zartNo',index:'zartNo', width:100, align:"center"},
                        {name:'zartDesc',index:'zartDesc', width:200, align:"center"},
                        {name:'zmcCode',index:'zmcCode', width:100, align:"center"},
                        {name:'zmcDesc',index:'zmcDesc', width:200, align:"center"},
                        {name:'zqty',index:'zqty', width:150, align:"center"},
                    ],
                    rowNum:10,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#ZarticlePager',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:""
                }).navGrid('#ZarticlePager',
                {add:false, edit:false, del:false, search:false, refresh: false},
                {width:"auto"},// Default for Add
                {width:"auto"},// Default for edit
                {width:"auto"}// Default for Delete
            ).navButtonAdd(
                '#ZarticlePager',
                {
                    caption:"Del",
                    buttonicon:"ui-icon-add",
                    onClickButton: function(){
                        var toDelete = $("#ZarticleGrid").jqGrid('getGridParam','selrow');
                        //alert("Selected Row :"+toDelete);
                        var maxRows=jQuery("#ZarticleGrid tr").length;
                        if(toDelete!=null){
                            if(maxRows>1){
                                $("#ZarticleGrid").jqGrid(
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

                // }
                //  function callLoadReqAndSubReqGrid(){
                //                alert("Called for set Level..");

                jQuery("#reqGrid").jqGrid({
                    url:reqUrl,
                    datatype: 'json',
                    width: 850,
                    height:110,
                    colNames:reqGridColNames,
                    colModel:reqGridColmodels,
                    rowNum:5,
                    rowList:[5,7,10],
                    viewrecords: true,
                    pager: '#reqPager',
                    multiselect: false,
                    //loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:"",  onSelectRow: function(id) {
                        promoReqId=id;
                        $("#promoreqID").val(id);
                        $("#isMstPromoIdX").val(id);
                        $("#isMstPromoIdY").val(id);
                        $("#isMstPromoIdZ").val(id);
                        $("#isMstPromoIdSet4").val(id);
                        $("#isMstPromoIdSet5").val(id);
                    } ,gridComplete:function(){
                        setMasterRequestGridRowSelectionOnGridCompeleteForXFileUpload();
                        setMasterRequestGridRowSelectionOnGridCompeleteAfterYForBXGYFileUpload();
                        setMasterRequestGridRowSelectionOnGridCompeleteForZFileUpload();
                        setMasterRequestGridRowSelectionOnGridCompeleteForSet4FileUpload();
                        setMasterRequestGridRowSelectionOnGridCompeleteForSet5FileUpload();
                    }
                });


                jQuery("#discountgrid").jqGrid({
                    url:"",
                    datatype: 'local',
                    width: 650,
                    height:110,
                    colNames:['id','Set','discid','Discount Type','Value'],
                    colModel:[
                        {name:'setid',index:'setid', width:100, align:"center",hidden:true},
                        {name:'setname',index:'setname', width:100, align:"center"},
                        {name:'setdiscid',index:'setdiscid', width:200, align:"center",hidden:true},
                        {name:'setdisc',index:'setdisc', width:200, align:"center"},
                        {name:'setvalue',index:'setvalue', width:100, align:"center"},

                    ],
                    rowNum:10,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#discountgridPg',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:""
                }).navGrid('#discountgridPg',
                {add:false, edit:false, del:false, search:false, refresh: false},
                {width:"auto"},// Default for Add
                {width:"auto"},// Default for edit
                {width:"auto"}// Default for Delete
            ).navButtonAdd(
                '#discountgridPg',
                {
                    caption:"Del",
                    buttonicon:"ui-icon-add",
                    onClickButton: function(){
                        var toDelete = $("#discountgrid").jqGrid('getGridParam','selrow');
                        //alert("Selected Row :"+toDelete);
                        var maxRows=jQuery("#discountgrid tr").length;
                        if(toDelete!=null){
                            if(maxRows>1){
                                $("#discountgrid").jqGrid(
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
                //   }


                function disableAllSubRequest(){
                    document.getElementById('trZ').style.display = 'none';
                    document.getElementById('trz1').style.display = 'none';
                    document.getElementById('trset41').style.display = 'none';
                    document.getElementById('trset4').style.display = 'none';
                    document.getElementById('trset51').style.display = 'none';
                    document.getElementById('trset5').style.display = 'none';

                }
                function  enableGroup3Request(){
                    document.getElementById('trZ').style.display = '';
                    document.getElementById('trz1').style.display = '';
                    document.getElementById('trset41').style.display = '';
                    document.getElementById('trset4').style.display = '';
                    document.getElementById('trset51').style.display = '';
                    document.getElementById('trset5').style.display = '';
                }
                function enableGroup2Request(){
                    document.getElementById('trZ').style.display = '';
                    document.getElementById('trz1').style.display = '';
                    document.getElementById('trset41').style.display = '';
                    document.getElementById('trset4').style.display = '';
                }
                function enableGroup1Request(){
                    document.getElementById('trZ').style.display ='';
                    document.getElementById('trz1').style.display = '';
                }
                function disabledOrDisplayControllOnPageLoad(){


                    $("#selGroup").val("0");
                    $("#tdXErrorFile").hide();
                    $("#tdYErrorFile").hide();
                    $("#tdZErrorFile").hide();
                    $("#tdSet4ErrorFile").hide();
                    $("#tdSet5ErrorFile").hide();


                    document.getElementById('BX_sub_content_2').style.display = 'none';
                    document.getElementById('GY_sub_content_2').style.display = 'none';
                    document.getElementById('BZ_sub_content_2').style.display = 'none';
                    document.getElementById('Set5_sub_content_2').style.display = 'none';
                    document.getElementById('Set4_sub_content_2').style.display = 'none';
                    disableAllSubRequest();


                    $("#rbtnManualBX").attr('checked', true);
                    $("#rbtnManualGY").attr('checked', true);
                    $("#rbtnManualBZ").attr('checked', true);
                    $("#rbtnManualSet4").attr('checked', true);
                    $("#rbtnManualSet5").attr('checked', true);

                    var valueX =$("#rbtnManualBX").val();
                    if(valueX =="on"){
                        isxManualEntryFormSubmit=1;
                    }else{
                        isxManualEntryFormSubmit=0;
                    }
                    var valuey =$("#rbtnManualGY").val();
                    if(valuey =="on"){
                        isYManualEntryFormSubmit=1;
                    }else{
                        isYManualEntryFormSubmit=0;
                    }
                    var valueZ =$("#rbtnManualBZ").val();
                    if(valueZ =="on"){
                        isZManualEntryFormSubmit=1;
                    }else{
                        isZManualEntryFormSubmit=0;
                    }
                    var valueSet4 =$("#rbtnManualSet4").val();
                    if(valueSet4 =="on"){
                        isSet4ManualEntryFormSubmit=1;
                    }else{
                        isSet4ManualEntryFormSubmit=0;
                    }
                    var valueSet5 =$("#rbtnManualSet5").val();
                    if(valueSet5 =="on"){
                        isSet5ManualEntryFormSubmit=1;
                    }else{
                        isSet5ManualEntryFormSubmit=0;
                    }

                    $("#BXtabMaster").attr("disabled", false);
                    $("#GYtabMaster").attr("disabled", false);
                    $("#BZtabMaster").attr("disabled", false);
                    $("#Set4tabMaster").attr("disabled", false);
                    $("#Set5tabMaster").attr("disabled", false);

                    //Date

                    $(function() {
                        var dates = $( "#txtBXGYFrom, #txtBXGYTo" ).datepicker({
                            defaultDate: "+1w",
                            numberOfMonths: 1,
                            changeMonth: true,
                            changeYear: true,
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
                }

                //This function set the Upload File View In Case Any Of the Local File Validation Gets Failed
                function setUploadFormForLocalFileValidationFailure(){
                    var hdXileError=$("#isXuploaderror").val();
                    var hdYileError=$("#isYuploaderror").val();
                    var hdZileError=$("#isZuploaderror").val();
                    var hdSet4ileError=$("#isSet4uploaderror").val();
                    var hdSet5ileError=$("#isSet5uploaderror").val();
                    var xhdngroupid=$("#selGroupX").val();
                    var yhdngroupid=$("#selGroupY").val();
                    var zhdngroupid=$("#selGroupZ").val();
                    var set4hdngroupid=$("#selGroupSet4").val();
                    var set5hdngroupid=$("#selGroupSet5").val();
                    //                    alert(set4hdngroupid);
                    //                    alert(hdSet4ileError);

                    if(hdXileError!=undefined && hdXileError.length>0 && hdXileError=="0"){
                        tabSwitch_2(2,2, 'BX_tab_', 'BX_sub_content_');
                        $("#rbtnManualBX").attr("disabled",true);
                        $("#rbtnManualBX").attr("checked",false);
                        $("#rbtnUploadBX").attr("checked",true);
                        isxManualEntryFormSubmit=0;
                        $("#selGroup").val(xhdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(xhdngroupid==1){
                            enableGroup1Request();
                        }else if(xhdngroupid==2){
                            enableGroup2Request();
                        }else if(xhdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }
                    }

                    if(hdYileError!=undefined && hdYileError.length>0 && hdYileError=="0"){
                        tabSwitch_2(2,2, 'GY_tab_', 'GY_sub_content_');
                        $("#rbtnManualGY").attr("disabled",true);
                        $("#rbtnManualGY").attr("checked",false);
                        $("#rbtnUploadGY").attr("checked",true);
                        isYManualEntryFormSubmit=0;
                        $("#selGroup").val(yhdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(yhdngroupid==1){
                            enableGroup1Request();
                        }else if(yhdngroupid==2){
                            enableGroup2Request();
                        }else if(yhdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }
                    }

                    if(hdZileError!=undefined && hdZileError.length>0 && hdZileError=="0"){
                        tabSwitch_2(2,2, 'BZ_tab_', 'BZ_sub_content_');
                        $("#rbtnManualBZ").attr("disabled",true);
                        $("#rbtnManualBZ").attr("checked",false);
                        $("#rbtnUploadBZ").attr("checked",true);
                        isZManualEntryFormSubmit=0;
                        $("#selGroup").val(zhdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(zhdngroupid==1){
                            enableGroup1Request();
                        }else if(zhdngroupid==2){
                            enableGroup2Request();
                        }else if(zhdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }
                    }

                    if(hdSet4ileError!=undefined && hdSet4ileError.length>0 && hdSet4ileError=="0"){
                        tabSwitch_2(2,2, 'Set4_tab_', 'Set4_sub_content_');
                        $("#rbtnManualSet4").attr("disabled",true);
                        $("#rbtnManualSet4").attr("checked",false);
                        $("#rbtnUploadSet4").attr("checked",true);
                        isSet4ManualEntryFormSubmit=0;
                        $("#selGroup").val(set4hdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(set4hdngroupid==1){
                            enableGroup1Request();
                        }else if(set4hdngroupid==2){
                            enableGroup2Request();
                        }else if(set4hdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }
                    }
                    if(hdSet5ileError!=undefined && hdSet5ileError.length>0 && hdSet5ileError=="0"){
                        tabSwitch_2(2,2, 'Set5_tab_', 'Set5_sub_content_');
                        $("#rbtnManualSet5").attr("disabled",true);
                        $("#rbtnManualSet5").attr("checked",false);
                        $("#rbtnUploadSet5").attr("checked",true);
                        isSet5ManualEntryFormSubmit=0;
                        $("#selGroup").val(set5hdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(set5hdngroupid==1){
                            enableGroup1Request();
                        }else if(set5hdngroupid==2){
                            enableGroup2Request();
                        }else if(set5hdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }
                    }
                }

                //Only File UPload and Save And Submit buttons are enabled. Rest Of the things are disabled.
                function setUploadFormForODSFileValidationFailure(){
                    var hdXileError=$("#isXuploaderror").val();
                    var hdYileError=$("#isYuploaderror").val();
                    var hdZileError=$("#isZuploaderror").val();
                    var hdSet4ileError=$("#isSet4uploaderror").val();
                    var xhdngroupid=$("#selGroupX").val();
                    var yhdngroupid=$("#selGroupY").val();
                    var zhdngroupid=$("#selGroupZ").val();
                    var set4hdngroupid=$("#selGroupSet4").val();
                    var set5hdngroupid=$("#selGroupSet5").val();
                    var hdSet5ileError=$("#isSet5uploaderror").val();

                    if(hdXileError!=undefined && hdXileError.length>0 && hdXileError=="1"){
                        tabSwitch_2(2,2, 'BX_tab_', 'BX_sub_content_');
                        $("#rbtnManualBX").attr("disabled",true);
                        $("#rbtnManualBX").attr("checked",false);
                        $("#rbtnUploadBX").attr("checked",true);
                        isxManualEntryFormSubmit=0;
                        $("#selGroup").val(xhdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(xhdngroupid==1){
                            enableGroup1Request();
                        }else if(xhdngroupid==2){
                            enableGroup2Request();
                        }else if(xhdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }
                        $("#tdXErrorFile").show();

                    }

                    if(hdYileError!=undefined && hdYileError.length>0 && hdYileError=="1"){
                        tabSwitch_2(2,2, 'GY_tab_', 'GY_sub_content_');
                        $("#rbtnManualGY").attr("disabled",true);
                        $("#rbtnManualGY").attr("checked",false);
                        $("#rbtnUploadGY").attr("checked",true);
                        isYManualEntryFormSubmit=0;
                        $("#selGroup").val(yhdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(yhdngroupid==1){
                            enableGroup1Request();
                        }else if(yhdngroupid==2){
                            enableGroup2Request();
                        }else if(yhdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }

                        $("#tdYErrorFile").show();

                    }

                    if(hdZileError!=undefined && hdZileError.length>0 && hdZileError=="1"){
                        tabSwitch_2(2,2, 'BZ_tab_', 'BZ_sub_content_');
                        $("#rbtnManualBZ").attr("disabled",true);
                        $("#rbtnManualBZ").attr("checked",false);
                        $("#rbtnUploadBZ").attr("checked",true);
                        isZManualEntryFormSubmit=0;
                        $("#selGroup").val(zhdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(zhdngroupid==1){
                            enableGroup1Request();
                        }else if(zhdngroupid==2){
                            enableGroup2Request();
                        }else if(zhdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }

                        $("#tdZErrorFile").show();

                    }

                    if(hdSet4ileError!=undefined && hdSet4ileError.length>0 && hdSet4ileError=="1"){
                        tabSwitch_2(2,2, 'Set4_tab_', 'Set4_sub_content_');
                        $("#rbtnManualSet4").attr("disabled",true);
                        $("#rbtnManualSet4").attr("checked",false);
                        $("#rbtnUploadSet4").attr("checked",true);
                        isSet4ManualEntryFormSubmit=0;
                        $("#selGroup").val(set4hdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(set4hdngroupid==1){
                            enableGroup1Request();
                        }else if(set4hdngroupid==2){
                            enableGroup2Request();
                        }else if(set4hdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }
                        $("#tdSet4ErrorFile").show();
                    }
                    if(hdSet5ileError!=undefined && hdSet5ileError.length>0 && hdSet5ileError=="1"){
                        tabSwitch_2(2,2, 'Set5_tab_', 'Set5_sub_content_');
                        $("#rbtnManualSet5").attr("disabled",true);
                        $("#rbtnManualSet5").attr("checked",false);
                        $("#rbtnUploadSet5").attr("checked",true);
                        isSet5ManualEntryFormSubmit=0;
                        $("#selGroup").val(set5hdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(set5hdngroupid==1){
                            enableGroup1Request();
                        }else if(set5hdngroupid==2){
                            enableGroup2Request();
                        }else if(set5hdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }
                        $("#tdSet5ErrorFile").show();
                    }

                }

                function setUploadFormAfterSuccessfullUpload(){
                    var hdXileErrorform=$("#isXuploaderror").val();
                    var hdYileErrorform=$("#isYuploaderror").val();
                    var hdZileErrorform=$("#isZuploaderror").val();
                    var hdSet4ileError=$("#isSet4uploaderror").val();
                    var xhdngroupid=$("#selGroupX").val();
                    var yhdngroupid=$("#selGroupY").val();
                    var zhdngroupid=$("#selGroupZ").val();
                    var set4hdngroupid=$("#selGroupSet4").val();
                    var set5hdngroupid=$("#selGroupSet5").val();
                    var hdSet5ileError=$("#isSet5uploaderror").val();
                    if(hdXileErrorform!=undefined && hdXileErrorform.length>0 && hdXileErrorform=="2"){
                        tabSwitch_2(2,2, 'BX_tab_', 'BX_sub_content_');
                        $("#BXtabMaster").attr("disabled", true);
                        $("#rbtnManualBX").attr("disabled",true);
                        $("#rbtnManualBX").attr("checked",false);
                        $("#rbtnUploadBX").attr("checked",true);
                        isxManualEntryFormSubmit=0;
                        $("#selGroup").val(xhdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(xhdngroupid==1){
                            enableGroup1Request();
                        }else if(xhdngroupid==2){
                            enableGroup2Request();
                        }else if(xhdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }

                    }

                    if(hdYileErrorform!=undefined && hdYileErrorform.length>0 && hdYileErrorform=="2"){
                        tabSwitch_2(2,2, 'GY_tab_', 'GY_sub_content_');
                        $("#GYtabMaster").attr("disabled", true);
                        $("#rbtnManualGY").attr("disabled",true);
                        $("#rbtnManualGY").attr("checked",false);
                        $("#rbtnUploadGY").attr("checked",true);
                        isYManualEntryFormSubmit=0;
                        $("#selGroup").val(yhdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(yhdngroupid==1){
                            enableGroup1Request();
                        }else if(yhdngroupid==2){
                            enableGroup2Request();
                        }else if(yhdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }
                    }

                    if(hdZileErrorform!=undefined && hdZileErrorform.length>0 && hdZileErrorform=="2"){
                        $("#BZtabMaster").attr("disabled", true);
                        tabSwitch_2(2,2, 'BZ_tab_', 'BZ_sub_content_');
                        $("#rbtnManualBZ").attr("disabled",true);
                        $("#rbtnManualBZ").attr("checked",false);
                        $("#rbtnUploadBZ").attr("checked",true);
                        isZManualEntryFormSubmit=0;
                        $("#selGroup").val(zhdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(zhdngroupid==1){
                            enableGroup1Request();
                        }else if(zhdngroupid==2){
                            enableGroup2Request();
                        }else if(zhdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }
                    }
                    if(hdSet4ileError!=undefined && hdSet4ileError.length>0 && hdSet4ileError=="2"){
                        $("#Set4tabMaster").attr("disabled", true);
                        tabSwitch_2(2,2, 'Set4_tab_', 'Set4_sub_content_');
                        $("#rbtnManualSet4").attr("disabled",true);
                        $("#rbtnManualSet4").attr("checked",false);
                        $("#rbtnUploadSet4").attr("checked",true);
                        isSet4ManualEntryFormSubmit=0;
                        $("#selGroup").val(set4hdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(set4hdngroupid==1){
                            enableGroup1Request();
                        }else if(set4hdngroupid==2){
                            enableGroup2Request();
                        }else if(set4hdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }
                    }
                    if(hdSet5ileError!=undefined && hdSet5ileError.length>0 && hdSet5ileError=="2"){
                        tabSwitch_2(2,2, 'Set5_tab_', 'Set5_sub_content_');
                        $("#rbtnManualSet5").attr("disabled",true);
                        $("#rbtnManualSet5").attr("checked",false);
                        $("#rbtnUploadSet5").attr("checked",true);
                        $("#Set5tabMaster").attr("disabled", true);
                        isSet5ManualEntryFormSubmit=0;
                        $("#selGroup").val(set5hdngroupid);
                        $("#selGroup").attr("disabled",true);
                        if(set5hdngroupid==1){
                            enableGroup1Request();
                        }else if(set5hdngroupid==2){
                            enableGroup2Request();
                        }else if(set5hdngroupid==3){
                            enableGroup3Request();
                        }else{
                            disableAllSubRequest();
                        }
                        $("#tdSet5ErrorFile").show();
                    }
                }

                function setValueInMasterPromoID(hdnPromoId){
                    $("#isMstPromoIdX").val(hdnPromoId);
                    $("#promoreqID").val(hdnPromoId);
                    $("#isMstPromoIdY").val(hdnPromoId);
                    $("#isMstPromoIdZ").val(hdnPromoId);
                    $("#isMstPromoIdSet4").val(hdnPromoId);
                    $("#isMstPromoIdSet5").val(hdnPromoId);
                }
                function setValueInMasterGroupIDSelection(hdngroupid){
                    $("#selGroup").val(hdngroupid);
                    $("#selGroupX").val(hdngroupid);
                    $("#selGroupY").val(hdngroupid);
                    $("#selGroupZ").val(hdngroupid);
                    $("#selGroupSet4").val(hdngroupid);
                    $("#selGroupSet5").val(hdngroupid);
                }

                function setMasterRequestGridRowSelectionOnGridCompeleteForXFileUpload(){
                    var hdnPromoId=$("#isMstPromoIdX").val();
                    var hdngroupid=$("#selGroupX").val();
                    if(hdnPromoId.length>0){
                        setValueInMasterPromoID(hdnPromoId);
                    }
                    if(hdngroupid.length>0){
                        setValueInMasterGroupIDSelection(hdngroupid);
                    }


                    if(hdnPromoId!=undefined && hdnPromoId.length>0 && hdnPromoId!=null){
                        //                        alert("promo Id : "+hdnPromoId);
                        $("#reqGrid").resetSelection();
                        $("#reqGrid").jqGrid('setSelection', hdnPromoId);
                    }
                }
                function setMasterRequestGridRowSelectionOnGridCompeleteAfterYForBXGYFileUpload(){

                    var hdnPromoId=$("#isMstPromoIdY").val();
                    //alert("called y to set value : "+hdnPromoId);
                    var hdngroupid=$("#selGroupY").val();
                    if(hdnPromoId.length>0){
                        setValueInMasterPromoID(hdnPromoId);
                    }
                    if(hdngroupid.length>0){
                        setValueInMasterGroupIDSelection(hdngroupid);
                    }

                    if(hdnPromoId!=undefined && hdnPromoId.length>0 && hdnPromoId!=null){
                        $("#reqGrid").resetSelection();
                        $("#reqGrid").jqGrid('setSelection', hdnPromoId);
                    }
                }
                function setMasterRequestGridRowSelectionOnGridCompeleteForZFileUpload(){
                    var hdnPromoId=$("#isMstPromoIdZ").val();
                    var hdngroupid=$("#selGroupZ").val();
                    if(hdnPromoId.length>0){
                        setValueInMasterPromoID(hdnPromoId);
                    }
                    if(hdngroupid.length>0){
                        setValueInMasterGroupIDSelection(hdngroupid);
                    }

                    if(hdnPromoId!=undefined && hdnPromoId.length>0 && hdnPromoId!=null){
                        $("#reqGrid").resetSelection();
                        $("#reqGrid").jqGrid('setSelection', hdnPromoId);
                    }
                }
                function setMasterRequestGridRowSelectionOnGridCompeleteForSet4FileUpload(){
                    var hdnPromoId=$("#isMstPromoIdSet4").val();
                    var hdngroupid=$("#selGroupSet4").val();
                    if(hdnPromoId.length>0){
                        setValueInMasterPromoID(hdnPromoId);
                    }
                    if(hdngroupid.length>0){
                        setValueInMasterGroupIDSelection(hdngroupid);
                    }

                    if(hdnPromoId!=undefined && hdnPromoId.length>0 && hdnPromoId!=null){
                        $("#reqGrid").resetSelection();
                        $("#reqGrid").jqGrid('setSelection', hdnPromoId);
                    }
                }
                function setMasterRequestGridRowSelectionOnGridCompeleteForSet5FileUpload(){
                    var hdnPromoId=$("#isMstPromoIdSet5").val();
                    var hdngroupid=$("#selGroupSet5").val();
                    if(hdnPromoId.length>0){
                        setValueInMasterPromoID(hdnPromoId);
                    }
                    if(hdngroupid.length>0){
                        setValueInMasterGroupIDSelection(hdngroupid);
                    }

                    if(hdnPromoId!=undefined && hdnPromoId.length>0 && hdnPromoId!=null){
                        $("#reqGrid").resetSelection();
                        $("#reqGrid").jqGrid('setSelection', hdnPromoId);
                    }
                }

                var group;
                $("#selGroup").change(function(){
                    group=$("#selGroup option:selected").val();
                    if(group ==-1){
                        alert("Please select Promotion Type Group.");
                        return false;
                    }else if(group ==0){
                        $("#selGroupX").val("0");
                        $("#selGroupY").val("0");
                        $("#selGroupZ").val("0");
                        $("#selGroupSet4").val("0");
                        $("#selGroupSet5").val("0");
                        disableAllSubRequest();
                    }

                    if(group==1){
                        enableGroup1Request();
                    }else if(group==2){

                        enableGroup2Request();
                    }else if(group==3){
                        enableGroup3Request();
                    }else{
                        disableAllSubRequest();
                    }
                    $("#selGroupX").val(group);
                    $("#selGroupY").val(group);
                    $("#selGroupZ").val(group);
                    $("#selGroupSet4").val(group);
                    $("#selGroupSet5").val(group);

                });


                $("#rbtnManualBX").click(function(){
                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(1,2, 'BX_tab_', 'BX_sub_content_');
                    }
                    isxManualEntryFormSubmit=1;
                });
                $("#rbtnUploadBX").click(function(){
                    document.getElementById('BX_sub_content_2').style.display='';
                    //                    document.getElementById('BX_manualTable').style.display='';
                    var checked = $(this).attr('checked', true);

                    if(checked){
                        tabSwitch_2(2,2, 'BX_tab_', 'BX_sub_content_');
                    }
                    isxManualEntryFormSubmit=0;
                    // alert("upload x :"+isxManualEntryFormSubmit);
                });

                $("#rbtnManualGY").click(function(){
                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(1,2, 'GY_tab_', 'GY_sub_content_');
                    }
                    isYManualEntryFormSubmit=1;
                });
                $("#rbtnUploadGY").click(function(){
                    document.getElementById('GY_sub_content_2').style.display='';
                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(2,2, 'GY_tab_', 'GY_sub_content_');
                    }
                    isYManualEntryFormSubmit=0;
                });
                $("#rbtnManualBZ").click(function(){
                    //BX_uploadTable   BX_manualTable

                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(1,2, 'BZ_tab_', 'BZ_sub_content_');
                    }
                    isZManualEntryFormSubmit=1;
                });
                $("#rbtnUploadBZ").click(function(){
                    document.getElementById('BZ_sub_content_2').style.display='';
                    //                    document.getElementById('BX_manualTable').style.display='';
                    var checked = $(this).attr('checked', true);

                    if(checked){
                        tabSwitch_2(2,2, 'BZ_tab_', 'BZ_sub_content_');
                    }
                    isZManualEntryFormSubmit=0;
                });

                $("#rbtnManualSet4").click(function(){
                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(1,2, 'Set4_tab_', 'Set4_sub_content_');
                    }
                    isSet4ManualEntryFormSubmit=1;
                });
                $("#rbtnUploadSet4").click(function(){
                    document.getElementById('Set4_sub_content_2').style.display='';
                    //                    document.getElementById('BX_manualTable').style.display='';
                    var checked = $(this).attr('checked', true);

                    if(checked){
                        tabSwitch_2(2,2, 'Set4_tab_', 'Set4_sub_content_');
                    }
                    isSet4ManualEntryFormSubmit=0;
                });
                $("#rbtnManualSet5").click(function(){
                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(1,2, 'Set5_tab_', 'Set5_sub_content_');
                    }
                    isSet5ManualEntryFormSubmit=1;
                });
                $("#rbtnUploadSet5").click(function(){
                    document.getElementById('Set5_sub_content_2').style.display='';
                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(2,2, 'Set5_tab_', 'Set5_sub_content_');
                    }
                    isSet5ManualEntryFormSubmit=0;
                });

                // set 1 X article detail starts
                var isxArticleEntered=1;
                var  articleCode,articleDesc,mcCode,mcDesc, qty;
                $("#btnValidateXArticle").click(function (){
                    articleCode= $("#txtBXArticleNo").val();
                    mcCode=$("#txtXMCCode").val();

                    if( articleCode !=undefined && articleCode.length>0 ){
                        isxArticleEntered=1;
                    }else{
                        isxArticleEntered=0;
                    }
                    var mstPromoId= $("#promoreqID").val();
                    if(mstPromoId.length==0){
                        alert("Please select Promotion Request.");
                        return false;
                    }
                    //Validate The Article OR MC From js/PromotionCommonUtil.js And Set The Response In textbox
                    validateArticleORMCCode(isxArticleEntered, mcCode, articleCode,"txtBXArticleNo","txtBXArticleDesc","txtXMCCode","txtXMCDesc",mstPromoId,"1");
                    $("#btnAddXArticle").attr("disabled", false);
                });

                var tempIndexSet1=0;
                $("#btnAddXArticle").attr("disabled", true);
                $("#btnAddXArticle").click(function (){
                    articleCode= $("#txtBXArticleNo").val();
                    articleDesc=$("#txtBXArticleDesc").val();
                    mcCode=$("#txtXMCCode").val();
                    mcDesc=$("#txtXMCDesc").val();
                    qty=$("#txtXQty").val();

                    if(jQuery("#XarticleGrid tr").length>1){
                        var tableDataIds= jQuery('#XarticleGrid').getDataIDs();
                        //alert("tab : "+tableDataIds);
                        var insertData;
                        if(isxArticleEntered==1){
                            insertData=$("#txtBXArticleNo").val();
                        }else{
                            insertData=$("#txtXMCCode").val();
                        }
                        for(var index=0;index<=tableDataIds.length;index++)
                        {
                            var rowData = jQuery('#XarticleGrid').getRowData(tableDataIds[index]);

                            // alert("Grid Data : "+rowData.bn);
                            if(isxArticleEntered==1){
                                if(insertData==rowData.xartNo){
                                    alert('Selected aricle code already exist.');
                                    $("#txtBXArticleNo").focus();
                                    $("#txtArticleNo").val("");
                                    return false;
                                }
                            }else{
                                if(insertData==rowData.xmcCode){
                                    alert('Selected MC code already exist.');
                                    $("#txtXMCCode").focus();
                                    $("#txtXMCCode").val("");
                                    return false;
                                }
                            }

                        }
                    }
                    var checkblank = isBlank(qty,"Quantity ");
                    if(checkblank[0]==false){
                        alert(checkblank[1]);
                        $("#txtXQty").focus();
                        return false;
                    }
                    if(!isNumeric(qty)){
                        alert("Quantity should be numeric.");
                        if(qty!=undefined || qty.length>0){
                            $("#txtXQty").focus();
                            $("#txtXQty").val("");
                        }
                        return false;
                    }
                    $("#XarticleGrid").jqGrid('addRowData',tempIndexSet1,{xartNo:articleCode,xartDesc:articleDesc,xmcCode:mcCode,xmcDesc:mcDesc,xqty:qty});
                    resetxArticleDtl();

                    tempIndexSet1++;
                });
                //x article upload
                $("#btnBXUpload").click(function (){
                    var fileid=  document.getElementById("BXarticleFile").value;
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
                    var mstPromoId= $("#isMstPromoIdX").val();
                    if(mstPromoId.length==0){
                        alert("Please select Promotion Request.");
                        return false;
                    }

                    //$("#isMstPromoIdX").val(mstPromoId);
                    getYArticleData();
                    getZArticleData();
                    getSet4ArticleData();
                    getSet5ArticleData();
                    var yarticle= $("#YarticleGridData").val();
                    if(yarticle.length>0){
                        $("#hdnYManualGridX").val(yarticle);
                    }
                    var zarticle= $("#ZarticleGridData").val();
                    if(zarticle.length>0){
                        $("#hdnZManualGridX").val(zarticle);
                    }
                    var set4article= $("#manualSet4GridData").val();
                    if(set4article.length>0){
                        $("#hdnSet4ManualGridX").val(set4article);
                    }
                    var set5article= $("#manualSet5GridData").val();
                    if(set5article.length>0){
                        $("#hdnSet5ManualGridX").val(set5article);
                    }
                });
                function resetxArticleDtl(){
                    $("#txtBXArticleNo").val("");
                    $("#txtBXArticleDesc").val("");
                    $("#txtXMCCode").val("");
                    $("#txtXMCDesc").val("");
                    $("#txtXQty").val("");
                    $("#btnAddXArticle").attr("disabled", true);
                    $("#txtBXArticleNo").focus();
                }

                // set 2 y article capture starts
                var isyArticleEntered=1;
                var  yarticleCode,yarticleDesc,ymcCode,ymcDesc, yqty;
                $("#btnValidateYArticle").click(function (){
                    articleCode= $("#txtGYArticleNo").val();
                    mcCode=$("#txtGYMCCode").val();
                    if( articleCode !=undefined && articleCode.length>0 ){
                        isyArticleEntered=1;
                    }else{
                        isyArticleEntered=0;
                    }

                    var mstPromoId= $("#promoreqID").val();
                    if(mstPromoId.length==0){
                        alert("Please select Promotion Request.");
                        return false;
                    }
                    //Validate The Article OR MC From js/PromotionCommonUtil.js And Set The Response In textbox
                    validateArticleORMCCode(isyArticleEntered, mcCode, articleCode,"txtGYArticleNo","txtGYArticleDesc","txtGYMCCode","txtGYMCDesc",mstPromoId,"1");
                    $("#btnAddGYArticle").attr("disabled", false);
                });

                var tempIndexSet2=0;
                $("#btnAddGYArticle").attr("disabled", true);
                $("#btnAddGYArticle").click(function (){
                    yarticleCode= $("#txtGYArticleNo").val();
                    yarticleDesc=$("#txtGYArticleDesc").val();
                    ymcCode=$("#txtGYMCCode").val();
                    ymcDesc=$("#txtGYMCDesc").val();
                    yqty=$("#txtYQty").val();

                    if(jQuery("#YarticleGrid tr").length>1){
                        var tableDataIds= jQuery('#YarticleGrid').getDataIDs();
                        //alert("tab : "+tableDataIds);
                        var insertData;
                        if(isyArticleEntered==1){
                            insertData=$("#txtGYArticleNo").val();
                        }else{
                            insertData=$("#txtGYMCCode").val();
                        }
                        for(var index=0;index<=tableDataIds.length;index++)
                        {
                            var rowData = jQuery('#YarticleGrid').getRowData(tableDataIds[index]);

                            // alert("Grid Data : "+rowData.bn);
                            if(isyArticleEntered==1){
                                if(insertData==rowData.yartNo){
                                    alert('Selected aricle code already exist.');
                                    $("#txtGYArticleNo").focus();
                                    $("#txtGYArticleNo").val("");
                                    return false;
                                }
                            }else{
                                if(insertData==rowData.ymcCode){
                                    alert('Selected MC code already exist.');
                                    $("#txtGYMCCode").focus();
                                    $("#txtGYMCCode").val("");
                                    return false;
                                }
                            }

                        }
                    }
                    var checkblank = isBlank(yqty,"Quantity ");
                    if(checkblank[0]==false){
                        alert(checkblank[1]);
                        $("#txtYQty").focus();
                        return false;
                    }
                    if(!isNumeric(yqty)){
                        alert("Quantity should be numeric.");
                        if(yqty!=undefined || yqty.length>0){
                            $("#txtYQty").focus();
                            $("#txtYQty").val("");
                        }
                        return false;
                    }
                    $("#YarticleGrid").jqGrid('addRowData',tempIndexSet2,{yartNo:yarticleCode,yartDesc:yarticleDesc,ymcCode:ymcCode,ymcDesc:ymcDesc,yqty:yqty});
                    resetyArticleDtl();

                    tempIndexSet2++;
                });
                // y file upload
                $("#btnGYUpload").click(function (){
                    var fileid=  document.getElementById("GYarticleFile").value;
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
                    var mstPromoId= $("#isMstPromoIdY").val();
                    if(mstPromoId.length==0){
                        alert("Please select Promotion Request.");
                        return false;
                    }
//                    $("#isMstPromoIdY").val(mstPromoId);
//                    $("#selGroupY").val(group);
                    getXArticleData();
                    getZArticleData();
                    getSet4ArticleData();
                    getSet5ArticleData();
                    var xarticle= $("#XarticleGridData").val();
                    if(xarticle.length>0){
                        $("#hdnXManualGridY").val(xarticle);
                    }
                    var zarticle= $("#ZarticleGridData").val();
                    if(zarticle.length>0){
                        $("#hdnZManualGridY").val(zarticle);
                    }
                    var set4article= $("#manualSet4GridData").val();
                    if(set4article.length>0){
                        $("#hdnSet4ManualGridY").val(set4article);
                    }
                    var set5article= $("#manualSet5GridData").val();
                    if(set5article.length>0){
                        $("#hdnSet5ManualGridY").val(set5article);
                    }


                });
                function resetyArticleDtl(){
                    $("#txtGYArticleNo").val("");
                    $("#txtGYArticleDesc").val("");
                    $("#txtGYMCCode").val("");
                    $("#txtGYMCDesc").val("");
                    $("#txtYQty").val("");
                    $("#btnAddGYArticle").attr("disabled", true);
                    $("#txtGYArticleNo").focus();
                }

                // set 3 Z article capture start
                var iszArticleEntered=1;
                var  zarticleCode,zarticleDesc,zmcCode,zmcDesc, zqty;
                $("#btnValidateZArticle").click(function (){
                    articleCode= $("#txtBZArticleNo").val();
                    mcCode=$("#txtZMCCode").val();
                    if( articleCode !=undefined && articleCode.length>0 ){
                        iszArticleEntered=1;
                    }else{
                        iszArticleEntered=0;
                    }

                    var mstPromoId= $("#promoreqID").val();
                    if(mstPromoId.length==0){
                        alert("Please select Promotion Request.");
                        return false;
                    }

                    //Validate The Article OR MC From js/PromotionCommonUtil.js And Set The Response In textbox
                    validateArticleORMCCode(iszArticleEntered, mcCode, articleCode,"txtBZArticleNo","txtBZArticleDesc","txtZMCCode","txtZMCDesc",mstPromoId,"1");
                    $("#btnAddZArticle").attr("disabled", false);
                });

                var tempIndexset3=0;
                $("#btnAddZArticle").attr("disabled", true);
                $("#btnAddZArticle").click(function (){
                    zarticleCode= $("#txtBZArticleNo").val();
                    zarticleDesc=$("#txtBZArticleDesc").val();
                    zmcCode=$("#txtZMCCode").val();
                    zmcDesc=$("#txtZMCDesc").val();
                    zqty=$("#txtZQty").val();

                    if(jQuery("#ZarticleGrid tr").length>1){
                        var tableDataIds= jQuery('#ZarticleGrid').getDataIDs();
                        //alert("tab : "+tableDataIds);
                        var insertData;
                        if(iszArticleEntered==1){
                            insertData=$("#txtBZArticleNo").val();
                        }else{
                            insertData=$("#txtZMCCode").val();
                        }
                        for(var index=0;index<=tableDataIds.length;index++)
                        {
                            var rowData = jQuery('#ZarticleGrid').getRowData(tableDataIds[index]);

                            // alert("Grid Data : "+rowData.bn);
                            if(iszArticleEntered==1){
                                if(insertData==rowData.zartNo){
                                    alert('Selected aricle code already exist.');
                                    $("#txtBZArticleNo").focus();
                                    $("#txtBZArticleNo").val("");
                                    return false;
                                }
                            }else{
                                if(insertData==rowData.zmcCode){
                                    alert('Selected MC code already exist.');
                                    $("#txtZMCCode").focus();
                                    $("#txtZMCCode").val("");
                                    return false;
                                }
                            }

                        }
                    }
                    var checkblank = isBlank(zqty,"Quantity ");
                    if(checkblank[0]==false){
                        alert(checkblank[1]);
                        $("#txtZQty").focus();
                        return false;
                    }
                    if(!isNumeric(zqty)){
                        alert("Quantity should be numeric.");
                        if(zqty!=undefined || zqty.length>0){
                            $("#txtZQty").focus();
                            $("#txtZQty").val("");
                        }
                        return false;
                    }
                    $("#ZarticleGrid").jqGrid('addRowData',tempIndexset3,{zartNo:zarticleCode,zartDesc:zarticleDesc,zmcCode:zmcCode,zmcDesc:zmcDesc,zqty:zqty});
                    resetZArticleDtl();

                    tempIndexset3++;
                });
                //z file upload
                $("#btnBZUpload").click(function (){
                    var fileid=  document.getElementById("BZarticleFile").value;
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
                    var mstPromoId= $("#isMstPromoIdZ").val();
                    if(mstPromoId.length==0){
                        alert("Please select Promotion Request.");
                        return false;
                    }
//                    $("#isMstPromoIdZ").val(mstPromoId);
//                    $("#selGroupZ").val(group);

                    getXArticleData();
                    getYArticleData();
                    getSet4ArticleData();
                    getSet5ArticleData();
                    var xarticle= $("#XarticleGridData").val();
                    if(xarticle.length>0){
                        $("#hdnXManualGridZ").val(xarticle);
                    }
                    var yarticle= $("#YarticleGridData").val();
                    //                    alert(yarticle);
                    //                    return false;
                    if(yarticle.length>0){
                        $("#hdnYManualGridZ").val(yarticle);
                    }
                    var set4article= $("#manualSet4GridData").val();
                    if(set4article.length>0){
                        $("#hdnSet4ManualGridZ").val(set4article);
                    }
                    var set5article= $("#manualSet5GridData").val();
                    if(set5article.length>0){
                        $("#hdnSet5ManualGridZ").val(set5article);
                    }
                });
                function resetZArticleDtl(){
                    $("#txtBZArticleNo").val("");
                    $("#txtBZArticleDesc").val("");
                    $("#txtZMCCode").val("");
                    $("#txtZMCDesc").val("");
                    $("#txtZQty").val("");
                    $("#btnAddZArticle").attr("disabled", true);
                    $("#txtBZArticleNo").focus();
                }

                // set 4 start
                var isSet4ArticleEntered=1;
                var  articleCode4,articleDesc4,mcCode4,mcDesc4, qty4;
                $("#btnValidateSet4Article").click(function (){
                    articleCode4= $("#txtSet4ArticleNo").val();
                    mcCode4=$("#txtSet4MCCode").val();
                    if( articleCode4 !=undefined && articleCode4.length>0 ){
                        isSet4ArticleEntered=1;
                    }else{
                        isSet4ArticleEntered=0;
                    }

                    var mstPromoId= $("#promoreqID").val();
                    if(mstPromoId.length==0){
                        alert("Please select Promotion Request.");
                        return false;
                    }

                    //Validate The Article OR MC From js/PromotionCommonUtil.js And Set The Response In textbox
                    validateArticleORMCCode(isSet4ArticleEntered, mcCode4, articleCode4,"txtSet4ArticleNo","txtSet4ArticleDesc","txtSet4MCCode","txtSet4MCDesc",mstPromoId,"1");
                    $("#btnAddSet4Article").attr("disabled", false);
                });

                var tempIndexset4=0;
                $("#btnAddSet4Article").attr("disabled", true);
                $("#btnAddSet4Article").click(function (){
                    articleCode4= $("#txtSet4ArticleNo").val();
                    articleDesc4=$("#txtSet4ArticleDesc").val();
                    mcCode4=$("#txtSet4MCCode").val();
                    mcDesc4=$("#txtSet4MCDesc").val();
                    qty4=$("#txtSet4Qty").val();

                    if(jQuery("#Set4articleGrid tr").length>1){
                        var tableDataIds= jQuery('#Set4articleGrid').getDataIDs();
                        //alert("tab : "+tableDataIds);
                        var insertData;
                        if(isSet4ArticleEntered==1){
                            insertData=$("#txtSet4ArticleNo").val();
                        }else{
                            insertData=$("#txtSet4MCCode").val();
                        }
                        for(var index=0;index<=tableDataIds.length;index++)
                        {
                            var rowData = jQuery('#Set4articleGrid').getRowData(tableDataIds[index]);

                            // alert("Grid Data : "+rowData.bn);
                            if(isSet4ArticleEntered==1){
                                if(insertData==rowData.set4artNo){
                                    alert('Selected aricle code already exist.');
                                    $("#txtSet4ArticleNo").focus();
                                    $("#txtSet4ArticleNo").val("");
                                    return false;
                                }
                            }else{
                                if(insertData==rowData.set4mcCode){
                                    alert('Selected MC code already exist.');
                                    $("#txtSet4MCCode").focus();
                                    $("#txtSet4MCCode").val("");
                                    return false;
                                }
                            }

                        }
                    }
                    var checkblank = isBlank(qty4,"Quantity ");
                    if(checkblank[0]==false){
                        alert(checkblank[1]);
                        $("#txtSet4Qty").focus();
                        return false;
                    }
                    if(!isNumeric(qty4)){
                        alert("Quantity should be numeric.");
                        if(zqty!=undefined || zqty.length>0){
                            $("#txtSet4Qty").focus();
                            $("#txtSet4Qty").val("");
                        }
                        return false;
                    }
                    $("#Set4articleGrid").jqGrid('addRowData',tempIndexset4,{set4artNo:articleCode4,set4artDesc:articleDesc4,set4mcCode:mcCode4,set4mcDesc:mcDesc4,set4qty:qty4});
                    resetset4ArticleDtl();

                    tempIndexset4++;
                });

                function resetset4ArticleDtl(){
                    $("#txtSet4ArticleNo").val("");
                    $("#txtSet4MCCode").val("");
                    $("#txtSet4ArticleDesc").val("");
                    $("#txtSet4MCDesc").val("");
                    $("#txtSet4Qty").val("");
                    $("#btnAddSet4Article").attr("disabled", true);
                    $("#txtSet4ArticleNo").focus();
                }

                $("#btnSet4Upload").click(function (){
                    var fileid=  document.getElementById("Set4articleFile").value;
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
                    var mstPromoId= $("#isMstPromoIdSet4").val();
                    if(mstPromoId.length==0){
                        alert("Please select Promotion Request.");
                        return false;
                    }
//                    $("#isMstPromoIdSet4").val(mstPromoId);
//                    $("#selGroupSet4").val(group);
                    getXArticleData();
                    getYArticleData();
                    getZArticleData();
                    getSet5ArticleData();
                    var xarticle= $("#XarticleGridData").val();
                    if(xarticle.length>0){
                        $("#hdnXManualGridSet4").val(xarticle);
                    }
                    var yarticle= $("#YarticleGridData").val();
                    if(yarticle.length>0){
                        $("#hdnYManualGridSet4").val(yarticle);
                    }
                    var zarticle= $("#ZarticleGridData").val();
                    if(zarticle.length>0){
                        $("#hdnZManualGridSet4").val(zarticle);
                    }
                    var set5article= $("#manualSet5GridData").val();
                    if(set5article.length>0){
                        $("#hdnSet5ManualGridSet4").val(set5article);
                    }

                });
                // set 4 end

                //set 5 start
                var isSet5ArticleEntered=1;
                var  articleCode5,articleDesc5,mcCode5,mcDesc5, qty5;
                $("#btnValidateSet5Article").click(function (){
                    articleCode5= $("#txtSet5ArticleNo").val();
                    mcCode5=$("#txtSet5MCCode").val();
                    if( articleCode5 !=undefined && articleCode5.length>0 ){
                        isSet5ArticleEntered=1;
                    }else{
                        isSet5ArticleEntered=0;
                    }

                    var mstPromoId= $("#promoreqID").val();
                    if(mstPromoId.length==0){
                        alert("Please select Promotion Request.");
                        return false;
                    }

                    //Validate The Article OR MC From js/PromotionCommonUtil.js And Set The Response In textbox
                    validateArticleORMCCode(isSet5ArticleEntered, mcCode5, articleCode5,"txtSet5ArticleNo","txtSet5ArticleDesc","txtSet5MCCode","txtSet5MCDesc",mstPromoId,"1");
                    $("#btnAddSet5Article").attr("disabled", false);
                });

                var tempIndexSet5=0;
                $("#btnAddSet5Article").attr("disabled", true);
                $("#btnAddSet5Article").click(function (){
                    articleCode5= $("#txtSet5ArticleNo").val();
                    articleDesc5=$("#txtSet5ArticleDesc").val();
                    mcCode5=$("#txtSet5MCCode").val();
                    mcDesc5=$("#txtSet5MCDesc").val();
                    qty5=$("#txtSet5Qty").val();

                    if(jQuery("#Set5articleGrid tr").length>1){
                        var tableDataIds= jQuery('#Set5articleGrid').getDataIDs();
                        //alert("tab : "+tableDataIds);
                        var insertData;
                        if(isSet5ArticleEntered==1){
                            insertData=$("#txtSet5ArticleNo").val();
                        }else{
                            insertData=$("#txtSet5MCCode").val();
                        }
                        for(var index=0;index<=tableDataIds.length;index++)
                        {
                            var rowData = jQuery('#Set5articleGrid').getRowData(tableDataIds[index]);

                            // alert("Grid Data : "+rowData.bn);
                            if(isSet5ArticleEntered==1){
                                if(insertData==rowData.set5artNo){
                                    alert('Selected aricle code already exist.');
                                    $("#txtSet5ArticleNo").focus();
                                    $("#txtSet5ArticleNo").val("");
                                    return false;
                                }
                            }else{
                                if(insertData==rowData.set5mcCode){
                                    alert('Selected MC code already exist.');
                                    $("#txtSet5MCCode").focus();
                                    $("#txtSet5MCCode").val("");
                                    return false;
                                }
                            }

                        }
                    }
                    var checkblank = isBlank(qty5,"Quantity ");
                    if(checkblank[0]==false){
                        alert(checkblank[1]);
                        $("#txtSet5Qty").focus();
                        return false;
                    }
                    if(!isNumeric(qty5)){
                        alert("Quantity should be numeric.");
                        if(zqty!=undefined || zqty.length>0){
                            $("#txtSet5Qty").focus();
                            $("#txtSet5Qty").val("");
                        }
                        return false;
                    }
                    $("#Set5articleGrid").jqGrid('addRowData',tempIndexSet5,{set5artNo:articleCode5,set5artDesc:articleDesc5,set5mcCode:mcCode5,set5mcDesc:mcDesc5,set5qty:qty5});
                    resetset5ArticleDtl();

                    tempIndexSet5++;
                });

                function resetset5ArticleDtl(){
                    $("#txtSet5ArticleNo").val("");
                    $("#txtSet5MCCode").val("");
                    $("#txtSet5ArticleDesc").val("");
                    $("#txtSet5MCDesc").val("");
                    $("#txtSet5Qty").val("");
                    $("#btnAddSet5Article").attr("disabled", true);
                    $("#txtSet5ArticleNo").focus();
                }

                $("#btnSet5Upload").click(function (){
                    var fileid=  document.getElementById("Set5articleFile").value;
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
                    var mstPromoId= $("#isMstPromoIdSet5").val();
                    if(mstPromoId.length==0){
                        alert("Please select Promotion Request.");
                        return false;
                    }
//                    $("#isMstPromoIdSet5").val(mstPromoId);
//                    $("#selGroupSet5").val(group);
                    getXArticleData();
                    getYArticleData();
                    getZArticleData();
                    getSet4ArticleData();
                    var xarticle= $("#XarticleGridData").val();
                    if(xarticle.length>0){
                        $("#hdnXManualGridSet5").val(xarticle);
                    }
                    var yarticle= $("#YarticleGridData").val();
                    if(yarticle.length>0){
                        $("#hdnYManualGridSet5").val(yarticle);
                    }
                    var zarticle= $("#ZarticleGridData").val();
                    if(zarticle.length>0){
                        $("#hdnZManualGridSet5").val(zarticle);
                    }
                    var set4article= $("#manualSet4GridData").val();
                    if(set4article.length>0){
                        $("#hdnSet4ManualGridSet5").val(set4article);
                    }

                });
                var discName ,setId ,setDiscValue;
                var tempIndexDisc=0;
                $("#addDisc").click(function (){
                    setId= $("#selSet").val();
                    discName=$("#DisConfig").val();
                    setDiscValue=$("#txtdisValue").val();
                    var selectedGroup=$("#selGroup").val();
                    //        alert(setId);
                    // alert(selectedGroup);
                    //        if(setId==-1){
                    //            alert("Please select Set.");
                    //            return false;
                    //        }
                    //        if(discName==-1){
                    //            alert("Please select Discount Type.");
                    //            return false;
                    //        }
                    //        return false;
                    if(selectedGroup==1){
                        if(setId==4 || setId==5){
                            alert("You can not enter discount for Set 4 & set 5");
                            return false;
                        }
                    }else if(selectedGroup==2){
                        if(setId==5){
                            alert("You can not enter discount for "+setId);
                            return false;
                        }
                    }else if(selectedGroup==0){
                        if(setId==3 ||setId==4 || setId==5){
                            alert("You can only enter discount for Set 1 & set 2");
                            return false;
                        }
                    }

                    if(jQuery("#discountgrid tr").length>1){
                        var tableDataIds= jQuery('#discountgrid').getDataIDs();
                        for(var index=0;index<=tableDataIds.length;index++)            {
                            var rowData = jQuery('#discountgrid').getRowData(tableDataIds[index]);
                            if(setId==rowData.setid){
                                alert('Selected Set already exist.');
                                $("#selSet").focus();
                                return false;
                            }
                        }
                    }
                    var discCheck = checkComboSelection("DisConfig", "Discount Config");
                    if(discCheck[0]==false){
                        alert(discCheck[1]);
                        return false;
                    }

                    var setCheck = checkComboSelection("selSet", "Set ");
                    if(setCheck[0]==false){
                        alert(setCheck[1]);
                        return false;
                    }
                    var checkblank = isBlank(setDiscValue,"Value ");
                    if(checkblank[0]==false){
                        alert(checkblank[1]);
                        $("#txtdisValue").focus();
                        return false;
                    }
                    if(!isNumeric(setDiscValue)){
                        alert("Value should be numeric.");
                        if(setDiscValue!=undefined || setDiscValue.length>0){
                            $("#txtdisValue").focus();
                            $("#txtdisValue").val("");
                        }
                        return false;
                    }
                    var selname=$("#selSet option:selected").text();
                    var config=$("#DisConfig option:selected").text();
                    
                    $("#discountgrid").jqGrid('addRowData',tempIndexDisc,{setid:setId,setname:selname,setdiscid:discName,setdisc:config,setvalue:setDiscValue});
                    resetDiscount();
                    tempIndexDisc++;
                });

                function resetDiscount(){
                    $("#txtdisValue").val('');
                    $("#selSet").val("-1");
                    $("#DisConfig").val("-1");
                }
                // set5 end
                $("#btnSave").click(function (){
                    //var txtdisValue= $("#txtdisValue").val();
                    var txtBXGYmargin= $("#txtBXGYmargin").val();
                    var txtBXGYgrowth= $("#txtBXGYgrowth").val();
                    var txtBXGYsellQty= $("#txtBXGYsellQty").val();
                    var txtBXGYgrowthConver= $("#txtBXGYgrowthConver").val();
                    var txtBXGYsalegrowth= $("#txtBXGYsalegrowth").val();
                    var txtBXGYFrom= $("#txtBXGYFrom").val();
                    var txtBXGYTo= $("#txtBXGYTo").val();
                    var promoId =$("#promoreqID").val();
                    
                    var txtRemarks =$("#txtRemarks").val();
                    if(promoId==null){
                        alert("Please select Promotion Request.");
                        return false;
                    }

                    //$("#promoreqID").val(promoReqId);
                    //alert("X:"+isxManualEntryFormSubmit);
                    //alert("Y:"+isYManualEntryFormSubmit);
                    //alert("PromoId : "+promoId);
                    //return false;

                    if(isxManualEntryFormSubmit==1){
                        getXArticleData();
                        var xarticle= $("#XarticleGridData").val();
                        if(xarticle.length==0){
                            alert("Please enter Article detail or Mc detail for X.");
                            $("#txtBXArticleNo").focus();
                            $("#txtBXArticleNo").val("");
                            return false;
                        }
                        $("#isXManualEntry").val("1");
                    }else{
                        $("#isXManualEntry").val("0");
                    }


                    if(isYManualEntryFormSubmit==1){
                        getYArticleData();
                        var yarticle=$("#YarticleGridData").val();
                        if(yarticle.length==0){
                            alert("Please enter Article detail or Mc detail for Y.");
                            $("#txtGYArticleNo").focus();
                            $("#txtGYArticleNo").val("");
                            return false;
                        }
                        $("#isYManualEntry").val("1");

                    }else{
                        $("#isYManualEntry").val("0");
                    }

                    var group=$("#selGroup option:selected").val();
                    if(group==1){
                        if(isZManualEntryFormSubmit==1){
                            getZArticleData();
                            var zarticle=$("#ZarticleGridData").val();
                            if(zarticle.length==0){
                                alert("Please enter Article detail or Mc detail for Z.");
                                $("#txtBZArticleNo").focus();
                                $("#txtBZArticleNo").val("");
                                return false;
                            }

                            $("#isZManualEntry").val("1");
                        }else{
                            $("#isZManualEntry").val("0");
                        }
                    }else if(group==2){
                        if(isZManualEntryFormSubmit==1){
                            getZArticleData();
                            var zarticle=$("#ZarticleGridData").val();
                            if(zarticle.length==0){
                                alert("Please enter Article detail or Mc detail for Z.");
                                $("#txtBZArticleNo").focus();
                                $("#txtBZArticleNo").val("");
                                return false;
                            }

                            $("#isZManualEntry").val("1");
                        }else{
                            $("#isZManualEntry").val("0");
                        }
                        if(isSet4ManualEntryFormSubmit==1){
                            getSet4ArticleData();
                            var Set4article= $("#manualSet4GridData").val();
                            if(Set4article.length==0){
                                alert("Please enter Article detail or Mc detail for Set 4.");
                                $("#txtSet4ArticleNo").focus();
                                $("#txtSet4ArticleNo").val("");
                                return false;
                            }
                            $("#isSet4ManualEntry").val("1");
                        }else{
                            $("#isSet4ManualEntry").val("0");
                        }
                    }else if(group==3){
                        if(isZManualEntryFormSubmit==1){
                            getZArticleData();
                            var zarticle=$("#ZarticleGridData").val();
                            if(zarticle.length==0){
                                alert("Please enter Article detail or Mc detail for Z.");
                                $("#txtBZArticleNo").focus();
                                $("#txtBZArticleNo").val("");
                                return false;
                            }

                            $("#isZManualEntry").val("1");
                        }else{
                            $("#isZManualEntry").val("0");
                        }
                        if(isSet4ManualEntryFormSubmit==1){
                            getSet4ArticleData();
                            var Set4article= $("#manualSet4GridData").val();
                            if(Set4article.length==0){
                                alert("Please enter Article detail or Mc detail for Set 4.");
                                $("#txtSet4ArticleNo").focus();
                                $("#txtSet4ArticleNo").val("");
                                return false;
                            }
                            $("#isSet4ManualEntry").val("1");
                        }else{
                            $("#isSet4ManualEntry").val("0");
                        }
                        if(isSet5ManualEntryFormSubmit==1){
                            getSet5ArticleData();
                            var Set5article= $("#manualSet5GridData").val();
                            if(Set5article.length==0){
                                alert("Please enter Article detail or Mc detail for Set 5.");
                                $("#txtSet5ArticleNo").focus();
                                $("#txtSet5ArticleNo").val("");
                                return false;
                            }
                            $("#isSet5ManualEntry").val("1");
                        }else{
                            $("#isSet5ManualEntry").val("0");
                        }
                    }


                    getDiscountData();
                    var disGrid = $("#manualdiscGridData").val();
                    //alert(disGrid);
                    if(disGrid.length==0){
                        alert("Please enter discount detail atleast for one level.");
                        $("#selSet").focus();
                        return false;
                    }
                   

                    var checkblankmargin = isBlank(txtBXGYmargin,"Margin Achivement ");
                    if(checkblankmargin[0]==false){
                        alert(checkblankmargin[1]);
                        $("#txtBXGYmargin").focus();
                        return false;
                    }
                    if(!isNumeric(txtBXGYmargin)){
                        alert("Margin Achivement should be numeric.");
                        $("#txtBXGYmargin").val("");
                        $("#txtBXGYmargin").focus();
                        return false;
                    }

                    var checkblankgrowth = isBlank(txtBXGYgrowth,"Growth in Ticket Size ");
                    if(checkblankgrowth[0]==false){
                        alert(checkblankgrowth[1]);
                        $("#txtBXGYgrowth").focus();
                        return false;
                    }
                    if(!isNumeric(txtBXGYgrowth)){
                        alert("Growth in Ticket Size should be numeric.");
                        $("#txtBXGYgrowth").val("");
                        $("#txtBXGYgrowth").focus();
                        return false;
                    }

                    var checkblanksell = isBlank(txtBXGYsellQty,"Sell thru – v/s quantity ");
                    if(checkblanksell[0]==false){
                        alert(checkblanksell[1]);
                        $("#txtBXGYsellQty").focus();
                        return false;
                    }
                    if(!isNumeric(txtBXGYsellQty)){
                        alert("Sell thru – v/s quantity should be numeric.");
                        $("#txtBXGYsellQty").val("");
                        $("#txtBXGYsellQty").focus();
                        return false;
                    }
                    if(!isInteger(txtBXGYsellQty)){
                        alert("Sell thru – v/s quantity should be Integer.");
                        $("#txtBXGYsellQty").val("");
                        $("#txtBXGYsellQty").focus();
                        return false;
                    }

                    var checkblankcover = isBlank(txtBXGYgrowthConver,"Growth in conversions ");
                    if(checkblankcover[0]==false){
                        alert(checkblankcover[1]);
                        $("#txtBXGYgrowthConver").focus();
                        return false;
                    }
                    if(!isNumeric(txtBXGYgrowthConver)){
                        alert("Growth in conversions should be numeric.");
                        $("#txtBXGYgrowthConver").val("");
                        $("#txtBXGYgrowthConver").focus();
                        return false;
                    }

                    var checkblanksalegrowth = isBlank(txtBXGYsalegrowth,"Sales Growth – both in quantity and value ");
                    if(checkblanksalegrowth[0]==false){
                        alert(checkblanksalegrowth[1]);
                        $("#txtBXGYsalegrowth").focus();
                        return false;
                    }
                    if(!isNumeric(txtBXGYsalegrowth)){
                        alert("Sales Growth – both in quantity and value should be numeric.");
                        $("#txtBXGYsalegrowth").val("");
                        $("#txtBXGYsalegrowth").focus();
                        return false;
                    }

                    if(txtBXGYFrom.length>0 && txtBXGYTo.length==0){
                        alert("Please Select Valid To Date!");
                        $("#txtBXGYTo").focus();
                        return false;
                    }
                    if(txtBXGYFrom.length==0){
                        alert("Please Select Valid Form Date!");
                        $("#txtBXGYFrom").focus();
                        return false;
                    }
                    if(txtBXGYTo.length==0){
                        alert("Please Select Valid To Date!");
                        $("#txtBXGYTo").focus();
                        return false;
                    }
                    
                    var checkblankremarks = isBlank(txtRemarks,"Remarks ");
                    if(checkblankremarks[0]==false){
                        alert(checkblankremarks[1]);
                        $("#txtRemarks").focus();
                        return false;
                    }
                    
                    var checklengthremarks = checkLength(txtRemarks ,"Remarks ",100);
                    if(checklengthremarks[0]==false){
                        $("#txtRemarks ").focus();
                        alert(checklengthremarks[1]);
                        return false;
                    }

                });

                function getXArticleData(){
                    var articleGridIds = jQuery("#XarticleGrid").getDataIDs();
                    if(articleGridIds.length > 0){
                        //alert("Total Qual Grid Data = "+qualGridIds);
                        var tempIndex=0;
                        var arr=new Array();

                        for(var index=0;index<=articleGridIds.length;index++){
                            //Columns Values!!
                            var rowData=jQuery('#XarticleGrid').getRowData(articleGridIds[index]);
                            arr[tempIndex]=rowData.xartNo;
                            arr[++tempIndex]=rowData.xartDesc;
                            arr[++tempIndex]=rowData.xmcCode;
                            arr[++tempIndex]=rowData.xmcDesc;
                            arr[++tempIndex]=rowData.xqty;
                            tempIndex++;
                        }
                        // alert("Qual arr : "+arr);
                        $("#XarticleGridData").val(arr);
                    }
                }

                function getDiscountData(){
                    var articleGridIds = jQuery("#discountgrid").getDataIDs();
                    if(articleGridIds.length > 0){
                        //alert("Total Qual Grid Data = "+qualGridIds);
                        var tempIndex=0;
                        var arr=new Array();

                        for(var index=0;index<=articleGridIds.length;index++){
                            //Columns Values!!
                            var rowData=jQuery('#discountgrid').getRowData(articleGridIds[index]);
                            arr[tempIndex]=rowData.setid;
                            arr[++tempIndex]=rowData.setname;
                            arr[++tempIndex]=rowData.setdiscid;
                            arr[++tempIndex]=rowData.setdisc;
                            arr[++tempIndex]=rowData.setvalue;
                            tempIndex++;
                        }
                        // alert("Qual arr : "+arr);
                        $("#manualdiscGridData").val(arr);
                    }
                }
                //This function is used to set the entered data in X Article grid Manually.
                // This scenario happens when Y Article file is uploaded with Post Call.
                //Call The function just below the X Article Grid Defined in Script Sothat Data Can be filled after grid compeletion.
                function SetXZSet4Set5ArticleGridDataOnYFilePostFileOnPageLoad(){
                   
                    var hdnXArticleGridData=$("#hdnXManualGridY").val();
                    alert("calledd : "+hdnXArticleGridData);
                    var hdnZManualGridData=$("#hdnZManualGridY").val();
                    var hdnSet4ManualGridData=$("#hdnSet4ManualGridY").val();
                    var hdnSet5ManualGridData=$("#hdnSet5ManualGridY").val();
                    if(hdnXArticleGridData!=undefined && hdnXArticleGridData.length>0 && hdnXArticleGridData!=null){
                        var indexForX=0;
                        var articledata=hdnXArticleGridData.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var xarticleCode=articledata[i * 5];
                            var xarticleDesc=articledata[(i * 5) + 1];
                            var xmcCode=articledata[(i * 5) + 2];
                            var xmcDesc=articledata[(i * 5) + 3];
                            var xqty=articledata[(i * 5) + 4];
                            if(xarticleCode!=undefined && xarticleCode.length>0){
                                $("#XarticleGrid").jqGrid('addRowData',indexForX,{xartNo:xarticleCode,xartDesc:xarticleDesc,xmcCode:xmcCode,xmcDesc:xmcDesc,xqty:xqty});
                                indexForX++;
                            }
                        }
                    }
                    if(hdnZManualGridData!=undefined && hdnZManualGridData.length>0 && hdnZManualGridData!=null){
                        var indexforZ=0;
                        var articledata=hdnZManualGridData.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var zarticleCode=articledata[i * 5];
                            var zarticleDesc=articledata[(i * 5) + 1];
                            var zmcCode=articledata[(i * 5) + 2];
                            var zmcDesc=articledata[(i * 5) + 3];
                            var zqty=articledata[(i * 5) + 4];
                            if(zarticleCode!=undefined && zarticleCode.length>0){
                                $("#ZarticleGrid").jqGrid('addRowData',indexforZ,{zartNo:zarticleCode,zartDesc:zarticleDesc,zmcCode:zmcCode,zmcDesc:zmcDesc,zqty:zqty});
                                indexforZ++;
                            }
                        }
                    }
                    if(hdnSet4ManualGridData!=undefined && hdnSet4ManualGridData.length>0 && hdnSet4ManualGridData!=null){
                        var indexSet4=0;
                        var articledata=hdnSet4ManualGridData.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var set4articleCode=articledata[i * 5];
                            var set4articleDesc=articledata[(i * 5) + 1];
                            var set4mcCode=articledata[(i * 5) + 2];
                            var set4mcDesc=articledata[(i * 5) + 3];
                            var set4qty=articledata[(i * 5) + 4];
                            if(set4articleCode!=undefined && set4articleCode.length>0){
                                $("#Set4articleGrid").jqGrid('addRowData',indexSet4,{set4artNo:set4articleCode,set4artDesc:set4articleDesc,set4mcCode:set4mcCode,set4mcDesc:set4mcDesc,set4qty:set4qty});
                                indexSet4++;
                            }
                        }
                    }
                    if(hdnSet5ManualGridData!=undefined && hdnSet5ManualGridData.length>0 && hdnSet5ManualGridData!=null){
                        var indexSet5=0;
                        var articledata=hdnSet5ManualGridData.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var set5articleCode=articledata[i * 5];
                            var set5articleDesc=articledata[(i * 5) + 1];
                            var set5mcCode=articledata[(i * 5) + 2];
                            var set5mcDesc=articledata[(i * 5) + 3];
                            var set5qty=articledata[(i * 5) + 4];
                            if(set5articleCode!=undefined && set5articleCode.length>0){
                                $("#Set5articleGrid").jqGrid('addRowData',indexSet5,{set5artNo:set5articleCode,set5artDesc:set5articleDesc,set5mcCode:set5mcCode,set5mcDesc:set5mcDesc,set5qty:set5qty});
                                indexSet5++;
                            }
                        }
                    }
                }

                function SetYZSet4Set5ArticleGridDataOnYFilePostFileOnPageLoad(){
                    var hdnYManualGridX=$("#hdnYManualGridX").val();
                    var hdnZManualGridX=$("#hdnZManualGridX").val();
                    var hdnSet4ManualGridX=$("#hdnSet4ManualGridX").val();
                    var hdnSet5ManualGridX=$("#hdnSet5ManualGridX").val();
                    if(hdnYManualGridX!=undefined && hdnYManualGridX.length>0 && hdnYManualGridX!=null){
                        var indexsetY=0;
                        var articledata=hdnYManualGridX.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var yarticleCode=articledata[i * 5];
                            var yarticleDesc=articledata[(i * 5) + 1];
                            var ymcCode=articledata[(i * 5) + 2];
                            var ymcDesc=articledata[(i * 5) + 3];
                            var yqty=articledata[(i * 5) + 4];
                            if(yarticleCode!=undefined && yarticleCode.length>0){
                                $("#YarticleGrid").jqGrid('addRowData',indexsetY,{yartNo:yarticleCode,yartDesc:yarticleDesc,ymcCode:ymcCode,ymcDesc:ymcDesc,yqty:yqty});
                                indexsetY++;
                            }
                        }
                    }
                    if(hdnZManualGridX!=undefined && hdnZManualGridX.length>0 && hdnZManualGridX!=null){
                        var indexsetx=0;
                        var articledata=hdnZManualGridX.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var zarticleCode=articledata[i * 5];
                            var zarticleDesc=articledata[(i * 5) + 1];
                            var zmcCode=articledata[(i * 5) + 2];
                            var zmcDesc=articledata[(i * 5) + 3];
                            var zqty=articledata[(i * 5) + 4];
                            if(zarticleCode!=undefined && zarticleCode.length>0){
                                $("#ZarticleGrid").jqGrid('addRowData',indexsetx,{zartNo:zarticleCode,zartDesc:zarticleDesc,zmcCode:zmcCode,zmcDesc:zmcDesc,zqty:zqty});
                                indexsetx++;
                            }
                        }
                    }
                    if(hdnSet4ManualGridX!=undefined && hdnSet4ManualGridX.length>0 && hdnSet4ManualGridX!=null){
                        var indexset4=0;
                        var articledata=hdnSet4ManualGridX.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var set4articleCode=articledata[i * 5];
                            var set4articleDesc=articledata[(i * 5) + 1];
                            var set4mcCode=articledata[(i * 5) + 2];
                            var set4mcDesc=articledata[(i * 5) + 3];
                            var set4qty=articledata[(i * 5) + 4];
                            if(set4articleCode!=undefined && set4articleCode.length>0){
                                $("#Set4articleGrid").jqGrid('addRowData',indexset4,{set4artNo:set4articleCode,set4artDesc:set4articleDesc,set4mcCode:set4mcCode,set4mcDesc:set4mcDesc,set4qty:set4qty});
                                indexset4++;
                            }
                        }
                    }
                    if(hdnSet5ManualGridX!=undefined && hdnSet5ManualGridX.length>0 && hdnSet5ManualGridX!=null){
                        var indexset5=0;
                        var articledata=hdnSet5ManualGridX.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var set5articleCode=articledata[i * 5];
                            var set5articleDesc=articledata[(i * 5) + 1];
                            var set5mcCode=articledata[(i * 5) + 2];
                            var set5mcDesc=articledata[(i * 5) + 3];
                            var set5qty=articledata[(i * 5) + 4];
                            if(set5articleCode!=undefined && set5articleCode.length>0){
                                $("#Set5articleGrid").jqGrid('addRowData',indexset5,{set5artNo:set5articleCode,set5artDesc:set5articleDesc,set5mcCode:set5mcCode,set5mcDesc:set5mcDesc,set5qty:set5qty});
                                indexset5++;
                            }
                        }
                    }
                }

                function SetXYSet4Set5ArticleGridDataOnYFilePostFileOnPageLoad(){
                    var hdnXManualGridZ=$("#hdnXManualGridZ").val();
                    var hdnYManualGridZ=$("#hdnYManualGridZ").val();
                    var hdnSet4ManualGridZ=$("#hdnSet4ManualGridZ").val();
                    var hdnSet5ManualGridData=$("#hdnSet5ManualGridZ").val();
                    if(hdnXManualGridZ!=undefined && hdnXManualGridZ.length>0 && hdnXManualGridZ!=null){
                        var indexsetz=0;
                        var articledata=hdnXManualGridZ.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var xarticleCode=articledata[i * 5];
                            var xarticleDesc=articledata[(i * 5) + 1];
                            var xmcCode=articledata[(i * 5) + 2];
                            var xmcDesc=articledata[(i * 5) + 3];
                            var xqty=articledata[(i * 5) + 4];
                            if(xarticleCode!=undefined && xarticleCode.length>0){
                                $("#XarticleGrid").jqGrid('addRowData',indexsetz,{xartNo:xarticleCode,xartDesc:xarticleDesc,xmcCode:xmcCode,xmcDesc:xmcDesc,xqty:xqty});
                                indexsetz++;
                            }
                        }
                    }
                    if(hdnYManualGridZ!=undefined && hdnYManualGridZ.length>0 && hdnYManualGridZ!=null){
                        var indexsety=0;
                        var articledata=hdnYManualGridZ.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var yarticleCode=articledata[i * 5];
                            var yarticleDesc=articledata[(i * 5) + 1];
                            var ymcCode=articledata[(i * 5) + 2];
                            var ymcDesc=articledata[(i * 5) + 3];
                            var yqty=articledata[(i * 5) + 4];
                            if(yarticleCode!=undefined && yarticleCode.length>0){
                                $("#YarticleGrid").jqGrid('addRowData',indexsety,{yartNo:yarticleCode,yartDesc:yarticleDesc,ymcCode:ymcCode,ymcDesc:ymcDesc,yqty:yqty});
                                indexsety++;
                            }
                        }
                    }
                    if(hdnSet4ManualGridZ!=undefined && hdnSet4ManualGridZ.length>0 && hdnSet4ManualGridZ!=null){
                        var indexset4=0;
                        var articledata=hdnSet4ManualGridZ.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var set4articleCode=articledata[i * 5];
                            var set4articleDesc=articledata[(i * 5) + 1];
                            var set4mcCode=articledata[(i * 5) + 2];
                            var set4mcDesc=articledata[(i * 5) + 3];
                            var set4qty=articledata[(i * 5) + 4];
                            if(set4articleCode!=undefined && set4articleCode.length>0){
                                $("#Set4articleGrid").jqGrid('addRowData',indexset4,{set4artNo:set4articleCode,set4artDesc:set4articleDesc,set4mcCode:set4mcCode,set4mcDesc:set4mcDesc,set4qty:set4qty});
                                indexset4++;
                            }
                        }
                    }
                    if(hdnSet5ManualGridData!=undefined && hdnSet5ManualGridData.length>0 && hdnSet5ManualGridData!=null){
                        var indexset5=0;
                        var articledata=hdnSet5ManualGridData.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var set5articleCode=articledata[i * 5];
                            var set5articleDesc=articledata[(i * 5) + 1];
                            var set5mcCode=articledata[(i * 5) + 2];
                            var set5mcDesc=articledata[(i * 5) + 3];
                            var set5qty=articledata[(i * 5) + 4];
                            if(set5articleCode!=undefined && set5articleCode.length>0){
                                $("#Set5articleGrid").jqGrid('addRowData',indexset5,{set5artNo:set5articleCode,set5artDesc:set5articleDesc,set5mcCode:set5mcCode,set5mcDesc:set5mcDesc,set5qty:set5qty});
                                indexset5++;
                            }
                        }
                    }
                }

                function SetXYZSet5ArticleGridDataOnYFilePostFileOnPageLoad(){
                    var hdnXManualGridSet4=$("#hdnXManualGridSet4").val();
                    var hdnYManualGridSet4=$("#hdnYManualGridSet4").val();
                    var hdnZManualGridSet4=$("#hdnZManualGridSet4").val();
                    var hdnSet5ManualGridData=$("#hdnSet5ManualGridSet4").val();
                    if(hdnXManualGridSet4!=undefined && hdnXManualGridSet4.length>0 && hdnXManualGridSet4!=null){
                        var indexsetx=0;
                        var articledata=hdnXManualGridSet4.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var xarticleCode=articledata[i * 5];
                            var xarticleDesc=articledata[(i * 5) + 1];
                            var xmcCode=articledata[(i * 5) + 2];
                            var xmcDesc=articledata[(i * 5) + 3];
                            var xqty=articledata[(i * 5) + 4];
                            if(xarticleCode!=undefined && xarticleCode.length>0){
                                $("#XarticleGrid").jqGrid('addRowData',indexsetx,{xartNo:xarticleCode,xartDesc:xarticleDesc,xmcCode:xmcCode,xmcDesc:xmcDesc,xqty:xqty});
                                indexsetx++;
                            }
                        }
                    }
                    if(hdnYManualGridSet4!=undefined && hdnYManualGridSet4.length>0 && hdnYManualGridSet4!=null){
                        var indexsety=0;
                        var articledata=hdnYManualGridSet4.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var yarticleCode=articledata[i * 5];
                            var yarticleDesc=articledata[(i * 5) + 1];
                            var ymcCode=articledata[(i * 5) + 2];
                            var ymcDesc=articledata[(i * 5) + 3];
                            var yqty=articledata[(i * 5) + 4];
                            if(yarticleCode!=undefined && yarticleCode.length>0){
                                $("#YarticleGrid").jqGrid('addRowData',indexsety,{yartNo:yarticleCode,yartDesc:yarticleDesc,ymcCode:ymcCode,ymcDesc:ymcDesc,yqty:yqty});
                                indexsety++;
                            }
                        }
                    }
                    if(hdnZManualGridSet4!=undefined && hdnZManualGridSet4.length>0 && hdnZManualGridSet4!=null){
                        var indexz=0;
                        var articledata=hdnZManualGridSet4.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var zarticleCode=articledata[i * 5];
                            var zarticleDesc=articledata[(i * 5) + 1];
                            var zmcCode=articledata[(i * 5) + 2];
                            var zmcDesc=articledata[(i * 5) + 3];
                            var zqty=articledata[(i * 5) + 4];
                            if(zarticleCode!=undefined && zarticleCode.length>0){
                                $("#ZarticleGrid").jqGrid('addRowData',indexz,{zartNo:zarticleCode,zartDesc:zarticleDesc,zmcCode:zmcCode,zmcDesc:zmcDesc,zqty:zqty});
                                indexz++;
                            }
                        }
                    }
                    if(hdnSet5ManualGridData!=undefined && hdnSet5ManualGridData.length>0 && hdnSet5ManualGridData!=null){
                        var indexset5=0;
                        var articledata=hdnSet5ManualGridData.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var set5articleCode=articledata[i * 5];
                            var set5articleDesc=articledata[(i * 5) + 1];
                            var set5mcCode=articledata[(i * 5) + 2];
                            var set5mcDesc=articledata[(i * 5) + 3];
                            var set5qty=articledata[(i * 5) + 4];
                            if(set5articleCode!=undefined && set5articleCode.length>0){
                                $("#Set5articleGrid").jqGrid('addRowData',indexset5,{set5artNo:set5articleCode,set5artDesc:set5articleDesc,set5mcCode:set5mcCode,set5mcDesc:set5mcDesc,set5qty:set5qty});
                                indexset5++;
                            }
                        }
                    }
                }
                function SetXYZSet4ArticleGridDataOnYFilePostFileOnPageLoad(){
                    var hdnXManualGridSet5=$("#hdnXManualGridSet5").val();
                    var hdnYManualGridSet5=$("#hdnYManualGridSet5").val();
                    var hdnZManualGridSet5=$("#hdnZManualGridSet5").val();
                    var hdnSet4ManualGridData=$("#hdnSet4ManualGridSet5").val();
                    if(hdnXManualGridSet5!=undefined && hdnXManualGridSet5.length>0 && hdnXManualGridSet5!=null){
                        var indexsetx=0;
                        var articledata=hdnXManualGridSet5.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var xarticleCode=articledata[i * 5];
                            var xarticleDesc=articledata[(i * 5) + 1];
                            var xmcCode=articledata[(i * 5) + 2];
                            var xmcDesc=articledata[(i * 5) + 3];
                            var xqty=articledata[(i * 5) + 4];
                            if(xarticleCode!=undefined && xarticleCode.length>0){
                                $("#XarticleGrid").jqGrid('addRowData',indexsetx,{xartNo:xarticleCode,xartDesc:xarticleDesc,xmcCode:xmcCode,xmcDesc:xmcDesc,xqty:xqty});
                                indexsetx++;
                            }
                        }
                    }
                    if(hdnYManualGridSet5!=undefined && hdnYManualGridSet5.length>0 && hdnYManualGridSet5!=null){
                        var indexsety=0;
                        var articledata=hdnYManualGridSet5.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var yarticleCode=articledata[i * 5];
                            var yarticleDesc=articledata[(i * 5) + 1];
                            var ymcCode=articledata[(i * 5) + 2];
                            var ymcDesc=articledata[(i * 5) + 3];
                            var yqty=articledata[(i * 5) + 4];
                            if(yarticleCode!=undefined && yarticleCode.length>0){
                                $("#YarticleGrid").jqGrid('addRowData',indexsety,{yartNo:yarticleCode,yartDesc:yarticleDesc,ymcCode:ymcCode,ymcDesc:ymcDesc,yqty:yqty});
                                indexsety++;
                            }
                        }
                    }
                    if(hdnZManualGridSet5!=undefined && hdnZManualGridSet5.length>0 && hdnZManualGridSet5!=null){
                        var indexsetz=0;
                        var articledata=hdnZManualGridSet5.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var zarticleCode=articledata[i * 5];
                            var zarticleDesc=articledata[(i * 5) + 1];
                            var zmcCode=articledata[(i * 5) + 2];
                            var zmcDesc=articledata[(i * 5) + 3];
                            var zqty=articledata[(i * 5) + 4];
                            if(zarticleCode!=undefined && zarticleCode.length>0){
                                $("#ZarticleGrid").jqGrid('addRowData',indexsetz,{zartNo:zarticleCode,zartDesc:zarticleDesc,zmcCode:zmcCode,zmcDesc:zmcDesc,zqty:zqty});
                                indexsetz++;
                            }
                        }
                    }
                    if(hdnSet4ManualGridData!=undefined && hdnSet4ManualGridData.length>0 && hdnSet4ManualGridData!=null){
                        var indexset4=0;
                        var articledata=hdnSet4ManualGridData.split(",");
                        for(var i=0;i<articledata.length/5;i++){
                            var set4articleCode=articledata[i * 5];
                            var set4articleDesc=articledata[(i * 5) + 1];
                            var set4mcCode=articledata[(i * 5) + 2];
                            var set4mcDesc=articledata[(i * 5) + 3];
                            var set4qty=articledata[(i * 5) + 4];
                            if(set4articleCode!=undefined && set4articleCode.length>0){
                                $("#Set4articleGrid").jqGrid('addRowData',indexset4,{set4artNo:set4articleCode,set4artDesc:set4articleDesc,set4mcCode:set4mcCode,set4mcDesc:set4mcDesc,set4qty:set4qty});
                                indexset4++;
                            }
                        }
                    }
                }

                function getYArticleData(){

                    var articleGridIds = jQuery("#YarticleGrid").getDataIDs();
                    if(articleGridIds.length > 0){
                        //alert("Total Qual Grid Data = "+qualGridIds);
                        var tempIndex=0;
                        var arr=new Array();

                        for(var index=0;index<=articleGridIds.length;index++){
                            //Columns Values!!
                            var rowData=jQuery('#YarticleGrid').getRowData(articleGridIds[index]);
                            arr[tempIndex]=rowData.yartNo;
                            arr[++tempIndex]=rowData.yartDesc;
                            arr[++tempIndex]=rowData.ymcCode;
                            arr[++tempIndex]=rowData.ymcDesc;
                            arr[++tempIndex]=rowData.yqty;
                            tempIndex++;
                        }
                        // alert("Qual arr : "+arr);
                        $("#YarticleGridData").val(arr);
                    }
                }
                function getZArticleData(){
                    var articleGridIds = jQuery("#ZarticleGrid").getDataIDs();
                    if(articleGridIds.length > 0){
                        //alert("Total Qual Grid Data = "+qualGridIds);
                        var tempIndex=0;
                        var arr=new Array();

                        for(var index=0;index<=articleGridIds.length;index++){
                            //Columns Values!!
                            var rowData=jQuery('#ZarticleGrid').getRowData(articleGridIds[index]);
                            arr[tempIndex]=rowData.zartNo;
                            arr[++tempIndex]=rowData.zartDesc;
                            arr[++tempIndex]=rowData.zmcCode;
                            arr[++tempIndex]=rowData.zmcDesc;
                            arr[++tempIndex]=rowData.zqty;
                            tempIndex++;
                        }
                        // alert("Qual arr : "+arr);
                        $("#ZarticleGridData").val(arr);
                    }
                }
                function getSet4ArticleData(){

                    var articleGridIds = jQuery("#Set4articleGrid").getDataIDs();
                    if(articleGridIds.length > 0){
                        //alert("Total Qual Grid Data = "+qualGridIds);
                        var tempIndex=0;
                        var arr=new Array();

                        for(var index=0;index<=articleGridIds.length;index++){
                            //Columns Values!!
                            var rowData=jQuery('#Set4articleGrid').getRowData(articleGridIds[index]);
                            arr[tempIndex]=rowData.set4artNo;
                            arr[++tempIndex]=rowData.set4artDesc;
                            arr[++tempIndex]=rowData.set4mcCode;
                            arr[++tempIndex]=rowData.set4mcDesc;
                            arr[++tempIndex]=rowData.set4qty;
                            tempIndex++;
                        }
                        // alert("Qual arr : "+arr);
                        $("#manualSet4GridData").val(arr);
                    }
                }

                function getSet5ArticleData(){
                    var articleGridIds = jQuery("#Set5articleGrid").getDataIDs();
                    if(articleGridIds.length > 0){
                        //alert("Total Qual Grid Data = "+qualGridIds);
                        var tempIndex=0;
                        var arr=new Array();

                        for(var index=0;index<=articleGridIds.length;index++){
                            //Columns Values!!
                            var rowData=jQuery('#Set5articleGrid').getRowData(articleGridIds[index]);
                            arr[tempIndex]=rowData.set5artNo;
                            arr[++tempIndex]=rowData.set5artDesc;
                            arr[++tempIndex]=rowData.set5mcCode;
                            arr[++tempIndex]=rowData.set5mcDesc;
                            arr[++tempIndex]=rowData.set5qty;
                            tempIndex++;
                        }
                        // alert("Qual arr : "+arr);
                        $("#manualSet5GridData").val(arr);
                    }
                }


                function resetAllFeilds(){
                    $("#DisConfig").val("-1");
                    $("#txtdisValue").val();
                    $("#txtBXGYmargin").val();
                    $("#txtBXGYgrowth").val();
                    $("#txtBXGYsellQty").val();
                    $("#txtBXGYgrowthConver").val();
                    $("#txtBXGYsalegrowth").val();
                    $("#txtBXGYFrom").val();
                    $("#txtBXGYTo").val();
                    $("#msg").val();
                }

            });
        </script>



    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />

        <!--            <div id="middle_cont">-->
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">
            <tr>
                <td><h1>Buy x Get Y Set Level Discount</h1></td>
            </tr>
            <tr><td height="15px"></td></tr>
            <tr>
                <td width="100%" align="center" >
                    <div id="msg">
                        <s:actionmessage cssClass="successText"/>
                        <s:actionerror cssClass="errorText"/>
                    </div>
                </td>
            </tr>
            <tr><td height="15px"></td></tr>
            <tr>
                <td>
                    <table id="" width="90%" border="0" align="center" cellpadding="2" cellspacing="4">
                        <tr>
                            <td  align="center">
                                <table id="reqGrid"></table>
                                <div id="reqPager"></div>
                            </td>
                            <td align="right" style="display: none">
                                <table id="transreqGrid"></table>
                                <div id="transreqPager"></div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td>
                    <table id="BuyXGetYTable" width="90%" border="0" align="center" cellpadding="2" cellspacing="4">
                        <tr><td height="5px"></td></tr>
                        <tr>
                            <td align="center">Group <select name="selGroup" id="selGroup" class="dropdown">
                                    <OPTION value="-1">----- Select Group -----</OPTION>
                                    <OPTION value="0">Set 2</OPTION>
                                    <OPTION value="1">Set 3</OPTION>
                                    <OPTION value="2">Set 4</OPTION>
                                    <OPTION value="3">Set 5</OPTION>
                                </select></td>
                        </tr>
                        <tr>
                            <td align="left">
                                <table width="60%" border="0" align="left" cellpadding="2" cellspacing="4">
                                    <tr>
                                        <td align="left"><b>Set 1</b></td>
                                        <td align="left">Capture Article</td>
                                        <td align="right">Manual Entry</td>
                                        <td align="left">
                                            <input type="radio" id="rbtnManualBX" name="xarticle" checked="true" />
                                        </td>
                                        <td align="right">Upload Article</td>
                                        <td align="left">
                                            <input type="radio" id="rbtnUploadBX" name="xarticle" />
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
                                                        <s:form id="xcreateManualProposal" action="" method="POST">

                                                            <tr>
                                                                <td align="right">Article Number</td>
                                                                <td align="left"><input type="text" name="txtBXArticleNo" id="txtBXArticleNo" /></td>
                                                                <td align="left">
                                                                    <input type="button" name="btnValidateXArticle" id="btnValidateXArticle" value="Validate"/>
                                                                </td>
                                                                <td align="right">MC Code</td>
                                                                <td align="left"><input type="text" name="txtBXMCCode" id="txtXMCCode" /></td>


                                                            </tr>
                                                            <tr>
                                                                <td align="right">Article Description</td>
                                                                <td align="left"><input type="text" name="txtBXArticleDesc" id="txtBXArticleDesc" /></td>
                                                                <td align="left">
                                                                    <input type="button" name="btnAddXArticle" id="btnAddXArticle" value="Add"/>
                                                                </td>
                                                                <td align="right">MC Description</td>
                                                                <td align="left"><input type="text" name="txtBXMCDesc" id="txtXMCDesc" /></td>

                                                            </tr>
                                                            <tr>
                                                                <td align="right">Qty</td>
                                                                <td align="left"><input type="text" name="txtXQty" id="txtXQty" /></td>
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
                                                        <s:form id="xuploadFile"  action="uploadbxgyxarticle_action" method="post" enctype="multipart/form-data">
                                                            <s:hidden id="isXuploaderror" name="createBXGYFormVO.isXuploaderror" value="%{createBXGYFormVO.isXuploaderror}" />
                                                            <s:hidden id="isMstPromoIdX" name="createBXGYFormVO.isMstPromoIdX" value="%{createBXGYFormVO.isMstPromoIdX}" />
                                                            <s:hidden id="selGroupX" name="createBXGYFormVO.selGroupX" value="%{createBXGYFormVO.selGroupX}" />
                                                            <s:hidden id="hdnYManualGridX" name="createBXGYFormVO.hdnYManualGridX " value="%{createBXGYFormVO.hdnYManualGridX}" />
                                                            <s:hidden id="hdnZManualGridX" name="createBXGYFormVO.hdnZManualGridX " value="%{createBXGYFormVO.hdnZManualGridX}" />
                                                            <s:hidden id="hdnSet4ManualGridX" name="createBXGYFormVO.hdnSet4ManualGridX " value="%{createBXGYFormVO.hdnSet4ManualGridX}" />
                                                            <s:hidden id="hdnSet5ManualGridX" name="createBXGYFormVO.hdnSet5ManualGridX " value="%{createBXGYFormVO.hdnSet5ManualGridX}" />
                                                            <tr>
                                                                <td width="2%"></td>
                                                                <td align="right">Upload File</td>
                                                                <td align="left">
                                                                    <s:file id ="BXarticleFile" name="createBXGYFormVO.BXarticleUploadFile" ></s:file>
                                                                </td>
                                                                <td align="center">
                                                                    <input  type="submit" id="btnBXUpload" name="btnBXUpload"  Value="Upload" />
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td width="2%"></td>
                                                                <td align="right">Download</td>
                                                                <td align="center">
                                                                    <a href ="downloadIntiationSampleArticleMCFile" class="download-sample " id="downloadSample">
                                                                        Sample File
                                                                    </a>
                                                                </td>
                                                                <td align="center" id="tdXErrorFile">
                                                                    <s:a  href="%{createBXGYFormVO.errorfilePath}" class="downloadError" id="downloadErrorFile" > Status File </s:a>
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
                        <tr><td height="5px"></td></tr>
                        <tr>
                            <td align="left">
                                <table width="60%" border="0" align="left" cellpadding="2" cellspacing="4">
                                    <tr>
                                        <td align="left"><b>Set 2</b></td>
                                        <td align="left">Capture Article</td>
                                        <td align="right">Manual Entry</td>
                                        <td align="left">
                                            <input type="radio" id="rbtnManualGY" name="yarticle" checked="true" />
                                        </td>
                                        <td align="right">Upload Article</td>
                                        <td align="left">
                                            <input type="radio" id="rbtnUploadGY" name="yarticle"  />
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
                                                        <s:form id="ycreateManualProposal" action="" method="POST">
                                                            <tr>
                                                                <td align="right">Article Number</td>
                                                                <td align="left"><input type="text" name="txtGYArticleNo" id="txtGYArticleNo" /></td>
                                                                <td align="left">
                                                                    <input type="button" name="btnValidateYArticle" id="btnValidateYArticle" value="Validate"/>
                                                                </td>
                                                                <td align="right">MC Code</td>
                                                                <td align="left"><input type="text" name="txtGYMCCode" id="txtGYMCCode" /></td>

                                                            </tr>
                                                            <tr>
                                                                <td align="right">Article Description</td>
                                                                <td align="left"><input type="text" name="txtGYArticleDesc" id="txtGYArticleDesc" /></td>

                                                                <td align="left">
                                                                    <input type="button" name="btnAddGYArticle" id="btnAddGYArticle" value="Add"/>
                                                                </td>
                                                                <td align="right">MC Description</td>
                                                                <td align="left"><input type="text" name="txtGYMCDesc" id="txtGYMCDesc" /></td>

                                                            </tr>
                                                            <tr>
                                                                <td align="right">Qty</td>
                                                                <td align="left"><input type="text" name="txtYQty" id="txtYQty" /></td>
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
                                                        <s:form id="yuploadFile"  action="uploadbxgyYarticle_action" method="post" enctype="multipart/form-data">
                                                            <s:hidden id="isYuploaderror" name="createBXGYFormVO.isYuploaderror" value="%{createBXGYFormVO.isYuploaderror}" />
                                                            <s:hidden id="isMstPromoIdY" name="createBXGYFormVO.isMstPromoIdY" value="%{createBXGYFormVO.isMstPromoIdY}" />
                                                            <s:hidden id="selGroupY" name="createBXGYFormVO.selGroupY" value="%{createBXGYFormVO.selGroupY}" />
                                                            <s:hidden id="hdnXManualGridY" name="createBXGYFormVO.hdnXManualGridY " value="%{createBXGYFormVO.hdnXManualGridY}" />
                                                            <s:hidden id="hdnZManualGridY" name="createBXGYFormVO.hdnZManualGridY " value="%{createBXGYFormVO.hdnZManualGridY}" />
                                                            <s:hidden id="hdnSet4ManualGridY" name="createBXGYFormVO.hdnSet4ManualGridY " value="%{createBXGYFormVO.hdnSet4ManualGridY}" />
                                                            <s:hidden id="hdnSet5ManualGridY" name="createBXGYFormVO.hdnSet5ManualGridY " value="%{createBXGYFormVO.hdnSet5ManualGridY}" />
                                                            <tr>
                                                                <td width="2%"></td>
                                                                <td align="right">Upload File</td>
                                                                <td align="left">
                                                                    <s:file id ="GYarticleFile" name="createBXGYFormVO.GYarticleUploadFile" ></s:file>
                                                                </td>
                                                                <td align="center">
                                                                    <input  type="submit" id="btnGYUpload" name="btnGYUpload"  Value="Upload" />
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td width="2%"></td>
                                                                <td align="right">Download</td>
                                                                <td align="center">
                                                                    <a href ="downloadIntiationSampleArticleMCFile" class="download-sample " id="downloadSample">
                                                                        Sample File
                                                                    </a>
                                                                </td>
                                                                <td align="center" id="tdYErrorFile">
                                                                    <s:a  href="%{createBXGYFormVO.errorfilePath}" class="downloadError" id="downloadErrorFile" > Status File </s:a>
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
                        <tr id="trZ">
                            <td align="left">
                                <table width="60%" border="0" align="left" cellpadding="2" cellspacing="4">
                                    <tr>
                                        <td align="left"><b>Set 3</b></td>
                                        <td align="left">Capture Article</td>
                                        <td align="right">Manual Entry</td>
                                        <td align="left">
                                            <input type="radio" id="rbtnManualBZ" name="zarticle" checked="true" />
                                        </td>
                                        <td align="right">Upload Article</td>
                                        <td align="left">
                                            <input type="radio" id="rbtnUploadBZ" name="zarticle" />
                                        </td>

                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr><td height="5px"></td></tr>
                        <tr id="trz1">
                            <td align="left">
                                <table width="95%" border="0" align="center" cellpadding="2" cellspacing="4">
                                    <tr>
                                        <td >
                                            <div id="BZtabMaster" class="tabbed_box">
                                                <ul class="tabs">
                                                    <li><a href="#" id="BZ_tab_1" class="active" onclick="return false;">Manual Article Entry</a></li>
                                                    <li><a href="#" id="BZ_tab_2" onclick="return false;">Upload Article File</a></li>
                                                </ul>
                                                <div id="BZ_sub_content_1" class="content" >
                                                    <table id="BZ_manualTable" width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                        <s:form id="zcreateManualProposal" action="" method="POST">

                                                            <tr>
                                                                <td align="right">Article Number</td>
                                                                <td align="left"><input type="text" name="txtBZArticleNo" id="txtBZArticleNo" /></td>
                                                                <td align="left">
                                                                    <input type="button" name="btnValidateZArticle" id="btnValidateZArticle" value="Validate"/>
                                                                </td>
                                                                <td align="right">MC Code</td>
                                                                <td align="left"><input type="text" name="txtZMCCode" id="txtZMCCode" /></td>


                                                            </tr>
                                                            <tr><td align="right">Article Description</td>
                                                                <td align="left"><input type="text" name="txtBZArticleDesc" id="txtBZArticleDesc" /></td>
                                                                <td align="left">
                                                                    <input type="button" name="btnAddZArticle" id="btnAddZArticle" value="Add"/>
                                                                </td>
                                                                <td align="right">MC Description</td>
                                                                <td align="left"><input type="text" name="txtZMCDesc" id="txtZMCDesc" /></td>

                                                            </tr>
                                                            <tr>
                                                                <td align="right">Qty</td>
                                                                <td align="left"><input type="text" name="txtZQty" id="txtZQty" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="5" align="center">
                                                                    <table id="ZarticleGrid"></table>
                                                                    <div id="ZarticlePager"></div>
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
                                                <div id="BZ_sub_content_2" class="content">
                                                    <table id="BZ_uploadTable"  align="center" cellpadding="2" cellspacing="4">
                                                        <s:form id="zuploadFile"  action="uploadbxgyZarticle_action" method="post" enctype="multipart/form-data">
                                                            <s:hidden id="isZuploaderror" name="createBXGYFormVO.isZuploaderror" value="%{createBXGYFormVO.isZuploaderror}" />
                                                            <s:hidden id="isMstPromoIdZ" name="createBXGYFormVO.isMstPromoIdZ" value="%{createBXGYFormVO.isMstPromoIdZ}" />
                                                            <s:hidden id="selGroupZ" name="createBXGYFormVO.selGroupZ" value="%{createBXGYFormVO.selGroupZ}" />
                                                            <s:hidden id="hdnXManualGridZ" name="createBXGYFormVO.hdnXManualGridZ " value="%{createBXGYFormVO.hdnXManualGridZ}" />
                                                            <s:hidden id="hdnYManualGridZ" name="createBXGYFormVO.hdnYManualGridZ " value="%{createBXGYFormVO.hdnYManualGridZ}" />
                                                            <s:hidden id="hdnSet4ManualGridZ" name="createBXGYFormVO.hdnSet4ManualGridZ " value="%{createBXGYFormVO.hdnSet4ManualGridZ}" />
                                                            <s:hidden id="hdnSet5ManualGridZ" name="createBXGYFormVO.hdnSet5ManualGridZ " value="%{createBXGYFormVO.hdnSet5ManualGridZ}" />
                                                            <tr>
                                                                <td width="2%"></td>
                                                                <td align="right">Upload File</td>
                                                                <td align="left">
                                                                    <s:file id ="BZarticleFile" name="createBXGYFormVO.BZarticleUploadFile" ></s:file>
                                                                </td>
                                                                <td align="center">
                                                                    <input  type="submit" id="btnBZUpload" name="btnBZUpload"  Value="Upload" />
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td width="2%"></td>
                                                                <td align="right">Download</td>
                                                                <td align="center">
                                                                    <a href ="downloadIntiationSampleArticleMCFile" class="download-sample " id="downloadSample">
                                                                        Sample File
                                                                    </a>
                                                                </td>
                                                                <td align="center" id="tdZErrorFile">
                                                                    <s:a  href="%{createBXGYFormVO.errorfilePath}" class="downloadError" id="downloadErrorFile" > Status File </s:a>
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

                        <tr id="trset4">
                            <td align="left">
                                <table width="60%" border="0" align="left" cellpadding="2" cellspacing="4">
                                    <tr>
                                        <td align="left"><b>Set 4</b></td>
                                        <td align="left">Capture Article</td>
                                        <td align="right">Manual Entry</td>
                                        <td align="left">
                                            <input type="radio" id="rbtnManualSet4" name="set4" checked="true"/>
                                        </td>
                                        <td align="right">Upload Article</td>
                                        <td align="left">
                                            <input type="radio" id="rbtnUploadSet4" name="set4" />
                                        </td>

                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr><td height="5px"></td></tr>
                        <tr id="trset41">
                            <td align="left">
                                <table width="95%" border="0" align="center" cellpadding="2" cellspacing="4">
                                    <tr>
                                        <td >
                                            <div id="Set4tabMaster" class="tabbed_box">
                                                <ul class="tabs">
                                                    <li><a href="#" id="Set4_tab_1" class="active" onclick="return false;">Manual Article Entry</a></li>
                                                    <li><a href="#" id="Set4_tab_2" onclick="return false;">Upload Article File</a></li>
                                                </ul>
                                                <div id="Set4_sub_content_1" class="content" >
                                                    <table id="Set4_manualTable" width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                        <s:form id="zcreateManualProposal" action="" method="POST">
                                                            <tr>
                                                                <td align="right">Article Number</td>
                                                                <td align="left"><input type="text" name="txtSet4ArticleNo" id="txtSet4ArticleNo" /></td>
                                                                <td align="left">
                                                                    <input type="button" name="btnValidateSet4Article" id="btnValidateSet4Article" value="Validate"/>
                                                                </td>
                                                                <td align="right">MC Code</td>
                                                                <td align="left"><input type="text" name="txtSet4MCCode" id="txtSet4MCCode" /></td>


                                                            </tr>
                                                            <tr><td align="right">Article Description</td>
                                                                <td align="left"><input type="text" name="txtSet4ArticleDesc" id="txtSet4ArticleDesc" /></td>
                                                                <td align="left">
                                                                    <input type="button" name="btnAddSet4Article" id="btnAddSet4Article" value="Add"/>
                                                                </td>
                                                                <td align="right">MC Description</td>
                                                                <td align="left"><input type="text" name="txtSet4MCDesc" id="txtSet4MCDesc" /></td>

                                                            </tr>
                                                            <tr>
                                                                <td align="right">Qty</td>
                                                                <td align="left"><input type="text" name="txtSet4Qty" id="txtSet4Qty" /></td>
                                                            </tr>
                                                            <tr>
                                                                <td colspan="5" align="center">
                                                                    <table id="Set4articleGrid"></table>
                                                                    <div id="Set4articlePager"></div>
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
                                                <div id="Set4_sub_content_2" class="content">
                                                    <table id="Set4_uploadTable"  align="center" cellpadding="2" cellspacing="4">
                                                        <s:form id="Set4uploadFile"  action="uploadbxgySet4article_action" method="post" enctype="multipart/form-data">
                                                            <s:hidden id="isSet4uploaderror" name="createBXGYFormVO.isSet4uploaderror" value="%{createBXGYFormVO.isSet4uploaderror}" />
                                                            <s:hidden id="isMstPromoIdSet4" name="createBXGYFormVO.isMstPromoIdSet4" value="%{createBXGYFormVO.isMstPromoIdSet4}" />
                                                            <s:hidden id="selGroupSet4" name="createBXGYFormVO.selGroupSet4" value="%{createBXGYFormVO.selGroupSet4}" />
                                                            <s:hidden id="hdnXManualGridSet4" name="createBXGYFormVO.hdnXManualGridSet4 " value="%{createBXGYFormVO.hdnXManualGridSet4}" />
                                                            <s:hidden id="hdnYManualGridSet4" name="createBXGYFormVO.hdnYManualGridSet4 " value="%{createBXGYFormVO.hdnYManualGridSet4}" />
                                                            <s:hidden id="hdnZManualGridSet4" name="createBXGYFormVO.hdnZManualGridSet4 " value="%{createBXGYFormVO.hdnZManualGridSet4}" />
                                                            <s:hidden id="hdnSet5ManualGridSet4" name="createBXGYFormVO.hdnSet5ManualGridSet4 " value="%{createBXGYFormVO.hdnSet5ManualGridSet4}" />
                                                            <tr>
                                                                <td width="2%"></td>
                                                                <td align="right">Upload File</td>
                                                                <td align="left">
                                                                    <s:file id ="Set4articleFile" name="createBXGYFormVO.set4articleUploadFile" ></s:file>
                                                                </td>
                                                                <td align="center">
                                                                    <input  type="submit" id="btnSet4Upload" name="btnSet4Upload"  Value="Upload" />
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td width="2%"></td>
                                                                <td align="right">Download</td>
                                                                <td align="center">
                                                                    <a href ="downloadIntiationSampleArticleMCFile" class="download-sample " id="downloadSample">
                                                                        Sample File
                                                                    </a>
                                                                </td>
                                                                <td align="center" id="tdSet4ErrorFile">
                                                                    <s:a  href="%{createBXGYFormVO.errorfilePath}" class="downloadError" id="downloadErrorFile" > Status File </s:a>
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

                        <tr id="trset5">
                            <td align="left">
                                <table width="60%" border="0" align="left" cellpadding="2" cellspacing="4">
                                    <tr>
                                        <td align="left"><b>Set 5</b></td>
                                        <td align="left">Capture Article</td>
                                        <td align="right">Manual Entry</td>
                                        <td align="left">
                                            <input type="radio" id="rbtnManualSet5" name="set5" checked="true"/>
                                        </td>
                                        <td align="right">Upload Article</td>
                                        <td align="left">
                                            <input type="radio" id="rbtnUploadSet5" name="set5" />
                                        </td>

                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr><td height="5px"></td></tr>
                        <tr id="trset51">
                            <td align="left">
                                <table width="95%" border="0" align="center" cellpadding="2" cellspacing="4">
                                    <tr>
                                        <td >
                                            <div id="Set5tabMaster" class="tabbed_box">
                                                <ul class="tabs">
                                                    <li><a href="#" id="Set5_tab_1" class="active" onclick="return false;">Manual Article Entry</a></li>
                                                    <li><a href="#" id="Set5_tab_2" onclick="return false;">Upload Article File</a></li>
                                                </ul>
                                                <div id="Set5_sub_content_1" class="content" >
                                                    <table id="Set5_manualTable" width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                        <tr>
                                                            <td align="right">Article Number</td>
                                                            <td align="left"><input type="text" name="txtSet5ArticleNo" id="txtSet5ArticleNo" /></td>
                                                            <td align="left">
                                                                <input type="button" name="btnValidateSet5Article" id="btnValidateSet5Article" value="Validate"/>
                                                            </td>
                                                            <td align="right">MC Code</td>
                                                            <td align="left"><input type="text" name="txtSet5MCCode" id="txtSet5MCCode" /></td>


                                                        </tr>
                                                        <tr><td align="right">Article Description</td>
                                                            <td align="left"><input type="text" name="txtSet5ArticleDesc" id="txtSet5ArticleDesc" /></td>
                                                            <td align="left">
                                                                <input type="button" name="btnAddSet5Article" id="btnAddSet5Article" value="Add"/>
                                                            </td>
                                                            <td align="right">MC Description</td>
                                                            <td align="left"><input type="text" name="txtSet5MCDesc" id="txtSet5MCDesc" /></td>

                                                        </tr>
                                                        <tr>
                                                            <td align="right">Qty</td>
                                                            <td align="left"><input type="text" name="txtSet5Qty" id="txtSet5Qty" /></td>
                                                        </tr>
                                                        <tr>
                                                            <td colspan="5" align="center">
                                                                <table id="Set5articleGrid"></table>
                                                                <div id="Set5articlePager"></div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td align="center" colspan="5">
                                                                <a href ="#" class="download-sample " onclick="tb_show( '', 'viewArticleMCSearch_action?height=150&width=520');">
                                                                    Article Search
                                                                </a>
                                                            </td>
                                                        </tr>
                                                    </table>
                                                </div>
                                                <div id="Set5_sub_content_2" class="content">
                                                    <table id="Set5_uploadTable"  align="center" cellpadding="2" cellspacing="4">
                                                        <s:form id="Set5uploadFile"  action="uploadbxgySet5article_action" method="post" enctype="multipart/form-data">
                                                            <s:hidden id="isSet5uploaderror" name="createBXGYFormVO.isSet5uploaderror" value="%{createBXGYFormVO.isSet5uploaderror}" />
                                                            <s:hidden id="isMstPromoIdSet5" name="createBXGYFormVO.isMstPromoIdSet5" value="%{createBXGYFormVO.isMstPromoIdSet5}" />
                                                            <s:hidden id="selGroupSet5" name="createBXGYFormVO.selGroupSet5" value="%{createBXGYFormVO.selGroupSet5}" />
                                                            <s:hidden id="hdnXManualGridSet5" name="createBXGYFormVO.hdnXManualGridSet5 " value="%{createBXGYFormVO.hdnXManualGridSet5}" />
                                                            <s:hidden id="hdnYManualGridSet5" name="createBXGYFormVO.hdnYManualGridSet5 " value="%{createBXGYFormVO.hdnYManualGridSet5}" />
                                                            <s:hidden id="hdnZManualGridSet5" name="createBXGYFormVO.hdnZManualGridSet5 " value="%{createBXGYFormVO.hdnZManualGridSet5}" />
                                                            <s:hidden id="hdnSet4ManualGridSet5" name="createBXGYFormVO.hdnSet4ManualGridSet5 " value="%{createBXGYFormVO.hdnSet4ManualGridSet5}" />
                                                            <tr>
                                                                <td width="2%"></td>
                                                                <td align="right">Upload File</td>
                                                                <td align="left">
                                                                    <s:file id ="Set5articleFile" name="createBXGYFormVO.set5articleUploadFile" ></s:file>
                                                                </td>
                                                                <td align="center">
                                                                    <input  type="submit" id="btnSet5Upload" name="btnSet5Upload"  Value="Upload" />
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <td width="2%"></td>
                                                                <td align="right">Download</td>
                                                                <td align="center">
                                                                    <a href ="downloadIntiationSampleArticleMCFile" class="download-sample " id="downloadSample">
                                                                        Sample File
                                                                    </a>
                                                                </td>
                                                                <td align="center" id="tdSet5ErrorFile">
                                                                    <s:a  href="%{createBXGYFormVO.errorfilePath}" class="downloadError" id="downloadErrorFile" > Status File </s:a>
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
                        <!--  Main detail Form  -->

                        <s:form id="otherdtlsubmit"  action="donothing">
                            <tr>
                                <td align="center">
                                    <table width="60%" border="0" align="center" cellpadding="2" cellspacing="4">
                                        <tr>
                                            <td>Set</td>
                                            <td> <select name="selSet" id="selSet" >
                                                    <OPTION value="-1">----- Select Set -----</OPTION>
                                                    <OPTION value="1">Set 1</OPTION>
                                                    <OPTION value="2">Set 2</OPTION>
                                                    <OPTION value="3">Set 3</OPTION>
                                                    <OPTION value="4">Set 4</OPTION>
                                                    <OPTION value="5">Set 5</OPTION>
                                                </select> </td>
                                            <td>Discount Config   </td>
                                            <td> <select name="createBXGYFormVO.DisConfig" id="DisConfig" class="dropdown">
                                                    <OPTION value="-1">----- Select Discount -----</OPTION>
                                                    <OPTION value="0">Value off</OPTION>
                                                    <OPTION value="1">Percentage Off</OPTION>
                                                    <OPTION value="2">Flat Discount</OPTION>
                                                </select> </td>
                                            <td>Value</td>
                                            <td><input type="text" id="txtdisValue" name="createBXGYFormVO.txtdisValue"/> </td>
                                            <td><input type="button" id="addDisc" name="addDisc" value="Add"/> </td>

                                        </tr>
                                        <tr>
                                            <td colspan="6" align="center">
                                                <table id="discountgrid"></table>
                                                <div id="discountgridPg"></div>
                                            </td>
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
                                        <tr>
                                            <td>Remarks</td>
                                            <td ><textarea id="txtremarks" name="createBXGYFormVO.txtRemarks"  rows="3" cols="25" style="width: 70%;height: 20%"></textarea></td>
                                        </tr>
                                        <tr>
                                            <td align="center">
                                                <table width="35%">
                                                    <tr>
                                                        <td align="center">
                                                            <s:hidden id="promoreqID" name="createBXGYFormVO.promoreqID" />
                                                            <input type="hidden" name="createBXGYFormVO.isYManualEntry" id="isYManualEntry" />
                                                            <input type="hidden" name="createBXGYFormVO.manualYArticleData" id="YarticleGridData" />
                                                            <input type="hidden" name="createBXGYFormVO.isXManualEntry" id="isXManualEntry" />
                                                            <input type="hidden" name="createBXGYFormVO.manualXArticleData" id="XarticleGridData" />
                                                            <s:hidden  name="createBXGYFormVO.isInitiatorRedirect" id="isInitiatorRedirect" value="%{createBXGYFormVO.isInitiatorRedirect}" />
                                                            <s:hidden  name="createBXGYFormVO.SessionmstPromoId" id="SessionmstPromoId" value="%{createBXGYFormVO.SessionmstPromoId}" />

                                                            <input type="hidden" name="createBXGYFormVO.isZManualEntry" id="isZManualEntry" />
                                                            <input type="hidden" name="createBXGYFormVO.ZarticleGridData" id="ZarticleGridData" />

                                                            <input type="hidden" name="createBXGYFormVO.isSet4ManualEntry" id="isSet4ManualEntry" />
                                                            <input type="hidden" name="createBXGYFormVO.manualSet4GridData" id="manualSet4GridData" />
                                                            <input type="hidden" name="createBXGYFormVO.isSet5ManualEntry" id="isSet5ManualEntry" />
                                                            <input type="hidden" name="createBXGYFormVO.manualSet5GridData" id="manualSet5GridData" />
                                                            <input type="hidden" name="createBXGYFormVO.manualdiscGridData" id="manualdiscGridData" />

                                                            <s:submit align="center" action="submitBXGXDtl" id="btnSave" name="btnSave" value="Save" cssClass="btn"/>
                                                            <!--                                                            <input align="left" type="submit" id="btnSave" name="btnSave"  Value="Save" Class="btn" />-->
                                                        </td>
                                                        <!--                                                        <td align="center">
                                                                                                                    <input align="left" type="submit" id="btnBXGYSaveNext" name="btnBXGYSaveNext"  Value="Save & Next" Class="btn" />
                                                                                                                </td>-->
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </s:form>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
