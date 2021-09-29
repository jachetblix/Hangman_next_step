package com.example.hangman_next_step;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.view.View.OnClickListener;

public class MainActivity extends AppCompatActivity{

    Button button_2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        button_2 = findViewById(R.id.button2);
    }
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


    //Click events
    public void onClickStart(View view) {
//        playSoundButton();

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        Button btnStartGame = findViewById(R.id.btn_start_game);
        btnStartGame.startAnimation(anim);
        startAnimationButton(R.id.btn_start_game);
        playSoundButton();
//        createWordsGame();

        Intent intent = new Intent(this, Second_Screen.class);
        startActivity(intent);


    }
    public void onClickScores(View view) {
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        Button btnScores = findViewById(R.id.btn_scores);
        startAnimationButton(R.id.btn_scores);

        playSoundButton();

        btnScores.startAnimation(anim);
//        playSoundButton();



    }

}
//COMMIT CHECKING