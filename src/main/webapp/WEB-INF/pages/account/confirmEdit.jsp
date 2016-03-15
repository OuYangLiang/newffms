<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
    <head>
    
    </head>

    <body>
        <div class="container">
            <div class="page-header">
                <h1>账户</h1>
            </div>
            
            <div style="padding-left: 20px; padding-bottom: 20px;">
                <button type="button" class="btn btn-default" id="btn-save">
                    <i class="glyphicon glyphicon-ok"></i>
                </button>
            
                <button type="button" class="btn btn-default" id="btn-cancel">
                    <i class="glyphicon glyphicon-arrow-left"></i>
                </button>
            </div>
            
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">修改确认</h3>
                </div>
                <div>&nbsp;</div>
                
                <div class="container lead">
                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-xs-5 col-md-2 text-right"><strong>账户所有人</strong></div>
                        <div class="col-xs-1">:</div>
                        <div class="col-md-4">${acntForm.owner.userName }</div>
                    </div>
                    
                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-xs-5 col-md-2 text-right"><strong>账户类型</strong></div>
                        <div class="col-xs-1">:</div>
                        <div class="col-md-4">${acntForm.acntType.desc }</div>
                    </div>
                    
                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-xs-5 col-md-2 text-right"><strong>描述</strong></div>
                        <div class="col-xs-1">:</div>
                        <div class="col-md-4">${acntForm.acntDesc }</div>
                    </div>
                    
                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-xs-5 col-md-2 text-right"><strong>初始可用额度</strong></div>
                        <div class="col-xs-1">:</div>
                        <div class="col-md-4">${acntForm.balance }</div>
                    </div>
                    
                    <c:if test="${acntForm.acntType == \"Creditcard\" }" >
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-xs-5 col-md-2 text-right"><strong>限定额度</strong></div>
                            <div class="col-xs-1">:</div>
                            <div class="col-md-4">${acntForm.quota }</div>
                        </div>
                            
                        <div class="row" style="margin-bottom: 10px;">
                            <div class="col-xs-5 col-md-2 text-right"><strong>初始欠款额度</strong></div>
                            <div class="col-xs-1">:</div>
                            <div class="col-md-4">${acntForm.debt }</div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>

        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
            	$ ("#btn-cancel").click(function(){
                    window.location.href = "<c:url value='/account/initEdit?back=true' />";
                });
                
            	$ ("#btn-save").click(function(){
                    window.location.href = "<c:url value='/account/saveEdit' />";
                });
            });
        </script>
    </body>
</html>
