/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocol;

/**
 *
 * @author Thomas
 */
public class RequestLoginResponse extends BaseRequest {
    
    //If this variable is true, the user can do datamining operation.
    //if this variable is false, the user can only view datamining operation.
    private boolean datascientist;
    
    
    /**
     * @return the datascientist
     */
    public boolean isIsdatascientist() {
        return datascientist;
    }

    /**
     * @param datascientist the datascientist to set
     */
    public void setIsdatascientist(boolean datascientist) {
        this.datascientist = datascientist;
    }
    
}
