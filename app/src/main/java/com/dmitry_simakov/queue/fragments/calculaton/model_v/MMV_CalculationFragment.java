package com.dmitry_simakov.queue.fragments.calculaton.model_v;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.fragments.BarGraphFragment;
import com.dmitry_simakov.queue.fragments.DoubleBarGraphFragment;

public class MMV_CalculationFragment extends Model_V_CalculationFragment {
    
    protected double[] W_Values = new double[6];
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
    protected void refreshText(boolean b) {
        super.refreshText(b);
        if (b) {
            k_TextView.setText("γ = " + k);
            t_TextView.setText("j = " + t);
        } else {
            k_TextView.setText("γ");
            t_TextView.setText("j");
        }
    }
    
    @Override
    protected void getP_Values() {
        Log.d("LOG", "MMV_CalculationFragment: getP_Values");
        double Pk_Values[] = model.getP();
        P_Values = new double[V];
        System.arraycopy(Pk_Values, 0, P_Values, 0, V);
        System.arraycopy(Pk_Values, V, W_Values, 0, 6);
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
