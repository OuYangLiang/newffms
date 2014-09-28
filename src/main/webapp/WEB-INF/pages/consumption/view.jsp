<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
    <head>
        <title>This is the title.</title>
        <link rel="stylesheet" href="<c:url value='/css/consumption.css' />" />
    </head>
    
    <body>
        <div class="button-area">
            <button id="btn-back">返回</button>
            <c:if test="${!cpnForm.consumption.confirmed }" >
	            <button id="btn-edit">修改</button>
	            <button id="btn-delete">删除</button>
	            <button id="btn-confirm">确认</button>
            </c:if>
            
            <c:if test="${cpnForm.consumption.confirmed }" >
                <button id="btn-rollback">撤销</button>
            </c:if>
        </div>
        
        <div class="content-header ui-widget-header">
            消费<span style="font-size: 80%;"> - 查看页面</span>
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">消费类型</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${cpnForm.consumption.cpnTypeDesc }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">时间</div>
                    
                    <div class="input">
                        <div class="confirmed-text"><fmt:formatDate value="${cpnForm.consumption.cpnTime}" pattern="yyyy-MM-dd HH:mm" /></div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            </div>
            
            <div class="content-title ui-widget-header">
                明细
            </div>
            
            <div>
                <div id="items">
                    <c:forEach var="item" items="${ cpnForm.cpnItems }" varStatus="status" >
                    <fieldset>
                        <legend >清单${status.index + 1 }</legend>
                        
                        <div class="label">说明</div>
                        <div class="input" style="width: 100px;">
                            <div class="confirmed-text">${item.itemDesc }</div>
                        </div>
                        
                        <div class="label">类别</div>
                        <div class="input" style="width: 300px;">
                            <div class="confirmed-text">${item.categoryFullDesc }</div>
                        </div>
                        
                        <div class="label">金额</div>
                        <div class="input" style="width: 80px;">
                            <div class="confirmed-text">${item.amount }</div>
                        </div>
                        
                        <div class="label">消费人</div>
                        <div class="input">
                            <div class="confirmed-text">${item.userName }</div>
                        </div>
                        
                        <div style="clear:both;" ></div>
                    </fieldset>
                    </c:forEach>
                </div>
                
            </div>
            
            <div class="content-title ui-widget-header">
                付款
            </div>
            
            <div>
                <div id="accounts">
                    <c:forEach var="item" items="${ cpnForm.accounts }" varStatus="status" >
                    <fieldset>
                        <legend>账户${status.index + 1 }</legend>
                        
                        <div class="label">类别</div>
                        <div class="input" style="width: 500px;">
                            <div class="confirmed-text">${item.acntHumanDesc }</div>
                        </div>
                        
                        <div class="label" >支付金额</div>
                        <div class="input" ><div class="confirmed-text">${item.payment }</div></div>
                        
                        <div style="clear:both;" ></div>
                        
                    </fieldset>
                    </c:forEach>
                </div>
                
                <div style="text-align: right;padding-right: 50px;margin-bottom: 20px;">总金额: ${cpnForm.totalItemAmount }</div>
            </div>
            
        </div>
        
        <div id="delete-confirm-dialog" title="警告">
            确定要删除吗?
        </div>
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui.min.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
            	$ (".button-area button").button();
            	$ ("#btn-back").click(function(){
                    window.location.href = "<c:url value='/consumption/summary' />";
                });
            	
            	<c:if test="${!cpnForm.consumption.confirmed }" >
	            	$ ("#btn-edit").click(function(){
	                    window.location.href = "<c:url value='/consumption/initEdit' />?cpnOid=<c:out value='${cpnForm.consumption.cpnOid}' />";
	                });
	                
	                $ ("#btn-delete").click(function(){
	                	$ ( "#delete-confirm-dialog" ).dialog( "open" );
	                });
	                
	                $ ("#btn-confirm").click(function(){
	                    window.location.href = "<c:url value='/consumption/confirm' />?cpnOid=<c:out value='${cpnForm.consumption.cpnOid}' />";
	                });
                </c:if>
            	
                <c:if test="${cpnForm.consumption.confirmed }" >
	                $ ("#btn-rollback").click(function(){
	                    window.location.href = "<c:url value='/consumption/rollback' />?cpnOid=<c:out value='${cpnForm.consumption.cpnOid}' />";
	                });
                </c:if>
                
                $( "#delete-confirm-dialog" ).dialog( {
                    autoOpen: false,
                    modal: true,
                    show: {
                        effect: "blind",
                        duration: 300
                    },
                    hide: {
                        effect: "explode",
                        duration: 1000
                    },
                    buttons: {
                        "No": function(){
                            $( this ).dialog( "close" );
                        },
	                    "Yes": function(){
	                    	window.location.href = "<c:url value='/consumption/delete' />?cpnOid=<c:out value='${cpnForm.consumption.cpnOid}' />";
	                    }
                    }
                });
            });
        </script>
    </body>
</html>
