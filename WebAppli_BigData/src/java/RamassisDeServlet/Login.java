/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RamassisDeServlet;

/*Todo
- Pas de protection pour le mutli thread
- Faut lancer sur 2 machines différents[Impossible de lancer plusieurs fois l'application]
- Rendre la facture plus jolie
*/

import Protocol.BaseRequest;
import Protocol.RequestLogin;
import Protocol.RequestLoginInitiator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Hashtable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 *
 * @author Thomas
 */
//@WebServlet(name = "Login", urlPatterns = {"/Login"}, initParams = {
//@WebInitParam(name = "TypeSGBD", value = "MySql")})
public class Login extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private Socket cliSock;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    
    public void init (ServletConfig config) throws ServletException 
    { 
        super.init(config); 
        Security.addProvider(new BouncyCastleProvider());
        
        try
        {
            FileInputStream fip= new FileInputStream("config.properties");
            Properties propfile=new Properties();
            propfile.load(fip);

            String port_auth = propfile.getProperty("port_serv", "5001");
            String acs_address = propfile.getProperty("serv_address", "localhost");
            
            cliSock = new Socket(acs_address, Integer.parseInt(port_auth));
            oos = new ObjectOutputStream(cliSock.getOutputStream());
            ois = new ObjectInputStream(cliSock.getInputStream());
            System.out.println ("Server connection OK !");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }

    } 
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String username = request.getParameter("username");
        String pswd = request.getParameter ("password");
        Boolean status;
        String  adressejsp;
        
        

        status = checkLogin (username,pswd);
        adressejsp = "/JSPInit.jsp" + "?username=" + URLEncoder.encode(username)  + "&status=" + URLEncoder.encode (status.toString());

         
        System.out.println ("Go call the jsp");
        System.out.println("Session ID login : " + request.getSession().getId());
        
        ServletContext sc = this.getServletContext();
        RequestDispatcher rd = sc.getRequestDispatcher(adressejsp);
        rd.forward(request, response);  //Appel du JSPInit
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Our methodes and fonctions (J'ai trouvé comment faire des régions en java[c'est quand même plus facile avec C#])">
    synchronized boolean checkLogin (String Username, String pswd)
    {

        try
        {
            //Todo : getsalt from server
            RequestLoginInitiator reqloginit = new RequestLoginInitiator();
            reqloginit.setId(BaseRequest.LOGIN_INITIATOR);
            reqloginit.setStatus(true);
            reqloginit.setError_msg(null);
            reqloginit.setSaltChallenge(Username);

            oos.writeObject(reqloginit);
            oos.flush();
            
            reqloginit = (RequestLoginInitiator) ois.readObject();

            RequestLogin reqlog = new RequestLogin();
            reqlog.setId(BaseRequest.LOGIN_WEB);
            reqlog.setStatus(true);
            reqlog.setError_msg(null);
            reqlog.setUsername(Username);
        
            System.out.println("Instanciation du message digest");
            MessageDigest md = MessageDigest.getInstance("SHA-1", "BC");
            md.update(pswd.getBytes());
            md.update(reqloginit.getSaltChallenge().getBytes());
            reqlog.setDigest(md.digest());
            
            oos.writeObject(reqlog);
            oos.flush();
            
            BaseRequest basreq = (BaseRequest) ois.readObject();
            
            return basreq.getStatus(); //True if login success
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    // </editor-fold>

}
