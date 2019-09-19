package org.javatribe.calculator.module;


import java.io.Serializable;

//病历对象
public class Therapy extends TTherapy implements Serializable {
    private String orgName;//机构或医院名称

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
}
