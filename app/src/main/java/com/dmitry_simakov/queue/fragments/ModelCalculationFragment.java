package com.dmitry_simakov.queue.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.models.Model;

public class ModelCalculationFragment extends Fragment implements View.OnClickListener {
    
    public static final String MODEL_NAME = "MODEL_NAME";
    private String modelName;
    private Model model;
    
    public static final String P_VALUES = "P_VALUES";
    private float[] PValues = new float[11];
    
    EditText lambdaET;
    EditText muET;
    Button okBtn;
    
    TextView kTV;
    TextView tTV;
    
    float lambda;
    float mu;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            modelName = savedInstanceState.getString(MODEL_NAME);
            PValues = savedInstanceState.getFloatArray(P_VALUES);
        }
    }
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_model_calculation, container, false);
        
        lambdaET = v.findViewById(R.id.lambda_ET);
        muET = v.findViewById(R.id.mu_ET);
        okBtn = v.findViewById(R.id.ok_Btn);
        okBtn.setOnClickListener(this);
        
        tTV = v.findViewById(R.id.t_TV);
        kTV = v.findViewById(R.id.k_TV);
        
        return v;
    }
    
    public void onStart() {
        super.onStart();
        Log.d("LOG", "ModelDescriptionFragment: onStart");
        String className = modelName.replace("/", "");
        String fullClassName = "com.dmitry_simakov.queue.models." + className;
        try {
            model = (Model) Class.forName(fullClassName).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(MODEL_NAME, modelName);
        savedInstanceState.putFloatArray(P_VALUES, PValues);
    }
    
    public void setModel(String modelName) {
        this.modelName = modelName;
    }
    
    @Override
    public void onClick(View view) {
        lambda = Float.valueOf(lambdaET.getText().toString());
        mu = Float.valueOf(muET.getText().toString());
        lambdaET.setText("");
        muET.setText("");
        if (model.setValues(lambda, mu)) {
            model.getP(PValues);
            kTV.setText("k = " + model.getK());
            tTV.setText("t = " + model.getT());
        }
        
        createFragment();
    }
    
    private void createFragment() {
        FragmentManager myFragmentManager = getFragmentManager();
        Fragment fragment = null;
        Bundle args = new Bundle();
        args.putFloatArray(P_VALUES, PValues);
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
    }
}