/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionRServe;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

/**
 *
 * @author renardN
 */
public class BeanRServe {
    
    private RConnection connection = null;
    
    
    public BeanRServe()
    {
        try {
            connection = new RConnection();
        } catch (RserveException ex) {
            Logger.getLogger(BeanRServe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private synchronized void readTable(String nameDataset, String pathCsvFile, boolean header)
    {
        if(header)
        {
            try {
                connection.voidEval(nameDataset + "<-read.table(\"" + pathCsvFile + "\", h=TRUE)");
            } catch (RserveException ex) {
                Logger.getLogger(BeanRServe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            try {
                connection.voidEval(nameDataset + "<-read.table(\"" + pathCsvFile + "\", h=FALSE)");
            } catch (RserveException ex) {
                Logger.getLogger(BeanRServe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    public synchronized void voidEval(String eval){
        try {
            connection.voidEval(eval);
        } catch (RserveException ex) {
            Logger.getLogger(BeanRServe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public synchronized REXP parseAndEval(String eval){
        try {
            return connection.parseAndEval(eval);
        } catch (RserveException ex) {
            Logger.getLogger(BeanRServe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (REngineException ex) {
            Logger.getLogger(BeanRServe.class.getName()).log(Level.SEVERE, null, ex);
        } catch (REXPMismatchException ex) {
            Logger.getLogger(BeanRServe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public synchronized REXP eval(String eval){
        try {
            return connection.eval(eval);
        } catch (RserveException ex) {
            Logger.getLogger(BeanRServe.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    private synchronized void doACP_ACM(boolean isACP ,String nameDatasetOut, String nameDatasetIn, List qualiSupList, List quantiSupList)
    {
        String typeAnalyse;
        if(isACP)
            typeAnalyse = "PCA";
        else
            typeAnalyse = "MCA";
        
        try {
            connection.voidEval("library(FactoMineR)");
        } catch (RserveException ex) {
            Logger.getLogger(BeanRServe.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String qualiSup = "c(";
        for(int i = 0; i < qualiSupList.size(); i++)
        {
            if(i == 0)
                qualiSup = qualiSup.concat(qualiSupList.get(i).toString());
            else
                qualiSup = qualiSup.concat("," + qualiSupList.get(i).toString());
        }
        qualiSup = qualiSup + ")";
        
        String quantiSup = "c(";
        for(int i = 0; i < quantiSupList.size(); i++)
        {
            if(i == 0)
                quantiSup = quantiSup.concat(quantiSupList.get(i).toString());
            else
                quantiSup = quantiSup.concat("," + quantiSupList.get(i).toString());
        }
        quantiSup = quantiSup + ")";
        
        if(!qualiSupList.isEmpty() && !quantiSupList.isEmpty())
        {
            try {
                connection.voidEval(nameDatasetOut + "<-" + typeAnalyse + "(" + nameDatasetIn + ", quali.sup = " + qualiSup + ", quanti.sup = " + quantiSup + ")");
            } catch (RserveException ex) {
                Logger.getLogger(BeanRServe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(!qualiSupList.isEmpty())
        {
            try {
                connection.voidEval(nameDatasetOut + "<-MCA(" + nameDatasetIn + ", quali.sup = " + qualiSup + ")");
            } catch (RserveException ex) {
                Logger.getLogger(BeanRServe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(!quantiSupList.isEmpty())
        {
            try {
                connection.voidEval(nameDatasetOut + "<-MCA(" + nameDatasetIn + ", quanti.sup = " + quantiSup + ")");
            } catch (RserveException ex) {
                Logger.getLogger(BeanRServe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            try {
                connection.voidEval(nameDatasetOut + "<-MCA(" + nameDatasetIn + ")");
            } catch (RserveException ex) {
                Logger.getLogger(BeanRServe.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
}
