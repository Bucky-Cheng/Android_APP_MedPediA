package com.example.group_project_0_1.Model;

public class History {

    private String uid;
    private String historyName;
    private String table;
    private String tab_id;
    private String search;
    private String time;

    public History(String uid, String historyName, String table, String tab_id, String search,String time) {
        this.uid = uid;
        this.historyName = historyName;
        this.table = table;
        this.tab_id = tab_id;
        this.search = search;
        this.time=time;
    }

    public History() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHistoryName() {
        return historyName;
    }

    public void setHistoryName(String historyName) {
        this.historyName = historyName;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getTab_id() {
        return tab_id;
    }

    public void setTab_id(String tab_id) {
        this.tab_id = tab_id;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
