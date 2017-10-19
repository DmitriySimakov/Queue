package com.dmitry_simakov.queue.fragments;

import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.dmitry_simakov.queue.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class BarGraphFragment extends Fragment {
    
    private static int NUM_OF_VALUES = 11;
    private BarChart barChart;
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_bar_graph, container, false);
        
        float[] XValues = new float[NUM_OF_VALUES];
        float[] YValues = new float[NUM_OF_VALUES];
        
        // TODO Count values from formulas
        for (int i = 0; i < NUM_OF_VALUES; i++) {
            XValues[i] = (float) i;
            YValues[i] = (float) Math.random() * 1000;
        }
        // TODO
        
        barChart = new BarChart(getActivity());
        barChart.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout linearLayout = v.findViewById(R.id.linear_layout);
        linearLayout.addView(barChart);
        
        List<BarEntry> entries = new ArrayList<BarEntry>();
        for (int i = 0; i < NUM_OF_VALUES; i++) {
            entries.add(new BarEntry(XValues[i], YValues[i]));
        }
        
        BarDataSet dataSet = new BarDataSet(entries, "График чего-то там");
        //dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setColor(getResources().getColor(R.color.red)); // Цвет бара
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
    
    @Override
    public void onResume() {
        super.onResume();
        //barChart.animateY(1500);
    }
}