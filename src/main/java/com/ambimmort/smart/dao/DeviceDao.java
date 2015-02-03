/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ambimmort.smart.dao;

import com.ambimmort.smart.bean.Device;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class DeviceDao {
    private Connection conn;
    
    public DeviceDao(Connection conn) {
        this.conn = conn;
    }

    public void add(Device device) throws SQLException {
        PreparedStatement pStat = conn.prepareStatement("insert into device(`ip_addr`,`ip_port`,`device_type`) values(?,?,?)");
        pStat.setString(1, device.getIpAddress());
        pStat.setInt(2, device.getIpPort());
        pStat.setInt(3, device.getDeviceType());
        pStat.executeUpdate();
    }
    
    public void delete(Device device) throws SQLException {
        PreparedStatement pStat = conn.prepareStatement("delete from device where `ip_addr` = ? and `ip_port` = ? and `device_type` = ?");
        pStat.setString(1, device.getIpAddress());
        pStat.setInt(2, device.getIpPort());
        pStat.setInt(3, device.getDeviceType());
        pStat.executeUpdate();
    }
    
    public List<Device> queryAll() throws SQLException {
        List<Device> devices = null;
        PreparedStatement pStat = conn.prepareStatement("select `id`, `ip_addr`, `ip_port`, `device_type` from device");
        ResultSet rs = pStat.executeQuery();
        while (rs.next()) {
            if (devices == null) {
                devices = new ArrayList<Device>();
            }
            Device device = new Device();
            device.setId(rs.getInt("id"));
            device.setIpAddress(rs.getString("ip_addr"));
            device.setIpPort(rs.getInt("ip_port"));
            device.setDeviceType(rs.getInt("device_type"));
            devices.add(device);
        }
        rs.close();
        return devices;
    }
    
    public List<Device> querySmart() throws SQLException{
        List<Device> devices = null;
        PreparedStatement pStat = conn.prepareStatement("select `id`, `ip_addr`, `ip_port`, `device_type` from device where device_type = 1");
        ResultSet rs = pStat.executeQuery();
        while (rs.next()) {
            if (devices == null) {
                devices = new ArrayList<Device>();
            }
            Device device = new Device();
            device.setId(rs.getInt("id"));
            device.setIpAddress(rs.getString("ip_addr"));
            device.setIpPort(rs.getInt("ip_port"));
            device.setDeviceType(rs.getInt("device_type"));
            devices.add(device);
        }
        rs.close();
        return devices;
    }

    public List<Device> queryEqualizer() throws SQLException{
        List<Device> devices = null;
        PreparedStatement pStat = conn.prepareStatement("select `id`, `ip_addr`, `ip_port`, `device_type` from device where device_type = 2");
        ResultSet rs = pStat.executeQuery();
        while (rs.next()) {
            if (devices == null) {
                devices = new ArrayList<Device>();
            }
            Device device = new Device();
            device.setId(rs.getInt("id"));
            device.setIpAddress(rs.getString("ip_addr"));
            device.setIpPort(rs.getInt("ip_port"));
            device.setDeviceType(rs.getInt("device_type"));
            devices.add(device);
        }
        rs.close();
        return devices;
    }
}
