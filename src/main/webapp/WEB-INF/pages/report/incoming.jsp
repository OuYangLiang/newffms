<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <head>
        <link rel="stylesheet" href="<c:url value='/css/validationEngine.jquery.css' />" />
    </head>

    <body>
        <div class="content-header ui-widget-header">
            收入情况查询
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                <div class="newline-wrapper ui-widget-content" id="dateArea">
                    <div class="label">查询范围</div>
                
                    <div class="input" >
                        <div style="float: left; margin-top: 10px; margin-right: 5px;">起始</div>
                        <div style="float:left; margin-right: 50px;">
                        <select id="start" name="start" style="width: 70px;" class="selectbox" >
                            <c:forEach var="year" items="${ years }" varStatus="status">
                                <option value ="${year}" <c:if test='${year == curYear }' >selected="selected"</c:if> >${year}年</option>
                            </c:forEach>
                        </select>
                        </div>
                        
                        <div style="float: left; margin-top: 10px; margin-right: 5px;">结束</div>
                        <div style="float:left;">
                        <select id="end" name="end" style="width: 70px;" class="selectbox" >
                            <c:forEach var="year" items="${ years }" varStatus="status">
                                <option value ="${year}" <c:if test='${year == curYear }' >selected="selected"</c:if> >${year}年</option>
                            </c:forEach>
                        </select>
                        </div>
                        
                        <div id="btn-query" style="float: left; margin-left: 120px; margin-top: 5px;">查询</div>
                    </div>
                    
                    <div style="clear:both;" ></div>
                </div>
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
            	$ ("#btn-query").button();
            	$( ".selectbox" ).selectmenu();
            	
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
            	
            	var refresh = function(data) {
                    options.series = data.incomingOfUser.series;
                    options.title.text = data.incomingOfUser.title;
                    $('#container').highcharts(options);
                    
                    options2.series = data.incomingOfType.series;
                    options2.title.text = data.incomingOfType.title;
                    $('#container2').highcharts(options2);
                };
                
                doQuery = function() {
                    
                    var start = $ ("#start").val();
                    var end   = $ ("#end").val();
                    
                    var queryStr = "?start=" + start + "&end=" + end;
                    
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
            });
        </script>
    </body>
</html>
