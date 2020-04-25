package com.example.group_project_0_1.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group_project_0_1.R;
import com.example.group_project_0_1.Service.GetAPI;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ExploreF extends Fragment {




    LineChart lineChart;
    BarChart barChart;
    PieChart pieChart;








    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_explore,container,false);

        lineChart=view.findViewById(R.id.chart1);
        barChart=view.findViewById(R.id.chart2);
        pieChart=view.findViewById(R.id.chart3);


        mytask my=new mytask();
        my.execute();







        return view;


    }

    private void xtext(ArrayList<String> xList){

        XAxis xAxis=lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xList));
    }
    private void ytext(){
        YAxis yAxisR=lineChart.getAxisRight();
        yAxisR.setEnabled(false);
    }

    private void setLine(ArrayList<Entry> arrayList) {
        System.out.println(arrayList);
        LineDataSet lineDataSet = new LineDataSet(arrayList, "資料來源：data.gov.hk");
        lineDataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        lineDataSet.setColor(Color.RED);
        lineDataSet.setLineWidth(2);
        lineDataSet.setCircleRadius(4);
        lineDataSet.enableDashedLine(5,5,0);
        lineDataSet.setHighlightLineWidth(2);
        lineDataSet.setHighlightEnabled(true);
        lineDataSet.setHighLightColor(Color.GREEN);
        lineDataSet.setValueTextSize(15);
        lineDataSet.setDrawFilled(false);


        LineData lineData=new LineData(lineDataSet);

        lineChart.setData(lineData);
        lineChart.invalidate();

    }

    private void setBar(ArrayList<BarEntry> arrayListB,ArrayList<String> xList){
        System.out.println(arrayListB);
        BarDataSet barDataSet=new BarDataSet(arrayListB,"資料來源：data.gov.hk");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(R.color.black);
        barDataSet.setValueTextSize(15);



        BarData barData=new BarData(barDataSet);

        barChart.setData(barData);

        barChart.invalidate();

        XAxis xAxis=barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xList));
        xAxis.setLabelCount(18);

        YAxis yAxisR=barChart.getAxisRight();
        yAxisR.setEnabled(false);


    }

    private void setPie(ArrayList<PieEntry> arrayListP){

        PieDataSet pieDataSet=new PieDataSet(arrayListP,"資料來源：data.gov.hk");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextColor(R.color.grey_100);
        pieDataSet.setValueTextSize(16f);

        PieData pieData=new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }


    private class mytask extends AsyncTask<String, Void, String> {

        @SuppressLint("WrongThread")

        JSONArray jsonArrayO=new JSONArray();
        JSONArray jsonArrayB=new JSONArray();
        JSONArray jsonArrayC=new JSONArray();
        private static final int OVERVIEW=200;
        private static final int BUILDING=300;
        private static final int COUNTRY=400;



        @Override
        protected String doInBackground(String... strings) {
            jsonArrayO=new GetAPI().getjson(OVERVIEW);
            jsonArrayB=new GetAPI().getjson(BUILDING);
            jsonArrayC=new GetAPI().getjson(COUNTRY);
            return "S";
        }

        @Override
        protected void onPostExecute(String msg) {

            super.onPostExecute(msg);



            System.out.println(jsonArrayO.length());


            //Line chart
            ArrayList<Entry> arrayList=new ArrayList<>();
            ArrayList<String> xList=new ArrayList<>();
            ArrayList<String> xAListB=new ArrayList<>();

            for(int i=jsonArrayO.length()-15;i<jsonArrayO.length();i++){
                JSONObject jsonObject=null;
                JSONObject lastObject=null;
                int num=0;

                try {
                   jsonObject = (JSONObject) jsonArrayO.get(i);
                   lastObject=(JSONObject) jsonArrayO.get(i-1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    xList.add(jsonObject.getString("更新日期"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    num=jsonObject.getInt("確診個案")-lastObject.getInt("確診個案");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                arrayList.add(new Entry(i,num));

            }
            setLine(arrayList);

            xtext(xList);
            ytext();

            //Bar chart

            ArrayList<BarEntry> arrayListB=new ArrayList<>();
            String[] xListB={"中西區","灣仔","東區","南區","深水埗","油尖旺","九龍城","黃大仙","觀塘","葵青"
            ,"荃灣","屯門","元朗","北區","大埔","沙田","西貢","離島"};
            int[] num={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            for(int i=0;i<jsonArrayB.length();i++){
                JSONObject object=null;
                String area="";

                try {
                    object= (JSONObject) jsonArrayB.get(i);
                    area=object.getString("地區");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for(int j=0;j<18;j++){
                    if(area.equals(xListB[j])){
                        num[j]++;
                    }
                }


            }

            for(int n=0;n<18;n++){
                xAListB.add(xListB[n]);
                arrayListB.add(new BarEntry(n,num[n]));
            }

            setBar(arrayListB,xAListB);


            //Pie chart

            ArrayList<PieEntry> arrayListP=new ArrayList<>();
            for(int i=0;i<9;i++){
                JSONObject object=null;
                String country="";
                int numC=0;

                try {
                    object= (JSONObject) jsonArrayC.get(i);
                    country=object.getString("國家/地區");
                    numC=object.getInt("個案數字");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(country.equals("中國內地")){
                    country="中國";
                }
                arrayListP.add(new PieEntry(numC,country));


            }

            setPie(arrayListP);

        }




    }





}
