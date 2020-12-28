/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur_data_analysis;

import connectionJdbc.BeanJDBC;
import static connectionJdbc.BeanJDBC.NO_UPDATE;
import connectionRServe.BeanRServe;
import java.awt.Image;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;

/**
 *
 * @author Aurélien Bolkaerts
 */
public class datamining extends javax.swing.JFrame {

    /**
     * Creates new form datamining
     */
    
    public datamining() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane3 = new javax.swing.JTabbedPane();
        DmSelectionTabbedPane = new javax.swing.JTabbedPane();
        RegCoorPanel = new javax.swing.JPanel();
        AnovaPanel = new javax.swing.JPanel();
        TitlePanel = new javax.swing.JPanel();
        TitleLabel = new javax.swing.JLabel();
        ImagePanel = new javax.swing.JPanel();
        ImageLabel = new javax.swing.JLabel();
        ConclusionPanel = new javax.swing.JPanel();
        CAHPanel = new javax.swing.JPanel();
        ACMPanel = new javax.swing.JPanel();
        ButtonsPanel = new javax.swing.JPanel();
        RefreshButton = new javax.swing.JButton();
        GetDMButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        javax.swing.GroupLayout RegCoorPanelLayout = new javax.swing.GroupLayout(RegCoorPanel);
        RegCoorPanel.setLayout(RegCoorPanelLayout);
        RegCoorPanelLayout.setHorizontalGroup(
            RegCoorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
        );
        RegCoorPanelLayout.setVerticalGroup(
            RegCoorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 366, Short.MAX_VALUE)
        );

        DmSelectionTabbedPane.addTab("Reg-Coor", RegCoorPanel);

        AnovaPanel.setLayout(new javax.swing.BoxLayout(AnovaPanel, javax.swing.BoxLayout.Y_AXIS));

        TitleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TitleLabel.setText("Nombre de containers en fonction des villes de départs et d'arrivées :");

        javax.swing.GroupLayout TitlePanelLayout = new javax.swing.GroupLayout(TitlePanel);
        TitlePanel.setLayout(TitlePanelLayout);
        TitlePanelLayout.setHorizontalGroup(
            TitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
            .addGroup(TitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TitlePanelLayout.createSequentialGroup()
                    .addGap(0, 85, Short.MAX_VALUE)
                    .addComponent(TitleLabel)
                    .addGap(0, 86, Short.MAX_VALUE)))
        );
        TitlePanelLayout.setVerticalGroup(
            TitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 136, Short.MAX_VALUE)
            .addGroup(TitlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TitlePanelLayout.createSequentialGroup()
                    .addGap(0, 60, Short.MAX_VALUE)
                    .addComponent(TitleLabel)
                    .addGap(0, 60, Short.MAX_VALUE)))
        );

        AnovaPanel.add(TitlePanel);

        javax.swing.GroupLayout ImagePanelLayout = new javax.swing.GroupLayout(ImagePanel);
        ImagePanel.setLayout(ImagePanelLayout);
        ImagePanelLayout.setHorizontalGroup(
            ImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagePanelLayout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(ImageLabel)
                .addContainerGap(333, Short.MAX_VALUE))
        );
        ImagePanelLayout.setVerticalGroup(
            ImagePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagePanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(ImageLabel)
                .addContainerGap(50, Short.MAX_VALUE))
        );

        AnovaPanel.add(ImagePanel);

        javax.swing.GroupLayout ConclusionPanelLayout = new javax.swing.GroupLayout(ConclusionPanel);
        ConclusionPanel.setLayout(ConclusionPanelLayout);
        ConclusionPanelLayout.setHorizontalGroup(
            ConclusionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
        );
        ConclusionPanelLayout.setVerticalGroup(
            ConclusionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 146, Short.MAX_VALUE)
        );

        AnovaPanel.add(ConclusionPanel);

        DmSelectionTabbedPane.addTab("Anova 2", AnovaPanel);

        javax.swing.GroupLayout CAHPanelLayout = new javax.swing.GroupLayout(CAHPanel);
        CAHPanel.setLayout(CAHPanelLayout);
        CAHPanelLayout.setHorizontalGroup(
            CAHPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
        );
        CAHPanelLayout.setVerticalGroup(
            CAHPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 366, Short.MAX_VALUE)
        );

        DmSelectionTabbedPane.addTab("CAH", CAHPanel);

        javax.swing.GroupLayout ACMPanelLayout = new javax.swing.GroupLayout(ACMPanel);
        ACMPanel.setLayout(ACMPanelLayout);
        ACMPanelLayout.setHorizontalGroup(
            ACMPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 569, Short.MAX_VALUE)
        );
        ACMPanelLayout.setVerticalGroup(
            ACMPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 366, Short.MAX_VALUE)
        );

        DmSelectionTabbedPane.addTab("ACM", ACMPanel);

        getContentPane().add(DmSelectionTabbedPane);

        ButtonsPanel.setLayout(new javax.swing.BoxLayout(ButtonsPanel, javax.swing.BoxLayout.LINE_AXIS));

        RefreshButton.setText("Refresh");
        RefreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RefreshButtonActionPerformed(evt);
            }
        });
        ButtonsPanel.add(RefreshButton);

        GetDMButton.setText("Get DM");
        GetDMButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GetDMButtonActionPerformed(evt);
            }
        });
        ButtonsPanel.add(GetDMButton);

        getContentPane().add(ButtonsPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private BeanJDBC beanJdbc;
    private BeanRServe beanRServ;
    
    private void RefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshButtonActionPerformed
        // TODO add your handling code here:
        connectToJDBC();
        connectToRServ();
        switch(DmSelectionTabbedPane.getSelectedIndex()){
            case 1:
                refreshRegCor();
                break;
                
            case 2:
                refreshAnova();
                break;
                
            case 3:
                refreshCAH();
                break;
                
            case 4:
                refreshACM();
                break;
        }
        
        //DebugResultSet(rs);
       
    }//GEN-LAST:event_RefreshButtonActionPerformed

    private void GetDMButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GetDMButtonActionPerformed
        // TODO add your handling code here:
        connectToJDBC();
        switch(DmSelectionTabbedPane.getSelectedIndex()){
            case 1:
                break;
                
            case 2:
                break;
                
            case 3:
                break;
                
            case 4:
                break;
        }
        //ConnectToRServ();
    }//GEN-LAST:event_GetDMButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(datamining.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(datamining.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(datamining.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(datamining.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new datamining().setVisible(true);
            }
        });    
    }
    
    
    public void connectToJDBC(){
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
        name = "jdbc:mysql://82.212.170.117:3306/bd_mouvements?useUnicode=true &useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false& serverTimezone=UTC";
        beanJdbc = new BeanJDBC(name, user, mdp);
    }
    
    public void connectToRServ(){
        beanRServ = new BeanRServe();
    }

    private void debugResultSet(ResultSet rs){
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void exportResultasDatasetInRServe(ResultSet rs, String dataSetTitle){
        String query;
        try {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            //System.out.println("colCount " + columnsNumber);
            
            //On met les données du resultset sous format c('....','...') pour les importer dans Rserve
            for(int i = 1; i <= columnsNumber; i++){
                query = rsmd.getColumnName(i) + "<- c(";
                //System.out.println("query " + query);
                while(rs.next()){
                    query += "'" + rs.getString(i) + "', ";
                    //System.out.println("query " + query);
                    
                }
                query = query.substring(0, query.length() - 2);
                query += ")";
                System.out.println(query);
                beanRServ.voidEval(query);

                rs.beforeFirst();    
            }
            
            query = dataSetTitle + " <- data.frame(";
            //On récupère les données et on les réunit dans un Dataframe
            for(int i = 1; i <= columnsNumber; i++){
                  query += rsmd.getColumnName(i) + ", ";
            }
            query = query.substring(0, query.length() - 2);
            query += ")";
            
            System.out.println(query);
            
            beanRServ.voidEval(query);
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void refreshAnova(){
        ResultSet rs = beanJdbc.ExecuteQuery(""
                + "select "
                + "     destination_villeDepart as 'Départ', "
                + "     destination_villeArrivee as 'Destination', "
                + "     count(destination_villeDepart) as 'NombreDeConteneurs' "
                + "from "
                + "     bd_mouvements.transports "
                + "group by "
                + "     destination_villeDepart, "
                + "     destination_villeArrivee");
        exportResultasDatasetInRServe(rs,"Roads");
        
        beanRServ.voidEval("Roads$NombreDeConteneurs=as.numeric(Roads$NombreDeConteneurs)");
        
        beanRServ.eval("png(file='Roads.png',width=1400,height=800)");
        beanRServ.parseAndEval("with(Roads,interaction.plot(destination_villeDepart,destination_villeArrivee,NombreDeConteneurs));dev.off()");
        
        REXP xp = beanRServ.parseAndEval("r=readBin('Roads.png','raw',1400*800)");
        beanRServ.parseAndEval("unlink('Roads.png');r");
        Image img = null;
        try {
            img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
        } catch (REXPMismatchException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("img = " + img);
        
        ImageLabel.setIcon(new ImageIcon(img));
        ImageLabel.repaint();
        
    }
    
    private void refreshRegCor(){
        
    }
    
    private void refreshCAH(){
        
    }
    
    private void refreshACM(){
        
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ACMPanel;
    private javax.swing.JPanel AnovaPanel;
    private javax.swing.JPanel ButtonsPanel;
    private javax.swing.JPanel CAHPanel;
    private javax.swing.JPanel ConclusionPanel;
    private javax.swing.JTabbedPane DmSelectionTabbedPane;
    private javax.swing.JButton GetDMButton;
    private javax.swing.JLabel ImageLabel;
    private javax.swing.JPanel ImagePanel;
    private javax.swing.JButton RefreshButton;
    private javax.swing.JPanel RegCoorPanel;
    private javax.swing.JLabel TitleLabel;
    private javax.swing.JPanel TitlePanel;
    private javax.swing.JTabbedPane jTabbedPane3;
    // End of variables declaration//GEN-END:variables
}
