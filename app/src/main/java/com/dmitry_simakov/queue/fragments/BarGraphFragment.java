package com.dmitry_simakov.queue.fragments;

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
    
    private BarChart barChart;
    private float[] XValues;
    private float[] YValues;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bar_graph, container, false);
    
        Bundle bundle = getArguments();
        if (bundle != null) {
            XValues = bundle.getFloatArray("XValues");
            YValues = bundle.getFloatArray("YValues");
            
        }
    
        barChart = new BarChart(getActivity());
        barChart.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        FrameLayout frameLayout = v.findViewById(R.id.bar_graph_frame);
        frameLayout.addView(barChart);
    
        List<BarEntry> entries = new ArrayList<BarEntry>();
        for (int i = 0; i < XValues.length; i++) {
            entries.add(new BarEntry(XValues[i], YValues[i]));
        }
    
        BarDataSet dataSet = new BarDataSet(entries, "График чего-то там");
        //dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setColor(getResources().getColor(R.color.red_700)); // Цвет бара
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(16f);
        //dataSet.setDrawValues(false);
        dataSet.setHighLightColor(Color.WHITE);
    
        // Окантовочка
        //dataSet.setBarBorderColor(Color.BLACK);
        //dataSet.setBarBorderWidth(1f);
    
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f); // Ширина бара определяется X величиной
    
        barChart.setData(barData);
        barChart.getDescription().setEnabled(false);
        barChart.setDoubleTapToZoomEnabled(false);
        //barChart.setScaleEnabled(false);
        //barChart.setDragEnabled(false);
        barChart.setPinchZoom(true);
        barChart.setDrawGridBackground(false);
        barChart.setDrawBarShadow(false);
    
        barChart.getAxisLeft().setSpaceBottom(0); // убрать отступ баров от ординаты
        barChart.getAxisRight().setEnabled(false);
    
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // Минимальные интервалы по X при зуме
        xAxis.setLabelCount(11); // Количество столбцов
    
        //barChart.invalidate(); // Если нужно перерисовать
        
        return v;
    }
    
    @Override
    public void onStart() {
        super.onStart();
        barChart.animateY(1500);
    }
    
}
