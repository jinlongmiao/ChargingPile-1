package com.swust.back.model;

//import javax.persistence.*;
//
//@Table(name = "t_pile_type")
public class PileType {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Hex:b4b5
     */
//    @Column(name = "type_code")
    private String typeCode;

    /**
     * 分类名称
     */
//    @Column(name = "type_name")
    private String typeName;

    /**
     * 额定功率或电流
     */
    private String wi;

    /**
     * 充电口数量
     */
    private Byte port;

    /**
     * dc直流,ac交流
     */
    private String type;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取Hex:b4b5
     *
     * @return type_code - Hex:b4b5
     */
    public String getTypeCode() {
        return typeCode;
    }

    /**
     * 设置Hex:b4b5
     *
     * @param typeCode Hex:b4b5
     */
    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    /**
     * 获取分类名称
     *
     * @return type_name - 分类名称
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * 设置分类名称
     *
     * @param typeName 分类名称
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 获取额定功率或电流
     *
     * @return wi - 额定功率或电流
     */
    public String getWi() {
        return wi;
    }

    /**
     * 设置额定功率或电流
     *
     * @param wi 额定功率或电流
     */
    public void setWi(String wi) {
        this.wi = wi;
    }

    /**
     * 获取充电口数量
     *
     * @return port - 充电口数量
     */
    public Byte getPort() {
        return port;
    }

    /**
     * 设置充电口数量
     *
     * @param port 充电口数量
     */
    public void setPort(Byte port) {
        this.port = port;
    }

    /**
     * 获取dc直流,ac交流
     *
     * @return type - dc直流,ac交流
     */
    public String getType() {
        return type;
    }

    /**
     * 设置dc直流,ac交流
     *
     * @param type dc直流,ac交流
     */
    public void setType(String type) {
        this.type = type;
    }
}