package com.everlesslycoding.hackindr.Models;

/**
 * Created by emilyperegrine on 19/12/2015.
 */
public class Idea {
    String title;
    String content;
    int votes;
    User user;

    public Idea(String title, String content, int votes, User user) {

        this.title = title;
        this.content = content;
        this.votes = votes;
        this.user = user;
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

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
