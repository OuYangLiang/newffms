<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<html>
    <header>
        <meta name="description" content="Family System - 2nd version">
        <meta name="author" content="欧阳亮">
        <meta charset="UTF-8">
        
        <link rel="stylesheet" href="<c:url value='/css/smoothness/jquery-ui-1.10.3.custom.min.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/ui.jqgrid.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/framework.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/menu.css' />" />
        
        <style>
            .ui-jqgrid-titlebar,
            .ui-jqgrid-htable th,
            .ui-jqgrid-btable .jqgrow td {
                height: 3em !important;
            }
        </style>
        
    </header>
    
    <body>
        <div class="pageHeader">
            <div class="headerTitle">家庭管理系统<span style="color: #A9A9A9;font-size: 60%;"> - Ver 2</span></div>
        </div>
    
        <div class="pageMenu">
            <nav>
                <ul>
                    <li><a href="#">首页</a></li>
                    <li><a href="consumption-summary.html">消费</a></li>
                    <li><a href="incoming-summary.html">收入</a></li>
                    <li><a href="account-summary.html">账户</a></li>
                    <li><a href="#">成员</a></li>
                    <li><a href="#">个人</a></li>
                    <li><a href="#">Tutorials</a>
                        <ul>
                            <li><a href="#">Photoshop</a>
                                <ul>
                                    <li><a href="#">Kureski</a></li>
                                </ul>
                            </li>
                            <li><a href="#">Illustrator</a></li>
                            <li><a href="#">Web Design</a>
                                <ul>
                                    <li><a href="#">HTML</a></li>
                                    <li><a href="#">CSS</a></li>
                                </ul>
                            </li>
                        </ul>
                    </li>
                    <li><a href="<c:url value='/j_spring_security_logout' />">退出</a></li>
                </ul>
            </nav>
        </div>
        
        <div class="content-header ui-widget-header">
            信息&nbsp;
        </div>
        
        <div class="contentWrapper">
            <div class="mainArea">
                对不起，你没有足够的权限访问该资源。<br/><br/>
            
                请先联系管理员
            </div>
        </div>
        
        <script src="<c:url value='/js/jquery-1.10.2.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery-ui-1.10.3.custom.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/i18n/grid.locale-cn.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jquery.jqGrid.min.js' />" charset="utf-8"></script>
        <script src="<c:url value='/js/jqGrid-setting.js' />" charset="utf-8"></script>
        
        <script>
            $( document ).ready(function() {
                
            });
        </script>
    </body>
</html>
