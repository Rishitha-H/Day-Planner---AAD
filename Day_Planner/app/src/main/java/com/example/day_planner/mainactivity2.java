package com.example.day_planner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashSet;

public class mainactivity2 extends AppCompatActivity {
    int noteID,titleID,subtitleID;
    ImageView i;
    //Button saven;


    //String tnote,ttitle,tsub;


    public static final int CAMERA_REQUEST_CODE = 20;
    public static final int Gallery_REQUEST_CODE = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainactivity2);
        Button b1=findViewById(R.id.cam);
        Button b2=findViewById(R.id.gal);
        i=findViewById(R.id.image);

        EditText editText = (EditText)findViewById(R.id.note);
        Intent intentx = getIntent();
        noteID = intentx.getIntExtra("noteID", -1);
        if(noteID != -1)
        {
            editText.setText(note_display.notes.get(noteID));

        }

        else
        {
            note_display.notes.add("");                // as initially, the note is empty
            noteID = note_display.notes.size() - 1;
            note_display.arrayAdapter1.notifyDataSetChanged();
        }

        editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                note_display.notes.set(noteID, String.valueOf(s));
                note_display.arrayAdapter1.notifyDataSetChanged();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.day_planner", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<>(note_display.notes);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });




}

    public void Gallery(View view) {
        Intent intent= new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,Gallery_REQUEST_CODE);
    }

    public void Camera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,CAMERA_REQUEST_CODE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CAMERA_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                Bitmap b=(Bitmap) data.getExtras().get("data");
                i.setImageBitmap(b);
            }
        }
        if(requestCode==Gallery_REQUEST_CODE){
            if(resultCode==RESULT_OK){
                Uri u = data.getData();
                i.setImageURI(u);
            }
        }
    }
}