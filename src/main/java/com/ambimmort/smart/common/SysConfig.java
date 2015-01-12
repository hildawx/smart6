/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ambimmort.smart.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class SysConfig {
    
    private static SysConfig conf;
    
    public static SysConfig getInstance() {
        try {
            if (conf == null) {
                conf = new SysConfig();
            }
        } catch (IOException ex) {
            Logger.getLogger(SysConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conf;
    }
    
    private Properties prop;
    
    private SysConfig() throws IOException{
        prop = new Properties();
        InputStream is = SysConfig.class.getClassLoader().getResourceAsStream("smart6.properties");
        prop.load(is);
        is.close();
    }
    
    public String get(String key) {
        return (String)prop.get(key);
    }
}
