package com.example.group_project_0_1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.group_project_0_1.InfoActivity;
import com.example.group_project_0_1.MessageActivity;

import com.example.group_project_0_1.Model.seaItem;
import com.example.group_project_0_1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class SeaAdapter extends RecyclerView.Adapter<SeaAdapter.ViewHolder>  {




        private Context mContext;
        private List<seaItem> mItems;



    public SeaAdapter(Context context, List<seaItem> mItems){
        this.mContext=context;
        this.mItems=mItems;

        }

        @NonNull
        @Override
        public SeaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.search_item,parent,false);
        return new SeaAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final seaItem item=mItems.get(position);
        holder.itemrname.setText(item.getName_en()+item.getName_zh());



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
                if(firebaseUser!=null) {
                    String seaContent="";
                    if(item.getName_zh().equals("")){
                        seaContent=item.getName_en();
                    }else{
                        seaContent=item.getName_zh();
                    }
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("uid", firebaseUser.getUid());
                    hashMap.put("historyName", seaContent);
                    hashMap.put("table", item.getTable());
                    hashMap.put("tab_id", item.getT_id());
                    hashMap.put("search", seaContent.toLowerCase());
                    hashMap.put("time", "HKT:"+new SimpleDateFormat("yyyy.MM.dd HH:mm:ss z").format(new Date()));
                    reference.child("Histories").push().setValue(hashMap);
                }

                Intent intent=new Intent(mContext, InfoActivity.class);
                intent.putExtra("table",item.getTable());
                intent.putExtra("t_id",item.getT_id());
                mContext.startActivity(intent);
            }
        });

    }

        @Override
        public int getItemCount() {
        return mItems.size();
    }


        public class ViewHolder extends RecyclerView.ViewHolder{

            public TextView itemrname;


            public ViewHolder(View itemView){
                super(itemView);

                itemrname=itemView.findViewById(R.id.itemname);


            }
        }



}
