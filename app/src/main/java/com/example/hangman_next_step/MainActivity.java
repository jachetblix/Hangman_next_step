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

    Button button_2;
    private Intent intentRecord;
    //TODO: Transfer this string to next screens.
    private String playerName;


    final String TABLE_NAME = "score_table";
    final String CREATE_TABLE_CMD="CREATE TABLE IF NOT EXISTS " + TABLE_NAME +"(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT , score INTEGER);";
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //CREATE DB FOR THE APP
        database = openOrCreateDatabase("database2.sql", MODE_PRIVATE, null);

        database.execSQL(CREATE_TABLE_CMD);

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
        // show enter player name layout
        LinearLayout layout = findViewById(R.id.layout_name);
        layout.setVisibility(View.VISIBLE);




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

        Animation anim = AnimationUtils.loadAnimation(this,R.anim.scale_up);
        Button btnStartGame = findViewById(R.id.btn_start_game);
        btnStartGame.startAnimation(anim);
        startAnimationButton(R.id.btn_start_game);
        playSoundButton();

        //get player name to transfer
        EditText editTxt = findViewById(R.id.edit_txt_name);
        playerName =  editTxt.getText().toString();

        Intent intent = new Intent(this, Second_Screen.class);
        intent.putExtra("playerName", playerName);

        startActivity(intent);

    }
}
//COMMIT CHECKING