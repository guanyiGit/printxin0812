package org.javatribe.calculator.module;


import java.io.Serializable;

public class ImmuneCard extends TImmuneCard implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long orgId;//机构id
    private String orgName;//机构名称

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

    @Override
    public String toString() {
        return "ImmuneCard{" +
                "orgId=" + orgId +
                ", orgName='" + orgName + '\'' +
                '}';
    }
}
