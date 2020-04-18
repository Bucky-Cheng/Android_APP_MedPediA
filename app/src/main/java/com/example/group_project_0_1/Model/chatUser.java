package com.example.group_project_0_1.Model;

public class chatUser {

    private String username;
    private String id;

    public chatUser(){

    }
    public  chatUser(String id, String username){
        this.id=id;
        this.username=username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
