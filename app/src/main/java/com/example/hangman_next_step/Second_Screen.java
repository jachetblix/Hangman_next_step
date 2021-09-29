package com.example.hangman_next_step;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class Second_Screen extends AppCompatActivity {
    Button third_activity;
    Button button3;
    Intent intent;
    Spinner spinner;
    TextView txtViewChosen;
    ImageView imgViewIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);


        //Objects
        third_activity = findViewById(R.id.third_activity_btn);
//        third_activity.setOnClickListener(this);
        button3 = findViewById(R.id.button3);
//        button3.setOnClickListener(this);
        intent= new Intent(this, Third_Screen.class);
        txtViewChosen = findViewById(R.id.txt_level_chose);
        imgViewIcon = findViewById(R.id.anim_emoji);
        imgViewIcon.setVisibility(View.INVISIBLE);


    }
    //
//    @Override
//    public void onClick(View v) {
////        startActivity(intent);
//
//        AlertDialog.Builder button3 = new AlertDialog.Builder(Second_Screen.this);
//        View mView = getLayoutInflater().inflate(R.layout.game_spinner,null);
//        button3.setTitle("Choose you game level");
//        spinner = mView.findViewById(R.id.spinner);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Second_Screen.this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.gamelevels));
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
//
//        button3.setPositiveButton("Ok", (dialog, which) -> {
//            if (!spinner.getSelectedItem().toString().equalsIgnoreCase("Choose your difficulty")) {
//                Toast.makeText(Second_Screen.this,
//                spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT)
//                        .show();
//                dialog.dismiss();
//                txtViewChosen.setText(spinner.getSelectedItem().toString());
//
//            }
//        });
//        button3.setNegativeButton("dismiss", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//            }
//        });
//        button3.setView(mView);
//        AlertDialog dialog = button3.create();
//        dialog.show();
//
//
//    }
    private void startAnimationButton(int idNum) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        Button btnAlef = findViewById(idNum);

        btnAlef.startAnimation(anim);


    }
    private void playSoundButton() {
        MediaPlayer mp;

        mp = MediaPlayer.create(this, R.raw.bubble_rise_edited);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                // TODO Auto-generated method stub
                mp.reset();
                mp.release();
                mp = null;
            }
        });
        mp.start();
    }

    public void onClickGameLevel(View view) {
        AlertDialog.Builder button3 = new AlertDialog.Builder(Second_Screen.this);
        View mView = getLayoutInflater().inflate(R.layout.game_spinner,null);
        button3.setTitle("Choose you game level");
        spinner = mView.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Second_Screen.this, android.R.layout.simple_spinner_item,getResources().getStringArray(R.array.gamelevels));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        playSoundButton();
        startAnimationButton(R.id.button3);
        button3.setPositiveButton("Ok", (dialog, which) -> {
            if (!spinner.getSelectedItem().toString().equalsIgnoreCase("Choose your difficulty")) {
                Toast.makeText(Second_Screen.this,
                        spinner.getSelectedItem().toString(),Toast.LENGTH_SHORT)
                        .show();
                dialog.dismiss();


                txtViewChosen.setText(spinner.getSelectedItem().toString());
                //Animation setup
                Animation anim = AnimationUtils.loadAnimation(this,R.anim.bounce);
//                anim.setDuration(100);

                txtViewChosen.animate().setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
//                        txtViewChosen.setVisibility(View.GONE);

                    }
                });

                anim.start();

                imgViewIcon.setVisibility(View.VISIBLE);
                String chosenOption = spinner.getSelectedItem().toString();
                Boolean animationMode = true;


                if(!spinner.getSelectedItem().toString().equalsIgnoreCase("Choose your difficulty")) {
                    if (chosenOption.equals("Easy")) {
                        imgViewIcon.setImageResource(R.drawable.easy_emoji_animation);

                    } else if (chosenOption.equals("Middle")) {
                        imgViewIcon.setImageResource(R.drawable.medium_emoji_anim);
                    } else {
                        imgViewIcon.setImageResource(R.drawable.hard_emoji_anim);
                    }


                }
                else
                {   imgViewIcon.setImageResource(R.drawable.question_mark_png);
                    animationMode = false;
                }
                if(animationMode) {
                    AnimationDrawable animDraw = (AnimationDrawable) imgViewIcon.getDrawable();
                    animDraw.start();
                }





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

    public void onClickStartGame(View view) {
        playSoundButton();
        startActivity(intent);
    }

    public static void wait(int ms)
    {
        try
        {

            Thread.sleep(ms);
        }
        catch(InterruptedException ex)
        {
            Thread.currentThread().interrupt();
        }
    }
}
