package com.example.day_planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.ListView;
import java.util.ArrayList;
import java.util.HashSet;

public class note_display extends AppCompatActivity {

    static ArrayList<String> notes = new ArrayList<>();

    static ArrayAdapter<String> arrayAdapter1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.addnote, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.addnote)
        {
            Intent intent = new Intent(getApplicationContext(),mainactivity2.class);
            startActivity(intent);
            return true;
        }

        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AlternativeTheme);
        setContentView(R.layout.activity_note_display);
        ListView listView = (ListView)findViewById(R.id.listView);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.day_planner", Context.MODE_PRIVATE);
        HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet("notes", null);
        if(set == null)
        {
            notes.add("First notes");
        }

        else
        {
            notes = new ArrayList<>(set);
        }

        listView.setAdapter(arrayAdapter1);
        arrayAdapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notes);
        listView.setAdapter(arrayAdapter1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onItemClick(AdapterView<?> parent, View view1, int position1, long id1)
            {
               
                Intent intent1 = new Intent(getApplicationContext(), mainactivity2.class);
                intent1.putExtra("noteID", position1);            //to tell us which row of listView was tapped
                startActivity(intent1);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view1, final int position1, long id1)
            {
                new AlertDialog.Builder(note_display.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete?")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)                        // to remove the selected note once "Yes" is pressed
                            {
                                notes.remove(position1);
                                arrayAdapter1.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.day_planner", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet<>(note_display.notes);

                                //DELETE
                                sharedPreferences.edit().putStringSet("notes", set).apply();

                            }
                        })

                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });

    }
}


