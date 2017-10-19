package com.dmitry_simakov.queue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dmitry_simakov.queue.fragments.BarGraphFragment;
import com.dmitry_simakov.queue.fragments.ModelViewFragment;

public class GraphActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        
        Intent intent = getIntent();
        
        ModelViewFragment modelViewFragment = (ModelViewFragment) getFragmentManager().findFragmentById(R.id.model_view_fragment);
        BarGraphFragment barGraphFragment = (BarGraphFragment) getFragmentManager().findFragmentById(R.id.bar_fragment);
    }
}