package com.swust.chargingmobileback.model;

import java.util.Date;
import java.util.Objects;

public class Customer {
    private Integer id;

    private String idCard;

    private String name;

    private String phone;

    private String password;

    private String carType;

    private String chargeCardType;

    private String chargeCardId;

    private Double chargeCardMoney;

    private String chargeCardState;

    private Date stateUpdateTime;

    private String lastTransUid;

    private Date stateUpdateToGateTime;

    private String address;

    private String stationid;

    private String stationAccess;

    private Date createTime;

    private String createUser;

    private String mem;

    private Byte status;

    private Date updateTime;

    private String updateUser;

    private String stateChangeType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType == null ? null : carType.trim();
    }

    public String getChargeCardType() {
        return chargeCardType;
    }

    public void setChargeCardType(String chargeCardType) {
        this.chargeCardType = chargeCardType == null ? null : chargeCardType.trim();
    }

    public String getChargeCardId() {
        return chargeCardId;
    }

    public void setChargeCardId(String chargeCardId) {
        this.chargeCardId = chargeCardId == null ? null : chargeCardId.trim();
    }

    public Double getChargeCardMoney() {
        return chargeCardMoney;
    }

    public void setChargeCardMoney(Double chargeCardMoney) {
        this.chargeCardMoney = chargeCardMoney;
    }

    public String getChargeCardState() {
        return chargeCardState;
    }

    public void setChargeCardState(String chargeCardState) {
        this.chargeCardState = chargeCardState == null ? null : chargeCardState.trim();
    }

    public Date getStateUpdateTime() {
        return stateUpdateTime;
    }

    public void setStateUpdateTime(Date stateUpdateTime) {
        this.stateUpdateTime = stateUpdateTime;
    }

    public String getLastTransUid() {
        return lastTransUid;
    }

    public void setLastTransUid(String lastTransUid) {
        this.lastTransUid = lastTransUid == null ? null : lastTransUid.trim();
    }

    public Date getStateUpdateToGateTime() {
        return stateUpdateToGateTime;
    }

    public void setStateUpdateToGateTime(Date stateUpdateToGateTime) {
        this.stateUpdateToGateTime = stateUpdateToGateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getStationid() {
        return stationid;
    }

    public void setStationid(String stationid) {
        this.stationid = stationid == null ? null : stationid.trim();
    }

    public String getStationAccess() {
        return stationAccess;
    }

    public void setStationAccess(String stationAccess) {
        this.stationAccess = stationAccess == null ? null : stationAccess.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getMem() {
        return mem;
    }

    public void setMem(String mem) {
        this.mem = mem == null ? null : mem.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public String getStateChangeType() {
        return stateChangeType;
    }

    public void setStateChangeType(String stateChangeType) {
        this.stateChangeType = stateChangeType == null ? null : stateChangeType.trim();
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", idCard='" + idCard + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", carType='" + carType + '\'' +
                ", chargeCardType='" + chargeCardType + '\'' +
                ", chargeCardId='" + chargeCardId + '\'' +
                ", chargeCardMoney=" + chargeCardMoney +
                ", chargeCardState='" + chargeCardState + '\'' +
                ", stateUpdateTime=" + stateUpdateTime +
                ", lastTransUid='" + lastTransUid + '\'' +
                ", stateUpdateToGateTime=" + stateUpdateToGateTime +
                ", address='" + address + '\'' +
                ", stationid='" + stationid + '\'' +
                ", stationAccess='" + stationAccess + '\'' +
                ", createTime=" + createTime +
                ", createUser='" + createUser + '\'' +
                ", mem='" + mem + '\'' +
                ", status=" + status +
                ", updateTime=" + updateTime +
                ", updateUser='" + updateUser + '\'' +
                ", stateChangeType='" + stateChangeType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(idCard, customer.idCard) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(phone, customer.phone) &&
                Objects.equals(password, customer.password) &&
                Objects.equals(carType, customer.carType) &&
                Objects.equals(chargeCardType, customer.chargeCardType) &&
                Objects.equals(chargeCardId, customer.chargeCardId) &&
                Objects.equals(chargeCardMoney, customer.chargeCardMoney) &&
                Objects.equals(chargeCardState, customer.chargeCardState) &&
                Objects.equals(stateUpdateTime, customer.stateUpdateTime) &&
                Objects.equals(lastTransUid, customer.lastTransUid) &&
                Objects.equals(stateUpdateToGateTime, customer.stateUpdateToGateTime) &&
                Objects.equals(address, customer.address) &&
                Objects.equals(stationid, customer.stationid) &&
                Objects.equals(stationAccess, customer.stationAccess) &&
                Objects.equals(createTime, customer.createTime) &&
                Objects.equals(createUser, customer.createUser) &&
                Objects.equals(mem, customer.mem) &&
                Objects.equals(status, customer.status) &&
                Objects.equals(updateTime, customer.updateTime) &&
                Objects.equals(updateUser, customer.updateUser) &&
                Objects.equals(stateChangeType, customer.stateChangeType);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, idCard, name, phone, password, carType, chargeCardType, chargeCardId, chargeCardMoney, chargeCardState, stateUpdateTime, lastTransUid, stateUpdateToGateTime, address, stationid, stationAccess, createTime, createUser, mem, status, updateTime, updateUser, stateChangeType);
    }
}