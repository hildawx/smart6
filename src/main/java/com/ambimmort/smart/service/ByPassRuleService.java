/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ambimmort.smart.service;

import com.ambimmort.smart.common.RestClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class ByPassRuleService {
    
    private static String SET_BYPASS_URL = "/gn/smart6/shunt/rules/json";
    private static String REMOVE_BYPASS_URL = "/gn/smart6/shunt/delete/json";

    public boolean setBypassRule(String ip, String port, String rule) {
        JSONObject data = new JSONObject();
        data.put("shunt_rules", rule);
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(SET_BYPASS_URL);
        try {
            RestClient.getInstance().post(sb.toString(), data.toString());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ByPassRuleService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean removeBypassRule(String ip, String port) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(REMOVE_BYPASS_URL);
        try {
            RestClient.getInstance().post(sb.toString(), null);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(ByPassRuleService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
}
