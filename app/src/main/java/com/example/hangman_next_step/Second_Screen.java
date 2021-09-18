package com.example.hangman_next_step;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Second_Screen extends AppCompatActivity implements OnClickListener {
    Button third_activity;
    Button button3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);
        third_activity = findViewById(R.id.third_activity_btn);
        third_activity.setOnClickListener(this);
        button3 = findViewById(R.id.button3);
        button3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this, Third_Screen.class);
        startActivity(intent);

        AlertDialog.Builder button3 = new AlertDialog.Builder(Second_Screen.this);
        View mView = getLayoutInflater().inflate(R.layout.game_spinner,null);
        button3.setTitle("Choose you game level");
        Spinner spinner = mView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Second_Screen.this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.gamelevels));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        button3.setPositiveButton("Ok", (dialog, which) -> {
            if (!spinner.getSelectedItem().toString().equalsIgnoreCase("Choose your difficulty")) {
                Toast.makeText(Second_Screen.this,
                spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT)
                        .show();
                dialog.dismiss();
            }
        });
        button3.setNegativeButton("dismiss", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
            }
        });
        button3.setView(mView);
        AlertDialog dialog = button3.create();
        dialog.show();
    }
}
