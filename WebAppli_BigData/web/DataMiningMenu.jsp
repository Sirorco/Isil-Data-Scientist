<%-- 
    Document   : DataMiningMenu
    Created on : 27-déc.-2020, 15:06:19
    Author     : Thomas
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>=== Main Menu ===</title>
    </head>
    <body>
        <%  
                    
            session = request.getSession();


            if ((boolean)session.getAttribute("isdatascientist") == true)
            {
                %> <h4>You are datascientist !</h4><%

            }
            else //Not a datascientist
            {
                //Call the JSP that pump the data from the server
                %> <h4>You aren't a datascientist !</h4><%
            }

            %><form method="GET" action="/WebAppli_BigData/Logout"> 
             <P><input type="submit" value="Logout !"></P> <!-- Le bt de type submit, va directement apeller la methode specifier au dessus--><%
 
        %>

    </body>
</html>
