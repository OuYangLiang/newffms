<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
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
        
        <c:url value='/incoming/confirmEdit' var='url' />
        <spring:form id="form" method="post" action="${url}" modelAttribute="incomingForm" >
        <input type="hidden" name="incomingOid" value="${incomingForm.incomingOid}" />
        <div id="errorArea" class="ui-widget" style="margin-bottom:5px;display:none">
            <div class="ui-state-error ui-corner-all" style="margin-right: 400px; padding: 5px 30px;" >
                <span class="ui-icon ui-icon-alert" style="float: left; margin-top:5px; "></span>
                <span id="errorMsg" style="font-size: 70%;"><spring:errors path="*" /></span>
            </div>
            
            <div style="clear:both;" ></div>
        </div>
        
        <div class="content-header ui-widget-header">
            收入<span style="font-size: 80%;"> - 修改</span>
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">收入人</div>
                    
                    <div class="input">
                        <spring:select path="ownerOid" class="selectbox" >
                            <spring:option value="" label="请选择"/>
                            <spring:options items="${users}" itemValue="userOid" itemLabel="userName"/>
                        </spring:select>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">收入类型</div>
                    
                    <div class="input">
                        <spring:select path="incomingType" class="selectbox" >
                            <spring:option value="" label="请选择"/>
                            <spring:options items="${incomingTypes}" />
                        </spring:select>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">说明</div>
                    
                    <div class="input">
                        <spring:input data-validation-engine="validate[required]" path="incomingDesc" class="inputbox" maxlength="30" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">日期</div>
                    
                    <div class="input">
                        <spring:input path="incomingDate" id="theDate" class="inputbox" readonly="true" data-validation-engine="validate[required]" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">金额</div>
                    
                    <div class="input">
                        <spring:input data-validation-engine="validate[required]" path="amount" class="inputbox" onBlur="javascript:checkAmount(this);" maxlength="7" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">目标账户</div>
                    
                    <div class="input">
                        <spring:input data-validation-engine="validate[required]" id="acntHumanDesc" path="acntHumanDesc" class="inputbox" readonly="true" onClick="javascript:selectAccount();" />
                        <input type="hidden" id="acntOid" name="acntOid" value="<c:out value='${incomingForm.acntOid }' />" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            </div>
            
        </div>
        
        </spring:form>
        
        <div id="account-select-dialog" title="账户" >
            <div>
                <table id="account-select-grid" ></table> 
            </div>
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
            	if ('<c:out value="${validation}" />' === 'false') {
                    $("#errorArea").css("display", "");
                }
            	
            	$ (".button-area button").button();
                $( ".selectbox" ).selectmenu();
                
                $ ("#btn-cancel").click(function(){
                    window.location.href = "<c:url value='/incoming/view' />?incomingOid=<c:out value='${incomingForm.incomingOid}' />";
                });
                
                $ ("#btn-save").click(function(){
                    //提交表单时，使用使用jquery validation engine进行前端基本验证。
                    $("#form").validationEngine();
                    if ($ ("#form").validationEngine('validate')) {
                        $ ("#form").submit();
                    }
                });
            	
            	$( "#theDate" ).datepicker({
                    dateFormat: "yy-mm-dd",
                    minDate: -7,
                    maxDate: 1,
                    showAnim: "slide"
                });
                
                
                var accountGridLoaded = false;
                $( "#account-select-dialog" ).dialog({
                    autoOpen: false,
                    minWidth: 775,
                    show: {
                        effect: "blind",
                        duration: 300
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
                
                chooseAccount = function(acntOid, acntHumanDesc)
                {
                	$( "#acntHumanDesc").val(acntHumanDesc);
                    $( "#acntOid").val(acntOid);
                    $( "#account-select-dialog" ).dialog( "close" );
                };
                
                selectAccount = function()
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
                    
                    $ ( "#account-select-dialog" ).dialog( "open" );
                };
            });
        </script>
    
    </body>
</html>