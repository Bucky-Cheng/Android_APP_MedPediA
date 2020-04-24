package com.example.group_project_0_1.Service;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConnectServer {


    Socket socket;
    ArrayList<JSONObject> jsonList=new ArrayList<>();
    ArrayList addList=new ArrayList();
    Boolean add=true;
    public ConnectServer(){

    }



    public ArrayList<JSONObject> connection(String SQL) throws BrokenBarrierException, InterruptedException {

        final CyclicBarrier barrier=new CyclicBarrier(2);

        new Thread(){
            @Override
            public void run() {
                String message = SQL;
                try {
                    //generate socket
                    socket = new Socket("ec2-3-86-161-118.compute-1.amazonaws.com", 8080);//ip，port
                    //send message to server
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    out.println(message);
                    //get message from server
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String line = null;
                    add=true;
                    while ((line = br.readLine()) != null) {
                        Pattern pattern= Pattern.compile("-?[0-9]+(\\.[0-9]+)?");
                        Matcher matcher=pattern.matcher(line);
                        if(matcher.matches()){
                            add=false;
                            addList.add(line);
                        }else {
                            StringBuilder sb = new StringBuilder();
                            //System.out.println(line);
                            sb.append(line);
                            JSONObject json = new JSONObject(sb.toString());

                            jsonList.add(json);
                        }


                    }

                    //shut down stream
                    out.close();
                    br.close();


                } catch (Exception e) {
                    Log.e("file", e.toString());
                } finally {
                    //shut down Socket
                    try {
                        if (socket != null) {
                            socket.close();
                        }
                        System.out.println("CONNECTION_THREAD");
                        barrier.await();
                    } catch (Exception e) {

                    }


                }


            }
        }.start();

        barrier.await();
        System.out.println("MAIN_THREAD");
        if(add){
            System.out.println(jsonList);
            return jsonList;
        }else{
            return addList;
        }

    }
}
