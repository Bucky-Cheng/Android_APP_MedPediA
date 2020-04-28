package com.example.group_project_0_1.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.example.group_project_0_1.Adapter.DrAdapter;
import com.example.group_project_0_1.Adapter.UserAdapter;
import com.example.group_project_0_1.Chatting;
import com.example.group_project_0_1.Login;
import com.example.group_project_0_1.Model.Dr;
import com.example.group_project_0_1.Model.DrProfile;
import com.example.group_project_0_1.Model.chatUser;
import com.example.group_project_0_1.R;
import com.example.group_project_0_1.Service.VerifyProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;


public class SeeDrF extends Fragment {


    private RecyclerView recyclerView;
    private DrAdapter drAdapter;
    private List<DrProfile> mDrs;
    private EditText search_drs;
    private Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_see_dr,container,false);

        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mDrs=new ArrayList<>();
        view.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(view.getContext(), Login.class);
                startActivity(intent);
            }
        });

        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        System.out.println("FFIIIRREEE++"+firebaseUser.getUid());
        if(firebaseUser==null){
            System.out.println("BBBBBBBBBBBBB");
            view.findViewById(R.id.log).setVisibility(View.VISIBLE);
            view.findViewById(R.id.login).setVisibility(View.VISIBLE);
            view.findViewById(R.id.noinfo).setVisibility(View.VISIBLE);
            view.findViewById(R.id.search_drs).setVisibility(View.GONE);
            view.findViewById(R.id.recycler_view).setVisibility(View.GONE);

        }else {
            VerifyProfile verifyProfile = new VerifyProfile();
            try {
                if (!verifyProfile.Verified(firebaseUser.getUid())) {
                    System.out.println("FFFFFFFFFFFFFFFF");
                    view.findViewById(R.id.log).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.login).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.noinfo).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.search_drs).setVisibility(View.GONE);
                    view.findViewById(R.id.recycler_view).setVisibility(View.GONE);

                } else {
                        System.out.println("LLLOOOGGG");
                    view.findViewById(R.id.log).setVisibility(View.GONE);
                    view.findViewById(R.id.login).setVisibility(View.GONE);
                    view.findViewById(R.id.noinfo).setVisibility(View.GONE);
                    view.findViewById(R.id.search_drs).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);


                    readUsers();
                    search_drs = view.findViewById(R.id.search_drs);
                    search_drs.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            searchUser(s.toString().toLowerCase());
                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });
                }
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return view;
    }

    private void readUsers(){
        final FirebaseUser firebaseUsera= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUsera!=null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Doctors");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (search_drs.getText().toString().equals("")) {
                        mDrs.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            DrProfile dr = snapshot.getValue(DrProfile.class);

                            assert dr != null;

                            System.out.println("UID:"+firebaseUsera.getUid());
                            if (!(dr.getId().equals(firebaseUsera.getUid()))) {
                                System.out.println("DRLIST+" + dr.getId());
                                mDrs.add(dr);
                            }

                        }
                        drAdapter = new DrAdapter(getContext(), mDrs);
                        recyclerView.setAdapter(drAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }
    }

    public void searchUser(final String username){

        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Query query= FirebaseDatabase.getInstance().getReference("Doctors").orderByChild("search");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mDrs.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    DrProfile dr=snapshot.getValue(DrProfile.class);

                    if(!dr.getId().equals(firebaseUser.getUid())){
                        if(dr.getSearch().contains(username)) {

                            mDrs.add(dr);
                        }
                    }

                }
                drAdapter=new DrAdapter(getContext(),mDrs);
                recyclerView.setAdapter(drAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
