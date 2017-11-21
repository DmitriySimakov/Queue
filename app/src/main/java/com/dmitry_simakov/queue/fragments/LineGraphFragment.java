package com.dmitry_simakov.queue.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dmitry_simakov.queue.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

public class LineGraphFragment extends Fragment {
    
    private LineChart chart;
    private double[] XValues;
    private double[] YValues;
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_line_graph, container, false);
        
        Bundle bundle = getArguments();
        if (bundle != null) {
            XValues = bundle.getDoubleArray("XValues");
            YValues = bundle.getDoubleArray("YValues");
        }
        
        chart = new LineChart(getActivity());
        chart.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        FrameLayout frameLayout = v.findViewById(R.id.line_graph_frame);
        frameLayout.addView(chart);
        
        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < XValues.length; i++) {
            entries.add(new Entry((float) XValues[i], (float) YValues[i]));
        }
        
        LineDataSet dataSet = new LineDataSet(entries, "График чего-то там");
        
        // подготовим цвета
        Resources res = getResources();
        int white = res.getColor(R.color.white);
        int red700 = res.getColor(R.color.red_700);
        int grey700 = res.getColor(R.color.grey_700);
        int grey900 = res.getColor(R.color.grey_900);
        
        // настройки линии
        dataSet.setColor(red700);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        dataSet.setLineWidth(2f);
    
        // настройки кружков
        dataSet.setCircleColor(red700);
        //dataSet.setDrawCircleHole(false);
        dataSet.setCircleColorHole(white);
        dataSet.setCircleRadius(4f);
    
        // настройки креста
        dataSet.setHighLightColor(grey700);
        dataSet.setHighlightLineWidth(1.5f);
    
        // настройки значений над кружками
        dataSet.setValueTextColor(grey900);
        dataSet.setValueTextSize(16f);
        //dataSet.setDrawValues(false);
    
        // настойки заполнения площади под линией
        //dataSet.setFillColor(Color.RED);
        //dataSet.setFillAlpha(255);
        //dataSet.setDrawFilled(true);
        
        LineData data = new LineData(dataSet);
    
        // настройки графика
        chart.setData(data);
        chart.getDescription().setEnabled(false);
    
        chart.setPinchZoom(true);
        chart.setDoubleTapToZoomEnabled(false);
        
        // Настройки осей
        chart.getAxisRight().setEnabled(false);
        
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setDrawGridLines(false); // Отключить вертикальные линии
        xAxis.setGranularity(1f); // Минимальные интервалы по X при зуме
        xAxis.setLabelCount(11); // Количество отображаемых значений
        
        return v;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        chart.animateY(1500);
    }
}