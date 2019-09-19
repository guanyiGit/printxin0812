package org.javatribe.calculator.module;

import java.io.Serializable;
import java.util.Date;

public class TUsers implements Serializable{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -6933673280965963564L;

	private Long userId;

    private Integer roleId;

    private Integer organizationId;

    private Long dogOwnId;

    private Integer orgType;

    private Integer districtId;

    private String openId;

    private String username;

    private String password;

    private String name;

    private Integer sex;

    private String phone;

    private String email;

    private Integer uStatus;

    private Integer loginNum;

    private Date recentloginTime;

    private Date creationTime;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Integer organizationId) {
        this.organizationId = organizationId;
    }

    public Long getDogOwnId() {
        return dogOwnId;
    }

    public void setDogOwnId(Long dogOwnId) {
        this.dogOwnId = dogOwnId;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public Integer getDistrictId() {
        return districtId;
    }

    public void setDistrictId(Integer districtId) {
        this.districtId = districtId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getuStatus() {
        return uStatus;
    }

    public void setuStatus(Integer uStatus) {
        this.uStatus = uStatus;
    }

    public Integer getLoginNum() {
        return loginNum;
    }

    public void setLoginNum(Integer loginNum) {
        this.loginNum = loginNum;
    }

    public Date getRecentloginTime() {
        return recentloginTime;
    }

    public void setRecentloginTime(Date recentloginTime) {
        this.recentloginTime = recentloginTime;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}