function setHighchartOption(){
    Highcharts.setOptions({
        chart: {
            backgroundColor: {
                linearGradient: [0, 0, 500, 500],
                stops: [
                    [0, 'rgb(255, 255, 255)'],
                    [1, 'rgb(240, 240, 255)']
                ]
            },
            borderWidth: 2,
            plotBackgroundColor: 'rgba(255, 255, 255, .9)',
            plotShadow: true,
            plotBorderWidth: 1
        },
        lang: {
            months: [ "1月" , "2月" , "3月" , "4月" , "5月" , "6月" , "7月" , "8月" , "9月" , "10月" , "11月" , "12月"],
            shortMonths: [ "1月" , "2月" , "3月" , "4月" , "5月" , "6月" , "7月" , "8月" , "9月" , "10月" , "11月" , "12月"]
        }
    });
};


function groupConsumptionViaCategory(data) {
    var summary = {};
    
    $.each(data, function(index, obj){
        if (!summary[obj['categoryOid']])
        {
            summary[obj['categoryOid']] = 0;
        }
        
        summary[obj['categoryOid']] += obj['item_amount'];
    });
    
    return summary;
}


function setAmountToCategories(param, groupCategory)
{
    $.each(param, function(index, obj){
        $.each(groupCategory, function(cat, amount){
            if (obj['categoryOid'] == cat)
                obj['amount'] = amount;
        });
    });

    var newParam = {};
    
    $.each(param, function(index, obj){
        if (!obj['amount'])
            obj['amount'] = 0;
    
        newParam[obj['categoryOid']] = obj;
    });
    
    $.each(param, function(index, obj){
        if (obj['isLeaf'])
        {
            var level = obj['level'];
            var categoryOid = obj['parentOid'];
            var amount = obj['amount'];
            
            while (level > 0)
            {
                if (!newParam[categoryOid]['amount'])
                    newParam[categoryOid]['amount'] = 0;
                    
                newParam[categoryOid]['amount'] += amount;
                
                level --;
                amount = newParam[categoryOid]['amount'];
                categoryOid = newParam[categoryOid]['parentOid'];
            }
        }
    });
    
    param = [];
    $.each(newParam, function(index, obj){
        //if (obj['amount'] > 0)
            param.push(obj);
    });
    
    return param;
}