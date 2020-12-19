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
public class RequestVerifyOTP extends BaseRequest {

    private int OTP;
    
    
    /**
     * @return the OTP
     */
    public int getOTP() {
        return OTP;
    }

    /**
     * @param OTP the OTP to set
     */
    public void setOTP(int OTP) {
        this.OTP = OTP;
    }
}
