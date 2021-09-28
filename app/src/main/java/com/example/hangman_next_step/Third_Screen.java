package com.example.hangman_next_step;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class Third_Screen extends AppCompatActivity {
    private LinearLayout layoutMenu,layoutGame;
    private Button btnMenu;
    private boolean wordAdded,secondWordAdded;
    private String str;
    private int foundLetters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);
        str = "אנדרויד";

        //OBJECTS INITIALIZATION
        layoutMenu = findViewById(R.id.layout_menu);
        layoutGame = findViewById(R.id.layout_game);

        //Buttons initialization
        btnMenu = findViewById(R.id.btn_menu);
        createWordsGame();

        //Initialize the discvoered letters counter.
        foundLetters  = 0;
    }
    //===================
    //CLICK EVENTS(NON-LETTERS)
    public void onClickMenu(View view) {

//        layoutGame.setVisibility(View.GONE);
        btnMenu.setVisibility(View.GONE);
        playSoundButton();
        layoutMenu.setVisibility(View.VISIBLE);
        startAnimationMenu(0);
    }
    public void onClickReturn(View view) {

        playSoundButtonReturn();
        startAnimationButton(R.id.btn_return);
        layoutMenu.setVisibility(View.GONE);
//        startAnimationMenu(1);
        layoutGame.setVisibility(View.VISIBLE);
        btnMenu.setVisibility(View.VISIBLE);





    }
    //====GAME REALTED FUNCTIONS========//
    private void endGame(int state)
    {
        //Lose
        if(state==0)
        {

        }
        else
        {
            //Won
            playSoundWinner();



        }
    }

    private void createWordsGame()
    {
        int len_word = str.length();
        boolean twoWords =false;

        if (str.indexOf(' ')!=-1) {twoWords = true; }

        LinearLayout firstLayout = findViewById(R.id.first_word_layout);


        int i = 0;
//        System.out.println(str.charAt(i));
        while(i<len_word&&str.charAt(i)!=' ' &&!wordAdded)
        {
            ImageView imgView = new ImageView(this);
            //TODO: Change size of lines in accordance to length of a word
            int size  = 0;
            if(len_word<=6)
                size = 150;
            else if(len_word==7)
                size=120;
            else
                size=150;

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size,size);

            imgView.setLayoutParams(params);
            imgView.setBackgroundResource(R.drawable.letter_blank);
            firstLayout.addView(imgView);
            i++;

        }

//        while(i<len_word)
//        {
//
//        }
        wordAdded = true;

    }

    private void applyLetter(int i,char ch)
    {
        LinearLayout layout = findViewById(R.id.first_word_layout);

        ImageView imgView = (ImageView) layout.getChildAt(i);

        switch(ch)
        {
            case 'א':
                imgView.setBackgroundResource(R.drawable.alef);
                break;
            case 'ב':
                imgView.setBackgroundResource(R.drawable.bet);
                break;
            case 'ג':
                imgView.setBackgroundResource(R.drawable.gimel);
                break;
            case 'ד':
                imgView.setBackgroundResource(R.drawable.daled);
                break;
            case 'ה':
            imgView.setBackgroundResource(R.drawable.hey);
                break;
            case 'ו':
                imgView.setBackgroundResource(R.drawable.vav);
                break;
            case 'ז':
                imgView.setBackgroundResource(R.drawable.zi);
                break;
        }
    }
    private int checkLetter(char ch)
    {
        int ind = 0;
        int val = str.indexOf(ch,ind);
        int count = 0;

        if(val!=-1) {
            //Reveals the letter
            applyLetter(val,ch);

            //Update values
            ind = val + 1;
            val = str.indexOf(ch,ind);

            count+=1;

            while(val!=-1) {
                //Reveals the letter
                applyLetter(val,ch);

                //Update values
                ind = val + 1;
                val = str.indexOf(ch,ind);
                count+=1;
            }
        }

        return count;


    }


    //Animations
    private void startAnimationWinner()
    {

    }
    private void startAnimationButton(int idNum) {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.scale_up);
        Button btnAlef = findViewById(idNum);

        btnAlef.startAnimation(anim);


    }

    private void startAnimationMenu(int state)
    {

        Animation anim;
        LinearLayout layout = findViewById(R.id.layout_menu);
        if (state==0) {
            //Open  menu
            anim = AnimationUtils.loadAnimation(this, R.anim.toptobottom);
        }
        else {
            //Close menu
            anim = AnimationUtils.loadAnimation(this, R.anim.bottomtotop);

        }
        //TODO:Add scale up animation to button return
        //TODO: Add scale up animation to button exit
        layout.startAnimation(anim);
    }

    //===================
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


    private void playSoundButtonReturn() {
        MediaPlayer mp;

        mp = MediaPlayer.create(this, R.raw.zapsplat_multimedia_button_click);
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
    private void playSoundWinner() {
        MediaPlayer mp;

        mp = MediaPlayer.create(this, R.raw.magic_chime);
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
        startAnimationButton(R.id.letter_alef);
        playSoundButton();
        int found = checkLetter('א');

        if(found>0)
        {
            Button btn = findViewById(R.id.letter_alef);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            if(foundLetters==str.length())
                endGame(1);//Won the game!
        }
        else
        {
            //WRONG GUESS
        }

    }

    public void onClickLetter_bet(View view) {
        startAnimationButton(R.id.letter_bet);
        playSoundButton();
        int found = checkLetter('ב');


        if(found>0)
        {
            Button btn = findViewById(R.id.letter_bet);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            if(foundLetters==str.length())
                endGame(1);//Won the game!
        }
        else
        {
            //WRONG GUESS
        }


    }

    public void onClickLetter_gimel(View view) {
        startAnimationButton(R.id.letter_gimel);
        playSoundButton();

        int found = checkLetter('ג');

        if(found>0)
        {
            Button btn = findViewById(R.id.letter_gimel);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            if(foundLetters==str.length())
                endGame(1);//Won the game!
        }
        else
        {
            //WRONG GUESS
        }

    }

    public void onClickLetter_dalet(View view) {
        startAnimationButton(R.id.letter_dalet);
        playSoundButton();

    }

    public void onClickLetter_hey(View view) {
        startAnimationButton(R.id.letter_hey);
        playSoundButton();

    }

    public void onClickLetter_vav(View view) {
        startAnimationButton(R.id.letter_vav);
        playSoundButton();

    }

    public void onClickLetter_z(View view) {
        startAnimationButton(R.id.letter_z);
        playSoundButton();

    }

    public void onClickLetter_het(View view) {
        startAnimationButton(R.id.letter_het);
        playSoundButton();

    }

    public void onClickLetter_tet(View view) {
        startAnimationButton(R.id.letter_tet);
        playSoundButton();

    }

    public void onClickLetter_yod(View view) {
        startAnimationButton(R.id.letter_yod);
        playSoundButton();

    }

    public void onClickLetter_kaf(View view) {
        startAnimationButton(R.id.letter_kaf);
        playSoundButton();

    }

    public void onClickLetter_lamed(View view) {
        startAnimationButton(R.id.letter_lamed);
        playSoundButton();

    }

    public void onClickLetter_mem(View view) {
        startAnimationButton(R.id.letter_mem);
        playSoundButton();

    }

    public void onClickLetter_nun(View view) {
        startAnimationButton(R.id.letter_nun);
        playSoundButton();

    }

    public void onClickLetter_samech(View view) {
        startAnimationButton(R.id.letter_samech);
        playSoundButton();

    }

    public void onClickLetter_ain(View view) {
        startAnimationButton(R.id.letter_ain);
        playSoundButton();

    }

    public void onClickLetter_pey(View view) {
        startAnimationButton(R.id.letter_pey);
        playSoundButton();

    }

    public void onClickLetter_zadik(View view) {
        startAnimationButton(R.id.letter_zadik);
        playSoundButton();

    }

    public void onClickLetter_kuf(View view) {
        startAnimationButton(R.id.letter_kuf);
        playSoundButton();

    }

    public void onClickLetter_resh(View view) {
        startAnimationButton(R.id.letter_resh);
        playSoundButton();

    }

    public void onClickLetter_shin(View view) {
        startAnimationButton(R.id.letter_shin);
        playSoundButton();
    }

    public void onClickLetter_taf(View view) {

        startAnimationButton(R.id.letter_taf);
        playSoundButton();
    }



}
