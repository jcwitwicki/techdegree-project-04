package com.teamtreehouse.blog.model;

import java.util.Date;

public class Comment {

    private String name;
    private String comment;
    private String date;

    public Comment(String name, String comment) {
        this.name = name;
        this.comment = comment;
        date = String.format("%tA %<tB %<te, %<tY %<tr", new Date());
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
} 
