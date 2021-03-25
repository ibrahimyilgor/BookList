package com.booklistiy.book;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ReadActivity extends AppCompatActivity {
    EditText page;
    ImageButton minus,plus,save;
    DatePicker dp;
    DatabaseReference reff,reff2,reff3;
    TextView t2,t3,t4;
    AdView ad2;
    Page p;
    String defaultpage="0";
    BarChart barchart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        page = findViewById(R.id.page);
        page.setText(defaultpage);

        minus = findViewById(R.id.minus);
        plus = findViewById(R.id.plus);
        save = findViewById(R.id.save);
        dp = findViewById(R.id.dp2);

        t2 = findViewById(R.id.textView2);
        t3 = findViewById(R.id.textView3);
        t4 = findViewById(R.id.textView4);

        barchart = findViewById(R.id.barchart);

        ArrayList<BarEntry> visitor = new ArrayList<>();
        visitor.add(new BarEntry(1,10));
        visitor.add(new BarEntry(2,30));
        visitor.add(new BarEntry(3,20));
        visitor.add(new BarEntry(4,80));
        visitor.add(new BarEntry(5,40));
        visitor.add(new BarEntry(6,60));
        visitor.add(new BarEntry(7,50));

        BarDataSet barDataSet = new BarDataSet(visitor,"Visitors");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData bardata= new BarData(barDataSet);

        barchart.setFitBars(true);
        barchart.setData(bardata);
        barchart.getDescription().setText("Example");
        barchart.animateY(2000);


        ad2 = findViewById(R.id.ad2);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("ca-app-pub-4943937138677405/2497004317");

        ad2.loadAd(adRequest);

        p = new Page();

        String personId = getIntent().getStringExtra("personid");

        Calendar now = Calendar.getInstance();
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(personId).child("Pages");

        getPage(personId);

        dp.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date;
                String day;
                if(dp.getDayOfMonth()<10){
                    day = "0"+ String.valueOf(dp.getDayOfMonth());
                }
                else{
                    day = String.valueOf(dp.getDayOfMonth());
                }
                monthOfYear+=1;
                if(monthOfYear+1<10){
                    date= day+"0"+String.valueOf(monthOfYear)+String.valueOf(year);
                }
                else{
                    date= day+String.valueOf(monthOfYear)+String.valueOf(year);
                }


                reff2 = FirebaseDatabase.getInstance().getReference().child("Users").child(personId).child("Pages");
                reff2.orderByKey().equalTo(date).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if(dataSnapshot.exists()) {
                            //Key exists
                            page.setText(dataSnapshot.child(date).child("page").getValue().toString());
                        } else {
                            page.setText("0");
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });




        minus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(page.getText().toString().equals("")){
                    page.setText("0");
                }
                Integer value = Integer.parseInt(page.getText().toString())-1;
                if(value<0){
                    value=0;
                }
                page.setText(value.toString());
            }
        });

        plus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(page.getText().toString().equals("")){
                    page.setText("0");
                }
                Integer value = Integer.parseInt(page.getText().toString())+1;
                page.setText(value.toString());
            }
        });

        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String datee="";
                String date2;
                String day;
                if(dp.getDayOfMonth()<10){
                    day = "0"+ String.valueOf(dp.getDayOfMonth());
                }
                else{
                    day = String.valueOf(dp.getDayOfMonth());
                }

                if(dp.getMonth()+1<10){
                    date2= day+"0"+String.valueOf(dp.getMonth()+1)+String.valueOf(dp.getYear());
                    datee= day+".0"+String.valueOf(dp.getMonth()+1)+"."+String.valueOf(dp.getYear());
                }
                else{
                    date2= day+String.valueOf(dp.getMonth()+1)+String.valueOf(dp.getYear());
                    datee= day+"."+String.valueOf(dp.getMonth()+1)+"."+String.valueOf(dp.getYear());
                }
                if(page.getText().toString().equals("")){
                    page.setText("0");
                }

                p.setDate(datee);
                p.setPage(page.getText().toString());

                reff.child(String.valueOf(date2)).setValue(p);
                Toast.makeText(ReadActivity.this, "Page number added successfully.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getPage(String personId){
        String date;
        String day;
        Integer monthOfYear = dp.getMonth();
        Integer year = dp.getYear();
        if(dp.getDayOfMonth()<10){
            day = "0"+ String.valueOf(dp.getDayOfMonth());
        }
        else{
            day = String.valueOf(dp.getDayOfMonth());
        }
        monthOfYear+=1;
        if(monthOfYear+1<10){
            date= day+"0"+String.valueOf(monthOfYear)+String.valueOf(year);
        }
        else{
            date= day+String.valueOf(monthOfYear)+String.valueOf(year);
        }

        reff3 = FirebaseDatabase.getInstance().getReference().child("Users").child(personId).child("Pages");
        reff3.orderByKey().equalTo(date).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()) {
                    //Key exists
                    page.setText(dataSnapshot.child(date).child("page").getValue().toString());
                } else {
                    page.setText("0");
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}