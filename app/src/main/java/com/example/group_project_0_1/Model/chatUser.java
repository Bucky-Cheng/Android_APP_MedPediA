package com.example.group_project_0_1.Model;

public class chatUser {

    private String username;
    private String id;
    private String status;
    private String search;
    private String imageUri;

    public chatUser(){

    }
    public  chatUser(String id, String username, String status, String search,String imageUri){
        this.id=id;
        this.username=username;
        this.status=status;
        this.search=search;
        this.imageUri=imageUri;
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

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
