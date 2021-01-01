/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils.Cards;

import javacard.framework.APDU;
import javacard.framework.Applet;
import javacard.framework.ISO7816;
import javacard.framework.ISOException;
import javacard.framework.OwnerPIN;
import javacard.framework.Util;

/**
 *
 * @author cam_i
 */
public class CartePuceApplet extends Applet
{
    //CONSTANTES
    public final static byte CartePuceLogin_CLA =(byte)0xB0;
    
    public final static byte VERIFY = (byte) 0x20;
    public final static byte GET_LOGINS = (byte) 0x30;
    public final static byte LOGIN_DONE = (byte) 0x40;
    
    final static short SW_VERIFICATION_FAILED = 0x6300;
    final static short SW_PIN_VERIFICATION_REQUIRED = 0x6301;
    
    final static byte PIN_TRY_LIMIT =(byte)0x03;
    final static byte MAX_PIN_SIZE =(byte)0x04;
    
    OwnerPIN pin;
    short nbreConnexions;
    
    private CartePuceApplet(byte[] bArray,short bOffset,byte bLength)
    {
        pin = new OwnerPIN(PIN_TRY_LIMIT,   MAX_PIN_SIZE);
        
        bArray[0]=0x01;
        bArray[1]=0x02; 
        bArray[2]=0x03;
        bArray[3]=0x04;
        
        pin.update(bArray, (short)(0), MAX_PIN_SIZE);
        nbreConnexions = 0;
        register();
    }
    
    public static void install(byte[] bArray, short bOffset, byte bLength)
    {
        new CartePuceApplet (bArray, bOffset, bLength);
    }
    
    public boolean select()
    {
        if ( pin.getTriesRemaining() == 0 )
            return false;
        return true;
    }
    
    public void process(APDU apdu) throws ISOException
    {
        byte[] buffer = apdu.getBuffer();

        if (selectingApplet())
            return;

        if (buffer[ISO7816.OFFSET_CLA] != CartePuceLogin_CLA)
            ISOException.throwIt(ISO7816.SW_CLA_NOT_SUPPORTED);

        switch (buffer[ISO7816.OFFSET_INS])
        {
            case GET_LOGINS: getLogin(apdu);
                        return;
            case VERIFY:    verify(apdu);
                            return; 
            case LOGIN_DONE: loginDone();
            default: ISOException.throwIt(ISO7816.SW_INS_NOT_SUPPORTED);
        }
    }
    
    private void verify(APDU apdu)
    {
        byte[] buffer = apdu.getBuffer();
        
        // lecture du PIN entré
        byte byteRead = (byte)(apdu.setIncomingAndReceive());
        if ( pin.check(buffer, ISO7816.OFFSET_CDATA, byteRead) == false )
            ISOException.throwIt (SW_VERIFICATION_FAILED);
    }
    
    
    private void getLogin(APDU apdu)
    {
        byte[] buffer = apdu.getBuffer();

        // nombre de bytes prévus par l'hôte pour la réponse
        short le = apdu.setOutgoing();
        if ( le < 2 )
            ISOException.throwIt (ISO7816.SW_WRONG_LENGTH);
        
        if ( ! pin.isValidated() )
            ISOException.throwIt(SW_PIN_VERIFICATION_REQUIRED);
        
        apdu.setOutgoingLength((byte)2);
        
        //écriture dans le buffer APDU
        Util.setShort (buffer, (short)0, nbreConnexions);
        
        // écriture dans le buffer APDU
        apdu.sendBytes((short)0, (short)2); 
    }

    public void deselect()
    {
        pin.reset();
    }

    private void loginDone() {
        if ( ! pin.isValidated() )
            ISOException.throwIt(SW_PIN_VERIFICATION_REQUIRED);
        
        nbreConnexions++;
    }

}
