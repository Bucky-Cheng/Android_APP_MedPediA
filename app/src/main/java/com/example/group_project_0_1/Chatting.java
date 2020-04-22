package com.example.group_project_0_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.group_project_0_1.Fragment.ChatsFragment;
import com.example.group_project_0_1.Fragment.UsersFragment;
import com.example.group_project_0_1.Model.chatUser;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chatting extends AppCompatActivity {


    private TextView username;
    private CircleImageView image;
    private Toolbar toolbar;

    private FirebaseAuth mAuth;
    public FirebaseAuth mAuth1;
    private FirebaseUser mUser;
    private DatabaseReference reference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        username=findViewById(R.id.username);
        image=findViewById(R.id.profile_image);


        mAuth=FirebaseAuth.getInstance();
        mUser= mAuth.getCurrentUser();



        reference= FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                chatUser chatuser=dataSnapshot.getValue(chatUser.class);
                username.setText(chatuser.getUsername());
                image.setImageResource(R.mipmap.ic_icon);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        TabLayout tabLayout=findViewById(R.id.tab_layout);
        ViewPager viewPager=findViewById(R.id.view_pager);

        ViewPageAdapter viewPageAdapter=new ViewPageAdapter(getSupportFragmentManager());

        viewPageAdapter.addFragment(new ChatsFragment(),"對話");
        viewPageAdapter.addFragment(new UsersFragment(),"聯絡人");

        viewPager.setAdapter(viewPageAdapter);

        tabLayout.setupWithViewPager(viewPager);

    }






    public void UserIntercept(FirebaseUser user){


        if (user != null) {
            // User is signed in
            boolean emailVerified = user.isEmailVerified();
            if(emailVerified){

            }else{
                //no verified
                Intent intent=new Intent(this, Login.class);
                startActivity(intent);
            }
        } else {
            Intent intent=new Intent(this, Login.class);
            startActivity(intent);
            // No user is signed in
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.homepage:
                Intent intent=new Intent(Chatting.this,MainActivity.class);
                startActivity(intent);
                break;
        }
        return true;
    }


    //[START]View page adapter
    class ViewPageAdapter extends FragmentPagerAdapter{


        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        public ViewPageAdapter(FragmentManager fm) {

            super(fm);


            this.fragments=new ArrayList<>();
            this.titles=new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
    //[END]

    private void status(String status){
        reference=FirebaseDatabase.getInstance().getReference("Users").child(mUser.getUid());
        HashMap<String,Object> hashMap=new HashMap<>();

        hashMap.put("status",status);
        reference.updateChildren(hashMap);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }
}
