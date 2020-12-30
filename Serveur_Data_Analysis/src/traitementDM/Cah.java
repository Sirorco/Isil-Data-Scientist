/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitementDM;

import Protocol.RequestBigDataResult;
import connectionJdbc.BeanJDBC;
import connectionRServe.BeanRServe;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import traitementDM.tests.datamining;

/**
 *
 * @author renardN
 */
public class Cah extends DataminingProcessing {
    
    
    public Cah()
    {
        super();
    }
    
    public Cah(BeanJDBC beanJdbc, BeanRServe beanRServe)
    {
        super(beanJdbc, beanRServe);
    }
    
    
    public void getDMCAH(){
        ResultSet rs = getBeanJdbc().ExecuteQuery(""
                + "select "
                + "     titre, fonction, conclusionGenerale, commentaire, graph, maj "
                + "from "
                + "     bd_decisions.analyse a, bd_decisions.analyse_graph ag "
                + "where "
                + "     a.fonction = 'CAH' and ag.analyse_id = a.id");
        //debugResultSet(rs);
        
        try {
            rs.first();
            getDataset().put(RequestBigDataResult.CAH_DATE, rs.getTimestamp("maj"));
            getDataset().put(RequestBigDataResult.CAH_GLOBAL_TITRE, rs.getString("titre"));
            getDataset().put(RequestBigDataResult.CAH_PLOT_ONE_TEXT, rs.getString("commentaire"));
            getDataset().put(RequestBigDataResult.CAH_GLOBAL_TEXT, rs.getString("conclusionGenerale"));
            //Ajout du graph un à la hashtable
            getDataset().put(RequestBigDataResult.CAH_PLOT_ONE, rs.getBinaryStream("graph"));
            
            rs.next();
            getDataset().put(RequestBigDataResult.CAH_PLOT_TWO_TEXT, rs.getString("commentaire"));
            getDataset().put(RequestBigDataResult.CAH_PLOT_TWO, rs.getBinaryStream("graph"));
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void refreshCAH(){
        
        setDataset(new Hashtable());
        
        //Requête SQL pour former la table comprenant "destination_villeDepart, destination_villeArrivee, count(destination_villeDepart)"
        ResultSet rs = getBeanJdbc().ExecuteQuery(""
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
        getBeanRServ().voidEval("Benefices$factures=as.numeric(Benefices$factures)");
        getBeanRServ().voidEval("rownames(Benefices) <- Benefices[,1]");
        
        //Graph1
        getBeanRServ().eval("library(cluster)");
        //Préparation du fichier image dans RServe
        getBeanRServ().eval("png(file='Benefices.png',width=800,height=800)");
        //Création du graph
        getBeanRServ().voidEval("class = agnes(Benefices, method='ward')");
        getBeanRServ().voidEval("plot(class)");
        getBeanRServ().eval("plot(class);dev.off()");
        
        //Import du graph 
        REXP xp = getBeanRServ().parseAndEval("r=readBin('Benefices.png','raw',800*800)");
        getBeanRServ().parseAndEval("unlink('Benefices.png');r");
        
        
        
        
        //update graph
        InputStream fis;
        try {
            fis = new ByteArrayInputStream(xp.asBytes());
            rs = getBeanJdbc().Update("bd_decisions.analyse_graph", "id = 3", "graph", fis); 
            //Ajout du graph un à la hashtable
            getDataset().put(RequestBigDataResult.CAH_PLOT_ONE, fis);
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
                + "     a.fonction = 'CAH' and ag.analyse_id = a.id");
        //debugResultSet(rs);
        
        try {
            rs.first();
            getDataset().put(RequestBigDataResult.CAH_DATE, rs.getTimestamp("maj"));
            getDataset().put(RequestBigDataResult.CAH_GLOBAL_TITRE, rs.getString("titre"));
            getDataset().put(RequestBigDataResult.CAH_PLOT_ONE_TEXT, rs.getString("commentaire"));
            getDataset().put(RequestBigDataResult.CAH_GLOBAL_TEXT, rs.getString("conclusionGenerale"));
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Graph2
        //Préparation du fichier image dans RServe
        getBeanRServ().eval("png(file='Benefices2.png',width=800,height=800)");
        //Création du graph
        getBeanRServ().eval("barplot(Benefices$factures, main = 'Benefices mensuelles', xlab ='Mois', ylab='Benefice',col='green', names.arg=row.names(Benefices));dev.off()");
        
        //Import du graph 
        xp = getBeanRServ().parseAndEval("r=readBin('Benefices2.png','raw',800*800)");
        getBeanRServ().parseAndEval("unlink('Benefices2.png');r");
        
        
        
        
        //update graph
        try {
            fis = new ByteArrayInputStream(xp.asBytes());
            getBeanJdbc().Update("bd_decisions.analyse_graph", "id = 4", "graph", fis);   
            //Ajout du graph deux à la hashtable
        getDataset().put(RequestBigDataResult.CAH_PLOT_TWO, fis);
        } catch (REXPMismatchException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Select des datas dans mysql
        rs = getBeanJdbc().ExecuteQuery("select commentaire from bd_decisions.analyse_graph where id =4");
        //debugResultSet(rs);
        
        try {
            rs.first();
            getDataset().put(RequestBigDataResult.CAH_PLOT_TWO_TEXT, rs.getString("commentaire"));
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
