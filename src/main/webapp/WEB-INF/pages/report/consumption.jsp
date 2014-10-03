<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
    <body>
        <div class="content-header ui-widget-header">
            Highchart - Samples
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div id="container" style="padding: 0 20px" ></div>
            </div>
        </div>
        
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/common.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/highcharts.src.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/drilldown.src.js' />" charset="utf-8"></script>
        

        <script>
            $( document ).ready(function() {
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
                            text: "Value"
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
                    }
                };
            	
            	$.getJSON('<c:url value="/report/consumptionDataSource" />', function(data) {
            		options.series = data.series;
            		options.drilldown = {};
                    options.drilldown.series = data.drilldown;
                    $('#container').highcharts(options);
            	});
            	
            	var series = [
            	    {
            	    	"name":"全部",
            	    	"data":[
            	    	    {
            	    	    	"name":"日常消费",
            	    	    	"y": 1000,
            	    	    	"drilldown":"100"
            	    	    },
            	    	    {
            	    	    	"name":"汽车",
            	    	    	"y": 1500,
            	    	    	"id": "200",
                                "drilldown":"200"
            	    	    }
            	    	]
            	    },
                    {
                        "name":"欧阳亮",
                        "data":[
                            {
                                "name":"日常消费",
                                "y": 700,
                                "id": "100-1",
                                "drilldown":"100-1"
                            },
                            {
                                "name":"汽车",
                                "y": 800,
                                "id": "200-1",
                                "drilldown":"200-1"
                            }
                        ]
                    },
                    {
                        "name":"喻敏",
                        "data":[
                            {
                                "name":"日常消费",
                                "y": 300,
                                "id": "100-2",
                                "drilldown":"100-2"
                            },
                            {
                                "name":"汽车",
                                "y": 700,
                                "id": "200-2",
                                "drilldown":"200-2"
                            }
                        ]
                    },
            	];
            	var drilldown = {
            	    "series": [
            	        {
            	        	"id": "100",
            	        	"name": "日常消费",
            	        	"data": [
            	        	    {
            	        	    	"name":"饮食",
            	        	    	"y": 500
            	        	    },
            	        	    {
            	        	    	"name":"酒水",
            	        	    	"y": 300
            	        	    },
            	        	    {
            	        	    	"name":"其它",
            	        	    	"y": 200
            	        	    }
            	        	]
            	        },
                        {
                            "id": "100-1",
                            "name": "欧阳亮",
                            "data": [
                                {
                                    "name":"饮食",
                                    "y": 350
                                },
                                {
                                    "name":"酒水",
                                    "y": 250
                                },
                                {
                                    "name":"其它",
                                    "y": 100
                                }
                            ]
                        },
                        {
                            "id": "100-2",
                            "name": "喻敏",
                            "data": [
                                {
                                    "name":"饮食",
                                    "y": 150
                                },
                                {
                                    "name":"酒水",
                                    "y": 50
                                },
                                {
                                    "name":"其它",
                                    "y": 100
                                }
                            ]
                        },
                        {
                            "id": "200",
                            "name": "汽车",
                            "data": [
                                {
                                    "name":"汽油",
                                    "y": 600
                                },
                                {
                                    "name":"停车费",
                                    "y": 300
                                },
                                {
                                    "name":"保养",
                                    "y": 600,
                                    "drilldown":"300"
                                }
                            ]
                        },
                        {
                            "id": "200-1",
                            "name": "欧阳亮",
                            "data": [
                                {
                                    "name":"汽油",
                                    "y": 500
                                },
                                {
                                    "name":"停车费",
                                    "y": 150
                                },
                                {
                                    "name":"保养",
                                    "y": 150,
                                    "drilldown":"300-1"
                                }
                            ]
                        },
                        {
                            "id": "200-2",
                            "name": "喻敏",
                            "data": [
                                {
                                    "name":"汽油",
                                    "y": 100
                                },
                                {
                                    "name":"停车费",
                                    "y": 150
                                },
                                {
                                    "name":"保养",
                                    "y": 450,
                                    "drilldown":"300-2"
                                }
                            ]
                        },
                        {
                            "id": "300",
                            "name": "保养",
                            "data": [
                                {
                                    "name":"洗车",
                                    "y": 230
                                },
                                {
                                    "name":"保养",
                                    "y": 370
                                }
                            ]
                        },
                        {
                            "id": "300-1",
                            "name": "欧阳亮",
                            "data": [
                                {
                                    "name":"洗车",
                                    "y": 130
                                },
                                {
                                    "name":"保养",
                                    "y": 20
                                }
                            ]
                        },
                        {
                            "id": "300-2",
                            "name": "喻敏",
                            "data": [
                                {
                                    "name":"洗车",
                                    "y": 100
                                },
                                {
                                    "name":"保养",
                                    "y": 350
                                }
                            ]
                        }
            	    ]
            	
            	};
            	
            });
        </script>
    </body>
</html>
