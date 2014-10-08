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
                <div class="newline-wrapper ui-widget-content" style="padding-top: 15px; padding-bottom: 15px; margin-bottom: 5px;">
                    <div class="input" style="width:100%">
                        <input type="radio" name="queryMethod" id="r1" style="margin-left:160px;" checked="true">当前月</input>
                        <input type="radio" name="queryMethod" id="r2" style="margin-left:20px;">前一月</input>
                        <input type="radio" name="queryMethod" id="r3" style="margin-left:20px;">按指定日期查询</input>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
            
                <div class="newline-wrapper ui-widget-content" style="display:none;" id="dateArea">
                    <div class="input" style="width:100%">
                        <form id="form" method="post">
                        <span style="margin-left:200px;">起始日期</span>
                        <input style="width: 100px;" type="text" name="start" id="start" class="inputbox" readonly="true" data-validation-engine="validate[required]" />
                        
                        <span style="margin-left:50px;">结束日期</span>
                        <input style="width: 100px;" type="text" name="end" id="end" class="inputbox" readonly="true" data-validation-engine="validate[required]" />
                        
                        <span id="btn-query" style="margin-left:20px; margin-top:-5px;">查询</span>
                        </form>
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
            	
            	$.getJSON('<c:url value="/report/consumptionDataSource" />', function(data) {
            		refresh(data);
            	});
            	
            	$("#r3").click(function(){
                    $ ("#dateArea").attr("style", "display:''");
                });
                
                $("#r1").click(function(){
                    $ ("#dateArea").attr("style", "display:none;");
                    $.getJSON('<c:url value="/report/consumptionDataSource?queryMethod=1" />', function(data) {
                    	refresh(data);
                    });
                });
                
                $("#r2").click(function(){
                    $ ("#dateArea").attr("style", "display:none;");
                    $.getJSON('<c:url value="/report/consumptionDataSource?queryMethod=2" />', function(data) {
                    	refresh(data);
                    });
                });
                
                $ ("#btn-query").click(function(){
                    $("#form").validationEngine();
                    if ($ ("#form").validationEngine('validate')) {
                    	var p1 = $("#start").val();
                    	var p2 = $("#end").val();
                    	
                    	var queryStr = "?queryMethod=3&start=" + p1 + "&end=" + p2;
                    	$.getJSON('<c:url value="/report/consumptionDataSource" />' + queryStr, function(data) {
                    		refresh(data);
                        });
                    }
                });
            	
            });
        </script>
    </body>
</html>
