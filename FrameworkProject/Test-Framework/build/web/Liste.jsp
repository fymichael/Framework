<%-- 
    Document   : Liste
    Created on : 4 avr. 2023, 21:55:37
    Author     : Fy Botas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import ="java.util.Vector" %>

<%
    Vector liste = (Vector)request.getAttribute("Liste_emp");
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1> Inscription </h1>
        <form action="emp-save" method="post">
            <p> <input type="number" name="id" placeholder="Entrez votre id"> </p>
            <p> <input type="text" name="nom" placeholder="Entrez votre nom"> </p>
            <p> <input type="text" name="prenom" placeholder="Entrez votre prenom"> </p>
            <p> <input type="date" name="dateDeNaissance" placeholder="Entrez votre Date de naissance"> </p>
            <p> <input type="submit" value="Save"> </p>
        </form>
        <h1> Liste des Employees </h1>
        <% for(int i=0;i<liste.size();i++) { %>
        <p><% out.print(liste.get(i)); %> <a href="emp-all?nom=<%out.print(liste.get(i)); %>">Details</a> </p>
        <% } %>
    </body>
</html>
