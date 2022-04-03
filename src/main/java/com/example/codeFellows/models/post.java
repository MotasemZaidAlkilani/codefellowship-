package com.example.codeFellows.models;

import javax.persistence.*;

@Entity
public class post {
    @Id
    @GeneratedValue
    int id;
    private String body;
    private String createdAt;

@ManyToOne
@JoinColumn(name="user_id")
ApplicationUser user;

    public void setUser(ApplicationUser user) {
        this.user = user;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public post() {
    }

    public post(String body, String createdAt) {
        this.body = body;
        this.createdAt = createdAt;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
