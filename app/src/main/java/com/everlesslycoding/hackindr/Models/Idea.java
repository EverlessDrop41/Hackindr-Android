package com.everlesslycoding.hackindr.Models;

/**
 * Created by emilyperegrine on 19/12/2015.
 */
public class Idea {
    String title;
    String content;
    int id;

    public Idea(String title, String content, int votes) {

        this.title = title;
        this.content = content;
        this.id = votes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
