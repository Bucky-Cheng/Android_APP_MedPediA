package com.example.group_project_0_1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.ResolutionDimension;

import com.example.group_project_0_1.R;
import com.example.group_project_0_1.Service.VerifyProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthMultiFactorException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorResolver;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.BrokenBarrierException;


public class Login extends ProgressActivity implements View.OnClickListener {

    private static final String TAG = "EmailPassword";

    private int STATUS_CODE=200;

    private TextView mStatusTextView;


    private EditText mEmailField;
    private EditText mPasswordField;
    private EditText mUserNameField;
    private EditText mDrIDField;
    private Spinner mDrProField;
    private RadioGroup userProfile;
    private RadioButton HKR;
    private RadioButton HKDR;
    private Boolean HKDRFlag=false;
    private String illPro="";
    private String[] illList;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    // [START declare_firebase_database]
    private DatabaseReference reference;
    // [END declare_firebase_database]

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setProgressBar(R.id.progressBar);


        // Views
        mStatusTextView = findViewById(R.id.status);

        mEmailField = findViewById(R.id.fieldEmail);
        mPasswordField = findViewById(R.id.fieldPassword);
        mUserNameField = findViewById(R.id.fieldName);
        mDrIDField=findViewById(R.id.fieldDrID);
        mDrProField=findViewById(R.id.fieldDrPro);
        userProfile=findViewById(R.id.radiogroup);
        HKR=findViewById(R.id.HKR);
        HKDR=findViewById(R.id.HKDR);
        // Buttons click
        findViewById(R.id.emailSignInButton).setOnClickListener(this);
        findViewById(R.id.emailCreateAccountButton).setOnClickListener(this);
        findViewById(R.id.signOutButton).setOnClickListener(this);
        findViewById(R.id.verifyEmailButton).setOnClickListener(this);
        findViewById(R.id.reloadButton).setOnClickListener(this);
        findViewById(R.id.forgetPassword).setOnClickListener(this);
        findViewById(R.id.verifyProfileButton).setOnClickListener(this);


        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]

        //spinner
        illList = getResources().getStringArray(R.array.pro_array);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,

                android.R.layout.simple_spinner_item, illList);
        mDrProField.setAdapter(adapter);
        mDrProField.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> arg0,
                                               View arg1, int arg2, long arg3) {
                        int index = arg0.getSelectedItemPosition();
                        illPro=illList[index];

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });

        userProfile.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == HKDR.getId()) {
                    Toast.makeText(Login.this, "請輸入醫師號碼",
                            Toast.LENGTH_SHORT).show();
                    mDrIDField.setVisibility(View.VISIBLE);
                    mDrProField.setVisibility(View.VISIBLE);
                    findViewById(R.id.spinner_name).setVisibility(View.VISIBLE);
                    HKDRFlag=true;
                } else {
                    mDrIDField.setVisibility(View.GONE);
                    HKDRFlag=false;
                }
            }
        });
    }

    // [START on_start_check_user]
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //mAuth.getCurrentUser().reload();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        try {
            updateUI(currentUser);
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    // [END on_start_check_user]

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressBar();

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            try {
                                updateUI(user);
                            } catch (BrokenBarrierException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "電子邮箱無效",
                                    Toast.LENGTH_SHORT).show();
                            try {
                                updateUI(null);
                            } catch (BrokenBarrierException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        // [START_EXCLUDE]
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        showProgressBar();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            try {
                                updateUI(user);
                            } catch (BrokenBarrierException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "信息驗証失敗",
                                    Toast.LENGTH_SHORT).show();
                            try {
                                updateUI(null);
                            } catch (BrokenBarrierException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            // [START_EXCLUDE]
                            //checkForMultiFactorFailure(task.getException());
                            // [END_EXCLUDE]
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            mStatusTextView.setText("當前狀態：信息驗証失敗");
                        }
                        hideProgressBar();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() throws BrokenBarrierException, InterruptedException {
        mAuth.signOut();
        updateUI(null);
    }

    private void sendEmailVerification() {
        // Disable button
        findViewById(R.id.verifyEmailButton).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        findViewById(R.id.verifyEmailButton).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this,
                                    "驗証身份信件已發送至" + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(Login.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    private void sendPasswordReset(final String emailAddress) {
        // [START send_password_reset]

        Log.d(TAG, "passwordReset:" + emailAddress);
        if (!onlyEmailValidateForm()) {
            return;
        }



        mAuth.sendPasswordResetEmail(emailAddress)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(Login.this,
                                    "重設密碼信件已發送至"+emailAddress,
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END send_password_reset]
    }

    private void storeInFirebaseDatabase(){
        // [START Store_Into_FirebaseDatabase]

        Log.d(TAG, "Store_Into_FirebaseDatabas");
        if (!userNameValidateForm()) {
            return;
        }
        FirebaseUser user=mAuth.getCurrentUser();
        String userid=user.getUid();


        reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

        HashMap<String,String> hashMap=new HashMap<>();
        hashMap.put("id",userid);
        hashMap.put("username",mUserNameField.getText().toString());
        hashMap.put("status","offline");
        hashMap.put("search",mUserNameField.getText().toString().toLowerCase());

        reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Log.d(TAG, "Store_Success.");
                    Toast.makeText(Login.this,
                            "成功",
                            Toast.LENGTH_SHORT).show();
                    finishUI();
                }
            }
        });
    }

    private void reload() {
        mAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    if (mAuth.getCurrentUser().isEmailVerified()){
                        Toast.makeText(Login.this,
                                "驗証成功",
                                Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Login.this,
                                "請閣下驗証電子邮箱",
                                Toast.LENGTH_SHORT).show();
                    }
                    try {
                        updateUI(mAuth.getCurrentUser());
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } else {
                    Log.e(TAG, "reload", task.getException());
                    Toast.makeText(Login.this,
                            "Failed to reload user.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean onlyEmailValidateForm(){
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }
        return valid;
    }

    private boolean userNameValidateForm(){
        boolean valid = true;

        String email = mUserNameField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mUserNameField.setError("Required.");
            valid = false;
        } else {
            mUserNameField.setError(null);
        }
        return valid;
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailField.setError("Required.");
            valid = false;
        } else {
            mEmailField.setError(null);
        }

        String password = mPasswordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordField.setError("Required.");
            valid = false;
        } else {
            mPasswordField.setError(null);
        }

        return valid;
    }

    private void updateUI(FirebaseUser user) throws BrokenBarrierException, InterruptedException {
        hideProgressBar();
        if (user != null) {
            mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                    user.getEmail(), user.isEmailVerified()));
            //mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
            findViewById(R.id.signedInButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.forgetPassword).setVisibility(View.GONE);
            if (user.isEmailVerified()) {
                VerifyProfile verifyProfile=new VerifyProfile();
                if(verifyProfile.Verified(user.getUid())){
                    finishUI();
                }else {
                    mUserNameField.setVisibility(View.VISIBLE);
                    userProfile.setVisibility(View.VISIBLE);
                    findViewById(R.id.verifyProfileButton).setVisibility(View.VISIBLE);
                    findViewById(R.id.verifyEmailButton).setVisibility(View.GONE);
                    findViewById(R.id.reloadButton).setVisibility(View.GONE);
                    //findViewById(R.id.signOutButton).setVisibility(View.VISIBLE);
                }
            } else {
                findViewById(R.id.signOutButton).setVisibility(View.GONE);
                findViewById(R.id.verifyEmailButton).setVisibility(View.VISIBLE);
                findViewById(R.id.verifyProfileButton).setVisibility(View.GONE);
                findViewById(R.id.reloadButton).setVisibility(View.VISIBLE);
            }
        } else {
            mStatusTextView.setText("當前狀態：未登入");
            findViewById(R.id.radiogroup).setVisibility(View.GONE);
            findViewById(R.id.fieldName).setVisibility(View.GONE);
            findViewById(R.id.emailPasswordButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.emailPasswordFields).setVisibility(View.VISIBLE);
            findViewById(R.id.signedInButtons).setVisibility(View.GONE);
            findViewById(R.id.forgetPassword).setVisibility(View.VISIBLE);
        }
    }

    private void finishUI(){
        FirebaseUser user=mAuth.getCurrentUser();
        mStatusTextView.setText(getString(R.string.emailpassword_status_fmt,
                user.getEmail(), user.isEmailVerified()));
        //mDetailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

        findViewById(R.id.emailPasswordButtons).setVisibility(View.GONE);
        findViewById(R.id.emailPasswordFields).setVisibility(View.GONE);
        findViewById(R.id.signOutButton).setVisibility(View.VISIBLE);
        findViewById(R.id.forgetPassword).setVisibility(View.GONE);
        findViewById(R.id.verifyEmailButton).setVisibility(View.GONE);
        findViewById(R.id.fieldName).setVisibility(View.GONE);
        findViewById(R.id.radiogroup).setVisibility(View.GONE);
        findViewById(R.id.fieldDrID).setVisibility(View.GONE);
        findViewById(R.id.fieldDrPro).setVisibility(View.GONE);
        findViewById(R.id.spinner_name).setVisibility(View.GONE);
        findViewById(R.id.reloadButton).setVisibility(View.GONE);
        mUserNameField.setVisibility(View.GONE);
        userProfile.setVisibility(View.GONE);
        findViewById(R.id.verifyProfileButton).setVisibility(View.GONE);
    }

    public Boolean addDr() throws BrokenBarrierException, InterruptedException {
        VerifyProfile verifyProfile=new VerifyProfile();
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        String DrName=mUserNameField.getText().toString();
        String Drid=mDrIDField.getText().toString();

        if(verifyProfile.VerifyDrID(Drid,illPro)){
            if(verifyProfile.VerifyDr(uid,DrName,Drid,illPro)){

                //ADD DR in Firebase
                FirebaseUser user=mAuth.getCurrentUser();
                String userid=user.getUid();
                reference= FirebaseDatabase.getInstance().getReference("Doctors").child(userid);

                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("id",userid);
                hashMap.put("username",mUserNameField.getText().toString());
                hashMap.put("pro",illPro);
                hashMap.put("intro","this my introduction");
                hashMap.put("search",mUserNameField.getText().toString().toLowerCase());

                reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "Store_Success.");

                        }
                    }
                });


                return true;
            }else{
                STATUS_CODE=400;
                return false;
            }
        }else{
            STATUS_CODE=500;
            return false;
        }
    }

    public Boolean addIllness() throws BrokenBarrierException, InterruptedException {
        VerifyProfile verifyProfile=new VerifyProfile();
        String uid=FirebaseAuth.getInstance().getCurrentUser().getUid();
        String username=mUserNameField.getText().toString();
        if(verifyProfile.VerifyIllness(uid,username)){
            return true;
        }else{
            STATUS_CODE=400;
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.emailCreateAccountButton) {
            createAccount(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.emailSignInButton) {
            signIn(mEmailField.getText().toString(), mPasswordField.getText().toString());
        } else if (i == R.id.signOutButton) {
            try {
                signOut();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (i == R.id.verifyEmailButton) {
            sendEmailVerification();
        } else if (i == R.id.reloadButton) {
            reload();
        }else if(i== R.id.forgetPassword){
            sendPasswordReset(mEmailField.getText().toString());
        }else if(i == R.id.verifyProfileButton){

            if(HKDRFlag){
                try {
                    if(addDr()){
                        storeInFirebaseDatabase();
                    }else{
                        if(STATUS_CODE==400){
                            Toast.makeText(Login.this,"insert error",Toast.LENGTH_LONG).show();
                        }else if(STATUS_CODE==500){
                            Toast.makeText(Login.this,"醫師號碼或職業錯誤！",Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                try {
                    if(addIllness()){
                        storeInFirebaseDatabase();
                    }else{
                        if(STATUS_CODE==400){
                            Toast.makeText(Login.this,"insert error",Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}