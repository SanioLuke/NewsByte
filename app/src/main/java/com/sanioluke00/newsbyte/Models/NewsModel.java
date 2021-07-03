package com.sanioluke00.newsbyte.Models;

import java.io.Serializable;

public class NewsModel implements Serializable {

    String author, title, description, url, urlToImage, publishedAt, content, source_name;

    public NewsModel(String author, String title, String description, String url, String urlToImage, String publishedAt, String content, String source_name) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
        this.source_name = source_name;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getContent() {
        return content;
    }

    public String getSource_name() {
        return source_name;
    }
}
