package com.example.group_project_0_1;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.group_project_0_1.Model.DrProfile;
import com.example.group_project_0_1.Model.chatUser;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class profileActivity extends AppCompatActivity {

    CircleImageView profile_imaage;
    TextView username;
    TextView category;
    TextView pro;
    TextView intro;

    FirebaseUser fuser;
    DatabaseReference reference;

    StorageReference storageReference;
    private static final int IMAGE_REQUEST=1;
    private Uri imageUri;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profile_imaage=findViewById(R.id.profile_image);
        username=findViewById(R.id.username);
        category=findViewById(R.id.category);
        pro=findViewById(R.id.pro);
        intro=findViewById(R.id.intro);

        storageReference= FirebaseStorage.getInstance().getReference("upload");

        fuser= FirebaseAuth.getInstance().getCurrentUser();
        reference= FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatUser user=dataSnapshot.getValue(chatUser.class);

                username.setText(user.getUsername());
                if(user.getImageUri().equals("default")) {
                    profile_imaage.setImageResource(R.mipmap.ic_icon);
                }else{
                    Glide.with(getApplicationContext()).load(user.getImageUri()).into(profile_imaage);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        getDr();


        profile_imaage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImage();
            }
        });

    }

    public void getDr(){
        FirebaseUser firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference referencea=FirebaseDatabase.getInstance().getReference("Doctors").child(firebaseUser.getUid());



            category.setText("持牌醫師");
            referencea.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    DrProfile drProfile=dataSnapshot.getValue(DrProfile.class);
                    if(drProfile!=null) {
                        pro.setText(drProfile.getPro());
                        intro.setText(drProfile.getIntro());
                        pro.setVisibility(View.VISIBLE);
                        intro.setVisibility(View.VISIBLE);
                    }else{
                        category.setText("香港居民");
                        pro.setVisibility(View.GONE);
                        findViewById(R.id.introtitle).setVisibility(View.GONE);
                        intro.setVisibility(View.GONE);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




    }

    private void openImage(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri){
        ContentResolver contentResolver=getApplicationContext().getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage(){
        final ProgressDialog progressDialog=new ProgressDialog(profileActivity.this);
        progressDialog.setMessage("upload緊...");
        progressDialog.show();

        if(imageUri!=null){
            StorageReference fileReference=storageReference.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
            uploadTask=fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot,Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(!task.isSuccessful()){
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if(task.isSuccessful()){
                        Uri downloadUri=task.getResult();
                        String mUri=downloadUri.toString();

                        reference=FirebaseDatabase.getInstance().getReference("Users").child(fuser.getUid());
                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("imageUri",mUri);
                        reference.updateChildren(hashMap);

                        progressDialog.dismiss();
                    }else{
                        Toast.makeText(profileActivity.this,"FIELD",Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(profileActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    progressDialog.show();
                }
            });
        }else{
            Toast.makeText(profileActivity.this,"無圖片被選中",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==IMAGE_REQUEST && resultCode==RESULT_OK && data!=null &&data.getData()!=null){
            imageUri=data.getData();

            if(uploadTask!=null && uploadTask.isInProgress()){
                Toast.makeText(profileActivity.this,"上傳中",Toast.LENGTH_LONG).show();
            }else{
                uploadImage();
            }
        }
    }

}