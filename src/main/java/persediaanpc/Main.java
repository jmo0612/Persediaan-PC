/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persediaanpc;

import com.thowo.jmpcframework.JMPCFunctions;

/**
 *
 * @author jimi
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        //String localeCode="in_ID";
        //JMPCFunctions.init(localeCode);
        //new FormMain().setVisible(true);
        
        JMPCFunctions.initLookAndFeel(Main.class);
        //new JMPCSplashForm(new LoginForm(),GitIgnoreDBConnection.getDBs(),"img/splash.jpg",Main.class,"id_ID").setVisible(true);
        new SplashScreen().setVisible(true);
    }
    
}
