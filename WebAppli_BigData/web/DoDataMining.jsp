<%-- 
    Document   : DoDataMining
    Created on : 27-déc.-2020, 18:03:03
    Author     : Thomas
--%>

<%@page import="Protocol.RequestBigDataResult"%>
<%@page import="java.io.ObjectInputStream"%>
<%@page import="java.io.ObjectOutputStream"%>
<%@page import="Protocol.BaseRequest"%>
<%@page import="Protocol.RequestDoBigData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Do data-mining</title>
    </head>
    <body>
        <%
            RequestDoBigData req = new RequestDoBigData();
            req.setId(BaseRequest.DO_BIG_DATA);
            
            String type = request.getParameter("threatment");
            
            if (type.equalsIgnoreCase("Regr-Corr"))
                req.setTypetraitement(RequestDoBigData.REG_CORR);
            else if (type.equalsIgnoreCase("Anova2"))
                req.setTypetraitement(RequestDoBigData.ANOVA);
            else if (type.equalsIgnoreCase("ACM"))
                req.setTypetraitement(RequestDoBigData.ACM);
            else if (type.equalsIgnoreCase("CAH"))
                req.setTypetraitement(RequestDoBigData.CAH);
                
            ObjectOutputStream oos = (ObjectOutputStream)session.getAttribute ("oos");
            ObjectInputStream ois = (ObjectInputStream)session.getAttribute ("ois");
            
            oos.writeObject(req);
            oos.flush();
            
            BaseRequest resp = (BaseRequest) ois.readObject();
            
            if (resp.getStatus())
            {
                %> <h4>Threatment done with succes !</h4> <%
            }
            else
            {
                %> <h4>Error !</h4> <%
            }

            %>
            
            <form method="GET" action="/WebAppli_BigData/DataMiningMenu.jsp"> 
            <P><input type="submit" value="Go back to the menu !"></P> <!-- Le bt de type submit, va directement appeler la methode specifiée au dessus-->
    </body>
</html>
