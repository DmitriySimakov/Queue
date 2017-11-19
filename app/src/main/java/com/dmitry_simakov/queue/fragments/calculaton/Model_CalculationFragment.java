package com.dmitry_simakov.queue.fragments.calculaton;

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
import android.widget.TextView;
import android.widget.Toast;

import com.dmitry_simakov.queue.ModelActivity;
import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.fragments.BarGraphFragment;
import com.dmitry_simakov.queue.models.Model;

import static com.dmitry_simakov.queue.fragments.MainActivityFragment.MODELS;

public class Model_CalculationFragment extends Fragment implements View.OnClickListener {
    
    protected int id;
    protected Model model;
    
    protected float lambda;
    protected EditText lambda_EditText;
    public static final String LAMBDA_VALUE = "LAMBDA_VALUE";
    
    protected float mu;
    protected EditText mu_EditText;
    public static final String MU_VALUE = "MU_VALUE";
    
    protected Button OK_Button;
    
    protected float k;
    protected TextView k_TextView;
    public static final String K_VALUE = "K_VALUE";
    
    protected float t;
    protected TextView t_TextView;
    public static final String T_VALUE = "T_VALUE";
    
    protected float[] P_Values = new float[11];
    public static final String P_VALUES = "P_VALUES";
    
    protected boolean wasCalculated = false;
    public static final String WAS_CALCULATED = "WAS_CALCULATED";
    
    protected Toast toast = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("LOG", "Model_CalculationFragment: onCreate");
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            getSavedInstanceStates(savedInstanceState);
        }
    }
    
    protected void getSavedInstanceStates(Bundle savedInstanceState) {
        wasCalculated = savedInstanceState.getBoolean(WAS_CALCULATED);
        
        lambda = savedInstanceState.getFloat(LAMBDA_VALUE);
        mu = savedInstanceState.getFloat(MU_VALUE);
        
        k = savedInstanceState.getFloat(K_VALUE);
        t = savedInstanceState.getFloat(T_VALUE);
        P_Values = savedInstanceState.getFloatArray(P_VALUES);
    }
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    
        Log.d("LOG", "Model_CalculationFragment: onCreateView");
        final View v = inflater.inflate(R.layout.fragment_model_calc, container, false);
    
        // Получаем модель по id
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt(ModelActivity.MODEL_ID);
        }
        setModel();
        
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
        
        findViews(v);
        
        // Устанавливаю сохранённые значния полей ввода
        refreshText(wasCalculated);
        
        createGraphFragment();
        
        return v;
    }
    
    protected void setModel() {
        model = MODELS[id];
    }
    
    protected void findViews(View v) {
        lambda_EditText = v.findViewById(R.id.lambda_EditText);
        mu_EditText = v.findViewById(R.id.mu_EditText);
        OK_Button = v.findViewById(R.id.OK_Button);
        OK_Button.setOnClickListener(this);
        t_TextView = v.findViewById(R.id.t_TextView);
        k_TextView = v.findViewById(R.id.k_TextView);
    }
    
    protected void refreshText(boolean b) {
        if (b) {
            lambda_EditText.setHint("λ = " + lambda);
            mu_EditText.setHint("μ = " + mu);
    
            k_TextView.setText("k_ = " + k);
            t_TextView.setText("t_ = " + t);
        } else {
            lambda_EditText.setHint("λ");
            mu_EditText.setHint("μ");
    
            k_TextView.setText("k_");
            t_TextView.setText("t_");
        }
    }
    
    @Override
    public void onPause() {
        Log.d("LOG", "Model_CalculationFragment: onPause");
        super.onPause();
        toast.cancel();
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("LOG", "Model_CalculationFragment: onSaveInstanceState");
        savedInstanceState.putBoolean(WAS_CALCULATED, wasCalculated);
        
        savedInstanceState.putFloat(LAMBDA_VALUE, lambda);
        savedInstanceState.putFloat(MU_VALUE, mu);
    
        savedInstanceState.putFloat(K_VALUE, k);
        savedInstanceState.putFloat(T_VALUE, t);
        savedInstanceState.putFloatArray(P_VALUES, P_Values);
    }
    
    @Override
    public void onClick(View view) {
        Log.d("LOG", "Model_CalculationFragment: onClick");
        String lambdaStr = lambda_EditText.getText().toString();
        String muStr = mu_EditText.getText().toString();
        
        if (lambdaStr.trim().length() == 0) {
            invalidInput("Пожалуйста, введите λ", lambda_EditText);
            return;
        }
        if (muStr.trim().length() == 0) {
            invalidInput("Пожалуйста, введите μ", mu_EditText);
            return;
        }
        
        try {
            lambda = Float.parseFloat(lambdaStr);
        } catch (Exception e) {
            invalidInput("Некорректный ввод", lambda_EditText);
            return;
        }
        try {
            mu = Float.parseFloat(muStr);
        } catch (Exception e) {
            invalidInput("Некорректный ввод", mu_EditText);
            return;
        }
        
        lambda_EditText.setText("");
        mu_EditText.setText("");
        
        String error = model.setValues(lambda, mu);
        if (error != null) {
            invalidInput(error, lambda_EditText);
            return;
        }
        model.calculate();
        wasCalculated = true;
        
        // Скрыть клавиатуру
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(OK_Button.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        
        // Показать введённые данные
        k = model.getK_();
        t = model.getT_();
        refreshText(true);
        
        getP_Values();
        createGraphFragment();
        
        lambda_EditText.clearFocus();
        mu_EditText.clearFocus();
    }
    
    protected void invalidInput(String message, EditText editText) {
        toast.setText(message);
        toast.show();
        editText.requestFocus();
    }
    
    protected void getP_Values() {
        Log.d("LOG", "Model_CalculationFragment: getP_Values");
        P_Values = model.getP();
    }
    
    protected void createGraphFragment() {
        Log.d("LOG", "Model_CalculationFragment: createGraphFragment");
        FragmentManager myFragmentManager = getFragmentManager();
        Fragment fragment = null;
        Bundle args = new Bundle();
        args.putFloatArray(P_VALUES, P_Values);
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