<%-- 
    Document   : Form
    Created on : 24 avr. 2023, 21:15:01
    Author     : Fy Botas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    </body>
</html>
