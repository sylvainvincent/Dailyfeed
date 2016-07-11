package com.esgi.teamst.dailyfeed.models;

/**
 * Classe metier représentant un article de presse
 */
public class Article {

    private int mId;
    private String mTitle;
    private String mContent;
    private String mArticleLink;
    private String mThumbnailLink;
    private String mPublishedDate;
    private boolean mFavorite;
    private int mSourceId;

    public Article() {}

    public Article(int id, String title, String content, String articleLink, String thumbnailLink, String publishedDate, boolean favorite, int sourceId) {
        this.mId = id;
        this.mTitle = title;
        this.mContent = content;
        this.mArticleLink = articleLink;
        this.mThumbnailLink = thumbnailLink;
        this.mPublishedDate = publishedDate;
        this.mFavorite = favorite;
        this.mSourceId = sourceId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public String getArticleLink() {
        return mArticleLink;
    }

    public void setArticleLink(String articleLink) {
        this.mArticleLink = articleLink;
    }

    public String getThumbnailLink() {
        return mThumbnailLink;
    }

    public void setThumbnailLink(String thumbnailLink) {
        this.mThumbnailLink = thumbnailLink;
    }

    public String getPublishedDate() {
        return mPublishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.mPublishedDate = publishedDate;
    }

    public boolean isFavorite() {
        return mFavorite;
    }

    public void setFavorite(boolean favorite) {
        this.mFavorite = favorite;
    }

    public int getSourceId() {
        return mSourceId;
    }

    public void setSourceId(int sourceId) {
        this.mSourceId = sourceId;
    }

    @Override
    public String toString() {
        return "Article{" +
                "mId=" + mId +
                ", mTitle='" + mTitle + '\'' +
                ", mContent='" + mContent + '\'' +
                ", mArticleLink='" + mArticleLink + '\'' +
                ", mThumbnailLink='" + mThumbnailLink + '\'' +
                ", mPublishedDate='" + mPublishedDate + '\'' +
                ", mFavorite=" + mFavorite +
                ", mSourceId=" + mSourceId +
                '}';
    }
}
