/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traitementDM;

import connectionJdbc.BeanJDBC;
import connectionRServe.BeanRServe;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import traitementDM.tests.datamining;

/**
 *
 * @author renardN
 */
public abstract class DataminingProcessing {
    
    private BeanJDBC beanJdbc;
    private BeanRServe beanRServ;
    private Hashtable dataset;
    
    public DataminingProcessing()
    {
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
        
        beanRServ = new BeanRServe();
        dataset = new Hashtable();
    }
    
    public DataminingProcessing(BeanJDBC beanJdbc, BeanRServe beanRServ)
    {
        this.beanJdbc = beanJdbc;   
        this.beanRServ = beanRServ;
        dataset = new Hashtable();
    }
    
    
    protected void debugResultSet(ResultSet rs){
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
    
    
    protected void exportResultasDatasetInRServe(ResultSet rs, String dataSetTitle){   
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
                getBeanRServ().voidEval(query);

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
            
            getBeanRServ().voidEval(query);
        } catch (SQLException ex) {
            Logger.getLogger(datamining.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the beanJdbc
     */
    public BeanJDBC getBeanJdbc() {
        return beanJdbc;
    }

    /**
     * @param beanJdbc the beanJdbc to set
     */
    public void setBeanJdbc(BeanJDBC beanJdbc) {
        this.beanJdbc = beanJdbc;
    }

    /**
     * @return the beanRServ
     */
    public BeanRServe getBeanRServ() {
        return beanRServ;
    }

    /**
     * @param beanRServ the beanRServ to set
     */
    public void setBeanRServ(BeanRServe beanRServ) {
        this.beanRServ = beanRServ;
    }

    /**
     * @return the dataset
     */
    public Hashtable getDataset() {
        return dataset;
    }

    /**
     * @param dataset the dataset to set
     */
    public void setDataset(Hashtable dataset) {
        this.dataset = dataset;
    }
}
