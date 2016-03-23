<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html lang="zh-CN">
    <head>
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
        <title>AdminLTE 2 | Documentation</title>
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport"/>
        <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->

        <!-- Bootstrap -->
        <link href="<c:url value='/bootstrap-3.3.5-dist/css/bootstrap.min.css' />" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet">
        <link href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet">
        <link href="<c:url value='/AdminLTE2/css/AdminLTE.min.css' />" rel="stylesheet">
        <link href="<c:url value='/AdminLTE2/css/skins/skin-blue.min.css' />" rel="stylesheet">

        <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
        <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
        <!--[if lt IE 9]>
            <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
            <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
            <![endif]-->
        <decorator:head />
    </head>

    <body class="hold-transition skin-blue">
		<div class="wrapper">
			<header class="main-header">
				<a href="#" class="logo"> <span class="logo-mini"><b>A</b>LT</span>
					<span class="logo-lg"><b>Admin</b>LTE</span>
				</a>
	
				<nav class="navbar navbar-static-top" role="navigation">
					<a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
					    <span class="sr-only">Toggle navigation</span>
					</a>
				</nav>
			</header>
			
			<aside class="main-sidebar">
                <section class="sidebar">
                    <div class="user-panel">
						<div class="pull-left image">
						  <img src="<c:url value='/AdminLTE2/img/user2-160x160.jpg' />" class="img-circle" alt="User Image">
						</div>
						
                        <div class="pull-left info">
                            <p>Alexander Pierce</p>
                            <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                        </div>
                    </div>

					<ul class="sidebar-menu">
						<li class="header">HEADER</li>
						<c:forEach var="item" items="${ SESSION_MENU_KEY }" varStatus="status" >
                            <li><a href="<c:url value='${item.moduleLink }' />"><i class="fa fa-link"></i> <span>${item.moduleDesc }</span></a></li>
                        </c:forEach>
                        <li><a href="<c:url value='/j_spring_security_logout' />"><i class="fa fa-link"></i> <span>退出</span></a></li>
					</ul>
                </section>
            </aside>
            
            <div class="content-wrapper">
                <decorator:body />
            </div>

			<footer class="main-footer">
				<!-- To the right -->
				<div class="pull-right hidden-xs">Anything you want</div>
				<!-- Default to the left -->
				<strong>Copyright &copy; 2015 <a href="#">Company</a>.
				</strong> All rights reserved.
			</footer>
	   </div>
    </body>
</html>
