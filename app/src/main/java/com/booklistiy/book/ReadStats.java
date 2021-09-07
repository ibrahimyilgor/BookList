package com.booklistiy.book;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReadStats extends AppCompatActivity {

    private static final String TAG = "";
    BarChart barchart;
    DatabaseReference reff;
    ImageButton b4,b7,b8;
    Button less,more;
    TextView weekly;
    TextView monthly;
    TextView yearly;
    TextView datetext;

    String currentchart = "week";

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_stats);

        b4 = findViewById(R.id.button7);
        b7 = findViewById(R.id.button4);
        b8 = findViewById(R.id.button8);

        less = findViewById(R.id.less);
        more = findViewById(R.id.more);

        weekly = findViewById(R.id.weekly);
        monthly = findViewById(R.id.monthly);
        yearly = findViewById(R.id.yearly);

        datetext =  findViewById(R.id.datetext);

        barchart = findViewById(R.id.barchart);

        String personId = getIntent().getStringExtra("personid");
        reff = FirebaseDatabase.getInstance().getReference().child("Users").child(personId).child("Pages");

        Calendar c = Calendar.getInstance();

        settime(c);

        b4.setOnClickListener(view -> showweek(c));
        b7.setOnClickListener(view -> showmonth(c));
        b8.setOnClickListener(view -> showyear(c));

        less.setOnClickListener(view -> lessfunc(c));
        more.setOnClickListener(view -> morefunc(c));

        showweek(c);
    }

    private void showweek(Calendar calendar) {
        reff.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SimpleDateFormat")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentchart = "week";
                List<BarEntry> data = new ArrayList<>();

                String mo = getResources().getString(R.string.mo);
                String tu = getResources().getString(R.string.tu);
                String we = getResources().getString(R.string.we);
                String th = getResources().getString(R.string.th);
                String fr = getResources().getString(R.string.fr);
                String sa = getResources().getString(R.string.sa);
                String su = getResources().getString(R.string.su);

                String[] dayss = { mo,tu,we,th,fr,sa,su};
                Calendar temp = (Calendar) calendar.clone();
                temp.set(temp.DAY_OF_WEEK, temp.MONDAY);

                // Print dates of the current week starting on Sunday
                @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("ddMMyyyy");
                int x = 0;
                for ( int i = x; i < 7; i++) {
                    data.add(new BarEntry(i,0));
                    System.out.println(df.format(temp.getTime()));
                    String s =df.format(temp.getTime());
                    temp.add(temp.DATE, 1);
                    if (dataSnapshot.hasChild(s)) {
                        //Key exists
                        data.set(i, new BarEntry(i, (float) Double.parseDouble(dataSnapshot.child(s).child("page").getValue().toString())));
                    } else {
                        //data.add(new BarEntry(finalI,0));
                        //data.set(i, new BarEntry(i, 0));
                    }
                }
                BarDataSet barDataSet = new BarDataSet(data, "Days of week");
                barDataSet.setColors(Color.rgb(214,40,40));
                barDataSet.setValueTextColor(Color.WHITE);
                barDataSet.setValueTextSize(16f);
                barDataSet.setDrawValues(true);

                BarData bardata = new BarData(barDataSet);

                bardata.setValueFormatter(new ValueFormatter() {  // MAKES VALUES INTEGER
                    @Override
                    public String  getFormattedValue(float value) {
                        if (value == 0){
                            return "";
                        }
                        else{
                            return String.valueOf((int) value);
                        }
                    }
                });

                barchart.setFitBars(true);
                barchart.setData(bardata);
                barchart.getDescription().setText("");
                barchart.setBackgroundColor(Color.rgb(17, 45, 78));
                barchart.animateY(2000);
                barchart.getAxisRight().setEnabled(false);
                barchart.getAxisLeft().setEnabled(false);
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

    private void showmonth(Calendar calendar) {

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentchart = "month";
                List<BarEntry> data = new ArrayList<>();

                String[] days = {};
                Calendar temp = (Calendar) calendar.clone();
                temp.set(temp.DAY_OF_MONTH, 1);

                // Print dates of the current week starting on Sunday
                @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("ddMMyyyy");
                int x = 1;
                int max_day_of_month = temp.getActualMaximum(temp.DAY_OF_MONTH);
                for ( int i = x; i <= max_day_of_month; i++) {
                    data.add(new BarEntry(i-1,0));
                    System.out.println(df.format(temp.getTime()));
                   // days.add(Integer.toString(i+1));
                    String s =df.format(temp.getTime());
                    temp.add(temp.DATE, 1);
                    if (dataSnapshot.hasChild(s)) {
                        //Key exists
                        data.set(i-1, new BarEntry(i-1, (float) Double.parseDouble(dataSnapshot.child(s).child("page").getValue().toString())));
                    } else {
                        //data.add(new BarEntry(finalI,0));
                        data.set(i-1, new BarEntry(i-1, 0));
                    }
                }
                BarDataSet barDataSet = new BarDataSet(data, "Days of month");
                barDataSet.setColors(Color.rgb(214,40,40));
                barDataSet.setValueTextColor(Color.WHITE);
                barDataSet.setValueTextSize(16f);
                barDataSet.setDrawValues(true);

                BarData bardata = new BarData(barDataSet);

                bardata.setValueFormatter(new ValueFormatter() {  // MAKES VALUES INTEGER
                    @Override
                    public String  getFormattedValue(float value) {
                        return String.valueOf((int) value);
                    }
                });

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

                barchart.getXAxis().setLabelCount(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }

    private void showyear(Calendar calendar) {

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentchart = "year";
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
                String nov = getResources().getString(R.string.nv);
                String dec = getResources().getString(R.string.de);

                String[] months = {jan,feb,mar,apr,may,jun,jul,aug,sep,oct,nov,dec};
                Calendar temp = (Calendar) calendar.clone();
                temp.set(temp.DAY_OF_YEAR, 1);

                Integer[] pages = {0,0,0,0,0,0,0,0,0,0,0,0};

                // Print dates of the current week starting on Sunday
                @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("ddMMyyyy");
                int x = 1;
                int max_day_of_year = temp.getActualMaximum(temp.DAY_OF_YEAR);
                for ( int i = x; i <= max_day_of_year; i++) {
                    String s =df.format(temp.getTime());

                    if (dataSnapshot.hasChild(s)) {
                        //Key exists
                        pages[temp.get(temp.MONTH)] += (Integer) Integer.parseInt(dataSnapshot.child(s).child("page").getValue().toString());
                       // data.set(finalI, new BarEntry(finalI, (float) Double.parseDouble(dataSnapshot.child(s).child("page").getValue().toString())));
                    } else {
                        //data.add(new BarEntry(finalI,0));
                        //data.set(finalI, new BarEntry(finalI, 0));
                    }
                    temp.add(temp.DATE, 1);
                }
                for(int y=0; y<12;y++) {
                    data.add(new BarEntry(y,pages[y]));
                }
                BarDataSet barDataSet = new BarDataSet(data, "Days of month");
                barDataSet.setColors(Color.rgb(214,40,40));
                barDataSet.setValueTextColor(Color.WHITE);
                barDataSet.setValueTextSize(16f);
                barDataSet.setDrawValues(true);

                BarData bardata = new BarData(barDataSet);

                bardata.setValueFormatter(new ValueFormatter() {  // MAKES VALUES INTEGER
                    @Override
                    public String  getFormattedValue(float value) {
                        if (value == 0){
                            return "";
                        }
                        else{
                            return String.valueOf((int) value);
                        }
                    }
                });

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

                barchart.getXAxis().setLabelCount(calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, databaseError.getMessage());
            }
        });
    }
    private void lessfunc(Calendar calendar) {
        if(currentchart.equals("week")){
            calendar.add(calendar.DAY_OF_MONTH, -7);
            settime(calendar);
            showweek(calendar);
        }
        else if(currentchart.equals("month")){
            calendar.add(calendar.MONTH, -1);
            settime(calendar);
            showmonth(calendar);
        }
        else if(currentchart.equals("year")){
            calendar.add(calendar.YEAR, -1);
            settime(calendar);
            showyear(calendar);
        }
    }

    private void morefunc(Calendar calendar) {
        if(currentchart.equals("week")){
            calendar.add(calendar.DAY_OF_MONTH, 7);
            settime(calendar);
            showweek(calendar);
        }
        else if(currentchart.equals("month")){
            calendar.add(calendar.MONTH, 1);
            settime(calendar);
            showmonth(calendar);
        }
        else if(currentchart.equals("year")){
            calendar.add(calendar.YEAR, 1);
            settime(calendar);
            showyear(calendar);
        }
    }

    private void settime(Calendar calendar){
        int dayint = calendar.get(calendar.DAY_OF_MONTH);
        String day;
        if(dayint < 10){
            day = "0"+String.valueOf(dayint);
        }
        else{
            day = String.valueOf(dayint);
        }
        int monthint = calendar.get(calendar.MONTH)+1;
        String month;
        if(monthint < 10){
            month = "0"+String.valueOf(monthint);
        }
        else{
            month = String.valueOf(monthint);
        }

        String year = String.valueOf(calendar.get(calendar.YEAR));

        datetext.setText(day+"."+month+"."+year);
    }
}