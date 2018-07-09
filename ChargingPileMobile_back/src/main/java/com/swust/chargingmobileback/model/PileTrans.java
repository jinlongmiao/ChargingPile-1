package com.swust.chargingmobileback.model;

import java.util.Date;

public class PileTrans {
    private Integer id;

    private String transUid;

    private String gateid;

    private String pileid;

    private String transId;

    private Date transTime;

    private Date transDate;

    private String step;

    private Integer percent;

    private String preV;

    private String chargeCardId;

    private String port;

    private Float ac;

    private Float dc;

    private Float s1Money;

    private String s1Preset;

    private String s1ChargeType;

    private String s1ChargeStartType;

    private Float s2Money;

    private String s2Hexmoney;

    private String s2Rate;

    private String s2Isok;

    private String s2ReasonEnd;

    private Float s3Money1;

    private Float s3Money2;

    private Boolean offline;

    private Boolean ispay;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTransUid() {
        return transUid;
    }

    public void setTransUid(String transUid) {
        this.transUid = transUid == null ? null : transUid.trim();
    }

    public String getGateid() {
        return gateid;
    }

    public void setGateid(String gateid) {
        this.gateid = gateid == null ? null : gateid.trim();
    }

    public String getPileid() {
        return pileid;
    }

    public void setPileid(String pileid) {
        this.pileid = pileid == null ? null : pileid.trim();
    }

    public String getTransId() {
        return transId;
    }

    public void setTransId(String transId) {
        this.transId = transId == null ? null : transId.trim();
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step == null ? null : step.trim();
    }

    public Integer getPercent() {
        return percent;
    }

    public void setPercent(Integer percent) {
        this.percent = percent;
    }

    public String getPreV() {
        return preV;
    }

    public void setPreV(String preV) {
        this.preV = preV == null ? null : preV.trim();
    }

    public String getChargeCardId() {
        return chargeCardId;
    }

    public void setChargeCardId(String chargeCardId) {
        this.chargeCardId = chargeCardId == null ? null : chargeCardId.trim();
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port == null ? null : port.trim();
    }

    public Float getAc() {
        return ac;
    }

    public void setAc(Float ac) {
        this.ac = ac;
    }

    public Float getDc() {
        return dc;
    }

    public void setDc(Float dc) {
        this.dc = dc;
    }

    public Float getS1Money() {
        return s1Money;
    }

    public void setS1Money(Float s1Money) {
        this.s1Money = s1Money;
    }

    public String getS1Preset() {
        return s1Preset;
    }

    public void setS1Preset(String s1Preset) {
        this.s1Preset = s1Preset == null ? null : s1Preset.trim();
    }

    public String getS1ChargeType() {
        return s1ChargeType;
    }

    public void setS1ChargeType(String s1ChargeType) {
        this.s1ChargeType = s1ChargeType == null ? null : s1ChargeType.trim();
    }

    public String getS1ChargeStartType() {
        return s1ChargeStartType;
    }

    public void setS1ChargeStartType(String s1ChargeStartType) {
        this.s1ChargeStartType = s1ChargeStartType == null ? null : s1ChargeStartType.trim();
    }

    public Float getS2Money() {
        return s2Money;
    }

    public void setS2Money(Float s2Money) {
        this.s2Money = s2Money;
    }

    public String getS2Hexmoney() {
        return s2Hexmoney;
    }

    public void setS2Hexmoney(String s2Hexmoney) {
        this.s2Hexmoney = s2Hexmoney == null ? null : s2Hexmoney.trim();
    }

    public String getS2Rate() {
        return s2Rate;
    }

    public void setS2Rate(String s2Rate) {
        this.s2Rate = s2Rate == null ? null : s2Rate.trim();
    }

    public String getS2Isok() {
        return s2Isok;
    }

    public void setS2Isok(String s2Isok) {
        this.s2Isok = s2Isok == null ? null : s2Isok.trim();
    }

    public String getS2ReasonEnd() {
        return s2ReasonEnd;
    }

    public void setS2ReasonEnd(String s2ReasonEnd) {
        this.s2ReasonEnd = s2ReasonEnd == null ? null : s2ReasonEnd.trim();
    }

    public Float getS3Money1() {
        return s3Money1;
    }

    public void setS3Money1(Float s3Money1) {
        this.s3Money1 = s3Money1;
    }

    public Float getS3Money2() {
        return s3Money2;
    }

    public void setS3Money2(Float s3Money2) {
        this.s3Money2 = s3Money2;
    }

    public Boolean getOffline() {
        return offline;
    }

    public void setOffline(Boolean offline) {
        this.offline = offline;
    }

    public Boolean getIspay() {
        return ispay;
    }

    public void setIspay(Boolean ispay) {
        this.ispay = ispay;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}