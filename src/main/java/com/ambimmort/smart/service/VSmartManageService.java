/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ambimmort.smart.service;

import com.ambimmort.smart.common.RestClient;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class VSmartManageService {
    private static String CREATE_VSMART = "/gn/vsmart6/create/json";
    private static String DELETE_VSMART = "/gn/vsmart6/delete/json";
    private static String SET_VSMART_POLICY = "/gn/vsmart6/master/policy/json";
    private static String VSMART_ICMP_SEARCH = "/gn/vsmart6/icmp/flowtb/query/json";
    private static String VSMART_TCP_SEARCH = "/gn/vsmart6/tcp/flowtb/query/json";
    private static String VSMART_UDP_SEARCH = "/gn/vsmart6/udp/flowtb/query/json";
    private static String VSMART_USERTB_SEARCH = "/gn/vsmart6/uesrtb/query/json";
    private static String VSMART_STATUS = "/gn/vsmart6/state/query/json";
    private static String QUERY_VSMART_CONFIG = "/gn/vsmart6/conf/query/json";
    
    private static String BACKUP_GROUP_ADD = "/gn/cluster/group/create/json";
    private static String BACKUP_GROUP_DEL = "/gn/cluster/group/delete/json";

    public boolean addvSmart(String ip, String port, String vSmartName) {
        JSONObject data = new JSONObject();
        data.put("vSmart6_name", vSmartName);
        
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(CREATE_VSMART);
        try {
            RestClient.getInstance().post(sb.toString(), data.toString());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(VSmartManageService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean delvSmart(String ip, String port, String vSmartName) {
        JSONObject data = new JSONObject();
        data.put("vSmart6_name", vSmartName);
        
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(DELETE_VSMART);
        try {
            RestClient.getInstance().delete(sb.toString(), data.toString());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(VSmartManageService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean setvSmartConfig(String ip, String port, String config) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(SET_VSMART_POLICY);
        try {
            RestClient.getInstance().post(sb.toString(), config);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(VSmartManageService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean createBackupGroup(String ip, String port, String groupName) {
        JSONObject data = new JSONObject();
        data.put("cluster_name", groupName);
        
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(BACKUP_GROUP_ADD);
        try {
            RestClient.getInstance().post(sb.toString(), data.toString());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(VSmartManageService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean deleteBackupGroup(String ip, String port, String groupName) {
        JSONObject data = new JSONObject();
        data.put("cluster_name", groupName);
        
        StringBuilder sb = new StringBuilder(0);
        sb.append("http://").append(ip).append(':').append(port).append(BACKUP_GROUP_DEL);
        try {
            RestClient.getInstance().delete(sb.toString(), data.toString());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(VSmartManageService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean addvSmartForGroup(String ip, String port, String vSmartName, String groupName) {
        return false;
    }

    public JSONArray searchICMP(String ip, String port, String vSmartName) {
        JSONArray rs = new JSONArray();
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(VSMART_ICMP_SEARCH).append('&').append(vSmartName);
        try {
            String resp = RestClient.getInstance().get(sb.toString());
            JSONObject rt = JSONArray.fromObject(resp).getJSONObject(0);
            Iterator<Entry> it = rt.entrySet().iterator();
            while (it.hasNext()) {
                JSONArray ob = new JSONArray();
                Entry entry = it.next();
                JSONObject item = ((JSONArray)entry.getValue()).getJSONObject(0);
                ob.add(item.getString("sipv6"));
                ob.add(item.getString("dipv6"));
                ob.add(item.getString("sport"));
                ob.add(item.getString("dport"));
                ob.add(item.getString("naptport"));
                rs.add(ob);
            }
        } catch (IOException ex) {
            Logger.getLogger(VSmartManageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public JSONArray searchTCP(String ip, String port, String vSmartName) {
        JSONArray rs = new JSONArray();
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(VSMART_TCP_SEARCH).append('&').append(vSmartName);
        try {
            String resp = RestClient.getInstance().get(sb.toString());
            JSONObject rt = JSONArray.fromObject(resp).getJSONObject(0);
            Iterator<Entry> it = rt.entrySet().iterator();
            while (it.hasNext()) {
                JSONArray ob = new JSONArray();
                Entry entry = it.next();
                JSONObject item = ((JSONArray)entry.getValue()).getJSONObject(0);
                ob.add(item.getString("sipv6"));
                ob.add(item.getString("dipv6"));
                ob.add(item.getString("sport"));
                ob.add(item.getString("dport"));
                ob.add(item.getString("naptport"));
                rs.add(ob);
            }
        } catch (IOException ex) {
            Logger.getLogger(VSmartManageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public JSONArray searchUDP(String ip, String port, String vSmartName) {
        JSONArray rs = new JSONArray();
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(VSMART_UDP_SEARCH).append('&').append(vSmartName);
        try {
            String resp = RestClient.getInstance().get(sb.toString());
            JSONObject rt = JSONArray.fromObject(resp).getJSONObject(0);
            Iterator<Entry> it = rt.entrySet().iterator();
            while (it.hasNext()) {
                JSONArray ob = new JSONArray();
                Entry entry = it.next();
                JSONObject item = ((JSONArray)entry.getValue()).getJSONObject(0);
                ob.add(item.getString("sipv6"));
                ob.add(item.getString("dipv6"));
                ob.add(item.getString("sport"));
                ob.add(item.getString("dport"));
                ob.add(item.getString("naptport"));
                rs.add(ob);
            }
        } catch (IOException ex) {
            Logger.getLogger(VSmartManageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public JSONArray searchUserTB(String ip, String port, String vSmartName) {
        JSONArray rs = new JSONArray();
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(VSMART_USERTB_SEARCH).append('&').append(vSmartName);
        try {
            String resp = RestClient.getInstance().get(sb.toString());
            JSONObject rt = JSONArray.fromObject(resp).getJSONObject(0);
            Iterator<Entry> it = rt.entrySet().iterator();
            while (it.hasNext()) {
                JSONArray ob = new JSONArray();
                Entry entry = it.next();
                JSONObject item = ((JSONArray)entry.getValue()).getJSONObject(0);
                ob.add(item.getString("User_IPv6"));
                ob.add(item.getString("NatIp"));
                ob.add(item.getString("NaptIp"));
                rs.add(ob);
            }
        } catch (IOException ex) {
            Logger.getLogger(VSmartManageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
}
