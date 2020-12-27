<%-- 
    Document   : Logout
    Created on : 27-déc.-2020, 15:09:50
    Author     : Thomas
--%>


<%@page import="Protocol.BaseRequest"%>
<%@page import="java.io.ObjectInputStream"%>
<%@page import="java.io.ObjectOutputStream"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>GoodBye !</title>
    </head>
    <body>
        <%       
            session = request.getSession();
            
            ObjectOutputStream oos = (ObjectOutputStream)session.getAttribute ("oos");
            ObjectInputStream ois = (ObjectInputStream)session.getAttribute ("ois");

            BaseRequest end = new BaseRequest(BaseRequest.LOGOUT, false, null);
            oos.writeObject(end);
            
            ois.close();
            oos.close();
 
        %>
    </body>
</html>
