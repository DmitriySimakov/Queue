package com.dmitry_simakov.queue;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements ModelsListFragment.ModelsListListener {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    @Override
    public void itemClicked(int id) {
        Log.d("LOG", "MainActivity: itemClicked");
        Intent intent = new Intent(this, ModelActivity.class);
        String modelName = ModelsListFragment.modelsNames[id];
        intent.putExtra(ModelActivity.MODEL_NAME, modelName);
        startActivity(intent);
    }
}