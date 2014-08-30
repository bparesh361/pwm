function getRemovedLineBreakerString(stringToReplace){
    stringToReplace = stringToReplace.replace(/(\r\n|\n|\r)/gm," ");
    return stringToReplace;
}


function isNumeric(stringvalue) {
    if (stringvalue == null || !stringvalue.toString().match(/^[-]?\d*\.?\d*$/)){
        return false;
    }
    return true;
}

function isInteger(stringvalue) {
    if(!/^\d+$/.test(stringvalue.toString())) {
        return false;
    }
    return true;
}

function hasWhiteSpace(s) { 
    // Check for white space
    var reWhiteSpace =/\s+/g;
    if (reWhiteSpace.test(s)) {
        return false;
    }
    return true;
}


function isBlank(stringvalue,fieldvalue){      
    if(stringvalue.length==0){        
        $("#msg").html('');
        return [false,fieldvalue+" can not be Blank."];
    } else {
        return [true,''];
    }
}

function checkLength(stringvalue, fieldvalue, length){    
    if(stringvalue.length>length){
        $("#msg").html('');

        return [false,fieldvalue+" length can not be more than "+length+"."];
    } else {
        return [true,''];
    }
}
function checkisNumeric(stringvalue,fieldvalue) {
    if (stringvalue == null || !stringvalue.toString().match(/^[-]?\d*\.?\d*$/)){
        return [false,fieldvalue+" should be numeric."];
    }
    return [true,''];
 
}

function checkComboSelection(comboboxId,comboboxName){
    var getComboBoxID=$("#"+comboboxId).val();
    if(getComboBoxID==-1){
        $("#"+comboboxId).focus();
        return [false,"Please select "+comboboxName+"."];
    }else{
        return [true,''];
    }
}
function isAlphaNumeric(a,fieldvalue) {
    if (a == null || a == "") {
        return [false,fieldvalue+" can not be null."];
    } else if (!a.match(/^[A-Za-z0-9^\s&]*$/)) {
        return [false,fieldvalue+" should be Alpha numeric."];
    } else {
        return [true,''];
    }
}


function moveData(tbFrom, tbTo) {
    //                alert("called");
    //                alert("tcform : "+tbFrom+"--- tbto : "+tbTo);
    var arrFrom = new Array();
    var arrTo = new Array();
    var arrLU = new Array();
    var i;
    for (i = 0; i < tbTo.options.length; i++)
    {
        arrLU[tbTo.options[i].text] = tbTo.options[i].value;
        arrTo[i] = tbTo.options[i].text;
    }
    var fLength = 0;
    var tLength = arrTo.length;
    for(i = 0; i < tbFrom.options.length; i++)
    {
        arrLU[tbFrom.options[i].text] = tbFrom.options[i].value;
        if (tbFrom.options[i].selected && tbFrom.options[i].value != "")
        {
            arrTo[tLength] = tbFrom.options[i].text;
            tLength++;
        }
        else
        {
            arrFrom[fLength] = tbFrom.options[i].text;
            fLength++;
        }
    }

    tbFrom.length = 0;
    tbTo.length = 0;
    var ii;

    for(ii = 0; ii < arrFrom.length; ii++)
    {
        var no = new Option();
        no.value = arrLU[arrFrom[ii]];
        no.text = arrFrom[ii];
        tbFrom[ii] = no;
    }

    for(ii = 0; ii < arrTo.length; ii++)
    {
        var no = new Option();
        no.value = arrLU[arrTo[ii]];
        no.text = arrTo[ii];
        tbTo[ii] = no;
    }
}

function gridPageNavigationPersistRow(id, isSelected,idsOfSelectedRows) {
    // alert("ID : "+id);
    var multiindex = $.inArray(id, idsOfSelectedRows);
    // alert("index :"+multiindex);
    if (!isSelected && multiindex >= 0) {
        idsOfSelectedRows.splice(multiindex, 1); // remove id from the list
    }
    else if (multiindex <0) {
        // alert("Element Push in Array : "+id);
        idsOfSelectedRows.push(id);
    }
// alert("idsOfSelectedRows : "+ idsOfSelectedRows.length);
//
}

function jqueryclearDate(fromdate,todate){
    var dates = $("input[id$="+fromdate+"], input[id$="+todate+"]");
    dates.attr('value', '');
    dates.each(function(){
        $.datepicker._clearDate(this);
    });
}

function gridPageNavigationPersistRowIDAndStatus(id_Status_Id, isSelected,selectedRowsIdAndStatus) {
    // alert("ID : "+id);
    var multiindex = $.inArray(id_Status_Id, selectedRowsIdAndStatus);
    // alert("index :"+multiindex);
    if (!isSelected && multiindex >= 0) {
        selectedRowsIdAndStatus.splice(multiindex, 1); // remove id from the list
    }
    else if (multiindex <0) {
        // alert("Element Push in Array : "+id);
        selectedRowsIdAndStatus.push(id_Status_Id);
    }
// alert("idsOfSelectedRows : "+ idsOfSelectedRows.length);
//
}

function moveAllDataRight(tbFrom, tbTo){
    //                alert("called");
    //                alert("tcform : "+tbFrom+"--- tbto : "+tbTo);
    var arrFrom = new Array();
    var arrTo = new Array();
    var arrLU = new Array();
    var i;
    for (i = 0; i < tbTo.options.length; i++)    {
        arrLU[tbTo.options[i].text] = tbTo.options[i].value;
        arrTo[i] = tbTo.options[i].text;
    }
    var fLength = 0;
    var tLength = arrTo.length;
    for(i = 0; i < tbFrom.options.length; i++)
    {
        arrLU[tbFrom.options[i].text] = tbFrom.options[i].value;
        if (tbFrom.options[i] && tbFrom.options[i].value != "")
        {
            arrTo[tLength] = tbFrom.options[i].text;
            tLength++;
        }
        else
        {
            arrFrom[fLength] = tbFrom.options[i].text;
            fLength++;
        }
    }

    tbFrom.length = 0;
    tbTo.length = 0;
    var ii;

    for(ii = 0; ii < arrFrom.length; ii++)
    {
        var no = new Option();
        no.value = arrLU[arrFrom[ii]];
        no.text = arrFrom[ii];
        tbFrom[ii] = no;
    }

    for(ii = 0; ii < arrTo.length; ii++)
    {
        var no = new Option();
        no.value = arrLU[arrTo[ii]];
        no.text = arrTo[ii];
        tbTo[ii] = no;
    }
}



