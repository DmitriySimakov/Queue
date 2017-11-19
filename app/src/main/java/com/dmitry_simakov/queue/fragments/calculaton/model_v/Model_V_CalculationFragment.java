package com.dmitry_simakov.queue.fragments.calculaton.model_v;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.fragments.calculaton.Model_CalculationFragment;
import com.dmitry_simakov.queue.models.Model_V;

import static com.dmitry_simakov.queue.fragments.MainActivityFragment.MODELS;

public class Model_V_CalculationFragment extends Model_CalculationFragment {
    
    Model_V model;
    
    protected int V;
    protected EditText V_EditText;
    public static final String V_VALUE = "V_VALUE";
    
    protected float Pt;
    protected TextView Pt_TextView;
    public static final String Pt_VALUE = "Pt_VALUE";
    
    @Override
    protected void setModel() {
        model = (Model_V) MODELS[id];
    }
    
    @Override
    protected void getSavedInstanceStates(Bundle savedInstanceState) {
        super.getSavedInstanceStates(savedInstanceState);
        Log.d("LOG", "Model_V_CalculationFragment: getSavedInstanceState");
        V = savedInstanceState.getInt(V_VALUE);
    }
    
    @Override
    protected void findViews(View v) {
        super.findViews(v);
        V_EditText = v.findViewById(R.id.V_EditText);
        V_EditText.setVisibility(View.VISIBLE);
        Pt_TextView = v.findViewById(R.id.Pt_TextView);
        Pt_TextView.setVisibility(View.VISIBLE);
    }
    
    @Override
    protected void refreshText(boolean b) {
        super.refreshText(b);
        if (b) {
            V_EditText.setHint("V = " + V);
    
            Pt_TextView.setText("Pt = " + Pt);
        } else {
            V_EditText.setHint("V");
    
            Pt_TextView.setText("Pt");
        }
    }
    
    @Override
    protected void saveInstanceStates(Bundle savedInstanceState) {
        super.saveInstanceStates(savedInstanceState);
        savedInstanceState.putInt(V_VALUE, V);
        savedInstanceState.putFloat(Pt_VALUE, Pt);
    }
    
    @Override
    public void onClick(View view) {
        Log.d("LOG", "Model_V_CalculationFragment: onClick");
        String lambdaStr = lambda_EditText.getText().toString();
        String muStr = mu_EditText.getText().toString();
        String vStr = V_EditText.getText().toString();
        
        if (lambdaStr.trim().length() == 0) {
            invalidInput("Пожалуйста, введите λ", lambda_EditText);
            return;
        }
        if (muStr.trim().length() == 0) {
            invalidInput("Пожалуйста, введите μ", mu_EditText);
            return;
        }
        if (vStr.trim().length() == 0) {
            invalidInput("Пожалуйста, введите V", V_EditText);
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
        try {
            V = Integer.parseInt(vStr);
        } catch (Exception e) {
            invalidInput("Некорректный ввод", V_EditText);
            return;
        }
        
        lambda_EditText.setText("");
        mu_EditText.setText("");
        V_EditText.setText("");
        
        String error = model.setValues(lambda, mu, V);
        if (error != null) {
            invalidInput(error, lambda_EditText);
            return;
        }
        model.calculate();
        
        // Скрыть клавиатуру
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(OK_Button.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        
        // Показать введённые данные
        k = model.getK_();
        t = model.getT_();
        Pt = model.getPt();
        refreshText(true);
    
        getP_Values();
        createGraphFragment();
        
        lambda_EditText.clearFocus();
        mu_EditText.clearFocus();
        V_EditText.clearFocus();
    }
}
