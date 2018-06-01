package com.swust.back.model;

//import javax.persistence.*;
//
//import java.beans.Transient;

//@Table(name = "t_user")
public class User {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    private String account;

    private String nickname;

    private String password;

//    @Transient
    private String captcha;
//    @Column(name = "bind_account")
    private String bindAccount;

    private String dwdm;

//    @Column(name = "last_login_time")
    private Integer lastLoginTime;

//    @Column(name = "last_login_ip")
    private String lastLoginIp;

//    @Column(name = "login_count")
    private Integer loginCount;

    private String verify;

    private String email;

    private String remark;

//    @Column(name = "create_time")
    private Integer createTime;

//    @Column(name = "update_time")
    private Integer updateTime;

    private Boolean status;

    private Byte type;

    private String photo;

//    @Column(name = "max_number")
    private Byte maxNumber;

    private String info;

    // 加密密码的盐
//    @Transient
    private String salt;


    // 角色Id
//    @Transient
    private Integer roleId;

    // 角色名称
//    @Transient
    private String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    /**
     * 密码盐
     * @return
     */
    public String getCredentialsSalt(){
        return this.account + this.salt;
    }


    /**
     * @return id
     */
    public Short getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Short id) {
        this.id = id;
    }

    /**
     * @return account
     */
    public String getAccount() {
        return account;
    }

    /**
     * @param account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return bind_account
     */
    public String getBindAccount() {
        return bindAccount;
    }

    /**
     * @param bindAccount
     */
    public void setBindAccount(String bindAccount) {
        this.bindAccount = bindAccount;
    }

    /**
     * @return dwdm
     */
    public String getDwdm() {
        return dwdm;
    }

    /**
     * @param dwdm
     */
    public void setDwdm(String dwdm) {
        this.dwdm = dwdm;
    }

    /**
     * @return last_login_time
     */
    public Integer getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * @param lastLoginTime
     */
    public void setLastLoginTime(Integer lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * @return last_login_ip
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * @param lastLoginIp
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    /**
     * @return login_count
     */
    public Integer getLoginCount() {
        return loginCount;
    }

    /**
     * @param loginCount
     */
    public void setLoginCount(Integer loginCount) {
        this.loginCount = loginCount;
    }

    /**
     * @return verify
     */
    public String getVerify() {
        return verify;
    }

    /**
     * @param verify
     */
    public void setVerify(String verify) {
        this.verify = verify;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return create_time
     */
    public Integer getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Integer getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return status
     */
    public Boolean getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Boolean status) {
        this.status = status;
    }

    /**
     * @return type
     */
    public Byte getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * @return photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return max_number
     */
    public Byte getMaxNumber() {
        return maxNumber;
    }

    /**
     * @param maxNumber
     */
    public void setMaxNumber(Byte maxNumber) {
        this.maxNumber = maxNumber;
    }

    /**
     * @return info
     */
    public String getInfo() {
        return info;
    }

    /**
     * @param info
     */
    public void setInfo(String info) {
        this.info = info;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}