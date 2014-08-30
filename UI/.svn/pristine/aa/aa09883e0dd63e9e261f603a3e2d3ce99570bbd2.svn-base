function firstTimePageLoad(){
    $("#hdnmstPromoTypeId").val("1");
    tabSwitch_2(1,2,'maintab_','maincontent_');
    makeBXGYDiscountSetLevelView();
    disabledControllOnPageLoad();
    var promotypeId=1;
    $("#promoSel").val(promotypeId)
    var setNo="1";
    fillSetBySetNo(setNo);
    fillDiscountConfigCombo(promotypeId);
}

function disabledControllOnPageLoad(){
    $("#tdErrorFile").hide();
    $("#maincontent_2").hide();
}
function fillSetBySetNo(setNoId){
    //    alert("Inside Fill Set :"+setNoId);
    $.ajax({
        url: "fillSetBySetNo?setNo="+setNoId,
        global: false,
        type: "POST",
        dataType:"json",
        contanttype: 'text/json',
        async:false,
        error:function(){
            alert("Can not connect to server");
        },
        success: function(data){
            //  alert(data);
            $('#setSel option').each(function(ik, option){
                $(option).remove();
            });

            var select = document.getElementById("setSel");

            //                            var newoption = document.createElement('option');
            //                            newoption.text = "---Select Set---";
            //                            newoption.value = -1;
            //                            select.add(newoption);
            //alert(" Doc Dec length : "+data.rows.formatdecList.length);
            //            alert("Data : "+data);
            if(data!=null){
                for(var i=0; i<data.rows.setdecList.length ; i++){
                    var optn = document.createElement("OPTION")
                    optn.text = data.rows.setdecList[i];
                    optn.value = data.rows.setIdList[i];
                    // alert(optn.text);
                    //document.reqForm.phaseSel.options.add(optn);
                    select.add(optn);
                }
            }
            else{
                alert("No Set available.\n Please contact to ADMIN.");
                return false;
            }

        }
    });
}
function makeBXGYDiscountSetLevelView(){
    //Enable Set TR
    $("#setSpaceTR").show();
    $("#setTR").show();

    //disable BuyGETTR
    $("#buygetSpaceTR").hide();
    $("#buygetTR").hide();

    //Enable Article Qty TR
    $("#articleQtyTR").hide();

    //Enable downloadfileWithQty TR
    $("#downloadfileWithQty").hide();

    //Disable WithoutQtySampleFileTR
    $("#downloadfileWithoutQty").show();

    //Enable Discount Grid TR
    $("#DiscountGridTR").show();

    //Disable Discount Config Qty TextBox TD
    $("#discQtyTD").show();

    //Disable Discount Config Qty label TD
    $("#discQtyLabelTD").show();

    //Disable bxgybuygetTR
    $("#bxgybuygetTR").hide();

    //Disable ticketBillTR
    $("#ticketBillTR").hide();

    //Disable ticketPoolTR
    $("#ticketPoolTR").hide();

    //Enable Set From Article Grid
    $("#XarticleGrid").showCol("setDesc");

    //Enable Qty From Article Grid
    $("#XarticleGrid").hideCol("xqty");

    //Disable Discount Qty From Discount Grid
    $("#discountGrid").showCol("discQty");

    //Enable Discount Set From Discount Grid
    $("#discountGrid").showCol("discSetName");

    //Enable Set File Upload Grid
    $("#uploadFileGrid").showCol("uploadFileSetName");
}

function makeBXGYDiscountPriceView(){
    //Enable Set TR
    $("#setSpaceTR").show();
    $("#setTR").show();

    //disable BuyGETTR
    $("#buygetSpaceTR").hide();
    $("#buygetTR").hide();

    //Enable Article Qty TR
    $("#articleQtyTR").hide();

    //Enable downloadfileWithQty TR
    $("#downloadfileWithQty").hide();

    //Disable WithoutQtySampleFileTR
    $("#downloadfileWithoutQty").show();

    //Enable Discount Grid TR
    $("#DiscountGridTR").show();

    //Disable Discount Config Qty TextBox TD
    $("#discQtyTD").show();

    //Disable Discount Config Qty label TD
    $("#discQtyLabelTD").show();

    //Disable bxgybuygetTR
    $("#bxgybuygetTR").hide();

    //Disable ticketBillTR
    $("#ticketBillTR").hide();

    //Disable ticketPoolTR
    $("#ticketPoolTR").hide();

    //Enable Set From Article Grid
    $("#XarticleGrid").showCol("setDesc");

    //Enable Qty From Article Grid
    $("#XarticleGrid").hideCol("xqty");

    //Disable Discount Qty From Discount Grid
    $("#discountGrid").showCol("discQty");

    //Disable Discount Set From Discount Grid
    $("#discountGrid").showCol("discSetName");

    //Enable Set File Upload Grid
    $("#uploadFileGrid").showCol("uploadFileSetName");

}

function makeBXGYView(){
    //disable Set TR
    $("#setSpaceTR").hide();
    $("#setTR").hide();

    //disable BuyGETTR
    $("#buygetSpaceTR").hide();
    $("#buygetTR").hide();

    //disable Article Qty TR
    $("#articleQtyTR").hide();

    //Disable downloadfileWithQty TR
    $("#downloadfileWithQty").hide();

    //Enable WithoutQtySampleFileTR
    $("#downloadfileWithoutQty").show();

    //Enable Discount Grid TR
    $("#DiscountGridTR").show();

    //Disable Discount Config Qty TextBox TD
    $("#discQtyTD").hide();

    //Disable Discount Config Qty label TD
    $("#discQtyLabelTD").hide();

    //Enable bxgybuygetTR
    $("#bxgybuygetTR").show();

    //Disable ticketBillTR
    $("#ticketBillTR").hide();

    //Disable ticketPoolTR
    $("#ticketPoolTR").hide();

    //Disable Set From Article Grid
    $("#XarticleGrid").hideCol("setDesc");

    //Disable Qty From Article Grid
    $("#XarticleGrid").hideCol("xqty");

    //Disable Discount Qty From Discount Grid
    $("#discountGrid").hideCol("discQty");

    //Disable Discount Set From Discount Grid
    $("#discountGrid").hideCol("discSetName");

    //Disable Set File Upload Grid
    $("#uploadFileGrid").hideCol("uploadFileSetName");
}

function makeFlatDiscountView(){
    //disable Set TR
    $("#setSpaceTR").hide();
    $("#setTR").hide();

    //disable BuyGETTR
    $("#buygetSpaceTR").hide();
    $("#buygetTR").hide();

    //disable Article Qty TR
    $("#articleQtyTR").hide();

    //Disable downloadfileWithQty TR
    $("#downloadfileWithQty").hide();

    //Enable WithoutQtySampleFileTR
    $("#downloadfileWithoutQty").show();

    //Enable Discount Grid TR
    $("#DiscountGridTR").show();

    //Disable Discount Config Qty TextBox TD
    $("#discQtyTD").hide();

    //Disable Discount Config Qty label TD
    $("#discQtyLabelTD").hide();

    //Disable bxgybuygetTR
    $("#bxgybuygetTR").hide();

    //Disable ticketBillTR
    $("#ticketBillTR").hide();

    //Disable ticketPoolTR
    $("#ticketPoolTR").hide();

    //Disable Set From Article Grid
    $("#XarticleGrid").hideCol("setDesc");

    //Disable Qty From Article Grid
    $("#XarticleGrid").hideCol("xqty");

    //Disable Discount Qty From Discount Grid
    $("#discountGrid").hideCol("discQty");

    //Disable Discount Set From Discount Grid
    $("#discountGrid").hideCol("discSetName");

    //Disable Set File Upload Grid
    $("#uploadFileGrid").hideCol("uploadFileSetName");
}

function makePowerPricingView(){
    //disable Set TR
    $("#setSpaceTR").hide();
    $("#setTR").hide();

    //disable BuyGETTR
    $("#buygetSpaceTR").hide();
    $("#buygetTR").hide();

    //disable Article Qty TR
    $("#articleQtyTR").hide();

    //Disable downloadfileWithQty TR
    $("#downloadfileWithQty").hide();

    //Enable WithoutQtySampleFileTR
    $("#downloadfileWithoutQty").show();

    //Enable Discount Grid TR
    $("#DiscountGridTR").show();

    //Enable Discount Config Qty TextBox TD
    $("#discQtyTD").show();

    //Enable Discount Config Qty label TD
    $("#discQtyLabelTD").show();

    //Disable bxgybuygetTR
    $("#bxgybuygetTR").hide();

    //Disable ticketBillTR
    $("#ticketBillTR").hide();

    //Disable ticketPoolTR
    $("#ticketPoolTR").hide();

    //Disable Set From Article Grid
    $("#XarticleGrid").hideCol("setDesc");

    //Disable Qty From Article Grid
    $("#XarticleGrid").hideCol("xqty");

    //Enable Discount Qty From Discount Grid
    $("#discountGrid").showCol("discQty");

    //Disable Discount Set From Discount Grid
    $("#discountGrid").hideCol("discSetName");

    //Disable Set File Upload Grid
    $("#uploadFileGrid").hideCol("uploadFileSetName");
}

function makeTicketSizeBillLevelView(){
    //disable Set TR
    $("#setSpaceTR").hide();
    $("#setTR").hide();

    //disable BuyGETTR
    $("#buygetSpaceTR").hide();
    $("#buygetTR").hide();

    //disable Article Qty TR
    $("#articleQtyTR").hide();

    //Disable downloadfileWithQty TR
    $("#downloadfileWithQty").hide();

    //Enable WithoutQtySampleFileTR
    $("#downloadfileWithoutQty").show();

    //Disable Discount Grid TR
    $("#DiscountGridTR").hide();

    //Disable Discount Config Qty TextBox TD
    $("#discQtyTD").hide();

    //Disable Discount Config Qty label TD
    $("#discQtyLabelTD").hide();

    //Disable bxgybuygetTR
    $("#bxgybuygetTR").hide();

    //Enable ticketBillTR
    $("#ticketBillTR").show();

    //Disable ticketPoolTR
    $("#ticketPoolTR").hide();

    //Disable Set From Article Grid
    $("#XarticleGrid").hideCol("setDesc");

    //Disable Qty From Article Grid
    $("#XarticleGrid").hideCol("xqty");

    //Disable Discount Qty From Discount Grid
    $("#discountGrid").hideCol("discQty");

    //Disable Discount Set From Discount Grid
    $("#discountGrid").hideCol("discSetName");

    //Disable Set File Upload Grid
    $("#uploadFileGrid").hideCol("uploadFileSetName");
}

function makeTicketSizePoolRewardView(){
    //disable Set TR
    $("#setSpaceTR").hide();
    $("#setTR").hide();

    //Enable BuyGETTR
    $("#buygetSpaceTR").show();
    $("#buygetTR").show();

    //disable Article Qty TR
    $("#articleQtyTR").hide();

    //Disable downloadfileWithQty TR
    $("#downloadfileWithQty").hide();

    //Enable WithoutQtySampleFileTR
    $("#downloadfileWithoutQty").show();

    //Enable Discount Grid TR
    $("#DiscountGridTR").show();

    //Enable Discount Config Qty TextBox TD
    $("#discQtyTD").show();

    //Enable Discount Config Qty label TD
    $("#discQtyLabelTD").show();

    //Disable bxgybuygetTR
    $("#bxgybuygetTR").hide();

    //Disable ticketBillTR
    $("#ticketBillTR").hide();

    //Enable ticketPoolTR
    $("#ticketPoolTR").show();

    //Disable Set From Article Grid
    $("#XarticleGrid").showCol("setDesc");

    //Disable Qty From Article Grid
    $("#XarticleGrid").hideCol("xqty");

    //Disable Discount Qty From Discount Grid
    $("#discountGrid").showCol("discQty");

    //Disable Discount Set From Discount Grid
    $("#discountGrid").hideCol("discSetName");

    //Enable Set File Upload Grid
    $("#uploadFileGrid").showCol("uploadFileSetName");
}

function makeConfigTabView(){
    javascript:tabSwitch_2(2,2,'maintab_','maincontent_');
$("#promoSel").attr("disabled",true);
    $("#setNoSel").attr("disabled",true);

    // For Promo Type 1 First Set Discount Config And Value must be blank only Qty is allowed
    //The same following code is available on set change event
    var promoTypeId=$("#promoSel").val();
    var selectedSet=$("#setSel :selected").text();
    //    alert("--- Promo Type ID : "+promoTypeId+"    Selected Set : "+selectedSet)
    if((promoTypeId==1 && selectedSet.toUpperCase()=="SET 1") || (promoTypeId==2 && selectedSet.toUpperCase()!="SET 1")){
        $("#disConfigSel").attr("disabled",true);
        $("#txtdisValue").attr("disabled",true);
    }else{
        $("#disConfigSel").attr("disabled",false);
        $("#txtdisValue").attr("disabled",false);
    }
}

function makeArticleTabView(){
    javascript:tabSwitch_2(1,2,'maintab_','maincontent_');
//$("#promoSel").attr("disabled",false);
//$("#setNoSel").attr("disabled",false);
}

function fillDiscountConfigCombo(promoTypeId){
    $('#disConfigSel option').each(function(ik, option){
        $(option).remove();
    });

    var select = document.getElementById("disConfigSel");
    var newoption = document.createElement('option');

    newoption.text = "---Select---";
    newoption.value = -1;
    select.add(newoption);

    if(promoTypeId!="2"){
        var newoption1 = document.createElement('option');
        newoption1.text = "Value Off";
        newoption1.value = 0;
        select.add(newoption1);

        var newoption2 = document.createElement('option');
        newoption2.text = "Percentage Off";
        newoption2.value = 1;
        select.add(newoption2);
    }
    var newoption3 = document.createElement('option');
    newoption3.text = "Flat Price";
    newoption3.value = 2;
    select.add(newoption3);
}

function resetArticleDtl(){
    $("#txtArticleNo").val("");
    $("#txtArticleDesc").val("");
    $("#txtMCCode").val("");
    $("#txtMCDesc").val("");
    //    $("#txtXQty").val("");
    $("#txtArticleNo").focus();

    $("#txtArticleNo").attr("disabled", false);
    $("#txtMCCode").attr("disabled", false);
    $("#txtArticleNo").attr("readonly", false);
    $("#txtMCCode").attr("readonly", false);
    //    $("#btnAddArticle").attr("disabled", true);
    $("#btnValidateArticle").attr("disabled", false);
}

function resetSubPromoDiscountConfigDtl(){
    $("#txtdisValue").val("");
    $("#txtDisQty").val("");
    $("#txtdisValue").focus();
}

function resetAllWithoutPromoType(){
    disabledControllOnPageLoad();
    $("#reqGrid").resetSelection();
    $("#setNoSel").val("1");
    $("#buygetSel").val("1");
    fillSetBySetNo("1");
    $("#rbtnManualBX").attr("checked",true);
    $("#rbtnUploadBX").attr("checked",false);
    tabSwitch_2(1,2,'maintab_','maincontent_');
    tabSwitch_2(1,2,'tab_','content_');
    $('#manualEntryForm')[0].reset();
    jQuery("#XarticleGrid").jqGrid("clearGridData", true);
    $('#fileUpload')[0].reset();
    $('#subPromoSubmit')[0].reset();
    jQuery("#discountGrid").jqGrid("clearGridData", true);   
}

function validateArticleORFileUploadAgainstSet(promoTypeId,isManualEntryFormSubmit){
    if(promoTypeId==1 || promoTypeId==2){
        if(isManualEntryFormSubmit==1){

            var values = [];
            $('#setSel option').each(function() {
                values.push( $(this).attr('value') );
            });

            var tableDataIds= jQuery('#XarticleGrid').getDataIDs();
            //Following Loop is to check wehter selected no of sets are entered or not in grid
            for(var i=0;i<values.length;i++){
                var isSetFound=false;
                var setComboVal=values[i];
                for(var index=0;index<=tableDataIds.length;index++){
                    var rowData = jQuery('#XarticleGrid').getRowData(tableDataIds[index]);
                    var enteredSet=rowData.setId;
                    if(setComboVal==enteredSet){
                        isSetFound=true;
                    }
                }
                if(isSetFound==false){
                    return [false,"PLease enter article detail for set : "+setComboVal];
                }
            }
            

        }else{
            var values = [];
            $('#setSel option').each(function() {
                values.push( $(this).attr('value') );
            });

            var tableDataIds= jQuery('#uploadFileGrid').getDataIDs();
            for(var i=0;i<values.length;i++){
                var isSetFound=false;
                var setComboVal=values[i];
                for(var index=0;index<=tableDataIds.length;index++){
                    var rowData = jQuery('#uploadFileGrid').getRowData(tableDataIds[index]);
                    var enteredSet=rowData.uploadFileSetID;
                    if(setComboVal==enteredSet){
                        isSetFound=true;
                    }
                }
                if(isSetFound==false){
                    return [false,"PLease upload article detail for set : "+setComboVal];
                }
            }
        }
    }else if(promoTypeId==6){
        var values = [];
        $('#buygetSel option').each(function() {
            values.push( $(this).attr('value') );
        });
        if(isManualEntryFormSubmit==1){
            var tableDataIds= jQuery('#XarticleGrid').getDataIDs();
            for(var i=0;i<values.length;i++){
                var isSetFound=false;
                var setComboVal=values[i];
                for(var index=0;index<=tableDataIds.length;index++){
                    var rowData = jQuery('#XarticleGrid').getRowData(tableDataIds[index]);
                    var enteredSet=rowData.setId;
                    if(setComboVal==enteredSet){
                        isSetFound=true;
                    }
                }
                if(isSetFound==false){
                    var setName="";
                    if(setComboVal=="1"){
                        setName="BUY";
                    }
                    else{
                        setName="GET";
                    }
                    return [false,"Please enter article detail for "+setName];
                }
            }

        }else{
            var tableDataIds= jQuery('#uploadFileGrid').getDataIDs();
            for(var i=0;i<values.length;i++){
                var isSetFound=false;
                var setComboVal=values[i];
                for(var index=0;index<=tableDataIds.length;index++){
                    var rowData = jQuery('#uploadFileGrid').getRowData(tableDataIds[index]);
                    var enteredSet=rowData.uploadFileSetID;
                    if(setComboVal==enteredSet){
                        isSetFound=true;
                    }
                }
                if(isSetFound==false){
                    alert();
                    return [false,"PLease upload article detail for set : "+setComboVal];
                }
            }
        }
    }
    return[true,''];
}

function validateSubPromoDiscountConfigFields(promoTypeId,discIndex){

    var discsetid="-";
    var discSetName="-";
    var discConfig="-";
    var discValue="-";
    var discQty="-";

    // Promotypes Other than 1 and 2
    if(promoTypeId!="1" && promoTypeId!="2"){
        var discountConfigCheck = checkComboSelection("disConfigSel", "Discount Config");
        if(discountConfigCheck[0]==false){
            return [false,discountConfigCheck[1]];
        }
        discConfig=$("#disConfigSel :selected").text();


        var discountConfigValue=$("#txtdisValue").val();
        var validateDiscountConfigValue=isBlank(discountConfigValue,"Discount Config Value");
        if(validateDiscountConfigValue[0]==false){
            $("#txtdisValue").focus();
            return[false,validateDiscountConfigValue[1]];
        }
        else if(!isNumeric(discountConfigValue)){
            $("#txtdisValue").focus();
            return [false,"Discount Config Value should be numeric."];
        }

        discValue=discountConfigValue;


        //check Value Range 1-100 For Percentage.
        if(discConfig=="Percentage Off"){
            if(discValue<=0 || discValue>100){
                $("#txtdisValue").focus();
                return [false,'Percentage value range must be 1-100.'];
            }
        }else{
            if(discValue<=0){
                $("#txtdisValue").focus();
                return [false,'value must be positive.'];
            }
        }
    }
    
    // 1= Buy X Get Y Set Level
    //2 = Buy X and Y @ Discounted price
    if(promoTypeId=="1" || promoTypeId=="2"){
        discsetid=$("#setSel").val();
        discSetName=$("#setSel :selected").text();
        
        if((promoTypeId=="1" && discSetName.toUpperCase()!="SET 1") || (promoTypeId=="2" && discSetName.toUpperCase()=="SET 1") ){
            var discountConfigCheck = checkComboSelection("disConfigSel", "Discount Config");
            if(discountConfigCheck[0]==false){
                return [false,discountConfigCheck[1]];
            }
            discConfig=$("#disConfigSel :selected").text();


            var discountConfigValue=$("#txtdisValue").val();
            var validateDiscountConfigValue=isBlank(discountConfigValue,"Discount Config Value");
            if(validateDiscountConfigValue[0]==false){
                $("#txtdisValue").focus();
                return[false,validateDiscountConfigValue[1]];
            }else if(!isNumeric(discountConfigValue)){
                $("#txtdisValue").focus();
                return [false,"Discount Config Value should be numeric."];
            }

            discValue=discountConfigValue;


            //check Value Range 1-100 For Percentage.
            if(discConfig=="Percentage Off"){
                if(discValue<=0 || discValue>100){
                    $("#txtdisValue").focus();
                    return [false,'Percentage value range must be 1-100.'];
                }
            }else{
                if(discValue<=0){
                    $("#txtdisValue").focus();
                    return [false,'value must be positive.'];
                }
            }
        }
       
        var discountConfigQty=$("#txtDisQty").val();
        var validateDiscountConfigQty=isBlank(discountConfigQty,"Discount Config Qty");
        if(validateDiscountConfigQty[0]==false){
            $("#txtDisQty").focus();
            return[false,validateDiscountConfigQty[1]];
        }else if(!isNumeric(discountConfigQty)){
            $("#txtDisQty").focus();
            //            resetSubPromoDiscountConfigDtl();
            return [false,"Discount Config Qty should be numeric."];
        }
        discQty=discountConfigQty;
        if(discQty<=0){
            $("#txtDisQty").focus();
            return [false,'Qty must be positive.'];
        }else if(discQty>2147483647){
            $("#txtDisQty").focus();
            return [false,'Qty must be less than 2147483647.'];
        }
        else if(!isInteger(discQty)){
            $("#txtDisQty").focus();
            return [false,'Qty must be numeric.'];
        }
        //        else if(!discQty.toString().match(/^[-]?\d*\$/)){
        //            $("#txtDisQty").focus();
        //            return [false,'Qty must be numeric.'];
        //        }
        if($("#discountGrid tr").length>0){
            var setId=$("#setSel").val();
            var tableDataIds= $('#discountGrid').getDataIDs();
            for(var index=0;index<=tableDataIds.length;index++){
                var rowData = $('#discountGrid').getRowData(tableDataIds[index]);
                if(setId==rowData.discsetid){
                    resetSubPromoDiscountConfigDtl();
                    return [false,'Selected Set already exist.'];
                }
            }
        }
        discsetid=$("#setSel").val();
        discSetName=$("#setSel :selected").text();
    }
    // 4= power pricing
    // 6= Ticket Size Pool Reward
    if(promoTypeId=="4" || promoTypeId=="6"){
        var discountConfigQty=$("#txtDisQty").val();
        var validateDiscountConfigQty=isBlank(discountConfigQty,"Discount Config Qty");
        if(validateDiscountConfigQty[0]==false){
            $("#txtDisQty").focus();
            return[false,validateDiscountConfigQty[1]];
        }else if(!isNumeric(discountConfigQty)){
            $("#txtDisQty").focus();
            resetSubPromoDiscountConfigDtl();
            return [false,"Discount Config Qty should be numeric."];
        }
        discQty=discountConfigQty;

        if(discQty<=0){
            $("#txtDisQty").focus();
            return [false,'Qty must be positive.'];
        }else if(discQty>2147483647){
            $("#txtDisQty").focus();
            return [false,'Qty must be less than 2147483647.'];
        }else if(!isInteger(discQty)){
            $("#txtDisQty").focus();
            return [false,'Qty must be numeric.'];
        }
        //        else if(!discQty.toString().match(/^[-]?\d*\$/)){
        //            $("#txtDisQty").focus();
        //            return [false,'Qty must be numeric.'];
        //        }
        if(promoTypeId==4){            
            var discvalue=$("#txtdisValue").val();
            if(discConfig=="Percentage Off"){                
                discvalue+=" %";
            }else{
                discvalue+=" Rs";
            }
            var discQty=$("#txtDisQty").val();
            var tableDataIds= jQuery('#discountGrid').getDataIDs();
            if(jQuery("#discountGrid tr").length>1){
                for(var index=0;index<=tableDataIds.length;index++){
                    var rowData = jQuery('#discountGrid').getRowData(tableDataIds[index]);
                    if(discvalue==rowData.discValue && discQty==rowData.discQty){
                        resetSubPromoDiscountConfigDtl();
                        return [false,'Entered discount value and qty already exist.'];
                    }
                }
            }
            $("#disConfigSel").attr("disabled",true);
        }
    }
    
    if(discConfig=="Percentage Off"){        
        discValue+=" %";
    }else{
        if(discConfig!="-"){
            discValue+=" Rs";
        }else{
            discValue="0";
        }
    }
    $("#discountGrid").jqGrid('addRowData',discIndex,{
        discsetid:discsetid,
        discSetName:discSetName,
        discConfig:discConfig,
        discValue:discValue,
        discQty:discQty
    });
    resetSubPromoDiscountConfigDtl();

    //Single Entry Validation
    
    //3 = Flat Price
    // 6= Ticket Size ( Pool Reward)
    // 7 =BXGY
    if(promoTypeId==3 || promoTypeId==6 || promoTypeId==7){
        $("#btnAddDiscDtl").attr("disabled",true);
    }
    return[true,''];
}

function getArticleData(){
    var articleGridIds = jQuery("#XarticleGrid").getDataIDs();
    if(articleGridIds.length > 0){
        //        alert("Total Qual Grid Data = "+articleGridIds);
        var tempIndex=0;
        var arr=new Array();

        for(var index=0;index<articleGridIds.length;index++){
            //Columns Values!!
            var rowData=jQuery('#XarticleGrid').getRowData(articleGridIds[index]);

            arr[tempIndex]=rowData.setId;
            arr[++tempIndex]=rowData.setDesc;
            arr[++tempIndex]=rowData.xartNo;
            arr[++tempIndex]=rowData.xartDesc;
            arr[++tempIndex]=rowData.xmcCode;
            arr[++tempIndex]=rowData.xmcDesc;
            arr[++tempIndex]=rowData.xqty;
            arr[++tempIndex]=rowData.xbrandCode;
            arr[++tempIndex]=rowData.xbrandDesc;
            tempIndex++;
        }
        // alert("Qual arr : "+arr);
        $("#XarticleGridData").val(arr);
    }

}

function getDiscountConfigData(){
    var discountGridIds = jQuery("#discountGrid").getDataIDs();
    if(discountGridIds.length > 0){
        //alert("Total Qual Grid Data = "+qualGridIds);
        var tempIndex=0;
        var arr=new Array();
        var discValue;
        for(var index=0;index<discountGridIds.length;index++){
            //Columns Values!!
            var rowData=jQuery('#discountGrid').getRowData(discountGridIds[index]);
            discValue=rowData.discValue;

            if(rowData.discConfig=="Percentage Off"){
                discValue=discValue.replace(" %","");
            }else{
                discValue=discValue.replace(" Rs","");
            }
            arr[tempIndex]=rowData.discsetid;
            arr[++tempIndex]=rowData.discSetName;
            arr[++tempIndex]=rowData.discConfig;
            arr[++tempIndex]=discValue;
            arr[++tempIndex]=rowData.discQty;
            tempIndex++;
        }
        // alert("Qual arr : "+arr);
        $("#discountGridData").val(arr);
    }
}
function getFileUploadArticleData(){
    var articleGridIds = jQuery("#uploadFileGrid").getDataIDs();
    if(articleGridIds.length > 0){
        //        alert("Total Qual Grid Data = "+articleGridIds);
        var tempIndex=0;
        var arr=new Array();

        for(var index=0;index<articleGridIds.length;index++){
            //Columns Values!!
            var rowData=jQuery('#uploadFileGrid').getRowData(articleGridIds[index]);

            arr[tempIndex]=rowData.uploadFileSetID;
            arr[++tempIndex]=rowData.uploadFileSetName;
            arr[++tempIndex]=rowData.uploadFilePath;
            tempIndex++;
        }
        // alert("Qual arr : "+arr);
        $("#hdnFileUploadGridData").val(arr);
    }

}
function getFileUploadArticleDataOnSave(){
    //    var articleGridIds = jQuery("#uploadFileGrid").getDataIDs();
    //    if(articleGridIds.length > 0){
    //        //        alert("Total Qual Grid Data = "+articleGridIds);
    //        var tempIndex=0;
    //        var arr=new Array();
    //
    //        for(var index=0;index<articleGridIds.length;index++){
    //            //Columns Values!!
    //            var rowData=jQuery('#uploadFileGrid').getRowData(articleGridIds[index]);
    //
    //            arr[tempIndex]=rowData.uploadFileSetID;
    //            arr[++tempIndex]=rowData.uploadFileSetName;
    //            arr[++tempIndex]=rowData.uploadFilePath;
    //            tempIndex++;
    //        }
    //        // alert("Qual arr : "+arr);
    //
    //        $("#XarticleGridData").val(arr);
    //    }

    var fileUploadGridData=$("#hdnFileUploadGridData").val();
    $("#XarticleGridData").val(fileUploadGridData);

}

function validateCreatSubPromoFields(isManualEntryFormSubmit,promoTypeId){

    var mstPromoId=$("#mstPromoId").val();
    if(mstPromoId==undefined || mstPromoId==null || mstPromoId.length==0){
        return[false,"Please select request"];
    }
    if(isManualEntryFormSubmit==1){
        getArticleData();
    }else{
        getFileUploadArticleDataOnSave();
    }

    if(promoTypeId!="5" && promoTypeId!="1" && promoTypeId!="2" && jQuery("#discountGrid tr").length<=1){
        //        alert("inside");
        $("#txtDisConfig").focus();
        return [false,"Please enter discount config detail."];        
    }else if(promoTypeId=="1" || promoTypeId=="2"){
        if(jQuery("#discountGrid tr").length<=1){
            return [false,"Please enter discount config detail."];
        }else{
            var discountGridIds = jQuery("#discountGrid").getDataIDs();
            var noOfset=$("#setNoSel").val();
            if(discountGridIds.length!=noOfset){
                return[false,"Please Enter Discount Config Detail For Every SET."];
            }
        }
        getDiscountConfigData();
    /*else if(promoTypeId=="1"){
            var discountGridIds = jQuery("#discountGrid").getDataIDs();
            var noOfset=$("#setNoSel").val();
            if(discountGridIds.length!=noOfset){
                return[false,"Please Enter Discount Config Detail For Every SET."];
            }
            var discountExist=false;
            for(var index=0;index<discountGridIds.length;index++){
                var rowData=jQuery('#discountGrid').getRowData(discountGridIds[index]);
            
                if(rowData.discConfig!="-" && rowData.discValue!="-"){
                    discountExist=true;
                    break;
                }
            }
            if(!discountExist){
                return[false,"Please Enter Discount Cofiguration Detail For Any SET."];
            }
        }else if(promoTypeId=="2"){
            var discountGridIds = jQuery("#discountGrid").getDataIDs();
            var noOfset=$("#setNoSel").val();
            if(discountGridIds.length!=noOfset){
                return[false,"Please Enter Discount Config Detail For Every SET."];
            }
            var set1Exist=false;
            for(var index=0;index<discountGridIds.length;index++){
                var rowData=jQuery('#discountGrid').getRowData(discountGridIds[index]);
                if(rowData.discSetName=="SET 1"){
                    set1Exist=true;
                    break;
                }
            }
            if(!set1Exist){
                return[false,"No Discount Cofiguration Found For SET1."];
            }

        }*/

    }else if(promoTypeId=="5"){
        //        alert("inside 5");
        var discountWorthAmt=$("#txtbuyWorthAmtTicketBill").val();
        var validateWorthAmt=isBlank(discountWorthAmt,"Buy Worth Amt");
        if(validateWorthAmt[0]==false){
            $("#txtbuyWorthAmtTicketBill").focus();
            return[false,validateWorthAmt[1]];
        }else if(!isNumeric(discountWorthAmt)){
            $("#txtbuyWorthAmtTicketBill").focus();
            return [false,"Buy Worth Amt should be numeric."];
        }else if(discountWorthAmt<=0){
            $("#txtbuyWorthAmtTicketBill").focus();
            return [false,'Buy worth amt must be positive.'];
        }

        var discountAmt=$("#txtdiscountAmtTicketBill").val();
        var validatedisAmt=isBlank(discountAmt,"Discount Amt");
        if(validatedisAmt[0]==false){
            $("#txtdiscountAmtTicketBill").focus();
            return[false,validatedisAmt[1]];
        }else if(!isNumeric(discountAmt)){
            $("#txtdiscountAmtTicketBill").focus();
            return [false,"Discount Amt should be numeric."];
        }else if(discountAmt<=0){
            $("#txtdiscountAmtTicketBill").focus();
            return [false,'Discount amt must be positive.'];
        }

        var discountConfigCheck = checkComboSelection("disConfigSelTicketBill", "Discount Config");
        if(discountConfigCheck[0]==false){
            return [false,discountConfigCheck[1]];
        }
    }else if(promoTypeId=="6"){
        //        alert("inside 6");
        var discountWorthAmt=$("#txtbuyWortAmt").val();
        var validateWorthAmt=isBlank(discountWorthAmt,"Buy Worth Amt");
        if(validateWorthAmt[0]==false){
            $("#txtbuyWortAmt").focus();
            return[false,validateWorthAmt[1]];
        }else if(!isNumeric(discountWorthAmt)){
            $("#txtbuyWortAmt").focus();
            return [false,"Buy Worth Amt should be numeric."];
        }
        getDiscountConfigData();
    }else if(promoTypeId=="7"){
        //        alert("inside 7");
        var buy=$("#txtbuy").val();
        var validatebuy=isBlank(buy,"Buy");
        if(validatebuy[0]==false){
            $("#txtbuy").focus();
            return[false,validatebuy[1]];
        }else if(!isNumeric(buy)){
            $("#txtbuy").focus();
            return [false,"Buy should be numeric."];
        }else if(buy<=0){
            $("#txtbuy").focus();
            return [false,'Buy must be positive.'];
        }

        var get=$("#txtget").val();
        var validateget=isBlank(get,"Get");
        if(validateget[0]==false){
            $("#txtget").focus();
            return[false,validateget[1]];
        }else if(!isNumeric(get)){
            $("#txtget").focus();
            return [false,"Get should be numeric."];
        }else if(get<=0){
            $("#txtget").focus();
            return [false,'Get must be positive.'];
        }
        getDiscountConfigData();
    }else{
        getDiscountConfigData();
    }

    

    var marginAchivement=$("#txtBXGYmargin").val();
    var validateMarginAchivement=isBlank(marginAchivement,"Expected Margin ");
    if(validateMarginAchivement[0]==false){
        $("#txtBXGYmargin").focus();
        return[false,validateMarginAchivement[1]];
    }else if(!isNumeric(marginAchivement)){
        $("#txtBXGYmargin").focus();
        return [false,"Expected Margin value should be numeric."];
    }

    var sellQty=$("#txtBXGYsellQty").val();
    var validateSellQty=isBlank(sellQty,"Expected Sales Qty growth ");
    if(validateSellQty[0]==false){
        $("#txtBXGYsellQty").focus();
        return[false,validateSellQty[1]];
    }else if(!isNumeric(sellQty)){
        $("#txtBXGYsellQty").focus();
        return [false,"Expected Sales Qty growth should be numeric."];
    }
    
    var qtyValueSellGrowth=$("#txtBXGYsalegrowth").val();
    var validateSellQtyValue=isBlank(qtyValueSellGrowth,"Expected Sales value growth ");
    if(validateSellQtyValue[0]==false){
        $("#txtBXGYsalegrowth").focus();
        return[false,validateSellQtyValue[1]];
    }else if(!isNumeric(qtyValueSellGrowth)){
        $("#txtBXGYsalegrowth").focus();
        return [false,"Expected Sales value growth should be numeric."];
    }

    /*
    var ticketSizeGrowth=$("#txtBXGYgrowth").val();
    var validateTicketSize=isBlank(ticketSizeGrowth,"Growth In Ticket Size");
    if(validateTicketSize[0]==false){
        $("#txtBXGYgrowth").focus();
        return[false,validateTicketSize[1]];
    }else if(!isNumeric(ticketSizeGrowth)){
        $("#txtBXGYgrowth").focus();
        return [false,"Growth In Ticket Size should be numeric."];
    }
     */
    /*CR 2 Change Growth In Ticket Size can be null
     */

    var ticketSizeGrowth=$("#txtBXGYgrowth").val();
    var validateTicketSize=isBlank(ticketSizeGrowth,"Growth In Ticket Size");
    if(validateTicketSize[0]==true && !isNumeric(ticketSizeGrowth)){
        $("#txtBXGYgrowth").focus();
        return [false,"Growth In Ticket Size should be numeric."];
    }

    

    /*
    var conversionGrowth=$("#txtBXGYgrowthConver").val();
    var validateConversionGrowth=isBlank(conversionGrowth,"Growth In Conversion");
    if(validateConversionGrowth[0]==false){
        $("#txtBXGYgrowthConver").focus();
        return[false,validateConversionGrowth[1]];
    }else if(!isNumeric(conversionGrowth)){
        $("#txtBXGYgrowthConver").focus();
        return [false,"Growth In Conversion should be numeric."];
    }
     */

    /*CR 2 Change Growth In Conversion can be null
     */
    var conversionGrowth=$("#txtBXGYgrowthConver").val();
    var validateConversionGrowth=isBlank(conversionGrowth,"Growth In Conversion");
    if(validateConversionGrowth[0]==true && !isNumeric(conversionGrowth)){
        $("#txtBXGYgrowthConver").focus();
        return [false,"Growth In Conversion should be numeric."];
    }
    
    var validFrom=$("#txtBXGYFrom").val();
    var validTo=$("#txtBXGYTo").val();

    if(validFrom==undefined || validFrom==null || validFrom.length==0){
        return[false,"Please select valid from date."];
    }else if(validTo==undefined || validTo==null || validTo.length==0){
        return[false,"Please select valid to date."];
    }

    var remarksLength=1000;
    var rmarksVal=$("#txtremarks").val();
    rmarksVal=  rmarksVal.replace(/(\r\n|\n|\r)/gm," ");
    $("#txtremarks").val(rmarksVal);
    var remarksCheck=isBlank(rmarksVal,"Remarks");
    if(remarksCheck[0]==false){
        $("#txtremarks").focus();
        return[false,remarksCheck[1]];
    }

    remarksCheck=checkLength(rmarksVal, "Remarks", remarksLength);
    if(remarksCheck[0]==false){
        $("#txtremarks").focus();
        return[false,remarksCheck[1]];
    }

    return[true,''];

}


