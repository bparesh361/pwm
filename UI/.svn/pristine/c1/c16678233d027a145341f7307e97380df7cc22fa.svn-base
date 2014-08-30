<%-- 
    Document   : createuser
    Created on : Dec 12, 2012, 12:23:19 AM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create User</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script type="text/javascript" src="js/ddsmoothmenu.js"></script>
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>
        <script src="js/jquery.ui.position.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>

        <script type="text/javascript">
            jQuery(document).ready(function(){

             
                //ONLOAD Start
                //Preventing caching for ajax calls
                enableRedirectLinkbasedOnResp();
                function enableRedirectLinkbasedOnResp(){
                    var isredirect=$("#isredirect").val();                  
                    if(isredirect!=undefined && isredirect.length>0 && isredirect=="1"){
                        $("#showRedirect").show();
                    }else{
                        $("#showRedirect").hide();
                    }
                }
                reselFields();
                function reselFields(){
                    $("#txtsitedesc").attr("disabled",true);
                    $("#txtregion").attr("disabled",true);
                    $("#txtformat").attr("disabled",true);
                    $("#txtlocation").attr("disabled",true);
                    $("#txtcity").attr("disabled",true);
                    $("#txtzone").attr("disabled",true);
                    $("#txtpassword").attr("disabled",true);
                    $("#txtuserId").attr("disabled",true);
                    $("#txtpassword").val("india123");
                    $("#btnSubmit").attr("disabled",false);
                    $("#btnUpdate").attr("disabled",true);
                    $("#txtuserempName").val('');
                    $("#txtempCode").val('');
                    $("#txtempname").val('');
                    $("#txtcontact").val('');
                    $("#txtemail").val('');
                    $("#txtreporting").val('');


                    document.getElementById('tbho').style.display = 'none';
                    document.getElementById('hoSel').style.display = 'none';
                    $("#hoSel").val("-1");
                    $("#ho1").attr('checked',false);

                    document.getElementById('tbstore').style.display = 'none';
                    document.getElementById('siteSel').style.display = 'none';
                    document.getElementById('tbdept').style.display = 'none';
                    document.getElementById('deptSel').style.display = 'none';
                    $("#siteSel").val("-1");
                    $("#store1").attr('checked',false);
                    $("#deptSel").val('');
                    document.getElementById('tbzone').style.display = 'none';
                    document.getElementById('ZoneSel').style.display = 'none';
                    $("#formatSel").val("-1");
                    $("#ZonedescSel").val('');
                    $("#ZoneSel").val("-1");
                    $("#zone1").attr('checked',false);

                    $("#roleSel").val("-1");
                    $("#txtsitedesc").val("");
                    $("#txtregion").val("");
                    $("#txtformat").val("");
                    $("#txtlocation").val("");
                    $("#txtcity").val("");
                    $("#txtzone").val("");
                    $("#txtuserId").val("");
                  
                    $("#userPass").val("");
                   

                    idEmp="";
                }
                $.ajaxSetup({ cache: false });             
               
                //ONLOAD FINISH
                //Existing emp
                var idEmp="";
                $("#txtuserempName").autocomplete({
                    minLength : 1,
                    //delay:10,
                    //scrollHeight:100,
                    source : function(request, response) {
                        var temp=    $.ajax({
                            url : "getEmployeeForFlexBox?txtuserempName="+$("#txtuserempName").val() ,
                            dataType : "json",
                            type:"POST",
                            data : {name_startsWith : request.term},
                            success : function(data) {                                   // alert(data);
                                response($.map(data.results,function(item) {
                                    return {id : item.id,label : item.name};

                                }));
                            }
                        });
                    }, select: function( event, ui ) {
                        idEmp = ui.item.id;
                    }
                });


                $("#hoSel").change(function(){
                    var SiteCode=$("#hoSel option:selected").val();
                    if(SiteCode !=-1){
                        getOrgDetailBySiteCode(SiteCode);
                    }
                });

                $("#siteSel").change(function(){
                    var SiteCode=$("#siteSel option:selected").val();
                    if(SiteCode !=-1){             
                        getOrgDetailBySiteCode(SiteCode);
                    }
                });             

                $("#ZoneSel").change(function(){
                    var SiteCode=$("#ZoneSel option:selected").val();
                    if(SiteCode !=-1){                       
                        getOrgDetailBySiteCode(SiteCode);
                    }
                });
                function getOrgDetailBySiteCode(SiteCode){
                    $.ajax({
                        url: "getorgdtlBysiteCode?SiteCode="+SiteCode,
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
                            $("#txtsitedesc").val(data.desc);
                            $("#txtregion").val(data.region);
                            $("#txtformat").val(data.format);
                            $("#txtlocation").val(data.location);
                            $("#txtcity").val(data.city);
                            $("#txtzone").val(data.zone);
                        }
                    });
                }
                function getformatwiseZoneSites(format){
                    $.ajax({
                        url: "getZoneBasedonFormat?format="+format,
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
                            //alert(data);
                            $('#ZoneSel option').each(function(ik, option){
                                $(option).remove();
                            });

                            var select = document.getElementById("ZoneSel");
                            var newoption = document.createElement('option');

                            if(data!=null){
                                if(data.rows.zonenameList.length>0){
                                    for(var i=0; i<data.rows.zonenameList.length ; i++){
                                        var optn = document.createElement("OPTION")
                                        optn.text = data.rows.zonenameList[i];
                                        optn.value = data.rows.zoneIdList[i];
                                        select.add(optn);
                                    }
                                }else{
                                    newoption.text = "--- Select Zone Site ---";
                                    newoption.value ="-1";
                                    select.add(newoption);
                                    alert("No Zone mapped with selected Format "+format);
                                }
                            }else{
                                newoption.text = "--- Select Zone Site ---";
                                newoption.value ="-1";
                                select.add(newoption);
                                alert("No Zone mapped with selected Format "+format);
                            }
                        }
                    });
                }

                function getzonedesc(store){
                    $.ajax({
                        url: "getZoneDescBasedonZone?store="+store,
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
                            //alert(data);
                            $('#ZonedescSel option').each(function(ik, option){
                                $(option).remove();
                            });

                            var select = document.getElementById("ZonedescSel");
                            var newoption = document.createElement('option');

                            if(data!=null){
                                if(data.rows.zonenameList.length>0){
                                    for(var i=0; i<data.rows.zonenameList.length ; i++){
                                        var optn = document.createElement("OPTION")
                                        optn.text = data.rows.zonenameList[i];
                                        optn.value = data.rows.zoneIdList[i];
                                        select.add(optn);
                                    }
                                }else{
                                    newoption.text = "--- Select Zone Site Desc---";
                                    newoption.value ="-1";
                                    select.add(newoption);
                                    alert("No Zone mapped with selected Format "+store);
                                }
                            }else{
                                newoption.text = "--- Select Zone Site Desc---";
                                newoption.value ="-1";
                                select.add(newoption);
                                alert("No Zone mapped with selected Format "+store);
                            }
                        }
                    });
                }
                $("#formatSel").change(function(){
                    var format=$("#formatSel option:selected").val();
                    if(format !=-1){
                        getformatwiseZoneSites(format);
                    }
                    var store=$("#ZoneSel option:selected").val();
                    if(store !=-1){
                        getzonedesc(store);
                        getOrgDetailBySiteCode(store);
                    }
                });
                $("#ZoneSel").change(function(){
                    var store=$("#ZoneSel option:selected").val();
                    if(store !=-1){
                        getzonedesc(store);
                    }
                });


                $("#btnView").click(function (){

                    if(idEmp.length==0){
                        alert("Please Select Employee!");
                        return false;
                    }

                    $.ajax({
                        url: "getUserdetailforUpdate?txtempid="+idEmp,
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
                            $("#txtempCode").val(data.rows.empCode);
                            $("#txtempname").val(data.rows.srtEmpName);
                            $("#txtcontact").val(data.rows.strContactNo);
                            $("#txtemail").val(data.rows.strEmail);
                            $("#txtreporting").val(data.rows.strReportingTo);

                            $("#btnSubmit").attr("disabled",true);
                            $("#btnUpdate").attr("disabled",false);
                            var locationId=data.rows.strLocationId;
                            if(locationId==1){
                                document.getElementById('tbho').style.display = '';
                                document.getElementById('hoSel').style.display = '';
                                $("#hoSel").val(data.rows.strStoreId);
                                getOrgDetailBySiteCode(data.rows.strStoreId);
                                $("#ho1").attr('checked',true);
                            }else if(locationId==2){
                                document.getElementById('tbstore').style.display = '';
                                document.getElementById('siteSel').style.display = '';
                                 $("#siteSel").val(data.rows.strStoreId);
                                $("#store1").attr('checked',true);
                                getOrgDetailBySiteCode(data.rows.strStoreId);
                                document.getElementById('tbdept').style.display = 'none';
                                document.getElementById('deptSel').style.display = 'none';
                                var StoreValues = data.rows.deptIdlist;                                
                                for(var index=0;index<StoreValues.length;index++){
                                    $("#deptSel option[value='"+StoreValues[index]+"']").attr("selected", "selected");
                                }                      
                            }else if(locationId==3){
                                document.getElementById('tbzone').style.display = '';
                                document.getElementById('ZoneSel').style.display = '';
                                $("#formatSel").val(data.rows.strformat);
                                getformatwiseZoneSites(data.rows.strformat);
                                getzonedesc(data.rows.strStoreId);
                                $("#ZonedescSel").val(data.rows.strzonesitedesc);
                                $("#ZoneSel").val(data.rows.strStoreId);
                                $("#zone1").attr('checked',true);
                            }
                            $("#roleSel").val(data.rows.strRoleId);
                            $("#txtsitedesc").val(data.rows.strStoreName);
                            $("#txtregion").val(data.rows.strRegion);
                            $("#txtformat").val(data.rows.strformat);
                            $("#txtlocation").val(data.rows.strLocationName);
                            $("#txtcity").val(data.rows.strCity);
                            $("#txtzone").val(data.rows.strZone);
                            $("#txtuserId").val(data.rows.empCode);                          
                            $("#txtpassword").val(data.rows.strPassword);
                            $("#userPass").val(data.rows.strPassword);

                        }
                    });
                });


                $("#btnUpdate").click(function (){
                    var validateCall=validateFields();
                    if(validateCall[0]==false){
                        alert(validateCall[1]);
                        return false;
                    }
                    $("#selempId").val(idEmp);
                });

                function validateFields(){
                    var txtempCode=$("#txtempCode").val();
                    var txtempname=$("#txtempname").val();
                    var txtcontact=$("#txtcontact").val();
                    var txtemail=$("#txtemail").val();

                    var checkblank = isBlank(txtempCode,"Employee Code ");
                    if(checkblank[0]==false){                        
                        $("#txtempCode").focus();
                        return [false,checkblank[1]];
                    }
                    if(!isNumeric(txtempCode)){                        
                        $("#txtempCode").val("");
                        $("#txtempCode").focus();
                        return [false,"Employee Code should be numeric."];
                    }
                    var checklengthresp = checkLength(txtempCode,"Employee Code ",20);
                    if(checklengthresp[0]==false){
                        $("#txtempCode").focus();                        
                        return [false,checklengthresp[1]];
                    }

                    //Employee Name
                    var checkblank = isBlank(txtempname,"Employee Name ");
                    if(checkblank[0]==false){                       
                        $("#txtempname").focus()
                        return [false,checkblank[1]];
                    }                    
                    var checklengthresp = checkLength(txtempname,"Employee Name ",80);
                    if(checklengthresp[0]==false){
                        $("#txtempname").focus();                     
                        return [false,checklengthresp[1]];
                    }

                    //Contact No
                    var checkblank = isBlank(txtcontact,"Contact No ");
                    var checklengthContact = checkLength(txtcontact,"Contact No ",10);
                    if(checkblank[0]==false){                        
                        $("#txtcontact").focus();
                        return [false,checkblank[1]];
                    }else if(!isNumeric(txtcontact)){                        
                        $("#txtcontact").val("");
                        $("#txtcontact").focus();
                        return [false,"Contact Number should be numeric."];
                    }else if(checklengthContact[0]==false){
                        $("#txtcontact").focus();                        
                        return [false,checklengthContact[1]];
                    }else if(txtcontact.length<10){
                        $("#txtcontact").focus();
                        return [false,"Contact number should not be less than 10."];
                    }

                    //email
                    var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
                    var checkblanktxtemail = isBlank(txtemail,"Email Id ");
                    var checklengthtxtemail = checkLength(txtemail,"Email Id ",50);
                    if(checkblanktxtemail[0]==false){                        
                        $("#txtemail").focus();
                        return [false,checkblanktxtemail[1]];
                    }else if(!emailReg.test(txtemail)) {
                        $("#txtemail").val("");                        
                        return [false,'Enter Valid Email ID!!'];
                    }else if(checklengthtxtemail[0]==false){
                        $("#txtemail").focus();                        
                        return [false,checklengthtxtemail[1]];
                    }


                    //RADIO BUTTON VALIDATION
                    var radios = document.getElementsByName("show_Site");
                    var formValid = false;
                    var i = 0;
                    while (!formValid && i < radios.length) {
                        if (radios[i].checked) formValid = true;
                        i++;
                    }
                    if (!formValid){                        
                        return [false,"Atleast select one Organization Type option!"];
                    }

                    //ORGANIZATION VALIDATION
                    var zone=$("#ZoneSel option:selected").val();
                    var site=$("#siteSel option:selected").val();
                    var ho=$("#hoSel option:selected").val();
                    //var dept=$("#deptSel option:selected").val();
                    var role=$("#roleSel option:selected").val();
                    var dept1=$("#deptSel").val();
                    //alert(dept1);

                    var hoChekc=$("#ho1").attr('checked');
                    if(hoChekc==true){
                        if(ho=="-1"){                            
                            return [false,"Please Select Ho Site! "];
                        }
                        $("#SendSiteCode").val(ho);
                    }
                    var storeCheck=$("#store1").attr('checked');
                    if(storeCheck==true){
                        if(site=="-1"){                            
                            return [false,"Please Select Site! "];
                        }
//                        if(dept1==undefined){
//                            return [false,"Please Select Department! "];
//                        }
                          var deptname = Array();
                        $('#deptSel option').each(function(index){
                            deptname[index] = $(this).val();
                        });
                        $("#SendDeptCode").val(deptname);
                        $("#SendSiteCode").val(site);
                    }

                    var zoneCheck=$("#zone1").attr('checked');
                    if(zoneCheck==true){
                        if(zone=="-1"){                            
                            return [false,"Please Select Zone Site! "];
                        }
                        $("#SendSiteCode").val(zone);
                    }

                    if(role=="-1"){                        
                        return [false,"Please Select Role! "];
                    }
                    return [true,''];
                }
                $("#btnReset").click(function (){
                 $("#message").html('');
                    reselFields();
                });
                $("#btnSubmit").click(function (){
                 
                    var validateCall=validateFields();
                    if(validateCall[0]==false){
                        alert(validateCall[1]);
                        return false;
                    }

                    //                    //PERSONAL DETAIL VALIDATION
                    //                    var txtempCode=$("#txtempCode").val();
                    //                    var txtempname=$("#txtempname").val();
                    //                    var txtcontact=$("#txtcontact").val();
                    //                    var txtemail=$("#txtemail").val();
                    //
                    //                    //                    txtempCode = jQuery.trim(txtempCode);
                    //                    //                    txtempname = jQuery.trim(txtempname);
                    //                    //                    txtcontact = jQuery.trim(txtcontact);
                    //                    //                    txtemail = jQuery.trim(txtemail);
                    //
                    //                    //Employee code
                    //                    var checkblank = isBlank(txtempCode,"Employee Code ");
                    //                    if(checkblank[0]==false){
                    //                        alert(checkblank[1]);
                    //                        $("#txtempCode").focus();
                    //                        return false;
                    //                    }
                    //                    if(!isNumeric(txtempCode)){
                    //                        alert("Employee Code should be numeric.");
                    //                        $("#txtempCode").val("");
                    //                        $("#txtempCode").focus();
                    //                        return false;
                    //                    }
                    //                    var checklengthresp = checkLength(txtempCode,"Employee Code ",20);
                    //                    if(checklengthresp[0]==false){
                    //                        $("#txtempCode").focus();
                    //                        alert(checklengthresp[1]);
                    //                        return false;
                    //                    }
                    //
                    //                    //Employee Name
                    //                    var checkblank = isBlank(txtempname,"Employee Name ");
                    //                    if(checkblank[0]==false){
                    //                        alert(checkblank[1]);
                    //                        $("#txtempname").focus()
                    //                        return false;
                    //                    }
                    //                    //                    if(isAlphaNumeric(txtempname)){
                    //                    //                        alert("Employee Name must be alpha numeric");
                    //                    //                        $('#txtempname').val("");
                    //                    //                        $('#txtempname').focus();
                    //                    //                        return false;
                    //                    //                    }
                    //
                    //                    var checklengthresp = checkLength(txtempname,"Employee Name ",80);
                    //                    if(checklengthresp[0]==false){
                    //                        $("#txtempname").focus();
                    //                        alert(checklengthresp[1]);
                    //                        return false;
                    //                    }
                    //
                    //                    //Contact No
                    //                    var checkblank = isBlank(txtcontact,"Contact No ");
                    //                    var checklengthContact = checkLength(txtcontact,"Contact No ",10);
                    //                    if(checkblank[0]==false){
                    //                        alert(checkblank[1]);
                    //                        $("#txtcontact").focus();
                    //                        return false;
                    //                    }else if(!isNumeric(txtcontact)){
                    //                        alert("Contact Number should be numeric.");
                    //                        $("#txtcontact").val("");
                    //                        $("#txtcontact").focus();
                    //                        return false;
                    //                    }else if(checklengthContact[0]==false){
                    //                        $("#txtcontact").focus();
                    //                        alert(checklengthContact[1]);
                    //                        return false;
                    //                    }
                    //
                    //                    //email
                    //                    var emailReg = /^([\w-\.]+@([\w-]+\.)+[\w-]{2,4})?$/;
                    //
                    //
                    //                    var checkblanktxtemail = isBlank(txtemail,"Email Id ");
                    //                    var checklengthtxtemail = checkLength(txtemail,"Email Id ",50);
                    //                    if(checkblanktxtemail[0]==false){
                    //                        alert(checkblanktxtemail[1]);
                    //                        $("#txtemail").focus();
                    //                        return false;
                    //                    }else if(!emailReg.test(txtemail)) {
                    //                        $("#txtemail").val("");
                    //                        alert('Enter Valid Email ID!!');
                    //                        return false;
                    //                    }else if(checklengthtxtemail[0]==false){
                    //                        $("#txtemail").focus();
                    //                        alert(checklengthtxtemail[1]);
                    //                        return false;
                    //                    }
                    //
                    //
                    //                    //RADIO BUTTON VALIDATION
                    //                    var radios = document.getElementsByName("show_Site");
                    //                    var formValid = false;
                    //                    var i = 0;
                    //                    while (!formValid && i < radios.length) {
                    //                        if (radios[i].checked) formValid = true;
                    //                        i++;
                    //                    }
                    //                    if (!formValid){
                    //                        alert("Atleast select one Organization Type option!");
                    //                        return false;
                    //                    }
                    //
                    //                    //ORGANIZATION VALIDATION
                    //                    var zone=$("#ZoneSel option:selected").val();
                    //                    var site=$("#siteSel option:selected").val();
                    //                    var ho=$("#hoSel option:selected").val();
                    //                    //var dept=$("#deptSel option:selected").val();
                    //                    var role=$("#roleSel option:selected").val();
                    //                    var dept1=$("#deptSel").val();
                    //                    //alert(dept1);
                    //
                    //                    var hoChekc=$("#ho1").attr('checked');
                    //                    if(hoChekc==true){
                    //                        if(ho=="-1"){
                    //                            alert("Please Select Ho Site! ");
                    //                            return false;
                    //                        }
                    //                        $("#SendSiteCode").val(ho);
                    //                    }
                    //                    var storeCheck=$("#store1").attr('checked');
                    //                    if(storeCheck==true){
                    //                        if(site=="-1"){
                    //                            alert("Please Select Site! ");
                    //                            return false;
                    //                        }
                    //                        if(dept1==undefined){
                    //                            alert("Please Select Department! ");
                    //                            return false;
                    //                        }
                    //
                    //                        $("#SendDeptCode").val(dept1);
                    //                        $("#SendSiteCode").val(site);
                    //                    }
                    //
                    //                    var zoneCheck=$("#zone1").attr('checked');
                    //                    if(zoneCheck==true){
                    //                        if(zone=="-1"){
                    //                            alert("Please Select Zone Site! ");
                    //                            return false;
                    //                        }
                    //                        $("#SendSiteCode").val(zone);
                    //                    }
                    //
                    //                    if(role=="-1"){
                    //                        alert("Please Select Role! ");
                    //                        return false;
                    //                    }

                   

                });



               
            });
        </script>


        <script type="text/javascript">
            //VALIDATE EMP CODE0
            function ValidateEmpcode() {
                var userid = $("#txtempCode").val();
                if(!isNumeric(userid)){
                    alert("Employee Code should be numeric.");
                    $("#txtempCode").val("");
                    $("#txtempCode").focus();
                    return false;
                }else{
                    $("#txtuserId").val(userid);
                }
                $("#txtempCode").focus();
            }
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
                var f = document.getElementById('createuser');
              
                //f.onsubmit = function(){ return false; }
                          
                  
                document.getElementById('tbstore').style.display = 'none';
                document.getElementById('siteSel').style.display = 'none';

                document.getElementById('tbho').style.display = 'none';
                document.getElementById('hoSel').style.display = 'none';

                document.getElementById('tbzone').style.display = 'none';
                document.getElementById('ZoneSel').style.display = 'none';

                document.getElementById('tbdept').style.display = 'none';
                document.getElementById('deptSel').style.display = 'none';
                // get a reference to the radio button group
                var rads = f.elements['show_Site'];
                for(var i=0;i<rads.length;i++) {
                    // we add the event handler to each button in the group
                    rads[i].onkeyup=rads[i].onclick=function(){
                        if(!this.checked) return;
                        document.getElementById('txtsitedesc').value="";
                        document.getElementById('txtregion').value="";
                        document.getElementById('txtformat').value="";
                        document.getElementById('txtlocation').value="";
                        document.getElementById('txtcity').value="";
                        document.getElementById('txtzone').value="";

                     

                        var el1= document.getElementById('siteSel');                       
                        var el2= document.getElementById('hoSel');
                        var el3= document.getElementById('ZoneSel');
                        var el4= document.getElementById('deptSel');


                        var elho = document.getElementById('tbho');
                        var elzone = document.getElementById('tbzone');
                        var elstore = document.getElementById('tbstore');
                        var eldept = document.getElementById('tbdept');
                    
                        el2.style.display = (this.value=="ho")?'':'none';
                        elho.style.display = (this.value=="ho")?'':'none';

                        el1.style.display = (this.value=="store")?'':'none';
                        elstore.style.display = (this.value=="store")?'':'none';

//                        el4.style.display = (this.value=="store")?'':'none';
//                        eldept.style.display = (this.value=="store")?'':'none';

                        el3.style.display = (this.value=="zone")?'':'none';
                        elzone.style.display = (this.value=="zone")?'':'none';

                        el1.value="-1";
                        el2.value="-1";
                        el3.value="-1";

                    }
                    // in case, for any reason, one of the radio buttons is already checked
                    rads[i].onclick();
                }
            });
            
        </script>


    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <!-- Middle content start here -->
        <div id="middle_cont">
            <s:form id="createuser" action="donothing" method="POST">
                <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14">
                    <tr>
                        <td width="100%" align="center" >
                            <div id="message">
                                <s:actionmessage cssClass="successText"/>
                                <s:actionerror cssClass="errorText"/>
                            </div>
                        </td>
                    </tr>  <tr><td class="errorText" align="right">(Fields marked with a * are mandatory.)</td></tr>
                    <tr>
                        <td><h1>Personal Details</h1></td>
                    </tr>

                    <tr>
                        <td>
                            <table>
                                <tr>
                                    <td  align="right" id="scode">Existing Employee Name</td>
                                    <td align="left"><input type="text" size="42px" id="txtuserempName" class="flexdrop"/></td>
                                    <td align="center"> <input type="button" name="btnView" id="btnView" value="Search"class="btn" align="center" /></td>
                                    <td align="center"><input type="button" name="btnReset" id="btnReset" value="Clear"class="btn" align="center" /></td>
                                </tr>
                            </table>
                        </td>


                    </tr>
                    <tr>
                        <td>
                            <table width="94%" border="0" align="center" cellpadding="2" cellspacing="4">
                                <tr>
                                    <td width="12%" align="right">Employee Code</td><td class="errorText">*&nbsp;&nbsp;&nbsp;</td>
                                    <td width="38%">
                                        <s:textfield type="text" name="formVo.txtempCode" id="txtempCode"  onChange="ValidateEmpcode();" value="%{formVo.txtempCode}" />
                                        <!--                                        <input type="text" name="txtempCode" id="txtempCode"  onChange="ValidateEmpcode();" />-->
                                    </td>
                                    <td width="12%" align="right">Reporting To </td>
                                    <td width="38%">
                                        <s:textfield type="text" name="formVo.txtreporting" id="txtreporting" value="%{formVo.txtreporting}" />
                                        <!--                                        <input type="text" name="txtreporting" id="txtreporting" />-->
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">Employee Name </td><td class="errorText">*&nbsp;&nbsp;&nbsp;</td>
                                    <td>
                                        <!--                                        <input type="text" name="txtempname" id="txtempname" />-->
                                        <s:textfield value="%{formVo.txtempname}"  type="text" name="formVo.txtempname" id="txtempname" />
                                    </td>
                                    <td align="right" style="display: none">Task Manager</td>
                                    <td>
                                        <s:textfield value="%{formVo.txttastmanager}"  type="text" name="formVo.txttastmanager" id="txttastmanager" style="display: none" />
                                        <!--                                    <input type="text" name="txttastmanager" id="txttastmanager"  /></td>-->
                                    </td>
                                </tr>
                                <tr>
                                    <td align="right">Contact Number</td><td class="errorText">*&nbsp;&nbsp;&nbsp;</td>
                                    <td>
                                        <s:textfield type="text" name="formVo.txtcontact" id="txtcontact" size="10" maxlength="10" value="%{formVo.txtemail}"/>
                                        <!--<input type="text" name="txtcontact" id="txtcontact" size="10" maxlength="10"/>-->

                                    </td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="right">Email Address </td><td class="errorText">*&nbsp;&nbsp;&nbsp;</td>
                                    <td>
                                        <s:textfield type="text" name="formVo.txtemail" id="txtemail" value="%{formVo.txtemail}" />
                                        <!--                                        <input type="text" name="txtemail" id="txtemail"  />-->
                                    </td>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td><h1>Organization Details</h1></td>
                    </tr>
                    <tr>
                        <td>
                            <table width="84%" border="0" align="center" cellpadding="2" cellspacing="4">
                                <tr>
                                    <td align="left"> <input type="radio" id="ho1" name="show_Site" value="ho" />Ho</td>
                                    <td align="left"> <input type="radio" id="store1" name="show_Site" value="store"/>Store</td>
                                    <td align="left"> <input type="radio" id="zone1" name="show_Site" value="zone"/>Zone</td>
                                    <td></td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                    <tr>
                        <td>
                            <table width="94%" border="0" align="center" cellpadding="2" cellspacing="4">

                                <tr id="tbho" >
                                    <td width="12%"  align="left" id="scode">Ho Site Code<span class="errorText">&nbsp;*</span></td>
                                    <td width="38%"> <s:select name="formVo.hoSel" id="hoSel"  list="HoSiteMap" headerKey="-1" headerValue="---Select HO Site---" value="---Select HO Site---" cssClass="dropdown" value="%{formVo.hoSel}"/></td>

                                    <td width="12%" >&nbsp;</td>
                                    <td width="38%" >&nbsp;</td>

                                </tr>
                                <tr id="tbstore">
                                    <td width="12%" align="left" id="scode">Site Code<span class="errorText">&nbsp;*</span></td>
                                    <td width="38%"> <s:select name="formVo.siteSel" id="siteSel"  list="siteMap" headerKey="-1" headerValue="---Select Site---" value="---Select Site---"  cssClass="dropdown" value="%{formVo.siteSel}"/></td>
                                    <td width="12%" >&nbsp;</td>
                                    <td width="38%" >&nbsp;</td>
                                </tr>

                                <tr id="tbzone">
                                    <td width="12%" align="left" id="scode">Format<span class="errorText">&nbsp;*</span></td>
                                    <td width="38%"> <s:select name="formVo.formatSel" id="formatSel"  list="formatList" headerKey="-1" headerValue="---Select Format---" value="---Select Format---"  cssClass="dropdown" value="%{formVo.formatSel}"/></td>
                                    <td width="15%" align="left" id="scode">Zone Site Code<span class="errorText">&nbsp;*</span></td>
                                    <td width="38%"> <s:select name="formVo.ZoneSel" id="ZoneSel"  list="#{}" headerKey="-1" headerValue="---Select Zone Site---" value="---Select Zone Site---"  cssClass="dropdown" value="%{formVo.ZoneSel}"/></td>
                                    <td width="12%" align="left" id="scode"> Description<span class="errorText">&nbsp;*</span></td>
                                    <td width="38%"> <s:select name="formVo.ZonedescSel" id="ZonedescSel"  list="#{}" headerKey="-1" headerValue="---Select Zone Site Desc---" value="---Select Zone Site Desc---"  cssClass="dropdown" value="%{formVo.ZonedescSel}"/></td>

                                </tr>
                                <tr>
                                    <td align="right">Site Description</td>
                                    <td><input type="text" name="txtsitedesc" id="txtsitedesc" /></td>
                                    <td align="right">Region</td>
                                    <td><input type="text" name="txtregion" id="txtregion" /></td>

                                </tr>
                                <tr>
                                    <td align="right">Format</td>
                                    <td ><input type="text" name="txtformat" id="txtformat" /></td>
                                    <td align="right">Location</td>
                                    <td ><input type="text" name="txtlocation" id="txtlocation" /></td>
                                </tr>

                                <tr>
                                    <td align="right">City</td>
                                    <td><input type="text" name="txtcity" id="txtcity" /></td>
                                    <td width="12%" align="right">Zone</td>
                                    <td width="38%"><input type="text" name="txtzone" id="txtzone" /></td>                                   
                                </tr>
                                <tr id="tbdept">
                                    <td align="right">Department<span class="errorText">&nbsp;*</span></td>
                                    <td><s:select name="deptSel" id="deptSel"  list="lstDept" size="5"  multiple="true"/></td>
                                    <%--<td>
                                        <select  multiple name="deptSel" id="deptSel"   style="height: 50%" >
                                        </select>

</td> --%>
                                    <td>&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td><h1>Role Details</h1></td>
                    </tr>
                    <tr>
                        <td>
                            <table width="94%" border="0" align="center" cellpadding="2" cellspacing="4">
                                <tr>
                                    <td width="13%" align="right">User Role<span class="errorText">&nbsp;*</span></td>

                                    <td width="37%"><s:select name="formVo.roleSel" id="roleSel"  list="roleMap" headerKey="-1" headerValue="---Select Role---" value="---Select Role---"  cssClass="dropdown" value="%{formVo.roleSel}"/></td>

                                    <td width="9s%" align="right">&nbsp;</td>
                                    <td width="38%">&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="right">User ID</td>
                                    <td align=""><input type="text" name="txtuserId" id="txtuserId" /></td>
                                    <td class="errorText"></td>
                                    <!--                                    <td align="left"><input type="button" name="btnValidateUser" id="btnValidateUser" value="Validate" class="but_sub" /></td>
                                                                        <td align="left"><label id="validmsg" name="validmsg" /></td>-->
                                </tr>
                                <tr>
                                    <td align="right">Password</td>
                                    <td><input type="password" name="txtpassword" id="txtpassword" /></td>
                                    <td class="errorText"></td>
                                    <td align="right">&nbsp;</td>
                                    <td>&nbsp;</td>
                                </tr>
                                <tr>
                                    <td align="center" colspan="5">
                                        <table align="center" width="40%">
                                            <tr>
                                                <td align="right">
                                                    <s:submit action="submituserDtl" id="btnSubmit" name="btnSubmit" value="Create" cssClass="btn" />

                                                    <!--                                        <input type="submit" name="btnSubmit" id="btnSubmit" value="Submit"class="btn" align="center" />-->
                                                </td>

                                                <td align="left">  <s:submit action="updateUserDtl" id="btnUpdate"  name="btnUpdate" value="Update" cssClass="btn" /></td>
                                                <td><s:hidden id="SendSiteCode" name="formVo.SendSiteCode" value="%{formVo.SendSiteCode}"/>
                                                    <s:hidden id="SendDeptCode" name="formVo.SendDeptCode" value="%{formVo.SendDeptCode}"/>
                                                    <s:hidden id="selempId" name="selempId" value="%{formVo.selempId}"/>
                                                    <s:hidden id="userPass" name="txtpassword" value="%{formVo.userPass}"/>
                                                    <s:hidden id="isredirect" name="formVo.isredirect" value="%{formVo.isredirect}"/>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr id="showRedirect"><td colspan="5" align="center">Please Enter MC for This User.<a href="redirect_userMch"> User-MC</a></td>
                                </tr>
                            </table>
                        </td>
                    </tr>

                </table>
            </s:form>
        </div>
        <!-- Middle content start here -->
    </body>
</html>
