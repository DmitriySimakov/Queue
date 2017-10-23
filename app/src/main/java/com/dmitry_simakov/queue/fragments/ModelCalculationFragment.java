package com.dmitry_simakov.queue.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.dmitry_simakov.queue.R;

public class ModelCalculationFragment extends Fragment {
    
    FrameLayout container;
    final static String TAG_1 = "BAR_GRAPH";
    final static String TAG_2 = "GRAPH";
    final static String TAG_3 = "TABLE";
    
    public static final String MODEL_NAME = "MODEL_NAME";
    private String modelName;
    
    private static final int NUM_OF_VALUES = 11;
    private float[] XValues;
    private float[] YValues;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            modelName = savedInstanceState.getString(MODEL_NAME);
        }
    }
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_model_calculation, container, false);
        
        XValues = new float[NUM_OF_VALUES];
        YValues = new float[NUM_OF_VALUES];
        
        // TODO Count values from formulas
        for (int i = 0; i < NUM_OF_VALUES; i++) {
            XValues[i] = (float) i;
            YValues[i] = (float) Math.random() * 1000;
        }
        // TODO
    
        FragmentManager myFragmentManager = getFragmentManager();
        Fragment fragment = null;
        Bundle args = new Bundle();
        args.putFloatArray("XValues", XValues);
        args.putFloatArray("YValues", YValues);
        //switch (/* Settings choice */) {
        //    case R.id.bar_graph:
                fragment = new BarGraphFragment();
        //        break;
        //    case R.id.graph:
        //        fragment = new GraphFragment();
        //        break;
        //}
        
        fragment.setArguments(args);
        myFragmentManager.beginTransaction().replace(R.id.p_frame, fragment).commit();
        
        return v;
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(MODEL_NAME, modelName);
    }
    
    public void setModel(String modelName) {
        this.modelName = modelName;
    }
}