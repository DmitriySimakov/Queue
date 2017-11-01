package com.dmitry_simakov.queue.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dmitry_simakov.queue.ModelActivity;
import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.models.Model;

public class ModelCalculationFragment extends Fragment implements View.OnClickListener {
    
    private Toast toast = null;
    
    private String modelName;
    private Model model;
    
    ImageView tFormula;
    ImageView kFormula;
    ImageView pFormula;
    
    EditText lambdaET;
    EditText muET;
    Button okBtn;
    TextView kTV;
    TextView tTV;
    
    public static final String LAMBDA_VALUE = "LAMBDA_VALUE";
    float lambda;
    
    public static final String MU_VALUE = "MU_VALUE";
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
            lambda = savedInstanceState.getFloat(LAMBDA_VALUE);
            mu = savedInstanceState.getFloat(MU_VALUE);
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
    
        model = Model.getModelByName(modelName);
    
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        
        // Нахожу элементы View по их id
        kFormula = v.findViewById(R.id.k_formula_IV);
        kFormula.setImageResource(model.getKFormula());
        tFormula = v.findViewById(R.id.t_formula_IV);
        tFormula.setImageResource(model.getTFormula());
        pFormula = v.findViewById(R.id.p_formula_IV);
        pFormula.setImageResource(model.getPFormula());
        
        lambdaET = v.findViewById(R.id.lambda_ET);
        muET = v.findViewById(R.id.mu_ET);
        okBtn = v.findViewById(R.id.ok_Btn);
        okBtn.setOnClickListener(this);
        tTV = v.findViewById(R.id.t_TV);
        kTV = v.findViewById(R.id.k_TV);
        
        // Устанавливаю значния элементов
        if (savedInstanceState != null) {
            lambdaET.setHint(getString(R.string.lambda) + " = " + lambda);
            muET.setHint(getString(R.string.mu) + " = " + mu);
            kTV.setText(" = " + k);
            tTV.setText(" = " + t);
        }
        createGraphFragment();
        
        return v;
    }
    
    @Override
    public void onPause() {
        super.onPause();
        toast.cancel();
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putFloat(LAMBDA_VALUE, lambda);
        savedInstanceState.putFloat(MU_VALUE, mu);
        savedInstanceState.putFloat(K_VALUE, k);
        savedInstanceState.putFloat(T_VALUE, t);
        savedInstanceState.putFloatArray(P_VALUES, PValues);
    }
    
    @Override
    public void onClick(View view) {
        String lambdaStr = lambdaET.getText().toString();
        String muStr = muET.getText().toString();
        if (lambdaStr.trim().length() == 0) {
            toast.setText("Пожалуйста, введите lambda");
            toast.show();
            //Toast.makeText(getActivity(), "Пожалуйста, введите lambda", Toast.LENGTH_SHORT).show();
            lambdaET.requestFocus();
            return;
        }
        if (muStr.trim().length() == 0) {
            toast.setText("Пожалуйста, введите mu");
            toast.show();
            //Toast.makeText(getActivity(), "Пожалуйста, введите mu", Toast.LENGTH_SHORT).show();
            muET.requestFocus();
            return;
        }
        try {
            lambda = Float.parseFloat(lambdaStr);
        } catch (Exception e) {
            toast.setText("Некорректный ввод");
            toast.show();
            //Toast.makeText(getActivity(), "Некорректный ввод", Toast.LENGTH_SHORT).show();
            lambdaET.requestFocus();
            return;
        }
        try {
            mu = Float.parseFloat(muStr);
        } catch (Exception e) {
            toast.setText("Некорректный ввод");
            toast.show();
            //Toast.makeText(getActivity(), "Некорректный ввод", Toast.LENGTH_SHORT).show();
            muET.requestFocus();
            return;
        }
        
        lambdaET.setText("");
        muET.setText("");
        
        String error = model.setValues(lambda, mu);
        if (error == null) {
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(okBtn.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            
            lambdaET.setHint(getString(R.string.lambda) + " = " + lambda);
            muET.setHint(getString(R.string.mu) + " = " + mu);
            
            model.getP(PValues);
    
            k = model.getK();
            t = model.getT();
            kTV.setText(" = " + k);
            tTV.setText(" = " + t);
            createGraphFragment();
            lambdaET.clearFocus();
            muET.clearFocus();
    
        } else {
            toast.setText(error);
            toast.show();
            //Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            lambdaET.requestFocus();
            return;
        }
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
        myFragmentManager.beginTransaction().replace(R.id.graph_frame, fragment).commit();
    }
}