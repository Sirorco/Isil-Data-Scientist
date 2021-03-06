/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur_data_analysis;

import connectionRServe.BeanRServe;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author renardN
 */
public class MainServer {
    
    private MainFrame mF;
    private int port;
    private ServerSocket sSocket = null;
    private boolean isRunning;
    private BeanRServe beanRServe; //Objet pour la connexion à R
    
    public MainServer (MainFrame mF)
    {
        isRunning = true;
        this.mF = mF;
        //Recherche des infos dans le fichier properties
        ResourceBundle bundle = ResourceBundle.getBundle("properties.fichConfig");
        port = Integer.parseInt(bundle.getString("socket.portServer"));
        try {
            sSocket = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
        beanRServe = new BeanRServe();
    }
    
    public void open()
    {
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning == true)
                {
                    try {
                        mF.getjTextFieldLogServeur().setText("Attente d'une nouvelle connexion");
                        System.out.println("Attente d'une nouvelle connexion");
                        
                        Socket cSocket = sSocket.accept();
                        
                        mF.getjTextFieldLogServeur().setText("Connexion client reçue !");
                        System.out.println("Connexion client reçue !");
                        
                        Thread t = new Thread(new TraitementClient(cSocket, mF, beanRServe));
                        t.start();
                        
                    } catch (IOException ex) {
                        Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        
        t.start();
    }
    
    public void close()
    {
        mF.getjTextFieldLogServeur().setText("Fermeture du serveur d'analyse de données !");
        isRunning = false;
        try 
        {
            if(sSocket != null && !sSocket.isClosed())
                sSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(MainServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
