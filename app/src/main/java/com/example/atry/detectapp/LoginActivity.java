package com.example.atry.detectapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    String userid,password;
  private EditText ed1,ed2;
  private Button btn1;
  private Button  txt1;
  private ProgressDialog progressDialog;
  private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ed1=findViewById(R.id.input_email);
        ed2=findViewById(R.id.input_password);
        btn1=findViewById(R.id.btn_login);
        txt1=findViewById(R.id.link_signup);
        progressDialog=new ProgressDialog(this);
         firebaseAuth = FirebaseAuth.getInstance();

         FirebaseApp.initializeApp(this);

         if(firebaseAuth.getCurrentUser()!=null)
        {
            finish();
            // start the mapsactivity
            startActivity(new Intent(getApplicationContext(),SampleActivity.class));
        }


        btn1.setOnClickListener(this);
         txt1.setOnClickListener(this);



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {


    }


    public void onClick(View view) {
        if(view==btn1)
        {
            userLogin();
        }
        if(view==txt1)
        {
            startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
        }
    }

    //Login method

    private void userLogin() {
        userid=ed1.getText().toString().trim();
        password=ed2.getText().toString().trim();

        if(TextUtils.isEmpty(userid))
        {
            // empty user login id
            Toast.makeText(this, "please fill the UserLogin Id", Toast.LENGTH_SHORT).show();
            //stopping the function

            return;
        }

        if(TextUtils.isEmpty(password))
        {
            //password filled empty
            Toast.makeText(this, "please fill the password", Toast.LENGTH_SHORT).show();
            //stopping the funtion
            return ;
        }

        //if the vatlidation arte ok
        // first show the progress bar
        progressDialog.setMessage(" logging in.....");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userid,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                if(task.isSuccessful())
                {
                    finish();
                    // start the mapsactivity
                    startActivity(new Intent(getApplicationContext(),SampleActivity.class));
                }
            }
        });

    }

}
