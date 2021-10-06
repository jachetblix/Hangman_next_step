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

    final String TABLE_NAME = "score_table";
    final String CREATE_TABLE_CMD="CREATE TABLE IF NOT EXISTS " + TABLE_NAME +"(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT);";
    SQLiteDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_activity);

        database = openOrCreateDatabase("database.sql", MODE_PRIVATE, null);

        database.execSQL(CREATE_TABLE_CMD);

        final ListView listview = findViewById(R.id.recordListView);
        final EditText nameEt = findViewById(R.id.firstInput);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listview.setAdapter(adapter);

        Button addBtn = findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = nameEt.getText().toString();

                ContentValues contentvalues = new ContentValues();
                contentvalues.put("name", name);
                database.insert(TABLE_NAME,null,contentvalues);
                nameEt.setText("");
            }
        });

        Button showBtn = findViewById(R.id.showBtn);
        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                
                Cursor cursor =  database.query(TABLE_NAME, null,null,null,null,null,null,null);

                int nameIndex = cursor.getColumnIndex("name");

                while(cursor.moveToNext())
                {
                    String name = cursor.getString(nameIndex);
                    adapter.add(name);
                }
                adapter.notifyDataSetChanged();
            }
        });
    }
}
