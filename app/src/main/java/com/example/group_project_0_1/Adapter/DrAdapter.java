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

import com.example.group_project_0_1.MessageActivity;
import com.example.group_project_0_1.Model.Chat;
import com.example.group_project_0_1.Model.DrProfile;
import com.example.group_project_0_1.Model.chatUser;
import com.example.group_project_0_1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DrAdapter extends RecyclerView.Adapter<DrAdapter.ViewHolder>{



    private Context mContext;
    private List<DrProfile> mDrs;

    private String lastRecord;

    public DrAdapter(Context context, List<DrProfile> mDrs){
        this.mContext=context;
        this.mDrs=mDrs;

    }

    @NonNull
    @Override
    public DrAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.dritem,parent,false);
        return new DrAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrAdapter.ViewHolder holder, int position) {
        final DrProfile dr=mDrs.get(position);
        holder.username.setText(dr.getUsername());
        holder.profile_image.setImageResource(R.drawable.dr);
        holder.dr_intro.setText(dr.getIntro());
        holder.dr_pro.setText(dr.getPro());






    }

    @Override
    public int getItemCount() {
        return mDrs.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;
        public TextView dr_pro;
        public TextView dr_intro;


        public ViewHolder(View itemView){
            super(itemView);

            username=itemView.findViewById(R.id.username);
            profile_image=itemView.findViewById(R.id.profile_image);
            dr_pro=itemView.findViewById(R.id.dr_pro);
            dr_intro=itemView.findViewById(R.id.dr_intro);

        }
    }





}
