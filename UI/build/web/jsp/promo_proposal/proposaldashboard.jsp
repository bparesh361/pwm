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
        <link href="css/tabs.css" rel="stylesheet" type="text/css" />
        <link href="css/thickbox.css" rel="stylesheet" type="text/css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="js/Tabfunction.js"></script>
        <script type="text/javascript" src="js/thickbox.js"></script>
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>
        <script type="text/javascript">

            var  idsOfSelectedRows = [];
            var selectedRowsIdAndStatus=[];
            var proposalId;
            $(function () {
                jQuery("#list4").jqGrid({
                    url:"getAllProposalDashboardByStatus",
                    datatype: 'json',
                    width: 1000,
                    height:230,
                    colNames:['Id','Proposal Date','Site Code','Site Description','Requestor Name','Contact No','Emp Code','Department','Problem Type','Promotion Type','Remarks','Status','statusid','Download','Delete','Additional File Download'],
                    colModel:[
                        {name:'id',index:'id', width:100, align:"center"},
                        {name:'date',index:'date', width:250, align:"center"},
                        {name:'sitecode',index:'sitecode', width:250, align:"center"},
                        {name:'sitedesc',index:'sitedesc', width:250, align:"center"},
                        {name:'requestorname',index:'requestorname', width:250, align:"center"},
                        {name:'contactno',index:'contactno', width:250, align:"center"},
                        {name:'empCode',index:'empCode', width:250, align:"center"},
                        {name:'dept',index:'dept', width:250, align:"center"},
                        {name:'type',index:'type', width:250, align:"center"},
                        {name:'promoType',index:'promoType', width:250, align:"center"},
                        {name:'remarks',index:'remarks', width:250, align:"center"},
                        {name:'status',index:'status', width:250, align:"center"},
                        {name:'statusId',index:'statusId', width:250, align:"center",hidden:true},
                        {name:'download',index:'download', width:250, align:"center",hidden:true},
                        {name:'delete',index:'delete', width:250, align:"center",hidden:true},
                        {name:'otherfile',index:'otherfile', width:250, align:"center"}
                    ],
                    rowNum:30,
                    rowList:[30],
                    headertitles: true,
                    viewrecords: true,
                    pager: '#pglist4',
                    //emptyrecords:'',
                    //recordtext:'',
                    multiselect: true,
                    loadonce:false,
                    ignoreCase:true,
                    imgpath: "themes/basic/images"
                    //caption:'Proposal Dashboard',
                    ,onSelectRow: function(id,isSelected){
                        gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);
                        proposalId=id;
                        $("#proposalId").val(idsOfSelectedRows);
                        var statusid =$('#list4').getCell(id,'statusId');
                        var ticket_Number=$('#list4').getCell(id,'id');
                        var page_no=$('#list4').getGridParam('page');
                        var id_status_id=ticket_Number+"-"+statusid+"-"+page_no;
                        gridPageNavigationPersistRowIDAndStatus(id_status_id,isSelected,selectedRowsIdAndStatus);
                
                    },
                    onSelectAll: function (aRowids, isSelected) {
                        var i, id;
                        for (i = 0; i < aRowids.length; i++) {
                            id = aRowids[i];
                            gridPageNavigationPersistRow(id,isSelected,idsOfSelectedRows);
                        }
                        var statusid =$('#list4').getCell(id,'statusId');
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
                        if(status_id!="4"){
                            return[false,"You can not Accept/Reject Proposal Request Number : "+ticket_number+" On Page No : "+page_no+" ."];
                        }
                    }
                }
                return [true,''];
            }
            jQuery(document).ready(function(){
                $("#txProblemID").val("");
                function confirmBox(){
                    if (confirm('Are you sure you want to delete Request ?')) {
                        return true;
                    }else {
                        return false;
                    }
                }
               
              
                
                $("#btnSubmit").click(function (){                                  
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    var fromdate = $("#txtBXGYFrom").val();
                    var todate = $("#txtBXGYTo").val();
                    var status = $("#sattusId").val();
                    if(fromdate.length==0 && todate.length==0 && status=="-1"){

                        alert("Please Select Search Type. Date or Status.");
                        return false;
                    }               
                    if( fromdate.length>0 && todate.length==0){
                        alert("To Date Can not be Blank"); 
                        return false;
                    }

                    if(status=="-1"){
                        status="";
                    }
                    jQuery("#list4").jqGrid('setGridParam',{url:'searchProposalDashboardByDate?txtBXGYFrom='+fromdate+'&txtBXGYTo='+todate+'&status='+status,datatype:'json',page:1}).trigger("reloadGrid");
                    
                    //  jQuery("#list4").jqGrid('setGridParam',{url:'searchProposalDashboardByDate',datatype:'json',page:1}).trigger("reloadGrid");
                    return false;
                });

                $(function() {
                    var dates = $( "#txtBXGYFrom, #txtBXGYTo" ).datepicker({
                        //                        defaultDate: "+1w",
                        //                        numberOfMonths: 1,
                        changeMonth: true,
                        changeYear: true,
                        dateFormat: 'yy-mm-dd',
                        // maxDate : '+0d',
                        //minDate:'+0d',


                        onSelect: function( selectedDate ) {
                            var option = this.id == "txtBXGYFrom" ? "minDate" : "maxDate",
                            instance = $( this ).data( "datepicker" ),
                            date = $.datepicker.parseDate(
                            instance.settings.dateFormat ||
                                $.datepicker._defaults.dateFormat,
                            selectedDate, instance.settings );
                            dates.not( this ).datepicker( "option", option, date );
                        }
                    });
                });

                function clearDate(){
                    var dates = $("input[id$='txtBXGYFrom'], input[id$='txtBXGYTo']");
                    dates.attr('value', '');
                    dates.each(function(){
                        $.datepicker._clearDate(this);
                    });
                }
                $("#btnReset").click(function (data){
                    idsOfSelectedRows = [];
                    selectedRowsIdAndStatus=[];
                    clearDate();
                    $('#msg').html('');
                    $('#txtBXGYFrom').val('');
                    $('#txtBXGYTo').val('');
                    $("#sattusId").val('-1');
                    jQuery("#list4").jqGrid('setGridParam',{url:'getAllProposalDashboardByStatus',datatype:'json',page:1}).trigger("reloadGrid");

                });

                $("#sattusId").change(function(){
                    var sattusId = $("#sattusId option:selected").val();
                  
                    if(sattusId=="55" || sattusId=="54"){
                        $("#btnAccept").attr("disabled",true);
                        $("#btnReject").attr("disabled",true);
                    }else{
                            
                        $("#btnAccept").attr("disabled",false);
                        $("#btnReject").attr("disabled",false);
                    }

                });
                $("#btnDelete").click(function (data){

                    // var id = $('#list4').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request !");
                        return false;
                    }
                    $("#proposalId").val(idsOfSelectedRows);
                    if (confirm('Are you sure you want to delete Request ?')) {
                        return true;
                    }
                    else {
                        return false;
                    }

                });
                $("#btnAccept").click(function (data){
                    var checkSelectedRowsStatus=checkStatusIdInSelectedRows();
                    if(checkSelectedRowsStatus[0]==false){
                        alert(checkSelectedRowsStatus[1]);
                        return false;
                    }
                    //var id = $('#list4').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request !");
                        return false;
                    }
                    $("#proposalId").val(idsOfSelectedRows);
                    var txtMenualRemarks = $('#txtMenualRemarks').val();
                    txtMenualRemarks=  txtMenualRemarks.replace(/(\r\n|\n|\r)/gm," ");
                    $("#txtMenualRemarks").val(txtMenualRemarks);
                    if(txtMenualRemarks==undefined ||txtMenualRemarks==null || txtMenualRemarks.length==0){
                        alert("Please Enter Remarks.");
                        return false;
                    }
                    if( txtMenualRemarks.length > 200){
                        alert("Remarks should not be greater than 200.");
                        return false;
                    }

                });
                $("#btnReject").click(function (data){
                    var checkSelectedRowsStatus=checkStatusIdInSelectedRows();
                    if(checkSelectedRowsStatus[0]==false){
                        alert(checkSelectedRowsStatus[1]);
                        return false;
                    }
                    //var id = $('#list4').jqGrid('getGridParam','selarrrow');
                    if(idsOfSelectedRows.length==0){
                        alert("Please Select Request !");
                        return false;
                    }
                    $("#proposalId").val(idsOfSelectedRows);
                    var txtMenualRemarks = $('#txtMenualRemarks').val();
                    txtMenualRemarks=  txtMenualRemarks.replace(/(\r\n|\n|\r)/gm," ");
                    $("#txtMenualRemarks").val(txtMenualRemarks);
                    if(txtMenualRemarks==undefined ||txtMenualRemarks==null || txtMenualRemarks.length==0){
                        alert("Please Enter Remarks.");
                        return false;
                    }
                    if( txtMenualRemarks.length > 200){
                        alert("Remarks should not be greater than 200.");
                        return false;
                    }
                   

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
        <s:form action="donothing" method="POST">
            <s:hidden id="proposalId" name="proposalId"/>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" >
                <tr>
                    <td>
                        <table  width="100%">
                            <tr><td height="10px"></td></tr>
                            <tr><td align="center" ><h1>Proposal</h1></td></tr>
                            <tr align="center">
                                <td >
                                    <div id="msg" >
                                        <s:actionmessage cssClass="successText"/>
                                        <s:actionerror cssClass="errorText" />
                                    </div>
                                </td>
                            </tr>
                            <tr><td style="height:10px"></td></tr>
                            <tr>
                                <td align="left"><input type="hidden" name="hdempId" id="hdempId"  value="<%= session.getAttribute(WebConstants.EMP_ID)%>" disabled/></td>
                            </tr>
                            <td align="right" >
                                <a href ="#" class="download-sample " onclick="tb_show( '', 'viewProposalDashboard?height=150&width=450');">
                                    Help
                                </a>
                            </td>
                            <tr><td>
                                    <table align="center" width="65%">
                                        <tr>
                                            <td>
                                                Start Date : 
                                            </td>
                                            <td><input type="text" id="txtBXGYFrom" name="fromdate" size="15" readonly></td>
                                            <td>
                                                End Date : 
                                            </td>
                                            <td><input type="text" id="txtBXGYTo" name="todate" size="15" readonly></td>

                                            <td  align="right">Status</td>
                                            <td align="left">  <s:select name="sattusId" id="sattusId" list="#{'4':'Submit','54':'Accepted','55':'Rejected'}" headerKey="-1" headerValue="---Select---" value="---Select---" cssClass="dropdown"/>  </td>

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
                            <tr>
                                <td  align="center"  >
                                    <table id="list4"></table>
                                    <div id="pglist4"></div>
                                </td>
                            </tr>
                            <tr><td style="height:10px" ></td></tr>
                            <tr>
                                <td align="center">
                                    <table width="50%">
                                        <td>Remarks : </td>
                                        <td ><textarea cols="40" rows="4" name="manualRemarks" id="txtMenualRemarks" title="Max 1000 Characters."></textarea>
                                    </table>
                                </td>

                            </tr>
                            <tr><td style="height:10px" ></td></tr>
                            <tr>
                                <td  align="center" >
                                    <table width="10%">
                                        <tr>
                                            <td align="center" style="display: none">
                                                <s:submit action="deleteProposalDashboard" id="btnDelete" name="btnDelete" cssClass="btn" value="Delete"/>
                                            </td>
                                            <td align="center">
                                                <s:submit action="acceptProposalDashboard" id="btnAccept" name="btnDelete" cssClass="btn" value="Accept"/>
                                            </td>
                                            <td align="center">
                                                <s:submit action="rejectProposalDashboard" id="btnReject" name="btnDelete" cssClass="btn" value="Reject"/>
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

