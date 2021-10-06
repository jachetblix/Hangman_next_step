package com.example.hangman_next_step;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity{

    // CREATE THE DB HERE SO WE CAN GET TO IT FROM ALL OVER THE APP
    //final String TABLE_NAM ="record_table";
   public final String TABLE_NAME ="record_table";
   final String CREATE_TABLE_CM = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +"(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, score INTEGER);";
    public SQLiteDatabase scoreDataBase;

    Button button_2;
    private Intent intentRecord;
    //TODO: Transfer this string to next screens.
    private String playerName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        button_2 = findViewById(R.id.button2);
        scoreDataBase = openOrCreateDatabase("database.db", MODE_PRIVATE, null);
        scoreDataBase.execSQL(CREATE_TABLE_CM);

        //insertion to db
        ContentValues contentvalues = new ContentValues();
        contentvalues.put("name", "roi");
        contentvalues.put("score", 999);
        scoreDataBase.insert(TABLE_NAME, null, contentvalues);
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

        Intent intent = new Intent(this, Record_Screen.class);
        startActivity(intent);




    }

    public void submitName(View view) {
        LinearLayout layout = findViewById(R.id.layout_name);
        EditText editTxt = findViewById(R.id.edit_txt_name);


        playerName =  editTxt.getText().toString();
        layout.setVisibility(View.GONE);

    }
}
//COMMIT CHECKING