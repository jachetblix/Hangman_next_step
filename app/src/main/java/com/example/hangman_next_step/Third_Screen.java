package com.example.hangman_next_step;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Third_Screen extends AppCompatActivity {
    private LinearLayout layoutMenu,layoutGame;
    private Button btnMenu;
    private boolean wordAdded,secondWordAdded;
    private String str;
    private int foundLetters;
    private int numGuesses;
    private int points = 0;
    private TextView txtPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third_activity);
        String[] s = {"שלום רב", "כוכבה", "זוארץ", "ארטישוק","חתול","חולצה","בננה","טילון","קממבר","יגוסלביה","ספרד"};
        String[] categories = {"ברכה", "שמות", "שמות", "ירקות","חיות","דוממים","פירות","מאכלים","מאכלים","ערי בירה","מדינות"};
        Random ran = new Random();

        int num = ran.nextInt(s.length);
//        int num = 0;
        str = s[num];
        String strCategory = categories[num];


        //OBJECTS INITIALIZATION
        layoutMenu = findViewById(R.id.layout_menu);
        layoutGame = findViewById(R.id.layout_game);

        //Buttons initialization
        btnMenu = findViewById(R.id.btn_menu);
        createWordsGame();


        //--- Initial animation setup
        startAnimationSwinging();

        //Initialize the discvoered letters counter.
        foundLetters  = 0;
        numGuesses = 0;

        //--- Initialize TextViews
        TextView txtView = findViewById(R.id.txt_category);
        txtView.setText(strCategory);

        txtPoints = findViewById(R.id.txt_scores);

    }
    private void reducePoints(){
        if(points>5)
            points-=5;
        else
            points=0;

        txtPoints.setText(Integer.valueOf(points).toString());
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
    private void wrongGuess()
    {

        ImageView img = findViewById(R.id.hangman_img);
        switch(numGuesses)
        {
            case 0:
                img.setImageResource(R.drawable.hang2);
                reducePoints();
                playSoundWrong();

                break;
            case 1:
                img.setImageResource(R.drawable.hang3);
                reducePoints();

                playSoundWrong();
                break;
            case 2:
                img.setImageResource(R.drawable.hang4);
                reducePoints();

                playSoundWrong();
                break;
            case 3:
                img.setImageResource(R.drawable.hang7);
                reducePoints();

                playSoundWrong();

                break;
            case 4:
                img.setImageResource(R.drawable.hang8);
                reducePoints();

                playSoundWrong();
                break;
            case 5:
                endGame(0);
                break;

        }

        numGuesses+=1;


    }
    //===================
    //CLICK EVENTS(NON-LETTERS)

    public void onClickAgain(View view) {
        Intent intent = new Intent(this,Second_Screen.class);
        startActivity(intent);
    }

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
    private void endGameCheck()
    {
        if(foundLetters==str.length() || (foundLetters==str.length()-1 && str.indexOf(' ')!=-1))
            endGame(1);//Won the game!

    }
    private void endGame(int state)
    {
        //Lose
        if(state==0)
        {
            Animation anim;
            LinearLayout layout = findViewById(R.id.layout_game_end);
            layout.setVisibility(View.VISIBLE);

            ImageView img = findViewById(R.id.winner_animation);
            img.setImageResource(R.drawable.loser_list_anim);
            AnimationDrawable draw = (AnimationDrawable) img.getDrawable();
            playSoundLoser();
            draw.start();
        }
        else
        {
            wait(200);
            //Won
            playSoundWinner();

            Animation anim;
            LinearLayout layout = findViewById(R.id.layout_game_end);
            layout.setVisibility(View.VISIBLE);

           ImageView img = findViewById(R.id.winner_animation);
           AnimationDrawable draw = (AnimationDrawable) img.getDrawable();
           draw.start();

        }

        //TODO: ENTER SCORE TO DB

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
                size=100;


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size,size);

            imgView.setLayoutParams(params);
            imgView.setBackgroundResource(R.drawable.letter_blank);
            firstLayout.addView(imgView);
            i++;

        }
        i+=1;
        LinearLayout secondLayout = findViewById(R.id.second_word_layout);

        while(i<len_word)
        {
            ImageView imgView = new ImageView(this);
            //TODO: Change size of lines in accordance to length of a word
            int size  = 0;
            if(len_word<=6)
                size = 150;
            else if(len_word==7)
                size=120;
            else
                size=100;


            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size,size);

            imgView.setLayoutParams(params);
            imgView.setBackgroundResource(R.drawable.letter_blank);
            secondLayout.addView(imgView);
            i++;

        }
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
                imgView.setLayoutParams(new LinearLayout.LayoutParams(imgView.getWidth()-20,imgView.getHeight()-20));

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
                imgView.setLayoutParams(new LinearLayout.LayoutParams(imgView.getWidth(),imgView.getHeight()-20));

                break;
            case 'ז':
                imgView.setBackgroundResource(R.drawable.zi);
                break;
            case 'ח':
                imgView.setBackgroundResource(R.drawable.het);
                break;
            case 'ט':
                imgView.setBackgroundResource(R.drawable.tet);
                break;
            case 'י':
                imgView.setBackgroundResource(R.drawable.yud);
                break;
            case 'כ':
                imgView.setBackgroundResource(R.drawable.kaf);
                break;
            case 'ל':
                imgView.setBackgroundResource(R.drawable.lamed);
                break;
            case 'מ':
                imgView.setBackgroundResource(R.drawable.mem);
                break;
            case 'נ':
                imgView.setBackgroundResource(R.drawable.nun);
                break;
            case 'ס':
                imgView.setBackgroundResource(R.drawable.samech);
                break;
            case 'ע':
                imgView.setBackgroundResource(R.drawable.ain);
                break;
            case 'פ':
                imgView.setBackgroundResource(R.drawable.pey);
                break;
            case 'צ':
                imgView.setBackgroundResource(R.drawable.zadik);
                break;
            case 'ק':
                imgView.setBackgroundResource(R.drawable.kuf);
                imgView.setLayoutParams(new LinearLayout.LayoutParams(imgView.getWidth(),imgView.getHeight()+10));

                break;
            case 'ר':
                imgView.setBackgroundResource(R.drawable.resh);
                break;
            case 'ש':
                imgView.setBackgroundResource(R.drawable.shin);
                break;
            case 'ת':
                imgView.setBackgroundResource(R.drawable.taf);
                break;
            case 'ך':
                imgView.setBackgroundResource(R.drawable.final_kaf);
                imgView.setLayoutParams(new LinearLayout.LayoutParams(imgView.getWidth(),imgView.getHeight()+10));

                break;
            case 'ם':
                imgView.setBackgroundResource(R.drawable.final_m);

                break;
            case 'ן':
                imgView.setBackgroundResource(R.drawable.final_nun);
                imgView.setLayoutParams(new LinearLayout.LayoutParams(imgView.getWidth(),imgView.getHeight()+10));
                break;
            case 'ף':
                imgView.setLayoutParams(new LinearLayout.LayoutParams(imgView.getWidth(),imgView.getHeight()+10));
                imgView.setBackgroundResource(R.drawable.final_pey);
                break;
            case 'ץ':
                imgView.setLayoutParams(new LinearLayout.LayoutParams(imgView.getWidth(),imgView.getHeight()+10));
                imgView.setBackgroundResource(R.drawable.final_zadik);
                break;
        }
        points+=10;
        txtPoints.setText(Integer.valueOf(points).toString());
    }
    private void applyLetterAfter(int i,char ch)
    {
        LinearLayout layout = findViewById(R.id.second_word_layout);

        ImageView imgView = (ImageView) layout.getChildAt(i- str.indexOf(' ')-1);

        switch(ch)
        {
            case 'א':
                imgView.setBackgroundResource(R.drawable.alef);
                break;
            case 'ב':
                imgView.setBackgroundResource(R.drawable.bet);
                imgView.setLayoutParams(new LinearLayout.LayoutParams(imgView.getWidth()-20,imgView.getHeight()-20));

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
                imgView.setLayoutParams(new LinearLayout.LayoutParams(imgView.getWidth(),imgView.getHeight()-20));

                break;
            case 'ז':
                imgView.setBackgroundResource(R.drawable.zi);
                break;
            case 'ח':
                imgView.setBackgroundResource(R.drawable.het);
                break;
            case 'ט':
                imgView.setBackgroundResource(R.drawable.tet);
                break;
            case 'י':
                imgView.setBackgroundResource(R.drawable.yud);
                break;
            case 'כ':
                imgView.setBackgroundResource(R.drawable.kaf);
                break;
            case 'ל':
                imgView.setBackgroundResource(R.drawable.lamed);
                break;
            case 'מ':
                imgView.setBackgroundResource(R.drawable.mem);
                break;
            case 'נ':
                imgView.setBackgroundResource(R.drawable.nun);
                break;
            case 'ס':
                imgView.setBackgroundResource(R.drawable.samech);
                break;
            case 'ע':
                imgView.setBackgroundResource(R.drawable.ain);
                break;
            case 'פ':
                imgView.setBackgroundResource(R.drawable.pey);
                break;
            case 'צ':
                imgView.setBackgroundResource(R.drawable.zadik);
                break;
            case 'ק':
                imgView.setBackgroundResource(R.drawable.kuf);
                imgView.setLayoutParams(new LinearLayout.LayoutParams(imgView.getWidth(),imgView.getHeight()+10));

                break;
            case 'ר':
                imgView.setBackgroundResource(R.drawable.resh);
                break;
            case 'ש':
                imgView.setBackgroundResource(R.drawable.shin);
                break;
            case 'ת':
                imgView.setBackgroundResource(R.drawable.taf);
                break;
            case 'ך':
                imgView.setBackgroundResource(R.drawable.final_kaf);
                imgView.setLayoutParams(new LinearLayout.LayoutParams(imgView.getWidth(),imgView.getHeight()+10));

                break;
            case 'ם':
                imgView.setBackgroundResource(R.drawable.final_m);

                break;
            case 'ן':
                imgView.setBackgroundResource(R.drawable.final_nun);
                imgView.setLayoutParams(new LinearLayout.LayoutParams(imgView.getWidth(),imgView.getHeight()+10));
                break;
            case 'ף':
                imgView.setLayoutParams(new LinearLayout.LayoutParams(imgView.getWidth(),imgView.getHeight()+10));
                imgView.setBackgroundResource(R.drawable.final_pey);
                break;
            case 'ץ':
                imgView.setLayoutParams(new LinearLayout.LayoutParams(imgView.getWidth(),imgView.getHeight()+10));
                imgView.setBackgroundResource(R.drawable.final_zadik);
                break;
        }
        points+=10;
        txtPoints.setText(Integer.valueOf(points).toString());
    }
    private int checkLetter(char ch)
    {
        int ind = 0;
        int val = str.indexOf(ch,ind);
        int count = 0;

        int valSpace = str.indexOf(' ');
        if(val!=-1) {
            //Reveals the letter
            if(valSpace!=-1)
            {
                applyLetter(val,ch);
            }
            else
                applyLetter(val,ch);

            //Update values
            ind = val + 1;
            val = str.indexOf(ch,ind);

            count+=1;

            while(val!=-1 ) {
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


    //=== ANIMATIONS SECTION ===//
    private void startAnimationSwinging()
    {
        ImageView img = findViewById(R.id.hangman_img);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.swinging);

        img.startAnimation(anim);
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

    private void playSoundWrong()
    {
        MediaPlayer mp;

        mp = MediaPlayer.create(this, R.raw.buzzer_game);
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

    private void playSoundLoser()
    {
        MediaPlayer mp;

        mp = MediaPlayer.create(this, R.raw.trumpet_sad);
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
        int found = checkLetter('א');

        if(found>0)
        {
            Button btn = findViewById(R.id.letter_alef);
            playSoundButton();

            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }
        else
        {
            //WRONG GUESS

            Button btn = findViewById(R.id.letter_alef);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);


            wrongGuess();




        }

    }

    public void onClickLetter_bet(View view) {
        startAnimationButton(R.id.letter_bet);
        int found = checkLetter('ב');


        if(found>0)
        {
            playSoundButton();

            Button btn = findViewById(R.id.letter_bet);
            btn.setBackgroundResource(R.drawable.button_letter_dis);

            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();


        }
        else
        {
            //WRONG GUESS

            Button btn = findViewById(R.id.letter_bet);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);


            wrongGuess();

        }


    }

    public void onClickLetter_gimel(View view) {
        startAnimationButton(R.id.letter_gimel);

        int found = checkLetter('ג');

        if(found>0)
        {
            playSoundButton();
            Button btn = findViewById(R.id.letter_gimel);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();

        }
        else
        {
            //WRONG GUESS

            Button btn = findViewById(R.id.letter_gimel);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();



        }

    }

    public void onClickLetter_dalet(View view) {
        startAnimationButton(R.id.letter_dalet);
        int found = checkLetter('ד');

        if(found>0)
        {
            playSoundButton();

            Button btn = findViewById(R.id.letter_dalet);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }
        else
        {
            //WRONG GUESS

            Button btn = findViewById(R.id.letter_dalet);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();




        }
    }

    public void onClickLetter_hey(View view) {
        startAnimationButton(R.id.letter_hey);

        int found = checkLetter('ה');

        if(found>0)
        {
            playSoundButton();

            Button btn = findViewById(R.id.letter_hey);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();

        }
        else
        {
            //WRONG GUESS

            Button btn = findViewById(R.id.letter_hey);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);

            btn.setClickable(false);

            wrongGuess();



        }

    }

    public void onClickLetter_vav(View view) {
        startAnimationButton(R.id.letter_vav);

        int found = checkLetter('ו');

        if(found>0)
        {
            playSoundButton();
            Button btn = findViewById(R.id.letter_vav);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();

        }
        else
        {
            //WRONG GUESS

            Button btn = findViewById(R.id.letter_vav);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();

        }

    }

    public void onClickLetter_z(View view) {
        startAnimationButton(R.id.letter_z);
        int found = checkLetter('ז');

        if(found>0)
        {
            playSoundButton();


            Button btn = findViewById(R.id.letter_z);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();

        }
        else
        {
            //WRONG GUESS
            Button btn = findViewById(R.id.letter_z);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();

        }

    }

    public void onClickLetter_het(View view) {
        startAnimationButton(R.id.letter_het);
        int found = checkLetter('ח');

        if(found>0)
        {
            playSoundButton();


            Button btn = findViewById(R.id.letter_het);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }
        else
        {
            //WRONG GUESS
            Button btn = findViewById(R.id.letter_het);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);


            wrongGuess();



        }

    }

    public void onClickLetter_tet(View view) {
        startAnimationButton(R.id.letter_tet);

        int found = checkLetter('ט');

        if(found>0)
        {
            playSoundButton();


            Button btn = findViewById(R.id.letter_tet);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }
        else
        {
            //WRONG GUESS
            Button btn = findViewById(R.id.letter_tet);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);
            wrongGuess();


        }

    }

    public void onClickLetter_yod(View view) {
        startAnimationButton(R.id.letter_yod);

        int found = checkLetter('י');

        if(found>0)
        {
            playSoundButton();


            Button btn = findViewById(R.id.letter_yod);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }
        else
        {
            //WRONG GUESS
            Button btn = findViewById(R.id.letter_yod);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();

        }

    }

    public void onClickLetter_kaf(View view) {
        startAnimationButton(R.id.letter_kaf);

        Boolean  buttonPlayed =false;

        //Regular 'כ'
        int found = checkLetter('כ');
        if(found>0)
        {

            playSoundButton();
            buttonPlayed = true;

            Button btn = findViewById(R.id.letter_kaf);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }


        //Final 'כ'
        int foundFinal = checkLetter('ך');
        if(found>0)
        {
            if(!buttonPlayed)
            {
                playSoundButton();

            }
            Button btn = findViewById(R.id.letter_kaf);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=foundFinal;

            endGameCheck();
        }
        else if(found==0)
        {
            //WRONG GUESS
            Button btn = findViewById(R.id.letter_kaf);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();


        }


    }

    public void onClickLetter_lamed(View view) {
        startAnimationButton(R.id.letter_lamed);

        int found = checkLetter('ל');

        if(found>0)
        {
            playSoundButton();

            Button btn = findViewById(R.id.letter_lamed);
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
            Button btn = findViewById(R.id.letter_lamed);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();

        }


    }

    public void onClickLetter_mem(View view) {
        startAnimationButton(R.id.letter_mem);
        //todo: deal with final letter.
        int found = checkLetter('מ');
        Boolean  buttonPlayed = false;
        if(found>0)
        {
            playSoundButton();
            buttonPlayed= true;




            Button btn = findViewById(R.id.letter_mem);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }


        int foundFinal = checkLetter('ם');
        if(foundFinal>0)
        {
            if(!buttonPlayed)
            {
                playSoundButton();
            }

            Button btn = findViewById(R.id.letter_mem);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=foundFinal;

            endGameCheck();
        }
        else if(found==0)
        {
            //WRONG GUESS
            Button btn = findViewById(R.id.letter_mem);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();


        }
    }

    public void onClickLetter_nun(View view) {
        startAnimationButton(R.id.letter_nun);

        int found = checkLetter('נ');
        Boolean buttonPlayed = false;
        if(found>0)
        {
            playSoundButton();
            buttonPlayed = true;
            Button btn = findViewById(R.id.letter_nun);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }

        int foundFinal = checkLetter('ן');

        if(foundFinal>0)
        {
            if(!buttonPlayed)
            {
                playSoundButton();
            }
            Button btn = findViewById(R.id.letter_nun);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=foundFinal;

            endGameCheck();
        }
        else if(found==0)
        {
            //WRONG GUESS

            Button btn = findViewById(R.id.letter_nun);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);
            wrongGuess();


        }


        //todo: deal with final letter.


    }

    public void onClickLetter_samech(View view) {
        startAnimationButton(R.id.letter_samech);

        int found = checkLetter('ס');

        if(found>0)
        {
            playSoundButton();

            Button btn = findViewById(R.id.letter_samech);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }
        else
        {
            //WRONG GUESS
            Button btn = findViewById(R.id.letter_samech);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();

        }

    }

    public void onClickLetter_ain(View view) {
        startAnimationButton(R.id.letter_ain);

        int found = checkLetter('ע');

        if(found>0)
        {
            playSoundButton();

            Button btn = findViewById(R.id.letter_ain);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }
        else
        {
            //WRONG GUESS
            Button btn = findViewById(R.id.letter_ain);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();


        }

    }

    public void onClickLetter_pey(View view) {
        startAnimationButton(R.id.letter_pey);
        int found = checkLetter('פ');
        Boolean buttonPlayed = false;
        if(found>0)
        {
            playSoundButton();
            buttonPlayed = true;

            Button btn = findViewById(R.id.letter_pey);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }

        int foundFinal = checkLetter('ף');
        if(foundFinal>0)
        {
            if(!buttonPlayed)
            {
                playSoundButton();
            }
            Button btn = findViewById(R.id.letter_pey);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=foundFinal;

            endGameCheck();
        }
        else if(found==0)
        {
            //WRONG GUESS
            Button btn = findViewById(R.id.letter_pey);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();


        }

        //todo: deal with final letter.


    }

    public void onClickLetter_zadik(View view) {
        startAnimationButton(R.id.letter_zadik);

        //todo: deal with final letter.
        int found = checkLetter('צ');
        Boolean  buttonPlayed  = false;
        if(found>0)
        {
            //Make sure activation of sound doesnt happening again
            playSoundButton();
            buttonPlayed  = true;


            Button btn = findViewById(R.id.letter_zadik);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }

        int foundFinal = checkLetter('ץ');
        if(foundFinal>0)
        {
            if(!buttonPlayed)
            {
                playSoundButton();

            }
            Button btn = findViewById(R.id.letter_zadik);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=foundFinal;

            endGameCheck();
        }
        else if(found==0)
        {
            //WRONG GUESS
            Button btn = findViewById(R.id.letter_zadik);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();

        }



    }

    public void onClickLetter_kuf(View view) {
        startAnimationButton(R.id.letter_kuf);
        int found = checkLetter('ק');

        if(found>0)
        {
            playSoundButton();


            Button btn = findViewById(R.id.letter_kuf);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }
        else
        {
            //WRONG GUESS
            Button btn = findViewById(R.id.letter_kuf);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();


        }

    }

    public void onClickLetter_resh(View view) {
        startAnimationButton(R.id.letter_resh);

        int found = checkLetter('ר');

        if(found>0)
        {
            playSoundButton();

            Button btn = findViewById(R.id.letter_resh);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }
        else
        {
            //WRONG GUESS
            Button btn = findViewById(R.id.letter_resh);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();

        }

    }

    public void onClickLetter_shin(View view) {
        startAnimationButton(R.id.letter_shin);

        int found = checkLetter('ש');

        if(found>0)
        {
            playSoundButton();

            Button btn = findViewById(R.id.letter_shin);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }
        else
        {
            //WRONG GUESS
            Button btn = findViewById(R.id.letter_shin);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();


        }
    }

    public void onClickLetter_taf(View view) {

        startAnimationButton(R.id.letter_taf);

        int found = checkLetter('ת');

        if(found>0)
        {
            playSoundButton();

            Button btn = findViewById(R.id.letter_taf);
            btn.setBackgroundResource(R.drawable.button_letter_dis);
            btn.setVisibility(View.INVISIBLE);

            //Adds number of letters that were found
            foundLetters+=found;

            endGameCheck();
        }
        else
        {
            //WRONG GUESS
            Button btn = findViewById(R.id.letter_taf);
            btn.setBackgroundResource(R.drawable.button_letter_wrong);
            btn.setClickable(false);

            wrongGuess();

        }
    }



}
