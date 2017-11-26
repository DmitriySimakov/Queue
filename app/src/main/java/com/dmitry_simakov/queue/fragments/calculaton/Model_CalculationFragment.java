package com.dmitry_simakov.queue.fragments.calculaton;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dmitry_simakov.queue.ImageViewDialog;
import com.dmitry_simakov.queue.ModelActivity;
import com.dmitry_simakov.queue.R;
import com.dmitry_simakov.queue.fragments.graphs.BarGraphFragment;
import com.dmitry_simakov.queue.models.Model;

import static android.view.inputmethod.EditorInfo.IME_ACTION_DONE;
import static com.dmitry_simakov.queue.fragments.MainActivityFragment.MODELS;

public class Model_CalculationFragment extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {
    
    protected Model model;
    protected int id;
    
    protected double lambda;
    protected EditText lambda_EditText;
    protected TextView lambda_TextView;
    public static final String LAMBDA_VALUE = "LAMBDA_VALUE";
    
    protected double mu;
    protected EditText mu_EditText;
    protected TextView mu_TextView;
    public static final String MU_VALUE = "MU_VALUE";
    
    protected Button OK_Button;
    protected Button popupButton;
    protected LinearLayout popupLayout;
    protected boolean popupIsOpen = false;
    public static final String POPUP_IS_OPEN = "POPUP_IS_OPEN";
    
    protected double k;
    protected TextView k_TextView;
    public static final String K_VALUE = "K_VALUE";
    
    protected double t;
    protected TextView t_TextView;
    public static final String T_VALUE = "T_VALUE";
    
    protected double[] P_Values = new double[11];
    public static final String P_VALUES = "P_VALUES";
    
    protected boolean wasCalculated = false;
    public static final String WAS_CALCULATED = "WAS_CALCULATED";
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        if (savedInstanceState != null) {
            getSavedInstanceStates(savedInstanceState);
        }
    }
    
    protected void getSavedInstanceStates(Bundle savedInstanceState) {
        wasCalculated = savedInstanceState.getBoolean(WAS_CALCULATED);
        popupIsOpen = savedInstanceState.getBoolean(POPUP_IS_OPEN);
        
        lambda = savedInstanceState.getDouble(LAMBDA_VALUE);
        mu = savedInstanceState.getDouble(MU_VALUE);
        
        k = savedInstanceState.getDouble(K_VALUE);
        t = savedInstanceState.getDouble(T_VALUE);
        P_Values = savedInstanceState.getDoubleArray(P_VALUES);
    }
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    
        final View v = inflater.inflate(R.layout.fragment_model_calc, container, false);
    
        // Получаем модель по id
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt(ModelActivity.MODEL_ID);
        }
        model = MODELS[id];
        
        findViews(v);
        setLastInputView();
        
        if (popupIsOpen) {
            popupLayout.setVisibility(View.VISIBLE);
        }
        
        refreshTextViews();
        createGraphFragment();
        
        return v;
    }
    
    protected void findViews(View v) {
        lambda_EditText = v.findViewById(R.id.lambda_EditText);
        mu_EditText = v.findViewById(R.id.mu_EditText);
        OK_Button = v.findViewById(R.id.OK_Button);
        OK_Button.setOnClickListener(this);
    
        popupLayout = v.findViewById(R.id.popupLayout);
        lambda_TextView = v.findViewById(R.id.lambda_TextView);
        mu_TextView = v.findViewById(R.id.mu_TextView);
        popupButton = v.findViewById(R.id.popupButton);
        popupButton.setOnClickListener(this);
        
        k_TextView = v.findViewById(R.id.k_TextView);
        k_TextView.setOnClickListener(this);
        t_TextView = v.findViewById(R.id.t_TextView);
        t_TextView.setOnClickListener(this);
        TextView P_TextView = v.findViewById(R.id.Pk_TextView);
        P_TextView.setOnClickListener(this);
    }
    
    protected void setLastInputView() {
        mu_EditText.setImeOptions(IME_ACTION_DONE);
        mu_EditText.setOnEditorActionListener(this);
    }
    
    protected void refreshTextViews() {
        if (wasCalculated) {
            k_TextView.setText("k_ = " + k);
            t_TextView.setText("t_ = " + t);
    
            lambda_TextView.setText(""+ lambda);
            mu_TextView.setText(""+ mu);
        } else {
            k_TextView.setText("k_");
            t_TextView.setText("t_");
        }
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(WAS_CALCULATED, wasCalculated);
        savedInstanceState.putBoolean(POPUP_IS_OPEN, popupIsOpen);
        
        savedInstanceState.putDouble(LAMBDA_VALUE, lambda);
        savedInstanceState.putDouble(MU_VALUE, mu);
    
        savedInstanceState.putDouble(K_VALUE, k);
        savedInstanceState.putDouble(T_VALUE, t);
        savedInstanceState.putDoubleArray(P_VALUES, P_Values);
    }
    
    @Override
    public void onClick(View view) {
        
        switch (view.getId()) {
            case R.id.OK_Button:
                onOkButtonPressed();
                break;
            case R.id.popupButton:
                moveTextToET();
                popupLayout.setVisibility(View.GONE);
                popupIsOpen = false;
                break;
            case R.id.k_TextView:
                ImageViewDialog.createDialog(model.getK_Formula(), model.getK_Description(), getActivity());
                break;
            case R.id.t_TextView:
                ImageViewDialog.createDialog(model.getT_Formula(), model.getT_Description(), getActivity());
                break;
            case R.id.Pk_TextView:
                ImageViewDialog.createDialog(model.getPk_Formula(), model.getPk_Description(), getActivity());
                break;
        }
    }
    
    protected String lambda_Str;
    protected String mu_Str;
    protected EditText failedEditText;
    
    protected void onOkButtonPressed() {
        getTextFromEditTexts();
        checkEditTextsFilled();
        if (failedEditText != null) {
            failedEditText.requestFocus();
            return;
        }
        checkEditTextsCorrect();
        if (failedEditText != null) {
            failedEditText.requestFocus();
            return;
        }
        checkValuesIsOnBounds();
        if (failedEditText != null) {
            failedEditText.requestFocus();
            return;
        }
    
        moveTextToTV();
        popupLayout.setVisibility(View.VISIBLE);
        popupIsOpen = true;
    
        try {
            setModelValues();
        } catch (Model.ConditionException e) {
            ImageViewDialog.createDialog(e.getConditionImage(), "", getActivity());
            return;
        }
        model.calculate();
        wasCalculated = true;
        getParametersFromModel();
    
        // Скрыть клавиатуру
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(OK_Button.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    
        // Показать вычисленные параметры
        refreshTextViews();
        createGraphFragment();
        clearFocusFromEditTexts();
    }
    
    protected void getTextFromEditTexts() {
        lambda_Str = lambda_EditText.getText().toString();
        mu_Str = mu_EditText.getText().toString();
    }
    
    protected void checkEditTextsFilled() {
        failedEditText = null;
        if (lambda_Str.trim().length() == 0) {
            lambda_EditText.setError("Введите λ");
            failedEditText = lambda_EditText;
        }
        if (mu_Str.trim().length() == 0) {
            mu_EditText.setError("Введите μ");
            if (failedEditText == null) failedEditText = mu_EditText;
        }
    }
    
    protected void checkEditTextsCorrect() {
        try {
            lambda = Double.parseDouble(lambda_Str);
        } catch (Exception e) {
            lambda_EditText.setError("Некорректный ввод");
            failedEditText = lambda_EditText;
        }
        
        try {
            mu = Double.parseDouble(mu_Str);
        } catch (Exception e) {
            mu_EditText.setError("Некорректный ввод");
            if (failedEditText == null) failedEditText = mu_EditText;
        }
    }
    
    protected void checkValuesIsOnBounds() {
        if (lambda <= 0) {
            lambda_EditText.setError("λ∈(0; +∞)");
            if (failedEditText == null) failedEditText = lambda_EditText;
        }
        if (mu <= 0) {
            lambda_EditText.setError("μ∈(0; +∞)");
            if (failedEditText == null) failedEditText = mu_EditText;
        }
    }
    
    protected void moveTextToTV() {
        lambda_TextView.setText(lambda_EditText.getText());
        mu_TextView.setText(mu_EditText.getText());
        
        lambda_EditText.setText("");
        mu_EditText.setText("");
    }
    
    protected void moveTextToET() {
        lambda_EditText.setText(lambda_TextView.getText());
        mu_EditText.setText(mu_TextView.getText());
    }
    
    protected void setModelValues() throws Model.ConditionException {
        model.setValues(lambda, mu);
    }
    
    protected void getParametersFromModel() {
        k = model.getK_();
        t = model.getT_();
        P_Values = model.getP();
    }
    
    protected void createGraphFragment() {
        FragmentManager myFragmentManager = getFragmentManager();
        Fragment fragment = null;
        Bundle args = new Bundle();
        args.putDoubleArray(P_VALUES, P_Values);
        fragment = new BarGraphFragment();
        fragment.setArguments(args);
        myFragmentManager.beginTransaction().replace(R.id.graph_frame, fragment).commit();
    }
    
    protected void clearFocusFromEditTexts() {
        lambda_EditText.clearFocus();
        mu_EditText.clearFocus();
    }
    
    @Override
    public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
        if (id == EditorInfo.IME_ACTION_DONE) {
            onOkButtonPressed();
            return true;
        }
        return false;
    }
}