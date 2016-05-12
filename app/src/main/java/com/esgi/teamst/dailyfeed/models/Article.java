package com.esgi.teamst.dailyfeed.models;

/**
 * Classe metier représentant un article de presse
 */
public class Article {

    private int mId;
    private String mTitle;
    private String mContent;
    private int mSourceId;

    public Article() {}

    public Article(int id, String title, String content, int sourceId) {
        mId = id;
        mTitle = title;
        mContent = content;
        mSourceId = sourceId;
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
}
