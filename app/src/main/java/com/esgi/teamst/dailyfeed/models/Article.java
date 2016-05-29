package com.esgi.teamst.dailyfeed.models;

import java.util.Date;

/**
 * Classe metier représentant un article de presse
 */
public class Article {

    private int mId;
    private String mTitle;
    private String mContent;
    private String mThumbnailLink;
    private Date mPublishedDate;
    private int mSourceId;

    public Article() {}

    public Article(int mId, String mTitle, String mContent, String mThumbnailLink, Date mPublishedDate, int mSourceId) {
        this.mId = mId;
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.mThumbnailLink = mThumbnailLink;
        this.mPublishedDate = mPublishedDate;
        this.mSourceId = mSourceId;
    }

    public int getmId() {
        return mId;
    }
    public void setmId(int mId) {
        this.mId = mId;
    }
    public String getmTitle() {
        return mTitle;
    }
    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }
    public String getmContent() {
        return mContent;
    }
    public void setmContent(String mContent) {
        this.mContent = mContent;
    }
    public int getmSourceId() {
        return mSourceId;
    }
    public void setmSourceId(int sourceId) {
        this.mSourceId = sourceId;
    }
    public String getmThumbnailLink() {
        return mThumbnailLink;
    }
    public void setmThumbnailLink(String mThumbnailLink) {
        this.mThumbnailLink = mThumbnailLink;
    }
    public Date getmPublishedDate() {
        return mPublishedDate;
    }
    public void setmPublishedDate(Date mPublishedDate) {
        this.mPublishedDate = mPublishedDate;
    }
}
