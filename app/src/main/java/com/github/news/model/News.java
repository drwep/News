package com.github.news.model;

import java.io.Serializable;

/**
 * author： xuyafan
 * description:
 */
public class News implements Serializable{

    private String img; //图片
    private String title; //标题
    private String intro; //简介
    private String date; //时间
    private String author;//作者
    private String detailString;//详细内容
    private String type;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDetailString() {
        return detailString;
    }

    public void setDetailString(String detailString) {
        this.detailString = detailString;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
