package com.esgi.teamst.dailyfeed.models;

/**
 * Classe metier représentant un article de presse
 */
public class Article {

    private String mTitle;
    private String mContent;
    private String mSource;

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

    public String getmSource() {
        return mSource;
    }

    public void setmSource(String mSource) {
        this.mSource = mSource;
    }
}
