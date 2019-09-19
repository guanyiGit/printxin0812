package org.javatribe.calculator.module;


import java.io.Serializable;
import java.util.List;

//犬主类
public class DogOwners extends TDogOwner implements Serializable {
    private Long dogOwnerCardId;//证件id
    private Integer cardType;//证件类型
    private String cardNum;//证件编号比如身份证号
    private String districtName;//现住县区名称
    private String thumbnailUrl;//缩略图url
    private String originalUrl;//原图url
    private Integer blackId;//黑名单id
    private Integer blackStatus;//黑名单状态
    private String joinCause;//加入黑名单原因
    private Integer dogNum;//犬只数
    private String ethnic;//名族
    private String scUrl;//手持身份证半身照原图
    private String zUrl;//身份证正面照
    private String fUrl;//身份证反面照
    private Long zImageId;
    private Long fImageId;
    List<Photos> ownerUrlLsit;//狗主照片url集合

    public Long getzImageId() {
        return zImageId;
    }

    public void setzImageId(Long zImageId) {
        this.zImageId = zImageId;
    }

    public Long getfImageId() {
        return fImageId;
    }

    public void setfImageId(Long fImageId) {
        this.fImageId = fImageId;
    }

    public Long getDogOwnerCardId() {
        return dogOwnerCardId;
    }

    public void setDogOwnerCardId(Long dogOwnerCardId) {
        this.dogOwnerCardId = dogOwnerCardId;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public Integer getBlackId() {
        return blackId;
    }

    public void setBlackId(Integer blackId) {
        this.blackId = blackId;
    }

    public Integer getBlackStatus() {
        return blackStatus;
    }

    public void setBlackStatus(Integer blackStatus) {
        this.blackStatus = blackStatus;
    }

    public String getJoinCause() {
        return joinCause;
    }

    public void setJoinCause(String joinCause) {
        this.joinCause = joinCause;
    }

    public Integer getDogNum() {
        return dogNum;
    }

    public void setDogNum(Integer dogNum) {
        this.dogNum = dogNum;
    }

    public String getEthnic() {
        return ethnic;
    }

    public void setEthnic(String ethnic) {
        this.ethnic = ethnic;
    }

    public List<Photos> getOwnerUrlLsit() {
        return ownerUrlLsit;
    }

    public void setOwnerUrlLsit(List<Photos> ownerUrlLsit) {
        this.ownerUrlLsit = ownerUrlLsit;
    }

    public String getScUrl() {
        return scUrl;
    }

    public void setScUrl(String scUrl) {
        this.scUrl = scUrl;
    }

    public String getzUrl() {
        return zUrl;
    }

    public void setzUrl(String zUrl) {
        this.zUrl = zUrl;
    }

    public String getfUrl() {
        return fUrl;
    }

    public void setfUrl(String fUrl) {
        this.fUrl = fUrl;
    }

    @Override
    public String toString() {
        return "DogOwners{" +
                "dogOwnerCardId=" + dogOwnerCardId +
                ", cardType=" + cardType +
                ", cardNum='" + cardNum + '\'' +
                ", districtName='" + districtName + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", originalUrl='" + originalUrl + '\'' +
                ", blackId=" + blackId +
                ", blackStatus=" + blackStatus +
                ", joinCause='" + joinCause + '\'' +
                ", dogNum=" + dogNum +
                ", ethnic='" + ethnic + '\'' +
                ", scUrl='" + scUrl + '\'' +
                ", zUrl='" + zUrl + '\'' +
                ", fUrl='" + fUrl + '\'' +
                ", ownerUrlLsit=" + ownerUrlLsit +
                '}';
    }
}
