/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desktopapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cam_i
 */
public class DesktopApp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try{
            Properties prop = new Properties();
            prop.load(new FileInputStream("desktop.properties"));
            String host = prop.getProperty("IpServer", "localhost");
            int port = Integer.parseInt(prop.getProperty("portServer", "50000"));

            MainWindow window = new MainWindow(host,port);
            if(window.login())
                window.setVisible(true);
            
            System.exit(0);
        }
        catch (IOException ex) {
            Logger.getLogger(DesktopApp.class.getName()).log(Level.SEVERE, null, ex);
            
            System.exit(1);
        }

    }
    
}
