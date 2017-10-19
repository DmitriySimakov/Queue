package com.dmitry_simakov.queue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ModelActivity extends AppCompatActivity {
    
    public static final String MODEL_NAME = "MODEL_NAME";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("LOG", "ModelActivity: onCreate");
        setContentView(R.layout.activity_model);
        
        Intent intent = getIntent();
        String modelName = intent.getStringExtra(MODEL_NAME);
        
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(modelName);
        }
        
        ModelViewFragment modelViewFragment = (ModelViewFragment) getFragmentManager().findFragmentById(R.id.model_view_fragment);
        modelViewFragment.setModel(modelName);
    }
}