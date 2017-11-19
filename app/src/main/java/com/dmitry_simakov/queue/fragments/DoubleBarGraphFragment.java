package com.dmitry_simakov.queue.fragments;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.fragments.calculaton.model_v.MMV_CalculationFragment;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

public class DoubleBarGraphFragment extends BarGraphFragment {
    
    private float[] y2_Values;
    
    protected TextView W_TextView;
    
    @Override
    protected void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        y2_Values = bundle.getFloatArray(MMV_CalculationFragment.W_VALUES);
    }
    
    @Override
    protected void prepareData(BarData data) {
        super.prepareData(data);
        Log.d("LOG", "DoubleGraphFragment: prepareData");
        
        // подготовим цвета
        Resources res = getResources();
        int white = res.getColor(R.color.white);
        int grey900 = res.getColor(R.color.grey_900);
    
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < y2_Values.length; i++) {
            entries.add(new BarEntry(i + y1_Values.length, y2_Values[i]));
        }
        BarDataSet dataSet = new BarDataSet(entries, "График чего-то там");
    
        // Настройки столбцов
        dataSet.setColor(grey900);
        dataSet.setHighLightColor(white);
    
        // Настройки значений над столбцами
        dataSet.setValueTextColor(grey900);
        dataSet.setValueTextSize(10f);
        //dataSet.setDrawValues(false);
    
        data.addDataSet(dataSet);
    }
}
