package com.esgi.teamst.dailyfeed.models;

/**
 * Classe métier représentant l'utilisateur de l'application
 */
public class User {

    private int mId;
    private String mEmail;
    private String password;

    public User() {}

    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
