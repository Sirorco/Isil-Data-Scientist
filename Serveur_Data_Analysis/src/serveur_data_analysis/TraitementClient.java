/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur_data_analysis;


import Protocol.BaseRequest;
import Protocol.RequestBigDataResult;
import Protocol.RequestDoBigData;
import Protocol.RequestLogin;
import Protocol.RequestLoginInitiator;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author renardN
 */
public class TraitementClient implements Runnable {
    
    private MainFrame mF;
    private Socket cSocket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    
    public TraitementClient(Socket cSocket, MainFrame mF)
    {
        this.mF = mF;
        this.cSocket = cSocket;
        try {
            oos = new ObjectOutputStream(cSocket.getOutputStream());
            ois = new ObjectInputStream(cSocket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    

    @Override
    public void run() {
        mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Lancement du thread de traitement client !");
        System.out.println("Thread :" + this.toString() + "Lancement du thread de traitement client !");
        boolean finConnexion = false;
        BaseRequest requeteBaseClient;
        BaseRequest reponseClient;
        
        
        try {
            while((requeteBaseClient = (BaseRequest) ois.readObject()).getId() != BaseRequest.LOGOUT)
            {
                reponseClient = new RequestBigDataResult();
                if(requeteBaseClient.getId() == BaseRequest.LOGIN_INITIATOR)
                {
                    RequestLoginInitiator requeteClient = (RequestLoginInitiator) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Traitement initialisation login");
                    System.out.println("Thread :" + this.toString() + "Traitement initialisation login");
                    
                    // Traitement initialisation login
                    
                    oos.writeObject(reponseClient);
                }
                
                if(requeteBaseClient.getId() == BaseRequest.LOGIN_EID)
                {
                    RequestLogin requeteClient = (RequestLogin) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Traitement login EID");
                    System.out.println("Thread :" + this.toString() + "Traitement login EID");
                    
                    //Traitement login EID
                    
                    oos.writeObject(reponseClient);
                }
                
                if(requeteBaseClient.getId() == BaseRequest.LOGIN_OTP)
                {
                    RequestLogin requeteClient = (RequestLogin) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Traitement login OTP");
                    System.out.println("Thread :" + this.toString() + "Traitement login OTP");
                    
                    //Traitement login OTP
                    
                    oos.writeObject(reponseClient);
                }
                
                if(requeteBaseClient.getId() == BaseRequest.LOGIN_WEB)
                {
                    RequestLogin requeteClient = (RequestLogin) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Traitement login WEB");
                    System.out.println("Thread :" + this.toString() + "Traitement login WEB");
                    
                    // Traitement login WEB
                    
                    oos.writeObject(reponseClient);
                }
                
                if(requeteBaseClient.getId() == BaseRequest.DO_BIG_DATA)
                {
                    RequestDoBigData requeteClient = (RequestDoBigData) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Traitement BIG DATA");
                    System.out.println("Thread :" + this.toString() + "Traitement BIG DATA");
                    
                    //Traitement BIG DATA
                    
                    
                    if(requeteClient.getTypetraitement() == RequestDoBigData.ACP)
                    {
                        // Traitement de l'ACP (Faire un objet)
                        oos.writeObject(reponseClient);
                    }
                    
                    if(requeteClient.getTypetraitement() == RequestDoBigData.ACM)
                    {
                        // Traitement de l'ACM (Faire un objet)
                        oos.writeObject(reponseClient);
                    }
                    
                    if(requeteClient.getTypetraitement() == RequestDoBigData.REG_CORR)
                    {
                        // Traitement de la REG_CORR (Faire un objet)
                        oos.writeObject(reponseClient);
                    }
                    if(requeteClient.getTypetraitement() == RequestDoBigData.ANOVA)
                    {
                        // Traitement de la ANOVA (Faire un objet)
                        oos.writeObject(reponseClient);
                    }
                }
            }
            
            closeConnexion();
        } catch (IOException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    private void closeConnexion()
    {
        try {
            ois.close();
            oos.close();
            if(!cSocket.isClosed())
                cSocket.close();
        } catch (IOException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
