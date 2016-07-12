package com.esgi.teamst.dailyfeed.models;

import java.util.List;

/**
 * Classe métier représentant l'utilisateur de l'application
 */
public class User {

    private int mId;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mPassword;
    private List<Article> mFavoritesArticlesList;

    public User() {}

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public List<Article> getmFavoritesArticlesList() {
        return mFavoritesArticlesList;
    }

    public void setmFavoritesArticlesList(List<Article> mFavoritesArticlesList) {
        this.mFavoritesArticlesList = mFavoritesArticlesList;
    }

    @Override
    public String toString() {
        return "User{" +
                "mId=" + mId +
                ", mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mPassword='" + mPassword + '\'' +
                ", mFavoritesArticlesList=" + mFavoritesArticlesList +
                '}';
    }
}
