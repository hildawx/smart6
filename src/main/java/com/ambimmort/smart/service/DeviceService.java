/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ambimmort.smart.service;

import com.ambimmort.smart.bean.Device;
import com.ambimmort.smart.dao.DeviceDao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 *
 * @author Administrator
 */
public class DeviceService {
    
    private DeviceDao deviceDao;

    public DeviceService(Connection conn) {
        this.deviceDao = new DeviceDao(conn);
    }

    public boolean addDevice(String ip, String port) {
        Device device = new Device();
        device.setIpAddress(ip);
        device.setIpPort(Integer.parseInt(port));
        try {
            deviceDao.add(device);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public boolean delDevice(String ip, String port) {
        Device device = new Device();
        device.setIpAddress(ip);
        device.setIpPort(Integer.parseInt(port));
        try {
            deviceDao.delete(device);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }
    
    public JSONObject getDeviceList() {
        JSONObject obj = new JSONObject();
        JSONArray data = new JSONArray();
        try {
            List<Device> deviceList = deviceDao.queryAll();
            if (deviceList != null) {
                Iterator<Device> it = deviceList.iterator();
                while (it.hasNext()) {
                    JSONArray item = new JSONArray();
                    Device device = it.next();
                    item.add(device.getIpAddress());
                    item.add(device.getIpPort());
                    data.add(item);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(DeviceService.class.getName()).log(Level.SEVERE, null, ex);
        }
        obj.put("aaData", data);
        return obj;
    }
    
}
