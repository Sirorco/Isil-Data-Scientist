/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocol;

import java.security.MessageDigest;
import java.security.Security;
import java.util.Vector;

/**
 *
 * @author Thomas
 */
public class RequestLogin extends BaseRequest {

    private String username;
    private byte[] digest;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param Username the username to set
     */
    public void setUsername(String Username) {
        this.username = Username;
    }

    /**
     * @return the digest
     */
    public byte[] getDigest() {
        return digest;
    }

    /**
     * @param Digest the digest to set
     */
    public void setDigest(byte[] Digest) {
        this.digest = Digest;
    }
    
    //This function calculate the digest of the elements in "Components" and store the result in the request.
    //md is the digest object we will use in order to calculate the digest
    //Components is the elements that are used in the digest
    public void CalculateDigest (MessageDigest md, Vector <String> Components)
    {
        while (Components.size()!=0)
        {
            md.update(Components.firstElement().getBytes());
            Components.remove(0);
        }
            this.setDigest(md.digest());
    }
    
    //This function calculate the digest of the elements in "Components", compare it with the digest store in the object and return the result of the comparaison.
    //md is the digest object we will use in order to calculate the digest
    //Components is the elements that are used in the digest
    public boolean VerifyDigest (MessageDigest md, Vector <String> Components)
    {
        while (Components.size()!=0)
        {
            md.update(Components.firstElement().getBytes());
            Components.remove(0);
        }

        byte []digesttmp = md.digest();
        
        if (MessageDigest.isEqual(getDigest(), digesttmp))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
