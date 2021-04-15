package com.example.automobilesnepal.models;

import java.io.Serializable;

public class NewsModel implements Serializable {
    private String news_image, news_title, news_content, published_date;

    public String getNews_image() {
        return news_image;
    }

    public void setNews_image(String news_image) {
        this.news_image = news_image;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public String getPublished_date() {
        return published_date;
    }

    public void setPublished_date(String published_date) {
        this.published_date = published_date;
    }

    public NewsModel(String news_image, String news_title, String news_content, String published_date) {
        this.news_image = news_image;
        this.news_title = news_title;
        this.news_content = news_content;
        this.published_date = published_date;
    }
}
