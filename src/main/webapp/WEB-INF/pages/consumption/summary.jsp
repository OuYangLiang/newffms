<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <head>
        <link rel="stylesheet" href="<c:url value='/css/validationEngine.jquery.css' />" />
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
                <form id="form" method="post" autocomplete="off" >
                <div class="newline-wrapper ui-widget-content" >
                    <div class="label">时间</div>
                
                    <div class="input" >
                        <span>起始日期</span>
                        <input style="width: 100px;" value="<fmt:formatDate value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.consumption.cpnTimeFrom }' pattern="yyyy-MM-dd" />" type="text" name="cpnTimeFrom" id="start" class="inputbox" readonly="true" data-validation-engine="validate[required]" />
                        
                        <span style="margin-left:50px;">结束日期</span>
                        <input style="width: 100px;" value="<fmt:formatDate value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.consumption.cpnTimeTo }' pattern="yyyy-MM-dd" />" type="text" name="cpnTimeTo" id="end" class="inputbox" readonly="true" data-validation-engine="validate[required]" />
                        
                        <span id="btn-query" style="margin-left:20px; margin-top:-5px;">查询</span>
                        
                        <div style="clear:both;" ></div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">状态</div>
                    
                    <div class="input" >
                        <select name="confirmed" class="selectbox" >
                            <option value ="">全部</option>
                            <option value ="true" <c:if test='${null != SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.consumption.confirmed && SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.consumption.confirmed }' >selected="selected"</c:if>>确认</option>
                            <option value ="false" <c:if test='${null != SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.consumption.confirmed && !SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.consumption.confirmed }' >selected="selected"</c:if>>初始</option>
                        </select>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content">
                    <div class="label">描述</div>
                        
                    <div class="input" >
                        <input value="<c:out value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.itemDesc }' />" type="text" name="itemDesc" class="inputbox" />
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
                	window.location.href = "<c:url value='/consumption/initAdd' />";
                });
            	
            	/* $("#gridList").jqGrid({
                    url: "<c:url value='/consumption/listOfSummary' />",
                    
                    page: "<c:url value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTION.requestPage}' />",
                    sortname: "<c:url value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTION.sortField}' />",
                    sortorder: "<c:url value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTION.sortDir}' />",
                    rowNum: "<c:url value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTION.sizePerPage}' />",
                    
                    jsonReader: {id: "cpnOid"},
                    colNames: ["日期", "消费类型", "总金额", "登记人", "状态", ""],
                    colModel: [
                        { sortable: true, index:"CPN_TIME", name: "cpnTime", width: 155, align: "center", formatter: 'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i'}},
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
                }); */
                
                $("#gridList").jqGrid({
                    url: "<c:url value='/consumption/listOfSummary' />",
                    
                    page: "<c:url value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.requestPage}' />",
                    sortname: "<c:url value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.sortField}' />",
                    sortorder: "<c:url value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.sortDir}' />",
                    rowNum: "<c:url value='${SESSION_KEY_SEARCH_PARAM_CONSUMPTIONITEM.sizePerPage}' />",
                    
                    jsonReader: {id: "itemOid"},
                    colNames: ["时间", "描述", "类别", "消费人", "消费方式", "行金额", "总金额", "登记人", "状态", ""],
                    colModel: [
{ sortable: true , name: "consumption.cpnTime", index:"CPN_TIME", width: 130, align: "center", formatter: 'date', formatoptions: {srcformat: 'Y-m-d H:i:s', newformat: 'Y-m-d H:i'}},
                        { sortable: false, name: "itemDesc", width: 100, align: "left" },
                        { sortable: true,  name: "categoryFullDesc", index:"I.CATEGORY_OID", width: 100, align: "left" },
                        { sortable: false, name: "userName", width: 60, align: "center" },
                        { sortable: false, name: "consumption.cpnType", width: 50, align: "center" },
                        { sortable: false, name: "amount", width: 70, align: "right", formatter:"currency", formatoptions:{thousandsSeparator: ",", prefix: "¥", suffix:"  "}},
                        { sortable: false, name: "consumption.amount", width: 70, align: "right", formatter:"currency", formatoptions:{thousandsSeparator: ",", prefix: "¥", suffix:"  "}},
                        { sortable: false, name: "consumption.baseObject.createBy", width: 50, align: "center" },
                        { sortable: false, name: "consumption.confirmed", width: 50, align: "center", formatter:function(cellvalue){return cellvalue?"确认":"初始";}},
                        { sortable: false, align: "center", width: 50, formatter:function (cellvalue, options, rowObject){
                            var url = '<c:url value='/consumption/view' />' + '?cpnOid=' + rowObject.cpnOid;
                            
                            var href = 'javascript:window.location.href="' + url + '"';
                            
                            return "<a href='" + href + "'>查看</a>";
                        }},
                    ]
                });
            	
            	$ ("#btn-query").button();
            	
            	$( "#start" ).datepicker({
                    dateFormat: "yy-mm-dd",
                    showAnim: "slide",
                    changeYear: true
                });
                
                $( "#end" ).datepicker({
                    dateFormat: "yy-mm-dd",
                    showAnim: "slide",
                    changeYear: true
                });
            	
                $ ("#btn-query").click(function(){
                    $.ajax({
                    	cache: false,
                    	url: "<c:url value='/consumption/search' />",
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