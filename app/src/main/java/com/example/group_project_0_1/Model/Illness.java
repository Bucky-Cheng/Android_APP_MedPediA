package com.example.group_project_0_1.Model;

public class Illness {

    private String uid;
    private String username;


    public Illness() {
    }

    public Illness(String uid, String username) {
        this.uid = uid;
        this.username = username;

    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}
