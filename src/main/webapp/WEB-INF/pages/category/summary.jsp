<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <body>
        <div class="button-area">
            <button id="btn-add">新建</button>
        </div>
        
        <div class="content-header ui-widget-header">
            消费类别
        </div>
        
        <div class="contentWrapper">
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
        <script src="<c:url value='/js/jqGrid-setting.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
            	$ (".button-area button").button();
            	
                $ ("#btn-add").click(function(){
                	window.location.href = "<c:url value='/category/initAdd' />";
                });
            	
            	$("#gridList").jqGrid({
                    url: "<c:url value='/category/ajaxGetAllCategories' />",
                    
                    jsonReader: {id: "categoryOidDesc"},
                    colNames: ["描述", "级别", "月度预算", "页子类别", ""],
                    colModel: [
                        {sortable: false, name: "categoryDesc", width: 100, align: "center"},
                        {sortable: false, name: "categoryLevel", width: 100, align: "center", formatter:function (cellvalue, options, rowObject){
                        	return (cellvalue+1) + "级";
                        }},
                        {sortable: false, name: "monthlyBudget", width: 100, align: "right", formatter:"currency", formatoptions:{thousandsSeparator: ",", prefix: "¥", suffix:"  "}},
                        {sortable: false, name: "isLeaf", width: 100, align: "center", formatter:function (cellvalue, options, rowObject){
                        	return cellvalue ? "是" : "否";
                        }},
                        { sortable: false, align: "center", width: 100, formatter:function (cellvalue, options, rowObject){
                            var url = '<c:url value='/category/view' />' + '?categoryOid=' + rowObject.categoryOid;
                            
                            var href = 'javascript:window.location.href="' + url + '"';
                            
                            return "<a href='" + href + "'>查看</a>";
                        }}
                    ],
                    
                    treeGrid: true,
                    treeReader: {
                        level_field: "categoryLevel",
                        parent_id_field: "parentOidDesc",
                        leaf_field: "isLeaf"
                    },
                    ExpandColClick: true,
                    ExpandColumn: "categoryDesc",
                    treeIcons: {leaf:'ui-icon-circle-check'},
                    treeGridModel: "adjacency",
                    rownumbers: false,
                    autowidth: true
                });
            	
            });
        </script>
    </body>
</html>