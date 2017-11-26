package com.dmitry_simakov.queue.fragments.graphs;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.fragments.calculaton.Model_CalculationFragment;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.ChartTouchListener;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.List;

public class BarGraphFragment extends Fragment implements OnChartValueSelectedListener, OnChartGestureListener {
    
    protected BarChart chart;
    
    protected double[] y1_Values;
    
    protected TextView P_TextView;
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    
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
        chart.setPinchZoom(true);
        chart.setBottom(0);
        
        // настройки осей
        chart.getAxisRight().setEnabled(false);
    
        chart.setOnChartValueSelectedListener(this);
        chart.setOnChartGestureListener(this);
        
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1f); // Минимальные интервалы по X при зуме
        xAxis.setLabelCount(11); // Количество столбцов
    
        return v;
    }
    
    protected void getBundle(Bundle bundle) {
        y1_Values = bundle.getDoubleArray(Model_CalculationFragment.P_VALUES);
    }
    
    BarDataSet dataSet;
    
    protected void prepareData(BarData data) {
        // подготовим цвета
        Resources res = getResources();
        int white = res.getColor(R.color.white);
        int red700 = res.getColor(R.color.red_700);
        int grey900 = res.getColor(R.color.grey_900);
        
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < y1_Values.length; i++) {
            entries.add(new BarEntry(i, (float) y1_Values[i]));
        }
        dataSet = new BarDataSet(entries, "Вероятность занятости k линий");
    
        // Настройки столбцов
        dataSet.setColor(red700);
        dataSet.setHighLightColor(white);
    
        // Настройки значений над столбцами
        dataSet.setValueTextColor(grey900);
        data.addDataSet(dataSet);
    }
    
    final int BARS_FOR_MIN_TEXT_SIZE = 20;
    final int BARS_FOR_MAX_TEXT_SIZE = 5;
    
    protected void adjustTextSize() {
        float barsInWindow = y1_Values.length / chart.getScaleX();
        
        if (barsInWindow <= BARS_FOR_MIN_TEXT_SIZE) {
            dataSet.setDrawValues(true);
            
            if (barsInWindow >= BARS_FOR_MAX_TEXT_SIZE) {
                dataSet.setValueTextSize(100 / barsInWindow);
            } else {
                dataSet.setValueTextSize(100 / BARS_FOR_MAX_TEXT_SIZE);
            }
        } else {
            dataSet.setDrawValues(false);
        }
    }
    
    @Override
    public void onStart() {
        super.onStart();
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
    
    @Override
    public void onChartScale(MotionEvent me, float scaleX, float scaleY) {
        adjustTextSize();
    }
    
    // Заглушки от OnChartGestureListener
    public void onChartGestureStart(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
    
    }
    public void onChartGestureEnd(MotionEvent me, ChartTouchListener.ChartGesture lastPerformedGesture) {
    
    }
    public void onChartLongPressed(MotionEvent me) {
    
    }
    public void onChartDoubleTapped(MotionEvent me) {
    
    }
    public void onChartSingleTapped(MotionEvent me) {
    
    }
    public void onChartFling(MotionEvent me1, MotionEvent me2, float velocityX, float velocityY) {
    
    }
    public void onChartTranslate(MotionEvent me, float dX, float dY) {
    
    }
}