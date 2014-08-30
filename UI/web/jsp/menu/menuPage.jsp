<%-- 
    Document   : menuPage
    Created on : Dec 11, 2012, 2:38:03 PM
    Author     : krutij
--%>

<%@page import="com.fks.ui.constants.PropertyEnum"%>
<%@page import="com.fks.ui.constants.PromotionPropertyUtil"%>
<%@page import="com.fks.ui.constants.WebConstants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Promotion Management Workflow</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />

        <!--        <script type="text/javascript" src="js/jquery.min.js"></script>-->
        <script type="text/javascript" src="js/ddsmoothmenu.js"></script>
        <script type="text/javascript">
            
            ddsmoothmenu.init({
                mainmenuid: "menulink", //menu DIV id
                orientation: 'h', //Horizontal or vertical menu: Set to "h" or "v"
                classname: 'main_nav', //class added to menu's outer DIV
                //customtheme: ["#1c5a80", "#18374a"],
                contentsource: "markup" //"markup" or ["container_id", "path_to_menu_file"]
            })
        </script>        
    </head>
    <body>

        <jsp:include page="/jsp/menu/topHeader.jsp" />
        <!-- Menu Start Here  -->
        <div id="menulink" class="main_nav">
            <%

                java.util.Map<String, String> accessedMenuItems = (java.util.Map<String, String>) session.getAttribute(WebConstants.ACCESS_MENU);
                // out.print(accessedMenuItems);

                //Get SAP WEB UI LOGIN URL
                String sapWebUI = PromotionPropertyUtil.getPropertyString(PropertyEnum.SAP_WEB_UI);

                //
                java.util.Map<String, String> profileMap = (java.util.Map<String, String>) session.getAttribute(WebConstants.USER_PROFILE);
                String storeCodeLocation = (String) session.getAttribute(WebConstants.LOCATION);
                //System.out.println(profileMap);
            %>
            <ul>

                <% if (accessedMenuItems.containsKey("100")) {%>
                <li class="selected">
                    <a href="#"><%= accessedMenuItems.get("100")%></a>
                    <ul>
                        <% if (accessedMenuItems.containsKey("101")) {%>
                        <li><a href="#"><%= accessedMenuItems.get("101")%></a>
                            <ul>
                                <% if (accessedMenuItems.containsKey("1011")) {%>
                                <li><a href="createuser_jsp"><%= accessedMenuItems.get("1011")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("1012")) {%>
                                <li><a href="searchuser_jsp"><%= accessedMenuItems.get("1012")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("1013")) {%>
                                <li><a href="mapuserMch_jsp"><%= accessedMenuItems.get("1013")%></a></li>
                                <% }%>
                            </ul>
                        </li>
                        <% }%>


                        <% if (accessedMenuItems.containsKey("102")) {%>
                        <li><a href="orgmaster_jsp"><%= accessedMenuItems.get("102")%></a></li>
                        <% }%>

                        <% if (accessedMenuItems.containsKey("103")) {%>
                        <li><a href="categoryMCh_jsp"><%= accessedMenuItems.get("103")%></a></li>
                        <% }%>

                        <% if (accessedMenuItems.containsKey("104")) {%>
                        <li><a href="#"><%= accessedMenuItems.get("104")%></a>

                            <ul>
                                <% if (accessedMenuItems.containsKey("1041")) {%>
                                <li><a href="rolemaster_jsp"><%= accessedMenuItems.get("1041")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("1042")) {%>
                                <li><a href="maproleProfile_jsp"><%= accessedMenuItems.get("1042")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("1043")) {%>
                                <li><a href="profilemodule_jsp"><%= accessedMenuItems.get("1043")%></a></li>
                                <% }%>
                            </ul>
                        </li>
                        <% }%>

                        <% if (accessedMenuItems.containsKey("105")) {%>
                        <li><a href="workflow_jsp"><%= accessedMenuItems.get("105")%></a></li>
                        <% }%>
                        <% if (accessedMenuItems.containsKey("106")) {%>
                        <li><a href="#"><%= accessedMenuItems.get("106")%></a>
                            <ul>
                                <% if (accessedMenuItems.containsKey("1061")) {%>
                                <li><a href="event_jsp"><%= accessedMenuItems.get("1061")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("1062")) {%>
                                <li><a href="marketingtype_jsp"><%= accessedMenuItems.get("1062")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("1063")) {%>
                                <li><a href="rejection_jsp"><%= accessedMenuItems.get("1063")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("1064")) {%>
                                <li><a href="problem_jsp"><%= accessedMenuItems.get("1064")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("1065")) {%>
                                <li><a href="promotion_jsp"><%= accessedMenuItems.get("1065")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("1066")) {%>
                                <li><a href="task_jsp"><%= accessedMenuItems.get("1066")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("1067")) {%>
                                <li><a href="zone_jsp"><%= accessedMenuItems.get("1067")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("1068")) {%>
                                <li><a href="cal_jsp"><%= accessedMenuItems.get("1068")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("1069")) {%>
                                <li><a href="campaign_jsp"><%= accessedMenuItems.get("1069")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("1070")) {%>
                                <li><a href="dept_jsp"><%= accessedMenuItems.get("1070")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("1071")) {%>
                                <li><a href="showStoreProposalPendingList"><%= accessedMenuItems.get("1071")%></a></li>
                                <% }%>

                            </ul>
                        </li>
                        <% }%>
                        <% if (accessedMenuItems.containsKey("107")) {%>
                        <li><a href="leadtime_jsp"><%= accessedMenuItems.get("107")%></a></li>
                        <% }%>
                    </ul>
                </li>
                <% }%>

                <% if (accessedMenuItems.containsKey("200")) {%>
                <li>
                    <a href="#"><%= accessedMenuItems.get("200")%></a>
                    <ul>
                        <% if (accessedMenuItems.containsKey("201")) {%>
                        <li>
                            <a href="#"><%= accessedMenuItems.get("201")%></a>
                            <ul>
                                <% if (accessedMenuItems.containsKey("2011")) {%>
                                <li><a href="viewproposal_action?isUpdate=0"><%= accessedMenuItems.get("2011")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("2012")) {%>
                                <li><a href="proposal_jsp"><%= accessedMenuItems.get("2012")%></a></li>
                                <% }%>
                            </ul>
                        </li>
                        <% }%>



                        <%
                            if (accessedMenuItems.containsKey("202") && !(storeCodeLocation.equalsIgnoreCase("HO"))) {%>
                        <li>
                            <a href="proposal_dashboard_jsp"><%= accessedMenuItems.get("202")%></a>
                        </li>
                        <% }%>
                        <% if (accessedMenuItems.containsKey("203")) {%>
                        <li><a href="viewArticleDownload"><%= accessedMenuItems.get("203")%></a></li>
                        <% }%>

                        <% if (accessedMenuItems.containsKey("204")) {%>
                        <li><a href="viewMChUserDownloadhtm"><%= accessedMenuItems.get("204")%></a></li>
                        <% }%>
                    </ul>

                </li>
                <% }%>

                <% if (accessedMenuItems.containsKey("300")) {%>
                <li><a href="#"><%= accessedMenuItems.get("300")%></a>
                    <ul>
                        <% if (accessedMenuItems.containsKey("301")) {%>
                        <li><a href="initiatePromotion_action"><%= accessedMenuItems.get("301")%></a>
                            <ul>
                                <% if (accessedMenuItems.containsKey("3011")) {%>
                                <li><a href="initiatePromotion_action"><%= accessedMenuItems.get("3011")%></a></li>
                                <% }%>
                                <%--
                                <% if (accessedMenuItems.containsKey("3012")) {%>
                                <li><a href="buyxgety_action"><%= accessedMenuItems.get("3012")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("3013")) {%>
                                <li><a href="buyxgetyDiscount_action"><%= accessedMenuItems.get("3013")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("3019")) {%>
                                <li><a href="bxgysingle_ation"><%= accessedMenuItems.get("3019")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("3014")) {%>
                                <li><a href="flatDiscount_action"><%= accessedMenuItems.get("3014")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("3015")) {%>
                                <li><a href="powerpricing_action"><%= accessedMenuItems.get("3015")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("3016")) {%>
                                <li><a href="billticketsize_action"><%= accessedMenuItems.get("3016")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("3017")) {%>
                                <li><a href="poolrewardticketsize_action"><%= accessedMenuItems.get("3017")%></a></li>
                                <% }%>
                                --%>
                                <% if (accessedMenuItems.containsKey("3010")) {%>
                                <li><a href="viewPromointiate_action"><%= accessedMenuItems.get("3010")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("3012")) {%>
                                <li><a href="viewcreateSubPromoFile"><%= accessedMenuItems.get("3012")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("3018")) {%>
                                <li><a href="organizationdtl_action"><%= accessedMenuItems.get("3018")%></a></li>
                                <% }%>
                            </ul>
                        </li>
                        <% }%>
                        <% if (accessedMenuItems.containsKey("302")) {%>
                        <li><a href="viewInitiationDahsboard_action"><%= accessedMenuItems.get("302")%></a></li>
                        <% }%>
                        <% if (accessedMenuItems.containsKey("303")) {%>
                        <li><a href="viewArticleDownload"><%= accessedMenuItems.get("303")%></a></li>
                        <% }%>
                        <% if (accessedMenuItems.containsKey("304")) {%>
                        <li><a href="viewMChUserDownloadhtm"><%= accessedMenuItems.get("304")%></a></li>
                        <% }%>
                    </ul>
                </li>
                <% }%>

                <% if (accessedMenuItems.containsKey("400")) {%>
                <li><a href="#"><%= accessedMenuItems.get("400")%></a>
                    <ul>
                        <% if (accessedMenuItems.containsKey("401")) {%>
                        <li><a href="viewlevel1approval_action"><%= accessedMenuItems.get("401")%></a></li>
                        <% }%>
                        <% if (accessedMenuItems.containsKey("402")) {%>
                        <li><a href="viewlevel2approval_action"><%= accessedMenuItems.get("402")%></a></li>
                        <% }%>
                        <% if (accessedMenuItems.containsKey("403")) {%>
                        <li><a href="viewbusinessexigencyapproval_action"><%= accessedMenuItems.get("403")%></a></li>
                        <% }%>
                    </ul>
                </li>
                <% }%>

                <% if (accessedMenuItems.containsKey("500")) {%>
                <li><a href="#"><%= accessedMenuItems.get("500")%></a>

                    <ul>
                        <% if (accessedMenuItems.containsKey("501")) {%>
                        <li><a href="#"><%= accessedMenuItems.get("501")%></a>
                            <ul>
                                <% if (accessedMenuItems.containsKey("5011")) {%>
                                <li><a href="viewPromoExecuteApproval_action"><%= accessedMenuItems.get("5011")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("5012")) {%>
                                <li><a href="viewPromoExecuteDashboard_action"><%= accessedMenuItems.get("5012")%></a></li>
                                <% }%>
                            </ul>
                        </li>
                        <% }%>
                        <%
                            if (storeCodeLocation.equalsIgnoreCase("HO")) {
                        %>
                        <% if (accessedMenuItems.containsKey("502")) {%>
                        <li><a href="#"><%= accessedMenuItems.get("502")%></a>                            
                            <ul>
                                <% if (accessedMenuItems.containsKey("5021")) {%>
                                <li><a href="viewPromoSetup_action"><%= accessedMenuItems.get("5021")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("5022")) {%>
                                <li><a href="#"><%= accessedMenuItems.get("5022")%></a>
                                    <ul>
                                        <% if (accessedMenuItems.containsKey("50221")) {%>
                                        <li><a href="viewTaskCreation_action"><%= accessedMenuItems.get("50221")%></a></li>
                                        <% }%>
                                        <% if (accessedMenuItems.containsKey("50222")) {%>
                                        <li><a href="viewInwardTask_action"><%= accessedMenuItems.get("50222")%></a></li>
                                        <% }%>
                                        <% if (accessedMenuItems.containsKey("50223")) {%>
                                        <li><a href="viewOutwardTask_action"><%= accessedMenuItems.get("50223")%></a></li>
                                        <% }%>
                                    </ul>
                                </li>
                                <% }%>
                            </ul>
                        </li>
                        <% }%>
                        <% } else if (storeCodeLocation.equalsIgnoreCase("Zone")) {%>

                        <% if (accessedMenuItems.containsKey("503")) {%>
                        <li><a href="#"><%= accessedMenuItems.get("503")%></a>
                            <ul>
                                <% if (accessedMenuItems.containsKey("5031")) {%>
                                <li><a href="viewPromoSetup_action"><%= accessedMenuItems.get("5031")%></a></li>
                                <% }%>
                                <% if (accessedMenuItems.containsKey("5032")) {%>
                                <li><a href="#"><%= accessedMenuItems.get("5032")%></a>
                                    <ul>
                                        <% if (accessedMenuItems.containsKey("50321")) {%>
                                        <li><a href="viewTaskCreation_action"><%= accessedMenuItems.get("50321")%></a></li>
                                        <% }%>
                                        <% if (accessedMenuItems.containsKey("50322")) {%>
                                        <li><a href="viewInwardTask_action"><%= accessedMenuItems.get("50322")%></a></li>
                                        <% }%>
                                        <% if (accessedMenuItems.containsKey("50323")) {%>
                                        <li><a href="viewOutwardTask_action"><%= accessedMenuItems.get("50323")%></a></li>
                                        <% }%>
                                    </ul>
                                </li>
                                <% }%>
                            </ul>
                        </li>
                        <% }%>
                        <% }%>
                    </ul>
                </li>
                <% }%>

                <% if (accessedMenuItems.containsKey("600")) {%>
                <li><a href="#"><%= accessedMenuItems.get("600")%></a>
                    <ul>
                        <% if (accessedMenuItems.containsKey("601")) {%>
                        <li><a href="viewcommunication_action"><%= accessedMenuItems.get("601")%></a></li>
                        <% }%>
                    </ul>
                </li>
                <% }%>


                <% if (accessedMenuItems.containsKey("700")) {%>
                <li><a href="#"><%= accessedMenuItems.get("700")%></a>
                    <ul>
                        <% if (accessedMenuItems.containsKey("701")) {
                                if (storeCodeLocation.equalsIgnoreCase("HO")) {
                                    if (profileMap.containsKey("3") || profileMap.containsKey("4") || profileMap.containsKey("6")) {
                        %>
                        <li><a href="viewArticleMcWisePromoLifecycle"><%= accessedMenuItems.get("701")%></a></li>
                        <% }%>
                        <% } else if (storeCodeLocation.equalsIgnoreCase("ZONE")) {
                            if (profileMap.containsKey("3") || profileMap.containsKey("4")) {
                        %>
                        <li><a href="viewArticleMcWisePromoLifecycle"><%= accessedMenuItems.get("701")%></a></li>
                        <% }%>
                        <% }%>
                        <% }%>


                        <%
                            // profile map 's key 2 is F1 that is Initiator profile.
                            if (accessedMenuItems.containsKey("702")) {
                                if (storeCodeLocation.equalsIgnoreCase("HO")) {
                                    if (profileMap.containsKey("3") || profileMap.containsKey("4") || profileMap.containsKey("6")) {

                        %>
                        <li><a href="viewStoreProposal"><%= accessedMenuItems.get("702")%></a></li>
                        <% }%>
                        <% } else if (storeCodeLocation.equalsIgnoreCase("ZONE")) {
                            if (profileMap.containsKey("2") || profileMap.containsKey("3") || profileMap.containsKey("4")) {
                        %>
                        <li><a href="viewStoreProposal"><%= accessedMenuItems.get("702")%></a></li>
                        <% }%>
                        <% }%>
                        <% }%>


                        <% if (accessedMenuItems.containsKey("703")) {
                                if (storeCodeLocation.equalsIgnoreCase("HO")) {
                                    if (profileMap.containsKey("3") || profileMap.containsKey("4") || profileMap.containsKey("6")) {
                        %>
                        <li><a href="viewPromoLifecycle"><%= accessedMenuItems.get("703")%></a></li>
                        <% }%>
                        <% } else if (storeCodeLocation.equalsIgnoreCase("ZONE")) {
                            if (profileMap.containsKey("3") || profileMap.containsKey("4")) {
                        %>
                        <li><a href="viewPromoLifecycle"><%= accessedMenuItems.get("703")%></a></li>
                        <% }%>
                        <% }%>
                        <% }%>

                        <% if (accessedMenuItems.containsKey("704")) {
                                if (profileMap.containsKey("6")) {
                        %>
                        <li><a href="viewinternalTask"><%= accessedMenuItems.get("704")%></a></li>
                        <% }%>
                        <% }%>

                        <% if (accessedMenuItems.containsKey("705")) {
                                if (profileMap.containsKey("6")) {
                        %>
                        <li><a href="viewPromoTeamDashBoardReport"><%= accessedMenuItems.get("705")%></a></li>
                        <% }%>
                        <% }%>
                        <!-- <% if (accessedMenuItems.containsKey("796")) {
                                if (profileMap.containsKey("6")) {
                        %>
                        <li><a href="viewVendorBackedPromoReport"><%= accessedMenuItems.get("796")%></a></li>
                        <% }%>
                        <% }%> -->

                    </ul>
                </li>
                <% }%>
                <li><a style="cursor: hand;"  name=<%=sapWebUI%> target="_blank" onclick="window.open($(this).attr('name'), '_blank');">SAP LOGIN</a></li>
            </ul>

        </div>
        <!-- Menu Start End  -->        
    </body>
</html>