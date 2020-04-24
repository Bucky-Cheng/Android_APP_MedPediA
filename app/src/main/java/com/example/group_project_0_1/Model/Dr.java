package com.example.group_project_0_1.Model;

public class Dr {

    private String uid;
    private String DrName;
    private String DrID;
    private String Pro;

    public Dr() {
    }

    public Dr(String uid, String drName, String drID, String pro) {
        this.uid = uid;
        this.DrName = drName;
        this.DrID = drID;
        this.Pro = pro;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDrName() {
        return DrName;
    }

    public void setDrName(String drName) {
        DrName = drName;
    }

    public String getDrID() {
        return DrID;
    }

    public void setDrID(String drID) {
        DrID = drID;
    }

    public String getPro() {
        return Pro;
    }

    public void setPro(String pro) {
        Pro = pro;
    }
}
