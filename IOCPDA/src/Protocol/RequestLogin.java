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
public class RequestLogin extends BaseRequest {
    
    private String username;
    private Byte[] digest;

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
    public Byte[] getDigest() {
        return digest;
    }

    /**
     * @param Digest the digest to set
     */
    public void setDigest(Byte[] Digest) {
        this.digest = Digest;
    }
}
