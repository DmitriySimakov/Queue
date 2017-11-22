package com.dmitry_simakov.queue.fragments.calculaton.model_v;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.fragments.DoubleBarGraphFragment;

public class MMV_CalculationFragment extends Model_V_CalculationFragment {
    
    protected double[] W_Values = new double[5];
    public static final String W_VALUES = "W_VALUES";
    
    @Override
    protected void getSavedInstanceStates(Bundle savedInstanceState) {
        super.getSavedInstanceStates(savedInstanceState);
        W_Values = savedInstanceState.getDoubleArray(W_VALUES);
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putDoubleArray(W_VALUES, W_Values);
    }
    
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
    
    @Override
    protected void getParametersFromModel() {
        super.getParametersFromModel();
        
        double Pk_Values[] = model.getP();
        P_Values = new double[V + 1];
        System.arraycopy(Pk_Values, 0, P_Values, 0, V + 1);
        System.arraycopy(Pk_Values, V + 1, W_Values, 0, 5);
    }
    
    @Override
    protected void createGraphFragment() {
        Log.d("LOG", "MMV_CalculationFragment: createGraphFragment");
        FragmentManager myFragmentManager = getFragmentManager();
        Fragment fragment = null;
        Bundle args = new Bundle();
        args.putDoubleArray(P_VALUES, P_Values);
        args.putDoubleArray(W_VALUES, W_Values);
        //switch (/* Settings choice */) {
        //    case R.id.bar_graph:
        fragment = new DoubleBarGraphFragment();
        //        break;
        //    case R.id.graph:
        //        fragment = new LineGraphFragment();
        //        break;
        //}
    
        fragment.setArguments(args);
        myFragmentManager.beginTransaction().replace(R.id.graph_frame, fragment).commit();
    }
}
