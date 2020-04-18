package com.example.group_project_0_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.group_project_0_1.Model.chatUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Chatting extends AppCompatActivity {


    private TextView username;
    private CircleImageView image;
    private Toolbar toolbar;

    private FirebaseAuth mAuth;
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

        mAuth = FirebaseAuth.getInstance();
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

    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        UserIntercept(currentUser);
    }
    // [END on_start_check_user]


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
}
