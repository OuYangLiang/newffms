<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <body>
        <div class="button-area">
            <button id="btn-save">确认</button>
            <button id="btn-cancel">返回</button>
        </div>
        
        <div class="content-header ui-widget-header">
            账户<span style="font-size: 80%;"> - 新建确认</span>
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
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui.min.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
            	$ ("#btn-cancel").click(function(){
                    window.location.href = "<c:url value='/account/initAdd' />";
                });
                
                $ ("#btn-save").click(function(){
                    window.location.href = "<c:url value='/account/saveAdd' />";
                });
                
                $ (".button-area button").button();
            });
        </script>
    
    </body>
</html>