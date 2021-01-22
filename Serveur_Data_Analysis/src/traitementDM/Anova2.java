/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitementDM;

import Protocol.RequestBigDataResult;
import connectionJdbc.BeanJDBC;
import connectionRServe.BeanRServe;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import traitementDM.tests.datamining;

/**
 *
 * @author renardN
 */
public class Anova2 extends DataminingProcessing {
    
    public Anova2()
    {
        super();
    }
    
    public Anova2(BeanJDBC beanJdbc, BeanRServe beanRServe)
    {
        super(beanJdbc, beanRServe);
    }
    
    public void getDMAnova(){
        ResultSet rs = getBeanJdbc().ExecuteQuery(""
                + "select "
                + "     titre, fonction, conclusionGenerale, commentaire, graph, maj "
                + "from "
                + "     bd_decisions.analyse a, bd_decisions.analyse_graph ag "
                + "where "
                + "     a.fonction = 'Anova 2' and ag.analyse_id = a.id");
        //debugResultSet(rs);
        
        try {
            rs.first();
            getDataset().put(RequestBigDataResult.ANOVA2_DATE, rs.getTimestamp("maj"));
            getDataset().put(RequestBigDataResult.ANOVA2_GLOBAL_TITRE, rs.getString("titre"));
            getDataset().put(RequestBigDataResult.ANOVA2_PLOT_ONE_TEXT, rs.getString("commentaire"));
            getDataset().put(RequestBigDataResult.ANOVA2_GLOBAL_TEXT, rs.getString("conclusionGenerale"));
            
            //Ajout du graph un à la hashtable
            InputStream is = rs.getBinaryStream("graph");
            byte[] graph;
            try {
                graph = new byte[is.available()];
                is.read(graph);
                getDataset().put(RequestBigDataResult.ANOVA2_PLOT_ONE, graph);
            } catch (IOException ex) {
                Logger.getLogger(Acm.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void refreshAnova(){
        //Requête SQL pour former la table comprenant "destination_villeDepart, destination_villeArrivee, count(destination_villeDepart)"
        ResultSet rs = getBeanJdbc().ExecuteQuery(""
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
        getBeanRServ().voidEval("Roads$NombreDeConteneurs=as.numeric(Roads$NombreDeConteneurs)");
        
        //Préparation du fichier image dans RServe
        getBeanRServ().eval("png(file='Roads.png',width=1400,height=800)");
        //Création du graph
        getBeanRServ().parseAndEval("with(Roads,interaction.plot(destination_villeDepart,destination_villeArrivee,NombreDeConteneurs));dev.off()");
        
        //Import du graph 
        REXP xp = getBeanRServ().parseAndEval("r=readBin('Roads.png','raw',1400*800)");
        getBeanRServ().parseAndEval("unlink('Roads.png');r");
        
        
        
        //update graph
        InputStream fis;
        try {
            fis = new ByteArrayInputStream(xp.asBytes());
            getBeanJdbc().Update("bd_decisions.analyse_graph", "id = 2", "graph", fis);
            //Ajout du graph un à la hashtable
            byte[] graph = xp.asBytes();
            getDataset().put(RequestBigDataResult.ANOVA2_PLOT_ONE, graph);
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
                + "     a.fonction = 'Anova 2' and ag.analyse_id = a.id");
        //debugResultSet(rs);
        
        try {
            rs.first();
            getDataset().put(RequestBigDataResult.ANOVA2_DATE, rs.getTimestamp("maj"));
            getDataset().put(RequestBigDataResult.ANOVA2_GLOBAL_TITRE, rs.getString("titre"));
            getDataset().put(RequestBigDataResult.ANOVA2_PLOT_ONE_TEXT, rs.getString("commentaire"));
            getDataset().put(RequestBigDataResult.ANOVA2_GLOBAL_TEXT, rs.getString("conclusionGenerale"));
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
