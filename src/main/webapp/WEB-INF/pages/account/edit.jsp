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
        
        <c:url value='/account/confirmEdit' var='url' />
        <spring:form id="form" method="post" action="${url}" modelAttribute="acntForm" >
        <input type="hidden" name="acntOid" value="${acntForm.acntOid}" />
        <input type="hidden" name="ownerOid" value="${acntForm.ownerOid}" />
        <input type="hidden" name="acntType" value="${acntForm.acntType}" />
        <div id="errorArea" class="ui-widget" style="margin-bottom:5px;display:none">
            <div class="ui-state-error ui-corner-all" style="margin-right: 400px; padding: 5px 30px;" >
                <span class="ui-icon ui-icon-alert" style="float: left; margin-top:5px; "></span>
                <span id="errorMsg" style="font-size: 70%;"><spring:errors path="*" /></span>
            </div>
            
            <div style="clear:both;" ></div>
        </div>
        
        <div class="content-header ui-widget-header">
            账户<span style="font-size: 80%;"> - 修改</span>
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">账户所有人</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.ownerUserName }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">账户类型</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.acntType.desc }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">描述</div>
                    
                    <div class="input">
                        <spring:input data-validation-engine="validate[required]" path="acntDesc" class="inputbox" maxlength="30" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">初始可用额度</div>
                    
                    <div class="input">
                        <spring:input data-validation-engine="validate[required]" path="balance" class="inputbox" onBlur="javascript:checkAmount(this);" maxlength="7" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content" id="quota" <c:if test="${acntForm.acntType != \"Creditcard\" }" >style="display:none;"</c:if> >
                    <div class="label">限定额度</div>
                    
                    <div class="input">
                        <spring:input id="quotaInput" path="quota" class="inputbox" onBlur="javascript:checkAmount(this);" maxlength="7" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content" id="debt" <c:if test="${acntForm.acntType != \"Creditcard\" }" >style="display:none;"</c:if> >
                    <div class="label">初始欠款额度</div>
                    
                    <div class="input">
                        <spring:input id="debtInput" path="debt" class="inputbox" onBlur="javascript:checkAmount(this);" maxlength="7" />
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
            </div>
            
        </div>
        
        </spring:form>
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine-zh_CN.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/common.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
            	if ('<c:out value="${validation}" />' === 'false') {
                    $("#errorArea").css("display", "");
                }
            	
            	$ (".button-area button").button();
                
                
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
                
                $( ".selectbox" ).selectmenu({
                	change: function() {
                		if ('Creditcard' === $(this).val()) {
                			$("#quota").attr("style", "display:''");
                			$("#debt").attr("style", "display:''");
                			
                		} else {
                			$("#quota").attr("style", "display:none;");
                            $("#debt").attr("style", "display:none;");
                            
                            $("#quotaInput").val('');
                            $("#debtInput").val('');
                		}
                	}
                });
            });
        </script>
    
    </body>
</html>