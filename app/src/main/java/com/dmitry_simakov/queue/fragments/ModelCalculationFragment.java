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

import com.dmitry_simakov.queue.ModelActivity;
import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.models.Model;

public class ModelCalculationFragment extends Fragment implements View.OnClickListener {
    
    private String modelName;
    private Model model;
    
    EditText lambdaET;
    EditText muET;
    Button okBtn;
    TextView kTV;
    TextView tTV;
    TextView pTV;
    
    float lambda;
    float mu;
    
    public static final String K_VALUE = "K_VALUE";
    float k;
    
    public static final String T_VALUE = "T_VALUE";
    float t;
    
    public static final String P_VALUES = "P_VALUES";
    private float[] PValues = new float[11];
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            k = savedInstanceState.getFloat(K_VALUE);
            t = savedInstanceState.getFloat(T_VALUE);
            PValues = savedInstanceState.getFloatArray(P_VALUES);
        }
    }
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_model_calculation, container, false);
    
        Bundle bundle = getArguments();
        if (bundle != null) {
            modelName = bundle.getString(ModelActivity.MODEL_NAME);
        }
    
        // Загружаю класс модели по имени
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
        
        // Нахожу элементы View по их id
        lambdaET = v.findViewById(R.id.lambda_ET);
        muET = v.findViewById(R.id.mu_ET);
        okBtn = v.findViewById(R.id.ok_Btn);
        okBtn.setOnClickListener(this);
        tTV = v.findViewById(R.id.t_TV);
        kTV = v.findViewById(R.id.k_TV);
        pTV = v.findViewById(R.id.p_TV);
        
        // Устанавливаю значния элементов
        kTV.setText("k = " + k);
        tTV.setText("t = " + t);
        pTV.setText("P = ");
        createGraphFragment();
        
        return v;
    }
    
    public void onStart() {
        super.onStart();
        Log.d("LOG", "ModelDescriptionFragment: onStart");
    
        
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putFloat(K_VALUE, k);
        savedInstanceState.putFloat(T_VALUE, t);
        savedInstanceState.putFloatArray(P_VALUES, PValues);
    }
    
    @Override
    public void onClick(View view) {
        lambda = Float.valueOf(lambdaET.getText().toString());
        mu = Float.valueOf(muET.getText().toString());
        lambdaET.setText("");
        muET.setText("");
        if (model.setValues(lambda, mu)) {
            model.getP(PValues);
            
            k = model.getK();
            t = model.getT();
            kTV.setText("k = " + k);
            tTV.setText("t = " + t);
        }
        
        createGraphFragment();
    }
    
    private void createGraphFragment() {
        FragmentManager myFragmentManager = getFragmentManager();
        Fragment fragment = null;
        Bundle args = new Bundle();
        args.putFloatArray(P_VALUES, PValues);
        //switch (/* Settings choice */) {
        //    case R.id.bar_graph:
        fragment = new BarGraphFragment();
        //        break;
        //    case R.id.graph:
        //        fragment = new LineGraphFragment();
        //        break;
        //}
    
        fragment.setArguments(args);
        myFragmentManager.beginTransaction().replace(R.id.p_frame, fragment).commit();
    }
}