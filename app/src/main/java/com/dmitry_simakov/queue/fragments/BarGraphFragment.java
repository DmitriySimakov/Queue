package com.dmitry_simakov.queue.fragments;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dmitry_simakov.queue.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class BarGraphFragment extends Fragment {
    
    private BarChart chart;
    private float[] XValues;
    private float[] YValues;
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bar_graph, container, false);
    
        Bundle bundle = getArguments();
        if (bundle != null) {
            XValues = bundle.getFloatArray("XValues");
            YValues = bundle.getFloatArray("YValues");
            
        }
    
        chart = new BarChart(getActivity());
        chart.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        FrameLayout frameLayout = v.findViewById(R.id.bar_graph_frame);
        frameLayout.addView(chart);
    
        List<BarEntry> entries = new ArrayList<BarEntry>();
        for (int i = 0; i < XValues.length; i++) {
            entries.add(new BarEntry(XValues[i], YValues[i]));
        }
    
        BarDataSet dataSet = new BarDataSet(entries, "График чего-то там");
    
        // подготовим цвета
        Resources res = getResources();
        int white = res.getColor(R.color.white);
        int red700 = res.getColor(R.color.red_700);
        int grey700 = res.getColor(R.color.grey_700);
        int grey900 = res.getColor(R.color.grey_900);
        
        // Настройки столбцов
        dataSet.setColor(red700);
        dataSet.setHighLightColor(white);
    
        // Настройки значений над столбцами
        dataSet.setValueTextColor(grey900);
        dataSet.setValueTextSize(16f);
        //dataSet.setDrawValues(false);
        
        BarData data = new BarData(dataSet);
        data.setBarWidth(0.9f); // Ширина бара определяется X величиной
    
        // настройки гистограммы
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.setDrawBarShadow(false);
    
        chart.setDoubleTapToZoomEnabled(false);
        //chart.setScaleEnabled(false);
        //chart.setDragEnabled(false);
        chart.setPinchZoom(true);
        
        // настройки осей
        chart.getAxisLeft().setSpaceBottom(0); // убрать отступ баров от ординаты
        chart.getAxisRight().setEnabled(false);
    
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // Минимальные интервалы по X при зуме
        xAxis.setLabelCount(11); // Количество столбцов
    
        return v;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        chart.animateY(1500);
    }
    
}
