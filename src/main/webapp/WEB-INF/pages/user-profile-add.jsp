<!doctype html>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<html>
    <header>
        <meta name="description" content="Family System - 2nd version">
        <meta name="author" content="欧阳亮">
        <meta charset="UTF-8">
        
        <title>User Profile - Create</title>
    </header>
    
    <body>
        <c:url value='/user/saveAdd' var='url' />
        <spring:errors path="errors" />
        <spring:form id="form" method="post" action="${url}" modelAttribute="user" >
            User Name: <spring:input path="userName" /><spring:errors path="userName" /><br/>
            User Alias: <input type="text" name="userAlias" /><br/>
            Gender: <select name="gender">
                        <option value ="Male">男</option>
                        <option value ="Female">女</option>
                    </select><br/>
            Phone: <input type="text" name="phone" /><br/>
            Email: <input type="text" name="email" /><spring:errors path="email" /><br/>
            Login Id: <input type="text" name="loginId" /><br/>
            Login Password: <input type="text" name="loginPwd" /><br/>
            <a href="javascript:document.getElementById('form').submit();" >Submit</a>
            <a href="javascript:window.location.href='<c:url value="/j_spring_security_logout" />';" >Logout</a>
        </spring:form>
    </body>
</html>