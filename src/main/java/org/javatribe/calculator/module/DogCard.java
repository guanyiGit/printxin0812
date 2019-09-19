package org.javatribe.calculator.module;


import java.io.Serializable;

public class DogCard extends TDogCard implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orgId;//机构id
    private String orgName;//机构名称
    private String reason;//犬证审核不通过原因

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String toString() {
        return "DogCard{" +
                "orgId=" + orgId +
                ", orgName='" + orgName + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
