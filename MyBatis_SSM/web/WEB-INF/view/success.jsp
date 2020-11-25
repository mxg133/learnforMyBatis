<%--
  Created by IntelliJ IDEA.
  User: mxg
  Date: 2020/11/25
  Time: 5:54 下午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>success</title>
</head>
<body>
<h1>success了</h1>
<c:forEach items="${list}" var="l">
    ${l.id}<br/>
    ${l.lastName}<br/>
</c:forEach>
</body>
</html>
