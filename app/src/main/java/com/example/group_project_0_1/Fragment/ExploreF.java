package com.example.group_project_0_1.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.group_project_0_1.R;
import com.github.mikephil.charting.charts.LineChart;


public class ExploreF extends Fragment {




    LineChart lineChart;







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_see_dr,container,false);

        lineChart=view.findViewById(R.id.chart1);



        return view;


    }

    




}
