<%-- 
    Document   : mapRoleProfile
    Created on : Dec 5, 2012, 3:13:06 PM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Role & Profile Mapping</title>

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
            
                var varroleID;
                var idRole="";
                jQuery("#list4").jqGrid({
                    url:"getAllProfiles",
                    datatype: 'json',
                    width: 530,
                    height:210,
                    colNames:['profileID','Profile', 'Level Access'],
                    colModel:[
                        {name:'profileID',index:'profileID', width:200, align:"center",hidden:true},
                        {name:'profilename',index:'profilename', width:150, align:"center"},
                        {name:'levelaccess',index:'levelaccess', width:150, align:"center"}
                        
                    ],
                    // rowNum:10,
                    //  rowList:[10,20,30],
                    viewrecords: true,
                    //  pager: '#pglist4',
                    multiselect: true,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    //caption:"Profile and Role Mapping",
                    onSelectRow: function(rowid){
                        varroleID = rowid;
                    },gridComplete:function(){
                        $("#cb_"+$("#list4")[0].id).hide();
                    }
                });

                $("#txtuserRole").autocomplete({
                    minLength : 1,
                    source : function(request, response) {
                        var temp=    $.ajax({
                            url : "flexBoxRoles?flexRoleName="+$("#txtuserRole").val() ,
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
                        idRole = ui.item.id;
                        $.ajax({
                            url: "getRoleLocationProfileDtl?SelidRole="+idRole,
                            global: false,
                            type: "POST",
                            dataType:"json",
                            contanttype: 'text/json',
                            async:false,
                            error:function(){
                                alert("Can not connect to server");
                            },
                            success: function(data){
                                //                                alert("server data : "+data.rows.profileIdList);
                                //                                alert("Location : "+ data.location);
                                //                                alert("server data length : "+data.rows.profileIdList.length);
                                $("#locationSel").val(data.location);
                                var tableDataIds= jQuery('#list4').getDataIDs();
                                //                               alert("tab : "+tableDataIds);

                                if(data.rows.profileIdList.length>0){                                   
                                    if(data.rows.profileIdList[0]!="-1"){
                                        //alert("Data " + data.rows.profileIdList[0]);
                                        for(var index=1;index<=tableDataIds.length;index++)
                                        {
                                            var rowData = jQuery('#list4').getRowData(tableDataIds[index-1]);
                                            for(var i=0; i<data.rows.profileIdList.length ; i++){
                                                if(data.rows.profileIdList[i]==rowData.profileID){                                                  
                                                    jQuery('#list4').jqGrid('setSelection',index);
                                                }
                                            }
                                        }
                                    }  
                                }
                            }
                        });
                    }
                });

                $("#btnSubmit").click(function (){
                    if(idRole.length==0){
                        alert("Please Select Role Name!");
                        $("#txtuserRole").focus();
                        return false;
                    }
                    var location=$("#locationSel").val();
                    if(location=="-1"){
                        alert("Please Select Location!");
                        return false;
                    }
                    var id = $('#list4').jqGrid('getGridParam','selarrrow');
                    if(id.length==0){
                        alert("Please Select Profile for selected Role!");
                        return false;
                    }
                    $("#SelectedIDs").val(id);
                    $("#txtroleID").val(idRole);
                    
                });

                $("#btnReset").click(function (data){
                    idRole="";
                    $("#SelectedIDs").val("");
                    $("#msg").html('');
                    $("#txtuserRole").val("");
                    $("#txtroleID").val("");
                    $("#locationSel").val("-1");
                    jQuery("#list4").jqGrid('setGridParam',{url:'getAllProfiles',datatype:'json',page:1}).trigger("reloadGrid");

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
                            <tr><td align="center"><h1>Role & Profile Mapping</h1></td></tr>
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
                                <td>
                                    <table align="center">
                                        <tr>
                                            <td>
                                                Role Name:<span class="errorText">&nbsp;*</span>
                                            </td>
                                            <td>
                                                <input type="text" id="txtuserRole" name="txtuserRole" size="40" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                Location:<span class="errorText">&nbsp;*</span>
                                            </td>
                                            <td>
                                                <s:select name="locationSel" id="locationSel"  list="locationMap" headerKey="-1" headerValue="---Select Location---" value="---Select Location---" cssClass="dropdown" />
                                            </td>
                                        </tr>

                                    </table>
                                </td>
                            </tr>
                             <tr><td style="height:5px"></td></tr>
                            <tr>
                                <td  align="center" colspan="2" >
                                    <table id="list4"></table>
                                    <div id="pglist4"></div>
                                </td>
                            </tr>
                            <tr><td style="height:5px" ></td></tr>
                            <tr>
                                <td align="center">
                                    <table width="40%">
                                        <tr>
                                            <td align="right" >
                                                <s:submit  id="btnSubmit" name="btnSubmit" value="Submit" cssClass="btn" action="submitrolemapping" ></s:submit>
                                                <s:hidden id="txtroleID" name="txtroleID" />
                                                <s:hidden name="SelectedIDs" id="SelectedIDs"/>
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
            </table>
        </s:form>
    </body>
</html>
