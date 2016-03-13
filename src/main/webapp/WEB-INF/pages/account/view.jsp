<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
    <head>
        <link href="<c:url value='/bootstrap-table-1.10.1/bootstrap-table.min.css' />" rel="stylesheet">
    </head>

    <body>
        <div class="container">
            <div class="page-header">
                <h1>账户</h1>
            </div>
            
            <div style="padding-left: 20px; padding-bottom: 20px;">
                <button type="button" class="btn btn-default" id="btn-back">
                    <i class="glyphicon glyphicon-arrow-left"></i>
                </button>
            
                <button type="button" class="btn btn-default" id="btn-edit">
                    <i class="glyphicon glyphicon-edit"></i>
                </button>
                
                <c:if test="${acntForm.balance > 0 }" >
	                <button type="button" class="btn btn-default" id="btn-transfer">
	                    <i class="glyphicon glyphicon-transfer"></i>
	                </button>
	            </c:if>
            
	            <c:if test="${isAccountSafeToRemove }" >
	                <button type="button" class="btn btn-default" id="btn-delete" data-toggle="modal" data-target="#deleteModal">
                        <i class="glyphicon glyphicon-remove"></i>
                    </button>
	            </c:if>
	            
	            <c:if test="${acntForm.balance > 0 }" >
                    <button type="button" class="btn btn-default" id="btn-viewDetail">
                        <i class="glyphicon glyphicon-list"></i>
                    </button>
                </c:if>
            </div>
            
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">账户明细</h3>
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
            
            <div class="panel panel-primary" id="detailArea" style="display:none;">
                <div class="panel-heading">
                    <h3 class="panel-title">账户流水</h3>
                </div>
                
                <div class="panel-body">
                    <table id="data-table" data-toggle="table"
                        data-pagination="true"
                        data-side-pagination="server"
                        data-sort-name="ADT_TIME"
                        data-sort-order="desc"
                        data-page-size="10"
                        data-page-number="1"
                        data-show-toggle="true"
                        data-show-columns="true"
                        data-silent-sort="false"
                        data-row-style="rowStyle">
                        <thead>
                            <tr>
                                <th data-field="adtDesc">描述</th>
                                <th data-field="adtType">类型</th>
                                <th data-field="amount" data-align="right" data-formatter="amtFormatter">变化量</th>
                                <th data-field="adtTime">发生时间</th>
                                <th data-field="baseObject.createBy">操作人</th>
                                <th data-field="baseObject.createTime">操作时间</th>
                            </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
        
        <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="deleteModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title" id="deleteModalLabel">警告</h4>
                    </div>
                    <div class="modal-body">
                        <div class="container lead">
                            确定要删除吗?
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                        <button type="button" class="btn btn-danger" id="btn-delete-confirm" data-dismiss="modal">删除</button>
                    </div>
                </div>
            </div>
        </div>

        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-table-1.10.1/bootstrap-table.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-table-1.10.1/locale/bootstrap-table-zh-CN.min.js' />" charset="utf-8"></script>
        
        <script>
	        function rowStyle(row, index) {
	            var classes = ['active', 'success', 'info', 'warning', 'danger'];
	            
	            if (index % 2 === 0 && index / 2 < classes.length) {
	                return {
	                    classes: classes[index / 2]
	                };
	            }
	            return {};
	        }
	        
	        function amtFormatter(value) {
	        	return "¥" + parseFloat(value).toFixed(2);
	        }
        
            $( document ).ready(function() {
            	$ ("#btn-back").click(function(){
                    window.location.href = "<c:url value='/account/summary?keepSp=Y' />";
                });
            	
            	$ ("#btn-edit").click(function(){
                    window.location.href = "<c:url value='/account/initEdit' />?acntOid=<c:out value='${acntForm.acntOid}' />";
                });
            	
            	if ($ ("#btn-transfer").length > 0) {
                    $ ("#btn-transfer").click(function(){
                        window.location.href = "<c:url value='/account/initTransfer' />?acntOid=<c:out value='${acntForm.acntOid}' />";
                    });
                }
            	
            	if ($ ("#btn-delete").length > 0) {
            		$ ("#btn-delete-confirm").click(function(){
                        window.location.href = "<c:url value='/account/delete' />?acntOid=<c:out value='${acntForm.acntOid}' />";
                    });
                }
            	
            	var gridLoaded = false;
            	$ ("#btn-viewDetail").click(function(){
            		if (gridLoaded) {
                        return;
                    }
            		
            		gridLoaded = true;
            		$("#detailArea").attr("style", "display:''");
            		
            		$ ("#data-table").bootstrapTable('refresh', {'url': "<c:url value='/account/listOfItemSummary?acntOid=${acntForm.acntOid}' />"});
            	});
            });
        </script>
    </body>
</html>
