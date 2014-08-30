<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Zone Master</title>
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

                jQuery("#list4").jqGrid({
                    url:"getAllZone",
                    datatype: 'json',
                    width: 530,
                    height:210,
                    colNames:['Id','Zone Name','Zone Code','Status'],
                    colModel:[
                        {name:'zoneid',index:'zoneid', width:60, align:"center"},
                        {name:'zonename',index:'zonename', width:250, align:"center"},                      
                        {name:'zonecode',index:'zonecode', width:250, align:"center"},
                        {name:'status1',index:'status1', width:250, align:"center"}
                    ],
                    rowNum:10,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#pglist4',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    //caption:'Zone Master',
                    onSelectRow: function(rowid){                                                 
                        varroleID = rowid;
                        var zoneid = $("#list4").getCell(rowid,"zoneid");  
                        var zonename = $("#list4").getCell(rowid,"zonename");   
                        var zonecode = $("#list4").getCell(rowid,"zonecode");   
                        $("#zonename").val(zonename);
                        $("#zonecode").val(zonecode);                                             
                        $("#txnzoneid").val(zoneid);

                        var status = $("#list4").getCell(rowid, 'status1');
                        var statusvalue="";
                        if(status=="Active"){
                            statusvalue = "0";
                        } else if(status=="Blocked"){
                            statusvalue = "1";
                        } else {
                            statusvalue="-1";
                        }

                        $("#selStatus").val(statusvalue);
                    }
                });
                
                $("#btnSubmit").click(function (){                                                          
                    var zonename = $("#zonename").val();
                    zonename=$.trim(zonename);
                    var checkblank = isBlank(zonename,"Zone Name ");
                    if(checkblank[0]==false){
                        alert(checkblank[1]); 
                        return false;
                    }                                       
                    var checklengthresp = checkLength(zonename,"Zone Name ",50);
                    if(checklengthresp[0]==false){
                        alert(checklengthresp[1]);
                        return false;
                    }
                    var zonecode = $("#zonecode").val();
                    zonecode=$.trim(zonecode);
                    var checkblank = isBlank(zonecode,"Zone Code ");
                    if(checkblank[0]==false){
                        alert(checkblank[1]); 
                        return false;
                    }                                       
                    var checklengthresp = checkLength(zonecode,"Zone Code ",50);
                    if(checklengthresp[0]==false){
                        alert(checklengthresp[1]);
                        return false;
                    }
                    var status=$("#selStatus option:selected").val();
                    if(status==-1){
                        alert("Please select Status.");
                        return false;
                    }
                    
                });
                $("#btnReset").click(function (data){
                    idRole="";
                    $("#zonename").val("");
                    $("#zonecode").val("");                    
                    $("#txnzoneid").val("");
                    $("#msg").html('');
                    $("#selStatus").val("-1");
                    jQuery("#list4").jqGrid('setGridParam',{url:'getAllZone',datatype:'json',page:1}).trigger("reloadGrid");

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
                            <tr><td align="center"><h1>Zone</h1></td></tr>
                            <tr align="center">
                                <td colspan="2" align="center">
                                    <div id="msg" >
                                        <s:actionmessage cssClass="successText"/>
                                        <s:actionerror cssClass="errorText" />
                                    </div>
                                </td>
                            </tr>
                            <tr><td style="height:10px"></td></tr>
                            <tr>
                                <td  align="center" colspan="2" >
                                    <table id="list4"></table>
                                    <div id="pglist4"></div>
                                </td>
                            </tr>
                            <tr><td style="height:10px" ></td></tr>
                            <tr>
                                <td>
                                    <table align="center">
                                        <tr>
                                            <td>
                                                Zone Name :<span class="errorText">&nbsp;*</span>
                                            </td>
                                            <td>
                                                <input type="text" id="zonename" name="zonename" size="40" />
                                            </td>
                                        </tr>                                    
                                    </table>
                                </td>
                            </tr>                          
                            <tr>
                                <td>
                                    <table align="center">
                                        <tr>
                                            <td>
                                                Zone Code :<span class="errorText">&nbsp;*</span>
                                            </td>
                                            <td>
                                                <input type="text" id="zonecode" name="zonecode" size="40" />
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
                                                <s:submit  id="btnSubmit" name="btnSubmit" value="Submit" cssClass="btn" action="submitZone"></s:submit>
                                                <s:hidden id="txnzoneid" name="txnzoneid" />
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

