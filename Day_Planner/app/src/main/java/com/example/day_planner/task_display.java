package com.example.day_planner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

public class task_display extends AppCompatActivity {


    static ArrayList<String> tasks = new ArrayList<String>();
    static ArrayAdapter<String> arrayAdapter;

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
            Intent intent = new Intent(getApplicationContext(),Task2.class);
            startActivity(intent);
            return true;
        }

        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AlternativeTheme);
        setContentView(R.layout.activity_task_display);
        ListView listView = (ListView)findViewById(R.id.listViewt);
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.day_planner", Context.MODE_PRIVATE);


        HashSet<String> set = (HashSet<String>)sharedPreferences.getStringSet("tasks", null);
        if(set == null)
        {
            tasks.add("Create your first task");
        }

        else
        {
            tasks = new ArrayList<>(set);
        }


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,tasks);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(getApplicationContext(), Task2.class);
                intent.putExtra("notetID", position);            //to tell us which row of listView was tapped
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                new AlertDialog.Builder(task_display.this)                   // we can't use getApplicationContext() here as we want the activity to be the context, not the application
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete?")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)                        // to remove the selected note once "Yes" is pressed
                            {
                                tasks.remove(position);
                                arrayAdapter.notifyDataSetChanged();

                                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.day_planner", Context.MODE_PRIVATE);
                                HashSet<String> set = new HashSet<>(task_display.tasks);
                                //edited
                                sharedPreferences.edit().putStringSet("tasks", set).apply();

                            }
                        })

                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
    }
}