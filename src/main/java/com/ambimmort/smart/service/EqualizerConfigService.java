/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ambimmort.smart.service;

import com.ambimmort.smart.common.RestClient;
import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class EqualizerConfigService {
    
    public static final String REAL_ADDRESSS_URL = "/gnslb/nexthop/get/json";
    public static final String SCHEDULING_ALGORITHM = "/gnslb/scheduling/algorithm/json";
    public static final String FLOW_SPEED = "/gnslb/nexthop/flow/speed/json";

    public JSONArray getRealAddress(String ip, String port) {
        JSONArray arr = new JSONArray();
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(":").append(port).append(REAL_ADDRESSS_URL);
        try {
            String resp = RestClient.getInstance().get(sb.toString());
            JSONArray ar = JSONArray.fromObject(resp);
            if (ar.size() > 0) {
                JSONObject target = ar.getJSONObject(0);
                Iterator it = target.keys();
                while (it.hasNext()) {
                    String name = (String)it.next();
                    JSONObject o = new JSONObject();
                    o.put("name", name);
                    o.put("mac", target.getString(name));
                    arr.add(o);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(EqualizerConfigService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }
    
    public boolean sendFlowSpeed(String ip, String port, String config) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(":").append(port).append(FLOW_SPEED);
        try {
            RestClient.getInstance().post(sb.toString(), config);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(EqualizerConfigService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public boolean sendSchedulingAlgorithm(String ip, String port, String mode) {
        JSONObject obj = new JSONObject();
        obj.put("scheduling_algorithm", mode);
        
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(":").append(port).append(SCHEDULING_ALGORITHM);
        try {
            RestClient.getInstance().post(sb.toString(), obj.toString());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(EqualizerConfigService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
}
