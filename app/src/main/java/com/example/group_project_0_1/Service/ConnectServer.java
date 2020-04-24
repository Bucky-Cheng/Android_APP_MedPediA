package com.example.group_project_0_1.Service;

import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectServer {


    Socket socket;
    String msg="";
    public ConnectServer(){

    }

    public String connection(String SQL){


        new Thread(){
            @Override
            public void run() {
                String message = SQL;
                try {
                    //generate socket
                    socket = new Socket("ec2-54-242-168-118.compute-1.amazonaws.com", 8080);//ip，port
                    //send message to server
                    PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                    out.println(message);
                    //get message from server
                    BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    msg= new String(br.readLine().getBytes(),"UTF-8");

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
                    } catch (Exception e) {

                    }
                }
            }
        }.start();

        return msg;
    }
}
