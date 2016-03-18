<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
    <head>
    
    </head>

    <body>
        <div class="container">
            <div class="page-header">
                <h1>收入</h1>
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
                    <h3 class="panel-title">新建确认</h3>
                </div>
                <div>&nbsp;</div>
                
                <div class="form-horizontal">
                    <div class="form-group">
                        <label for="userNameInput" class="col-xs-4 col-sm-2 control-label">收入人</label>
                        <div class="col-xs-7 col-sm-4">
                            <div class="form-control" style="BORDER-STYLE: none;" id="userNameInput">${incomingForm.owner.userName }</div>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="incomingTypeInput" class="col-xs-4 col-sm-2 control-label">收入类型</label>
                        <div class="col-xs-7 col-sm-4">
                            <div class="form-control" style="BORDER-STYLE: none;" id=incomingTypeInput>${incomingForm.incomingType.desc }</div>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="incomingDescInput" class="col-xs-4 col-sm-2 control-label">说明</label>
                        <div class="col-xs-7 col-sm-4">
                            <div class="form-control" style="BORDER-STYLE: none;" id="incomingDescInput">${incomingForm.incomingDesc }</div>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="incomingDateInput" class="col-xs-4 col-sm-2 control-label">日期</label>
                        <div class="col-xs-7 col-sm-4">
                            <div class="form-control" style="BORDER-STYLE: none;" id="incomingDateInput"><fmt:formatDate value='${incomingForm.incomingDate }' pattern="yyyy-MM-dd" /></div>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="amountInput" class="col-xs-4 col-sm-2 control-label">金额</label>
                        <div class="col-xs-7 col-sm-4">
                            <div class="form-control" style="BORDER-STYLE: none;" id="amountInput">${incomingForm.amount }</div>
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label for="targetAccountInput" class="col-xs-4 col-sm-2 control-label">目标账户</label>
                        <div class="col-xs-7 col-sm-4">
                            <div class="form-control" style="BORDER-STYLE: none;" id="targetAccountInput">${incomingForm.targetAccount.acntHumanDesc }</div>
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
                    window.location.href = "<c:url value='/incoming/initAdd?back=true' />";
                });
                
                $ ("#btn-save").click(function(){
                    window.location.href = "<c:url value='/incoming/saveAdd' />";
                });
                
            });
        </script>
    </body>
</html>
