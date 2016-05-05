package com.esgi.teamst.dailyfeed.models;

import java.util.Date;

/**
 * Classe metier représentant un article de presse
 */
public class Article {

    private String title;
    private String desciption;
    private String content;
    private Date date;

    public Article(){}

    public Article(String title, String desciption, String contenu, Date date) {
        this.title = title;
        this.desciption = desciption;
        this.content = contenu;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesciption() {
        return desciption;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String contenu) {
        this.content = contenu;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
