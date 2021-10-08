package com.example.hangman_next_step;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

public class Record_Screen extends AppCompatActivity {
    //TODO: localisation of TABLE_NAME
    final String TABLE_NAME = "score_table";
    final String CREATE_TABLE_CMD="CREATE TABLE IF NOT EXISTS " + TABLE_NAME +"(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);";
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_activity);

        database = openOrCreateDatabase("database2.sql", MODE_PRIVATE, null);

        //database.execSQL(CREATE_TABLE_CMD);

        final ListView listview = findViewById(R.id.recordListView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listview.setAdapter(adapter);

        Cursor cursor =  database.query(TABLE_NAME, null,null,null,null,null,null,null);

        int nameIndex = cursor.getColumnIndex("name");
        int scoreIndex = cursor.getColumnIndex("score");

        while(cursor.moveToNext())
        {
            String name = cursor.getString(nameIndex) + " " + cursor.getString(scoreIndex);
            adapter.add(name);
        }
        adapter.notifyDataSetChanged();
    }
}
