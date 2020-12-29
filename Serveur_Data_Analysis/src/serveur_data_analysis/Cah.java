/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur_data_analysis;

import Protocol.RequestBigDataResult;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;

/**
 *
 * @author renardN
 */
public class Cah extends DataminingProcessing {
    
    
    public Cah()
    {
        super();
    }
    
    
    public void getDMCAH(){
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
            datasetReturn.put(RequestBigDataResult.CAH_PLOT_ONE_TEXT, rs.getString("commentaire"));
            TitleLabelCAH.setText(rs.getString("titre"));
            datasetReturn.put(RequestBigDataResult.CAH_GLOBAL_TEXT, rs.getString("conclusionGenerale"));
            InputStream is = rs.getBinaryStream("graph");
            try {
                ImageIcon img= new ImageIcon(ImageIO.read(is));
                //Ajout du graph un à la hashtable
                datasetReturn.put(RequestBigDataResult.CAH_PLOT_ONE, img);
            } catch (IOException ex) {
                Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            rs.next();
            datasetReturn.put(RequestBigDataResult.CAH_PLOT_TWO_TEXT, rs.getString("commentaire"));
            is = rs.getBinaryStream("graph");
            try {
                ImageIcon img= new ImageIcon(ImageIO.read(is));
                //Affichage du graph
                datasetReturn.put(RequestBigDataResult.CAH_PLOT_TWO, img);
            } catch (IOException ex) {
                Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void refreshCAH(){
        
        datasetReturn = new Hashtable();
        
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
        
        
        //Ajout du graph un à la hashtable
        datasetReturn.put(RequestBigDataResult.CAH_PLOT_ONE, new ImageIcon(img));
        
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
            datasetReturn.put(RequestBigDataResult.CAH_PLOT_ONE_TEXT, rs.getString("titre") + " : " + rs.getString("commentaire"));
            datasetReturn.put(RequestBigDataResult.CAH_GLOBAL_TEXT, rs.getString("conclusionGenerale"));
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
        
        
        //Ajout du graph deux à la hashtable
        datasetReturn.put(RequestBigDataResult.CAH_PLOT_TWO, img);
        
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
            datasetReturn.put(RequestBigDataResult.CAH_PLOT_TWO_TEXT, rs.getString("commentaire"));
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
