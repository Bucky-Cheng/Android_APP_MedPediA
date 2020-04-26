package com.example.group_project_0_1.Model;

public class seaItem {

    private int id;
    private  String name_zh;
    private  String name_en;
    private  String table;
    private  String t_id;

    public seaItem(int id, String name_zh, String name_en, String table, String t_id) {
        this.id = id;
        this.name_zh = name_zh;
        this.name_en = name_en;
        this.table = table;
        this.t_id = t_id;
    }

    public seaItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName_zh() {
        return name_zh;
    }

    public void setName_zh(String name_zh) {
        this.name_zh = name_zh;
    }

    public String getName_en() {
        return name_en;
    }

    public void setName_en(String name_en) {
        this.name_en = name_en;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
    }
}
