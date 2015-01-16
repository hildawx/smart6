/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ambimmort.smart.service;

import com.ambimmort.smart.common.RestClient;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class BackupGroupService {
    public static String BACKUP_GROUP_ADD = "/gn/cluster/group/create/json";
    public static String BACKUP_GROUP_DEL = "/gn/cluster/group/delete/json";
    public static String BACKUP_GROUP_LIST = "/gn/cluster/query/json";
    public static String ADD_VSMART_INTO_GROUP = "/gn/vsmart6/insert2cluster/json";
    public static String REMOVE_VSMART_INTO_GROUP = "/gn/vsmart6/del5cluster/json";
    
    public JSONArray getAllBackupGroup(String ip, String port) {
        JSONArray arr = new JSONArray();
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(BACKUP_GROUP_LIST);
        try {
            String resp = RestClient.getInstance().get(sb.toString());
            JSONArray resArr = JSONArray.fromObject(resp);
            if (resArr.size() > 0) {
                String[] groups = resArr.getJSONObject(0).getString("groups").split("-");
                for (int i=0; i<groups.length; i++) {
                    JSONArray item = new JSONArray();
                    item.add(groups[i]);
                    arr.add(item);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(BackupGroupService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arr;
    }
    
    public boolean addvSmart2Group(String ip, String port, String vSmartName, String groupName) {
        JSONObject data = new JSONObject();
        data.put("vSmart6_name", vSmartName);
        data.put("cluster_name", groupName);
        
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(ADD_VSMART_INTO_GROUP);
        try {
            RestClient.getInstance().post(sb.toString(), data.toString());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(BackupGroupService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public boolean removevSmartFromGroup(String ip, String port, String vSmartName, String groupName) {
        JSONObject data = new JSONObject();
        data.put("vSmart6_name", vSmartName);
        data.put("cluster_name", groupName);
        
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(REMOVE_VSMART_INTO_GROUP);
        try {
            RestClient.getInstance().post(sb.toString(), data.toString());
            return true;
        } catch (IOException ex) {
            Logger.getLogger(BackupGroupService.class.getName()).log(Level.SEVERE, null, ex);
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
    
}
