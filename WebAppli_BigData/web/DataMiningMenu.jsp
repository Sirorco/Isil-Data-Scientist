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
                %> <h4>You are datascientist !</h4>
                <h4>DataMining threatment availiable :</h4>
                
                <form method="GET" action="/WebAppli_BigData/DoDataMining.jsp"> 
                <input type="radio" name="threatment" value="Regr-Corr" />Regression-Correlation
                <input type="radio" name="threatment" value="Anova2" />Anova2
                <input type="radio" name="threatment" value="ACM" />ACM
                <input type="radio" name="threatment" value="CAH" />CAH
                <P><input type="submit" value="Launch operation"></P> <!-- Le bt de type submit, va directement appeler la methode specifiée au dessus-->
                </form> 
                
                <BR>&nbsp;<BR>&nbsp; <%

            }
            else //Not a datascientist
            {
                //Call the JSP that pump the data from the server
                %> <h4>You aren't a datascientist !</h4><%
            }

            %> <h4>DataMining result availiable :</h4>

            <form method="GET" action="/WebAppli_BigData/DisplayDataMining.jsp"> 
            <input type="radio" name="threatment" value="Regr-Corr" />Regression-Correlation
            <input type="radio" name="threatment" value="Anova2" />Anova2
            <input type="radio" name="threatment" value="ACM" />ACM
            <input type="radio" name="threatment" value="CAH" />CAH
            <P><input type="submit" value="View results"></P> <!-- Le bt de type submit, va directement appeler la methode specifiée au dessus-->
            </form> 
            
            <BR>&nbsp;<BR>&nbsp; <%

            %>
            <BR>&nbsp;<BR>&nbsp; 
            <form method="GET" action="/WebAppli_BigData/Logout.jsp"> 
            <P><input type="submit" value="Logout !"></P> <!-- Le bt de type submit, va directement appeler la methode specifiée au dessus--><%
 
        %>

    </body>
</html>
