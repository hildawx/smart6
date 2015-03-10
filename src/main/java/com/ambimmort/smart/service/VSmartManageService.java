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
    public static String CREATE_VSMART = "/gn/vsmart6/create/json";
    public static String DELETE_VSMART = "/gn/vsmart6/delete/json";
    public static String ALL_VSMART_LIST = "/gn/vsmart6/query/json";
    public static String SET_VSMART_MASTER_POLICY = "/gn/vsmart6/master/policy/json";
    public static String SET_VSMART_SLAVE_POLICY = "/gn/vsmart6/slave/policy/json";
    public static String VSMART_ICMP_SEARCH = "/gn/vsmart6/icmp/flowtb/query/json";
    public static String VSMART_TCP_SEARCH = "/gn/vsmart6/tcp/flowtb/query/json";
    public static String VSMART_UDP_SEARCH = "/gn/vsmart6/udp/flowtb/query/json";
    public static String VSMART_USERTB_SEARCH = "/gn/vsmart6/usertb/query/json";
    public static String VSMART_STATUS = "/gn/vsmart6/state/query/json";
    public static String QUERY_VSMART_CONFIG = "/gn/vsmart6/conf/query/json";
    public static String VSMART_GROUP_STATE = "/gn/vsmart6/state/query/json";
    
    public static String SMART_RUNNING_INFO = "/gn/smart6/cpuandmem/json";

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
        deleteConfigLocal(ip, port, vSmartName);
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
        saveConfigLocal(ip, port, config);
        
        JSONObject confObj = JSONObject.fromObject(config);
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(VSMART_GROUP_STATE).append('&').append(confObj.getString("vSmart6_name"));
        try {
            String resp = RestClient.getInstance().get(sb.toString());
            String stat = JSONArray.fromObject(resp).getJSONObject(0).getString("state");
            if ("slave".equals(stat)) {
                JSONObject slaveConfObj = new JSONObject();
                slaveConfObj.put("vSmart6_name", confObj.getString("vSmart6_name"));
                slaveConfObj.put("v6lanip", confObj.getString("v6lanip"));
                slaveConfObj.put("Aaddr_pool", confObj.getString("prefA_nat_start") + "-" + confObj.getString("prefA_nat_end"));
                slaveConfObj.put("Baddr_pool", confObj.getString("Baddr_pool"));

                sb = new StringBuilder();
                sb.append("http://").append(ip).append(':').append(port).append(SET_VSMART_SLAVE_POLICY);
                RestClient.getInstance().post(sb.toString(), slaveConfObj.toString());
            } else {
                confObj.put("Aaddr_pool", confObj.getString("prefA_nat_start") + "-" + confObj.getString("prefA_nat_end"));
                confObj.remove("prefA_nat_start");
                confObj.remove("prefA_nat_end");

                sb = new StringBuilder();
                sb.append("http://").append(ip).append(':').append(port).append(SET_VSMART_MASTER_POLICY);
                RestClient.getInstance().post(sb.toString(), config);
            }
            return true;
        } catch (IOException ex) {
            Logger.getLogger(VSmartManageService.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
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
    
    public JSONObject queryvSmartConfig(String ip, String port, String vSmartName) {
        JSONObject rs = new JSONObject();
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(QUERY_VSMART_CONFIG).append('&').append(vSmartName);
        try {
            String resp = RestClient.getInstance().get(sb.toString());
            JSONObject o = JSONArray.fromObject(resp).getJSONObject(0);
            if (o.has("cluster_name")) {
                rs.put("cluster_name", o.getString("cluster_name"));
            }
            rs.put("pid", o.getString("pid"));
            rs.put("v6lanip", o.getString("v6lanip"));
            rs.put("v6lan_mac", o.getString("v6lan-mac"));
            rs.put("v6gateway", o.getString("v6gateway"));
            rs.put("v6gateway_mac", o.getString("v6gateway-mac"));
            rs.put("v4wanip", o.getString("v4wanip"));
            rs.put("v4wan_mac", o.getString("v4wan-mac"));
            rs.put("v4gateway", o.getString("v4gateway"));
            rs.put("v4gateway_mac", o.getString("v4gateway-mac"));
            rs.put("prefA_feature_prefA_Len", buildPrefFeatureAndLen(o.getJSONArray("A")));
            rs.put("Aaddrpool", o.getString("Aaddrpool"));
            rs.put("prefB_feature_prefB_Len", buildPrefFeatureAndLen(o.getJSONArray("B")));
            rs.put("Baddrpool", o.getString("Baddrpool"));
            rs.put("prefix_len", o.getString("prefix/len"));
        } catch (IOException ex) {
            Logger.getLogger(VSmartManageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    private String buildPrefFeatureAndLen(JSONArray feature) {
        if (feature == null || feature.size() == 0) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        Iterator it = feature.iterator();
        int i = 0;
        while (it.hasNext()) {
            JSONObject o = (JSONObject) it.next();
            for (Object key : o.keySet()) {
                if (i != 0) {
                    sb.append(';');
                }
                sb.append(o.get(key));
                i++;
            }
        }
        return sb.toString();
    }

    public JSONObject getSmartStatisticInfo(String ip, String port) {
        JSONObject rs = new JSONObject();
        
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(SMART_RUNNING_INFO);
        try {
            String resp = RestClient.getInstance().get(sb.toString());
            JSONObject rsObj = JSONArray.fromObject(resp).getJSONObject(0);
            rs.put("cpu", rsObj.getString("cpu_rate"));
            rs.put("memory", rsObj.getString("mem_rate"));
        } catch (IOException ex) {
            Logger.getLogger(VSmartManageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rs;
    }
    
    public JSONArray getAllvSmartInfo(String ip, String port) {
        JSONArray rtArr = new JSONArray();
        StringBuilder sb = new StringBuilder();
        sb.append("http://").append(ip).append(':').append(port).append(ALL_VSMART_LIST);
        
        try {
            String resp = RestClient.getInstance().get(sb.toString());
            JSONArray arr = JSONArray.fromObject(resp);
            if (arr.size() > 0) {
                String all_vsmart6 = arr.getJSONObject(0).getString("all_vsmart6");
                if (all_vsmart6 != null && !"".equals(all_vsmart6)) {
                    String[] vSmartList = all_vsmart6.split("-");
                    for (int i = 0; i < vSmartList.length; i++) {
                        String vSmart = vSmartList[i];
                        sb = new StringBuilder();
                        sb.append("http://").append(ip).append(':').append(port).append(VSMART_GROUP_STATE).append('&').append(vSmart);
                        resp = RestClient.getInstance().get(sb.toString());
                        String stat = JSONArray.fromObject(resp).getJSONObject(0).getString("state");
                        JSONArray item = new JSONArray();
                        item.add(vSmart);
                        item.add(stat);
                        rtArr.add(item);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(VSmartManageService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rtArr;
    }
    
    public JSONObject getvSmartConfig(String ip, String port, String vSmartName) {
        String fileName = ip + "." + port + "." + vSmartName + ".cfg";
        String conf = ConfigFileManager.getInstance().getConfig(fileName);
        if (conf != null) {
            return JSONObject.fromObject(conf);
        }
        return null;
    }

    private void saveConfigLocal(String ip, String port, String config) {
        String vSmartName = ip + "." + port + "." + JSONObject.fromObject(config).getString("vSmart6_name") + ".cfg";
        ConfigFileManager.getInstance().writeConfig(vSmartName, config);
    }

    private void deleteConfigLocal(String ip, String port, String vSmartName) {
        String fileName = ip + "." + port + "." + vSmartName + ".cfg";
        ConfigFileManager.getInstance().deleteConfigFile(fileName);
    }
}
