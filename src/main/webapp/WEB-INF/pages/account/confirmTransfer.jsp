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
                
                <div class="container lead">
                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-xs-5 col-md-2 text-right"><strong>源账户</strong></div>
                        <div class="col-xs-1">:</div>
                        <div class="col-md-6">${acntForm.acntHumanDesc }</div>
                    </div>
                    
                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-xs-5 col-md-2 text-right"><strong>目标账户</strong></div>
                        <div class="col-xs-1">:</div>
                        <div class="col-md-6">${acntForm.target.acntHumanDesc }</div>
                    </div>
                    
                    <div class="row" style="margin-bottom: 10px;">
                        <div class="col-xs-5 col-md-2 text-right"><strong>转账金额</strong></div>
                        <div class="col-xs-1">:</div>
                        <div class="col-md-6">${acntForm.payment }</div>
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
