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

import java.util.ArrayList;
import java.util.List;

public class ReadStats extends AppCompatActivity {

    BarChart barchart;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_stats);

        barchart = findViewById(R.id.barchart);

        String[] days = {"","Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"};

        ArrayList<BarEntry> visitor = new ArrayList<>();
        visitor.add(new BarEntry(1,10));
        visitor.add(new BarEntry(2,30));
        visitor.add(new BarEntry(3,20));
        visitor.add(new BarEntry(4,80));
        visitor.add(new BarEntry(5,40));
        visitor.add(new BarEntry(6,60));
        visitor.add(new BarEntry(7,60));

        BarDataSet barDataSet = new BarDataSet(visitor,"Days of week");
        barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);
        barDataSet.setValueTextColor(Color.WHITE);
        barDataSet.setValueTextSize(16f);

        BarData bardata= new BarData(barDataSet);

        int maxCapacity = 60;
        LimitLine ll = new LimitLine(maxCapacity, "");
        ll.setLineWidth(4f);
        ll.setTextSize(12f);
        ll.setTextColor(Color.RED);
        barchart.getAxisLeft().addLimitLine(ll);

        barchart.setFitBars(true);
        barchart.setData(bardata);
        barchart.getDescription().setText("");
        barchart.setBackgroundColor(Color.rgb(17,45,78));
        barchart.animateY(2000);
        barchart.getAxisLeft().setDrawGridLines(false);
        barchart.getXAxis().setDrawGridLines(false);
        barchart.setScaleEnabled(false);
        barchart.getAxisLeft().setTextColor(Color.rgb(255,255,255));
        barchart.getAxisRight().setTextColor(Color.rgb(255,255,255));
        barchart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(days));
        barchart.getLegend().setTextColor(Color.rgb(255,255,255));
        barchart.getXAxis().setTextSize(20);
        barchart.getXAxis().setTextColor(Color.rgb(255,255,255));
        barchart.getLegend().setTextSize(20);
        barchart.getXAxis().setYOffset(-20);
    }
}