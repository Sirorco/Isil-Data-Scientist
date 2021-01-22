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
import static Protocol.BaseRequest.LOGOUT;
import Protocol.RequestLogin;
import Protocol.RequestLoginInitiator;
import Protocol.RequestLoginResponse;
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
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import java.util.*;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Thomas
 */
@WebServlet(name = "Login", urlPatterns = {"/Login"})
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
    
    private String port_auth;
    private String acs_address;
    
    
    public void init (ServletConfig config) throws ServletException 
    { 
        super.init(config); 
        Security.addProvider(new BouncyCastleProvider());
        
        /*try
        {
            FileInputStream fip= new FileInputStream("config.properties");
            Properties propfile=new Properties();
            propfile.load(fip);

            port_auth = propfile.getProperty("port_serv", "50001");
            acs_address = propfile.getProperty("serv_address", "localhost");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        
        port_auth = "50000";
        acs_address = "localhost";

    } 
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String username = request.getParameter("username");
        String pswd = request.getParameter ("password");
        Boolean status;
        String  adressejsp;
        

        Socket cliSock = new Socket(acs_address, Integer.parseInt(port_auth));
        ObjectOutputStream oos = new ObjectOutputStream(cliSock.getOutputStream());
        ObjectInputStream ois = new ObjectInputStream(cliSock.getInputStream());        
        System.out.println ("Server connection OK !");
        
        


        int returnval =checkLogin (username,pswd, oos, ois);
        
        
        if (returnval<1) //Bad login, we kill the connection
        {
            BaseRequest end = new BaseRequest(LOGOUT, false, null);
            oos.writeObject(end);
            
            ois.close();
            oos.close();
            status = false;
            
            System.out.println ("Kill the streams");
        }
        else
        {
            //On foure les flux dans la session.
            request.getSession().setAttribute("oos", oos);
            request.getSession().setAttribute("ois", ois);
            
            status = true;
            
            if (returnval == 1)
            {
                request.getSession().setAttribute("isdatascientist",false);
            }
            else
            {
                request.getSession().setAttribute("isdatascientist",true);
            }
        }
        
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
    // -1 => error somewhere
    // 0 => Bad login
    // 1 => Good login but not datascientist
    // 2 => Good login and datascientist
    int checkLogin (String Username, String pswd, ObjectOutputStream oos, ObjectInputStream ois)
    {

        try
        {
            //Todo : getsalt from server
            RequestLoginInitiator reqloginit = new RequestLoginInitiator();
            reqloginit.setId(BaseRequest.LOGIN_INITIATOR);
            reqloginit.setSaltChallenge(Username);

            oos.writeObject(reqloginit);
            oos.flush();
            
            reqloginit = (RequestLoginInitiator) ois.readObject();

            RequestLogin reqlog = new RequestLogin();
            reqlog.setId(BaseRequest.LOGIN_WEB);
            reqlog.setUsername(Username);
        
            System.out.println("Instanciation du message digest");
            MessageDigest md = MessageDigest.getInstance("SHA-1", "BC");
            Vector<String> Components = new Vector<String>();
            Components.add(reqloginit.getSaltChallenge());
            Components.add(pswd);
            reqlog.CalculateDigest(md, Components);
            
            oos.writeObject(reqlog);
            oos.flush();
            
            RequestLoginResponse logrep = (RequestLoginResponse) ois.readObject();
            
            if (logrep.getStatus() == false)
            {
                return 0; //bad login
            }
            else
            {
                if (logrep.isIsdatascientist())
                {
                    return 2; //datascientist
                }
                else
                {
                    return 1; //not datascientist
                }
            }
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchProviderException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    // </editor-fold>

}
