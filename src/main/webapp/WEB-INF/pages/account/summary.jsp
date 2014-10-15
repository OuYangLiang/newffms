<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <head>
        <title>This is the title.</title>
        <link rel="stylesheet" href="<c:url value='/css/validationEngine.jquery.css' />" />
    </head>
    
    <body>
        <div class="button-area">
            <button id="btn-add">新建</button>
        </div>
        
        <div class="content-header ui-widget-header">
            账户
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <form id="form" method="post" >
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">账户所有人</div>
                    
                    <div class="input">
                        <select name="ownerOid" class="selectbox" >
                            <option value ="">全部</option>
                            <c:forEach var="user" items="${ users }" varStatus="status">
							    <option value ="${user.userOid}" <c:if test='${user.userOid == SESSION_KEY_SEARCH_PARAM_ACCOUNT.ownerOid }' >selected="selected"</c:if>>${user.userName}</option>
                            </c:forEach>
                        </select>
                        
                        <span id="btn-query" style="margin-left:150px; margin-top:-20px;">查询</span>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                </form>
            </div>
            
            <div class="content-title ui-widget-header">
                查询结果
            </div>
            
            <div class="mainArea">
                <div>
                    <table id="gridList" ></table> 
                    <div id="gridListNav"></div>
                </div>
            </div>
        </div>
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/i18n/grid.locale-cn.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.jqGrid.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine-zh_CN.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jqGrid-setting.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/common.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
            	$ (".button-area button").button();
            	$( ".selectbox" ).selectmenu();
            	
                $ ("#btn-add").click(function(){
                	window.location.href = "<c:url value='/account/initAdd' />";
                });
            	
            	$("#gridList").jqGrid({
                    url: "<c:url value='/account/listOfSummary' />",
                    
                    page: "<c:url value='${SESSION_KEY_SEARCH_PARAM_ACCOUNT.requestPage}' />",
                    sortname: "<c:url value='${SESSION_KEY_SEARCH_PARAM_ACCOUNT.sortField}' />",
                    sortorder: "<c:url value='${SESSION_KEY_SEARCH_PARAM_ACCOUNT.sortDir}' />",
                    rowNum: "<c:url value='${SESSION_KEY_SEARCH_PARAM_ACCOUNT.sizePerPage}' />",
                    
                    jsonReader: {id: "acntOid"},
                    colNames: ["所有人", "类型", "描述", "可用额度", ""],
                    colModel: [
                        {sortable: false, name: "ownerUserName", width: 60, align: "center"},
                        {sortable: false, name: "acntType", width: 60, align: "center"},
                        {sortable: false, name: "acntDesc", width: 200, align: "left"},
                        {sortable: false, name: "balance", width: 100, align: "right", formatter:"currency", formatoptions:{thousandsSeparator: ",", prefix: "¥", suffix:"  "}},
                        { sortable: false, align: "center", formatter:function (cellvalue, options, rowObject){
                            var url = '<c:url value='/account/view' />' + '?acntOid=' + rowObject.acntOid;
                            
                            var href = 'javascript:window.location.href="' + url + '"';
                            
                            return "<a href='" + href + "'>查看</a>";
                        }}
                    ]
                });
            	
            	$ ("#btn-query").button();
                $ ("#btn-query").click(function(){
                    $.ajax({
                    	cache: false,
                    	url: "<c:url value='/account/search' />",
                    	type: "POST",
                    	async: true,
                    	data: $('#form').serialize(),
                    	success: function() {
                    		$("#gridList").trigger("reloadGrid");
                    	}
                    });
                });
            });
        </script>
    </body>
</html>