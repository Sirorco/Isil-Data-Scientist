/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitementDM;

import Protocol.RequestBigDataResult;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import traitementDM.tests.datamining;

/**
 *
 * @author renardN
 */
public class Acm extends DataminingProcessing {
    
    
    public Acm()
    {
        super();
    }
    
    
    public void getDMACM(){
        ResultSet rs = getBeanJdbc().ExecuteQuery(""
                + "select "
                + "     titre, fonction, conclusionGenerale, commentaire, graph, maj "
                + "from "
                + "     bd_decisions.analyse a, bd_decisions.analyse_graph ag "
                + "where "
                + "     a.fonction = 'ACM' and ag.analyse_id = a.id");
        //debugResultSet(rs);
        
        try {
            rs.first();
            getDataset().put(RequestBigDataResult.ACM_DATE, rs.getTimestamp("maj"));
            getDataset().put(RequestBigDataResult.ACM_GLOBAL_TITRE, rs.getString("titre"));
            getDataset().put(RequestBigDataResult.ACM_PLOT_ONE_TEXT, rs.getString("commentaire"));
            getDataset().put(RequestBigDataResult.ACM_GLOBAL_TEXT, rs.getString("conclusionGenerale"));
            
            //Ajout du graph un à la hashtable
            getDataset().put(RequestBigDataResult.ACM_PLOT_ONE, rs.getBinaryStream("graph"));
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void refreshACM(){
        //Requête SQL pour former la table comprenant "destination_villeDepart, destination_villeArrivee, count(destination_villeDepart)"
        ResultSet rs = getBeanJdbc().ExecuteQuery("select ALL destination_villeDepart, destination_villeArrivee from bd_mouvements.transports");
        
        //Envoie de la table à RServe
        exportResultasDatasetInRServe(rs,"Routes");
        //Modification des type de col
        getBeanRServ().voidEval("names(Routes)[1]<-'Depart'");
        getBeanRServ().voidEval("names(Routes)[2]<-'Destination'");
        getBeanRServ().voidEval("Routes <- Routes[sample(nrow(Routes),200),]");
        
        getBeanRServ().eval("library(FactoMineR)");
        getBeanRServ().eval("library('factoextra')");
        
        //Préparation du fichier image dans RServe
        getBeanRServ().eval("png(file='Routes.png',width=1400,height=800)");
        //Création du graph
        getBeanRServ().parseAndEval("acm <- MCA(Routes, graph=FALSE)"); 
        getBeanRServ().parseAndEval("print(fviz_mca_var(acm, repel = TRUE));dev.off()");
        //Import du graph 
        REXP xp = getBeanRServ().parseAndEval("r=readBin('Routes.png','raw',1400*800)");
        
        getBeanRServ().parseAndEval("unlink('Routes.png');r");
        
        Image img = null;
        try {
            img = Toolkit.getDefaultToolkit().createImage(xp.asBytes());
        } catch (REXPMismatchException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //update graph
        InputStream fis;
        try {
            fis = new ByteArrayInputStream(xp.asBytes());
            rs = getBeanJdbc().Update("bd_decisions.analyse_graph", "id = 5", "graph", fis);    
            //Ajout du graph un à la hashtable
            getDataset().put(RequestBigDataResult.ACM_PLOT_ONE, fis);
        } catch (REXPMismatchException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        //Select des datas dans mysql
        rs = getBeanJdbc().ExecuteQuery(""
                + "select "
                + "     titre, fonction, conclusionGenerale, commentaire, maj "
                + "from "
                + "     bd_decisions.analyse a, bd_decisions.analyse_graph ag "
                + "where "
                + "     a.fonction = 'ACM' and ag.analyse_id = a.id");
        //debugResultSet(rs);
        
        try {
            rs.first();
            getDataset().put(RequestBigDataResult.ACM_DATE, rs.getTimestamp("maj"));
            getDataset().put(RequestBigDataResult.ACM_GLOBAL_TITRE, rs.getString("titre"));
            getDataset().put(RequestBigDataResult.ACM_PLOT_ONE_TEXT, rs.getString("commentaire"));
            getDataset().put(RequestBigDataResult.ACM_GLOBAL_TEXT, rs.getString("conclusionGenerale"));
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
