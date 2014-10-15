<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <body>
        <div class="button-area">
            <button id="btn-back">返回</button>
            <button id="btn-edit">修改</button>
            
            <c:if test="${isAccountSafeToRemove }" >
                <button id="btn-delete">删除</button>
            </c:if>
        </div>
        
        <div class="content-header ui-widget-header">
            账户<span style="font-size: 80%;"> - 查看页面</span>
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
                        <div class="confirmed-text">${acntForm.acntDesc }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">初始可用额度</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.balance }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <c:if test="${acntForm.acntType == \"Creditcard\" }" >
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">限定额度</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.quota }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">初始欠款额度</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.debt }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                </c:if>
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
                    window.location.href = "<c:url value='/account/summary?keepSp=Y' />";
                });
                
                $ ("#btn-edit").click(function(){
                    window.location.href = "<c:url value='/account/initEdit' />?acntOid=<c:out value='${acntForm.acntOid}' />";
                });
                
                <c:if test="${isAccountSafeToRemove }" >
	                $ ("#btn-delete").click(function(){
	                    $ ( "#delete-confirm-dialog" ).dialog( "open" );
	                });
	                
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
	                            window.location.href = "<c:url value='/account/delete' />?acntOid=<c:out value='${acntForm.acntOid}' />";
	                        }
	                    }
	                });
                </c:if>
            });
        </script>
    </body>
</html>
