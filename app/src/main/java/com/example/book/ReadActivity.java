package com.example.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class ReadActivity extends AppCompatActivity {
    EditText page;
    Button minus,plus;
    DatePicker dp;
    DatabaseReference reff;

    Page p;

    String defaultpage="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        page = findViewById(R.id.page);
        page.setText(defaultpage);

        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        dp = findViewById(R.id.dp2);

        p = new Page();

        Calendar now = Calendar.getInstance();
        dp.init(now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH),null);

        String personId = getIntent().getStringExtra("personid");

        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(personId).child("Pages");


        minus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String datee="";
                String date2;

                if(dp.getMonth()+1<10){
                    date2= String.valueOf(dp.getDayOfMonth())+"0"+String.valueOf(dp.getMonth()+1)+String.valueOf(dp.getYear());
                    datee= String.valueOf(dp.getDayOfMonth())+".0"+String.valueOf(dp.getMonth()+1)+"."+String.valueOf(dp.getYear());
                }
                else{
                    date2= String.valueOf(dp.getDayOfMonth())+String.valueOf(dp.getMonth()+1)+String.valueOf(dp.getYear());
                    datee= String.valueOf(dp.getDayOfMonth())+"."+String.valueOf(dp.getMonth()+1)+"."+String.valueOf(dp.getYear());
                }
                p.setDate(datee);
                Integer value = Integer.parseInt(page.getText().toString())-1;
                page.setText(value.toString());
                p.setPage(page.getText().toString());


                reff.child(String.valueOf(date2)).setValue(p);
            }
        });

        plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String datee="";
                String date2;

                if(dp.getMonth()+1<10){
                    date2= String.valueOf(dp.getDayOfMonth())+"0"+String.valueOf(dp.getMonth()+1)+String.valueOf(dp.getYear());
                    datee= String.valueOf(dp.getDayOfMonth())+".0"+String.valueOf(dp.getMonth()+1)+"."+String.valueOf(dp.getYear());
                }
                else{
                    date2= String.valueOf(dp.getDayOfMonth())+String.valueOf(dp.getMonth()+1)+String.valueOf(dp.getYear());
                    datee= String.valueOf(dp.getDayOfMonth())+"."+String.valueOf(dp.getMonth()+1)+"."+String.valueOf(dp.getYear());
                }
                p.setDate(datee);
                Integer value = Integer.parseInt(page.getText().toString())+1;
                page.setText(value.toString());
                p.setPage(page.getText().toString());
                reff.child(String.valueOf(date2)).setValue(p);
            }
        });

       /* page.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String datee="";
                String date2;

                if(dp.getMonth()+1<10){
                    date2= String.valueOf(dp.getDayOfMonth())+"0"+String.valueOf(dp.getMonth()+1)+String.valueOf(dp.getYear());
                    datee= String.valueOf(dp.getDayOfMonth())+".0"+String.valueOf(dp.getMonth()+1)+"."+String.valueOf(dp.getYear());
                }
                else{
                    date2= String.valueOf(dp.getDayOfMonth())+String.valueOf(dp.getMonth()+1)+String.valueOf(dp.getYear());
                    datee= String.valueOf(dp.getDayOfMonth())+"."+String.valueOf(dp.getMonth()+1)+"."+String.valueOf(dp.getYear());
                }
                p.setDate(datee);
                p.setPage(page.getText().toString());
                reff.child(String.valueOf(date2)).setValue(p);
            }
        });*/

    }
}