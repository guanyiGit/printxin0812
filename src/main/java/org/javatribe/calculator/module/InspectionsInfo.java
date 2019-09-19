package org.javatribe.calculator.module;


import java.io.Serializable;

public class InspectionsInfo extends TInspectionsInfo implements Serializable {
    private String orgName;//年检单位
    private String checkPeople;//年检人姓名

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCheckPeople() {
        return checkPeople;
    }

    public void setCheckPeople(String checkPeople) {
        this.checkPeople = checkPeople;
    }
}
