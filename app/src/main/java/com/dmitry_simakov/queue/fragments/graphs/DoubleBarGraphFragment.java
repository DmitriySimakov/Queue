package com.dmitry_simakov.queue.fragments.graphs;

import android.content.res.Resources;
import android.os.Bundle;

import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.fragments.calculaton.model_v.MMV_CalculationFragment;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class DoubleBarGraphFragment extends BarGraphFragment {
    
    private double[] y2_Values;
    
    @Override
    protected void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        y2_Values = bundle.getDoubleArray(MMV_CalculationFragment.W_VALUES);
    }
    
    BarDataSet dataSet2;
    
    @Override
    protected void prepareData(BarData data) {
        super.prepareData(data);
        
        // подготовим цвета
        Resources res = getResources();
        int white = res.getColor(R.color.white);
        int grey900 = res.getColor(R.color.grey_900);
    
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < y2_Values.length; i++) {
            entries.add(new BarEntry(i + y1_Values.length, (float) y2_Values[i]));
        }
        dataSet2 = new BarDataSet(entries, "Вероятность j ожидающих");
    
        // Настройки столбцов
        dataSet2.setColor(grey900);
        dataSet2.setHighLightColor(white);
    
        // Настройки значений над столбцами
        dataSet2.setValueTextColor(grey900);
        data.addDataSet(dataSet2);
    }
    
    @Override
    protected void adjustTextSize() {
        float barsInWindow = (y1_Values.length + y2_Values.length) / chart.getScaleX();
        
        if (barsInWindow <= BARS_FOR_MIN_TEXT_SIZE) {
            dataSet.setDrawValues(true);
            dataSet2.setDrawValues(true);
        
            if (barsInWindow >= BARS_FOR_MAX_TEXT_SIZE) {
                dataSet.setValueTextSize(100 / barsInWindow);
                dataSet2.setValueTextSize(100 / barsInWindow);
            } else {
                dataSet.setValueTextSize(100 / BARS_FOR_MAX_TEXT_SIZE);
                dataSet2.setValueTextSize(100 / BARS_FOR_MAX_TEXT_SIZE);
            }
        } else {
            dataSet.setDrawValues(false);
            dataSet2.setDrawValues(false);
        }
    }
}
