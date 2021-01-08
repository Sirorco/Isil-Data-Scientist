<%-- 
    Document   : JSPInit
    Created on : 30-oct.-2017, 16:54:20
    Author     : Thomas Tolunay
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.sql.ResultSetMetaData"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Vector"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Init</title>
    </head>
    <body>
        <%  
            boolean status = Boolean.parseBoolean(request.getParameter("status"));  

            if (status == true)
            {
                %> <h4>Welcome back !</h4><%

                String username = request.getParameter("username");
                //String pwd =request.getParameter("password") ;

                %><p> <%=username%> <p><%
                    
                    
                session = request.getSession();
                
                session.setAttribute ("session.username", username); 
                session.setAttribute ("validity_date", session.getCreationTime()+300000); //5min

                Date date = new Date(session.getCreationTime()+300000);
                DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                String dateFormatted = formatter.format(date);
                
                
                %><p> You session will be valid until : <%=dateFormatted%> <p><%
                    
                if ((boolean)session.getAttribute("isdatascientist") == true)
                {
                    %> <h4>You are datascientist !</h4><%
                }
                else //Not a datascientist
                {
                    //Call the JSP that pump the data from the server
                    %> <h4>You aren't a datascientist !</h4><%
                }
 
                %><form method="GET" action="/WebAppli_BigData/DataMiningMenu.jsp"> 
                    <P><input type="submit" value="Go do some datamining !"></P> <!-- Le bt de type submit, va directement apeller la methode specifier au dessus--><%
                      
                
            }
            else
            {
                %> <h4>Login failed !</h4>
                <h4>Bad username or password !</h4> <%
            }
        %>

    </body>
</html>
