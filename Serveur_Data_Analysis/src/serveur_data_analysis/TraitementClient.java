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
import Protocol.RequestLoginResponse;
import Protocol.RequestLogineID;
import connectionJdbc.BeanJDBC;
import connectionRServe.BeanRServe;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import traitementDM.Acm;
import traitementDM.Anova2;
import traitementDM.Cah;
import traitementDM.RegCor;

/**
 *
 * @author renardN
 */
public class TraitementClient implements Runnable {

    private MainFrame mF;
    private Socket cSocket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    
    //Objets pour le traitement DM :
    private Acm acm;
    private Anova2 anova2;
    private Cah cah;
    private RegCor regCor;

    private BeanJDBC beanJdbc; //Objet pour la connexion à la BDD
    
    private BeanRServe beanRServe; //Objet pour la connexion à R

    public TraitementClient(Socket cSocket, MainFrame mF) {
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

        //Chargement du driver de MYSQL
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        beanJdbc = new BeanJDBC(name, user, mdp);
        beanRServe = new BeanRServe();
        
    }

    @Override
    public void run() {
        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Lancement du thread de traitement client !");
        System.out.println("Thread : " + this.toString() + " Lancement du thread de traitement client !");
        BaseRequest requeteBaseClient = null;
        BaseRequest reponseClient = null;
        String saltClient = null;

        try {
            while ((requeteBaseClient = (BaseRequest) ois.readObject()).getId() != BaseRequest.LOGOUT) {
                if (requeteBaseClient.getId() == BaseRequest.LOGIN_INITIATOR) {
                    mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Traitement initialisation login");
                    System.out.println("Thread : " + this.toString() + " Traitement initialisation login");

                    // Traitement initialisation login
                    SecureRandom sr = new SecureRandom();
                    byte[] byteSr = new byte[256];
                    sr.nextBytes(byteSr);
                    saltClient = Arrays.toString(byteSr);
                    reponseClient = new RequestLoginInitiator();
                    ((RequestLoginInitiator) reponseClient).setSaltChallenge(saltClient);
                    mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Sallage construit !");
                    System.out.println("Thread : " + this.toString() + " Sallage construit !");
                    reponseClient.setStatus(true);
                }

                if (requeteBaseClient.getId() == BaseRequest.LOGIN_EID) {
                    reponseClient = new RequestLoginResponse();
                    RequestLogineID requeteClient = (RequestLogineID) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Traitement login EID");
                    System.out.println("Thread :" + this.toString() + " Traitement login EID");

                    //Traitement login EID
                    if (saltClient != null) {
                        ResultSet rs = beanJdbc.SelectAllWhere("personnel", "login = \"" + requeteClient.getUsername() + "\"", BeanJDBC.NO_UPDATE);
                        if (rs.next()) {
                            X509Certificate certif = (X509Certificate) requeteClient.geteIDcertificate();
                            
                            try {
                                certif.checkValidity();
                                
                                if(checkOCSP(certif))
                                {
                                    //vérification signature
                                    PublicKey pk = certif.getPublicKey();
                                    Signature s = Signature.getInstance("SHA1withRSA");
                                    s.initVerify(pk);
                                    s.update(saltClient.getBytes());
                                    if (s.verify(requeteClient.getDigest())) {
                                        if (rs.getString("fonction").equalsIgnoreCase("Datascientist")) {
                                            ((RequestLoginResponse) reponseClient).setIsdatascientist(true);
                                        } else {
                                            ((RequestLoginResponse) reponseClient).setIsdatascientist(false);
                                        }
                                        reponseClient.setStatus(true);

                                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Personne authentifiée !");
                                        System.out.println("Thread : " + this.toString() + " Personne authentifiée !");
                                    } else {
                                        reponseClient.setStatus(false);
                                    }
                                }
                                else
                                    reponseClient.setStatus(false);
                                
                            } catch (CertificateExpiredException | CertificateNotYetValidException ex) {
                                Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
                                reponseClient.setStatus(false);
                            }

                        } else {
                            reponseClient.setStatus(false);
                        }
                    } else {
                        reponseClient.setStatus(false);
                    }

                    if (!reponseClient.getStatus()) {
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Personne non authentifiée !");
                        System.out.println("Thread : " + this.toString() + " Personne non authentifiée !");
                    }
                }

                if (requeteBaseClient.getId() == BaseRequest.LOGIN_CARTES_A_PUCES) {
                    reponseClient = new RequestLoginResponse();
                    RequestLogin requeteClient = (RequestLogin) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Traitement login carte à puces");
                    System.out.println("Thread :" + this.toString() + "Traitement login carte à puces");

                    
                    ResultSet rs = beanJdbc.SelectAllWhere("personnel", "login = \"" + requeteClient.getUsername() + "\"", BeanJDBC.NO_UPDATE);
                    try {
                        if (rs.next()) {
                            MessageDigest md = MessageDigest.getInstance("SHA-256");
                            Vector<String> components = new Vector<String>();
                            String pin = rs.getString("pin");
                            components.add(pin);
                            String cptAcces = rs.getString("compteurAcces");
                            mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + " Compteur d'accès = " + cptAcces);
                            System.out.println("Thread :" + this.toString() + " Compteur d'accès = " + cptAcces);
                            components.add(cptAcces);
                            if (requeteClient.VerifyDigest(md, components)) {
                                int tempCptAcces = Integer.parseInt(cptAcces);
                                tempCptAcces++;

                                if (rs.getString("fonction").equalsIgnoreCase("Datascientist")) {
                                    ((RequestLoginResponse) reponseClient).setIsdatascientist(true);
                                } else {
                                    ((RequestLoginResponse) reponseClient).setIsdatascientist(false);
                                }

                                beanJdbc.Update("personnel", "login = \"" + requeteClient.getUsername() + "\"", "compteurAcces", Integer.toString(tempCptAcces));
                                mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + " Personne authentifiée !");
                                System.out.println("Thread :" + this.toString() + " Personne authentifiée !");
                                mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + " Compteur d'accès mis à jour : " + Integer.toString(tempCptAcces));
                                System.out.println("Thread :" + this.toString() + " Compteur d'accès mis à jour : " + Integer.toString(tempCptAcces));

                                reponseClient.setStatus(true);
                            } else {
                                reponseClient.setStatus(false);
                            }
                        } else {
                            reponseClient.setStatus(false);
                        }
                    } catch (SQLException | NoSuchAlgorithmException ex) {
                        Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (!reponseClient.getStatus()) {
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Personne non authentifiée !");
                        System.out.println("Thread : " + this.toString() + " Personne non authentifiée !");
                    }
                }

                if (requeteBaseClient.getId() == BaseRequest.LOGIN_OTP) {
                    reponseClient = new RequestLoginResponse();
                    RequestLogin requeteClient = (RequestLogin) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + "Création de l'otp");
                    System.out.println("Thread :" + this.toString() + "Création de l'otp");

                    //Création de l'otp
                    if (saltClient != null) {
                        ResultSet rs = beanJdbc.SelectAllWhere("personnel", "login = \"" + requeteClient.getUsername() + "\"", BeanJDBC.NO_UPDATE); // Le username de la requeteClient (RequestLogin) correspond au login dans la bdd compta table personnel
                        try {
                            if (rs.next()) {
                                MessageDigest md = MessageDigest.getInstance("SHA-256");
                                Vector<String> components = new Vector<String>();
                                components.add(saltClient);
                                String login = rs.getString("login");
                                components.add(login);
                                String pin = rs.getString("pin");
                                components.add(pin);
                                if (requeteClient.VerifyDigest(md, components)) {
                                    if (rs.getString("fonction").equalsIgnoreCase("Datascientist")) {
                                        ((RequestLoginResponse) reponseClient).setIsdatascientist(true);
                                    } else {
                                        ((RequestLoginResponse) reponseClient).setIsdatascientist(false);
                                    }
                                    reponseClient.setStatus(true);

                                    mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Personne authentifiée !");
                                    System.out.println("Thread : " + this.toString() + " Personne authentifiée !");
                                } else {
                                    reponseClient.setStatus(false);
                                }
                            } else {
                                reponseClient.setStatus(false);
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (NoSuchAlgorithmException ex) {
                            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        reponseClient.setStatus(false);
                    }

                    if (!reponseClient.getStatus()) {
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Personne non authentifiée !");
                        System.out.println("Thread : " + this.toString() + " Personne non authentifiée !");
                    }
                }

                if (requeteBaseClient.getId() == BaseRequest.LOGIN_WEB) {
                    reponseClient = new RequestLoginResponse();
                    RequestLogin requeteClient = (RequestLogin) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Traitement login WEB");
                    System.out.println("Thread : " + this.toString() + " Traitement login WEB");

                    // Traitement login WEB
                    if (saltClient != null) {
                        ResultSet rs = beanJdbc.SelectAllWhere("personnel", "login = \"" + requeteClient.getUsername() + "\"", BeanJDBC.NO_UPDATE); // Le username de la requeteClient (RequestLogin) correspond au login dans la bdd compta table personnel 
                        try {
                            if (rs.next()) {
                                MessageDigest md = MessageDigest.getInstance("SHA-1");
                                Vector<String> components = new Vector<String>();
                                components.add(saltClient);
                                String password = rs.getString("motDePasse");
                                components.add(password);
                                if (requeteClient.VerifyDigest(md, components)) {
                                    if (rs.getString("fonction").equalsIgnoreCase("Datascientist")) {
                                        ((RequestLoginResponse) reponseClient).setIsdatascientist(true);
                                    } else {
                                        ((RequestLoginResponse) reponseClient).setIsdatascientist(false);
                                    }

                                    mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Personne authentifiée !");
                                    System.out.println("Thread : " + this.toString() + " Personne authentifiée !");

                                    reponseClient.setStatus(true);
                                } else {
                                    reponseClient.setStatus(false);
                                }
                            } else {
                                reponseClient.setStatus(false);
                            }

                        } catch (SQLException | NoSuchAlgorithmException ex) {
                            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        reponseClient.setStatus(false);
                    }
                    if (!reponseClient.getStatus()) {
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Personne non authentifiée !");
                        System.out.println("Thread : " + this.toString() + " Personne non authentifiée !");
                    }
                }

                if (requeteBaseClient.getId() == BaseRequest.BIG_DATA_RESULT) {
                    reponseClient = new RequestBigDataResult();
                    RequestDoBigData requeteClient = (RequestDoBigData) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Traitement BIG DATA (GET GRAPH)");
                    System.out.println("Thread : " + this.toString() + " Traitement BIG DATA (GET GRAPH)");

                    //Traitement BIG DATA
                    if (requeteClient.getTypetraitement() == RequestDoBigData.CAH) {
                        cah.getDMCAH();
                        ((RequestBigDataResult)reponseClient).setData(cah.getDataset());
                        reponseClient.setStatus(true);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " CAH envoyé !");
                        System.out.println("Thread : " + this.toString() + " CAH envoyé !");
                    }
                    else
                    {
                        reponseClient.setStatus(false);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Erreur envoi CAH !");
                        System.out.println("Thread : " + this.toString() + " Erreur envoi CAH !");
                    }
                    
                    if (requeteClient.getTypetraitement() == RequestDoBigData.ACM) {
                        acm.getDMACM();
                        ((RequestBigDataResult)reponseClient).setData(acm.getDataset());
                        reponseClient.setStatus(true);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " ACM envoyé !");
                        System.out.println("Thread : " + this.toString() + " ACM envoyé !");
                    }
                    else
                    {
                        reponseClient.setStatus(false);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Erreur envoi ACM !");
                        System.out.println("Thread : " + this.toString() + " Erreur envoi ACM !");
                    }

                    if (requeteClient.getTypetraitement() == RequestDoBigData.REG_CORR) {
                        regCor.getDMRegCor();
                        ((RequestBigDataResult)reponseClient).setData(regCor.getDataset());
                        reponseClient.setStatus(true);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " REG-COR envoyé !");
                        System.out.println("Thread : " + this.toString() + " REG-COR envoyé !");
                    }
                    else
                    {
                        reponseClient.setStatus(false);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Erreur envoi REG-COR !");
                        System.out.println("Thread : " + this.toString() + " Erreur envoi REG-COR !");
                    }
                    
                    if (requeteClient.getTypetraitement() == RequestDoBigData.ANOVA) {
                        anova2.getDMAnova();
                        ((RequestBigDataResult)reponseClient).setData(anova2.getDataset());
                        reponseClient.setStatus(true);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " ANOVA2 envoyé !");
                        System.out.println("Thread : " + this.toString() + " ANOVA2 envoyé !");
                    }
                    else
                    {
                        reponseClient.setStatus(false);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Erreur envoi ANOVA2 !");
                        System.out.println("Thread : " + this.toString() + " Erreur envoi ANOVA2 !");
                    }
                }
                
                if (requeteBaseClient.getId() == BaseRequest.DO_BIG_DATA) {
                    reponseClient = new RequestBigDataResult();
                    RequestDoBigData requeteClient = (RequestDoBigData) requeteBaseClient;
                    mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Traitement BIG DATA (REFRESH GRAPH)");
                    System.out.println("Thread : " + this.toString() + " Traitement BIG DATA (REFRESH GRAPH)");

                    //Traitement BIG DATA
                    if (requeteClient.getTypetraitement() == RequestDoBigData.CAH) {
                        cah.refreshCAH();
                        ((RequestBigDataResult)reponseClient).setData(cah.getDataset());
                        reponseClient.setStatus(true);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " CAH rafraichi et envoyé !");
                        System.out.println("Thread : " + this.toString() + " CAH rafraichi et envoyé !");
                    }
                    else
                    {
                        reponseClient.setStatus(false);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Erreur envoi CAH !");
                        System.out.println("Thread : " + this.toString() + " Erreur envoi CAH !");
                    }
                    
                    if (requeteClient.getTypetraitement() == RequestDoBigData.ACM) {
                        acm.refreshACM();
                        ((RequestBigDataResult)reponseClient).setData(acm.getDataset());
                        reponseClient.setStatus(true);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " ACM rafraichi et envoyé !");
                        System.out.println("Thread : " + this.toString() + " ACM rafraichi et  envoyé !");
                    }
                    else
                    {
                        reponseClient.setStatus(false);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Erreur envoi ACM !");
                        System.out.println("Thread : " + this.toString() + " Erreur envoi ACM !");
                    }

                    if (requeteClient.getTypetraitement() == RequestDoBigData.REG_CORR) {
                        regCor.refreshRegCor();
                        ((RequestBigDataResult)reponseClient).setData(regCor.getDataset());
                        reponseClient.setStatus(true);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " REG-COR rafraichi et envoyé !");
                        System.out.println("Thread : " + this.toString() + " REG-COR rafraichi et envoyé !");
                    }
                    else
                    {
                        reponseClient.setStatus(false);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Erreur envoi REG-COR !");
                        System.out.println("Thread : " + this.toString() + " Erreur envoi REG-COR !");
                    }
                    
                    if (requeteClient.getTypetraitement() == RequestDoBigData.ANOVA) {
                        anova2.refreshAnova();
                        ((RequestBigDataResult)reponseClient).setData(anova2.getDataset());
                        reponseClient.setStatus(true);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " ANOVA2 rafraichi et envoyé !");
                        System.out.println("Thread : " + this.toString() + " ANOVA2 rafraichi et envoyé !");
                    }
                    else
                    {
                        reponseClient.setStatus(false);
                        mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Erreur envoi ANOVA2 !");
                        System.out.println("Thread : " + this.toString() + " Erreur envoi ANOVA2 !");
                    }
                }
                
                oos.writeObject(reponseClient);
                mF.getjTextFieldLogServeur().setText("Thread : " + this.toString() + " Objet envoyé au client !");
                System.out.println("Thread : " + this.toString() + " Objet envoyé au client !");
            }

            closeConnexion();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void closeConnexion() {
        try {
            ois.close();
            oos.close();
            if (!cSocket.isClosed()) {
                cSocket.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(TraitementClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        mF.getjTextFieldLogServeur().setText("Thread :" + this.toString() + " LOGOUT du client !!");
        System.out.println("Thread :" + this.toString() + " LOGOUT du client !!");
    }

    private boolean checkOCSP(X509Certificate certif) {
        return true;
    }

}
