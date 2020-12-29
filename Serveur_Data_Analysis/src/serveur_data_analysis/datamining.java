/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur_data_analysis;

import connectionJdbc.BeanJDBC;
import connectionRServe.BeanRServe;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
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
        TitlePanelAnova = new javax.swing.JPanel();
        TitleLabelAnova = new javax.swing.JLabel();
        ImagePanelAnova = new javax.swing.JPanel();
        ImageLabelAnova = new javax.swing.JLabel();
        ConclusionPanelAnova = new javax.swing.JPanel();
        DescriptionLabelAnova = new javax.swing.JLabel();
        ConclusionLabelAnova = new javax.swing.JLabel();
        CAHPanel = new javax.swing.JPanel();
        TitlePanelCAH = new javax.swing.JPanel();
        TitleLabelCAH = new javax.swing.JLabel();
        ImagePanelCAH = new javax.swing.JPanel();
        imgPanel1 = new javax.swing.JPanel();
        ImageLabelCAH = new javax.swing.JLabel();
        DescriptionLabelCAH = new javax.swing.JLabel();
        imgPanel2 = new javax.swing.JPanel();
        ImageLabelCAH2 = new javax.swing.JLabel();
        DescriptionLabelCAH2 = new javax.swing.JLabel();
        ConclusionPanelCAH = new javax.swing.JPanel();
        ConclusionLabelCAH = new javax.swing.JLabel();
        ACMPanel = new javax.swing.JPanel();
        TitlePanelACM = new javax.swing.JPanel();
        TitleLabelACM = new javax.swing.JLabel();
        ImagePanelACM = new javax.swing.JPanel();
        ImageLabelACM = new javax.swing.JLabel();
        ConclusionPanelACM = new javax.swing.JPanel();
        DescriptionLabelACM = new javax.swing.JLabel();
        ConclusionLabelACM = new javax.swing.JLabel();
        ButtonsPanel = new javax.swing.JPanel();
        RefreshButton = new javax.swing.JButton();
        GetDMButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        javax.swing.GroupLayout RegCoorPanelLayout = new javax.swing.GroupLayout(RegCoorPanel);
        RegCoorPanel.setLayout(RegCoorPanelLayout);
        RegCoorPanelLayout.setHorizontalGroup(
            RegCoorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 805, Short.MAX_VALUE)
        );
        RegCoorPanelLayout.setVerticalGroup(
            RegCoorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );

        DmSelectionTabbedPane.addTab("Reg-Coor", RegCoorPanel);

        AnovaPanel.setLayout(new javax.swing.BoxLayout(AnovaPanel, javax.swing.BoxLayout.Y_AXIS));

        TitleLabelAnova.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TitleLabelAnova.setText("Nombre de containers en fonction des villes de départs et d'arrivées :");

        javax.swing.GroupLayout TitlePanelAnovaLayout = new javax.swing.GroupLayout(TitlePanelAnova);
        TitlePanelAnova.setLayout(TitlePanelAnovaLayout);
        TitlePanelAnovaLayout.setHorizontalGroup(
            TitlePanelAnovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 805, Short.MAX_VALUE)
            .addGroup(TitlePanelAnovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TitlePanelAnovaLayout.createSequentialGroup()
                    .addGap(0, 203, Short.MAX_VALUE)
                    .addComponent(TitleLabelAnova)
                    .addGap(0, 204, Short.MAX_VALUE)))
        );
        TitlePanelAnovaLayout.setVerticalGroup(
            TitlePanelAnovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 160, Short.MAX_VALUE)
            .addGroup(TitlePanelAnovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TitlePanelAnovaLayout.createSequentialGroup()
                    .addGap(0, 72, Short.MAX_VALUE)
                    .addComponent(TitleLabelAnova)
                    .addGap(0, 72, Short.MAX_VALUE)))
        );

        AnovaPanel.add(TitlePanelAnova);

        javax.swing.GroupLayout ImagePanelAnovaLayout = new javax.swing.GroupLayout(ImagePanelAnova);
        ImagePanelAnova.setLayout(ImagePanelAnovaLayout);
        ImagePanelAnovaLayout.setHorizontalGroup(
            ImagePanelAnovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagePanelAnovaLayout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(ImageLabelAnova)
                .addContainerGap(569, Short.MAX_VALUE))
        );
        ImagePanelAnovaLayout.setVerticalGroup(
            ImagePanelAnovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagePanelAnovaLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(ImageLabelAnova)
                .addContainerGap(114, Short.MAX_VALUE))
        );

        AnovaPanel.add(ImagePanelAnova);

        DescriptionLabelAnova.setText("Graph-description");

        ConclusionLabelAnova.setText("Conclusion");

        javax.swing.GroupLayout ConclusionPanelAnovaLayout = new javax.swing.GroupLayout(ConclusionPanelAnova);
        ConclusionPanelAnova.setLayout(ConclusionPanelAnovaLayout);
        ConclusionPanelAnovaLayout.setHorizontalGroup(
            ConclusionPanelAnovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConclusionPanelAnovaLayout.createSequentialGroup()
                .addGap(332, 332, 332)
                .addGroup(ConclusionPanelAnovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DescriptionLabelAnova)
                    .addComponent(ConclusionLabelAnova))
                .addContainerGap(372, Short.MAX_VALUE))
        );
        ConclusionPanelAnovaLayout.setVerticalGroup(
            ConclusionPanelAnovaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConclusionPanelAnovaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DescriptionLabelAnova)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ConclusionLabelAnova)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        AnovaPanel.add(ConclusionPanelAnova);

        DmSelectionTabbedPane.addTab("Anova 2", AnovaPanel);

        CAHPanel.setLayout(new javax.swing.BoxLayout(CAHPanel, javax.swing.BoxLayout.Y_AXIS));

        TitleLabelCAH.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TitleLabelCAH.setText("Bénéfices mensuels");

        javax.swing.GroupLayout TitlePanelCAHLayout = new javax.swing.GroupLayout(TitlePanelCAH);
        TitlePanelCAH.setLayout(TitlePanelCAHLayout);
        TitlePanelCAHLayout.setHorizontalGroup(
            TitlePanelCAHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TitlePanelCAHLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TitleLabelCAH)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        TitlePanelCAHLayout.setVerticalGroup(
            TitlePanelCAHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TitlePanelCAHLayout.createSequentialGroup()
                .addGap(0, 72, Short.MAX_VALUE)
                .addComponent(TitleLabelCAH)
                .addGap(0, 71, Short.MAX_VALUE))
        );

        CAHPanel.add(TitlePanelCAH);

        ImagePanelCAH.setLayout(new javax.swing.BoxLayout(ImagePanelCAH, javax.swing.BoxLayout.LINE_AXIS));

        imgPanel1.setLayout(new javax.swing.BoxLayout(imgPanel1, javax.swing.BoxLayout.Y_AXIS));
        imgPanel1.add(ImageLabelCAH);

        DescriptionLabelCAH.setText("Graph-description");
        imgPanel1.add(DescriptionLabelCAH);

        ImagePanelCAH.add(imgPanel1);

        imgPanel2.setLayout(new javax.swing.BoxLayout(imgPanel2, javax.swing.BoxLayout.Y_AXIS));
        imgPanel2.add(ImageLabelCAH2);

        DescriptionLabelCAH2.setText("Graph-description");
        imgPanel2.add(DescriptionLabelCAH2);

        ImagePanelCAH.add(imgPanel2);

        CAHPanel.add(ImagePanelCAH);

        ConclusionLabelCAH.setText("Conclusion");

        javax.swing.GroupLayout ConclusionPanelCAHLayout = new javax.swing.GroupLayout(ConclusionPanelCAH);
        ConclusionPanelCAH.setLayout(ConclusionPanelCAHLayout);
        ConclusionPanelCAHLayout.setHorizontalGroup(
            ConclusionPanelCAHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConclusionPanelCAHLayout.createSequentialGroup()
                .addGap(372, 372, 372)
                .addComponent(ConclusionLabelCAH)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ConclusionPanelCAHLayout.setVerticalGroup(
            ConclusionPanelCAHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConclusionPanelCAHLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(ConclusionLabelCAH)
                .addContainerGap(145, Short.MAX_VALUE))
        );

        CAHPanel.add(ConclusionPanelCAH);

        DmSelectionTabbedPane.addTab("CAH", CAHPanel);

        ACMPanel.setLayout(new javax.swing.BoxLayout(ACMPanel, javax.swing.BoxLayout.Y_AXIS));

        TitleLabelACM.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TitleLabelACM.setText("Routes commerciales particulières");

        javax.swing.GroupLayout TitlePanelACMLayout = new javax.swing.GroupLayout(TitlePanelACM);
        TitlePanelACM.setLayout(TitlePanelACMLayout);
        TitlePanelACMLayout.setHorizontalGroup(
            TitlePanelACMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 805, Short.MAX_VALUE)
            .addGroup(TitlePanelACMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TitlePanelACMLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(TitleLabelACM)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        TitlePanelACMLayout.setVerticalGroup(
            TitlePanelACMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 104, Short.MAX_VALUE)
            .addGroup(TitlePanelACMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(TitlePanelACMLayout.createSequentialGroup()
                    .addGap(0, 44, Short.MAX_VALUE)
                    .addComponent(TitleLabelACM)
                    .addGap(0, 44, Short.MAX_VALUE)))
        );

        ACMPanel.add(TitlePanelACM);

        javax.swing.GroupLayout ImagePanelACMLayout = new javax.swing.GroupLayout(ImagePanelACM);
        ImagePanelACM.setLayout(ImagePanelACMLayout);
        ImagePanelACMLayout.setHorizontalGroup(
            ImagePanelACMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagePanelACMLayout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(ImageLabelACM)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ImagePanelACMLayout.setVerticalGroup(
            ImagePanelACMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ImagePanelACMLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(ImageLabelACM)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        ACMPanel.add(ImagePanelACM);

        DescriptionLabelACM.setText("Graph-description");

        ConclusionLabelACM.setText("Conclusion");

        javax.swing.GroupLayout ConclusionPanelACMLayout = new javax.swing.GroupLayout(ConclusionPanelACM);
        ConclusionPanelACM.setLayout(ConclusionPanelACMLayout);
        ConclusionPanelACMLayout.setHorizontalGroup(
            ConclusionPanelACMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConclusionPanelACMLayout.createSequentialGroup()
                .addGap(332, 332, 332)
                .addGroup(ConclusionPanelACMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(DescriptionLabelACM)
                    .addComponent(ConclusionLabelACM))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        ConclusionPanelACMLayout.setVerticalGroup(
            ConclusionPanelACMLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ConclusionPanelACMLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(DescriptionLabelACM)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ConclusionLabelACM)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        ACMPanel.add(ConclusionPanelACM);

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
    private boolean connectedToJDBC = false;
    private boolean connectedToRServe = false;
    
    private void RefreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RefreshButtonActionPerformed
       
        switch(DmSelectionTabbedPane.getSelectedIndex()){
            case 0:
                System.out.println("Refresh Reg cor");
                refreshRegCor();
                break;
                
            case 1:
                System.out.println("Refresh Anova");
                refreshAnova();
                break;
                
            case 2:
                System.out.println("Refresh CAH");
                refreshCAH();
                break;
                
            case 3:
                System.out.println("Refresh ACM");
                refreshACM();
                break;
        }
        
        //DebugResultSet(rs);
       
    }//GEN-LAST:event_RefreshButtonActionPerformed

    private void GetDMButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GetDMButtonActionPerformed
        switch(DmSelectionTabbedPane.getSelectedIndex()){
            case 0:
                System.out.println("Get dm Reg cor");
                getDMRegCor();
                break;
                
            case 1:
                System.out.println("Get dm Anova");
                getDMAnova();
                break;
                
            case 2:
                System.out.println("Get dm CAH");
                getDMCAH();
                break;
                
            case 3:
                System.out.println("Get dm ACM");
                getDMACM();
                break;
        }
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
        if(connectedToJDBC) return;
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
        connectedToJDBC = true;
    }
    
    public void connectToRServ(){
        if(connectedToRServe) return;
        beanRServ = new BeanRServe();
        connectedToRServe = true;
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
            rs.beforeFirst();
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
        connectToJDBC();
        connectToRServ();
        //Requête SQL pour former la table comprenant "destination_villeDepart, destination_villeArrivee, count(destination_villeDepart)"
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
        
        //Envoie de la table à RServe
        exportResultasDatasetInRServe(rs,"Roads");
        //Modification des type de col
        beanRServ.voidEval("Roads$NombreDeConteneurs=as.numeric(Roads$NombreDeConteneurs)");
        
        //Préparation du fichier image dans RServe
        beanRServ.eval("png(file='Roads.png',width=1400,height=800)");
        //Création du graph
        beanRServ.parseAndEval("with(Roads,interaction.plot(destination_villeDepart,destination_villeArrivee,NombreDeConteneurs));dev.off()");
        
        //Import du graph 
        REXP xp = beanRServ.parseAndEval("r=readBin('Roads.png','raw',1400*800)");
        beanRServ.parseAndEval("unlink('Roads.png');r");
        Image img = null;
        try {
            img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
        } catch (REXPMismatchException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //Affichage du graph
        ImageLabelAnova.setIcon(new ImageIcon(img));
        ImageLabelAnova.repaint();
        
        //update graph
        InputStream fis;
        try {
            fis = new ByteArrayInputStream(xp.asBytes());
            rs = beanJdbc.Update("bd_decisions.analyse_graph", "id = 2", "graph", fis);    
        } catch (REXPMismatchException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Select des datas dans mysql
        rs = beanJdbc.ExecuteQuery(""
                + "select "
                + "     titre, fonction, conclusionGenerale, commentaire "
                + "from "
                + "     bd_decisions.analyse a, bd_decisions.analyse_graph ag "
                + "where "
                + "     a.fonction = 'Anova 2' and ag.analyse_id = a.id");
        //debugResultSet(rs);
        
        try {
            rs.first();
            TitleLabelAnova.setText(rs.getString("titre"));
            DescriptionLabelAnova.setText(rs.getString("commentaire"));
            ConclusionLabelAnova.setText(rs.getString("conclusionGenerale"));
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    private void refreshRegCor(){
        
    }
    
    private void refreshCAH(){
        connectToJDBC();
        connectToRServ();
        
        //Requête SQL pour former la table comprenant "destination_villeDepart, destination_villeArrivee, count(destination_villeDepart)"
        ResultSet rs = beanJdbc.ExecuteQuery(""
                + "select "
                + "     MONTHNAME(mois) as mois, "
                + "     SUM(total) as factures "
                + "from "
                + "     bd_compta.factures "
                + "group by "
                + "     mois");
        debugResultSet(rs);
        
        
        //Envoie de la table à RServe
        exportResultasDatasetInRServe(rs,"Benefices");
        //Modification des type de col
        beanRServ.voidEval("Benefices$factures=as.numeric(Benefices$factures)");
        beanRServ.voidEval("rownames(Benefices) <- Benefices[,1]");
        
        //Graph1
        beanRServ.eval("library(cluster)");
        //Préparation du fichier image dans RServe
        beanRServ.eval("png(file='Benefices.png',width=800,height=800)");
        //Création du graph
        beanRServ.voidEval("class = agnes(Benefices, method='ward')");
        beanRServ.voidEval("plot(class)");
        beanRServ.eval("plot(class);dev.off()");
        
        //Import du graph 
        REXP xp = beanRServ.parseAndEval("r=readBin('Benefices.png','raw',800*800)");
        beanRServ.parseAndEval("unlink('Benefices.png');r");
        Image img = null;
        try {
            img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
        } catch (REXPMismatchException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //Affichage du graph
        ImageLabelCAH.setIcon(new ImageIcon(img));
        ImageLabelCAH.repaint();
        
        //update graph
        InputStream fis;
        try {
            fis = new ByteArrayInputStream(xp.asBytes());
            rs = beanJdbc.Update("bd_decisions.analyse_graph", "id = 3", "graph", fis);    
        } catch (REXPMismatchException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Select des datas dans mysql
        rs = beanJdbc.ExecuteQuery(""
                + "select "
                + "     titre, fonction, conclusionGenerale, commentaire "
                + "from "
                + "     bd_decisions.analyse a, bd_decisions.analyse_graph ag "
                + "where "
                + "     a.fonction = 'CAH' and ag.analyse_id = a.id");
        //debugResultSet(rs);
        
        try {
            rs.first();
            TitleLabelCAH.setText(rs.getString("titre"));
            DescriptionLabelCAH.setText(rs.getString("commentaire"));
            ConclusionLabelCAH.setText(rs.getString("conclusionGenerale"));
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Graph2
        //Préparation du fichier image dans RServe
        beanRServ.eval("png(file='Benefices2.png',width=800,height=800)");
        //Création du graph
        beanRServ.eval("barplot(Benefices$factures, main = 'Benefices mensuelles', xlab ='Mois', ylab='Benefice',col='green', names.arg=row.names(Benefices));dev.off()");
        
        //Import du graph 
        xp = beanRServ.parseAndEval("r=readBin('Benefices2.png','raw',800*800)");
        beanRServ.parseAndEval("unlink('Benefices2.png');r");
        img = null;
        try {
            img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
        } catch (REXPMismatchException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //Affichage du graph
        ImageLabelCAH2.setIcon(new ImageIcon(img));
        ImageLabelCAH2.repaint();
        
        //update graph
        try {
            fis = new ByteArrayInputStream(xp.asBytes());
            rs = beanJdbc.Update("bd_decisions.analyse_graph", "id = 4", "graph", fis);    
        } catch (REXPMismatchException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Select des datas dans mysql
        rs = beanJdbc.ExecuteQuery("select commentaire from bd_decisions.analyse_graph where id =4");
        //debugResultSet(rs);
        
        try {
            rs.first();
            DescriptionLabelCAH2.setText(rs.getString("commentaire"));
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void refreshACM(){
        connectToJDBC();
        connectToRServ();
        //Requête SQL pour former la table comprenant "destination_villeDepart, destination_villeArrivee, count(destination_villeDepart)"
        ResultSet rs = beanJdbc.ExecuteQuery("select ALL destination_villeDepart, destination_villeArrivee from bd_mouvements.transports");
        
        //Envoie de la table à RServe
        exportResultasDatasetInRServe(rs,"Routes");
        //Modification des type de col
        beanRServ.voidEval("names(Routes)[1]<-'Depart'");
        beanRServ.voidEval("names(Routes)[2]<-'Destination'");
        beanRServ.voidEval("Routes <- Routes[sample(nrow(Routes),200),]");
        
        beanRServ.eval("library(FactoMineR)");
        beanRServ.eval("library('factoextra')");
        
        //Préparation du fichier image dans RServe
        beanRServ.eval("png(file='Routes.png',width=1400,height=800)");
        //Création du graph
        beanRServ.parseAndEval("acm <- MCA(Routes, graph=FALSE)"); 
        beanRServ.parseAndEval("print(fviz_mca_var(acm, repel = TRUE));dev.off()");
        //Import du graph 
        REXP xp = beanRServ.parseAndEval("r=readBin('Routes.png','raw',1400*800)");
        
        beanRServ.parseAndEval("unlink('Routes.png');r");
        
        Image img = null;
        try {
            img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
        } catch (REXPMismatchException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //Affichage du graph
        ImageLabelACM.setIcon(new ImageIcon(img));
        ImageLabelACM.repaint();
        
        
        //update graph
        InputStream fis;
        try {
            fis = new ByteArrayInputStream(xp.asBytes());
            rs = beanJdbc.Update("bd_decisions.analyse_graph", "id = 5", "graph", fis);    
        } catch (REXPMismatchException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Select des datas dans mysql
        rs = beanJdbc.ExecuteQuery(""
                + "select "
                + "     titre, fonction, conclusionGenerale, commentaire "
                + "from "
                + "     bd_decisions.analyse a, bd_decisions.analyse_graph ag "
                + "where "
                + "     a.fonction = 'ACM' and ag.analyse_id = a.id");
        //debugResultSet(rs);
        
        try {
            rs.first();
            TitleLabelACM.setText(rs.getString("titre"));
            DescriptionLabelACM.setText(rs.getString("commentaire"));
            ConclusionLabelACM.setText(rs.getString("conclusionGenerale"));
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getDMRegCor(){
        
    }
    
    private void getDMAnova(){
        connectToJDBC();
        ResultSet rs = beanJdbc.ExecuteQuery(""
                + "select "
                + "     titre, fonction, conclusionGenerale, commentaire, graph "
                + "from "
                + "     bd_decisions.analyse a, bd_decisions.analyse_graph ag "
                + "where "
                + "     a.fonction = 'Anova 2' and ag.analyse_id = a.id");
        //debugResultSet(rs);
        
        try {
            rs.first();
            TitleLabelAnova.setText(rs.getString("titre"));
            DescriptionLabelAnova.setText(rs.getString("commentaire"));
            ConclusionLabelAnova.setText(rs.getString("conclusionGenerale"));
            InputStream is = rs.getBinaryStream("graph");
            try {
                ImageIcon img= new ImageIcon(ImageIO.read(is));
                //Affichage du graph
                ImageLabelAnova.setIcon(img);
                ImageLabelAnova.repaint();
            } catch (IOException ex) {
                Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getDMCAH(){
        connectToJDBC();
        ResultSet rs = beanJdbc.ExecuteQuery(""
                + "select "
                + "     titre, fonction, conclusionGenerale, commentaire, graph "
                + "from "
                + "     bd_decisions.analyse a, bd_decisions.analyse_graph ag "
                + "where "
                + "     a.fonction = 'CAH' and ag.analyse_id = a.id");
        //debugResultSet(rs);
        
        try {
            rs.first();
            TitleLabelCAH.setText(rs.getString("titre"));
            DescriptionLabelCAH.setText(rs.getString("commentaire"));
            ConclusionLabelCAH.setText(rs.getString("conclusionGenerale"));
            InputStream is = rs.getBinaryStream("graph");
            try {
                ImageIcon img= new ImageIcon(ImageIO.read(is));
                //Affichage du graph
                ImageLabelCAH.setIcon(img);
                ImageLabelCAH.repaint();
            } catch (IOException ex) {
                Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            rs.next();
            DescriptionLabelCAH2.setText(rs.getString("commentaire"));
            is = rs.getBinaryStream("graph");
            try {
                ImageIcon img= new ImageIcon(ImageIO.read(is));
                //Affichage du graph
                ImageLabelCAH2.setIcon(img);
                ImageLabelCAH2.repaint();
            } catch (IOException ex) {
                Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void getDMACM(){
        connectToJDBC();
        ResultSet rs = beanJdbc.ExecuteQuery(""
                + "select "
                + "     titre, fonction, conclusionGenerale, commentaire, graph "
                + "from "
                + "     bd_decisions.analyse a, bd_decisions.analyse_graph ag "
                + "where "
                + "     a.fonction = 'ACM' and ag.analyse_id = a.id");
        //debugResultSet(rs);
        
        try {
            rs.first();
            TitleLabelACM.setText(rs.getString("titre"));
            DescriptionLabelACM.setText(rs.getString("commentaire"));
            ConclusionLabelACM.setText(rs.getString("conclusionGenerale"));
            InputStream is = rs.getBinaryStream("graph");
            try {
                ImageIcon img= new ImageIcon(ImageIO.read(is));
                //Affichage du graph
                ImageLabelACM.setIcon(img);
                ImageLabelACM.repaint();
            } catch (IOException ex) {
                Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel ACMPanel;
    private javax.swing.JPanel AnovaPanel;
    private javax.swing.JPanel ButtonsPanel;
    private javax.swing.JPanel CAHPanel;
    private javax.swing.JLabel ConclusionLabelACM;
    private javax.swing.JLabel ConclusionLabelAnova;
    private javax.swing.JLabel ConclusionLabelCAH;
    private javax.swing.JPanel ConclusionPanelACM;
    private javax.swing.JPanel ConclusionPanelAnova;
    private javax.swing.JPanel ConclusionPanelCAH;
    private javax.swing.JLabel DescriptionLabelACM;
    private javax.swing.JLabel DescriptionLabelAnova;
    private javax.swing.JLabel DescriptionLabelCAH;
    private javax.swing.JLabel DescriptionLabelCAH2;
    private javax.swing.JTabbedPane DmSelectionTabbedPane;
    private javax.swing.JButton GetDMButton;
    private javax.swing.JLabel ImageLabelACM;
    private javax.swing.JLabel ImageLabelAnova;
    private javax.swing.JLabel ImageLabelCAH;
    private javax.swing.JLabel ImageLabelCAH2;
    private javax.swing.JPanel ImagePanelACM;
    private javax.swing.JPanel ImagePanelAnova;
    private javax.swing.JPanel ImagePanelCAH;
    private javax.swing.JButton RefreshButton;
    private javax.swing.JPanel RegCoorPanel;
    private javax.swing.JLabel TitleLabelACM;
    private javax.swing.JLabel TitleLabelAnova;
    private javax.swing.JLabel TitleLabelCAH;
    private javax.swing.JPanel TitlePanelACM;
    private javax.swing.JPanel TitlePanelAnova;
    private javax.swing.JPanel TitlePanelCAH;
    private javax.swing.JPanel imgPanel1;
    private javax.swing.JPanel imgPanel2;
    private javax.swing.JTabbedPane jTabbedPane3;
    // End of variables declaration//GEN-END:variables
}
