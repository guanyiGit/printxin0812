package org.javatribe.calculator.module;

import java.util.Date;

/**
 * 犬证表
 */
public class TDogCard {
    private Long seqNum;

    /**
     * 犬id
     */
    private Long dogId;

    /**
     * 犬主id
     */
    private Long dogOwnerId;

    /**
     * 会员证id
     */
    private Long memberCardId;

    /**
     * 证书编号--not null
     */
    private String dogCardNum;

    /**
     * 有效期开始时间--not null
     */
    private Date startTime;

    /**
     * 有效期结束时间--not null
     */
    private Date endTime;

    /**
     * 签发时间
     */
    private Date lssueTime;

    /**
     * 发证机构id
     */
    private Integer lssueOrgId;

    /**
     * 签发人，办理人--关联用户id
     */
    private Long lssuerId;

    /**
     * 状态--not null
     * 0--已禁用
     * 1--启用
     * 2--已过期
     * 3--注销
     * 4--待审核
     */
    private Integer status;

    /**
     * 创建时间--not null
     */
    private Date creationTime;

    private Integer isAudit;
    
    /**
     * 备注
     */
    private String remark;


    private Date cardmakingTime; //制卡时间
    private Integer cardmakingStatus; //制卡状态
    private Integer makingId;  //制卡人

    public Integer getMaking_id() {
        return makingId;
    }

    public void setMaking_id(Integer making_id) {
        this.makingId = making_id;
    }


    public Date getCardmakingTime() {
        return cardmakingTime;
    }

    public void setCardmakingTime(Date cardmakingTime) {
        this.cardmakingTime = cardmakingTime;
    }

    public Integer getCardmakingStatus() {
        return cardmakingStatus;
    }

    public void setCardmakingStatus(Integer cardmakingStatus) {
        this.cardmakingStatus = cardmakingStatus;
    }



    private String devicenum;

    public Long getSeqNum() {
        return seqNum;
    }

    public void setSeqNum(Long seqNum) {
        this.seqNum = seqNum;
    }

    public Long getDogId() {
        return dogId;
    }

    public void setDogId(Long dogId) {
        this.dogId = dogId;
    }

    public Long getDogOwnerId() {
        return dogOwnerId;
    }

    public void setDogOwnerId(Long dogOwnerId) {
        this.dogOwnerId = dogOwnerId;
    }

    public Long getMemberCardId() {
        return memberCardId;
    }

    public void setMemberCardId(Long memberCardId) {
        this.memberCardId = memberCardId;
    }

    public String getDogCardNum() {
        return dogCardNum;
    }

    public void setDogCardNum(String dogCardNum) {
        this.dogCardNum = dogCardNum == null ? null : dogCardNum.trim();
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getLssueTime() {
        return lssueTime;
    }

    public void setLssueTime(Date lssueTime) {
        this.lssueTime = lssueTime;
    }

    public Integer getLssueOrgId() {
        return lssueOrgId;
    }

    public void setLssueOrgId(Integer lssueOrgId) {
        this.lssueOrgId = lssueOrgId;
    }

    public Long getLssuerId() {
        return lssuerId;
    }

    public void setLssuerId(Long lssuerId) {
        this.lssuerId = lssuerId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }

    public Integer getIsAudit() {
        return isAudit;
    }

    public void setIsAudit(Integer isAudit) {
        this.isAudit = isAudit;
    }


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
    public String getDevicenum() {
        return devicenum;
    }

    public void setDevicenum(String devicenum) {
        this.devicenum = devicenum;
    }
}