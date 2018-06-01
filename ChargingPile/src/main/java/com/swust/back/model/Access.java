package com.swust.back.model;

//import javax.persistence.*;
//
//@Table(name = "t_access")
public class Access {
//    @Column(name = "role_id")
    private Short roleId;
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "node_id")
    private Short nodeId;

    private byte level;

    private Short pid;

    private String module;

    /**
     * @return role_id
     */
    public Short getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(Short roleId) {
        this.roleId = roleId;
    }

    /**
     * @return node_id
     */
    public Short getNodeId() {
        return nodeId;
    }

    /**
     * @param nodeId
     */
    public void setNodeId(Short nodeId) {
        this.nodeId = nodeId;
    }

    /**
     * @return level
     */
    public byte getLevel() {
        return level;
    }

    /**
     * @param level
     */
    public void setLevel(byte level) {
        this.level = level;
    }

    /**
     * @return pid
     */
    public Short getPid() {
        return pid;
    }

    /**
     * @param pid
     */
    public void setPid(Short pid) {
        this.pid = pid;
    }

    /**
     * @return module
     */
    public String getModule() {
        return module;
    }

    /**
     * @param module
     */
    public void setModule(String module) {
        this.module = module;
    }
}