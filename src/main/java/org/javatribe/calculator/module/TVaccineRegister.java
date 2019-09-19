package org.javatribe.calculator.module;

import java.util.Date;

public class TVaccineRegister {
    private Long vaccineRegisterId;

    private Long dogId;

    private Integer orgId;

    private Integer injectionType;

    private Long refId;

    private String vaccineName;

    private String vaccineProducer;

    private String vaccineNum;

    private Integer vaccineCount;

    private Integer injectionIntervalDays;

    private Date firstInjectionDate;

    private Integer schedule;

    private Date creationTime;

    public Long getVaccineRegisterId() {
        return vaccineRegisterId;
    }

    public void setVaccineRegisterId(Long vaccineRegisterId) {
        this.vaccineRegisterId = vaccineRegisterId;
    }

    public Long getDogId() {
        return dogId;
    }

    public void setDogId(Long dogId) {
        this.dogId = dogId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getInjectionType() {
        return injectionType;
    }

    public void setInjectionType(Integer injectionType) {
        this.injectionType = injectionType;
    }

    public Long getRefId() {
        return refId;
    }

    public void setRefId(Long refId) {
        this.refId = refId;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName == null ? null : vaccineName.trim();
    }

    public String getVaccineProducer() {
        return vaccineProducer;
    }

    public void setVaccineProducer(String vaccineProducer) {
        this.vaccineProducer = vaccineProducer == null ? null : vaccineProducer.trim();
    }

    public String getVaccineNum() {
        return vaccineNum;
    }

    public void setVaccineNum(String vaccineNum) {
        this.vaccineNum = vaccineNum == null ? null : vaccineNum.trim();
    }

    public Integer getVaccineCount() {
        return vaccineCount;
    }

    public void setVaccineCount(Integer vaccineCount) {
        this.vaccineCount = vaccineCount;
    }

    public Integer getInjectionIntervalDays() {
        return injectionIntervalDays;
    }

    public void setInjectionIntervalDays(Integer injectionIntervalDays) {
        this.injectionIntervalDays = injectionIntervalDays;
    }

    public Date getFirstInjectionDate() {
        return firstInjectionDate;
    }

    public void setFirstInjectionDate(Date firstInjectionDate) {
        this.firstInjectionDate = firstInjectionDate;
    }

    public Integer getSchedule() {
        return schedule;
    }

    public void setSchedule(Integer schedule) {
        this.schedule = schedule;
    }

    public Date getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Date creationTime) {
        this.creationTime = creationTime;
    }
}