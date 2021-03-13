package com.example.book;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class EditActivity extends AppCompatActivity {
    TextView t11,t12,t13,t14,t15;
    EditText bookname,bookauthor,bookpage,bookprice;
    DatePicker dp;
    Button update,delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        t11 = findViewById(R.id.t11);
        t12 = findViewById(R.id.t12);
        t13 = findViewById(R.id.t13);
        t14 = findViewById(R.id.t14);
        t15 = findViewById(R.id.t15);

        bookname = findViewById(R.id.bookname2);
        bookauthor = findViewById(R.id.bookauthor2);
        bookpage = findViewById(R.id.bookpage2);
        bookprice = findViewById(R.id.bookprice2);

        dp = findViewById(R.id.dp12);

        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);

        String info = getIntent().getStringExtra("info");

        Toast.makeText(EditActivity.this, info, Toast.LENGTH_SHORT).show();
    }
}