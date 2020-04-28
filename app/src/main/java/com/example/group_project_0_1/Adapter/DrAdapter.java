package com.example.group_project_0_1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.group_project_0_1.MessageActivity;
import com.example.group_project_0_1.Model.Chat;
import com.example.group_project_0_1.Model.DrProfile;
import com.example.group_project_0_1.Model.Follows;
import com.example.group_project_0_1.Model.chatUser;
import com.example.group_project_0_1.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
        final ArrayList flag = new ArrayList();
        final DrProfile dr=mDrs.get(position);

        holder.username.setText(dr.getUsername());
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference1=FirebaseDatabase.getInstance().getReference("Users");
        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String img="";

                System.out.println("AAAAAAAA");
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    chatUser userdr=snapshot.getValue(chatUser.class);
                    if(userdr.getId().equals(dr.getId())){
                        img=userdr.getImageUri();
                        break;
                    }
                }
                if(img.equals("default")) {
                    holder.profile_image.setImageResource(R.drawable.dr);
                }else{
                    Glide.with(mContext).load(img).into(holder.profile_image);
                }

                img="";

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        holder.dr_intro.setText(dr.getIntro());
        holder.dr_pro.setText(dr.getPro());

        DatabaseReference reference2=FirebaseDatabase.getInstance().getReference(firebaseUser.getUid());
        reference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    Follows follows=snapshot.getValue(Follows.class);
                    if(follows.getTid().equals(dr.getId())){
                        flag.add(dr.getId());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        if (flag.size()>0){
            holder.follow.setText("追蹤中");
        }

        holder.follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if(!holder.follow.getText().equals("追蹤中")) {


                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    HashMap<String, Object> hashMap = new HashMap<>();

                    hashMap.put("tid", dr.getId());

                    reference.child(firebaseUser.getUid()).push().setValue(hashMap);
                    Toast.makeText(mContext, "己追蹤", Toast.LENGTH_LONG).show();
                    holder.follow.setText("追蹤中");
                }
            }
        });








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
        public Button follow;


        public ViewHolder(View itemView){
            super(itemView);

            username=itemView.findViewById(R.id.username);
            profile_image=itemView.findViewById(R.id.profile_image);
            dr_pro=itemView.findViewById(R.id.dr_pro);
            dr_intro=itemView.findViewById(R.id.dr_intro);
            follow=itemView.findViewById(R.id.follow);
        }
    }


    private String setImg(DrProfile dr){
        System.out.println("DRLISTAAAA    "+dr.getId());
        final ArrayList<chatUser> chatUserList = new ArrayList<>();
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference referenceDr=FirebaseDatabase.getInstance().getReference("Users");
        System.out.println("DRLISTBBB    "+referenceDr);



        referenceDr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("DRLISTBBB    "+dr.getId());
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    chatUser chatUser1 = snapshot.getValue(chatUser.class);

                    if(chatUser1.getId().equals(dr.getId())) {

                        chatUserList.add(chatUser1);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        System.out.println("DRLISTAAAA"+chatUserList.get(0).getId());
        return chatUserList.get(0).getImageUri();
    }


}
