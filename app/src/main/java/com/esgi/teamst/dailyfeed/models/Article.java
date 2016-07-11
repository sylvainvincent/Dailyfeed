package com.esgi.teamst.dailyfeed.models;

/**
 * Classe metier représentant un article de presse
 */
public class Article {

    private int mId;
    private String mTitle;
    private String mContent;
    private String mArticleUrl;
    private String mThumbnailLink;
    private String mPublishedDate;
    private boolean mFavorite;
    private int mSourceId;

    public Article() {}

    public Article(int id, String title, String content, String articleLink, String thumbnailLink, String publishedDate, boolean favorite, int sourceId) {
        mId = id;
        mTitle = title;
        mContent = content;
        mArticleUrl = articleLink;
        mThumbnailLink = thumbnailLink;
        mPublishedDate = publishedDate;
        mFavorite = favorite;
        mSourceId = sourceId;
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

    public String getArticleUrl() {
        return mArticleUrl;
    }

    public void setArticleUrl(String articleLink) {
        this.mArticleUrl = articleLink;
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
                ", mArticleUrl='" + mArticleUrl + '\'' +
                ", mThumbnailLink='" + mThumbnailLink + '\'' +
                ", mPublishedDate='" + mPublishedDate + '\'' +
                ", mFavorite=" + mFavorite +
                ", mSourceId=" + mSourceId +
                '}';
    }
}
