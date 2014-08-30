<%--
    Document   : createSubPromoFile
    Created on : Feb 15, 2013, 1:45:38 PM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reports</title>
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
        <script type="text/javascript">
            jQuery(document).ready(function(){
                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });
                
                $("#statusSel").val("-1");
                jQuery("#reqGrid").jqGrid({
                    url:"getallRequestReportFieldsForArticleMC",
                    datatype: 'json',
                    width: 875,
                    height:150,
                    colNames:['Report Request ID','Request Date ','From Date','To Date',
                        'Category','Sub Category','Campaign','Ticket No','Promo Request Status','Status','Remarks','File'],
                    colModel:[
                        {name:'no',index:'no', width:210, align:"center",sortable: false},
                        {name:'rdate',index:'rdate', width:200, align:"center",sortable: false},
                        {name:'rinitiationDateFrom',index:'rinitiationDateFrom', width:200, align:"center",sortable: false},
                        {name:'rinitiationDateTo',index:'rinitiationDateTo', width:200, align:"center",sortable: false},
                        {name:'category',index:'category', width:200, align:"center",sortable: false},
                        {name:'subcategory',index:'subcategory', width:200, align:"center",sortable: false},
                        {name:'campaign',index:'campaign', width:200, align:"center",sortable: false},
                        {name:'ticketno',index:'ticketno', width:200, align:"center",sortable: false},
                        {name:'pstatus',index:'pstatus', width:200, align:"center",sortable: false},
                        {name:'status',index:'status', width:200, align:"center",sortable: false},
                        {name:'remarks',index:'remarks', width:200, align:"center",sortable: false},
                        {name:'file',index:'file', width:200, align:"center",sortable: false},
                    ],
                    rowNum:30,
                    rowList:[30],
                    viewrecords: true,
                    headertitles: true,
                    pager: '#reqPager',
                    multiselect: false,
                    //loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:""                    
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
                        jQuery("#reqGrid").jqGrid('setGridParam',{url:'getallRequestReportFieldsForArticleMC',page:1}).trigger("reloadGrid");

                    }
                }
            );

                $("#categorySel").change(function(){
                    var category=$("#categorySel option:selected").val();
                   
                    if(category !=-1){
                        $.ajax({
                            url: "getMchCategoryWise?categoryname="+category,
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
                               
                                $('#subcategorySel option').each(function(ik, option){
                                    $(option).remove();
                                });

                                var select = document.getElementById("subcategorySel");
                                var newoption = document.createElement('option');
                                newoption.text = "---Select Sub Category---";
                                newoption.value = -1;
                                select.add(newoption);

                                if(data!=null){
                                    for(var i=0; i<data.rows.subcategroylist.length ; i++){
                                        var optn = document.createElement("OPTION")
                                        optn.text = data.rows.subcategroylist[i];
                                        optn.value = data.rows.subcategroylist[i];
                                        select.add(optn);
                                    }
                                }
                            }
                        });
                    }
                });
                $(function() {
                    var dates = $( "#popup_container1, #popup_container2" ).datepicker({
                        //defaultDate: "+1w",
                        //numberOfMonths: 1,
                        changeMonth: true,
                        changeYear: true,
                        dateFormat: 'yy-mm-dd',
                        // maxDate : '+0d',
                        //minDate:'+0d',

                        onSelect: function( selectedDate ) {
                            var option = this.id == "popup_container1" ? "minDate" : "maxDate",
                            instance = $( this ).data( "datepicker" ),
                            date = $.datepicker.parseDate(
                            instance.settings.dateFormat ||
                                $.datepicker._defaults.dateFormat,
                            selectedDate, instance.settings );
                            dates.not( this ).datepicker( "option", option, date );
                        }

                    });
                });
                $("#btnClear").click(function (){
                    jqueryclearDate("popup_container1","popup_container2");
                    $('#msg').html('');
                    $('#uploadFile')[0].reset();
                    $("#statusSel").val("-1");
                    jQuery("#reqGrid").jqGrid('setGridParam',{url:"getallRequestReportFieldsForArticleMC",datatype:'json',page:1}).trigger("reloadGrid");
                });
                var startDate,endDate,categoryName,subCategory,campaign,ticketNo,Status;
                $("#btnSearch").click(function (){
                    startDate=$("#popup_container1").val();
                    endDate=$("#popup_container2").val();
                    categoryName=$("#categorySel option:selected").val();
                    subCategory=$("#subcategorySel option:selected").val();
                    campaign=$("#eventSel option:selected").val();
                    ticketNo=$("#tbTicketNo").val();
                    Status = $("#statusSel option:selected").val();
                    startDate=$.trim(startDate);
                    endDate=$.trim(endDate);
                    ticketNo=$.trim(ticketNo);

                    //                    alert("sDate :"+startDate +"k");
                    //                    alert("eDate :"+endDate+"k");
                    //                    alert("CName :"+categoryName+"k");
                    //                    alert("SCNAME :"+subCategory+"k");
                    //                    alert("event :"+campaign+"k");
                    //                    alert("Tno :"+ticketNo+"k");
                    //                    alert("Status:"+Status+"k");
                    

                    if((ticketNo ==null || ticketNo.length==0) && (startDate==undefined || startDate==null || startDate.length==0) && (endDate==undefined || endDate==null || endDate.length==0) && (categoryName==-1 ||categoryName==null)&&(campaign==-1 ||campaign==null) &&(Status==null || Status==-1) && (subCategory==-1 ||subCategory==null)){
                        alert("Please enter Ticket No or Promotion Initiation Date and Category.");
                        return false;
                    }else{

                        if((startDate==undefined || startDate==null || startDate.length==0) && (endDate==undefined || endDate==null || endDate.length==0)&&(ticketNo ==null || ticketNo.length==0)&& (categoryName==-1 ||categoryName==null)){
                            alert("Please enter Ticket No or Promotion Initiation Date and Category.");
                            return false;
                        }else{
                            if((ticketNo !=null && ticketNo.length>0) && ((startDate!=null && startDate.length>0)|| (endDate!=null && endDate.length>0)|| (categoryName!=-1 &&categoryName!=null))){
                                alert("Please enter Ticket No or Promotion Initiation Date and Category.");
                                return false;
                            }else if((ticketNo !=null && ticketNo.length>0) && ((startDate==undefined && startDate==null && startDate.length==0) || (endDate==undefined && endDate==null && endDate.length==0) || (categoryName==-1 && categoryName==null)||(campaign!=-1 &&campaign!=null) ||(Status!=null && Status!=-1) || (subCategory!=-1 &&subCategory!=null))){
                                alert("Please enter Ticket No or Promotion Initiation Date and Category.");
                                return false;
                            }else if((ticketNo !=null || ticketNo.length>0) && (startDate==undefined || startDate==null || startDate.length==0) && (endDate==undefined || endDate==null || endDate.length==0) && (categoryName==-1 ||categoryName==null)&&(campaign==-1 ||campaign==null) &&(Status==null || Status==-1) && (subCategory==-1 ||subCategory==null)){
                                $("#reporttype").val("INITIATION_DATE_CATEGORY_TICKET_NO");
                            }else  if((startDate!=undefined || startDate!=null || startDate.length>0) && (endDate==undefined || endDate==null || endDate.length==0)&&(ticketNo ==null || ticketNo.length==0)){
                                alert("Please enter To Date.");
                                return false;
                            }else if((startDate==undefined || startDate==null || startDate.length==0) && (endDate!=undefined || endDate!=null || endDate.length>0)&&(ticketNo ==null || ticketNo.length==0)){
                                alert("Please enter From Date.");
                                return false;
                            }else if((startDate!=undefined || startDate!=null || startDate.length>0)&& (endDate!=undefined || endDate!=null || endDate.length>0)&&(ticketNo ==null || ticketNo.length==0)&& (categoryName==-1 ||categoryName==null)){
                                alert("Please select Category.");
                                return false;
                            }else   if((startDate!=null || startDate.length>0) && (endDate!=null || endDate.length>0) && (categoryName!=-1 ||categoryName!=null) && (campaign==-1 ||campaign==null) &&(Status==null || Status==-1) && (subCategory==-1 ||subCategory==null) &&(ticketNo==null ||ticketNo.length==0)){                            //INITIATION_DATE_CATEGORY
                                $("#reporttype").val("INITIATION_DATE_CATEGORY");
                            }else if((startDate!=null || startDate.length>0) && (endDate!=null || endDate.length>0) && (categoryName!=-1 ||categoryName!=null) &&(campaign!=null || campaign!=-1) &&(subCategory==null || subCategory==-1)&&(ticketNo==null || ticketNo.length==0)&&(Status==null || Status==-1)){
                                //INITIATION_DATE_CATEGORY_EVENT
                                $("#reporttype").val("INITIATION_DATE_CATEGORY_EVENT");
                            }else if((startDate!=null || startDate.length>0) && (endDate!=null || endDate.length>0) && (categoryName!=-1 ||categoryName!=null) &&(subCategory!=null || subCategory!=-1)&&(ticketNo==null || ticketNo.length==0)&&(campaign==null || campaign==-1)&&(Status==null || Status==-1)){
                                //INITIATION_DATE_SUB_CATEGORY
                                $("#reporttype").val("INITIATION_DATE_SUB_CATEGORY");
                            }else if((startDate!=null || startDate.length>0) && (endDate!=null || endDate.length>0) && (categoryName!=-1 ||categoryName!=null) &&(Status!=null || Status!=-1)&&(ticketNo==null || ticketNo.length==0)&&(subCategory==null || subCategory==-1)&&(campaign==null || campaign==-1)){
                                //INITIATION_DATE_CATEGORY_STATUS
                                $("#reporttype").val("INITIATION_DATE_CATEGORY_STATUS");
                            }else if((campaign!=-1 ||campaign!=null) &&(Status!=null || Status!=-1) && (subCategory!=-1 ||subCategory!=null)&&(ticketNo!=null || ticketNo.length>0)){
                                alert("Please Select Sub Category or Campaign or Status along with Category and Initiation Date.");
                                $("#eventSel").val("-1");
                                $("#subcategorySel").val("-1");
                                $("#tbTicketNo").val("");
                                $("#statusSel").val("-1");
                                return false;
                            }
                            
                        }

                    }
                    //
                    //
                    //                    alert($("#reporttype").val());
                    //                    return false;

                });
            });
        </script>
    </head>
    <body >
        <jsp:include page="/jsp/menu/menuPage.jsp" />        
        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">
            <tr>
                <td>
                    <table  width="30%">
                        <tr>
                            <td align="center">
                                <h1>Promo Life Cycle - Article & MC Level</h1>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>
            <tr>
                <td width="100%" align="center" >
                    <div id="msg">
                        <s:actionmessage cssClass="successText"/>
                        <s:actionerror cssClass="errorText"/>
                    </div>
                </td>
            </tr>
            <tr>
                <td align="center">
                    <table cellspacing="4" cellpadding="4" width="60%">
                        <s:form  id="uploadFile"  action="submitReportRequestArticleMCLEvel" method="POST">
                            <s:token name="token"></s:token>
                            <s:hidden name="formVo.reporttype" id="reporttype" />
                            <tr>
                                <td>
                                    <table>
                                        <tr>
                                            <td align="right">Promotion Initiation Date </td>
                                            <td class="errorText">*&nbsp;&nbsp;</td>
                                            <td align="left"> From &nbsp;  <input name="formVo.startDate" type="text" class="datef" id="popup_container1" readonly="readonly"/>
                                                &nbsp;&nbsp;&nbsp;To &nbsp;
                                                <input name="formVo.endDate" type="text" class="datef" id="popup_container2" readonly="readonly"/>
                                                &nbsp;&nbsp;&nbsp;</td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table>

                                        <tr><td height="5px" ></td></tr>
                                        <tr>
                                            <td  align="right">Category</td>  <td class="errorText">*&nbsp;&nbsp;&nbsp;&nbsp;</td>

                                            <td align="left" ><s:select name="formVo.categorySel" id="categorySel"  list="categoryMap" headerKey="-1" headerValue="---Select Category---" value="---Select Category---" cssClass="dropdown"/></td>
                                        </tr>
                                        <tr><td height="5px" ></td></tr>

                                        <tr>
                                            <td  align="right">Campaign</td>
                                            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                            <td align="left" ><s:select name="formVo.eventSel" id="eventSel"  list="eventMap" headerKey="-1" headerValue="---Select Campaign---" value="---Select Campaign---" cssClass="dropdown"/></td>
                                            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                        </tr>
                                        <tr><td height="5px" ></td></tr>
                                        <tr>
                                            <td  align="right">Status</td>
                                            <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                            <td align="left">
                                                <s:select name="formVo.statusSel" id="statusSel" list="statutsMap"  headerKey="-1" headerValue="---Select Status----"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>                          
                                <td>
                                    <table>
                                        <tr id="trSubCategory">
                                            <td  align="left">Sub Category </td>
                                            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                            <td align="left">
                                                <s:select name="formVo.subcategorySel" id="subcategorySel" list="subcategoryMap"  headerKey="-1" headerValue="---Select Sub Category----"/>
                                            </td>
                                        </tr>
                                        <tr><td height="5px" ></td></tr>
                                        <tr id="">
                                            <td  align="right">Ticket No</td>
                                            <td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
                                            <td align="left">
                                                <input type="text" id="tbTicketNo" name="formVo.tbTicketNo" />
                                            </td>
                                        </tr>
                                        <tr><td height="5px" ></td></tr>

                                    </table>
                                </td>
                            </tr>
                            <tr id="">

                            <tr id="searchTR" >
                                <td colspan="8" align="center">
                                    <table align="center" >
                                        <tr>
                                            <td align="center" >
                                                <s:submit  type="button" name="btnSearch" id="btnSearch" value="Submit" cssClass="btn"/>
                                            </td>
                                            <td>
                                                <input type="button" name="btnClear" id="btnClear" value="Clear" class="btn"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                        </s:form>
                    </table>
                </td>
            </tr>
            <tr>
                <td  align="center">
                    <table id="reqGrid"></table>
                    <div id="reqPager"></div>
                </td>
            </tr>
        </table>
        <!--</div>-->

    </body>
</body>
</html>
