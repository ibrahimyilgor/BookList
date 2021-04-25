package com.booklistiy.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
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

    private static final String TAG = "";
    BarChart barchart;
    DatabaseReference reff;
    ImageButton b4,b7,b8;
    TextView weekly,monthly,yearly;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_stats);

        b4 = findViewById(R.id.button4);
        b7 = findViewById(R.id.button7);
        b8 = findViewById(R.id.button8);

        weekly = findViewById(R.id.weekly);
        monthly = findViewById(R.id.monthly);
        yearly = findViewById(R.id.yearly);

        barchart = findViewById(R.id.barchart);

        String personId = getIntent().getStringExtra("personid");
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(personId).child("Pages");

        b4.setOnClickListener(view -> showweek());
        b7.setOnClickListener(view -> showmonth());
        b8.setOnClickListener(view -> showyear());
        showweek();
    }

    private void showweek() {
        reff.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<BarEntry> data = new ArrayList<>();

                String mo = getResources().getString(R.string.mo);
                String tu = getResources().getString(R.string.tu);
                String we = getResources().getString(R.string.we);
                String th = getResources().getString(R.string.th);
                String fr = getResources().getString(R.string.fr);
                String sa = getResources().getString(R.string.sa);
                String su = getResources().getString(R.string.su);

                String[] dayss = { mo,tu,we,th,fr,sa,su};

                Calendar c = Calendar.getInstance();

                // Set the calendar to Sunday of the current week
                c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

                // Print dates of the current week starting on Sunday
                @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("ddMMyyyy");
                int x = 0;
                for ( int i = x; i < 7; i++) {
                    data.add(new BarEntry(i,0));
                    System.out.println(df.format(c.getTime()));
                    String s =df.format(c.getTime());
                    c.add(Calendar.DATE, 1);
                    if (dataSnapshot.hasChild(s)) {
                        //Key exists
                        data.set(i, new BarEntry(i, (float) Double.parseDouble(dataSnapshot.child(s).child("page").getValue().toString())));
                    } else {
                        //data.add(new BarEntry(finalI,0));
                        data.set(i, new BarEntry(i, 0));
                    }
                }
                BarDataSet barDataSet = new BarDataSet(data, "Days of week");
                barDataSet.setColors(Color.rgb(214,40,40));
                barDataSet.setValueTextColor(Color.WHITE);
                barDataSet.setValueTextSize(16f);
                barDataSet.setDrawValues(false);

                BarData bardata = new BarData(barDataSet);

                barchart.setFitBars(true);
                barchart.setData(bardata);
                barchart.getDescription().setText("");
                barchart.setBackgroundColor(Color.rgb(17, 45, 78));
                barchart.animateY(2000);
                barchart.getAxisRight().setEnabled(false);
                barchart.getAxisLeft().setDrawGridLines(true);
                barchart.getAxisLeft().setGridColor(Color.WHITE);
                barchart.getAxisLeft().setDrawAxisLine(false);
                barchart.getXAxis().setDrawGridLines(false);
                barchart.getXAxis().setDrawAxisLine(false);
                //   barchart.getAxisRight().setDrawGridLines(false);
                //     barchart.getAxisRight().setDrawAxisLine(false);
                barchart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                barchart.setScaleEnabled(false);
                barchart.getAxisLeft().setTextColor(Color.rgb(255, 255, 255));
                //   barchart.getAxisRight().setTextColor(Color.rgb(255, 255, 255));
                barchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(dayss));
                barchart.getLegend().setTextColor(Color.rgb(255, 255, 255));
                barchart.getXAxis().setTextSize(20);
                barchart.getXAxis().setTextColor(Color.rgb(255, 255, 255));
                barchart.getLegend().setTextSize(20);
                barchart.getXAxis().setYOffset(-20);

                barchart.getLegend().setEnabled(false);

                barchart.setDragEnabled(false);
                barchart.setScaleEnabled(false);
                barchart.setScaleXEnabled(false);
                barchart.setScaleYEnabled(false);
                barchart.setPinchZoom(false);
                barchart.getAxisLeft().setAxisMinimum(0f);
                barchart.getXAxis().setGranularity(1f);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    private void showmonth() {
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<BarEntry> data = new ArrayList<>();

                String[] days = {};

                Calendar c = Calendar.getInstance();

                // Set the calendar to Sunday of the current week
                c.set(Calendar.DAY_OF_MONTH, 1);

                // Print dates of the current week starting on Sunday
                @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("ddMMyyyy");
                int x = 0;
                for ( int i = x; i < c.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                    data.add(new BarEntry(i,0));
                    System.out.println(df.format(c.getTime()));
                   // days.add(Integer.toString(i+1));
                    String s =df.format(c.getTime());
                    c.add(Calendar.DATE, 1);
                    if (dataSnapshot.hasChild(s)) {
                        //Key exists
                        data.set(i, new BarEntry(i, (float) Double.parseDouble(dataSnapshot.child(s).child("page").getValue().toString())));
                    } else {
                        //data.add(new BarEntry(finalI,0));
                        data.set(i, new BarEntry(i, 0));
                    }
                }
                BarDataSet barDataSet = new BarDataSet(data, "Days of month");
                barDataSet.setColors(Color.rgb(214,40,40));
                barDataSet.setValueTextColor(Color.WHITE);
                barDataSet.setValueTextSize(16f);
                barDataSet.setDrawValues(false);

                BarData bardata = new BarData(barDataSet);

                barchart.setFitBars(true);
                barchart.setData(bardata);
                barchart.getDescription().setText("");
                barchart.setBackgroundColor(Color.rgb(17, 45, 78));
                barchart.animateY(2000);
                barchart.getAxisRight().setEnabled(false);
                barchart.getAxisLeft().setDrawGridLines(true);
                barchart.getAxisLeft().setGridColor(Color.WHITE);
                barchart.getAxisLeft().setDrawAxisLine(false);
                barchart.getXAxis().setDrawGridLines(false);
                barchart.getXAxis().setDrawAxisLine(false);
                //   barchart.getAxisRight().setDrawGridLines(false);
                //     barchart.getAxisRight().setDrawAxisLine(false);
                barchart.getXAxis().setPosition(XAxis.XAxisPosition.TOP);
                barchart.setScaleEnabled(false);
                barchart.getAxisLeft().setTextColor(Color.rgb(255, 255, 255));
                //   barchart.getAxisRight().setTextColor(Color.rgb(255, 255, 255));
                barchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(days));
                barchart.getLegend().setTextColor(Color.rgb(255, 255, 255));
                barchart.getXAxis().setTextSize(10);
                barchart.getXAxis().setTextColor(Color.rgb(255, 255, 255));
                barchart.getLegend().setTextSize(20);
                barchart.getXAxis().setYOffset(-20);

                barchart.getLegend().setEnabled(false);

                barchart.setDragEnabled(false);
                barchart.setScaleEnabled(false);
                barchart.setScaleXEnabled(false);
                barchart.setScaleYEnabled(false);
                barchart.setPinchZoom(false);
                barchart.getAxisLeft().setAxisMinimum(0f);

                barchart.getXAxis().setLabelCount(c.getActualMaximum(Calendar.DAY_OF_MONTH));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    private void showyear() {
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                List<BarEntry> data = new ArrayList<>();

                String jan = getResources().getString(R.string.ja);
                String feb = getResources().getString(R.string.fe);
                String mar = getResources().getString(R.string.ma);
                String apr = getResources().getString(R.string.ap);
                String may = getResources().getString(R.string.ma);
                String jun = getResources().getString(R.string.ju);
                String jul = getResources().getString(R.string.jl);
                String aug = getResources().getString(R.string.au);
                String sep = getResources().getString(R.string.se);
                String oct = getResources().getString(R.string.oc);
                String nov = getResources().getString(R.string.no);
                String dec = getResources().getString(R.string.de);

                String[] months = {jan,feb,mar,apr,may,jun,jul,aug,sep,oct,nov,dec};

                Calendar c = Calendar.getInstance();

                // Set the calendar to Sunday of the current week
                c.set(Calendar.DAY_OF_YEAR, 1);

                Integer[] pages = {0,0,0,0,0,0,0,0,0,0,0,0};

                // Print dates of the current week starting on Sunday
                @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("ddMMyyyy");
                int x = 0;
                for ( int i = x; i < c.getActualMaximum(Calendar.DAY_OF_YEAR); i++) {
                    //data.add(new BarEntry(i,0));
                    System.out.println(df.format(c.getTime()));
                    // days.add(Integer.toString(i+1));
                    String s =df.format(c.getTime());

                    c.add(Calendar.DATE, 1);
                    if (dataSnapshot.hasChild(s)) {
                        //Key exists
                        pages[c.get(Calendar.MONTH)] += (Integer) Integer.parseInt(dataSnapshot.child(s).child("page").getValue().toString());
                       // data.set(finalI, new BarEntry(finalI, (float) Double.parseDouble(dataSnapshot.child(s).child("page").getValue().toString())));
                    } else {
                        //data.add(new BarEntry(finalI,0));
                        //data.set(finalI, new BarEntry(finalI, 0));
                    }
                }
                for(int y=0; y<12;y++) {
                    data.add(new BarEntry(y,pages[y]));
                }
                BarDataSet barDataSet = new BarDataSet(data, "Days of month");
                barDataSet.setColors(Color.rgb(214,40,40));
                barDataSet.setValueTextColor(Color.WHITE);
                barDataSet.setValueTextSize(16f);
                barDataSet.setDrawValues(false);

                BarData bardata = new BarData(barDataSet);

                barchart.setFitBars(true);
                barchart.setData(bardata);
                barchart.getDescription().setText("");
                barchart.setBackgroundColor(Color.rgb(17, 45, 78));
                barchart.animateY(2000);
                barchart.getAxisRight().setEnabled(false);
                barchart.getAxisLeft().setDrawGridLines(true);
                barchart.getAxisLeft().setGridColor(Color.WHITE);
                barchart.getAxisLeft().setDrawAxisLine(false);
                barchart.getXAxis().setDrawGridLines(false);
                barchart.getXAxis().setDrawAxisLine(false);
                //   barchart.getAxisRight().setDrawGridLines(false);
                //     barchart.getAxisRight().setDrawAxisLine(false);
                barchart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                barchart.setScaleEnabled(false);
                barchart.getAxisLeft().setTextColor(Color.rgb(255, 255, 255));
                //   barchart.getAxisRight().setTextColor(Color.rgb(255, 255, 255));
                barchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(months));
                barchart.getLegend().setTextColor(Color.rgb(255, 255, 255));
                barchart.getXAxis().setTextSize(20);
                barchart.getXAxis().setTextColor(Color.rgb(255, 255, 255));
                barchart.getLegend().setTextSize(20);
                barchart.getXAxis().setYOffset(-20);

                barchart.getLegend().setEnabled(false);

                barchart.setDragEnabled(false);
                barchart.setScaleEnabled(false);
                barchart.setScaleXEnabled(false);
                barchart.setScaleYEnabled(false);
                barchart.setPinchZoom(false);
                barchart.getAxisLeft().setAxisMinimum(0f);

                barchart.getXAxis().setLabelCount(c.getActualMaximum(Calendar.DAY_OF_MONTH));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }
}