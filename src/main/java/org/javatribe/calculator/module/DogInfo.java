package org.javatribe.calculator.module;


import java.io.Serializable;

public class DogInfo extends TDogInfo implements Serializable {
    private Integer mark;//禁養表id:是否禁养如果不為空就是禁養品種
    private String breed;//品种名称
    private String color;//毛色
    private String zUrl;
    private Long zImageId;
    private String cUrl;
    private Long cImageId;
    private String wUrl;
    private Long wImageId;
    private String wUrltwo;
    private Long wImageIdtwo;
    /**
     * 注销备注
     */
    private String cancellationNote;

    public String getzUrl() {
        return zUrl;
    }

    public void setzUrl(String zUrl) {
        this.zUrl = zUrl;
    }

    public Long getzImageId() {
        return zImageId;
    }

    public void setzImageId(Long zImageId) {
        this.zImageId = zImageId;
    }

    public String getcUrl() {
        return cUrl;
    }

    public void setcUrl(String cUrl) {
        this.cUrl = cUrl;
    }

    public Long getcImageId() {
        return cImageId;
    }

    public void setcImageId(Long cImageId) {
        this.cImageId = cImageId;
    }

    public String getwUrl() {
        return wUrl;
    }

    public void setwUrl(String wUrl) {
        this.wUrl = wUrl;
    }

    public Long getwImageId() {
        return wImageId;
    }

    public void setwImageId(Long wImageId) {
        this.wImageId = wImageId;
    }

    public String getwUrltwo() {
        return wUrltwo;
    }

    public void setwUrltwo(String wUrltwo) {
        this.wUrltwo = wUrltwo;
    }

    public Long getwImageIdtwo() {
        return wImageIdtwo;
    }

    public void setwImageIdtwo(Long wImageIdtwo) {
        this.wImageIdtwo = wImageIdtwo;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "DogInfo{" +
                "mark=" + mark +
                ", breed='" + breed + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public String getCancellationNote() {
        return cancellationNote;
    }

    public void setCancellationNote(String cancellationNote) {
        this.cancellationNote = cancellationNote;
    }
}
