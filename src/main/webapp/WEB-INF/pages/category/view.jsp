<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <body>
        <div class="button-area">
            <button id="btn-back">返回</button>
            <button id="btn-edit">修改</button>
            
            <c:if test="${ removable && catForm.isLeaf }" >
                <button id="btn-delete">删除</button>
            </c:if>
        </div>
        
        <div class="content-header ui-widget-header">
            类别<span style="font-size: 80%;"> - 查看页面</span>
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">描述</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${catForm.categoryDesc }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">月度预算</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${catForm.monthlyBudget }</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">级别</div>
                    
                    <div class="input">
                        <div class="confirmed-text">${catForm.categoryLevel + 1}</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">是否页子级别</div>
                    
                    <div class="input">
                        <div class="confirmed-text">
                            <c:choose>
                                <c:when test="${catForm.isLeaf }">是</c:when>
                                <c:otherwise>否</c:otherwise>
                            </c:choose>
                        </div>
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
                    window.location.href = "<c:url value='/category/summary' />";
                });
                
                $ ("#btn-edit").click(function(){
                    window.location.href = "<c:url value='/category/initEdit' />?categoryOid=<c:out value='${catForm.categoryOid}' />";
                });
                
                if ($ ("#btn-delete").length > 0) {
                	$ ("#btn-delete").click(function(){
                        $ ( "#delete-confirm-dialog" ).dialog( "open" );
                    });
                }
                    
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
                            window.location.href = "<c:url value='/category/delete' />?categoryOid=<c:out value='${catForm.categoryOid}' />";
                        }
                    }
                });
            });
        </script>
    </body>
</html>
