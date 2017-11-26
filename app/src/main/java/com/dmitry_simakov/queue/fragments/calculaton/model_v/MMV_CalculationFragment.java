package com.dmitry_simakov.queue.fragments.calculaton.model_v;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.fragments.graphs.BarGraphFragment;

public class MMV_CalculationFragment extends Model_V_CalculationFragment {
    
    @Override
    protected void refreshTextViews() {
        super.refreshTextViews();
        
        if (wasCalculated) {
            k_TextView.setText("γ_ = " + k);
            t_TextView.setText("j_ = " + t);
        } else {
            k_TextView.setText("γ_");
            t_TextView.setText("j_");
        }
    }
}
