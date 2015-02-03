/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ambimmort.smart.service;

import com.ambimmort.smart.common.SysConfig;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class ConfigFileManager {
    
    private static ConfigFileManager manager;
    
    private String configRootDir;
    
    private ConfigFileManager(){
        configRootDir = SysConfig.getInstance().get("config.dir");
        File dir = new File(configRootDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }
    
    public static ConfigFileManager getInstance() {
        if (manager == null) {
            manager = new ConfigFileManager();
        }
        return manager;
    }
    
    public void writeConfig(String FileName, String config) {
        try {
            FileWriter fw = new FileWriter(configRootDir + "/" + FileName);
            fw.write(config);
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(ConfigFileManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void deleteConfigFile(String FileName) {
        File f = new File(configRootDir + "/" + FileName);
        f.deleteOnExit();
    }
    
    public String getConfig(String FileName) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(configRootDir + "/" + FileName));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException ex) {
            Logger.getLogger(ConfigFileManager.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
