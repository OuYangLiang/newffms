function checkAmount(obj) {
    var oldValue = $(obj).val();
    
    if (isNaN(oldValue)) {
        alert("请输入有效值。");
        $(obj).val("0.00");
        return false;
    }
    
    if(oldValue!="")
        $(obj).val(parseFloat(oldValue).toFixed(2));
    
    return true;
}