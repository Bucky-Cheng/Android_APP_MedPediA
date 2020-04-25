package com.example.group_project_0_1.Model;

public class DrProfile {

    private String id;
    private String username;
    private String pro;
    private String intro;
    private String search;

    public DrProfile(String id, String username, String pro, String intro, String search) {
        this.id = id;
        this.username = username;
        this.pro = pro;
        this.intro = intro;
        this.search = search;
    }

    public DrProfile() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
