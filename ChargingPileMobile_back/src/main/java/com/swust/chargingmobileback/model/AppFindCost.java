package com.swust.chargingmobileback.model;

import java.util.Date;

public class AppFindCost {
    private String phone;

    private String username;

    private String stationname;

    private String pilenumber;

    private String port;

    private Float cost;

    private Date costtime;

    private Date endtime;

    private Date alltime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname == null ? null : stationname.trim();
    }

    public String getPilenumber() {
        return pilenumber;
    }

    public void setPilenumber(String pilenumber) {
        this.pilenumber = pilenumber == null ? null : pilenumber.trim();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }

    public Date getCosttime() {
        return costtime;
    }

    public void setCosttime(Date costtime) {
        this.costtime = costtime;
    }

    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getAlltime() {
        return alltime;
    }

    public void setAlltime(Date alltime) {
        this.alltime = alltime;
    }
}