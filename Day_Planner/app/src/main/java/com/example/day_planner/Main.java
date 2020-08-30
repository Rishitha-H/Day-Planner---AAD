package com.example.day_planner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

public class Main extends AppCompatActivity  {
    RecyclerView noteLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final DrawerLayout drawerLayout=findViewById(R.id.drawerlayout );
        findViewById(R.id.menui).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);

            }
        });
        NavigationView navigationView=findViewById(R.id.navigationview);
        navigationView.setItemIconTintList(null);


        Button btn = (Button) findViewById(R.id.btask);

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                startActivity(new Intent(Main.this,task_display.class));
                Toast.makeText(Main.this, "List of tasks", Toast.LENGTH_SHORT).show();

            }
        });
        Button btn2 = (Button) findViewById(R.id.bnotes);

        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main.this,note_display.class));
                Toast.makeText(Main.this, "List of notes", Toast.LENGTH_SHORT).show();

            }
        });

    }




}