<%-- 
    Document   : initiateproposal
    Created on : Dec 24, 2012, 11:24:06 AM
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
        <title>Promotion Initiation</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
        <link href="css/thickbox.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>

        <script type="text/javascript" src="js/thickbox.js"></script>
        <script language="JavaScript" type="text/javascript">

            var version = navigator.appVersion;

            function showKeyCode(e) {
                var keycode = (window.event) ? event.keyCode : e.keyCode;

                if ((version.indexOf('MSIE') != -1)) {
                    if (keycode == 116) {
                        event.keyCode = 0;
                        event.returnValue = false;
                        return false;
                    }
                }
                else {
                    if (keycode == 116) {
                        return false;
                    }
                }
            }

        </script>


        <script type="text/javascript">
            
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });
                resetfields();              

                var promoId="";
                var isSubmit=$("#isSubmit").val();
                var sessionPromoId=$("#SessionmstPromoId").val();
              
                if(isSubmit!=undefined && isSubmit.length>0 && isSubmit=="0"){
                    //alert(sessionPromoId);
                    $.ajax({
                        url: "getPromoidWiseData?selpromoId="+sessionPromoId,
                        global: false,
                        type: "POST",
                        dataType:"json",
                        contanttype: 'text/json',
                        async:false,
                        error:function(){
                            alert("Can not connect to server");
                        },
                        success: function(data){
                         
                            $("#txtReqId").val(sessionPromoId);
                            $("#txtRequestName").val(data.reqName);
                            $("#txtremarks").val(data.remarks);
                            $("#eventSel").val(data.event);
                            $("#campaignSel").val(data.campaignData);
                            $("#marketingSel").val(data.mktg);                           
                            var categoryValues = data.category.split(",");
                            //alert(categoryValues);
                            $('#listToCategory option').each(function(ik, option){
                                $(option).remove();
                            });

                            var select = document.getElementById("listToCategory");                           
                            for(var index=0;index<categoryValues.length;index++){                               
                                categoryValues[index] = $.trim(categoryValues[index]);
                                //$("#categorysel option[value='"+categoryValues[index]+"']").attr("selected", "selected");
                                $("#categorysel").find("option[value='"+categoryValues[index]+"']").remove();
                                var optn = document.createElement("OPTION")
                                optn.text = categoryValues[index];
                                optn.value = categoryValues[index];
                                select.add(optn);
                          
                            }

                            var txtcategoryname = Array();
                            $('#listToCategory option').each(function(index){
                                txtcategoryname[index] ="'"+ $(this).text()+"'";
                            });
                            if(txtcategoryname==undefined || txtcategoryname.length==0){
                                alert("Please select Category.");
                                return false;
                            }
                            getsubCategoryBasedOnCategory(txtcategoryname);
                            var subcategoryValues = data.subcategory.split(",");
                            var select1 = document.getElementById("listTosubCategory");
                            for(var index=0;index<subcategoryValues.length;index++){
                                subcategoryValues[index] = $.trim(subcategoryValues[index]);
                                //$("#subcategorysel option[value='"+subcategoryValues[index]+"']").attr("selected", "selected");                             
                                //remove items which is in right side box.
                                $("#subcategorysel").find("option[value='"+subcategoryValues[index]+"']").remove();
                                var optn = document.createElement("OPTION")
                                optn.text = subcategoryValues[index];
                                optn.value = subcategoryValues[index];
                                select1.add(optn);
                            }
                        }
                    });
                }
                $("#btnDtlSave").click(function (data){

                    var txtRequestName=$("#txtRequestName").val();
                    txtRequestName = jQuery.trim(txtRequestName);
                    var txtremarks=$("#txtremarks").val();
                    txtremarks= jQuery.trim(txtremarks);


                    var checkblank = isBlank(txtRequestName,"Request Name ");
                    if(checkblank[0]==false){
                        alert(checkblank[1]);
                        $("#txtRequestName").focus();
                        return false;
                    }

                    var checklengthresp = checkLength(txtRequestName,"Request Name ",60);
                    if(checklengthresp[0]==false){
                        $("#txtRequestName").focus();
                        alert(checklengthresp[1]);
                        return false;
                    }

                    var event=$("#eventSel").val();
                    if(event=="-1"){
                        alert("Please Select Campaign!");
                        return false;
                    }

                    var campaign=$("#campaignSel").val();
                    if(campaign=="-1"){
                        alert("Please Select Objective!");
                        return false;
                    }

                    var marketingSel=$("#marketingSel").val();
                    //alert(marketingSel);
                    if(marketingSel=="-1"){
                        alert("Please Select Marketing type!");
                        return false;
                    }
                    
                    var categoryname = Array();
                    $('#listToCategory option').each(function(index){
                        categoryname[index] = $(this).text();
                    });
                    if(categoryname==undefined || categoryname.length==0){
                        alert("Please select Category.");
                        return false;
                    }
                    $("#ToCategoryLB").val(categoryname);
                    //alert(categoryname);

                    var subcategorysel = Array();
                    $('#listTosubCategory option').each(function(index){
                        subcategorysel[index] = $(this).text();
                    });
                    if(subcategorysel==undefined || subcategorysel.length==0){
                        alert("Please select Sub Category.");
                        return false;
                    }
                   
                    $("#ToSubCategoryLB").val(subcategorysel);
                    //alert(subcategorysel);


                    var txtcategoryname = Array();
                    $('#listToCategory option').each(function(index){
                        txtcategoryname[index] ="'"+ $(this).text()+"'";
                    });
                    if(txtcategoryname==undefined ||txtcategoryname.length==0 ){
                        alert("Please select Category.");
                        return false;
                    }
                    $("#hdncategory").val(txtcategoryname);
                    var cate =$("#hdncategory").val();
                    var checklengCategory = checkLength(cate,"Please remove some selected category.",25000);
                    if(checklengCategory[0]==false){
                        $("#listToCategory").focus();
                        txtcategoryname = Array();
                        alert(checklengCategory[1]);
                        return false;
                    }
                    $("#hdncategory").val(txtcategoryname);
                    var txtsubcategoryname = Array();

                    $('#listTosubCategory option').each(function(index){
                        txtsubcategoryname[index] ="'"+ $(this).text()+"'";
                    });
                    if(txtsubcategoryname==undefined || txtsubcategoryname.length==0){
                        alert("Please select Sub Category.");
                        return false;
                    }
                    $("#hdnSubCategory").val(txtsubcategoryname);
                    var subcat = $("#hdnSubCategory").val();
                    var checkLengSubCategory = checkLength(subcat," Please remove some selected sub category.",25000);
                    if(checkLengSubCategory[0]==false){
                        $("#listTosubCategory").focus();
                        txtsubcategoryname = Array();
                        alert(checkLengSubCategory[1]);
                        return false;
                    }
                      $("#hdnSubCategory").val(txtsubcategoryname);

                    txtremarks=  txtremarks.replace(/(\r\n|\n|\r)/gm," ");
                    $("#txtremarks").val(txtremarks);
                    var checkblank = isBlank(txtremarks,"Remarks ");
                    if(checkblank[0]==false){
                        alert(checkblank[1]);
                        $("#txtremarks").focus();
                        return false;
                    }

                    var checklengthresp = checkLength(txtremarks,"Remarks ",1000);
                    if(checklengthresp[0]==false){
                        $("#txtremarks").focus();
                        alert(checklengthresp[1]);
                        return false;
                    }
                    if(promoId.length>0){
                        $("#txtReqId").val(promoId);
                    }
                    
                });
               
                function fillCategoryData(){
                    $.ajax({
                        url: "fillcategroy",
                        global: false,
                        type: "POST",
                        dataType:"json",
                        contanttype: 'text/json',
                        async:false,
                        error:function(){
                            alert("Can not connect to server");
                        },
                        success: function(data){

                            $('#categorysel option').each(function(ik, option){
                                $(option).remove();
                            });
                            //alert(data.rows.categroylist);
                            var selectregion = document.getElementById("categorysel");
                            if(data!=null){
                                if(data.rows.categroylist!=null){
                                    for(var i=0; i<data.rows.categroylist.length ; i++){
                                        var optnRegion = document.createElement("OPTION")
                                        optnRegion.text = data.rows.categroylist[i];
                                        optnRegion.value = data.rows.categroylist[i];
                                        selectregion.add(optnRegion);
                                    }
                                }
                            }

                        }
                    });
                }

                function resetfields(){
                    fillCategoryData();
                    $("#txtRequestName").val("");
                    $("#txtExistingReq").val("");
                    $("#hdnSubCategory").val("");
                    $("#hdncategory").val("");
                    // $("#checkcategory").attr('checked',false);
                    $("#txtremarks").val("");
                    $("#eventSel").val("-1");
                    $("#marketingSel").val("-1");
                    $("#campaignSel").val("-1");
                    $("#listToCategory").empty();
                    $("#subcategorysel").empty();
                    $("#listTosubCategory").empty();
                    $("#txtRequestName").attr("disabled",false);
                    $("#txtremarks").attr("disabled",false);
                    $("#eventSel").attr("disabled",false);
                    $("#marketingSel").attr("disabled",false);
                    $("#categorysel").attr("disabled",false);
                    $("#subcategorysel").attr("disabled",false);
                    $("#txtremarks").attr("disabled",false);
                    $("#txtExistingReq").attr("disabled",false);
                    $("#btnDtlSave").attr("disabled",false);
                    //$("#btnGo").attr("disabled",true);
                    //$("#promotionSel").attr("disabled",true);
                    //document.getElementById('capturedtl').style.display = 'none';


                }
                
                $("#btnDtlReset").click(function (data){
                    promoId="";
                    $("#txtReqId").val("");
                    resetfields();
                    $("#msg").html('');
                });

                $("#btnGo").click(function (data){
                    var promotype =$("#promotionSel").val();
                    if(promotype==-1){
                        alert("Please Select Promotion Type.");
                        return false;
                    }
                   
                });
                
                $("#txtExistingReq").autocomplete({
                    minLength : 1,
                    source : function(request, response) {
                        var temp=    $.ajax({
                            url : "fillpromoFlex?txtExistingReq="+$("#txtExistingReq").val() ,
                            dataType : "json",
                            type:"POST",
                            data : {name_startsWith : request.term},
                            success : function(data) {
                                                              
                                response($.map(data.results,function(item) {
                                    return {id : item.id,label : item.name};

                                }));
                            }
                        });
                    }, select: function( event, ui ) {
                        promoId = ui.item.id;
                        $.ajax({
                            url: "getPromoidWiseData?selpromoId="+promoId,
                            global: false,
                            type: "POST",
                            dataType:"json",
                            contanttype: 'text/json',
                            async:false,
                            error:function(){
                                alert("Can not connect to server");
                            },
                            success: function(data){
                                // alert("server data : "+data.category);
                                $("#txtRequestName").val(data.reqName);
                                $("#txtremarks").val(data.remarks);
                                $("#eventSel").val(data.event);
                                $("#marketingSel").val(data.mktg);
                                $("#categorysel").val(data.category);
                                $("#subcategorysel").val(data.subcategory);
                            }
                        });
                    }
                });

                //                $("#categorysel").change(function(){
                //                    //                    var txtcategoryname = $("#categorysel option:selected").val();
                //                    //                    alert("Phase ID : "+txtcategoryname);
                //                    //                    $("#message").html('');
                //
                //                    var txtcategoryname = Array();
                //                    $('#categorysel option:selected').each(function(index){
                //                        txtcategoryname[index] = $(this).text();
                //                    });
                //                    $("#message").html('');
                //                    $.ajax({
                //                        url: "fillsubcategroy?txtcategoryname="+txtcategoryname,
                //                        global: false,
                //                        type: "POST",
                //                        dataType:"json",
                //                        contanttype: 'text/json',
                //                        async:false,
                //                        error:function(){
                //                            alert("Can not connect to server");
                //                        },
                //                        success: function(data){
                //                            //  alert(data);
                //                            $('#subcategorysel option').each(function(ik, option){
                //                                $(option).remove();
                //                            });
                //
                //                            var select = document.getElementById("subcategorysel");
                //                            var newoption = document.createElement('option');
                //
                //                            // newoption.text = "---Select Sub Category---";
                //                            //newoption.value = -1;
                //                            //select.add(newoption);
                //
                //
                //                            if(data!=null){
                //                                for(var i=0; i<data.rows.subcategroylist.length ; i++){
                //                                    var optn = document.createElement("OPTION")
                //                                    optn.text = data.rows.subcategroylist[i];
                //                                    optn.value = data.rows.subcategroylist[i];
                //                                    select.add(optn);
                //                                }
                //                            }
                //                        }
                //                    });
                //                });

               
            });
            function getsubCategoryBasedOnCategory(txtcategoryname){
                $.ajax({
                    url: "fillsubcategroy?txtcategoryname="+txtcategoryname,
                    global: false,
                    type: "POST",
                    dataType:"json",
                    contanttype: 'text/json',
                    async:false,
                    error:function(){
                        alert("Can not connect to server");
                    },
                    success: function(data){

                        $('#subcategorysel option').each(function(ik, option){
                            $(option).remove();
                        });

                        var selectregion = document.getElementById("subcategorysel");
                        if(data!=null){
                            if(data.rows.subcategroylist!=null){
                                for(var i=0; i<data.rows.subcategroylist.length ; i++){
                                    var optnRegion = document.createElement("OPTION")
                                    optnRegion.text = data.rows.subcategroylist[i];
                                    optnRegion.value = data.rows.subcategroylist[i];
                                    selectregion.add(optnRegion);
                                }
                            }else{
                                alert("No Sub Category available for selected Category.");
                            }

                        }
                     
                    }
                });

            }
            function moveDataRightWithCategory(tbFrom, tbTo){
                //$('#subcategorysel').empty();                
                $("#listTosubCategory").empty();
                moveData(tbFrom,tbTo);                
                var txtcategoryname = Array();
                $('#listToCategory option').each(function(index){
                    txtcategoryname[index] ="'"+ $(this).text()+"'";
                });
                if(txtcategoryname==undefined || txtcategoryname.length==0){
                    alert("Please select Category.");
                    return false;
                }
                getsubCategoryBasedOnCategory(txtcategoryname);
            }
                
            function moveAllDataWithCategory(tbFrom, tbTo){
                // $('#subcategorysel').empty();
                $("#listTosubCategory").empty();
                moveAllDataRight(tbFrom,tbTo);
                var txtcategoryname = Array();
                $('#listToCategory option').each(function(index){
                    txtcategoryname[index] ="'"+ $(this).text()+"'";
                });
                if(txtcategoryname==undefined || txtcategoryname.length==0){
                    alert("Please select Category.");
                    return false;
                }
                getsubCategoryBasedOnCategory(txtcategoryname);
            }
            function movedataWithCategoryLeft(tbFrom, tbTo){
                //$('#subcategorysel').empty();
                $("#listTosubCategory").empty();
                moveData(tbFrom,tbTo);
                var txtcategoryname = Array();
                $('#listToCategory option').each(function(index){
                    txtcategoryname[index] ="'"+ $(this).text()+"'";
                });
                if(txtcategoryname==undefined || txtcategoryname.length==0){
                    alert("Please select Category.");
                    $('#subcategorysel').empty();
                    return false;
                }
                getsubCategoryBasedOnCategory(txtcategoryname);
            }
          
            //                $("#checkcategory").click(function(){
            //
            //
            //                    $('#subcategorysel').empty();
            //                    //$("#listToCategory").empty();
            //                    $("#listTosubCategory").empty();
            //                    $('#subcategorysel option').each(function(ik, option){
            //                        $(option).remove();
            //                    });
            //
            //                    $("#message").html('');
            //                    getsubCategoryBasedOnCategory();
            //
            //
            //                });

         
        </script>

    </head>
    <body onload="JavaScript:document.body.focus();" onkeydown="return showKeyCode(event)">
        <jsp:include page="/jsp/menu/menuPage.jsp" />

        <!--            <div id="middle_cont">-->
        <table width="100%" border="0" cellspacing="2" cellpadding="2"  align="center">
            <tr><td class="errorText" align="right">(Fields marked with a * are mandatory.)</td></tr>
            <tr>
                <td width="100%" align="center" >
                    <div id="msg">
                        <s:actionmessage cssClass="successText"/>
                        <s:actionerror cssClass="errorText"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <table width="100%" border="0" cellspacing="2" cellpadding="2"  align="center">
                        <tr>
                            <td align="left">
                                <table width="80%" border="0" align="center" cellpadding="2" cellspacing="4">
                                    <s:form id="initiate" name="initiate" action="donothing" method="POST">

                                        <s:hidden  name="formVo.SessionmstPromoId" id="SessionmstPromoId" value="%{formVo.SessionmstPromoId}" />
                                        <tr>
                                            <td><h1>Requestor Details</h1></td>
                                        </tr>
                                        <tr>
                                            <td  align="right">Name</td>
                                            <td align="left"><input type="text" name="txtempName" id="txtempName"  value="<%= session.getAttribute(WebConstants.EMP_NAME)%>" disabled/></td>
                                            <td align="right">Employee Code</td>
                                            <td align="left"><input type="text" name="txtempCode" id="txtempCode" value="<%= session.getAttribute(WebConstants.USER_ID)%>" disabled/></td>
                                            <td align="center" >
                                                <a href ="#" class="download-sample " onclick="tb_show( '', 'viewHelpInitiator?height=400&width=650');">
                                                    Help
                                                </a>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td  align="right">Zone</td>
                                            <td align="left"><input type="text" name="txtZone" id="txtZone" value="<%= session.getAttribute(WebConstants.ZONE_NAME)%>" disabled/></td>
                                            <td  align="right">Contact Number</td>
                                            <td align="left"><input type="text" name="txtcontactNo" id="txtcontactNo" value="<%= session.getAttribute(WebConstants.CONTACT_NO)%>" disabled/></td>
                                        </tr>
                                        <tr>
                                            <td align="right">Site Code</td>
                                            <td align="left"><input type="text" name="txtsiteCode" id="txtsiteCode" value="<%= session.getAttribute(WebConstants.STORE_CODE)%>" disabled/></td>
                                            <td  align="right">Email ID</td>
                                            <td align="left"><input type="text" name="txtemailId" id="txtemailId" value="<%= session.getAttribute(WebConstants.EMAIL_ID)%>" disabled/></td>
                                        </tr>
                                        <tr>
                                            <td  align="right">Site Description</td>
                                            <td align="left"><input type="text" name="txtsiteDesc" id="txtsiteDesc" value="<%= session.getAttribute(WebConstants.STORE_DESC)%>" disabled/></td>
                                            <td  align="right">Location</td>
                                            <td align="left"><input type="text" name="txtLocation" id="txtLocation" value="<%= session.getAttribute(WebConstants.LOCATION)%>" disabled/></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>

                            <tr>
                                <td align="left">
                                    <table width="80%" border="0" align="center" cellpadding="2" cellspacing="4">
                                        <tr>
                                            <td ><h1>Promotion Details</h1></td>
                                        </tr>
                                        <!--                                        <tr>
                                                                                    <td colspan="1" align="right">Update Existing Request</td>
                                                                                    <td><input type="text" name="formVo.txtExistingReq" id="txtExistingReq" title="Search With Request Name"/>  </td>
                                                                                </tr>-->

                                        <tr>
                                            <td align="right">Request Name :<span class="errorText">&nbsp;*</span></td>
                                            <td align="left"><input type="text" id="txtRequestName" name="formVo.txtRequestName" /></td>
                                            <td  align="right">Objective :<span class="errorText">&nbsp;*</span></td>
                                            <td align="left"><s:select name="formVo.campaignSel" id="campaignSel"  list="campaignMap" headerKey="-1" headerValue="---Select Objective---" value="---Select Objective---" cssClass="dropdown"/></td>
                                        </tr>
                                        <tr>
                                            <td  align="right">Campaign :<span class="errorText">&nbsp;*</span></td>
                                            <td align="left"><s:select name="formVo.eventSel" id="eventSel"  list="eventMap" headerKey="-1" headerValue="---Select Campaign---" value="---Select Campaign---" cssClass="dropdown"/></td>
                                            <td align="right">Marketing Type : <span class="errorText">&nbsp;*</span></td>
                                            <td align="left"><s:select name="formVo.marketingSel" id="marketingSel"  list="mktgMap" headerKey="-1" headerValue="---Select Marketing Type---" value="---Select Marketing Type---" cssClass="dropdown"/></td>
                                            <td align="center" valign="middle"></td>
                                        </tr>
                                        <tr>
                                            <td align="right">Vendor Backing</td>
                                            <td align="left"><s:select name="formVo.vendorbackingsel" id="vendorbackingsel" list="#{'0':'NO', '1':'YES'}" headerKey="-1" value="---Select Vendor Backing---" cssClass="dropdown" /></td>
                                        </tr>
                                        <tr><td height="10px" ></td></tr>
                                        <tr align="center">
                                            <td  align="right">Category <span class="errorText">&nbsp;*</span></td>
                                            <td align="left">                                                 
                                                <s:select name="categorysel" id="categorysel"  list="CategoryList" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 70px"/>
                                            </td>
                                            <td align="left" valign="middle">
                                                <input type="button" onClick="moveDataRightWithCategory(this.form.categorysel,this.form.listToCategory);"
                                                       value=" -> " ><br />
                                                <input type="button" onClick="moveAllDataWithCategory(this.form.categorysel,this.form.listToCategory);"
                                                       value=" >> "><br />
                                                <input type="button" onClick="movedataWithCategoryLeft(this.form.listToCategory,this.form.categorysel)"
                                                       value=" <- ">
                                            </td>
                                            <td align="left">
                                                <s:select name="ToCategoryLB" id="listToCategory"  list="#{}" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 70px"/>
                                            </td>
                                            <!--                                            <td align="left"><input type="checkbox" id="checkcategory" name="checkdtl" value="5"/>Show</td>-->
                                        </tr>
                                        <tr><td height="10px" ></td></tr>
                                        <tr align="center">
                                            <td  align="right">Sub Category <span class="errorText">&nbsp;*</span></td>
                                            <td align="left">

                                                <s:select name="subcategorysel" id="subcategorysel"  list="SubCategoryList" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 70px"/>
                                            </td>
                                            <td align="left" valign="middle">
                                                <input type="button" onClick="moveData(this.form.subcategorysel,this.form.listTosubCategory);"
                                                       value=" -> "><br />
                                                <input type="button" onClick="moveAllDataRight(this.form.subcategorysel,this.form.listTosubCategory);"
                                                       value=" >> "><br />
                                                <input type="button" onClick="moveData(this.form.listTosubCategory,this.form.subcategorysel)"
                                                       value=" <- ">
                                            </td>
                                            <td align="left">
                                                <s:select name="ToSubCategoryLB" id="listTosubCategory"  list="#{}" cssClass="dropdown" multiple="true" size="4" cssStyle="width:300px;height: 70px"/>
                                            </td>
                                            <td></td>
                                        </tr>
                                        <tr>
                                            <td  align="right">Remark : <span class="errorText">&nbsp;*</span></td>
                                            <td align="left" colspan="4"><textarea id="txtremarks" name="formVo.txtremarks"  rows="3" cols="25" style="width: 70%;height: 20%"></textarea></td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr><td height="15px" ></td></tr>
                            <%--<tr>
                                <td id="capturedtl">
                                    <table width="40%%"  border="0" align="center" cellpadding="2" cellspacing="4">
                                        <tr>
                                            <td align="right">Promotion Type</td>
                                            <td align="left"><s:select name="formVo.promotionSel" id="promotionSel"  list="promotionMap" headerKey="-1" headerValue="---Select Promotion Type---" value="---Select Promotion Type---" cssClass="dropdown" /></td>
                                            <td align="left">
                                                <s:submit align="left" action="redirect_promotype" type="submit" id="btnGo" name="btnGo"  value="Next Promo" cssClass="btn" />
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            --%>
                            <tr>
                                <td align="center">
                                    <table width="30%" border="0" align="center" cellpadding="2" cellspacing="4">
                                        <tr>
                                            <td align="right">
                                                <s:hidden id="txtReqId" name="formVo.txtReqId" value="%{formVo.txtReqId}" />
                                                <s:hidden id="isSubmit" name="formVo.isSubmit" value="%{formVo.isSubmit}" />
                                                <s:hidden id="hdncategory" name="formVo.hdncategory" value="%{formVo.hdncategory}" />
                                                <s:hidden id="hdnSubCategory" name="formVo.hdnSubCategory" value="%{formVo.hdnSubCategory}" />
                                                <s:hidden id="ToCategoryLB" name="formVo.ToCategoryLB" value="%{formVo.ToCategoryLB}" />
                                                <s:hidden id="ToSubCategoryLB" name="formVo.ToSubCategoryLB" value="%{formVo.ToSubCategoryLB}" />
                                                <s:submit align="left" action="submitPromotionReq" type="submit" id="btnDtlSave" name="btnDtlSave"  Value="Save" cssClass="btn" />
                                                <!--                                                <input align="left" type="submit" id="btnDtlSave" name="btnDtlSave"  Value="Save" Class="button" />-->
                                            </td>
                                            <td align="left">
                                                <input align="left" type="button" id="btnDtlReset" name="btnDtlReset"  Value="Clear" Class="btn" />
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>

                            <tr><td height="15px" ></td></tr>
                            <%--   <tr>
                                   <td align="center">
                                       <table>
                                           <tr>
                                               <td>
                                                   Upload File
                                               </td>
                                               <td>
                                                   <s:file id ="promotionFile" name="formVo.promotionFileUpload" ></s:file>
                                               </td>
                                               <td></td>
                                               <td align="center">
                                                   <a href ="downloadTemplatePromotionFile" class="download-sample ">
                                                       Sample File
                                                   </a>
                                               </td>
                                               <td></td>
                                               <td align="center" id="tdErrorFile">
                                                   <s:a  href="%{formVo.errorfilePath}" class="downloadError" id="downloadErrorFile" > Status File </s:a>
                                               </td>
                                           </tr>
                                       </table>

                                </td>
                            </tr>
                            --%>

                        </s:form>
                    </table>
                </td>

            </tr>

            <tr><td height="15px" ></td></tr>

        </table>
        <!--</div>-->

    </body>
</html>
