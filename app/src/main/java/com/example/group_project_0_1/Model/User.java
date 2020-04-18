package com.example.group_project_0_1.Model;

public class User {

    private String UserID;
    private String Emial;
    private String UserName;
    private String DrID;
    private Boolean DrFlag;

    public User(String UserID, String Email, String UserName, String DrID, Boolean DrFlag){
        this.UserID=UserID;
        this.Emial=Email;
        this.UserName=UserName;
        this.DrID=DrID;
        this.DrFlag=DrFlag;
    }
}
