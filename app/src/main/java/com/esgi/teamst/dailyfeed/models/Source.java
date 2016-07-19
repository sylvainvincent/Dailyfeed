package com.esgi.teamst.dailyfeed.models;

/**
 * Classe métier représentant la source d'où provient l'article
 */
public class Source {

    private int mId;
    private String mName;
    private String mUrl;
    private boolean mAvailable;

    public Source() {}

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUrl() { return mUrl; }

    public void setUrl(String mUrl) { this.mUrl = mUrl; }

    public boolean isAvailable() {
        return mAvailable;
    }

    public void setAvailable(boolean available) {
        this.mAvailable = available;
    }
}
