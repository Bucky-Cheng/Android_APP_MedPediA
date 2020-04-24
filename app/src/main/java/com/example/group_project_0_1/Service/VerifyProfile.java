package com.example.group_project_0_1.Service;

import com.example.group_project_0_1.DAO.SQLDao;
import com.example.group_project_0_1.Model.Dr;
import com.example.group_project_0_1.Model.Illness;

public class VerifyProfile {


    public VerifyProfile(){

    }

    public Boolean VerifyIllness(String uid,String username){
        Illness illness=new Illness(uid,username);
        String SQL= new SQLDao().AddIllness(illness);
        ConnectServer connectServer=new ConnectServer();
        String result=connectServer.connection(SQL);
        if(result.equals("")||result.length()<=0){
            return false;
        }else {
            return true;
        }
    }

    public Boolean VerifyDrID(String DrID,String Pro) {

        String SQL = new SQLDao().VerifyDr(DrID,Pro);
        ConnectServer connectServer = new ConnectServer();
        String result = connectServer.connection(SQL);
        if (result.equals("") || result.length() <= 0) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean VerifyDr(String uid,String username,String DrID, String Pro){
        Dr dr=new Dr(uid, username, DrID, Pro);
        String SQL=new SQLDao().AddDr(dr);
        ConnectServer connectServer=new ConnectServer();
        String result=connectServer.connection(SQL);
        if(result.equals("")||result.length()<=0){
            return false;
        }else {
            return true;
        }
    }

}
