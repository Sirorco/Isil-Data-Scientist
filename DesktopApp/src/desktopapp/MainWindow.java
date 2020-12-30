/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp;

import Protocol.BaseRequest;
import Protocol.RequestBigDataResult;
import Protocol.RequestDoBigData;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author cam_i
 */
public class MainWindow extends javax.swing.JFrame {

    private Socket socket;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;
    private boolean isDataScientist;
    
    /**
     * Creates new form MainWindow
     * @param host
     * @param port
     * @throws java.io.IOException
     */
    public MainWindow(String host,int port) throws IOException {
        initComponents();
        
        socket = new Socket(host,port);
        outputStream = new ObjectOutputStream(socket.getOutputStream());
        inputStream = new ObjectInputStream(socket.getInputStream());
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                try {
                    stop();
                } catch (IOException ex) {
                    Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public boolean login()
    {
        LoginDialog dialog = new LoginDialog(this,true,inputStream,outputStream);
        dialog.setVisible(true);
        return dialog.isOk();
    }
    
    public void stop() throws IOException
    {
        BaseRequest req = new BaseRequest();
        req.setId(BaseRequest.LOGOUT);
        
        outputStream.writeObject(req);
        
        socket.close();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mainTabbedPane = new javax.swing.JTabbedPane();
        viewPane = new javax.swing.JPanel();
        viewTabbedPane = new javax.swing.JTabbedPane();
        cahPane = new javax.swing.JPanel();
        cahTitle = new javax.swing.JLabel();
        cahGraphsPane = new javax.swing.JPanel();
        cahPlot1 = new javax.swing.JLabel();
        cahPlot2 = new javax.swing.JLabel();
        cahText1 = new javax.swing.JLabel();
        cahText2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        cahGlobal = new javax.swing.JLabel();
        cahDate = new javax.swing.JLabel();
        anovaPane = new javax.swing.JPanel();
        anovaTitle = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        anovaPlot = new javax.swing.JLabel();
        anovaText = new javax.swing.JLabel();
        anovaGlobal = new javax.swing.JLabel();
        anovaDate = new javax.swing.JLabel();
        acmPane = new javax.swing.JPanel();
        acmTitle = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        acmPlot = new javax.swing.JLabel();
        acmText = new javax.swing.JLabel();
        acmGlobal = new javax.swing.JLabel();
        acmDate = new javax.swing.JLabel();
        regPane = new javax.swing.JPanel();
        regTitle = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        regPlot = new javax.swing.JLabel();
        regText = new javax.swing.JLabel();
        regGlobal = new javax.swing.JLabel();
        regDate = new javax.swing.JLabel();
        refreshButton = new javax.swing.JButton();
        doPane = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DesktoppApp");

        mainTabbedPane.setName("View BigData"); // NOI18N

        viewPane.setLayout(new java.awt.BorderLayout());

        cahPane.setLayout(new java.awt.BorderLayout());
        cahPane.add(cahTitle, java.awt.BorderLayout.NORTH);

        java.awt.GridBagLayout cahGraphsPaneLayout = new java.awt.GridBagLayout();
        cahGraphsPaneLayout.columnWeights = new double[] {1.0, 1.0};
        cahGraphsPaneLayout.rowWeights = new double[] {4.0, 1.0};
        cahGraphsPane.setLayout(cahGraphsPaneLayout);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        cahGraphsPane.add(cahPlot1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        cahGraphsPane.add(cahPlot2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        cahGraphsPane.add(cahText1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        cahGraphsPane.add(cahText2, gridBagConstraints);

        cahPane.add(cahGraphsPane, java.awt.BorderLayout.CENTER);

        jPanel1.setLayout(new java.awt.GridLayout(2, 1));
        jPanel1.add(cahGlobal);
        jPanel1.add(cahDate);

        cahPane.add(jPanel1, java.awt.BorderLayout.SOUTH);

        viewTabbedPane.addTab("Bénéfices mensuels", cahPane);

        anovaPane.setLayout(new java.awt.BorderLayout());
        anovaPane.add(anovaTitle, java.awt.BorderLayout.NORTH);

        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.rowWeights = new double[] {4.0, 1.0, 1.0, 1.0};
        jPanel2.setLayout(jPanel2Layout);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel2.add(anovaPlot, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel2.add(anovaText, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel2.add(anovaGlobal, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        jPanel2.add(anovaDate, gridBagConstraints);

        anovaPane.add(jPanel2, java.awt.BorderLayout.CENTER);

        viewTabbedPane.addTab("Routes commerciales", anovaPane);

        acmPane.setLayout(new java.awt.BorderLayout());
        acmPane.add(acmTitle, java.awt.BorderLayout.NORTH);

        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.rowWeights = new double[] {4.0, 1.0, 1.0, 1.0};
        jPanel3.setLayout(jPanel3Layout);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel3.add(acmPlot, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel3.add(acmText, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel3.add(acmGlobal, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        jPanel3.add(acmDate, gridBagConstraints);

        acmPane.add(jPanel3, java.awt.BorderLayout.CENTER);

        viewTabbedPane.addTab("Routes particulières", acmPane);

        regPane.setLayout(new java.awt.BorderLayout());
        regPane.add(regTitle, java.awt.BorderLayout.NORTH);

        java.awt.GridBagLayout jPanel4Layout = new java.awt.GridBagLayout();
        jPanel4Layout.rowWeights = new double[] {4.0, 1.0, 1.0, 1.0};
        jPanel4.setLayout(jPanel4Layout);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        jPanel4.add(regPlot, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        jPanel4.add(regText, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        jPanel4.add(regGlobal, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        jPanel4.add(regDate, gridBagConstraints);

        regPane.add(jPanel4, java.awt.BorderLayout.CENTER);

        viewTabbedPane.addTab("Retards", regPane);

        viewPane.add(viewTabbedPane, java.awt.BorderLayout.CENTER);
        viewTabbedPane.getAccessibleContext().setAccessibleName("");

        refreshButton.setText("Refresh");
        refreshButton.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        refreshButton.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });
        viewPane.add(refreshButton, java.awt.BorderLayout.SOUTH);

        mainTabbedPane.addTab("View BigData", viewPane);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("TO DO");

        javax.swing.GroupLayout doPaneLayout = new javax.swing.GroupLayout(doPane);
        doPane.setLayout(doPaneLayout);
        doPaneLayout.setHorizontalGroup(
            doPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(doPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(743, Short.MAX_VALUE))
        );
        doPaneLayout.setVerticalGroup(
            doPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(doPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(774, Short.MAX_VALUE))
        );

        mainTabbedPane.addTab("Do BigData", doPane);

        getContentPane().add(mainTabbedPane, java.awt.BorderLayout.CENTER);
        mainTabbedPane.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        try {
            RequestBigDataResult resp;
            
            switch(viewTabbedPane.getSelectedIndex()){
                case 0:
                    resp = send(RequestDoBigData.CAH);
                    
                    cahGlobal.setText(resp.getValue(RequestBigDataResult.CAH_GLOBAL_TEXT).toString());
                    
                    cahTitle.setText(resp.getValue(RequestBigDataResult.CAH_GLOBAL_TITRE).toString());
                    
                    ImageIcon plotCAH1 = new ImageIcon((byte[])resp.getValue(RequestBigDataResult.CAH_PLOT_ONE));
                    cahPlot1.setIcon(plotCAH1);
                    cahText1.setText(resp.getValue(RequestBigDataResult.CAH_PLOT_ONE_TEXT).toString());
                    
                    ImageIcon plotCAH2 = new ImageIcon((byte[])resp.getValue(RequestBigDataResult.CAH_PLOT_TWO));
                    cahPlot2.setIcon(plotCAH2);
                    cahText2.setText(resp.getValue(RequestBigDataResult.CAH_PLOT_TWO_TEXT).toString());
                    
                    cahDate.setText(((Timestamp)resp.getValue(RequestBigDataResult.CAH_DATE)).toString());
                    break;
                    
                case 1:
                    resp = send(RequestDoBigData.ANOVA);
                    
                    anovaGlobal.setText(resp.getValue(RequestBigDataResult.ANOVA2_GLOBAL_TEXT).toString());
                    
                    anovaTitle.setText(resp.getValue(RequestBigDataResult.ANOVA2_GLOBAL_TITRE).toString());
                    
                    ImageIcon plotANOVA = new ImageIcon((byte[])resp.getValue(RequestBigDataResult.ANOVA2_PLOT_ONE));
                    anovaPlot.setIcon(plotANOVA);
                    anovaText.setText(resp.getValue(RequestBigDataResult.ANOVA2_PLOT_ONE_TEXT).toString());
                    
                    anovaDate.setText(((Timestamp)resp.getValue(RequestBigDataResult.ANOVA2_DATE)).toString());
                    break;
                    
                case 2:
                    resp = send(RequestDoBigData.ACM);
                    
                    acmGlobal.setText(resp.getValue(RequestBigDataResult.ACM_GLOBAL_TEXT).toString());
                    
                    acmTitle.setText(resp.getValue(RequestBigDataResult.ACM_GLOBAL_TITRE).toString());
                    
                    ImageIcon plotACM = new ImageIcon((byte[])resp.getValue(RequestBigDataResult.ACM_PLOT_ONE));
                    acmPlot.setIcon(plotACM);
                    acmText.setText(resp.getValue(RequestBigDataResult.ACM_PLOT_ONE_TEXT).toString());
                    
                    acmDate.setText(((Timestamp)resp.getValue(RequestBigDataResult.ACM_DATE)).toString());
                    break;
                    
                case 3:
                    resp = send(RequestDoBigData.REG_CORR);
                    
                    regGlobal.setText(resp.getValue(RequestBigDataResult.REGCORR_GLOBAL_TEXT).toString());
                    
                    regTitle.setText(resp.getValue(RequestBigDataResult.REGCORR_GLOBAL_TITRE).toString());
                    
                    ImageIcon plotREG = new ImageIcon((byte[])resp.getValue(RequestBigDataResult.REGCORR_PLOT_ONE));
                    regPlot.setIcon(plotREG);
                    regText.setText(resp.getValue(RequestBigDataResult.REGCORR_PLOT_ONE_TEXT).toString());
                    
                    regDate.setText(((Timestamp)resp.getValue(RequestBigDataResult.REGCORR_DATE)).toString());
                    break;
            }
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_refreshButtonActionPerformed

    void setDataScientist(boolean isdatascientist) {
        isDataScientist = isdatascientist;
        
        mainTabbedPane.setEnabledAt(1, isdatascientist);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel acmDate;
    private javax.swing.JLabel acmGlobal;
    private javax.swing.JPanel acmPane;
    private javax.swing.JLabel acmPlot;
    private javax.swing.JLabel acmText;
    private javax.swing.JLabel acmTitle;
    private javax.swing.JLabel anovaDate;
    private javax.swing.JLabel anovaGlobal;
    private javax.swing.JPanel anovaPane;
    private javax.swing.JLabel anovaPlot;
    private javax.swing.JLabel anovaText;
    private javax.swing.JLabel anovaTitle;
    private javax.swing.JLabel cahDate;
    private javax.swing.JLabel cahGlobal;
    private javax.swing.JPanel cahGraphsPane;
    private javax.swing.JPanel cahPane;
    private javax.swing.JLabel cahPlot1;
    private javax.swing.JLabel cahPlot2;
    private javax.swing.JLabel cahText1;
    private javax.swing.JLabel cahText2;
    private javax.swing.JLabel cahTitle;
    private javax.swing.JPanel doPane;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JButton refreshButton;
    private javax.swing.JLabel regDate;
    private javax.swing.JLabel regGlobal;
    private javax.swing.JPanel regPane;
    private javax.swing.JLabel regPlot;
    private javax.swing.JLabel regText;
    private javax.swing.JLabel regTitle;
    private javax.swing.JPanel viewPane;
    private javax.swing.JTabbedPane viewTabbedPane;
    // End of variables declaration//GEN-END:variables

    private RequestBigDataResult send(int type) throws IOException, ClassNotFoundException {
        RequestDoBigData req = new RequestDoBigData();
        req.setId(BaseRequest.DO_BIG_DATA);
        req.setTypetraitement(type);
        
        outputStream.writeObject(req);
        RequestBigDataResult resp = (RequestBigDataResult)inputStream.readObject();
        
        return resp;
    }
}
