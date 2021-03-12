package com.example.book;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class ReadActivity extends AppCompatActivity {
    EditText page;
    Button minus,plus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        page = findViewById(R.id.page);
        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
    }
}