<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <head>
    </head>

    <body>
        <div class="container">
            <div class="page-header">
                <h1>消费情况查询</h1>
            </div>
            
            <div class="text-left">
                <p>
	                <div class="row">
	                    <div class="col-xs-12 col-md-2">
	                        <button type="button" class="btn btn-primary btn-block" id="btn-previous">上一月</button>
	                    </div>
	                    
	                    <div class="col-xs-12 col-md-2">
	                        <button type="button" class="btn btn-primary btn-block" id="btn-next">下一月</button>
	                    </div>
	                    
	                    <div class="col-xs-12 col-md-2">
	                        <a class="btn btn-primary btn-block" data-toggle="collapse" href="#searchArea">排除类别</a>
	                    </div>
	                </div>
                </p>
            </div>
            
            <div class="collapse" id="searchArea">
                <div class="panel panel-primary">
                    <div class="panel-heading">
	                    <h3 class="panel-title text-center">请沟选排除项</h3>
	                </div>
	                <div class="panel-body" style="padding:0 100px;">
	                    <div class="row">
		                    <c:forEach var="item" items="${ rootCategories }" varStatus="status" >
		                    <div class="col-xs-6 col-md-3">
		                        <input type="checkbox" id="category${status.index }" value="${item.categoryOid }" onclick="javascript:doQuery();"><c:out value="${ item.categoryDesc }" /></input>
		                    </div>
		                    </c:forEach>
		                </div>
	                </div>
                </div>
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
                                    <div id="container4" ></div>
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
                        <div class="col-xs-12 col-md-6">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div id="container2" ></div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="col-xs-12 col-md-6">
                            <div class="panel panel-default">
                                <div class="panel-body">
                                    <div id="container3" ></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                
            </div>
        </div>
    
        <script src="<c:url value='/js/jquery-1.11.1.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/bootstrap-3.3.5-dist/js/bootstrap.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/highcharts.src.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/drilldown.src.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/moment.js' />" charset="utf-8"></script>

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
            	
            	var options4 = {
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
                    
                    options4.series = data.colRltOfAmount.series;
                    options4.title.text = data.colRltOfAmount.title;
                    $('#container4').highcharts(options4);
            	};
            	
            	selectYear  = parseInt(moment().format("YYYY"));
                selectMonth = parseInt(moment().format("MM"));
            	
            	doQuery = function() {
            	    var selectedCategories = [];
                    
                    var i = 0;
                    
                    while ( $("#category" + i).length > 0) {
                        if ($("#category" + i).prop("checked")) {
                            selectedCategories.push($("#category" + i).val());
                        }
                        
                        i++;
                    }
                    
                    var queryStr = "?year=" + selectYear + "&month=" + selectMonth;
                    
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
            	
            	doQuery();
            	
            	$("#btn-previous").click(function(){
            		selectMonth--;
            		
            		if (selectMonth == 0) {
            			selectMonth = 12;
            			selectYear--;
            		}
            		
                    doQuery();
                });
                
                $("#btn-next").click(function(){
                    selectMonth++;
                    
                    if (selectMonth == 13) {
                        selectMonth = 1;
                        selectYear++;
                    }
                    
                    doQuery();
                });
                
            });
        </script>
    </body>
</html>
