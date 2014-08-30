<%-- 
    Document   : mapUserMch
    Created on : Dec 17, 2012, 12:28:29 PM
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
        <title>User-MCH</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui-lightness/jquery-ui-1.8.6.custom.css" />
        <link rel="stylesheet" type="text/css" media="screen" href="css/ui.jqgrid.css" />

        <script type="text/javascript" src="js/jquery.min.js"></script>
        <script src="js/i18n/grid.locale-en.js" type="text/javascript"></script>
        <script src="js/jquery.jqGrid.min.js" type="text/javascript"></script>
        <script src="js/jquery-ui-core.min.js" type="text/javascript"></script>

        <link href="css/tabs.css" rel="stylesheet" type="text/css" />
        <script type="text/javascript" src="js/Tabfunction.js"></script>

        <script type="text/javascript">

            jQuery(document).ready(function(){
                var idEmp;

                var redirectemp=$("#redEmpId").val();
                //                  $("#exisEmpTr").show();
                //                    $("#redirectEmpTr").hide();
 
                if(redirectemp.length>0){
                    idEmp=redirectemp;
 
                    //document.getElementById('exisEmpTr').style.display = 'none';
                    $("#redirectEmpTr").show();
                    document.getElementById('exisEmpTr').style.display = 'none';
                    //   $("#exisEmpTr").hide();
                }else{
                    idEmp="";
                    //                     $("#exisEmpTr").show();
                    //                    $("#redirectEmpTr").hide();
                }
               
                var getEmpProfileData;
                $("#txtuserempName").autocomplete({
                    minLength : 1,
                    delay:10,
                    scrollHeight:100,
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
                        getuserprofileInfo(idEmp);
                    }
                });

                function getuserprofileInfo(idEmp){
                    $.ajax({
                        url: "getUserProfile?EmpID="+idEmp,
                        global: false,
                        type: "POST",
                        dataType:"json",
                        contanttype: 'text/json',
                        async:false,
                        error:function(){
                            alert("Can not connect to server");
                        },
                        success: function(data){
                            //alert("server data : "+data.rows.profileIdList);
                            getEmpProfileData= data.rows.profileIdList
                            setTabOptionByProfile(getEmpProfileData);
                        }
                    });
                }
                var activeTabName;
                function setTabOptionByProfile(profile){
                    $("#tab_1").attr("href", "#");
                    $("#tab_2").attr("href", "#");
                    $("#tab_3").attr("href", "#");
                    $("#btnF1Download").attr("disabled",false);
                    $("#btnF1Submit").attr("disabled",false);
                    //$("#tab_4").attr("href", "#");

                    if(profile.length>0){
                        for(var index=0;index<profile.length;index++)
                        {
                            //                            alert("Data :"+profile[index]);
                            if(profile[index]=="2" || profile[index]=="9"){
                                tabSwitch_2(1,3, 'tab_', 'content_');
                                $("#tab_1").attr("href", "javascript:tabSwitch_2(1,3,'tab_','content_');");
                                activeTabName="f1Tab";
                                //                                break;
                            }else if(profile[index]=="3"){
                                tabSwitch_2(2,3, 'tab_', 'content_');
                                $("#tab_2").attr("href", "javascript:tabSwitch_2(2,3,'tab_','content_');");
                                activeTabName="f2Tab";
                                //                                break;
                            }else if(profile[index]=="4"){
                                tabSwitch_2(3,3, 'tab_', 'content_');
                                $("#tab_3").attr("href", "javascript:tabSwitch_2(3,3,'tab_','content_');");
                                activeTabName="f3Tab";
                                //                                break;
                            }else if(profile[index]=="8"){
                                tabSwitch_2(1,3, 'tab_', 'content_');
                                $("#tab_1").attr("href", "javascript:tabSwitch_2(1,3,'tab_','content_');");
                                $("#tab_2").attr("href", "javascript:tabSwitch_2(2,3,'tab_','content_');");
                                $("#tab_3").attr("href", "javascript:tabSwitch_2(3,3,'tab_','content_');");
                                break;
                            }else{
                                tabSwitch_2(1,3, 'tab_', 'content_');
                                $("#tab_1").attr("href", "javascript:tabSwitch_2(1,3,'tab_','content_');");

                                $("#btnF1Download").attr("disabled",true);
                                $("#btnF1Submit").attr("disabled",true);
                            }
                            //                            if(profile[index]=="6"){
                            //                                tabSwitch_2(4,4, 'tab_', 'content_');
                            //                                $("#tab_4").attr("href", "javascript:tabSwitch_2(4,4,'tab_','content_');");
                            //                                activeTabName="f5Tab";
                            //                            }
                        }
                    }

                }
                
                $("#btnF1Download").click(function (){
                    //alert("F1");
                    if(idEmp.length==0){
                        alert("Please Select Employee!");
                        return false;
                    }
                    $('#sendEmpId').val(idEmp);
                    
                    downloadFile("downloadF1UserFile");
                });
                $("#btnF2Download").click(function (){
                    //alert("F2");
                    if(idEmp.length==0){
                        alert("Please Select Employee!");
                        return false;
                    }
                    $('#sendEmpId').val(idEmp);

                    downloadFile("downloadF2UserFile");
                });
                $("#btnF3Download").click(function (){
                    //alert("F3");
                    if(idEmp.length==0){
                        alert("Please Select Employee!");
                        return false;
                    }
                    $('#sendEmpId').val(idEmp);

                    downloadFile("downloadF3UserFile");
                });
                $("#btnF5Download").click(function (){
                    //alert("F4");
                    if(idEmp.length==0){
                        alert("Please Select Employee!");
                        return false;
                    }
                    $('#sendEmpId').val(idEmp);
                    
                    downloadFile("downloadF5UserFile");
                });
                $("#btnDownloadUSerMC").click(function (){
                    $('#wait-animation').show();
                    document.location.href = "downloadUserMCHMappingFile";
                    $('#wait-animation').hide();
                });

                function downloadFile(urlAction){
                    $('#wait-animation').show();
                    document.location.href = urlAction+"?sendEmpId="+idEmp;
                    $('#wait-animation').hide();
                }
                $("#btnF1Submit").click(function (){
                    if(idEmp.length==0){
                        alert("Please Select Employee!");
                        return false;
                    }
                    $('#sendEmpId').val(idEmp);
                    $('#sendProfileId').val("2");
                    var fileid=  document.getElementById("mchuserF1File").value;
                    // alert(fileid);
                    if(fileid==null ||fileid==""){
                        alert("Please select file to upload.");
                        return false;
                    }
                    var valid_extensions = /(.csv)$/i;
                    if(valid_extensions.test(fileid)){
                    } else{
                        alert('Invalid File! Please Upload CSV File Only');
                        return false;
                    }
                });
                $("#btnF2Submit").click(function (){
                    if(idEmp.length==0){
                        alert("Please Select Employee!");
                        return false;
                    }
                    $('#sendEmpId').val(idEmp);
                    $('#sendProfileId').val("3");

                    var fileid=  document.getElementById("mchuserF2File").value;
                    // alert(fileid);
                    if(fileid==null ||fileid==""){
                        alert("Please select file to upload.");
                        return false;
                    }
                    var valid_extensions = /(.csv)$/i;
                    if(valid_extensions.test(fileid)){
                    } else{
                        alert('Invalid File! Please Upload CSV File Only');
                        return false;
                    }
                });
                $("#btnF3Submit").click(function (){
                    if(idEmp.length==0){
                        alert("Please Select Employee!");
                        return false;
                    }
                    $('#sendEmpId').val(idEmp);
                    $('#sendProfileId').val("4");
                    var fileid=  document.getElementById("mchuserF3File").value;
                    // alert(fileid);
                    if(fileid==null ||fileid==""){
                        alert("Please select file to upload.");
                        return false;
                    }
                    var valid_extensions = /(.csv)$/i;
                    if(valid_extensions.test(fileid)){
                    } else{
                        alert('Invalid File! Please Upload CSV File Only');
                        return false;
                    }
                });
                $("#btnF5Submit").click(function (){
                    if(idEmp.length==0){
                        alert("Please Select Employee!");
                        return false;
                    }
                    $('#sendEmpId').val(idEmp);
                    $('#sendProfileId').val("6");
                    var fileid=  document.getElementById("mchuserF5File").value;
                    // alert(fileid);
                    if(fileid==null ||fileid==""){
                        alert("Please select file to upload.");
                        return false;
                    }
                    var valid_extensions = /(.csv)$/i;
                    if(valid_extensions.test(fileid)){
                    } else{
                        alert('Invalid File! Please Upload CSV File Only');
                        return false;
                    }
                });

                $("#btnReset").click(function (data){
                    $("#msg").html("");
                    $('#mapuser')[0].reset();
                    $('#sendEmpId').val('');
                    $("#sendProfileId").val('');
                    $('#redEmpName').val('');
                    $("#redEmpId").val('');
                    idEmp="";
                    $("#txtuserempName").val('');
                    tabSwitch_2(1,3, 'tab_', 'content_');
                    $("#tab_1").attr("href", "javascript:tabSwitch_2(1,3,'tab_','content_');");
                    $("#btnF1Download").attr("disabled",false);
                    $("#btnF1Submit").attr("disabled",false);
                });

            });
        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <div id="middle_cont">
            <s:form id="mapuser" action="donothing" method="POST" enctype="multipart/form-data">
                <table width="100%" border="0" cellspacing="0" cellpadding="0"  align="center">
                    <tr>
                        <td align="center"><h1>User MC-Code Mapping</h1></td>
                    </tr>

                    <tr><td height="15px"></td></tr>
                    <tr align="center">
                        <td align="center">
                            <div id="msg" >
                                <s:actionmessage cssClass="successText"/>
                                <s:actionerror cssClass="errorText" />
                            </div>
                        </td>
                    </tr>
                    <tr><td height="15px"></td></tr>
                    <tr>
                        <td>
                            <table width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                <tr id="redirectEmpTr" style="display: none">
                                    <td align="center" > Employee Name

                                        <%
                                                    String empid = (String) session.getAttribute(WebConstants.Redirect_Emp_id);
                                                    if (empid != null) {
                                                        System.out.println("Emp : " + empid);
                                        %>
                                        <input type="text" id="redEmpName" value="<%= session.getAttribute(WebConstants.Redirect_Emp_Name)%> : <%= session.getAttribute(WebConstants.Redirect_Emp_id)%> " />
                                        <input type="hidden"id="redEmpId"  value="<%= session.getAttribute(WebConstants.Redirect_Emp_id)%> "/>
                                        <% } else {%>
                                        <input type="hidden"id="redEmpId"  />
                                        <input type="hidden"id="redEmpName"  />

                                        <% }%>
                                    </td>
                                </tr>
                                <tr align="center" id="exisEmpTr">
                                    <td>
                                        <table width="40%" border="0" align="center" cellpadding="2" cellspacing="4" >
                                            <tr>
                                                <td >Employee Name :<span class="errorText">&nbsp;*</span></td>
                                                <td ><input type="text" size="40px" id="txtuserempName" /></td>
                                            </tr>
                                        </table>
                                    </td>

                                </tr>
                                <tr>
                                    <td align="center">
                                        <table width="74%" border="0" align="center" cellpadding="2" cellspacing="4" >
                                            <tr><td  style="height:20px"></td></tr>
                                            <s:hidden id="sendEmpId" name="sendEmpId" />
                                            <s:hidden id="sendProfileId" name="sendProfileId" />
                                            <tr>
                                                <td align="center">
                                                    <div id="tabMaster" class="tabbed_box">
                                                        <ul class="tabs">
                                                            <li><a href="javascript:tabSwitch_2(1,3,'tab_','content_');" id="tab_1" class="active">F1 - Initiator</a></li>
                                                            <li><a href="javascript:tabSwitch_2(2,3,'tab_','content_');" id="tab_2">F2 - Approver 1</a></li>
                                                            <li><a href="javascript:tabSwitch_2(3,3,'tab_','content_');" id="tab_3">F3 - Approver 2</a></li>
                                                            <!--                                                            <li><a href="javascript:tabSwitch_2(4,4,'tab_','content_');" id="tab_4">Profile - F5</a></li>-->

                                                        </ul>
                                                        <div id="content_1" class="content">
                                                            <table id="profile1" width="65%" >
                                                                <tr><td height="10px"></td>
                                                                <tr>
                                                                    <td align="left">
                                                                        Upload MCH-F1 File:
                                                                    </td>
                                                                    <td align="left">
                                                                        <s:file id ="mchuserF1File" name="userMCHF1File" ></s:file>
                                                                    </td>
                                                                    <td align="left" >  
                                                                        <s:submit  id="btnF1Submit" name="btnF1Submit" value="Upload" cssClass="btn" action="submitUserMCH" ></s:submit>
                                                                    </td>
                                                                </tr>
                                                                <tr><td height="15px"></td>
                                                                </tr>
                                                                <tr>
                                                                    <td  colspan="2"align="right">
                                                                        <%-- <s:submit id="btnF1Download" name="btnF1Download" value=" Download F1 User MCH File" cssClass="largebtn" action="downloadF1UserFile" ></s:submit> --%>
                                                                        <input type="button" id="btnF1Download" name="btnF1Download" value=" Download F1 User MCH File" Class="largebtn"/>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </div>

                                                        <div id="content_2" class="content">
                                                            <table id="profile2" class="proposalMenu"   width="65%" >
                                                                <tr><td height="10px"></td>
                                                                <tr>
                                                                    <td align="left">
                                                                        Upload MCH-F2 File:
                                                                    </td>
                                                                    <td align="left">
                                                                        <s:file id ="mchuserF2File" name="userMCHF2File" ></s:file>
                                                                    </td>
                                                                    <td align="left" >
                                                                        <s:submit  id="btnF2Submit" name="btnF2Submit" value="Upload" cssClass="btn" action="submitUserMCH" ></s:submit>
                                                                    </td>
                                                                </tr>
                                                                <tr><td height="15px"></td>
                                                                <tr>
                                                                    <td  colspan="2"align="right">
                                                                        <%-- <s:submit id="btnF2Download" name="btnF2Download" value=" Download F2 User MCH File" cssClass="largebtn" action="downloadF2UserFile" ></s:submit> --%>
                                                                        <input type="button" id="btnF2Download" name="btnF2Download" value=" Download F2 User MCH File" Class="largebtn"/>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </div>

                                                        <div id="content_3" class="content">
                                                            <table id="profile3" class="initateMenu"   width="65%" >
                                                                <tr><td height="10px"></td>
                                                                <tr>
                                                                    <td align="left">
                                                                        Upload MCH-F3 File:
                                                                    </td>
                                                                    <td align="left">
                                                                        <s:file id ="mchuserF3File" name="userMCHF3File" ></s:file>
                                                                    </td>
                                                                    <td align="left" >
                                                                        <s:submit  id="btnF3Submit" name="btnF3Submit" value="Upload" cssClass="btn" action="submitUserMCH" ></s:submit>
                                                                    </td>
                                                                </tr>
                                                                <tr><td height="15px"></td>
                                                                <tr>
                                                                    <td  colspan="2"align="right">
                                                                        <%-- <s:submit id="btnF3Download" name="btnF3Downlaod" value=" Download F3 User MCH File" cssClass="largebtn" action="downloadF3UserFile" ></s:submit> --%>
                                                                        <input type="button" id="btnF3Download" name="btnF3Downlaod" value=" Download F3 User MCH File" Class="largebtn"/>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </div>
                                                        <div id="content_4" class="content" style="display: none">
                                                            <table id="profile4" class="approvalMenu"   width="65%" >
                                                                <tr><td height="10px"></td>
                                                                <tr>
                                                                    <td align="left">
                                                                        Upload MCH-F5 File:
                                                                    </td>
                                                                    <td align="left">
                                                                        <s:file id ="mchuserF5File" name="userMCHF5File" ></s:file>
                                                                    </td>
                                                                    <td align="left" >
                                                                        <s:submit  id="btnF5Submit" name="btnF5Submit" value="Upload" cssClass="btn" action="submitUserMCH" ></s:submit>
                                                                    </td>
                                                                </tr>
                                                                <tr><td height="15px"></td>
                                                                <tr>
                                                                    <td  colspan="2"align="right">
                                                                        <%--<s:submit id="btnF5Download" name="btnF4Downlaod" value=" Download F4 User MCH File" cssClass="largebtn" action="downloadF4UserFile" ></s:submit>--%>
                                                                        <input type="button" id="btnF5Download" name="btnF5Download" value=" Download F5 User MCH File" Class="largebtn"/>
                                                                    </td>
                                                                </tr>
                                                            </table>
                                                        </div>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <table width="20%" align="center">
                                            <tr>
                                                <td align="center">
                                                    <input id="btnReset" name="btnReset" type="button" value="CLEAR"  class="btn"/>
                                                </td>
                                                <td align="center">
                                                    <input id="btnDownloadUSerMC" name ="btnDownloadUSerMC" type="button" value="Download User MCH" class="largebtn" />
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
        </div>
    </body>
</html>
