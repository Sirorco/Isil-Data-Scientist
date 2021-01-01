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
public class RequestLoginInitiator extends BaseRequest {
    
    private String saltchallenge;

    /**
     * @return the saltchallenge
     */
    public String getSaltChallenge() {
        return saltchallenge;
    }

    /**
     * @param SaltChallenge the saltchallenge to set
     */
    public void setSaltChallenge(String SaltChallenge) {
        this.saltchallenge = SaltChallenge;
    }
    
}
