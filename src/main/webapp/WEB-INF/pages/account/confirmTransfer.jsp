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
                    <h3 class="panel-title">转账确认</h3>
                </div>
                <div>&nbsp;</div>
                
                <div class="form-horizontal">
                    <div class="form-group">
                        <label for=srcAccountInput class="col-xs-4 col-sm-2 control-label">账户所有人</label>
                        <div class="col-xs-7 col-sm-4">
                            <div class="form-control" style="BORDER-STYLE: none;" id="srcAccountInput">${acntForm.acntHumanDesc }</div>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for=targetAccountInput class="col-xs-4 col-sm-2 control-label">目标账户</label>
                        <div class="col-xs-7 col-sm-4">
                            <div class="form-control" style="BORDER-STYLE: none;" id="targetAccountInput">${acntForm.target.acntHumanDesc }</div>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for=paymentInput class="col-xs-4 col-sm-2 control-label">转账金额</label>
                        <div class="col-xs-7 col-sm-4">
                            <div class="form-control" style="BORDER-STYLE: none;" id="paymentInput">${acntForm.payment }</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
                $ ("#btn-cancel").click(function(){
                    window.location.href = "<c:url value='/account/initTransfer' />";
                });
                
                $ ("#btn-save").click(function(){
                    window.location.href = "<c:url value='/account/saveTransfer' />";
                });
            });
        </script>
    </body>
</html>
