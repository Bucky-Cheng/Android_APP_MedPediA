package com.example.group_project_0_1.DAO;

import com.example.group_project_0_1.Model.Dr;
import com.example.group_project_0_1.Model.Illness;

public class SQLDao {

    public SQLDao() {
    }

    public String AddIllness(Illness illness){

        String SQL="INSERT INTO illness (userid, username) VALUES ('"
                + illness.getUid()+"', '"
                +illness.getUsername()+"');";
        System.out.println(SQL);
        return SQL;
    }

    public String VerifyDr(String Drid,String Pro){
        String SQL="SELECT * FROM DrId WHERE did='"+Drid+"' and dpro='"+Pro+"';";
        return SQL;
    }

    public String AddDr(Dr dr){

        String SQL="INSERT INTO dr (userid, username, drid, drpro) VALUES ('"
                + dr.getUid()+"', '"
                +dr.getDrName()+"', '"
                +dr.getDrID()+"','"
                +dr.getPro()+"');";
        return SQL;
    }

    public String upDateDrId(int id){
        String SQL="UPDATE DrId SET flag = 1 WHERE id = "+id+";";
        return SQL;
    }

    public String VerifiedIllness(String uid){
        String SQL="SELECT * FROM illness WHERE userid='"+uid+"';";
        return SQL;
    }

    public String VerifiedDr(String uid){
        String SQL="SELECT * FROM dr WHERE userid='"+uid+"';";
        return SQL;
    }

    public String findItems(){
        String SQL="SELECT * FROM indexA";
        return SQL;
    }
}
