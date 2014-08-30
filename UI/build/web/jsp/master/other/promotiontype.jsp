<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Promotion Master</title>
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
                $("#txPromotionID").val("");
                jQuery("#list4").jqGrid({
                    url:"getAllPromotionType",
                    datatype: 'json',
                    width: 530,
                    height:210,
                    colNames:['Id','Promotion Type','Status'],
                    colModel:[
                        {name:'promotionid',index:'promotionid', width:60, align:"center"},
                        {name:'promotionname',index:'promotionname', width:250, align:"center"},                      
                        {name:'status',index:'status', width:250, align:"center"}                      
                    ],
                    rowNum:10,
                    rowList:[10,20,30],
                    viewrecords: true,
                    pager: '#pglist4',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    //caption:'Promotion Type Master',
                    onSelectRow: function(rowid){                         
                        varroleID = rowid;
                        var promotionid = $("#list4").getCell(rowid,"promotionid");  
                        var promotionname = $("#list4").getCell(rowid,"promotionname");  
                        // alert(promotionname);
                        $("#promotiontypename").val(promotionname);
                        $("#txPromotionID").val(promotionid);
                        var status = $("#list4").getCell(rowid,"status");
                        //alert($("#txPromotionID").val());
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
                                             
                    }
                    

                });
                $("#btnSubmit").click(function (){                                                          
                    var mktgname = $("#promotiontypename").val();                            
                    var checkblank = isBlank(mktgname,"Promotion Type Name ");
                    if(checkblank[0]==false){
                        alert(checkblank[1]); 
                        return false;
                    }                                       
                    var checklengthresp = checkLength(mktgname,"Promotion Type Name ",50);
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
                   
                    $("#msg").html('');
                    $("#promotiontypename").val("");
                    $("#txPromotionID").val("");  
                    $("#selStatus").val("-1");
                    jQuery("#list4").jqGrid('setGridParam',{url:'getAllPromotionType',datatype:'json',page:1}).trigger("reloadGrid");

                });
            });
        </script>
    </head>

    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <s:form action="submitPromotionMaster">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" >
                <tr>
                    <td>
                        <table  width="100%">
                            <tr><td height="10px"></td></tr>
                            <tr><td align="center" colspan="2"><h1>Promotion Type</h1></td></tr>
                            <tr align="center">
                                <td colspan="2">
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
                            <tr style="display: none">
                                <td>
                                    <table align="center">
                                        <tr>
                                            <td>
                                                Promotion Type :
                                            </td>
                                            <td>
                                                <input type="text" id="promotiontypename" name="promotiontypename" size="40" />
                                            </td>
                                        </tr>      
                                        <tr>
                                            <td>
                                                Status:</td>
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
                            <tr style="display: none">
                                <td align="center">
                                    <table width="40%">
                                        <tr>
                                            <td align="center" >
                                                <s:submit  id="btnSubmit" name="btnSubmit" value="SUBMIT" cssClass="button"></s:submit>                                                
                                                <s:hidden id="txPromotionID" name="txPromotionID" />
                                            </td>
                                            <td align="left">
                                                <input id="btnReset" name="btnReset" type="button" value="CLEAR"  class="button"/>
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

