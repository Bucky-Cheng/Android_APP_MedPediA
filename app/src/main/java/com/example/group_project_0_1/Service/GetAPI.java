package com.example.group_project_0_1.Service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetAPI {


    String API = "https://api.data.gov.hk/v2/filter?q=%7B%22resource%22%3A%22http%3A%2F%2Fwww.chp.gov.hk%2Ffiles%2Fmisc%2Flist_of_collection_points_chi.csv%22%2C%22section%22%3A1%2C%22format%22%3A%22json%22%7D";
    String APIALLCASE = "https://api.data.gov.hk/v2/filter?q=%7B%22resource%22%3A%22http%3A%2F%2Fwww.chp.gov.hk%2Ffiles%2Fmisc%2Fenhanced_sur_covid_19_chi.csv%22%2C%22section%22%3A1%2C%22format%22%3A%22json%22%7D";
    private static final String OVERVIEW="https://api.data.gov.hk/v2/filter?q=%7B%22resource%22%3A%22http%3A%2F%2Fwww.chp.gov.hk%2Ffiles%2Fmisc%2Flatest_situation_of_reported_cases_covid_19_chi.csv%22%2C%22section%22%3A1%2C%22format%22%3A%22json%22%7D";
    private static final String BUILDING="https://api.data.gov.hk/v2/filter?q=%7B%22resource%22%3A%22http%3A%2F%2Fwww.chp.gov.hk%2Ffiles%2Fmisc%2Fbuilding_list_chi.csv%22%2C%22section%22%3A1%2C%22format%22%3A%22json%22%7D";
    private static final String COUNTRY="https://api.data.gov.hk/v2/filter?q=%7B%22resource%22%3A%22http%3A%2F%2Fwww.chp.gov.hk%2Ffiles%2Fmisc%2Fcountries_areas_visited_by_cases_with_travel_history_chi.csv%22%2C%22section%22%3A1%2C%22format%22%3A%22json%22%7D";
    public GetAPI() {
    }

    public JSONArray getjson(int flag) {
        String APIsource="";
        if(flag==200){
            APIsource=OVERVIEW;
        }else if(flag==300){
            APIsource=BUILDING;
        }else if(flag==400){
            APIsource=COUNTRY;
        }
        JSONArray jsonArray=new JSONArray();

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        Request request = new Request.Builder()
                .url(APIsource)
                .method("GET", null)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }

        String response_s = "";
        try {
            response_s = response.body().string();
            //System.out.println(response_s);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        String response_sa = response_s.substring(1, response_s.length() - 1);
        jsonArray = null;
        try {
            jsonArray = new JSONArray(response_s);
        } catch (
                JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonArray.get(2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonArray;

    }

}
