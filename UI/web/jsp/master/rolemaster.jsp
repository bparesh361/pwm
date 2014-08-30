<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Role</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script type="text/javascript">
            jQuery(document).ready(function(){
                var varroleID;
                $("#txtroleID").val('');
                jQuery("#list4").jqGrid({
                    url:"getAllRoles",
                    datatype: 'json',
                    width: 730,
                    height:210,
                    colNames:['role ID','Role', 'Status','Creation Date', 'Created By', 'Updated Date','Updated By'],
                    colModel:[
                        {name:'roleId',index:'roleId', width:200, align:"center",hidden:true},
                        {name:'rolename',index:'rolename', width:250, align:"center",sortable:false},
                        {name:'status',index:'status', width:150, align:"center",sortable:false},
                        {name:'cdate',index:'cdate', width:200, align:"center",sortable:false},
                        {name:'createdby',index:'createdby', width:200, align:"center",sortable:false},
                        {name:'udate',index:'udate', width:200, align:"center",sortable:false},
                        {name:'updatedby',index:'updatedby', width:200, align:"center",sortable:false},
                    ],
                    rowNum:10,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#pglist4',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    //caption:"Role Creation",
                    onSelectRow: function(rowid){
                        varroleID = rowid;
                        var roleNameData = $("#list4").getCell(rowid, 'rolename');
                        $("#txtuserRole").val(roleNameData);
                        var rolestatus = $("#list4").getCell(rowid, 'status');
                        var rolevalue="";
                        if(rolestatus=="Active"){
                            rolevalue="0";
                        }else if(rolestatus=="In-Active"){
                            rolevalue="1";
                        }else{
                            rolevalue="-1";
                        }
                        $("#selStatus").val(rolevalue);


                        // $("#selStatus option:contains(" + rolestatus + ")").attr('selected', 'selected');
                    }

                });


                $("#btnSubmit").click(function (){
                    var status=$("#selStatus option:selected").val();
                    var role =$("#txtuserRole").val();
                    role=$.trim(role);
                    if(role.length ==0){
                        alert("Please enter Role name.");
                        $("#txtuserRole").focus();
                        return false;
                    }
                    if(role.length >40){
                        alert("Role name should not be greater than 40.");
                        $("#txtuserRole").focus();
                        return false;
                    }
                    if(status=="-1"){
                        alert("Please select Role Status.");
                        return false;
                    }
                    
                    if(varroleID !=null){
                        $("#txtroleID").val(varroleID);
                    }
                });

                $("#btnReset").click(function (data){
                    $('#selStatus').val('-1');
                    $("#txtuserRole").val('');
                    $("#txtroleID").val('');
                    $("#msg").html('');
                    jQuery("#list4").jqGrid('setGridParam',{url:'getAllRoles',datatype:'json',page:1}).trigger("reloadGrid");


                });
            });
        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <!-- Middle content start here -->
        <!--        <div id="middle_cont" >-->        
        <s:form id="role" action="donothing">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" >
                <tr>
                    <td>
                        <table  width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                            <tr><td height="5px"></td>
                            </tr>
                            <tr><td class="errorText" align="right">(Fields marked with a * are mandatory.)</td></tr>
                            <tr>
                                <td align="center"><h1>Role</h1></td>
                            </tr>
                            <tr align="center">
                                <td align="center">
                                    <div id="msg" >
                                        <s:actionmessage cssClass="successText"/>
                                        <s:actionerror cssClass="errorText" />
                                    </div>
                                </td>
                            </tr>                            
                            <tr><td height="5px"></td></tr>
                            <tr>
                                <td  align="center"  >
                                    <table id="list4"></table>
                                    <div id="pglist4"></div>
                                </td>
                            </tr>
                            <tr><td style="height:5px" ></td></tr>
                            <tr>
                                <td>
                                    <table align="center">
                                        <tr>
                                            <td>
                                                User Role:
                                            </td>
                                            <td class="errorText">*&nbsp;&nbsp;&nbsp;</td>
                                            <td>
                                                <input type="text" id="txtuserRole" name="txtuserRole" size="40" maxlength="40" />
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                Status:</td>
                                            <td class="errorText">*&nbsp;&nbsp;&nbsp;</td>
                                            <td>
                                                <select name="selStatus" id="selStatus" class="dropdown">
                                                    <OPTION value="-1">----- Select Status -----</OPTION>
                                                    <OPTION value="0">Active</OPTION>
                                                    <OPTION value="1">In-Active</OPTION>
                                                </select>
                                            </td>
                                        </tr>

                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td align="center">
                                    <table width="40%">
                                        <tr>
                                            <td align="center" >
                                                <s:submit  id="btnSubmit" name="btnSubmit" value="Submit" cssClass="btn" action="submitUpdateRoleDetail"  ></s:submit>
                                                <s:hidden id="txtroleID" name="txtroleID" />
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

