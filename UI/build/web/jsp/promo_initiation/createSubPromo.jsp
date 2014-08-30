<%-- 
    Document   : createSubPromo
    Created on : Jan 24, 2013, 11:02:34 AM
    Author     : krutij
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Promotion Initiation</title>
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
        <script type="text/javascript" src="js/jquery_ui_validations.js"></script>
        <script type="text/javascript" src="js/PromotionCommonUtil.js"></script>
        <script type="text/javascript" src="js/SubPromotionUtil.js"></script>
        <script type="text/javascript" src="js/thickbox.js"></script>
        <script type="text/javascript">
            jQuery(document).ready(function(){

                jQuery("#reqGrid").jqGrid({
                    url:"",
                    datatype: 'local',
                    width: 900,
                    height:110,
                    colNames:['Ticket Number','Request Date','Request Name', 'Campaign','Marketing Type','Category','Sub Category'],
                    colModel:[
                        {name:'reqno',index:'reqno', width:170, align:"center"},
                        {name:'date',index:'date', width:200, align:"center"},
                        {name:'reqName',index:'reqName', width:170, align:"center"},
                        {name:'event',index:'event', width:200, align:"center"},
                        {name:'mktgtype',index:'mktgtype', width:200, align:"center"},
                        {name:'category',index:'category', width:200, align:"center"},
                        {name:'subcategory',index:'subcategory', width:200, align:"center"},

                    ],
                    rowNum:30,
                    rowList:[30],
                    viewrecords: true,
                    pager: '#reqPager',
                    multiselect: false,
                    headertitles: true,
                    //                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:"",
                    onSelectRow: function(id) {
                        if(isMasterReqSelected==true){
                            if(id!=$("#mstPromoId").val()){
                                alert("Request can not be changed.");
                                $("#reqGrid").resetSelection();
                                $("#reqGrid").jqGrid('setSelection', $("#mstPromoId").val());                                
                            }

                        }
                        if(isMasterReqSelected==false){
                            if(isManualEntryFormSubmit==1 && jQuery("#XarticleGrid tr").length>1 && id!=$("#mstPromoId").val()){                                                                
                                //When One Article is added into article grid in case of manual entry
                                //Following code prevent user to reselect the master request from the grid
                                alert("Request can not be changed.");
                                $("#reqGrid").resetSelection();
                                $("#reqGrid").jqGrid('setSelection', $("#mstPromoId").val());                                
                            }else{
                                $("#mstPromoId").val(id);
                            }
                        }
                    }
                    ,gridComplete:function(){
                        //This happens when user navigate from one page to another.
                        //When One Article is added into article grid in case of manual entry
                        //Following code prevent user to reselect the master request from the grid
                        if(isMasterReqSelected==false && isManualEntryFormSubmit==1 && jQuery("#XarticleGrid tr").length>1){
                            $("#reqGrid").resetSelection();
                            $("#reqGrid").jqGrid('setSelection', $("#mstPromoId").val());
                        }


                        //Make Master Requqest Selection Again
                        // And Set isMasterReqSelected=true For preventing user to select another request
                        var hdnPromoId=$("#hdnFileUploadMstPromoId").val();
                        if(hdnPromoId!=undefined && hdnPromoId.length>0 && hdnPromoId!=null){
                            //                        alert("promo Id : "+hdnPromoId);
                            $("#reqGrid").resetSelection();
                            $("#reqGrid").jqGrid('setSelection', hdnPromoId);
                            isMasterReqSelected=true;
                            $("#mstPromoId").val(hdnPromoId);
                        }
                    }
                });

                jQuery("#XarticleGrid").jqGrid({
                    url:"",
                    datatype: 'local',
                    width: 850,
                    height:110,
                    colNames:['SET ID','SET','Article Number','Article Description','MC Code', 'MC Description','QTY','Brand Code', 'Brand Description'],
                    colModel:[
                        {name:'setId',index:'setId', width:100, align:"center",hidden:true},
                        {name:'setDesc',index:'setDesc', width:100, align:"center"},
                        {name:'xartNo',index:'xartNo', width:100, align:"center"},
                        {name:'xartDesc',index:'xartDesc', width:200, align:"center"},
                        {name:'xmcCode',index:'xmcCode', width:100, align:"center"},
                        {name:'xmcDesc',index:'xmcDesc', width:200, align:"center"},
                        {name:'xqty',index:'xqty', width:150, align:"center"},
                        {name:'xbrandCode',index:'xbrandCode', width:100, align:"center",hidden:true},
                        {name:'xbrandDesc',index:'xbrandDesc', width:200, align:"center",hidden:true},
                    ],
                    //                    rowNum:250,
                    rowList:[],
                    pgbuttons: false,
                    viewrecords: true,
                    pager: '#XarticlePager',
                    
                    editurl:'donothing',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:""
                }).navGrid('#XarticlePager',
                {add:false, edit:false, del:false, search:false, refresh: false},
                {width:"auto"},// Default for Add
                {width:"auto"},// Default for edit
                {width:"auto"}// Default for Delete
            ).navButtonAdd(
                '#XarticlePager',
                {
                    caption:"DELETE",
                    buttonicon:"ui-icon-add",
                    onClickButton: function(){
                        var toDelete = $("#XarticleGrid").jqGrid('getGridParam','selrow');
                        //alert("Selected Row :"+toDelete);
                        var maxRows=jQuery("#XarticleGrid tr").length;
                        if(toDelete!=null){
                            if(maxRows>1){
                                //                                alert("inside");
                                $("#XarticleGrid").jqGrid(
                                'delGridRow',
                                toDelete,
                                {url:'donothing',reloadAfterSubmit:true}
                            );
                                return true;
                            }
                        }else{
                            alert("Please, Select a Row");
                            return false;
                        }
                    }
                }
            );

                jQuery("#uploadFileGrid").jqGrid({
                    url:"",
                    datatype: 'local',
                    width: 400,
                    height:110,
                    colNames:['Set ID','Set Name','Download Article File','Article File'],
                    colModel:[
                        {name:'uploadFileSetID',index:'uploadFileSetID', width:170, align:"center",hidden:true},
                        {name:'uploadFileSetName',index:'uploadFileSetName', width:200, align:"center"},
                        {name:'downloadArticleFile',index:'downloadArticleFile', width:170, align:"center"},
                        {name:'uploadFilePath',index:'uploadFilePath', width:170, align:"center",hidden:true}
                    ],
                    rowNum:5,
                    rowList:[5,7,10],
                    viewrecords: true,
                    pager: '#uploadFilePager',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:""
                });
                //                .navGrid('#uploadFilePager',
                //                {add:false, edit:false, del:false, search:false, refresh: false},
                //                {width:"auto"},// Default for Add
                //                {width:"auto"},// Default for edit
                //                {width:"auto"}// Default for Delete
                //            ).navButtonAdd(
                //                '#uploadFilePager',
                //                {
                //                    caption:"Del",
                //                    buttonicon:"ui-icon-add",
                //                    onClickButton: function(){
                //                        var toDelete = $("#uploadFileGrid").jqGrid('getGridParam','selrow');
                //                        //alert("Selected Row :"+toDelete);
                //                        var maxRows=jQuery("#uploadFileGrid tr").length;
                //                        if(toDelete!=null){
                //                            if(maxRows>1){
                //                                $("#uploadFileGrid").jqGrid(
                //                                'delGridRow',
                //                                toDelete,
                //                                {url:'donothing',reloadAfterSubmit:true}
                //                            );
                //                                return true;
                //                            }
                //                        }else{
                //                            alert("Please, Select a Row");
                //                            return false;
                //                        }
                //                    }
                //                }
                //            );

                jQuery("#discountGrid").jqGrid({
                    url:"",
                    datatype: 'local',
                    width: 800,
                    height:110,
                    colNames:['setid','SET','Discount Type','Value','Qty'],
                    colModel:[
                        {name:'discsetid',index:'discsetid', width:100, align:"center",hidden:true},
                        {name:'discSetName',index:'discSetName', width:100, align:"center"},
                        {name:'discConfig',index:'discConfig', width:200, align:"center"},
                        {name:'discValue',index:'discValue', width:100, align:"center"},
                        {name:'discQty',index:'discQty', width:100, align:"center"},

                    ],
                    rowNum:5,
                    rowList:[20],
                    viewrecords: true,
                    pager: '#discountPager',
                    editurl:'donothing',
                    multiselect: false,
                    loadonce:true,
                    ignoreCase:true,
                    imgpath: "themes/basic/images",
                    caption:""
                    //                    ,gridComplete:function(){
                    //                        SetDiscountConfigGridDataOnFileFailureInGridComplete();
                    //                    }
                }).navGrid('#discountPager',
                {add:false, edit:false, del:false, search:false, refresh: false},
                {width:"auto"},// Default for Add
                {width:"auto"},// Default for edit
                {width:"auto"}// Default for Delete
            ).navButtonAdd(
                '#discountPager',
                {
                    caption:"DELETE",
                    buttonicon:"ui-icon-add",
                    onClickButton: function(){
                        var toDelete = $("#discountGrid").jqGrid('getGridParam','selrow');                        
                        var maxRows=jQuery("#discountGrid tr").length;                        
                        if(toDelete!=null){
                            if(maxRows>1){                                

                                //                                $("#discountGrid").jqGrid(
                                //                                'delGridRow',
                                //                                toDelete,
                                //                                {url:'donothing',reloadAfterSubmit:true}
                                //                            );
                                                                
                                if (!(confirm('Are you sure you want to delete record?'))) {
                                    return false;
                                }
                                $('#discountGrid').jqGrid('delRowData',toDelete);
                                
                                var promoTypeId=$("#promoSel").val();
                                
                                //Single Entry Validation Enable Add Button
                                //2 = Buy X and Y @ Discounted price
                                //3 = Flat Price
                                // 6= Ticket Size ( Pool Reward)
                                // 7 =BXGY
                                if(promoTypeId==2 || promoTypeId==3 || promoTypeId==6 || promoTypeId==7){
                                    var checkGridRows=jQuery("#discountGrid tr").length;                                    
                                    if(checkGridRows<2){
                                        $("#btnAddDiscDtl").attr("disabled",false);
                                    }
                                }

                                if(promoTypeId==4){                                     
                                    if(maxRows==2){                                       
                                        $("#disConfigSel").attr("disabled",false);
                                    }

                                }
                                return true;
                            }
                        }else{
                            alert("Please, Select a Row");
                            return false;
                        }
                    }
                }
            );
                

                ////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////// PAGE LOAD FUCNTION CALLS START  /////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////

                //Preventing caching for ajax calls
                $.ajaxSetup({ cache: false });

                var isManualEntryFormSubmit=1;
                
                // eg. if user select setNO=5 and made article entry for 5 sets and then user change the setNO=3 then this flag comes to true
                //Relevant alert is shown on next link.
                var isDifferentSetFound=false;
                var differentSetNo;

                //Following is the flag for master request selection
                //It becomes true on Next button link on article tab
                //once its true use can not select the other master request on config tab
                //this condition check will be there on master grid on selection row event.
                var isMasterReqSelected=false;

                onPageLoadCall();
                

                //This function Called When Jsp Page Loads
                //Function Check weather Page is first time loaded or its loaded after File upload mechanism
                //if page is loaded first time it will call firstTimePageLoad() function
                //otherwise it is calling afterFileUploadMechanismPageUpload() fucntion
                function onPageLoadCall(){
                    var hdnPromoId=$("#hdnFileUploadMstPromoId").val();
                    if(hdnPromoId!=undefined && hdnPromoId.length>0 && hdnPromoId!=null){
                        //                        alert("File Upload Page load called");
                        afterFileUploadMechanismPageUpload();
                    }else{
                        //                        alert("First Time Page Load Called");
                        firstTimePageLoad();
                        //                        $("#reqGrid").jqGrid("clearGridData", true);
                        $("#reqGrid").jqGrid('setGridParam',{url :"getAllPromotiondtl_initiator",datatype:'json',page:1}).trigger("reloadGrid");
                    }
                }
                function afterFileUploadMechanismPageUpload(){
                    //                    alert("Set NO : "+$("#hdnFileUploadSetNo").val());
                    //                    alert("Set ID : "+$("#hdnFileUploadSetId").val());
                    //                    alert("Set Name : "+$("#hdnFileUploadSetName").val());
                    //                    alert("Mst Promo Id : "+$("#hdnFileUploadMstPromoId").val());
                    //                    alert("Mst Promo Type ID : "+$("#hdnFileUploadMstPromoTypeId").val());
                    //                    alert("Grid Data : "+$("#hdnFileUploadGridData").val());
                    //                    alert("Hdn Page No : "+$("#hdnFileUploadGridPageNo").val());

                    var hdnPromoId=$("#hdnFileUploadMstPromoId").val();
                    var hdnPromoTypeId=$("#hdnFileUploadMstPromoTypeId").val();
                    var hdnSetNo=$("#hdnFileUploadSetNo").val();
                    var hdnSetId=$("#hdnFileUploadSetId").val();
                    var hdnGridData=$("#hdnFileUploadGridData").val();
                    var hdnPageNo=$("#hdnFileUploadGridPageNo").val();

                    //Make Master Requqest Selection Again
                    //And Set isMasterReqSelected=true For preventing user to select another request
                    //above functionality is placed on master request grid compelte event

                    // Set Manual Entry = 0 For File Upload Case
                    if(hdnPromoId!=undefined && hdnPromoId.length>0 && hdnPromoId!=null){
                        isManualEntryFormSubmit=0;
                    }
                    //Get The Page NO On File Upload Button
                    //Set Grid According to the Page No
                    //Call The Master Grid With Selected Record Page NO
                    if(hdnPageNo!=undefined && hdnPageNo.length>0 && hdnPageNo!=null){
                        //                        alert("Page No : "+hdnPageNo);
                        if(hdnPageNo!="1"){
                            $("#reqGrid").jqGrid('setGridParam',{url :"getAllPromotiondtl_initiator",datatype:'json',page:hdnPageNo}).trigger("reloadGrid");
                        }else{
                            $("#reqGrid").jqGrid('setGridParam',{url :"getAllPromotiondtl_initiator",datatype:'json',page:1}).trigger("reloadGrid");
                        }
                    }


                    //Select PromotypeId dropdown & Make PromotypeId disable & Make The view Again According to PromotypeId
                    if(hdnPromoTypeId!=undefined && hdnPromoTypeId.length>0 && hdnPromoTypeId!=null){
                        $("#promoSel").val(hdnPromoTypeId);
                        $("#hdnmstPromoTypeId").val(hdnPromoTypeId);
                        $("#promoSel").attr("disabled",true);
                        fillDiscountConfigCombo(hdnPromoTypeId);
                        if(hdnPromoTypeId=="1"){
                            makeBXGYDiscountSetLevelView();
                        }else if(hdnPromoTypeId=="2"){
                            makeBXGYDiscountPriceView();
                        }else if(hdnPromoTypeId=="3"){
                            makeFlatDiscountView();
                            $("#btnBXUpload").attr("Disabled",true);
                        }else if(hdnPromoTypeId=="4"){
                            makePowerPricingView();
                            $("#btnBXUpload").attr("Disabled",true);
                        }else if(hdnPromoTypeId=="5"){
                            makeTicketSizeBillLevelView();
                            $("#btnBXUpload").attr("Disabled",true);
                        }else if(hdnPromoTypeId=="6"){
                            makeTicketSizePoolRewardView();
                        }else if(hdnPromoTypeId=="7"){
                            makeBXGYView();
                            $("#btnBXUpload").attr("Disabled",true);
                        }
                    }

                    //Select SetNO dropdown , Make SetNo disable And Fill Set Dropdown Again
                    if(hdnSetNo!=undefined && hdnSetNo.length>0 && hdnSetNo!=null){
                        $("#setNoSel").val(hdnSetNo);
                        $("#setNoSel").attr("disabled",true);
                        fillSetBySetNo(hdnSetNo);

                    }

                    //Make File Upload Tab Enabled
                    if(hdnPromoId!=undefined && hdnPromoId.length>0 && hdnPromoId!=null){
                        tabSwitch_2(1,2,'maintab_','maincontent_');
                        tabSwitch_2(2,2,'tab_','content_');
                        $("#rbtnManualBX").attr("disabled",true);
                        $("#rbtnUploadBX").attr("disabled",false);
                        $("#rbtnUploadBX").attr("checked",true);
                    }

                    //Fill File Upload Grid
                    if(hdnGridData!=undefined && hdnGridData.length>0 && hdnGridData!=null){
                        var fileSetId="1";
                        var fileSetName="-";
                        var filedownload="-";
                        var filePath="-";
                        var index=0;
                        var articledata=hdnGridData.split(",");

                        //                        alert(discdata);
                        for(var i=0;i<articledata.length/3;i++){
                            fileSetId=articledata[i * 3];
                            fileSetName=articledata[(i * 3) + 1];
                            filePath=articledata[(i * 3) + 2];

                            if(fileSetId!=undefined && fileSetId.length>0){
                                filedownload= "<a style=\"font-family:arial;color:blue;font-size:10px;\" href=downloadFileFromPath?filePath="
                                    + filePath + ">Download</a>";
                                $("#uploadFileGrid").jqGrid('addRowData',index,{
                                    uploadFileSetID:fileSetId,
                                    uploadFileSetName:fileSetName,
                                    downloadArticleFile:filedownload,
                                    uploadFilePath:filePath
                                });
                                index++;
                            }
                        }
                    }

                }
                ////////////////////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////// PAGE LOAD FUCNTION CALLS END  /////////////////////////////////
                ////////////////////////////////////////////////////////////////////////////////////////////////

                               
                $("#promoSel").change(function(){
                    /*Following Code captures selected the Request Grid Id
                     *As per the CR Suggested, Now Promosel Comes Down To Grid
                     *We Take the Selected Id Of Grid into selectedreqGridId variable temporary before reseting the form and grid
                     *And Again set the mstPromoId hidden field value back with the variable selectedreqGridId
                     *Also Set the selection of the Grid back with the variable selectedreqGridId
                     *Above is necessary because mstPromoId hidden field and reqGrid is reset into function resetAllWithoutPromoType
                     **/

                    var selectedreqGridId=$("#mstPromoId").val();
                    if(selectedreqGridId==undefined || selectedreqGridId==null || selectedreqGridId.length==0){
                        alert("Please Select Ticket Number!");
                        $("#promoSel").val(1);
                        return false;
                    }

                    resetAllWithoutPromoType();

                    $("#mstPromoId").val(selectedreqGridId);
                    $("#reqGrid").jqGrid('setSelection', selectedreqGridId);

                    $("#btnValidateArticle").attr("disabled",false);

                    //Cr2 Change Add Button Enabled
                    //                    $("#btnAddArticle").attr("disabled",true);

                    $("#txtArticleNo").attr("disabled",false);
                    $("#txtMCCode").attr("disabled",false);

                    var promoId=$("#promoSel").val();
                    $("#hdnmstPromoTypeId").val(promoId);
                    
                    fillDiscountConfigCombo(promoId);
                    if(promoId=="1"){
                        makeBXGYDiscountSetLevelView();
                    }else if(promoId=="2"){
                        makeBXGYDiscountPriceView();
                    }else if(promoId=="3"){
                        makeFlatDiscountView();
                    }else if(promoId=="4"){
                        makePowerPricingView();
                    }else if(promoId=="5"){
                        makeTicketSizeBillLevelView();
                    }else if(promoId=="6"){
                        makeTicketSizePoolRewardView();
                    }else if(promoId=="7"){
                        makeBXGYView();
                    }
                });

                $("#setNoSel").change(function(){
                    var setNo=$("#setNoSel").val();
                    //                    $("#setNoSel").attr("disabled",true);
                    fillSetBySetNo(setNo);

                    isDifferentSetFound=false;
                    differentSetNo="";
                    if(isManualEntryFormSubmit==1 && (promoTypeId==1 || promoTypeId==2)){
                        var values = [];
                        $('#setSel option').each(function() {
                            values.push( $(this).attr('value') );
                        });

                        var tableDataIds= jQuery('#XarticleGrid').getDataIDs();
                        //following Article Grid Records loop is to check against the set combo
                        // if user changes the set selection from set dropdown after article entered in to grid.
                        //the difference set article entries are deleted from grid.
                        var deleteSetId=[];
                        for(var index=0;index<=tableDataIds.length;index++){
                            var rowData = jQuery('#XarticleGrid').getRowData(tableDataIds[index]);
                            var enteredSet=rowData.setId;
                            var isSetFound=false;
                            for(var i=0;i<values.length;i++){
                                var setComboVal=values[i];
                                if( isSetFound==false && (enteredSet==setComboVal)){
                                    isSetFound=true;
                                }
                            }
                            if(isSetFound==false){
                                deleteSetId.push(enteredSet);
                            }
                        }
                        //Delete the Different Set Article Entry From Grid.
                        //                        alert("delete length: "+deleteSetId.length);
                        if(deleteSetId.length>1){
                            isDifferentSetFound=true;
                            differentSetNo=deleteSetId;
                        }
                    }

                });

                $("#setSel").change(function(){
                    // For Promo Type 1 First Set Discount Config And Value must be blank only Qty is allowed
                    //The same following code is available on set change event
                    promoTypeId=$("#promoSel").val();
                    var selectedSet=$("#setSel :selected").text();
                    //                    alert("--- Promo Type ID : "+promoTypeId+"    Selected Set : "+selectedSet)
                    if( (promoTypeId==1 && selectedSet.toUpperCase()=="SET 1") || (promoTypeId==2 && selectedSet.toUpperCase()!="SET 1")){
                        $("#disConfigSel").attr("disabled",true);
                        $("#txtdisValue").attr("disabled",true);
                    }else{
                        $("#disConfigSel").attr("disabled",false);
                        $("#txtdisValue").attr("disabled",false);
                    }
                });

                $("#maintab_1").click(function(){
                    if($("#setNoSel").attr("disabled")){
                        alert("Please Click On Previous Link.");
                    }
                    return false;
                });
                
                $("#rbtnManualBX").click(function(){
                    //BX_uploadTable   BX_manualTable

                    var checked = $(this).attr('checked', true);
                    if(checked){
                        tabSwitch_2(1,2, 'tab_', 'content_');
                    }
                    $('#manualEntryForm')[0].reset();
                    $('#fileUpload')[0].reset();
                    isManualEntryFormSubmit=1;
                });
                $("#rbtnUploadBX").click(function(){
                    var checked = $(this).attr('checked', true);

                    if(checked){
                        tabSwitch_2(2,2, 'tab_', 'content_');
                    }
                    $('#manualEntryForm')[0].reset();
                    $('#fileUpload')[0].reset();
                    isManualEntryFormSubmit=0;
                });

                

                var isArticleEntered=1;
                var  articleCode,articleDesc,mcCode,mcDesc,qty,brandCode,brandDesc;
                /*
                $("#btnValidateArticle").click(function (){
                    var mstPromoId=$("#mstPromoId").val();
                    if(mstPromoId==undefined || mstPromoId==null || mstPromoId.length==0){
                        alert("Please select request");
                        return false;
                    }
                    articleCode= $("#txtArticleNo").val();
                    mcCode=$("#txtMCCode").val();

                    if( articleCode !=undefined && articleCode.length>0 ){
                        isArticleEntered=1;
                    }else{
                        isArticleEntered=0;
                    }

                    //Validate The Article OR MC From js/PromotionCommonUtil.js And Set The Response In textbox
                    //                    validateArticleORMCCode(isArticleEntered, mcCode, articleCode,"txtArticleNo","txtArticleDesc","txtMCCode","txtMCDesc");
                    validateArticleORMCCode(isArticleEntered, mcCode, articleCode,"txtArticleNo","txtArticleDesc","txtMCCode","txtMCDesc",mstPromoId,"1");
                    
                    //                    alert("is article ent  flag :"+isArticleEntered);

                });
                 */

                               
                var tempIndex=0;
                var setId,setDesc,promoTypeId;
                //following variable is taken when qty is not entered by user
                //it checks if article/Mc validation call is made or not
                var isValidateCalled=false;
                $("#btnAddArticle").click(function (){

                    //                    alert(" validate called : "+isValidateCalled);
                    if(isValidateCalled==false){
                        /*CR2 changes put validate code in add button itself
                         **/
                        var mstPromoId=$("#mstPromoId").val();
                        if(mstPromoId==undefined || mstPromoId==null || mstPromoId.length==0){
                            alert("Please Select Ticket Number!");
                            return false;
                        }
                        articleCode= $("#txtArticleNo").val();
                        mcCode=$("#txtMCCode").val();

                        if( articleCode !=undefined && articleCode.length>0 ){
                            isArticleEntered=1;
                        }else{
                            isArticleEntered=0;
                        }

                        //Validate The Article OR MC From js/PromotionCommonUtil.js And Set The Response In textbox
                        //                    validateArticleORMCCode(isArticleEntered, mcCode, articleCode,"txtArticleNo","txtArticleDesc","txtMCCode","txtMCDesc");
                        var validatePromoArticle=validatePromotionArticleORMCCode(isArticleEntered, mcCode, articleCode,"txtArticleNo","txtArticleDesc","txtMCCode","txtMCDesc","txtBrandCode","txtBrandDesc",mstPromoId,"1");
                   
                        //                    alert("validate : "+validatePromoArticle);
                        if(validatePromoArticle[0]==false){
                            alert(validatePromoArticle[1]);
                            isValidateCalled=false;
                            return false;
                        }else{
                            isValidateCalled=true;
                        }
                        
                        //changes of CR2 finished
                    }

                    //check condition if articledescription and mc details are not null
                    //if it is null then article is not validated                    
                    articleDesc=$("#txtArticleDesc").val();
                    mcCode=$("#txtMCCode").val();
                    mcDesc=$("#txtMCDesc").val();
                    

                    if( (articleDesc==null || articleDesc.length==0) || (mcCode==null || mcCode.length==0) || (mcDesc==null || mcDesc.length==0) ){
                        alert("Article Is Not Validated.");
                        return false;
                    }
                    
                    if(isValidateCalled){
                        
                        promoTypeId=$("#promoSel").val();
                        var validateArticle=addArticleInGrid(promoTypeId,tempIndex);
                        if(validateArticle[0]==false){
                            alert(validateArticle[1]);
                            if(validateArticle[1]=="Please enter qunatity" || validateArticle[1]=="Qunatity should be numeric." || validateArticle[1]=="Qty must be positive."){
                                $("#btnAddArticle").attr("disabled", false);
                                $("#btnValidateArticle").attr("disabled", true);
                                $("#txtArticleNo").attr("disabled", true);
                                $("#txtMCCode").attr("disabled", true);
                                isValidateCalled=true;
                            }else{
                                //Cr2 change
                                //                            $("#btnAddArticle").attr("disabled", true);
                                $("#btnValidateArticle").attr("disabled", false);
                                $("#txtArticleNo").attr("disabled", false);
                                $("#txtMCCode").attr("disabled", false);
                                isValidateCalled=false;
                            }                            
                            return false;
                        }
                        tempIndex++;

                        $("#txtArticleNo").attr("disabled", false);
                        $("#txtMCCode").attr("disabled", false);

                        //Cr2 change
                        $("#btnAddArticle").attr("disabled", false);
                        $("#btnValidateArticle").attr("disabled", false);
                        isValidateCalled=false;
                    }
                    
                });


                $("#btnBXUpload").click(function (){
                    var mstPromoId=$("#mstPromoId").val();
                    if(mstPromoId==undefined || mstPromoId==null || mstPromoId.length==0){
                        alert("Please select request");
                        return false;
                    }
                    var fileid=  document.getElementById("articleFile").value;
                    if(fileid==null ||fileid==""){
                        alert("Please select file to upload.");
                        return false;
                    }
                    var valid_extensions = /(.csv)$/i;
                    if(valid_extensions.test(fileid)){
                    } else{
                        alert('Invalid File. Please Upload CSV File Only.');
                        return false;
                    }
                    var validateSet=validateFileUploadSet();
                    if(validateSet[0]==false){
                        alert(validateSet[1]);
                        return false;
                    }
                    SetFileUploadHiddenFields();
                    
                });

                function validateFileUploadSet(){
                    
                    var mstPromoTypeId=$("#promoSel").val();
                    
                    if(jQuery("#uploadFileGrid tr").length>1){
                        var tableDataIds= jQuery('#uploadFileGrid').getDataIDs();
                            
                        for(var index=0;index<=tableDataIds.length;index++)
                        {
                            var rowData = jQuery('#uploadFileGrid').getRowData(tableDataIds[index]);
                            if(mstPromoTypeId=="1" || mstPromoTypeId=="2"){
                                var setId=$("#setSel").val();
                                if(setId==rowData.uploadFileSetID){
                                    return [false,"File is already uploaded for selected set."];
                                }
                            }else if(mstPromoTypeId=="6"){
                                var setId=$("#buygetSel").val();
                                if(setId==rowData.uploadFileSetID){
                                    return [false,"File is already uploaded for selected set."];
                                }
                            }
                        }
                    }
                    return [true,''];
                }

                function SetFileUploadHiddenFields(){
                    var setNo=$("#setNoSel").val();
                    var setId=$("#setSel").val();
                    var setName=$("#setSel :selected").text();
                    
                    var mstPromoId=$("#mstPromoId").val();

                    var mstPromoTypeId=$("#promoSel").val();
                    if(mstPromoTypeId=="6"){
                        setId=$("#buygetSel").val();
                        setName=$("#buygetSel :selected").text();
                    }
                    //                    var masterGridPageNo=$('#uploadFileGrid').getGridParam('page');
                    var masterGridPageNo=$(".ui-pg-input").val();
                    $("#hdnFileUploadGridPageNo").val(masterGridPageNo);
                    
                    $("#hdnFileUploadSetNo").val(setNo);
                    $("#hdnFileUploadSetId").val(setId);
                    $("#hdnFileUploadSetName").val(setName);
                    $("#hdnFileUploadMstPromoId").val(mstPromoId);
                    $("#hdnFileUploadMstPromoTypeId").val(mstPromoTypeId);

                    getFileUploadArticleData();

                    //                    alert("Set NO : "+$("#hdnFileUploadSetNo").val());
                    //                    alert("Set ID : "+$("#hdnFileUploadSetId").val());
                    //                    alert("Set Name : "+$("#hdnFileUploadSetName").val());
                    //                    alert("Mst Promo Id : "+$("#hdnFileUploadMstPromoId").val());
                    //                    alert("Mst Promo Type ID : "+$("#hdnFileUploadMstPromoTypeId").val());
                    //                    alert("Grid Data : "+$("#hdnFileUploadGridData").val());
                    //                    alert("Grid Page No : "+$("#hdnFileUploadGridPageNo").val());
                }

                

                
                
                //Validate Article View And Set Config View
                $("#configViewNextLink").click(function (){
                    var mstPromoId=$("#mstPromoId").val();
                    if(mstPromoId==undefined || mstPromoId==null || mstPromoId.length==0){
                        alert("Please select request");
                        return false;

                    }
                    
                    if(isManualEntryFormSubmit==1 && jQuery("#XarticleGrid tr").length<=1){
                        alert("Please Enter Article Detail.")
                        return false;
                    }else if(isManualEntryFormSubmit==0 && jQuery("#uploadFileGrid tr").length<=1){
                        alert("Please Upload Article Detail.")
                        return false;
                    }

                    //                    if(isDifferentSetFound==true){
                    //                        alert("Please delete the article entries for Set : "+differentSetNo);
                    //                        return false;
                    //                    }
                    //
                    //
                    //                    //Validate Article is manual entered or uploaded as per the set selection for promotion type id 1,2,6
                    //                    var promoTypeId=$("#promoSel").val();
                    //                    var validateSet=validateArticleORFileUploadAgainstSet(promoTypeId,isManualEntryFormSubmit);
                    //                    if(validateSet[0]==false){
                    //                        alert(validateSet[1]);
                    //                        return false;
                    //                    }

                    makeConfigTabView();
                    isMasterReqSelected=true;
                });
                               
                var discIndex=0;
                
                $("#btnAddDiscDtl").click(function(){
                    var promoTypeId=$("#promoSel").val();
                    var validateDiscount=validateSubPromoDiscountConfigFields(promoTypeId,discIndex);
                    if(validateDiscount[0]==false){
                        alert(validateDiscount[1]);
                        return false;
                    }
                    discIndex++;
                });
                
                $("#btnSave").click(function(){
                    var promoTypeId=$("#promoSel").val();
                    //                    alert("type ID : "+promoTypeId);

                    if(isDifferentSetFound==true){
                        alert("Please delete the article entries for Set : "+differentSetNo);
                        makeArticleTabView();
                        return false;
                    }


                    //Validate Article is manual entered or uploaded as per the set selection for promotion type id 1,2,6
                    var promoTypeId=$("#promoSel").val();
                    var validateSet=validateArticleORFileUploadAgainstSet(promoTypeId,isManualEntryFormSubmit);
                    if(validateSet[0]==false){
                        alert(validateSet[1]);
                        makeArticleTabView();
                        return false;
                    }

                    var validateFields=validateCreatSubPromoFields(isManualEntryFormSubmit,promoTypeId);
                    if(validateFields[0]==false){
                        alert(validateFields[1]);
                        return false;
                    }
                    if(isManualEntryFormSubmit==1){
                        $("#isManualEntry").val("1");
                    }else{
                        $("#isManualEntry").val("0");
                    }

                    //                    return false;
                });

                $("#articleViewPreviousLink").click(function(){
                    makeArticleTabView();
                });

                

                function addArticleInGrid(promoTypeId,tempIndex){
                    //                    qty=$("#txtXQty").val();
                    qty=0;
                    //If Promo Type Id Set Level And Promo Level Discount
                    //Then Fill Grid With Set And Qty.
                    if(promoTypeId=="1" || promoTypeId=="2"){

                        //                        if(qty==undefined || qty==null || qty.length==0){
                        //                            $("#txtXQty").focus();
                        //                            $("#btnAddArticle").attr("disabled", false);
                        //                            return [false,"Please enter qunatity"];
                        //                        }else if(!isNumeric(qty)){
                        //                            $("#txtXQty").focus();
                        //                            $("#btnAddArticle").attr("disabled", false);
                        //                            return [false,"Qunatity should be numeric."];
                        //                        }else if(qty<=0){
                        //                            $("#txtXQty").focus();
                        //                            return [false,'Qty must be positive.'];
                        //                        }

                        setId=$("#setSel").val();
                        setDesc=$("#setSel :selected").text();
                        qty=0;
                    }else if(promoTypeId=="6"){
                        setId=$("#buygetSel").val();
                        setDesc=$("#buygetSel :selected").text();
                        qty="0";
                    }else{
                        setId="1";
                        setDesc="-";
                        qty="0";
                    }
                    //                    alert(setDesc);
                    articleCode= $("#txtArticleNo").val();
                    articleDesc=$("#txtArticleDesc").val();
                    mcCode=$("#txtMCCode").val();
                    mcDesc=$("#txtMCDesc").val();
                    brandCode=$("#txtBrandCode").val();
                    brandDesc=$("#txtBrandDesc").val();


                    if(jQuery("#XarticleGrid tr").length>1){
                        var tableDataIds= jQuery('#XarticleGrid').getDataIDs();
                        //alert("tab : "+tableDataIds);
                        var insertData;
                        if(isArticleEntered==1){
                            insertData=$("#txtArticleNo").val();
                        }else{
                            insertData=$("#txtMCCode").val();
                        }
                        for(var index=0;index<=tableDataIds.length;index++)
                        {
                            var rowData = jQuery('#XarticleGrid').getRowData(tableDataIds[index]);

                            // alert("Grid Data : "+rowData.bn);
                            if(isArticleEntered==1){
                                if(promoTypeId=="1" || promoTypeId=="2" || promoTypeId=="6"){
                                    if(insertData==rowData.xartNo && setDesc==rowData.setDesc){
                                        $("#txtArticleNo").focus();
                                        resetArticleDtl();
                                    
                                        return [false,'Selected aricle code already exist in '+setDesc+"."];
                                    }
                                }else{
                                    if(insertData==rowData.xartNo){
                                        $("#txtArticleNo").focus();
                                        resetArticleDtl();
                                    
                                        return [false,'Selected aricle code already exist.'];
                                    }
                                }
                                
                            }else{
                                if(promoTypeId=="1" || promoTypeId=="2" || promoTypeId=="6"){
                                    if(insertData==rowData.xmcCode && setDesc==rowData.setDesc){
                                        $("#txtMCCode").focus();
                                        resetArticleDtl();

                                        return [false,'Selected MC code already exist in '+setDesc+'.'];
                                    }
                                }else{
                                    if(insertData==rowData.xmcCode){
                                        $("#txtMCCode").focus();
                                        resetArticleDtl();

                                        return [false,'Selected MC code already exist.'];
                                    }
                                }
                                
                            }

                        }
                    }
 
                    $("#XarticleGrid").jqGrid('addRowData',tempIndex,{setId:setId,setDesc:setDesc,xartNo:articleCode,xartDesc:articleDesc,xmcCode:mcCode,xmcDesc:mcDesc,xqty:qty,xbrandCode:brandCode,xbrandDesc:brandDesc});
                    resetArticleDtl();

                    return[true,''];
                }

                

                $(function() {
                    var dates = $( "#txtBXGYFrom, #txtBXGYTo" ).datepicker({
                        defaultDate: "+1w",
                        numberOfMonths: 1,
                        changeMonth: true,
                        //changeYear: true,
                        dateFormat: 'dd-mm-yy',
                        // maxDate : '+0d',
                        minDate:'+0d',


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

                
                                              
            });
        </script>
    </head>
    <body>
        <jsp:include page="/jsp/menu/menuPage.jsp" />

        <div id="middle_cont">

            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="f14" align="center">
                <tr><td class="errorText" align="right">(Fields marked with a * are mandatory.)</td></tr>
                <tr>
                    <td width="100%" align="center" >
                        <div id="message">
                            <s:actionmessage cssClass="successText"/>
                            <s:actionerror cssClass="errorText"/>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td><h1>Promotion Initiation</h1></td>
                    <td align="center" >
                        <a href ="#" class="download-sample " onclick="tb_show( '', 'viewSubPromoHelp?height=500&width=650');">
                            Help
                        </a>
                    </td>
                </tr>
                <tr>
                    <td  align="center">
                        <table id="reqGrid"></table>
                        <div id="reqPager"></div>
                    </td>
                </tr>
                <tr>
                    <td height="10px"></td>
                </tr>
                <tr>
                    <td align="center">
                        <table>
                            <tr>
                                <td  align="right">Promo Type :</td>
                                <td align="left">  <s:select name="formVO.promoTypeId" id="promoSel"  list="promoMap" />  </td>
                            </tr>
                        </table>
                    </td>
                </tr>

                <tr>
                    <td align="center">
                        <table>

                            <tr id="setSpaceTR">
                                <td  colspan="2" height="10px"></td>
                            </tr>
                            <tr id="setTR">
                                <td colspan="2">
                                    <table>
                                        <tr>
                                            <td  align="right">NO OF SET :</td>
                                            <td align="left">  <s:select  id="setNoSel"  list="setNoMap" cssClass="dropdown"  />  </td>
                                        </tr>
                                        <tr>
                                            <td  colspan="2" height="10px"></td>
                                        </tr>
                                        <tr>
                                            <td  align="right">SET :</td>
                                            <td align="left">  <s:select name="formVO.setId" id="setSel"  list="#{'-1':'--- Select ---'}"  cssClass="dropdown"  />  </td>
                                        </tr>
                                        <tr>
                                            <td  colspan="2" height="10px"></td>
                                        </tr>
                                        <tr id="articleQtyTR">
                                            <td align="right">Qty :</td>
                                            <td align="left"><input type="text"  id="txtXQty" /></td>
                                        </tr>
                                    </table>
                                </td>

                            </tr>
                            <tr id="buygetSpaceTR">
                                <td  colspan="2" height="10px"></td>
                            </tr>
                            <tr id="buygetTR">
                                <td  align="right">BUY/GET SET:</td>
                                <td align="left">  <s:select  list="#{'1':'Buy Worth','2':'Get'}" id="buygetSel" name="formVO.setId" value="%{formVO.setId}" />  </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td height="10px"></td>
                </tr>
                <tr>
                    <td height="10px"></td>
                </tr>                
                <tr>
                    <td>
                        <div id="maintabMaster" class="tabbed_box">
                            <ul class="tabs">
                                <li><a href="#" id="maintab_1" class="active" >Article Detail</a></li>
                                <li><a href="#" id="maintab_2" onclick="alert('Please Click On Next Link.'); return false;">Config/Other Detail</a></li>
                            </ul>
                            <div id="maincontent_1" class="content">
                                <table width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                    <tr>
                                        <td align="left">
                                            <table width="60%" >
                                                <tr>
                                                    <td align="left"><b>Capture Article</b></td>
                                                    <td align="right">Manual Entry</td>
                                                    <td align="left">
                                                        <input type="radio" id="rbtnManualBX" name="captureArticle" checked="true" value="1"/>
                                                    </td>
                                                    <td align="right">Upload Article</td>
                                                    <td align="left">
                                                        <input type="radio" id="rbtnUploadBX" name="captureArticle"  value="0"/>
                                                    </td>
                                                    <%--  <td align="center" >
                                                        <a href ="#" class="download-sample " onclick="tb_show( '', 'viewArticleMCSearch_action?height=375&width=1000');">
                                                            Article Search
                                                        </a>
                                                    </td>
                                                    --%>
                                                    <!--                                                    <td align="right">
                                                                                                            <a href ="viewMChUserDownloadhtm" class="download-sample">
                                                                                                                MC Download
                                                                                                            </a>
                                                                                                        </td>-->
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="left">
                                            <table width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                <tr>
                                                    <td align="center">
                                                        <div id="BXtabMaster" class="tabbed_box">
                                                            <ul class="tabs">
                                                                <li><a href="#" id="tab_1" class="active" style="color: white;" onclick="alert('Please Select Manual Article Option.'); return false;">Manual Article Entry</a></li>
                                                                <li><a href="#" id="tab_2" style="color: white;" onclick="alert('Please Select Upload Article Option.'); return false;">Upload Article File</a></li>
                                                            </ul>
                                                            <div id="content_1" class="content" >
                                                                <table id="BX_manualTable" width="100%" height="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                                    <s:form id="manualEntryForm" action="donothing" method="POST" >
                                                                        <tr>
                                                                            <td align="right">Article Number</td>
                                                                            <td align="left"><input type="text"  id="txtArticleNo" /></td>
                                                                            <td align="right">MC Code</td>
                                                                            <td align="left"><input type="text"  id="txtMCCode" /></td>
                                                                            <td align="left">
                                                                                <input type="button" name="btnAddArticle" id="btnAddArticle" value="Add" class="btn" />
                                                                            </td>
                                                                        </tr>
                                                                        <tr style="display: none;">
                                                                            <td align="right">Article Description</td>
                                                                            <td align="left"><input type="text" id="txtArticleDesc" readonly="true" /></td>
                                                                            <td align="right">MC Description</td>
                                                                            <td align="left"><input type="text"  id="txtMCDesc" readonly="true" /></td>
                                                                            <td align="left">
                                                                                <input type="button" name="btnValidateArticle" id="btnValidateArticle" value="Validate" class="btn" style="display: none;"/>
                                                                            </td>
                                                                        </tr>                                                                        
                                                                        <tr style="display: none;">
                                                                            <td align="right">Brand Code</td>
                                                                            <td align="left"><input type="text" id="txtBrandCode" readonly="true" /></td>
                                                                            <td align="right">Brand Description</td>
                                                                            <td align="left"><input type="text"  id="txtBrandDesc" readonly="true" /></td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td colspan="5" align="center">
                                                                                <table id="XarticleGrid"></table>
                                                                                <div id="XarticlePager"></div>
                                                                            </td>
                                                                        </tr>
                                                                    </s:form>
                                                                </table>
                                                            </div>
                                                            <div id="content_2" class="content">
                                                                <table id="BX_uploadTable"  align="center" cellpadding="2" cellspacing="4">
                                                                    <s:form id="fileUpload" action="subPromoFileUpload_action" method="POST" enctype="multipart/form-data">
                                                                        <s:hidden name="formVO.fileUploadSetNo" id="hdnFileUploadSetNo" value="%{formVO.fileUploadSetNo}"/>
                                                                        <s:hidden name="formVO.fileUploadsetId" id="hdnFileUploadSetId" value="%{formVO.fileUploadsetId}"/>
                                                                        <s:hidden name="formVO.fileUploadsetName" id="hdnFileUploadSetName" value="%{formVO.fileUploadsetName}"/>
                                                                        <s:hidden name="formVO.fileUploadMstPromoId" id="hdnFileUploadMstPromoId" value="%{formVO.fileUploadMstPromoId}"/>
                                                                        <s:hidden name="formVO.fileUploadMstPromoTypeId" id="hdnFileUploadMstPromoTypeId" value="%{formVO.fileUploadMstPromoTypeId}"/>
                                                                        <s:hidden name="formVO.fileUploadGridData" id="hdnFileUploadGridData" value="%{formVO.fileUploadGridData}"/>
                                                                        <s:hidden name="formVO.fileUploadGridPageNo" id="hdnFileUploadGridPageNo" value="%{formVO.fileUploadGridPageNo}"/>

                                                                        <tr>
                                                                            <td width="2%"></td>
                                                                            <td align="right">Upload File</td>
                                                                            <td align="left">
                                                                                <s:file id ="articleFile" name="articleFileUpload" ></s:file>
                                                                            </td>
                                                                            <td align="center">
                                                                                <input  type="submit" id="btnBXUpload" name="btnBXUpload" class="btn"  Value="Upload" />
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td width="2%"></td>
                                                                            <td align="right">Download</td>
                                                                            <td align="center" id="downloadfileWithoutQty">
                                                                                <a href ="downloadSampleArticleMCFile" class="download-sample ">
                                                                                    Sample File
                                                                                </a>
                                                                            </td>
                                                                            <td align="center" id="downloadfileWithQty">
                                                                                <a href ="downloadIntiationSampleArticleMCFile" class="download-sample " id="downloadSample">
                                                                                    Sample File
                                                                                </a>
                                                                            </td>
                                                                            <td align="center" id="tdErrorFile">
                                                                                <%--<s:a  href="%{formVO.errorfilePath}" class="downloadError" id="downloadErrorFile" > Status File </s:a>--%>
                                                                            </td>
                                                                        </tr>
                                                                        <tr>
                                                                            <td colspan="5" align="center">
                                                                                <table id="uploadFileGrid"></table>
                                                                                <div id="uploadFilePager"></div>
                                                                            </td>
                                                                        </tr>
                                                                    </s:form>
                                                                </table>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td  align="right">
                                            <a id="configViewNextLink"  href="#"  class="legendText">Next</a>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div id="maincontent_2" class="content">
                                <table id="BuyXGetYTable" width="90%" border="0" align="center" cellpadding="2" cellspacing="4">
                                    <s:form id="subPromoSubmit" action="createSubPromo" method="POST">

                                        <s:hidden name="formVO.mstPromoId" id="mstPromoId" value="%{formVO.mstPromoId}"/>
                                        <s:hidden name="formVO.promoTypeId" id="hdnmstPromoTypeId" value="%{formVO.promoTypeId}"/>
                                        <s:hidden name="formVO.isManualEntry" id="isManualEntry" value="%{formVO.isManualEntry}"/>                                        
                                        <s:hidden name="formVO.manualArticleData" id="XarticleGridData" />
                                        <s:hidden name="formVO.discountConfigGridData" id="discountGridData" />

                                        <tr id="DiscountGridTR">
                                            <td align="center">
                                                <table width="70%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                    <tr>
                                                        <td>Discount Config   </td>
                                                        <td>
                                                            <s:select headerKey="-1" headerValue="---Select Discount---" list="#{}" id="disConfigSel"  />
                                                        </td>
                                                        <td>Value</td>
                                                        <td><s:textfield id="txtdisValue"  /> </td>
                                                        <td id="discQtyLabelTD">Quantity</td>
                                                        <td id="discQtyTD"> <input id="txtDisQty" type="text"  /></td>
                                                        <td><input type="button" name="btnAddDiscDtl" id="btnAddDiscDtl" class="btn" value="Add"/></td>
                                                    </tr>
                                                    <tr>
                                                        <td  align="center" colspan="7">
                                                            <table id="discountGrid"></table>
                                                            <div id="discountPager"></div>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr><td height="5px" ></td></tr>
                                        <tr id="bxgybuygetTR">
                                            <td align="center">
                                                <table width="40%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                    <tr>
                                                        <td>Buy</td>
                                                        <td>
                                                            <s:textfield id="txtbuy" name="formVO.buyQtyBXGY" />
                                                        </td>
                                                        <td>Get</td>
                                                        <td><s:textfield id="txtget" name="formVO.getQtyBXGY" /></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        <tr><td height="5px" ></td></tr>
                                        <tr id="ticketBillTR">
                                            <td align="center">
                                                <table width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                    <tr>
                                                        <td>Buy Worth Amount</td>
                                                        <td><s:textfield id="txtbuyWorthAmtTicketBill" name="formVO.buyWorthAmtTicketBill"  /></td>
                                                        <td>Discount amount</td>
                                                        <td><s:textfield id="txtdiscountAmtTicketBill" name="formVO.discountAmtTicketBill"  /></td>
                                                        <td>Discount Config   </td>
                                                        <td>
                                                            <s:select headerKey="-1" headerValue="---Select Discount---" list="#{'0':'Value Off','1':'Percentage Off','2':'Flat Price'}" id="disConfigSelTicketBill" name="formVO.discountConfigTicketBill"  />
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>

                                        <tr><td height="5px" ></td></tr>
                                        <tr>
                                            <td>
                                                <table width="60%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                    <tr id="ticketPoolTR">
                                                        <td align="right">Buy Worth Amount (%): <span class="errorText">&nbsp;*</span></td>
                                                        <td align="left"><s:textfield id="txtbuyWortAmt" name="formVO.buyWorthAmtTicketPool"  /></td>
                                                    </tr>
                                                    <tr>
                                                        <td align="right">Expected Margin (%): <span class="errorText">&nbsp;*</span></td>
                                                        <td align="left"><s:textfield id="txtBXGYmargin" name="formVO.marginAchivement"  /></td>
                                                    </tr>
                                                    <tr>
                                                        <td align="right">Expected Sales Qty growth (%): <span class="errorText">&nbsp;*</span></td>
                                                        <td align="left"><s:textfield id="txtBXGYsellQty" name="formVO.sellQty"  /></td>
                                                    </tr>
                                                    <tr>
                                                        <td align="right">Expected Sales value growth (%): <span class="errorText">&nbsp;*</span></td>
                                                        <td align="left"><s:textfield id="txtBXGYsalegrowth" name="formVO.qtyValueSellGrowth"  /></td>
                                                    </tr>
                                                    <tr>
                                                        <td align="right">Growth in Ticket size (%): <%-- <span class="errorText">&nbsp;*</span> --%></td>
                                                        <td align="left"><s:textfield id="txtBXGYgrowth" name="formVO.ticketSizeGrowth"  /></td>
                                                    </tr>
                                                    <tr>
                                                        <td align="right">Growth in conversions (%):  <%--<span class="errorText">&nbsp;*</span> --%> </td>
                                                        <td align="left"><s:textfield id="txtBXGYgrowthConver" name="formVO.conversionGrowth"  /></td>
                                                    </tr>
                                                    <tr>
                                                        <td colspan="2">
                                                            <table width="100%" border="0" align="center" cellpadding="2" cellspacing="4">
                                                                <tr>
                                                                    <td>Valid From <span class="errorText">&nbsp;*</span></td>
                                                                    <td><s:textfield id="txtBXGYFrom" name="formVO.validFrom"  readonly="true"/></td>
                                                                    <td>Valid To <span class="errorText">&nbsp;*</span></td>
                                                                    <td><s:textfield id="txtBXGYTo" name="formVO.validTo"  readonly="true"/></td>
                                                                </tr>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                    <tr>
                                                        <td align="right">Remarks :<span class="errorText">&nbsp;*</span></td>
                                                        <td align="left"><textarea id="txtremarks" name="formVO.txtRemarks"  rows="3" cols="25" style="width: 70%;height: 20%"></textarea></td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>

                                        <tr>
                                            <td align="center">
                                                <table width="100%">
                                                    <tr>
                                                        <td align="left" width="20%">
                                                            <a id="articleViewPreviousLink"  href="#"  class="legendText">Previous</a>
                                                        </td>
                                                        <td align="center">
                                                            <input align="left" type="submit" id="btnSave" name="btnSave"  Value="Save" Class="btn" />
                                                        </td>
                                                        <!--                                                <td align="center">
                                                                                                            <input align="left" type="button" id="btnReset" name="btnReset"  Value="RESET" Class="button" />
                                                                                                        </td>-->

                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                    </s:form>
                                </table>
                            </div>                            
                        </div>                        
                    </td>
                </tr>
            </table>
        </div>
    </body>
</html>
