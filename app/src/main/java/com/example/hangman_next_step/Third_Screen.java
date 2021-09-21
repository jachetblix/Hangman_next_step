package com.example.hangman_next_step;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Third_Screen extends AppCompatActivity {
    private LinearLayout layoutMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);


        //OBJECTS INITIALIZATION
        layoutMenu = findViewById(R.id.layout_menu);

    }

    //CLICK EVENTS(NON-LETTERS)
    public void onClickMenu(View view) {
        layoutMenu.setVisibility(View.VISIBLE);
    }

    //Aniamtions
    private void startAnimationLetter(int idNum) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        Button btnAlef = findViewById(idNum);

        btnAlef.startAnimation(anim);

    }
    //Sounds
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

    public void onClickLetter_alef(View view) {
        startAnimationLetter(R.id.letter_alef);
        playSoundButton();


    }

    public void onClickLetter_bet(View view) {
        startAnimationLetter(R.id.letter_bet);
        playSoundButton();


    }

    public void onClickLetter_gimel(View view) {
        startAnimationLetter(R.id.letter_gimel);

    }

    public void onClickLetter_dalet(View view) {
        startAnimationLetter(R.id.letter_dalet);
        playSoundButton();

    }

    public void onClickLetter_hey(View view) {
        startAnimationLetter(R.id.letter_hey);
        playSoundButton();

    }

    public void onClickLetter_vav(View view) {
        startAnimationLetter(R.id.letter_vav);
        playSoundButton();

    }

    public void onClickLetter_z(View view) {
        startAnimationLetter(R.id.letter_z);
        playSoundButton();

    }

    public void onClickLetter_het(View view) {
        startAnimationLetter(R.id.letter_het);
        playSoundButton();

    }

    public void onClickLetter_tet(View view) {
        startAnimationLetter(R.id.letter_tet);
        playSoundButton();

    }

    public void onClickLetter_yod(View view) {
        startAnimationLetter(R.id.letter_yod);
        playSoundButton();

    }

    public void onClickLetter_kaf(View view) {
        startAnimationLetter(R.id.letter_kaf);
        playSoundButton();

    }

    public void onClickLetter_lamed(View view) {
        startAnimationLetter(R.id.letter_lamed);
        playSoundButton();

    }

    public void onClickLetter_mem(View view) {
        startAnimationLetter(R.id.letter_mem);
        playSoundButton();

    }

    public void onClickLetter_nun(View view) {
        startAnimationLetter(R.id.letter_nun);
        playSoundButton();

    }

    public void onClickLetter_samech(View view) {
        startAnimationLetter(R.id.letter_samech);
        playSoundButton();

    }

    public void onClickLetter_ain(View view) {
        startAnimationLetter(R.id.letter_ain);
        playSoundButton();

    }

    public void onClickLetter_pey(View view) {
        startAnimationLetter(R.id.letter_pey);
        playSoundButton();

    }

    public void onClickLetter_zadik(View view) {
        startAnimationLetter(R.id.letter_zadik);
        playSoundButton();

    }

    public void onClickLetter_kuf(View view) {
        startAnimationLetter(R.id.letter_kuf);
        playSoundButton();

    }

    public void onClickLetter_resh(View view) {
        startAnimationLetter(R.id.letter_resh);
        playSoundButton();

    }

    public void onClickLetter_shin(View view) {
        startAnimationLetter(R.id.letter_shin);
        playSoundButton();
    }

    public void onClickLetter_taf(View view) {

        startAnimationLetter(R.id.letter_taf);
        playSoundButton();
    }


}
