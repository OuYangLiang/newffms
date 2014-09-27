<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html>
    <head>
        <title>This is the title.</title>
        <link rel="stylesheet" href="<c:url value='/css/consumption.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/validationEngine.jquery.css' />" />
    </head>
    
    <body>
        <div class="button-area">
            <button id="btn-save">修改</button>
            <button id="btn-cancel">返回</button>
        </div>
        
        <spring:errors path="errors" />
        
        <c:url value='/consumption/confirmEdit' var='url' />
        <spring:form id="form" method="post" action="${url}" modelAttribute="cpnForm" >
        <input type="hidden" name="consumption.cpnOid" value="${cpnForm.consumption.cpnOid}" />
        <div id="errorArea" class="ui-widget" style="margin-bottom:5px;display:none">
            <div class="ui-state-error ui-corner-all" style="margin-right: 400px; padding: 5px 30px;" >
	            <span class="ui-icon ui-icon-alert" style="float: left; margin-top:5px; "></span>
	            <span id="errorMsg" style="font-size: 70%;"><spring:errors path="*" /></span>
            </div>
            
            <div style="clear:both;" ></div>
        </div>
        
        <div class="content-header ui-widget-header">
            消费<span style="font-size: 80%;"> - 修改</span>
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">消费方式</div>
                    
                    <div class="input">
                        <spring:select path="consumption.cpnType" class="selectbox" >
                            <spring:option value="" label="请选择"/>
                            <spring:options items="${cpnTypes}" />
                        </spring:select>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">日期</div>
                    
                    <div class="input">
                        <spring:input path="consumption.cpnTime" id="cpnTime" class="inputbox" readonly="true" data-validation-engine="validate[required]" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">时间</div>
                    
                    <div class="input">
                        <div id="displayTime" class="value">00:00</div>
                        <div id="timeSlider" class="slider" ></div>
                        <input type="hidden" id="cpnTimeSlider" name="consumption.cpnTimeSlider" value="${cpnForm.consumption.cpnTimeSlider}" />
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
                                <spring:input data-validation-engine="validate[required]" id="itemDesc${status.index }" path="cpnItems[${status.index }].itemDesc" class="inputbox" maxlength="30" style="width: 250px;" />
                            </div>

                            <div class="label">类别</div>
                            <div class="input" style="width: 100px;">
                                <spring:input data-validation-engine="validate[required]" id="categoryDesc${status.index }" path="cpnItems[${status.index }].categoryDesc" class="inputbox" readonly="true" onClick="javascript:selectCategory(${status.index });" />
                                <input type="hidden" id="categoryOid${status.index }" name="cpnItems[${status.index }].categoryOid" value="${item.categoryOid }" />
                            </div>

                            <div class="label">金额</div>
                            <div class="input">
                                <spring:input data-validation-engine="validate[required]" id="itemAmount${status.index }" path="cpnItems[${status.index }].amount" class="inputbox" onBlur="javascript:checkItemAmount(this);" maxlength="7" style="width: 70px;" />
                            </div>

                            <div class="label">消费人</div>
                            <div class="input">
                                <spring:select data-validation-engine="validate[required]" id="itemOwner${status.index }" path='cpnItems[${status.index }].ownerOid' class="selectbox" >
                                    <spring:option value="" label="请选择"/>
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
                            <spring:input data-validation-engine="validate[required]" id="acntHumanDesc${status.index }" style="width: 400px;" path="accounts[${status.index }].acntHumanDesc" class="inputbox" readonly="true" onClick="javascript:selectAccount(${status.index });" />
                            <input type="hidden" id="accountOid${status.index }" name="accounts[${status.index }].acntOid" value="${item.acntOid }" />
                        </div>
                        
                        <div class="label" >支付金额</div>
                        <div class="input" >
                            <spring:input data-validation-engine="validate[required]" id="payment${status.index }" path="accounts[${status.index }].payment" class="inputbox" onBlur="javascript:checkAmount(this);" maxlength="7" />
                        </div>
                        
                        <div style="clear:both;" ></div>
                    </fieldset>
                    </c:forEach>
                </div>
                
                <div style="text-align: right;padding-right: 50px;margin-bottom: 20px;">总金额: <span id="totalAmountDisplay">${cpnForm.totalItemAmount }</span></div>
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
        
        <div id="last-item-dialog" title="警告">
            没消费记录吗，亲？
        </div>
        
        <div id="last-account-dialog" title="警告">
            你想不付钱啊，亲。
        </div>
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/i18n/grid.locale-cn.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.jqGrid.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine-zh_CN.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jqGrid-setting.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/common.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
            	//验证失败后，显示后台错误信息。
            	if ('<c:out value="${validation}" />' === 'false') {
            		$("#errorArea").css("display", "");
            	}
            	
            	$ ("#btn-cancel").click(function(){
            		window.location.href = "<c:url value='/consumption/view' />?cpnOid=<c:out value='${cpnForm.consumption.cpnOid}' />";
                });
                
                $ ("#btn-save").click(function(){
                	//提交表单时，使用使用jquery validation engine进行前端基本验证。
                	$("#form").validationEngine();
                	if ($ ("#form").validationEngine('validate')) {
                		//前端基本验证通过的话，继续进行业务前端业务验证
                        var totalPayment = 0;
                        var totalAmount  = 0;
                        
                        for (var i = 0; i < itemCnt; i ++) {
                            totalAmount += parseFloat($("#itemAmount" + i).val());
                        }
                        for (var i = 0; i < accountCnt; i ++) {
                            totalPayment += parseFloat($("#payment" + i).val());
                        }
                        
                        if (totalAmount != totalPayment) {
                        	//前端业务验证不通过，显示错误信息，不提交表单。
                            $("#errorMsg").html("消费总金额与支付总金额不匹配，亲。");
                            $("#errorArea").css("display", "");
                            
                            return;
                        }
                        
                        $ ("#form").submit();
                	}
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
                
                calculateAmount = function() {
                	var totalAmount = 0;
                	for (var i = 0; i < itemCnt; i ++) {
                		if ($("#itemAmount" + i).val() != "") {
                			totalAmount += parseFloat($("#itemAmount" + i).val());
                		}
                    }
                	
                	$("#totalAmountDisplay").html(parseFloat(totalAmount).toFixed(2));
                };
                
                checkItemAmount = function(obj) {
                	checkAmount(obj);
                	calculateAmount();
                };
                
                $( "#last-item-dialog" ).dialog( {
                    autoOpen: false,
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
                
                $( "#last-account-dialog" ).dialog( {
                    autoOpen: false,
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
                
                //明细
                var categoryGridLoaded = false;
                var itemCnt = ${ cpnForm.cpnItems.size() };
                var itemTemplate = "<fieldset id=\"item\#{itemSeq}\">" +
	                "<legend id=\"item-title\#{itemSeq}\">清单\#{itemCnt}</legend>" +
	
	                "<div class=\"input\">" +
	                    "<a id=\"remove-item\#{itemSeq}\" href=\"javascript:removeItem(\#{itemSeq});\" class=\"link\">Remove</a>" +
	                "</div>" +
	
	                "<div class=\"label\">说明</div>" +
	                "<div class=\"input\">" +
	                    "<input type=\"text\" data-validation-engine=\"validate[required]\" id=\"itemDesc\#{itemSeq}\" name=\"cpnItems[\#{itemSeq}].itemDesc\" class=\"inputbox\" maxlength=\"30\" style=\"width: 250px;\" />" +
	                "</div>" +
	
	                "<div class=\"label\">类别</div>" +
	                "<div class=\"input\" style=\"width: 100px;\">" +
	                    "<input type=\"text\" data-validation-engine=\"validate[required]\" id=\"categoryDesc\#{itemSeq}\" name=\"cpnItems[\#{itemSeq}].categoryDesc\" class=\"inputbox\" readonly=\"true\" onClick=\"javascript:selectCategory(\#{itemSeq});\" />" +
	                    "<input type=\"hidden\" id=\"categoryOid\#{itemSeq}\" name=\"cpnItems[\#{itemSeq}].categoryOid\" value=\"\" />" +
	                "</div>" +
	
	                "<div class=\"label\">金额</div>" +
	                "<div class=\"input\">" +
	                    "<input type=\"text\" data-validation-engine=\"validate[required]\" id=\"itemAmount\#{itemSeq}\" name=\"cpnItems[\#{itemSeq}].amount\" class=\"inputbox\" onBlur=\"javascript:checkItemAmount(this);\" maxlength=\"7\" style=\"width: 70px;\" />" +
	                "</div>" +
	
	                "<div class=\"label\">消费人</div>" +
	                "<div class=\"input\">" +
	                    "<select data-validation-engine=\"validate[required]\" id=\"itemOwner\#{itemSeq}\" name='cpnItems[\#{itemSeq}].ownerOid' class=\"selectbox\" >" +
	                        "<option value =\"\">请选择</option>" +
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
                	if (itemCnt == 1) {
                		$ ( "#last-item-dialog" ).dialog( "open" );
                        return;
                    }
                	
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
                	 if (!categoryGridLoaded) {
                		 categoryGridLoaded = true;
                		 
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
                	 }
                	 
                     curItem = seq;
                     $ ( "#category-select-dialog" ).dialog( "open" );
                 };
	            
                 $( "#category-select-dialog" ).dialog( {
                     autoOpen: false,
                     maxHeight: 300,
                     modal: true,
                     show: {
                         effect: "blind",
                         duration: 400
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
                 
	            //账户
	            var selectedAccounts = new Array();//用来保存已经使用过的账户oid。
	            <c:forEach var="item" items="${ cpnForm.accounts }" varStatus="status" >
	            selectedAccounts.push('${item.acntOid}');
	            </c:forEach>
	            
	            var accountGridLoaded = false;
	            var accountCnt = ${ cpnForm.accounts.size() };
                var accountTemplate = "<fieldset id=\"account\#{accountSeq}\">" +
		                "<legend id=\"account-title\#{accountSeq}\">账户\#{accountCnt}</legend>" +
		                "<div class=\"input\">" +
		                    "<a id=\"remove-account\#{accountSeq}\" href=\"javascript:removeAccount(\#{accountSeq});\" class=\"link\">Remove</a>" +
		                "</div>" +
		                
		                "<div class=\"label\">账户</div>" +
		                "<div class=\"input\" style=\"width: 400px;\">" +
		                    "<input type=\"text\" data-validation-engine=\"validate[required]\" id=\"acntHumanDesc\#{accountSeq}\" style=\"width: 400px;\" name=\"accounts[\#{accountSeq}].acntHumanDesc\" class=\"inputbox\" readonly=\"true\" onClick=\"javascript:selectAccount(\#{accountSeq});\" />" +
		                    "<input type=\"hidden\" id=\"accountOid\#{accountSeq}\" name=\"accounts[\#{accountSeq}].acntOid\" value=\"\" />" +
		                "</div>" +
		                
		                "<div class=\"label\" >支付金额</div>" +
		                "<div class=\"input\" >" +
		                    "<input type=\"text\" data-validation-engine=\"validate[required]\" id=\"payment\#{accountSeq}\" name=\"accounts[\#{accountSeq}].payment\" class=\"inputbox\" onBlur=\"javascript:checkAmount(this);\" maxlength=\"7\" />" +
		                "</div>" +
		                
		                "<div style=\"clear:both;\" ></div>" +
		            "</fieldset>";
                
                $( "#account-select-dialog" ).dialog({
                    autoOpen: false,
                    minWidth: 775,
                    show: {
                        effect: "blind",
                        duration: 400
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
                    if (accountCnt == 1) {
                    	$ ( "#last-account-dialog" ).dialog( "open" );
                    	return;
                    }
                    
                    if (arrContain(selectedAccounts, $("#accountOid" + seq).val())) {
                        arrRemove(selectedAccounts, $("#accountOid" + seq).val());
                    }
                    
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
                	if (arrContain(selectedAccounts, acntOid)) {
                		alert("该账户已经使用过了，亲。");
                		return;
                	}
                	
                    $( "#acntHumanDesc" + curAccount).val(acntHumanDesc);
                    $( "#accountOid" + curAccount).val(acntOid);
                    $ ( "#account-select-dialog" ).dialog( "close" );
                    selectedAccounts.push(acntOid);
                };
                
                selectAccount = function(seq)
                {
                	if (!accountGridLoaded) {
                		accountGridLoaded = true;
                		
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
                    }
                	
                    curAccount = seq;
                    $ ( "#account-select-dialog" ).dialog( "open" );
                };
                
            });
        </script>
    </body>
</html>
