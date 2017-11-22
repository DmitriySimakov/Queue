package com.dmitry_simakov.queue.fragments.calculaton;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dmitry_simakov.queue.ImageViewDialog;
import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.fragments.calculaton.model_v.Model_V_CalculationFragment;
import com.dmitry_simakov.queue.models.MMVVN;
import com.dmitry_simakov.queue.models.Model;

public class MMVVN_CalculationFragment extends Model_V_CalculationFragment {
    
    private int N;
    private EditText N_EditText;
    private TextView N_TextView;
    public static final String N_VALUE = "N_VALUE";
    
    private double Pb;
    private TextView Pb_TextView;
    public static final String Pb_VALUE = "Pb_VALUE";
    
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
        savedInstanceState.putDouble(Pb_VALUE, Pb);
    }
    
    @Override
    protected void findViews(View v) {
        super.findViews(v);
    
        lambda_EditText.setHint("A");
        
        N_EditText = v.findViewById(R.id.N_EditText);
        N_EditText.setVisibility(View.VISIBLE);
        N_TextView = v.findViewById(R.id.N_TextView);
        N_TextView.setVisibility(View.VISIBLE);
        
        Pb_TextView = v.findViewById(R.id.Pb_TextView);
        Pb_TextView.setVisibility(View.VISIBLE);
        Pb_TextView.setOnClickListener(this);
    }
    
    @Override
    protected void refreshTextViews() {
        super.refreshTextViews();
        
        if (wasCalculated) {
            Pb_TextView.setText("Pb = " + Pb);
    
            N_TextView.setText(""+ N);
        } else {
            Pb_TextView.setText("Pb");
        }
    }
    
    @Override
    public void onClick(View view) {
        super.onClick(view);
        Log.d("LOG", "MMVVN_CalculationFragment: onClick");
        
        switch (view.getId()) {
            case R.id.Pb_TextView:
                ImageViewDialog.createDialog(((MMVVN)model).getPb_Formula(), getActivity());
                break;
        }
    }
    
    private String N_Str;
    
    @Override
    protected void getTextFromEditTexts() {
        super.getTextFromEditTexts();
        
        N_Str = N_EditText.getText().toString();
    }
    
    @Override
    protected void checkEditTextsFilled() {
        super.checkEditTextsFilled();
    
        if (lambda_Str.trim().length() == 0) {
            lambda_EditText.setError("Введите A");
            if (failedEditText == null) failedEditText = lambda_EditText;
        }
        
        if (N_Str.trim().length() == 0) {
            N_EditText.setError("Введите V");
            if (failedEditText == null) failedEditText = N_EditText;
        }
    }
    
    @Override
    protected void checkEditTextsCorrect() {
        super.checkEditTextsCorrect();
        
        try {
            N = Integer.parseInt(N_Str);
        } catch (Exception e) {
            N_EditText.setError("Некорректный ввод");
            if (failedEditText == null) failedEditText = N_EditText;
        }
    }
    
    @Override
    protected void checkValuesIsOnBounds() {
        super.checkValuesIsOnBounds();
    
        if (lambda <= 0 || lambda >= 1) {
            lambda_EditText.setError("A∈(0; 1)");
            if (failedEditText == null) failedEditText = lambda_EditText;
        }
        if (N <= 0 || N > 100) {
            N_EditText.setError("N∈[1; 100]");
            if (failedEditText == null) failedEditText = N_EditText;
        }
    }
    
    @Override
    protected void moveTextToTV() {
        super.moveTextToTV();
        
        N_TextView.setText(N_EditText.getText());
        N_EditText.setText("");
    }
    
    @Override
    protected void moveTextToET() {
        super.moveTextToET();
        
        N_EditText.setText(N_TextView.getText());
    }
    
    @Override
    protected void setModelValues() throws Model.ConditionException {
        ((MMVVN)model).setValues(lambda, mu, V, N);
    }
    
    @Override
    protected void getParametersFromModel() {
        super.getParametersFromModel();
        
        Pb = ((MMVVN)model).getPb();
    }
    
    @Override
    protected void clearFocusFromEditTexts() {
        super.clearFocusFromEditTexts();
        
        N_EditText.clearFocus();
    }
}
