<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

        <!-- Bootstrap -->
        <link href="<c:url value='/bootstrap-3.3.5-dist/css/bootstrap.min.css' />" rel="stylesheet">

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
            <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
            <![endif]-->
        <link rel="stylesheet" href="<c:url value='/css/validationEngine.jquery.css' />" />
    </head>

    <body>
        <nav class="navbar navbar-inverse" role="navigation">
	        <div class="navbar-header">
	            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
	                <span class="sr-only">Toggle navigation</span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	            </button>
	            <span class="navbar-brand">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;菜单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
	        </div>
	        
	        <div id="navbar" class="collapse navbar-collapse">
	            <ul class="nav navbar-nav">
                    <c:forEach var="item" items="${ SESSION_MENU_KEY }" varStatus="status" >
                        <li><a href="<c:url value='${item.moduleLink }' />">${item.moduleDesc }</a></li>
                    </c:forEach>
	            </ul>
	        </div>
	    </nav>
	    
	    <div class="container">
            <div class="page-header">
	            <h1>消费明细</h1>
	        </div>
	        
	        <div class="text-left">
	            <p>
	                <button type="button" class="btn btn-primary" id="btn-previous">上一年</button>
	                <button type="button" class="btn btn-primary" id="btn-next">下一年</button>
	            </p>
	        </div>
	        
	        <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title text-center">报表</h3>
                </div>
                
                <div class="panel-body">
                    <div class="row">
                        <div class="col-xs-12">
	                        <div class="panel panel-default">
	                            <div class="panel-body">
	                                <div id="container3" ></div>
	                            </div>
	                        </div>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div id="container" ></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    
                    <div class="row">
                        <div class="col-xs-12">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div id="container2" ></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
	        
	    </div>
    
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='//bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/highcharts.src.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/drilldown.src.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/moment.js' />" charset="utf-8"></script>
        
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
                            "pointFormat": "{series.name}: <b>¥{point.y:,.2f}</b>"
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
                            "format": "<b>{point.name}</b>:¥{point.y:,.2f}"
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
                            "pointFormat": "{series.name}: <b>¥{point.y:,.2f}</b>"
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
                            "format": "<b>{point.name}</b>:¥{point.y:,.2f}"
                        },
                        series: []
                    };
            	
            	var options3 = {
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
            	
            	var selectYear = parseInt(moment().format("YYYY")); 
            	
            	var refresh = function(data) {
                    options.series = data.incomingOfUser.series;
                    options.title.text = data.incomingOfUser.title;
                    $('#container').highcharts(options);
                    
                    options2.series = data.incomingOfType.series;
                    options2.title.text = data.incomingOfType.title;
                    $('#container2').highcharts(options2);
                    
                    options3.series = data.incomingOfAll.series;
                    options3.title.text = data.incomingOfAll.title;
                    $('#container3').highcharts(options3);
                };
                
                doQuery = function() {
                    var queryStr = "?start=" + selectYear + "&end=" + selectYear;
                    
                    $.ajax({
                        cache: false,
                        url: '<c:url value="/report/incomingDataSource" />' + queryStr,
                        type: "POST",
                        async: true,
                        success: function(data) {
                            refresh(data);
                        }
                    });
                };
                
                $ ("#btn-query").click(function(){
                	doQuery();
                });
                
                doQuery();
                
                $("#btn-previous").click(function(){
                	selectYear -= 1;
                	doQuery();
                });
                
                $("#btn-next").click(function(){
                	selectYear += 1;
                	doQuery();
                });
            });
        </script>
    </body>
</html>
