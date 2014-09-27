<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<html>
    <head>
        <title>This is the title.</title>
    </head>
    
    <body>
        <div class="button-area">
            <button id="btn-add">新建</button>
        </div>
        
        <div class="content-header ui-widget-header">
            消费
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                Apache Maven is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information.
                <br/><br/><br/>
                If you think that Maven could help your project, you can find out more information about in the "About Maven" section of the navigation. This includes an in-depth description of what Maven is, a list of some of its main features, and a set of frequently asked questions about what Maven is.
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
            	
                $ ("#btn-add").click(function(){
                	window.location.href = "<c:url value='/consumption/initAdd' />";
                });
            	
            	$("#gridList").jqGrid({
                    url: "<c:url value='/consumption/listOfSummary' />",
                    jsonReader: {id: "cpnOid"},
                    colNames: ["日期", "消费类型", "总金额", "登记人", "状态", ""],
                    colModel: [
                        { sortable: false, name: "cpnTime", width: 155, align: "center", formatter: 'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i'}},
                        { sortable: false, name: "cpnType", width: 190,align: "center" },
                        { sortable: false, name: "amount", width: 190,align: "right", formatter:"currency", formatoptions:{thousandsSeparator: ",", prefix: "¥", suffix:"  "}},
                        { sortable: false, name: "baseObject.createBy", width: 180, align: "center" },
                        { sortable: false, name: "confirmed", width: 100, align: "center", formatter:function(cellvalue){return cellvalue?"确认":"初始";}},
                        { sortable: false, align: "center", formatter:function (cellvalue, options, rowObject){
                        	var url = '<c:url value='/consumption/view' />' + '?cpnOid=' + rowObject.cpnOid;
                        	
                        	var href = 'javascript:window.location.href="' + url + '"';
                        	
                        	return "<a href='" + href + "'>查看</a>";
                        }},
                    ],
                    
                    subGrid: true,
                    subGridRowExpanded: function(subgrid_id, row_id) {
                        var subgrid_table_id;
                        subgrid_table_id = subgrid_id+"_t";
                        $("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table>");
                        
                        $("#"+subgrid_table_id).jqGrid({
                            url: "<c:url value='/consumption/listOfItemSummary' />" + "?cpnOid=" + row_id,
                            jsonReader: {id: "itemOid"},
                            colNames: ['描述','类别','金额','消费人'],
                            colModel: [
                                {sortable: false, name:"itemDesc", width:80, align: "left"},
                                {sortable: false, name:"categoryFullDesc", width:130, align: "center"},
                                {sortable: false, name:"amount", width:80, align:"right", formatter:"currency", formatoptions:{thousandsSeparator: ",", prefix: "¥"}},
                                {sortable: false, name:"userName", width:80, align:"center"}
                            ],
                            pager:""
                        });
                        
                    }
                });
            });
        </script>
    </body>
</html>