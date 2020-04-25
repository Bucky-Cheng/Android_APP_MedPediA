package com.example.group_project_0_1.Service;

import com.example.group_project_0_1.DAO.SQLDao;
import com.example.group_project_0_1.Model.Dr;
import com.example.group_project_0_1.Model.Illness;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;

public class VerifyProfile {

    private int drFlag;

    public VerifyProfile(){

    }

    public Boolean VerifyIllness(String uid,String username) throws BrokenBarrierException, InterruptedException {
        Illness illness=new Illness(uid,username);
        String SQL= new SQLDao().AddIllness(illness);
        ConnectServer connectServer=new ConnectServer();
        ArrayList addList=connectServer.connection(SQL);
        if(addList.size()<=0){
            return false;
        }else {
            return true;
        }
    }

    public Boolean VerifyDrID(String DrID,String Pro) throws BrokenBarrierException, InterruptedException {

        Boolean addF=true;

        String SQL = new SQLDao().VerifyDr(DrID,Pro);
        //System.out.println(SQL);
        ConnectServer connectServer = new ConnectServer();
        ArrayList<JSONObject> jsonList= connectServer.connection(SQL);
        //System.out.println(jsonList);
        if (jsonList.size() <= 0) {
            return false;
        } else {
            int flag=0;
            for(int i=0;i<jsonList.size();i++){
                try {
                    flag=Integer.parseInt(jsonList.get(i).getString("flag"));
                    drFlag=Integer.parseInt(jsonList.get(i).getString("id"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(flag==1){
                    addF=false;
                    break;
                }
            }
            if(addF){
                return true;
            }else{
                return false;
            }

        }
    }

    public Boolean VerifyDr(String uid,String username,String DrID, String Pro) throws BrokenBarrierException, InterruptedException {
        Dr dr=new Dr(uid, username, DrID, Pro);
        String SQL=new SQLDao().AddDr(dr);
        ConnectServer connectServer=new ConnectServer();
        ArrayList addList=connectServer.connection(SQL);
        if(addList.size()<=0){
            return false;
        }else {
            VerifyDrID(DrID,Pro);
            System.out.println("DRFLAG:"+drFlag);
            String SQLU=new SQLDao().upDateDrId(drFlag);
            ConnectServer connectServer1=new ConnectServer();
            ArrayList addList1=connectServer1.connection(SQLU);
            return true;
        }
    }

    public Boolean Verified (String uid) throws BrokenBarrierException, InterruptedException {

        String SQLill=new SQLDao().VerifiedIllness(uid);
        String SQLdr=new SQLDao().VerifiedDr(uid);

        ConnectServer connectServer = new ConnectServer();
        ArrayList<JSONObject> jsonListill= connectServer.connection(SQLill);
        ArrayList<JSONObject> jsonListdr= connectServer.connection(SQLdr);

        if(jsonListill.size()<=0 && jsonListdr.size()<=0){
            return false;
        }else{
            return true;
        }

    }

}
