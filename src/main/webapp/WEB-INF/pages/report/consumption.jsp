<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <head>
        <title>This is the title.</title>
        <link rel="stylesheet" href="<c:url value='/css/validationEngine.jquery.css' />" />
    </head>

    <body>
        <div class="content-header ui-widget-header">
            消费情况查询
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content" >
                    <div class="label">时间</div>
                
                    <div class="input" >
                    
                        <div style="float: left;width: 100px;"><input type="radio" name="queryMethod" id="r1" class="ratio" checked="true">当前月</input></div>
                        <div style="float: left;width: 100px;"><input type="radio" name="queryMethod" id="r2" class="ratio" >前一月</input></div>
                        <div style="float: left;width: 100px;"><input type="radio" name="queryMethod" id="r3" class="ratio" >指定日期</input></div>
                        <div style="float: left;width: 100px;"><input type="checkbox" id="r4" class="checkbox">排除类别</input></div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            
                <div class="newline-wrapper ui-widget-content" style="display:none;" id="dateArea">
                    <div class="label">日期范围</div>
                
                    <div class="input" >
                        <form id="form" method="post">
                        <span >起始日期</span>
                        <input style="width: 100px;" type="text" name="start" id="start" class="inputbox" readonly="true" data-validation-engine="validate[required]" />
                        
                        <span style="margin-left:30px;">结束日期</span>
                        <input style="width: 100px;" type="text" name="end" id="end" class="inputbox" readonly="true" data-validation-engine="validate[required]" />
                        
                        <span id="btn-query" style="margin-left:20px; margin-top:-5px;">查询</span>
                        </form>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
                
                <div class="newline-wrapper ui-widget-content" style="display:none;" id="categoryArea">
                    <div class="label">排除类别</div>
                    
                    <div class="input">
                        <c:forEach var="item" items="${ rootCategories }" varStatus="status" >
                            <div style="float: left;width: 100px;">
                                <input type="checkbox" id="category${status.index }" value="${item.categoryOid }" onclick="javascript:doQuery();" class="checkbox" ><c:out value="${ item.categoryDesc }" /></input>
                            </div>
                            
                            <c:if test="${ (status.index + 1) % 4 == 0 && status.index != 0}" ><br/><div style="clear:both;" ></div></c:if>
                        </c:forEach>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            </div>
        
            <div class="content-title ui-widget-header">
                消费情况柱状图
            </div>
        
            <div class="mainArea">
                <div id="container" style="padding: 0 20px" ></div>
            </div>
            
            <div class="content-title ui-widget-header">
                消费情况比例图
            </div>
        
            <div class="mainArea" style="width: 50%; float: left; padding: 0 0 0 0;">
                <div>
                    <div id="container2" style="padding: 0 20px" ></div>
                </div>
            </div>
            <div class="mainArea" style="width: 50%; float: right; padding: 0 0 0 0;">
                <div>
                    <div id="container3" style="padding: 0 20px" ></div>
                </div>
            </div>
            <div style="clear:both;" ></div>
            
        </div>
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine-zh_CN.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/highcharts.src.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/drilldown.src.js' />" charset="utf-8"></script>
        

        <script>
            $( document ).ready(function() {
            	$ ("#btn-query").button();
            	
            	$( "#start" ).datepicker({
                    dateFormat: "yy-mm-dd",
                    showAnim: "slide"
                });
            	
            	$( "#end" ).datepicker({
                    dateFormat: "yy-mm-dd",
                    showAnim: "slide"
                });
            	
            	var options = {
                    chart: {
                        type: "column"
                    },
                    title: {
                        text: 'Title'
                    },
                    xAxis: {
                    	type:'category'
                    },
                    tooltip: {
                        "pointFormat": "{series.name}: <b>{point.y:,.2f}</b>"
                    },
                    yAxis: {
                        title: {
                            text: ""
                        }
                    },
                    plotOptions: {
                        series: {
                            borderWidth: 0,
                            dataLabels: {
                                enabled: false,
                                "format": "{point.y:,.2f}"
                            }
                        }
                    },
                    dataLabels: {
                        "enabled": true,
                        "format": "<b>{point.name}</b>: {point.y:,.2f}"
                    },
                    series: [],
                    drilldown: {series:[]}
                };
            	
            	var options2 = {
           		    title: {
           	            text: 'title'
           	        },
           	        tooltip: {
           	            pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
           	        },
           	        plotOptions: {
           	            pie: {
           	                allowPointSelect: false,
           	                showInLegend: true,
           	                dataLabels: {
           	                    enabled: true,
           	                    format: '<b>{point.name}</b>: {point.percentage:.2f} %',
                            }
                        }
                    },
           	        series: [],
           	        drilldown: {series:[]}
           	    };
            	
            	var options3 = {
                        title: {
                            text: 'title'
                        },
                        tooltip: {
                            pointFormat: '{series.name}: <b>{point.percentage:.2f}%</b>'
                        },
                        plotOptions: {
                            pie: {
                                allowPointSelect: false,
                                showInLegend: true,
                                cursor: 'pointer',
                                dataLabels: {
                                    enabled: true,
                                    format: '<b>{point.name}</b>: {point.percentage:.2f} %',
                                }
                            }
                        },
                        series: [],
                        drilldown: {series:[]}
                    };
            	
            	var refresh = function(data) {
            		options.series = data.colRlt.series;
                    options.drilldown = {};
                    options.drilldown.series = data.colRlt.drilldown;
                    options.title.text = data.colRlt.title;
                    $('#container').highcharts(options);
                    
                    options2.series = data.pieRltOfAll.series;
                    options2.drilldown = {};
                    options2.drilldown.series = data.pieRltOfAll.drilldown;
                    options2.title.text = data.pieRltOfAll.title;
                    $('#container2').highcharts(options2);
                    
                    options3.series = data.pieRltOfUser.series;
                    options3.title.text = data.pieRltOfUser.title;
                    $('#container3').highcharts(options3);
            	};
            	
            	$.ajax({
                    cache: false,
                    url: '<c:url value="/report/consumptionDataSource" />',
                    type: "POST",
                    async: true,
                    success: function(data) {
                        refresh(data);
                    }
                });
            	
            	doQuery = function() {
            	    var selectedCategories = [];
                    
                    var i = 0;
                    
                    while ( $("#category" + i).length > 0) {
                        if ($("#category" + i).prop("checked")) {
                            selectedCategories.push($("#category" + i).val());
                        }
                        
                        i++;
                    }
                    
                    var queryStr;
                    if ($("#r1").prop("checked")) {
                        queryStr = "?queryMethod=1";
                    } else if ($("#r2").prop("checked")) {
                        queryStr = "?queryMethod=2";
                    } else if ($("#r3").prop("checked")) {
                        $("#form").validationEngine();
                        if ($ ("#form").validationEngine('validate')) {
                            var p1 = $("#start").val();
                            var p2 = $("#end").val();
                            
                            queryStr = "?queryMethod=3&start=" + p1 + "&end=" + p2;
                        }else {
                            return;
                        }
                            
                    }
                    
                    if (selectedCategories.length != 0) {
                        queryStr = queryStr + "&excludeCategories=" + selectedCategories.join("|");
                    }
                    
                    $.ajax({
                        cache: false,
                        url: '<c:url value="/report/consumptionDataSource" />' + queryStr,
                        type: "POST",
                        async: true,
                        success: function(data) {
                            refresh(data);
                        }
                    });
            	};
            	
                
                $("#r1").click(function(){
                    $ ("#dateArea").attr("style", "display:none;");
                    doQuery();
                });
                
                $("#r2").click(function(){
                    $ ("#dateArea").attr("style", "display:none;");
                    doQuery();
                });
                
                $("#r3").click(function(){
                	$ ("#dateArea").attr("style", "display:''");
                });
                
                $("#r4").click(function(){
                	if ($("#r4").prop("checked")) {
                		$ ("#categoryArea").attr("style", "display:''");
                	} else {
                		$ ("#categoryArea").attr("style", "display:none;");
                	}
                });
                
                $ ("#btn-query").click(function(){
                	doQuery();
                });
                
            });
        </script>
    </body>
</html>
