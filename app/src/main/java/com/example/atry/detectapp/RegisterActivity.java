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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username,emailid,pass,confirmpass;
    Button registerbtn;
    String userid,password;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username=findViewById(R.id.edittext1);
        emailid=findViewById(R.id.edittext2);
        pass=findViewById(R.id.edittext3);
        confirmpass=findViewById(R.id.edittext4);
        registerbtn=findViewById(R.id.registerbtn);
        firebaseAuth = FirebaseAuth.getInstance();
        registerbtn.setOnClickListener(this);


    }

    public void onClick(View view) {
        if(view==registerbtn)
        {
            registeration();
        }
    }

    private void registeration() {
        userid=emailid.getText().toString().trim();
        password=pass.getText().toString().trim();
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
        //progressDialog.setMessage(" Registering.....");
         // progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(userid,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    // user is succesfully registered
                    // start the porfile activity here
                    //   progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this,"Registered successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

                }else {
                    Toast.makeText(RegisterActivity.this," unsuccessful",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
