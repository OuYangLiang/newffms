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
            消费<span style="font-size: 80%;"> - 新建</span>
        </div>
        
        <c:url value='/consumption/saveAdd' var='url' />
        <spring:form id="form" method="post" action="${url}" modelAttribute="cpnForm" >
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">消费方式</div>
                    
                    <div class="input">
                        <spring:select path="consumption.cpnType" items="${cpnTypes}" class="selectbox" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">日期</div>
                    
                    <div class="input">
                        <spring:input path="consumption.cpnTime" id="cpnTime" class="inputbox" readonly="true"/>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">时间</div>
                    
                    <div class="input">
                        <div id="displayTime" class="value">00:00</div>
                        <div id="timeSlider" class="slider" ></div>
                        <input type="hidden" id="cpnTimeSlider" name="consumption.cpnTimeSlider" value="${consumption.cpnTimeSlider }" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            </div>
            
            <div class="content-title ui-widget-header">
                明细
            </div>
            
            <div class="button-area" style="margin-top: 10px; margin-bottom: 20px;">
                <span id="btn-add-item">增加</span>
            </div>
            
            <div>
                <div id="items">
                    <c:forEach var="item" items="${ cpnForm.cpnItems }" varStatus="status" >
                        <fieldset id="item${status.index }">
                            <legend id="item-title${status.index }">清单${status.index + 1}</legend>

                            <div class="input">
                                <a id="remove-item${status.index }" href="javascript:removeItem(${status.index });" class="link">Remove</a>
                            </div>

                            <div class="label">说明</div>
                            <div class="input">
                                <spring:input id="itemDesc${status.index }" path="cpnItems[${status.index }].itemDesc" class="inputbox" maxlength="30" style="width: 250px;" />
                            </div>

                            <div class="label">类别</div>
                            <div class="input" style="width: 100px;">
                                <spring:input id="categoryDesc${status.index }" path="cpnItems[${status.index }].categoryDesc" class="inputbox" readonly="true" onClick="javascript:selectCategory(${status.index });" />
                                <input type="hidden" id="categoryOid${status.index }" name="cpnItems[${status.index }].categoryOid" value="${item.categoryOid }" />
                            </div>

                            <div class="label">金额</div>
                            <div class="input">
                                <spring:input id="itemAmount${status.index }" path="cpnItems[${status.index }].amount" class="inputbox" onBlur="javascript:checkAmount(this);" maxlength="7" style="width: 70px;" />
                            </div>

                            <div class="label">消费人</div>
                            <div class="input">
                                <spring:select id="itemOwner${status.index }" path='cpnItems[${status.index }].ownerOid' class="selectbox" >
                                    <spring:option value="-1" label="请选择"/>
                                    <spring:options items="${users}" itemValue="userOid" itemLabel="userName"/>
                                </spring:select>
                            </div>

                            <div style="clear:both;" ></div>
                        </fieldset>
                    </c:forEach>
                </div>
                
            </div>
            
            <div class="content-title ui-widget-header">
                付款
            </div>
            
            <div class="button-area" style="margin-top: 10px; margin-bottom: 20px;">
                <span id="btn-add-account">增加</span>
            </div>
            
            <div>
                <div id="accounts">
                    <c:forEach var="item" items="${ cpnForm.accounts }" varStatus="status" >
                    <fieldset id="account${status.index }">
                        <legend id="account-title${status.index }">账户${status.index +1 }</legend>
                        <div class="input">
                            <a id="remove-account${status.index }" href="javascript:removeAccount(${status.index });" class="link">Remove</a>
                        </div>
                        
                        <div class="label">账户</div>
                        <div class="input" style="width: 400px;">
                            <spring:input id="acntHumanDesc${status.index }" style="width: 400px;" path="accounts[${status.index }].acntHumanDesc" class="inputbox" readonly="true" onClick="javascript:selectAccount(${status.index });" />
                            <input type="hidden" id="accountOid${status.index }" name="accounts[${status.index }].acntOid" value="${item.acntOid }" />
                        </div>
                        
                        <div class="label" >支付金额</div>
                        <div class="input" >
                            <spring:input id="payment${status.index }" path="accounts[${status.index }].payment" class="inputbox" onBlur="javascript:checkAmount(this);" maxlength="7" />
                        </div>
                        
                        <div style="clear:both;" ></div>
                    </fieldset>
                    </c:forEach>
                </div>
                
                <div style="text-align: right;padding-right: 50px;margin-bottom: 20px;">总金额: 0.00</div>
            </div>
            
        </div>
        </spring:form>
        
        
        <div id="category-select-dialog" title="类型" >
            <div>
                <table id="category-select-grid" ></table> 
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
        <script src="<c:url value='/js/common.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
            	$ ("#btn-cancel").click(function(){
                    
                });
                
                $ ("#btn-save").click(function(){
                    $ ("#form").submit();
                });
            	
            	$ (".button-area button").button();
            	$( ".selectbox" ).selectmenu();
            	
                $( "#cpnTime" ).datepicker({
                    dateFormat: "yy-mm-dd",
                    minDate: -30,
                    maxDate: 30,
                    showAnim: "slide",
                    onSelect: function(dateText, inst) {
                    	$("#cpnTime").val(dateText);
                    }
                });
                
                var cpnTimeSlider = ${cpnForm.consumption.cpnTimeSlider};
                var timeSliderToDesc = function(cpnTimeSlider) {
                	var hour = Math.floor(cpnTimeSlider / 4);
                    var minute = cpnTimeSlider % 4 * 15;
                    
                    if (hour < 10) hour = "0" + hour.toString();
                    if (minute == 0) minute = "0" + minute.toString();
                    
                    return hour + ":" + minute;
                };
                $( "#displayTime" ).html( timeSliderToDesc(cpnTimeSlider) );
                $( "#timeSlider" ).slider({
                    max: 95,
                    min: 0,
                    value: cpnTimeSlider,
                    slide: function( event, ui ) {
                    	$( "#cpnTimeSlider" ).val(ui.value);
                        $( "#displayTime" ).html( timeSliderToDesc(ui.value) );
                    }
                });
                
                
                //明细
                var itemCnt = ${ cpnForm.cpnItems.size() };
                var itemTemplate = "<fieldset id=\"item\#{itemSeq}\">" +
	                "<legend id=\"item-title\#{itemSeq}\">清单\#{itemCnt}</legend>" +
	
	                "<div class=\"input\">" +
	                    "<a id=\"remove-item\#{itemSeq}\" href=\"javascript:removeItem(\#{itemSeq});\" class=\"link\">Remove</a>" +
	                "</div>" +
	
	                "<div class=\"label\">说明</div>" +
	                "<div class=\"input\">" +
	                    "<input type=\"text\" id=\"itemDesc\#{itemSeq}\" name=\"cpnItems[\#{itemSeq}].itemDesc\" class=\"inputbox\" maxlength=\"30\" style=\"width: 250px;\" />" +
	                "</div>" +
	
	                "<div class=\"label\">类别</div>" +
	                "<div class=\"input\" style=\"width: 100px;\">" +
	                    "<input type=\"text\" id=\"categoryDesc\#{itemSeq}\" name=\"cpnItems[\#{itemSeq}].categoryDesc\" class=\"inputbox\" readonly=\"true\" onClick=\"javascript:selectCategory(\#{itemSeq});\" />" +
	                    "<input type=\"hidden\" id=\"categoryOid\#{itemSeq}\" name=\"cpnItems[\#{itemSeq}].categoryOid\" value=\"\" />" +
	                "</div>" +
	
	                "<div class=\"label\">金额</div>" +
	                "<div class=\"input\">" +
	                    "<input type=\"text\" id=\"itemAmount\#{itemSeq}\" name=\"cpnItems[\#{itemSeq}].amount\" class=\"inputbox\" onBlur=\"javascript:checkAmount(this);\" maxlength=\"7\" style=\"width: 70px;\" />" +
	                "</div>" +
	
	                "<div class=\"label\">消费人</div>" +
	                "<div class=\"input\">" +
	                    "<select class=\"selectbox\" id=\"itemOwner\#{itemSeq}\" name='cpnItems[\#{itemSeq}].ownerOid'>" +
	                        "<option value =\"-1\">请选择</option>" +
	                        <c:forEach var="user" items="${users}" varStatus="status" >
	                        "<option value =\"${user.userOid}\">${user.userName}</option>" +
	                        </c:forEach>
	                    "</select>" +
	                "</div>" +
	
	                "<div style=\"clear:both;\" ></div>" +
	            "</fieldset>";
	            
	            $( "#btn-add-item" ).button();
                $( "#btn-add-item" ).click(function(){
                    itemCnt ++;
                    $ ( "#items" ).append(itemTemplate.replace( /#\{itemSeq\}/g, (itemCnt-1) ).replace( /#\{itemCnt\}/g, itemCnt ) );
                    $( ".selectbox" ).selectmenu();
                });
                
                removeItem = function(seq){
                    $ ("#item" + seq).remove();
                    
                    if (seq < (itemCnt - 1))
                    {
                         seq ++;
                         while (seq <= (itemCnt - 1))
                         {
                        	 $ ( "#itemOwner" + seq).attr("name", "cpnItems[" + (seq-1) + "].ownerOid");
                             $ ( "#itemOwner" + seq).attr("id", "itemOwner" + (seq-1));
                             
                             $ ( "#itemAmount" + seq).attr("name", "cpnItems[" + (seq-1) + "].amount");
                             $ ( "#itemAmount" + seq).attr("id", "itemAmount" + (seq-1));
                             
                             $ ( "#categoryDesc" + seq).attr("name", "cpnItems[" + (seq-1) + "].categoryDesc");
                             $ ( "#categoryDesc" + seq).attr("onClick", "javascript:selectCategory(" + (seq-1) + ");");
                             $ ( "#categoryDesc" + seq).attr("id", "categoryDesc" + (seq-1));
                             $ ( "#categoryOid" + seq).attr("name", "cpnItems[" + (seq-1) + "].categoryOid");
                             $ ( "#categoryOid" + seq).attr("id", "categoryOid" + (seq-1));
                             
                             $ ( "#itemDesc" + seq).attr("name", "cpnItems[" + (seq-1) + "].itemDesc");
                             $ ( "#itemDesc" + seq).attr("id", "itemDesc" + (seq-1));
                             
                             $ ( "#remove-item" + seq).attr("href", "javascript:removeItem(" + (seq-1) + ");");
                             $ ( "#remove-item" + seq).attr("id", "remove-item" + (seq-1));
                             
                             $ ( "#item-title" + seq).html("清单" + (seq));
                             $ ( "#item-title" + seq).attr("id", "item-title" + (seq-1));
                             
                             $ ( "#item" + seq).attr("id", "item" + (seq-1));
                        
                             seq++;
                         }
                    }
                    
                    itemCnt --;
                 };
                 
                 chooseCategory = function(categoryOid, categoryDesc) {
                     $( "#categoryDesc" + curItem).val(categoryDesc);
                     $( "#categoryOid" + curItem).val(categoryOid);
                     $ ( "#category-select-dialog" ).dialog( "close" );
                 };
                 
                 selectCategory = function(seq) {
                     curItem = seq;
                     $ ( "#category-select-dialog" ).dialog( "open" );
                 };
	            
                 $( "#category-select-dialog" ).dialog( {
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
                 
                 var categoryFormatter = function (cellvalue, options, rowObject){
                     if (rowObject.isLeaf)
                         return "<a href=\"javascript:chooseCategory(" + rowObject.categoryOid + ", '" + cellvalue + "');\" >" + cellvalue + "</a>";
                     else
                         return cellvalue;
                 };
                 
                 $("#category-select-grid").jqGrid({
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
	            
	            
                
	            
	            //账户
	            var accountCnt = ${ cpnForm.accounts.size() };
                var accountTemplate = "<fieldset id=\"account\#{accountSeq}\">" +
		                "<legend id=\"account-title\#{accountSeq}\">账户\#{accountCnt}</legend>" +
		                "<div class=\"input\">" +
		                    "<a id=\"remove-account\#{accountSeq}\" href=\"javascript:removeAccount(\#{accountSeq});\" class=\"link\">Remove</a>" +
		                "</div>" +
		                
		                "<div class=\"label\">账户</div>" +
		                "<div class=\"input\" style=\"width: 400px;\">" +
		                    "<input type=\"text\" id=\"acntHumanDesc\#{accountSeq}\" style=\"width: 400px;\" name=\"accounts[\#{accountSeq}].acntHumanDesc\" class=\"inputbox\" readonly=\"true\" onClick=\"javascript:selectAccount(\#{accountSeq});\" />" +
		                    "<input type=\"hidden\" id=\"accountOid\#{accountSeq}\" name=\"accounts[\#{accountSeq}].acntOid\" value=\"\" />" +
		                "</div>" +
		                
		                "<div class=\"label\" >支付金额</div>" +
		                "<div class=\"input\" >" +
		                    "<input type=\"text\" id=\"payment\#{accountSeq}\" name=\"accounts[\#{accountSeq}].payment\" class=\"inputbox\" onBlur=\"javascript:checkAmount(this);\" maxlength=\"7\" />" +
		                "</div>" +
		                
		                "<div style=\"clear:both;\" ></div>" +
		            "</fieldset>";
                
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
                
                $( "#btn-add-account" ).button();
                $( "#btn-add-account" ).click(function(){
                    accountCnt ++;                 
                    $ ( "#accounts" ).append(accountTemplate.replace( /#\{accountSeq\}/g, (accountCnt-1) ).replace( /#\{accountCnt\}/g, accountCnt ) );
                });
                
                removeAccount = function(seq){
                   $ ("#account" + seq).remove();
                   
                   if (seq < (accountCnt -1) )
                   {
                        seq ++;
                        while (seq <= (accountCnt -1))
                        {
                        	$ ( "#account" + seq).attr("id", "account" + (seq-1));
                        	
                        	$ ( "#account-title" + seq).html("账户" + seq);
                            $ ( "#account-title" + seq).attr("id", "account-title" + (seq-1));
                            
                            $ ( "#remove-account" + seq).attr("href", "javascript:removeAccount(" + (seq-1) + ");");
                            $ ( "#remove-account" + seq).attr("id", "remove-account" + (seq-1));
                        	
                        	$ ( "#acntHumanDesc" + seq).attr("name", "accounts[" + (seq-1) + "].acntHumanDesc");
                        	$ ( "#acntHumanDesc" + seq).attr("onClick", "javascript:selectAccount(" + (seq-1) + ");");
                            $ ( "#acntHumanDesc" + seq).attr("id", "acntHumanDesc" + (seq-1));
                            
                            $ ( "#accountOid" + seq).attr("name", "accounts[" + (seq-1) + "].acntOid");
                            $ ( "#accountOid" + seq).attr("id", "accountOid" + (seq-1));
                        	
                            $ ( "#payment" + seq).attr("name", "accounts[" + (seq-1) + "].payment");
                            $ ( "#payment" + seq).attr("id", "payment" + (seq-1));
                            
                            seq++;
                        }
                   }
                   
                   accountCnt --;
                };
                
                
                chooseAccount = function(acntOid, acntHumanDesc)
                {
                    $( "#acntHumanDesc" + curAccount).val(acntHumanDesc);
                    $( "#accountOid" + curAccount).val(acntOid);
                    $ ( "#account-select-dialog" ).dialog( "close" );
                };
                
                selectAccount = function(seq)
                {
                    curAccount = seq;
                    $ ( "#account-select-dialog" ).dialog( "open" );
                };
                
                var accountFormatter = function (cellvalue, options, rowObject){
                    return "<a href=\"javascript:chooseAccount(" + rowObject.acntOid + ", '" + cellvalue + "');\" >" + cellvalue + "</a>";
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
            });
        </script>
    </body>
</html>
