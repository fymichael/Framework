<%-- 
    Document   : TestJson
    Created on : 7 juil. 2023, 15:32:13
    Author     : P15B-79-FY
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <% if(request.getAttribute("json")!= null) { %>
        <h1><%= request.getAttribute("json") %></h1>
        <% }else if(request.getAttribute("jsonHashMap") != null) { %>
        <h1><%= request.getAttribute("jsonHashMap") %></h1>
        <% } %>

    </body>
</html>
