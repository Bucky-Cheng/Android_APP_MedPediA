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
import android.widget.EditText;

import com.example.group_project_0_1.Adapter.DrAdapter;
import com.example.group_project_0_1.Adapter.HisAdapter;
import com.example.group_project_0_1.Login;
import com.example.group_project_0_1.Model.DrProfile;
import com.example.group_project_0_1.Model.History;
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


public class HistoryF extends Fragment {

    private RecyclerView recyclerView;
    private HisAdapter hisAdapter;
    private List<History> mHis;
    private EditText search_his;
    private Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_history,container,false);

        recyclerView=view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mHis=new ArrayList<>();
        view.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(view.getContext(), Login.class);
                startActivity(intent);
            }
        });

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser==null){
            System.out.println("BBBBBBBBBBBBB");
            view.findViewById(R.id.log).setVisibility(View.VISIBLE);
            view.findViewById(R.id.login).setVisibility(View.VISIBLE);
            view.findViewById(R.id.noinfo).setVisibility(View.VISIBLE);
            view.findViewById(R.id.search_his).setVisibility(View.GONE);
            view.findViewById(R.id.recycler_view).setVisibility(View.GONE);

        }else {
            VerifyProfile verifyProfile = new VerifyProfile();
            try {
                if (!verifyProfile.Verified(firebaseUser.getUid())) {

                    view.findViewById(R.id.log).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.login).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.noinfo).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.search_his).setVisibility(View.GONE);
                    view.findViewById(R.id.recycler_view).setVisibility(View.GONE);

                } else {
                    System.out.println("HHHHHHHHHH");
                    view.findViewById(R.id.log).setVisibility(View.GONE);
                    view.findViewById(R.id.login).setVisibility(View.GONE);
                    view.findViewById(R.id.noinfo).setVisibility(View.GONE);
                    view.findViewById(R.id.search_his).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.recycler_view).setVisibility(View.VISIBLE);


                    readHis();
                    search_his = view.findViewById(R.id.search_his);
                    search_his.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            searchHis(s.toString().toLowerCase());
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


    private void readHis(){
        final FirebaseUser firebaseUsera= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUsera!=null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Histories");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (search_his.getText().toString().equals("")) {
                        mHis.clear();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            History his = snapshot.getValue(History.class);

                            assert his != null;

                            System.out.println("UID:"+firebaseUsera.getUid());
                            if (his.getUid().equals(firebaseUsera.getUid())) {
                                System.out.println("DRLIST+" + his.getUid());
                                mHis.add(his);
                            }

                        }
                        hisAdapter = new HisAdapter(getContext(), mHis);
                        recyclerView.setAdapter(hisAdapter);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }

            });
        }
    }

    public void searchHis(final String hisname){

        final FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        Query query= FirebaseDatabase.getInstance().getReference("Histories").orderByChild("search");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mHis.clear();
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    History his=snapshot.getValue(History.class);

                    if(his.getUid().equals(firebaseUser.getUid())){
                        if(his.getSearch().contains(hisname)) {

                            mHis.add(his);
                        }
                    }

                }
                hisAdapter=new HisAdapter(getContext(),mHis);
                recyclerView.setAdapter(hisAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




}
