package com.esgi.teamst.dailyfeed.models;

import java.util.Date;

/**
 * Classe metier représentant un article de presse
 */
public class Article {

    private String title;
    private String link;
    private Date date;

    public Article(){}

    public Article(String title, String link, Date date) {
        this.title = title;
        this.link = link;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
