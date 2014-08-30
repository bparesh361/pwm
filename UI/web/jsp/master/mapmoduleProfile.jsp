<%-- 
    Document   : mapmoduleProfile
    Created on : Dec 7, 2012, 4:10:54 PM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Profile Setting</title>
        <link href="css/tabs.css" rel="stylesheet" type="text/css" />       
        <script type="text/javascript" src="js/Tabfunction.js"></script>

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
           

                var modulesIDList=""   ;
                var modulesNameList=""   ;

                $.ajax({
                    url: "getmodules",
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
                        
                        var masterRowcnt=0;
                        var proposalRowCnt=0;
                        var initiateRowCnt=0;
                        var approvalcnt=0;
                        var execnt=0;
                        var commcnt=0;
                        var othercnt=0;
                        modulesIDList=data.rows.moduleIdList;
                        modulesNameList=data.rows.moduleNameList;

                        // alert("total : "+modulesIDList.length);
                        for(var i=0; i<modulesIDList.length ; i++){
                            //                            alert("module id :"+modulesIDList[i]+".... SubString : "+modulesIDList[i].substring(0,1));
                            if(modulesIDList[i].substring(0,1)=="1"){
                                var masTR;
                                //alert("Match 1 :"+modulesIDList[i]);
                                if(masterRowcnt%3==0 || masterRowcnt==0){                                       
                                    masTR=$('<tr></tr>');
                                }
                                var newMasterTD=$('<td align="left"><input type="checkbox" class = "chcktbl" id='+modulesIDList[i]+'>'+modulesNameList[i]+'</td>')
                                masTR.append(newMasterTD);
                                $("#masterMenu > tbody").append(masTR);
                                masterRowcnt++;
                              
                            }else if(modulesIDList[i].substring(0,1)=="2"){
                                //                                alert("Match 2 :"+modulesIDList[i]);
                                var proTR;
                                if(proposalRowCnt%3==0 || proposalRowCnt==0){
                                    //  alert(" New Row value : "+masterRowcnt%3);
                                    proTR=$('<tr></tr>');
                                }
                                var newProTD=$('<td align="left"><input type="checkbox" class = "chcktbl" id='+modulesIDList[i]+'>'+modulesNameList[i]+'</td>')
                                proTR.append(newProTD);
                                $("#proposalMenu > tbody").append(proTR);
                                proposalRowCnt++;
                                //                                alert("Module : "+modulesIDList[i]+" added in proposal.");
                            }
                            else if(modulesIDList[i].substring(0,1)=="3"){
                                //                                alert("Match 3 :"+modulesIDList[i]);
                                var iniTR;
                                if(initiateRowCnt%3==0 || initiateRowCnt==0){
                                    //  alert(" New Row value : "+masterRowcnt%3);
                                    iniTR=$('<tr></tr>');
                                }
                                var newInitiateTD=$('<td align="left"><input type="checkbox" class = "chcktbl" id='+modulesIDList[i]+'>'+modulesNameList[i]+'</td>')
                                iniTR.append(newInitiateTD);
                                $("#initateMenu > tbody").append(iniTR);
                                initiateRowCnt++;
                            }else if(modulesIDList[i].substring(0,1)=="4"){
                                //alert("Match 4 :"+modulesIDList[i]);
                                var appTR;
                                if(approvalcnt%3==0 || approvalcnt==0){
                                    //  alert(" New Row value : "+masterRowcnt%3);
                                    appTR=$('<tr></tr>');
                                }
                                var newapprovalTD=$('<td align="left"><input type="checkbox" class = "chcktbl" id='+modulesIDList[i]+'>'+modulesNameList[i]+'</td>')
                                appTR.append(newapprovalTD);
                                $("#approvalMenu > tbody").append(appTR);
                                approvalcnt++;
                            }else if(modulesIDList[i].substring(0,1)=="5"){
                                //alert("Match 5 :"+modulesIDList[i]);
                                var exeTR;
                                if(execnt%3==0 || execnt==0){
                                    //  alert(" New Row value : "+masterRowcnt%3);
                                    exeTR=$('<tr></tr>');
                                }
                                var newExecTD=$('<td align="left"><input type="checkbox" class = "chcktbl" id='+modulesIDList[i]+'>'+modulesNameList[i]+'</td>')
                                exeTR.append(newExecTD);
                                $("#execMenu > tbody").append(exeTR);
                                execnt++;
                            }else if(modulesIDList[i].substring(0,1)=="6"){
                                //alert("Match 6 :"+modulesIDList[i]);
                                var comTR;
                                if(commcnt%3==0 || commcnt==0){
                                    //  alert(" New Row value : "+masterRowcnt%3);
                                    comTR=$('<tr></tr>');
                                }
                                var newcommTD=$('<td align="left"><input type="checkbox" class = "chcktbl" id='+modulesIDList[i]+'>'+modulesNameList[i]+'</td>')
                                comTR.append(newcommTD);
                                $("#commuMenu > tbody").append(comTR);
                                commcnt++;
                            }else if(modulesIDList[i].substring(0,1)=="7"){
                                //alert("Match 6 :"+modulesIDList[i]);
                                var othTR;
                                if(othercnt%3==0 || othercnt==0){
                                    //  alert(" New Row value : "+masterRowcnt%3);
                                    othTR=$('<tr></tr>');
                                }
                                var newcommTD=$('<td align="left"><input type="checkbox" class = "chcktbl" id='+modulesIDList[i]+'>'+modulesNameList[i]+'</td>')
                                othTR.append(newcommTD);
                                $("#otherMenu > tbody").append(othTR);
                                othercnt++;
                            }
                        }
                    }                
                });

                $("#profileSel").change(function(){
                    var profile=$("#profileSel option:selected").val();
                    if(profile !=-1){
                        $.ajax({
                            url: "getProfileModule?profileId="+profile,
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
                               
                                var id=new Array();
                                var cnt=0;
                                $('.chcktbl:checked').attr('checked', false);
                                $("input[type=checkbox][id*=]").each(function(){
                                    id[cnt]=$(this).attr("id");
                                    cnt++;
                                });
                                //alert("selected Role IDs : "+data.rows.moduleIdList );
                                //alert(id);
                                if(data.rows.moduleIdList.length>0){
                                    //alert(id.length);
                                    for(var j=0;j<id.length;j++){
                                        // alert(data.rows.moduleIdList.length);
                                        for(var i=0; i<data.rows.moduleIdList.length ; i++){
                                            if(id[j]==data.rows.moduleIdList[i]){
                                                // alert("selected Role IDs : "+data.rows.moduleIdList[i]);
                                                $("#" + id[j]).attr('checked',true);
                                            }
                                        }
                                    }
                                }else{
                                    alert(data.resp);
                                }                             
                            }
                        });
                    }
                });

                $("#btnSubmit").click(function (){

                    var val = [];
                    var submitcnt=0;
                    var profile=$("#profileSel option:selected").val();
                    if(profile==-1){
                        alert("Please Select Profile!")
                        return false;
                    }
                    $("input[type=checkbox][id*=]").each(function(){
                        var id=$(this).attr("id");
                        if(id!="checkBlock" && $('#'+id).is(':checked')){
                            val[submitcnt]=id;
                            submitcnt++;
                        }
                    });

                    if(val.length==0){
                        alert("Please select Modules!.");
                        return false;
                    }


                    $("#SelectedIDs").val(val);
                    // alert("selected :"+$("#SelectedIDs").val());
                    //$("#existRoleID").val(selectedRoleID);
                });
                $("#btnReset").click(function (data){

                    $("#tab_1").show();
                    $('#profileSel').val("-1");
                    $("#SelectedIDs").val('');
                    $("#message").html('');
                    $("#form")[0].reset();
                });

            });
        </script>
    </head>
    <body >
        <jsp:include page="/jsp/menu/menuPage.jsp" />
        <table width="100%" align="center">
            <tr><td  style="height:10px"></td></tr>
            <tr>
                <td align="center"><h1>Profile Setting</h1></td>
            </tr>
            <tr>
                <td align="center">
                    <div id="message" align="center">
                        <s:actionmessage cssClass="successText"/>
                        <s:actionerror cssClass="errorText" />
                    </div>
                </td>
            </tr>
            <tr><td  style="height:10px"></td></tr>
            <tr>
                <td align="center">
                    <s:form action="donothing" id="form" method="POST" >
                        <table width="80%" align="center"  cellpadding="0" cellspacing="0" >
                            <tr><td  style="height:10px"></td></tr>
                            <tr>
                                <td align="center">
                                    <table align="center" width="40%">
                                        <tr>
                                            <td>
                                                Profile :<span class="errorText">&nbsp;*</span>
                                            </td>
                                            <td align="left">
                                                <s:select name="profileSel" id="profileSel"  list="profileMap" headerKey="-1" headerValue="---Select Profile---" value="---Select Profile---" cssClass="dropdown"/>
                                            </td>
                                        </tr>
                                    </table>
                                </td>
                            </tr>
                            <tr><td  style="height:20px"></td></tr>
                            <tr>
                                <td>
                                    <div id="tabMaster" class="tabbed_box">
                                        <ul class="tabs">
                                            <li><a href="javascript:tabSwitch_2(1,7,'tab_','content_');" id="tab_1" class="active">Master</a></li>
                                            <li><a href="javascript:tabSwitch_2(2,7,'tab_','content_');" id="tab_2">Proposal Management</a></li>
                                            <li><a href="javascript:tabSwitch_2(3,7,'tab_','content_');" id="tab_3">Promotion Initiate</a></li>
                                            <li><a href="javascript:tabSwitch_2(4,7,'tab_','content_');" id="tab_4">Promotion Approval</a></li>
                                            <li><a href="javascript:tabSwitch_2(5,7,'tab_','content_');" id="tab_5">Promotion Execution</a></li>
                                            <li><a href="javascript:tabSwitch_2(6,7,'tab_','content_');" id="tab_6">Promotion Communication</a></li>
                                            <li><a href="javascript:tabSwitch_2(7,7,'tab_','content_');" id="tab_7">Reports</a></li>
                                        </ul>

                                        <div id="content_1" class="content">
                                            <table id="masterMenu" class="masterMenu"   width="100%" >
                                                <tr></tr>
                                            </table>
                                        </div>

                                        <div id="content_2" class="content">
                                            <table id="proposalMenu" class="proposalMenu"   width="100%" >
                                                <tr></tr>
                                            </table>
                                        </div>

                                        <div id="content_3" class="content">
                                            <table id="initateMenu" class="initateMenu"   width="100%" >
                                                <tr></tr>
                                            </table>
                                        </div>
                                        <div id="content_4" class="content">
                                            <table id="approvalMenu" class="approvalMenu"   width="100%" >
                                                <tr></tr>
                                            </table>
                                        </div>
                                        <div id="content_5" class="content">
                                            <table id="execMenu" class="execMenu"   width="100%" >
                                                <tr></tr>
                                            </table>
                                        </div>
                                        <div id="content_6" class="content">
                                            <table id="commuMenu" class="commuMenu"   width="100%" >
                                                <tr></tr>
                                            </table>
                                        </div>
                                        <div id="content_7" class="content">
                                            <table id="otherMenu" class="otherMenu"   width="100%" >
                                                <tr></tr>
                                            </table>
                                        </div>
                                    </div>
                                </td>
                            </tr>

                            <tr><td  style="height:10px"></td></tr>
                            <tr>
                                <td align="center">
                                    <table width="10%">
                                        <tr>
                                            <td align="center" >
                                                <s:submit action="submitprofilemodule" align="center" id="btnSubmit" name="btnSubmit" value="Save"  cssClass="btn" />
                                                <!--                                                <input type="submit" align="center" id="btnSubmit" name="btnSubmit" value="Save"  class="btn"/>-->
                                                <s:hidden name="SelectedIDs" id="SelectedIDs"/>
                                            </td>
                                            <td align="center">
                                                <input align="center" type="button" class="btn"  id="btnReset" name="btnReset" value="Clear" />
                                            </td>
                                        </tr>
                                    </table>
                                </td>

                            </tr>
                            <tr><td  style="height:10px"></td></tr>
                        </table>
                    </s:form>
                </td>
            </tr>
        </table>
    </body>
</html>
