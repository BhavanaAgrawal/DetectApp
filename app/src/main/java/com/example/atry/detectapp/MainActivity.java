package com.example.atry.detectapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
Button bt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt1= findViewById(R.id.StartBtn);

        bt1.setOnClickListener( this);
    }

public void onClick(View view)
    {
if(view==bt1)
{
    startActivity(new Intent(MainActivity.this,LoginActivity.class));
}
    }
}
