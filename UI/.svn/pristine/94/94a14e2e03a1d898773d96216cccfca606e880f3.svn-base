<%@page import="com.fks.ui.constants.WebConstants"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Proposal</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />
        <link href="css/thickbox.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script src="js/jquery_ui_validations.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/thickbox.js"></script>
        <script type="text/javascript">
            var idsOfSelectedRows = [];
            var proposalId;
            var  selectedRowsIdAndStatus = [];
            $(function () {
                jQuery("#list4").jqGrid({
                    url:"getAllProposalByUser",
                    datatype: 'json',
                    width: 800,
                    height:230,
                    colNames:['Id','Proposal Date','Problem Type','Promotion Type','Status','statusId','Initiator Remarks','Remarks','Article / MC Data','Delete','Submit','Additional File Download'],
                    colModel:[
                        {name:'id',index:'id', width:60, align:"center"},
                        {name:'date',index:'date', width:250, align:"center"},
                        {name:'type',index:'type', width:250, align:"center"},
                        {name:'promotype',index:'promotype', width:250, align:"center"},
                        {name:'status',index:'status', width:250, align:"center"},
                        {name:'statusID',index:'statusID', width:250, align:"center",hidden:true},
                        {name:'initiator_remarks',index:'initiator_remarks', width:250, align:"center"},
                        {name:'remarks',index:'remarks', width:250, align:"center"},
                        {name:'download',index:'download', width:250, align:"center",hidden:true},
                        {name:'delete',index:'delete', width:250, align:"center",hidden:true},
                        {name:'submit',index:'submit', width:250, align:"center",hidden:true},
                        {name:'otherDownlod',index:'otherDownlod', width:250, align:"center"}
                    ],
                    rowNum:30,
                    rowList:[30],
                    headertitles: true,
                    viewrecords: true,
                    pager: '#pglist4',
                    multiselect: true,
                    //                    loadonce:true,
                    //                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:''
                    ,onSelectRow: function(id,isSelected){
                        gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);
                        proposalId=id;
                        $("#proposalId").val(idsOfSelectedRows);
                        var statusid =$('#list4').getCell(id,'statusID');
                        var ticket_Number=$('#list4').getCell(id,'id');
                        var page_no=$('#list4').getGridParam('page');
                        var id_status_id=ticket_Number+"-"+statusid+"-"+page_no;
                        gridPageNavigationPersistRowIDAndStatus(id_status_id,isSelected,selectedRowsIdAndStatus);
                        //                        for (var i = 0; i < idsOfSelectedRows.length; i++) {
                        //                            var statusid =$('#list4').getCell(idsOfSelectedRows[i],'statusID');
                        //                            if(statusid=="3"){
                        //                                $("#btnDelete").attr("disabled",false);
                        //                                $("#btnUpdate").attr("disabled",false);
                        //                            }else if(statusid=="4"){
                        //                                $("#btnDelete").attr("disabled",true);
                        //                                $("#btnUpdate").attr("disabled",true);
                        //                            }else if(statusid=="54" || statusid=="55"){
                        //                                $("#btnDelete").attr("disabled",true);
                        //                                $("#btnUpdate").attr("disabled",true);
                        //                            }
                        //                        }
                    },
                    onSelectAll: function (aRowids, isSelected) {
                        var i, id;
                        for (i = 0; i < aRowids.length; i++) {
                            id = aRowids[i];
                            gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);
                        }
                        var statusid =$('#list4').getCell(id,'statusID');
                        var ticket_Number=$('#list4').getCell(id,'id');
                        var page_no=$('#list4').getGridParam('page');
                        var id_status_id=ticket_Number+"-"+statusid+"-"+page_no;
                        gridPageNavigationPersistRowIDAndStatus(id_status_id,isSelected,selectedRowsIdAndStatus);
                    },
                    loadComplete: function () {
                        var $this = $(this), i, count;
                        for (i = 0, count = idsOfSelectedRows.length; i < count; i++) {
                            $this.jqGrid('setSelection', idsOfSelectedRows[i], false);
                        }
                    }
                    //                    ,gridComplete:function(){
                    //                        $("#cb_"+$("#list4")[0].id).hide();
                    //                    }

                });
            });
            function checkStatusIdInSelectedRows(){
                if(selectedRowsIdAndStatus.length>0){
                    for (i = 0; i < selectedRowsIdAndStatus.length; i++) {

                        var row_id_status_id=(selectedRowsIdAndStatus[i]).split("-");
                        var ticket_number=row_id_status_id[0];
                        var status_id=row_id_status_id[1];
                        var page_no=row_id_status_id[2];
                        //                        alert("-- row Id : "+ticket_number);
                        //                        alert("-- status Id : "+status_id);
                        if(status_id!="3"){
                            return[false,"You can not Delete or Update Proposal Request Number : "+ticket_number+" On Page No : "+page_no+" ."];
                        }

                    }
                }
                return [true,''];
            }
            jQuery(document).ready(function(){

                function confirmBox(){
                    if (confirm('Are you sure you want to delete Request ?')) {
                        alert('inside yes.');
                        return true;
                    }else {
                        alert('inside cancel');
                        return false;
                    }
                }

            
              
                $("#btnDelete").click(function (data){
                    var checkSelectedRowsStatus=checkStatusIdInSelectedRows();
                    if(checkSelectedRowsStatus[0]==false){
                        alert(checkSelectedRowsStatus[1]);
                        return false;
                    }
                    if(proposalId==undefined ||proposalId==null || proposalId.length==0){
                        alert("Please Select Request.");
                        return false;
                    }
                    var selid=idsOfSelectedRows;
                    if(idsOfSelectedRows.length==0 || selid.length>1){
                        alert("Please Select Only One Request For Delete!");
                        return false;
                    }
                    if (confirm('Are you sure you want to delete Request ?')) {
                        return true;
                    }
                    else {
                        return false;
                    }
                });
                $("#btnSubmit").click(function (){
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    var status = $("#sattusId").val();                    
                    if(status==undefined || status==null || status.length==0 || status==-1){
                        alert("Please Select Status.");
                        return false;
                    }
                    jQuery("#list4").jqGrid('setGridParam',{url:'getAllProposalByUser?status='+status,datatype:'json',page:1}).trigger("reloadGrid");                    
                    //  jQuery("#list4").jqGrid('setGridParam',{url:'searchProposalDashboardByDate',datatype:'json',page:1}).trigger("reloadGrid");
                    return false;
                });
                $("#btnReset").click(function (data){
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    $("#msg").html("");
                    $("#sattusId").val('-1');
                    jQuery("#list4").jqGrid('setGridParam',{url:'getAllProposalByUser',datatype:'json',page:1}).trigger("reloadGrid");

                });
                $("#btnUpdate").click(function (data){
                    var checkSelectedRowsStatus=checkStatusIdInSelectedRows();
                    if(checkSelectedRowsStatus[0]==false){
                        alert(checkSelectedRowsStatus[1]);
                        return false;
                    }
                    var selid=idsOfSelectedRows;
                    if(idsOfSelectedRows.length==0 || selid.length>1){
                        alert("Please Select Only One Request For Update!");
                        return false;
                    }
                    
                    $("#isUpdate").val("1");
                });

                //Download button
                $("#downloadBtn").click(function(){
                    $("#msg").html('');                   
                    var  empId=   $("#hdempId").val();
                    // var id = $('#list4').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request !");
                        return false;
                    }else{
                        var iframe = document.createElement("iframe");
                        iframe.src = "downloadMultipleProposalExcel?transId="+idsOfSelectedRows+"&empID="+empId;
                        iframe.style.display = "none";
                        document.body.appendChild(iframe);
                    }

                });
            });
        </script>
    </head>

    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <s:form action="donothing" method="POST" >
            <s:hidden id="proposalId" name="proposalId"/>
            <s:hidden id="isUpdate" name="isUpdate"/>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" >
                <tr>
                    <td align="center">
                        <table  width="100%">
                            <tr><td height="10px"></td></tr>
                            <tr><td align="center" ><h1>Proposal</h1></td>
                                <td align="center" >
                                    <a href ="#" class="download-sample " onclick="tb_show( '', 'viewModifyProposalHelp?height=370&width=650');">
                                        Help
                                    </a>
                                </td>
                            </tr>
                            <tr align="center">
                                <td >
                                    <div id="msg">
                                        <s:actionmessage cssClass="successText"/>
                                        <s:actionerror cssClass="errorText" />
                                    </div>
                                </td>
                            </tr>
                            <tr>
                                <td align="left"><input type="hidden" name="hdempId" id="hdempId"  value="<%= session.getAttribute(WebConstants.EMP_ID)%>" disabled/></td>
                            </tr>
                            <tr><td style="height:10px"></td></tr>
                            <tr><td>
                                    <table align="center" width="30%">
                                        <tr>
                                            <td  align="right">Status</td>
                                            <td align="left">  <s:select name="sattusId" id="sattusId" list="#{'4':'Submit','3':'Save','54':'Accepted','55':'Rejected'}" headerKey="-1" headerValue="---Select---" value="---Select---" cssClass="dropdown"/>  </td>
                                            <td>
                                                <input type="button"  id="btnSubmit" name="btnSubmit" value="Search" Class="btn">
                                            </td>
                                            <td>
                                                <input type="button" id="btnReset" name="btnReset" class="btn" value="Clear"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>

                            </tr>
                            <tr><td style="height:10px"></td></tr>
                            <tr>
                                <td  align="center"  >
                                    <table id="list4"></table>
                                    <div id="pglist4"></div>
                                </td>
                            </tr>
                            <tr><td style="height:10px" ></td></tr>
                            <tr>
                                <td  align="center" >
                                    <table width="10%">
                                        <tr>
                                            <td align="center">
                                                <s:submit action="deleteProposal" id="btnDelete" name="btnDelete" cssClass="btn" value="Delete"/>
                                            </td>
                                            <td align="center">
                                                <s:submit action="viewproposal_action" id="btnUpdate" name="btnUpdate" cssClass="btn" value="Update" />
                                            </td>
                                            <td style="height: 200%">
                                                <input type="button" name="downloadBtn" id="downloadBtn" value="Download"  class="btn"/>
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

