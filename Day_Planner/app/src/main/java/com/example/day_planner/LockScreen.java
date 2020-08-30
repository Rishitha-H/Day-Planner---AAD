package com.example.day_planner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import io.paperdb.Paper;

public class LockScreen extends AppCompatActivity{
    PatternLockView patternLockView;
    String save_pattern_key="pattern_code";
    String fpattern="";
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);
        final String savepattern=Paper.book().read(save_pattern_key);
        if(savepattern!=null && !savepattern.equals("null")){
            setContentView(R.layout.patternscreen);


            patternLockView=(PatternLockView)findViewById(R.id.pattern_lock_view);
            patternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    fpattern=PatternLockUtils.patternToString(patternLockView,pattern);
                    if(fpattern.equals(savepattern)){
                        patternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);

                        Toast.makeText(LockScreen.this, "Welcome :)", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LockScreen.this,mainactivity.class));
                    }else{
                        patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                        Toast.makeText(LockScreen.this, "Incorrect Pattern, Please try again", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCleared() {

                }
            });

        }
        else{
            setContentView(R.layout.activity_lock_screen);
            patternLockView=(PatternLockView)findViewById(R.id.pattern_lock_view);
            patternLockView.addPatternLockListener(new PatternLockViewListener() {


                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                   fpattern=PatternLockUtils.patternToString(patternLockView,pattern);
                }

                @Override
                public void onCleared() {

                }



            });

            b=findViewById(R.id.set);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Paper.book().write(save_pattern_key,fpattern);
                    Toast.makeText(LockScreen.this, "Pattern Saved", Toast.LENGTH_SHORT).show();

                    startActivity(new Intent(LockScreen.this,mainactivity.class));

                }
            });

             }
        }
    }


