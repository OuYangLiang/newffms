<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <head>
        <link rel="stylesheet" href="<c:url value='/css/validationEngine.jquery.css' />" />
    </head>

    <body>
        <div class="button-area">
            <button id="btn-save">转账</button>
            <button id="btn-cancel">返回</button>
        </div>
        
        <spring:errors path="errors" />
        
        <c:url value='/account/confirmTransfer' var='url' />
        <spring:form id="form" method="post" action="${url}" modelAttribute="acntForm" autocomplete="off" >
        <input type="hidden" name="acntOid" value="${acntForm.acntOid}" />
        <input type="hidden" name="ownerOid" value="${acntForm.ownerOid}" />
        <input type="hidden" name="acntType" value="${acntForm.acntType}" />
        <input type="hidden" name="acntDesc" value="${acntForm.acntDesc}" />
        <input type="hidden" name="quota" value="${acntForm.quota}" />
        <input type="hidden" name="balance" value="${acntForm.balance}" />
        <input type="hidden" name="debt" value="${acntForm.debt}" />
        
        <div id="errorArea" class="ui-widget" style="margin-bottom:5px;display:none">
            <div class="ui-state-error ui-corner-all" style="margin-right: 400px; padding: 5px 30px;" >
                <span class="ui-icon ui-icon-alert" style="float: left; margin-top:5px; "></span>
                <span id="errorMsg" style="font-size: 70%;"><spring:errors path="*" /></span>
            </div>
            
            <div style="clear:both;" ></div>
        </div>
        
        <div class="content-header ui-widget-header">
            账户<span style="font-size: 80%;"> - 转账</span>
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">源账户</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.acntHumanDesc }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
	                <div class="label">目标账户</div>
	                
	                <div class="input">
	                    <spring:input data-validation-engine="validate[required]" id="targetAcntHumanDesc" path="target.acntHumanDesc" class="inputbox" readonly="true" onClick="javascript:selectAccount();" />
	                    <input type="hidden" id="targetAcntOid" name="target.acntOid" value="<c:out value='${acntForm.target.acntOid }' />" />
	                </div>
	                
	                <div style="clear:both;" ></div>
	            </div>
	            
	            <div class="newline-wrapper ui-widget-content">
                    <div class="label">转账金额</div>
                    
                    <div class="input">
                        <spring:input data-validation-engine="validate[required]" path="payment" class="inputbox" onBlur="javascript:checkAmount(this);" maxlength="11" />
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
            	
            	$ ("#btn-cancel").click(function(){
                    window.location.href = "<c:url value='/account/view' />?acntOid=<c:out value='${acntForm.acntOid}' />";
                });
                
                $ ("#btn-save").click(function(){
                    //提交表单时，使用使用jquery validation engine进行前端基本验证。
                    $("#form").validationEngine();
                    if ($ ("#form").validationEngine('validate')) {
                        $ ("#form").submit();
                    }
                });
                
                $ (".button-area button").button();
                
                
                
                
                
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
                    $( "#targetAcntHumanDesc").val(acntHumanDesc);
                    $( "#targetAcntOid").val(acntOid);
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