/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocol;

import java.security.cert.Certificate;



/**
 *
 * @author Thomas
 */
public class RequestLogineID extends RequestLogin {
    
    private Certificate eIDcertificate;
    
    /**
     * @return the eIDcertificate
     */
    public Certificate geteIDcertificate() {
        return eIDcertificate;
    }

    /**
     * @param eIDcertificate the eIDcertificate to set
     */
    public void seteIDcertificate(Certificate eIDcertificate) {
        this.eIDcertificate = eIDcertificate;
    }
    
}
