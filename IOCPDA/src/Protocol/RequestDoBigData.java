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
public class RequestDoBigData extends BaseRequest {

    private int typetraitement;
    
    /**
     * @return the typetraitement
     */
    public int getTypetraitement() {
        return typetraitement;
    }

    /**
     * @param typetraitement the typetraitement to set
     */
    public void setTypetraitement(int typetraitement) {
        this.typetraitement = typetraitement;
    }
}
