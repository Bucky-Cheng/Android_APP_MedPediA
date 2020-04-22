package com.example.group_project_0_1.Model;

public class chatUser {

    private String username;
    private String id;
    private String status;

    public chatUser(){

    }
    public  chatUser(String id, String username, String status){
        this.id=id;
        this.username=username;
        this.status=status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
