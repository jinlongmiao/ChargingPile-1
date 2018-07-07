package com.swust.chargingmobileback.model;

public class AppUsers {
    private Integer id;

    private String name;

    private String password;

    private String telephone;

    private String mail;

    private String imei;

    public AppUsers(String name, String pwd, String phone, String mail, String imei) {
        this.name = name;
        this.password = pwd;
        this.telephone = phone;
        this.mail = mail;
        this.imei = imei;
    }

    public AppUsers(int id, String name, String pwd, String phone, String mail, String imei) {
        this.id = id;
        this.name = name;
        this.password = pwd;
        this.telephone = phone;
        this.mail = mail;
        this.imei = imei;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei == null ? null : imei.trim();
    }
}