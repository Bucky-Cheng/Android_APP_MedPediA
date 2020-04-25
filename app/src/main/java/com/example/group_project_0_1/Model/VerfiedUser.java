package com.example.group_project_0_1.Model;

public class VerfiedUser {

    private String uid;
    private String Verified;

    public VerfiedUser(String uid, String verified) {
        this.uid = uid;
        Verified = verified;
    }

    public VerfiedUser() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVerified() {
        return Verified;
    }

    public void setVerified(String verified) {
        Verified = verified;
    }
}
