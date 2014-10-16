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
            账户<span style="font-size: 80%;"> - 账户确认</span>
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">源账户</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.acntHumanDesc }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">目标账户</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.target.acntHumanDesc }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">转账金额</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${acntForm.payment }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            </div>
            
        </div>
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui.min.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
            	$ ("#btn-cancel").click(function(){
                    window.location.href = "<c:url value='/account/initTransfer' />";
                });
                
                $ ("#btn-save").click(function(){
                    window.location.href = "<c:url value='/account/saveTransfer' />";
                });
                
                $ (".button-area button").button();
            });
        </script>
    
    </body>
</html>