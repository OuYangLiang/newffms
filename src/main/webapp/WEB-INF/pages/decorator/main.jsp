<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/pages/taglibs-include.jsp"%>
<!doctype html>
<html>
    <head>
        <meta name="description" content="Family System - 2nd version">
        <meta name="author" content="欧阳亮">
        <meta charset="UTF-8">
        
        <title><decorator:title /></title>
        <link rel="stylesheet" href="<c:url value='/css/smoothness/jquery-ui.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/ui.jqgrid.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/framework.css' />" />
        <link rel="stylesheet" href="<c:url value='/css/menu.css' />" />
        
        <decorator:head />
    </head>
    
    <body>
        <div class="pageHeader">
            <div class="headerTitle">家庭管理系统<span style="color: #A9A9A9;font-size: 60%;"> - Ver 2</span></div>
        </div>
    
        <div class="pageMenu">
            <nav>
                <ul>
                    <li><a href="<c:url value='/welcome' />">首页</a></li>
                    <li><a href="<c:url value='/consumption/summary' />">消费</a></li>
                    <li><a href="<c:url value='/report/consumption' />">消费情况</a></li>
                    <li><a href="<c:url value='/incoming/summary' />">收入</a></li>
                    <li><a href="<c:url value='/report/incoming' />">收入情况</a></li>
                    <li><a href="<c:url value='/account/summary' />">账户</a></li>
                    <li><a href="#">成员</a></li>
                    <li><a href="<c:url value='/profile/initEdit' />">个人</a></li>
                    <li><a href="<c:url value='/category/summary' />">类别</a></li>
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
        
        <decorator:body />
    </body>
</html>
