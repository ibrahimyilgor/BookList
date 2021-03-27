package com.booklistiy.book;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReadStats extends AppCompatActivity {

    BarChart barchart;
    DatabaseReference reff;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_stats);

        barchart = findViewById(R.id.barchart);

        String personId = getIntent().getStringExtra("personid");

        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(personId).child("Pages");

        List<BarEntry> data = new ArrayList<>();

        String[] days = {"Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};

        // Get calendar set to current date and time
        Calendar c = Calendar.getInstance();

        // Set the calendar to Sunday of the current week
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);

        // Print dates of the current week starting on Sunday
        DateFormat df = new SimpleDateFormat("ddMMyyyy");
        int x = 0;
        for ( int i = x; i < 7; i++) {
            data.add(new BarEntry(i,i*5+1));
            System.out.println(df.format(c.getTime()));
            String s =df.format(c.getTime());
            c.add(Calendar.DATE, 1);

            //visitor.add(new BarEntry(1,(reff.child(df.format(c.getTime())).child("page").getValue().toString())));
           // page.setText(reff.child(df.format(c.getTime()).toString()).child("page").getValue().toString());

            int finalI = i;

            reff.child(s).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(dataSnapshot.exists()) {
                        //Key exists
                        data.set(finalI , new BarEntry(finalI, (float) Double.parseDouble(dataSnapshot.child("page").getValue().toString())));
                    } else {
                        //data.add(new BarEntry(finalI,0));
                        data.set(finalI , new BarEntry(finalI,0));
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }

/*
        visitor.add(new BarEntry(1,10));
        visitor.add(new BarEntry(2,30));
        visitor.add(new BarEntry(3,20));
        visitor.add(new BarEntry(4,80));
        visitor.add(new BarEntry(5,40));
        visitor.add(new BarEntry(6,60));
        visitor.add(new BarEntry(7,60));
*/


        BarDataSet barDataSet = new BarDataSet(data, "Days of week");
        barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(16f);

        BarData bardata = new BarData(barDataSet);

            // LIMIT
    /* int maxCapacity = 60;
    LimitLine ll = new LimitLine(maxCapacity, "");
    ll.setLineWidth(4f);
    ll.setTextSize(12f);
    ll.setTextColor(Color.RED);
    barchart.getAxisLeft().addLimitLine(ll);*/

        barchart.setFitBars(true);
        barchart.setData(bardata);
        barchart.getDescription().setText("");
        barchart.setBackgroundColor(Color.rgb(17, 45, 78));
        barchart.animateY(2000);
        barchart.getAxisLeft().setDrawGridLines(false);
        barchart.getXAxis().setDrawGridLines(false);
        barchart.setScaleEnabled(false);
        barchart.getAxisLeft().setTextColor(Color.rgb(255, 255, 255));
        barchart.getAxisRight().setTextColor(Color.rgb(255, 255, 255));
        barchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(days));
        barchart.getLegend().setTextColor(Color.rgb(255, 255, 255));
        barchart.getXAxis().setTextSize(20);
        barchart.getXAxis().setTextColor(Color.rgb(255, 255, 255));
        barchart.getLegend().setTextSize(20);
        barchart.getXAxis().setYOffset(-20);
    }
}