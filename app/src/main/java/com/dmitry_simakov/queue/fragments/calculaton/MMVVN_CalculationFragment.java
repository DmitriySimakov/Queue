package com.dmitry_simakov.queue.fragments.calculaton;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.fragments.calculaton.model_v.Model_V_CalculationFragment;
import com.dmitry_simakov.queue.models.MMVVN;
import static com.dmitry_simakov.queue.fragments.MainActivityFragment.MODELS;

public class MMVVN_CalculationFragment extends Model_V_CalculationFragment {
    
    private int N;
    private EditText N_EditText;
    public static final String N_VALUE = "N_VALUE";
    
    private float Pb;
    private TextView Pb_TextView;
    public static final String Pb_VALUE = "Pb_VALUE";
    
    @Override
    protected void setModel() {
        model = (MMVVN) MODELS[id];
    }
    
    @Override
    protected void getSavedInstanceStates(Bundle savedInstanceState) {
        super.getSavedInstanceStates(savedInstanceState);
        N = savedInstanceState.getInt(N_VALUE);
        Pb = savedInstanceState.getFloat(Pb_VALUE);
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt(N_VALUE, N);
        savedInstanceState.putFloat(Pb_VALUE, Pb);
    }
    
    @Override
    protected void findViews(View v) {
        super.findViews(v);
        N_EditText = v.findViewById(R.id.N_EditText);
        N_EditText.setVisibility(View.VISIBLE);
        Pb_TextView = v.findViewById(R.id.Pb_TextView);
        Pb_TextView.setVisibility(View.VISIBLE);
    }
    
    @Override
    protected void refreshText(boolean b) {
        super.refreshText(b);
        if (b) {
            lambda_EditText.setHint("A = " + lambda);
            N_EditText.setHint("N = " + N);
        
            Pb_TextView.setText("Pb = " + Pb);
        } else {
            lambda_EditText.setHint("A");
            N_EditText.setHint("N");
    
            Pb_TextView.setText("Pb");
        }
    }
    
    @Override
    public void onClick(View view) {
        Log.d("LOG", "MMVVN_CalculationFragment: onClick");
        String aStr = lambda_EditText.getText().toString();
        String muStr = mu_EditText.getText().toString();
        String vStr = V_EditText.getText().toString();
        String nStr = N_EditText.getText().toString();
    
        if (aStr.trim().length() == 0) {
            invalidInput("Пожалуйста, введите A", lambda_EditText);
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
        if (nStr.trim().length() == 0) {
            invalidInput("Пожалуйста, введите N", N_EditText);
            return;
        }
    
        try {
            lambda = Float.parseFloat(aStr);
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
        try {
            N = Integer.parseInt(nStr);
        } catch (Exception e) {
            invalidInput("Некорректный ввод", N_EditText);
            return;
        }
    
        lambda_EditText.setText("");
        mu_EditText.setText("");
        V_EditText.setText("");
        N_EditText.setText("");
    
        MMVVN m = (MMVVN) model;
        String error = m.setValues(lambda, mu, V, N);
        if (error != null) {
            invalidInput(error, lambda_EditText);
            return;
        }
        m.calculate();
        wasCalculated = true;
    
        // Скрыть клавиатуру
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(OK_Button.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    
        // Показать введённые данные
        k = m.getK_();
        t = m.getT_();
        Pt = m.getPt();
        Pb = m.getPb();
        refreshText(true);
    
        getP_Values();
        createGraphFragment();
    
        lambda_EditText.clearFocus();
        mu_EditText.clearFocus();
        V_EditText.clearFocus();
        N_EditText.clearFocus();
    }
}
