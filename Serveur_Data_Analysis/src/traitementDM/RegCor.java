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
public class RegCor extends DataminingProcessing {
    
    
    public RegCor()
    {
        super();
    }
    
    public void getDMRegCor(){
        ResultSet rs = getBeanJdbc().ExecuteQuery(""
                + "select "
                + "     titre, fonction, conclusionGenerale, commentaire, graph "
                + "from "
                + "     bd_decisions.analyse a, bd_decisions.analyse_graph ag "
                + "where "
                + "     a.fonction = 'Regression-corrélation' and ag.analyse_id = a.id");
        //debugResultSet(rs);
        
        try {
            rs.first();
            getDataset().put(RequestBigDataResult.REGCORR_DATE, rs.getTimestamp("maj"));
            getDataset().put(RequestBigDataResult.REGCORR_GLOBAL_TITRE, rs.getString("titre"));
            getDataset().put(RequestBigDataResult.REGCORR_PLOT_ONE_TEXT, rs.getString("commentaire"));
            getDataset().put(RequestBigDataResult.REGCORR_GLOBAL_TEXT, rs.getString("conclusionGenerale"));
            //Ajout du graph un à la hashtable
            getDataset().put(RequestBigDataResult.REGCORR_PLOT_ONE, rs.getBinaryStream("graph"));
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    public void refreshRegCor(){
        //Requête SQL pour former la table comprenant "nconteneurs, retard, mois, nDockers"
        String request = "select "
                + "     count(id) as 'nconteneurs', "
                + "     sum(retard) as 'retard', "
                //+ "     monthname(dateDepartPrevu) as 'mois', "
                + "     w.nDockers as 'ndockers'"
                + "from "
                + "     transports t,"
                + "     bd_compta.workingdockers w "
                + "where"
                + "     monthname(t.dateDepartPrevu) like w.mois "
                + "group by "
                + "     monthname(dateDepartPrevu)";
        System.out.println(request);
        ResultSet rs = getBeanJdbc().ExecuteQuery(request);
        
        //Envoie de la table à RServe
        exportResultasDatasetInRServe(rs,"Retard");
        //Modification des type de col
        getBeanRServ().voidEval("Retard$nconteneurs=as.numeric(Retard$nconteneurs)");
        getBeanRServ().voidEval("Retard$retard=as.numeric(Retard$retard)");
        getBeanRServ().voidEval("Retard$nDockers =as.numeric(Retard$nDockers)");
        //beanRServ.voidEval("rownames(Retard)<-Retard[,3]");
        
        //Préparation du fichier image dans RServe
        getBeanRServ().eval("png(file='Retard.png',width=1400,height=800)");
        //Création du graph
        getBeanRServ().parseAndEval("plot(Retard);dev.off()");
        
        //Import du graph 
        REXP xp = getBeanRServ().parseAndEval("r=readBin('Retard.png','raw',1400*800)");
        getBeanRServ().parseAndEval("unlink('Retard.png');r");
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
            rs = getBeanJdbc().Update("bd_decisions.analyse_graph", "id = 1", "graph", fis);    
            //Ajout du graph un à la hashtable
            getDataset().put(RequestBigDataResult.REGCORR_PLOT_ONE, fis);
        } catch (REXPMismatchException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Select des datas dans mysql
        rs = getBeanJdbc().ExecuteQuery(""
                + "select "
                + "     titre, fonction, conclusionGenerale, commentaire "
                + "from "
                + "     bd_decisions.analyse a, bd_decisions.analyse_graph ag "
                + "where "
                + "     a.fonction = 'Regression-corrélation' and ag.analyse_id = a.id");
        //debugResultSet(rs);
        
        try {
            rs.first();
            getDataset().put(RequestBigDataResult.REGCORR_DATE, rs.getTimestamp("maj"));
            getDataset().put(RequestBigDataResult.REGCORR_GLOBAL_TITRE, rs.getString("titre"));
            getDataset().put(RequestBigDataResult.REGCORR_PLOT_ONE_TEXT, rs.getString("commentaire"));
            getDataset().put(RequestBigDataResult.REGCORR_GLOBAL_TEXT, rs.getString("conclusionGenerale"));
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
