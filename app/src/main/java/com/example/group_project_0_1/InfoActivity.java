package com.example.group_project_0_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.group_project_0_1.DAO.SQLDao;
import com.example.group_project_0_1.Service.ConnectServer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.BrokenBarrierException;

public class InfoActivity extends AppCompatActivity {




    Intent intent;

    String table;
    String t_id;

    LinearLayout firstLayout;
    LinearLayout secondLayout;
    LinearLayout thirdLayout;
    LinearLayout fourthLayout;

    TextView first;
    TextView first_content;
    TextView second;
    TextView second_content;
    TextView third;
    TextView third_content;
    TextView fourth;
    TextView fourth_content;

    Button first_button;
    Button second_button;
    Button third_button;
    Button fourth_button;

    TextToSpeech textToSpeech;


    ArrayList<JSONObject> jsonObjectArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        intent=getIntent();
        table=intent.getStringExtra("table");
        t_id=intent.getStringExtra("t_id");


        firstLayout=findViewById(R.id.first_layout);
        secondLayout=findViewById(R.id.second_layout);
        thirdLayout=findViewById(R.id.third_layout);
        fourthLayout=findViewById(R.id.fourth_layout);

        firstLayout.setVisibility(View.GONE);
        secondLayout.setVisibility(View.GONE);
        thirdLayout.setVisibility(View.GONE);
        fourthLayout.setVisibility(View.GONE);

        first=findViewById(R.id.first);
        first_content=findViewById(R.id.first_content);
        first_button=findViewById(R.id.first_button);

        second=findViewById(R.id.second);
        second_content=findViewById(R.id.second_content);
        second_button=findViewById(R.id.second_button);

        third=findViewById(R.id.third);
        third_content=findViewById(R.id.third_content);
        third_button=findViewById(R.id.third_button);

        fourth=findViewById(R.id.fourth);
        fourth_content=findViewById(R.id.fourth_content);
        fourth_button=findViewById(R.id.fourth_button);

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status!=TextToSpeech.ERROR){
                    textToSpeech.setLanguage(new Locale("yue","HK"));
                }
            }
        });


        jsonObjectArrayList=new ArrayList<>();

        String SQL=new SQLDao().findContent(table,t_id);
        try {
            jsonObjectArrayList=new ConnectServer().connection(SQL);
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        if(table.equals("drug")){

            try {
                readDrug(jsonObjectArrayList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else if(table.equals("disease")){

            try {
                readDisease(jsonObjectArrayList);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }





    }

    private void readDrug(ArrayList<JSONObject> jsonObjectArrayList) throws JSONException {

        firstLayout.setVisibility(View.VISIBLE);
        secondLayout.setVisibility(View.VISIBLE);
        thirdLayout.setVisibility(View.VISIBLE);

        JSONObject object=jsonObjectArrayList.get(0);


        first.setText("總覽");
        String Overview=object.getString("overview");
        first_content.setText(Overview);
        second.setText("用途");
        String use=object.getString("use");
        second_content.setText(use);
        third.setText("不良反應");
        String Reactions=object.getString("reactions");
        third_content.setText(Reactions);

        first_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(Overview);
            }
        });
        second_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(use);
            }
        });
        third_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(Reactions);
            }
        });






    }


    private void readDisease(ArrayList<JSONObject> jsonObjectArrayList) throws JSONException {

        firstLayout.setVisibility(View.VISIBLE);
        secondLayout.setVisibility(View.VISIBLE);
        thirdLayout.setVisibility(View.VISIBLE);
        fourthLayout.setVisibility(View.VISIBLE);

        JSONObject object=jsonObjectArrayList.get(0);


        first.setText("病徵");
        String Symptom=object.getString("symptom");
        first_content.setText(Symptom);
        second.setText("診斷");
        String Diagnosis=object.getString("diagnosis");
        second_content.setText(Diagnosis);
        third.setText("治療");
        String Treatment=object.getString("treatment");
        third_content.setText(Treatment);
        fourth.setText("預防");
        String Prevention=object.getString("prevention");
        third_content.setText(Prevention);

        first_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(Symptom);
            }
        });
        second_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(Diagnosis);
            }
        });
        third_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(Treatment);
            }
        });
        fourth_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speak(Prevention);
            }
        });




    }

    private void speak(String content){
        textToSpeech.speak(content,TextToSpeech.QUEUE_FLUSH,null);
    }
}
