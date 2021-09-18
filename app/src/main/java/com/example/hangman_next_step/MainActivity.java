package com.example.hangman_next_step;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity implements  OnClickListener{

    Button button_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button_2 = findViewById(R.id.button2);
        button_2.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Second_Screen.class);
        startActivity(intent);
    }

}