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
            收入情况查询
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                                
            </div>
        
            <div class="content-title ui-widget-header">
                各人收入情况
            </div>
        
            <div class="mainArea">
                <div id="container" style="padding: 0 20px" ></div>
            </div>
            
            <div class="content-title ui-widget-header">
                各分类收入情况
            </div>
        
            <div class="mainArea">
                <div id="container2" style="padding: 0 20px" ></div>
            </div>
            
            
        </div>
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.validationEngine-zh_CN.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/highcharts.src.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/drilldown.src.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
            	var options = {
                        chart: {
                            type: "spline"
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
                        series: []
                    };
            	
            	var options2 = {
                        chart: {
                            type: "spline"
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
                        series: []
                    };
            	
            	var refresh = function(data) {
                    options.series = data.incomingOfUser.series;
                    $('#container').highcharts(options);
                    
                    options2.series = data.incomingOfType.series;
                    $('#container2').highcharts(options2);
                };
                
                $.ajax({
                    cache: false,
                    url: '<c:url value="/report/incomingDataSource?start=2014&end=2014" />',
                    type: "POST",
                    async: true,
                    success: function(data) {
                        refresh(data);
                    }
                });
            });
        </script>
    </body>
</html>
