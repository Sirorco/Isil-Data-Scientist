/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp;

import Protocol.BaseRequest;
import Protocol.RequestLogin;
import Protocol.RequestLoginInitiator;
import Protocol.RequestLoginResponse;
import Protocol.RequestLogineID;
import Utils.Cards.CartePuceApplet;
import com.sun.javacard.apduio.Apdu;
import com.sun.javacard.apduio.CadClient;
import com.sun.javacard.apduio.TLP224Exception;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Vector;
import javax.smartcardio.*;
import javax.swing.JOptionPane;

/**
 *
 * @author cam_i
 */
public class LoginDialog extends javax.swing.JDialog {
    
    private boolean ok;
    
    byte[] selectAppCommand = {(byte)0x00,(byte)0xA4,(byte)0x04,(byte)0x00,(byte)0x06};
    byte[] AID = {00, 00, 00, 00, 01, 01};
    byte[] pinCommand = {CartePuceApplet.CartePuceLogin_CLA,CartePuceApplet.VERIFY,(byte)0x00,(byte)0x00,(byte)0x04};
    byte[] getLogins= {CartePuceApplet.CartePuceLogin_CLA,CartePuceApplet.GET_LOGINS,(byte)0x00,(byte)0x00,(byte)0x00};
    byte[] loginDone ={CartePuceApplet.CartePuceLogin_CLA,CartePuceApplet.LOGIN_DONE,(byte)0x00,(byte)0x00,(byte)0x00};
    
    private Socket carteSo;
    private CadClient cad;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    
    /**
     * Creates new form LoginDialog
     * @param parent
     * @param modal
     * @param inputStream
     * @param outputStream
     */
    public LoginDialog(java.awt.Frame parent, boolean modal,ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        super(parent, modal);
        initComponents();
        
        ok=false;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        puceRadioButton = new javax.swing.JRadioButton();
        IdRadioButton = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        loginText = new javax.swing.JTextField();
        okButton = new javax.swing.JButton();

        setTitle("Login");

        jLabel1.setText("Moyen de login:");

        buttonGroup1.add(puceRadioButton);
        puceRadioButton.setSelected(true);
        puceRadioButton.setText("Carte à puce");

        buttonGroup1.add(IdRadioButton);
        IdRadioButton.setText("Carte d'identité");

        jLabel2.setText("Login :");

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(IdRadioButton)
                            .addComponent(puceRadioButton)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(okButton)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(loginText, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(131, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(puceRadioButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(IdRadioButton)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(loginText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(74, 74, 74)
                .addComponent(okButton)
                .addContainerGap(90, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        try
        {
            if(puceRadioButton.isSelected())
                loginPuce();
            else
                loginId();
            if(ok)
                setVisible(false);
        }
        catch(Exception ex)
        {
            System.err.println("OUPS : "+ex.getMessage());
        }
    }//GEN-LAST:event_okButtonActionPerformed

    public boolean isOk()
    {
        return ok;
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton IdRadioButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField loginText;
    private javax.swing.JButton okButton;
    private javax.swing.JRadioButton puceRadioButton;
    // End of variables declaration//GEN-END:variables

    private void loginPuce() throws IOException, CardException, TLP224Exception, NoSuchAlgorithmException, ClassNotFoundException {
        Apdu apdu = new Apdu();;
        if(carteSo == null)
        {
            carteSo = new Socket("localhost",9025);
            OutputStream os = carteSo.getOutputStream();
            InputStream is = carteSo.getInputStream();

            cad = new CadClient(is,os);
            cad.powerUp();

            apdu.setDataIn(AID);
            apdu.command = selectAppCommand;
            cad.exchangeApdu(apdu);
            if(apdu.getStatus() != 0x9000)
                return;
        }
        
        //vérification du pin
        boolean pinOk = false;
        String pin;
        do
        {
            pin = JOptionPane.showInputDialog(this, "Entrez votre code pin :");
            if(pin.length() != 4)
            {
                JOptionPane.showMessageDialog(this, "Wrong pin, try again", "AOUCH", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            apdu.command = pinCommand;
            byte[] bytes = pin.getBytes();
            for(int i=0; i< bytes.length; i++)
                bytes[i] -= 0x30;
            apdu.setDataIn(bytes);
            cad.exchangeApdu(apdu);
            if(apdu.getStatus() != 0x9000)
                JOptionPane.showMessageDialog(this, "Wrong pin, try again", "AOUCH", JOptionPane.ERROR_MESSAGE);
            else
                pinOk=true;
        }
        while(!pinOk);
        
        //récupération du nombre de login
        apdu.command = getLogins;
        apdu.setDataIn(new byte[0]);
        apdu.Le = 0X02;
        cad.exchangeApdu(apdu);
        byte[] data = apdu.getDataOut();
        int nbreCo = Byte.toUnsignedInt(data[0])*256 + Byte.toUnsignedInt(data[1]);
        
        System.out.println("Nbre de connexions :"+nbreCo);
        Vector<String> values = new Vector();
        values.add(pin);
        values.add(Integer.toString(nbreCo));
        
        if(sendLoginPuce(values))
        {
            //Si server ok
            apdu.command = loginDone;
            apdu.setDataIn(new byte[0]);
            apdu.Le = 0X00;
            cad.exchangeApdu(apdu);

            //vérification du nombre de login
            apdu.command = getLogins;
            apdu.setDataIn(new byte[0]);
            apdu.Le = 0X02;
            cad.exchangeApdu(apdu);
            data = apdu.getDataOut();
            nbreCo = Byte.toUnsignedInt(data[0])*256 + Byte.toUnsignedInt(data[1]);

            System.out.println("Nbre de connexions :"+nbreCo);

            cad.powerDown();
            cad.close();
            ok = true;
        }
    }

    private void loginId() throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException, InvalidKeyException, ClassNotFoundException, SignatureException {
        //Demande un challenge
        RequestLoginInitiator reqChall = new RequestLoginInitiator();
        reqChall.setId(BaseRequest.LOGIN_INITIATOR);
        
        outputStream.writeObject(reqChall);
        reqChall = (RequestLoginInitiator)inputStream.readObject();
        
        String challenge = reqChall.getSaltChallenge();

        //ajout du provider - sinon, l'ajouter dans java.security
        ByteArrayInputStream bais = new ByteArrayInputStream("name = beid\nlibrary = C:\\Windows\\System32\\beidpkcs11.dll".getBytes());
        Provider pkcs11Provider = new sun.security.pkcs11.SunPKCS11(bais);
        Security.addProvider(pkcs11Provider);
        
        KeyStore idKs = KeyStore.getInstance("PKCS11");
        idKs.load(null, null);
        
        PrivateKey cle = (PrivateKey) idKs.getKey("Signature",  null);
        Signature s = Signature.getInstance("SHA1withRSA");
        s.initSign(cle);
        s.update(challenge.getBytes());
        
        RequestLogineID req = new RequestLogineID();
        req.setId(BaseRequest.LOGIN_EID);
        req.setUsername(loginText.getText());
        req.setDigest(s.sign());
        req.seteIDcertificate(idKs.getCertificate("Signature"));
        
        outputStream.writeObject(req);
        RequestLoginResponse resp = (RequestLoginResponse)inputStream.readObject();
        
        MainWindow parent  = (MainWindow) getParent();
        parent.setDataScientist(resp.isIsdatascientist());
        ok = resp.getStatus();
    }
    
    private boolean sendLoginPuce(Vector<String> values) throws NoSuchAlgorithmException, IOException, ClassNotFoundException
    {
        RequestLogin req = new RequestLogin();
        req.setId(BaseRequest.LOGIN_CARTES_A_PUCES);
        req.setUsername(loginText.getText());
        
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        req.CalculateDigest(md, values);
        
        outputStream.writeObject(req);
        RequestLoginResponse resp = (RequestLoginResponse)inputStream.readObject();
        
        MainWindow parent  = (MainWindow) getParent();
        parent.setDataScientist(resp.isIsdatascientist());
        return resp.getStatus();
    }
}
