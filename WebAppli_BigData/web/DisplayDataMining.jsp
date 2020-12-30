<%-- 
    Document   : DisplayDataMining
    Created on : 27-déc.-2020, 18:48:45
    Author     : Thomas
--%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="Protocol.BaseRequest"%>
<%@page import="Protocol.RequestDoBigData"%>
<%@page import="Protocol.RequestBigDataResult"%>
<%@page import="java.io.ObjectInputStream"%>
<%@page import="java.io.ObjectOutputStream"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Big Data </title>
    </head>
    <body>
        <%
            RequestDoBigData req = new RequestDoBigData();
            req.setId(BaseRequest.BIG_DATA_RESULT);
            
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
            
            RequestBigDataResult resp = (RequestBigDataResult) ois.readObject();
            
            if (resp.getStatus())
            {
                if (type.equalsIgnoreCase("Regr-Corr"))
                {
                    /*String Date = "14-12-1997";
                    String formule = "val = 4*x + 9*p";
                    String comment1 = "Nice !";
                    String ccl = "Bof !";*/
                    Timestamp DateTime = (Timestamp) resp.getValue(RequestBigDataResult.REGCORR_DATE);
                    String Date = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(DateTime);
                    String title = (String) resp.getValue(RequestBigDataResult.REGCORR_GLOBAL_TITRE);
                    //String comment1 = (String) resp.getValue(RequestBigDataResult.REGCORR_PLOT_ONE); => Image
                    String comment1 = (String) resp.getValue(RequestBigDataResult.REGCORR_PLOT_ONE_TEXT);
                    String ccl = (String) resp.getValue(RequestBigDataResult.REGCORR_GLOBAL_TEXT);
                    %> <h4>Regression-Correlation</h4>
                    <h4>Titre : <%=title%></h4>
                    <h4>Date de réalisation du traitement : <%=Date%></h4><%
                    //<img src="data:image/jpg;base64,${book.base64Image}" width="240" height="300"/>
                    %> <h4><%=comment1%></h4>
                    <h4><%=ccl%></h4>
                    <%
                }
                else if (type.equalsIgnoreCase("Anova2"))
                {
                    Timestamp DateTime = (Timestamp) resp.getValue(RequestBigDataResult.ANOVA2_DATE);
                    String Date = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(DateTime);
                    String title = (String) resp.getValue(RequestBigDataResult.ANOVA2_GLOBAL_TITRE);
                    //String comment1 = (String) resp.getValue(RequestBigDataResult.ANOVA2_PLOT_ONE); => Image
                    String comment1 = (String) resp.getValue(RequestBigDataResult.ANOVA2_PLOT_ONE_TEXT);
                    String ccl = (String) resp.getValue(RequestBigDataResult.ANOVA2_GLOBAL_TEXT);
                    %> <h4>Anova2</h4>
                    <h4>Titre : <%=title%></h4>
                    <h4>Date de réalisation du traitement : <%=Date%></h4><%
                    //<img src="data:image/jpg;base64,${book.base64Image}" width="240" height="300"/>
                    %> <h4><%=comment1%></h4>
                    <h4><%=ccl%></h4>
                    <%
                }
                else if (type.equalsIgnoreCase("ACM"))
                {       
                    Timestamp DateTime = (Timestamp) resp.getValue(RequestBigDataResult.ACM_DATE);
                    String Date = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(DateTime);
                    String title = (String) resp.getValue(RequestBigDataResult.ACM_GLOBAL_TITRE);
                    //String comment1 = (String) resp.getValue(RequestBigDataResult.ACM_PLOT_ONE); => Image
                    String comment1 = (String) resp.getValue(RequestBigDataResult.ACM_PLOT_ONE_TEXT);
                    String ccl = (String) resp.getValue(RequestBigDataResult.ACM_GLOBAL_TEXT);
                    %> <h4>ACM</h4>
                    <h4>Titre : <%=title%></h4>
                    <h4>Date de réalisation du traitement : <%=Date%></h4><%
                    //<img src="data:image/jpg;base64,${book.base64Image}" width="240" height="300"/>
                    %> <h4><%=comment1%></h4>
                    <h4><%=ccl%></h4>
                    <%
                }
                else if (type.equalsIgnoreCase("CAH"))
                {
                    Timestamp DateTime = (Timestamp) resp.getValue(RequestBigDataResult.CAH_DATE);
                    String Date = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy").format(DateTime);
                    String title = (String) resp.getValue(RequestBigDataResult.CAH_GLOBAL_TITRE);
                    //String comment1 = (String) resp.getValue(RequestBigDataResult.CAH_PLOT_ONE); => Image
                    String comment1 = (String) resp.getValue(RequestBigDataResult.CAH_PLOT_ONE_TEXT);
                    //String comment1 = (String) resp.getValue(RequestBigDataResult.CAH_PLOT_TWO); => Image
                    String comment2 = (String) resp.getValue(RequestBigDataResult.CAH_PLOT_TWO_TEXT);
                    String ccl = (String) resp.getValue(RequestBigDataResult.REGCORR_GLOBAL_TEXT);
                    %> <h4>CAH</h4>
                    <h4>Titre : <%=title%></h4>
                    <h4>Date de réalisation du traitement : <%=Date%></h4><%
                    //<img src="data:image/jpg;base64,${book.base64Image}" width="240" height="300"/>
                    %> <h4><%=comment1%></h4><%
                    //<img src="data:image/jpg;base64,${book.base64Image}" width="240" height="300"/>
                    %> <h4><%=comment2%></h4>
                    <h4><%=ccl%></h4>
                    <%
                }
            }
            else
            {
                %> <h4>Error !</h4> <%
            }

            %>
            
            <form method="GET" action="/WebAppli_BigData/DataMiningMenu.jsp"> 
            <P><input type="submit" value="Go back to the menu !"></P> <!-- Le bt de type submit, va directement apeller la methode specifier au dessus-->
    </body>
</html>
