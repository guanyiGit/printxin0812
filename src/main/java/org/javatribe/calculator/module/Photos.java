package org.javatribe.calculator.module;

import java.io.Serializable;

//放犬的照片url
public class Photos implements Serializable {
    private static final long serialVersionUID = 1L;

    private String imageUrl;//原图
    private String thumbnailUrl;//缩略图
    private Long picId;//图片id

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public Long getPicId() {
        return picId;
    }

    public void setPicId(Long picId) {
        this.picId = picId;
    }

    @Override
    public String toString() {
        return "Photos{" +
                "imageUrl='" + imageUrl + '\'' +
                ", thumbnailUrl='" + thumbnailUrl + '\'' +
                ", picId=" + picId +
                '}';
    }
}
