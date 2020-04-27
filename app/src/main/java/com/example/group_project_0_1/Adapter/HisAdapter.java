package com.example.group_project_0_1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group_project_0_1.InfoActivity;
import com.example.group_project_0_1.MessageActivity;
import com.example.group_project_0_1.Model.Chat;
import com.example.group_project_0_1.Model.History;
import com.example.group_project_0_1.Model.chatUser;
import com.example.group_project_0_1.R;


import java.util.List;

public class HisAdapter extends RecyclerView.Adapter<HisAdapter.ViewHolder>{


        private Context mContext;
        private List<History> mHis;


    public HisAdapter(Context context, List< History > mHis){
        this.mContext=context;
        this.mHis=mHis;

    }

        @NonNull
        @Override
        public HisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.hisitem,parent,false);
        return new HisAdapter.ViewHolder(view);
    }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final History his=mHis.get(position);
        holder.hisname.setText(his.getHistoryName());
        holder.time.setText(his.getTime());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, InfoActivity.class);
                intent.putExtra("table",his.getTable());
                intent.putExtra("t_id",his.getTab_id());
                mContext.startActivity(intent);
            }
        });




    }

        @Override
        public int getItemCount() {
        return mHis.size();
    }


        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView hisname;
            public TextView time;

            public ViewHolder(View itemView){
                super(itemView);

                hisname=itemView.findViewById(R.id.itemname);
                time=itemView.findViewById(R.id.time);

            }
        }




}
