package com.dmitry_simakov.queue.fragments.calculaton.model_v;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dmitry_simakov.queue.ImageViewDialog;
import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.fragments.calculaton.Model_CalculationFragment;
import com.dmitry_simakov.queue.models.Model;
import com.dmitry_simakov.queue.models.Model_V;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;

public class Model_V_CalculationFragment extends Model_CalculationFragment {
    
    protected int V;
    protected EditText V_EditText;
    protected TextView V_TextView;
    public static final String V_VALUE = "V_VALUE";
    
    protected double Pt;
    protected TextView Pt_TextView;
    public static final String Pt_VALUE = "Pt_VALUE";
    
    @Override
    protected void getSavedInstanceStates(Bundle savedInstanceState) {
        super.getSavedInstanceStates(savedInstanceState);
        
        V = savedInstanceState.getInt(V_VALUE);
        Pt = savedInstanceState.getFloat(Pt_VALUE);
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        
        savedInstanceState.putInt(V_VALUE, V);
        savedInstanceState.putDouble(Pt_VALUE, Pt);
    }
    
    @Override
    protected void findViews(View v) {
        super.findViews(v);
        
        V_EditText = v.findViewById(R.id.V_EditText);
        V_EditText.setVisibility(View.VISIBLE);
        V_TextView = v.findViewById(R.id.V_TextView);
        V_TextView.setVisibility(View.VISIBLE);
        
        Pt_TextView = v.findViewById(R.id.Pt_TextView);
        Pt_TextView.setVisibility(View.VISIBLE);
        Pt_TextView.setOnClickListener(this);
    }
    
    @Override
    protected void setLastInputView() {
        V_EditText.setImeOptions(IME_ACTION_DONE);
        V_EditText.setOnEditorActionListener(this);
    }
    
    @Override
    protected void refreshTextViews() {
        super.refreshTextViews();
        
        if (wasCalculated) {
            Pt_TextView.setText("Pt = " + Pt);
    
            V_TextView.setText(""+ V);
        } else {
            Pt_TextView.setText("Pt");
        }
    }
    
    @Override
    public void onClick(View view) {
        super.onClick(view);
        
        switch (view.getId()) {
            case R.id.Pt_TextView:
                ImageViewDialog.createDialog(((Model_V)model).getPt_Formula(), ((Model_V)model).getPt_Description(), getActivity());
                break;
        }
    }
    
    protected String V_Str;
    
    @Override
    protected void getTextFromEditTexts() {
        super.getTextFromEditTexts();
        
        V_Str = V_EditText.getText().toString();
    }
    
    @Override
    protected void checkEditTextsFilled() {
        super.checkEditTextsFilled();
        
        if (V_Str.trim().length() == 0) {
            V_EditText.setError("Введите V");
            if (failedEditText == null) failedEditText = V_EditText;
        }
    }
    
    @Override
    protected void checkEditTextsCorrect() {
        super.checkEditTextsCorrect();
        
        try {
            V = Integer.parseInt(V_Str);
        } catch (Exception e) {
            V_EditText.setError("Некорректный ввод");
            if (failedEditText == null) failedEditText = V_EditText;
        }
    }
    
    @Override
    protected void checkValuesIsOnBounds() {
        super.checkValuesIsOnBounds();
    
        if (V <= 0) {
            lambda_EditText.setError("V∈[1; +∞)");
            if (failedEditText == null) failedEditText = V_EditText;
        }
    }
    
    @Override
    protected void moveTextToTV() {
        super.moveTextToTV();
        
        V_TextView.setText(V_EditText.getText());
        V_EditText.setText("");
    }
    
    @Override
    protected void moveTextToET() {
        super.moveTextToET();
        
        V_EditText.setText(V_TextView.getText());
    }
    
    @Override
    protected void setModelValues() throws Model.ConditionException {
        ((Model_V)model).setValues(lambda, mu, V);
    }
    
    @Override
    protected void getParametersFromModel() {
        super.getParametersFromModel();
        
        Pt = ((Model_V)model).getPt();
    }
    
    @Override
    protected void clearFocusFromEditTexts() {
        super.clearFocusFromEditTexts();
        
        V_EditText.clearFocus();
    }
}
