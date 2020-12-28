/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connectionJdbc;

import java.beans.*;
import java.io.Serializable;
import java.sql.*;
import java.sql.Statement;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thomas
 */
public class BeanJDBC implements Serializable {
    
    private Connection myConn = null;
    private String Error = "No Error";
    
    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
    public static final Boolean NO_UPDATE = false;
    public static final Boolean UPDATE = true;
    
    private String sampleProperty;
    
    private PropertyChangeSupport propertySupport;
    
    public BeanJDBC() {
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public BeanJDBC(String Bdd, String userName, String pswd) {
        propertySupport = new PropertyChangeSupport(this);
        try 
        {
            // 1. Get a connection to database
            myConn = DriverManager.getConnection(Bdd, userName , pswd);
        }
        catch (Exception exc) 
        {
            exc.printStackTrace();
            myConn = null;
            Error = exc.getMessage();
	}

    }
    
    public BeanJDBC(String Bdd) {
        propertySupport = new PropertyChangeSupport(this);
        try 
        {
            // 1. Get a connection to database
            myConn = DriverManager.getConnection(Bdd);
        }
        catch (Exception exc) 
        {
            exc.printStackTrace();
            myConn = null;
            Error = exc.getMessage();
	}

    }
    
    public BeanJDBC(String Bdd,  Properties props) {
        propertySupport = new PropertyChangeSupport(this);
        try 
        {
            // 1. Get a connection to database
            myConn = DriverManager.getConnection(Bdd,props);
        }
        catch (Exception exc) 
        {
            exc.printStackTrace();
            myConn = null;
            Error = exc.getMessage();
	}

    }
    
    public ResultSet SelectAll (String Table, Boolean upd)
    {
        Statement myStmt = null;
        ResultSet myRs = null;
        
        if (myConn != null)
        {
            try {
                myStmt = myConn.createStatement();
                
                if (upd == NO_UPDATE)
                    myRs = myStmt.executeQuery("select * from " + Table);
                else
                    myRs = myStmt.executeQuery("select * from " + Table + " for update");
                
            } catch (SQLException exc) {
                exc.printStackTrace();
                Error = exc.getMessage();
            }
        }
        
        return myRs;
    }
    
    public ResultSet SelectAllWhere (String Table, String Conditionarg, Boolean upd)
    {
        Statement myStmt = null;
        ResultSet myRs = null;
        
        if (myConn != null)
        {
            try {
                myStmt = myConn.createStatement();
                if (upd == NO_UPDATE)
                    myRs = myStmt.executeQuery("select * from " + Table + " where " + Conditionarg);
                else
                    myRs = myStmt.executeQuery("select * from " + Table + " where " + Conditionarg + " for update");
                
            } catch (SQLException exc) {
                exc.printStackTrace();
                Error = exc.getMessage();
            }
        }
        
        return myRs;
    }
    
    public ResultSet SelectCountWhere (String Table, String Conditionarg)
    {
        Statement myStmt = null;
        ResultSet myRs = null;
        
        if (myConn != null)
        {
            try {
                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery("select count(*) from " + Table + " where " + Conditionarg);
                
            } catch (SQLException exc) {
                exc.printStackTrace();
                Error = exc.getMessage();
            }
        }
        
        return myRs;
    }
    
    public ResultSet SelectCount (String Table)
    {
        Statement myStmt = null;
        ResultSet myRs = null;
        
        if (myConn != null)
        {
            try {
                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery("select count(*) from " + Table);
                
            } catch (SQLException exc) {
                exc.printStackTrace();
                Error = exc.getMessage();
            }
        }
        
        return myRs;
    }
    
    public ResultSet Update (String Table, String Conditionarg,String colonne ,String newValue)
    {
        java.sql.Statement myStmt = null;
        ResultSet myRs = null;
        
        if (myConn != null)
        {
            try {
                myStmt = myConn.createStatement();
                
               /* UPDATE table
                SET nom_colonne_1 = 'nouvelle valeur'
                WHERE condition*/
                myStmt.executeUpdate("update " + Table + " set "+ colonne + "=" + newValue +  " where " + Conditionarg   );
                 //  myStmt.executeUpdate("update fly set fly_id  = 78 where fly_id = 22 "  );

                    //myRs =myStmt.executeQuery("select * from " + Table  );

                // myRs =  myStmt.executeQuery("select *  from  " +Table   );
            } catch (SQLException exc) {
                exc.printStackTrace();
                Error = exc.getMessage();
                myRs = null;
            }
        }
        
        return myRs;
    }
    
    
    public ResultSet Delete (String Table, String Conditionarg)
    {
        Statement myStmt = null;
        ResultSet myRs = null;
        
        if (myConn != null)
        {
            try {
                myStmt = myConn.createStatement();
                myStmt.executeUpdate("delete from " + Table + " where " + Conditionarg);
                
            } catch (SQLException exc) {
                exc.printStackTrace();
                Error = exc.getMessage();
            }
        }
        
        return myRs;
    }
    
    public boolean Insert (String Table, Vector Values)
    {
        java.sql.Statement myStmt = null;
        
        if (myConn != null)
        {
            try {
                myStmt = myConn.createStatement();
                
                String valuestoinsert = "( ";
                int cpt = 0;
                
                while (cpt < Values.size())
                {
                    valuestoinsert += (String)Values.get(cpt);
                    cpt++;
                    if (cpt < Values.size())
                    {
                        valuestoinsert = valuestoinsert + ",";
                    }
                }

                myStmt.executeUpdate("insert into " + Table + " values " + valuestoinsert + ")"   );

            } catch (SQLException exc) {
                exc.printStackTrace();
                Error = exc.getMessage();
                return false;
            }
        }
        
        return true;
    }
    
    public ResultSet ExecuteQuery(String query){
        Statement myStmt = null;
        ResultSet myRs = null;
        if (myConn != null)
        {
            try {
                myStmt = myConn.createStatement();
                myRs = myStmt.executeQuery(query);
            } catch (SQLException exc) {
                exc.printStackTrace();
                Error = exc.getMessage();
            }
        }
        
        return myRs;
    }
    
    public boolean Isconnected ()
    {
        if (myConn == null)
        {
            return (false);
        }
        else
        {
            return (true);
        }
    }
    
    public void CloseConnection ()
    {
        if (myConn != null) {
            try 
            {
                if (myConn.getAutoCommit())
                    myConn.rollback();
                myConn.close();
                myConn = null;
            } catch (SQLException exc) {
                exc.printStackTrace();
            }
	}
    }
    
    public Boolean SetAutocomit (Boolean state)
    {
        if (myConn != null)
        {
            try {
                myConn.setAutoCommit(state);
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(BeanJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public Boolean Commit()
    {
        if (myConn != null)
        {
            try {
                myConn.commit();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(BeanJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public Boolean Rollback()
    {
        if (myConn != null)
        {
            try {
                myConn.rollback();
                return true;
            } catch (SQLException ex) {
                Logger.getLogger(BeanJDBC.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }
    
    public String GetError ()
    {
        return (Error);
    }
    
    
    
    //Machin pas utiles
    /*
    
    public String getSampleProperty() {
        return sampleProperty;
    }
    
    public void setSampleProperty(String value) {
        String oldValue = sampleProperty;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
 
    */
}
