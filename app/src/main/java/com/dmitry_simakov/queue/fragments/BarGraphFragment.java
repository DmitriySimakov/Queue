package com.dmitry_simakov.queue.fragments;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.fragments.calculaton.Model_CalculationFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class BarGraphFragment extends Fragment implements OnChartValueSelectedListener {
    
    protected BarChart chart;
    
    protected double[] y1_Values;
    
    protected TextView P_TextView;
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    
        Log.d("LOG", "BarGraphFragment: onCreateView");
        View v = inflater.inflate(R.layout.fragment_bar_graph, container, false);
    
        // Получаем значения из активити
        Bundle bundle = getArguments();
        if (bundle != null) {
            getBundle(bundle);
        }
    
        // Получаем TextViews для OnChartValueSelectedListener
        P_TextView = getActivity().findViewById(R.id.Pk_TextView);
    
        // Содздаём гистограмму
        chart = new BarChart(getActivity());
        chart.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        FrameLayout frameLayout = v.findViewById(R.id.bar_graph_frame);
        frameLayout.addView(chart);
        
        // Задаём значения гистограмме
        BarData data = new BarData();
        prepareData(data);
        adjustTextSize();
        data.setBarWidth(0.9f); // Ширина бара определяется X величиной
    
        // настройки гистограммы
        chart.setData(data);
        chart.getDescription().setEnabled(false);
        chart.setDrawBarShadow(false);
    
        chart.setDoubleTapToZoomEnabled(false);
        //chart.setScaleEnabled(false);
        //chart.setDragEnabled(false);
        chart.setPinchZoom(true);
        chart.setBottom(0);
        
        // настройки осей
        chart.getAxisRight().setEnabled(false);
    
        chart.setOnChartValueSelectedListener(this);
    
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // Минимальные интервалы по X при зуме
        xAxis.setLabelCount(11); // Количество столбцов
    
        return v;
    }
    
    protected void getBundle(Bundle bundle) {
        Log.d("LOG", "BarGraphFragment: getBundle");
        y1_Values = bundle.getDoubleArray(Model_CalculationFragment.P_VALUES);
    }
    
    BarDataSet dataSet;
    
    protected void prepareData(BarData data) {
        Log.d("LOG", "BarGraphFragment: prepareData");
        // подготовим цвета
        Resources res = getResources();
        int white = res.getColor(R.color.white);
        int red700 = res.getColor(R.color.red_700);
        int grey900 = res.getColor(R.color.grey_900);
        
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < y1_Values.length; i++) {
            entries.add(new BarEntry(i, (float) y1_Values[i]));
        }
        dataSet = new BarDataSet(entries, "График чего-то там");
    
        // Настройки столбцов
        dataSet.setColor(red700);
        dataSet.setHighLightColor(white);
    
        // Настройки значений над столбцами
        dataSet.setValueTextColor(grey900);
        data.addDataSet(dataSet);
    }
    
    protected void adjustTextSize() {
        if (y1_Values.length <= 20) {
            if (y1_Values.length >= 10) {
                dataSet.setValueTextSize(100 / y1_Values.length);
            } else {
                dataSet.setValueTextSize(10);
            }
        } else {
            dataSet.setDrawValues(false);
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
        Log.d("LOG", "BarGraphFragment: onStart");
        chart.animateY(1500);
    }
    
    @Override
    public void onValueSelected(Entry e, Highlight h) {
        int k = (int) e.getX();
        P_TextView.setText("P[" + k + "] = " + e.getY());
    }
    
    @Override
    public void onNothingSelected() {
        P_TextView.setText("P[k]");
    }
}