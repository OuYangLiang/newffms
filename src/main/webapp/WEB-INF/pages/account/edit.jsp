<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
    <head>
        <link rel="stylesheet" href="<c:url value='/css/validationEngine.jquery.css' />" />
    </head>

    <body>
        <div class="container">
            <div class="page-header">
                <h1>账户</h1>
            </div>
            
            <spring:errors path="errors" />
            
            <div style="padding-left: 20px; padding-bottom: 20px;">
                <button type="button" class="btn btn-default" id="btn-save">
                    <i class="glyphicon glyphicon-ok"></i>
                </button>
            
                <button type="button" class="btn btn-default" id="btn-cancel">
                    <i class="glyphicon glyphicon-remove"></i>
                </button>
            </div>
            
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">修改</h3>
                </div>
                <div>&nbsp;</div>
                
                <c:url value='/account/confirmEdit' var='url' />
                <spring:form id="form" class="form-horizontal" method="post" action="${url}" modelAttribute="acntForm" autocomplete="off" >
                    <input type="hidden" name="acntOid" value="${acntForm.acntOid}" />
			        <input type="hidden" name="ownerOid" value="${acntForm.ownerOid}" />
			        <input type="hidden" name="acntType" value="${acntForm.acntType}" />
                
                    <div class="row" id="errorArea" style="display:none">
                        <div class="col-md-1"></div>
                        <div class="col-md-10">
                            <div class="alert alert-danger" role="alert" >
                                <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                                <span class="sr-only">Error:</span>
                                <spring:errors path="*" />
                            </div>
                        </div>
                    </div>
                
                    <div class="form-group">
                        <label for="userNameInput" class="col-md-2 control-label">账户所有人</label>
                        <div class="col-md-4">
                            <div class="form-control" style="BORDER-STYLE: none;" id="userNameInput">${acntForm.owner.userName }</div>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="acntTypeInput" class="col-md-2 control-label">账户类型</label>
                        <div class="col-md-4">
                            <div class="form-control" style="BORDER-STYLE: none;" id=acntTypeInput>${acntForm.acntType.desc }</div>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="acntDescInput" class="col-md-2 control-label">描述</label>
                        <div class="col-md-4">
                            <spring:input data-validation-engine="validate[required]" path="acntDesc" class="form-control" maxlength="30" id="acntDescInput" />
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="balanceInput" class="col-md-2 control-label">初始可用额度</label>
                        <div class="col-md-4">
                            <spring:input data-validation-engine="validate[required]" path="balance" class="form-control" onBlur="javascript:checkAmount(this);" maxlength="11" id="balanceInput"/>
                        </div>
                    </div>
                    
                    <div class="form-group" id="quota" <c:if test="${acntForm.acntType != \"Creditcard\" }" >style="display:none;"</c:if>>
                        <label for="quotaInput" class="col-md-2 control-label">限定额度</label>
                        <div class="col-md-4">
                            <spring:input path="quota" class="form-control" onBlur="javascript:checkAmount(this);" maxlength="11" id="quotaInput"/>
                        </div>
                    </div>
                    
                    <div class="form-group" id="debt" <c:if test="${acntForm.acntType != \"Creditcard\" }" >style="display:none;"</c:if>>
                        <label for="debtInput" class="col-md-2 control-label">初始欠款额度</label>
                        <div class="col-md-4">
                            <spring:input path="debt" class="form-control" onBlur="javascript:checkAmount(this);" maxlength="11" id="debtInput"/>
                        </div>
                    </div>
                    
                </spring:form>
            </div>
        </div>

        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine-zh_CN.js' />" charset="utf-8"></script>
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
                
            });
        </script>
    </body>
</html>
