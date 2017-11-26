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
    
    private BarChart chart;
    
    private double[] P_Values;
    private int V;
    
    private TextView P_TextView;
    BarDataSet dataSet1;
    BarDataSet dataSet2;
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    
        View v = inflater.inflate(R.layout.fragment_bar_graph, container, false);
    
        // Получаем значения из активити
        Bundle bundle = getArguments();
        if (bundle != null) {
            P_Values = bundle.getDoubleArray(Model_CalculationFragment.P_VALUES);
            V = bundle.getInt(Model_CalculationFragment.V_VALUE);
        }
    
        // Получаем TextViews для OnChartValueSelectedListener
        P_TextView = getActivity().findViewById(R.id.Pk_TextView);
    
        // Содздаём гистограмму
        chart = new BarChart(getActivity());
        chart.setLayoutParams(new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        FrameLayout frameLayout = v.findViewById(R.id.bar_graph_frame);
        frameLayout.addView(chart);
        
        // подготовим цвета
        Resources res = getResources();
        int white = res.getColor(R.color.white);
        int red700 = res.getColor(R.color.red_700);
        int grey900 = res.getColor(R.color.grey_900);
    
        BarData data = new BarData();
        
        List<BarEntry> entries1 = new ArrayList<>();
        for (int i = 0; i <= V; i++) {
            entries1.add(new BarEntry(i, (float) P_Values[i]));
        }
        dataSet1 = new BarDataSet(entries1, "Вероятность занятости k линий");
    
        // Настройки столбцов
        dataSet1.setColor(red700);
        dataSet1.setHighLightColor(white);
        dataSet1.setValueTextColor(grey900);
        data.addDataSet(dataSet1);
        
        if (P_Values.length > V) {
            List<BarEntry> entries2 = new ArrayList<>();
            for (int i = V + 1; i < P_Values.length; i++) {
                entries2.add(new BarEntry(i, (float) P_Values[i]));
            }
            dataSet2 = new BarDataSet(entries2, "Вероятность j ожидающих");
    
            // Настройки столбцов
            dataSet2.setColor(grey900);
            dataSet2.setHighLightColor(white);
    
            // Настройки значений над столбцами
            dataSet2.setValueTextColor(grey900);
            data.addDataSet(dataSet2);
        }
        
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
    
    final int BARS_FOR_MIN_TEXT_SIZE = 20;
    final int BARS_FOR_MAX_TEXT_SIZE = 5;
    
    protected void adjustTextSize() {
        float barsInWindow = P_Values.length / chart.getScaleX();
        
        if (barsInWindow <= BARS_FOR_MIN_TEXT_SIZE) {
            dataSet1.setDrawValues(true);
            
            if (barsInWindow >= BARS_FOR_MAX_TEXT_SIZE) {
                dataSet1.setValueTextSize(100 / barsInWindow);
            } else {
                dataSet1.setValueTextSize(100 / BARS_FOR_MAX_TEXT_SIZE);
            }
        } else {
            dataSet1.setDrawValues(false);
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