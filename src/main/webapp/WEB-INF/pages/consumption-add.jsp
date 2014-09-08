<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html>
    <head>
        <title>This is the title.</title>
        <link rel="stylesheet" href="<c:url value='/css/consumption.css' />" />
    </head>
    
    <body>
        <div class="button-area">
            <button id="btn-save">新建</button>
            <button id="btn-cancel">取消</button>
        </div>        
        
        <div class="content-header ui-widget-header">
            消费<span style="font-size: 80%;"> -> 新建</span>
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">消费类型</div>
                    
                    <div class="input">
                        <select class="selectbox">
                            <option value ="volvo">超市</option>
                            <option value ="saab">网购</option>
                            <option value="opel">普通</option>
                        </select>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">说明</div>
                    
                    <div class="input">
                        <input type="text" class="inputbox"></input>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">日期</div>
                    
                    <div class="input">
                        <input id="theDate" type="text" class="inputbox"></input>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">时间</div>
                    
                    <div class="input">
                        <div id="displayTime" class="value">00:00</div>
                        <div id="timeSlider" class="slider" ></div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            </div>
            
            <div class="content-title ui-widget-header">
                明细
            </div>
            
            <div class="button-area" style="margin-top: 10px; margin-bottom: 20px;">
                <button id="btn-add-item">增加</button>
            </div>
            
            <div>
                <div id="items">
                    <fieldset id="item1">
                        <legend id="item-title1">清单1</legend>
                        
                        <div class="input">
                            <a id="remove-item1" href="javascript:removeItem(1);" class="link">Remove</a>
                        </div>
                        
                        <div class="label">说明</div>
                        <div class="input"><input type="text" class="inputbox"></input></div>
                        
                        <div class="label">类别</div>
                        <div class="input" style="width: 100px;">
                            <a href="javascript:selectItemType(1);" id="item-type1" class="link">请选择</a>
                        </div>
                        
                        <div class="label">金额</div>
                        <div class="input"><input type="text" class="inputbox"></input></div>
                        
                        <div class="label">消费人</div>
                        <div class="input">
                            <select class="selectbox">
                                <option value ="volvo">欧阳亮</option>
                                <option value ="saab">喻敏</option>
                                <option value="opel">欧阳晓筱</option>
                            </select>
                        </div>
                        
                        <div style="clear:both;" ></div>
                    </fieldset>
                </div>
                
            </div>
            
            <div class="content-title ui-widget-header">
                付款
            </div>
            
            <div class="button-area" style="margin-top: 10px; margin-bottom: 20px;" >
                <button id="btn-add-account">增加</button>
            </div>
            
            <div>
                <div id="accounts">
                    <fieldset id="account1">
                        <legend id="account-title1">账户1</legend>
                        <div class="input">
                            <a id="remove-account1" href="javascript:removeAccount(1);" class="link">Remove</a>
                        </div>
                        
                        <div class="label">账户</div>
                        <div class="input" style="width: 400px;">
                            <a href="javascript:selectAccount(1);" id="selectAccount1" class="link">请选择</a>
                        </div>
                        
                        <div class="label" >支付金额</div>
                        <div class="input" ><input type="text" class="inputbox"></input></div>
                        
                        <div style="clear:both;" ></div>
                        
                    </fieldset>
                </div>
                
                <div style="text-align: right;padding-right: 50px;margin-bottom: 20px;">总金额: 0.00</div>
            </div>
            
        </div>
        
        <div id="dialog" title="类型" >
            <div>
                <table id="gridList" ></table> 
            </div>
        </div>
        
        <div id="account-select-dialog" title="账户" >
            <div>
                <table id="account-select-grid" ></table> 
            </div>
        </div>
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/i18n/grid.locale-cn.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.jqGrid.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jqGrid-setting.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
            	$( ".selectbox" ).selectmenu();
            	
                $( "#theDate" ).datepicker({
                    dateFormat: "yy-mm-dd",
                    minDate: -7,
                    maxDate: 1,
                    showAnim: "slide"
                });
                
                $( "#timeSlider" ).slider({
                    max: 95,
                    min: 0,
                    slide: function( event, ui ) {
                        var hour = Math.floor(ui.value / 4);
                        var minute = ui.value % 4 * 15;
                        
                        if (hour < 10) hour = "0" + hour.toString();
                        if (minute == 0) minute = "0" + minute.toString();
                    
                        $( "#displayTime" ).html( hour + ":" + minute );
                    }
                });
                
                
                var itemCnt = 1;
                var accountCnt = 1;
            
                var itemTemplate = "<fieldset id=\"item\#{itemSeq}\">" + 
                    "<legend id=\"item-title\#{itemSeq}\">清单\#{itemSeq}</legend>" +
                    
                    "<div class=\"input\">" +
                        "<a id=\"remove-item\#{itemSeq}\" href=\"javascript:removeItem(\#{itemSeq});\" class=\"link\">Remove</a>" +
                    "</div>" + 
                    
                    "<div class=\"label\">说明</div>" +
                    "<div class=\"input\"><input type=\"text\" class=\"inputbox\"></input></div>" +
                    
                    "<div class=\"label\">类别</div>" +
                    "<div class=\"input\" style=\"width: 100px;\">" +
                        "<a href=\"javascript:selectItemType(\#{itemSeq});\" id=\"item-type\#{itemSeq}\" class=\"link\">请选择</a>" +
                    "</div>" +
                    
                    "<div class=\"label\">金额</div>" +
                    "<div class=\"input\"><input type=\"text\" class=\"inputbox\"></input></div>" +
                    
                    "<div class=\"label\">消费人</div>" +
                    "<div class=\"input\">" +
                        "<select class=\"selectbox\">" +
                            "<option value =\"volvo\">欧阳亮</option>" +
                            "<option value =\"saab\">喻敏</option>" +
                            "<option value=\"opel\">欧阳晓筱</option>" +
                        "</select>" +
                    "</div>" +
                    
                    "<div style=\"clear:both;\" ></div>" +
                "</fieldset>" ;
                
                var accountTemplate = "<fieldset id=\"account\#{accountSeq}\">" +
                        "<legend id=\"account-title\#{accountSeq}\">账户\#{accountSeq}</legend>" +
                        "<div class=\"input\">" +
                            "<a id=\"remove-account\#{accountSeq}\" href=\"javascript:removeAccount(\#{accountSeq});\" class=\"link\">Remove</a>" +
                        "</div>" +
                        
                        "<div class=\"label\">账户</div>" +
                        "<div class=\"input\" style=\"width: 400px;\">" +
                            "<a href=\"javascript:selectAccount(\#{accountSeq});\" id=\"selectAccount\#{accountSeq}\" class=\"link\">请选择</a>" +
                        "</div>" +
                        
                        "<div class=\"label\" >支付金额</div>" +
                        "<div class=\"input\" ><input type=\"text\" class=\"inputbox\"></input></div>" +
                        
                        "<div style=\"clear:both;\" ></div>" +
                        
                    "</fieldset>";
                
                $ (".button-area button").button();
                
                $( "#dialog" ).dialog({
                    autoOpen: false,
                    maxHeight: 300,
                    modal: true,
                    show: {
                        effect: "blind",
                        duration: 1000
                    },
                    hide: {
                        effect: "explode",
                        duration: 1000
                    },
                    buttons: {
                        "Close": function(){
                            $( this ).dialog( "close" );
                        }
                    }
                });
                
                $( "#account-select-dialog" ).dialog({
                    autoOpen: false,
                    minWidth: 775,
                    show: {
                        effect: "blind",
                        duration: 1000
                    },
                    hide: {
                        effect: "explode",
                        duration: 1000
                    },
                    buttons: {
                        "Close": function(){
                            $( this ).dialog( "close" );
                        }
                    }
                });
                
                $( "#btn-add-item" ).click(function(){
                    itemCnt ++;                 
                    $ ( "#items" ).append($( itemTemplate.replace( /#\{itemSeq\}/g, itemCnt ) ));
                    $( ".selectbox" ).selectmenu();
                });
                
                $( "#btn-add-account" ).click(function(){
                    accountCnt ++;                 
                    $ ( "#accounts" ).append($( accountTemplate.replace( /#\{accountSeq\}/g, accountCnt ) ));
                });
                
                removeItem = function(seq){
                   $ ("#item" + seq).remove();
                   
                   if (seq < itemCnt)
                   {
                        seq ++;
                        while (seq <= itemCnt)
                        {
                            $ ( "#item-type" + seq).attr("href", "javascript:selectItemType(" + (seq-1) + ");");
                            $ ( "#item-type" + seq).attr("id", "item-type" + (seq-1));
                            
                            $ ( "#remove-item" + seq).attr("href", "javascript:removeItem(" + (seq-1) + ");");
                            $ ( "#remove-item" + seq).attr("id", "remove-item" + (seq-1));
                            
                            $ ( "#item-title" + seq).html("清单" + (seq-1));
                            $ ( "#item-title" + seq).attr("id", "item-title" + (seq-1));
                            
                            $ ( "#item" + seq).attr("id", "item" + (seq-1));
                       
                            seq++;
                        }
                   }
                   
                   itemCnt --;
                };
                
                removeAccount = function(seq){
                   $ ("#account" + seq).remove();
                   
                   if (seq < accountCnt)
                   {
                        seq ++;
                        while (seq <= accountCnt)
                        {
                            $ ( "#selectAccount" + seq).attr("href", "javascript:selectAccount(" + (seq-1) + ");");
                            $ ( "#selectAccount" + seq).attr("id", "selectAccount" + (seq-1));
                            
                            $ ( "#remove-account" + seq).attr("href", "javascript:removeAccount(" + (seq-1) + ");");
                            $ ( "#remove-account" + seq).attr("id", "remove-account" + (seq-1));
                            
                            $ ( "#account-title" + seq).html("账户" + (seq-1));
                            $ ( "#account-title" + seq).attr("id", "account-title" + (seq-1));
                            
                            $ ( "#account" + seq).attr("id", "account" + (seq-1));
                       
                            seq++;
                        }
                   }
                   
                   accountCnt --;
                };
                
                chooseCategory = function(value){
                    $( "#item-type" + curItem).html(value);
                    $ ( "#dialog" ).dialog( "close" );
                };
                
                selectItemType = function(seq)
                {
                    curItem = seq;
                    $ ( "#dialog" ).dialog( "open" );
                };
                
                chooseAccount = function(value)
                {
                    $( "#selectAccount" + curAccount).html(value);
                    $ ( "#account-select-dialog" ).dialog( "close" );
                };
                
                selectAccount = function(seq)
                {
                    curAccount = seq;
                    $ ( "#account-select-dialog" ).dialog( "open" );
                };
                
                var categoryFormatter = function (cellvalue, options, rowObject){
                    if (rowObject.isLeaf)
                        return "<a href=\"javascript:chooseCategory('" + cellvalue + "');\" >" + cellvalue + "</a>";
                    else
                        return cellvalue;
                };
                
                $("#gridList").jqGrid({
                    url: "<c:url value='/category/ajaxGetAllCategories' />",
                    jsonReader: {id: "categoryOidDesc"},
                    colNames: ["描术", "级别"],
                    colModel: [
                        { name: "categoryDesc", width: 155, align: "left", sortable: false, formatter:categoryFormatter },
                        { name: "categoryLevel", width: 50, align: "center", sortable: false }
                    ],
                    
                    treeGrid: true,
                    treeReader: {
                    	level_field: "categoryLevel",
                        parent_id_field: "parentOidDesc",
                        leaf_field: "isLeaf"
                    },
                    ExpandColClick: true,
                    ExpandColumn: "categoryDesc",
                    treeIcons: {leaf:'ui-icon-circle-check'},
                    treeGridModel: "adjacency",
                    rownumbers: false,
                    autowidth: true
                });
                
                var accountFormatter = function (cellvalue, options, rowObject){
                    return "<a href=\"javascript:chooseAccount('" + cellvalue + "');\" >" + cellvalue + "</a>";
                };
                
                $("#account-select-grid").jqGrid({
                    url: "<c:url value='/account/ajaxGetAllAccounts' />",
                    jsonReader: {id: "accountOid"},
                    colNames: ["所有人", "账户类型", "账户", "可用余额"],
                    colModel: [
                        { name: "ownerUserName", width: 100, align: "center", sortable: false },
                        { name: "acntTypeDesc", width: 100,align: "center", sortable: false },
                        { name: "acntHumanDesc", width: 300, align: "center", sortable: false, formatter:accountFormatter },
                        { name: "balance", width: 180, align: "center", sortable: false}
                    ],
                    autowidth: true
                });
                
                $ ("#btn-cancel").click(function(){
                    window.location.href = "consumption-summary.html";
                });
                
                $ ("#btn-save").click(function(){
                    window.location.href = "consumption-add-confirm.html";
                });
                
            });
        </script>
    </body>
</html>
