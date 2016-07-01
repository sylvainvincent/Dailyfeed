package com.esgi.teamst.dailyfeed.models;

/**
 * Classe métier représentant la source d'où provient l'article
 */
public class Source {

    private int mId;
    private String mName;
    private String mUrl;

    public Source() {}

    public int getmId() {
        return mId;
    }

    public void setmId(int id) {
        mId = id;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String name) {
        mName = name;
    }

    public String getmUrl() { return mUrl; }

    public void setmUrl(String mUrl) { this.mUrl = mUrl; }

}
