<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Problem Type Master</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script src="js/jquery_ui_validations.js" type="text/javascript"></script>
        <script type="text/javascript">
            jQuery(document).ready(function(){
                $("#txProblemID").val("");
                jQuery("#list4").jqGrid({
                    url:"getAllProblem",
                    datatype: 'json',
                    width: 530,
                    height:210,
                    colNames:['Id','Problem Type','Status'],
                    colModel:[
                        {name:'problemid',index:'problemid', width:60, align:"center"},
                        {name:'problemname',index:'problemname', width:250, align:"center"}, 
                        {name:'status',index:'status', width:250, align:"center"},
                    ],
                    rowNum:10,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#pglist4',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    // caption:'Problem Type Master',
                    onSelectRow: function(rowid){                        
                        varroleID = rowid;
                        var problemid = $("#list4").getCell(rowid,"problemid");  
                        var problemname = $("#list4").getCell(rowid,"problemname");  
                        var status = $("#list4").getCell(rowid,"status");  
                        $("#problemname").val(problemname);
                        $("#txProblemID").val(problemid);
                        var statusvalue="";
                        if(status=="Active"){
                            statusvalue = "0";
                        } else if(status=="Blocked"){
                            statusvalue = "1";
                        } else {
                            statusvalue="-1";
                        }           
                        //alert(statusvalue);
                        $("#selStatus").val(statusvalue);
                        //alert($("#txProblemID").val());
                    }
                });
                
                $("#btnSubmit").click(function (){                                                          
                    var mktgname = $("#problemname").val();
                    mktgname=$.trim(mktgname);
                    var checkblank = isBlank(mktgname,"Problem Name ");
                    if(checkblank[0]==false){
                        alert(checkblank[1]); 
                        return false;
                    }                                       
                    var checklengthresp = checkLength(mktgname,"Problem Name ",50);
                    if(checklengthresp[0]==false){
                        alert(checklengthresp[1]);
                        return false;
                    }
                    var status=$("#selStatus option:selected").val();
                    if(status=="-1"){
                        alert("Please select Status.");
                        return false;
                    }
                });
                $("#btnReset").click(function (data){
                    idRole="";
                    $("#msg").html("");
                    $("#problemname").val("");
                    $("#txProblemID").val("");             
                    $("#selStatus").val("-1");
                    jQuery("#list4").jqGrid('setGridParam',{url:'getAllProblem',datatype:'json',page:1}).trigger("reloadGrid");

                });
            });
        </script>
    </head>

    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <s:form action="donothing">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" >
                <tr>
                    <td>
                        <table  width="100%">
                            <tr><td height="5px"></td></tr>
                            <tr><td class="errorText" align="right">(Fields marked with a * are mandatory.)</td></tr>
                            <tr><td align="center" colspan="2"><h1>Problem Type</h1></td></tr>
                            <tr align="center">
                                <td colspan="2">
                                    <div id="msg" >
                                        <s:actionmessage cssClass="successText"/>
                                        <s:actionerror cssClass="errorText" />
                                    </div>
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
                                <td>
                                    <table align="center">
                                        <tr>
                                            <td>
                                                Problem Type :<span class="errorText">&nbsp;*</span>
                                            </td>
                                            <td>
                                                <input type="text" id="problemname" name="problemname" size="40" />
                                            </td>
                                        </tr> 
                                        <tr>
                                            <td>
                                                Status:<span class="errorText">&nbsp;*</span></td>
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
                                                <s:submit  id="btnSubmit" name="btnSubmit" value="Submit" cssClass="btn" action="submitProblemMaster"></s:submit>
                                                <s:hidden id="txProblemID" name="txProblemID" />
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

