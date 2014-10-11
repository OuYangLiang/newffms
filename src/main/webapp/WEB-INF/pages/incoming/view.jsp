<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <body>
        <div class="button-area">
            <button id="btn-back">返回</button>
            <c:if test="${!incomingForm.confirmed }" >
                <button id="btn-edit">修改</button>
                <button id="btn-delete">删除</button>
                <button id="btn-confirm">确认</button>
            </c:if>
            
            <c:if test="${incomingForm.confirmed }" >
                <button id="btn-rollback">撤销</button>
            </c:if>
        </div>
        
        <div class="content-header ui-widget-header">
            收入<span style="font-size: 80%;"> - 查看页面</span>
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">收入人</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${incomingForm.owner }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">收入类型</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${incomingForm.incomingType.desc }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">说明</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${incomingForm.incomingDesc }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">日期</div>
                    
                    <div class="input">
                        <div class="confirmed-text"><fmt:formatDate value='${incomingForm.incomingDate }' pattern="yyyy-MM-dd" /></div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">金额</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${incomingForm.amount }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">目标账户</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${incomingForm.acntHumanDesc }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
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
                    window.location.href = "<c:url value='/incoming/summary?keepSp=Y' />";
                });
                
                <c:if test="${!incomingForm.confirmed }" >
                    $ ("#btn-edit").click(function(){
                        window.location.href = "<c:url value='/incoming/initEdit' />?incomingOid=<c:out value='${incomingForm.incomingOid}' />";
                    });
                    
                    $ ("#btn-delete").click(function(){
                        $ ( "#delete-confirm-dialog" ).dialog( "open" );
                    });
                    
                    $ ("#btn-confirm").click(function(){
                        window.location.href = "<c:url value='/incoming/confirm' />?incomingOid=<c:out value='${incomingForm.incomingOid}' />";
                    });
                </c:if>
                
                <c:if test="${incomingForm.confirmed }" >
                    $ ("#btn-rollback").click(function(){
                        window.location.href = "<c:url value='/incoming/rollback' />?incomingOid=<c:out value='${incomingForm.incomingOid}' />";
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
                            window.location.href = "<c:url value='/incoming/delete' />?incomingOid=<c:out value='${incomingForm.incomingOid}' />";
                        }
                    }
                });
            });
        </script>
    </body>
</html>
