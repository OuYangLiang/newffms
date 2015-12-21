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
        <decorator:head />
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
	    
	    <decorator:body />
    </body>
</html>
