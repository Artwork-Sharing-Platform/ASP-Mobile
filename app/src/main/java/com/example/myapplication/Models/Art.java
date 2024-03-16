package com.example.myapplication.Models;

import android.graphics.Bitmap;

public class Art {
    private String _id;
    private String userId;
    private String categoryId;
    private String type;
    private String access;
    private String url;
    private String title;
    private String description;
    private String link;
    private Boolean status;
    private Boolean isCheckedAds;
    private Boolean isCheckedComment;
    private Boolean isCheckedSimilar;
    private String createdAt;
    private String updatedAt;
    private int __v;

    public Art(String _id, String userId, String categoryId, String type, String access, String url, String title, String description, String link, Boolean status, Boolean isCheckedAds, Boolean isCheckedComment, Boolean isCheckedSimilar, String createdAt, String updatedAt, int __v) {
        this._id = _id;
        this.userId = userId;
        this.categoryId = categoryId;
        this.type = type;
        this.access = access;
        this.url = url;
        this.title = title;
        this.description = description;
        this.link = link;
        this.status = status;
        this.isCheckedAds = isCheckedAds;
        this.isCheckedComment = isCheckedComment;
        this.isCheckedSimilar = isCheckedSimilar;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.__v = __v;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getCheckedAds() {
        return isCheckedAds;
    }

    public void setCheckedAds(Boolean checkedAds) {
        isCheckedAds = checkedAds;
    }

    public Boolean getCheckedComment() {
        return isCheckedComment;
    }

    public void setCheckedComment(Boolean checkedComment) {
        isCheckedComment = checkedComment;
    }

    public Boolean getCheckedSimilar() {
        return isCheckedSimilar;
    }

    public void setCheckedSimilar(Boolean checkedSimilar) {
        isCheckedSimilar = checkedSimilar;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
