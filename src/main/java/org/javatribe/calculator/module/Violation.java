package org.javatribe.calculator.module;


import java.io.Serializable;

//违法信息对象
public class Violation extends TViolation implements Serializable {
    private String violation;//违规类型描述
    private Integer punishType;//处罚方式
    private Double fine;//罚款或扣分值
    private Integer deductionSum;//累计扣分
    private String operatorName;//执法人员姓名
    private String remark;//备注

    public String getViolation() {
        return violation;
    }

    public void setViolation(String violation) {
        this.violation = violation;
    }

    public Integer getPunishType() {
        return punishType;
    }

    public void setPunishType(Integer punishType) {
        this.punishType = punishType;
    }

    public Double getFine() {
        return fine;
    }

    public void setFine(Double fine) {
        this.fine = fine;
    }

    public Integer getDeductionSum() {
        return deductionSum;
    }

    public void setDeductionSum(Integer deductionSum) {
        this.deductionSum = deductionSum;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
