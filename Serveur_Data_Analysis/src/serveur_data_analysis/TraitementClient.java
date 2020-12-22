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
import connectionJdbc.BeanJDBC;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Vector;
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
    
    private BeanJDBC beanJdbc; //Objet pour la connexion à la BDD
    
    
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
        //Recherche des infos dans le fichier properties
        ResourceBundle bundle = ResourceBundle.getBundle("properties.fichConfig");
        String name = bundle.getString("sgbd.name");
        String user = bundle.getString("sgbd.user");
        String mdp = bundle.getString("sgbd.mdp");
        beanJdbc = new BeanJDBC(name, user, mdp);
        
    }
    

    @Override
    public void run() {
        mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Lancement du thread de traitement client !");
        System.out.println("Thread :" + this.toString() + "Lancement du thread de traitement client !");
        BaseRequest requeteBaseClient;
        BaseRequest reponseClient;
        String saltClient = null;
        
        try {
            while((requeteBaseClient = (BaseRequest) ois.readObject()).getId() != BaseRequest.LOGOUT)
            {
                if(requeteBaseClient.getId() == BaseRequest.LOGIN_INITIATOR)
                {
                    RequestLoginInitiator requeteClient = (RequestLoginInitiator) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Traitement initialisation login");
                    System.out.println("Thread :" + this.toString() + "Traitement initialisation login");
                    
                    // Traitement initialisation login
                    SecureRandom sr = new SecureRandom();
                    byte[] byteSr = new byte[256];
                    sr.nextBytes(byteSr);
                    saltClient = Arrays.toString(byteSr);
                    requeteClient.setSaltChallenge(saltClient);
                    
                    oos.writeObject(requeteClient);
                }
                
                if(requeteBaseClient.getId() == BaseRequest.LOGIN_EID)
                {
                    RequestLogin requeteClient = (RequestLogin) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Traitement login EID");
                    System.out.println("Thread :" + this.toString() + "Traitement login EID");
                    
                    //Traitement login EID
                    if(saltClient != null)
                    {
                        ResultSet rs = beanJdbc.SelectAllWhere("personnel", "login = \"" + requeteClient.getUsername() + "\"", BeanJDBC.NO_UPDATE);
                        try {
                            if(rs.next())
                            {
                                KeyStore ks = KeyStore.getInstance("PKCS11");
                                // J'ai fait comme Camille ci-dessous mais on doit s'échanger un certif non ? (Dans la partie de Camille Provider pkcs11Provider = new sun.security.pkcs11.SunPKCS11(bais); est en erreur car bias n'est pas de type config)
                                ks.load(null, null);
                                X509Certificate certif = (X509Certificate) ks.getCertificate(null);
                                PublicKey pk = certif.getPublicKey();
                                Signature s = Signature.getInstance("SHA1withRSA");
                                s.initVerify(pk);
                                s.update(saltClient.getBytes());
                                if(s.verify(requeteClient.getDigest()))
                                    requeteClient.setStatus(true);
                                else
                                    requeteClient.setStatus(false);
                            }
                            else
                                requeteClient.setStatus(false);
                            
                        } catch (NoSuchAlgorithmException | CertificateException | KeyStoreException | InvalidKeyException | SQLException | SignatureException ex) {
                            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
                        } 
                    }
                    else
                        requeteClient.setStatus(false);
                    oos.writeObject(requeteClient);
                }
                
                if(requeteBaseClient.getId() == BaseRequest.LOGIN_CARTES_A_PUCES)
                {
                    RequestLogin requeteClient = (RequestLogin) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Traitement login carte à puces");
                    System.out.println("Thread :" + this.toString() + "Traitement login carte à puces");
                    
                    //Traitement login carte à puce
                    // Dois-je saller ???
                    if(saltClient != null)
                    {
                        ResultSet rs = beanJdbc.SelectAllWhere("personnel", "login = \"" + requeteClient.getUsername() + "\"", BeanJDBC.NO_UPDATE);
                        try {
                            if(rs.next())
                            {
                                MessageDigest md = MessageDigest.getInstance("SHA-256");
                                Vector<String> components = new Vector<String>();
                                // Faut-il saller dans ce cas-ci aussi ?? Je ne vois pas qu'il demande ça dans les consignes mais je me dis que si on ne salle pas autant envoyer le message en clair et ne pas faire de digest.
                                components.add(saltClient);
                                //Dans ce cas-ci login == username aussi
                                // Dois-je ajouter tout ça ???
                                String login = rs.getString("login");
                                components.add(login);
                                String pin = rs.getString("pin");
                                components.add(pin);
                                String password = rs.getString("password");
                                components.add(password);
                                String cptAcces = rs.getString("compteur acces");
                                components.add(cptAcces);
                                if(requeteClient.VerifyDigest(md, components))
                                {
                                    int tempCptAcces = Integer.parseInt(cptAcces);
                                    tempCptAcces++;
                                    // Mise à jour de la bdd pour l'incrémentation du compteur d'accès! 
                                    beanJdbc.Update("personnel", "login = \"" + requeteClient.getUsername() + "\"", "compteur acces", Integer.toString(tempCptAcces));
                                    requeteClient.setStatus(true);
                                }
                                else
                                    requeteClient.setStatus(false);
                            }
                            else
                                requeteClient.setStatus(false);
                        } catch (SQLException | NoSuchAlgorithmException ex) {
                            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else
                        requeteClient.setStatus(false);
                    
                    oos.writeObject(requeteClient);
                }
                
                if(requeteBaseClient.getId() == BaseRequest.LOGIN_CREATE_OTP)
                {
                    RequestLogin requeteClient = (RequestLogin) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Création de l'otp");
                    System.out.println("Thread :" + this.toString() + "Création de l'otp");
                    
                    //Création de l'otp
                    
                    oos.writeObject(requeteClient);
                }
                
                if(requeteBaseClient.getId() == BaseRequest.LOGIN_VERIFY_OTP)
                {
                    RequestLogin requeteClient = (RequestLogin) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Vérification de l'otp");
                    System.out.println("Thread :" + this.toString() + "Vérification de l'otp");
                    
                    //Vérification de l'otp
                    
                    oos.writeObject(requeteClient);
                }
                                
                if(requeteBaseClient.getId() == BaseRequest.LOGIN_WEB)
                {
                    RequestLogin requeteClient = (RequestLogin) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Traitement login WEB");
                    System.out.println("Thread :" + this.toString() + "Traitement login WEB");
                                        
                    // Traitement login WEB
                    
                    if(saltClient != null)
                    {
                        ResultSet rs = beanJdbc.SelectAllWhere("personnel", "login = \"" + requeteClient.getUsername() + "\"", BeanJDBC.NO_UPDATE); // Le username de la requeteClient (RequestLogin) correspond au login dans la bdd compta table personnel 
                        try {
                            if(rs.next())
                            {
                                MessageDigest md = MessageDigest.getInstance("SHA-1");
                                Vector<String> components = new Vector<String>();
                                components.add(saltClient);
                                String password = rs.getString("mot de passe");
                                components.add(password);
                                if(requeteClient.VerifyDigest(md, components))
                                {
                                    requeteClient.setStatus(true);
                                }
                                else
                                    requeteClient.setStatus(false);
                            }
                            else
                                requeteClient.setStatus(false);
                            
                        } catch (SQLException | NoSuchAlgorithmException ex) {
                            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else
                        requeteClient.setStatus(false);
                    
                    oos.writeObject(requeteClient);
                }
                
                if(requeteBaseClient.getId() == BaseRequest.DO_BIG_DATA)
                {
                    reponseClient = new RequestBigDataResult();
                    RequestDoBigData requeteClient = (RequestDoBigData) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Traitement BIG DATA");
                    System.out.println("Thread :" + this.toString() + "Traitement BIG DATA");
                    
                    //Traitement BIG DATA
                    
                    
                    if(requeteClient.getTypetraitement() == RequestDoBigData.ACP)
                    {
                        // Traitement de l'ACP (Faire un objet)
                    }
                    
                    if(requeteClient.getTypetraitement() == RequestDoBigData.ACM)
                    {
                        // Traitement de l'ACM (Faire un objet)
                    }
                    
                    if(requeteClient.getTypetraitement() == RequestDoBigData.REG_CORR)
                    {
                        // Traitement de la REG_CORR (Faire un objet)
                    }
                    if(requeteClient.getTypetraitement() == RequestDoBigData.ANOVA)
                    {
                        // Traitement de la ANOVA (Faire un objet)
                    }
                    oos.writeObject(reponseClient);
                }
            }
            
            closeConnexion();
        } catch (IOException | ClassNotFoundException ex) {
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
