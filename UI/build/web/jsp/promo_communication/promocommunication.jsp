<%-- 
    Document   : promocommunication
    Created on : Jan 11, 2013, 10:10:38 AM
    Author     : krutij
--%>

<%@page import="com.fks.ui.constants.WebConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Promotion Communication</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <script src="js/jquery-1.6.4.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
        <link href="css/thickbox.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="js/thickbox.js"></script>
        <script src="js/jquery.ui.position.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>
        <script type="text/javascript">
            var promoReqId;
            var  idsOfSelectedRows = [];
            $(function () {
                jQuery("#commGrid").jqGrid({
                    url:"getCommunicationGrid?eventId="+"0"+"&searchType="+"ZONE_DATE_DEFAULT",
                    datatype: 'json',
                    width: 1100,
                    height:230,
                    colNames:['Request ID','Campaign','Site','City','State','Region','Zone','Promo Type','Promo Detail','Category','Sub Category', 'Valid from','Valid To','Cashier Trigger','Bonus Buy','Article/MC','Download'],
                    colModel:[
                        {name:'reqId',index:'reqId', width:200, align:"center"},
                        {name:'event',index:'event', width:200, align:"center"},
                        {name:'site',index:'site', width:200, align:"center"},
                        {name:'city',index:'city', width:200, align:"center"},
                        {name:'state',index:'state', width:200, align:"center"},
                        {name:'region',index:'region', width:200, align:"center"},
                        {name:'zone',index:'zone', width:200, align:"center"},
                        {name:'promotype',index:'promotype', width:200, align:"center"},
                        {name:'reqName',index:'reqName', width:200, align:"center"},
                        {name:'category',index:'category', width:200, align:"center"},
                        {name:'subcategory',index:'subcategory', width:200, align:"center"},
                        {name:'validfrom',index:'validfrom', width:200, align:"center"},
                        {name:'validto',index:'validto', width:200, align:"center"},
                        {name:'cachTriCode',index:'cachTriCode', width:200, align:"center"},
                        {name:'bonusBuy',index:'bonusBuy', width:200, align:"center"},
                        {name:'downloadarticle',index:'downloadarticle', width:200, align:"center",hidden:true},
                        {name:'download',index:'download', width:200, align:"center",hidden:true},

                    ],
                    rowNum:30,
                    rowList:[30],
                    viewrecords: true,
                    headertitles: true,
                    pager: '#commPg',
                    //emptyrecords:'',
                    //recordtext:'',
                    multiselect: true,
                    //loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:"",
                    onSelectRow: function(id,isSelected) {
                        promoReqId=id;
                        gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);                        
                        // alert(id);
                    }
                    ,onSelectAll: function (aRowids, isSelected) {
                        var i, id;
                        for (i = 0; i < aRowids.length; i++) {
                            id = aRowids[i];
                            gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);
                        }                        
                    }
                    ,loadComplete: function () {
                        var $this = $(this), i, count;
                        for (i = 0, count = idsOfSelectedRows.length; i < count; i++) {
                            $this.jqGrid('setSelection', idsOfSelectedRows[i], false);
                        }
                    }
                    //                    ,gridComplete:function(){
                    //                        $("#cb_"+$("#commGrid")[0].id).hide();
                    //                    }
                });
            });

            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });
                loadControllOnPageLoad();

                              
                $("#categorySel").change(function(){
                    var category=$("#categorySel option:selected").val();
                    $('#listmch').empty();
                    $('#listTomch').empty();
                    if(category !=-1){
                        $.ajax({
                            url: "getMchCategoryWise?categoryname="+category,
                            cache:false,
                            global: false,
                            type: "POST",
                            dataType:"json",
                            contanttype: 'text/json',
                            async:false,
                            error:function(){
                                alert("Can not connect to server");
                            },
                            success: function(data){
                                $("#trSubCategory").show();
                                $('#trAddArticle').hide();
                                $('#trArticleList').hide();
                                //  alert(data);
                                $('#subcategorySel option').each(function(ik, option){
                                    $(option).remove();
                                });

                                var select = document.getElementById("subcategorySel");
                                var newoption = document.createElement('option');
                                newoption.text = "---Select Sub Category---";
                                newoption.value = -1;
                                select.add(newoption);

                                if(data!=null){
                                    for(var i=0; i<data.rows.subcategroylist.length ; i++){
                                        var optn = document.createElement("OPTION")
                                        optn.text = data.rows.subcategroylist[i];
                                        optn.value = data.rows.subcategroylist[i];
                                        select.add(optn);
                                    }
                                }

                                $('#listmch option').each(function(ik, option){
                                    $(option).remove();
                                });

                                var selectmch = document.getElementById("listmch");
                                if(data!=null){
                                    if(data.rows.mchnameList!=null){
                                        for(var i=0; i<data.rows.mchnameList.length ; i++){
                                            var opt = document.createElement("OPTION")
                                            opt.text = data.rows.mchnameList[i];
                                            opt.value = data.rows.mchnameList[i];
                                            selectmch.add(opt);
                                        }
                                    }else{
                                        alert("No Mch available for selected category.");
                                    }
                                }
                            }
                        });
                    }
                });

                $("#subcategorySel").change(function(){
                    var category=$("#subcategorySel option:selected").val();
                    $('#listmch').empty();
                    $('#listTomch').empty();
                    if(category !=-1){
                        $.ajax({
                            url: "getMchSubCategoryWise?subcategoryname="+category,
                            cache:false,
                            global: false,
                            type: "POST",
                            dataType:"json",
                            contanttype: 'text/json',
                            async:false,
                            error:function(){
                                alert("Can not connect to server");
                            },
                            success: function(data){


                                $('#listmch option').each(function(ik, option){
                                    $(option).remove();
                                });

                                var selectmch = document.getElementById("listmch");
                                if(data!=null){
                                    if(data.rows.mchnameList!=null){
                                        for(var i=0; i<data.rows.mchnameList.length ; i++){
                                            var opt = document.createElement("OPTION")
                                            opt.text = data.rows.mchnameList[i];
                                            opt.value = data.rows.mchnameList[i];
                                            selectmch.add(opt);
                                        }
                                    }else{
                                        alert("No Mch available for selected sub category.");
                                    }
                                }
                            }
                        });
                    }
                });

                $("#checkArticle").click(function(){
                    // alert("called");
                    $('#listToarticle').empty();
                    $('#listArticle').empty();
                    //$("#checkArticle").attr('checked',true);
                    var mchoptions = Array();
                    $('#listTomch option').each(function(index){
                        mchoptions[index] = $(this).text();
                    });
                    var mch = $('#listTomch option').val();

                    if(mch==undefined){
                        alert("Please select MC.");
                        return false;
                    }else{                       
                        $.ajax({
                            url: "getArticleMchWise?mcname="+mchoptions,
                            global: false,
                            type: "POST",
                            dataType:"json",
                            contanttype: 'text/json',
                            async:false,
                            error:function(){
                                alert("Can not connect to server");
                            },
                            success: function(data){
                                $('#listArticle option').each(function(ik, option){
                                    $(option).remove();
                                });
                                var selectmch = document.getElementById("listArticle");
                                if(data!=null){
                                    if(data.rows.articlenameList!=null){
                                        for(var i=0; i<data.rows.articlenameList.length ; i++){
                                            var opt = document.createElement("OPTION")
                                            opt.text = data.rows.articlenameList[i];
                                            opt.value = data.rows.articlenameList[i];
                                            selectmch.add(opt);
                                        }
                                    }else{
                                        alert("No Article available for selected Mc.");
                                    }
                                }
                            }
                        });
                    }
                });


                function loadControllOnPageLoad(){

                    $("#trSubCategory").hide();
                    $('#trAddArticle').hide();
                    $('#trArticleList').hide();
                    $(function() {
                        var dates = $( "#popup_container1, #popup_container2" ).datepicker({
                            //                            defaultDate: "+1w",
                            //                            numberOfMonths: 1,
                            changeMonth: true,
                            changeYear: true,
                            dateFormat: 'yy-mm-dd',
                            // maxDate : '+0d',
                            //minDate:'+0d',

                            onSelect: function( selectedDate ) {
                                var option = this.id == "popup_container1" ? "minDate" : "maxDate",
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

               
              
                var gridUrl;
                $("#btnSearch").click(function (){
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    var serachType="";
                    var zone = Array();
                    $('#zonesel option').each(function(index){
                        zone[index] = $(this).val();
                    });                  
                    var startDate= $("#popup_container1").val();
                    var endDate= $("#popup_container2").val();
                    var event =$('#eventSel').val();
                    var category =$('#categorySel').val();
                    //                       var category =$('#categorySel').val();
                    var mchoptions = Array();
                    $('#listTomch option').each(function(index){
                        mchoptions[index] = $(this).text();
                    });
                    //alert(mchoptions);
                    var articleoption = Array();
                    $('#listToarticle option').each(function(index){
                        articleoption[index] = $(this).text();
                    });

                    if(zone==null || zone.length==0 || zone==undefined){
                        alert("Please select Zone.");
                        return false;
                    }else{
                        if(startDate==undefined || startDate==null || startDate.length==0){
                            alert("Please select valid FROM date.");
                            return false;
                        }else if(endDate==undefined || endDate==null || endDate.length==0){
                            alert("Please select valid TO date.");
                            return false;
                        }

                        if(event==-1 && category==-1 && (articleoption==undefined || articleoption.length==0)){
                            serachType ="ZONE_DATE_MC";
                            gridUrl="getCommunicationGrid?&startDate="+startDate+"&endDate="+endDate+"&searchType="+serachType+"&zonesel="+zone+"&eventId="+event+"&mccode="+mchoptions;
                        }else if(event==-1 && category==-1 && (mchoptions!=null || mchoptions.length>0 || mchoptions!=undefined)){
                            serachType ="ZONE_DATE_MC_ARTICLE";
                            gridUrl="getCommunicationGrid?&startDate="+startDate+"&endDate="+endDate+"&searchType="+serachType+"&zonesel="+zone+"&eventId="+event+"&mccode="+mchoptions+"&article="+articleoption;
                        }
                        //                        else{
                        //                            alert("Please Enter MC Code.");
                        //                            return false;
                        //                        }
                        if(event==-1){
                            if(category==-1 && (mchoptions==undefined || mchoptions.length==0) && (articleoption==undefined || articleoption.length==0)){
                                serachType ="ZONE_DATE";
                                gridUrl="getCommunicationGrid?&startDate="+startDate+"&endDate="+endDate+"&searchType="+serachType+"&zonesel="+zone;
                            }else if((mchoptions==undefined || mchoptions.length==0) && (articleoption==undefined || articleoption.length==0)){
                                var fromMCHoptions = Array();
                                $('#listmch option').each(function(index){
                                    fromMCHoptions[index] = $(this).text();
                                });
                                serachType ="ZONE_DATE_CATEGORY";
                                gridUrl="getCommunicationGrid?&startDate="+startDate+"&endDate="+endDate+"&searchType="+serachType+"&zonesel="+zone+"&eventId="+event+"&categoryName="+fromMCHoptions;
                            }else if( (articleoption==undefined || articleoption.length==0)){
                                serachType ="ZONE_DATE_CATEGORY_MC";
                                gridUrl="getCommunicationGrid?&startDate="+startDate+"&endDate="+endDate+"&searchType="+serachType+"&zonesel="+zone+"&eventId="+event+"&mccode="+mchoptions;
                            }else{
                                serachType ="ZONE_DATE_CATEGORY_MC_ARTICLE";
                                gridUrl="getCommunicationGrid?&startDate="+startDate+"&endDate="+endDate+"&searchType="+serachType+"&zonesel="+zone+"&eventId="+event+"&mccode="+mchoptions+"&article="+articleoption;
                            }
                            
                        }else if(event!=null){                        
                            if(category==-1 && (mchoptions==undefined || mchoptions.length==0) && (articleoption==undefined || articleoption.length==0)){
                                serachType ="ZONE_DATE_EVENT";
                                gridUrl="getCommunicationGrid?&startDate="+startDate+"&endDate="+endDate+"&searchType="+serachType+"&zonesel="+zone+"&eventId="+event;
                            }else if((mchoptions==undefined || mchoptions.length==0) && (articleoption==undefined || articleoption.length==0)){
                                var fromMCHoptions = Array();
                                $('#listmch option').each(function(index){
                                    fromMCHoptions[index] = $(this).text();
                                });
                                serachType ="ZONE_DATE_EVENT_CATEGORY";
                                gridUrl="getCommunicationGrid?&startDate="+startDate+"&endDate="+endDate+"&searchType="+serachType+"&zonesel="+zone+"&eventId="+event+"&categoryName="+fromMCHoptions;
                            }else if( (articleoption==undefined || articleoption.length==0)){
                                serachType ="ZONE_DATE_EVENT_CATEGORY_MC";
                                gridUrl="getCommunicationGrid?&startDate="+startDate+"&endDate="+endDate+"&searchType="+serachType+"&zonesel="+zone+"&eventId="+event+"&mccode="+mchoptions;
                            }else{
                                serachType ="ZONE_DATE_EVENT_CATEGORY_MC_ARTICLE";
                                gridUrl="getCommunicationGrid?&startDate="+startDate+"&endDate="+endDate+"&searchType="+serachType+"&zonesel="+zone+"&eventId="+event+"&mccode="+mchoptions+"&article="+articleoption;
                            }
                        }
                    }
                    jQuery("#commGrid").jqGrid('setGridParam',{url:gridUrl,datatype:'json',page:1}).trigger("reloadGrid");
                });

                function addoptionToSubCategory(){
                    $("#subcategorySel").empty();
                    var select = document.getElementById("subcategorySel");
                    var newoption = document.createElement('option');
                    newoption.text = "---Select Sub Category---";
                    newoption.value = -1;
                    select.add(newoption);
                }
                function clearDate(){
                    var dates = $("input[id$='popup_container1'], input[id$='popup_container2']");
                    dates.attr('value', '');
                    dates.each(function(){
                        $.datepicker._clearDate(this);
                    });
                }
                $("#btnReset").click(function (data){
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    clearDate();
                    $("#message").html("");
                    $("#subcategorySel").val("-1");
                    $("#trSubCategory").hide();
                    addoptionToSubCategory();

                    $('#trAddArticle').hide();
                    $('#trArticleList').hide();
                    $('#eventSel').val('-1');
                    $('#categorySel').val('-1');
                    $('#listmch').empty();
                    $('#listTomch').empty();
                    $('#listToarticle').empty();
                    $('#listArticle').empty();
                    $("#popup_container1").val("");
                    $("#popup_container2").val("");
                    $("#checkArticle").attr('checked',false);
                    $('#zonesel').empty();
                    $('#listzone').empty();
                    fetchzoneBasedOnuser();
                    jQuery("#commGrid").jqGrid("clearGridData", true);
                    $('#promoCommunication')[0].reset();
                    
                });
                $("#btnArticle").click(function (){
                    var mchoptions = Array();
                    $('#listTomch option').each(function(index){
                        mchoptions[index] = $(this).text();
                    });
                    if(mchoptions==undefined || mchoptions.length==0 ){
                        alert("Please select/ enter MC.");
                        return false;
                    }
                    var txtarticlemanualCode=$("#txtArticle").val();
                    var select = document.getElementById("listToarticle");
                    txtarticlemanualCode= $.trim(txtarticlemanualCode);
                    var optn = document.createElement("OPTION")
                    optn.text = txtarticlemanualCode;
                    optn.value = txtarticlemanualCode;
                    select.add(optn);
                    $("#txtArticle").val('');
                   
                   
                });
                function fetchzoneBasedOnuser(){

                    $.ajax({
                        url: "getZoneBasedonUser",
                        global: false,
                        type: "POST",
                        dataType:"json",
                        contanttype: 'text/json',
                        async:false,
                        error:function(){
                            alert("Can not connect to server");
                        },
                        success: function(data){
                            //alert(data);
                            $('#listzone option').each(function(ik, option){
                                $(option).remove();
                            });

                            var selectregion = document.getElementById("listzone");
                            if(data!=null){
                                if(data.rows.zoneDescList!=null){
                                    for(var i=0; i<data.rows.zoneDescList.length ; i++){
                                        var optnRegion = document.createElement("OPTION")
                                        optnRegion.text = data.rows.zoneDescList[i];
                                        optnRegion.value = data.rows.zoneIdList[i];
                                        selectregion.add(optnRegion);
                                    }
                                }else{
                                    alert("No Zone available for selected Format.");
                                }

                            }
                        }
                    });
                }

                $("#btnMC").click(function (){

                    var txtMCManualcode=$("#txtMc").val();
                    var select = document.getElementById("listTomch");
                    txtMCManualcode= $.trim(txtMCManualcode);
                    
                    if(txtMCManualcode!=null &&  txtMCManualcode.length>0 && txtMCManualcode!=undefined){
                        var optn = document.createElement("OPTION");
                        optn.text = txtMCManualcode;
                        optn.value = txtMCManualcode;
                        select.add(optn);
                    }else{
                        alert("Please enter MC.");
                        return false;
                    }
                  
                    $("#txtMc").val('');
                    var mchoptions = Array();
                    $('#listTomch option').each(function(index){
                        mchoptions[index] = $(this).text();
                    });                   
                    if(mchoptions==undefined || mchoptions.length==0 ){
                        alert("Please select MC.");
                        return false;
                    }else{
                        $('#trAddArticle').show();
                        $('#trArticleList').show();
                    }
                    getArticledtl(mchoptions);
                });

                function getArticledtl(mchoption){
                    $.ajax({
                        url: "getArticleMchWise?mcname="+mchoption,
                        global: false,
                        type: "POST",
                        dataType:"json",
                        contanttype: 'text/json',
                        async:false,
                        error:function(){
                            alert("Can not connect to server");
                        },
                        success: function(data){
                            $('#listArticle option').each(function(ik, option){
                                $(option).remove();
                            });
                            var selectmch = document.getElementById("listArticle");
                            if(data!=null){
                                if(data.rows.articlenameList!=null){
                                    for(var i=0; i<data.rows.articlenameList.length ; i++){
                                        var opt = document.createElement("OPTION")
                                        opt.text = data.rows.articlenameList[i];
                                        opt.value = data.rows.articlenameList[i];
                                        selectmch.add(opt);
                                    }
                                }else{
                                    alert("No Article available for selected Mc.");
                                }
                            }
                        }
                    });
                }


                //Download button
                $("#downloadBtn").click(function(){
                    $("#message").html('');
                    $("#message").html('');
                    var  empId=   $("#hdempId").val();
                    //                    var id = $('#commGrid').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request !");
                        return false;
                    }else{
                        var iframe = document.createElement("iframe");
                        iframe.src = "downloadMultipleExcel?transId="+idsOfSelectedRows+"&empID="+empId+"&downloadType=COMMUNICATION_DOWNLOAD";
                        iframe.style.display = "none";
                        document.body.appendChild(iframe);
                    }

                });
            });

        </script>
        <script type="text/javascript">
            function moveDataRightWithArticle(tbFrom, tbTo){
                //$('#subcategorysel').empty();
                $('#listToarticle').empty();
                $('#listArticle').empty();
              
                //$("#checkArticle").attr('checked',true);
                moveData(tbFrom,tbTo);
                var mchoptions = Array();
                $('#listTomch option').each(function(index){
                    mchoptions[index] = $(this).val();
                });
               
                if(mchoptions==undefined || mchoptions.length==0 ){
                    alert("Please select MC.");
                    return false;
                }
                getArticle(mchoptions);
                $('#trArticleList').show();
                $('#trAddArticle').show();

            }

            function moveAllDataWithArticle(tbFrom, tbTo){
                // $('#subcategorysel').empty();
                $('#listToarticle').empty();
                $('#listArticle').empty();
             
                //$("#checkArticle").attr('checked',true);
                moveAllDataRight(tbFrom,tbTo);
                var mchoptions = Array();
                $('#listTomch option').each(function(index){
                    mchoptions[index] = $(this).text();
                });                
                if(mchoptions==undefined || mchoptions.length==0 ){
                    alert("Please select MC.");
                    return false;
                }
                getArticle(mchoptions);
                $('#trAddArticle').show();
                $('#trArticleList').show();
            }
            function movedataWithArticleLeft(tbFrom, tbTo){
                //$('#subcategorysel').empty();
                $('#listToarticle').empty();
                $('#listArticle').empty();
                $('#trAddArticle').hide();
                $('#trArticleList').hide();
                //$("#checkArticle").attr('checked',true);
                moveData(tbFrom,tbTo);
                var mchoptions = Array();
                $('#listTomch option').each(function(index){
                    mchoptions[index] = $(this).text();
                });
                if(mchoptions==undefined || mchoptions.length==0 ){
                    alert("Please select MC.");
                    return false;
                }
                getArticle(mchoptions);
                $('#trAddArticle').show();
                $('#trArticleList').show();
            }

            function getArticle(mchoption){
                $.ajax({
                    url: "getArticleMchWise?mcname="+mchoption,
                    global: false,
                    type: "POST",
                    dataType:"json",
                    contanttype: 'text/json',
                    async:false,
                    error:function(){
                        alert("Can not connect to server");
                    },
                    success: function(data){
                        $('#listArticle option').each(function(ik, option){
                            $(option).remove();
                        });
                        var selectmch = document.getElementById("listArticle");
                        if(data!=null){
                            if(data.rows.articlenameList!=null){
                                for(var i=0; i<data.rows.articlenameList.length ; i++){
                                    var opt = document.createElement("OPTION")
                                    opt.text = data.rows.articlenameList[i];
                                    opt.value = data.rows.articlenameList[i];
                                    selectmch.add(opt);
                                }
                            }else{
                                alert("No Article available for selected Mc.");
                            }
                        }
                    }
                });
                
            }
        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />

        <table width="100%" border="0" cellspacing="2" cellpadding="2"  align="center">

            <s:form id="promoCommunication" action="donothing" method="POST" >
                <!--                <tr>
                                    <td colspan="2"><h1>Promotion Communication</h1></td>
                                </tr>-->
                <tr>
                    <td align="right" >
                        <a href ="#" class="download-sample " onclick="tb_show( '', 'viewCommunicationHelp?height=170&width=450');">
                            Help
                        </a>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td align="left"><input type="hidden" name="hdempId" id="hdempId"  value="<%= session.getAttribute(WebConstants.EMP_ID)%>" disabled/></td>
                </tr>
                <tr><td height="25px" ></td></tr>
                <tr>
                    <td width="90%" align="center" style="color: #0D6C0D;font-weight: bold" colspan="2">
                        <div id="message">
                            <s:actionmessage cssClass="successText"/>
                            <s:actionerror cssClass="errorText"/>
                        </div>
                    </td>
                </tr>
                <tr><td class="errorText" align="right">(Fields marked with a * are mandatory.)</td></tr>
                <tr>
                    <td >
                        <table width="90%" align="center" >
                            <tr>
                                <td align="right">Zone </td>
                                <td class="errorText">*&nbsp;&nbsp;&nbsp;</td>
                                <td >
                                    <table align="left">
                                        <tr>
                                            <td >
                                                <s:select name="zoneLB" id="listzone"  list="zoneMap" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 65px"/>
                                            </td>
                                            <td align="center" valign="middle">
                                                <input type="button" onClick="moveData(this.form.zoneLB,this.form.zonesel)"
                                                       value="->"><br />
                                                <input type="button" onClick="moveAllDataRight(this.form.zoneLB,this.form.zonesel);"
                                                       value=">>"><br />
                                                <input type="button" onClick="moveData(this.form.zonesel,this.form.zoneLB)"
                                                       value="<-">
                                            </td>
                                            <td>
                                                <s:select name="zonesel" id="zonesel"  list="#{}" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 65px"/>
                                            </td>

                                        </tr>
                                    </table>
                                </td>
                            </tr>                    

                            <tr><td height="5px" ></td></tr>
                            <tr>
                                <td align="right">Promotion Validity Date </td>
                                <td class="errorText">*&nbsp;</td>
                                <td align="left"> From &nbsp;  <input name="startDate" type="text" class="datef" id="popup_container1" readonly="readonly"/>
                                    &nbsp;&nbsp;&nbsp;To &nbsp;
                                    <input name="endDate" type="text" class="datef" id="popup_container2" readonly="readonly"/>
                                    &nbsp;&nbsp;&nbsp;</td>
                            </tr>
                            <tr><td height="5px" ></td></tr>
                            <tr>
                                <td  align="right">Campaign</td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td align="left" ><s:select name="eventSel" id="eventSel"  list="eventMap" headerKey="-1" headerValue="---Select Campaign---" value="---Select Campaign---" cssClass="dropdown"/></td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            </tr>
                            <tr><td height="5px" ></td></tr>
                            <tr>
                                <td  align="right">Category</td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td align="left" ><s:select name="categorySel" id="categorySel"  list="categoryMap" headerKey="-1" headerValue="---Select Category---" value="---Select Category---" cssClass="dropdown"/></td>

                            </tr>
                            <tr><td height="5px" ></td></tr>
                            <tr id="trSubCategory">
                                <td  align="right">Sub Category </td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                <td align="left">
                                    <s:select name="subcategoryName" id="subcategorySel" list="subcategoryMap"  headerKey="-1" headerValue="---Select----"/>
                                </td>
                            </tr>
                            <tr><td height="5px" ></td></tr>
                            <tr>
                                <td></td>
                                <td>&nbsp;&nbsp;&nbsp;</td>
                                <td align="right" >
                                    <table align="center" width="40%">
                                        <tr>
                                            <td></td>
                                            <td>Add MC : </td>
                                            <td><input type="text" id="txtMc" name="txtMc"/></td>
                                            <td><input type="button" id="btnMC" name="btnMC" value="ADD MC" class="btn"/></td>
                                        </tr>
                                    </table></td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            </tr>
                            <tr>
                                <td  align="right">MC</td>
                                <td>&nbsp;&nbsp;&nbsp;</td>
                                <td>
                                    <table width="100%" align="left">
                                        <tr>
                                            <td>
                                                <table align="left">

                                                    <tr>
                                                        <td >
                                                            <s:select name="mchLB" id="listmch"  list="mchList" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 65px"/>
                                                        </td>
                                                        <td align="center" valign="middle">
                                                            <input type="button" onClick="moveDataRightWithArticle(this.form.mchLB,this.form.listTomch)"
                                                                   value="->"><br />
                                                            <input type="button" onClick="moveAllDataWithArticle(this.form.mchLB,this.form.listTomch);"
                                                                   value=">>"><br />
                                                            <input type="button" onClick="movedataWithArticleLeft(this.form.listTomch,this.form.mchLB)"
                                                                   value="<-">
                                                        </td>

                                                        <td>
                                                            <select multiple size="10" name="TomchLB" id="listTomch" style="width:300px;height: 65px">
                                                            </select>
                                                        </td>                                                        
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            </tr>
                            <tr><td height="5px" ></td></tr>
                            <tr id="trAddArticle">
                                <td></td>
                                <td>&nbsp;&nbsp;&nbsp;</td>
                                <td align="right" >
                                    <table align="center" width="40%">
                                        <tr>
                                            <td></td>
                                            <td>Add Article : </td>
                                            <td><input type="text" id="txtArticle" name="txtArticle"/></td>
                                            <td><input type="button" id="btnArticle" name="btnArticle" value="ADD Article" class="btn"/></td>
                                        </tr>
                                    </table></td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            </tr>
                            <tr id="trArticleList">
                                <td  align="right">Article</td>
                                <td>&nbsp;&nbsp;&nbsp;</td>
                                <td>
                                    <table width="100%" align="left">
                                        <tr>
                                            <td align="left">
                                                <table align="left">
                                                    <tr>
                                                        <td >
                                                            <s:select name="articleLB" id="listArticle"  list="articleList" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 65px"/>
                                                        </td>
                                                        <td align="center" valign="middle">
                                                            <input type="button" onClick="moveData(this.form.articleLB,this.form.listToarticle)"
                                                                   value="->"><br />
                                                            <input type="button" onClick="moveAllDataRight(this.form.articleLB,this.form.listToarticle);"
                                                                   value=">>"><br />
                                                            <input type="button" onClick="moveData(this.form.listToarticle,this.form.articleLB)"
                                                                   value="<-">
                                                        </td>
                                                        <td>
                                                            <select multiple size="10" name="ToarticleLB" id="listToarticle" style="width:300px;height: 65px">
                                                            </select>

                                                        </td>
                                                        <!--                                                        <td align="right" width="100px"><input type="checkbox" id="checkArticle" name="checkdtl" value="5"/>Show Article</td>-->
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                                <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                            </tr>                          
                            <tr>
                                <td colspan="4" align="center">
                                    <input type="button" id="btnSearch" name="btnSearch" class="btn" value="Search"/>
                                    <input type="button" id="btnReset" name="btnReset" class="btn" value="Clear"/>
                                </td>
                            </tr>                      
                        </table>
                    </td>
                </tr>
                <tr>
                    <td  align="center" colspan="2">
                        <table id="commGrid"></table>
                        <div id="commPg"></div>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <img src="images/spacer.gif" width="10" height="10"/>
                    </td>
                </tr>
                <tr>
                    <td align="center" colspan="2" style="height: 200%">
                        <input type="button" name="downloadBtn" id="downloadBtn" value="Download"  class="btn"/>
                    </td>  
                </tr>

            </s:form>
        </table>

    </body>
</html>
