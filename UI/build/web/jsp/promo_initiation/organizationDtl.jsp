<%-- 
    Document   : organizationDtl
    Created on : Dec 27, 2012, 10:53:18 AM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Organization Group Selection</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
        <link href="css/thickbox.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/ui_gridcolmodels.js"></script>
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>
        <script type="text/javascript" src="js/thickbox.js"></script>
        <script type="text/javascript">
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });

                //Load Data on Page Load
                loadControllOnPageLoad();
                var promoReqId;
                function loadControllOnPageLoad(){
                    $("#checkFormat").attr('checked', true);
                    //$("#checkZone").attr('checked', true);
                    jQuery("#reqGrid").jqGrid({
                        url:"getAllPromotiondtl_initiator_org",
                        datatype: 'json',
                        width: 850,
                        height:110,
                        colNames:reqGridColNames,
                        colModel:reqGridColmodels,
                        rowNum:30,
                        rowList:[30],
                        viewrecords: true,
                        pager: '#reqPager',
                        multiselect: false,
                        headertitles: true,
                        //                    loadonce:true,
                        ignoreCase:true,
                        imgpath: "themes/basic/images",
                        caption:"",
                        onSelectRow: function(id) {
                            promoReqId=id;
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
                            promoReqId=null;
                            var urlStr='getAllPromotiondtl_initiator_org';
                            jQuery("#reqGrid").jqGrid('setGridParam',{url:urlStr,page:1}).trigger("reloadGrid");

                        }
                    }
                );

                }

           
              
              
                $("#checkZone").click(function(){                   
                    //$("#checkZone").attr('checked',true);
                    var zoneoptions = Array();
                    $('#listToZone option').each(function(index){
                        zoneoptions[index] = $(this).text();
                    });
                    // alert("Opetion : "+zoneoptions);
                    if(zoneoptions==undefined){
                        alert("Please select Zone.");
                        return false;
                    }else{
                        // alert("called");
                        $.ajax({
                            url: "getStateAndRegion?zonename="+zoneoptions,
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
                                $('#listRegion option').each(function(ik, option){
                                    $(option).remove();
                                });

                                $('#listState option').each(function(ik, option){
                                    $(option).remove();
                                });

                                var selectregion = document.getElementById("listRegion");
                                var selectState = document.getElementById("listState");

                                if(data!=null){
                                    if(data.rows.regionnameList!=null){
                                        for(var i=0; i<data.rows.regionnameList.length ; i++){
                                            var optnRegion = document.createElement("OPTION")
                                            optnRegion.text = data.rows.regionnameList[i];
                                            optnRegion.value = data.rows.regionnameList[i];
                                            selectregion.add(optnRegion);
                                        }
                                    }else{
                                        alert("No region available for selected Zone.");
                                    }
                                    if(data.rows.StatenameList!=null){
                                        for(var i=0; i<data.rows.StatenameList.length ; i++){
                                            var optnstate = document.createElement("OPTION")
                                            optnstate.text = data.rows.StatenameList[i];
                                            optnstate.value = data.rows.StatenameList[i];
                                            selectState.add(optnstate);
                                        }
                                    }else{
                                        alert("No state available for selected Zone.");
                                    }
                                }
                            }
                        });
                    }

                });

                $("#checkcity").click(function(){
                    // alert("called");
                    //$("#checkcity").attr('checked',true);
                    var stateoptions = Array();
                    $('#listToState option').each(function(index){
                        stateoptions[index] = $(this).text();
                    });
                    // alert("Opetion : "+zoneoptions);
                    if(stateoptions==undefined){
                        alert("Please select State.");
                        return false;
                    }else{
                        // alert("called");
                        $.ajax({
                            url: "getCitybasedonState?statename="+stateoptions,
                            global: false,
                            type: "POST",
                            dataType:"json",
                            contanttype: 'text/json',
                            async:false,
                            error:function(){
                                alert("Can not connect to server");
                            },
                            success: function(data){

                                $('#listCity option').each(function(ik, option){
                                    $(option).remove();
                                });

                                var selectcity = document.getElementById("listCity");
                                if(data!=null){
                                    if(data.rows.citynameList!=null){
                                        for(var i=0; i<data.rows.citynameList.length ; i++){
                                            var opt = document.createElement("OPTION")
                                            opt.text = data.rows.citynameList[i];
                                            opt.value = data.rows.citynameList[i];
                                            selectcity.add(opt);
                                        }
                                    }else{
                                        alert("No City available for selected State.");
                                    }
                                }
                            }
                        });
                    }

                });

                $("#checkstore").click(function(){
                    //alert("called");
                    //$("#checkstore").attr('checked',true);
                    var cityoptions = Array();
                    $('#listToCity option').each(function(index){
                        cityoptions[index] = $(this).text();
                    });
                    // alert("Opetion : "+zoneoptions);
                    if(cityoptions==undefined){
                        alert("Please select State.");
                        return false;
                    }else{
                        // alert("called");
                        $.ajax({
                            url: "getstoreCitywise?cityname="+cityoptions,
                            global: false,
                            type: "POST",
                            dataType:"json",
                            contanttype: 'text/json',
                            async:false,
                            error:function(){
                                alert("Can not connect to server");
                            },
                            success: function(data){

                                $('#liststore option').each(function(ik, option){
                                    $(option).remove();
                                });

                                var selectcity = document.getElementById("liststore");
                                if(data!=null){
                                    if(data.rows.storenameList!=null){
                                        for(var i=0; i<data.rows.storenameList.length ; i++){
                                            var opt = document.createElement("OPTION")
                                            opt.text = data.rows.storenameList[i];
                                            opt.value = data.rows.storeIdList[i];
                                            selectcity.add(opt);
                                        }
                                    }else{
                                        alert("No Store available for selected City.");
                                    }
                                }
                            }
                        });
                    }
                });
                var formatoptions = Array();
                var regionoptions = Array();
                var zoneoptions = Array();
                var stateoptions = Array();
                var cityoptions = Array();
                var storeoptions = Array();
                var storeFRomSide = Array();
                $("#btnSave").click(function (){
                    if(promoReqId==null){
                        alert("Please select Promotion request.");
                        return false;
                    }
                  
                    $('#listToformat option').each(function(index){
                        formatoptions[index] = $(this).text();
                    });
                    $('#listToZone option').each(function(index){
                        zoneoptions[index] = $(this).val();
                    });
                    $('#listToRegion option').each(function(index){
                        regionoptions[index] = $(this).text();
                    });
                    $('#listToState option').each(function(index){
                        stateoptions[index] = $(this).text();
                    });
                    $('#listToCity option').each(function(index){
                        cityoptions[index] = $(this).text();
                    });
                    $('#listTostore option').each(function(index){
                        storeoptions[index] = $(this).val();
                    });
                    $("#mstZoneList").val(zoneoptions);
                    var zoneData=$("#mstZoneList").val();
                  
                    if(zoneData.length==0){
                        alert("Please Select atleast one Zone.");
                        return false;
                    }
                    $("#mstFormatlist").val(formatoptions);
                    $("#mstStoreList").val(storeoptions);
                    $("#mstCityList").val(cityoptions);
                    $("#mstRegionList").val(regionoptions);
                    //$("#mstZoneList").val(zoneoptions);
                    $("#mststateList").val(stateoptions);

                    $("#mstPromoId").val(promoReqId);
                    $("#statusId").val("11");
                });

                $("#btnSubmit").click(function (){
                    if(promoReqId==null){
                        alert("Please select Promotion request.");
                        return false;
                    }
                    $('#listToformat option').each(function(index){
                        formatoptions[index] = $(this).text();
                    });
                    $('#listToZone option').each(function(index){
                        zoneoptions[index] = $(this).val();
                    });
                   
                    $('#listToRegion option').each(function(index){
                        regionoptions[index] = $(this).text();
                    });
                    $('#listToState option').each(function(index){
                        stateoptions[index] = $(this).text();
                    });
                    $('#listToCity option').each(function(index){
                        cityoptions[index] = $(this).text();
                    });
                    $('#listTostore option').each(function(index){
                        storeoptions[index] = $(this).val();
                    });
                    $("#mstFormatlist").val(formatoptions);
                    var formatData=$("#mstFormatlist").val();
                    if(formatData.length==0){
                        alert("Please Select format.");
                        return false;
                    }
                    $("#mstZoneList").val(zoneoptions);
                    var zoneData=$("#mstZoneList").val();                    
                    if(zoneData.length==0){
                        alert("Please Select atleast one Zone.");
                        return false;
                    }
                   
                    $("#mstStoreList").val(storeoptions);
                    $("#mstCityList").val(cityoptions);
                    $("#mstRegionList").val(regionoptions);
                    $("#mststateList").val(stateoptions);
                    $("#mstPromoId").val(promoReqId);
                    $("#statusId").val("12");
                    if($("#mstRegionList").val().length==0){
                        $('#listState option').each(function(index){
                            storeFRomSide[index] = $(this).text();
                        });
                        if(storeFRomSide==undefined || storeFRomSide.length==0){
                            alert("No State available for selected Format and Zone.");
                            return false;
                        }
                    }                   
                });

                getAllFormat();
                function getAllFormat(){
                    $.ajax({
                        url: "getAllFormatExceptZoneAndHo",
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
                            $('#listformat option').each(function(ik, option){
                                $(option).remove();
                            });
                
                            var select = document.getElementById("listformat");
                
                            if(data!=null){
                                for(var i=0; i<data.rows.formatList.length ; i++){
                                    var optn = document.createElement("OPTION")
                                    optn.text = data.rows.formatList[i];
                                    optn.value = data.rows.formatList[i];
                                    select.add(optn);
                                }
                            }else{
                
                            }
                
                        }
                    });
                
                }
                $("#btnClear").click(function (){
                    $("#message").html("");
                    $("#listToformat").empty();
                    $("#listToZone").empty();
                    $("#listToRegion").empty();
                    $("#listToState").empty();
                    $("#listToCity").empty();
                    $("#listTostore").empty();
                    $("#listformat").empty();
                    $("#listzone").empty();
                    $("#listRegion").empty();
                    $("#listState").empty();
                    $("#listCity").empty();
                    $("#liststore").empty();
                    getAllFormat();                  

                    $("#mstFormatlist").val('');
                    $("#mstStoreList").val('');
                    $("#mstCityList").val('');
                    $("#mstRegionList").val('');
                    $("#mstZoneList").val('');
                    $("#mststateList").val('');
                    $("#mstPromoId").val('');
                    $("#statusId").val('');
                });

            });
             
        </script>
        <script type="text/javascript">

            function resetListBoxONFORMATSelection(){
                $("#listToZone").empty();
                $("#listToRegion").empty();
                $("#listToState").empty();
                $("#listToCity").empty();
                $("#listTostore").empty();
                $("#listRegion").empty();
                $("#listzone").empty();
                $("#listState").empty();
                $("#listCity").empty();
                $("#liststore").empty();
            }
            function movedataWithZoneRight(tbFrom, tbTo){
                resetListBoxONFORMATSelection();
                moveData(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }
                fetchzoneBasedOnFormatforHOANDZOne(txtformat);
            }
            function moveAllDataWithZone(tbFrom, tbTo){
                resetListBoxONFORMATSelection();
                moveAllDataRight(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }
                fetchzoneBasedOnFormatforHOANDZOne(txtformat);
            }
            function movedataWithZoneLeft(tbFrom, tbTo){
                resetListBoxONFORMATSelection();
                moveData(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }
                fetchzoneBasedOnFormatforHOANDZOne(txtformat);
            }
            function fetchzoneBasedOnFormatforHOANDZOne(txtformat){
           
                $.ajax({
                    url: "fetchzoneBasedOnFormat?txtformat="+txtformat,
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
         

            function resetListBoxOnZoneSelection(){
                $("#listState").empty();
                $("#listToRegion").empty();
                $("#listToState").empty();
                $("#listToCity").empty();
                $("#listTostore").empty();
                $("#listRegion").empty();
                $("#listCity").empty();
                $("#liststore").empty();
            }
            function movedataWithStateRight(tbFrom, tbTo){
                resetListBoxOnZoneSelection();
                moveData(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                var zoneoptions = Array();
                $('#listToZone option').each(function(index){
                    zoneoptions[index] = $(this).val();
                });
                // alert("Opetion : "+zoneoptions);
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }else if(zoneoptions==undefined || zoneoptions.length==0){
                    alert("Please select Zone.");
                    return false;
                }
                fetchStateBasedOnZoneAndFormat(zoneoptions,txtformat);
            }
            function moveAllDataWithState(tbFrom, tbTo){
                resetListBoxOnZoneSelection();
                moveAllDataRight(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                var zoneoptions = Array();
                $('#listToZone option').each(function(index){
                    zoneoptions[index] = $(this).val();
                });
                // alert("Opetion : "+zoneoptions);
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }else if(zoneoptions==undefined || zoneoptions.length==0){
                    alert("Please select Zone.");
                    return false;
                }
                fetchStateBasedOnZoneAndFormat(zoneoptions,txtformat);
            }
            function movedataWithStateLeft(tbFrom, tbTo){
                resetListBoxOnZoneSelection();
                moveData(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                var zoneoptions = Array();
                $('#listToZone option').each(function(index){
                    zoneoptions[index] = $(this).val();
                });
                // alert("Opetion : "+zoneoptions);
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }else if(zoneoptions==undefined || zoneoptions.length==0){
                    alert("Please select Zone.");
                    return false;
                }
                fetchStateBasedOnZoneAndFormat(zoneoptions,txtformat);
            }
            function fetchStateBasedOnZoneAndFormat(zoneoptions,txtformat){
             
                $.ajax({
                    url: "getStateBasedOnZoneAndFormat?zonename="+zoneoptions+"&txtformat="+txtformat,
                    global: false,
                    type: "POST",
                    dataType:"json",
                    contanttype: 'text/json',
                    async:false,
                    error:function(){
                        alert("Can not connect to server");
                    },
                    success: function(data){
                            
                        $('#listState option').each(function(ik, option){
                            $(option).remove();
                        });
                        var selectState = document.getElementById("listState");

                        if(data!=null){
                            if(data.rows.StatenameList!=null){
                                for(var i=0; i<data.rows.StatenameList.length ; i++){
                                    var optnstate = document.createElement("OPTION")
                                    optnstate.text = data.rows.StatenameList[i];
                                    optnstate.value = data.rows.StatenameList[i];
                                    selectState.add(optnstate);
                                }
                            }else{
                                alert("No state available for selected Zone.");
                            }
                        }
                    }
                });
            }

            function resetListBoxOnStateSelection(){
                $("#listToRegion").empty();
                $("#listRegion").empty();
                $("#listToCity").empty();
                $("#listTostore").empty();
                $("#listCity").empty();
                $("#liststore").empty();
            }
            function movedataWithRegionRight(tbFrom, tbTo){
                resetListBoxOnStateSelection();
                moveData(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                var stateoptions = Array();
                $('#listToState option').each(function(index){
                    stateoptions[index] ="'"+ $(this).text()+"'";
                });
                var zoneoptions = Array();
                $('#listToZone option').each(function(index){
                    zoneoptions[index] = $(this).val();
                });
                // alert("Opetion : "+zoneoptions);
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }else if(stateoptions==undefined || stateoptions.length==0){
                    alert("Please select State.");
                    return false;
                }else if(zoneoptions==undefined || zoneoptions.length==0){
                    alert("Please select zone.");
                    return false;
                }
                fetchRegionBasedOnStateAndFormat(stateoptions,txtformat,zoneoptions);
            }
            function moveAllDataWithRegion(tbFrom, tbTo){
                resetListBoxOnStateSelection();
                moveAllDataRight(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                var stateoptions = Array();
                $('#listToState option').each(function(index){
                    stateoptions[index] ="'"+ $(this).text()+"'";
                });
                var zoneoptions = Array();
                $('#listToZone option').each(function(index){
                    zoneoptions[index] = $(this).val();
                });
                // alert("Opetion : "+zoneoptions);
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }else if(stateoptions==undefined || stateoptions.length==0){
                    alert("Please select State.");
                    return false;
                }else if(zoneoptions==undefined || zoneoptions.length==0){
                    alert("Please select zone.");
                    return false;
                }
                fetchRegionBasedOnStateAndFormat(stateoptions,txtformat,zoneoptions);
            }
            function movedataWithRegionLeft(tbFrom, tbTo){
                resetListBoxOnStateSelection();
                moveData(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                var stateoptions = Array();
                $('#listToState option').each(function(index){
                    stateoptions[index] ="'"+ $(this).text()+"'";
                });
                var zoneoptions = Array();
                $('#listToZone option').each(function(index){
                    zoneoptions[index] = $(this).val();
                });
                // alert("Opetion : "+zoneoptions);
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }else if(stateoptions==undefined || stateoptions.length==0){
                    alert("Please select State.");
                    return false;
                }else if(zoneoptions==undefined || zoneoptions.length==0){
                    alert("Please select zone.");
                    return false;
                }
                fetchRegionBasedOnStateAndFormat(stateoptions,txtformat,zoneoptions);
            }
            function fetchRegionBasedOnStateAndFormat(stateoptions,txtformat,zoneoptions){
               
                // alert("called");
                $.ajax({
                    url: "getRegionBasedOnstateAndFormat?statename="+stateoptions+"&txtformat="+txtformat+"&zonename="+zoneoptions,
                    global: false,
                    type: "POST",
                    dataType:"json",
                    contanttype: 'text/json',
                    async:false,
                    error:function(){
                        alert("Can not connect to server");
                    },
                    success: function(data){

                        $('#listRegion option').each(function(ik, option){
                            $(option).remove();
                        });
                        var selectregion = document.getElementById("listRegion");

                        if(data!=null){
                            if(data!=null){
                                if(data.rows.regionnameList!=null){
                                    for(var i=0; i<data.rows.regionnameList.length ; i++){
                                        var optnRegion = document.createElement("OPTION")
                                        optnRegion.text = data.rows.regionnameList[i];
                                        optnRegion.value = data.rows.regionnameList[i];
                                        selectregion.add(optnRegion);
                                    }
                                }else{
                                    alert("No region available for selected Zone.");
                                }
                            }
                        }
                    }
                });
                
            }

            function resetListBoxOnRegionSelection(){
                $("#listCity").empty();
                $("#listToCity").empty();
                $("#listTostore").empty();
                $("#liststore").empty();
            }
            function movedataWithCityRight(tbFrom, tbTo){
                resetListBoxOnRegionSelection();
                moveData(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                var regionoptions = Array();
                $('#listToRegion option').each(function(index){
                    regionoptions[index] ="'"+ $(this).text()+"'";
                });
                var stateoptions = Array();
                $('#listToState option').each(function(index){
                    stateoptions[index] ="'"+ $(this).text()+"'";
                });
                // alert("Opetion : "+zoneoptions);
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }else if(regionoptions==undefined || regionoptions.length==0){
                    alert("Please select Region.");
                    return false;
                }else if(stateoptions==undefined || stateoptions.length==0){
                    alert("Please select State.");
                    return false;
                }
                fetchcityBasedOnregionAndFormat(regionoptions,txtformat,stateoptions);
            }
            function moveAllDataWithCity(tbFrom, tbTo){
                resetListBoxOnRegionSelection();
                moveAllDataRight(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                var regionoptions = Array();
                $('#listToRegion option').each(function(index){
                    regionoptions[index] ="'"+ $(this).text()+"'";
                });
                var stateoptions = Array();
                $('#listToState option').each(function(index){
                    stateoptions[index] ="'"+ $(this).text()+"'";
                });
                // alert("Opetion : "+zoneoptions);
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }else if(regionoptions==undefined || regionoptions.length==0){
                    alert("Please select Region.");
                    return false;
                }else if(stateoptions==undefined || stateoptions.length==0){
                    alert("Please select State.");
                    return false;
                }
                fetchcityBasedOnregionAndFormat(regionoptions,txtformat,stateoptions);
            }
            function movedataWithcityLeft(tbFrom, tbTo){
                resetListBoxOnRegionSelection();
                moveData(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                var regionoptions = Array();
                $('#listToRegion option').each(function(index){
                    regionoptions[index] ="'"+ $(this).text()+"'";
                });
                var stateoptions = Array();
                $('#listToState option').each(function(index){
                    stateoptions[index] ="'"+ $(this).text()+"'";
                });
                // alert("Opetion : "+zoneoptions);
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }else if(regionoptions==undefined || regionoptions.length==0){
                    alert("Please select Region.");
                    return false;
                }else if(stateoptions==undefined || stateoptions.length==0){
                    alert("Please select State.");
                    return false;
                }
                fetchcityBasedOnregionAndFormat(regionoptions,txtformat,stateoptions);
            }
            function fetchcityBasedOnregionAndFormat(regionoptions,txtformat,stateoptions){
                
                // alert("called");
                $.ajax({
                    url: "getCityBasedOnRegionAndFormat?regionname="+regionoptions+"&txtformat="+txtformat+"&statename="+stateoptions,
                    global: false,
                    type: "POST",
                    dataType:"json",
                    contanttype: 'text/json',
                    async:false,
                    error:function(){
                        alert("Can not connect to server");
                    },
                    success: function(data){
                        $('#listCity option').each(function(ik, option){
                            $(option).remove();
                        });

                        var selectcity = document.getElementById("listCity");
                        if(data!=null){
                            if(data.rows.citynameList!=null){
                                for(var i=0; i<data.rows.citynameList.length ; i++){
                                    var opt = document.createElement("OPTION")
                                    opt.text = data.rows.citynameList[i];
                                    opt.value = data.rows.citynameList[i];
                                    selectcity.add(opt);
                                }
                            }else{
                                alert("No City available for selected State.");
                            }
                        }
                    }
                });
               
            }

            function resetListBoxOnCitySelection(){
                $("#listTostore").empty();
                $("#liststore").empty();
            }
            function movedataWithStoreRight(tbFrom, tbTo){              
                resetListBoxOnCitySelection();
                moveData(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                var cityoptions = Array();
                $('#listToCity option').each(function(index){
                    cityoptions[index] ="'"+ $(this).text()+"'";
                });
                var zoneoptions = Array();
                $('#listToZone option').each(function(index){
                    zoneoptions[index] = $(this).val();
                });
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }
                if(cityoptions==undefined || cityoptions.length==0){
                    alert("Please select City.");
                    return false;
                }
                if(zoneoptions==undefined || zoneoptions.length==0){
                    alert("Please select Zone.");
                    return false;
                }
                fetchStoreBasedOnCityAndFormat(cityoptions,txtformat,zoneoptions);
            }
            function moveAllDataWithStore(tbFrom, tbTo){
                resetListBoxOnCitySelection();
                moveAllDataRight(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                var cityoptions = Array();
                $('#listToCity option').each(function(index){
                    cityoptions[index] ="'"+ $(this).text()+"'";
                });
                var zoneoptions = Array();
                $('#listToZone option').each(function(index){
                    zoneoptions[index] = $(this).val();
                });
              
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }
                if(cityoptions==undefined || cityoptions.length==0){
                    alert("Please select City.");
                    return false;
                }
                if(zoneoptions==undefined || zoneoptions.length==0){
                    alert("Please select Zone.");
                    return false;
                }
                fetchStoreBasedOnCityAndFormat(cityoptions,txtformat,zoneoptions);
            }
            function movedataWithStoreLeft(tbFrom, tbTo){
                resetListBoxOnCitySelection();
                moveData(tbFrom,tbTo);
                var txtformat= Array();;
                $('#listToformat option').each(function(index){
                    txtformat[index] ="'"+ $(this).text()+"'";
                });
                var cityoptions = Array();
                $('#listToCity option').each(function(index){
                    cityoptions[index] ="'"+ $(this).text()+"'";
                });
                var zoneoptions = Array();
                $('#listToZone option').each(function(index){
                    zoneoptions[index] = $(this).val();
                });
                 
                if(txtformat==undefined || txtformat.length==0){
                    alert("Please select Format.");
                    return false;
                }
                if(cityoptions==undefined || cityoptions.length==0){
                    alert("Please select City.");
                    return false;
                }
                if(zoneoptions==undefined || zoneoptions.length==0){
                    alert("Please select Zone.");
                    return false;
                }
                fetchStoreBasedOnCityAndFormat(cityoptions,txtformat,zoneoptions);
            }
            function fetchStoreBasedOnCityAndFormat(cityoptions,txtformat,zoneoptions){
             
                // alert("called");
                $.ajax({
                    url: "getStoreBasedOnCityAndFormat?cityname="+cityoptions+"&txtformat="+txtformat+"&zonename="+zoneoptions,
                    global: false,
                    type: "POST",
                    dataType:"json",
                    contanttype: 'text/json',
                    async:false,
                    error:function(){
                        alert("Can not connect to server");
                    },
                    success: function(data){
                          
                        $('#liststore option').each(function(ik, option){
                            $(option).remove();
                        });

                        var selectcity = document.getElementById("liststore");
                        if(data!=null){
                            if(data.rows.storenameList!=null){
                                for(var i=0; i<data.rows.storenameList.length ; i++){
                                    var opt = document.createElement("OPTION")
                                    opt.text = data.rows.storenameList[i];
                                    opt.value = data.rows.storeIdList[i];
                                    selectcity.add(opt);
                                }
                            }else{
                                alert("No Store available for selected City.");
                            }
                        }
                    }
                });
            }

            function moveDataStoreRight(tbFrom,tbTo){
                var listopetion = Array();
                $('#liststore option').each(function(index){
                    listopetion[index] ="'"+ $(this).text()+"'";
                });
                // alert("Opetion : "+zoneoptions);
                if(listopetion==undefined || listopetion.length==0){
                    alert("No Store to move.");
                    return false;
                }
                moveData(tbFrom,tbTo);
            }
            function moveALLDataStore(tbFrom,tbTo){
                var listopetion = Array();
                $('#liststore option').each(function(index){
                    listopetion[index] ="'"+ $(this).text()+"'";
                });
                // alert("Opetion : "+zoneoptions);
                if(listopetion==undefined || listopetion.length==0){
                    alert("No Store to move.");
                    return false;
                }
                moveAllDataRight(tbFrom,tbTo);
            }
            function moveDataStoreLeft(tbFrom,tbTo){
                var listopetion = Array();
                $('#listTostore option').each(function(index){
                    listopetion[index] ="'"+ $(this).text()+"'";
                });
                if(listopetion==undefined || listopetion.length==0){
                    alert("No Store to move.");
                    return false;
                }
                moveData(tbFrom,tbTo);
            }



        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">
            <tr><td class="errorText" align="right">(Fields marked with a * are mandatory.)</td></tr>
            <s:form id="organization" action="donothing" method="post" enctype="multipart/form-data">
                <tr>
                    <td width="100%" align="center" >
                        <div id="message">
                            <s:actionmessage cssClass="successText"/>
                            <s:actionerror cssClass="errorText"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><h1>Organization Group Selection</h1></td>
                    <td align="center" >
                        <a href ="#" class="download-sample " onclick="tb_show( '', 'organizationPromoHelp?height=150&width=650');">
                            Help
                        </a>
                    </td>
                </tr>
                <tr><td height="5px"></td></tr>
                <tr>
                    <td colspan="5" align="center">
                        <table id="reqGrid"></table>
                        <div id="reqPager"></div>
                    </td>
                </tr>
                <tr><td height="15px"></td></tr>

                <tr>
                    <td>
                         <s:form id="storePromoId" name="storePromo" action="StorePromoFileAction" method="POST" enctype="multipart/form-data">
                         <table>
                                        <tr>
                                            <td>
                                                Upload File : <span class="errorText">&nbsp;*</span>
                                            </td>
                                            <td>
                                                <s:file id ="storePromoFileUpload" name="storePrmFileUpload" ></s:file>
                                            </td>
                                            <td></td>
                                            <td align="center">
                                                <a href ="downloadMultiplePromoTemplateFile" class="download-sample ">
                                                    Sample File
                                                </a>
                                            </td>
                                            <td></td>
                                          
                                        </tr>
                                         <tr><td height="5px" ></td></tr>
                            <tr>
                                <td align="center">
                                    <s:submit align="center" action="StorePromoFileAction" type="submit" id="btnUpload" name="btnUpload" value="Upload" cssClass="btn" />
                                </td>
                            </tr>
                                    </table>
                         </s:form>

                        <table  width="90%" border="0" align="center" cellpadding="1" cellspacing="2">
                            <tr>
                                <td>
                                    <table align="center">
                                        <tr><td><b>Format<span class="errorText">&nbsp;*</span></b></td>
                                            <td></td>
                                            <td><b>Selection</b></td>
                                        </tr>
                                        <tr>
                                            <td >
                                                <s:select name="FromatLB" id="listformat"  list="formatList" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 70px"/>
                                            </td>
                                            <td align="center" valign="middle">
                                                <!--                                                <input type="button" onClick="moveData(this.form.FromatLB,this.form.listToformat);fetchzoneBasedOnFormatforHOANDZOne();"
                                                                                                       value="->"><br />-->
                                                <input type="button" onClick="movedataWithZoneRight(this.form.FromatLB,this.form.listToformat);"
                                                       value="->"><br />
                                                <input type="button" onClick="moveAllDataWithZone(this.form.FromatLB,this.form.listToformat);"
                                                       value=">>"><br />
                                                <input type="button" onClick="movedataWithZoneLeft(this.form.listToformat,this.form.FromatLB)"
                                                       value="<-">
                                            </td>
                                            <td>
                                                <s:select name="ToFormatLB" id="listToformat"  list="#{}" cssClass="dropdown" multiple="true" size="4" style="width:300px;height: 70px"/>
                                                <!--                                                <select multiple size="10" name="orgFormVo.ToFormatLB" id="listToformat" style="width:300px;height: 60px">
                                                                                                </select>-->
                                            </td>
                                            <!--                                            <td align="right" width="100px"><input type="checkbox" id="checkFormat" name="checkdtl" value="1"/></td>-->
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr><td height="3px"></td></tr>
                            <tr>
                                <td>
                                    <table align="center">
                                        <tr><td><b>Zone<span class="errorText">&nbsp;*</span></b></td>
                                            <td></td>
                                            <td><b>Selection</b></td>
                                        </tr>
                                        <tr>
                                            <td >
                                                <s:select name="zoneLB" id="listzone"  list="zoneList" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 70px"/>
                                            </td>
                                            <td align="center" valign="middle">
                                                <input type="button" onClick="movedataWithStateRight(this.form.zoneLB,this.form.listToZone)"
                                                       value="->"><br />
                                                <input type="button" onClick="moveAllDataWithState(this.form.zoneLB,this.form.listToZone);"
                                                       value=">>"><br />
                                                <input type="button" onClick="movedataWithStateLeft(this.form.listToZone,this.form.zoneLB)"
                                                       value="<-">
                                            </td>
                                            <td>
                                                <s:select name="ToZoneLB" id="listToZone"  list="#{}" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 70px"/>
                                                <!--                                                <select multiple size="10" name="orgFormVo.ToZoneLB" id="listToZone" style="width:300px;height: 60px">
                                                                                                </select>-->
                                            </td>
                                            <!--                                            <td align="right" width="100px"><input type="checkbox" id="checkZone" name="checkdtl" value="2"/></td>-->
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr><td height="3px"></td></tr>
                            <tr>
                                <td>
                                    <table align="center">
                                        <tr><td><b>State</b></td>
                                            <td></td>
                                            <td><b>Selection</b></td>
                                        </tr>
                                        <tr>
                                            <td align="left">
                                                <s:select name="stateLB" id="listState"  list="stateList" cssClass="dropdown" multiple="true" size="5" cssStyle="width:300px;height: 70px"/>
                                            </td>
                                            <td align="center" valign="middle">
                                                <input type="button" onClick="movedataWithRegionRight(this.form.stateLB,this.form.listToState)"
                                                       value="->"><br />
                                                <input type="button" onClick="moveAllDataWithRegion(this.form.stateLB,this.form.listToState);"
                                                       value=">>"><br />
                                                <input type="button" onClick="movedataWithRegionLeft(this.form.listToState,this.form.stateLB)"
                                                       value="<-">
                                            </td>
                                            <td>
                                                <s:select name="ToStateLB" id="listToState"  list="#{}" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 70px"/>
                                                <!--                                                <select multiple size="10" name="orgFormVo.ToStateLB" id="listToState" style="width:300px;height: 60px">
                                                                                                </select>-->
                                            </td>
                                            <!--                                            <td align="right" width="100px"><input type="checkbox" id="checkstate" name="checkdtl" value="3"/></td>-->
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr><td height=3px"></td></tr>
                            <tr>
                                <td>
                                    <table align="center">
                                        <tr><td><b>Region</b></td>
                                            <td></td>
                                            <td><b>Selection</b></td>
                                        </tr>
                                        <tr>
                                            <td >
                                                <s:select name="regionLB" id="listRegion"  list="regionList" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 70px"/>
                                            </td>
                                            <td align="center" valign="middle">
                                                <input type="button" onClick="movedataWithCityRight(this.form.regionLB,this.form.listToRegion)"
                                                       value="->"><br />
                                                <input type="button" onClick="moveAllDataWithCity(this.form.regionLB,this.form.listToRegion);"
                                                       value=">>"><br />
                                                <input type="button" onClick="movedataWithcityLeft(this.form.listToRegion,this.form.regionLB)"
                                                       value="<-">
                                            </td>
                                            <td>
                                                <s:select name="ToRegionLB" id="listToRegion"  list="#{}" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 70px"/>
                                                <!--                                                <select multiple size="10" name="orgFormVo.ToRegionLB" id="listToRegion" style="width:300px;height: 60px">
                                                                                                </select>-->
                                            </td>
                                            <!--                                            <td align="right" width="100px"><input type="checkbox" id="checkRegion" name="checkdtl" value="2"/></td>-->
                                        </tr>
                                    </table>
                                </td>
                            </tr>

                            <tr><td height="3px"></td></tr>
                            <tr>
                                <td>
                                    <table align="center">
                                        <tr><td><b>City</b></td>
                                            <td></td>
                                            <td><b>Selection</b></td>
                                        </tr>
                                        <tr>
                                            <td >
                                                <s:select name="cityLB" id="listCity"  list="cityList" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 70px"/>
                                            </td>
                                            <td align="center" valign="middle">
                                                <input type="button" onClick="movedataWithStoreRight(this.form.cityLB,this.form.listToCity)"
                                                       value="->"><br />
                                                <input type="button" onClick="moveAllDataWithStore(this.form.cityLB,this.form.listToCity);"
                                                       value=">>"><br />
                                                <input type="button" onClick="movedataWithStoreLeft(this.form.listToCity,this.form.cityLB)"
                                                       value="<-">
                                            </td>
                                            <td>
                                                <s:select name="ToCityLB" id="listToCity"  list="#{}" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 70px"/>
                                                <!--                                                <select multiple size="10" name="orgFormVo.ToCityLB" id="listToCity" style="width:300px;height: 60px">
                                                                                                </select>-->
                                            </td>
                                            <!--                                            <td align="right" width="100px"><input type="checkbox" id="checkcity" name="checkdtl" value="4"/></td>-->
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr><td height="3px"></td></tr>
                            <tr>
                                <td>
                                    <table align="center">
                                        <tr><td><b>Store</b></td>
                                            <td></td>
                                            <td><b>Selection</b></td>
                                        </tr>
                                        <tr>
                                            <td >
                                                <s:select name="storeLB" id="liststore"  list="storeList" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 70px"/>
                                            </td>

                                            <td align="center" valign="middle">
                                                <input type="button" onClick="moveDataStoreRight(this.form.storeLB,this.form.listTostore)"
                                                       value="->"><br />
                                                <input type="button" onClick="moveALLDataStore(this.form.storeLB,this.form.listTostore);"
                                                       value=">>"><br />
                                                <input type="button" onClick="moveDataStoreLeft(this.form.listTostore,this.form.storeLB)"
                                                       value="<-">
                                            </td>
                                            <td>
                                                <s:select name="TostoreLB" id="listTostore"  list="#{}" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 70px"/>

                                            </td>
                                            <!--                                            <td align="right" width="100px"><input type="checkbox" id="checkstore" name="checkdtl" value="5"/></td>-->
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <s:hidden id="statusId" name="orgFormVo.statusId" />
                <s:hidden id="mstPromoId" name="orgFormVo.mstPromoId" />
                <s:hidden id="mstFormatlist" name="orgFormVo.mstFormatlist" />
                <s:hidden id="mstRegionList" name="orgFormVo.mstRegionList" />
                <s:hidden id="mstZoneList" name="orgFormVo.mstZoneList" />
                <s:hidden id="mststateList" name="orgFormVo.mststateList" />
                <s:hidden id="mstCityList" name="orgFormVo.mstCityList" />
                <s:hidden id="mstStoreList" name="orgFormVo.mstStoreList" />
                <tr>
                    <td>
                        <table align="center" width="30%" border="0" cellpadding="2" cellspacing="4">
                            <tr>
                                <td>
                                    <input type="button" value="Clear" id="btnClear" name="btnClear" class="btn"/>
                                </td>
                                <%--   <td>
                                       <s:submit value="Save" id="btnSave" name="btnSave" cssClass="btn" action="submitOrgnizationdtl" />
                                       <!--                                    <input type="button" value="Save" id="btnSave" name="btnSave" />-->
   </td> --%>
                                <td>
                                    <s:submit value="Submit" id="btnSubmit" name="btnSubmit" cssClass="btn" action="submitOrgnizationdtl" />

                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </s:form>
        </table>
    </body>
</html>