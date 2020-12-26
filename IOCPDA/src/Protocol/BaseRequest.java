/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Protocol;

/**
 *
 * @author Thomas
 * IOCPDA : Input Output Container Platform Data Analyse
 */
public class BaseRequest {
    //Requests IDs
    public static final int NOT_SET = -1;
    public static final int LOGIN_INITIATOR = 0;
    public static final int LOGIN_EID = 1;
    public static final int LOGIN_CARTES_A_PUCES = 2;
    public static final int LOGIN_OTP = 3;
    public static final int LOGIN_WEB = 4;
    public static final int DO_BIG_DATA = 5;
    public static final int BIG_DATA_RESULT = 6;
    public static final int LOGOUT = 7;
    
    private int id;
    private boolean status;
    private String error_msg;
    
    public BaseRequest()
    {
        id = NOT_SET;
        status = false;
        error_msg = null;
    }
    
    BaseRequest(int IDp, boolean statusp, String errorp)
    {
        setId(IDp);
        setStatus(statusp);
        setError_msg(errorp);
    }
    
    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * @return the status
     */
    public boolean getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(boolean status) {
        this.status = status;
    }
    
    /**
     * @return the error_msg
     */
    public String getError_msg() {
        return error_msg;
    }

    /**
     * @param error_msg the error_msg to set
     */
    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
